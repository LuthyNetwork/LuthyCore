package com.yan.minecraft.luthycore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yan.minecraft.luthycore.helpers.CorePlugin;
import com.yan.minecraft.luthycore.libs.database.CoreHikariImplement;
import com.yan.minecraft.luthycore.libs.proxy.BungeeChannelApi;
import com.yan.minecraft.luthycore.service.IAccount;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

public class LuthyAPI {

    @Getter public CoreHikariImplement hikariConnection;
    @Getter public BungeeChannelApi bungeeChannelApi;

    @Getter public IAccount accountService;
    @Getter public Gson gson;

    public LuthyAPI(HikariDataSource hikariDataSource, CorePlugin plugin) {
        bungeeChannelApi = new BungeeChannelApi(plugin);

        accountService = plugin.getService(IAccount.class);

        gson = new GsonBuilder().create();

        hikariConnection = new CoreHikariImplement(hikariDataSource);
    }

}
