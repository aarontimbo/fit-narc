package com.atimbo.recipe.util

import com.atimbo.recipe.domain.*
import com.atimbo.test.dao.BaseEntityBuilder

import org.hibernate.SessionFactory

class EntityBuilder extends BaseEntityBuilder {

    static List<Class<?>> getAllEntities() {
        return [RecipeEntity]
    }

    EntityBuilder(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

}