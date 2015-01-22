package com.atimbo.recipe.resources

import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.TestDAOFactory
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.modules.RecipeModule
import com.atimbo.recipe.modules.RecipeSourceModule
import com.atimbo.recipe.modules.builders.RecipeBuilder
import com.atimbo.recipe.transfer.Recipe
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DatabaseSpecification

class RecipeResourceSpec extends DatabaseSpecification {

    private static final String TITLE = 'Spicy Ribs'
    private static final String CREATED_BY = 'ast'

    EntityBuilder entityBuilder
    RecipeBuilder recipeBuilder
    RecipeModule recipeModule
    RecipeResource recipeResource
    RecipeSourceModule recipeSourceModule

    @Override
    def setup() {
        entityBuilder = new EntityBuilder(sessionFactory)
        DAOFactory daoFactory = new TestDAOFactory(sessionFactory)

        recipeBuilder = new RecipeBuilder()
        recipeModule = new RecipeModule(daoFactory)
        recipeModule.recipeBuilder = recipeBuilder

        recipeSourceModule = Mock()
        recipeModule.recipeSourceModule = recipeSourceModule

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

}
