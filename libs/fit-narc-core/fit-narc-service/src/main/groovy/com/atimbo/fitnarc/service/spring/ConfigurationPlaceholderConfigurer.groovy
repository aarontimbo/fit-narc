package com.atimbo.fitnarc.service.spring

import com.fasterxml.jackson.databind.ObjectMapper
import com.yammer.dropwizard.config.Configuration
import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanDefinitionStoreException
import org.springframework.beans.factory.BeanInitializationException
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.BeanDefinitionVisitor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.util.PropertiesPersister
import org.springframework.util.PropertyPlaceholderHelper
import org.springframework.util.StringValueResolver

/**
 * Politely taken from https://raw.github.com/nhuray/dropwizard-spring/master/src/main/java/com/github/nhuray/
 * dropwizard/spring/config/ConfigurationPlaceholderConfigurer.java
 *
 * {@link org.springframework.beans.factory.config.PlaceholderConfigurerSupport} subclass that resolves
 * ${...} placeholders
 * against the Dropwizard {@link Configuration}..
 */
class ConfigurationPlaceholderConfigurer {

    private Configuration configuration

    private ObjectMapper objectMapper

    private PropertiesPersister propertiesPersister

    // ~ Copied from {@link PlaceholderConfigurerSupport} -------------------------------------------------------------

    /** Default placeholder prefix: {@value} */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = '${'

    /** Default placeholder suffix: {@value} */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = '}'

    /** Default value separator: {@value} */
    public static final String DEFAULT_VALUE_SEPARATOR = ':'

    /** Defaults to {@value #DEFAULT_PLACEHOLDER_PREFIX} */
    protected String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX

    /** Defaults to {@value #DEFAULT_PLACEHOLDER_SUFFIX} */
    protected String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX

    /** Defaults to {@value #DEFAULT_VALUE_SEPARATOR} */
    protected String valueSeparator = DEFAULT_VALUE_SEPARATOR

    protected boolean ignoreUnresolvablePlaceholders = false

    protected String nullValue

    /**
     * {@linkplain #processProperties process} properties against the given bean factory.
     *
     * @throws BeanInitializationException if any properties cannot be loaded
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            Properties props = new Properties()

            // Load properties
            loadProperties(props)

            // Process properties
            processProperties(beanFactory, props)
        } catch (IOException ex) {
            throw new BeanInitializationException('Could not load properties from Dropwizard configuration', ex)
        }
    }

    /**
     * {@linkplain #loadProperties load} properties against the Configuration.
     *
     * @throws BeanInitializationException if any properties cannot be loaded
     */
    private void loadProperties(Properties props) throws BeansException, IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream()
        objectMapper.writeValue(stream, configuration)
        propertiesPersister.load(props, new ByteArrayInputStream(stream.toByteArray()))
    }

    /**
     * @param beanFactory
     * @param props
     * @throws BeansException
     */
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
    throws BeansException {
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(props)
        doProcessProperties(beanFactory, valueResolver)
    }


    /**
     * Set the prefix that a placeholder string starts with.
     * The default is {@value #DEFAULT_PLACEHOLDER_PREFIX}.
     */
    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix
    }

    /**
     * Set the suffix that a placeholder string ends with.
     * The default is {@value #DEFAULT_PLACEHOLDER_SUFFIX}.
     */
    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix
    }

    /**
     * Specify the separating character between the placeholder variable
     * and the associated default value, or {@code null} if no such
     * special character should be processed as a value separator.
     * The default is {@value #DEFAULT_VALUE_SEPARATOR}.
     */
    public void setValueSeparator(String valueSeparator) {
        this.valueSeparator = valueSeparator
    }

    /**
     * Set a value that should be treated as {@code null} when
     * resolved as a placeholder value: e.g. '' (empty String) or 'null'.
     * <p>Note that this will only apply to full property values,
     * not to parts of concatenated values.
     * <p>By default, no such null value is defined. This means that
     * there is no way to express {@code null} as a property
     * value unless you explicitly map a corresponding value here.
     */
    public void setNullValue(String nullValue) {
        this.nullValue = nullValue
    }

    /**
     * Set whether to ignore unresolvable placeholders.
     * <p>Default is 'false': An exception will be thrown if a placeholder fails
     * to resolve. Switch this flag to 'true' in order to preserve the placeholder
     * String as-is in such a case, leaving it up to other placeholder configurers
     * to resolve it.
     */
    public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders
    }

    /**
     * Set the configuration to load properties.
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration
    }

    public void setObjectMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
        this.propertiesPersister = new JsonPropertiesPersister(objectMapper)
    }

    // ~ Copied from {@link PropertyPlaceholderConfigurer} --------------------------------------------------------


    class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final PropertyPlaceholderHelper helper
        private final PropertyPlaceholderHelper.PlaceholderResolver resolver

        public PlaceholderResolvingStringValueResolver(Properties props) {
            this.helper = new PropertyPlaceholderHelper(
                    placeholderPrefix, placeholderSuffix, valueSeparator, ignoreUnresolvablePlaceholders)
            this.resolver = new PropertyPlaceholderConfigurerResolver(props)
        }

        public String resolveStringValue(String strVal) throws BeansException {
            String value = this.helper.replacePlaceholders(strVal, this.resolver)
            return (value == nullValue ? null : value)
        }
    }

    class PropertyPlaceholderConfigurerResolver implements PropertyPlaceholderHelper.PlaceholderResolver {
        private final Properties props

        private PropertyPlaceholderConfigurerResolver(Properties props) {
            this.props = props
        }

        public String resolvePlaceholder(String placeholderName) {
            return props.getProperty(placeholderName)
        }
    }

    // ~ Copied and adapted from {@link PlaceholderConfigurerSupport} ---------------------------------------------

    @SuppressWarnings('CatchException')
    protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                       StringValueResolver valueResolver) {
        BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver)
        String[] beanNames = beanFactoryToProcess.beanDefinitionNames
        for (String curName : beanNames) {
            BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName)
            try {
                visitor.visitBeanDefinition(bd)
            } catch (Exception ex) {
                throw new BeanDefinitionStoreException(bd.resourceDescription, curName, ex.message)
            }
        }

        // New in Spring 2.5: resolve placeholders in alias target names and aliases as well.
        beanFactoryToProcess.resolveAliases(valueResolver)

        // New in Spring 3.0: resolve placeholders in embedded values such as annotation attributes.
        beanFactoryToProcess.addEmbeddedValueResolver(valueResolver)
    }
}
