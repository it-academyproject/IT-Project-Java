package com.it_academyproject.jwt_security.controller;

import com.it_academyproject.jwt_security.service.PasswordResetService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PasswordResetController
{
    @Autowired
    PasswordResetService passwordResetService;

    // Sends the reset password email
    @GetMapping ( "/api/get-reset-email" )
    public ResponseEntity<String> resetPassword(@RequestBody String userEmailJson)
    {
        try
        {
            JSONObject getData = new JSONObject(userEmailJson);
            JSONObject sendData = passwordResetService.resetPassword ( getData.getString("email") );
            return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            JSONObject sendData = new JSONObject();
            JSONObject message = new JSONObject();
            message.put("type" , "error");
            message.put("message" , e.getMessage());
            sendData.put("Message" , message);
            return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
        }
    }

    //Save the new password
    @PostMapping ( "/api/save-new-password/" )
    public ResponseEntity<String> savePassword(@RequestBody String body)
    {
        try {
            JSONObject sendData = passwordResetService.savePassword ( body );
            return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            String exceptionMessage = e.getMessage();
            JSONObject sendData = new JSONObject();
            JSONObject message = new JSONObject();
            message.put("type" , "error");
            message.put("message" , exceptionMessage);
            sendData.put("Message" , message);
            return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
        }
    }



}
