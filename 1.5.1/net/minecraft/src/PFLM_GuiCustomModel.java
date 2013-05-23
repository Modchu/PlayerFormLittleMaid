package net.minecraft.src;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class PFLM_GuiCustomModel extends PFLM_GuiSlotBase {
	protected int xSize_lo;
	protected int ySize_lo;
	protected float Size_lo;
	protected World popWorld;
	protected PFLM_EntityPlayerDummy drawEntity = null;
	private final File cfgdir = new File(Minecraft.getMinecraftDir(), "/config/CustomModel/");
	public GuiScreen parentScreen;
	private PFLM_GuiSlot partsSlot;
	private PFLM_GuiSlot partsDetailSlot;
	private PFLM_GuiSlot addChildNameSlot;
	private PFLM_GuiSlot textureNameSlot;
	private PFLM_GuiTextField[] inputStringBox;
	private int editBoxCount;
	private int editBoxType;
	private int[] editInt;
	private float[] editFloat;
	private int textureNameSlotSelected;
	private static List addButtonList = new LinkedList();
	private static ArrayList<String> textureNameList = new ArrayList<String>();
	private static ArrayList<String> modelRendererNameList = new ArrayList<String>();
	private static LinkedList<String> partsDetailSlotDrawString = new LinkedList<String>();
	private static int maidColor;
	private static int partsSlotSelected;
	private static int partsDetailSlotSelected;
	private static int addChildNameSlotSelected;
	private static int partsDetailSlotDrawStringInitNunmer;
	private static int customNumber;
	private static float modelScale;
	private static float editBoxSizeY;
	private static float editBoxSizeX;
	private static boolean drawEntityInitFlag;
	private static boolean isEdit;
	private static boolean partsDetailSlotDrawStringInitFlag;
	private static boolean editBoxInitFlag;
	private static String texture;
	private static String textureName;
	private static String textureArmorName;

	public PFLM_GuiCustomModel(GuiScreen par1GuiScreen, World world) {
		popWorld = world;
		parentScreen = par1GuiScreen;
		if (modelScale == 0.0F) modelScale = 0.9375F;
		partsSlotSelected = -1;
		partsDetailSlotSelected = -1;
		addChildNameSlotSelected = -1;
		partsDetailSlotDrawStringInitNunmer = -1;
		customNumber = 1;
		if (drawEntity != null) drawEntity.textureModel = null;
		drawEntityInitFlag = false;
		isEdit = false;
		partsDetailSlotDrawStringInitFlag = false;
		editBoxInitFlag = false;
		addButtonList.clear();
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.addAll(addButtonList);
		int x = width / 2;
		int y = height / 2;
		buttonList.add(new PFLM_GuiSmallButton(200, width - 160, 10, 60, 15, "Save"));
		buttonList.add(new PFLM_GuiSmallButton(201, width - 100, 10, 60, 15, "Return"));
		buttonList.add(new PFLM_GuiSmallButton(205, width - 160, 25, 60, 15, "DeleteBox"));
		buttonList.add(new PFLM_GuiSmallButton(204, width - 100, 25, 60, 15, "AddBox"));
		buttonList.add(new PFLM_GuiSmallButton(202, 10, 10, 60, 15, "AddParts"));
		buttonList.add(new PFLM_GuiSmallButton(203, 70, 10, 60, 15, "Delete"));
		if (partsSlot != null) {
			partsSlot.init(mc, this, 100, 15, 30, 30, 0);
		} else partsSlot = new PFLM_GuiSlot(mc, this, 100, 15, 30, 30, 0);
		if (partsDetailSlot != null) {
			partsDetailSlot.init(mc, this, 200, 15, width - 160, 60, 1);
		} else partsDetailSlot = new PFLM_GuiSlot(mc, this, 200, 15, width - 160, 60, 1);
	}

	@Override
	public void updateScreen() {
		try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(!guibutton.enabled) return;
		//Save
		if(guibutton.id == 200) {
			File file = new File(cfgdir, (new StringBuilder()).append("CustomModel_").append(customNumber).append(".cfg").toString());
			((MultiModelCustom) drawEntity.textureModel[0]).customModel.save(file);
			mc.displayGuiScreen(null);
			return;
		}
		//Return
		if(guibutton.id == 201) {
			mc.displayGuiScreen(new PFLM_Gui(popWorld));
			return;
		}
		//addParts
		if(guibutton.id == 202) {
			((MultiModelCustom) drawEntity.textureModel[0]).customModel.addParts();
		}
		//delete
		if(guibutton.id == 203) {
			((MultiModelCustom) drawEntity.textureModel[0]).customModel.deleteParts(partsSlotSelected);
			Modchu_Debug.mDebug("((MultiModelCustom) drawEntity.textureModel[0]).customModel.parts.length="+((MultiModelCustom) drawEntity.textureModel[0]).customModel.parts.length);
			if (((MultiModelCustom) drawEntity.textureModel[0]).customModel.parts.length == 0) {
				partsDetailSlotDrawString.clear();
				//partsDetailSlot = null;
				initGui();
			}
		}
		if(guibutton.id == 202
				| guibutton.id == 203) {
			isEdit = false;
			editBoxInitFlag = false;
			addChildNameSlot = null;
			inputStringBox = null;
			allPartsReInitSetting();
			return;
		}
		//addBox
		if(guibutton.id == 204) {
			((MultiModelCustom) drawEntity.textureModel[0]).customModel.addBox(partsSlotSelected);
		}
		//deleteBox
		if(guibutton.id == 205) {
			((MultiModelCustom) drawEntity.textureModel[0]).customModel.deleteBox(partsSlotSelected);
		}
		if(guibutton.id == 204
				| guibutton.id == 205) {
			isEdit = false;
			editBoxInitFlag = false;
			addChildNameSlot = null;
			inputStringBox = null;
			//saveTempCustomModel();
			return;
		}
		//BoxType
		if(guibutton.id > 999
				&& guibutton.id < 1999) {
			int i1 = guibutton.id - 1000;
			((MultiModelCustom) drawEntity.textureModel[0]).customModel.boxType[partsSlotSelected][i1] =
					(byte) ((((MultiModelCustom) drawEntity.textureModel[0]).customModel.boxType[partsSlotSelected][i1] + 1 < ((MultiModelCustom) drawEntity.textureModel[0]).customModel.maxboxType) ?
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.boxType[partsSlotSelected][i1] + 1 : 0);
			editBoxInitFlag = false;
			addChildNameSlot = null;
			inputStringBox = null;
			saveTempCustomModel();
		}
		//partsSlot.actionPerformed(guibutton);
	}

	private void allPartsReInitSetting() {
		for (int i = 0; i < ((MultiModelCustom) drawEntity.textureModel[0]).customModel.parts.length ;i++) {
			((MultiModelCustom) drawEntity.textureModel[0]).customModel.parts[i].clearCubeList();
			((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsInitSetting(i, 0.0F);
		}
	}

	private void saveTempCustomModel() {
		((MultiModelCustom) drawEntity.textureModel[0]).customModel.parts[partsSlotSelected].clearCubeList();
		((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsInitSetting(partsSlotSelected, 0.0F);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		if (partsSlot != null) partsSlot.drawScreen(i, j, f);
		if (partsDetailSlot != null) partsDetailSlot.drawScreen(i, j, f);
		if (addChildNameSlot != null) addChildNameSlot.drawScreen(i, j, f);
		if (textureNameSlot != null) textureNameSlot.drawScreen(i, j, f);
		if (inputStringBox != null) {
			for(int i1 = 0; i1 < inputStringBox.length; i1++) {
				if (inputStringBox[i1] != null) {
					inputStringBox[i1].drawTextBox();
				}
			}
		}
		if (textureNameSlot != null) textureNameSlot.clickDecision(i, j, f);
		if (addChildNameSlot != null) addChildNameSlot.clickDecision(i, j, f);
		if (partsDetailSlot != null) partsDetailSlot.clickDecision(i, j, f);
		if (partsSlot != null) partsSlot.clickDecision(i, j, f);
		//GL11.glPushMatrix();
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//GL11.glShadeModel(GL11.GL_FLAT);
		//GL11.glEnable(GL11.GL_ALPHA_TEST);
		//GL11.glDisable(GL11.GL_BLEND);
		//GL11.glDisable(GL11.GL_LIGHTING);
		//GL11.glDisable(GL11.GL_FOG);
		//GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		//GL11.glDisable(2903 /*GL_COLOR_MATERIAL*/);
		super.drawScreen(i, j, f);
		drawGuiContainerBackgroundLayer(f, i, j);
		//GL11.glPopMatrix();
		xSize_lo = i;
		ySize_lo = j;
		Size_lo = f;
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glPushMatrix();
		fontRenderer.drawString("CustomModel Setting", width / 2 - 50, 10, 0xffffff);
		StringBuilder s = (new StringBuilder()).append("Edit Custom Number : ");
		s = s.append(customNumber);
		fontRenderer.drawString(s.toString(), width / 2 - 80, 20, 0xffffff);
		if (drawEntity == null) drawEntity = new PFLM_EntityPlayerDummy(popWorld);
		if (!drawEntityInitFlag) {
			setTextureValue();
			((EntityLiving) drawEntity).texture = texture;
			drawEntity.maidColor = maidColor;
			drawEntity.textureName = textureName;
			drawEntity.textureArmorName = textureArmorName;
			drawEntity.modelScale = modelScale;
			drawEntity.showArmor = false;
			drawEntity.others = true;
			drawEntity.setPosition(mc.thePlayer.posX , mc.thePlayer.posY, mc.thePlayer.posZ);
			drawEntityInitFlag = true;
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = width / 2 - 90;
		int i1 = height / 2 - 35;
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
		GL11.glTranslatef(l + 51 , i1 + 155, 50F);
		float f1 = 50F;
		GL11.glScalef(-f1, f1, f1);
		GL11.glRotatef(180F, 180F, 0.0F, 1.0F);
		float f2 = mc.thePlayer.renderYawOffset;
		float f3 = mc.thePlayer.rotationYaw;
		float f4 = mc.thePlayer.rotationPitch;
		float f5 = (float)l - (float)xSize_lo;
		float f6 = (float)i1 - (float)ySize_lo;
		//GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		//GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-(float)Math.atan(f5 / 40F) * 90F, 0.0F, 1.0F, 0.0F);
		((EntityLiving) drawEntity).renderYawOffset = (float)Math.atan(f5 / 40F) * 20F;
		((EntityLiving) drawEntity).rotationYaw = (float)Math.atan(f5 / 40F) * 40F;
		((EntityLiving) drawEntity).rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
		GL11.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180F;
		RenderManager.instance.renderEntityWithPosYaw(drawEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		mc.thePlayer.renderYawOffset = f2;
		mc.thePlayer.rotationYaw = f3;
		mc.thePlayer.rotationPitch = f4;
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glDisable(2903 /*GL_COLOR_MATERIAL*/);
		//Modchu_CustomModel model = textureModel[0].customModel;
		//Modchu_Debug.mDebug("drawGuiContainerBackgroundLayer model.partsName="+model.partsName);
		GL11.glPopMatrix();
	}

	public void drawEditScreen(int showSelectionBoxLeft, int showSelectionBoxTop) {
		//GL11.glPushMatrix();
		showSelectionBoxTop = showSelectionBoxTop - 10;
		editBoxSizeX = 160F;
		editBoxSizeY = height - showSelectionBoxTop;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(0x22666666, 255);
		tessellator.addVertexWithUV((double)showSelectionBoxLeft, (double)showSelectionBoxTop + editBoxSizeY, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV((double)showSelectionBoxLeft + editBoxSizeX, (double)showSelectionBoxTop + editBoxSizeY, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV((double)showSelectionBoxLeft + editBoxSizeX, (double)showSelectionBoxTop, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV((double)showSelectionBoxLeft, (double)showSelectionBoxTop, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		editDrawString(showSelectionBoxLeft, showSelectionBoxTop);
		//GL11.glPopMatrix();
	}

	private void editBoxSetting(int showSelectionBoxLeft, int showSelectionBoxTop) {
		inputStringBox[0].xPos = (int) showSelectionBoxLeft + 32;
		inputStringBox[0].yPos = (int) showSelectionBoxTop + 5;
		inputStringBox[1].xPos = (int) showSelectionBoxLeft + 100;
		inputStringBox[1].yPos = (int) showSelectionBoxTop + 5;
		int j = 9;
		int k = 0;
		boolean flag = false;
		PFLM_GuiSmallButton button;
		if (((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected] > 0) {
			for (int i = 0; i < ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected] ;i++) {
				button = (PFLM_GuiSmallButton) addButtonList.get(i);
				button.xPosition = (int) showSelectionBoxLeft + 60;
				button.yPosition = (int) showSelectionBoxTop + 53 + k;
				inputStringBox[j].xPos = (int) showSelectionBoxLeft + 22;
				inputStringBox[j].yPos = (int) showSelectionBoxTop + 35 + k;
				inputStringBox[j + 1].xPos = (int) showSelectionBoxLeft + 90;
				inputStringBox[j + 1].yPos = (int) showSelectionBoxTop + 35 + k;
				inputStringBox[j + 2].xPos = (int) showSelectionBoxLeft + 22;
				inputStringBox[j + 2].yPos = (int) showSelectionBoxTop + 83 + k;
				inputStringBox[j + 3].xPos = (int) showSelectionBoxLeft + 22;
				inputStringBox[j + 3].yPos = (int) showSelectionBoxTop + 98 + k;
				inputStringBox[j + 4].xPos = (int) showSelectionBoxLeft + 22;
				inputStringBox[j + 4].yPos = (int) showSelectionBoxTop + 113 + k;
				inputStringBox[j + 5].xPos = (int) showSelectionBoxLeft + 90;
				inputStringBox[j + 5].yPos = (int) showSelectionBoxTop + 83 + k;
				inputStringBox[j + 6].xPos = (int) showSelectionBoxLeft + 90;
				inputStringBox[j + 6].yPos = (int) showSelectionBoxTop + 98 + k;
				inputStringBox[j + 7].xPos = (int) showSelectionBoxLeft + 90;
				inputStringBox[j + 7].yPos = (int) showSelectionBoxTop + 113 + k;
				flag = i + 1 != ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected];
				if (((MultiModelCustom) drawEntity.textureModel[0]).customModel.boxType[partsSlotSelected][i] != 2) {
					inputStringBox[j + 8].xPos = (int) showSelectionBoxLeft + 22;
					inputStringBox[j + 8].yPos = (int) showSelectionBoxTop + 140 + k;
					inputStringBox[j + 9].xPos = (int) showSelectionBoxLeft + 90;
					inputStringBox[j + 9].yPos = (int) showSelectionBoxTop + 140 + k;
					j = j + 10;
				} else {
					j = j + 8;
				}
				if (flag) k += 125;
			}
		} else {
			k = -15;
		}
		inputStringBox[2].xPos = (int) showSelectionBoxLeft + 22;
		inputStringBox[2].yPos = (int) showSelectionBoxTop + 182 + k;
		inputStringBox[3].xPos = (int) showSelectionBoxLeft + 22;
		inputStringBox[3].yPos = (int) showSelectionBoxTop + 197 + k;
		inputStringBox[4].xPos = (int) showSelectionBoxLeft + 22;
		inputStringBox[4].yPos = (int) showSelectionBoxTop + 212 + k;
		inputStringBox[5].xPos = (int) showSelectionBoxLeft + 22;
		inputStringBox[5].yPos = (int) showSelectionBoxTop + 245 + k;
		inputStringBox[6].xPos = (int) showSelectionBoxLeft + 90;
		inputStringBox[6].yPos = (int) showSelectionBoxTop + 245 + k;
		inputStringBox[7].xPos = (int) showSelectionBoxLeft + 22;
		inputStringBox[7].yPos = (int) showSelectionBoxTop + 285 + k;
		inputStringBox[8].xPos = (int) showSelectionBoxLeft + 90;
		inputStringBox[8].yPos = (int) showSelectionBoxTop + 285 + k;
	}

	private void editDrawString(int showSelectionBoxLeft, int showSelectionBoxTop) {
		StringBuilder s = null;
		String s0 = null;
		//TextureWidth Height
		fontRenderer.drawString("TextureSize", (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 2, 0xffffff);
		fontRenderer.drawString("Width", (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 17, 0xffffff);
		fontRenderer.drawString("Height", (int) showSelectionBoxLeft + 68, (int) showSelectionBoxTop + 17, 0xffffff);
		//TextureOffsetX Y
		fontRenderer.drawString("TextureOffset", (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 35, 0xffffff);
		fontRenderer.drawString("X", (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 47, 0xffffff);
		fontRenderer.drawString("Y", (int) showSelectionBoxLeft + 80, (int) showSelectionBoxTop + 47, 0xffffff);
		//Box Plate Ball
		int k = 0;
		for (int i1 = 0; i1 < ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected]; i1++) {
			k = i1 * 105;
			s = new StringBuilder().append(i1).append(" BoxType : ");
			fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 62 + k, 0xffffff);
			switch(((MultiModelCustom) drawEntity.textureModel[0]).customModel.boxType[partsSlotSelected][i1]) {
			case 0:
				s0 = "addBox";
				break;
			case 1:
				s0 = "addPlate";
				break;
			case 2:
				s0 = "addBall";
				break;
			}
			fontRenderer.drawString(s0, (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 72 + k, 0xffffff);
			fontRenderer.drawString("point", (int) showSelectionBoxLeft + 22, (int) showSelectionBoxTop + 82 + k, 0xffffff);
			fontRenderer.drawString("size", (int) showSelectionBoxLeft + 90, (int) showSelectionBoxTop + 82 + k, 0xffffff);
			fontRenderer.drawString("X", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 95 + k, 0xffffff);
			fontRenderer.drawString("Y", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 110 + k, 0xffffff);
			fontRenderer.drawString("Z", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 125 + k, 0xffffff);
			fontRenderer.drawString("X", (int) showSelectionBoxLeft + 78, (int) showSelectionBoxTop + 95 + k, 0xffffff);
			fontRenderer.drawString("Y", (int) showSelectionBoxLeft + 78, (int) showSelectionBoxTop + 110 + k, 0xffffff);
			fontRenderer.drawString("Z", (int) showSelectionBoxLeft + 78, (int) showSelectionBoxTop + 125 + k, 0xffffff);

			if (((MultiModelCustom) drawEntity.textureModel[0]).customModel.boxType[partsSlotSelected][i1] != 2) {
				s = new StringBuilder().append("ScaleFactor  Correction");
				fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft, (int) showSelectionBoxTop + 140 + k, 0xffffff);
			}
			k = k + 15;
		}
		if (((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected] > 0)
			showSelectionBoxTop += k;
		//RotateAngleX Y Z
		fontRenderer.drawString("X", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 178, 0xffffff);
		fontRenderer.drawString("Y", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 193, 0xffffff);
		fontRenderer.drawString("Z", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 205, 0xffffff);
		s = (new StringBuilder()).append("RotateAngle");
		fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft, (int) showSelectionBoxTop + 165, 0xffffff);
		//Type
		fontRenderer.drawString("Texture", (int) showSelectionBoxLeft + 80, (int) showSelectionBoxTop + 220, 0xffffff);
		fontRenderer.drawString("ColorFixity", (int) showSelectionBoxLeft + 80, (int) showSelectionBoxTop + 230, 0xffffff);
		fontRenderer.drawString("Factor", (int) showSelectionBoxLeft + 90, (int) showSelectionBoxTop + 270, 0xffffff);
		fontRenderer.drawString("Correction", (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 270, 0xffffff);
		s = (new StringBuilder()).append("Type");
		fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 230, 0xffffff);
		s = (new StringBuilder()).append("Move");
		fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft + 90, (int) showSelectionBoxTop + 260, 0xffffff);
		fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 260, 0xffffff);

		//addChildName
		s = (new StringBuilder()).append("addChildName");
		fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 300, 0x55ffff);
		//textureName
		s = (new StringBuilder()).append("textureName");
		fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 535, 0x55ffff);
	}

	private void editBoxInit(int showSelectionBoxLeft, int showSelectionBoxTop) {
		Modchu_Debug.mDebug("editBoxInit");
		addChildNameSlot = null;
		textureNameSlot = null;
		inputStringBox = null;
		addButtonList.clear();
		//TextureWidth TextureHeight
		int inputStringBoxCount = 0;
		inputStringBox = new PFLM_GuiTextField[11 + (((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumberMax * 8)];
		inputStringBoxCount = 0;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureWidth[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureHeight[partsSlotSelected]);
		inputStringBoxCount++;
		//RotateAngleX Y Z
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleX[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleY[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleZ[partsSlotSelected]);
		inputStringBoxCount++;
		//addChildName
		addChildNameSlot = new PFLM_GuiSlot(mc, this, 100, 15, (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 5, 2);
		if (((MultiModelCustom) drawEntity.textureModel[0]).customModel.mainModeltextureName != null) {
			HashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(((MultiModelCustom) drawEntity.textureModel[0]).customModel.mainModel, ((MultiModelCustom) drawEntity.textureModel[0]).customModel.mainModeltextureName, 0);
			String s;
			String addChildName = ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partAddChildName[partsSlotSelected];
			if (modelRendererMap != null
					&& !modelRendererMap.isEmpty()) {
				Iterator<Entry<String, Field>> iterator = modelRendererMap.entrySet().iterator();
				Entry<String, Field> entry;
				modelRendererNameList.clear();
				while(iterator.hasNext()) {
					entry = iterator.next();
					s = entry.getKey();
					modelRendererNameListSetting(s);
				}
			}
			for (int i1 = 0; i1 < ((MultiModelCustom) drawEntity.textureModel[0]).customModel.parts.length; i1++) {
				s = ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName[i1];
				modelRendererNameListSetting(s);
			}
			if (addChildName != null) {
				for (int i1 = 0; i1 < modelRendererNameList.size(); i1++) {
					if (addChildName.equals(modelRendererNameList.get(i1))) {
						//Modchu_Debug.mDebug("addChildName="+addChildName+" modelRendererNameList.get(i1)="+modelRendererNameList.get(i1));
						addChildNameSlotSelected = i1;
					}
				}
			}
		}
		//Type
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsType[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureColor[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTypeFactor[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTypeCorrection[partsSlotSelected]);
		inputStringBoxCount++;
		//Box Plate Ball
		for (int i = 0; i < ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected] ;i++) {
			addButtonList.add(new PFLM_GuiSmallButton(1000 + i, 0, 0, 60, 13, "change"));
			//partsTextureOffsetX Y
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureOffsetX[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureOffsetY[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointX[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointY[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointZ[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxX[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxY[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxZ[partsSlotSelected][i]);
			inputStringBoxCount++;
			if (((MultiModelCustom) drawEntity.textureModel[0]).customModel.boxType[partsSlotSelected][i] != 2) {
				inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
				inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsScaleFactor[partsSlotSelected][i]);
				inputStringBoxCount++;
				inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
				inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsScaleCorrection[partsSlotSelected][i]);
				inputStringBoxCount++;
			}
		}
		//textureName
		textureNameSlot = new PFLM_GuiSlot(mc, this, 100, 15, (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 5, 3);
		List textures = mod_Modchu_ModchuLib.getTextureManagerTextures();
		Object ltb = null;
		String s = null;
		String s1 = ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureNameMap.get(partsSlotSelected);
		for (int i1 = 0; i1 < textures.size(); i1++) {
			ltb = textures.get(i1);
			s = mod_Modchu_ModchuLib.getTextureBoxFileName(ltb);
			textureNameList.add(s);
			if (s.equals(s1)) textureNameSlotSelected = i1;
		}
		initGui();
	}

	private boolean updateEditString(String s, int i) {
		if (s != null) ;else return false;
		boolean b = false;
		switch(i) {
		case 0:
			if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
				int i2 = inputStringBoxIntegerCheck(i, s, 0);
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureWidth[partsSlotSelected] = i2;
				b = true;
			}
			break;
		case 1:
			if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
				int i2 = inputStringBoxIntegerCheck(i, s, 0);
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureHeight[partsSlotSelected] = i2;
				b = true;
			}
			break;
		case 2:
			if (mod_Modchu_ModchuLib.floatCheck(s)) {
				float f = Float.valueOf(s);
				f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleX[partsSlotSelected] = f;
				b = true;
			}
			break;
		case 3:
			if (mod_Modchu_ModchuLib.floatCheck(s)) {
				float f = Float.valueOf(s);
				f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleY[partsSlotSelected] = f;
				b = true;
			}
			break;
		case 4:
			if (mod_Modchu_ModchuLib.floatCheck(s)) {
				float f = Float.valueOf(s);
				f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleZ[partsSlotSelected] = f;
				b = true;
			}
			break;
		case 5:
			if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
				int i2 = Integer.valueOf(s);
				i2 = inputStringBoxIntegerMinCheck(i, i2);
				if (i2 >= ((MultiModelCustom) drawEntity.textureModel[0]).customModel.maxTypeMode) i2 = ((MultiModelCustom) drawEntity.textureModel[0]).customModel.maxTypeMode - 1;
				s = ""+i2;
				if (mod_Modchu_ModchuLib.byteCheck(s))
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsType[partsSlotSelected] = Byte.valueOf(s);
				b = true;
			}
			break;
		case 6:
			if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
				int i2 = Integer.valueOf(s);
				i2 = i2 & 0xf;
				s = ""+i2;
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureColor[partsSlotSelected] = Byte.valueOf(s);
				b = true;
			}
			break;
		case 7:
			if (mod_Modchu_ModchuLib.floatCheck(s)) {
				float f = Float.valueOf(s);
				//f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTypeFactor[partsSlotSelected] = f;
				b = true;
			}
			break;
		case 8:
			if (mod_Modchu_ModchuLib.floatCheck(s)) {
				float f = Float.valueOf(s);
				//f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTypeCorrection[partsSlotSelected] = f;
				b = true;
			}
			break;
		}
		if (i > 8) {
			int j = i - 9;
			int i1 = j / 10;
			j = j % 10;
			switch(j) {
			case 0:
				if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 1);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureOffsetX[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 1:
				if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 1);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureOffsetY[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 2:
				if (mod_Modchu_ModchuLib.floatCheck(s)) {
					float f = Float.valueOf(s);
					f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointX[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			case 3:
				if (mod_Modchu_ModchuLib.floatCheck(s)) {
					float f = Float.valueOf(s);
					f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointY[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			case 4:
				if (mod_Modchu_ModchuLib.floatCheck(s)) {
					float f = Float.valueOf(s);
					f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointZ[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			case 5:
				if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 0);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxX[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 6:
				if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 0);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxY[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 7:
				if (mod_Modchu_ModchuLib.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 0);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxZ[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 8:
				if (mod_Modchu_ModchuLib.floatCheck(s)) {
					float f = Float.valueOf(s);
					//f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsScaleFactor[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			case 9:
				if (mod_Modchu_ModchuLib.floatCheck(s)) {
					float f = Float.valueOf(s);
					//f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsScaleCorrection[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			}
		}
		Modchu_Debug.mDebug("updateEditString s="+s+" i="+i);
		return b;
	}

	private int inputStringBoxIntegerCheck(int i, String s, int j) {
		int i2;
		if (mod_Modchu_ModchuLib.integerCheckInt(s) == 2) {
			long l = Long.valueOf(s);
			i2 = l < 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			inputStringBox[i].setText(""+i2);
		} else {
			i2 = Integer.valueOf(s);
			if (j < 1) i2 = inputStringBoxIntegerMinCheck(i, i2);
			if (j < 2) i2 = inputStringBoxIntegerMaxCheck(i, i2);
		}
		return i2;
	}

	private int inputStringBoxIntegerMinCheck(int i, int i2) {
		if (i2 < 0) {
			i2 = 0;
			inputStringBox[i].setText("0");
		}
		return i2;
	}

	private int inputStringBoxIntegerMaxCheck(int i, int i2) {
		if (i2 > Integer.MAX_VALUE) {
			i2 = Integer.MAX_VALUE;
			inputStringBox[i].setText(""+Integer.MAX_VALUE);
			Modchu_Debug.mDebug("inputStringBoxIntegerMaxCheck inputStringBox[i].getText()="+inputStringBox[i].getText());
		}
		return i2;
	}

	private float inputStringBoxFloatMinCheck(int i, float f) {
		if (f < Float.MIN_EXPONENT) {
			f = Float.MIN_EXPONENT;
			inputStringBox[i].setText(""+Float.MIN_EXPONENT);
		}
		return f;
	}

	private float inputStringBoxFloatMaxCheck(int i, float f) {
		if (f > Float.MAX_EXPONENT) {
			f = Float.MAX_EXPONENT;
			inputStringBox[i].setText(""+Float.MAX_EXPONENT);
		}
		return f;
	}

	private void modelRendererNameListSetting(String s) {
		boolean b;
		if (!modelRendererNameList.contains(s)) {
			b = false;
			if (((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName[partsSlotSelected] != null) ;else b = true;
			if (!b
					&& !((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName[partsSlotSelected].equals(s)) b = true;
			if (b) modelRendererNameList.add(s);
		}
	}

	private PFLM_GuiTextField newInputStringBox(int i, int i1, int i2, int i3) {
		PFLM_GuiTextField guiTextField = new PFLM_GuiTextField(fontRenderer, i, i1, i2, i3);
		guiTextField.setTextColor(0x02ffffff);
		guiTextField.setDisabledTextColour(-1);
		//PFLM_GuiTextField.setEnableBackgroundDrawing(false);
		guiTextField.setMaxStringLength(30);
		guiTextField.setEnabled(true);
		return guiTextField;
	}

	private float getEditBoxX() {
		return (float)width - 200;
	}

	private float getEditBoxY() {
		return 60F;
	}

	private float floatPlus(float f, float f1, float f2, float f3, int i) {
		f = f * (float) i;
		int i1 = (int) f;
		if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
			i1 = i1 + (int)(f2 * (float) i);
		} else {
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			i1 = i1 + (int)(f3 * (float) i);
			} else {
    			i1 = i1 + (int)(f1 * (float) i);
			}
		}
		f = (float)i1 / (float)i;
		return f;
	}

	protected void keyTyped(char par1, int par2) {
		boolean b = false;
		boolean updateFlag = false;
		if (inputStringBox != null) {
			String s = null;
			for(int i1 = 0; i1 < inputStringBox.length; i1++) {
				if (inputStringBox[i1] != null) {
					if (inputStringBox[i1].textboxKeyTyped(par1, par2)) {
						s = inputStringBox[i1].getText();
						if (updateEditString(s, i1)) updateFlag = true;
						b = true;
					}
				}
			}
		}
		if (!b) super.keyTyped(par1, par2);
		if (updateFlag) saveTempCustomModel();
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		if (inputStringBox != null) {
			for(int i = 0; i < inputStringBox.length; i++) {
				if (inputStringBox[i] != null) inputStringBox[i].mouseClicked(par1, par2, par3);
			}
		}
	}

	public void setTextureValue() {
		if (textureName == null) {
			textureName = "default_Custom1";
		}
		int i = getMaidColor();

		texture = mod_Modchu_ModchuLib.textureManagerGetTexture(textureName, i);
		if (texture == null) {
			int n = 0;
			for (; n < 16 && texture == null; n = n + 1) {
				i++;
				i = i & 0xf;
				setMaidColor(i);
				texture = mod_Modchu_ModchuLib.textureManagerGetTexture(textureName, i);
			}
			if (texture == null) {
				setNextTexturePackege(0);
				texture = mod_Modchu_ModchuLib.textureManagerGetTexture(textureName, i);
			}
		}

		if (drawEntity.textureModel != null) {
			Modchu_Debug.mDebug("drawEntity.textureModel != null");
		} else {
			Modchu_Debug.mDebug("drawEntity.textureModel == null textureName="+textureName);
			drawEntity.textureModel = mod_Modchu_ModchuLib.textureBoxModelsCheck(null, textureName, true, true);
		}

		//setArmorTextureValue();
	}

	public void setColorTextureValue() {
		if (textureName == null) {
			textureName = "default_Custom1";
		}
		int i = getMaidColor();
		String t = texture;
		texture = mod_Modchu_ModchuLib.textureManagerGetTexture(textureName, i);
		int n = 0;
		for (; n < 16 && texture == null; n = n + 1) {
			if (PFLM_Gui.colorReverse) {
				i--;
			} else {
				i++;
			}
			i = i & 0xf;
			setMaidColor(i);
			texture = mod_Modchu_ModchuLib.textureManagerGetTexture(textureName, i);
		}
		if (texture == null) {
			texture = t;
			return;
		}
	}

	public void setArmorTextureValue() {
		if (textureArmorName == null) {
			textureArmorName = mod_PFLM_PlayerFormLittleMaid.getArmorName(textureName);
			if (textureArmorName == null) {
				textureArmorName = "default_Custom1";
			}
		}
		Modchu_Debug.mDebug("textureArmorName="+textureArmorName);
		MultiModelCustom[] models = (MultiModelCustom[]) mod_Modchu_ModchuLib.textureBoxModelsCheck(null, textureArmorName, true, true);
		if (models != null) {
			if (models[1] != null) drawEntity.textureModel[1] = models[1];
			if (models[2] != null) drawEntity.textureModel[2] = models[2];
		} else {
			models = (MultiModelCustom[]) mod_Modchu_ModchuLib.textureBoxModelsCheck(null, "default_Custom1");
			if (models != null) {
				if (models[1] != null) drawEntity.textureModel[1] = models[1];
				if (models[2] != null) drawEntity.textureModel[2] = models[2];
			}
		}
	}

	public static void setNextTexturePackege(int i) {
		if (i == 0) {
			textureName = mod_Modchu_ModchuLib.textureManagerGetNextPackege(textureName,
							getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(textureName));
		}
		if (i == 1) {
			textureArmorName = mod_Modchu_ModchuLib.textureManagerGetNextArmorPackege(textureArmorName);
			Modchu_Debug.mDebug("setNextTexturePackege(int) textureArmorName="+textureArmorName);
		}
	}

	public static void setPrevTexturePackege(int i) {
		if (i == 0) {
			textureName =
					mod_Modchu_ModchuLib.textureManagerGetPrevPackege(textureName, getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(textureName));
		}
		if (i == 1) {
			textureArmorName = mod_Modchu_ModchuLib.textureManagerGetPrevArmorPackege(textureArmorName);
		}
	}

	public static int getMaidColor() {
		return maidColor;
	}

	public static void setMaidColor(int i) {
		maidColor = i & 0xf;
	}

	public static void setTextureName(String s) {
		textureName = s;
	}

	public static void setTextureArmorName(String s) {
		textureArmorName = s;
	}

	@Override
	public int getTop(int guiNumber) {
		switch(guiNumber) {
		case 0:
			return 30;
		case 1:
			return 60;
		case 2:
		case 3:
			return (int) getEditBoxY();
		}
		return 0;
	}

	@Override
	public int getBottom(int guiNumber) {
		switch(guiNumber) {
		case 0:
			return height - 30;
		case 1:
			return height - 10;
		case 2:
		case 3:
			return height;
		}
		return 0;
	}

	@Override
	public int getGuiSlotSize(int guiNumber) {
		if (drawEntity != null
				&& drawEntity.textureModel != null
				&& drawEntity.textureModel[0] != null
				&& ((MultiModelCustom) drawEntity.textureModel[0]).customModel != null) {
			if (guiNumber == 0
					&& ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName != null) {
				return ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName.length;
			}
			else if (guiNumber == 1) {
				int k = 0;
				if (partsSlotSelected > -1
						&& ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected] > 0)
					k += ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected] * 5;
				return partsSlotSelected > -1 ? 55 + k : -1;
			}
			else if (guiNumber == 2) {
				if (modelRendererNameList != null
						&& !modelRendererNameList.isEmpty()) return modelRendererNameList.size();
			}
			else if (guiNumber == 3) {
				if (textureNameList != null
						&& !textureNameList.isEmpty()) return textureNameList.size();
			}
		}
		return -1;
	}

	@Override
	public int getGuiSlotContentHeight(int guiNumber) {
		return getGuiSlotSize(guiNumber) * 15;
	}

	@Override
	public void guiSlotElementClicked(int guiNumber, int i, boolean flag) {
		//flag = true 左ダブルクリック
		//Edit中クリックロック
		//if (isEdit
				//&& guiNumber < 2) return;

		switch(guiNumber) {
		case 0:
			if (partsSlotSelected != i) {
				partsSlotSelected = i;
				editBoxInitFlag = false;
			}
			break;
		case 1:
/*
			partsDetailSlotSelected = i;
			if (!partsDetailSlotDrawStringInitFlag
					&& partsDetailSlotSelected > -1
					&& partsSlotSelected > -1) {
				partsDetailSlotDrawStringInit();
				isEdit = true;
			}
			if (partsSlotSelected == -1) isEdit = false;
*/
			break;
		case 2:
			if (addChildNameSlotSelected != i) {
				addChildNameSlotSelected = i;
				String s = modelRendererNameList.get(partsSlotSelected);
				HashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(((MultiModelCustom) drawEntity.textureModel[0]).customModel.mainModel, ((MultiModelCustom) drawEntity.textureModel[0]).customModel.mainModeltextureName, 0);
				if (modelRendererMap != null
						&& modelRendererMap.containsKey(s)) {
					Field f = modelRendererMap.get(s);
					try {
						MMM_ModelRenderer modelRenderer = (MMM_ModelRenderer) f.get(((MultiModelCustom) drawEntity.textureModel[0]).customModel.mainModel);
						if (modelRenderer != null) modelRenderer.addChild(((MultiModelCustom) drawEntity.textureModel[0]).customModel.parts[partsSlotSelected]);
					} catch (Exception e) {
					}
				}
			}
			break;
		case 3:
			if (textureNameSlotSelected != i) {
				textureNameSlotSelected = i;
				if (textureNameList != null
						&& textureNameList.contains(i)) {
					String s = textureNameList.get(i);
					if (s != null) ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureNameMap.put(i, s);
				}
			}
			break;
		}
	}

	@Override
	public void outOfRangeClick(int guiNumber, int mouse_x, int mouse_y, boolean flag) {
		switch(guiNumber) {
		case 0:
			//partsSlotSelected = -1;
			break;
		case 1:
			//if (flag) {
				//partsDetailSlotSelected = -1;
				//isEdit = false;
			//}
			break;
		case 2:
			//addChildNameSlotSelected = -1;
			break;
		case 3:
			//textureNameSlotSelected = -1;
			break;
		}
	}

	@Override
	public boolean guiSlotIsSelected(int guiNumber, int i) {
		int i1 = -1;
		switch(guiNumber) {
		case 0:
			i1 = partsSlotSelected;
			break;
		case 1:
			//i1 = partsDetailSlotSelected;
			break;
		case 2:
			i1 = addChildNameSlotSelected;
			break;
		case 3:
			i1 = textureNameSlotSelected;
			break;
		}
		if (i == i1) {
			return true;
		}
		return false;
	}

	@Override
	public void guiSlotDrawSlot(int guiNumber, int i, int showSelectionBoxLeft, int showSelectionBoxRight, int showSelectionBoxTop, int showSelectionBoxBottom, Tessellator tessellator) {
		//Modchu_Debug.mDebug("guiSlotDrawSlot guiNumber="+guiNumber);
		if (drawEntity != null
				&& drawEntity.textureModel != null
				&& drawEntity.textureModel[0] != null
				&& ((MultiModelCustom) drawEntity.textureModel[0]).customModel != null) {
			int k = 0;
			if (partsSlotSelected > -1
					&& ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected] > 0)
				k += ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelected] * 105 - 105;
			if (guiNumber == 0
					&& ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName != null
					&& i > -1) {
				String s0 = ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName[i];
				drawString(fontRenderer, s0, showSelectionBoxLeft + 5, showSelectionBoxTop + 2, 0xffffff);
			}
			else if (guiNumber == 1
					&& i > -1) {
				if (partsSlotSelected > -1
						&& i == (int) partsDetailSlot.amountScrolled) {
					showSelectionBoxTop = (int) -partsDetailSlot.amountScrolled * 15 + showSelectionBoxTop;
					//Modchu_Debug.mDebug("i="+i+" partsDetailSlot.amountScrolled="+partsDetailSlot.amountScrolled+" showSelectionBoxTop="+showSelectionBoxTop);
					if (!editBoxInitFlag) {
						editBoxInitFlag = true;
						editBoxInit(showSelectionBoxLeft, showSelectionBoxTop);
					}
					editBoxSetting(showSelectionBoxLeft, showSelectionBoxTop);
					drawEditScreen(showSelectionBoxLeft, showSelectionBoxTop);
				}
			}
			else if (guiNumber == 2) {
				addChildNameSlot.slotPosX = (int) getEditBoxX() + 50;
				addChildNameSlot.slotPosY = (int) -partsDetailSlot.amountScrolled * 15 + 380 + k;
				addChildNameSlot.scrollBarY = (int) -partsDetailSlot.amountScrolled * 15 + 330 + k;
				//Modchu_Debug.mDebug("addChildNameSlot.slotPosY="+addChildNameSlot.slotPosY);
				String s0 = null;
				if (modelRendererNameList != null
						&& !modelRendererNameList.isEmpty()
						&& i > -1) {
					s0 = modelRendererNameList.get(i);
					drawString(fontRenderer, s0, showSelectionBoxLeft + 5, showSelectionBoxTop + 2, 0xffffff);
				}
			}
			else if (guiNumber == 3) {
				textureNameSlot.slotPosX = (int) getEditBoxX() + 50;
				textureNameSlot.slotPosY = (int) -partsDetailSlot.amountScrolled * 15 + 610 + k;
				textureNameSlot.scrollBarY = (int) -partsDetailSlot.amountScrolled * 15 + 560 + k;
				String s0 = null;
				if (textureNameList != null
						&& !textureNameList.isEmpty()
						&& i > -1) {
					s0 = textureNameList.get(i);
					drawString(fontRenderer, s0, showSelectionBoxLeft + 5, showSelectionBoxTop + 2, 0xffffff);
				}
			}
		}
	}

	private String[] getPartsDetailSlotData(String t, String t1) {
		String[] s = new String[3];
		String s1 = null;
		String s2 = null;
		int i = t.lastIndexOf(t1);
		if(i > -1) {
			s1 = t.substring(0, i);
			s[2] = t.substring(i + 1);
			i = s1.lastIndexOf(t1);
			if(i > -1) {
				s[0] = s1.substring(0, i);
				s[1] = s1.substring(i + 1);
			}
			else s[1] = s[2];
		}
		else s[0] = t;
		return s;
	}

	@Override
	public int getSlotScrollBarX(int guiNumber) {
		switch(guiNumber) {
		case 0:
			return 15;
		case 1:
			return width - 15;
		case 2:
		case 3:
			return (int) getEditBoxX() + 42;
		}
		return 0;
	}

	@Override
	public int getSlotScrollBarSizeX(int guiNumber) {
		return 6;
	}
}
