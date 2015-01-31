package com.atimbo.recipe.modules.builders

import com.atimbo.common.RecipeItemType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.RecipeSourceDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.transfer.RecipeItem
import org.springframework.stereotype.Service

import javax.inject.Inject

/**
 * Builder to create {@link RecipeSourceEntity} from request.
 */
@Service
class RecipeSourceBuilder extends AbstractRecipeItemBuilder {

    final RecipeSourceDAO recipeSourceDAO

    @Inject
    RecipeSourceBuilder(DAOFactory daoFactory) {
        this.recipeSourceDAO = daoFactory.recipeSourceDAO
    }

    public boolean canBuild(RecipeItem item) {
        return item.type == RecipeItemType.RECIPE_SOURCE
    }

    public RecipeSourceEntity build(RecipeEntity recipeEntity, RecipeItem item) {

        RecipeSourceEntity entity = getOrCreateRecipeItem(recipeEntity, item)

        baseBuilder(entity, item)

        entity.with {
            author        = item.author
            lastUpdatedBy = item.lastUpdatedBy ?: entity.createdBy
            sortOrder     = item.sortOrder ?: 1
            sourceUrl     = item.sourceUrl
            title         = item.title
            sourceType    = item.sourceType
        }
        return entity
    }

    RecipeSourceEntity getOrCreateRecipeItem(RecipeEntity recipeEntity, RecipeItem item) {
        RecipeSourceEntity entity = recipeSourceDAO.findByUuId(item.uuId)
        if (!entity) {
            entity = new RecipeSourceEntity(recipe: recipeEntity, createdBy: recipeEntity.createdBy)
        }
        return entity
    }
}
