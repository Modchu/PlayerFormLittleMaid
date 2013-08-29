package net.minecraft.src;

import java.util.Map;

//import net.minecraft.client.Minecraft;

public class mod_PFLM_PlayerFormLittleMaid extends BaseMod
{
	public static mod_PFLM_PlayerFormLittleMaid mod_pflm_playerformlittlemaid;
	public static PFLM_Main pflm_main;
	public static PFLM_IRenderPlayer pflm_RenderPlayer;
	public static PFLM_IRenderPlayerDummy pflm_RenderPlayerDummy;

	public mod_PFLM_PlayerFormLittleMaid()
	{
		mod_pflm_playerformlittlemaid = this;
		// b181deleteload();
	}

	public String getVersion()
	{
		if (pflm_main != null) ;else pflm_main = new PFLM_Main();
		return pflm_main.getVersion();
	}

	public void load()
	{
		pflm_main = new PFLM_Main();
		pflm_main.load();
	}

	public void loadInit() {
		pflm_main.loadInit();
		ModLoader.setInGameHook(this, true, true);
	}

	public void addRenderer(Map map)
	{
		pflm_main.addRenderer(map);
	}

	public void keyboardEvent(KeyBinding keybinding) {
		pflm_main.keyboardEvent(keybinding);
	}

	public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen par1GuiScreen) {
		return pflm_main.onTickInGUI(f, minecraft, par1GuiScreen);
	}

	public boolean onTickInGame(float f, Minecraft minecraft)
	{
		return pflm_main.onTickInGame(f, minecraft);
	}

	public void serverConnect(NetClientHandler netClientHandler) {
		clientConnect(netClientHandler);
		return;
	}

	public void clientConnect(NetClientHandler netClientHandler) {
		pflm_main.clientConnect(netClientHandler);
	}

	public void modsLoaded()
	{
		boolean keyFlag = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129 ? mod_Modchu_ModchuLib.modchu_Main.isForge : false;
		//ƒL[‚Ì“o˜^‚Æ–¼Ì•ÏŠ·ƒe[ƒuƒ‹‚Ì“o˜^
		String s;
		if (pflm_main.isPlayerForm) {
			s = "key.PlayerFormLittleMaid";
			ModLoader.registerKey(this, new KeyBinding(s, 41), keyFlag);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("PlayerFormLittleMaidGui").toString()
					);
			s = "key.PFLM Models Key";
			ModLoader.registerKey(this, new KeyBinding(s, 64), keyFlag);
			ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("PFLMModelsKey").toString()
					);
			if(pflm_main.waitTime == 0) {
				s = "key.PFLM wait";
				ModLoader.registerKey(this, new KeyBinding(s, 26), keyFlag);
				ModLoader.addLocalization(
						(new StringBuilder()).append(s).toString(),
						(new StringBuilder()).append("PFLMWait").toString()
						);
			}
		}
		s = "key.Sit";
		ModLoader.registerKey(this, new KeyBinding(s, 39), keyFlag);
		ModLoader.addLocalization(
					(new StringBuilder()).append(s).toString(),
					(new StringBuilder()).append("Sit").toString()
					);
		s = "key.LieDown";
		ModLoader.registerKey(this, new KeyBinding(s, 27), keyFlag);
		ModLoader.addLocalization(
				(new StringBuilder()).append(s).toString(),
				(new StringBuilder()).append("LieDown").toString()
				);
		shortcutKeysinit();
		pflm_main.modsLoaded();
	}

	public void modsLoadedInit() {
		pflm_main.modsLoadedInit();
		ModLoader.setInGUIHook(this, true, true);
	}

	public static void shortcutKeysinit() {
		boolean keyFlag = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129 ? mod_Modchu_ModchuLib.modchu_Main.isForge : false;
		String s;
		for(int i = 0; i < pflm_main.maxShortcutKeys;i++) {
			if (pflm_main.shortcutKeysUse[i]) {
				s = "key.ModelChange"+i;
				ModLoader.registerKey(mod_pflm_playerformlittlemaid, new KeyBinding(s, 33+i), keyFlag);
				ModLoader.addLocalization(
						(new StringBuilder()).append(s).toString(),
						(new StringBuilder()).append("ModelChange"+i).toString());
			}
		}
		KeyBinding keyBinding;
		for(int i = 0; i < KeyBinding.keybindArray.size() ; i++) {
			keyBinding = (KeyBinding) KeyBinding.keybindArray.get(i);
			if (keyBinding.keyDescription.equalsIgnoreCase("key.PFLM Models Key")) {
				pflm_main.PFLMModelsKeyCode = keyBinding.keyCode;
				break;
			}
		}
	}
}