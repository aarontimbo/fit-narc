package com.atimbo.recipe.transfer

import org.joda.time.LocalDateTime

import javax.validation.constraints.NotNull

/**
 * Created with IntelliJ IDEA.
 * User: ast
 * Date: 11/19/14
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
