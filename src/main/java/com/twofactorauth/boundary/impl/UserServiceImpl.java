package com.twofactorauth.boundary.impl;
import com.twofactorauth.boundary.UserService;
import com.twofactorauth.entity.UserModel;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote
public class UserServiceImpl extends AbstractBeanImpl<UserModel,Long> implements UserService{

    @PersistenceContext(unitName = "pu")
    EntityManager em;

    public UserServiceImpl() {
        super(UserModel.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
