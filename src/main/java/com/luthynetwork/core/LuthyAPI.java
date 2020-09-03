package com.luthynetwork.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luthynetwork.core.helpers.CorePlugin;
import com.luthynetwork.core.libs.database.CoreHikariImplement;
import com.luthynetwork.core.libs.proxy.BungeeChannelApi;
import com.luthynetwork.core.service.IAccount;
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
