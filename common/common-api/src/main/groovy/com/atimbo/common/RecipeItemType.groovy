package com.atimbo.common

enum RecipeItemType {

    DIRECTION('Direction'),
    INGREDIENT('Ingredient'),
    NOTE('Note')

    final String id

    public RecipeItemType(String id) {
        this.id = id
    }

}