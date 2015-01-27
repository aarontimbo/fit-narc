package com.atimbo.recipe.domain

import groovy.transform.EqualsAndHashCode

import javax.persistence.*

@Entity
@Table(name = 'direction')
@EqualsAndHashCode()
class DirectionEntity extends RecipeItemEntity {

    @Column(name = 'description', nullable = false)
    String description

}
