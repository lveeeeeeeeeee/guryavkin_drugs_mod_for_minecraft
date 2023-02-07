package net.guryavkin.drugsmodguryavkin.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WeedEffect extends DrugEffect implements IDrugEffect{
    public WeedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public String GetShaderPath() {
        return null;
    }

    @Override
    public void ServerSideAct(Level level, Player player) {

    }

    @Override
    public void ClientSideAct() {

    }

    @Override
    public void Applied(Player player, int duration) {

    }
}
