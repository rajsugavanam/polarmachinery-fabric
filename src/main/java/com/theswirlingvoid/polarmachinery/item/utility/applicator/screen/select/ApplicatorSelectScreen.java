package com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select;

import com.mojang.blaze3d.systems.RenderSystem;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.ApplicatorHelper;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.ApplicatorInventory;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.animation.RadialAnimationOptions;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.options.ColorOptions;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.structs.arc.RadialArc;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.structs.arc.RadialArcAngularData;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.structs.arc.RadialArcData;
import com.theswirlingvoid.polarmachinery.item.utility.applicator.screen.select.structs.arc.RadialArcVisuals;
import com.theswirlingvoid.polarmachinery.screens.ModScreenHandlers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec2f;

@Environment(EnvType.CLIENT)
public class ApplicatorSelectScreen extends Screen {

	private ColorOptions colorOptions;
	// private ArcPositionalData arcData;
	private float forgivenessRatio;

	private float currentInnerRadius;
	private float currentOuterRadius;
	private float currentPushoutRadius;

	private int selectedArc = -1;
	private int arcIndex = 0;

	private static final float INNER_RADIUS_RATIO = 0.18f;
	private static final float OUTER_RADIUS_RATIO = 0.38f;
	private static final float PUSHOUT_RADIUS_RATIO = 0.02f;
	private static final float ANGLE_SHIFT = 247.5f;
	private static final int INVENTORY_ARCS = ModScreenHandlers.SWITCHER_INVENTORY_SIZE;

	float universalScreenScale;

	private MatrixStack matrixStack;

	public RadialAnimationOptions animationOptions = new RadialAnimationOptions(0.04f, 0.08f);

	public ApplicatorInventory inventory;

	public ApplicatorSelectScreen(ColorOptions colorOptions, float forgivenessRatio, ApplicatorInventory inv) {
		super(Text.of(""));
		MinecraftClient client = MinecraftClient.getInstance();
		super.init(client, width, height);
		this.colorOptions = colorOptions;
		this.forgivenessRatio = forgivenessRatio;
		this.inventory = inv;

		client.player.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.MASTER, 1, 1f);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) 
	{

		universalScreenScale = (float) (Math.sqrt((client.currentScreen.width*client.currentScreen.height))/320.12f);
		matrixStack = matrices;
		//* THIS LOOPS!!!!!!!
		// renderBackground(matrices);
		// this dynamically updates
		float heightFloat = (float) height;
		currentInnerRadius = heightFloat*INNER_RADIUS_RATIO;
		currentOuterRadius = heightFloat*OUTER_RADIUS_RATIO;
		currentPushoutRadius = heightFloat*PUSHOUT_RADIUS_RATIO;

		/* --------------------------- Add Arcs to Buffer --------------------------- */

		// degree dist from arc start to arc end
		
		// determines how many vertices make up the arc

		/* ----------------------------- Mouse Detection ---------------------------- */

		int mouseInArc = mouseInSector(mouseX, mouseY);

		/* -------------------------------------------------------------------------- */


		/* --------------------------------- BG DRAW -------------------------------- */
		// fadeBackground(positionMatrix);
		/* -------------------------------------------------------------------------- */

		ItemStack selectedItem = ItemStack.EMPTY;
		Text centerText = Text.literal("None").setStyle(Style.EMPTY.withColor(Formatting.WHITE));
		if (selectedArc != -1)
		{
			selectedItem = inventory.getStack(selectedArc);
			if (selectedItem != ItemStack.EMPTY)
			{
				Formatting rarityColor = ApplicatorHelper.getRarityColor(selectedItem.getRarity());
				centerText = selectedItem.getName().copy().setStyle(Style.EMPTY.withColor(rarityColor));
			}
		}
		/* -------------------------------- ARC DRAW -------------------------------- */
		for (int subIndex = 0; subIndex < arcIndex+1; subIndex++)
		{
			// this code will repeat anyway
			boolean selected = false;

			// if the mouse changed the area/arc it's hovering over
			if (mouseInArc != selectedArc)
			{
				animationOptions.setSnapshotDelta(animationOptions.getTotalSecs());
				selectedArc = mouseInArc;
				onSectorChange(selectedArc);
			}

			// if the current index corresponds to the area/arc the mouse is targeting
			if (subIndex == mouseInArc)
				selected = true;
			
			float degStep = 360/INVENTORY_ARCS;
			float degStart = (float) (subIndex*degStep);
			float degEnd = (float) (degStart+degStep);

			RadialArcVisuals visuals = new RadialArcVisuals(inventory.getStack(subIndex), colorOptions, animationOptions, matrixStack);
			RadialArcData data = new RadialArcData(currentInnerRadius, currentOuterRadius, currentPushoutRadius, subIndex, selected);
			RadialArcAngularData angularData = new RadialArcAngularData(5f, degStart+ANGLE_SHIFT, degEnd+ANGLE_SHIFT);
			RadialArc arc = new RadialArc(visuals, data, angularData);

			arc.drawArcWithItem(client, 2, getArcAnimationCompletion(subIndex), getElapsedDeltaTime());

			// drawCircleSector(subIndex, selected);
		}
		//* TOTAL DELTA IS INVOLVED IN FADING. REMOVE FIRST PART IF FADING IS UNWANTED. */
		if (
			(animationOptions.getTotalSecs() >= (arcIndex+1)*animationOptions.getSecsToRenderNextArc()) && 
			arcIndex < INVENTORY_ARCS-1
		)
		{
			arcIndex++;
		}
		/* -------------------------------------------------------------------------- */

		/* ---------------------------- CENTER TEXT DRAW ---------------------------- */

		drawCenterText(centerText);
		/* -------------------------------------------------------------------------- */

		/* ----------------------------- DELTA INCREMENT ---------------------------- */
		animationOptions.setTotalSecs(animationOptions.getTotalSecs()+(delta/20));
		/* -------------------------------------------------------------------------- */


		RenderSystem.enableCull();
		RenderSystem.disableBlend();
	}

	// public void drawCircleSector(int subIndex, boolean selected)
	// {

	// 	/* ------------------------------ Elapsed Time ------------------------------ */
	// 	float elapsedTime = getElapsedDeltaTime();
	// 	float elapsedMult = Math.min(1, 8*elapsedTime);
	// 	/* -------------------------------------------------------------------------- */


	// 	// saved here so the item renderer can use it (and so if statement isn't spammed)
	// 	/* ------------------------------- Arc Pushout ------------------------------ */
	// 	float arcPushoutPercentage = 0f;
	// 	AlphaRGB sectorColor = colorOptions.getAppropriateColorCopy(false);
	// 	if (selected)
	// 	{
	// 		arcPushoutPercentage = 0.1f;
	// 		// towards 0 = unselected, towards 1 = selected
	// 		sectorColor = colorOptions.linearMix(elapsedMult);
	// 	}
	// 	sectorColor.setAlpha(getAnimatedAlpha(sectorColor.getAlpha(), subIndex));
	// 	/* -------------------------------------------------------------------------- */
		
	// 	/* ------------------------------ Item Pushout ------------------------------ */
	// 	float elapsedSelectMult = (1+(elapsedMult*arcPushoutPercentage));
	// 	/* -------------------------------------------------------------------------- */

	// 	ShaderFunctions.applyArcShaders(sectorColor);

	// 	Tessellator tessellator = Tessellator.getInstance();
	// 	BufferBuilder bufferBuilder = tessellator.getBuffer();
	// 	bufferBuilder.begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
		
	// 	// is a constant
	// 	float midRadians = (float) Math.toRadians(arcData.getDegStart()+arcData.getDegEnd())/2;
	// 	// center vec for item rendering
	// 	Vec2f itemVec = new Vec2f((float)Math.cos(midRadians), (float)(Math.sin(midRadians)));

	// 	// project from the center of the arc; that is, based on the center of the start and end angles
	// 	// also a constant offset
	// 	Vec2f vecOffset = new Vec2f(
	// 			(float) (currentPushoutRadius*Math.cos(midRadians)), 
	// 			(float) (currentPushoutRadius*Math.sin(midRadians))
	// 	);

	// 	float offsetInnerRadius = currentPushoutRadius+currentInnerRadius;
	// 	float offsetOuterRadius = currentPushoutRadius+currentOuterRadius;

	// 	// for animation - goes from 0 to 1
	// 	/* -------------------------- Multiplier Animation -------------------------- */
	// 	float multiplier = (float) Math.min(
	// 		1, (animationOptions.getTotalSecs()/(animationOptions.getSecsToRenderNextArc()*(subIndex+5)))
	// 	);
	// 	/* -------------------------------------------------------------------------- */

	// 	vecOffset = vecOffset.multiply(multiplier);
	// 	float radiusDist = multiplier*(offsetOuterRadius-offsetInnerRadius);

	// 	// these are defaulted here so that the item draw function can use these without needing to recalculate
	// 	for (float deg = arcData.getDegStart(); deg <= arcData.getDegEnd(); deg=de)
	// 	{

	// 		// convert the start angle to radians
	// 		double r = Math.toRadians(deg);

	// 		float innerCos = (float) (currentInnerRadius*Math.cos(r));
	// 		float innerSin = (float) (currentInnerRadius*Math.sin(r));
			
	// 		float outerCos = (float) (currentOuterRadius*Math.cos(r));
	// 		float outerSin = (float) (currentOuterRadius*Math.sin(r));


	// 		/* ---------------------------- Circle Correction --------------------------- */
	// 		Vec2f innerVertexVec = new Vec2f(innerCos, innerSin);
	// 		Vec2f outerVertexVec = new Vec2f(outerCos, outerSin);
			
	// 		Vec2f innerTransformedVec = innerVertexVec.add(vecOffset);
	// 		Vec2f outerTransformedVec = outerVertexVec.add(vecOffset);

	// 		Vec2f normalizedTransformVec = innerTransformedVec.normalize();

	// 		Vec2f finalVec = new Vec2f(normalizedTransformVec.x*offsetInnerRadius, normalizedTransformVec.y*offsetInnerRadius);

	// 		Vec2f vecDist = outerTransformedVec.add(innerTransformedVec.multiply(-1));
	// 		Vec2f normalizedDist = vecDist.normalize();
	// 		Vec2f finalDist = new Vec2f(normalizedDist.x*radiusDist, normalizedDist.y*radiusDist);

	// 		Vec2f finalOutVec = finalVec.add(finalDist);
	// 		/* -------------------------------------------------------------------------- */

	// 		/* -------------------- Alter Position If Sector Selected ------------------- */
	// 		finalVec = finalVec.multiply(elapsedSelectMult);
	// 		finalOutVec = finalOutVec.multiply(elapsedSelectMult);
	// 		/* -------------------------------------------------------------------------- */

	// 		Matrix4f posMatrix = matrixStack.peek().getPositionMatrix();
	// 		// outer radius vertices
	// 		bufferBuilder.vertex(posMatrix, (float) (width/2 + finalOutVec.x), (float) (height/2 + finalOutVec.y), 0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
	// 		// inner radius vertices
	// 		bufferBuilder.vertex(posMatrix, (float) (width/2 + finalVec.x), (float) (height/2 + finalVec.y), 0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();

	// 	}
	// 	BufferRenderer.drawWithShader(bufferBuilder.end());

	// 	Vec2f innerCenter = itemVec.multiply(offsetInnerRadius*elapsedSelectMult);
	// 	Vec2f outerCenter = itemVec.multiply((offsetInnerRadius+radiusDist)*elapsedSelectMult);
	// 	/* -------------------------- DRAW ITEM ICON IN ARC ------------------------- */
	// 	drawInventoryIcon(subIndex, innerCenter, outerCenter);
	// 	/* -------------------------------------------------------------------------- */
	// }

	private float getElapsedDeltaTime() {
		return animationOptions.getTotalSecs()-animationOptions.getSnapshotDelta();
	}

	private float getArcAnimationCompletion(int index)
	{
		return Math.min(animationOptions.getTotalSecs()/(animationOptions.getSecsToRenderNextArc()*(index+4)), 1);
	}

	// public void drawInventoryIcon(int subIndex, Vec2f corner1, Vec2f corner2)
	// {
	// 	Vec2f distVec = corner2.add(corner1.multiply(-1));
	// 	Vec2f midArcVec = corner1.add(distVec.multiply(0.5f));

	// 	// these two are so that the item will get larger with a selected arc (because the radius expands)
	// 	float standardDist = currentOuterRadius-currentInnerRadius;
	// 	float itemScaleMult = distVec.length()/standardDist;

	// 	// item info
	// 	ItemStack arcItem = inventory.getStack(subIndex);
	// 	if (arcItem == ItemStack.EMPTY)
	// 		return;

	// 	// set shaders
	// 	float renderMult = Math.min(animationOptions.getTotalSecs()/(animationOptions.getSecsToRenderNextArc()*(subIndex+4)), 1);
	// 	AlphaRGB shaderColor = new AlphaRGB(1.0f, 1.0f, 1.0f, 1.0f);
	// 	ShaderFunctions.applyItemShaders(shaderColor);
	// 	// texture size is always 16
	// 	float halfPixels = 8f;

	// 	ExtendedItemRenderer extendedRenderer = new ExtendedItemRenderer(client.getItemRenderer());
		
	// 	extendedRenderer.renderGuiItemIconWithScale(arcItem, 2*renderMult*universalScreenScale*itemScaleMult, (int)(width/2 + midArcVec.x - halfPixels), (int)(height/2 + midArcVec.y - halfPixels));

	// 	ShaderFunctions.revertItemShaders();
	// }

	// public void fadeBackground(Matrix4f positionMatrix)
	// {
	// 	AlphaRGB bgColor = new AlphaRGB(150,150, 150, 0.6f);

	// 	Tessellator tessellator = Tessellator.getInstance();
	// 	BufferBuilder bufferBuilder = tessellator.getBuffer();
	// 	bufferBuilder.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

	// 	float totalFadeTime = (animationOptions.getSecsFadeIn()*INVENTORY_ARCS)/2;
	// 	float alpha = linearFadeFromDelta(bgColor.getAlpha(), totalFadeTime);

	// 	bufferBuilder.vertex(positionMatrix, 0f, 0f, 0f).color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), alpha).next();
	// 	bufferBuilder.vertex(positionMatrix, width, 0f, 0f).color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), alpha).next();
	// 	bufferBuilder.vertex(positionMatrix, width, height, 0f).color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), alpha).next();
	// 	bufferBuilder.vertex(positionMatrix, 0f, height, 0f).color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), alpha).next();

	// 	BufferRenderer.drawWithShader(bufferBuilder.end());
	// }

	public void drawCenterText(Text text)
	{
		float matrixScale = 1.5f*universalScreenScale;
		
		// System.out.println(client.currentScreen.height);

		float maxWidth = 60;
		float textWidth = client.textRenderer.getWidth(text);

		float textWidthProportionOfMaxWidth = Math.max(1, textWidth/maxWidth);
		matrixScale = matrixScale/textWidthProportionOfMaxWidth;

		float halfWidth = textWidth/2;
		float halfHeight = 4;

		matrixStack.scale(matrixScale, matrixScale, matrixScale);
		client.textRenderer.drawWithShadow(matrixStack, text, width/(2*matrixScale)-halfWidth, height/(2*matrixScale)-halfHeight, 1);
		matrixStack.scale(1/matrixScale, 1/matrixScale, 1/matrixScale);
	}

	

	/**
	 * Get the integer number of what arc/sector the cursor is in.
	 * @param mouseX Mouse X location
	 * @param mouseY Mouse Y location
	 * @return The integer number of what arc/sector the cursor is in.
	 */
	public int mouseInSector(int mouseX, int mouseY)
	{
		// vector from screen corner
		Vec2f mouseVector = new Vec2f(mouseX, mouseY);

		// vector from screen center
		Vec2f centerMouseVec = new Vec2f(mouseVector.x-(width/2), mouseVector.y-(height/2));
		float mouseVecLen = centerMouseVec.length();

		if (
			mouseVecLen > ((currentInnerRadius/forgivenessRatio)+currentPushoutRadius) &&
			mouseVecLen < ((currentOuterRadius*Math.sqrt(forgivenessRatio))+currentPushoutRadius)
		)
		{
			float cosTheta = (centerMouseVec.x/mouseVecLen);
			double theta = Math.acos(cosTheta);
			
			int sign = (int) Math.signum(centerMouseVec.y);

			if (sign < 0)
			{
				theta = Math.toDegrees(
					2*Math.PI-theta
				);
			} else {
				theta = Math.toDegrees(theta);
			}

			float proportion = (float) theta/360;
			float propOffset = (float) (360-ANGLE_SHIFT)/360;
			float totalCoverage = proportion+propOffset;
			int index = (int) (totalCoverage*INVENTORY_ARCS)%INVENTORY_ARCS;
			return index;
		}

		return -1;
	}

	private void onSectorChange(int selectedArc)
	{
		client.player.playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.MASTER, 1, 0f);
	}

	public float linearFadeFromDelta(float totalAlpha, float secondsToCompletion)
	{
		return Math.min(totalAlpha, (totalAlpha*animationOptions.getTotalSecs())/(secondsToCompletion));
	}

	public float getAnimatedAlpha(float totalAlpha, int sectorIndex)
	{
		return Math.min(totalAlpha, (totalAlpha*animationOptions.getTotalSecs())/(animationOptions.getSecsFadeIn()*(sectorIndex+1)));
	}

	@Override
	public boolean shouldPause() {
		return false;
	}
	
}
