package com.luthynetwork.core.listener;

import com.luthynetwork.core.LuthyAPI;
import com.luthynetwork.core.LuthyCore;
import com.luthynetwork.core.data.Account;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AccountListener implements Listener {

    private final LuthyAPI api = LuthyCore.getApi();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        api.getAccountService().load(player.getUniqueId());

        Account account = api.getAccountService().get(player.getUniqueId());

        account.setName(player.getName());
        account.setLastLogin(System.currentTimeMillis());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event){
        Account account = api.getAccountService().get(event.getPlayer().getUniqueId());

        api.getAccountService().save(account);
    }

}
