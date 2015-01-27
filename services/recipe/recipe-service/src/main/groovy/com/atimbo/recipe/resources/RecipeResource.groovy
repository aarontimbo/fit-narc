package com.atimbo.recipe.resources

import com.atimbo.fitnarc.service.resources.AbstractResource
import com.atimbo.recipe.domain.RecipeEntity
import com.atimbo.recipe.modules.RecipeModule
import com.atimbo.recipe.transfer.Recipe
import com.atimbo.recipe.transfer.RecipeCreateUpdateRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.yammer.dropwizard.hibernate.UnitOfWork
import com.yammer.metrics.annotation.Timed
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller

import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.GET
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Endpoint for {@link RecipeEntity}
 */
@Path('/recipe')
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
@Controller
class RecipeResource extends AbstractResource {

    private final RecipeModule recipeModule

    @Inject
    RecipeResource(RecipeModule recipeModule, ObjectMapper objectMapper) {
        super(objectMapper)
        this.recipeModule = recipeModule
    }

    /**
     * Find a recipe by a universally unique identifier.
     *
     * Returns a status of '200 OK' if successful.
     *
     * @param       uuId
     * @return      Recipe transfer object
     */
    @Path('/{uuId}')
    @GET
    @Timed
    @UnitOfWork
    public Recipe findRecipe(@PathParam('uuId') String uuId) {
        RecipeEntity recipeEntity = recipeModule.findByUUId(uuId)
        return translateAndReturn(recipeEntity, Recipe)
    }

    /**
     * Create or update a recipe.
     *
     * Returns a status of '200 OK' if successful.
     *
     * @param       request the Recipe create or update request
     * @return      Recipe transfer object
     */
    @PUT
    @Timed
    @UnitOfWork
    public Recipe getRecipe(@Valid RecipeCreateUpdateRequest request) {
        // TODO: validate the request
        RecipeEntity recipeEntity = recipeModule.createOrUpdateFromRequest(request)
        return translateAndReturn(recipeEntity, Recipe)
    }
}
