package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.structs.arc;

import java.util.Optional;

import com.theswirlingvoid.polarmachinery.generic.ExtendedItemRenderer;
import com.theswirlingvoid.polarmachinery.generic.color.AlphaRGB;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.visualhelpers.ShaderFunctions;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec2f;

public class RadialArc {
	
	private RadialArcData arcData;
	private RadialArcAngularData angularData;
	private RadialArcVisuals visuals;

	private float universalScreenScale;

	public RadialArc(RadialArcVisuals visuals, RadialArcData arcData, RadialArcAngularData data) {
		this.visuals = visuals;
		this.arcData = arcData;
		this.angularData = data;
	}

	public void drawArcWithItem(MinecraftClient client, float itemScale, float circleAnimationCompletion, @Nullable float selectElapsedTime)
	{
		this.drawArc(client, this.angularData.getDegResolution(), selectElapsedTime);
		this.drawItem(client, circleAnimationCompletion, itemScale, selectElapsedTime);
	}

	public void drawArc(MinecraftClient client, float degResolution, @Nullable float elapsedTime)
	{
		universalScreenScale = (float) (Math.sqrt((client.currentScreen.width*client.currentScreen.height))/320.12f);
		/* ------------------------------ Elapsed Time ------------------------------ */
		float elapsedMult = getElapsedMult(elapsedTime);
		/* -------------------------------------------------------------------------- */


		// saved here so the item renderer can use it (and so if statement isn't spammed)
		/* ------------------------------- Arc Pushout ------------------------------ */
		float arcPushoutPercentage = getArcPushoutPercentage();
		AlphaRGB sectorColor = getSectorColor(elapsedMult);
		/* -------------------------------------------------------------------------- */
		
		/* ------------------------------ Item Pushout ------------------------------ */
		float pushoutMult = (1+(elapsedMult*arcPushoutPercentage));
		/* -------------------------------------------------------------------------- */

		ShaderFunctions.applyArcShaders(sectorColor);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

		float offsetInnerRadius = getOffsetInnerRadius();
		float offsetOuterRadius = getOffsetOuterRadius();

		// for animation - goes from 0 to 1
		/* -------------------------- Multiplier Animation -------------------------- */
		float multiplier = (float) Math.min(
			1, (this.visuals.getAnimationOptions().getTotalSecs()/(this.visuals.getAnimationOptions().getSecsToRenderNextArc()*(this.arcData.getIndex()+5)))
		);
		/* -------------------------------------------------------------------------- */

		Vec2f vecOffset = getVecOffset().multiply(multiplier);
		float radiusDist = multiplier*(offsetOuterRadius-offsetInnerRadius);

		// these are defaulted here so that the item draw function can use these without needing to recalculate
		for (float deg = this.angularData.getDegStart(); deg <= this.angularData.getDegEnd(); deg=deg+this.angularData.getDegResolution())
		{

			// convert the start angle to radians
			double r = Math.toRadians(deg);

			float innerCos = (float) (this.arcData.getInnerRadius()*Math.cos(r));
			float innerSin = (float) (this.arcData.getInnerRadius()*Math.sin(r));
			
			float outerCos = (float) (this.arcData.getOuterRadius()*Math.cos(r));
			float outerSin = (float) (this.arcData.getOuterRadius()*Math.sin(r));


			/* ---------------------------- Circle Correction --------------------------- */
			Vec2f innerVertexVec = new Vec2f(innerCos, innerSin);
			Vec2f outerVertexVec = new Vec2f(outerCos, outerSin);
			
			Vec2f innerTransformedVec = innerVertexVec.add(vecOffset);
			Vec2f outerTransformedVec = outerVertexVec.add(vecOffset);

			Vec2f normalizedTransformVec = innerTransformedVec.normalize();

			Vec2f finalVec = new Vec2f(normalizedTransformVec.x*offsetInnerRadius, normalizedTransformVec.y*offsetInnerRadius);

			Vec2f vecDist = outerTransformedVec.add(innerTransformedVec.multiply(-1));
			Vec2f normalizedDist = vecDist.normalize();
			Vec2f finalDist = new Vec2f(normalizedDist.x*radiusDist, normalizedDist.y*radiusDist);

			Vec2f finalOutVec = finalVec.add(finalDist);
			/* -------------------------------------------------------------------------- */

			/* -------------------- Alter Position If Sector Selected ------------------- */
			finalVec = finalVec.multiply(pushoutMult);
			finalOutVec = finalOutVec.multiply(pushoutMult);
			/* -------------------------------------------------------------------------- */

			Matrix4f posMatrix = this.visuals.getMatrixStack().peek().getPositionMatrix();
			// outer radius vertices
			bufferBuilder.vertex(posMatrix, (float) (client.currentScreen.width/2 + finalOutVec.x), (float) (client.currentScreen.height/2 + finalOutVec.y), 0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
			// inner radius vertices
			bufferBuilder.vertex(posMatrix, (float) (client.currentScreen.width/2 + finalVec.x), (float) (client.currentScreen.height/2 + finalVec.y), 0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();

		}
		BufferRenderer.drawWithShader(bufferBuilder.end());
	}

	private float getArcPushoutPercentage()
	{
		if (this.arcData.isSelected())
			return 0.1f;
		else
			return 0.0f;
	}

	private float getElapsedMult(float elapsedTime)
	{
		Optional<Float> elapsedTimeOptional = Optional.of(elapsedTime);
		if (elapsedTimeOptional.isEmpty())
		{
			elapsedTime = 1f;
		}
		float elapsedMult = Math.min(1, 8*elapsedTime);

		return elapsedMult;
	}

	private AlphaRGB getSectorColor(float elapsedMult)
	{
		AlphaRGB sectorColor = this.visuals.getColorOptions().getAppropriateColorCopy(false);

		// towards 0 = unselected, towards 1 = selected
		if (this.arcData.isSelected())
			sectorColor = this.visuals.getColorOptions().linearMix(elapsedMult);

		sectorColor.setAlpha(getAnimatedAlpha(sectorColor.getAlpha()));

		return sectorColor;
	}

	private float getAnimatedAlpha(float totalAlpha)
	{
		return Math.min(totalAlpha, (totalAlpha*this.visuals.getAnimationOptions().getTotalSecs())/(this.visuals.getAnimationOptions().getSecsFadeIn()*(this.arcData.getIndex()+1)));
	}

	private Vec2f getNormalMidVec()
	{
		float midRadians = (float) Math.toRadians(this.angularData.getDegStart()+this.angularData.getDegEnd())/2;
		return new Vec2f((float)Math.cos(midRadians), (float)(Math.sin(midRadians)));
	}

	public Vec2f getOffsetInnerCorner()
	{
		Vec2f midVec = getNormalMidVec();
		Vec2f copy = new Vec2f(midVec.x, midVec.y);
		return copy.multiply(this.arcData.getOffsetInnerRadius());
	}

	public Vec2f getOffsetOuterCorner()
	{
		Vec2f midVec = getNormalMidVec();
		Vec2f copy = new Vec2f(midVec.x, midVec.y);
		return copy.multiply(this.arcData.getOffsetOuterRadius());
	}

	private Vec2f getVecOffset()
	{
		Vec2f midVec = getNormalMidVec();
		Vec2f copy = new Vec2f(midVec.x, midVec.y);
		// project from the center of the arc; that is, based on the center of the start and end angles
		// also a constant offset
		return copy.multiply(this.arcData.getPushoutRadius());
	}

	public float getOffsetInnerRadius()
	{
		return this.arcData.getPushoutRadius()+this.arcData.getInnerRadius();
	}

	public float getOffsetOuterRadius()
	{
		return this.arcData.getPushoutRadius()+this.arcData.getOuterRadius();
	}

	public void drawItem(MinecraftClient client, float animationCompletion, float scale, @Nullable float elapsedTime)
	{
		float pushoutMult = (1+(getElapsedMult(elapsedTime)*getArcPushoutPercentage()));

		float pushoutDist = animationCompletion*(getOffsetOuterRadius()-getOffsetInnerRadius());

		// from normalized vec,
		// 	inner corner multiplier = offsetInnerRadius*elapsedSelectMult
		//  outer corner multiplier = (offsetInnerRadius+radiusDist)*elapsedSelectMult

		// we need to convert it, so:
		// innerCornerMult/offsetInnerRadius = elapsedSelectMult
		// elapsedSelectMult*(offsetInnerRadius+radiusDist) = outerCornerMult

		Vec2f innerCorner = getOffsetInnerCorner().multiply(pushoutMult);
		// outer corner is relative offset from inner based on dist; it expands with the animation
		float outerCornerMult = 1+(pushoutDist/this.arcData.getOffsetInnerRadius());
		Vec2f outerCorner = innerCorner.multiply(outerCornerMult);

		Vec2f distVec = outerCorner.add(innerCorner.multiply(-1));
		Vec2f midArcVec = innerCorner.add(distVec.multiply(0.5f));

		// these two are so that the item will get larger with a selected arc (because the radius expands)
		// float standardDist = this.arcData.getOuterRadius()-this.arcData.getInnerRadius();

		// set shaders
		AlphaRGB shaderColor = new AlphaRGB(1.0f, 1.0f, 1.0f, 1.0f);
		ShaderFunctions.applyItemShaders(shaderColor);
		// texture size is always 16
		float halfPixels = 8f;

		ExtendedItemRenderer extendedRenderer = new ExtendedItemRenderer(client.getItemRenderer());

		extendedRenderer.renderGuiItemIconWithScale(this.visuals.getDisplayedItem(), scale*universalScreenScale*animationCompletion, (int)(client.currentScreen.width/2 + midArcVec.x - halfPixels), (int)(client.currentScreen.height/2 + midArcVec.y - halfPixels));

		ShaderFunctions.revertItemShaders();
	}
}
