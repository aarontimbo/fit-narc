package com.atimbo.recipe.transfer

import javax.validation.constraints.NotNull

/**
 * Transfer object representing a DirectionEntity.
 */
class Direction extends RecipeItem {

    @NotNull
    String description
}
