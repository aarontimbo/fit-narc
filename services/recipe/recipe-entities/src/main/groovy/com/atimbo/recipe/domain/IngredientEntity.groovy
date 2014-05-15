package com.atimbo.recipe.domain

import groovy.transform.EqualsAndHashCode

import javax.persistence.*

@Entity
@Table(name = 'ingredient')
@EqualsAndHashCode()
class IngredientEntity extends AbstractRecipeItemEntity {

    /**
     * Links ingredient to a USDA nutrient database food
     */
    @Column(name = 'food_id', nullable = true)
    Long foodId

    /**
     * Combined with the foodId, links ingredient to a USDA nutrient database food weight
     */
    @Column(name = 'food_sequence', nullable = true)
    Long foodSequence

    /**
     * Amount of an ingredient used in a recipe
     */
    @Column(name = 'amount', nullable = false)
    Float amount = 1.0

    /**
     * Describe an ingredient not available in the USDA nutrient database
     */
    @Column(name = 'description', nullable = true, length = 50)
    String description

}
