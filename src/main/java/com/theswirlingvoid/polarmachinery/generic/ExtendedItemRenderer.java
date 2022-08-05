package com.theswirlingvoid.polarmachinery.generic;

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.theswirlingvoid.polarmachinery.mixin.ItemRendererAccessor;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class ExtendedItemRenderer {

	private ItemRenderer baseRenderer;

	public ExtendedItemRenderer(ItemRenderer renderer) 
	{
		baseRenderer = renderer;
	}

	public void renderGuiItemIconWithScale(ItemStack stack, float matrixScale, int x, int y) {
		renderGuiItemModelWithScale(stack, matrixScale, x, y, baseRenderer.getModel(stack, (World)null, (LivingEntity)null, 0));
	 }

	 public BakedModel getModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity, int seed) {
		BakedModel bakedModel;
		if (stack.isOf(Items.TRIDENT)) {
		   bakedModel = ((ItemRendererAccessor) baseRenderer).getModels().getModelManager().getModel(new ModelIdentifier("minecraft:trident_in_hand#inventory"));
		} else if (stack.isOf(Items.SPYGLASS)) {
		   bakedModel = ((ItemRendererAccessor) baseRenderer).getModels().getModelManager().getModel(new ModelIdentifier("minecraft:spyglass_in_hand#inventory"));
		} else {
		   bakedModel = ((ItemRendererAccessor) baseRenderer).getModels().getModel(stack);
		}
  
		ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld)world : null;
		BakedModel bakedModel2 = bakedModel.getOverrides().apply(bakedModel, stack, clientWorld, entity, seed);
		return bakedModel2 == null ? ((ItemRendererAccessor) baseRenderer).getModels().getModelManager().getMissingModel() : bakedModel2;
	 }

	 protected void renderGuiItemModelWithScale(ItemStack stack, float matrixScale, int x, int y, BakedModel model) {
		((ItemRendererAccessor) baseRenderer).getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false);
		RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		MatrixStack matrixStack = RenderSystem.getModelViewStack();
		matrixStack.push();
		matrixStack.translate((double)x, (double)y, (double)(100.0F + ((ItemRendererAccessor) baseRenderer).getZOffset()));
		matrixStack.translate(8.0D, 8.0D, 0.0D);
		matrixStack.scale(1.0F, -1.0F, 1.0F);
		matrixStack.scale(16.0F, 16.0F, 16.0F);

		/* -------------------------------------------------------------------------- */

		matrixStack.scale(matrixScale, matrixScale, matrixScale);

		/* -------------------------------------------------------------------------- */

		RenderSystem.applyModelViewMatrix();
		MatrixStack matrixStack2 = new MatrixStack();
		Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		boolean bl = !model.isSideLit();
		if (bl) {
		   DiffuseLighting.disableGuiDepthLighting();
		}
  
		((ItemRendererAccessor) baseRenderer).invokeRenderItem(stack, Mode.GUI, false, matrixStack2, immediate, 15728880, OverlayTexture.DEFAULT_UV, model);
		immediate.draw();
		RenderSystem.enableDepthTest();
		if (bl) {
		   DiffuseLighting.enableGuiDepthLighting();
		}
  
		matrixStack.pop();
		RenderSystem.applyModelViewMatrix();
	 }
	
}
