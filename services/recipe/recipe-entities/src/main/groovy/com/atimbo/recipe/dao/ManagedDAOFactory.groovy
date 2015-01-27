package com.atimbo.recipe.dao

import javax.inject.Inject
import org.springframework.stereotype.Component

@Component
class ManagedDAOFactory implements DAOFactory {

    @Inject
    RecipeDAO recipeDAO

    @Inject
    RecipeSourceDAO recipeSourceDAO

}
