package modchu.pflm;

import java.util.HashMap;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_Main;
import modchu.model.ModchuModel_GuiModelSelectMaster;
import modchu.model.ModchuModel_IEntityCaps;
import modchu.model.ModchuModel_RenderMasterBase;
import modchu.model.ModchuModel_TextureManagerBase;

public class PFLM_GuiOthersPlayerIndividualCustomizeMaster extends PFLM_GuiOthersPlayerMaster {
	public static String playerName;
	public static int othersMaidColor;
	public static float othersModelScale;
	public static String othersTextureName;
	public static String othersTextureArmorName;
	public static int individualCustomizeChangeMode;
	public static int othersHandednessMode;

	public PFLM_GuiOthersPlayerIndividualCustomizeMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void init(HashMap<String, Object> map) {
		super.init(map);
		guiOthersPlayerIndividualCustomizeMasterInit(map);
	}

	@Override
	public void reInit() {
		super.reInit();
		guiOthersPlayerIndividualCustomizeMasterInit(null);
	}

	private void guiOthersPlayerIndividualCustomizeMasterInit(HashMap<String, Object> map) {
		isIndividual = true;
		if (map != null) {
			playerName = map.containsKey("String") ? (String)map.get("String") : null;
		}
		drawEntitySetFlag = true;
	}

	@Override
	protected void initButtonSetting() {
		super.initButtonSetting();
		int skinMode = getSkinMode();
		buttonOnline = skinMode == ModchuModel_IEntityCaps.skinMode_online;
		buttonOffline = skinMode == ModchuModel_IEntityCaps.skinMode_offline;
		buttonRandom = skinMode == ModchuModel_IEntityCaps.skinMode_Random;
		buttonScale = modelScaleButton;
		buttonParts = false;
		buttonIndividualCustomize = false;
		buttonReturn = true;
		buttonOtherPlayer = false;
		buttonPlayer = skinMode == ModchuModel_IEntityCaps.skinMode_PlayerOffline
				| skinMode == ModchuModel_IEntityCaps.skinMode_PlayerOnline
				| skinMode == ModchuModel_IEntityCaps.skinMode_Player
				| skinMode == ModchuModel_IEntityCaps.skinMode_online;
		buttonShowArmor = skinMode == ModchuModel_IEntityCaps.skinMode_offline;
	}

	public void actionPerformed(Object guibutton) {
		if (!Modchu_AS.getBoolean(Modchu_AS.guiButtonEnabled, guibutton)) {
			return;
		}
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);
		boolean isShiftKeyDown = Modchu_AS.getBoolean(Modchu_AS.isShiftKeyDown);
		boolean isCtrlKeyDown = Modchu_AS.getBoolean(Modchu_AS.isCtrlKeyDown);
		//ModelListSelect
		if (id == 56) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", ModchuModel_GuiModelSelectMaster.class, popWorld, base, false, getColor(), playerName));
			return;
		}
		//Save
		if (id == 200) {
			setPlayerLocalData();
			PFLM_Main.saveOthersPlayerParamater(true);
			PFLM_Config.clearCfgData();
			PFLM_Main.loadOthersPlayerParamater();
			noSaveFlag = false;
			PFLM_Main.clearDataMap();
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, parentScreen);
			return;
		}
		//Return
		if (id == 201) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, parentScreen);
			return;
		}
		super.actionPerformed(guibutton);
	}

	private void setPlayerLocalData() {
		String s[] = new String[6];
		s[0] = getTextureName();
		s[1] = getTextureArmorName();
		s[2] = "" + getColor();
		s[3] = "" + othersModelScale;
		s[4] = "" + getSkinMode();
		s[5] = "" + getHandednessMode();
		PFLM_Main.playerLocalData.put(playerName, s);
	}

	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		resetFlagCheck(false, false);
		int xSize = 80;
		int ySize = 50;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int guiLeft = width / 2 - xSize + 30;
		int guiTop = height / 2 - (ySize / 2) - 20;
		StringBuilder s10 = (new StringBuilder()).append("playerName : ");
		s10 = s10.append(playerName);
		drawString(s10.toString(), 10, 10, 0xffffff);
		drawString("ModelSetting", 10, 20, 0xffffff);
		StringBuilder s = (new StringBuilder()).append("TextureName : ");
		StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
		StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
		StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
		s9 = s9.append(PFLM_GuiConstant.getOtherChangeModeString(getChangeMode()));
		drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
		StringBuilder s11 = (new StringBuilder()).append("Handedness : ");
		s11 = s11.append(getHandednessModeString(getHandednessMode()));
		//if (getHandednessMode() == -1) s11 = s11.append(" Result : ").append(getHandednessModeString(handedness));
		drawString(s11.toString(), guiLeft, guiTop + 140, 0xffffff);
		int skinMode = getSkinMode();
		if (PFLM_ConfigData.useScaleChange
				&& (skinMode == ModchuModel_IEntityCaps.skinMode_offline
				| skinMode == ModchuModel_IEntityCaps.skinMode_Random)
				&& modelScaleButton) {
			String s6 = "modelScale : " + getScale();
			s6 = (new StringBuilder()).append(s6).toString();
			drawString(s6, guiLeft - 140, guiTop + 30, 0xffffff);
			String s7 = "modelScaleChange";
			s7 = (new StringBuilder()).append(s7).toString();
			drawString(s7, guiLeft - 140, guiTop - 5, 0xffffff);
		}
		if (skinMode == ModchuModel_IEntityCaps.skinMode_offline) {
			s = s.append(getTextureName());
			drawString(s.toString(), guiLeft, guiTop + 90, 0xffffff);
			s2 = s2.append(getColor());
			drawString(s2.toString(), guiLeft, guiTop + 100, 0xffffff);
			s1 = s1.append(getTextureArmorName());
			drawString(s1.toString(), guiLeft, guiTop + 110, 0xffffff);
			StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
			PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
			s8 = s8.append(modelData.getCapsValueBoolean(modelData.caps_freeVariable, "showArmor"));
			drawString(s8.toString(), guiLeft, guiTop + 120, 0xffffff);
			drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
			drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
			drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
			int l = guiLeft;
			int i1 = guiTop;
			ModchuModel_RenderMasterBase.drawMobModel(width, height, i, j, l + 51, i1 + 75, 0, 25, 50F, 0.0F, comeraPosX, comeraPosY, comeraPosZ, comeraRotationX, comeraRotationY, comeraRotationZ, cameraZoom, cameraZoom, cameraZoom, true, drawEntity);
		}
	}

	public String getTextureName() {
		return othersTextureName;
	}

	public void setTextureName(String s) {
		othersTextureName = s != null
				&& !s.isEmpty() ? s : ModchuModel_TextureManagerBase.instance.getDefaultTextureName();
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureName, s);
	}

	public String getTextureArmorName() {
		return othersTextureArmorName;
	}

	public void setTextureArmorName(String s) {
		othersTextureArmorName = s != null
				&& !s.isEmpty() ? s : ModchuModel_TextureManagerBase.instance.getDefaultTextureName();
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
	}

	public int getColor() {
		return othersMaidColor;
	}

	public void setColor(int i) {
		othersMaidColor = i & 0xf;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_maidColor, i & 0xf);
	}

	public float getScale() {
		return othersModelScale;
	}

	public int getHandednessMode() {
		return othersHandednessMode;
	}

	public void setHandednessMode(int i) {
		othersHandednessMode = i;
	}

	public void setScale(float f) {
		othersModelScale = f;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_modelScale, f);
	}

	@Override
	protected int getChangeMode() {
		return individualCustomizeChangeMode;
	}

	@Override
	public void setChangeMode(int i) {
		individualCustomizeChangeMode = i;
	}
}