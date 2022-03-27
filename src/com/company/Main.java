package com.company;

import com.company.local.Auth;
import com.company.ui.*;
import com.company.local.LocalMain;
import com.company.utils.Setup;

import javax.naming.NoPermissionException;

import static com.company.utils.Privileges.IS_RUNNING_AS_ADMINISTRATOR;

public class Main {

    public static void main(String[] args) {
        if (!IS_RUNNING_AS_ADMINISTRATOR)
        {
            System.out.println("This program must be ran with admin privileges");
        }else
            {
                if (!Auth.authExists())
                {
                    try
                    {
                        RegistrationWindow.create();
                        Setup.mkdir();
                    }
                    catch (Exception ignored)
                    {
                        System.out.println("Couldn't open the registration page!");
                    }
                }
                else{
                    if(Auth.login())
                    {
                        System.out.println("Loaded auth successfully");
                        LocalMain.run();
                    }else {
                        System.out.println("Couldn't load auth");
                    }
                }
            }
    }
}
