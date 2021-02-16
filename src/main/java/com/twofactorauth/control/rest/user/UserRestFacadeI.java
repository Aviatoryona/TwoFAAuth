package com.twofactorauth.control.rest.user;

import com.twofactorauth.entity.MessageModel;
import com.twofactorauth.entity.UserModel;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Tag(name = "Users")
/**
 * Flow
 * 1.Login
 * 2.Check if 2FA enabled
 * 3.If true 2 above iniate2FA and watch request
 * 4.If response true 3 above, terminate watch and move to next
 */
public interface UserRestFacadeI {

    /**
     *
     * @param userModel model
     * @return
     */
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    MessageModel createUser(UserModel userModel);

    /**
     *
     * @param userName userName
     * @param pwd pwd
     * @return
     */
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/login/{username}/{pwd}")
    MessageModel getUser(@PathParam("username")String userName,@PathParam("pwd") String pwd);

    /**
     * Update user token
     * @param id userId
     * @param token token
     * @return
     */
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/updateToken/{id}/{token}")
    MessageModel updateToken(@PathParam("id")Long id,@PathParam("token") String token);


    /**
     *
     * @param id  userId
     * @param isAllowed true or false
     * @return
     */
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/updateAccess/{id}/{isAllowed}")
    MessageModel updateAccess(@PathParam("id")Long id,@PathParam("isAllowed") boolean isAllowed);

    /**
     *
     * @param id userId
     * @return
     */
    @GET
    @Path("/requestStatus/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    MessageModel requestStatus(@PathParam("id")Long id);

    /**
     *
     * @param id
     * @param token
     * @return
     */
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/iniate2FA/{id}/{token}")
    MessageModel iniate2FA(@PathParam("id") Long id,@PathParam("token") String token);
}
