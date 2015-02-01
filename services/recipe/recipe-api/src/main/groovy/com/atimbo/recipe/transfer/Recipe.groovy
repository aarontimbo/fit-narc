package com.atimbo.recipe.transfer

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.joda.time.LocalDateTime

import javax.validation.constraints.NotNull

/**
 * Transfer object representing a RecipeEntity.
 */
@JsonPropertyOrder(alphabetic=true)
class Recipe extends ATimboId {

    @NotNull
    String title

    String description

    List<RecipeSource> sources = []

    List<Ingredient> ingredients = []

    @NotNull
    String createdBy

    @NotNull
    String lastUpdatedBy

    @NotNull
    LocalDateTime dateCreated

    @NotNull
    LocalDateTime lastUpdated

}
