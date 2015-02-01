package com.atimbo.recipe.modules

import com.atimbo.common.RecipeItemType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.RecipeDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeItemEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.modules.builders.RecipeBuilder
import com.atimbo.recipe.transfer.Direction
import com.atimbo.recipe.transfer.Ingredient
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import com.atimbo.recipe.transfer.RecipeItem
import com.atimbo.recipe.transfer.RecipeSource
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.inject.Inject
import javax.persistence.EntityNotFoundException

@Service
class RecipeModule<T extends RecipeItem> {

    RecipeDAO recipeDAO

    @Resource
    RecipeBuilder recipeBuilder

    @Resource
    RecipeItemModule recipeItemModule

    @Inject
    RecipeModule(DAOFactory daoFactory) {
        this.recipeDAO = daoFactory.recipeDAO
    }

    RecipeEntity findByUUId(String uuId) {
        RecipeEntity recipeEntity = recipeDAO.findByUUId(uuId)
        return recipeEntity
    }

    RecipeEntity createOrUpdateFromRequest(RecipeCreateUpdateRequest request) {
        RecipeEntity recipeEntity
        boolean isNew = true
        if (request.uuId) {
            recipeEntity = recipeDAO.findByUUId(request.uuId)
            isNew = false
        } else {
            recipeEntity = new RecipeEntity()
        }

        recipeBuilder.build(recipeEntity, request, isNew)

        addRecipeItems(recipeEntity, request)

        return recipeDAO.createOrUpdate(recipeEntity)
    }

    //~BEGIN private methods =========================

    private void addRecipeItems(RecipeEntity recipeEntity, RecipeCreateUpdateRequest request) {
        request.items.each { RecipeItem item ->
            recipeEntity.addToItems(addRecipeItem(recipeEntity, item))
        }
    }

    private RecipeItemEntity addRecipeItem(RecipeEntity recipeEntity, T item) {
        return recipeItemModule.createOrUpdateRecipeItem(recipeEntity, item)
    }
}
