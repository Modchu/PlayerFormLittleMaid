package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class PFLM_GuiOthersPlayerIndividualCustomize extends
		PFLM_GuiOthersPlayer {
	public static String playerName;
	public static int othersMaidColor;
	public static float othersModelScale;
	public static String othersTextureName;
	public static String othersTextureArmorName;
	public static int changeMode;
	public static int othersHandednessMode;

	public PFLM_GuiOthersPlayerIndividualCustomize(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		isIndividual = true;
	}

	public PFLM_GuiOthersPlayerIndividualCustomize(PFLM_GuiBase par1GuiScreen, World world, String s) {
		this(par1GuiScreen, world);
		playerName = s;
		PFLM_RenderPlayerDummyMaster.showArmor = true;
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if(!guibutton.enabled)
		{
			return;
		}
		//ModelListSelect
		if(guibutton.id == 56) {
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiModelSelect(this, popWorld, false, getColor(), playerName) });
			//mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 4, playerName));
			return;
		}
		//Save
		if(guibutton.id == 200)
		{
			setPlayerLocalData();
			mod_PFLM_PlayerFormLittleMaid.pflm_main.saveOthersPlayerParamater(true);
			PFLM_Config.clearCfgData();
			noSaveFlag = false;
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ parentScreen });
			//mc.displayGuiScreen(parentScreen);
			mod_PFLM_PlayerFormLittleMaid.pflm_main.clearPlayers();
			return;
		}
		//Return
		if(guibutton.id == 201)
		{
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ parentScreen });
			//mc.displayGuiScreen(parentScreen);
			return;
		}
		//ChangeMode
		if(guibutton.id == 400)
		{
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				changeMode--;
			} else {
				changeMode++;
			}
			if (changeMode > changeModeMax) changeMode = 0;
			if (changeMode < 0) changeMode = changeModeMax;
			//mod_PFLM_PlayerFormLittleMaid.pflm_main.clearPlayers();
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
    	s[2] = ""+getColor();
    	s[3] = ""+othersModelScale;
    	s[4] = ""+changeMode;
    	s[5] = ""+getHandednessMode();
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.playerLocalData.put(playerName, s);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	int xSize = 80;
    	int ySize = 50;
    	int guiLeft = width / 2 - xSize + 30;
    	int guiTop = height / 2 - (ySize / 2) - 20;
    	StringBuilder s10 = (new StringBuilder()).append("playerName : ");
    	s10 = s10.append(playerName);
    	fontRenderer.drawString(s10.toString(), 10, 10, 0xffffff);
    	fontRenderer.drawString("ModelSetting", 10, 20, 0xffffff);
    	StringBuilder s = (new StringBuilder()).append("TextureName : ");
    	StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
    	StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
    	StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
    	s9 = s9.append(getChangeModeString(changeMode));
    	fontRenderer.drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
    	StringBuilder s11 = (new StringBuilder()).append("Handedness : ");
    	s11 = s11.append(getHandednessModeString(getHandednessMode()));
    	//if (getHandednessMode() == -1) s11 = s11.append(" Result : ").append(getHandednessModeString(handedness));
    	fontRenderer.drawString(s11.toString(), guiLeft, guiTop + 140, 0xffffff);
    	if(changeMode == modeOthersSettingOffline) {
    		s = s.append(getTextureName());
    		fontRenderer.drawString(s.toString(), guiLeft, guiTop + 90, 0xffffff);
    		s2 = s2.append(getColor());
    		fontRenderer.drawString(s2.toString(), guiLeft, guiTop + 100, 0xffffff);
    		s1 = s1.append(getTextureArmorName());
    		fontRenderer.drawString(s1.toString(), guiLeft, guiTop + 110, 0xffffff);
    		StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
    		s8 = s8.append(PFLM_RenderPlayerDummyMaster.showArmor);
    		fontRenderer.drawString(s8.toString(), guiLeft, guiTop + 120, 0xffffff);
    		if(mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange
    				&& modelScaleButton) {
    			String s6 = "ModelScale : "+othersModelScale;
    			s6 = (new StringBuilder()).append(s6).toString();
    			fontRenderer.drawString(s6, guiLeft - 120, guiTop + 90, 0xffffff);
    			String s7 = "ModelScaleChange";
    			s7 = (new StringBuilder()).append(s7).toString();
    			fontRenderer.drawString(s7, guiLeft - 120, guiTop + 55, 0xffffff);
    		}
    		fontRenderer.drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
    		fontRenderer.drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
    		fontRenderer.drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
    		if (drawEntitySetFlag) {
    			if (drawEntity == null) drawEntity = new PFLM_EntityPlayerDummy(popWorld);
    			drawEntitySetFlag = false;
    			setTextureValue();
    			PFLM_RenderPlayerDummyMaster.allModelInit(drawEntity, false);
    			drawEntity.setPosition(thePlayer.posX , thePlayer.posY, thePlayer.posZ);
    		}
    		int l = guiLeft;
    		int i1 = guiTop;
    		drawMobModel2(i, j, l + 51, i1 + 75, 0, 25, 50F, 0.0F, true);
    	}
    }

	public String getTextureName() {
		return othersTextureName;
	}

	public void setTextureName(String s) {
		othersTextureName = s;
	}

	public String getTextureArmorName() {
		return othersTextureArmorName;
	}

	public void setTextureArmorName(String s) {
		othersTextureArmorName = s;
	}

	public int getColor() {
		return othersMaidColor;
	}

	public void setColor(int i) {
		othersMaidColor = i & 0xf;
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
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_modelScale, f);
	}

	public void setChangeMode(int i) {
		changeMode = i;
	}
}
