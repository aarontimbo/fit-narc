package com.atimbo.recipe.dao

import org.hibernate.SessionFactory

class TestDAOFactory implements DAOFactory {

    SessionFactory sessionFactory

    TestDAOFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory
    }

    RecipeDAO getRecipeDAO() {
        return new RecipeDAO(sessionFactory)
    }
}
