package xyz.mrmelon54.BedrockDestroyer.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.mrmelon54.BedrockDestroyer.BedrockDestroyer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
 * The Llama entity extends AbstractDonkeyEntity so this should work for the llama too
 */

@Mixin(AbstractDonkeyEntity.class)
public abstract class MixinAbstractDonkeyEntity extends AbstractHorseEntity {
    protected MixinAbstractDonkeyEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    protected abstract int getInventorySize();

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractDonkeyEntity;openInventory(Lnet/minecraft/entity/player/PlayerEntity;)V"))
    public void wrapInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (BedrockDestroyer.getInstance().shouldIgnorePlayer(player)) return;

        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.getStack(i);
            if (BedrockDestroyer.getInstance().shouldDestroyItemStack(stack)) {
                items.setStack(i, BedrockDestroyer.getInstance().createEmptyStack());
            }
        }
    }
}
