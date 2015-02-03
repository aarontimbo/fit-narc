package com.atimbo.recipe.modules.builders

import com.atimbo.common.RecipeItemType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.DirectionDAO
import com.atimbo.recipe.domain.DirectionEntity
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.transfer.Direction
import com.atimbo.recipe.transfer.RecipeItem

import javax.inject.Inject

/**
 * Builder to create {@link DirectionEntity} from request.
 */
class DirectionBuilder extends AbstractRecipeItemBuilder implements RecipeItemBuilder {

    final DirectionDAO directionDAO

    @Inject
    DirectionBuilder(DAOFactory daoFactory) {
        this.directionDAO = daoFactory.directionDAO
    }

    public boolean canBuild(RecipeItem item) {
        return item.type == RecipeItemType.DIRECTION
    }

    public DirectionEntity build(RecipeEntity recipeEntity, Object obj) {
        Direction request = obj as Direction

        DirectionEntity entity = getOrCreateRecipeItem(recipeEntity, request)

        baseBuilder(entity, request)

        entity.with {
            description   = request.description
            lastUpdatedBy = request.lastUpdatedBy ?: entity.createdBy
        }
        return directionDAO.createOrUpdate(entity)
    }

    DirectionEntity getOrCreateRecipeItem(RecipeEntity recipeEntity, RecipeItem item) {
        DirectionEntity entity = directionDAO.findByUuId(item.uuId)
        if (!entity) {
            entity = new DirectionEntity(recipe: recipeEntity, createdBy: recipeEntity.createdBy)
        }
        return entity
    }

}
