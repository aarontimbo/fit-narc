package com.atimbo.recipe.modules

import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.TestDAOFactory
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.modules.builders.RecipeBuilder
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DatabaseSpecification

class RecipeModuleSpec extends DatabaseSpecification {

    EntityBuilder entityBuilder
    RecipeBuilder recipeBuilder
    RecipeModule module
    RecipeSourceModule recipeSourceModule

    @Override
    def setup() {
        entityBuilder = new EntityBuilder(sessionFactory)
        DAOFactory daoFactory = new TestDAOFactory(sessionFactory)
        module = new RecipeModule(daoFactory)
        recipeSourceModule = Mock()
        module.recipeSourceModule = recipeSourceModule

        recipeBuilder = new RecipeBuilder()
        module.recipeBuilder = recipeBuilder
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }

    void 'create a recipe from a create request'() {
        given:
        RecipeCreateUpdateRequest request = new RecipeCreateUpdateRequest(
                title: 'meatstraganza',
                description: 'meaty goodness',
                createdBy: 'ast'
        )

        when:
        RecipeEntity recipeEntity = module.createOrUpdateFromRequest(request)

        then:
        recipeEntity
        recipeEntity.uuId
        recipeEntity.title         == request.title
        recipeEntity.description   == request.description
        recipeEntity.createdBy     == request.createdBy
        recipeEntity.lastUpdatedBy == request.createdBy
    }

    void 'update existing recipe from a request'() {
        given: 'an existing recipe'
        RecipeEntity existingRecipe = new RecipeEntity(title: 'not enough meat', createdBy: 'tsa')
        entityBuilder.save(existingRecipe)

        and: 'a update request'
        RecipeCreateUpdateRequest request = new RecipeCreateUpdateRequest(
                uuId:        existingRecipe.uuId,
                title:       'meatstraganza',
                description: 'meaty goodness',
                createdBy:   'ast'
        )

        when:
        RecipeEntity recipeEntity = module.createOrUpdateFromRequest(request)

        then: 'the recipe has been updated'
        recipeEntity
        recipeEntity.uuId          == existingRecipe.uuId
        recipeEntity.title         == request.title
        recipeEntity.description   == request.description
        recipeEntity.createdBy     == existingRecipe.createdBy
        recipeEntity.lastUpdatedBy == request.createdBy
    }

}
