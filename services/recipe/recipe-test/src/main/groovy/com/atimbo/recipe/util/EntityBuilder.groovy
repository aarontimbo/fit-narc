package com.atimbo.recipe.util

import com.atimbo.recipe.domain.*
import com.atimbo.test.dao.BaseEntityBuilder

import org.hibernate.SessionFactory

class EntityBuilder extends BaseEntityBuilder {

    static List<Class<?>> getAllEntities() {
        return [
                DirectionEntity,
                IngredientEntity,
                RecipeEntity,
                RecipeSourceEntity
        ]
    }

    EntityBuilder(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

}