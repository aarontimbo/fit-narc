package com.atimbo.recipe.modules.builders

import com.atimbo.common.RecipeItemType
import com.atimbo.common.RecipeSourceType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.TestDAOFactory
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.transfer.RecipeSource
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DatabaseSpecification
import spock.lang.Unroll

class RecipeSourceBuilderSpec extends DatabaseSpecification {

    private static final String AUTHOR = 'Willy Wonka'
    private static final Integer SERVINGS  = 4
    private static final String TITLE  = 'Death by Chocolate'

    EntityBuilder entityBuilder
    RecipeEntity recipeEntity
    RecipeSourceBuilder builder

    @Override
    void setup() {
        recipeEntity = new RecipeEntity(title: 'Meatstraganza!',
                                        createdBy: 'ast',
                                        servings: SERVINGS)
        entityBuilder = new EntityBuilder(sessionFactory)
        entityBuilder.save(recipeEntity)

        DAOFactory daoFactory = new TestDAOFactory(sessionFactory)
        builder = new RecipeSourceBuilder(daoFactory)
    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.allEntities
    }

    @Unroll
    void 'recipe source builder canBuild value is #canBuild for #itemType'() {
        given: 'a recipe item'
        RecipeSource recipeSource = new RecipeSource(
                author:     AUTHOR,
                title:      TITLE,
                sortOrder:  1,
                sourceType: RecipeSourceType.COOKBOOK,
                type:       itemType
        )

        when: 'canBuild is checked'
        boolean checked = builder.canBuild(recipeSource)

        then: 'expected canBuild value'
        checked == canBuild

        where:
        itemType                     | canBuild
        RecipeItemType.RECIPE_SOURCE | true
        RecipeItemType.DIRECTION     | false
        RecipeItemType.INGREDIENT    | false
    }

    void 'build recipe source from a request'() {
        given: 'a recipe source create request'
        RecipeSource recipeSource = new RecipeSource(
                author:     AUTHOR,
                title:      TITLE,
                sortOrder:  1,
                sourceType: RecipeSourceType.COOKBOOK,
                type:       RecipeItemType.RECIPE_SOURCE
        )

        when: 'the recipe source is built'
        RecipeSourceEntity entity = builder.build(recipeEntity, recipeSource)

        then: 'the entity is built with the expected values'
        entity
        with(entity) {
            author     == AUTHOR
            title      == TITLE
            sortOrder  == 1
            sourceType == RecipeSourceType.COOKBOOK
        }

    }

}
