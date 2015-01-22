package com.atimbo.recipe.dao

interface DAOFactory {

    RecipeDAO getRecipeDAO()

    RecipeSourceDAO getRecipeSourceDAO()

}