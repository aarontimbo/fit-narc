package com.atimbo.recipe.transfer

import org.joda.time.LocalDateTime

import javax.validation.constraints.NotNull

/**
 * Base transfer object to be extended by {@link RecipeItem}s.
 */
class RecipeItem extends ATimboId implements Comparable {

    Integer sortOrder

    String lastUpdatedBy

    LocalDateTime dateCreated
    LocalDateTime lastUpdated

    public int compareTo(Object anotherItem) {
        this.sortOrder <=> anotherItem.sortOrder
    }

}
