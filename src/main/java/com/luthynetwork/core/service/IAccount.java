package com.luthynetwork.core.service;

import com.luthynetwork.core.data.Account;

import java.util.Map;
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
