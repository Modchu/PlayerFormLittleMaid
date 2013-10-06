package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class PFLM_GuiOthersPlayer extends PFLM_GuiModelSelectBase {
	protected boolean modelScaleButton;
	protected boolean noSaveFlag = false;
	protected boolean colorReverse = false;
	protected boolean isIndividual = false;
	public static int changeMode = 0;
	public static final int modefalse							= 0;
	public static final int modePlayer						= 1;
	public static final int modeOthersSettingOffline 	= 2;
	public static final int modePlayerOffline				= 3;
	public static final int modePlayerOnline				= 4;
	public static final int modeRandom						= 5;
	public static final int changeModeMax =	5;
	public EntityPlayer thePlayer;

	public PFLM_GuiOthersPlayer(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		PFLM_RenderPlayerDummyMaster.showArmor = true;
		setTextureValue();
	}

	@Override
	public void initGui() {
		if (!isIndividual) {
			buttonOnline = changeMode == modePlayerOnline;
			buttonRandom = changeMode == modeRandom;
			buttonScale = modelScaleButton;
			buttonParts = false;
			buttonPlayer = changeMode == modePlayerOffline
					| changeMode == modePlayerOnline
					| changeMode == modePlayer
					| changeMode == modefalse;
			buttonShowArmor = changeMode == modeOthersSettingOffline;
		}
		buttonReturn = true;
		super.initGui();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		if (!isIndividual) {
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 401, x + 50, y - 5, 100, 15, "IndividualCustomize" }));
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if(!guibutton.enabled)
		{
			return;
		}
		//isModelSize Default
		if(guibutton.id == 3)
		{
			setScale(PFLM_RenderPlayerDummyMaster.modelData.modelMain.model instanceof MultiModelBaseBiped ? ((MultiModelBaseBiped) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).getModelScale(PFLM_RenderPlayerDummyMaster.modelData) : 0.9375F);
		}
		//isModelSize UP
		if(guibutton.id == 4)
		{
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				setScale(getScale() <= 9.5 ? getScale() + 0.5F : 10.0F);
			} else {
				if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
					setScale(getScale() <= 9.99 ? getScale() + 0.01F : 10.0F);
				} else {
					setScale(getScale() <= 9.9 ? getScale() + 0.1F : 10.0F);
				}
			}
		}
		//isModelSize Down
		if(guibutton.id == 5)
		{
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				setScale(getScale() > 0.5 ? getScale()  - 0.5F : 0.01F);
			}
			else if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
				setScale(getScale() > 0.01 ? getScale()  - 0.01F : 0.01F);
			} else {
				setScale(getScale() > 0.1 ? getScale()  - 0.1F : 0.01F);
			}
		}
		if (guibutton.id > 2 && guibutton.id < 6) {
			drawEntitySetFlag = true;
			return;
		}
		//ScaleChange Close
		if(guibutton.id == 6)
		{
			modelScaleButton = false;
			initGui();
			return;
		}
		//ScaleChange Open
		if(guibutton.id == 7)
		{
			modelScaleButton = true;
			initGui();
			return;
		}
		//guiMultiPngSaveButton ShowArmor
		if(guibutton.id == 20)
		{
			PFLM_RenderPlayerDummyMaster.showArmor = !PFLM_RenderPlayerDummyMaster.showArmor;
			drawEntitySetFlag = true;
			initGui();
			return;
		}
		//ModelChange
		if(guibutton.id == 50
				| guibutton.id == 51) {
			String[] s0 = mod_PFLM_PlayerFormLittleMaid.pflm_main.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), guibutton.id == 50 ? 1 : 0, false);
			setTextureName(s0[0]);
			setTextureArmorName(s0[1]);
			modelChange();
			return;
		}
		//ColorChange
		if(guibutton.id == 52) {
			setColor(getColor() - 1);
			colorReverse = true;
		}
		if(guibutton.id == 53) {
			setColor(getColor() + 1);
			colorReverse = false;
		}
		if(guibutton.id == 52
				| guibutton.id == 53) {
			setColor(colorCheck(getTextureName(), getColor(), colorReverse));
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, getColor());
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_changeColor, PFLM_RenderPlayerDummyMaster.modelData);
			noSaveFlag = true;
			drawEntitySetFlag = true;
			return;
		}
		//ArmorChange
		if(guibutton.id == 54
				| guibutton.id == 55) {
			String[] s0 = mod_PFLM_PlayerFormLittleMaid.pflm_main.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), guibutton.id == 54 ? 1 : 0, true);
			setTextureArmorName(s0[1]);
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, getTextureArmorName());
			noSaveFlag = true;
			drawEntitySetFlag = true;
			PFLM_ModelDataMaster.instance.setResetFlag(true);
			return;
		}
		//ModelListSelect
		if(guibutton.id == 56) {
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiModelSelect(this, popWorld, false, getColor()) });
			//mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 2));
			return;
		}
		//ArmorListSelect
		if(guibutton.id == 57) {
			return;
		}
		//Handedness
		if(guibutton.id == 58) {
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				setHandednessMode(getHandednessMode() - 1);
			} else {
				setHandednessMode(getHandednessMode() + 1);
			}
			if (getHandednessMode() < -1) setHandednessMode(1);
			if (getHandednessMode() > 1) setHandednessMode(-1);
			return;
		}
		//Save
		if(guibutton.id == 200)
		{
			mod_PFLM_PlayerFormLittleMaid.pflm_main.saveOthersPlayerParamater(false);
			PFLM_Config.clearCfgData();
			mod_PFLM_PlayerFormLittleMaid.pflm_main.clearPlayers();
			noSaveFlag = false;
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ null });
			//mc.displayGuiScreen(null);
			return;
		}
		//Return
		if(guibutton.id == 201)
		{
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_Gui(popWorld) });
			//mc.displayGuiScreen(new PFLM_Gui(popWorld));
			return;
		}
		//ChangeMode
		if(guibutton.id == 13)
		{
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				changeMode--;
			} else {
				changeMode++;
			}
			if (changeMode > changeModeMax) changeMode = 0;
			if (changeMode < 0) changeMode = changeModeMax;
			mod_PFLM_PlayerFormLittleMaid.pflm_main.clearPlayers();
			drawEntitySetFlag = true;
			initGui();
			return;
		}
		//IndividualCustomize
		if(guibutton.id == 401)
		{
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiOthersPlayerIndividualCustomizeSelect(this, popWorld) });
			//mc.displayGuiScreen(new PFLM_GuiOthersPlayerIndividualCustomizeSelect(this, mc, popWorld));
			return;
		}
	}

	public void modelChange() {
		setTextureValue();
		noSaveFlag = true;
		drawEntitySetFlag = true;
		PFLM_ModelDataMaster.instance.setResetFlag(true);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int xSize = 80;
		int ySize = 50;
		int guiLeft = width / 2 - xSize + 30;
		int guiTop = height / 2 - (ySize / 2) - 20;
		fontRenderer.drawString("othersPlayerModelSetting", width / 2 - 50, height / 2 - 100, 0xffffff);
		StringBuilder s = (new StringBuilder()).append("TextureName : ");
		StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
		StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
		StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
		s9 = s9.append(getChangeModeString(changeMode));
		fontRenderer.drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
		if(changeMode == modeOthersSettingOffline
				| changeMode == modeRandom) {
			StringBuilder s10 = (new StringBuilder()).append("Handedness : ");
			s10 = s10.append(getHandednessModeString(getHandednessMode()));
			//if (getHandednessMode() == -1) s10 = s10.append(" Result : ").append(getHandednessModeString(handedness));
			fontRenderer.drawString(s10.toString(), guiLeft, guiTop + 140, 0xffffff);
		}
		if(mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange
				&& modelScaleButton) {
			String s6 = "modelScale : "+getScale();
			s6 = (new StringBuilder()).append(s6).toString();
			fontRenderer.drawString(s6, guiLeft - 140, guiTop + 30, 0xffffff);
			String s7 = "modelScaleChange";
			s7 = (new StringBuilder()).append(s7).toString();
			fontRenderer.drawString(s7, guiLeft - 140, guiTop - 5, 0xffffff);
		}
		boolean localFlag = PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_skinMode) == PFLM_ModelDataMaster.instance.skinMode_local;
		if (localFlag
				| changeMode == modeOthersSettingOffline) {
/*
			if (changeMode == modefalse
					| changeMode == modePlayerOnline
							| (changeMode == modePlayer
									&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline)
					&& !localFlag) {
			} else {
*/
				s = s.append(getTextureName());
				fontRenderer.drawString(s.toString(), guiLeft, guiTop + 90, 0xffffff);
				s2 = s2.append(getColor());
				fontRenderer.drawString(s2.toString(), guiLeft, guiTop + 100, 0xffffff);
				s1 = s1.append(getTextureArmorName());
				fontRenderer.drawString(s1.toString(), guiLeft, guiTop + 110, 0xffffff);
			//}
		}
		if(changeMode == modeOthersSettingOffline) {
			StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
			s8 = s8.append(PFLM_RenderPlayerDummyMaster.showArmor);
			fontRenderer.drawString(s8.toString(), guiLeft, guiTop + 120, 0xffffff);
			fontRenderer.drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
			fontRenderer.drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
			fontRenderer.drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
			if (drawEntitySetFlag) {
				drawEntitySetFlag = false;
				mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.allModelInit(drawEntity, false);
				drawEntity.setPosition(thePlayer.posX , thePlayer.posY, thePlayer.posZ);
			}
			int l = guiLeft;
			int i1 = guiTop;
			drawMobModel2(i, j, l + 51, i1 + 75, 0, 25, 50F, 0.0F, true);
		}
	}

	@Override
	public void setTextureArmorPackege(int i) {
		//Modchu_Debug.mDebug("setTextureArmorPackege textureArmorName="+PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName));
		String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName), i);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
		Object ltb = mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege(s);
		//Modchu_Debug.mDebug("setTextureArmorPackege s="+s);
		if (ltb != null) ;else {
			//Modchu_Debug.mDebug("setTextureArmorPackege ltb == null !!");
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, "default");
		}
		if (PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName) != null) ;else {
			//Modchu_Debug.mDebug("setTextureArmorPackege PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName) == null !!");
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
		}
	}

	public void setArmorTextureValue() {
		if (getTextureArmorName() == null) setTextureArmorName(getTextureName());
		if (mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege(getTextureArmorName()) == null) {
			String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName(getTextureName(), 1);
			setTextureArmorName(s != null && !s.isEmpty() ? s : getTextureArmorName().indexOf("_Biped") == -1 ? "default" : "Biped");
		}
		PFLM_RenderPlayerDummyMaster.showArmor = true;
	}

	public void selected(String textureName, String textureArmorName, int color, boolean armorMode) {
		super.selected(textureName, textureArmorName, color, armorMode);
		if (!armorMode) PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, getTextureName());
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, getTextureArmorName());
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, getColor());
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_changeColor, PFLM_RenderPlayerDummyMaster.modelData);
		//PFLM_RenderPlayerDummyMaster.showArmor = true;
		//drawEntitySetFlag = true;
		//mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.allModelInit(drawEntity, false);
	}

	public String getTextureName() {
		return mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName;
	}

	public void setTextureName(String s) {
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName = s;
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, s);
	}

	public String getTextureArmorName() {
		return mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName;
	}

	public void setTextureArmorName(String s) {
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName = s;
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
	}

	public int getColor() {
		return mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor;
	}

	public void setColor(int i) {
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor = i & 0xf;
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, i & 0xf);
	}

	public float getScale() {
		return mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale;
	}

	public void setScale(float f) {
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale = f;
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_modelScale, f);
	}

	public int getHandednessMode() {
		return mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode;
	}

	public void setHandednessMode(int i) {
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode = i;
	}

	@Override
	public void memoryRelease() {
	}

	public static String getChangeModeString(int i) {
		String s = null;
		switch (i) {
		case 0:
			s = "modefalse";
			break;
		case 1:
			s = "modePlayer";
			break;
		case 2:
			s = "modeOthersSettingOffline";
			break;
		case 3:
			s = "modePlayerOffline";
			break;
		case 4:
			s = "modePlayerOnline";
			break;
		case 5:
			s = "modeRandom";
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
