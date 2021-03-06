package xyz.mrmelon54.BedrockDestroyer.mixin;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.mrmelon54.BedrockDestroyer.BedrockDestroyer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlock.class)
public abstract class MixinAbstractFurnaceBlock {
    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractFurnaceBlock;openScreen(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)V", ordinal = 0))
    private void wrapOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (BedrockDestroyer.getInstance().shouldIgnorePlayer(player)) return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
            int size = abstractFurnaceBlockEntity.size();
            for (int i = 0; i < size; i++) {
                ItemStack stack = abstractFurnaceBlockEntity.getStack(i);
                if (BedrockDestroyer.getInstance().shouldDestroyItemStack(stack))
                    abstractFurnaceBlockEntity.setStack(i, BedrockDestroyer.getInstance().createEmptyStack());
            }
        }
    }
}
