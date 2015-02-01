package com.atimbo.recipe.modules.builders

import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.transfer.RecipeItem
import org.joda.time.LocalDateTime

/**
 * Abstract Builder for {@link RecipeItemEntity}
 */
abstract class AbstractRecipeItemBuilder {

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
