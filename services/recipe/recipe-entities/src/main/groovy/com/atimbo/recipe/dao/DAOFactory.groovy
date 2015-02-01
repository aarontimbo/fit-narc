package com.atimbo.recipe.dao

interface DAOFactory {

    DirectionDAO getDirectionDAO()

    IngredientDAO getIngredientDAO()

    RecipeDAO getRecipeDAO()

    RecipeSourceDAO getRecipeSourceDAO()

}