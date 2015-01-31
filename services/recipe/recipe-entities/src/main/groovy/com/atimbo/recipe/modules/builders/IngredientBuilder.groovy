package com.atimbo.recipe.modules.builders

import com.atimbo.common.RecipeItemType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.IngredientDAO
import com.atimbo.recipe.domain.IngredientEntity
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.transfer.RecipeItem

import javax.inject.Inject

/**
 * Build {@link IngredientEntity} from request.
 */
class IngredientBuilder extends AbstractRecipeItemBuilder {

    final IngredientDAO ingredientDAO

    @Inject
    IngredientBuilder(DAOFactory daoFactory) {
        this.ingredientDAO = daoFactory.ingredientDAO
    }

    public boolean canBuild(RecipeItem item) {
        return item.type == RecipeItemType.INGREDIENT
    }

    public IngredientEntity build(RecipeEntity recipeEntity, RecipeItem item) {
        IngredientEntity entity = getOrCreateRecipeItem(recipeEntity, item)

        baseBuilder(entity, item)

        entity.with {
            foodId       = item.foodId
            foodSequence = item.foodSequence
            amount       = item.amount
            description  = item.description
        }
        return entity
    }

    IngredientEntity getOrCreateRecipeItem(RecipeEntity recipeEntity, RecipeItem item) {
        IngredientEntity entity = ingredientDAO.findByUuId(item.uuId)
        if (!entity) {
            entity = new IngredientEntity(recipe: recipeEntity, createdBy: recipeEntity.createdBy)
        }
        return entity
    }

}
