package com.atimbo.recipe.dao

import com.atimbo.common.utils.UniqueIDGenerator
import com.atimbo.recipe.domain.IngredientEntity
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DAOSpecification
import com.sun.jersey.api.NotFoundException

class IngredientDAOSpec extends DAOSpecification<IngredientDAO> {

    EntityBuilder builder
    IngredientEntity ingredient
    RecipeEntity recipeEntity

    def setup() {
        builder = new EntityBuilder(sessionFactory)
        recipeEntity = new RecipeEntity(uuId: UniqueIDGenerator.generateUUId(),
                                        title: 'Meatstraganza!',
                                        createdBy: 'ast')
        ingredient = new IngredientEntity(description: 'meat', createdBy: 'ast')
    }

    @Override
    IngredientDAO buildDAO() {
        return new IngredientDAO(sessionFactory)
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }

    void 'get ingredient with non-existent id throws error'(){
        given: 'no ingredients'

        when: 'getting an ingredient by an id'
        dao.findById(1)

        then: 'a hibernate exception is thrown'
        Exception e = thrown(NotFoundException)
    }

    void 'creating a new ingredient for an existing recipe succeeds'() {
        given: 'a recipe'
        builder.daoFor(RecipeEntity, RecipeDAO).createOrUpdate(recipeEntity)
        ingredient.recipe = recipeEntity

        when: 'creating the ingredient'
        IngredientEntity expectedEntity = dao.create(ingredient)

        then: 'the ingredient exists'
        expectedEntity

        when: 'getting the ingredient by id'
        IngredientEntity ingredientEntity = dao.findById(expectedEntity.id)

        then: 'the ingredient is found'
        ingredientEntity == expectedEntity
        ingredientEntity.recipe == recipeEntity
    }
}
