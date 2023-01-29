package net.guryavkin.drugsmodguryavkin.event;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.networking.ModMessages;
import net.guryavkin.drugsmodguryavkin.networking.packet.HighC2SPacket;
import net.guryavkin.drugsmodguryavkin.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents
{
    @Mod.EventBusSubscriber(modid = DrugsModGuryavkin.MOD_ID, value = Dist.CLIENT)
    public static class ClientKeyEvents
    {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBinding.FUCK_KEY);
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            try
            {
                if(KeyBinding.FUCK_KEY.consumeClick())
                {
                    GameRenderer renderer = Minecraft.getInstance().gameRenderer;
                    ResourceLocation loc = new ResourceLocation(DrugsModGuryavkin.MOD_ID, "shaders/post/tripeffect.json");
                    if (ResourceLocation.isValidResourceLocation(loc.getPath()))
                    {
                        renderer.loadEffect(loc);
                        ModMessages.sendToServer(new HighC2SPacket());
                    }
                    System.out.println(String.format("fuckety resource location bad, %1$s %2$s", loc.getNamespace(), loc.getPath()));
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
