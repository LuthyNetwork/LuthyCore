package com.yan.minecraft.luthycore.data;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data public class Account {

    private UUID uuid;

    private String name;

    private long cash = 0;

    protected String password = "";

    private long firstLogin = 0;
    private long lastLogin = 0;

    /*
        Local Data Storage
    */
    private transient Map<String,Object> localData;


    public Account(UUID uuid){
        this.uuid = uuid;
        this.firstLogin = System.currentTimeMillis();
        this.localData = Maps.newHashMap();
    }

    public Map<String,Object> getLocalData(){
        return localData;
    }
    public void addLocalData(String key,Object value){
        this.localData.put(key,value);
    }
    public Object getLocalData(String key){
        return this.localData.get(key);
    }
    public void removeLocalData(String key){
        this.localData.remove(key);
    }

}
