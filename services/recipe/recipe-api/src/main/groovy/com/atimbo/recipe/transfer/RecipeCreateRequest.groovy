package com.atimbo.recipe.transfer

import groovy.transform.ToString

@ToString
class RecipeCreateRequest {
    String title
    String description
    String createdBy
}
