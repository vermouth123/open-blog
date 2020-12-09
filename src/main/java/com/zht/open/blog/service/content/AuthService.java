package com.zht.open.blog.service.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    //保存登录时间，key是login，value是校验时间，超时时间一周
    private static Map<String, Long> map = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    public boolean checkUser() {
        File file = new File("auth/user");
        return file.exists() && file.isFile();
    }

    //第一次使用编辑器时需要添加，用户名就是博客名，只需要设置密码就可以
    public void addUser(String password) {
        File file = new File("auth/user");
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(password);
            writer.close();
            map.put("login", System.currentTimeMillis());
        } catch (IOException e) {
            logger.error("[auth-service] create file error, e = {}", e);
        }
    }

    public void updatePassword(String password) throws Exception {
        File file = new File("auth/user");
        if(!file.exists()){
            throw new Exception("user not exist");
        }
        FileWriter writer = new FileWriter(file);
        writer.write(password);
        writer.close();
    }

    public boolean isLogout() {
        if (!map.containsKey("login")) {
            return false;
        }
        long loginTime = map.get("login");
        long duration = System.currentTimeMillis() - loginTime;
        if (duration > (7 * 24 * 60 * 60 * 1000)) {
            map.clear();
            return false;
        }
        return true;
    }

    public boolean checkAuth(String password) {
        File file = new File("auth/user");
        if (!file.exists()) {
            return false;
        }
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[100];
            reader.read(chars, 0, 100);
            StringBuilder pwd = new StringBuilder();
            if (chars != null && chars.length != 0) {
                for (char c : chars) {
                    pwd.append(c);
                }
            }
            reader.close();
            if (pwd.equals(password)) {
                return true;
            }
        } catch (FileNotFoundException e) {
            logger.error("[auth-service] auth file not exist, e = {}", e);
        } catch (IOException e) {
            logger.error("[auth-service] read file error,e = {}", e);
        }
        return false;
    }


    public static void main(String[] args) throws IOException {

        File file = new File("auth/user");
        file.createNewFile();
    }
}
