package com.luthynetwork.core.libs.server;

import com.luthynetwork.core.LuthyAPI;
import com.luthynetwork.core.LuthyCore;
import com.luthynetwork.core.libs.util.message.MessageBuilder;

public class StaffUtil {

    //    public static String CHAT_FORMAT = "§7§l[§cS§7§l] %group% %player% » §f%message%";
    public static String CHAT_FORMAT = "§7§l[§cS§7§l] %player% » §f%message%";
    private static final LuthyAPI api = LuthyCore.getApi();

    public static void chat(String sender, String message) {
        api.getBungeeChannelApi().getPlayerList("ALL").whenComplete(((strings, throwable) -> {

            for (String player : strings) {
//                Account account = api.getAccountService().get(player);
//                if (account.getGroup().isHigherOrEqual(Group.HELPER)) {
                MessageBuilder builder = new MessageBuilder(CHAT_FORMAT);
//                    builder.add("%group%", account.getGroup().getPrefix());
                builder.add("%player%", sender);
                builder.add("%message%", message);
                api.getBungeeChannelApi().sendMessage(player, builder.build());
//                }
            }
        }));
    }


}
