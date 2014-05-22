package com.atimbo.recipe.conf

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.json.ObjectMapperFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FitNarcConfiguration {

    @Bean
    ObjectMapperFactory provideObjectMapperFactory(Environment environment) {
        ObjectMapperFactory objectMapperFactory = environment.objectMapperFactory
        objectMapperFactory.disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
        return objectMapperFactory
    }

    @Bean
    ObjectMapper provideObjectMapper(ObjectMapperFactory factory) {
        return factory.build()
    }

}
