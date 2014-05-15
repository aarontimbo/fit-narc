package com.atimbo.test.dao

import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

class BaseEntityBuilder {
    SessionFactory sessionFactory

    Map<Class, AbstractDAO> daos = [:]

    BaseEntityBuilder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory
    }

    public <T> T daoFor(Class clazz, Class<T> daoClazz) {
        if (!daos.containsKey(clazz)) {
            daos[clazz] = daoClazz.newInstance(sessionFactory)
        }
        daos[clazz]
    }

    public void save(Object entity) {
        if (entity) {
            sessionFactory.currentSession.saveOrUpdate(entity)
        }
    }
}
