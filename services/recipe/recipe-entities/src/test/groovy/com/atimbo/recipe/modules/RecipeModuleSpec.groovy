package com.atimbo.recipe.modules

import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.TestDAOFactory
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.modules.builders.RecipeBuilder
import com.atimbo.recipe.transfer.RecipeCreateRequest
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DatabaseSpecification

class RecipeModuleSpec extends DatabaseSpecification {

    RecipeBuilder recipeBuilder
    RecipeModule module

    @Override
    def setup() {
        DAOFactory daoFactory = new TestDAOFactory(sessionFactory)
        module = new RecipeModule(daoFactory)

        recipeBuilder = new RecipeBuilder()
        module.recipeBuilder = recipeBuilder
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }

    void 'create a recipe from a create request'() {
        given:
        RecipeCreateRequest createRequest = new RecipeCreateRequest(
                title: 'meatstraganza',
                description: 'meaty goodness',
                createdBy: 'ast'
        )

        when:
        RecipeEntity recipeEntity = module.createFromRequest(createRequest)

        then:
        recipeEntity
        recipeEntity.uuId
        recipeEntity.lastUpdatedBy == createRequest.createdBy
    }
}
