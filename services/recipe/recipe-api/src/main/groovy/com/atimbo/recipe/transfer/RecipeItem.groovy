package com.atimbo.recipe.transfer

import com.atimbo.common.RecipeItemType
import org.joda.time.LocalDateTime

import javax.validation.constraints.NotNull

/**
 * Base transfer object to be extended by {@link RecipeItem}s.
 */
class RecipeItem extends ATimboId implements Comparable {

    /**
     * Required.
     *
     * {@link RecipeItem}s will be translated as a {@link SortedSet}.
     *
     * MUST BE UNIQUE within a Set of type (e.g. RecipeSource, Ingredient)
     * or not all items will be returned. Items with duplicate sort
     * order values will be ignored.
     */
    @NotNull
    Integer sortOrder

    String lastUpdatedBy

    /**
     * Required.
     *
     * Used to martial {@link RecipeItem} to the correct
     * RecipeItemBuilder.
     */
    @NotNull
    RecipeItemType type

    LocalDateTime dateCreated
    LocalDateTime lastUpdated

    int compareTo(Object anotherItem) {
        this.sortOrder <=> anotherItem.sortOrder
    }

    boolean getRecipeSource() { false }

    boolean getIngredient() { false }

    boolean getDirection() { false }

}
