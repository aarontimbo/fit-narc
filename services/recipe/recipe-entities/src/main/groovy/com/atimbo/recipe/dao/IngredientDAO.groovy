package com.atimbo.recipe.dao

import com.atimbo.recipe.domain.IngredientEntity
import com.sun.jersey.api.NotFoundException
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.hibernate.criterion.Restrictions
import org.springframework.stereotype.Repository

import javax.inject.Inject

@Repository
class IngredientDAO extends AbstractDAO<IngredientEntity> {

    @Inject
    IngredientDAO(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

    IngredientEntity createOrUpdate(IngredientEntity ingredient) {
        persist(ingredient)
    }

    IngredientEntity findById(Long id) {
        IngredientEntity ingredient = get(id)
        if (!ingredient) {
            throw new NotFoundException("no ingredient found with id: ${id}")
        }
        return ingredient
    }

    IngredientEntity findByUuId(String uuId) {
        return uniqueResult(criteria().add(Restrictions.eq('uuId', uuId)))
    }
}
