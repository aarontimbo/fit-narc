package com.atimbo.test.dao

import com.yammer.dropwizard.hibernate.AbstractDAO

/**
 * Base class for testing DAOs
 */
abstract class DAOSpecification<T extends AbstractDAO> extends DatabaseSpecification {

    T dao

    def setup() {
        dao = buildDAO()
    }

    abstract T buildDAO()
}