package modchu.pflm;

import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_CastHelper;
import modchu.lib.characteristic.Modchu_GuiBase;
import modchu.lib.characteristic.Modchu_GuiModelView;

public class PFLM_GuiOthersPlayerIndividualCustomizeMaster extends PFLM_GuiOthersPlayerMaster {
	public static String playerName;
	public static int othersMaidColor;
	public static float othersModelScale;
	public static String othersTextureName;
	public static String othersTextureArmorName;
	public static int individualCustomizeChangeMode;
	public static int othersHandednessMode;

	public PFLM_GuiOthersPlayerIndividualCustomizeMaster(Object guiBase, Object guiScreen, Object world, Object... o) {
		super(guiBase, guiScreen, world, (Object[])o);
	}

	@Override
	public void init(Object guiBase, Object guiScreen, Object world, Object... o) {
		super.init(guiBase, guiScreen, world, (Object[])o);
		guiOthersPlayerIndividualCustomizeMasterInit((Object[])o);
	}

	@Override
	public void reInit() {
		super.reInit();
		guiOthersPlayerIndividualCustomizeMasterInit(playerName);
	}

	private void guiOthersPlayerIndividualCustomizeMasterInit(Object... o) {
		isIndividual = true;
		if (o != null) {
			Object[] o2 = Modchu_CastHelper.ObjectArray(o);
			if (o2 != null) {
				playerName = o2.length > 0
						&& o2[0] != null ? (String)o2[0] : Modchu_CastHelper.String(o2);
			}
		}
		drawEntitySetFlag = true;
	}

	@Override
	protected void initButtonSetting() {
		super.initButtonSetting();
		buttonOnline = individualCustomizeChangeMode == PFLM_GuiConstant.modePlayerOnline;
		buttonOffline = individualCustomizeChangeMode == PFLM_GuiConstant.modeOffline;
		buttonRandom = individualCustomizeChangeMode == PFLM_GuiConstant.modeRandom;
		buttonScale = modelScaleButton;
		buttonParts = false;
		buttonIndividualCustomize = false;
		buttonReturn = true;
		buttonPlayer = individualCustomizeChangeMode == PFLM_GuiConstant.modePlayerOffline
				| individualCustomizeChangeMode == PFLM_GuiConstant.modePlayerOnline
				| individualCustomizeChangeMode == PFLM_GuiConstant.modePlayer
				| individualCustomizeChangeMode == PFLM_GuiConstant.modeOnline;
		buttonShowArmor = individualCustomizeChangeMode == PFLM_GuiConstant.modeOffline;
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
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiModelView(PFLM_GuiModelSelectMaster.class, base, popWorld, false, getColor(), playerName));
			return;
		}
		//Save
		if (id == 200) {
			setPlayerLocalData();
			PFLM_ConfigData.showArmor = showArmor;
			PFLM_Main.saveOthersPlayerParamater(true);
			PFLM_Config.clearCfgData();
			PFLM_Main.loadOthersPlayerParamater();
			noSaveFlag = false;
			PFLM_Main.clearDataMap();
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiBase(PFLM_GuiOthersPlayerIndividualCustomizeSelectMaster.class, popWorld));
			return;
		}
		//Return
		if (id == 201) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiBase(PFLM_GuiOthersPlayerIndividualCustomizeSelectMaster.class, popWorld));
			return;
		}
		//ChangeMode
		if (id == 13) {
			if (isShiftKeyDown) {
				individualCustomizeChangeMode--;
			} else {
				individualCustomizeChangeMode++;
			}
			if (individualCustomizeChangeMode > othersPlayerMaxchangeMode) individualCustomizeChangeMode = 0;
			if (individualCustomizeChangeMode < 0) individualCustomizeChangeMode = othersPlayerMaxchangeMode;
			//PFLM_Main.clearDataMap();
			drawEntitySetFlag = true;
			initGui();
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
		s[4] = "" + individualCustomizeChangeMode;
		s[5] = "" + getHandednessMode();
		PFLM_Main.playerLocalData.put(playerName, s);
	}

	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		resetFlagCheck(true);
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
		s9 = s9.append(getChangeModeString(individualCustomizeChangeMode));
		drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
		StringBuilder s11 = (new StringBuilder()).append("Handedness : ");
		s11 = s11.append(getHandednessModeString(getHandednessMode()));
		//if (getHandednessMode() == -1) s11 = s11.append(" Result : ").append(getHandednessModeString(handedness));
		drawString(s11.toString(), guiLeft, guiTop + 140, 0xffffff);
		if (PFLM_ConfigData.useScaleChange
				&& (individualCustomizeChangeMode == PFLM_GuiConstant.modeOffline
				| individualCustomizeChangeMode == PFLM_GuiConstant.modeRandom)
				&& modelScaleButton) {
			String s6 = "modelScale : " + getScale();
			s6 = (new StringBuilder()).append(s6).toString();
			drawString(s6, guiLeft - 140, guiTop + 30, 0xffffff);
			String s7 = "modelScaleChange";
			s7 = (new StringBuilder()).append(s7).toString();
			drawString(s7, guiLeft - 140, guiTop - 5, 0xffffff);
		}
		if (individualCustomizeChangeMode == PFLM_GuiConstant.modeOffline) {
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
			drawMobModel(i, j, l + 51, i1 + 75, 0, 25, 50F, 0.0F, true);
		}
	}

	public String getTextureName() {
		return othersTextureName;
	}

	public void setTextureName(String s) {
		othersTextureName = s != null
				&& !s.isEmpty() ? s : "default";
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureName, s);
	}

	public String getTextureArmorName() {
		return othersTextureArmorName;
	}

	public void setTextureArmorName(String s) {
		othersTextureArmorName = s != null
				&& !s.isEmpty() ? s : "default";
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