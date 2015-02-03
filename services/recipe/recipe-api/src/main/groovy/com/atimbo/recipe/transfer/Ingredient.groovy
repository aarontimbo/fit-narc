package com.atimbo.recipe.transfer

import com.atimbo.common.RecipeItemType
import com.fasterxml.jackson.annotation.JsonPropertyOrder

import javax.validation.constraints.NotNull

/**
 * Transfer object representing a recipe {@link Ingredient}.
 */
@JsonPropertyOrder(alphabetic=true)
class Ingredient extends RecipeItem {

    Long foodId

    Long foodSequence

    @NotNull
    Float amount

    String description

    RecipeItemType type = RecipeItemType.INGREDIENT

    @Override
    boolean getIngredient() { true }
}
