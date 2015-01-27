package com.atimbo.recipe.domain

import com.atimbo.common.utils.UniqueIDGenerator
import com.fasterxml.jackson.annotation.JsonManagedReference
import groovy.transform.EqualsAndHashCode
import org.joda.time.LocalDateTime

import javax.persistence.*

@Entity
@Table(name = 'recipe')
@EqualsAndHashCode(excludes = ['recipeSource','items'])
class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(name = 'uu_id', nullable = false)
    String uuId = UniqueIDGenerator.generateUUId()

    @Column(name = 'title', nullable = false)
    String title

    @Column(name = 'description', nullable = true)
    String description

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = 'recipe_source_id')
    @JsonManagedReference
    RecipeSourceEntity recipeSource

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = 'recipe')
    Set<RecipeItemEntity> items = []

    @Column(name = 'created_by', nullable = false, length = 50)
    String createdBy

    @Column(name = 'last_updated_by', nullable = true, length = 50)
    String lastUpdatedBy

    @Column(name = 'date_created', nullable = false)
    LocalDateTime dateCreated = new LocalDateTime()

    @Column(name = 'last_updated', nullable = false)
    LocalDateTime lastUpdated = new LocalDateTime()

}