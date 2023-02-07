package net.guryavkin.drugsmodguryavkin.networking.packet;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.effect.DrugEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HighS2CPacket {
    private static boolean drugged;

    private static int drugAmount;

    private static String shaderPath;

    public HighS2CPacket(boolean _drugged, String _shaderPath)
    {
        HighS2CPacket.shaderPath = _shaderPath;
        HighS2CPacket.drugged = _drugged;
    }

    public HighS2CPacket(boolean _drugged)
    {
        HighS2CPacket.shaderPath = "";
        HighS2CPacket.drugged = _drugged;
    }

    public HighS2CPacket(FriendlyByteBuf buffer)
    {
        HighS2CPacket.drugged = buffer.readBoolean();
        HighS2CPacket.shaderPath = buffer.readUtf();
    }

    public void toBytes(FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(HighS2CPacket.drugged);
        buffer.writeUtf(HighS2CPacket.shaderPath);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            //clientside
            if (HighS2CPacket.drugged)
            {
                DrugEffect.ApplyShader(HighS2CPacket.shaderPath);
            }
            else
            {
                DrugEffect.ToggleShader(HighS2CPacket.shaderPath);
            }
        });
        return true;
    }
}
