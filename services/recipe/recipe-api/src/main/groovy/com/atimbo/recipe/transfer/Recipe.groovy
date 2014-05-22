package com.atimbo.recipe.transfer

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import javax.validation.constraints.NotNull

import org.joda.time.LocalDate

/**
 * Transfer object representing a RecipeEntity.
 */
@JsonPropertyOrder(alphabetic=true)
class Recipe extends ATimboId {

    @NotNull
    String title

    String description

    List<Ingredient> ingredients = []

    List<Direction> directions = []

    RecipeSource recipeSource

    @NotNull
    String createdBy

    String lastUpdatedBy

    @NotNull
    LocalDate dateCreated = new LocalDate()

    @NotNull
    LocalDate lastUpdated = new LocalDate()

}
