package com.atimbo.recipe.transfer

import org.joda.time.LocalDateTime

import javax.validation.constraints.NotNull

/**
 * Base transfer object to be extended by {@link RecipeItem}s.
 */
class RecipeItem extends ATimboId implements Comparable {

    @NotNull
    Recipe recipe

    @NotNull
    Integer sortOrder

    String lastUpdatedBy

    @NotNull
    LocalDateTime dateCreated

    @NotNull
    LocalDateTime lastUpdated

    public int compareTo(Object anotherItem) {
        this.sortOrder <=> anotherItem.sortOrder
    }

}
