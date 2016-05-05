package modchu.pflm;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_CastHelper;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_IGuiModelView;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.model.ModchuModel_IEntityCaps;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_RenderMasterBase;
import modchu.model.ModchuModel_TextureManagerBase;
import modchu.model.multimodel.base.MultiModelBaseBiped;

import org.lwjgl.input.Mouse;

public class PFLM_GuiModelSelectMaster extends PFLM_GuiModelViewMaster {
	public int modelColor;
	private int modelListx;
	private int modelListy;
	private int pointX;
	private int pointY;
	private int selectSlot;
	private int offsetSlot;
	private double[] textureRect;
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

	public PFLM_GuiModelSelectMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void init(HashMap<String, Object> map) {
		super.init(map);
		guiModelSelectMasterInit(map);
	}

	private void guiModelSelectMasterInit(HashMap<String, Object> map) {
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
		if (map != null
				&& !map.isEmpty()) {
			armorMode = Modchu_CastHelper.Boolean(map.get("Boolean"));
			setColor(Modchu_CastHelper.Int(map.get("Integer")));
			playerName = Modchu_CastHelper.String(map.get("String"));
		}
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", armorMode);
		modelData.setCapsValue(modelData.caps_freeVariable, "showMainModel", !armorMode);
		modelData.setCapsValue(modelData.caps_skinMode, ModchuModel_IEntityCaps.skinMode_offline);
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
		Modchu_Main.resetTextureRect(textureRect);
		drawEntitySetFlag = true;
		changeColorFlag = true;
	}

	@Override
	public void reInit() {
		super.reInit();
		guiModelSelectMasterInit(null);
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
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		if (!ModchuModel_Main.bipedCheck(modelData.models[0])) buttonList.add(newInstanceButton(armorMode ? 102 : 103, 70, 205, 75, 20, armorMode ? "Model" : "Armor"));
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
			if (i < ModchuModel_TextureManagerBase.instance.textures.size()) {
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
					&& parentScreen instanceof Modchu_IGuiModelView) {
				((Modchu_IGuiModelView) parentScreen).reInit();
			}
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, parentScreen);
		}
		//Armor | Model
		if (id == 102 | id == 103) {
			Modchu_Debug.mDebug("Armor 1 ((Modchu_GuiModelView) parentScreen).getTextureName()="+((Modchu_IGuiModelView) parentScreen).getTextureName());
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", PFLM_GuiModelSelectMaster.class, popWorld, parentScreen, id == 103, getColor()));
			Modchu_Debug.mDebug("Armor 2 ((Modchu_GuiModelView) parentScreen).getTextureName()="+((Modchu_IGuiModelView) parentScreen).getTextureName());
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
		//Modchu_Debug.mDebug1("drawGuiContainerBackgroundLayer ((Modchu_GuiModelView) parentScreen).getTextureName()="+((Modchu_GuiModelView) parentScreen).getTextureName());
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
				int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
				int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
				ModchuModel_RenderMasterBase.drawMobModel(width, height, i, j, 300, 150, 90, 30, 50F, 0.0F, comeraPosX, comeraPosY, comeraPosZ, comeraRotationX, comeraRotationY, comeraRotationZ, cameraZoom, cameraZoom, cameraZoom, true, drawEntity);
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
		if (selectCursorId > 0
				&& selectSlot > -1
				&& getTexturesNamber(selectSlot, getColor()) != -1) {
			if (Modchu_Main.getMinecraftVersion() > 159) {
				Modchu_AS.set(Modchu_AS.textureUtilBindTexture, selectCursorId);
			} else {
				Modchu_AS.set(Modchu_AS.renderEngineSetupTexture, bufferedimage, 1);
			}
			Modchu_Main.renderTexture((selectSlot - offsetSlot) % selectBoxX * 25 + modelListx, (selectSlot - offsetSlot) / selectBoxX * 55 + modelListy - 50, 8, 8, textureRect);
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
			if (i1 < 0 | i1 >= ModchuModel_TextureManagerBase.instance.textures.size()) ;
			else {
				Object ltb = ModchuModel_TextureManagerBase.instance.getTextureBox(i1);
				setTextureName(i2, null);
				if (ltb != null) setTextureName(i2, ModchuModel_TextureManagerBase.instance.getTextureBoxTextureName(ltb));
				if (getTextureName(i2) != null && !getTextureName(i2).isEmpty() | armorMode) ;
				else {
					setTextureName(i2, ModchuModel_TextureManagerBase.instance.getDefaultTextureName());
					ltb = ModchuModel_TextureManagerBase.instance.getTextureBox(getTextureName(i2));
					if (ltb != null) setTextureName(i2, ModchuModel_TextureManagerBase.instance.getTextureBoxPackegeName(ltb));
				}
				if (getTextureName(i2) != null && ltb != null) {
					PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
					modelData.setCapsValue(modelData.caps_freeVariable, "skinChar", false);
					setTextureValue(getTextureName(i2), getTextureName(i2), getColor());
					setTextureArmorName(i2, Modchu_CastHelper.String(modelData.getCapsValue(modelData.caps_textureArmorName)));
					//Modchu_Debug.mDebug("getTextureName(i2)=" + getTextureName(i2));
					//Modchu_Debug.mDebug("getTextureArmorName(i2)=" + getTextureArmorName(i2));
					if (!armorMode) reLoadModel(drawEntity, false, false);
					else {
						Object master = Modchu_Main.getModchuCharacteristicObjectMaster(ModchuModel_Main.renderPlayerDummyInstance);
						Modchu_Reflect.invokeMethod(master.getClass(), "modelArmorInit", new Class[]{ Object.class, boolean.class, boolean.class }, master, new Object[]{ drawEntity, false, false });
					}
					if (changeColorFlag) PFLM_Main.changeColor(drawEntity);
					textureModel[0][i2] = !armorMode ? modelData.models[0] : modelData.models[1];
					textureModel[1][i2] = modelData.models[1];
					textureModel[2][i2] = modelData.models[2];
					//Modchu_Debug.mDebug("textureModel[0][i2]="+textureModel[0][i2]);
					//Modchu_Debug.mDebug("textureModel[1][i2]="+textureModel[1][i2]);
					//Modchu_Debug.mDebug("textureModel[2][i2]="+textureModel[2][i2]);
				}
			}
			isRendering[i2] = (!armorMode && textureModel[0][i2] != null) | (armorMode && (textureModel[1][i2] != null | textureModel[2][i2] != null));
			//Modchu_Debug.mDebug("textureModel[0]["+i2+"]="+textureModel[0][i2]+" i1="+i1);
		} else {
			setTextureModel(i2);
		}
		//Modchu_Debug.mDebug("modelNamber="+modelNamber+" getTextureName("+i2+")="+getTextureName(i2));
		if (isRendering[i2]) {
			int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
			int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
			ModchuModel_RenderMasterBase.drawMobModel(width, height, i, j, x, y, 0, -50, f, 0.0F, comeraPosX, comeraPosY, comeraPosZ, comeraRotationX, comeraRotationY, comeraRotationZ, cameraZoom, cameraZoom, cameraZoom, false, drawEntity);
		}
	}

	private void setTextureModel(int i) {
		if (i > -1
				&& textureModel != null
				&& i < textureModel[0].length); else return;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureName, getTextureName(i));
		modelData.setCapsValue(modelData.caps_maidColor, getColor());
		modelData.setCapsValue(modelData.caps_textureArmorName, getTextureArmorName(i));
		modelData.models[0] = (MultiModelBaseBiped) textureModel[0][i];
		modelData.models[1] = (MultiModelBaseBiped) textureModel[1][i];
		modelData.models[2] = (MultiModelBaseBiped) textureModel[2][i];
		if (!armorMode) ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).modelTextureReset(drawEntity, modelData);
	}

	public void setTextureValue(String texture, String armorTexture, int color) {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureName, texture);
		modelData.setCapsValue(modelData.caps_maidColor, color);
		modelData.setCapsValue(modelData.caps_textureArmorName, armorTexture);
		if (!armorMode) {
			setTextureArmorPackege();
			modelData.setCapsValue(modelData.caps_textureArmorName, (String) modelData.getCapsValue(modelData.caps_textureArmorName));
		}
	}

	@Override
	public void setTextureArmorPackege() {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureArmorName, modelData.getCapsValue(modelData.caps_textureName));
		String s = ModchuModel_TextureManagerBase.instance.getArmorName((String) modelData.getCapsValue(modelData.caps_textureArmorName), 2);
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
	}

	@Override
	public void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		boolean doubleClick = false;
		if (Mouse.isButtonDown(0)
				&& displayModels); else return;
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
			if (i1 < ModchuModel_TextureManagerBase.instance.textures.size()) {
				if (getTexturesNamber(i1, getColor()) != -1) selectSlot = i1;
			}
			if (doubleClick && selectSlot > -1 && getTexturesNamber(selectSlot, getColor()) != -1) {
				//Modchu_Debug.mDebug("selectSlot="+selectSlot+" PFLM_Main.texturesNamber[getColor()][selectSlot]="+PFLM_Main.texturesNamber[getColor()][selectSlot]);
				selected();
			}
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		//Modchu_Debug.dDebug("keyTyped i="+i);
		if (i == 61) {
			displayModels = !displayModels;
			initGui();
		}
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
		if (parentScreen instanceof Modchu_IGuiModelView); else {
			Modchu_Debug.mDebug("selected !parentScreen instanceof Modchu_IGuiModelView !! parentScreen="+(parentScreen != null ? parentScreen.getClass() : null));
			return;
		}
		Modchu_IGuiModelView gui = (Modchu_IGuiModelView) parentScreen;
		int i2 = maxSelectBoxCheck(selectSlot);
		gui.selected(getTextureName(i2), getTextureArmorName(i2), getColor(), armorMode);
		Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, gui);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_freeVariable, "showMainModel", true);
		gui.setTextureValue();
		gui.modelChange();
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
		return modelData.getCapsValueFloat(modelData.caps_modelScale);
	}

	@Override
	public void setScale(float f) {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_modelScale, f);
	}

	@Override
	public void modelChange() {
	}

}