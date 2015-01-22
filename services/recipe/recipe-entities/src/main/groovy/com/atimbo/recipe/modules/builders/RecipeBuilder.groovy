package com.atimbo.recipe.modules.builders

import com.atimbo.common.utils.UniqueIDGenerator
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import org.joda.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class RecipeBuilder {

    public void build(RecipeEntity recipeEntity, RecipeCreateUpdateRequest request, boolean isNew) {
        recipeEntity.with{
            title         = request.title
            description   = request.description
            lastUpdatedBy = request.updatedBy ?: request.createdBy
        }
        if (!recipeEntity.createdBy) {
            recipeEntity.createdBy = request.createdBy
        }
        if (!isNew) {
            recipeEntity.lastUpdated = new LocalDateTime()
        }
    }

}
