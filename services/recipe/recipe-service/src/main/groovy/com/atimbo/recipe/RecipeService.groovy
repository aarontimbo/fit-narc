package com.atimbo.recipe

import com.atimbo.fitnarc.service.spring.SpringBundle
import com.atimbo.recipe.conf.RecipeConfiguration
import com.atimbo.recipe.domain.DirectionEntity
import com.atimbo.recipe.domain.IngredientEntity
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableList
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.hibernate.SessionFactoryFactory
import com.yammer.dropwizard.migrations.MigrationsBundle
import org.hibernate.SessionFactory
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class RecipeService extends Service<RecipeConfiguration> {

    final String service_name = 'service_recipe'

    protected ObjectMapper objectMapper

    public static final List<Class<?>> SERVICE_ENTITIES = [
            DirectionEntity,
            IngredientEntity,
            RecipeEntity,
            RecipeSourceEntity
    ]

    protected MigrationsBundle<RecipeConfiguration> migrationsBundle = initializeMigrationsBundle()

    private final HibernateBundle<RecipeConfiguration> hibernateBundle =
        new HibernateBundle<RecipeConfiguration>(
                ImmutableList.copyOf(serviceEntities),
                new SessionFactoryFactory()
        ) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(RecipeConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    public static void main(String[] args) throws Exception {
        new RecipeService().run(args)
    }

    @Override
    void initialize(Bootstrap<RecipeConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle)
        bootstrap.addBundle(migrationsBundle)
        ConfigurableApplicationContext context = createContext()
        addSpring(bootstrap, context)
    }

    @Override
    void run(RecipeConfiguration configuration, Environment environment) {
        environment.objectMapperFactory.disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
        objectMapper = environment.objectMapperFactory.build()
    }

    protected SpringBundle addSpring(Bootstrap<RecipeConfiguration> bootstrap,
                                     ConfigurableApplicationContext context) {
        SpringBundle bundle = new SpringBundle( context , true, true, true)
        bootstrap.addBundle(bundle)
        return bundle
    }

    protected ConfigurableApplicationContext createContext() {
        ConfigurableApplicationContext ctx = createBaseContext()
        ConfigurableListableBeanFactory beanFactory = ctx.beanFactory
        beanFactory.registerSingleton('sessionFactory', new FactoryBean<SessionFactory>() {
            @Override
            SessionFactory getObject() throws Exception {
                return hibernateBundle.sessionFactory
            }

            @Override
            Class<?> getObjectType() {
                return SessionFactory
            }

            @Override
            boolean isSingleton() {
                return true
            }
        })
        return ctx
    }

    protected ConfigurableApplicationContext createBaseContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()
        context.scan(this.class.package.name)
        return context
    }

    void addResources(RecipeConfiguration configuration, Environment environment) throws ClassNotFoundException {
        //Handled by Spring... see SpringBundle
    }

    @Override
    MigrationsBundle<RecipeConfiguration> initializeMigrationsBundle() {
        new MigrationsBundle<RecipeConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(RecipeConfiguration configuration) {
                return configuration.database
            }
        }
    }

    @Override
    protected List<Class> getServiceEntities() {
        SERVICE_ENTITIES
    }

}
