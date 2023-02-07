package net.guryavkin.drugsmodguryavkin.mixin;

import com.google.common.collect.Lists;
import net.guryavkin.drugsmodguryavkin.util.IPostChainTweak;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(PostChain.class)
public abstract class RenderMaxTimeMixin implements IPostChainTweak, AutoCloseable {

    @Shadow
    private float time;

    @Shadow
    private float lastStamp;

    @Shadow
    private final List<PostPass> passes = Lists.newArrayList();

    /**
     * @author guryavkin
     * @reason 1 second of time for the shader????? too little
     */
    @Overwrite(remap = false)
    public void process(float p_110024_) {
        if (p_110024_ < this.lastStamp) {
            this.time += 1.0F - this.lastStamp;
            this.time += p_110024_;
        } else {
            this.time += p_110024_ - this.lastStamp;
        }

        for(this.lastStamp = p_110024_; this.time > 2400.0F; this.time -= 2400.0F) {
        }

        for(PostPass postpass : this.passes) {
            postpass.process(this.time / 20.0F);
        }
    }
}
