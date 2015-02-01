package com.atimbo.recipe.domain

import com.atimbo.common.utils.UniqueIDGenerator
import com.fasterxml.jackson.annotation.JsonBackReference
import org.joda.time.LocalDateTime

import javax.persistence.*

@Entity
@Table(name = 'recipe_item')
@Inheritance(strategy = InheritanceType.JOINED)
class RecipeItemEntity implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = 'recipe_id', nullable = false)
    @JsonBackReference
    RecipeEntity recipe

    @Column(name = 'uu_id', nullable = false)
    String uuId = UniqueIDGenerator.generateUUId()

    /**
     * Sort order of recipe item types
     */
    @Column(name = 'sort_order', nullable = false)
    Integer sortOrder

    @Column(name = 'created_by', nullable = false, length = 50)
    String createdBy

    @Column(name = 'last_updated_by', nullable = true, length = 50)
    String lastUpdatedBy

    @Column(name = 'date_created', nullable = false)
    LocalDateTime dateCreated = new LocalDateTime()

    @Column(name = 'last_updated', nullable = false)
    LocalDateTime lastUpdated = new LocalDateTime()

    public int compareTo(Object anotherItem) {
        this.sortOrder <=> anotherItem.sortOrder
    }

    @Transient
    boolean getRecipeSource() { false }

    @Transient
    boolean getIngredient() { false }

    @Transient
    boolean getDirection() { false }

}
