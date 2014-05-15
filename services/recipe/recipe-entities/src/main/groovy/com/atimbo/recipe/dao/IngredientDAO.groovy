package com.atimbo.recipe.dao

import com.atimbo.recipe.domain.IngredientEntity
import com.sun.jersey.api.NotFoundException
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class IngredientDAO extends AbstractDAO<IngredientEntity> {

    IngredientDAO(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

    IngredientEntity create(IngredientEntity ingredient) {
        persist(ingredient)
    }

    IngredientEntity findById(Long id) {
        IngredientEntity ingredient = get(id)
        if (!ingredient) {
            throw new NotFoundException("no ingredient found with id: ${id}")
        }
        return ingredient
    }
}
