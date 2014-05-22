package com.atimbo.fitnarc.service.spring

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Preconditions
import com.sun.jersey.spi.inject.InjectableProvider
import com.yammer.dropwizard.ConfiguredBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Configuration
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.lifecycle.Managed
import com.yammer.dropwizard.tasks.Task
import com.yammer.metrics.core.HealthCheck
import groovy.util.logging.Slf4j
import org.eclipse.jetty.util.component.LifeCycle
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.ConfigurableApplicationContext

import javax.ws.rs.Path
import javax.xml.ws.Provider

/**
 * Politely lifted from https://raw.github.com/nhuray/dropwizard-spring/master/src/main/java/
 * com/github/nhuray/dropwizard/spring/SpringBundle.java
 *
 * A bundle which load Spring Application context to automatically initialize Dropwizard {@link Environment}
 * including health checks, resources, providers, tasks and managed. */
@Slf4j
class SpringBundle<T extends Configuration> implements ConfiguredBundle<T> {

    public static final String CONFIGURATION_BEAN_NAME = 'dw'
    public static final String ENVIRONMENT_BEAN_NAME = 'dwEnv'
    public static final String PLACEHOLDER_BEAN_NAME = 'dwPlaceholder'

    private final ConfigurableApplicationContext context
    private ConfigurationPlaceholderConfigurer placeholderConfigurer
    private boolean shouldRegisterConfiguration
    private boolean shouldRegisterEnvironment

    /**
     * Creates a new SpringBundle to automatically initialize Dropwizard {@link Environment}
     * <p/>
     *
     * @param context the application context to load
     */
    public SpringBundle(ConfigurableApplicationContext context) {
        this(context, false, false, false)
    }

    /**
     * Creates a new SpringBundle to automatically initialize Dropwizard {@link Environment}
     * <p/>
     *
     * @param context               the application context to load
     * @param shouldRegisterConfiguration register Dropwizard configuration as a Spring Bean.
     * @param shouldRegisterEnvironment   register Dropwizard environment as a Spring Bean.
     * @param registerPlaceholder   resolve Dropwizard configuration as properties.
     */
    public SpringBundle(ConfigurableApplicationContext context, boolean shouldRegisterConfiguration,
                        boolean shouldRegisterEnvironment, boolean registerPlaceholder) {
        if (shouldRegisterConfiguration || shouldRegisterEnvironment  || registerPlaceholder) {
            Preconditions.checkArgument(!context.isActive(), 'Context must be not active in order to register ' +
                    'configuration, environment or placeholder')
        }
        this.context = context
        this.shouldRegisterConfiguration = shouldRegisterConfiguration
        this.shouldRegisterEnvironment = shouldRegisterEnvironment
        if (registerPlaceholder) {
            this.placeholderConfigurer = new ConfigurationPlaceholderConfigurer()
        }
    }

    /**
     * Creates a new SpringBundle to automatically initialize Dropwizard {@link Environment}
     * <p/>
     *
     * @param context               the application context to load
     * @param shouldRegisterConfiguration register Dropwizard configuration as a Spring Bean.
     * @param shouldRegisterEnvironment   register Dropwizard environment as a Spring Bean.
     * @param placeholderConfigurer placeholderConfigurer to resolve Dropwizard configuration as properties.
     */
    public SpringBundle(ConfigurableApplicationContext context, boolean shouldRegisterConfiguration,
                        boolean shouldRegisterEnvironment, ConfigurationPlaceholderConfigurer placeholderConfigurer) {
        Preconditions.checkArgument(placeholderConfigurer != null, 'PlaceholderConfigurer is required')
        if (shouldRegisterConfiguration || shouldRegisterEnvironment  || placeholderConfigurer != null) {
            Preconditions.checkArgument(!context.isActive(), 'Context must be not active in order to register ' +
                    'configuration, environment or placeholder')
        }
        this.context = context
        this.shouldRegisterConfiguration = shouldRegisterConfiguration
        this.shouldRegisterEnvironment = shouldRegisterEnvironment
        this.placeholderConfigurer = placeholderConfigurer
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        // Register Dropwizard Configuration as a Bean Spring.
        if (shouldRegisterConfiguration) {
            registerConfiguration(configuration, context)
        }

        // Register the Dropwizard environment
        if (shouldRegisterEnvironment) {
            registerEnvironment(environment, context)
        }

        // Register a placeholder to resolve Dropwizard Configuration as properties.
        if (placeholderConfigurer != null) {
            registerPlaceholder(environment, configuration, context)
        }

        // Refresh context if is not active
        if (!context.isActive()) {
            context.refresh()
        }

        // Initialize Dropwizard environment
        registerManaged(environment, context)
        registerLifecycle(environment, context)
        registerTasks(environment, context)
        registerHealthChecks(environment, context)
        registerInjectableProviders(environment, context)
        registerProviders(environment, context)
        registerResources(environment, context)
    }


    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // nothing doing
    }

    public ConfigurableApplicationContext getContext() {
        return context
    }

    public void setPlaceholderConfigurer(ConfigurationPlaceholderConfigurer placeholderConfigurer) {
        Preconditions.checkArgument(placeholderConfigurer != null, 'PlaceholderConfigurer is required')
        this.placeholderConfigurer = placeholderConfigurer
    }

    public void setRegisterConfiguration(boolean registerConfiguration) {
        this.shouldRegisterConfiguration = registerConfiguration
    }

    public void setRegisterEnvironment(boolean registerEnvironment) {
        this.shouldRegisterEnvironment = registerEnvironment
    }

    // ~ Dropwizard Environment initialization methods -----------------------------------------------------------------

    /**
     * Register {@link Managed}s in Dropwizard {@link Environment} from Spring application context.
     *
     * @param environment the Dropwizard environment
     * @param context     the Spring application context
     */
    private void registerManaged(Environment environment, ConfigurableApplicationContext context) {
        context.getBeansOfType(Managed).each { String beanName, Managed managed ->
            // Add managed to Dropwizard environment
            environment.manage(managed)
            log.info('Registering managed: ' + managed.class.name)
        }
    }


    /**
     * Register {@link LifeCycle}s in Dropwizard {@link Environment} from Spring application context.
     *
     * @param environment the Dropwizard environment
     * @param context     the Spring application context
     */
    private void registerLifecycle(Environment environment, ConfigurableApplicationContext context) {
        context.getBeansOfType(LifeCycle).each { String beanName, LifeCycle lifeCycle ->
            // Add lifeCycle to Dropwizard environment
            if (beanName != ENVIRONMENT_BEAN_NAME) {
                environment.manage(lifeCycle)
                log.info('Registering lifeCycle: ' + lifeCycle.class.name)
            }
        }
    }


    /**
     * Register {@link Task}s in Dropwizard {@link Environment} from Spring application context.
     *
     * @param environment the Dropwizard environment
     * @param context     the Spring application context
     */
    private void registerTasks(Environment environment, ConfigurableApplicationContext context) {
        context.getBeansOfType(Task).each { String beanName, Task task ->
            // Add task to Dropwizard environment
            environment.addTask(task)
            log.info('Registering task: ' + task.class.name)
        }
    }


    /**
     * Register {@link HealthCheck}s in Dropwizard {@link Environment} from Spring application context.
     *
     * @param environment the Dropwizard environment
     * @param context     the Spring application context
     */
    private void registerHealthChecks(Environment environment, ConfigurableApplicationContext context) {
        context.getBeansOfType(HealthCheck).each { String beanName, HealthCheck healthCheck ->
            // Add healthCheck to Dropwizard environment
            environment.addHealthCheck(healthCheck)
            log.info('Registering healthCheck: ' + healthCheck.class.name)
        }
    }


    /**
     * Register {@link InjectableProvider}s in Dropwizard {@link Environment} from Spring application context.
     *
     * @param environment the Dropwizard environment
     * @param context     the Spring application context
     */
    private void registerInjectableProviders(Environment environment, ConfigurableApplicationContext context) {
        context.getBeansOfType(InjectableProvider).each { String beanName, InjectableProvider injectableProvider ->
            // Add injectableProvider to Dropwizard environment
            environment.addProvider(injectableProvider)
            log.info('Registering injectable provider: ' + injectableProvider.class.name)
        }
    }

    /**
     * Register objects annotated with {@link Provider} in Dropwizard {@link Environment} from Spring
     * application context.
     *
     * @param environment the Dropwizard environment
     * @param context     the Spring application context
     */
    private void registerProviders(Environment environment, ConfigurableApplicationContext context) {
        context.getBeansWithAnnotation(Provider).each { String beanName, Provider provider ->
            // Add injectableProvider to Dropwizard environment
            environment.addProvider(provider)
            log.info('Registering provider : ' + provider.class.name)
        }
    }


    /**
     * Register resources annotated with {@link Path} in Dropwizard {@link Environment} from Spring application context.
     *
     * @param environment the Dropwizard environment
     * @param context     the Spring application context
     */
    private void registerResources(Environment environment, ConfigurableApplicationContext context) {
        context.getBeansWithAnnotation(Path).each { String beanName, Object resource ->
            // Add injectableProvider to Dropwizard environment
            environment.addResource(resource)
            log.info('Registering resource : ' + resource.class.name)
        }
    }

    /**
     * Register Dropwizard {@link Configuration} as a Bean Spring.
     *
     * @param configuration Dropwizard {@link Configuration}
     * @param context       spring application context
     */
    private void registerConfiguration(T configuration, ConfigurableApplicationContext context) {
        ConfigurableListableBeanFactory beanFactory = context.beanFactory
        beanFactory.registerSingleton(CONFIGURATION_BEAN_NAME, configuration)
        log.info('Registering Dropwizard Configuration under name : ' + CONFIGURATION_BEAN_NAME)
    }


    /**
     * Register Dropwizard {@link Environment} as a Bean Spring.
     *
     * @param environment Dropwizard {@link Environment}
     * @param context     Spring application context
     */
    private void registerEnvironment(Environment environment, ConfigurableApplicationContext context) {
        ConfigurableListableBeanFactory beanFactory = context.beanFactory
        beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, environment)
        log.info('Registering Dropwizard Environment under name : ' + ENVIRONMENT_BEAN_NAME)
    }


    /**
     * Register a placeholder to resolve Dropwizard Configuration as properties.
     *
     * @param configuration Dropwizard configuration
     * @param context       spring application context
     */
    private void registerPlaceholder(Environment environment, T configuration, ConfigurableApplicationContext context) {
        ConfigurableListableBeanFactory beanFactory = context.beanFactory
        ObjectMapper objectMapper = environment.objectMapperFactory.build()
                .disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
        placeholderConfigurer.setObjectMapper(objectMapper)
        placeholderConfigurer.setConfiguration(configuration)
        beanFactory.registerSingleton(PLACEHOLDER_BEAN_NAME, placeholderConfigurer)
        log.info('Registering Dropwizard Placeholder under name : ' + PLACEHOLDER_BEAN_NAME)
    }
}
