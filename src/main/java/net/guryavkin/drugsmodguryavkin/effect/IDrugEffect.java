package net.guryavkin.drugsmodguryavkin.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IDrugEffect
{
    String GetShaderPath();

    void ServerSideAct(Level level, Player player);

    void ClientSideAct();

    public void Applied(Player player, int duration, int amp);
}
