package com.atimbo.recipe.modules.builders

import com.atimbo.common.RecipeItemType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.IngredientDAO
import com.atimbo.recipe.domain.IngredientEntity
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.transfer.Ingredient
import com.atimbo.recipe.transfer.RecipeItem

import javax.inject.Inject

/**
 * Build {@link IngredientEntity} from request.
 */
class IngredientBuilder extends AbstractRecipeItemBuilder implements RecipeItemBuilder {

    final IngredientDAO ingredientDAO

    @Inject
    IngredientBuilder(DAOFactory daoFactory) {
        this.ingredientDAO = daoFactory.ingredientDAO
    }

    public boolean canBuild(RecipeItem item) {
        return item.type == RecipeItemType.INGREDIENT
    }

    public IngredientEntity build(RecipeEntity recipeEntity, Object obj) {
        Ingredient ingredient = obj as Ingredient
        IngredientEntity entity = getOrCreateRecipeItem(recipeEntity, item)

        baseBuilder(entity, ingredient)
        entity.with {
            foodId        = ingredient.foodId
            foodSequence  = ingredient.foodSequence
            amount        = ingredient.amount
            description   = ingredient.description
            lastUpdatedBy = ingredient.lastUpdatedBy ?: entity.createdBy
            sortOrder     = ingredient.sortOrder
        }
        return ingredientDAO.createOrUpdate(entity)
    }

    IngredientEntity getOrCreateRecipeItem(RecipeEntity recipeEntity, RecipeItem item) {
        IngredientEntity entity = ingredientDAO.findByUuId(item.uuId)
        if (!entity) {
            entity = new IngredientEntity(recipe: recipeEntity, createdBy: recipeEntity.createdBy)
        }
        return entity
    }

}
