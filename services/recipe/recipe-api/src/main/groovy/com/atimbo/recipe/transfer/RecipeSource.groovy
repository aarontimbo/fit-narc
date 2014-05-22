package com.atimbo.recipe.transfer

import com.atimbo.common.RecipeSourceType

import javax.validation.constraints.NotNull

/**
 * Created with IntelliJ IDEA.
 * User: ast
 * Date: 11/19/14
 */
class RecipeSource extends RecipeItem {

    @NotNull
    String author

    @NotNull
    String title

    String sourceUrl

    @NotNull
    RecipeSourceType sourceType

}
