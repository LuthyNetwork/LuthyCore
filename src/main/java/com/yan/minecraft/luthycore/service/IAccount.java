package com.yan.minecraft.luthycore.service;

import com.yan.minecraft.luthycore.data.Account;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface IAccount {

    Account get(UUID uuid);
    Account get(String name);

    Account create(UUID uuid);

    void delete(UUID uuid);


    // Mysql Methods
    void saveAll();
    void save(Account corePlayer);
    void load(UUID uuid);

    Map<UUID,Account> all();
}
