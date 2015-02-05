package com.atimbo.recipe.dao

import com.atimbo.common.utils.UniqueIDGenerator
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DAOSpecification
import com.sun.jersey.api.NotFoundException

class RecipeDAOSpec extends DAOSpecification<RecipeDAO> {

    EntityBuilder builder
    RecipeEntity recipeEntity

    def setup() {
        builder = new EntityBuilder(sessionFactory)
        recipeEntity = new RecipeEntity(uuId: UniqueIDGenerator.generateUUId(),
                                        title: 'Meatstraganza!',
                                        createdBy: 'ast',
                                        servings: 4)
    }

    @Override
    RecipeDAO buildDAO() {
        return new RecipeDAO(sessionFactory)
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }

    void 'get recipe with non-existent id throws error'(){
        given: 'no recipes'

        when: 'getting a recipe by an id'
        dao.findById(1)

        then: 'a hibernate exception is thrown'
        Exception e = thrown(NotFoundException)
    }

    void 'creating a new recipe succeeds'() {
        given: 'a new recipe'

        when: 'creating the recipe'
        RecipeEntity expectedRecipe = dao.createOrUpdate(recipeEntity)

        then: 'the recipe exists'
        expectedRecipe

        when: 'getting the recipe by id'
        RecipeEntity recipe = dao.findById(expectedRecipe.id)

        then: 'the recipe is found'
        recipe == expectedRecipe

    }


}
