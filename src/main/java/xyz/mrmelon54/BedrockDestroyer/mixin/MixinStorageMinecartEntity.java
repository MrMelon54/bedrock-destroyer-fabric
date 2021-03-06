package xyz.mrmelon54.BedrockDestroyer.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import xyz.mrmelon54.BedrockDestroyer.BedrockDestroyer;
import xyz.mrmelon54.BedrockDestroyer.IMinecartEntitySize;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StorageMinecartEntity.class)
public abstract class MixinStorageMinecartEntity implements IMinecartEntitySize {
    @Shadow
    public abstract ItemStack getStack(int slot);

    @Shadow
    public abstract void setStack(int slot, ItemStack stack);

    @Inject(method = "interact", at = @At("HEAD"))
    public void wrapInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (BedrockDestroyer.getInstance().shouldIgnorePlayer(player)) return;

        for (int i = 0; i < getSize(); i++) {
            ItemStack stack = getStack(i);
            if (BedrockDestroyer.getInstance().shouldDestroyItemStack(stack)) {
                setStack(i, BedrockDestroyer.getInstance().createEmptyStack());
            }
        }
    }
}
