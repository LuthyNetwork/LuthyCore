package com.luthymc.warps.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class ItemStackSerializer {

    public static ItemStack deserialize(String data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        ItemStack itemStack = null;
        try {
            Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            Class<?> nmsItemStackClass = getNMSClass("ItemStack");
            Object nbtTagCompound = Objects.requireNonNull(getNMSClass("NBTCompressedStreamTools")).getMethod("a", DataInputStream.class).invoke(null, dataInputStream);
            Object craftItemStack = Objects.requireNonNull(nmsItemStackClass).getMethod("createStack", nbtTagCompoundClass).invoke(null, nbtTagCompound);
            itemStack = (ItemStack) Objects.requireNonNull(getOBClass("inventory.CraftItemStack")).getMethod("asBukkitCopy", nmsItemStackClass).invoke(null, craftItemStack);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return itemStack;
    }

    public static String serialize(ItemStack item) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);

        try {
            Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            Constructor<?> nbtTagCompoundConstructor = Objects.requireNonNull(nbtTagCompoundClass).getConstructor();
            Object nbtTagCompound = nbtTagCompoundConstructor.newInstance();
            Object nmsItemStack = Objects.requireNonNull(getOBClass("inventory.CraftItemStack")).getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
            Objects.requireNonNull(getNMSClass("ItemStack")).getMethod("save", nbtTagCompoundClass).invoke(nmsItemStack, nbtTagCompound);
            Objects.requireNonNull(getNMSClass("NBTCompressedStreamTools")).getMethod("a", nbtTagCompoundClass, DataOutput.class).invoke(null, nbtTagCompound, dataOutput);

        } catch (SecurityException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }

    private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    private static Class<?> getOBClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

}
