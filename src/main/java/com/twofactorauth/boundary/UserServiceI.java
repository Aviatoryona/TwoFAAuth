package com.twofactorauth.boundary;

import com.twofactorauth.entity.MessageModel;
import com.twofactorauth.entity.UserModel;

public interface UserServiceI extends AbstractBean<UserModel,Long>{

    MessageModel getUser(String userName, String pwd)throws Exception;

    MessageModel updateToken(Long id,String token)throws Exception;
    MessageModel updateAccess(Long id,boolean isAllowed)throws Exception;
    MessageModel requestStatus(Long id) throws Exception;
    MessageModel iniate2FA( Long id,String token)throws Exception;

    MessageModel getDefault();
}
