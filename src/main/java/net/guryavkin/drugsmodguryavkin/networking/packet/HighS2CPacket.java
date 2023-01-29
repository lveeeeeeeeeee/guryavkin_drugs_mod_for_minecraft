package net.guryavkin.drugsmodguryavkin.networking.packet;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HighS2CPacket {
    public HighS2CPacket()
    {

    }

    public HighS2CPacket(FriendlyByteBuf buffer)
    {

    }

    public void toBytes(FriendlyByteBuf buffer)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        try
        {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() ->
            {
                // actions of the client
            });
            return true;
        }
        catch (Exception e)
        {
            DrugsModGuryavkin.LOGGER.info(e.getMessage());
            return false;
        }
    }
}
