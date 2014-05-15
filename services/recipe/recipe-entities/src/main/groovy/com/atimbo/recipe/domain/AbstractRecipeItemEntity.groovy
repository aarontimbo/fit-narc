package com.atimbo.recipe.domain

import org.joda.time.LocalDate

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class AbstractRecipeItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = 'recipe_id', nullable = false)
    RecipeEntity recipe

    /**
     * Sort order of recipe item types
     */
    @Column(name = 'sort_order', nullable = false)
    Integer sortOrder = 1

    @Column(name = 'created_by', nullable = false, length = 50)
    String createdBy

    @Column(name = 'last_updated_by', nullable = true, length = 50)
    String lastUpdatedBy

    @Column(name = 'date_created', nullable = false)
    LocalDate dateCreated = new LocalDate()

    @Column(name = 'last_updated', nullable = false)
    LocalDate lastUpdated = new LocalDate()

}
