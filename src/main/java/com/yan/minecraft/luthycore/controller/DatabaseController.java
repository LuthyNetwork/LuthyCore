package com.yan.minecraft.luthycore.controller;

import com.yan.minecraft.luthycore.LuthyCore;

import java.sql.PreparedStatement;


public class DatabaseController {

    public static void init(){
        LuthyCore.getApi().getHikariConnection().query("CREATE TABLE IF NOT EXISTS `users` (`uuid` VARCHAR(100) primary key,`data` TEXT);",
                PreparedStatement::execute);
    }

}
