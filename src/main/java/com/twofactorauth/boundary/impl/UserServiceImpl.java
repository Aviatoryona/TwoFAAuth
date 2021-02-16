package com.twofactorauth.boundary.impl;
import com.twofactorauth.boundary.UserServiceI;
import com.twofactorauth.entity.MessageModel;
import com.twofactorauth.entity.UserModel;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserServiceImpl extends AbstractBeanImpl<UserModel,Long> implements UserServiceI{

    @PersistenceContext(unitName = "pu")
    EntityManager em;

    public UserServiceImpl() {
        super(UserModel.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public MessageModel getUser(String userName, String pwd) {
        Query q= em.createQuery("FROM UserModel u WHERE u.username=:username AND u.pwd=:pwd",UserModel.class);
        q.setParameter("username",userName);
        q.setParameter("pwd",pwd);
        UserModel userModel= (UserModel) q.getSingleResult();
        return new MessageModel(
                userModel!=null?true:false,
                "Done",
                userModel
        );
    }

    @Override
    public MessageModel updateToken(Long id, String token) {
        Query q=em.createQuery("UPDATE  UserModel  u set u.oneSignalToken=:token WHERE u.id=:id");
        q.setParameter("token",token);
        q.setParameter("id",id);
        q.executeUpdate();
        return new MessageModel(
                true,
                "Done"
        );
    }

    @Override
    public MessageModel updateAccess(Long id, boolean isAllowed) {
        Query q=em.createQuery("UPDATE  UserModel  u set u.isAllowed=:isAllowed WHERE u.id=:id");
        q.setParameter("isAllowed",isAllowed);
        q.setParameter("id",id);
        q.executeUpdate();
        return new MessageModel(
                true,
                "Done"
        );
    }

    @Override
    public MessageModel requestStatus(Long id) throws Exception{
        Query q= em.createQuery("SELECT u.requestStatus FROM UserModel u WHERE u.id=:id",Boolean.class);
        q.setParameter("id",id);
        Boolean b= (Boolean) q.getSingleResult();
        return new MessageModel(
                b!=null?true:false,
                "Done",
                b
        );
    }

    /**
     * Send request here
     * @param id
     * @param token
     * @return
     */
    @Override
    public MessageModel iniate2FA(Long id, String token) {
        // write code here
        return new MessageModel(
                true,
                "Done"
        );
    }

    @Override
    public MessageModel getDefault() {
        return new MessageModel(
                false,
                "No data found"
        );
    }
}
