package com.atimbo.recipe.dao

import org.hibernate.SessionFactory

class TestDAOFactory implements DAOFactory {

    SessionFactory sessionFactory

    TestDAOFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory
    }

    IngredientDAO getIngredientDAO() {
        return new IngredientDAO(sessionFactory)
    }

    DirectionDAO getDirectionDAO() {
        return new DirectionDAO(sessionFactory)
    }

    RecipeDAO getRecipeDAO() {
        return new RecipeDAO(sessionFactory)
    }

    RecipeSourceDAO getRecipeSourceDAO() {
        return new RecipeSourceDAO(sessionFactory)
    }

}
