package com.atimbo.recipe.dao

import com.atimbo.recipe.domain.RecipeEntity
import com.sun.jersey.api.NotFoundException
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.hibernate.criterion.Restrictions
import org.springframework.stereotype.Repository

import javax.inject.Inject
import javax.persistence.EntityNotFoundException

@Repository
class RecipeDAO extends AbstractDAO<RecipeEntity> {

    @Inject
    RecipeDAO(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

    RecipeEntity createOrUpdate(RecipeEntity recipe) {
        persist(recipe)
    }

    RecipeEntity findById(Long id) {
        RecipeEntity recipe = get(id)
        if (!recipe) {
            throw new NotFoundException("no recipe found with id: ${id}")
        }
        return recipe
    }

    RecipeEntity findByUUId(String uuId) {
        RecipeEntity recipeEntity = uniqueResult(criteria().add(Restrictions.eq('uuId', uuId)))
        if (! recipeEntity) {
            throw new EntityNotFoundException("Unable to find recipe with UUID: ${uuId}")
        }
        return recipeEntity
    }
}
