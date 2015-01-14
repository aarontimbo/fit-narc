package com.atimbo.recipe.domain

import com.atimbo.common.utils.UniqueIDGenerator
import org.joda.time.LocalDateTime

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class AbstractRecipeItemEntity implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = 'recipe_id', nullable = false)
    RecipeEntity recipe


    @Column(name = 'uu_id', nullable = false)
    String uuId = UniqueIDGenerator.generateUUId()

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
    LocalDateTime dateCreated = new LocalDateTime()

    @Column(name = 'last_updated', nullable = false)
    LocalDateTime lastUpdated = new LocalDateTime()

    public int compareTo(Object anotherItem) {
        this.sortOrder <=> anotherItem.sortOrder
    }

}
