package com.atimbo.common

public enum RecipeItemType {

    RECIPE_SOURCE('Recipe Source'),
    INGREDIENT('Ingredient'),
    DIRECTION('Direction')

    final String id

    RecipeItemType(String id) {
        this.id = id
    }

}