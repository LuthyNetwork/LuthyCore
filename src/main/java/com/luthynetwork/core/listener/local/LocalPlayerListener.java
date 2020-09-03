package com.luthynetwork.core.listener.local;

import com.luthynetwork.core.events.PlayerWalkEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LocalPlayerListener implements Listener {

    @EventHandler
    private void move(PlayerMoveEvent event){
        if (event.getFrom().getX() != event.getTo().getX() && event.getFrom().getZ() != event.getTo().getZ()) {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerWalkEvent(event));
        }
    }

}
