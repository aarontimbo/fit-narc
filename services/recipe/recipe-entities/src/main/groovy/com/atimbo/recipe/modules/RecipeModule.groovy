package com.atimbo.recipe.modules

import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.RecipeDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.modules.builders.RecipeBuilder
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.inject.Inject
import javax.persistence.EntityNotFoundException

@Service
class RecipeModule {


    RecipeDAO recipeDAO

    @Resource
    RecipeBuilder recipeBuilder

    @Inject
    RecipeModule(DAOFactory daoFactory) {
        this.recipeDAO = daoFactory.recipeDAO
    }

    RecipeEntity findByUUId(String uuId) {
        RecipeEntity recipeEntity = recipeDAO.findByUUId(uuId)
        if (!recipeEntity) {
            throw new EntityNotFoundException("No recipe found with UUID: ${uuId}")
        }
        return recipeEntity
    }

    RecipeEntity createOrUpdateFromRequest(RecipeCreateUpdateRequest request) {
        RecipeEntity recipeEntity
        boolean isNew = true
        if (request.uuId) {
            recipeEntity = recipeDAO.findByUUId(request.uuId)
            if (!recipeEntity) {
                throw new EntityNotFoundException("Unable to find recipe ${request.uuId}")
            }
            isNew = false
        } else {
            recipeEntity = new RecipeEntity()
        }

        recipeEntity = recipeBuilder.build(recipeEntity, request, isNew)
        return recipeDAO.persist(recipeEntity)
    }
}
