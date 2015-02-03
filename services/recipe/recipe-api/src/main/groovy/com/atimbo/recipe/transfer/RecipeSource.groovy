package com.atimbo.recipe.transfer

import com.atimbo.common.RecipeItemType
import com.atimbo.common.RecipeSourceType

import javax.validation.constraints.NotNull

/**
 * Transfer object for {@link RecipeSource}
 */
class RecipeSource extends RecipeItem {

    @NotNull
    String author

    @NotNull
    String title

    String sourceUrl

    @NotNull
    RecipeSourceType sourceType

    RecipeItemType type = RecipeItemType.RECIPE_SOURCE

    @Override
    boolean getRecipeSource() { true }
}
