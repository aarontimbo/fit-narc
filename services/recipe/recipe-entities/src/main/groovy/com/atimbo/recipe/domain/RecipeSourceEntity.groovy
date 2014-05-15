package com.atimbo.recipe.domain


import com.atimbo.common.RecipeSourceType
import groovy.transform.EqualsAndHashCode

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'recipe_source')
@EqualsAndHashCode()
class RecipeSourceEntity extends AbstractRecipeItemEntity {

    @Column(name = 'author', nullable = false)
    String author

    @Column(name = 'title', nullable = false)
    String title

    @Column(name = 'source_url', nullable = true)
    String sourceUrl

    @Column(name = 'source_type', nullable = false)
    RecipeSourceType sourceType= RecipeSourceType.UNKNOWN

}
