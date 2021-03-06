package modchu.pflm;

import java.util.HashMap;
import java.util.List;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_IGuiModelView;
import modchu.lib.Modchu_Main;
import modchu.model.ModchuModel_GuiModelSelectMaster;
import modchu.model.ModchuModel_IEntityCaps;
import modchu.model.ModchuModel_RenderMasterBase;
import modchu.model.ModchuModel_TextureManagerBase;
import modchu.model.multimodel.base.MultiModelBaseBiped;

public class PFLM_GuiOthersPlayerMaster extends PFLM_GuiMaster {
	public boolean noSaveFlag;
	public boolean colorReverse;
	public boolean isIndividual;
	public boolean buttonIndividualCustomize;

	public PFLM_GuiOthersPlayerMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void init(HashMap<String, Object> map) {
		super.init(map);
		guiOthersPlayerMasterInit();
	}

	@Override
	public void reInit() {
		super.reInit();
		guiOthersPlayerMasterInit();
	}

	@Override
	protected void guiMasterInitAfter() {
	}

	private void guiOthersPlayerMasterInit() {
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", PFLM_ConfigData.showArmor);
		drawEntitySetFlag = true;
		noSaveFlag = false;
		colorReverse = false;
		isIndividual = false;
		buttonIndividualCustomize = true;
		int changeMode = getChangeMode();
		if (changeMode > PFLM_GuiConstant.otherChangeModelist.size()) setChangeMode(0);
		if (changeMode < 0) setChangeMode(PFLM_GuiConstant.otherChangeModelist.size() - 1);
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
			int skinMode = getSkinMode();
			buttonOnline = skinMode == ModchuModel_IEntityCaps.skinMode_PlayerOnline;
			buttonOffline = skinMode == ModchuModel_IEntityCaps.skinMode_offline;
			buttonRandom = skinMode == ModchuModel_IEntityCaps.skinMode_Random;
			buttonScale = modelScaleButton;
			buttonParts = false;
			buttonPlayer = skinMode == ModchuModel_IEntityCaps.skinMode_PlayerOffline
					| skinMode == ModchuModel_IEntityCaps.skinMode_PlayerOnline
					| skinMode == ModchuModel_IEntityCaps.skinMode_Player
					| skinMode == ModchuModel_IEntityCaps.skinMode_online;
			buttonShowArmor = skinMode == ModchuModel_IEntityCaps.skinMode_offline;
			Modchu_Debug.mDebug("initButtonSetting changeModeString="+PFLM_GuiConstant.getOtherChangeModeString(getChangeMode()));
			Modchu_Debug.mDebug("initButtonSetting buttonOffline="+buttonOffline);
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
			setScale(modelData.models[0] instanceof MultiModelBaseBiped ? ((MultiModelBaseBiped) modelData.models[0]).getModelScale(modelData) : 0.9375F);
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
			PFLM_ConfigData.showArmor = !PFLM_ConfigData.showArmor;
			modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", PFLM_ConfigData.showArmor);
			drawEntitySetFlag = true;
			initGui();
			return;
		}
		//ModelChange
		if(id == 50
				| id == 51) {
			String[] s0 = ModchuModel_TextureManagerBase.instance.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), id == 50 ? 1 : 0, false, PFLM_ConfigData.autoArmorSelect, false);
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
			String[] s0 = ModchuModel_TextureManagerBase.instance.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), id == 54 ? 1 : 0, true, false, PFLM_ConfigData.autoArmorSelect);
			setTextureArmorName(s0[1]);
			modelData.setCapsValue(modelData.caps_textureArmorName, getTextureArmorName());
			noSaveFlag = true;
			drawEntitySetFlag = true;
			return;
		}
		//ModelListSelect
		if(id == 56) {
			Modchu_IGuiModelView gui = (Modchu_IGuiModelView) Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", ModchuModel_GuiModelSelectMaster.class, popWorld, base);
			((ModchuModel_GuiModelSelectMaster) Modchu_Main.getModchuCharacteristicObjectMaster(gui)).drawEntitySetFlag = true;
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
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", PFLM_GuiMaster.class, popWorld));
			return;
		}
		//ChangeMode
		if(id == 13)
		{
			int changeMode = getChangeMode();
			if (isShiftKeyDown) {
				changeMode--;
			} else {
				changeMode++;
			}
			if (changeMode >= PFLM_GuiConstant.otherChangeModelist.size()) changeMode = 0;
			if (changeMode < 0) changeMode = PFLM_GuiConstant.otherChangeModelist.size() - 1;
			setChangeMode(changeMode);
			PFLM_ModelDataMaster.instance.removeDataMap(drawEntity);
			setTextureValue();
			drawEntitySetFlag = true;
			initGui();
			return;
		}
		//IndividualCustomize
		if(id == 401)
		{
			Modchu_Debug.mDebug("PFLM_GuiOthersPlayerMaster id == 401 popWorld="+popWorld);
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiScreen", PFLM_GuiOthersPlayerIndividualCustomizeSelectMaster.class, popWorld));
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
		resetFlagCheck(false, false);
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
		int skinMode = getSkinMode();
		s9 = s9.append(PFLM_GuiConstant.getOtherChangeModeString(getChangeMode()));
		drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
		if(skinMode == ModchuModel_IEntityCaps.skinMode_offline
				| skinMode == ModchuModel_IEntityCaps.skinMode_Random) {
			StringBuilder s10 = (new StringBuilder()).append("Handedness : ");
			s10 = s10.append(getHandednessModeString(getHandednessMode()));
			//if (getHandednessMode() == -1) s10 = s10.append(" Result : ").append(getHandednessModeString(handedness));
			drawString(s10.toString(), guiLeft, guiTop + 140, 0xffffff);
		}
		if (PFLM_ConfigData.useScaleChange
				&& (skinMode == ModchuModel_IEntityCaps.skinMode_offline
				| skinMode == ModchuModel_IEntityCaps.skinMode_Random)
				&& modelScaleButton) {
			String s6 = "modelScale : "+getScale();
			s6 = (new StringBuilder()).append(s6).toString();
			drawString(s6, guiLeft - 140, guiTop + 30, 0xffffff);
			String s7 = "modelScaleChange";
			s7 = (new StringBuilder()).append(s7).toString();
			drawString(s7, guiLeft - 140, guiTop - 5, 0xffffff);
		}
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		boolean localFlag = modelData.getCapsValueInt(modelData.caps_skinMode) == ModchuModel_IEntityCaps.skinMode_local;
		if (localFlag
				| skinMode == ModchuModel_IEntityCaps.skinMode_offline) {
			s = s.append(getTextureName());
			drawString(s.toString(), guiLeft, guiTop + 90, 0xffffff);
			s2 = s2.append(getColor());
			drawString(s2.toString(), guiLeft, guiTop + 100, 0xffffff);
			s1 = s1.append(getTextureArmorName());
			drawString(s1.toString(), guiLeft, guiTop + 110, 0xffffff);
		}
		if(skinMode == ModchuModel_IEntityCaps.skinMode_offline) {
			StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
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

	@Override
	public void setTextureArmorPackege(int i) {
		//Modchu_Debug.mDebug("setTextureArmorPackege textureArmorName="+modelData.getCapsValue(modelData.caps_textureArmorName));
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		String s = ModchuModel_TextureManagerBase.instance.getArmorName((String)modelData.getCapsValue(modelData.caps_textureArmorName), i, false);
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
		Object ltb = ModchuModel_TextureManagerBase.instance.checkTextureArmorPackege(s);
		//Modchu_Debug.mDebug("setTextureArmorPackege s="+s);
		if (ltb != null); else {
			//Modchu_Debug.mDebug("setTextureArmorPackege ltb == null !!");
			modelData.setCapsValue(modelData.caps_textureArmorName, ModchuModel_TextureManagerBase.instance.getDefaultTextureName());
		}
		if (modelData.getCapsValue(modelData.caps_textureArmorName) != null); else {
			//Modchu_Debug.mDebug("setTextureArmorPackege modelData.getCapsValue(modelData.caps_textureArmorName) == null !!");
			modelData.setCapsValue(modelData.caps_textureArmorName, modelData.getCapsValue(modelData.caps_textureName));
		}
	}

	@Override
	public void setArmorTextureValue() {
		if (getTextureArmorName() == null) setTextureArmorName(getTextureName());
		if (ModchuModel_TextureManagerBase.instance.checkTextureArmorPackege(getTextureArmorName()) == null) {
			String s = ModchuModel_TextureManagerBase.instance.getArmorName(getTextureName(), 1, false);
			setTextureArmorName(s);
		}
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", PFLM_ConfigData.showArmor);
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

	public int getSkinMode() {
		return PFLM_GuiConstant.getOtherSkinMode(getChangeMode());
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