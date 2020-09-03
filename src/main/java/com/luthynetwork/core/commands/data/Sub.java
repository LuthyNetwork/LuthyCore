package com.luthynetwork.core.commands.data;

import com.luthynetwork.core.commands.annotation.subcommand.SubCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * The original name of the project is VoidCommand.
 * You can download this library at
 * @link https://github.com/ianlibanio/VoidCommand
 *
 * @author Ian Libânio (Null)
 */
@Getter
@AllArgsConstructor
public class Sub {

    private final SubCommand subCommand;
    private final Method method;

}
