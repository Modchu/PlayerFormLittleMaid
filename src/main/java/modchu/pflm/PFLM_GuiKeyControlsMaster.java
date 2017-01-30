package modchu.pflm;

import java.util.HashMap;
import java.util.List;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_IGuiModelView;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_RenderMasterBase;

import org.lwjgl.input.Keyboard;

public class PFLM_GuiKeyControlsMaster extends PFLM_GuiMaster {

	private int select;
	public static final int modeOthersSettingOffline = 0;
	public static final int modeSetModelAndArmor = 1;
	public static final int modeSetModelAndColor = 2;
	public static final int modeSetModel = 3;
	public static final int modeSetColor = 4;
	public static final int modeSetColorAndArmor = 5;
	public static final int modeSetArmor = 6;
	public static final int modeModelScale = 7;
	public static final int modePlayerOffline = 8;
	public static final int modePlayerOnline = 9;
	public static final int modeRandom = 10;
	public static final int modeActionRelease = 11;
	public static final int modeAction = 12;
	public static final int modeActionLast = 41;
	public static final int modeCustomModelCfgReLoad = 42;
	public static final int modeAllMultiModelActionPlus = 43;
	public static final int modeAllMultiModelActionMinus = 44;
	public static final int modeAllMultiModelActionModeChangePlus = 45;
	public static final int modeAllMultiModelActionModeChangeMinus = 46;
	public static final int modeAllMultiModelActionRun = 47;
	public static final int changeModeMax = 48;
	private String shortcutKey;

	public PFLM_GuiKeyControlsMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void initGui() {
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		buttonList.clear();
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		buttonList.add(newInstanceButton(403, x + 75, y + 10, 75, 15, "Use Change"));
		buttonList.add(newInstanceButton(200, x, y + 115, 75, 20, "Save"));
		buttonList.add(newInstanceButton(201, x + 75, y + 115, 75, 20, "Return"));
		buttonList.add(newInstanceButton(407, x + 75, y - 6, 20, 15, "-10"));
		buttonList.add(newInstanceButton(401, x + 95, y - 6, 15, 15, "-"));
		buttonList.add(newInstanceButton(402, x + 110, y - 6, 15, 15, "+"));
		buttonList.add(newInstanceButton(408, x + 125, y - 6, 20, 15, "+10"));
		if (PFLM_ConfigData.shortcutKeysUse[select]) {
			buttonList.add(newInstanceButton(404, x + 75, y + 70, 75, 15, "Use ModelsKey"));
			buttonList.add(newInstanceButton(405, x + 75, y + 85, 75, 15, "Use CtrlKey"));
			buttonList.add(newInstanceButton(406, x + 75, y + 100, 75, 15, "Use ShiftKey"));
			buttonList.add(newInstanceButton(400, x + 75, y + 25, 75, 15, "ChangeMode"));
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] == modeOthersSettingOffline | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModel | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModelAndColor | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
				buttonList.add(newInstanceButton(56, x - 10, y + 10, 85, 15, "ModelListSelect"));
				buttonList.add(newInstanceButton(50, x + 40, y + 25, 15, 15, "<"));
				buttonList.add(newInstanceButton(51, x + 55, y + 25, 15, 15, ">"));
			}
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] == modeOthersSettingOffline | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetColor | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModelAndColor | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
				buttonList.add(newInstanceButton(52, x + 40, y + 40, 15, 15, "-"));
				buttonList.add(newInstanceButton(53, x + 55, y + 40, 15, 15, "+"));
			}
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] == modeOthersSettingOffline | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetArmor | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetColorAndArmor | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
				buttonList.add(newInstanceButton(54, x + 40, y + 55, 15, 15, "<"));
				buttonList.add(newInstanceButton(55, x + 55, y + 55, 15, 15, ">"));
				buttonList.add(newInstanceButton(20, x, y + 85, 75, 15, "showArmor"));
			}
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] == modeOthersSettingOffline | PFLM_ConfigData.shortcutKeysChangeMode[select] == modeModelScale) {
				if (modelScaleButton) {
					buttonList.add(newInstanceButton(3, width / 2 - 140, height / 2 + 20, 50, 20, "Default"));
					buttonList.add(newInstanceButton(4, width / 2 - 90, height / 2 + 20, 30, 20, "UP"));
					buttonList.add(newInstanceButton(5, width / 2 - 170, height / 2 + 20, 30, 20, "Down"));
					buttonList.add(newInstanceButton(6, x + 75, y + 40, 75, 15, "Close"));
				} else {
					buttonList.add(newInstanceButton(7, x + 75, y + 40, 75, 15, "ScaleChange"));
				}
			}
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] >= modeAction && PFLM_ConfigData.shortcutKeysChangeMode[select] <= modeActionLast) {
				buttonList.add(newInstanceButton(409, x + 50, y + 165, 15, 15, "+"));
				buttonList.add(newInstanceButton(410, x + 35, y + 165, 15, 15, "-"));
				buttonList.add(newInstanceButton(411, x + 65, y + 165, 20, 15, "+10"));
				buttonList.add(newInstanceButton(412, x + 15, y + 165, 20, 15, "-10"));
			}
		}
		if (PFLM_ConfigData.shortcutKeysChangeMode[select] < 8) setTextureValue();
		selectInit();
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
	}

	@Override
	public void actionPerformed(Object guibutton) {
		if (!Modchu_AS.getBoolean(Modchu_AS.guiButtonEnabled, guibutton)) {
			return;
		}
		//Save
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);
		if (id == 200) {
			PFLM_Main.saveShortcutKeysParamater();
			PFLM_Config.clearCfgData();
			noSaveFlag = false;
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, (Object)null);
			PFLM_Main.clearDataMap();
			return;
		}
		//Return
		if (id == 201) {
			((Modchu_IGuiModelView) parentScreen).setTextureValue();
			Modchu_AS.set(Modchu_AS.allModelInit, ModchuModel_Main.renderPlayerDummyInstance, drawEntity, false);
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, parentScreen);
			return;
		}
		//ChangeMode
		if (id == 400) {
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				PFLM_ConfigData.shortcutKeysChangeMode[select]--;
				if ((PFLM_ConfigData.shortcutKeysChangeMode[select] > modeAction && PFLM_ConfigData.shortcutKeysChangeMode[select] <= modeActionLast)
				//TODO modeを増やしたときに問題ないかチェックする
				//&& changeModeMax == modeActionLast + 1
				) PFLM_ConfigData.shortcutKeysChangeMode[select] = modeAction;
				if (PFLM_ConfigData.shortcutKeysChangeMode[select] < 0) PFLM_ConfigData.shortcutKeysChangeMode[select] = changeModeMax - 1;
			} else {
				PFLM_ConfigData.shortcutKeysChangeMode[select]++;
				if (PFLM_ConfigData.shortcutKeysChangeMode[select] > modeAction && PFLM_ConfigData.shortcutKeysChangeMode[select] <= modeActionLast) PFLM_ConfigData.shortcutKeysChangeMode[select] = modeActionLast + 1;
			}
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] >= changeModeMax) PFLM_ConfigData.shortcutKeysChangeMode[select] = 0;
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] < 0) PFLM_ConfigData.shortcutKeysChangeMode[select] = changeModeMax;
			//Modchu_Debug.mDebug("PFLM_ConfigData.shortcutKeysChangeMode[select]="+PFLM_ConfigData.shortcutKeysChangeMode[select]);
			initGui();
			return;
		}
		//select--
		if (id == 401) {
			select = select > 0 ? --select : PFLM_ConfigData.maxShortcutKeys - 1;
			initGui();
			return;
		}
		//select++
		if (id == 402) {
			select = select < PFLM_ConfigData.maxShortcutKeys - 1 ? ++select : 0;
			initGui();
			return;
		}
		//use Change
		if (id == 403) {
			PFLM_ConfigData.shortcutKeysUse[select] = !PFLM_ConfigData.shortcutKeysUse[select];
			//modc_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
			initGui();
			return;
		}
		//use ModelsKey
		if (id == 404) {
			PFLM_ConfigData.shortcutKeysPFLMModelsUse[select] = !PFLM_ConfigData.shortcutKeysPFLMModelsUse[select];
			//modc_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
			initGui();
			return;
		}
		//use Ctrl
		if (id == 405) {
			PFLM_ConfigData.shortcutKeysCtrlUse[select] = !PFLM_ConfigData.shortcutKeysCtrlUse[select];
			//modc_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
			initGui();
			return;
		}
		//use Shift
		if (id == 406) {
			PFLM_ConfigData.shortcutKeysShiftUse[select] = !PFLM_ConfigData.shortcutKeysShiftUse[select];
			//modc_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
			initGui();
			return;
		}
		//select-10
		if (id == 407) {
			select = select > 10 ? select - 10 : 0;
			initGui();
			return;
		}
		//select+10
		if (id == 408) {
			select = select < PFLM_ConfigData.maxShortcutKeys - 10 ? select + 10 : PFLM_ConfigData.maxShortcutKeys - 1;
			initGui();
			return;
		}
		//Action++
		if (id == 409) {
			PFLM_ConfigData.shortcutKeysChangeMode[select]++;
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] > modeActionLast) PFLM_ConfigData.shortcutKeysChangeMode[select] = modeAction;
			initGui();
			return;
		}
		//Action--
		if (id == 410) {
			PFLM_ConfigData.shortcutKeysChangeMode[select]--;
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] < modeAction) PFLM_ConfigData.shortcutKeysChangeMode[select] = modeActionLast;
			initGui();
			return;
		}
		//Action+10
		if (id == 411) {
			PFLM_ConfigData.shortcutKeysChangeMode[select] = PFLM_ConfigData.shortcutKeysChangeMode[select] + 10;
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] > modeActionLast) PFLM_ConfigData.shortcutKeysChangeMode[select] = modeActionLast;
			initGui();
			return;
		}
		//Action-10
		if (id == 412) {
			PFLM_ConfigData.shortcutKeysChangeMode[select] = PFLM_ConfigData.shortcutKeysChangeMode[select] - 10;
			if (PFLM_ConfigData.shortcutKeysChangeMode[select] < modeAction) PFLM_ConfigData.shortcutKeysChangeMode[select] = modeAction;
			initGui();
			return;
		}
		super.actionPerformed(guibutton);
	}

	public void selectInit() {
		shortcutKey = "";
		String s = "key.ModelChange" + select;
		shortcutKey = getKeyDisplayString(s);
		if (PFLM_ConfigData.shortcutKeysPFLMModelsUse[select]) {
			s = "key.PFLM Models Key";
			shortcutKey = new StringBuilder().append(getKeyDisplayString(s)).append(" + ").append(shortcutKey).toString();
		}
		if (PFLM_ConfigData.shortcutKeysCtrlUse[select]) {
			boolean isMac = Modchu_AS.getBoolean(Modchu_AS.isMac);
			shortcutKey = new StringBuilder().append(Modchu_AS.getString(Modchu_AS.gameSettingsGetKeyDisplayString, isMac ? 219 : 29)).append(" or ").append(Modchu_AS.getString(Modchu_AS.gameSettingsGetKeyDisplayString, isMac ? 220 : 157)).append(" + ").append(shortcutKey).toString();
		}
		if (PFLM_ConfigData.shortcutKeysShiftUse[select]) {
			shortcutKey = new StringBuilder().append(Modchu_AS.getString(Modchu_AS.gameSettingsGetKeyDisplayString, 42)).append(" or ").append(Modchu_AS.getString(Modchu_AS.gameSettingsGetKeyDisplayString, 54)).append(" + ").append(shortcutKey).toString();
		}
	}

	public String getKeyDisplayString(String s) {
		Object keyBinding;
		List keybindArray = Modchu_AS.getList(Modchu_AS.keybindArray);
		for (int i = 0; i < keybindArray.size(); i++) {
			keyBinding = keybindArray.get(i);
			String keyDescription = Modchu_AS.getString(Modchu_AS.keyBindingKeyDescription, keyBinding);
			if (keyDescription.equalsIgnoreCase(s)) {
				return Modchu_AS.getString(Modchu_AS.gameSettingsGetKeyDisplayString, Modchu_AS.getInt(Modchu_AS.keyBindingKeyCode, keyBinding));
			}
		}
		return null;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int xSize = 80;
		int ySize = 50;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int guiLeft = width / 2 - xSize + 30;
		int guiTop = height / 2 - (ySize / 2) - 20;
		drawString("KeyControlsSetting", width / 2 - 50, height / 2 - 110, 0xffffff);
		drawString((new StringBuilder()).append("ShortcutKey = ").append(shortcutKey).toString(), width / 2 - 150, height / 2 - 100, 0xffffff);
		drawString("SettingNumber", width / 2 + 40, height / 2 - 88, 0xffffff);
		String use = PFLM_ConfigData.shortcutKeysUse[select] ? "use" : "unused";
		StringBuilder s10 = (new StringBuilder()).append("shortcutKey : ");
		s10 = s10.append(use);
		drawString(s10.toString(), guiLeft, guiTop + 150, 0xffffff);
		drawString("" + select, width / 2 + 115, height / 2 - 88, 0xffffff);
		if (!PFLM_ConfigData.shortcutKeysUse[select]) return;
		StringBuilder s = (new StringBuilder()).append("TextureName : ");
		StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
		StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
		StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
		s9 = s9.append(getChangeModeString(PFLM_ConfigData.shortcutKeysChangeMode[select]));
		drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
		if (PFLM_ConfigData.shortcutKeysChangeMode[select] == modeOthersSettingOffline
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModel
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModelAndColor
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
			s = s.append(getTextureName());
			drawString(s.toString(), guiLeft, guiTop + 100, 0xffffff);
			drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
		}
		if (PFLM_ConfigData.shortcutKeysChangeMode[select] == modeOthersSettingOffline
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetColor
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModelAndColor
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
			s2 = s2.append(getColor());
			drawString(s2.toString(), guiLeft, guiTop + 110, 0xffffff);
			drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
		}
		if (PFLM_ConfigData.shortcutKeysChangeMode[select] == modeOthersSettingOffline
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetModelAndArmor
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetArmor
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
			s1 = s1.append(getTextureArmorName());
			drawString(s1.toString(), guiLeft, guiTop + 120, 0xffffff);
			StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
			Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);
			PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
			s8 = s8.append(PFLM_ConfigData.showArmor);
			drawString(s8.toString(), guiLeft, guiTop + 140, 0xffffff);
			drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
		}
		if (PFLM_ConfigData.shortcutKeysChangeMode[select] == modeOthersSettingOffline
				| PFLM_ConfigData.shortcutKeysChangeMode[select] == modeModelScale) {
			if (modelScaleButton) {
				String s6 = "ModelScale : " + getScale();
				s6 = (new StringBuilder()).append(s6).toString();
				drawString(s6, guiLeft - 120, guiTop + 90, 0xffffff);
				String s7 = "ModelScaleChange";
				s7 = (new StringBuilder()).append(s7).toString();
				drawString(s7, guiLeft - 120, guiTop + 55, 0xffffff);
			}
		}
		if (PFLM_ConfigData.shortcutKeysChangeMode[select] != modePlayerOnline
				&& PFLM_ConfigData.shortcutKeysChangeMode[select] != modePlayerOffline
				&& PFLM_ConfigData.shortcutKeysChangeMode[select] != modeRandom
				&& PFLM_ConfigData.shortcutKeysChangeMode[select] < modeActionRelease) {
			resetFlagCheck(false, false);
			int l = guiLeft;
			int i1 = guiTop;
			ModchuModel_RenderMasterBase.drawMobModel(width, height, i, j, l + 51, i1 + 75, 0, 25, 50F, 0.0F, comeraPosX, comeraPosY, comeraPosZ, comeraRotationX, comeraRotationY, comeraRotationZ, cameraZoom, cameraZoom, cameraZoom, true, drawEntity);
		}
	}

	@Override
	public String getTextureName() {
		return PFLM_ConfigData.shortcutKeysTextureName[select];
	}

	@Override
	public void setTextureName(String s) {
		PFLM_ConfigData.shortcutKeysTextureName[select] = s;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureName, s);
	}

	@Override
	public String getTextureArmorName() {
		return PFLM_ConfigData.shortcutKeysTextureArmorName[select];
	}

	@Override
	public void setTextureArmorName(String s) {
		PFLM_ConfigData.shortcutKeysTextureArmorName[select] = s;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
	}

	@Override
	public int getColor() {
		return PFLM_ConfigData.shortcutKeysMaidColor[select];
	}

	@Override
	public void setColor(int i) {
		PFLM_ConfigData.shortcutKeysMaidColor[select] = i & 0xf;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_maidColor, i & 0xf);
	}

	@Override
	public void setChangeMode(int i) {
		PFLM_ConfigData.shortcutKeysChangeMode[select] = i;
	}

	@Override
	public float getScale() {
		return PFLM_ConfigData.shortcutKeysModelScale[select];
	}

	@Override
	public void setScale(float f) {
		PFLM_ConfigData.shortcutKeysModelScale[select] = f;
	}

	public static String getChangeModeString(int i) {
		String s = null;
		switch (i) {
		case modeOthersSettingOffline:
			s = "AllParameterSetting";
			break;
		case modeSetModelAndArmor:
			s = "ModelAndArmor";
			break;
		case modeSetModelAndColor:
			s = "ModelAndColor";
			break;
		case modeSetModel:
			s = "ModelOnly";
			break;
		case modeSetColor:
			s = "ColorOnly";
			break;
		case modeSetColorAndArmor:
			s = "ColorAndArmor";
			break;
		case modeSetArmor:
			s = "ArmorOnly";
			break;
		case modeModelScale:
			s = "ModelScaleOnly";
			break;
		case modePlayerOffline:
			s = "SetPlayerOffline";
			break;
		case modePlayerOnline:
			s = "SetPlayerOnline";
			break;
		case modeRandom:
			s = "SetRandom";
			break;
		case modeActionRelease:
			s = "ActionRelease";
			break;
		case modeCustomModelCfgReLoad:
			s = "CustomModelCfgReLoad";
			break;
		case modeAllMultiModelActionPlus:
			s = "AllActionPlus";
			break;
		case modeAllMultiModelActionMinus:
			s = "AllActionMinus";
			break;
		case modeAllMultiModelActionModeChangePlus:
			s = "AllActionModeChangePlus";
			break;
		case modeAllMultiModelActionModeChangeMinus:
			s = "AllActionModeChangeMinus";
			break;
		case modeAllMultiModelActionRun:
			s = "AllActionRun";
			break;
		}
		if (i >= modeAction && i <= modeActionLast) {
			int j = i - modeAction + 1;
			s = "Action" + j;
		}
		return s;
	}
}