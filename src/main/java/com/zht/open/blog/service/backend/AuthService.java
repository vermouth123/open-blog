package com.zht.open.blog.service.backend;

import java.io.File;

public class AuthService {



    public boolean checkUser(){
        File file = new File("user");
        return file.exists() && file.isFile();
    }



}
