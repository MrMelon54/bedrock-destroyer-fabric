package xyz.mrmelon54.BedrockDestroyer.mixin;

import net.minecraft.entity.vehicle.ChestMinecartEntity;
import xyz.mrmelon54.BedrockDestroyer.IMinecartEntitySize;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChestMinecartEntity.class)
public abstract class MixinChestMinecartEntity implements IMinecartEntitySize {

    @Shadow
    public abstract int size();

    @Override
    public int getSize() {
        return size();
    }
}
