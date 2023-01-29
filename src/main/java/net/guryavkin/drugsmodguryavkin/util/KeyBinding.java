package net.guryavkin.drugsmodguryavkin.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding
{
    public static final String KEY_BIND_CATEGORY = "key.category.drugsmodguryavkin.fuckery";
    public static final String KEY_FUCKERY = "key.drugsmodguryavkin.fuckery";

    public static final KeyMapping FUCK_KEY = new KeyMapping(
            KEY_FUCKERY,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            KEY_BIND_CATEGORY);


}
