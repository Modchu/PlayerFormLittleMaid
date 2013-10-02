package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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

	public PFLM_GuiKeyControls(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
	}

	@Override
	public void initGui() {
		buttonList.clear();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 403, x + 75, y + 10, 75, 15, "Use Change" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 200, x, y + 115, 75, 20, "Save" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 201, x + 75, y + 115, 75, 20, "Return" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 407, x + 75, y - 6, 20, 15, "-10" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 401, x + 95, y - 6, 15, 15, "-" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 402, x + 110, y - 6, 15, 15, "+" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 408, x + 125, y - 6, 20, 15, "+10" }));
		if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysUse[select]) {
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 404, x + 75, y + 70, 75, 15, "Use ModelsKey" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 405, x + 75, y + 85, 75, 15, "Use CtrlKey" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 406, x + 75, y + 100, 75, 15, "Use ShiftKey" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 400, x + 75, y + 25, 75, 15, "ChangeMode" }));
			if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeOthersSettingOffline
					| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModel
					| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModelAndColor
					| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 56, x - 10, y + 10, 85, 15, "ModelListSelect" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 50, x + 40, y + 25, 15, 15, "<" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 51, x + 55, y + 25, 15, 15, ">" }));
			}
			if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeOthersSettingOffline
					| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetColor
					| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModelAndColor
	    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 52, x + 40, y + 40, 15, 15, "-" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 53, x + 55, y + 40, 15, 15, "+" }));
			}
			if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeOthersSettingOffline
					| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetArmor
	    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetColorAndArmor
					| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 54, x + 40, y + 55, 15, 15, "<" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 55, x + 55, y + 55, 15, 15, ">" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 20, x, y + 85, 75, 15, "showArmor" }));
			}
			if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeOthersSettingOffline
					| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeModelScale) {
				if(modelScaleButton) {
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 3, width / 2 - 140, height / 2 + 20, 50, 20, "Default" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 4, width / 2 - 90, height / 2 + 20, 30, 20, "UP" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 5, width / 2 - 170, height / 2 + 20, 30, 20, "Down" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 6, x + 75, y + 40, 75, 15, "Close" }));
				} else {
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 7, x + 75, y + 40, 75, 15, "ScaleChange" }));
				}
			}
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] >= modeAction
					&& mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] <= modeActionLast) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 409, x + 50, y + 165, 15, 15, "+" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 410, x + 35, y + 165, 15, 15, "-" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 411, x + 65, y + 165, 20, 15, "+10" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 412, x + 15, y + 165, 20, 15, "-10" }));
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
    	//Save
    	if(guibutton.id == 200)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.saveShortcutKeysPlayerParamater();
    		PFLM_Config.clearCfgData();
    		noSaveFlag = false;
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ null });
    		//mc.displayGuiScreen(null);
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
    			mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select]--;
    			if ((mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] > modeAction
    					&& mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] <= modeActionLast + 1)
    					//TODO modeを増やしたときに問題ないかチェックする
    					//&& changeModeMax == modeActionLast + 1
    					) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = modeAction;
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] < 0) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = changeModeMax - 1;
    		} else {
    			mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select]++;
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] > modeAction
    					&& mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] <= modeActionLast) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = modeActionLast + 1;
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] >= changeModeMax) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = 0;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] < 0) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = changeModeMax;
    		//Modchu_Debug.mDebug("mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select]="+mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select]);
    		initGui();
    		return;
    	}
    	//select--
    	if(guibutton.id == 401)
    	{
    		select = select > 0 ? --select : mod_PFLM_PlayerFormLittleMaid.pflm_main.maxShortcutKeys - 1;
    		initGui();
    		return;
    	}
    	//select++
    	if(guibutton.id == 402)
    	{
    		select = select < mod_PFLM_PlayerFormLittleMaid.pflm_main.maxShortcutKeys - 1 ? ++select : 0;
    		initGui();
    		return;
    	}
    	//use Change
    	if(guibutton.id == 403)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysUse[select] = !mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysUse[select];
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
    		initGui();
    		return;
    	}
    	//use ModelsKey
    	if(guibutton.id == 404)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysPFLMModelsUse[select] = !mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysPFLMModelsUse[select];
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
    		initGui();
    		return;
    	}
    	//use Ctrl
    	if(guibutton.id == 405)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysCtrlUse[select] = !mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysCtrlUse[select];
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
    		initGui();
    		return;
    	}
    	//use Shift
    	if(guibutton.id == 406)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysShiftUse[select] = !mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysShiftUse[select];
    		mod_PFLM_PlayerFormLittleMaid.shortcutKeysinit();
    		initGui();
    		return;
    	}
    	//select-10
    	if(guibutton.id == 407)
    	{
    		select = select > 10 ? select - 10 : 0;
    		initGui();
    		return;
    	}
    	//select+10
    	if(guibutton.id == 408)
    	{
    		select = select < mod_PFLM_PlayerFormLittleMaid.pflm_main.maxShortcutKeys - 10 ? select + 10 : mod_PFLM_PlayerFormLittleMaid.pflm_main.maxShortcutKeys - 1;
    		initGui();
    		return;
    	}
    	//Action++
    	if(guibutton.id == 409)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select]++;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] > modeActionLast) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = modeAction;
    		initGui();
    		return;
    	}
    	//Action--
    	if(guibutton.id == 410)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select]--;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] < modeAction) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = modeActionLast;
    		initGui();
    		return;
    	}
    	//Action+10
    	if(guibutton.id == 411)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] + 10;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] > modeActionLast) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = modeActionLast;
    		initGui();
    		return;
    	}
    	//Action-10
    	if(guibutton.id == 412)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] - 10;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] < modeAction) mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = modeAction;
    		initGui();
    		return;
    	}
    	super.actionPerformed(guibutton);
    }

    public void selectInit() {
    	shortcutKey = "";
    	String s = "key.ModelChange"+select;
    	shortcutKey = getKeyDisplayString(s);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysPFLMModelsUse[select]) {
    		s = "key.PFLM Models Key";
    		shortcutKey = new StringBuilder().append(getKeyDisplayString(s)).append(" + ").append(shortcutKey).toString();
    	}
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysCtrlUse[select]) {
    		shortcutKey = new StringBuilder().append(GameSettings.getKeyDisplayString(29)).append(" or ").append(GameSettings.getKeyDisplayString(157)).append(" + ").append(shortcutKey).toString();
    	}
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysShiftUse[select]) {
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

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	int xSize = 80;
    	int ySize = 50;
    	int guiLeft = width / 2 - xSize + 30;
    	int guiTop = height / 2 - (ySize / 2) - 20;
    	fontRenderer.drawString("KeyControlsSetting", width / 2 - 50, height / 2 - 110, 0xffffff);
    	fontRenderer.drawString((new StringBuilder()).append("ShortcutKey = ").append(shortcutKey).toString(), width / 2 - 150, height / 2 - 100, 0xffffff);
    	fontRenderer.drawString("SettingNumber", width / 2 + 40, height / 2 - 88, 0xffffff);
    	String use = mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysUse[select] ? "use" : "unused";
    	StringBuilder s10 = (new StringBuilder()).append("shortcutKey : ");
    	s10 = s10.append(use);
    	fontRenderer.drawString(s10.toString(), guiLeft, guiTop + 150, 0xffffff);
    	fontRenderer.drawString(""+select, width / 2 + 115, height / 2 - 88, 0xffffff);
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysUse[select]) return;
    	StringBuilder s = (new StringBuilder()).append("TextureName : ");
    	StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
    	StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
    	StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
    	s9 = s9.append(getChangeModeString(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select]));
    	fontRenderer.drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
    	if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeOthersSettingOffline
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModel
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModelAndColor
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModelAndArmor) {
    		s = s.append(getTextureName());
    		fontRenderer.drawString(s.toString(), guiLeft, guiTop + 100, 0xffffff);
    		fontRenderer.drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
    	}
    	if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeOthersSettingOffline
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetColor
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModelAndColor
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
    		s2 = s2.append(getColor());
    		fontRenderer.drawString(s2.toString(), guiLeft, guiTop + 110, 0xffffff);
    		fontRenderer.drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
    	}
    	if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeOthersSettingOffline
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetModelAndArmor
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetArmor
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeSetColorAndArmor) {
    		s1 = s1.append(getTextureArmorName());
    		fontRenderer.drawString(s1.toString(), guiLeft, guiTop + 120, 0xffffff);
    		StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
    		s8 = s8.append(PFLM_RenderPlayerDummyMaster.showArmor);
    		fontRenderer.drawString(s8.toString(), guiLeft, guiTop + 140, 0xffffff);
    		fontRenderer.drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
    	}
    	if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeOthersSettingOffline
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] == modeModelScale) {
    		if(modelScaleButton) {
    			String s6 = "ModelScale : "+getScale();
    			s6 = (new StringBuilder()).append(s6).toString();
    			fontRenderer.drawString(s6, guiLeft - 120, guiTop + 90, 0xffffff);
    			String s7 = "ModelScaleChange";
    			s7 = (new StringBuilder()).append(s7).toString();
    			fontRenderer.drawString(s7, guiLeft - 120, guiTop + 55, 0xffffff);
    		}
    	}
    	if(mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] != modePlayerOnline
    			&& mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] != modePlayerOffline
    			&& mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] != modeRandom
    			&& mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] < modeActionRelease) {
    		if (drawEntitySetFlag) {
    			drawEntitySetFlag = false;
    			setTextureValue();
    			mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.allModelInit(drawEntity, false);
    		}
    		int l = guiLeft;
    		int i1 = guiTop;
    		drawMobModel2(i, j, l + 51, i1 + 75, 0, 25, 50F, 0.0F, true);
    	}
    }

    @Override
    public String getTextureName() {
    	return mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysTextureName[select];
    }

    @Override
    public void setTextureName(String s) {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysTextureName[select] = s;
    	PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, s);
    }

    @Override
    public String getTextureArmorName() {
    	return mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysTextureArmorName[select];
    }

    @Override
    public void setTextureArmorName(String s) {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysTextureArmorName[select] = s;
    	PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
    }

    @Override
    public int getColor() {
    	return mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysMaidColor[select];
    }

    @Override
    public void setColor(int i) {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysMaidColor[select] = i & 0xf;
    	PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, i & 0xf);
    }

    public void setChangeMode(int i) {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysChangeMode[select] = i;
    }

    @Override
    public float getScale() {
    	return mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysModelScale[select];
    }

    @Override
    public void setScale(float f) {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysModelScale[select] = f;
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
