package com.atimbo.recipe.transfer

import com.atimbo.common.RecipeItemType
import org.joda.time.LocalDateTime

import javax.validation.constraints.NotNull

/**
 * Base transfer object to be extended by {@link RecipeItem}s.
 */
class RecipeItem extends ATimboId implements Comparable {

    Integer sortOrder

    String lastUpdatedBy

    RecipeItemType type

    LocalDateTime dateCreated
    LocalDateTime lastUpdated

    int compareTo(Object anotherItem) {
        this.sortOrder <=> anotherItem.sortOrder
    }

    boolean getIngredient() { false }

}
