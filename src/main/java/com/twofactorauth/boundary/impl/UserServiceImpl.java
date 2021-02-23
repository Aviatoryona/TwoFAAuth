package com.twofactorauth.boundary.impl;

import com.twofactorauth.boundary.UserServiceI;
import com.twofactorauth.entity.MessageModel;
import com.twofactorauth.entity.UserModel;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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
        Query q=em.createQuery("UPDATE  UserModel  u set u.isAllowed=:isAllowed,u.requestStatus=1 WHERE u.id=:id");
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
        Query q= em.createQuery("SELECT u.requestStatus AS rs,u.isAllowed AS al FROM UserModel u WHERE u.id=:id");
        q.setParameter("id",id);
//        Boolean b= (Boolean) q.getSingleResult();
        Object map=q.getSingleResult();
        return new MessageModel(
                true,
                "Done",
                map
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
        //set isAllowed to false, then send notification
        updateAccess(id,false);

        sendRequest(id,token);

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

    /**
     *
     * @param id
     * @param token
     */
    private void sendRequest(Long id,String token){
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \"768b0275-4652-4d4f-b6bb-b31438dda180\","
                    +   "\"include_player_ids\": [\""+token+"\"],"
                    +   "\"data\": {\"id\": \""+id+"\"},"
                    +   "\"contents\": {"
                    +       "\"en\": \"2FA Action required\""
                    +    "},"
                    +   "\"headings\": {"
                    +       "\"en\": \"Hi There\""
                    +    "}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            Scanner scanner;
            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                scanner = new Scanner(con.getInputStream(), "UTF-8");
            }
            else {
                scanner = new Scanner(con.getErrorStream(), "UTF-8");
            }
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
}
