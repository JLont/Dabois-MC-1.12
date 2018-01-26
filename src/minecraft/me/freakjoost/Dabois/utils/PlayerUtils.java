package me.freakjoost.Dabois.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.freakjoost.Dabois.Dabois;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class PlayerUtils {

    /**
     * This method can be used to send your player a message. This message can only
     * be seen by you, no one else.
     **/
    public static void tellPlayer(String text) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(
                ChatFormatting.WHITE + "[" + ChatFormatting.RED + Dabois.INSTANCE.NAME + ChatFormatting.WHITE + "] " + text));
    }
}
