package com.atimbo.recipe.modules.builders

import com.atimbo.common.RecipeItemType
import com.atimbo.common.utils.UniqueIDGenerator
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.IngredientDAO
import com.atimbo.recipe.dao.TestDAOFactory
import com.atimbo.recipe.domain.IngredientEntity
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.transfer.Ingredient
import com.atimbo.recipe.util.EntityBuilder
import com.atimbo.test.dao.DatabaseSpecification

class RecipeItemBuilderSpec extends DatabaseSpecification {

    EntityBuilder entityBuilder
    IngredientDAO ingredientDAO
    RecipeEntity recipeEntity
    RecipeItemBuilder builder

    @Override
    void setup() {
        ingredientDAO = new IngredientDAO(sessionFactory)
        DAOFactory daoFactory = new TestDAOFactory(sessionFactory)

        builder = new RecipeItemBuilder(daoFactory)

        recipeEntity = new RecipeEntity(uuId: UniqueIDGenerator.generateUUId(),
                                        title: 'Meatstraganza!',
                                        createdBy: 'ast')

    }

    @Override
    List<Class<?>> getEntities() {
        return EntityBuilder.getAllEntities()
    }

    void 'create a new recipe item of type'() {
        given: 'a recipe'

        and: 'a recipe item create request'
        Ingredient ingredient = new Ingredient(
                sortOrder:    6,
                amount:       2.0,
                foodId:       1234,
                foodSequence: 5,
                type:         RecipeItemType.INGREDIENT
        )

        and: 'a recipe item entity'
        IngredientEntity ingredientEntity = new IngredientEntity(
                recipe:    recipeEntity,
                createdBy: recipeEntity.createdBy
        )

        when: 'the item is built'
        builder.build(ingredientEntity, ingredient)

        then: 'the expected item is returned'
        ingredientEntity
        with(ingredientEntity) {
            amount       == ingredient.amount
            sortOrder    == ingredient.sortOrder
            foodId       == ingredient.foodId
            foodSequence == ingredient.foodSequence
            recipe       == recipeEntity
        }

    }
}
