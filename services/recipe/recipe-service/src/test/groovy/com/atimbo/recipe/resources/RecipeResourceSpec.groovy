package com.atimbo.recipe.resources

import com.atimbo.common.RecipeItemType
import com.atimbo.common.RecipeSourceType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.TestDAOFactory
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.modules.RecipeItemModule
import com.atimbo.recipe.modules.RecipeModule
import com.atimbo.recipe.modules.builders.RecipeBuilder
import com.atimbo.recipe.transfer.Recipe
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import com.atimbo.recipe.transfer.RecipeSource
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DatabaseSpecification

class RecipeResourceSpec extends DatabaseSpecification {

    private static final String TITLE = 'Spicy Ribs'
    private static final String CREATED_BY = 'ast'

    EntityBuilder entityBuilder
    RecipeBuilder recipeBuilder
    RecipeModule recipeModule
    RecipeResource recipeResource
    RecipeItemModule recipeItemModule

    @Override
    def setup() {
        entityBuilder = new EntityBuilder(sessionFactory)
        DAOFactory daoFactory = new TestDAOFactory(sessionFactory)

        recipeBuilder = new RecipeBuilder()
        recipeModule = new RecipeModule(daoFactory)
        recipeModule.recipeBuilder = recipeBuilder

        recipeItemModule = new RecipeItemModule(daoFactory)
        recipeModule.recipeItemModule = recipeItemModule

        recipeResource = new RecipeResource(recipeModule, objectMapper)
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }


    void 'find existing recipe by unique identifier'() {
        given:
        RecipeEntity recipeEntity = new RecipeEntity(title: TITLE, createdBy: CREATED_BY)
        entityBuilder.save(recipeEntity)

        when:
        Recipe recipeTO = recipeResource.findRecipe(recipeEntity.uuId)

        then:
        recipeTO
        recipeTO.uuId == recipeEntity.uuId
    }

    void 'create recipe from request'() {
        given: 'a recipe create request'
        RecipeCreateUpdateRequest request = new RecipeCreateUpdateRequest(
            title: TITLE,
            createdBy: CREATED_BY
        )

        when: 'we get a recipe from a request'
        Recipe recipeTO = recipeResource.getRecipe(request)

        then: 'the recipe is created'
        recipeTO
        recipeTO.uuId
        recipeTO.title == TITLE
    }

    void 'update existing recipe from request'() {
        given: 'an existing recipe'
        RecipeEntity recipeEntity = new RecipeEntity(title: TITLE, createdBy: CREATED_BY)
        entityBuilder.save(recipeEntity)

        and: 'a recipe create request'
        RecipeCreateUpdateRequest request = new RecipeCreateUpdateRequest(
                uuId: recipeEntity.uuId,
                title: TITLE,
                description: 'succulent pork ribs',
                updatedBy: 'tsa'
        )

        when: 'we get a recipe from a request'
        Recipe recipeTO = recipeResource.getRecipe(request)

        then: 'the recipe is updated'
        recipeTO
        with(recipeTO) {
            uuId          == recipeEntity.uuId
            title         == TITLE
            description   == request.description
            lastUpdatedBy == request.updatedBy
        }
    }

    void 'create recipe from request with recipe items'() {
        given: 'a recipe create request'
        RecipeCreateUpdateRequest request = new RecipeCreateUpdateRequest(
                title: TITLE,
                createdBy: CREATED_BY
        )

        and: 'a recipe source'
        RecipeSource recipeSourceTO = new RecipeSource(
                author:     'Me at',
                title:      'How to Cook Meat',
                sourceType: RecipeSourceType.COOKBOOK,
                sortOrder:  1,
                type:       RecipeItemType.RECIPE_SOURCE
        )
        request.sources << recipeSourceTO

        when: 'we get a recipe from a request'
        Recipe recipeTO = recipeResource.getRecipe(request)

        then: 'the recipe is created'
        recipeTO
        recipeTO.uuId
        recipeTO.title == TITLE
        recipeTO.sources.size() == 1
    }

}
