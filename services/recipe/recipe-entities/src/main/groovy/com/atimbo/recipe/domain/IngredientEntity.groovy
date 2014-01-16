package com.atimbo.recipe.domain

import groovy.transform.EqualsAndHashCode

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'ingredient')
@EqualsAndHashCode()
class IngredientEntity extends RecipeItemEntity {

    @Column(name = 'food_id', nullable = true)
    Long foodId

    @Column(name = 'food_sequence', nullable = true)
    Long foodSequence

    @Column(name = 'amount', nullable = false)
    Float amount = 1.0

    @Column(name = 'description', nullable = false)
    String description



}
