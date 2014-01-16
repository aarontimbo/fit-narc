package com.atimbo.recipe.domain

import com.atimbo.common.RecipeItemType
import groovy.transform.EqualsAndHashCode
import org.joda.time.LocalDate

import javax.persistence.*

@Entity
class RecipeItemEntity {

    @Id
    @GeneratedValue
    Long id

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'recipe_id', nullable = false)
    RecipeEntity recipe

    @Column(name = 'item_type', nullable = false)
    RecipeItemType type

    @Column(name = 'sort_order', nullable = false)
    Integer sortOrder

    @Column(name = 'date_created', nullable = false)
    LocalDate dateCreated = new LocalDate()

    @Column(name = 'last_updated', nullable = false)
    LocalDate lastUpdated
}
