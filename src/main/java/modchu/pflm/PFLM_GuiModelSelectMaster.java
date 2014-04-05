package modchu.pflm;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_GuiBase;
import modchu.lib.characteristic.Modchu_GuiModelView;
import modchu.model.ModchuModel_IModelCapsConstant;
import modchu.model.ModchuModel_Main;
import modchu.model.replacepoint.ModchuModel_ModelBaseDuoReplacePoint;
import modchu.model.replacepoint.ModchuModel_ModelBaseSoloReplacePoint;

import org.lwjgl.input.Mouse;

public class PFLM_GuiModelSelectMaster extends PFLM_GuiModelViewMaster {
	public int modelColor;
	private int modelListx;
	private int modelListy;
	private int pointX;
	private int pointY;
	private int selectSlot;
	private int offsetSlot;
	private double textureRect[];
	private BufferedImage bufferedimage;
	private long lastClicked;
	private int modelNamber;
	private int selectBoxX;
	private int selectBoxY;
	private int selectCursorId;
	private String playerName;
	private int select;
	private boolean changeColorFlag;
	private Object[][] textureModel;
	private String[] textureName;
	private String[] textureArmorName;
	private boolean isRendering[];
	public boolean displayModels;
	public boolean armorMode;

	public PFLM_GuiModelSelectMaster(Modchu_GuiBase guiBase, Object par1GuiScreen, Object world, Object... o) {
		super(guiBase, par1GuiScreen, world, (Object[])o);
	}

	@Override
	public void init(Modchu_GuiBase guiBase, Object par1GuiScreen, Object world, Object... o) {
		super.init(guiBase, par1GuiScreen, world, (Object[])o);
		guiModelSelectMasterInit((Object[])o);
	}

	private void guiModelSelectMasterInit(Object... o) {
		modelColor = 0;
		modelListx = 30;
		modelListy = 60;
		selectSlot = -1;
		offsetSlot = 0;
		modelNamber = 0;
		selectBoxX = 8;
		selectBoxY = 3;
		selectCursorId = -1;
		playerName = null;
		displayModels = true;
		displayModels = true;
		armorMode = false;
		PFLM_Main.texturesNamberInit();
		textureRect = new double[8];
		int i1 = getMaxSelectBoxViewCount();
		textureModel = new Object[3][i1];
		textureName = new String[i1];
		textureArmorName = new String[i1];
		isRendering = new boolean[i1];
		if (o != null
				&& o.length > 0
				&& o[0] != null) armorMode = (Boolean) o[0];
		if (o != null
				&& o.length > 1
				&& o[1] != null) setColor(Integer.valueOf(""+o[1]));
		if (o != null
				&& o.length > 2
				&& o[2] != null) playerName = (String) o[2];
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_freeVariable, "showArmor", armorMode);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_freeVariable, "showMainModel", !armorMode);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_skinMode, PFLM_ModelDataMaster.skinMode_offline);
		if (PFLM_ConfigData.isModelSize) {
			Modchu_AS.set(Modchu_AS.minecraftGameSettingsThirdPersonView, 0);
		}
		Modchu_AS.set(Modchu_AS.entitySetPosition, drawEntity, Modchu_AS.getDouble(Modchu_AS.entityPosX), Modchu_AS.getDouble(Modchu_AS.entityPosY), Modchu_AS.getDouble(Modchu_AS.entityPosZ));
		try {
			bufferedimage = ImageIO.read((URL) Modchu_Reflect.invokeMethod(Class.class, "getResource", new Class[]{ String.class }, Modchu_Reflect.loadClass("Minecraft"), new Object[]{ Modchu_Main.getMinecraftVersion() > 159 ? "/assets/minecraft/textures/particle/particles.png" : "/particles.png" }));
			//bufferedimage = ImageIO.read((Minecraft.class).getResource("/particles.png"));
			int sx = bufferedimage.getWidth() / 128;
			int sy = bufferedimage.getHeight() / 128;
			bufferedimage = bufferedimage.getSubimage(0, 32 * sy, 8 * sx, 8 * sy);
			drawSelectCursorInit();
		} catch (Exception e) {
			e.printStackTrace();
			Modchu_Debug.lDebug("PFLM_GuiModelSelect bufferedimage Exception!!");
			selectCursorId = -1;
		}
		resetTextureRect();
		drawEntitySetFlag = true;
		changeColorFlag = true;
	}

	@Override
	public void reInit() {
		super.reInit();
		guiModelSelectMasterInit((Object)null);
	}

	@Override
	public void initGui() {
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		buttonList.clear();
		if (!displayButton) return;
		buttonList.add(newInstanceButton(0, 80, 185, 15, 15, "<"));
		buttonList.add(newInstanceButton(1, 100, 185, 15, 15, ">"));
		if (!armorMode) {
			buttonList.add(newInstanceButton(2, 145, 180, 15, 15, "<"));
			buttonList.add(newInstanceButton(3, 160, 180, 15, 15, ">"));
		}
		buttonList.add(newInstanceButton(armorMode ? 102 : 103, 70, 205, 75, 20, armorMode ? "Model" : "Armor"));
		buttonList.add(newInstanceButton(100, 155, 205, 75, 20, "select"));
		buttonList.add(newInstanceButton(101, 240, 205, 75, 20, "return"));
		buttonList.add(newInstanceButton(999, 0, 0, 0, 0, ""));
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
	}

	public void actionPerformed(Object guibutton) {
		if (!Modchu_AS.getBoolean(Modchu_AS.guiButtonEnabled, guibutton)) {
			return;
		}
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		//pagePrev
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);
		if (id == 0) {
			int i1 = getMaxSelectBoxViewCount();
			int i = offsetSlot - i1;
			if (i > -1) offsetSlot -= i1;
			selectSlot = -1;
			drawEntitySetFlag = true;
			return;
		}
		//pageNext
		if (id == 1) {
			int i1 = getMaxSelectBoxViewCount();
			int i = i1 + offsetSlot;
			if (i < ModchuModel_Main.getTextureManagerTexturesSize()) {
				if (getTexturesNamber(i, getColor()) != -1) offsetSlot += i1;
			}
			selectSlot = -1;
			drawEntitySetFlag = true;
			return;
		}
		if (!armorMode) {
			//colorPrev
			if (id == 2) {
				setColor(getColor() - 1);
				maxPageCheack();
				changeColorFlag = true;
				drawEntitySetFlag = true;
				return;
			}
			//colorNext
			if (id == 3) {
				setColor(getColor() + 1);
				maxPageCheack();
				changeColorFlag = true;
				drawEntitySetFlag = true;
				return;
			}
		}
		//select
		if (id == 100) {
			if (selectSlot > -1
					&& getTexturesNamber(selectSlot, getColor()) != -1) {
				selected();
			}
		}
		//return
		if (id == 101) {
			if (parentScreen != null
					&& parentScreen instanceof Modchu_GuiModelView) {
				((Modchu_GuiModelView) parentScreen).reInit();
			}
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, parentScreen);
		}
		//Armor | Model
		if (id == 102 | id == 103) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiModelView(PFLM_GuiModelSelectMaster.class, parentScreen, popWorld, id == 103, getColor()));
			return;
		}
	}

	private void maxPageCheack() {
		int maxCount = getMaxSelectBoxViewCount();
		int maxTexturesNamber = getMaxTexturesNamber(getColor());
		if (offsetSlot / (getMaxSelectBoxViewCount()) > (maxTexturesNamber - 1) / maxCount) {
			offsetSlot = (maxTexturesNamber - 1) / maxCount * maxCount;
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		//GL11.glPushMatrix();
		if (displayModels) {
			modelNamber = offsetSlot;
			float f1 = 20F;
			for (int i2 = 0; i2 < selectBoxY; i2++) {
				for (int i1 = 0; i1 < selectBoxX; i1++) {
					drawModel(i, j, i1 * 25 + modelListx, i2 * 50 + modelListy, f1, modelNamber);
					++modelNamber;
				}
			}
			drawSelectCursor();
		}
		if (selectSlot > -1) {
			int i2 = maxSelectBoxCheck(selectSlot);
			String textureName = getTextureName(i2);
			if (textureName != null) {
				drawString(armorMode ? "TextureArmorName" : "TextureName", 240, 170, 0xffffff);
				drawString(textureName, 240, 180, 0xffffff);
				setTextureModel(i2);
				drawMobModel(i, j, 300, 150, 90, 30, 50F, 0.0F, true);
			}
		}
		drawString("Page : " + offsetSlot / getMaxSelectBoxViewCount() + " / " + (getMaxTexturesNamber(getColor()) - 1) / getMaxSelectBoxViewCount(), 55, 170, 0xffffff);
		if (!armorMode) drawString("MaidColor : " + getColor(), 130, 170, 0xffffff);
		drawString(armorMode ? "ArmorSelect" : "ModelSelect", 180, 5, 0xffffff);
		if (changeColorFlag) changeColorFlag = false;
		if (drawEntitySetFlag) {
			//Modchu_Debug.mDebug("drawGuiContainerBackgroundLayer drawEntitySetFlag = false set");
			drawEntitySetFlag = false;
		}
		//GL11.glPopMatrix();
	}

	private int getMaxTexturesNamber(int i) {
		return armorMode ? PFLM_ConfigData.maxTexturesArmorNamber : PFLM_ConfigData.maxTexturesNamber[i];
	}

	private void drawSelectCursorInit() {
		if (bufferedimage != null) {
			if (Modchu_Main.getMinecraftVersion() > 159) {
				selectCursorId = Modchu_AS.getInt(Modchu_AS.textureUtilUploadTextureImage, 1, bufferedimage);
			} else {
				Modchu_AS.set(Modchu_AS.renderEngineSetupTexture, bufferedimage, 1);
			}
		}
	}

	private void drawSelectCursor() {
		if (selectCursorId > 0 && selectSlot > -1 && getTexturesNamber(selectSlot, getColor()) != -1) {
			if (Modchu_Main.getMinecraftVersion() > 159) {
				Modchu_AS.set(Modchu_AS.textureUtilBindTexture, selectCursorId);
			} else {
				Modchu_AS.set(Modchu_AS.renderEngineSetupTexture, bufferedimage, 1);
			}
			renderTexture((selectSlot - offsetSlot) % selectBoxX * 25 + modelListx, (selectSlot - offsetSlot) / selectBoxX * 55 + modelListy - 50, 8, 8);
		}
	}

	private void drawModel(int i, int j, int x, int y, float f, int modelNamber) {
		int i2 = maxSelectBoxCheck(modelNamber);
		//Modchu_Debug.mDebug("drawModel i2="+i2);
		if (i2 < 0) return;
		if (drawEntitySetFlag) {
			int i1 = getTexturesNamber(modelNamber, getColor());
			if (armorMode) setColor(0);
			setScale(PFLM_Main.getModelScale(drawEntity));
			if (i2 == 0) {
				for (int i3 = 0; i3 < getMaxSelectBoxViewCount(); i3++) {
					textureModel[0][i3] = null;
					textureModel[1][i3] = null;
					textureModel[2][i3] = null;
				}
			}
			if (i1 < 0 | i1 >= ModchuModel_Main.getTextureManagerTexturesSize()) ;
			else {
				Object ltb = ModchuModel_Main.getTextureBox(i1);
				setTextureName(i2, null);
				if (ltb != null) setTextureName(i2, ModchuModel_Main.getTextureBoxFileName(ltb));
				if (getTextureName(i2) != null && !getTextureName(i2).isEmpty() | armorMode) ;
				else {
					setTextureName(i2, "default");
					ltb = ModchuModel_Main.getTextureBox(getTextureName(i2));
					if (ltb != null) setTextureName(i2, ModchuModel_Main.getTextureBoxFileName(ltb));
				}
				if (getTextureName(i2) != null && ltb != null) {
					PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
					setTextureValue(getTextureName(i2), getTextureName(i2), getColor());
					setTextureArmorName(i2, (String) modelData.getCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName));
					Modchu_Debug.mDebug("getTextureName(i2)=" + getTextureName(i2));
					Modchu_Debug.mDebug("getTextureArmorName(i2)=" + getTextureArmorName(i2));
					Modchu_AS.set(Modchu_AS.allModelInit, PFLM_Main.renderPlayerDummyInstance, drawEntity, false);
					if (changeColorFlag) PFLM_Main.changeColor(drawEntity);
					textureModel[0][i2] = !armorMode ? modelData.modelMain.model : modelData.modelFATT.modelInner;
					textureModel[1][i2] = modelData.modelFATT.modelInner;
					textureModel[2][i2] = modelData.modelFATT.modelOuter;
					//Modchu_Debug.mDebug("textureModel[0][i2]="+textureModel[0][i2]);
					//Modchu_Debug.mDebug("textureModel[1][i2]="+textureModel[1][i2]);
					//Modchu_Debug.mDebug("textureModel[2][i2]="+textureModel[2][i2]);
				}
			}
			isRendering[i2] = (!armorMode && textureModel[0][i2] != null) | (armorMode && textureModel[1][i2] != null | textureModel[2][i2] != null);
			//Modchu_Debug.mDebug("textureModel[0]["+i2+"]="+textureModel[0][i2]+" i1="+i1);
		} else {
			setTextureModel(i2);
		}
		//Modchu_Debug.mDebug("modelNamber="+modelNamber+" getTextureName("+i2+")="+getTextureName(i2));
		if (isRendering[i2]) drawMobModel(i, j, x, y, 0, -50, f, 0.0F, false);
	}

	private void setTextureModel(int i) {
		if (i > -1
				&& textureModel != null
				&& i < textureModel[0].length) ;else return;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureName, getTextureName(i));
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_maidColor, getColor());
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName, getTextureArmorName(i));
		Modchu_Reflect.setFieldObject(ModchuModel_ModelBaseSoloReplacePoint.class, "model", modelData.modelMain, textureModel[0][i]);
		Modchu_Reflect.setFieldObject(ModchuModel_ModelBaseDuoReplacePoint.class, "modelInner", modelData.modelFATT, textureModel[1][i]);
		Modchu_Reflect.setFieldObject(ModchuModel_ModelBaseDuoReplacePoint.class, "modelOuter", modelData.modelFATT, textureModel[2][i]);
		if (!armorMode) PFLM_ModelDataMaster.instance.modelTextureReset(drawEntity, modelData);
	}

	public void setTextureValue(String texture, String armorTexture, int color) {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureName, texture);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_maidColor, color);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName, armorTexture);
		if (!armorMode) {
			setTextureArmorPackege();
			modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName, (String) modelData.getCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName));
		}
	}

	@Override
	public void setTextureArmorPackege() {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName, modelData.getCapsValue(ModchuModel_IModelCapsConstant.caps_textureName));
		String s = PFLM_Main.getArmorName((String) modelData.getCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName), 2);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName, s);
	}

	@Override
	public boolean mouseClicked(int x, int y, int i) {
		boolean b = super.mouseClicked(x, y, i);
		boolean doubleClick = false;
		if (Mouse.isButtonDown(0)
				&& displayModels) ;else return true;
		long l1 = Modchu_AS.getLong(Modchu_AS.minecraftSystemTime);
		if (l1 - lastClicked < 250L) doubleClick = true;
		lastClicked = l1;
		pointX = (x - 21) / 24;
		pointY = (y - 10) / 50;
		//Modchu_Debug.dDebug("x="+x);
		//Modchu_Debug.dDebug("y="+y,1);
		//Modchu_Debug.dDebug("pointX="+pointX,2);
		//Modchu_Debug.dDebug("pointY="+pointY,3);
		if (pointX >= 0 && pointX < selectBoxX && pointY >= 0 && pointY < selectBoxY) {
			int i1 = offsetSlot + pointX + (pointY * selectBoxX);
			if (i1 < ModchuModel_Main.getTextureManagerTexturesSize()) {
				if (getTexturesNamber(i1, getColor()) != -1) selectSlot = i1;
			}
			if (doubleClick && selectSlot > -1 && getTexturesNamber(selectSlot, getColor()) != -1) {
				//Modchu_Debug.mDebug("selectSlot="+selectSlot+" PFLM_Main.texturesNamber[getColor()][selectSlot]="+PFLM_Main.texturesNamber[getColor()][selectSlot]);
				selected();
			}
		}
		return true;
	}

	@Override
	public boolean keyTyped(char c, int i) {
		super.keyTyped(c, i);
		//Modchu_Debug.dDebug("keyTyped i="+i);
		if (i == 61) {
			displayModels = !displayModels;
			initGui();
		}
		return true;
	}

	private int getTexturesNamber(int i, int i1) {
		if (armorMode) {
			if (i >= PFLM_ConfigData.texturesArmorNamber.length) return -1;
		} else {
			if (i >= PFLM_ConfigData.texturesNamber[i1].length) return -1;
		}
		return armorMode ? PFLM_ConfigData.texturesArmorNamber[i] : PFLM_ConfigData.texturesNamber[i1][i];
	}

	private void selected() {
		if (parentScreen instanceof Modchu_GuiModelView) ;else {
			Modchu_Debug.mDebug("selected !parentScreen instanceof Modchu_GuiModelView !! parentScreen="+(parentScreen != null ? parentScreen.getClass() : null));
			return;
		}
		Modchu_GuiModelView gui = (Modchu_GuiModelView) parentScreen;
		int i2 = maxSelectBoxCheck(selectSlot);
		gui.selected(getTextureName(i2), getTextureArmorName(i2), getColor(), armorMode);
		Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, gui);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_freeVariable, "showArmor", true);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_freeVariable, "showMainModel", true);
		gui.setArmorTextureValue();
		gui.modelChange();
	}

	private void renderTexture(int x, int y, int width, int height) {
		Object tes = Modchu_AS.get(Modchu_AS.tessellatorInstance);
		Modchu_AS.set(Modchu_AS.tessellatorStartDrawingQuads, tes);
		Modchu_AS.set(Modchu_AS.tessellatorAddVertexWithUV, tes, (double) (x + 0), (double) (y + 0), 0.0D, textureRect[0], textureRect[1]);
		Modchu_AS.set(Modchu_AS.tessellatorAddVertexWithUV, tes, (double) (x + 0), (double) (y + height), 0.0D, textureRect[2], textureRect[3]);
		Modchu_AS.set(Modchu_AS.tessellatorAddVertexWithUV, tes, (double) (x + width), (double) (y + height), 0.0D, textureRect[4], textureRect[5]);
		Modchu_AS.set(Modchu_AS.tessellatorAddVertexWithUV, tes, (double) (x + width), (double) (y + 0), 0.0D, textureRect[6], textureRect[7]);
		Modchu_AS.set(Modchu_AS.tessellatorDraw, tes);
	}

	private void resetTextureRect() {
		textureRect[0] = 0.0D;
		textureRect[1] = 0.0D;
		textureRect[2] = 0.0D;
		textureRect[3] = 1.0D;
		textureRect[4] = 1.0D;
		textureRect[5] = 1.0D;
		textureRect[6] = 1.0D;
		textureRect[7] = 0.0D;
	}

	private double[] mirrorTexture(double[] src) {
		double[] result = new double[8];
		result[0] = src[6];
		result[1] = src[1];
		result[2] = src[4];
		result[3] = src[3];
		result[4] = src[2];
		result[5] = src[5];
		result[6] = src[0];
		result[7] = src[7];
		return result;
	}

	private double[] rotateTexture(double[] src) {
		double[] result = new double[8];
		result[0] = src[2];
		result[1] = src[3];
		result[2] = src[4];
		result[3] = src[5];
		result[4] = src[6];
		result[5] = src[7];
		result[6] = src[0];
		result[7] = src[1];
		return result;
	}

	public int getMaxSelectBoxViewCount() {
		return selectBoxX * selectBoxY;
	}

	private int maxSelectBoxCheck(int i) {
		int i1 = getMaxSelectBoxViewCount();
		while (i >= i1) {
			i = i - i1;
		}
		return i;
	}

	@Override
	public String getTextureName() {
		return getTextureName(selectSlot);
	}

	public String getTextureName(int i) {
		return textureName != null
				&& i > -1
				&& i < textureName.length ? textureName[i] : null;
	}

	@Override
	public void setTextureName(String s) {
		setTextureName(selectSlot, s);
	}

	public void setTextureName(int i, String s) {
		if (i < textureName.length) textureName[i] = s;
	}

	@Override
	public String getTextureArmorName() {
		return null;
		//return getTextureArmorName(modelNamber);
	}

	public String getTextureArmorName(int i) {
		return textureArmorName != null
				&& i > -1
				&& i < textureArmorName.length ? textureArmorName[i] : null;
	}

	@Override
	public void setTextureArmorName(String s) {
		//setTextureArmorName(modelNamber, s);
	}

	public void setTextureArmorName(int i, String s) {
		if (i < textureArmorName.length) textureArmorName[i] = s;
	}

	@Override
	public int getColor() {
		return modelColor;
	}

	@Override
	public void setColor(int i) {
		modelColor = i & 0xf;
	}

	@Override
	public float getScale() {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		return modelData.getCapsValueFloat(ModchuModel_IModelCapsConstant.caps_modelScale);
	}

	@Override
	public void setScale(float f) {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_modelScale, f);
	}

	@Override
	public void modelChange() {
	}

}