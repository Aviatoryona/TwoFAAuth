package com.twofactorauth.control.rest.user;

import com.twofactorauth.boundary.UserServiceI;
import com.twofactorauth.entity.MessageModel;
import com.twofactorauth.entity.UserModel;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserRestFacadeImpl implements UserRestFacadeI{

    @EJB
    UserServiceI userService;

    @Override
    public MessageModel createUser(UserModel userModel) {
        UserModel u=userService.create(userModel);
        return
                new MessageModel(
                        u!=null? true:false,
                        "User created",
                        u
                );
    }

    @Override
    public MessageModel getUser(String userName, String pwd) {
        return userService.getUser(userName,pwd);
    }

    @Override
    public MessageModel updateToken(Long id, String token) {
        return userService.updateToken(id,token);
    }

    @Override
    public MessageModel updateAccess(Long id, boolean isAllowed) {
        return userService.updateAccess(id,isAllowed);
    }

    @Override
    public MessageModel requestStatus(Long id) {
        return userService.requestStatus(id);
    }

    @Override
    public MessageModel iniate2FA(Long id, String token) {
        return userService.iniate2FA(id,token);
    }
}
