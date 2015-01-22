package com.atimbo.recipe.modules

import com.atimbo.common.RecipeSourceType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.TestDAOFactory
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.modules.builders.RecipeSourceBuilder
import com.atimbo.recipe.transfer.RecipeSource
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DatabaseSpecification

class RecipeSourceModuleSpec extends DatabaseSpecification {

    private static final String AUTHOR = 'Willy Wonka'
    private static final String TITLE  = 'Death by Chocolate'

    EntityBuilder entityBuilder
    RecipeEntity recipeEntity
    RecipeSourceModule module

    @Override
    void setup() {
        DAOFactory daoFactory = new TestDAOFactory(sessionFactory)

        module = new RecipeSourceModule(daoFactory)
        module.recipeSourceBuilder = new RecipeSourceBuilder()

        entityBuilder = new EntityBuilder(sessionFactory)
        recipeEntity = new RecipeEntity(title: 'Meatstraganza!', createdBy: 'ast')
        entityBuilder.save(recipeEntity)
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }

    void 'create recipe source from request'() {
        given: 'a recipe'
        assert recipeEntity

        and: 'a create request'
        RecipeSource recipeSource = new RecipeSource(
                author: AUTHOR,
                title: TITLE,
                sourceType: RecipeSourceType.COOKBOOK,
                sortOrder: 1,
        )

        when: 'the recipe source is generated from the request'
        RecipeSourceEntity entity = module.createOrUpdate(recipeEntity, recipeSource)

        then:
        entity
        with(entity) {
            author     == AUTHOR
            title      == TITLE
            sourceType == RecipeSourceType.COOKBOOK
        }
    }
}
