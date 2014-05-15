package com.atimbo.recipe.dao

import com.atimbo.common.utils.UniqueIDGenerator
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DAOSpecification
import com.sun.jersey.api.NotFoundException

class RecipeSourceDAOSpec extends DAOSpecification<RecipeSourceDAO> {

    EntityBuilder builder
    RecipeEntity recipeEntity
    RecipeSourceEntity recipeSource

    def setup() {
        builder = new EntityBuilder(sessionFactory)
        recipeEntity = new RecipeEntity(uuId: UniqueIDGenerator.generateUUId(),
                                        title: 'Meatstraganza!',
                                        createdBy: 'ast')
        recipeSource = new RecipeSourceEntity(author: 'Swedish Chef',
                                              title: 'Swedish Meatballs',
                                              createdBy: 'ast')
    }

    @Override
    RecipeSourceDAO buildDAO() {
        return new RecipeSourceDAO(sessionFactory)
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }

    void 'get recipe source with non-existent id throws error'(){
        given: 'no recipe sources'

        when: 'getting a recipe source by an id'
        dao.findById(1)

        then: 'a hibernate exception is thrown'
        Exception e = thrown(NotFoundException)
    }

    void 'creating a new recipe source for an existing recipe succeeds'() {
        given: 'a recipe'
        builder.daoFor(RecipeEntity, RecipeDAO).create(recipeEntity)
        recipeSource.recipe = recipeEntity

        when: 'creating the recipe source'
        RecipeSourceEntity expectedEntity = dao.create(recipeSource)

        then: 'the recipe source exists'
        expectedEntity

        when: 'getting the recipe source by id'
        RecipeSourceEntity recipeSourceEntity = dao.findById(expectedEntity.id)

        then: 'the recipe source is found'
        recipeSourceEntity == expectedEntity
        recipeSourceEntity.recipe == recipeEntity

    }

}
