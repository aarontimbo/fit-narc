package com.atimbo.recipe.modules

import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.DirectionDAO
import com.atimbo.recipe.dao.IngredientDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.modules.builders.RecipeItemBuilder
import com.atimbo.recipe.transfer.RecipeItem
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.inject.Inject

/**
 * Create and Update {@link RecipeItemEntity} objects.
 */
@Service
class RecipeItemModule {

    final DirectionDAO directionDAO
    final IngredientDAO ingredientDAO

    @Resource
    RecipeItemBuilder builder

    @Inject
    RecipeItemModule(DAOFactory daoFactory) {
        this.directionDAO = daoFactory
        this.ingredientDAO = ingredientDAO
    }

    RecipeItemEntity createOrUpdateRecipeItem(RecipeEntity recipeEntity, RecipeItem recipeItem) {
        if (recipeItem) {
            RecipeItemEntity recipeItemEntity = ingredientDAO.findByUuId(recipeItem.uuId)
            boolean isNew = false
            if (!recipeItemEntity) {
                isNew = true
                recipeItemEntity = new RecipeItemEntity(recipe: recipeEntity, createdBy: recipeEntity.createdBy)
            }
            builder.build()

            return recipeItemEntity
        }
        return null
    }
}
