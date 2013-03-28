package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class PFLM_GuiKeyControls extends
		PFLM_GuiOthersPlayer {

	private static int select;
	public static final int modeOthersSettingOffline 		= 0;
	public static final int modeSetModelAndArmor 		= 1;
	public static final int modeSetModelAndColor			= 2;
	public static final int modeSetModel 						= 3;
	public static final int modeSetColor 						= 4;
	public static final int modeSetColorAndArmor	 		= 5;
	public static final int modeSetArmor 						= 6;
	public static final int modeModelScale 					= 7;
	public static final int modePlayerOffline 					= 8;
	public static final int modePlayerOnline					= 9;
	public static final int modeRandom 						= 10;
	public static final int modeActionRelease				= 11;
	public static final int modeAction 							= 12;
	public static final int modeActionLast 					= 41;
	public static final int modeCustomModelCfgReLoad	= 42;
	public static final int changeModeMax 					= 43;
	private String shortcutKey;

	public PFLM_GuiKeyControls(GuiScreen par1GuiScreen, World world) {
		super(par1GuiScreen, world);
	}

	@Override
	public void initGui() {
		buttonList.clear();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		buttonList.add(new Modchu_GuiSmallButton(403, x + 75, y + 10, 75, 15, "Use Change"));
		buttonList.add(new Modchu_GuiSmallButton(200, x, y + 115, 75, 20, "Save"));
		buttonList.add(new Modchu_GuiSmallButton(201, x + 75, y + 115, 75, 20, "Return"));
		buttonList.add(new Modchu_GuiSmallButton(407, x + 75, y - 6, 20, 15, "-10"));
		buttonList.add(new Modchu_GuiSmallButton(401, x + 95, y - 6, 15, 15, "-"));
		buttonList.add(new Modchu_GuiSmallButton(402, x + 110, y - 6, 15, 15, "+"));
		buttonList.add(new Modchu_GuiSmallButton(408, x + 125, y - 6, 20, 15, "+10"));
		if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysUse[select]) {
			buttonList.add(new Modchu_GuiSmallButton(404, x + 75, y + 70, 75, 15, "Use ModelsKey"));
			buttonList.add(new Modchu_GuiSmallButton(405, x + 75, y + 85, 75, 15, "Use CtrlKey"));
			buttonList.add(new Modchu_GuiSmallButton(406, x + 75, y + 100, 75, 15, "Use ShiftKey"));
			buttonList.add(new Modchu_GuiSmallButton(400, x + 75, y + 25, 75, 15, "ChangeMode"));
			if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeOthersSettingOffline
					| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModel
					| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModelAndColor
					| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
				buttonList.add(new Modchu_GuiSmallButton(56, x - 10, y + 10, 85, 15, "ModelListSelect"));
				buttonList.add(new Modchu_GuiSmallButton(50, x + 40, y + 25, 15, 15, "<"));
				buttonList.add(new Modchu_GuiSmallButton(51, x + 55, y + 25, 15, 15, ">"));
			}
			if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeOthersSettingOffline
					| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetColor
					| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModelAndColor
	    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
				buttonList.add(new Modchu_GuiSmallButton(52, x + 40, y + 40, 15, 15, "-"));
				buttonList.add(new Modchu_GuiSmallButton(53, x + 55, y + 40, 15, 15, "+"));
			}
			if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeOthersSettingOffline
					| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetArmor
	    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetColorAndArmor
					| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
				buttonList.add(new Modchu_GuiSmallButton(54, x + 40, y + 55, 15, 15, "<"));
				buttonList.add(new Modchu_GuiSmallButton(55, x + 55, y + 55, 15, 15, ">"));
				buttonList.add(new Modchu_GuiSmallButton(20, x, y + 70, 75, 15, "showArmor"));
			}
			if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeOthersSettingOffline
					| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeModelScale) {
				if(modelScaleButton) {
					buttonList.add(new Modchu_GuiSmallButton(3, width / 2 - 140, height / 2 + 20, 50, 20, "Default"));
					buttonList.add(new Modchu_GuiSmallButton(4, width / 2 - 90, height / 2 + 20, 30, 20, "UP"));
					buttonList.add(new Modchu_GuiSmallButton(5, width / 2 - 170, height / 2 + 20, 30, 20, "Down"));
					buttonList.add(new Modchu_GuiSmallButton(6, x + 75, y + 40, 75, 15, "Close"));
				} else {
					buttonList.add(new Modchu_GuiSmallButton(7, x + 75, y + 40, 75, 15, "ScaleChange"));
				}
			}
			if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] >= modeAction
					&& mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] <= modeActionLast) {
				buttonList.add(new Modchu_GuiSmallButton(409, x + 50, y + 165, 15, 15, "+"));
				buttonList.add(new Modchu_GuiSmallButton(410, x + 35, y + 165, 15, 15, "-"));
				buttonList.add(new Modchu_GuiSmallButton(411, x + 65, y + 165, 20, 15, "+10"));
				buttonList.add(new Modchu_GuiSmallButton(412, x + 15, y + 165, 20, 15, "-10"));
			}
		}
		setTextureValue();
		selectInit();
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
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] = ((MultiModelBaseBiped) mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[0]).getModelScale();
    	}
    	//isModelSize UP
    	if(guibutton.id == 4)
    	{
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] += mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] <= 9.5 ? 0.5F : 0;
    		} else {
    			if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
    				mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] += mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] <= 9.99 ? 0.01F : 0;
    			} else {
    				mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] += mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] <= 9.9 ? 0.1F : 0;
    			}
    		}
    	}
    	//isModelSize Down
    	if(guibutton.id == 5)
    	{
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] -= mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] > 0.5 ? 0.5F : 0;
    		} else {
    			if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
    				mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] -= mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] > 0.01 ? 0.01F : 0;
    			} else {
    				mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] -= mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] > 0.1 ? 0.1F : 0;
    			}
    		}
    	}
    	//ScaleChange Close
    	if(guibutton.id == 6)
    	{
    		modelScaleButton = false;
    		initGui();
    	}
    	//ScaleChange Open
    	if(guibutton.id == 7)
    	{
    		modelScaleButton = true;
    		initGui();
    	}
    	//guiMultiPngSaveButton ShowArmor
    	if(guibutton.id == 20)
    	{
    		showArmor = !showArmor;
    		initGui();
    	}
    	//ModelChange
    	if(guibutton.id == 50) setPrevTexturePackege(0);
    	if(guibutton.id == 51) setNextTexturePackege(0);
    	if(guibutton.id == 50
    			| guibutton.id == 51)
    	{
    		modelChange();
    	}
    	//ColorChange
    	if(guibutton.id == 52) {
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysMaidColor[select]--;
    		colorReverse = true;
    	}
    	if(guibutton.id == 53) {
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysMaidColor[select]++;
    		colorReverse = false;
    	}
    	if(guibutton.id == 52
    			| guibutton.id == 53)
    	{
    		setMaidColor(mod_PFLM_PlayerFormLittleMaid.shortcutKeysMaidColor[select]);
    		setColorTextureValue();
    		noSaveFlag = true;
    		mod_PFLM_PlayerFormLittleMaid.setResetFlag(true);
    	}
    	//ArmorChange
    	if(guibutton.id == 54) setPrevTexturePackege(1);
    	if(guibutton.id == 55) setNextTexturePackege(1);
    	if(guibutton.id == 54
    			| guibutton.id == 55)
    	{
    		setTextureValue();
    		noSaveFlag = true;
    	}
    	//ModelListSelect
    	if(guibutton.id == 56) {
    		mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 6, select));
    	}
    	//Save
    	if(guibutton.id == 200)
    	{
    		mod_PFLM_PlayerFormLittleMaid.saveShortcutKeysPlayerParamater();
    		Modchu_Config.clearCfgData();
    		noSaveFlag = false;
    		mc.displayGuiScreen(null);
    		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
    	}
    	//Return
    	if(guibutton.id == 201)
    	{
    		mc.displayGuiScreen(parentScreen);
    	}
    	//ChangeMode
    	if(guibutton.id == 400)
    	{
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select]--;
    			if ((mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] > modeAction
    					&& mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] <= modeActionLast + 1)
    					//TODO modeを増やしたときに問題ないかチェックする
    					//&& changeModeMax == modeActionLast + 1
    					) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = modeAction;
    			if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] < 0) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = changeModeMax - 1;
    		} else {
    			mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select]++;
    			if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] > modeAction
    					&& mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] <= modeActionLast) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = modeActionLast + 1;
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] >= changeModeMax) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = 0;
    		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] < 0) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = changeModeMax;
    		//Modchu_Debug.mDebug("mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select]="+mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select]);
    		initGui();
    	}
    	//select--
    	if(guibutton.id == 401)
    	{
    		select = select > 0 ? --select : mod_PFLM_PlayerFormLittleMaid.maxShortcutKeys - 1;
    		initGui();
    	}
    	//select++
    	if(guibutton.id == 402)
    	{
    		select = select < mod_PFLM_PlayerFormLittleMaid.maxShortcutKeys - 1 ? ++select : 0;
    		initGui();
    	}
    	//use Change
    	if(guibutton.id == 403)
    	{
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysUse[select] = !mod_PFLM_PlayerFormLittleMaid.shortcutKeysUse[select];
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
    		initGui();
    	}
    	//use ModelsKey
    	if(guibutton.id == 404)
    	{
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysPFLMModelsUse[select] = !mod_PFLM_PlayerFormLittleMaid.shortcutKeysPFLMModelsUse[select];
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
    		initGui();
    	}
    	//use Ctrl
    	if(guibutton.id == 405)
    	{
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysCtrlUse[select] = !mod_PFLM_PlayerFormLittleMaid.shortcutKeysCtrlUse[select];
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
    		initGui();
    	}
    	//use Shift
    	if(guibutton.id == 406)
    	{
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysShiftUse[select] = !mod_PFLM_PlayerFormLittleMaid.shortcutKeysShiftUse[select];
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
    		initGui();
    	}
    	//select-10
    	if(guibutton.id == 407)
    	{
    		select = select > 10 ? select - 10 : 0;
    		initGui();
    	}
    	//select+10
    	if(guibutton.id == 408)
    	{
    		select = select < mod_PFLM_PlayerFormLittleMaid.maxShortcutKeys - 10 ? select + 10 : mod_PFLM_PlayerFormLittleMaid.maxShortcutKeys - 1;
    		initGui();
    	}
    	//Action++
    	if(guibutton.id == 409)
    	{
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select]++;
    		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] > modeActionLast) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = modeAction;
    		initGui();
    	}
    	//Action--
    	if(guibutton.id == 410)
    	{
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select]--;
    		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] < modeAction) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = modeActionLast;
    		initGui();
    	}
    	//Action+10
    	if(guibutton.id == 411)
    	{
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] + 10;
    		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] > modeActionLast) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = modeActionLast;
    		initGui();
    	}
    	//Action-10
    	if(guibutton.id == 412)
    	{
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] - 10;
    		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] < modeAction) mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = modeAction;
    		initGui();
    	}
    }

    public void selectInit() {
    	shortcutKey = "";
    	String s = "key.ModelChange"+select;
    	shortcutKey = getKeyDisplayString(s);
    	if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysPFLMModelsUse[select]) {
    		s = "key.PFLM Models Key";
    		shortcutKey = new StringBuilder().append(getKeyDisplayString(s)).append(" + ").append(shortcutKey).toString();
    	}
    	if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysCtrlUse[select]) {
    		shortcutKey = new StringBuilder().append(GameSettings.getKeyDisplayString(29)).append(" or ").append(GameSettings.getKeyDisplayString(157)).append(" + ").append(shortcutKey).toString();
    	}
    	if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysShiftUse[select]) {
    		shortcutKey = new StringBuilder().append(GameSettings.getKeyDisplayString(42)).append(" or ").append(GameSettings.getKeyDisplayString(54)).append(" + ").append(shortcutKey).toString();
    	}
    }

    public String getKeyDisplayString(String s) {
    	KeyBinding keyBinding;
    	for(int i = 0; i < KeyBinding.keybindArray.size() ; i++) {
    		keyBinding = (KeyBinding) KeyBinding.keybindArray.get(i);
    		if (keyBinding.keyDescription.equalsIgnoreCase(s)) {
    			return GameSettings.getKeyDisplayString(keyBinding.keyCode);
    		}
    	}
    	return null;
    }

	public void modelChange() {
    	setTextureValue();
    	noSaveFlag = true;
    }

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	int xSize = 80;
    	int ySize = 50;
    	int guiLeft = width / 2 - xSize + 30;
    	int guiTop = height / 2 - (ySize / 2) - 20;
    	fontRenderer.drawString("KeyControlsSetting", width / 2 - 50, height / 2 - 110, 0xffffff);
    	fontRenderer.drawString((new StringBuilder()).append("ShortcutKey = ").append(shortcutKey).toString(), width / 2 - 150, height / 2 - 100, 0xffffff);
    	fontRenderer.drawString("SettingNumber", width / 2 + 40, height / 2 - 88, 0xffffff);
    	String use = mod_PFLM_PlayerFormLittleMaid.shortcutKeysUse[select] ? "use" : "unused";
    	StringBuilder s10 = (new StringBuilder()).append("shortcutKey : ");
    	s10 = s10.append(use);
    	fontRenderer.drawString(s10.toString(), guiLeft, guiTop + 150, 0xffffff);
    	fontRenderer.drawString(""+select, width / 2 + 115, height / 2 - 88, 0xffffff);
    	if (!mod_PFLM_PlayerFormLittleMaid.shortcutKeysUse[select]) return;
    	StringBuilder s = (new StringBuilder()).append("TextureName : ");
    	StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
    	StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
    	StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
    	s9 = s9.append(getChangeModeString(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select]));
    	fontRenderer.drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
    	if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeOthersSettingOffline
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModel
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModelAndColor
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
    		s = s.append(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select]);
    		fontRenderer.drawString(s.toString(), guiLeft, guiTop + 100, 0xffffff);
        	fontRenderer.drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
    	}
    	if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeOthersSettingOffline
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetColor
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModelAndColor
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
    		s2 = s2.append(mod_PFLM_PlayerFormLittleMaid.shortcutKeysMaidColor[select]);
    		fontRenderer.drawString(s2.toString(), guiLeft, guiTop + 110, 0xffffff);
    		fontRenderer.drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
    	}
    	if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeOthersSettingOffline
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetModelAndArmor
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetArmor
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
    		s1 = s1.append(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select]);
    		fontRenderer.drawString(s1.toString(), guiLeft, guiTop + 120, 0xffffff);
    		StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
    		s8 = s8.append(showArmor);
    		fontRenderer.drawString(s8.toString(), guiLeft, guiTop + 140, 0xffffff);
    		fontRenderer.drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
    	}
    	if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeOthersSettingOffline
    			| mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] == modeModelScale) {
    		if(modelScaleButton) {
    			String s6 = "ModelScale : "+mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select];
    			s6 = (new StringBuilder()).append(s6).toString();
    			fontRenderer.drawString(s6, guiLeft - 120, guiTop + 90, 0xffffff);
    			String s7 = "ModelScaleChange";
    			s7 = (new StringBuilder()).append(s7).toString();
    			fontRenderer.drawString(s7, guiLeft - 120, guiTop + 55, 0xffffff);
    		}
    	}
    	if(mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] != modePlayerOnline
    			&& mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] != modePlayerOffline
    			&& mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] != modeRandom
    			&& mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] < modeActionRelease) {
    		if (drawEntity == null) drawEntity = new PFLM_EntityPlayerDummy(popWorld);
    		setTextureValue();
    		((EntityLiving) drawEntity).texture = mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select];
    		((PFLM_EntityPlayerDummy) drawEntity).textureModel = mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel;
    		((PFLM_EntityPlayerDummy) drawEntity).maidColor = mod_PFLM_PlayerFormLittleMaid.shortcutKeysMaidColor[select];
    		((PFLM_EntityPlayerDummy) drawEntity).textureName = mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select];
    		((PFLM_EntityPlayerDummy) drawEntity).textureArmorName = mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select];
    		((PFLM_EntityPlayerDummy) drawEntity).textureArmor0 = mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmor0;
    		((PFLM_EntityPlayerDummy) drawEntity).textureArmor1 = mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmor1;
    		((PFLM_EntityPlayerDummy) drawEntity).modelScale = mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select];
    		((PFLM_EntityPlayerDummy) drawEntity).showArmor = showArmor;
    		//Modchu_Debug.mDebug("textureName="+shortcutKeysTextureName);
    		//Modchu_Debug.mDebug("texture="+shortcutKeysTexture);
    		//Modchu_Debug.mDebug("textureArmorName="+shortcutKeysTextureArmorName);
    		drawEntity.setPosition(mc.thePlayer.posX , mc.thePlayer.posY, mc.thePlayer.posZ);
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		int l = guiLeft;
    		int i1 = guiTop;
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
    		float f5 = (float)(l + 51) - (float)xSize_lo;
    		float f6 = (float)((i1 + 75) - 50) - (float)ySize_lo;
    		GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
    		RenderHelper.enableStandardItemLighting();
    		GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
    		GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 0.0F, 1.0F, 0.0F);
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
    	}
    }

	public void setTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select] == null) {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select] = "default";
		}
		int i = getMaidColor();

		mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select], i);
		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] == null) {
			int n = 0;
			for (; n < mod_PFLM_PlayerFormLittleMaid.maxShortcutKeys && mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] == null; n = n + 1) {
				i++;
				i = i & 0xf;
				setMaidColor(i);
				mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select], i);
			}
			if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] == null) {
				setNextTexturePackege(0);
				mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select], i);
			}
		}
		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel != null) {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[0] = null;
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[1] = null;
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[2] = null;
		} else {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel = new Object[3];
		}
		Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select]);
		if (ltb != null) mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[0] = mod_PFLM_PlayerFormLittleMaid.getTextureBoxModels(ltb)[0];
		setArmorTextureValue();
	}

	public void setColorTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select] == null) {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select] = "default";
		}
		int i = getMaidColor();
		String t = mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select];
		mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select], i);
		for (int n = 0; n < mod_PFLM_PlayerFormLittleMaid.maxShortcutKeys && mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] == null; n = n + 1) {
			if (PFLM_Gui.colorReverse) {
				i--;
			} else {
				i++;
			}
			i = i & 0xf;
			setMaidColor(i);
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select], i);
		}
		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] == null) mod_PFLM_PlayerFormLittleMaid.shortcutKeysTexture[select] = t;
	}

	public void setArmorTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select] == null) {
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select]));
			if (mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select] == null) {
				mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select] = "default";
			}
		}
		Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select]);
		Object[] models = mod_PFLM_PlayerFormLittleMaid.getTextureBoxModels(ltb);
		if (ltb != null) {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[1] = models[1];
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[2] = models[2];
		} else {
			ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox("default");
			if (ltb != null) {
				mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[1] = models[1];
				mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureModel[2] = models[2];
			}
		}
	}

	public static void setNextTexturePackege(int i) {
		if (i == 0) {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select] =
					mod_PFLM_PlayerFormLittleMaid.textureManagerGetNextPackege(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select], getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select]));
		}
		if (i == 1) {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetNextArmorPackege(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select]);
		}
	}

	public static void setPrevTexturePackege(int i) {
		if (i == 0) {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select] =
					mod_PFLM_PlayerFormLittleMaid.textureManagerGetPrevPackege(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select], getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select]));
		}
		if (i == 1) {
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetPrevArmorPackege(mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select]);
		}
	}

    public static int getMaidColor()
    {
    	return mod_PFLM_PlayerFormLittleMaid.shortcutKeysMaidColor[select];
    }

    public static void setMaidColor(int i)
    {
    	mod_PFLM_PlayerFormLittleMaid.shortcutKeysMaidColor[select] = i & 0xf;
    }

    public static void setTextureName(String s) {
    	mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select] = s;
    }

    public static void setTextureArmorName(String s) {
    	mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select] = s;
    }

    public static void setModelScale(float f)
    {
    	mod_PFLM_PlayerFormLittleMaid.shortcutKeysModelScale[select] = f;
    }

	public static void setChangeMode(int i) {
		mod_PFLM_PlayerFormLittleMaid.shortcutKeysChangeMode[select] = i;
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
		}
		if (i >= modeAction
				&& i <= modeActionLast) {
			int j = i - modeAction + 1;
			s = "Action"+j;
		}
		return s;
	}
}
