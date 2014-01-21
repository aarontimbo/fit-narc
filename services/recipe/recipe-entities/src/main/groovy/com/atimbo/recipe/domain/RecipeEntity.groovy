package com.atimbo.recipe.domain

import groovy.transform.EqualsAndHashCode
import org.joda.time.LocalDate

import javax.persistence.*

@Entity
@Table(name = 'recipe')
@EqualsAndHashCode()
class RecipeEntity {

    @Id
    @GeneratedValue
    Long id

    @Column(name = 'title', nullable = false)
    String title

    @Column(name = 'description', nullable = true)
    String description

    @Column(name = 'created_by', nullable = false, length = 50)
    String createdBy

    @Column(name = 'last_updated_by', nullable = true, length = 50)
    String lastUpdatedBy

    @Column(name = 'date_created', nullable = false)
    LocalDate dateCreated = new LocalDate()

    @Column(name = 'last_updated', nullable = false)
    LocalDate lastUpdated = new LocalDate()

}