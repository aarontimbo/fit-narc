package com.atimbo.recipe.dao

import com.atimbo.recipe.domain.RecipeSourceEntity
import com.sun.jersey.api.NotFoundException
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class RecipeSourceDAO extends AbstractDAO<RecipeSourceEntity> {

    RecipeSourceDAO(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

    RecipeSourceEntity create(RecipeSourceEntity recipeSource) {
        persist(recipeSource)
    }

    RecipeSourceEntity findById(Long id) {
        RecipeSourceEntity recipeSource = get(id)
        if (!recipeSource) {
            throw new NotFoundException("recipe source not found by id: ${id}")
        }
        return recipeSource
    }
}
