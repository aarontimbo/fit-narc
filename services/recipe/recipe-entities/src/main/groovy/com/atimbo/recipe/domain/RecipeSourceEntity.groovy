package com.atimbo.recipe.domain


import com.atimbo.common.RecipeSourceType
import groovy.transform.EqualsAndHashCode

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

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

}
