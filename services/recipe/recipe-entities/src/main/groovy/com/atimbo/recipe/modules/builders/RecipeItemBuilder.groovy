package com.atimbo.recipe.modules.builders

import com.atimbo.common.RecipeItemType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.IngredientDAO
import com.atimbo.recipe.domain.IngredientEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.transfer.Ingredient
import com.atimbo.recipe.transfer.RecipeItem
import org.joda.time.LocalDateTime
import org.springframework.stereotype.Service

import javax.inject.Inject

/**
 * Build {@link RecipeItemEntity}
 * from request.
 */
@Service
class RecipeItemBuilder<T> {

    final IngredientDAO ingredientDAO

    @Inject
    RecipeItemBuilder(DAOFactory daoFactory) {
        this.ingredientDAO = daoFactory.ingredientDAO
    }

    public void build(RecipeItemEntity recipeItemEntity, RecipeItem recipeItem){
        if (recipeItemEntity instanceof IngredientEntity) {
            buildIngredient(recipeItemEntity as IngredientEntity, recipeItem as Ingredient)
        }
    }

    private void baseBuilder(RecipeItemEntity recipeItemEntity, RecipeItem recipeItem) {
        recipeItemEntity.with {
            lastUpdatedBy = recipeItem.lastUpdatedBy ?: recipeItemEntity.createdBy
            sortOrder     = recipeItem.sortOrder ?: 1
        }

        // Modify last updated date if the item exists
        if (recipeItemEntity.id) {
            recipeItemEntity.lastUpdated = new LocalDateTime()
        }
    }

    private void buildIngredient(IngredientEntity ingredientEntity, Ingredient ingredient) {
        baseBuilder(ingredientEntity, ingredient)

        ingredientEntity.with {
            foodId       = ingredient.foodId
            foodSequence = ingredient.foodSequence
            amount       = ingredient.amount
            description  = ingredient.description
        }
    }

}
