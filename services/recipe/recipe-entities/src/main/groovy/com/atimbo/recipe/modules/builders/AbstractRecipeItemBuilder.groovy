package com.atimbo.recipe.modules.builders

import com.atimbo.recipe.domain.IngredientEntity
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.transfer.Ingredient
import com.atimbo.recipe.transfer.RecipeItem
import org.joda.time.LocalDateTime

/**
 * Abstract Builder for {@link RecipeItemEntity}
 */
abstract class AbstractRecipeItemBuilder<T extends RecipeItemEntity> {

    abstract boolean canBuild(RecipeItem item)

    abstract T build(RecipeEntity recipeEntity, RecipeItem item)

    abstract T getOrCreateRecipeItem(RecipeEntity recipeEntity, RecipeItem item)

    protected void baseBuilder(RecipeItemEntity recipeItemEntity, RecipeItem recipeItem) {
        recipeItemEntity.with {
            lastUpdatedBy = recipeItem.lastUpdatedBy ?: recipeItemEntity.createdBy
            sortOrder     = recipeItem.sortOrder ?: 1
        }

        // Modify last updated date if the item exists
        if (recipeItemEntity.id) {
            recipeItemEntity.lastUpdated = new LocalDateTime()
        }
    }

}
