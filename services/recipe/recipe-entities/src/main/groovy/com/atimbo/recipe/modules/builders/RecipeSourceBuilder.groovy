package com.atimbo.recipe.modules.builders

import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.transfer.RecipeSource
import org.joda.time.LocalDateTime
import org.springframework.stereotype.Service

/**
 * Builder to create {@link RecipeSourceEntity} from request.
 */
@Service
class RecipeSourceBuilder {

    public void build(RecipeSourceEntity recipeSourceEntity, RecipeSource recipeSource, boolean isNew) {
        recipeSourceEntity.with {
            author        = recipeSource.author
            lastUpdatedBy = recipeSource.lastUpdatedBy ?: recipeSourceEntity.createdBy
            sortOrder     = recipeSource.sortOrder ?: 1
            sourceUrl     = recipeSource.sourceUrl
            title         = recipeSource.title
            sourceType    = recipeSource.sourceType
        }

        if (!isNew) {
            recipeSourceEntity.lastUpdated = new LocalDateTime()
        }
    }
}
