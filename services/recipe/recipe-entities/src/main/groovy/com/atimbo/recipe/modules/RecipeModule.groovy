package com.atimbo.recipe.modules

import com.atimbo.recipe.dao.DAOFactory
import com.atimbo.recipe.dao.RecipeDAO
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.modules.builders.RecipeBuilder
import com.atimbo.recipe.transfer.RecipeCreateRequest
import org.springframework.stereotype.Service

import javax.annotation.Resource

@Service
class RecipeModule {

    RecipeDAO recipeDAO

    @Resource
    RecipeBuilder recipeBuilder

    RecipeModule(DAOFactory daoFactory) {
        this.recipeDAO = daoFactory.recipeDAO
    }

    RecipeEntity findByUUId(String uuId) {
        return recipeDAO.findByUUId(uuId)
    }

    RecipeEntity createFromRequest(RecipeCreateRequest createRequest) {
        RecipeEntity recipeEntity = recipeBuilder.build(createRequest)
        return recipeDAO.persist(recipeEntity)
    }
}
