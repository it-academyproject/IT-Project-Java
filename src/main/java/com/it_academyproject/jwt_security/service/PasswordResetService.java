package com.it_academyproject.jwt_security.service;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.exceptions.*;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.tools.email.EmailObj;
import com.it_academyproject.jwt_security.model.PasswordResetToken;
import com.it_academyproject.jwt_security.repository.PasswordResetTokenRepository;
import com.it_academyproject.tools.email.EmailUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PasswordResetService
{
    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public JSONObject resetPassword ( String email ) throws UserNotFoundException , Exception
    {
        // 1. verify the user email exists and get the user
        // 2. create a token to send
        // 3. add to the db
        // 4. send an email to the user with the link and the token.

        MyAppUser myAppUser = myAppUserRepository.findByEmail( email );
        if ( myAppUser != null && myAppUser.isEnabled() )
        {
            //check if the user has a token, if it does remove the old and generate a new one.
            List<PasswordResetToken> passwordResetTokenList = passwordResetTokenRepository.findByMyAppUserId( myAppUser.getId() );
            if ( passwordResetTokenList.size() > 0 )
            {
                for (int i = 0; i < passwordResetTokenList.size() ; i++)
                {
                    passwordResetTokenRepository.delete( passwordResetTokenList.get(i) );
                }

            }

            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken( token , myAppUser );
            passwordResetTokenRepository.save( passwordResetToken );

            try
            {
                String email_to = email;
                String email_from = "itacademy@virginiacampo.com";
                String email_subject = "It-Academy Password Reset";
                String email_content = "Hello User! you have requested the reser of your password. Please go to the following to do this: " +
                        "http://localhost:8080/password-reset.html?email=\"" + email + "\"&token="+token;

                EmailObj emailObj = new EmailObj(email_to , email_from , email_subject , email_content );
                EmailUtil emailUtil = new EmailUtil( emailObj );
                boolean emailSentCorrectly = emailUtil.sendHTMLEmail();
                System.out.println(token);
                if ( emailSentCorrectly )
                {
                    JSONObject sendData = new JSONObject();
                    JSONObject message = new JSONObject();
                    message.put("type" , "success");
                    message.put("message" , "The email was sent successfully.");
                    sendData.put("message" , message);
                    System.out.println(sendData);
                    return sendData;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                JSONObject sendData = new JSONObject();
                JSONObject errorMessage = new JSONObject();
                errorMessage.put("type" , "error");
                errorMessage.put("message" , e.getMessage());
                sendData.append("message" , errorMessage);
                return sendData;
            }
        }
        else if ( myAppUser == null )
        {
            throw (new UserNotFoundException("The email does not belong to anyone."));
        }
        else if ( ! myAppUser.isEnabled() )
        {
            throw ( new UserNotEnabled( email ));
        }

        JSONObject sendData = new JSONObject();
        return sendData;
    }
    public JSONObject savePassword ( String body ) throws InvalidFormatException, InvalidToken, EmptyFieldException, Exception
    {
        // 1. get the values form the parameters. (header - token / body - email)
        // 2. with the email get the user
        // 3. verify the token belongs to that user
        // 4. verify the token is still active.
        // 5. save password in the user
        // 6. change the active date of the token to null.

        JSONObject getData = new JSONObject( body );
        if (    getData.has("token") &&
                getData.has("email") &&
                getData.has("password") &&
                getData.has("confirm_password") &&
                ! getData.getString("token").equals("") &&
                ! getData.getString("email").equals("") &&
                ! getData.getString("password").equals("") &&
                ! getData.getString("confirm_password").equals("") &&
                getData.getString("password").equals( getData.getString("confirm_password") )
        )
        {
            String token = getData.getString("token");
            String email = getData.getString("email");
            String password = getData.getString("password");

            List<PasswordResetToken> pwrtListy = passwordResetTokenRepository.findByToken( token );
            //search for token
            if ( pwrtListy.size() == 1 )
            {
                //token found - is it valid?
                PasswordResetToken pwrt = pwrtListy.get(0);
                Date expirationDate = pwrt.getExpiryDate();
                Date now = new Date();
                if ( expirationDate.compareTo(now) > 0 )
                {
                    //it is valid - Look for the user
                    Optional<MyAppUser> optional = myAppUserRepository.findById(pwrt.getMyAppUser().getId());
                    if ( optional.isPresent() )
                    {
                        //user found
                        MyAppUser myAppUser = optional.get();
                        //check email
                        if ( email.equals(myAppUser.getEmail()))
                        {
                            //same email
                            //verify password format
                            // min 8, max 16 characters
                            // at least: 1 uppercase - 1 lowercase - 1 number - 1 special character
                            Pattern p = Pattern.compile( "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}" );

                            Matcher m = p.matcher(password);
                            if ( m.matches() )
                            {
                                //password ok
                                myAppUser.setPassword(passwordEncoder.encode( password ));
                                myAppUserRepository.save( myAppUser );

                                JSONObject sendData = new JSONObject();
                                JSONObject message = new JSONObject();
                                message.put("type" , "success");
                                message.put("message" , "The password was updated successfully.");
                                sendData.put("message" , message);
                                System.out.println(sendData);
                                return sendData;
                            }
                            else
                            {
                                throw ( new InvalidFormatException("format"));
                            }

                        }
                        else
                        {
                            throw ( new InvalidToken ("email"));
                        }
                    }
                    else
                    {
                        throw ( new InvalidToken("No user"));
                    }

                }
                else
                {
                    throw ( new InvalidToken("Expired"));
                }
            }
            else
            {
                throw ( new InvalidToken("not found"));
            }
        }
        else if ( ! getData.has("token"))
        {
            throw ( new EmptyFieldException("token" , "missing" ));
        }
        else if ( getData.getString("token").equals("") )
        {
            throw ( new EmptyFieldException("token") );
        }

        else if ( ! getData.has("email"))
        {
            throw ( new EmptyFieldException("email" , "missing") );
        }
        else if (getData.getString("email").equals("") )
        {
            throw ( new EmptyFieldException("email") );
        }

        else if ( ! getData.has("password") )
        {
            throw ( new EmptyFieldException("password" , "missing") );
        }
        else if ( getData.getString("password").equals("") )
        {
            throw ( new EmptyFieldException("password") );
        }

        else if ( ! getData.has("confirm_password") )
        {
            throw ( new EmptyFieldException("confirm_password" , "missing") );
        }
        else if ( getData.getString("confirm_password").equals("") )
        {
            throw ( new EmptyFieldException("confirm_password") );
        }

        else if ( ! getData.has("confirm_password") )
        {
            throw ( new EmptyFieldException("confirm_password" , "missing") );
        }
        else if ( getData.getString("confirm_password").equals("") )
        {
            throw ( new EmptyFieldException("confirm_password") );
        }
        else if (! getData.getString("password").equals( getData.getString("confirm_password") ))
        {
            throw (new Exception( "Password and Confirmation Password don't match."));
        }

        JSONObject sendData = new JSONObject();
        JSONObject errorMessage = new JSONObject();
        errorMessage.put("type" , "error");
        errorMessage.put("message" , "Unknown Error.");
        sendData.append("message" , errorMessage);
        return sendData;

    }
}
