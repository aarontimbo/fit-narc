package com.atimbo.recipe.transfer

import com.atimbo.common.RecipeItemType

import javax.validation.constraints.NotNull

/**
 * Transfer object representing a DirectionEntity.
 */
class Direction extends RecipeItem {

    @NotNull
    String description

    RecipeItemType type = RecipeItemType.DIRECTION
}
