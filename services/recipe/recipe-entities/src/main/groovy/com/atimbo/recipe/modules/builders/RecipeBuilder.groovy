package com.atimbo.recipe.modules.builders

import com.atimbo.common.utils.UniqueIDGenerator
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import org.springframework.stereotype.Service

@Service
class RecipeBuilder {

    RecipeEntity build(RecipeEntity recipeEntity, RecipeCreateUpdateRequest request) {
        recipeEntity.with{
            title         = request.title
            description   = request.description
            lastUpdatedBy = request.updatedBy ?: request.createdBy
        }
        if (!recipeEntity.createdBy) {
            recipeEntity.createdBy = request.createdBy
        }
        return recipeEntity
    }

}
