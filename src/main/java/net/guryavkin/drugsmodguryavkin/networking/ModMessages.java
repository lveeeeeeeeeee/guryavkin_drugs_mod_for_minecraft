package net.guryavkin.drugsmodguryavkin.networking;

import net.guryavkin.drugsmodguryavkin.DrugsModGuryavkin;
import net.guryavkin.drugsmodguryavkin.networking.packet.HighC2SPacket;
import net.guryavkin.drugsmodguryavkin.networking.packet.HighS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id (){
        return packetId++;
    }

    public static void register()
    {
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(
                new ResourceLocation(DrugsModGuryavkin.MOD_ID, "high_effect"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(
                HighC2SPacket.class,
                id(),
                NetworkDirection.PLAY_TO_SERVER)
                .decoder(HighC2SPacket::new)
                .encoder(HighC2SPacket::toBytes)
                .consumerMainThread(HighC2SPacket::handle)
                .add();

        net.messageBuilder(
                HighS2CPacket.class,
                id(),
                NetworkDirection.PLAY_TO_CLIENT
                )
                .decoder(HighS2CPacket::new)
                .encoder(HighS2CPacket::toBytes)
                .consumerMainThread(HighS2CPacket::handle)
                .add();
    }



    public static <MSG> void sendToServer(MSG message)
    {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
