package com.atimbo.recipe.modules

import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.RecipeSourceDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.domain.RecipeSourceEntity
import com.atimbo.recipe.modules.builders.RecipeSourceBuilder
import com.atimbo.recipe.transfer.RecipeSource
import org.springframework.stereotype.Service

import javax.annotation.Resource
import javax.inject.Inject

@Service
class RecipeSourceModule {

    RecipeSourceDAO recipeSourceDAO

    @Resource
    RecipeSourceBuilder recipeSourceBuilder

    @Inject
    RecipeSourceModule(DAOFactory daoFactory) {
        recipeSourceDAO = daoFactory.recipeSourceDAO
    }

    RecipeSourceEntity createOrUpdate(RecipeEntity recipeEntity, RecipeSource recipeSource){
        if (recipeSource) {
            RecipeSourceEntity recipeSourceEntity = recipeSourceDAO.findByUuId(recipeSource.uuId)
            boolean isNew = false
            if (!recipeSourceEntity) {
                isNew = true
                recipeSourceEntity = new RecipeSourceEntity(recipe: recipeEntity, createdBy: recipeEntity.createdBy)
            }
            recipeSourceBuilder.build(recipeSourceEntity, recipeSource, isNew)
            return recipeSourceDAO.createOrUpdate(recipeSourceEntity)
        }
        return null
    }
}
