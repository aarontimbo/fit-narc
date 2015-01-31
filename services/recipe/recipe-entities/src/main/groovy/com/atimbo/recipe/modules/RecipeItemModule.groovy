package com.atimbo.recipe.modules

import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.DirectionDAO
import com.atimbo.recipe.dao.IngredientDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.modules.builders.RecipeItemBuilder
import com.atimbo.recipe.modules.builders.RecipeSourceBuilder
import com.atimbo.recipe.transfer.RecipeItem
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.inject.Inject

/**
 * Create and Update {@link RecipeItemEntity} objects.
 */
@Service
class RecipeItemModule {

    List<RecipeItemBuilder> builders

    @Inject
    RecipeItemModule(DAOFactory daoFactory) {
        init(daoFactory)
    }

    RecipeItemEntity createOrUpdateRecipeItem(RecipeEntity recipeEntity, RecipeItem recipeItem) {
        if (!recipeItem) { return null }

        RecipeItemEntity itemEntity
        builders.each { RecipeItemBuilder builder ->
            if (builder.canBuild(itemEntity)) {
                return builder.build(recipeEntity, recipeItem)
            }
        }
    }

    private void init(DAOFactory daoFactory) {
        builders << new RecipeSourceBuilder(daoFactory)
    }

}
