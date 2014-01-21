package com.atimbo.recipe.dao

import com.atimbo.recipe.domain.RecipeEntity
import com.sun.jersey.api.NotFoundException
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class RecipeDAO extends AbstractDAO<RecipeEntity> {

    RecipeDAO(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

    RecipeEntity create(RecipeEntity recipe) {
        persist(recipe)
    }

    RecipeEntity findById(Long id) {
        RecipeEntity recipe = get(id)
        if (!recipe) {
            throw new NotFoundException("no recipe found with id: ${id}")
        }
        return recipe
    }
}
