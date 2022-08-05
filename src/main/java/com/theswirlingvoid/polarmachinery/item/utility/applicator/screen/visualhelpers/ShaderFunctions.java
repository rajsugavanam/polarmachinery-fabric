package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.visualhelpers;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;
import com.theswirlingvoid.polarmachinery.generic.color.AlphaRGB;

import net.minecraft.client.render.GameRenderer;

public class ShaderFunctions {

	public static void applyArcShaders(AlphaRGB color)
	{
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.disableCull();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		RenderSystem.setShaderColor(color.getRed()/255, color.getGreen()/255, color.getBlue()/255, color.getAlpha());
	}

	public static void applyItemShaders(AlphaRGB color)
	{
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public static void revertItemShaders()
	{
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
