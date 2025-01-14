package com.tfg.GoAway.modules.user.infraestructure.out.db.sql_server;

import lombok.extern.slf4j.Slf4j;

import com.tfg.GoAway.modules.user.domain.UserCriteria;

import jakarta.persistence.*;



@Slf4j
public final class CustomUserRepositoryQueryBuilder {

    private static final String Q_BASE = "SELECT "
        + " email, "
        + " name, "
        + " password, "
        + " contact_number, "
        + " direction_id, "
        + " user_type ";


    private static final String Q_FILTER_USER = " AND email = :email";

    private static final String PARAM_USER_EMAIL = "email";

    private CustomUserRepositoryQueryBuilder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static Query buildQuery(final UserCriteria criteria, final EntityManager entityManager){
        
        Query query = getQuery(criteria, entityManager);
        setParams(query, criteria);

        return query;
    }

    private static Query getQuery(final UserCriteria criteria, final EntityManager entityManager) {
        return entityManager.createNativeQuery(createQuery(criteria), Tuple.class);
    }   


    private static String createQuery(final UserCriteria criteria){
        
        final var query = new StringBuilder(Q_BASE);

        if (criteria.getEmail() != null){
            query.append(Q_FILTER_USER);
        }

        return query.toString();
    }

    private static void setParams(final Query query, final UserCriteria criteria){

        if (criteria.getEmail() != null){
            query.setParameter(PARAM_USER_EMAIL, criteria.getEmail());
        }
    }

}
