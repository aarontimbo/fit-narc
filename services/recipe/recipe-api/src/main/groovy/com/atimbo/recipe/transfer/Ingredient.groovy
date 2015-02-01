package com.atimbo.recipe.transfer

import com.fasterxml.jackson.annotation.JsonPropertyOrder

import javax.validation.constraints.NotNull

/**
 * Transfer object representing a recipe ingredient.
 */
@JsonPropertyOrder(alphabetic=true)
class Ingredient extends RecipeItem {

    Long foodId

    Long foodSequence

    @NotNull
    Float amount

    String description

    @Override
    boolean getIngredient() { true }
}
