package com.atimbo.recipe.dao

import com.atimbo.common.utils.UniqueIDGenerator
import com.atimbo.recipe.domain.DirectionEntity
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DAOSpecification
import com.sun.jersey.api.NotFoundException

class DirectionDAOSpec extends DAOSpecification<DirectionDAO> {

    EntityBuilder builder
    DirectionEntity direction
    RecipeEntity recipeEntity

    def setup() {
        builder = new EntityBuilder(sessionFactory)
        recipeEntity = new RecipeEntity(uuId: UniqueIDGenerator.generateUUId(),
                                        title: 'Meatstraganza!',
                                        createdBy: 'ast')
        direction = new DirectionEntity(description: 'grill', sortOrder: 1, createdBy: 'ast')
    }

    @Override
    DirectionDAO buildDAO() {
        return new DirectionDAO(sessionFactory)
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }

    void 'get direction with non-existent id throws error'(){
        given: 'no directions'

        when: 'getting an direction by an id'
        dao.findById(1)

        then: 'a hibernate exception is thrown'
        Exception e = thrown(NotFoundException)
    }

    void 'creating a new direction for an existing recipe succeeds'() {
        given: 'a recipe'
        builder.daoFor(RecipeEntity, RecipeDAO).createOrUpdate(recipeEntity)
        direction.recipe = recipeEntity

        when: 'creating the direction'
        DirectionEntity expectedEntity = dao.createOrUpdate(direction)

        then: 'the direction exists'
        expectedEntity

        when: 'getting the direction by unique id'
        DirectionEntity directionEntity = dao.findById(expectedEntity.id)

        then: 'the direction is found'
        directionEntity == expectedEntity
        directionEntity.recipe == recipeEntity
    }

}
