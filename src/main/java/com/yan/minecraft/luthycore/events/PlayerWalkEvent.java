package com.yan.minecraft.luthycore.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

@AllArgsConstructor
public class PlayerWalkEvent extends Event {

    @Getter private final PlayerMoveEvent move;

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
