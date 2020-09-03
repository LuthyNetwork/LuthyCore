package com.luthynetwork.core.commands.registration;

import com.luthynetwork.core.commands.VoidCommand;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

/**
 * The original name of the project is VoidCommand.
 * You can download this library at
 * @link https://github.com/ianlibanio/VoidCommand
 *
 * @author Ian Libânio (Null)
 */
@AllArgsConstructor
public class VoidRegister {

    private final Plugin plugin;

    @SneakyThrows
    public void add(VoidCommand... commands)  {
        Field bukkitCommandMap = plugin.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);

        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(plugin.getServer());

        for (VoidCommand command : commands) {
            commandMap.register(plugin.getName(), command);
        }
    }
}