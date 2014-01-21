package com.atimbo.common

enum RecipeSourceType {
    COOKBOOK('Cookbook'),
    MAGAZINE('Magazine'),
    WEBSITE('Website'),
    UNKNOWN('Unknown')

    final String id

    public RecipeSourceType(String id) {
        this.id = id
    }

}
