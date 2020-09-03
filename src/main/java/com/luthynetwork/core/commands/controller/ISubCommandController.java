package com.luthynetwork.core.commands.controller;

import java.util.Map;

/**
 * The original name of the project is VoidCommand.
 * You can download this library at
 * @link https://github.com/ianlibanio/VoidCommand
 *
 * @author Ian Libânio (Null)
 */
public interface ISubCommandController<K, V> {

    void put(K k, V v);
    void clear();
    Map<K, V> get();

}
