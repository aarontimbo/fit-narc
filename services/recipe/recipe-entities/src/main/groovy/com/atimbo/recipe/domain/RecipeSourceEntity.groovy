package com.atimbo.recipe.domain


import com.atimbo.common.RecipeSourceType
import com.fasterxml.jackson.annotation.JsonBackReference
import groovy.transform.EqualsAndHashCode

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = 'recipe_source')
@EqualsAndHashCode()
class RecipeSourceEntity extends RecipeItemEntity {

    @Column(name = 'author', nullable = false, length = 50)
    String author

    @Column(name = 'title', nullable = false, length = 50)
    String title

    @Column(name = 'source_url', nullable = true, length = 150)
    String sourceUrl

    @Column(name = 'source_type', nullable = false)
    RecipeSourceType sourceType = RecipeSourceType.UNKNOWN

    @Transient
    boolean getRecipeSource() { true }

}
