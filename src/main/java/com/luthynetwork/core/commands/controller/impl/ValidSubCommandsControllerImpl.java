package com.luthynetwork.core.commands.controller.impl;

import com.google.common.collect.Maps;
import com.luthynetwork.core.commands.controller.ISubCommandController;
import com.luthynetwork.core.commands.data.Sub;

import java.util.Map;

/**
 * The original name of the project is VoidCommand.
 * You can download this library at
 * @link https://github.com/ianlibanio/VoidCommand
 *
 * @author Ian Libânio (Null)
 */
public class ValidSubCommandsControllerImpl implements ISubCommandController<String, Sub> {

    private final Map<String, Sub> subCommandMethodMap = Maps.newHashMap();

    @Override
    public void put(String s, Sub sub) {
        subCommandMethodMap.put(s, sub);
    }

    @Override
    public void clear() {
        subCommandMethodMap.clear();
    }

    @Override
    public Map<String, Sub> get() {
        return subCommandMethodMap;
    }

}
