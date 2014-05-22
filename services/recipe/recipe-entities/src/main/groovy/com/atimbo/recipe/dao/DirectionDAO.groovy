package com.atimbo.recipe.dao

import com.atimbo.recipe.domain.DirectionEntity
import com.sun.jersey.api.NotFoundException
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

import javax.inject.Inject

@Repository
class DirectionDAO extends AbstractDAO<DirectionEntity> {

    @Inject
    DirectionDAO(SessionFactory sessionFactory) {
        super(sessionFactory)
    }

    DirectionEntity create(DirectionEntity directionEntity) {
        persist(directionEntity)
    }

    DirectionEntity findById(Long id) {
        DirectionEntity directionEntity = get(id)
        if (!directionEntity) {
            throw new NotFoundException("no direction found with id: ${id}")
        }
        return directionEntity
    }

}
