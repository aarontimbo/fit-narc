package com.atimbo.recipe.modules.builders

import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.transfer.RecipeItem

/**
 * Interface for building {@link RecipeItemEntity} from {@link RecipeItem}.
 */
public interface RecipeItemBuilder {

    public boolean canBuild(RecipeItemEntity recipeItemEntity)

    public RecipeItemEntity build(RecipeEntity recipeEntity, RecipeItem recipeItem)

}