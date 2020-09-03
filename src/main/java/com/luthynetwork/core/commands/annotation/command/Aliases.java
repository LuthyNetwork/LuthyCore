package com.luthynetwork.core.commands.annotation.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The original name of the project is VoidCommand.
 * You can download this library at
 * @link https://github.com/ianlibanio/VoidCommand
 *
 * @author Ian Libânio (Null)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Aliases {

    String[] value() default { "" };

}
