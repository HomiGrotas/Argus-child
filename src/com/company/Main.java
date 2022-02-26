package com.company;

import com.company.local.Auth;
import com.company.ui.*;


public class Main {

    public static void main(String[] args) {
        if (!Auth.authExists())
        {
            try
            {
                RegistrationWindow.create();
            }
            catch (Exception ignored)
            {
                System.out.println("Couldn't open the registration page!");
            }
        }
        else{
            if(Auth.login())
            {
                System.out.println("Logged in successfully");
            }else {
                System.out.println("Couldn't log in");
            }
        }
    }
}
