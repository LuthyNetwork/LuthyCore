package com.luthynetwork.core;

import com.luthynetwork.core.controller.DatabaseController;
import com.luthynetwork.core.helpers.CorePlugin;
import com.luthynetwork.core.listener.AccountListener;
import com.luthynetwork.core.listener.local.LocalPlayerListener;
import com.luthynetwork.core.service.IAccount;
import com.luthynetwork.core.service.implement.AccountImpl;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;

public final class LuthyCore extends CorePlugin {

    @Getter private static LuthyCore instance;
    @Getter private static LuthyAPI api;

    @Override
    public void load() {
        provideService(IAccount.class, new AccountImpl());
    }

    @Override
    public void enable() {
        instance = this;

        saveDefaultConfig();

        api = new LuthyAPI(getDataSourceFromConfig(), this);
        DatabaseController.init();

        listeners(this, new AccountListener(), new LocalPlayerListener());

        Bukkit.getOnlinePlayers().forEach(player -> api.getAccountService().load(player.getUniqueId()));
    }

    @Override
    @SneakyThrows
    public void disable() {
        api.getHikariConnection().connection().close();
    }

}
