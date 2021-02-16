package com.twofactorauth.boundary;

import com.twofactorauth.entity.MessageModel;
import com.twofactorauth.entity.UserModel;

public interface UserServiceI extends AbstractBean<UserModel,Long>{

    MessageModel getUser(String userName, String pwd);

    MessageModel updateToken(Long id,String token);
    MessageModel updateAccess(Long id,boolean isAllowed);
    MessageModel requestStatus(Long id);
    MessageModel iniate2FA( Long id,String token);
}
