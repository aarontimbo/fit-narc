package com.atimbo.recipe.modules.builders

import com.atimbo.common.RecipeItemType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.RecipeSourceDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.transfer.RecipeItem
import com.atimbo.recipe.transfer.RecipeSource
import org.springframework.stereotype.Service

import javax.inject.Inject

/**
 * Builder to create {@link RecipeSourceEntity} from request.
 */
@Service
class RecipeSourceBuilder extends AbstractRecipeItemBuilder implements RecipeItemBuilder {

    final RecipeSourceDAO recipeSourceDAO

    @Inject
    RecipeSourceBuilder(DAOFactory daoFactory) {
        this.recipeSourceDAO = daoFactory.recipeSourceDAO
    }

    public boolean canBuild(RecipeItem item) {
        return item.type == RecipeItemType.RECIPE_SOURCE
    }

    public RecipeSourceEntity build(RecipeEntity recipeEntity, Object obj) {
        RecipeSource recipeSource = obj as RecipeSource

        RecipeSourceEntity entity = getOrCreateRecipeItem(recipeEntity, recipeSource)

        baseBuilder(entity, recipeSource)

        entity.with {
            author        = recipeSource.author
            lastUpdatedBy = recipeSource.lastUpdatedBy ?: entity.createdBy
            sortOrder     = recipeSource.sortOrder ?: 1
            sourceUrl     = recipeSource.sourceUrl
            title         = recipeSource.title
            sourceType    = recipeSource.sourceType
        }
        return recipeSourceDAO.createOrUpdate(entity)
    }

    RecipeSourceEntity getOrCreateRecipeItem(RecipeEntity recipeEntity, RecipeItem item) {
        RecipeSourceEntity entity = recipeSourceDAO.findByUuId(item.uuId)
        if (!entity) {
            entity = new RecipeSourceEntity(recipe: recipeEntity, createdBy: recipeEntity.createdBy)
        }
        return entity
    }
}
