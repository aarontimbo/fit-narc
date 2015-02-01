package com.atimbo.recipe.modules.builders

import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.transfer.RecipeItem

/**
 * Interface for building {@link RecipeItemEntity} from {@link RecipeItem}.
 */
public interface RecipeItemBuilder<T extends RecipeItemEntity> {

    public boolean canBuild(RecipeItem item)

    public T build(RecipeEntity recipeEntity, Object item)

    public T getOrCreateRecipeItem(RecipeEntity recipeEntity, RecipeItem item)
}