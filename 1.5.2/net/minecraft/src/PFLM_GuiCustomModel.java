package net.minecraft.src;

import java.util.HashMap;
import java.util.LinkedList;

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
	public GuiScreen parentScreen;
	private Modchu_GuiSlot partsSlot;
	private Modchu_GuiSlot partsDetailSlot;
	private int editBoxType;
	private static LinkedList<String> partsDetailSlotDrawString = new LinkedList<String>();
	private static int maidColor;
	private static int partsSlotSelectedSelected;
	private static int partsDetailSlotSelectedSelected;
	private static int partsDetailSlotDrawStringInitNunmer = -1;
	private static int customNumber = 1;
	private static float modelScale;
	private static float editBoxX;
	private static float editBoxY;
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
		partsSlotSelectedSelected = -1;
		partsDetailSlotSelectedSelected = -1;
		if (drawEntity != null) drawEntity.textureModel = null;
		drawEntityInitFlag = false;
		isEdit = false;
		partsDetailSlotDrawStringInitFlag = false;
		editBoxInitFlag = false;
	}

	@Override
	public void initGui() {
		buttonList.clear();
		int x = width / 2;
		int y = height / 2;
		buttonList.add(new Modchu_GuiSmallButton(200, width - 160, 10, 75, 20, "Save"));
		buttonList.add(new Modchu_GuiSmallButton(201, width - 85, 10, 75, 20, "Return"));
		Modchu_Debug.mDebug("partsDetailSlotSelectedSelected="+partsDetailSlotSelectedSelected);
		if (partsDetailSlotSelectedSelected > -1) {
			buttonList.add(new Modchu_GuiSmallButton(300, width - 160, 30, 75, 20, !isEdit ? "Edit" : "Close"));
		}
		partsSlot = new Modchu_GuiSlot(mc, this, 100, 30, height - 30, 15, 30, 30, 0);
		partsDetailSlot = new Modchu_GuiSlot(mc, this, 200, 10, height - 30, 15, width - 200, 60, 1);
		if (editBoxInitFlag) {
			Modchu_Debug.mDebug("initGui editBoxInitFlag");
			editBoxInit(editBoxType);
		}
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
			mc.displayGuiScreen(null);
			return;
		}
		//Return
		if(guibutton.id == 201) {
			mc.displayGuiScreen(new PFLM_Gui(popWorld));
			return;
		}
		//Edit
		if(guibutton.id == 300) {
			isEdit = !isEdit;
			if (!isEdit) editBoxInitFlag = false;
			initGui();
			return;
		}
		//partsSlot.actionPerformed(guibutton);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(f, i, j);
		if (partsSlot != null) partsSlot.drawScreen(i, j, f);
		if (partsDetailSlot != null) partsDetailSlot.drawScreen(i, j, f);
		if (isEdit) drawEditScreen(i, j, f);
		super.drawScreen(i, j, f);
		xSize_lo = i;
		ySize_lo = j;
		Size_lo = f;
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
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
		int l = width / 2 - 80;
		int i1 = height / 2 - 25;
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
		GL11.glPushMatrix();
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
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		//Modchu_CustomModel model = textureModel[0].customModel;
		//Modchu_Debug.mDebug("drawGuiContainerBackgroundLayer model.partsName="+model.partsName);
	}

	public void drawEditScreen(int i, int j, float f) {
		editBoxX = getEditBoxX();
		editBoxY = getEditBoxY();
		editBoxSizeX = 240F;
		editBoxSizeY = 80F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(0x22666666, 255);
		tessellator.addVertexWithUV((double)editBoxX, (double)editBoxY + editBoxSizeY, 0.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV((double)editBoxX + editBoxSizeX, (double)editBoxY + editBoxSizeY, 0.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV((double)editBoxX + editBoxSizeX, (double)editBoxY, 0.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV((double)editBoxX, (double)editBoxY, 0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		String[] s = null;
		if (partsDetailSlotSelectedSelected > -1) s = getPartsDetailSlotData(partsDetailSlotDrawString.get(partsDetailSlotSelectedSelected), "_");
		if (s != null) {
			fontRenderer.drawString(s[0], (int) editBoxX + 5, (int) editBoxY + 2, 0xffffff);
			int i1 = -1;
			if (mod_Modchu_ModchuLib.integerCheck(s[2])) i1 = Integer.valueOf(s[2]);
			//fontRenderer.drawString(s[1], (int) editBoxX + 5, (int) editBoxY + 12, 0xffffff);
			fontRenderer.drawString(s[2], (int) editBoxX + 55, (int) editBoxY + 12, 0xffffff);
			editBoxType = i1;
			if (!editBoxInitFlag) {
				editBoxInitFlag = true;
				initGui();
			}
		}
	}

	private float getEditBoxX() {
		return (float)width / 2 - 120;
	}

	private float getEditBoxY() {
		return (float)height / 2 + 60;
	}

	private void editBoxInit(int i) {
		Modchu_Debug.mDebug("editBoxInit i="+i);
		editBoxX = getEditBoxX();
		editBoxY = getEditBoxY();
		switch(i) {
		case 0:
			//TextureWidth TextureHeight
			buttonList.add(new Modchu_GuiSmallButton(400, (int) editBoxX + 55, (int) editBoxY + 12, 15, 15, "+"));
			buttonList.add(new Modchu_GuiSmallButton(401, (int) editBoxX + 95, (int) editBoxY + 12, 15, 15, "-"));
			break;
		}
	}

	public void setTextureValue() {
		if (textureName == null) {
			textureName = "default_Custom1";
		}
		int i = getMaidColor();

		texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(textureName, i);
		if (texture == null) {
			int n = 0;
			for (; n < 16 && texture == null; n = n + 1) {
				i++;
				i = i & 0xf;
				setMaidColor(i);
				texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(textureName, i);
			}
			if (texture == null) {
				setNextTexturePackege(0);
				texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(textureName, i);
			}
		}

		if (drawEntity.textureModel != null) {
			Modchu_Debug.mDebug("drawEntity.textureModel != null");
		} else {
			Modchu_Debug.mDebug("drawEntity.textureModel == null textureName="+textureName);
			drawEntity.textureModel = mod_PFLM_PlayerFormLittleMaid.textureBoxModelsCheck(null, textureName, true);
		}

		//setArmorTextureValue();
	}

	public void setColorTextureValue() {
		if (textureName == null) {
			textureName = "default_Custom1";
		}
		int i = getMaidColor();
		String t = texture;
		texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(textureName, i);
		int n = 0;
		for (; n < 16 && texture == null; n = n + 1) {
			if (PFLM_Gui.colorReverse) {
				i--;
			} else {
				i++;
			}
			i = i & 0xf;
			setMaidColor(i);
			texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(textureName, i);
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
		MultiModelCustom[] models = (MultiModelCustom[]) mod_PFLM_PlayerFormLittleMaid.textureBoxModelsCheck(null, textureArmorName, true);
		if (models != null) {
			if (models[1] != null) drawEntity.textureModel[1] = models[1];
			if (models[2] != null) drawEntity.textureModel[2] = models[2];
		} else {
			models = (MultiModelCustom[]) mod_PFLM_PlayerFormLittleMaid.textureBoxModelsCheck(null, "default_Custom1");
			if (models != null) {
				if (models[1] != null) drawEntity.textureModel[1] = models[1];
				if (models[2] != null) drawEntity.textureModel[2] = models[2];
			}
		}
	}

	public static void setNextTexturePackege(int i) {
		if (i == 0) {
			textureName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetNextPackege(textureName,
							getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(textureName));
		}
		if (i == 1) {
			textureArmorName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetNextArmorPackege(textureArmorName);
			Modchu_Debug.mDebug("setNextTexturePackege(int) textureArmorName="+textureArmorName);
		}
	}

	public static void setPrevTexturePackege(int i) {
		if (i == 0) {
			textureName =
					mod_PFLM_PlayerFormLittleMaid.textureManagerGetPrevPackege(textureName, getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(textureName));
		}
		if (i == 1) {
			textureArmorName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetPrevArmorPackege(textureArmorName);
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
				if (partsDetailSlotDrawStringInitNunmer != partsSlotSelectedSelected) {
					if (partsSlotSelectedSelected > -1) partsDetailSlotDrawStringInit();
					partsDetailSlotDrawStringInitNunmer = partsSlotSelectedSelected;
				}
				return partsSlotSelectedSelected == -1 ? -1 : partsDetailSlotDrawString.size();
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
		if (isEdit) return;

		switch(guiNumber) {
		case 0:
			partsSlotSelectedSelected = i;
			break;
		case 1:
			partsDetailSlotSelectedSelected = i;
			if (!partsDetailSlotDrawStringInitFlag
					&& partsDetailSlotSelectedSelected > -1
					&& partsSlotSelectedSelected > -1) {
				partsDetailSlotDrawStringInit();
			}
			break;
		}
	}

	@Override
	public boolean guiSlotIsSelected(int guiNumber, int i) {
		int i1 = guiNumber == 0 ? partsSlotSelectedSelected : partsDetailSlotSelectedSelected;
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
			if (guiNumber == 0
					&& ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName != null) {
				String s0 = ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsName[i];
				drawString(fontRenderer, s0, showSelectionBoxLeft + 5, showSelectionBoxTop + 2, 0xffffff);
			}
			else if (guiNumber == 1) {
				if (partsSlotSelectedSelected > -1) {
					String[] s = null;
					if (i >= partsDetailSlotDrawString.size()) return;
					s = getPartsDetailSlotData(partsDetailSlotDrawString.get(i), "_");
					if (s != null) fontRenderer.drawString(s[0], showSelectionBoxLeft + 5, showSelectionBoxTop + 2, 0xffffff);
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

	private void partsDetailSlotDrawStringInit() {
		partsDetailSlotDrawString.clear();
		isEdit = false;
		editBoxInitFlag = false;
		if (partsDetailSlotSelectedSelected > -1) partsDetailSlotDrawStringInitFlag = true;
		//TextureWidth Height
		StringBuilder s = (new StringBuilder()).append("TextureWidth : ");
		s = s.append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureWidth[partsSlotSelectedSelected]);
		s = s.append(" Height : ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureHeight[partsSlotSelectedSelected]);
		s = s.append("_0_0");
		partsDetailSlotDrawString.add(s.toString());
		//TextureOffsetX Y
		s = (new StringBuilder()).append("TextureOffsetX : ");
		s = s.append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureOffsetX[partsSlotSelectedSelected]);
		s = s.append(" Y : ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureOffsetY[partsSlotSelectedSelected]);
		s = s.append("_0_1");
		partsDetailSlotDrawString.add(s.toString());
		//Box
		for (int i = 0; i < ((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxNumber[partsSlotSelectedSelected]; i++) {
			s = (new StringBuilder()).append("addBox(");
			s = s.append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxX[partsSlotSelectedSelected][i]);
			s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxY[partsSlotSelectedSelected][i]);
			s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxZ[partsSlotSelectedSelected][i]);
			s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointX[partsSlotSelectedSelected][i]);
			s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointY[partsSlotSelectedSelected][i]);
			s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsBoxInitPointZ[partsSlotSelectedSelected][i]);
			s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsScaleFactor[partsSlotSelectedSelected][i]);
			s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsScaleCorrection[partsSlotSelectedSelected][i]);
			s = s.append(")");
			s = s.append("_").append(i);
			s = s.append("_2");
			partsDetailSlotDrawString.add(s.toString());
		}
		//RotateAngleX Y
		s = (new StringBuilder()).append("RotateAngleX(");
		s = s.append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleX[partsSlotSelectedSelected]);
		s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleY[partsSlotSelectedSelected]);
		s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsRotateAngleZ[partsSlotSelectedSelected]);
		s = s.append(")");
		s = s.append("_0_3");
		partsDetailSlotDrawString.add(s.toString());
		//addChildName
		s = (new StringBuilder()).append("addChildName : ");
		s = s.append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partAddChildName[partsSlotSelectedSelected]);
		s = s.append("_0_4");
		partsDetailSlotDrawString.add(s.toString());
		//Type
		s = (new StringBuilder()).append("Type(");
		s = s.append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsType[partsSlotSelectedSelected]);
		s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTextureColor[partsSlotSelectedSelected]);
		s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTypeFactor[partsSlotSelectedSelected]);
		s = s.append(", ").append(((MultiModelCustom) drawEntity.textureModel[0]).customModel.partsTypeCorrection[partsSlotSelectedSelected]);
		s = s.append(")");
		s = s.append("_0_5");
		partsDetailSlotDrawString.add(s.toString());
		initGui();
	}

	@Override
	public int getSlotScrollBarX(int guiNumber) {
		return guiNumber == 0 ? 15 : width - 15;
	}

	@Override
	public int getSlotScrollBarSizeX(int guiNumber) {
		return 6;
	}
}
