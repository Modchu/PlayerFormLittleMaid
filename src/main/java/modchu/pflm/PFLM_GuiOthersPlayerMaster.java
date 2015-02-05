package modchu.pflm;

import java.util.List;

import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_GuiBase;
import modchu.lib.characteristic.Modchu_GuiModelView;
import modchu.model.ModchuModel_Main;
import modchu.model.multimodel.base.MultiModelBaseBiped;

public class PFLM_GuiOthersPlayerMaster extends PFLM_GuiMaster {
	public boolean noSaveFlag;
	public boolean colorReverse;
	public boolean isIndividual;
	public boolean buttonIndividualCustomize;
	public static final int othersPlayerMaxchangeMode = 5;

	public PFLM_GuiOthersPlayerMaster(Object guiBase, Object guiScreen, Object world, Object... o) {
		super(guiBase, guiScreen, world, (Object[]) o);
	}

	@Override
	public void init(Object guiBase, Object guiScreen, Object world, Object... o) {
		super.init(guiBase, guiScreen, world, (Object[]) o);
		guiOthersPlayerMasterInit();
	}

	@Override
	public void reInit() {
		super.reInit();
		guiOthersPlayerMasterInit();
	}

	private void guiOthersPlayerMasterInit() {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", showArmor);
		drawEntitySetFlag = true;
		noSaveFlag = false;
		colorReverse = false;
		isIndividual = false;
		buttonIndividualCustomize = true;
	}

	@Override
	public void initGui() {
		super.initGui();
		if (!displayButton) return;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		if (buttonIndividualCustomize
				&& !isIndividual) {
			List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
			buttonList.add(newInstanceButton(401, x + 50, y - 5, 100, 15, "IndividualCustomize"));
			Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
		}
	}

	@Override
	protected void initButtonSetting() {
		if (!isIndividual) {
			buttonOnline = getChangeMode() == PFLM_GuiConstant.modePlayerOnline;
			buttonOffline = getChangeMode() == PFLM_GuiConstant.modeOffline;
			buttonRandom = getChangeMode() == PFLM_GuiConstant.modeRandom;
			buttonScale = modelScaleButton;
			buttonParts = false;
			buttonPlayer = getChangeMode() == PFLM_GuiConstant.modePlayerOffline
					| getChangeMode() == PFLM_GuiConstant.modePlayerOnline
					| getChangeMode() == PFLM_GuiConstant.modePlayer
					| getChangeMode() == PFLM_GuiConstant.modeOnline;
			buttonShowArmor = getChangeMode() == PFLM_GuiConstant.modeOffline;
		}
		buttonReturn = true;
		buttonCustomize = false;
		buttonMultiPngSave = false;
		buttonCustomModel = false;
		buttonKeyControls = false;
	}

	@Override
	public void initDrawEntity() {
		super.initDrawEntity();
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
	}

	@Override
	public void actionPerformed(Object guibutton) {
		if (!Modchu_AS.getBoolean(Modchu_AS.guiButtonEnabled, guibutton)) {
			return;
		}
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		//isModelSize Default
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);
		boolean isShiftKeyDown = Modchu_AS.getBoolean(Modchu_AS.isShiftKeyDown);
		boolean isCtrlKeyDown = Modchu_AS.getBoolean(Modchu_AS.isCtrlKeyDown);
		if(id == 3)
		{
			setScale(modelData.modelMain.model instanceof MultiModelBaseBiped ? ((MultiModelBaseBiped) modelData.modelMain.model).getModelScale(modelData) : 0.9375F);
		}
		//isModelSize UP
		if(id == 4)
		{
			if (isShiftKeyDown) {
				setScale(getScale() <= 9.5 ? getScale() + 0.5F : 10.0F);
			} else {
				if (isCtrlKeyDown) {
					setScale(getScale() <= 9.99 ? getScale() + 0.01F : 10.0F);
				} else {
					setScale(getScale() <= 9.9 ? getScale() + 0.1F : 10.0F);
				}
			}
		}
		//isModelSize Down
		if(id == 5)
		{
			if (isShiftKeyDown) {
				setScale(getScale() > 0.5 ? getScale()  - 0.5F : 0.01F);
			}
			else if (isCtrlKeyDown) {
				setScale(getScale() > 0.01 ? getScale()  - 0.01F : 0.01F);
			} else {
				setScale(getScale() > 0.1 ? getScale()  - 0.1F : 0.01F);
			}
		}
		if (id > 2 && id < 6) {
			drawEntitySetFlag = true;
			return;
		}
		//ScaleChange Close
		if(id == 6)
		{
			modelScaleButton = false;
			initGui();
			return;
		}
		//ScaleChange Open
		if(id == 7)
		{
			modelScaleButton = true;
			initGui();
			return;
		}
		//guiMultiPngSaveButton ShowArmor
		if(id == 20)
		{
			showArmor = !showArmor;
			modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", showArmor);
			drawEntitySetFlag = true;
			initGui();
			return;
		}
		//ModelChange
		if(id == 50
				| id == 51) {
			String[] s0 = PFLM_Main.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), id == 50 ? 1 : 0, false, PFLM_ConfigData.autoArmorSelect);
			setTextureName(s0[0]);
			setTextureArmorName(s0[1]);
			modelChange();
			return;
		}
		//ColorChange
		if(id == 52) {
			setColor(getColor() - 1);
			colorReverse = true;
		}
		if(id == 53) {
			setColor(getColor() + 1);
			colorReverse = false;
		}
		if(id == 52
				| id == 53) {
			setColor(colorCheck(getTextureName(), getColor(), colorReverse));
			modelData.setCapsValue(modelData.caps_maidColor, getColor());
			modelData.setCapsValue(modelData.caps_changeColor, modelData);
			noSaveFlag = true;
			drawEntitySetFlag = true;
			return;
		}
		//ArmorChange
		if(id == 54
				| id == 55) {
			String[] s0 = PFLM_Main.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), id == 54 ? 1 : 0, true, PFLM_ConfigData.autoArmorSelect);
			setTextureArmorName(s0[1]);
			modelData.setCapsValue(modelData.caps_textureArmorName, getTextureArmorName());
			noSaveFlag = true;
			drawEntitySetFlag = true;
			return;
		}
		//ModelListSelect
		if(id == 56) {
			Modchu_GuiModelView gui = new Modchu_GuiModelView(PFLM_GuiModelSelectMaster.class, base, popWorld);
			((PFLM_GuiModelSelectMaster) gui.master).drawEntitySetFlag = true;
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, gui);
			return;
		}
		//ArmorListSelect
		if(id == 57) {
			return;
		}
		//Handedness
		if(id == 58) {
			if (isShiftKeyDown) {
				setHandednessMode(getHandednessMode() - 1);
			} else {
				setHandednessMode(getHandednessMode() + 1);
			}
			if (getHandednessMode() < -1) setHandednessMode(1);
			if (getHandednessMode() > 1) setHandednessMode(-1);
			return;
		}
		//Save
		if(id == 200)
		{
			PFLM_ConfigData.showArmor = showArmor;
			PFLM_Main.saveOthersPlayerParamater(false);
			PFLM_Config.clearCfgData();
			PFLM_Main.loadOthersPlayerParamater();
			PFLM_Main.clearDataMap();
			noSaveFlag = false;
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, (Object)null);
			return;
		}
		//Return
		if(id == 201)
		{
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiModelView(PFLM_GuiMaster.class, popWorld));
			return;
		}
		//ChangeMode
		if(id == 13)
		{
			if (isShiftKeyDown) {
				setChangeMode(getChangeMode() - 1);
			} else {
				setChangeMode(getChangeMode() + 1);
			}
			if (getChangeMode() > othersPlayerMaxchangeMode) setChangeMode(0);
			if (getChangeMode() < 0) setChangeMode(othersPlayerMaxchangeMode);
			PFLM_ModelDataMaster.instance.removeDataMap(drawEntity);
			setTextureValue();
			drawEntitySetFlag = true;
			initGui();
			return;
		}
		//IndividualCustomize
		if(id == 401)
		{
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiBase(PFLM_GuiOthersPlayerIndividualCustomizeSelectMaster.class, popWorld));
			return;
		}
	}

	@Override
	public void modelChange() {
		noSaveFlag = true;
		drawEntitySetFlag = true;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		//drawEntitySetFlag = true;
		//textureResetFlag = true;
		resetFlagCheck(true);
		int xSize = 80;
		int ySize = 50;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int guiLeft = width / 2 - xSize + 30;
		int guiTop = height / 2 - (ySize / 2) - 20;
		drawString("othersPlayerModelSetting", width / 2 - 50, height / 2 - 100, 0xffffff);
		StringBuilder s = (new StringBuilder()).append("TextureName : ");
		StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
		StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
		StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
		s9 = s9.append(getChangeModeString(getChangeMode()));
		drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
		if(getChangeMode() == PFLM_GuiConstant.modeOffline
				| getChangeMode() == PFLM_GuiConstant.modeRandom) {
			StringBuilder s10 = (new StringBuilder()).append("Handedness : ");
			s10 = s10.append(getHandednessModeString(getHandednessMode()));
			//if (getHandednessMode() == -1) s10 = s10.append(" Result : ").append(getHandednessModeString(handedness));
			drawString(s10.toString(), guiLeft, guiTop + 140, 0xffffff);
		}
		if (PFLM_ConfigData.useScaleChange
				&& (getChangeMode() == PFLM_GuiConstant.modeOffline
				| getChangeMode() == PFLM_GuiConstant.modeRandom)
				&& modelScaleButton) {
			String s6 = "modelScale : "+getScale();
			s6 = (new StringBuilder()).append(s6).toString();
			drawString(s6, guiLeft - 140, guiTop + 30, 0xffffff);
			String s7 = "modelScaleChange";
			s7 = (new StringBuilder()).append(s7).toString();
			drawString(s7, guiLeft - 140, guiTop - 5, 0xffffff);
		}
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		boolean localFlag = modelData.getCapsValueInt(modelData.caps_skinMode) == PFLM_ModelDataMaster.skinMode_local;
		if (localFlag
				| getChangeMode() == PFLM_GuiConstant.modeOffline) {
/*
			if (changeMode == modefalse
					| changeMode == PFLM_GuiConstant.modePlayerOnline
							| (changeMode == modePlayer
									&& PFLM_Main.changeMode == PFLM_Gui.modeOnline)
					&& !localFlag) {
			} else {
*/
				s = s.append(getTextureName());
				drawString(s.toString(), guiLeft, guiTop + 90, 0xffffff);
				s2 = s2.append(getColor());
				drawString(s2.toString(), guiLeft, guiTop + 100, 0xffffff);
				s1 = s1.append(getTextureArmorName());
				drawString(s1.toString(), guiLeft, guiTop + 110, 0xffffff);
			//}
		}
		if(getChangeMode() == PFLM_GuiConstant.modeOffline) {
			StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
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

	@Override
	public void setTextureArmorPackege(int i) {
		//Modchu_Debug.mDebug("setTextureArmorPackege textureArmorName="+modelData.getCapsValue(modelData.caps_textureArmorName));
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		String s = PFLM_Main.getArmorName((String)modelData.getCapsValue(modelData.caps_textureArmorName), i);
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
		Object ltb = ModchuModel_Main.checkTextureArmorPackege(s);
		//Modchu_Debug.mDebug("setTextureArmorPackege s="+s);
		if (ltb != null); else {
			//Modchu_Debug.mDebug("setTextureArmorPackege ltb == null !!");
			modelData.setCapsValue(modelData.caps_textureArmorName, "default");
		}
		if (modelData.getCapsValue(modelData.caps_textureArmorName) != null); else {
			//Modchu_Debug.mDebug("setTextureArmorPackege modelData.getCapsValue(modelData.caps_textureArmorName) == null !!");
			modelData.setCapsValue(modelData.caps_textureArmorName, modelData.getCapsValue(modelData.caps_textureName));
		}
	}

	@Override
	public void setArmorTextureValue() {
		if (getTextureArmorName() == null) setTextureArmorName(getTextureName());
		if (ModchuModel_Main.checkTextureArmorPackege(getTextureArmorName()) == null) {
			String s = PFLM_Main.getArmorName(getTextureName(), 1);
			setTextureArmorName(s != null && !s.isEmpty() ? s : getTextureArmorName().indexOf("_Biped") == -1 ? "default" : "Biped");
		}
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", showArmor);
	}

	@Override
	public void selected(String textureName, String textureArmorName, int color, boolean armorMode) {
		super.selected(textureName, textureArmorName, color, armorMode);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		if (!armorMode) modelData.setCapsValue(modelData.caps_textureArmorName, modelData.getCapsValue(modelData.caps_textureName));
		modelData.setCapsValue(modelData.caps_changeColor, modelData);
		//drawEntitySetFlag = true;
	}

	@Override
	public String getTextureName() {
		return PFLM_ConfigData.othersTextureName;
	}

	@Override
	public void setTextureName(String s) {
		PFLM_ConfigData.othersTextureName = s;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureName, s);
	}

	@Override
	public String getTextureArmorName() {
		return PFLM_ConfigData.othersTextureArmorName;
	}

	@Override
	public void setTextureArmorName(String s) {
		PFLM_ConfigData.othersTextureArmorName = s;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
	}

	@Override
	public int getColor() {
		return PFLM_ConfigData.othersMaidColor;
	}

	@Override
	public void setColor(int i) {
		PFLM_ConfigData.othersMaidColor = i & 0xf;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_maidColor, i & 0xf);
	}

	@Override
	public float getScale() {
		return PFLM_ConfigData.othersModelScale;
	}

	@Override
	public void setScale(float f) {
		PFLM_ConfigData.othersModelScale = f;
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_modelScale, f);
	}

	@Override
	public int getHandednessMode() {
		return PFLM_ConfigData.othersHandednessMode;
	}

	@Override
	public void setHandednessMode(int i) {
		PFLM_ConfigData.othersHandednessMode = i;
	}

	@Override
	protected int getChangeMode() {
		return PFLM_ConfigData.othersChangeMode;
	}

	@Override
	public void setChangeMode(int i) {
		PFLM_ConfigData.othersChangeMode = i;
	}

	public static String getChangeModeString(int i) {
		String s = null;
		switch (i) {
		case 0:
			s = "modeOthersSettingOffline";
			break;
		case 1:
			s = "modefalse";
			break;
		case 2:
			s = "modeRandom";
			break;
		case 3:
			s = "modePlayer";
			break;
		case 4:
			s = "modePlayerOffline";
			break;
		case 5:
			s = "modePlayerOnline";
			break;
		}
		return s;
	}

	public static String getHandednessModeString(int i) {
		String s = null;
		switch (i) {
		case -1:
			s = "RandomMode";
			break;
		case 0:
			s = "R";
			break;
		case 1:
			s = "L";
			break;
		}
		return s;
	}
}