package com.yan.minecraft.luthycore.service.implement;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.yan.minecraft.luthycore.LuthyAPI;
import com.yan.minecraft.luthycore.LuthyCore;
import com.yan.minecraft.luthycore.data.Account;
import com.yan.minecraft.luthycore.service.IAccount;

import java.util.Map;
import java.util.UUID;

public class AccountImpl implements IAccount {

    private final Map<UUID, Account> all = Maps.newHashMap();
    private final LuthyAPI api = LuthyCore.getApi();

    @Override
    public void save(Account corePlayer) {
        Gson gson = api.getGson();

        api.getHikariConnection().query("INSERT INTO USERS(uuid,data) VALUES(?,?) ON DUPLICATE KEY UPDATE DATA=?", (statement) -> {
            statement.setString(1, corePlayer.getUuid().toString());
            statement.setString(2, gson.toJson(corePlayer));
            statement.setString(3, gson.toJson(corePlayer));
            statement.execute();
        });
    }

    @Override
    public void saveAll() {
        Gson gson = api.getGson();
        api.getHikariConnection().query("INSERT INTO USERS(uuid,data) VALUES(?,?) ON DUPLICATE KEY UPDATE DATA=?", (statement) -> {
            for (Account corePlayer : all().values()) {
                statement.setString(1, corePlayer.getUuid().toString());
                statement.setString(2, gson.toJson(corePlayer));
                statement.setString(3, gson.toJson(corePlayer));
                statement.addBatch();
            }
            statement.executeBatch();
            statement.clearBatch();
        });
    }

    @Override
    public void load(UUID uuid) {
        Gson gson = api.getGson();

        api.getHikariConnection().query("select * from users where uuid=?", (statement) -> {
            statement.setString(1, uuid.toString());
            statement.execute();
            api.getHikariConnection().result(statement, (resultSet -> {
                if (!resultSet.next()) {
                    create(uuid);
//                    System.out.println("creating new");
                    return;
                }
                all().put(uuid, gson.fromJson(resultSet.getString("data"), Account.class));
                System.out.println(all.size());
            }));
        });
    }

    @Override
    public Account get(String name) {
        return all.values().stream().filter(player -> player.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public Account get(UUID uuid) {
        return all.get(uuid);
    }

    @Override
    public Account create(UUID uuid) {
        if (get(uuid) == null) {
            Account corePlayer = new Account(uuid);
            all.put(uuid, corePlayer);
            return corePlayer;
        }
        return get(uuid);
    }

    @Override
    public void delete(UUID uuid) {
        if (get(uuid) != null) {
            all.remove(uuid);
        }
    }

    @Override
    public Map<UUID, Account> all() {
        return all;
    }

}
