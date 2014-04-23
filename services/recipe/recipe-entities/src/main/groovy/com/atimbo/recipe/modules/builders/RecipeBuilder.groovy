package com.atimbo.recipe.modules.builders

import com.atimbo.common.utils.UniqueIDGenerator
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.transfer.RecipeCreateRequest
import org.springframework.stereotype.Service

@Service
class RecipeBuilder {

    RecipeEntity build(RecipeCreateRequest createRequest) {
        RecipeEntity recipeEntity = new RecipeEntity(uuId: UniqueIDGenerator.generateUUId())
        recipeEntity.with{
            title = createRequest.title
            description = createRequest.description
            createdBy = createRequest.createdBy
            lastUpdatedBy = createRequest.createdBy
        }
        return recipeEntity
    }
}
