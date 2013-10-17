package net.minecraft.src;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class PFLM_GuiCustomModel extends PFLM_GuiSlotBase {
	private final File cfgdir = new File(mod_Modchu_ModchuLib.modchu_Main.getMinecraftDir(), "/config/CustomModel/");
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
	private static boolean isEdit;
	private static boolean partsDetailSlotDrawStringInitFlag;
	private static boolean editBoxInitFlag;
	private static String textureName;
	private static String textureArmorName;

	public PFLM_GuiCustomModel(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		if (modelScale == 0.0F) modelScale = 0.9375F;
		partsSlotSelected = -1;
		partsDetailSlotSelected = -1;
		addChildNameSlotSelected = -1;
		partsDetailSlotDrawStringInitNunmer = -1;
		customNumber = 1;
		drawEntitySetFlag = true;
		isEdit = false;
		partsDetailSlotDrawStringInitFlag = false;
		editBoxInitFlag = false;
		addButtonList.clear();
		setTextureValue();
		mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.allModelInit(drawEntity, false);
		mod_PFLM_PlayerFormLittleMaid.pflm_main.changeColor((PFLM_EntityPlayerDummy)drawEntity);
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.addAll(addButtonList);
		int x = width / 2;
		int y = height / 2;
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 200, width - 160, 10, 60, 15, "Save" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 201, width - 100, 10, 60, 15, "Return" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 205, width - 100, 35, 60, 15, "DeleteBox" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 204, width - 160, 35, 60, 15, "AddBox" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 202, 10, 10, 60, 15, "AddParts" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 203, 70, 10, 60, 15, "Delete" }));
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
			((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.save(file);
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ null });
			//mc.displayGuiScreen(null);
			return;
		}
		//Return
		if(guibutton.id == 201) {
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_Gui(popWorld) });
			//mc.displayGuiScreen(new PFLM_Gui(popWorld));
			return;
		}
		//addParts
		if(guibutton.id == 202) {
			((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.addParts();
		}
		//delete
		if(guibutton.id == 203) {
			((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.deleteParts(partsSlotSelected);
			Modchu_Debug.mDebug("((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts.length="+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts.length);
			if (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts.length == 0) {
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
			((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.addBox(partsSlotSelected);
		}
		//deleteBox
		if(guibutton.id == 205) {
			((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.deleteBox(partsSlotSelected);
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
			((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.boxType[partsSlotSelected][i1] =
					(byte) ((((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.boxType[partsSlotSelected][i1] + 1 < ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.maxboxType) ?
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.boxType[partsSlotSelected][i1] + 1 : 0);
			editBoxInitFlag = false;
			addChildNameSlot = null;
			inputStringBox = null;
			saveTempCustomModel();
		}
		//partsSlot.actionPerformed(guibutton);
	}

	private void allPartsReInitSetting() {
		for (int i = 0; i < ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts.length ;i++) {
			((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts[i].clearCubeList();
			((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsInitSetting(i, 0.0F);
		}
	}

	private void saveTempCustomModel() {
		((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts[partsSlotSelected].clearCubeList();
		((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsInitSetting(partsSlotSelected, 0.0F);
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
		superDrawScreen(i, j, f);
		drawGuiContainerBackgroundLayer(f, i, j);
		//GL11.glPopMatrix();
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glPushMatrix();
		fontRenderer.drawString("CustomModel Setting", width / 2 - 50, 10, 0xffffff);
		StringBuilder s = (new StringBuilder()).append("Edit Custom Number : ");
		s = s.append(customNumber);
		fontRenderer.drawString(s.toString(), width / 2 - 80, 20, 0xffffff);
		if (drawEntitySetFlag) {
			setTextureValue();
			mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.allModelInit(drawEntity, false);
			drawEntity.setPosition(mc.thePlayer.posX , mc.thePlayer.posY, mc.thePlayer.posZ);
			drawEntitySetFlag = false;
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = width / 2 - 90;
		int i1 = height / 2 - 35;
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
		GL11.glTranslatef(l + 51 , i1 + 155, 50F);
		float f1 = 50F;
		GL11.glScalef(-f1, f1, f1);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		float f5 = (float)l - (float)i;
		float f6 = (float)i1 - (float)j;
		//GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
		if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
		if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender
				&& mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() < 160) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		//GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-(float)Math.atan(f5 / 40F) * 90F, 0.0F, 1.0F, 0.0F);
		((EntityLiving) drawEntity).renderYawOffset = (float)Math.atan(f5 / 40F) * 40F;
		((EntityLiving) drawEntity).rotationYaw = (float)Math.atan(f5 / 40F) * 40F;
		((EntityLiving) drawEntity).rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
		GL11.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180F;
		RenderManager.instance.renderEntityWithPosYaw(drawEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
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
		GuiButton button;
		if (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] > 0) {
			for (int i = 0; i < ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] ;i++) {
				button = (GuiButton) addButtonList.get(i);
				button.xPosition = (int) showSelectionBoxLeft + 60;
				button.yPosition = (int) showSelectionBoxTop + 63 + k;
				inputStringBox[j].xPos = (int) showSelectionBoxLeft + 22;
				inputStringBox[j].yPos = (int) showSelectionBoxTop + 45 + k;
				inputStringBox[j + 1].xPos = (int) showSelectionBoxLeft + 90;
				inputStringBox[j + 1].yPos = (int) showSelectionBoxTop + 45 + k;
				inputStringBox[j + 2].xPos = (int) showSelectionBoxLeft + 22;
				inputStringBox[j + 2].yPos = (int) showSelectionBoxTop + 93 + k;
				inputStringBox[j + 3].xPos = (int) showSelectionBoxLeft + 22;
				inputStringBox[j + 3].yPos = (int) showSelectionBoxTop + 108 + k;
				inputStringBox[j + 4].xPos = (int) showSelectionBoxLeft + 22;
				inputStringBox[j + 4].yPos = (int) showSelectionBoxTop + 123 + k;
				inputStringBox[j + 5].xPos = (int) showSelectionBoxLeft + 90;
				inputStringBox[j + 5].yPos = (int) showSelectionBoxTop + 93 + k;
				inputStringBox[j + 6].xPos = (int) showSelectionBoxLeft + 90;
				inputStringBox[j + 6].yPos = (int) showSelectionBoxTop + 108 + k;
				inputStringBox[j + 7].xPos = (int) showSelectionBoxLeft + 90;
				inputStringBox[j + 7].yPos = (int) showSelectionBoxTop + 123 + k;
				flag = i + 1 != ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected];
				if (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.boxType[partsSlotSelected][i] != 2) {
					inputStringBox[j + 8].xPos = (int) showSelectionBoxLeft + 22;
					inputStringBox[j + 8].yPos = (int) showSelectionBoxTop + 150 + k;
					inputStringBox[j + 9].xPos = (int) showSelectionBoxLeft + 90;
					inputStringBox[j + 9].yPos = (int) showSelectionBoxTop + 150 + k;
					j = j + 10;
				} else {
					j = j + 8;
				}
				if (flag) k += 145;
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
		//Box Plate Ball
		int k = 0;
		for (int i1 = 0; i1 < ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected]; i1++) {
			k = i1 * 145;
			s = new StringBuilder().append("Box Number ").append(i1);
			fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 35 + k, 0xffffff);
			fontRenderer.drawString("TextureOffset", (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 45 + k, 0xffffff);
			fontRenderer.drawString("X", (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 57 + k, 0xffffff);
			fontRenderer.drawString("Y", (int) showSelectionBoxLeft + 80, (int) showSelectionBoxTop + 57 + k, 0xffffff);
			s = new StringBuilder().append("BoxType : ");
			fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 72 + k, 0xffffff);
			switch(((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.boxType[partsSlotSelected][i1]) {
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
			fontRenderer.drawString(s0, (int) showSelectionBoxLeft + 5, (int) showSelectionBoxTop + 82 + k, 0xffffff);
			fontRenderer.drawString("point", (int) showSelectionBoxLeft + 22, (int) showSelectionBoxTop + 92 + k, 0xffffff);
			fontRenderer.drawString("size", (int) showSelectionBoxLeft + 90, (int) showSelectionBoxTop + 92 + k, 0xffffff);
			fontRenderer.drawString("X", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 105 + k, 0xffffff);
			fontRenderer.drawString("Y", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 120 + k, 0xffffff);
			fontRenderer.drawString("Z", (int) showSelectionBoxLeft + 9, (int) showSelectionBoxTop + 135 + k, 0xffffff);
			fontRenderer.drawString("X", (int) showSelectionBoxLeft + 78, (int) showSelectionBoxTop + 105 + k, 0xffffff);
			fontRenderer.drawString("Y", (int) showSelectionBoxLeft + 78, (int) showSelectionBoxTop + 120 + k, 0xffffff);
			fontRenderer.drawString("Z", (int) showSelectionBoxLeft + 78, (int) showSelectionBoxTop + 135 + k, 0xffffff);

			if (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.boxType[partsSlotSelected][i1] != 2) {
				s = new StringBuilder().append("ScaleFactor  Correction");
				fontRenderer.drawString(s.toString(), (int) showSelectionBoxLeft, (int) showSelectionBoxTop + 150 + k, 0xffffff);
			}
			k = k + 15;
		}
		if (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] > 0)
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
		//getTextureName()
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
		inputStringBox = new PFLM_GuiTextField[11 + (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumberMax * 10)];
		inputStringBoxCount = 0;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureWidth[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureHeight[partsSlotSelected]);
		inputStringBoxCount++;
		//RotateAngleX Y Z
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsRotateAngleX[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsRotateAngleY[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsRotateAngleZ[partsSlotSelected]);
		inputStringBoxCount++;
		//addChildName
		addChildNameSlot = new PFLM_GuiSlot(mc, this, 100, 15, (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 5, 2);
		if (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.mainModeltextureName != null) {
			ConcurrentHashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.mainModel, ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.mainModeltextureName, 0);
			String s;
			String addChildName = ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partAddChildName[partsSlotSelected];
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
			for (int i1 = 0; i1 < ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts.length; i1++) {
				s = ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsName[i1];
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
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsType[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureColor[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTypeFactor[partsSlotSelected]);
		inputStringBoxCount++;
		inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
		inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTypeCorrection[partsSlotSelected]);
		inputStringBoxCount++;
		//Box Plate Ball
		for (int i = 0; i < ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] ;i++) {
			addButtonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 1000 + i, 0, 0, 60, 13, "change" }));
			//partsTextureOffsetX Y
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureOffsetX[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureOffsetY[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxInitPointX[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxInitPointY[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 45, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxInitPointZ[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxX[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxY[partsSlotSelected][i]);
			inputStringBoxCount++;
			inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
			inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxZ[partsSlotSelected][i]);
			inputStringBoxCount++;
			if (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.boxType[partsSlotSelected][i] != 2) {
				inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
				inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsScaleFactor[partsSlotSelected][i]);
				inputStringBoxCount++;
				inputStringBox[inputStringBoxCount] = newInputStringBox(0, 0, 32, 12);
				inputStringBox[inputStringBoxCount].setText(""+((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsScaleCorrection[partsSlotSelected][i]);
				inputStringBoxCount++;
			}
		}
		//getTextureName()
		textureNameSlot = new PFLM_GuiSlot(mc, this, 100, 15, (int) showSelectionBoxLeft + 10, (int) showSelectionBoxTop + 5, 3);
		List textures = mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTextures();
		Object ltb = null;
		String s = null;
		String s1 = ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureNameMap.get(partsSlotSelected);
		for (int i1 = 0; i1 < textures.size(); i1++) {
			ltb = textures.get(i1);
			s = mod_Modchu_ModchuLib.modchu_Main.getTextureBoxFileName(ltb);
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
			if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
				int i2 = inputStringBoxIntegerCheck(i, s, 0);
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureWidth[partsSlotSelected] = i2;
				b = true;
			}
			break;
		case 1:
			if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
				int i2 = inputStringBoxIntegerCheck(i, s, 0);
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureHeight[partsSlotSelected] = i2;
				b = true;
			}
			break;
		case 2:
			if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
				float f = Float.valueOf(s);
				f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsRotateAngleX[partsSlotSelected] = f;
				b = true;
			}
			break;
		case 3:
			if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
				float f = Float.valueOf(s);
				f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsRotateAngleY[partsSlotSelected] = f;
				b = true;
			}
			break;
		case 4:
			if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
				float f = Float.valueOf(s);
				f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsRotateAngleZ[partsSlotSelected] = f;
				b = true;
			}
			break;
		case 5:
			if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
				int i2 = Integer.valueOf(s);
				i2 = inputStringBoxIntegerMinCheck(i, i2);
				if (i2 >= ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.maxTypeMode) i2 = ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.maxTypeMode - 1;
				s = ""+i2;
				if (mod_Modchu_ModchuLib.modchu_Main.byteCheck(s))
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsType[partsSlotSelected] = Byte.valueOf(s);
				b = true;
			}
			break;
		case 6:
			if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
				int i2 = Integer.valueOf(s);
				i2 = i2 & 0xf;
				s = ""+i2;
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureColor[partsSlotSelected] = Byte.valueOf(s);
				b = true;
			}
			break;
		case 7:
			if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
				float f = Float.valueOf(s);
				//f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTypeFactor[partsSlotSelected] = f;
				b = true;
			}
			break;
		case 8:
			if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
				float f = Float.valueOf(s);
				//f = inputStringBoxFloatMinCheck(i, f);
				f = inputStringBoxFloatMaxCheck(i, f);
				((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTypeCorrection[partsSlotSelected] = f;
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
				if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 1);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureOffsetX[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 1:
				if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 1);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureOffsetY[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 2:
				if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
					float f = Float.valueOf(s);
					f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxInitPointX[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			case 3:
				if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
					float f = Float.valueOf(s);
					f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxInitPointY[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			case 4:
				if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
					float f = Float.valueOf(s);
					f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxInitPointZ[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			case 5:
				if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 0);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxX[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 6:
				if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 0);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxY[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 7:
				if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) > 0) {
					int i2 = inputStringBoxIntegerCheck(i, s, 0);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxZ[partsSlotSelected][i1] = i2;
					b = true;
				}
				break;
			case 8:
				if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
					float f = Float.valueOf(s);
					//f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsScaleFactor[partsSlotSelected][i1] = f;
					b = true;
				}
				break;
			case 9:
				if (mod_Modchu_ModchuLib.modchu_Main.floatCheck(s)) {
					float f = Float.valueOf(s);
					//f = inputStringBoxFloatMinCheck(i, f);
					f = inputStringBoxFloatMaxCheck(i, f);
					((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsScaleCorrection[partsSlotSelected][i1] = f;
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
		if (mod_Modchu_ModchuLib.modchu_Main.integerCheckInt(s) == 2) {
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
			if (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsName[partsSlotSelected] != null) ;else b = true;
			if (!b
					&& !((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsName[partsSlotSelected].equals(s)) b = true;
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
		if (mod_Modchu_ModchuLib.modchu_Main.isCtrlKeyDown()) {
			i1 = i1 + (int)(f2 * (float) i);
		} else {
			if (mod_Modchu_ModchuLib.modchu_Main.isShiftKeyDown()) {
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
		if (getTextureName() != null
				&& !getTextureName().isEmpty()) ;else setTextureName("default_Custom1");
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, getTextureName());
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, getColor());
		setTextureArmorPackege(2);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, getTextureArmorName());
	}

	@Override
	public void setTextureArmorPackege(int i) {
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
		String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName), i);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
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
		if (PFLM_RenderPlayerDummyMaster.modelData != null
				&& PFLM_RenderPlayerDummyMaster.modelData.modelMain != null
				&& ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model) != null
				&& ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel != null) {
			if (guiNumber == 0
					&& ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsName != null) {
				return ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsName.length;
			}
			else if (guiNumber == 1) {
				int k = 0;
				if (partsSlotSelected > -1
						&& ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] > 0)
					k += ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] * 5;
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
			break;
		case 2:
			if (addChildNameSlotSelected != i
				&& modelRendererNameList.size() < addChildNameSlotSelected) {
				String s = modelRendererNameList.get(addChildNameSlotSelected);
				Modchu_ModelRenderer modelRenderer = getModelRenderer(s, ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.mainModeltextureName, ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.mainModel, 0);
				if (modelRenderer != null) {
					if (modelRenderer.childModels != null) modelRenderer.childModels.remove(((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts[partsSlotSelected]);
				}
				addChildNameSlotSelected = i;
				s = modelRendererNameList.get(addChildNameSlotSelected);
				modelRenderer = getModelRenderer(s, ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.mainModeltextureName, ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.mainModel, 0);
				if (modelRenderer != null) {
					modelRenderer.addChild(((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.parts[partsSlotSelected]);
				}
			}
			break;
		case 3:
			if (textureNameSlotSelected != i) {
				textureNameSlotSelected = i;
				if (textureNameList != null) {
					String s = textureNameList.get(i);
					Modchu_Debug.mDebug("textureNameSlotSelected="+textureNameSlotSelected+" s="+s);
					if (s != null) ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsTextureNameMap.put(partsSlotSelected, s);
				}
			}
			break;
		}
	}

	private Modchu_ModelRenderer getModelRenderer(String s, String s2, Object model, int i) {
		Modchu_ModelRenderer modelRenderer = null;
		ConcurrentHashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(model, s2, 0);
		if (modelRendererMap != null
				&& modelRendererMap.containsKey(s)) {
			Field f = modelRendererMap.get(s);
			try {
				modelRenderer = (Modchu_ModelRenderer) f.get(((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.mainModel);
			} catch (Exception e) {
			}
		}
		return modelRenderer;
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
		if (PFLM_RenderPlayerDummyMaster.modelData != null
				&& PFLM_RenderPlayerDummyMaster.modelData.modelMain != null
				&& ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model) != null
				&& ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel != null) {
			int k = 0;
			if (partsSlotSelected > -1
					&& ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] > 0)
				k += ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] * 145 - 145;
			if (guiNumber == 0
					&& ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsName != null
					&& i > -1) {
				String s0 = ((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsName[i];
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
				k += (((MultiModelCustom) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).customModel.partsBoxNumber[partsSlotSelected] + 1) * 50;
				textureNameSlot.slotPosX = (int) getEditBoxX() + 50;
				textureNameSlot.slotPosY = (int) -partsDetailSlot.amountScrolled * 15 + 510 + k;
				textureNameSlot.scrollBarY = (int) -partsDetailSlot.amountScrolled * 15 + 460 + k;
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

	@Override
	public int getLimitSelectedDisplayCount(int guiNumber) {
		switch(guiNumber) {
		case 2:
		case 3:
			return 10;
		}
		return -1;
	}

	@Override
	public String getTextureName() {
		return textureName;
	}

	@Override
	public void setTextureName(String s) {
		textureName = s;
	}

	@Override
	public String getTextureArmorName() {
		return textureArmorName;
	}

	@Override
	public void setTextureArmorName(String s) {
		textureArmorName = s;
	}

	@Override
	public int getColor() {
		return maidColor ;
	}

	@Override
	public void setColor(int i) {
		maidColor = i & 0xf;
	}

	public float getScale() {
		return 0.0F;
	}

	public void setScale(float f) {
	}

	@Override
	public void memoryRelease() {
	}

	@Override
	public void modelChange() {
	}
}
