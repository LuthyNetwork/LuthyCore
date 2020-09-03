package com.yan.minecraft.luthycore.libs.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface HikariConnection {

    Connection connection() throws SQLException;
}
