package com.atimbo.recipe.transfer

import groovy.transform.ToString

import javax.validation.constraints.NotNull

@ToString
class RecipeCreateUpdateRequest {

    /**
     * Unique identifier for recipe. If null this
     * is a create request. If exists, update the recipe.
     */
    String uuId

    /** Recipe title */
    @NotNull
    String title

    /** Long description of the recipe */
    String description

    List<RecipeSource> sources

    List<Ingredient> ingredients

    List<Direction> directions

    /** Provenance data **/
    String createdBy
    String updatedBy
}
