package com.yan.minecraft.luthycore.model;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLConsumer<T> {
    void accept(T t) throws SQLException;
}
