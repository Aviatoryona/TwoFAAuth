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
        try {
            UserModel u = userService.create(userModel);
            return
                    new MessageModel(
                            u != null ? true : false,
                            "User created",
                            u
                    );
        }catch (Exception e){
            return userService.getDefault();
        }
    }

    @Override
    public MessageModel getUser(String userName, String pwd) {
        try {
            return userService.getUser(userName,pwd);
        } catch (Exception e) {
            return userService.getDefault();
        }
    }

    @Override
    public MessageModel updateToken(Long id, String token){
        try {
            return userService.updateToken(id,token);
        } catch (Exception e) {
            return userService.getDefault();
        }
    }

    @Override
    public MessageModel updateAccess(Long id, boolean isAllowed) {
        try {
            return userService.updateAccess(id,isAllowed);
        } catch (Exception e) {
            return userService.getDefault();
        }
    }

    @Override
    public MessageModel requestStatus(Long id) {
        try {
            return userService.requestStatus(id);
        } catch (Exception e) {
            return userService.getDefault();
        }
    }

    @Override
    public MessageModel iniate2FA(Long id, String token) {
        try {
            return userService.iniate2FA(id,token);
        } catch (Exception e) {
            return userService.getDefault();
        }
    }
}
