package com.luthynetwork.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;

@AllArgsConstructor
public class AccountLoadEvent extends Event {

    @Getter private final PlayerJoinEvent joinEvent;

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
