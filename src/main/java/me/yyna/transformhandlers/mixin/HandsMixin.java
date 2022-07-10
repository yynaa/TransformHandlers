package me.yyna.transformhandlers.mixin;

import me.yyna.transformhandlers.SettingsScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HandsMixin {
	@Inject(at = @At("HEAD"), method = "renderArmHoldingItem")
	private void renderArmHoldingItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm, CallbackInfo ci){
		if (arm == Arm.RIGHT && SettingsScreen.settings.enable && SettingsScreen.settings.ArmRight.enable){
			matrices.translate((double)SettingsScreen.settings.ArmRight.x/10D, (double)SettingsScreen.settings.ArmRight.y/10D, (double)SettingsScreen.settings.ArmRight.z/10D);
		}else if (arm == Arm.LEFT  && SettingsScreen.settings.enable && SettingsScreen.settings.ArmLeft.enable) {
			matrices.translate((double)SettingsScreen.settings.ArmLeft.x/10D, (double)SettingsScreen.settings.ArmLeft.y/10D, (double)SettingsScreen.settings.ArmLeft.z/10D);
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER), method = "renderFirstPersonItem")
	private void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info){
		if (!item.isEmpty() && SettingsScreen.settings.enable){
			if (ChargedCrossbowEnabled(item)){
				matrices.translate((double)SettingsScreen.settings.ChargedCrossbow.x/10D, (double)SettingsScreen.settings.ChargedCrossbow.y/10D, (double)SettingsScreen.settings.ChargedCrossbow.z/10D);
			} else if(FilledMapEnabled(item)) {
				matrices.translate((double)SettingsScreen.settings.FilledMap.x/10D, (double)SettingsScreen.settings.FilledMap.y/10D, (double)SettingsScreen.settings.FilledMap.z/10D);
			}

			if (CheckForDisabledSpecials(item)){
				if (MainHandEnabled(hand)){
					matrices.translate((double)SettingsScreen.settings.ItemsMain.x/10D, (double)SettingsScreen.settings.ItemsMain.y/10D, (double)SettingsScreen.settings.ItemsMain.z/10D);
				}else if (OffHandEnabled(hand)) {
					matrices.translate((double)SettingsScreen.settings.ItemsOff.x/10D, (double)SettingsScreen.settings.ItemsOff.y/10D, (double)SettingsScreen.settings.ItemsOff.z/10D);
				}
			}

		}
	}

	private static boolean MainHandEnabled(Hand hand){
		return hand == Hand.MAIN_HAND && SettingsScreen.settings.ItemsMain.enable;
	}
	private static boolean OffHandEnabled(Hand hand){
		return hand == Hand.OFF_HAND && SettingsScreen.settings.ItemsOff.enable;
	}
	private static boolean CheckForDisabledSpecials(ItemStack item){
		return !(item.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(item) && !SettingsScreen.settings.ChargedCrossbow.apply)
				&& !(item.isOf(Items.FILLED_MAP) && !SettingsScreen.settings.FilledMap.apply);
	}
	private static boolean ChargedCrossbowEnabled(ItemStack item){
		return item.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(item) && SettingsScreen.settings.ChargedCrossbow.enable;
	}
	private static boolean FilledMapEnabled(ItemStack item){
		return item.isOf(Items.FILLED_MAP) && SettingsScreen.settings.FilledMap.enable;
	}
}
