package com.yan.minecraft.luthycore;

import com.yan.minecraft.luthycore.helpers.CorePlugin;
import com.yan.minecraft.luthycore.listener.AccountListener;
import com.yan.minecraft.luthycore.listener.local.LocalPlayerListener;
import com.yan.minecraft.luthycore.service.IAccount;
import com.yan.minecraft.luthycore.service.implement.AccountImpl;
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

        api = new LuthyAPI(getDataSourceFromConfig(), this);

        listeners(this, new AccountListener(), new LocalPlayerListener());

        Bukkit.getOnlinePlayers().forEach(player -> api.getAccountService().load(player.getUniqueId()));
    }

    @Override
    @SneakyThrows
    public void disable() {
        api.getHikariConnection().connection().close();
    }

}
