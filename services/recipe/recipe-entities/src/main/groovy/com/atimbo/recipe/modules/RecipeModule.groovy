package com.atimbo.recipe.modules

import com.atimbo.common.RecipeItemType
import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.RecipeDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.modules.builders.RecipeBuilder
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import com.atimbo.recipe.transfer.RecipeItem
import com.atimbo.recipe.transfer.RecipeSource
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.inject.Inject
import javax.persistence.EntityNotFoundException

@Service
class RecipeModule {


    RecipeDAO recipeDAO

    @Resource
    RecipeBuilder recipeBuilder

    @Resource
    RecipeSourceModule recipeSourceModule

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
        recipeDAO.persist(recipeEntity)

        addRecipeSource(recipeEntity, request.recipeSource)

        addRecipeItems(recipeEntity, request.items)

        return recipeEntity
    }

    private void addRecipeSource(RecipeEntity recipeEntity, RecipeSource recipeSource) {
        RecipeSourceEntity recipeSourceEntity = recipeSourceModule.createOrUpdate(recipeEntity, recipeSource)
        recipeEntity.recipeSource = recipeSourceEntity
    }

    private void addRecipeItems(RecipeEntity recipeEntity, List<RecipeItem> items) {
        items.each { RecipeItem item ->
            addRecipeItem(recipeEntity, item)
        }
    }

    private void addRecipeItem(RecipeEntity recipeEntity, RecipeItem item) {
        if (item.type == RecipeItemType.INGREDIENT) {

        } else if (item.type == RecipeItemType.DIRECTION) {
            //TODO: build recipe direction
        }
    }
}
