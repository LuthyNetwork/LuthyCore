package com.luthynetwork.core.commands;

import com.google.common.collect.Maps;
import com.luthynetwork.core.commands.annotation.command.Aliases;
import com.luthynetwork.core.commands.annotation.command.Command;
import com.luthynetwork.core.commands.annotation.subcommand.SubCommand;
import com.luthynetwork.core.commands.context.Context;
import com.luthynetwork.core.commands.controller.ISubCommandController;
import com.luthynetwork.core.commands.controller.impl.SubCommandControllerImpl;
import com.luthynetwork.core.commands.controller.impl.ValidSubCommandsControllerImpl;
import com.luthynetwork.core.commands.data.Sub;
import com.luthynetwork.core.commands.settings.Executor;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The original name of the project is VoidCommand.
 * You can download this library at
 * @link https://github.com/ianlibanio/VoidCommand
 *
 * @author Ian Libânio (Null)
 */
public abstract class VoidCommand extends org.bukkit.command.Command {

    private Player player;
    private Command command;

    private final ISubCommandController<String, Sub> controller = new SubCommandControllerImpl();
    private final ISubCommandController<String, Sub> validController = new ValidSubCommandsControllerImpl();

    public VoidCommand() {
        super("");

        for (Method method : this.getClass().getDeclaredMethods()) {
            // Commands
            val command = method.getAnnotation(Command.class);
            val aliases = method.getAnnotation(Aliases.class);

            if (this.command == null && command != null) {
                this.command = command;
                setName(command.name());
            }

            if (aliases != null) setAliases(Arrays.asList(aliases.value()));

            // SubCommands
            val subAnnotation = method.getAnnotation(SubCommand.class);

            if ((command == null && aliases == null) && subAnnotation != null) {
                controller.put(subAnnotation.name(), new Sub(subAnnotation, method));
            }
        }
    }

    public abstract void command(Context context);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        val executor = command.executor();

        if (executor.equals(Executor.PLAYER_ONLY) && !isPlayer(sender)) {
            sender.sendMessage(ChatColor.GRAY + "Este comando só pode ser executado por " + ChatColor.RED + "PLAYERS" + ChatColor.GRAY + ".");
            return true;
        }

        if (executor.equals(Executor.CONSOLE_ONLY) && isPlayer(sender)) {
            sender.sendMessage(ChatColor.GRAY + "Esse comando só pode ser executado no " + ChatColor.RED + "CONSOLE" + ChatColor.GRAY + ".");
            return true;
        }

        if (!hasPermission(sender, command.permission())) {
            sender.sendMessage(ChatColor.RED + "Você não possui a permissão '" + ChatColor.BOLD + command.permission() + ChatColor.RED + "' para executar este comando!");
            return true;
        }
        if (isPlayer(sender)) this.player = (Player) sender;

        AtomicBoolean use = new AtomicBoolean(true);

        if (args.length > 0) {
            controller.get().forEach((name, sub) -> {
                val parameters = sub.getMethod().getParameterTypes();

                if (parameters.length >= 1 && parameters[0].isAssignableFrom(Context.class)) {
                    val split = name.split("\\.");

                    if (split.length == 0 || args[0].equals("") || args.length < split.length) {
                        use.set(false);
                        return;
                    }

                    for (int i = 0; i < split.length; i++) {
                        if (!args[i].equalsIgnoreCase(split[i])) {
                            use.set(false);
                            return;
                        }
                    }

                    if (hasPermission(sender, sub.getSubCommand().permission())) {
                        validController.put(name, sub);
                    }
                }
            });

            val valid = validController.get();

            if (valid.size() == 1) {
                valid.values().forEach(sub -> invoke(sub.getMethod(), sub.getSubCommand().executor(), sender, label, args, sub.getSubCommand()));
                use.set(false);
            }

            if (valid.size() > 1) {
                AtomicReference<Map.Entry<String, Sub>> max = new AtomicReference<>(null);

                valid.forEach((name, sub) -> {
                    if (max.get() == null || name.split("\\.").length > max.get().getKey().split("\\.").length)
                        max.set(Maps.immutableEntry(name, sub));
                });

                val sub = max.get().getValue();

                invoke(sub.getMethod(), sub.getSubCommand().executor(), sender, label, args, sub.getSubCommand());
                use.set(false);
            }
        }

        if (use.get()) {
            command(new Context(sender, label, args, player, command, null));
        }

        if (!use.get() && validController.get().equals(Collections.emptyMap())) {
            for (final String invalid : command.invalid()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', invalid));
            }
        }

        validController.clear();
        return false;
    }

    private boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        return permission == null || permission.equals("") || sender.hasPermission(permission);
    }

    @SneakyThrows
    private void invoke(Method method, Executor executor, CommandSender sender, String label, String[] args, SubCommand subCommand) {
        if (executor.equals(Executor.PLAYER_ONLY) && !isPlayer(sender)) {
            sender.sendMessage(ChatColor.GRAY + "Este comando só pode ser executado por " + ChatColor.RED + "PLAYERS" + ChatColor.GRAY + ".");
            return;
        }

        if (executor.equals(Executor.CONSOLE_ONLY) && isPlayer(sender)) {
            sender.sendMessage(ChatColor.GRAY + "Esse comando só pode ser executado no " + ChatColor.RED + "CONSOLE" + ChatColor.GRAY + ".");
            return;
        }

        method.invoke(this, new Context(sender, label, args, player, command, subCommand));
    }
}
