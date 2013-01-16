
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class mod_PFLM_PlayerFormLittleMaid extends BaseMod
{
	//cfg書き込み項目
	public static boolean DebugMessage = true;
	public static boolean DebugMessagetexture = true;
	public static boolean AlphaBlend = true;
	public static int Physical_BurningPlayer = 0;
	public static int Physical_MeltingPlayer = 0;
	public static float Physical_Hammer = 1.0F;
	public static boolean Physical_Undead = false;
	public static String Physical_HurtSound="random.hurt";
	public static boolean isPlayerForm = true;
	public static boolean isPlayerAPIDebug = false;
	public static boolean isModelSize = false;
	public static boolean isClearWater = false;
	public static boolean isVoidFog = true;
	public static boolean isFog = true;
	public static boolean isDimming = true;
	public static boolean isSwapGuiCreate = true;
	public static float watherFog = 0.1F;
	public static float watherFog2 = 0.05F;
	public static int waterStillLightOpacity = 3;
	public static int waterMovingLightOpacity = 3;
	public static float lavaFog = 2.0F;
	public static float transparency = 1.0F;
	public static String textureSavedir = "/output/";
	public static boolean guiMultiPngSaveButton = true;
	public static boolean EnableCommands = false;
	public static boolean EnablePOV = false;
	public static boolean onlineModeButton = true;
	public static boolean isRenderName = true;
	public static boolean isMouseOverMinecraftMenu = true;
	public static double locationPositionCorrectionY = 0.5D;
	public static int waitTime = 600;
	public static boolean multiAutoOnlinemode = true;
	public static boolean skirtFloats = false;
	public static float skirtFloatsVolume = 0.5F;
	public static int othersPlayerWaitTime = 600;
	public static boolean modchuRemodelingModel = true;
	public static boolean versionCheck = true;
	public static boolean useInvisibilityBody = true;
	public static boolean useInvisibilityArmor = false;
	public static boolean useInvisibilityItem = false;
	public static boolean debugReflect = false;
	public static boolean debugReflectDetail = false;
	public static int handednessMode = 0;

	public static boolean onlineMode = false;
	public static int maidColor = 0;
	public static String defaultTexture = "default";
	public static String defaultTextureArmorName = "default";
	public static float setWatherFog = 0F;
	public static float setWatherFog2 = 0F;
	public static int setwaterStillLightOpacity = 0;
	public static int setwaterMovingLightOpacity = 0;
	public static boolean isPlayerAPI = false;
	public static boolean guiEnable = true;
	private static int erpflmCheck = 0;
	public static int guiSelectWorldSwapCount = 0;
	public static int guiMultiplayerSwapCount = 0;
	public static boolean isThirdPersonCamera = false;
	public static boolean isnoBiomesX = false;
	public static boolean isSmartMoving = false;
	public static boolean isShaders = false;
	public static boolean isShader = false;
	public static boolean isDynamicLights = false;
	public static boolean isForge = false;
	public static boolean isAether = false;
	public static boolean is2D = false;
	public static boolean isCCTV = false;
	public static boolean isFavBlock = false;
	public static boolean isDecoBlock = false;
	public static boolean isWait = false;
	public static boolean isMulti = false;
	public static boolean isOlddays = false;
	public static boolean isSSP = false;
	public static boolean isLMM = false;
	public static boolean isItemRendererHD = false;
	private boolean setMultiAutoOnlinemode = true;
	public boolean gotchaNullFlag = false;
	public static Object textureModel[];
	public static Object gotcha;
	public static String textureArmor0[] = new String[4];
	public static String textureArmor1[] = new String[4];
	public static String texture = null;
	public static String textureName = null;
	public static String textureArmorName = null;
	public static String othersTexture = null;
	public static String othersTextureName = "default";
	public static String othersTextureArmorName = "default";
	public static int othersMaidColor = 0;
	public static Object othersTextureModel[];
	public static String othersTextureArmor0[] = new String[4];
	public static String othersTextureArmor1[] = new String[4];
	public static float othersModelScale = 0.0F;
	public static Object othersIndividualTextureModel[];
	public static String othersIndividualTextureArmor0[] = new String[4];
	public static String othersIndividualTextureArmor1[] = new String[4];
	public static final int maxShortcutKeys = 100;
	public static Object shortcutKeysTextureModel[];
	public static String shortcutKeysTextureArmor0[] = new String[4];
	public static String shortcutKeysTextureArmor1[] = new String[4];
	public static float shortcutKeysModelScale[] = new float[maxShortcutKeys];
	public static String shortcutKeysTexture[] = new String[maxShortcutKeys];
	public static String shortcutKeysTextureName[] = new String[maxShortcutKeys];
	public static String shortcutKeysTextureArmorName[] = new String[maxShortcutKeys];
	public static int shortcutKeysMaidColor[] = new int[maxShortcutKeys];
	public static int shortcutKeysChangeMode[] = new int[maxShortcutKeys];
	public static boolean shortcutKeysUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysPFLMModelsUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysCtrlUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysShiftUse[] = new boolean[maxShortcutKeys];
	public static int shortcutKeysNumber = 0;
	private static final File cfgdir = new File(Minecraft.getMinecraftDir(), "/config/");
	private static File mainCfgfile = new File(cfgdir, ("PlayerFormLittleMaid.cfg"));
	public static File cfgfile = new File(cfgdir, ("PlayerFormLittleMaidGuiSave.cfg"));
	public static File othersCfgfile = new File(cfgdir, ("PlayerFormLittleMaidGuiOthersPlayer.cfg"));
	private static File modelListfile = new File(cfgdir, ("PlayerFormLittleMaidModelList.cfg"));
	private static File textureListfile = new File(cfgdir, ("PlayerFormLittleMaidtextureList.cfg"));
	private static File shortcutKeysCfgfile = new File(cfgdir, ("PlayerFormLittleMaidShortcutKeys.cfg"));
	public static List<String> showModelList = new ArrayList<String>();
	public static List<String> modelList = new ArrayList<String>();
	public static List<String> textureList = new ArrayList<String>();
	public static mod_PFLM_PlayerFormLittleMaid mod_pflm_playerformlittlemaid;
	public static HashMap playerLocalData = new HashMap();
	public static Minecraft mc = Minecraft.getMinecraft();
	public final int playerFormLittleMaidVersion;
	public static boolean newRelease = false;
	public static String newVersion = "";
	public final String minecraftVersion;
	public static int[][] texturesNamber;
	public static int[] maxTexturesNamber = new int [16];
	public static String[] debugString;
	public static Class decoBlock;
	public static Class decoBlockBase;
	public static Class favBlock;
	public static Class LMM_EntityLittleMaid;
	public static Class LMM_InventoryLittleMaid;
	public static Class LMM_SwingStatus;
	public static Class LMM_EntityLittleMaidAvatar;
	private static int PFLMModelsKeyCode;
	private boolean isItemRendererDebug = true;

	//対応minecraftバージョンによって使っていたりいなかったり
	public static boolean isSwapGuiSelectWorld = false;
	public static boolean addRendererFlag = false;
	private boolean aetherAddRenderer;
	private int keyCode = 0;
	private int keybindingTime = 0;
	public static String optiVersionName;
	public static int getIconWidthTerrain = 16;
	public static boolean smartMovingAddRenderer = false;
	public static boolean smartMovingAddRenderer2 = false;
	public static boolean smartMovingVersion = false;
	public static boolean aetherInit = false;
	public static NetClientHandler netclienthandler;
	public static Class pflm_playerControllerCreative2;
	public static Class pflm_playerController2;
	public static Class pflm_renderPlayer2;
	public static Class pflm_entityPlayerSP2;

	public mod_PFLM_PlayerFormLittleMaid()
	{
		// b181deleteload();
		if (getVersion().startsWith("1.4.6")) {
			playerFormLittleMaidVersion = 146;
			minecraftVersion = "1.4.6";
			return;
		}
		if (getVersion().startsWith("1.4.4~5")) {
			playerFormLittleMaidVersion = 145;
			minecraftVersion = "1.4.5";
			return;
		}
		if (getVersion().startsWith("1.4.4")) {
			playerFormLittleMaidVersion = 144;
			minecraftVersion = "1.4.4";
			return;
		}
		if (getVersion().startsWith("1.4.2")) {
			playerFormLittleMaidVersion = 142;
			minecraftVersion = "1.4.2";
			return;
		}
		if (getVersion().startsWith("1.4.1")) {
			playerFormLittleMaidVersion = 141;
			minecraftVersion = "1.4.1";
			return;
		}
		if (getVersion().startsWith("1.4")) {
			playerFormLittleMaidVersion = 140;
			minecraftVersion = "1.4";
			return;
		}
		if (getVersion().startsWith("1.3.2")) {
			playerFormLittleMaidVersion = 132;
			minecraftVersion = "1.3.2";
			return;
		}
		if (getVersion().startsWith("1.3.1")) {
			playerFormLittleMaidVersion = 131;
			minecraftVersion = "1.3.1";
			return;
		}
		if (getVersion().startsWith("1-2-4")) {
			playerFormLittleMaidVersion = 124;
			minecraftVersion = "1.2.4";
			return;
		}
		if (getVersion().startsWith("1-2-3")) {
			playerFormLittleMaidVersion = 123;
			minecraftVersion = "1.2.3";
			return;
		}
		if (getVersion().startsWith("1-1")) {
			playerFormLittleMaidVersion = 110;
			minecraftVersion = "1.1.0";
			return;
		}
		if (getVersion().startsWith("1-0-0")) {
			playerFormLittleMaidVersion = 100;
			minecraftVersion = "1.0.0";
			return;
		}
		if (getVersion().startsWith("1-9pre")) {
			playerFormLittleMaidVersion = 90;
			minecraftVersion = "1.9pre";
			return;
		}
		if (getVersion().startsWith("1-8-1")) {
			playerFormLittleMaidVersion = 81;
			minecraftVersion = "beta1.8.1";
			return;
		}
		if (getVersion().startsWith("1-7-3")) {
			playerFormLittleMaidVersion = 73;
			minecraftVersion = "beta1.7.3";
			return;
		}
		if (getVersion().startsWith("1-6-6")) {
			playerFormLittleMaidVersion = 66;
			minecraftVersion = "beta1.6.6";
			return;
		}
		if (getVersion().startsWith("1-5-1")) {
			playerFormLittleMaidVersion = 51;
			minecraftVersion = "beta1.5.1";
			return;
		}
		playerFormLittleMaidVersion = 0;
		minecraftVersion = "";
	}

	public boolean isRelease() {
		return getClass().getPackage() == null;
	}

	public String getVersion()
	{
		return "1.4.6-18";
	}

	public void load()
	{
		mod_pflm_playerformlittlemaid = this;
		loadcfg();
		ModLoader.setInGameHook(this, true, true);
		Modchu_Debug.init();
		Modchu_Config.init();
		loadParamater();
		loadModelList();
		loadOthersPlayerParamater();
		loadShortcutKeysParamater();
		if (versionCheck) startVersionCheckThread();
		if (DebugMessage
				&& !mod_pflm_playerformlittlemaid.isRelease()) debugString = new String[10];
		MMM_FileManager.getModFile("playerformlittlemaid", "playerformlittlemaid");
		MMM_TextureManager.addSearch("playerformlittlemaid", "/mob/littleMaid/", "ModelPlayerFormLittleMaid_");
	}

	public void addRenderer(Map map)
	{
//-@-125
		if (isPlayerForm
				&& !isAether) {
			Modchu_Debug.Debug("addRenderer");
			ModLoader.getLogger().fine("playerFormLittleMaid-addRenderer");
			if (isSmartMoving) {
				PFLM_RenderPlayerSmart var1 = new PFLM_RenderPlayerSmart();
				map.put(EntityClientPlayerMP.class, var1);
				map.put(EntityOtherPlayerMP.class, var1);
			}
			else if (isForge) {
				PFLM_RenderPlayer var1 = new PFLM_RenderPlayer();
				if (isPlayerAPI) map.put(PFLM_EntityPlayer.class, var1);
				else map.put(PFLM_EntityPlayerSP.class, var1);
				map.put(EntityClientPlayerMP.class, var1);
				map.put(EntityOtherPlayerMP.class, var1);
				var1.setRenderManager(RenderManager.instance);
			}
			else if (isOlddays) {
				Object var1;
				try {
					var1 = pflm_renderPlayer2.newInstance();
					map.put(EntityClientPlayerMP.class, var1);
					map.put(EntityOtherPlayerMP.class, var1);
					map.remove(EntityPlayer.class);
					((Render) var1).setRenderManager(RenderManager.instance);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				PFLM_RenderPlayer var1 = new PFLM_RenderPlayer();
				map.put(EntityClientPlayerMP.class, var1);
				map.put(EntityOtherPlayerMP.class, var1);
			}
		}
		Object var2 = null;
		if (isSmartMoving) {
			var2 = new PFLM_RenderPlayerDummySmart();
			map.put(PFLM_EntityPlayerDummy.class, var2);
		} else {
			var2 = new PFLM_RenderPlayerDummy();
			map.put(PFLM_EntityPlayerDummy.class, var2);
		}
		if (isForge) ((Render) var2).setRenderManager(RenderManager.instance);
//@-@125
/*//125delete
		if (isPlayerForm
				&& !isAether) {
			Modchu_Debug.Debug("addRenderer");
			ModLoader.getLogger().fine("playerFormLittleMaid-addRenderer");
			map.put(EntityPlayerSP.class, new PFLM_RenderPlayer());
			map.put(EntityOtherPlayerMP.class, new PFLM_RenderPlayer());
		}
		if (guiMultiPngSaveButton) {
*///125delete
//-@-110
		// 125deleteif (isSmartMoving) {
		// 125deletemap.put(PFLM_EntityPlayerDummy.class, new PFLM_RenderPlayerDummySmart());
		// 125delete} else {
//@-@110
		// 125deletemap.put(PFLM_EntityPlayerDummy.class, new PFLM_RenderPlayerDummy());
		/*110//*/// 125delete}
		// 125delete}
	}

	public void keyboardEvent(KeyBinding keybinding) {
		if (mc.currentScreen instanceof GuiChat) return;
//-@-125
		if (playerFormLittleMaidVersion > 129) {
			if (isForge) {
				if (keybinding.keyCode == keyCode
						&& keybindingTime > 0) return;
				setKeyCode(keybinding.keyCode);
				keybindingTime = 10;
			}
		}
//@-@125
		// GUIを開く
		if (keybinding.keyDescription.equals("key.PlayerFormLittleMaid"))
		{
			if (mc.theWorld != null && mc.currentScreen == null
					&& !Keyboard.isKeyDown(PFLMModelsKeyCode)
					&& !Keyboard.isKeyDown(29)
					&& !Keyboard.isKeyDown(157)
					&& !Keyboard.isKeyDown(42)
					&& !Keyboard.isKeyDown(54)) {
				ModLoader.openGUI(mc.thePlayer, new PFLM_Gui(mc.theWorld));
			}
		}
		EntityPlayerSP entityplayersp = mc.thePlayer;
		if (entityplayersp == null)
		{
			return;
		}
		if (keybinding.keyDescription.equals("key.Sit"))
		{
			if (!Keyboard.isKeyDown(PFLMModelsKeyCode)
					&& !Keyboard.isKeyDown(29)
					&& !Keyboard.isKeyDown(157)
					&& !Keyboard.isKeyDown(42)
					&& !Keyboard.isKeyDown(54)) {
				float f = ((EntityPlayer)(entityplayersp)).moveForward * ((EntityPlayer)(entityplayersp)).moveForward + ((EntityPlayer)(entityplayersp)).moveStrafing * ((EntityPlayer)(entityplayersp)).moveStrafing;

				if (!entityplayersp.isRiding() && mc.inGameHasFocus && (double)f < 0.20000000000000001D && !((EntityPlayer)(entityplayersp)).isJumping)
				{
					byte byte0 = ((EntityPlayer)(entityplayersp)).dataWatcher.getWatchableObjectByte(16);
					((EntityPlayer)(entityplayersp)).dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 != 1 ? 1 : 0)));

					//entityplayersp.setFlag(1 ,true);
					//if (mc.theWorld.isRemote && EnableCommands)
					//{
						//((EntityPlayerSP)entityplayersp).sendChatMessage("/sit");
					//}
				}
			}
		}
		else if (keybinding.keyDescription.equals("key.LieDown"))
		{
			if (!Keyboard.isKeyDown(PFLMModelsKeyCode)
					&& !Keyboard.isKeyDown(29)
					&& !Keyboard.isKeyDown(157)
					&& !Keyboard.isKeyDown(42)
					&& !Keyboard.isKeyDown(54)) {
				float f1 = ((EntityPlayer)(entityplayersp)).moveForward * ((EntityPlayer)(entityplayersp)).moveForward + ((EntityPlayer)(entityplayersp)).moveStrafing * ((EntityPlayer)(entityplayersp)).moveStrafing;

				if (entityplayersp.isRiding() || !mc.inGameHasFocus || f1 > 0.0F || ((EntityPlayer)(entityplayersp)).isJumping)
				{
					return;
				}

				//if (mc.theWorld.isRemote && EnableCommands)
				//{
					//((EntityPlayerSP)entityplayersp).sendChatMessage("/sleep");
				//}

				byte byte1 = ((EntityPlayer)(entityplayersp)).dataWatcher.getWatchableObjectByte(16);
				((EntityPlayer)(entityplayersp)).dataWatcher.updateObject(16, Byte.valueOf((byte)(byte1 != 2 ? 2 : 0)));
				float f2;

				for (f2 = ((EntityPlayer)(entityplayersp)).rotationYaw; f2 < 0.0F; f2 += 360F) { }

				for (; f2 > 360F; f2 -= 360F) { }

				int i = (int)((f2 + 45F) / 90F);
				/*b173//*/((EntityPlayer)(entityplayersp)).dataWatcher.updateObject(17, Byte.valueOf((byte)i));
/*//b173delete
//-@-b166
				if (isPlayerAPI) {
					if (entityplayersp instanceof PFLM_EntityPlayer) {
						((PFLM_EntityPlayer) entityplayersp).setSleepMotion(i);
					}
				} else {
//@-@b166
					if (entityplayersp instanceof PFLM_EntityPlayerSP) {
						((PFLM_EntityPlayerSP) entityplayersp).setSleepMotion(i);
					}
    			//b166//}
*///b173delete
			}
		}
		else if (keybinding.keyDescription.equals("key.PFLM Models Key"))
		{
			if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157))
			{
				clearPlayers();
				if (PFLM_Gui.changeMode == PFLM_Gui.modeRandom
						&& mc.gameSettings.thirdPersonView == 0) mc.gameSettings.thirdPersonView = 1;
			}
			//else if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))
			//{
				//EnableCommands = !EnableCommands;
				//entityplayersp.addChatMessage((new StringBuilder()).append("Sending commands has been ").append(EnableCommands ? "enabled" : "disabled").toString());
			//}
			//else {
				//EnablePOV = !EnablePOV;
				//entityplayersp.addChatMessage((new StringBuilder()).append("POV Change has been ").append(EnablePOV ? "enabled" : "disabled").toString());
			//}
		}
		else if (keybinding.keyDescription.startsWith("key.ModelChange")) {
			int i = Integer.valueOf(keybinding.keyDescription.substring("key.ModelChange".length(), keybinding.keyDescription.length()));
			boolean flag = true;
			if (shortcutKeysPFLMModelsUse[i]
					&& !Keyboard.isKeyDown(PFLMModelsKeyCode)) flag = false;
			if (shortcutKeysCtrlUse[i]
					&& !Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157)) flag = false;
			if (shortcutKeysShiftUse[i]
					&& !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)) flag = false;
			if (shortcutKeysUse[i]
					&& flag) {
				boolean clear = false;
				switch (shortcutKeysChangeMode[i]) {
				case PFLM_GuiKeyControls.modeOthersSettingOffline:
					textureName = shortcutKeysTextureName[i];
					textureArmorName = shortcutKeysTextureArmorName[i];
					maidColor = shortcutKeysMaidColor[i];
					PFLM_Gui.modelScale = shortcutKeysModelScale[i];
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeModelScale:
					PFLM_Gui.modelScale = shortcutKeysModelScale[i];
					break;
				case PFLM_GuiKeyControls.modePlayerOffline:
					onlineMode = false;
					PFLM_Gui.changeMode = PFLM_Gui.modeOffline;
					clear = true;
					break;
				case PFLM_GuiKeyControls.modePlayerOnline:
					onlineMode = true;
					PFLM_Gui.changeMode = PFLM_Gui.modeOnline;
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeRandom:
					PFLM_Gui.changeMode = PFLM_Gui.modeRandom;
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeSetModel:
					textureName = shortcutKeysTextureName[i];
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeSetColor:
					maidColor = shortcutKeysMaidColor[i];
					break;
				case PFLM_GuiKeyControls.modeSetArmor:
					textureArmorName = shortcutKeysTextureArmorName[i];
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeSetModelAndArmor:
					textureName = shortcutKeysTextureName[i];
					textureArmorName = shortcutKeysTextureArmorName[i];
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeSetModelAndColor:
					textureName = shortcutKeysTextureName[i];
					maidColor = shortcutKeysMaidColor[i];
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeSetColorAndArmor:
					textureArmorName = shortcutKeysTextureArmorName[i];
					maidColor = shortcutKeysMaidColor[i];
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeActionRelease:
					setShortcutKeysAction(false);
					break;
				}
				if (shortcutKeysChangeMode[i] >= PFLM_GuiKeyControls.modeAction
						&& shortcutKeysChangeMode[i] <= PFLM_GuiKeyControls.modeActionLast) {
					setRunActionNumber(shortcutKeysChangeMode[i] - PFLM_GuiKeyControls.modeAction + 1);
					setShortcutKeysAction(true);
				}
				if (PFLM_Gui.partsSaveFlag) {
					PFLM_Gui.partsSaveFlag = false;
					saveParamater();
					Modchu_Config.clearCfgData();
				}
				if (clear) {
					clearPlayers();
					if (PFLM_Gui.changeMode == PFLM_Gui.modeRandom
							&& mc.gameSettings.thirdPersonView == 0) mc.gameSettings.thirdPersonView = 1;
				}
				shortcutKeysNumber = i;
			}
		}
		else if (waitTime == 0) {
			if (keybinding.keyDescription.equals("key.PFLM wait"))
			{
				//Modchu_Debug.mDebug("PFLMWait");
				if (!((EntityPlayer)(entityplayersp)).isJumping)
				{
					isWait = !isWait;
					//Modchu_Debug.mDebug("PFLMWait isWait="+isWait);
					//if (mc.theWorld.isRemote && EnableCommands)
					//{
						//((EntityPlayerSP)entityplayersp).sendChatMessage("/wait");
					//}
				}
			}
		}
	}

	private void setKeyCode(int i) {
		keyCode = i;
	}

	public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen par1GuiScreen) {
//-@-125
		if(isSmartMoving
				&& mc != null) PFLM_SmartMovingOther.TranslateIfNecessary((GameSettings)null);
//@-@125
/*//125delete
//-@-110
		if (!smartMovingAddRenderer
				&& isPlayerForm
				&& isSmartMoving
				&& !isForge) {
			PFLM_PlayerController.addRenderer();
			smartMovingAddRenderer = true;
			Modchu_Debug.Debug("SmartMovingAddRenderer");
			ModLoader.getLogger().fine("playerFormLittleMaid-SmartMovingAddRenderer");
		}
//@-@110
//-@-123
		// GUI置き換え
		if (par1GuiScreen instanceof GuiSelectWorld
				&& isAether
				&& guiSelectWorldSwapCount < 10) {
			// ワールドセレクトを置き換え
			if (!(par1GuiScreen instanceof GuiSelectWorldPlayerFormLittleMaidAether)) {
				guiSelectWorldSwapCount++;
				String guiMainMenu = getClassName("GuiMainMenu");
				Field fi = null;
				try {
					fi = Class.forName(guiMainMenu).getField("musicId");
				} catch (Exception e1) {
					try {
						fi = Class.forName("xt").getField("musicId");
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				try {
					int ID = (Integer) fi.get(null);
					minecraft.displayGuiScreen(new GuiSelectWorldPlayerFormLittleMaidAether((GuiSelectWorld)par1GuiScreen, ID));
					Modchu_Debug.Debug("Swap GuiSelectWorldAether.");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		}
//@-@123
		// GUI置き換え
		if (par1GuiScreen instanceof GuiSelectWorld
				&& !isAether
				&& isSwapGuiSelectWorld
				&& guiSelectWorldSwapCount < 10) {
			// ワールドセレクトを置き換え
			if (!(par1GuiScreen instanceof GuiSelectWorldPlayerFormLittleMaid)) {
				guiSelectWorldSwapCount++;
				minecraft.displayGuiScreen(new GuiSelectWorldPlayerFormLittleMaid((GuiSelectWorld)par1GuiScreen));
				Modchu_Debug.Debug("Swap GuiSelectWorld.");
//-@-b166
				return true;
//@-@b166
				// b166deletereturn;
			}
		}

		if (par1GuiScreen instanceof GuiMainMenu) {
//-@-b166
			if (isPlayerAPI) {
				if (PFLM_EntityPlayer.gotcha != null) {
					//Modchu_Debug.Debug("PFLM_EntityPlayer.gotcha != null");
					PFLM_EntityPlayer.gotcha = null;
					gotchaNullFlag = true;
					return true;
				}
			} else {
//@-@b166
				if (gotcha != null) {
					//Modchu_Debug.Debug("gotcha != null");
					gotcha = null;
					gotchaNullFlag = true;
//-@-b166
					return true;
//@-@b166
					// b166deletereturn;
//-@-b166
    		 	}
//@-@b166
			}
		} else {
			if (gotchaNullFlag) gotchaNullFlag = false;
		}
*///125delete
//-@-b166
		return true;
//@-@b166
		// b166deletereturn;
	}

	public boolean onTickInGame(float f, Minecraft minecraft)
	{
		if (DebugMessage
				&& !mod_pflm_playerformlittlemaid.isRelease()) Modchu_Debug.dDebugDrow();
//-@-125
		if (minecraft.currentScreen != null
				&& !isForge) onTickInGUI(0.0F, minecraft, minecraft.currentScreen);

		if (isItemRendererHD
				&& !isItemRendererDebug) {
			if (!(mc.entityRenderer.itemRenderer instanceof Modchu_ItemRendererHD)) {
				mc.entityRenderer.itemRenderer = new Modchu_ItemRendererHD(mc);
			}
			if (!(RenderManager.instance.itemRenderer instanceof Modchu_ItemRendererHD)) {
				RenderManager.instance.itemRenderer = new Modchu_ItemRendererHD(mc);
			}
		} else {
			if (!(mc.entityRenderer.itemRenderer instanceof Modchu_ItemRenderer)) {
				mc.entityRenderer.itemRenderer = new Modchu_ItemRenderer(mc);
			}
			if (!(RenderManager.instance.itemRenderer instanceof Modchu_ItemRenderer)) {
				RenderManager.instance.itemRenderer = new Modchu_ItemRenderer(mc);
			}
		}

		if (isSSP) {
			if (!pflm_playerController2.isInstance(mc.playerController)
				&& netclienthandler != null) {
				Class[] types = { Minecraft.class , NetClientHandler.class };
				Object[] args = {mc, netclienthandler};
				mc.playerController = (PlayerControllerMP) Modchu_Reflect.newInstance(pflm_playerController2, types, args);
				//mc.playerController = new PFLM_PlayerController2(mc, netclienthandler);
			}
			Object b = Modchu_Reflect.getFieldObject(Modchu_Reflect.getField(pflm_playerController2, "entityplayerformlittlemaidsp"), null);
			if (mc.theWorld.getWorldInfo().getGameType() == EnumGameType.SURVIVAL
					&& !pflm_entityPlayerSP2.isInstance(mc.thePlayer)
					&& b != null) {
				mc.thePlayer = (EntityClientPlayerMP) b;
				//mc.thePlayer = PFLM_PlayerController2.entityplayerformlittlemaidsp;
			}
			if (mc.theWorld.getWorldInfo().getGameType() == EnumGameType.CREATIVE
					&& !pflm_playerControllerCreative2.isInstance(mc.playerController)
					&& netclienthandler != null) {
				Class[] types = { Minecraft.class , NetClientHandler.class };
				Object[] args = {mc, netclienthandler};
				mc.playerController = (PlayerControllerMP) Modchu_Reflect.newInstance(pflm_playerControllerCreative2, types, args);
				//mc.playerController = new PFLM_PlayerControllerCreative2(mc, netclienthandler);
			}
		}
		if (isOlddays) {
			if (!pflm_renderPlayer2.isInstance(RenderManager.instance.getEntityClassRenderObject(net.minecraft.src.EntityClientPlayerMP.class))) {
				Object ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_playerController2, "addRenderer"));
				//PFLM_PlayerController2.addRenderer();
				//Modchu_Debug.Debug("onTickInGame PFLM_PlayerController2.addRenderer()");
			}
		}

		if (isForge
				&& keybindingTime > 0) --keybindingTime;
		if (minecraft.getServerData() != null) {
			if (!minecraft.isSingleplayer()) {
				if (multiAutoOnlinemode) {
					if (!onlineMode
						&& setMultiAutoOnlinemode) {
						//Modchu_Debug.mDebug("onTickInGame !minecraft.isSingleplayer()");
						setMultiAutoOnlinemode = false;
						onlineMode = true;
						clearPlayers();
					} else {
						setMultiAutoOnlinemode = false;
					}
				}
				if (!isMulti) isMulti = true;
			} else {
				if (isMulti) isMulti = false;
			}
		}
		if (minecraft.theWorld == null
				&& !setMultiAutoOnlinemode) {
			//Modchu_Debug.mDebug("onTickInGame minecraft.theWorld == null");
			setMultiAutoOnlinemode = true;
		}

		//if(isSmartMoving
				//&& mc != null) PFLM_SmartMovingOther.onTickInGame();
//@-@125
/*//125delete
			if(minecraft.thePlayer.worldObj.isRemote) {
//-@-110
				if (isPlayerForm
						&& isSmartMoving
						&& !isForge) {
					if (!(RenderManager.instance.getEntityClassRenderObject(net.minecraft.src.EntityPlayerSP.class) instanceof PFLM_RenderPlayerSmart)) {
						PFLM_PlayerController.addRenderer();
						Modchu_Debug.Debug("onTickInGame SmartMovingAddRenderer");
					}
				}
//@-@110
				if (multiAutoOnlinemode) {
					if (!onlineMode
						&& setMultiAutoOnlinemode) {
						Modchu_Debug.mDebug("onTickInGame setMultiAutoOnlinemode");
						setMultiAutoOnlinemode = false;
						onlineMode = true;
						clearPlayers();
					} else {
						setMultiAutoOnlinemode = false;
					}
				}
				if (!isMulti) isMulti = true;
				if (minecraft.theWorld == null
						&& !setMultiAutoOnlinemode) {
					Modchu_Debug.mDebug("onTickInGame !setMultiAutoOnlinemode minecraft.theWorld == null");
					setMultiAutoOnlinemode = true;
				}
			} else {
				if (isMulti) isMulti = false;
			}
*///125delete
/*
		if (isModelSize
				&& !(minecraft.entityRenderer instanceof EntityRendererAltPlayerFormLittleMaid)) {
			minecraft.entityRenderer = new EntityRendererAltPlayerFormLittleMaid(minecraft);
			Modchu_Debug.mDebug("EntityRendererAltPlayerFormLittleMaid to set.");
		}
*/
//-@-110
//-@-125
			if (isPlayerForm
					&& isSmartMoving) {
				if (!(RenderManager.instance.getEntityClassRenderObject(net.minecraft.src.EntityClientPlayerMP.class) instanceof PFLM_RenderPlayerSmart)
						| !(RenderManager.instance.getEntityClassRenderObject(net.minecraft.src.EntityOtherPlayerMP.class) instanceof PFLM_RenderPlayerSmart)) {
					PFLM_PlayerController.addRenderer();
					Modchu_Debug.Debug("onTickInGame SmartMovingAddRenderer");
				}
			}
//@-@125
/*//125delete
//-@-110
			if (!smartMovingAddRenderer2
					&& isSmartMoving
					&& isForge) {
				if (!(RenderManager.instance.getEntityClassRenderObject(net.minecraft.src.EntityPlayerSP.class) instanceof PFLM_RenderPlayerSmart)) {
					PFLM_PlayerController.addRenderer();
					smartMovingAddRenderer2 = true;
					Modchu_Debug.Debug("SmartMovingAddRenderer2");
					ModLoader.getLogger().fine("playerFormLittleMaid-SmartMovingAddRenderer2");
				}
			}
*///125delete
//@-@110
//-@-123
			/*//125deleteif (!aetherAddRenderer && isAether) {
				if (!(RenderManager.instance.getEntityClassRenderObject(net.minecraft.src.EntityPlayer.class) instanceof PFLM_RenderPlayerAether)) {
					aetherAddRenderer = true;
					RenderPlayer renderplayer = new PFLM_RenderPlayerAether();
					Object obj = null;
					try {
						obj = getPrivateValue(net.minecraft.src.RenderManager.class, RenderManager.instance, "entityRenderMap");
					} catch (Exception e) {
					}
					if (obj == null) {
						try {
							obj = getPrivateValue(net.minecraft.src.RenderManager.class, RenderManager.instance, "o");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (obj != null) {
						((Map) obj).put(EntityPlayer.class, renderplayer);
						((Map) obj).put(EntityOtherPlayerMP.class, renderplayer);
						renderplayer.setRenderManager(RenderManager.instance);
						Modchu_Debug.Debug("aetherAddRenderer");
						ModLoader.getLogger().fine("playerFormLittleMaid-aetherAddRenderer");
					} else {
						Modchu_Debug.Debug("aetherAddRenderer obj null !!");
						ModLoader.getLogger().warning("playerFormLittleMaid-aetherAddRenderer obj null !!");
					}
				}
			}
//@-@123
//-@-b166
			if(!minecraft.thePlayer.worldObj.isRemote
					&& minecraft.currentScreen == null
					&& !isAether
					&& !isSwapGuiSelectWorld) {
				if ((isPlayerAPI
						&& PFLM_EntityPlayer.gotcha == null)
						| (!isPlayerAPI
								&& gotcha == null)) {
					Modchu_Debug.mDebug("playerControllerReplace");
					playerControllerReplace(minecraft);
					return true;
				}
			}*///125delete
//@-@b166
		boolean b = false;
		if (!isAether) {
			if(!(minecraft.renderGlobal instanceof PFLM_RenderGlobalAlt)) {
				b = true;
			}
		}
//-@-123
		/*//125deleteif (isAether) {
			if(!(minecraft.renderGlobal instanceof PFLM_RenderGlobalAltAether)) {
				b = true;
			}
		}*///125delete
//@-@123
		if (b)
		{
			minecraft.theWorld.removeWorldAccess(minecraft.renderGlobal);
			minecraft.renderGlobal = new PFLM_RenderGlobalAlt(minecraft, minecraft.renderEngine);
			minecraft.renderGlobal.setWorldAndLoadRenderers(minecraft.theWorld);
			EntityPlayer entityplayer;

			for (Iterator iterator = minecraft.theWorld.playerEntities.iterator(); iterator.hasNext(); minecraft.renderGlobal.obtainEntitySkin(entityplayer))
			{
				Object obj = iterator.next();
				entityplayer = (EntityPlayer)obj;
				minecraft.renderGlobal.releaseEntitySkin(entityplayer);
			}
		}

		if (minecraft.thePlayer != null)
		{
			EntityPlayerSP entityplayersp = minecraft.thePlayer;
			float f1 = ((EntityPlayer)(entityplayersp)).moveForward * ((EntityPlayer)(entityplayersp)).moveForward + ((EntityPlayer)(entityplayersp)).moveStrafing * ((EntityPlayer)(entityplayersp)).moveStrafing;
			byte byte0 = ((EntityPlayer)(entityplayersp)).dataWatcher.getWatchableObjectByte(16);

			if (entityplayersp.isRiding() && byte0 > 0 || ((EntityPlayer)(entityplayersp)).isJumping || ((EntityPlayer)(entityplayersp)).sleeping)
			{
				((EntityPlayer)(entityplayersp)).dataWatcher.updateObject(16, Byte.valueOf((byte)0));
			}
			else if (byte0 == 1 && (double)f1 > 0.20000000000000001D)
			{
				((EntityPlayer)(entityplayersp)).dataWatcher.updateObject(16, Byte.valueOf((byte)0));
			}
			else if (byte0 == 2 && f1 > 0.0F)
			{
				((EntityPlayer)(entityplayersp)).dataWatcher.updateObject(16, Byte.valueOf((byte)0));
			}
		}
		/*b166//*/return true;
		// b166deletereturn;
	}

	private double getPosX(Minecraft minecraft) {
		return minecraft.thePlayer.posX;
	}

	private double getPosY(Minecraft minecraft) {
		return minecraft.thePlayer.posY;
	}

	private double getPosZ(Minecraft minecraft) {
		return minecraft.thePlayer.posZ;
	}

    public void serverConnect(NetClientHandler netClientHandler) {
    	if (playerFormLittleMaidVersion > 129) {
    		clientConnect(netClientHandler);
    		return;
    	}
/*//125delete
    	Modchu_Debug.mDebug("serverConnect");
		double x = getPosX(mc);
		double y = getPosY(mc);
		double z = getPosZ(mc);
		EntityPlayerSP var9 = mc.thePlayer;
		int var10 = 0;

		if (mc.thePlayer != null)
		{
			var10 = mc.thePlayer.entityId;
			mc.theWorld.setEntityDead(mc.thePlayer);
		}
		Modchu_Debug.Debug("Replace PFLM_PlayerController.");
		mc.playerController = new PFLM_PlayerControllerMP(mc, netClientHandler);
		PFLM_PlayerControllerMP.entityplayerformlittlemaidmp = new PFLM_EntityPlayerMP(mc, mc.theWorld, mc.session, netClientHandler);
		if (mc.thePlayer != null) mc.thePlayer.setDead();
		mc.thePlayer = PFLM_PlayerControllerMP.entityplayerformlittlemaidmp;
		mc.renderViewEntity = null;
		setPosition(x, y, z);
		if (mc.thePlayer instanceof PFLM_EntityPlayerMP) {
			((PFLM_EntityPlayerMP) mc.thePlayer).copyPlayer(var9);
		} else {
//-@-b166
			if (isPlayerAPI) {
				((PFLM_EntityPlayer) mc.thePlayer).copyPlayer(var9);
			} else {
//@-@b166
				((PFLM_EntityPlayerSP) mc.thePlayer).copyPlayer(var9);
//-@-b166
			}
//@-@b166
		}
		mc.renderViewEntity = mc.thePlayer;
		mc.playerController.flipPlayer(mc.thePlayer);
		mc.theWorld.spawnPlayerWithLoadedChunks(mc.thePlayer);
		mc.thePlayer.movementInput = new MovementInputFromOptions(mc.gameSettings);
		mc.thePlayer.entityId = var10;
		mc.thePlayer.func_6420_o();
//-@-b173
		if (mc.theWorld.getWorldInfo().getGameType() == 1) {
			((PlayerControllerMP) mc.playerController).setCreative(true);
			mc.playerController.isInTestMode = true;
		} else {
			((PlayerControllerMP) mc.playerController).setCreative(false);
			mc.playerController.func_6473_b(mc.thePlayer);
		}
//@-@b173
*///125delete
    }

    public void clientConnect(NetClientHandler netClientHandler) {
//-@-125
    	netclienthandler = netClientHandler;
    	mc = Minecraft.getMinecraft();
		double x = 0.0D;
		double y = 0.0D;
		double z = 0.0D;
		boolean setPositionFlag = false;
		if (mc.thePlayer != null) {
			setPositionFlag = true;
			x = getPosX(mc);
			y = getPosY(mc);
			z = getPosZ(mc);
		}
		EntityPlayerSP var9 = mc.thePlayer;
		//Modchu_Debug.Debug("get x="+x+" y+"+y+" z="+z);
		try {
			EnumGameType enumGameType = (EnumGameType) getPrivateValue(net.minecraft.src.PlayerControllerMP.class, mc.playerController, 11);
			//int var2 = 0;
			//if (mc.thePlayer != null) var2 = mc.thePlayer.entityId;

			if(isSSP) {
				enumGameType = mc.theWorld.getWorldInfo().getGameType();
				Class[] types = { Minecraft.class , NetClientHandler.class };
				Object[] args = {mc, netclienthandler};
				if (enumGameType != null) {
					//Modchu_Debug.Debug("enumGameType="+enumGameType);
					if (enumGameType == EnumGameType.CREATIVE) {
						mc.playerController = (PlayerControllerMP) Modchu_Reflect.newInstance(pflm_playerControllerCreative2, types, args);
						//mc.playerController = new PFLM_PlayerControllerCreative2(mc, netClientHandler);
						Modchu_Debug.Debug("Replace PFLM_PlayerControllerCreative2.");
					} else {
						mc.playerController = (PlayerControllerMP) Modchu_Reflect.newInstance(pflm_playerController2, types, args);
						//mc.playerController = new PFLM_PlayerController2(mc, netClientHandler);
						Modchu_Debug.Debug("Replace PFLM_PlayerController2.");
					}
				} else {
					mc.playerController = (PlayerControllerMP) Modchu_Reflect.newInstance(pflm_playerController2, types, args);
					//mc.playerController = new PFLM_PlayerController2(mc, netClientHandler);
					Modchu_Debug.Debug("Replace PFLM_PlayerController2.");
				}
			} else {
				mc.playerController = new PFLM_PlayerController(mc, netClientHandler);
				Modchu_Debug.Debug("Replace PFLM_PlayerController.");
			}

			if (enumGameType != null) {
				Modchu_Debug.mDebug("enumGameType="+enumGameType);
				mc.playerController.setGameType(enumGameType);
			}
			if (mc.thePlayer != null) mc.setDimensionAndSpawnPlayer(mc.thePlayer.dimension);
			else mc.setDimensionAndSpawnPlayer(0);
			if(isSSP
					&& enumGameType != null
					&& enumGameType == EnumGameType.CREATIVE) {
				Object ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(Class.forName(getClassName("PlayerControllerCreative")), "a", new Class[]{EntityPlayer.class}), mc.playerController, mc.thePlayer);
				//((PFLM_PlayerControllerCreative2) mc.playerController).setPlayerCapabilities(mc.thePlayer);
				ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_playerControllerCreative2, "setInCreativeMode", new Class[]{boolean.class}), mc.playerController, true);
				//((PFLM_PlayerControllerCreative2) mc.playerController).setInCreativeMode(true);
				ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_entityPlayerSP2, "copyPlayer", new Class[]{EntityPlayer.class}), mc.thePlayer, var9);
				//((PFLM_EntityPlayerSP2) mc.thePlayer).copyPlayer(var9);
				//Modchu_Debug.Debug("isSSP CREATIVE set");
			}
//@-@125
/*//@/
			if(!isSmartMoving
					&& isModelSize
					&& !isPlayerAPIDebug) {
				PFLM_PlayerBaseServer.registerPlayerBase();
				Modchu_Debug.Debug("PlayerAPI Server register.");
			}
*///@/
/*
			int var3 = mc.thePlayer.dimension;
		        mc.theWorld.setSpawnLocation();
		        mc.theWorld.removeAllEntities();
		        if (mc.thePlayer != null)
		        {
		        	mc.theWorld.setEntityDead(mc.thePlayer);
		        }
		        mc.renderViewEntity = null;
		        mc.thePlayer = mc.playerController.func_78754_a(mc.theWorld);
		        mc.thePlayer.dimension = var3;
		        mc.renderViewEntity = mc.thePlayer;
		        mc.thePlayer.preparePlayerToSpawn();
		        mc.theWorld.spawnEntityInWorld(mc.thePlayer);
		        mc.playerController.flipPlayer(mc.thePlayer);
		        mc.thePlayer.movementInput = new MovementInputFromOptions(mc.gameSettings);
		        if (var2 > 0) mc.thePlayer.entityId = var2;
		        mc.playerController.setPlayerCapabilities(mc.thePlayer);
		        mc.thePlayer.sendQueue.handleClientCommand(new Packet205ClientCommand(1));
*/
//-@-125
			if (setPositionFlag) {
				//double d = (double)(1.8F - getHeight());
				//if (d < 0) d = -d + 1.0D;
				//d = d + 0.5D;
				Modchu_Debug.mDebug("setPositionFlag getHeight()="+getHeight());
				if (isModelSize) {
					setPosition(x, y, z);
					setPositionCorrection(0.0D, 0.5D, 0.0D);
				}
				else setPosition(x, y, z);
			}
			clearPlayers();
			//Modchu_Debug.mDebug("setPosition x="+x+" y+"+y+" z="+z);
		} catch (Exception e) {
			e.printStackTrace();
		}
//@-@125
    }
/*//125delete
	public void playerControllerReplace(Minecraft minecraft) {
		if (minecraft.thePlayer.worldObj.isRemote) return;
		double x = getPosX(minecraft);
		double y = getPosY(minecraft);
		double z = getPosZ(minecraft);
		//Modchu_Debug.Debug("get x="+x+" y+"+y+" z="+z);
		EntityPlayerSP var9 = minecraft.thePlayer;
		int var10 = 0;

		if (minecraft.thePlayer != null)
		{
			var10 = minecraft.thePlayer.entityId;
			minecraft.theWorld.setEntityDead(minecraft.thePlayer);
		}
		//Modchu_Debug.Debug("x="+minecraft.thePlayer.posX+" y="+minecraft.thePlayer.posY+" z="+minecraft.thePlayer.posZ);
*///125delete
		/*b173//*/// 125deleteif (!minecraft.playerController.isInTestMode) {
/*//125delete
			mod_PFLM_PlayerFormLittleMaid
					.Modchu_Debug.Debug("Replace PFLM_PlayerController.");
			minecraft.playerController = new PFLM_PlayerController(minecraft);
//-@-b166
			if (isPlayerAPI) {
				PFLM_PlayerController.entityplayerformlittlemaid = new PFLM_EntityPlayer(minecraft, minecraft.theWorld, minecraft.session, minecraft.theWorld.worldProvider.worldType);
				if (minecraft.thePlayer != null) minecraft.thePlayer.setDead();
				minecraft.thePlayer = PFLM_PlayerController.entityplayerformlittlemaid;
			} else {
//@-@b166
				PFLM_PlayerController.entityplayerformlittlemaidsp = new PFLM_EntityPlayerSP(minecraft, minecraft.theWorld, minecraft.session, minecraft.theWorld.worldProvider.worldType);
				if (minecraft.thePlayer != null) minecraft.thePlayer.setDead();
				minecraft.thePlayer = PFLM_PlayerController.entityplayerformlittlemaidsp;
//-@-b166
			}
//@-@b166
//-@-b173
 		} else {
			mod_PFLM_PlayerFormLittleMaid
					.Modchu_Debug.Debug("Replace PFLM_PlayerControllerCreative.");
			minecraft.playerController = new PFLM_PlayerControllerCreative(
					minecraft);
			if (isPlayerAPI) {
				PFLM_PlayerControllerCreative.entityplayerformlittlemaid = new PFLM_EntityPlayer(minecraft, minecraft.theWorld, minecraft.session, minecraft.theWorld.worldProvider.worldType);
				if (minecraft.thePlayer != null) minecraft.thePlayer.setDead();
				minecraft.thePlayer = PFLM_PlayerControllerCreative.entityplayerformlittlemaid;
			} else {
				PFLM_PlayerControllerCreative.entityplayerformlittlemaidsp = new PFLM_EntityPlayerSP(minecraft, minecraft.theWorld, minecraft.session, minecraft.theWorld.worldProvider.worldType);
				if (minecraft.thePlayer != null) minecraft.thePlayer.setDead();
				minecraft.thePlayer = PFLM_PlayerControllerCreative.entityplayerformlittlemaidsp;
			}
		}
//@-@b173
		//if (minecraft.thePlayer != null) minecraft.theWorld.setEntityDead(minecraft.thePlayer);

		minecraft.renderViewEntity = null;
		//minecraft.thePlayer = null;
		//minecraft.thePlayer = (EntityPlayerSP)minecraft.playerController.createPlayer(minecraft.theWorld);
		setPosition(x, y, z);
		//Modchu_Debug.Debug("setPosition x="+x+" y+"+y+" z="+z);

		// b181deleteif (mc.thePlayer instanceof PFLM_EntityPlayerMP) {
			// b181delete((PFLM_EntityPlayerMP) mc.thePlayer).copyPlayer(var9);
		// b181delete} else {
//-@-b166
			// b181deleteif (isPlayerAPI) {
				// b181delete((PFLM_EntityPlayer) mc.thePlayer).copyPlayer(var9);
			// b181delete} else {
//@-@b166
				// b181delete((PFLM_EntityPlayerSP) mc.thePlayer).copyPlayer(var9);
//-@-b166
			// b181delete}
//@-@b166
		// b181delete}

//-@-b166
		minecraft.thePlayer.copyPlayer(var9);
//@-@b166
		//minecraft.thePlayer.dimension = par2;
		minecraft.renderViewEntity = minecraft.thePlayer;
		//minecraft.thePlayer.preparePlayerToSpawn();
		//minecraft.thePlayer.setLocationAndAngles((double)((float)minecraft.thePlayer.posX + 0.5F), (double)((float)minecraft.thePlayer.posY + 0.1F), (double)((float)minecraft.thePlayer.posZ + 0.5F), 0.0F, 0.0F);

		minecraft.playerController.flipPlayer(minecraft.thePlayer);
		minecraft.theWorld.spawnPlayerWithLoadedChunks(minecraft.thePlayer);
		minecraft.thePlayer.movementInput = new MovementInputFromOptions(minecraft.gameSettings);

		minecraft.thePlayer.entityId = var10;
		minecraft.thePlayer.func_6420_o();
*///125delete
		/*b173//*/// 125deleteif(minecraft.playerController.isInCreativeMode()) minecraft.playerController.func_6473_b(minecraft.thePlayer);
	// 125delete}

	public void setPosition(double x, double y, double z) {
		/*//125deleteif (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
    	} else return;
    	if (mc.thePlayer.worldObj.isRemote) {
    		((PFLM_EntityPlayerMP) gotcha).setPosition(x, y, z);
    	} else /*125delete*/
//-@-b166
		if (isPlayerAPI) {
    		PFLM_EntityPlayer.gotcha.setPosition(x, y, z);
    	} else {
//-@-125
    		if (isSSP) {
				//Object ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(Entity.class, "setPosition", new Class[]{double.class, double.class, double.class}), gotcha, x, y, z);
    			((Entity) gotcha).setPosition(x, y, z);
    		} else
//@-@125
//@-@b166
    		((PFLM_EntityPlayerSP) gotcha).setPosition(x, y, z);
    	/*b166//*/}
    }

    public static void setPositionCorrection(double x, double y, double z) {
    	/*//125deleteif (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
    	} else return;
    	if (mc.thePlayer.worldObj.isRemote) {
    		((PFLM_EntityPlayerMP) gotcha).setPositionCorrection(x, y, z);
    	} else /*125delete*/
//-@-b166
    	if (isPlayerAPI) {
    		PFLM_EntityPlayer.gotcha.setPositionCorrection(x, y, z);
    	} else {
//-@-125
    		if (isSSP) {
    			Object ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_entityPlayerSP2, "setPositionCorrection", new Class[]{double.class, double.class, double.class}), gotcha, x, y, z);
    			//((PFLM_EntityPlayerSP2) gotcha).setPositionCorrection(x, y, z);
    		} else
//@-@125
//@-@b166
    		((PFLM_EntityPlayerSP) gotcha).setPositionCorrection(x, y, z);
    	/*b166//*/}
    }

    public static boolean gotchaNullCheck() {
//-@-125
    	return true;
//@-@125
/*//125delete
    	if (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
        } else return true;
    	if (mc.thePlayer.worldObj.isRemote) {
    		if (mc.thePlayer instanceof PFLM_EntityPlayerMP) return true;
    		NetClientHandler netClientHandler = (NetClientHandler) getPrivateValue(PlayerControllerMP.class, mc.playerController, 9);
    		if (netClientHandler == null) return false;
    		mod_playerformlittlemaid.serverConnect(netClientHandler);
    	} else
//-@-b166
    	if (isPlayerAPI) {
    		if (PFLM_EntityPlayer.gotcha == null) {
    			//if(mc.currentScreen != null) return;
    			mod_playerformlittlemaid.playerControllerReplace(mc);
    		}
    	} else {
//@-@b166
    		if (gotcha == null) {
    			//if(mc.currentScreen != null) return;
    			mod_playerformlittlemaid.playerControllerReplace(mc);
    		}
//-@-b166
    	}
//@-@b166
    	return true;
*///125delete
    }

    public static String getArmorName(String s) {
		if (s == null) return "";
		String s1 = s;
		//int i = s.lastIndexOf('_');
		//if (i > -1) s1 = s.substring(i + 1);
		if (PFLM_Texture.armors
				.get(s) == null) {
			if (s.indexOf("Biped") == -1) {
				String[] cheackModelName = {
						"Elsa","Pawapro"
				};
				for (int i = 0 ; i < cheackModelName.length ; i++) {
					if (s.indexOf(cheackModelName[i]) != -1) {
						return "_erasearmor";
					}
				}
				s1 = "default";
			} else {
				s1 = "_Biped";
			}
		}
		return s1;
	}

    public static void setSkinUrl(String s) {
    	/*//125deleteif (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
    		if (mc.thePlayer.worldObj.isRemote) {
    			((PFLM_EntityPlayerMP) gotcha).skinUrl = s;
    		} else /*125delete*/
//-@-b166
    		if (isPlayerAPI) {
    			((PFLM_PlayerBase) gotcha).player.skinUrl = s;
    		} else {
//-@-125
    			if (isSSP) {
    				Modchu_Reflect.setFieldObject(Modchu_Reflect.getField(pflm_entityPlayerSP2, "skinUrl"), null, s);
    				//((PFLM_EntityPlayerSP2) gotcha).skinUrl = s;
    			} else
//@-@125
//@-@b166
    			((PFLM_EntityPlayerSP) gotcha).skinUrl = s;
        	/*b166//*/}
    	// 125delete}
    }

    public static void setTexture(String s) {
    	/*//125deleteif (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
    		if (mc.thePlayer.worldObj.isRemote) {
    			((PFLM_EntityPlayerMP) gotcha).texture = s;
    		} else /*125delete*/
//-@-b166
    		if (isPlayerAPI) {
    			((PFLM_PlayerBase) gotcha).setPlayerTexture(s);
    		} else {
//-@-125
    			if (isSSP) {
        			Object ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_entityPlayerSP2, "setPlayerTexture", new Class[]{String.class}), gotcha, s);
    				//((PFLM_EntityPlayerSP2) gotcha).setPlayerTexture(s);
    			} else
//@-@125
//@-@b166
    			((PFLM_EntityPlayerSP) gotcha).setPlayerTexture(s);
        	/*b166//*/}
    	// 125delete}
    }

	public static void newModel() {
//-@-110
		if (isSmartMoving) {
			textureModel = new ModelPlayerFormLittleMaidSmart[3];
			othersTextureModel = new ModelPlayerFormLittleMaidSmart[3];
			othersIndividualTextureModel = new ModelPlayerFormLittleMaidSmart[3];
			shortcutKeysTextureModel = new ModelPlayerFormLittleMaidSmart[3];
		} else {
//@-@110
			textureModel = new ModelPlayerFormLittleMaidBaseBiped[3];
			othersTextureModel = new ModelPlayerFormLittleMaidBaseBiped[3];
			othersIndividualTextureModel = new ModelPlayerFormLittleMaidBaseBiped[3];
			shortcutKeysTextureModel = new ModelPlayerFormLittleMaidBaseBiped[3];
		/*110//*/}
	}

    public static void resetHeight() {
    	/*//125deleteif (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
    		if (mc.thePlayer.worldObj.isRemote) {
    			if (gotcha != null) ((PFLM_EntityPlayerMP) gotcha).resetHeight();
    		} else /*125delete*/
//-@-b166
    		if (isPlayerAPI) {
    			PFLM_EntityPlayer.gotcha.resetHeight();
    		} else {
//-@-125
    			if (isSSP) {
    				Object ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_entityPlayerSP2, "publicResetHeight"), gotcha);
    			} else
//@-@125
//@-@b166
    			((PFLM_EntityPlayerSP) gotcha).resetHeight();
        	/*b166//*/}
    	// 125delete}
    }

    public static void clearPlayers() {
    	if (mc.renderGlobal != null
    			&& mc.thePlayer != null) {
    		mc.renderGlobal.releaseEntitySkin(mc.thePlayer);
    		mc.renderGlobal.obtainEntitySkin(mc.thePlayer);
    	}
//-@-110
    	if(isSmartMoving) {
    		PFLM_RenderPlayerSmart.clearPlayers();
    		//PFLM_RenderPlayerSmart.resetFlag = true;
    	} else {
//-@-125
    		if (isOlddays) {
    			Object ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "clearPlayers"));
    			//PFLM_RenderPlayer2.clearPlayers();
    		} else
//@-@125
//@-@110
    		PFLM_RenderPlayer.clearPlayers();
    		//PFLM_RenderPlayer.resetFlag = true;
    	/*110//*/}
    }

    public static Object[] getDefaultModel() {
//-@-110
    	if(isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig;
    	} else {
//-@-125
    		if (isOlddays) {
    			return (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			//return PFLM_RenderPlayer2.getModelBasicOrig();
    		} else
//@-@125
//@-@110
    		return PFLM_RenderPlayer.modelBasicOrig;
    	/*110//*/}
    }

    public static void setResetFlag(boolean flag) {
//-@-110
    	if (isSmartMoving) {
    		PFLM_RenderPlayerSmart.resetFlag = flag;
    	} else {
//-@-125
    		if (isOlddays) {
    			Modchu_Reflect.setFieldObject(Modchu_Reflect.getField(pflm_renderPlayer2, "resetFlag"), null, flag);
    			//PFLM_RenderPlayer2.resetFlag = flag;
    		} else
//@-@125
//@-@110
    		PFLM_RenderPlayer.resetFlag = flag;
    	/*110//*/}
    }

    public static void setTextureResetFlag(boolean flag) {
//-@-110
    	if (isSmartMoving) {
    		PFLM_RenderPlayerSmart.textureResetFlag = flag;
    	} else {
//-@-125
    		if (isOlddays) {
    			Modchu_Reflect.setFieldObject(Modchu_Reflect.getField(pflm_renderPlayer2, "textureResetFlag"), null, flag);
    			//PFLM_RenderPlayer2.textureResetFlag = flag;
    		} else
//@-@125
//@-@110
    		PFLM_RenderPlayer.textureResetFlag = flag;
    	/*110//*/}
    }

    public static void setSize(float f1, float f2) {
    	/*//125deleteif (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
    		if (mc.thePlayer.worldObj.isRemote) {
    			((PFLM_EntityPlayerMP) gotcha).setSize(f1, f2);
    		} else /*125delete*/
//-@-b166
    		if (isPlayerAPI) {
    			PFLM_EntityPlayer.gotcha.setSize(f1, f2);
    		} else {
//-@-125
    			if (isSSP) {
    				Object ret = Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_entityPlayerSP2, "publicSetSize", new Class[]{float.class, float.class}), gotcha, f1, f2);
    				//((PFLM_EntityPlayerSP2) gotcha).publicSetSize(f1, f2);
    			} else
//@-@125
//@-@b166
    			((PFLM_EntityPlayerSP) gotcha).setSize(f1, f2);
    		/*b166//*/}
    	// 125delete}
    }

    public static void setFirstPersonHandResetFlag(boolean flag) {
//-@-110
    	if (isSmartMoving) {
    		PFLM_RenderPlayerSmart.firstPersonHandResetFlag = flag;
    	} else {
//-@-125
    		if (isOlddays) {
    			Modchu_Reflect.setFieldObject(Modchu_Reflect.getField(pflm_renderPlayer2, "firstPersonHandResetFlag"), null, flag);
    			//PFLM_RenderPlayer2.firstPersonHandResetFlag = flag;
    		} else
//@-@125
//@-@110
    			PFLM_RenderPlayer.firstPersonHandResetFlag = flag;
    	/*110//*/}
    }

    public static float getModelScale() {
    	return getModelScale(textureModel[0]);
    }

    public static float getModelScale(Object o) {
    	if (o != null) {
//-@-110
    		if (isSmartMoving) {
    			return ((ModelPlayerFormLittleMaidSmart) o).getModelScale();
    		} else {
//@-@110
    			return ((ModelPlayerFormLittleMaidBaseBiped) o).getModelScale();
    		/*110//*/}
    	}
    	//-@-110
    	if (isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig[0].getModelScale();
    	} else {
//-@-125
    		if (isOlddays) {
    			Object[] obj = (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelScale"), obj[0]));
    			//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getModelScale();
    		} else
//@-@125
//@-@110
    		return PFLM_RenderPlayer.modelBasicOrig[0].getModelScale();
    	/*110//*/}
    }

    public static float getWidth() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).getWidth();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).getWidth();
    			/*110//*/}
    		}
    	}
    	//-@-110
    	if (isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig[0].getWidth();
    	} else {
//-@-125
    		if (isOlddays) {
    			Object[] obj = (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getWidth"), obj[0]));
    			//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getWidth();
    		} else
//@-@125
//@-@110
    		return PFLM_RenderPlayer.modelBasicOrig[0].getWidth();
    	/*110//*/}
    }

    public static float getHeight() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).getHeight();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).getHeight();
    			/*110//*/}
    		}
    	}
    	//-@-110
    	if (isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig[0].getHeight();
    	} else {
//-@-125
    		if (isOlddays) {
    			Object[] obj = (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getHeight"), obj[0]));
    			//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getHeight();
    		} else
//@-@125
//@-@110
    		return PFLM_RenderPlayer.modelBasicOrig[0].getHeight();
    	/*110//*/}
    }

    public static float getyOffset() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).getyOffset();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).getyOffset();
    			/*110//*/}
    		}
    	}
    	//-@-110
    	if (isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig[0].getyOffset();
    	} else {
//-@-125
    		if (isOlddays) {
    			Object[] obj = (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getyOffset"), obj[0]));
    			//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getyOffset();
    		} else
//@-@125
//@-@110
    			return PFLM_RenderPlayer.modelBasicOrig[0].getyOffset();
    	/*110//*/}
    }

    public static float getRidingWidth() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).getRidingWidth();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).getRidingWidth();
    			}
    		/*110//*/}
    	}
    	//-@-110
    	if (isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig[0].getRidingWidth();
    	} else {
//-@-125
    		if (isOlddays) {
    			Object[] obj = (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getRidingWidth"), obj[0]));
    			//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getRidingWidth();
    		} else
//@-@125
//@-@110
    			return PFLM_RenderPlayer.modelBasicOrig[0].getRidingWidth();
    	/*110//*/}
    }

    public static float getRidingHeight() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).getRidingHeight();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).getRidingHeight();
    			}
    		/*110//*/}
    	}
    	//-@-110
    	if (isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig[0].getRidingHeight();
    	} else {
//-@-125
    		if (isOlddays) {
    			Object[] obj = (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getRidingHeight"), obj[0]));
    			//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getRidingHeight();
    		} else
//@-@125
//@-@110
    		return PFLM_RenderPlayer.modelBasicOrig[0].getRidingHeight();
    	/*110//*/}
    }

    public static float getRidingyOffset() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).getRidingyOffset();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).getRidingyOffset();
    			/*110//*/}
    		}
    	}
    	//-@-110
    	if (isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig[0].getRidingyOffset();
    	} else {
//-@-125
    		if (isOlddays) {
    			Object[] obj = (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getRidingyOffset"), obj[0]));
    			//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getRidingyOffset();
    		} else
//@-@125
//@-@110
    		return PFLM_RenderPlayer.modelBasicOrig[0].getRidingyOffset();
    	/*110//*/}
    }

    public static double getMountedYOffset() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).getMountedYOffset();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).getMountedYOffset();
    			/*110//*/}
    		}
    	}
    	//-@-110
    	if (isSmartMoving) {
    		return PFLM_RenderPlayerSmart.modelBasicOrig[0].getMountedYOffset();
    	} else {
//-@-125
    		if (isOlddays) {
    			Object[] obj = (Object[]) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getModelBasicOrig"));
    			return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getMountedYOffset"), obj[0]));
    			//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getMountedYOffset();
    		} else
//@-@125
//@-@110
    		return PFLM_RenderPlayer.modelBasicOrig[0].getMountedYOffset();
    	/*110//*/}
    }

    public static boolean getIsRiding() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).isRiding;
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).isRiding;
    			/*110//*/}
    		}
    	}
    	return false;
    }

    public static float getPhysical_Hammer() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).Physical_Hammer();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).Physical_Hammer();
    			/*110//*/}
    		}
    	}
    	return Physical_Hammer;
    }

    public static float ridingViewCorrection() {
    	if (textureModel != null) {
    		if (textureModel[0] != null) {
//-@-110
    			if (isSmartMoving) {
    				return ((ModelPlayerFormLittleMaidSmart) textureModel[0]).ridingViewCorrection();
    			} else {
//@-@110
    				return ((ModelPlayerFormLittleMaidBaseBiped) textureModel[0]).ridingViewCorrection();
    			/*110//*/}
    		}
    	}
    	return 0.0F;
    }

    public static float getOnGround() {
//-@-110
    	if (isSmartMoving) {
            PFLM_ModelDataSmart modelDataPlayerFormLittleMaid = (PFLM_ModelDataSmart)PFLM_RenderPlayerSmart.playerData.get(mc.thePlayer);
    		return modelDataPlayerFormLittleMaid.mainModel.onGround;
    	} else {
//@-@110
            PFLM_ModelData modelDataPlayerFormLittleMaid = (PFLM_ModelData)PFLM_RenderPlayer.playerData.get(mc.thePlayer);
    		return modelDataPlayerFormLittleMaid.mainModel.onGround;
    	/*110//*/}
    }

    public static float getEyeHeight() {
    	/*//125deleteif (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
    		if (mc.thePlayer.worldObj.isRemote) {
    			((PFLM_EntityPlayerMP) gotcha).resetHeight();
    		} else /*125delete*/
//-@-b166
    		if (isPlayerAPI) {
    			return PFLM_EntityPlayer.gotcha.getEyeHeight();
    		} else {
//-@-125
    			if (isSSP) {
    				return Float.valueOf((String) Modchu_Reflect.invoke(Modchu_Reflect.getMethod(pflm_renderPlayer2, "getEyeHeight"), gotcha));
    				//return ((PFLM_EntityPlayerSP2) gotcha).getEyeHeight();
    			} else
//@-@125
//@-@b166
    			return ((PFLM_EntityPlayerSP) gotcha).getEyeHeight();
        	/*b166//*/}
    	// 125delete}
    	// 125deletereturn 0.12F;
    }

    public static String getUsername() {
    	/*//125deleteif (mc.thePlayer != null
    			&& mc.thePlayer.worldObj != null) {
    		if (mc.thePlayer.worldObj.isRemote) {
    			return ((PFLM_EntityPlayerMP) gotcha).username;
    		} else /*125delete*/
//-@-b166
    		if (isPlayerAPI) {
    			return ((PFLM_PlayerBase) gotcha).player.username;
    		} else {
//-@-125
    			if (isSSP) {
    				return (String) Modchu_Reflect.getFieldObject(Modchu_Reflect.getField(pflm_entityPlayerSP2, "username"), gotcha);
    				//return ((PFLM_EntityPlayerSP2) gotcha).username;
    			} else
//@-@125
//@-@b166
    			return ((PFLM_EntityPlayerSP) gotcha).username;
        	/*b166//*/}
    	// 125delete}
    	// 125deletereturn null;
    }

    public static boolean getChangeModelFlag() {
//-@-110
    	if (isSmartMoving) {
    		PFLM_ModelDataSmart modelDataPlayerFormLittleMaid = (PFLM_ModelDataSmart)PFLM_RenderPlayerSmart.playerData.get(mc.thePlayer);
    		return modelDataPlayerFormLittleMaid.changeModelFlag;
    	} else {
//@-@110
    		PFLM_ModelData modelDataPlayerFormLittleMaid = (PFLM_ModelData)PFLM_RenderPlayer.playerData.get(mc.thePlayer);
    		return modelDataPlayerFormLittleMaid.changeModelFlag;
    	/*110//*/}
    }

    public static void setChangeModelFlag(boolean b) {
//-@-110
    	if (isSmartMoving) {
    		PFLM_ModelDataSmart modelDataPlayerFormLittleMaid = (PFLM_ModelDataSmart)PFLM_RenderPlayerSmart.playerData.get(mc.thePlayer);
    		modelDataPlayerFormLittleMaid.changeModelFlag = b;
    	} else {
//@-@110
    		PFLM_ModelData modelDataPlayerFormLittleMaid = (PFLM_ModelData)PFLM_RenderPlayer.playerData.get(mc.thePlayer);
    		modelDataPlayerFormLittleMaid.changeModelFlag = b;
    	/*110//*/}
    }

    public static int getHandednessMode() {
//-@-110
    	if (isSmartMoving) {
    		PFLM_ModelDataSmart modelDataPlayerFormLittleMaid = (PFLM_ModelDataSmart)PFLM_RenderPlayerSmart.playerData.get(mc.thePlayer);
    		return modelDataPlayerFormLittleMaid.handedness;
    	} else {
//@-@110
    		PFLM_ModelData modelDataPlayerFormLittleMaid = (PFLM_ModelData)PFLM_RenderPlayer.playerData.get(mc.thePlayer);
    		return modelDataPlayerFormLittleMaid.handedness;
    	/*110//*/}
    }

    public static void setHandednessMode(int i) {
//-@-110
    	if (isSmartMoving) {
    		PFLM_ModelDataSmart modelDataPlayerFormLittleMaid = (PFLM_ModelDataSmart)PFLM_RenderPlayerSmart.playerData.get(mc.thePlayer);
    		modelDataPlayerFormLittleMaid.handedness = i;
    	} else {
//@-@110
    		PFLM_ModelData modelDataPlayerFormLittleMaid = (PFLM_ModelData)PFLM_RenderPlayer.playerData.get(mc.thePlayer);
    		modelDataPlayerFormLittleMaid.handedness = i;
    	/*110//*/}
    }

    public static void setShortcutKeysAction(boolean b) {
//-@-110
    	if (isSmartMoving) {
    		PFLM_ModelDataSmart modelDataPlayerFormLittleMaid = PFLM_RenderPlayerSmart.getPlayerData(mc.thePlayer);
    		if (modelDataPlayerFormLittleMaid != null) {} else return;
    		modelDataPlayerFormLittleMaid.shortcutKeysAction = b;
    	} else {
//@-@110
    		PFLM_ModelData modelDataPlayerFormLittleMaid = PFLM_RenderPlayer.getPlayerData(mc.thePlayer);
    		if (modelDataPlayerFormLittleMaid != null) {} else return;
    		modelDataPlayerFormLittleMaid.shortcutKeysAction = b;
    	/*110//*/}
    }

    public static void setRunActionNumber(int i) {
//-@-110
    	if (isSmartMoving) {
    		PFLM_ModelDataSmart modelDataPlayerFormLittleMaid = PFLM_RenderPlayerSmart.getPlayerData(mc.thePlayer);
    		if (modelDataPlayerFormLittleMaid != null) {} else return;
    		modelDataPlayerFormLittleMaid.runActionNumber = i;
    	} else {
//@-@110
    		PFLM_ModelData modelDataPlayerFormLittleMaid = PFLM_RenderPlayer.getPlayerData(mc.thePlayer);
    		if (modelDataPlayerFormLittleMaid != null) {} else return;
    		modelDataPlayerFormLittleMaid.runActionNumber = i;
    	/*110//*/}
    }

    public static void setShortcutKeysActionInitFlag(boolean b) {
//-@-110
    	if (isSmartMoving) {
    		PFLM_ModelDataSmart modelDataPlayerFormLittleMaid = PFLM_RenderPlayerSmart.getPlayerData(mc.thePlayer);
    		if (modelDataPlayerFormLittleMaid != null) {} else return;
    		modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag = b;
    	} else {
//@-@110
    		PFLM_ModelData modelDataPlayerFormLittleMaid = PFLM_RenderPlayer.getPlayerData(mc.thePlayer);
    		if (modelDataPlayerFormLittleMaid != null) {} else return;
    		modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag = b;
    	/*110//*/}
    }

	public static void setTextureValue() {
		if (textureName == null
				| onlineMode) {
			return;
		}
		int i = getMaidColor();
		texture = PFLM_Texture
				.getTextureName(textureName, i);
		if (texture == null) {
			int n = 0;
			// System.out.println("setTextureValue for Start");
			for (; n < 16 && texture == null; n = n + 1) {
				i++;
				i = i & 0xf;
				// System.out.println("setTextureValue for n="+n+" i="+i);
				setMaidColor(i);
				texture = PFLM_Texture
						.getTextureName(textureName, i);
			}
			if (texture == null) {
				setNextTexturePackege(0);
				texture = PFLM_Texture
						.getTextureName(textureName, i);
			}
		}
		textureModel[0] = null;
		textureModel[1] = null;
		textureModel[2] = null;
		int j = textureName.lastIndexOf('_');
		String s = textureName.substring(j + 1);
		if (j > -1) {
			s = textureName.substring(j + 1);
		} else {
			s = textureName;
		}
		Object[] amodelPlayerFormLittleMaid = (Object[]) PFLM_Texture.modelMap
				.get(s);
		if (amodelPlayerFormLittleMaid != null) {
			textureModel[0] = amodelPlayerFormLittleMaid[0];
		}
		// setSizeの設定値はダミー。設定は呼び出し先で
		if (isModelSize) {
			setSize(0.5F, 1.35F);
			resetHeight();
		}
		setArmorTextureValue();
	}

	public static void setColorTextureValue() {
		if (textureName == null
				| onlineMode) {
			return;
		}
		int i = getMaidColor();
		String t = texture;
		texture = PFLM_Texture
				.getTextureName(textureName, i);
		int n = 0;
		// System.out.println("setColorTextureValue for Start");
		for (; n < 16 && texture == null; n = n + 1) {
			if (PFLM_Gui.colorReverse) {
				i--;
			} else {
				i++;
			}
			i = i & 0xf;
			// System.out.println("setColorTextureValue for n="+n+" i="+i);
			setMaidColor(i);
			texture = PFLM_Texture
					.getTextureName(textureName, i);
		}
		if (texture == null) {
			texture = t;
			return;
		}
	}

	public static void setArmorTextureValue() {
		// Modchu_Debug.mDebug("setArmorTextureValue textureArmorName="+textureArmorName);
		if (onlineMode)
			return;
		if (textureArmorName == null) {
			setTextureArmorName(textureName);
			if (textureArmorName == null) {
				return;
			}
		}
		int i = textureArmorName.lastIndexOf('_');
		if (i > -1) {
			String s = "default";
			if (PFLM_Texture.armors
					.get(textureArmorName) == null) {
				String s1 = textureArmorName.substring(i);
				s1 = PFLM_Texture.getModelSpecificationArmorPackege(s1);
				if (s1 != null) {
					setTextureArmorName(s1);
					return;
				}
				String[] cheackModelName = {
						"Elsa","Pawapro"
				};
				boolean flag = false;
				for (int i2 = 0 ; i2 < cheackModelName.length ; i2++) {
					if (textureArmorName.indexOf(cheackModelName[i2]) != -1) {
						s = "erasearmor";
						flag = true;
					}
				}
				if (!flag
						&& textureName.indexOf("Biped") != -1) {
					s = "Biped";
				}
				setTextureArmorName(s);
			} else {
				s = textureArmorName.substring(i + 1);
			}
			Object[] amodelPlayerFormLittleMaid = (Object[]) PFLM_Texture.modelMap
					.get(s);
			if (amodelPlayerFormLittleMaid != null) {
				textureModel[1] = amodelPlayerFormLittleMaid[1];
				textureModel[2] = amodelPlayerFormLittleMaid[2];
			} else {
				amodelPlayerFormLittleMaid = (Object[]) (textureName.indexOf("_Biped") == -1 ?
						PFLM_Texture.modelMap.get("default") :
							PFLM_Texture.modelMap.get("Biped"));
				if (amodelPlayerFormLittleMaid != null) {
					textureModel[1] = amodelPlayerFormLittleMaid[1];
					textureModel[2] = amodelPlayerFormLittleMaid[2];
				}
			}
		}
	}

	public static void setNextTexturePackege(int i) {
		if (i == 0) {
			textureName = PFLM_Texture
					.getNextPackege(textureName,
							getMaidColor());
			setTextureArmorName(textureName);
			if (PFLM_Texture.armors
					.get(textureArmorName) == null) {
				String s1 = textureArmorName.substring(i);
				s1 = PFLM_Texture.getModelSpecificationArmorPackege(s1);
				if (s1 != null) {
					setTextureArmorName(s1);
					return;
				}
				if (textureName.indexOf("Biped") == -1) {
					String[] cheackModelName = {
							"Elsa","Pawapro"
					};
					boolean flag = false;
					for (int i2 = 0 ; i2 < cheackModelName.length ; i2++) {
						if (textureArmorName.indexOf(cheackModelName[i2]) != -1) {
							setTextureArmorName("erasearmor");
							flag = true;
						}
					}
					if (!flag) setTextureArmorName("default");
				} else {
					setTextureArmorName("Biped");
				}
			}
		}
		if (i == 1) {
			textureArmorName = PFLM_Texture
					.getNextArmorPackege(textureArmorName);
		}
		// System.out.println("setNextTexturePackege textureName="+textureName);
	}

	public static void setPrevTexturePackege(int i) {
		if (i == 0) {
			textureName = PFLM_Texture
					.getPrevPackege(textureName,
							getMaidColor());
			setTextureArmorName(textureName);
			if (PFLM_Texture.armors
					.get(textureArmorName) == null) {
				String s1 = textureArmorName.substring(i);
				s1 = PFLM_Texture.getModelSpecificationArmorPackege(s1);
				if (s1 != null) {
					setTextureArmorName(s1);
					return;
				}
				if (textureName.indexOf("Biped") == -1) {
					setTextureArmorName("erasearmor");
				} else {
					setTextureArmorName("Biped");
				}
			}
		}
		if (i == 1) {
			textureArmorName = PFLM_Texture
					.getPrevArmorPackege(textureArmorName);
		}
	}

	public static int getMaidColor() {
		return maidColor;
	}

	public static void setMaidColor(int i) {
		maidColor = i & 0xf;
	}

    public static void setTextureName(String s) {
    	textureName = s;
    }

    public static void setTextureArmorName(String s) {
    	textureArmorName = s;
    }

	public static void loadcfg() {
		// cfg読み込み
		if (cfgdir.exists()) {
			if (!mainCfgfile.exists()) {
				// cfgファイルが無い = 新規作成
				String s[] = {
						"DebugMessage=true", "DebugMessagetexture=true", "debugReflect=false", "AlphaBlend=true",
						"Physical_BurningPlayer=0", "Physical_MeltingPlayer=0", "Physical_Hammer=1.0F",
						"Physical_Undead=false", "Physical_HurtSound=damage.hit", "isPlayerForm=true",
						"isPlayerAPIDebug=false", "isModelSize=false", "isClearWater=false",
						"isVoidFog=true", "isFog=true", "isDimming=true",
						"watherFog=0.1F", "watherFog2=0.05F", "waterStillLightOpacity=3",
						"waterMovingLightOpacity=3", "lavaFog=2.0F", "transparency=1.0F",
						"textureSavedir=/output/", "guiMultiPngSaveButton=true", "EnableCommands=false",
						"EnablePOV=false", "onlineModeButton=true", "isRenderName=true",
						"isMouseOverMinecraftMenu=true", "locationPositionCorrectionY=0.5D", "waitTime=600",
						"multiAutoOnlinemode=true", "skirtFloats=false", "skirtFloatsVolume=1.0F",
						"othersPlayerWaitTime=600", "modchuRemodelingModel=true", "versionCheck=true",
						"useInvisibilityBody=true","useInvisibilityArmor=false","useInvisibilityItem=false"
				};
				Modchu_Config.writerConfig(mainCfgfile, s);
			} else {
				// cfgファイルがある
				DebugMessage = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "DebugMessage", DebugMessage)).toString());
				DebugMessagetexture = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "DebugMessagetexture", DebugMessagetexture)).toString());
				debugReflect = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "debugReflect", debugReflect)).toString());
				AlphaBlend = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "AlphaBlend", AlphaBlend)).toString());
				Physical_BurningPlayer = Integer.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "Physical_BurningPlayer", Physical_BurningPlayer)).toString());
				Physical_MeltingPlayer = Integer.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "Physical_MeltingPlayer", Physical_MeltingPlayer)).toString());
				Physical_Hammer = Float.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "Physical_Hammer", Physical_Hammer)).toString());
				Physical_Undead = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "Physical_Undead", Physical_Undead)).toString());
				Physical_HurtSound = (Modchu_Config.loadConfig(showModelList, mainCfgfile, "Physical_HurtSound", Physical_HurtSound)).toString();
				isPlayerForm = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isPlayerForm", isPlayerForm)).toString());
				isPlayerAPIDebug = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isPlayerAPIDebug", isPlayerAPIDebug)).toString());
				isModelSize = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isModelSize", isModelSize)).toString());
				isClearWater = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isClearWater", isClearWater)).toString());
				isVoidFog = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isVoidFog", isVoidFog)).toString());
				isFog = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isFog", isFog)).toString());
				isDimming = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isDimming", isDimming)).toString());
				watherFog = Float.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "watherFog", watherFog)).toString());
				watherFog2 = Float.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "watherFog2", watherFog2)).toString());
				waterStillLightOpacity = Integer.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "waterStillLightOpacity", waterStillLightOpacity)).toString());
				waterMovingLightOpacity = Integer.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "waterMovingLightOpacity", waterMovingLightOpacity)).toString());
				lavaFog = Float.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "lavaFog", lavaFog)).toString());
				transparency = Float.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "transparency", transparency)).toString());
				textureSavedir = (Modchu_Config.loadConfig(showModelList, mainCfgfile, "textureSavedir", textureSavedir)).toString();
				guiMultiPngSaveButton = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "guiMultiPngSaveButton", guiMultiPngSaveButton)).toString());
				EnableCommands = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "EnableCommands", EnableCommands)).toString());
				EnablePOV = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "EnablePOV", EnablePOV)).toString());
				onlineModeButton = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "onlineModeButton", onlineModeButton)).toString());
				isRenderName = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isRenderName", isRenderName)).toString());
				isMouseOverMinecraftMenu = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "isMouseOverMinecraftMenu", isMouseOverMinecraftMenu)).toString());
				locationPositionCorrectionY = Double.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "locationPositionCorrectionY", locationPositionCorrectionY)).toString());
				waitTime = Integer.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "waitTime", waitTime)).toString());
				multiAutoOnlinemode = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "multiAutoOnlinemode", multiAutoOnlinemode)).toString());
				skirtFloats = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "skirtFloats", skirtFloats)).toString());
				skirtFloatsVolume = Float.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "skirtFloatsVolume", skirtFloatsVolume)).toString());
				othersPlayerWaitTime = Integer.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "othersPlayerWaitTime", othersPlayerWaitTime)).toString());
				modchuRemodelingModel = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "modchuRemodelingModel", modchuRemodelingModel)).toString());
				versionCheck = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "versionCheck", versionCheck)).toString());
				useInvisibilityBody = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "useInvisibilityBody", useInvisibilityBody)).toString());
				useInvisibilityArmor = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "useInvisibilityArmor", useInvisibilityArmor)).toString());
				useInvisibilityItem = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, mainCfgfile, "useInvisibilityItem", useInvisibilityItem)).toString());
				cfgMaxMinCheck();
				String k[] = {
						"DebugMessage", "DebugMessagetexture", "debugReflect", "AlphaBlend",
						"Physical_BurningPlayer", "Physical_MeltingPlayer", "Physical_Hammer",
						"Physical_Undead", "Physical_HurtSound", "isPlayerForm",
						"isPlayerAPIDebug", "isModelSize", "isClearWater",
						"isVoidFog", "isFog", "isDimming",
						"watherFog", "watherFog2", "waterStillLightOpacity",
						"waterMovingLightOpacity", "lavaFog", "transparency",
						"textureSavedir", "guiMultiPngSaveButton", "EnableCommands",
						"EnablePOV", "onlineModeButton", "isRenderName",
						"isMouseOverMinecraftMenu", "locationPositionCorrectionY", "waitTime",
						"multiAutoOnlinemode", "skirtFloats", "skirtFloatsVolume",
						"othersPlayerWaitTime", "modchuRemodelingModel", "versionCheck",
						"useInvisibilityBody", "useInvisibilityArmor", "useInvisibilityItem"
				};
				String k1[] = {
						""+DebugMessage, ""+DebugMessagetexture, ""+debugReflect, ""+AlphaBlend,
						""+Physical_BurningPlayer, ""+Physical_MeltingPlayer, ""+Physical_Hammer,
						""+Physical_Undead, ""+Physical_HurtSound, ""+isPlayerForm,
						""+isPlayerAPIDebug, ""+isModelSize, ""+isClearWater,
						""+isVoidFog, ""+isFog, ""+isDimming,
						""+watherFog, ""+watherFog2, ""+waterStillLightOpacity,
						""+waterMovingLightOpacity, ""+lavaFog, ""+transparency,
						""+textureSavedir, ""+guiMultiPngSaveButton, ""+EnableCommands,
						""+EnablePOV, ""+onlineModeButton, ""+isRenderName,
						""+isMouseOverMinecraftMenu, ""+locationPositionCorrectionY, ""+waitTime,
						""+multiAutoOnlinemode, ""+skirtFloats, ""+skirtFloatsVolume,
						""+othersPlayerWaitTime, ""+modchuRemodelingModel, ""+versionCheck,
						""+useInvisibilityBody, ""+useInvisibilityArmor, ""+useInvisibilityItem
				};
				Modchu_Config.writerSupplementConfig(mainCfgfile, k, k1);
			}
		}
	}

	public static void cfgMaxMinCheck() {
		if (watherFog < 0) watherFog = 0.0F;
		if (watherFog > 1) watherFog = 1.0F;
		if (watherFog2 < 0) watherFog2 = 0.0F;
		if (watherFog2 > 1) watherFog2 = 1.0F;
		if (waterStillLightOpacity < 0) waterStillLightOpacity = 0;
		if (waterStillLightOpacity > 255) waterStillLightOpacity = 255;
		if (waterMovingLightOpacity < 0) waterMovingLightOpacity = 0;
		if (waterMovingLightOpacity > 255) waterMovingLightOpacity = 255;
		if (lavaFog < 0) lavaFog = 0.0F;
		if (lavaFog > 2) lavaFog = 2.0F;
		if (transparency < 0) transparency = 0.0F;
		if (transparency > 1) transparency = 1.0F;
		if (locationPositionCorrectionY < -1) locationPositionCorrectionY = -1.0D;
		if (locationPositionCorrectionY > 1) locationPositionCorrectionY = 1.0D;
		if (skirtFloatsVolume < 0) skirtFloatsVolume = 0.0F;
		if (skirtFloatsVolume > 2) skirtFloatsVolume = 2.0F;
	}

	public static void writerParamater() {
		//GUI設定ファイル書き込み
		String s[] = {
				"defaultTexture="+defaultTexture, "defaultTextureArmorName="+defaultTextureArmorName, "maidColor="+maidColor,
				"ModelScale="+PFLM_Gui.modelScale, "onlineMode="+onlineMode, "setModel="+PFLM_Gui.setModel,
				"setColor="+PFLM_Gui.setColor, "setArmor="+PFLM_Gui.setArmor, "showArmor="+PFLM_Gui.showArmor
		};
		if(texture != null) s[0]="defaultTexture="+textureName;
		if(textureArmorName != null) s[0]="textureArmorName="+textureArmorName;
		Modchu_Config.writerConfig(cfgfile, s);
	}

	public static void loadParamater() {
		// Gui設定項目読み込み
		Modchu_Debug.mDebug("loadParamater");
		if (cfgdir.exists()) {
			if (!cfgfile.exists()) {
				// コンフィグファイルが無い = 新規作成
				writerParamater();
			} else {
				// コンフィグファイルがある
				defaultTextureArmorName = (Modchu_Config.loadConfig(showModelList, cfgfile, "defaultTextureArmorName", defaultTextureArmorName)).toString();
				defaultTexture = (Modchu_Config.loadConfig(showModelList, cfgfile, "defaultTexture", defaultTexture)).toString();
				maidColor = Integer.valueOf((Modchu_Config.loadConfig(showModelList, cfgfile, "maidColor", maidColor)).toString());
				PFLM_Gui.modelScale = Float.valueOf((Modchu_Config.loadConfig(showModelList, cfgfile, "ModelScale", PFLM_Gui.modelScale)).toString());
				PFLM_Gui.changeMode = Integer.valueOf((Modchu_Config.loadConfig(showModelList, cfgfile, "changeMode", PFLM_Gui.changeMode)).toString());
				PFLM_Gui.setModel = Integer.valueOf((Modchu_Config.loadConfig(showModelList, cfgfile, "setModel", PFLM_Gui.setModel)).toString());
				PFLM_Gui.setColor = Integer.valueOf((Modchu_Config.loadConfig(showModelList, cfgfile, "maidColor", maidColor)).toString());
				PFLM_Gui.setArmor = Integer.valueOf((Modchu_Config.loadConfig(showModelList, cfgfile, "setArmor", PFLM_Gui.setArmor)).toString());
				PFLM_Gui.showArmor = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, cfgfile, "showArmor", PFLM_Gui.showArmor)).toString());
				handednessMode = Integer.valueOf((Modchu_Config.loadConfig(showModelList, cfgfile, "handednessMode", handednessMode)).toString());
				Modchu_Config.loadConfigShowModel(showModelList, cfgfile);
				if (handednessMode < -1) handednessMode = -1;
				if (handednessMode > 1) handednessMode = 1;
			}
		}
	}

	public static void saveParamater() {
		// Gui設定項目をcfgファイルに保存
		String k[] = {
				"defaultTexture", "defaultTextureArmorName", "maidColor", "ModelScale", "changeMode",
				"setModel", "setColor", "setArmor", "showArmor", "handednessMode"
		};
		String k1[] = {
				""+defaultTexture, ""+defaultTextureArmorName, ""+maidColor, ""+PFLM_Gui.modelScale, ""+PFLM_Gui.changeMode,
				""+PFLM_Gui.setModel, ""+PFLM_Gui.setColor, ""+PFLM_Gui.setArmor, ""+PFLM_Gui.showArmor, ""+handednessMode
		};
		if (textureName != null) k1[0] = ""+textureName;
		if (textureArmorName != null) k1[1] = ""+textureArmorName;
		Modchu_Config.saveParamater(cfgfile, k, k1);
	}

	public static void loadModelList() {
		// ModelList読み込み
		if (cfgdir.exists()) {
			if (!modelListfile.exists()) {
				// ModelListファイルが無い = 新規作成
				String s[] = {
						"default", "Aokise", "Aug", "Beverly2", "chrno",
						"chu", "DogAngel", "DogAngel2", "Elsa", "Elsie" ,
						"ExtraArms", "Kagami", "Kelo", "long", "Mabel",
						"mahoro", "naz", "Petit", "QB", "Shion",
						"SR2", "suika", "taremimi", "tareusa", "twinD",
						"usagi", "Utsuho", "Yukkuri", "Elsa2", "Beverly3",
						"Angel", "Tenshi", "Yomu", "ChibiNeko", "Pawapro",
						"SA", "bgs", "brs", "ch", "dm",
						"st", "MS", "MS1", "NM", "NM1",
						"Beverly4", "Evelyn3", "VUD1", "VUD1", "VUD1",
						"VUD1", "VUD1", "VUD1", "VUD1", "VUD1",
						"VUD1", "VUD1", "VUD1", "VUD1", "VUD1",
						"VUD1", "VUD1", "VUD1", "VUD1", "VUD1",
						"VUD1", "VUD1", "VUD1", "VUD1", "VUD1",
						"VUD1", "VUD1", "VUD1", "VUD1", "VUD1",
						"Yukari", "Yukari", "Yukari", "Yukari",
						"Elsa3"
				};
				Modchu_Config.writerModelList(s, modelListfile, modelList);
			} else {
				// ModelListファイルがある
				Modchu_Config.loadList(modelListfile, modelList, "PlayerFormLittleMaidModelList.cfg");
			}
			if (!textureListfile.exists()) {
				// textureListファイルが無い = 新規作成
				String s[] = {
						"default", "ERYI_Aokise", "MMM_Aug", "b2color_Beverly2", "Cirno_chrno",
						"chu_chu", "CV_DogAngel", "CV_DogAngel2", "e10color_Elsa", "e2color_Elsie" ,
						"littleForces_ExtraArms", "CF_Kagami", "Rana_Kelo", "CF_long", "m1color_Mabel",
						"Mahoro_mahoro", "naz_naz", "MaidCV_Petit", "x32_QB", "kimono_Shion",
						"MMM_SR2", "suika_suika", "CV_taremimi", "Tei_tareusa", "CF_twinD",
						"CV_usagi", "CV_Utsuho", "Manju_Yukkuri", "e11color_Elsa2", "b4color_Beverly3",
						"CV_Angel", "CV_Tenshi" ,"CV_Yomu", "CV_ChibiNeko", "CV_Pawapro",
						"moyu_SA", "littleMaidMob_bgs", "littleMaidMob_brs", "LittleMaidMob_ch", "littleMaidMob_dm",
						"littleMaidMob_st", "littleMaidMob_MS", "littleMAidMob_MS1", "LittleMaidMob_NM", "LittleMaidMob_NM1",
						"b3color_Beverly4", "e20colorQ_Evelyn3", "LittleVocal_VUD1.Aki_VUD1", "LittleVocal_VUD1.Aku_VUD1", "LittleVocal_VUD1.Fuyu_VUD1",
						"LittleVocal_VUD1.Gumi_VUD1", "LittleVocal_VUD1.Haku_VUD1", "LittleVocal_VUD1.Haru_VUD1", "LittleVocal_VUD1.Ia_VUD1", "LittleVocal_VUD1.Kiku_VUD1",
						"LittleVocal_VUD1.Lily_VUD1", "LittleVocal_VUD1.Luka_VUD1", "LittleVocal_VUD1.Mako_VUD1", "LittleVocal_VUD1.Meiko_VUD1", "LittleVocal_VUD1.Miki_VUD1",
						"LittleVocal_VUD1.Miku_VUD1", "LittleVocal_VUD1.Momo_VUD1", "LittleVocal_VUD1.Natsu_VUD1", "LittleVocal_VUD1.Neru_VUD1", "LittleVocal_VUD1.Oto_VUD1",
						"LittleVocal_VUD1.Rin_VUD1", "LittleVocal_VUD1.Rina_VUD1", "LittleVocal_VUD1.Ruko_VUD1", "LittleVocal_VUD1.Sara_VUD1", "LittleVocal_VUD1.Tei_VUD1",
						"LittleVocal_VUD1.Teto_VUD1", "LittleVocal_VUD1.Uta_VUD1", "LittleVocal_VUD1.Yukari0_VUD1", "LittleVocal_VUD1.Yukari1_VUD1", "LittleVocal_VUD1.ZMiku_VUD1",
						"VOICEROID.Yukari0_Yukari", "VOICEROID.Yukari1_Yukari", "VOICEROID.YukariS0_Yukari", "VOICEROID.YukariS1_Yukari", "e11color_Elsa3"
				};
				Modchu_Config.writerModelList(s, textureListfile, textureList);
			} else {
				// textureListファイルがある
				Modchu_Config.loadList(textureListfile, textureList, "PlayerFormLittleMaidtextureList.cfg");
			}
		}
	}

	public static void writerOthersPlayerParamater() {
		//GUIOthersPlayer設定ファイル書き込み
		String s[] = {
				"othersTextureName="+othersTextureName, "othersTextureArmorName="+othersTextureArmorName, "othersMaidColor="+othersMaidColor,
				"othersModelScale="+othersModelScale, "showArmor="+PFLM_GuiOthersPlayer.showArmor
		};
		Modchu_Config.writerConfig(othersCfgfile, s);
	}

	public static void loadOthersPlayerParamater() {
		// GuiOthersPlayer設定項目読み込み
		Modchu_Debug.mDebug("loadOthersPlayerParamater");
		if (cfgdir.exists()) {
			if (!othersCfgfile.exists()) {
				// コンフィグファイルが無い = 新規作成
				writerOthersPlayerParamater();
			} else {
				// コンフィグファイルがある
				othersTextureName = (Modchu_Config.loadConfig(showModelList, othersCfgfile, "othersTextureName", othersTextureName)).toString();
				othersTextureArmorName = (Modchu_Config.loadConfig(showModelList, othersCfgfile, "othersTextureArmorName", othersTextureArmorName)).toString();
				othersMaidColor = Integer.valueOf((Modchu_Config.loadConfig(showModelList, othersCfgfile, "othersMaidColor", othersMaidColor)).toString());
				othersModelScale = Float.valueOf((Modchu_Config.loadConfig(showModelList, othersCfgfile, "othersModelScale", othersModelScale)).toString());
				PFLM_GuiOthersPlayer.showArmor = Boolean.valueOf((Modchu_Config.loadConfig(showModelList, othersCfgfile, "showArmor", PFLM_GuiOthersPlayer.showArmor)).toString());
				PFLM_GuiOthersPlayer.changeMode = Integer.valueOf((Modchu_Config.loadConfig(showModelList, othersCfgfile, "changeMode", PFLM_GuiOthersPlayer.changeMode)).toString());
				Modchu_Config.loadConfigPlayerLocalData(playerLocalData, othersCfgfile);
				if (PFLM_GuiOthersPlayer.changeMode > PFLM_GuiOthersPlayer.changeModeMax) PFLM_GuiOthersPlayer.changeMode = 0;
				if (PFLM_GuiOthersPlayer.changeMode < 0) PFLM_GuiOthersPlayer.changeMode = PFLM_GuiOthersPlayer.changeModeMax;
			}
		}
	}

	public static void saveOthersPlayerParamater(boolean flag) {
		// GuiOthersPlayer設定項目をcfgファイルに保存
		String k[] = {
				"othersTextureName", "othersTextureArmorName", "othersMaidColor", "othersModelScale", "showArmor", "changeMode"
		};
		String k1[] = {
				""+othersTextureName, ""+othersTextureArmorName, ""+othersMaidColor,
				""+othersModelScale,
				""+PFLM_GuiOthersPlayer.showArmor,
				""+PFLM_GuiOthersPlayer.changeMode
		};
		Modchu_Config.saveOthersPlayerParamater(PFLM_GuiOthersPlayerIndividualCustomize.playerName, playerLocalData, othersCfgfile, k, k1, flag);
	}

	public static void removeOthersPlayerParamater(String s) {
		// GuiOthersPlayer設定から指定内容削除
		Modchu_Config.removeOthersPlayerParamater(othersCfgfile, s);
	}

	public static void saveShortcutKeysPlayerParamater() {
		// GuiShortcutKeys設定項目をcfgファイルに保存
		if (!shortcutKeysCfgfile.exists()) {
			// コンフィグファイルが無い = 新規作成
			writerShortcutKeysParamater();
		}
		int m = 9;
		String k[] = new String[maxShortcutKeys * m];
		String k1[] = new String [maxShortcutKeys * m];
		int j;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < maxShortcutKeys;i++) {
			j = 0;
			if (shortcutKeysTextureName[i] != null) {} else shortcutKeysTextureName[i] = "default";
			if (shortcutKeysTextureArmorName[i] != null) {} else shortcutKeysTextureArmorName[i] = "default";
			if (shortcutKeysModelScale[i] != 0.0F) {} else shortcutKeysModelScale[i] = 0.9375F;
			k[m * i + j] = sb.append("shortcutKeysTextureName[").append(i).append("]").toString();
			k1[m * i + j] = shortcutKeysTextureName[i];
			j++;
			sb.delete(0, sb.length());
			k[m * i + j] = sb.append("shortcutKeysTextureArmorName[").append(i).append("]").toString();
			k1[m * i + j] = shortcutKeysTextureArmorName[i];
			j++;
			sb.delete(0, sb.length());
			k[m * i + j] = sb.append("shortcutKeysMaidColor[").append(i).append("]").toString();
			k1[m * i + j] = ""+shortcutKeysMaidColor[i];
			j++;
			sb.delete(0, sb.length());
			k[m * i + j] = sb.append("shortcutKeysChangeMode[").append(i).append("]").toString();
			k1[m * i + j] = ""+shortcutKeysChangeMode[i];
			j++;
			sb.delete(0, sb.length());
			k[m * i + j] = sb.append("shortcutKeysModelScale[").append(i).append("]").toString();
			k1[m * i + j] = ""+shortcutKeysModelScale[i];
			j++;
			sb.delete(0, sb.length());
			k[m * i + j] = sb.append("shortcutKeysUse[").append(i).append("]").toString();
			k1[m * i + j] = ""+shortcutKeysUse[i];
			j++;
			sb.delete(0, sb.length());
			k[m * i + j] = sb.append("shortcutKeysPFLMModelsUse[").append(i).append("]").toString();
			k1[m * i + j] = ""+shortcutKeysPFLMModelsUse[i];
			j++;
			sb.delete(0, sb.length());
			k[m * i + j] = sb.append("shortcutKeysCtrlUse[").append(i).append("]").toString();
			k1[m * i + j] = ""+shortcutKeysCtrlUse[i];
			j++;
			sb.delete(0, sb.length());
			k[m * i + j] = sb.append("shortcutKeysShiftUse[").append(i).append("]").toString();
			k1[m * i + j] = ""+shortcutKeysShiftUse[i];
			j++;
			sb.delete(0, sb.length());
		};
		Modchu_Config.saveParamater(shortcutKeysCfgfile, k, k1);
	}

	public static void writerShortcutKeysParamater() {
		//GUIShortcutKeys設定ファイル書き込み
		int m = 9;
		String s[] = new String [maxShortcutKeys * m];
		int j;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < maxShortcutKeys;i++) {
			j = 0;
			s[m * i + j] = sb.append("shortcutKeysTextureName[").append(i).append("]=default").toString();
			j++;
			sb.delete(0, sb.length());
			s[m * i + j] = sb.append("shortcutKeysTextureArmorName[").append(i).append("]=default").toString();
			j++;
			sb.delete(0, sb.length());
			s[m * i + j] = sb.append("shortcutKeysMaidColor[").append(i).append("]=0").toString();
			j++;
			sb.delete(0, sb.length());
			s[m * i + j] = sb.append("shortcutKeysChangeMode[").append(i).append("]=0").toString();
			j++;
			sb.delete(0, sb.length());
			s[m * i + j] = sb.append("shortcutKeysModelScale[").append(i).append("]=0.9375").toString();
			j++;
			sb.delete(0, sb.length());
			s[m * i + j] = sb.append("shortcutKeysUse[").append(i).append("]=false").toString();
			j++;
			sb.delete(0, sb.length());
			s[m * i + j] = sb.append("shortcutKeysPFLMModelsUse[").append(i).append("]=false").toString();
			j++;
			sb.delete(0, sb.length());
			s[m * i + j] = sb.append("shortcutKeysCtrlUse[").append(i).append("]=false").toString();
			j++;
			sb.delete(0, sb.length());
			s[m * i + j] = sb.append("shortcutKeysShiftUse[").append(i).append("]=false").toString();
			j++;
			sb.delete(0, sb.length());
		};
		Modchu_Config.writerConfig(shortcutKeysCfgfile, s);
	}

	public static void loadShortcutKeysParamater() {
		// GuiShortcutKeys設定項目読み込み
		Modchu_Debug.mDebug("loadShortcutKeysParamater");
		if (cfgdir.exists()) {
			if (!shortcutKeysCfgfile.exists()) {
				// コンフィグファイルが無い = 新規作成
				//writerShortcutKeysParamater();
			} else {
				// コンフィグファイルがある
				String s;
				String k;
				for(int i = 0; i < maxShortcutKeys;i++) {
					k = shortcutKeysTextureName[i] != null ? shortcutKeysTextureName[i] : "";
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysTextureName[").append(i).append("]").toString(), k).toString();
					shortcutKeysTextureName[i] = shortcutKeysTextureName[i] != null ? s: "default";
					k = shortcutKeysTextureArmorName[i] != null ? shortcutKeysTextureArmorName[i] : "";
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysTextureArmorName[").append(i).append("]").toString(), k).toString();
					shortcutKeysTextureArmorName[i] = shortcutKeysTextureArmorName[i] != null ? s: "default";
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysMaidColor[").append(i).append("]").toString(), shortcutKeysMaidColor[i]).toString();
					shortcutKeysMaidColor[i] = s != null ? Integer.valueOf(s) : 0;
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysChangeMode[").append(i).append("]").toString(), shortcutKeysChangeMode[i]).toString();
					shortcutKeysChangeMode[i] = s != null ? Integer.valueOf(s) : 0;
					if (shortcutKeysChangeMode[i] > PFLM_GuiKeyControls.changeModeMax) shortcutKeysChangeMode[i] = 0;
					if (shortcutKeysChangeMode[i] < 0) shortcutKeysChangeMode[i] = PFLM_GuiKeyControls.changeModeMax;
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysModelScale[").append(i).append("]").toString(), shortcutKeysModelScale[i]).toString();
					shortcutKeysModelScale[i] = s != null ? Float.valueOf(s) : 0.9375F;
					if (shortcutKeysModelScale[i] > 10.0F) shortcutKeysModelScale[i] = 10.0F;
					if (shortcutKeysModelScale[i] < 0.0F) shortcutKeysModelScale[i] = 0.0F;
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysUse[").append(i).append("]").toString(), shortcutKeysUse[i]).toString();
					shortcutKeysUse[i] = s != null ? Boolean.valueOf(s) : false;
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysPFLMModelsUse[").append(i).append("]").toString(), shortcutKeysPFLMModelsUse[i]).toString();
					shortcutKeysPFLMModelsUse[i] = s != null ? Boolean.valueOf(s) : false;
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysCtrlUse[").append(i).append("]").toString(), shortcutKeysCtrlUse[i]).toString();
					shortcutKeysCtrlUse[i] = s != null ? Boolean.valueOf(s) : false;
					s = Modchu_Config.loadConfig(showModelList, shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysShiftUse[").append(i).append("]").toString(), shortcutKeysShiftUse[i]).toString();
					shortcutKeysShiftUse[i] = s != null ? Boolean.valueOf(s) : false;
				}
			}
		}
	}

	public void modsLoaded()
    {
		// マージclass判定
		List list = ModLoader.getLoadedMods();
		int size = list.size();
		for (int i = 0; i < size; i++)
		{
			BaseMod mod = (BaseMod)list.get(i);
			String name = mod.getClass().getSimpleName();
			if (name.equals("mod_ThirdPersonCamera")) {
				isThirdPersonCamera = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_ThirdPersonCamera Check ok.");
				Modchu_Debug.Debug("mod_ThirdPersonCamera Check ok.");
			}
			else if (name.equals("mod_noBiomesX")) {
				isnoBiomesX = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_noBiomesX Check ok.");
				Modchu_Debug.Debug("mod_noBiomesX Check ok.");
			}
/*//125delete
			else if (name.equals("mod_MinecraftForge")) {
				erpflmCheck = 1;
				isForge = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_MinecraftForge Check ok.");
				Modchu_Debug.Debug("mod_MinecraftForge Check ok.");
			}
*///125delete
			else if (name.equals("mod_SmartMoving")) {
				isSmartMoving = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_SmartMoving Check ok.");
				Modchu_Debug.Debug("mod_SmartMoving Check ok.");
			}
			else if (name.equals("mod_Aether")) {
				isAether = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_Aether Check ok.");
				Modchu_Debug.Debug("mod_Aether Check ok.");
			}
			else if (name.equals("mod_2DCraft")) {
				is2D = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_2DCraft Check ok.");
				Modchu_Debug.Debug("mod_2DCraft Check ok.");
			}
			else if (name.equals("mod_CCTV")) {
				isCCTV = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_CCTV Check ok.");
				Modchu_Debug.Debug("mod_CCTV Check ok.");
			}
			else if (name.equals("mod_DecoBlock")) {
				isDecoBlock = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_DecoBlock Check ok.");
				Modchu_Debug.Debug("mod_DecoBlock Check ok.");
			}
			else if (name.equals("mod_FavBlock")) {
				isFavBlock = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_FavBlock Check ok.");
				Modchu_Debug.Debug("mod_FavBlock Check ok.");
			}
			else if (name.equals("mod_LMM_littleMaidMob")) {
				isLMM = true;
				ModLoader.getLogger().fine("playerFormLittleMaid-mod_LMM_littleMaidMob Check ok.");
				Modchu_Debug.Debug("mod_LMM_littleMaidMob Check ok.");
			}
		}
		//mod_SmartMovingMp Shaders MinecraftForgeなど対応クラス存在チェック
		String className1[] = {
				"mod_SmartMovingMp", "Shaders", "DynamicLights", "Shader", "FMLRenderAccessLibrary",
				"EntityPlayerSP2", "RenderPlayer2", "net.minecraft.decoblock.DecoBlock", "net.minecraft.favstar.BlockFav", "ItemRendererHD"
		};
		String test2 = null;
		for(int n = 0 ; n < className1.length ; n++){
			try {
				test2 = getClassName(className1[n]);
				//Modchu_Debug.mDebug("test2 = "+test2);
				test2 = ""+Class.forName(test2);
				ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-"+ test2 + " Check ok.")).toString());
				Modchu_Debug.Debug(test2 + " Check ok.");
				if(n == 0) isSmartMoving = true;
				if(n == 1) isShaders = true;
				if(n == 2) isDynamicLights = true;
				if(n == 3) {
					isShader = true;
					erpflmCheck = 7;
				}
				if(n == 4) {
					isForge = true;
					erpflmCheck = 1;
				}
				if(n == 5) isSSP = true;
				if(n == 6) isOlddays = true;
				if(n == 7) isDecoBlock = true;
				if(n == 8) isFavBlock = true;
				if(n == 9) isItemRendererHD = true;
			} catch (ClassNotFoundException e) {
			}
		}
    	if (!isThirdPersonCamera) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isThirdPersonCamera false.");
			Modchu_Debug.Debug("isThirdPersonCamera false.");
		}
		if (!isnoBiomesX) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isnoBiomesX false.");
			Modchu_Debug.Debug("isnoBiomesX false.");
		}
		if (!isSmartMoving) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isSmartMoving false.");
			Modchu_Debug.Debug("isSmartMoving false.");
		}
		if (!isShaders) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isShaders false.");
			Modchu_Debug.Debug("isShaders false.");
		}
		if (!isDynamicLights) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isDynamicLights false.");
			Modchu_Debug.Debug("isDynamicLights false.");
		}
		if (!isShader) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isShader false.");
			Modchu_Debug.Debug("isShader false.");
		}
		if (!isAether) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isAether false.");
			Modchu_Debug.Debug("isAether false.");
		}
		if (!is2D) {
			ModLoader.getLogger().fine("playerFormLittleMaid-is2D false.");
			Modchu_Debug.Debug("is2D false.");
		}
		if (!isCCTV) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isCCTV false.");
			Modchu_Debug.Debug("isCCTV false.");
		}
		if (!isDecoBlock) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isDecoBlock false.");
			Modchu_Debug.Debug("isDecoBlock false.");
		}
		if (!isFavBlock) {
			ModLoader.getLogger().fine("playerFormLittleMaid-isFavBlock false.");
			Modchu_Debug.Debug("isFavBlock false.");
		}
		if (!isForge) {
			ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No Forge.")).toString());
			Modchu_Debug.Debug("No Forge.");
		}
		if (!isOlddays) {
			ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No Olddays.")).toString());
			Modchu_Debug.Debug("No Olddays.");
		}
		if (!isLMM) {
			ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No littleMaidMob.")).toString());
			Modchu_Debug.Debug("No littleMaidMob.");
		}
		if (!isItemRendererHD) {
			ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No ItemRendererHD.")).toString());
			Modchu_Debug.Debug("No ItemRendererHD.");
		}
//-@-125
		else {
			ModLoader.setInGUIHook(this, true, true);
		}
//@-@125
		if (isSSP) {
			try {
				pflm_playerControllerCreative2 = Class.forName(getClassName("PFLM_PlayerControllerCreative2"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				pflm_playerController2 = Class.forName(getClassName("PFLM_PlayerController2"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				pflm_entityPlayerSP2 = Class.forName(getClassName("PFLM_EntityPlayerSP2"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (isOlddays) {
			try {
				pflm_renderPlayer2 = Class.forName(getClassName("PFLM_RenderPlayer2"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (isLMM) {
			try {
				LMM_EntityLittleMaid = Class.forName(getClassName("LMM_EntityLittleMaid"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				LMM_InventoryLittleMaid = Class.forName(getClassName("LMM_InventoryLittleMaid"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				LMM_SwingStatus = Class.forName(getClassName("LMM_SwingStatus"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				LMM_EntityLittleMaidAvatar = Class.forName(getClassName("LMM_EntityLittleMaidAvatar"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		boolean keyFlag = playerFormLittleMaidVersion > 129 ? isForge : false;
		// GUI を開くキーの登録と名称変換テーブルの登録
		if (isPlayerForm) {
			String s = "key.PlayerFormLittleMaid";
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
			if(waitTime == 0) {
				s = "key.PFLM wait";
				ModLoader.registerKey(this, new KeyBinding(s, 26), keyFlag);
				ModLoader.addLocalization(
						(new StringBuilder()).append(s).toString(),
						(new StringBuilder()).append("PFLMWait").toString()
						);
			}
			if (guiMultiPngSaveButton) ModLoader.registerEntityID(PFLM_EntityPlayerDummy.class, "PFLM_EntityPlayerDummy", ModLoader.getUniqueEntityId());
		}
		String s = "key.Sit";
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
		if (isModelSize) {
/*//110delete
//-@-100
				// OptiFine判定L
				try {
					s = (String) getPrivateValue(net.minecraft.src.RenderGlobal.class, this, "version");
					String s1 = "OptiFine";
					if (s.indexOf(s1) != -1) {
						Modchu_Debug.Debug(s + " Check ok.");
						erpflmCheck = 2;
					}
				} catch (Exception exception) {
					//Modchu_Debug.Debug("No OptiFineL.");
				}
//@-@100
*///110delete

			// OptiFine判定
			boolean t = false;
			String className2 = getClassName("Config");
			test2 = null;
			int n1 = 0;
			try {
				test2 = ""+Class.forName(className2);
				ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-"+ test2 + " Check ok.")).toString());
				Modchu_Debug.Debug(test2 + " Check ok.");
				t = true;
				// b166deleteerpflmCheck = 4;
			} catch (ClassNotFoundException e) {
			}
			if (t) {
//-@-b166
				s = null;
				Field f11;
/*//110delete
				try {
					f11 = Class.forName(className2).getDeclaredField("version");
					f11.setAccessible(true);
					String s1 = (String) f11.get(null);
					erpflmCheck = optiNameCheck(s1, erpflmCheck);
				} catch (Exception e1) {
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No OptiFine.")).toString());
					Modchu_Debug.Debug("No OptiFine.");
				}
//@-@b166
*///110delete
//-@-110
				try {
					f11 = Class.forName(className2).getField("OF_NAME");
					try {
						s = (String) f11.get(null);
					} catch (Exception e) {
						e.printStackTrace();
						ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No OptiFine.")).toString());
						Modchu_Debug.Debug("No OptiFine.");
					}
				} catch (Exception e1) {
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No OptiFine.")).toString());
					Modchu_Debug.Debug("No OptiFine.");
				}
				erpflmCheck = optiNameCheck(s, erpflmCheck);
				if (erpflmCheck <= 1) {
					boolean err = false;
					String s2 = "";
					try {
						f11 = Class.forName(className2).getField("OF_EDITION");
						try {
							s = (String) f11.get(null);
							erpflmCheck = optiNameCheck(s, erpflmCheck);
							try {
								f11 = Class.forName(className2).getField("VERSION");
								try {
									s2 = (String) f11.get(null);
								} catch (Exception exception) {
									err = true;
								}
							} catch (Exception exception) {
								err = true;
							}
//@-@110
//-@-123
							String s1 = "B2";
							if (s2.indexOf(s1) != -1) {
								ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-OptiFine "+ s2 + " Check ok.")).toString());
								Modchu_Debug.Debug("OptiFine "+ s2 + " Check ok.");
								// 125deleteerpflmCheck = erpflmCheck == 3 ? 6 : 5 ;
								/*125//*/erpflmCheck = 6;
							}
//@-@123
//-@-110
//-@-125
							s1 = "B3";
							if (s2.indexOf(s1) != -1) {
								ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-OptiFine "+ s2 + " Check ok.")).toString());
								Modchu_Debug.Debug("OptiFine "+ s2 + " Check ok.");
								erpflmCheck = 5;
							}
//@-@125
						} catch (Exception e) {
							err = true;
						}
					} catch (Exception exception) {
						err = true;
					}
					if(err) {
						ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No OptiFine.")).toString());
						Modchu_Debug.Debug("No OptiFine.");
					}
				}
//@-@110
			} else {
/*//b166delete
				try {
					s = "FontRenderer";
					Package pac = getClass().getPackage();
					if (pac != null) s = pac.getName().concat(".").concat(s);
					Method method = Class.forName(s).getDeclaredMethod("lineIsCommand", new Class[] {int.class});
					erpflmCheck = 2;
				} catch (Exception e1) {
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No Optimine.")).toString());
					Modchu_Debug.Debug("No Optimine.");
*///b166delete
//-@-b166
				ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No OptiFine Config.")).toString());
				Modchu_Debug.Debug("No OptiFine Config.");
//-@-125
				try {
					s = "VersionThread";
					s = ""+Class.forName(s);
					erpflmCheck = 2;
				} catch (Exception e1) {
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No OptiFineL.")).toString());
					Modchu_Debug.Debug("No OptiFineL.");
				}
//@-@125
//@-@b166
				// b166delete}
			}
		}

		if (isModelSize) {
			try {
				Minecraft instance = Minecraft.getMinecraft();
				if (isCCTV) {
					String s3;
					instance.entityRenderer = new PFLM_EntityRendererCCTV(instance);
					s3 = "PFLM_EntityRendererCCTV";
					ModLoader.getLogger().fine((new StringBuilder(s3 + " to set.")).toString());
					Modchu_Debug.Debug(s3 + " to set.");
					try {
						s = "mod_CCTV";
						Package pac = getClass().getPackage();
						if (pac != null) s = pac.getName().concat(".").concat(s);
						Class CCTV = Class.forName(s);
						Field f11;
						try {
							f11 = CCTV.getDeclaredField("rendererReplaced");
							f11.setAccessible(true);
							f11.set(null, true);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else
				if (is2D) {
					String s3;
					instance.entityRenderer = new PFLM_EntityRenderer2D(instance);
					s3 = "PFLM_EntityRenderer2D";
					ModLoader.getLogger().fine((new StringBuilder(s3 + " to set.")).toString());
					Modchu_Debug.Debug(s3 + " to set.");
				} else
				if (erpflmCheck == 0) {
					instance.entityRenderer = new PFLM_EntityRenderer(instance);
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-PFLM_EntityRenderer to set.")).toString());
					Modchu_Debug.Debug("PFLM_EntityRenderer to set.");
				} else
				if (erpflmCheck == 1) {
					instance.entityRenderer = new PFLM_EntityRendererForge(instance);
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-PFLM_EntityRendererForge to set.")).toString());
					Modchu_Debug.Debug("PFLM_EntityRendererForge to set.");
				} else
				if (erpflmCheck == 2) {
					instance.entityRenderer = new PFLM_EntityRendererOptiL(instance);
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-PFLM_EntityRendererOptiL to set.")).toString());
					Modchu_Debug.Debug("PFLM_EntityRendererOptiL to set.");
				} else
				if (erpflmCheck == 3) {
					String s3;
					if(isShaders) {
//-@-125
						instance.entityRenderer = new PFLM_EntityRendererShaders(instance);
						s3 = "PFLM_EntityRendererShaders";
//@-@125
/*//125delete
						instance.entityRenderer = new PFLM_EntityRendererShadersHDM(instance);
						s3 = "PFLM_EntityRendererShadersHDM";
*///125delete
					} else {
//-@-125
						instance.entityRenderer = new PFLM_EntityRendererOptiHD(instance);
						s3 = "PFLM_EntityRendererOptiHD";
//@-@125
/*//125delete
						instance.entityRenderer = new PFLM_EntityRendererOptiHDM(instance);
						s3 = "PFLM_EntityRendererOptiHDM";
*///125delete
					}
					ModLoader.getLogger().fine((new StringBuilder(s3 + " to set.")).toString());
					Modchu_Debug.Debug(s3 + " to set.");
				} else
				if (erpflmCheck == 4) {
					String s3;
					if(isShaders) {
						//-@-125
						instance.entityRenderer = new PFLM_EntityRendererShadersHDU(instance);
						s3 = "PFLM_EntityRendererShadersHDU";
//@-@125
/*//125delete
						instance.entityRenderer = new PFLM_EntityRendererShaders(instance);
						s3 = "PFLM_EntityRendererShaders";
*///125delete
					} else {
//-@-125
						instance.entityRenderer = new PFLM_EntityRendererOptiHDU(instance);
						s3 = "PFLM_EntityRendererOptiHDU";
//@-@125
/*//125delete
						instance.entityRenderer = new PFLM_EntityRendererOptiHD(instance);
						s3 = "PFLM_EntityRendererOptiHD";
*///125delete
					}
					ModLoader.getLogger().fine((new StringBuilder(s3 + " to set.")).toString());
					Modchu_Debug.Debug(s3 + " to set.");
				} else
					if (erpflmCheck == 5) {
						String s3;
						if(isShaders) {
//-@-125
							instance.entityRenderer = new PFLM_EntityRendererShadersHDU3(instance);
							s3 = "PFLM_EntityRendererShadersHDU3";
//@-@125
/*//125delete
							instance.entityRenderer = new PFLM_EntityRendererShadersC(instance);
							s3 = "PFLM_EntityRendererShadersC";
*///125delete
						} else {
//-@-125
							instance.entityRenderer = new PFLM_EntityRendererOptiHDU3(instance);
							s3 = "PFLM_EntityRendererOptiHDU3";
//@-@125
/*//125delete
							instance.entityRenderer = new PFLM_EntityRendererOptiHDC(instance);
							s3 = "PFLM_EntityRendererOptiHDC";
*///125delete
						}
						ModLoader.getLogger().fine((new StringBuilder(s3 + " to set.")).toString());
						Modchu_Debug.Debug(s3 + " to set.");
					} else
						if (erpflmCheck == 6) {
						String s3;
						if(isShaders) {
//-@-125
							instance.entityRenderer = new PFLM_EntityRendererShadersHDU3(instance);
							s3 = "PFLM_EntityRendererShadersHDU3";
//@-@125
/*//125delete
							instance.entityRenderer = new PFLM_EntityRendererShadersHDMTC(instance);
							s3 = "PFLM_EntityRendererShadersHDMTC";
*///125delete
						} else {
//-@-125
							instance.entityRenderer = new PFLM_EntityRendererOptiHDU2(instance);
							s3 = "PFLM_EntityRendererOptiHDU2";
//@-@125
/*//125delete
							instance.entityRenderer = new PFLM_EntityRendererOptiHDMTC(instance);
							s3 = "PFLM_EntityRendererOptiHDMTC";
*///125delete
						}
						ModLoader.getLogger().fine((new StringBuilder(s3 + " to set.")).toString());
						Modchu_Debug.Debug(s3 + " to set.");
					} else
					if (erpflmCheck == 7) {
					String s3;
					if(isShader) {
//-@-110
						instance.entityRenderer = new PFLM_EntityRendererShader(instance);
						s3 = "PFLM_EntityRendererShader";
//@-@110
/*//110delete
						instance.entityRenderer = new PFLM_EntityRenderer(instance);
						s3 = "PFLM_EntityRenderer";
*///110delete
						ModLoader.getLogger().fine((new StringBuilder(s3 + " to set.")).toString());
						Modchu_Debug.Debug(s3 + " to set.");
					}
				}
			} catch(Exception exception) {
				ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-PFLM_EntityRenderer Fail to set.")).toString());
				Modchu_Debug.Debug("PFLM_EntityRenderer Fail to set!");
			}
		}
//-@-125
		if(isSmartMoving) {
			isModelSize = false;
			try {
				s = getClassName("PlayerAPI");
				Method mes = null;
				mes = Class.forName(s).getMethod("unregister", new Class[] {String.class});
				if(isSmartMoving) mes.invoke(null, "Smart Moving");
				//PlayerAPI.unregister("Smart Moving");
			} catch (Exception e) {
			}
			PFLM_PlayerBaseSmartServer.registerPlayerBase();
			PFLM_SmartOtherPlayerData.initialize(mc.gameSettings, false, ModLoader.getLogger());
		}
//@-@125
		if (playerFormLittleMaidVersion < 130
				| isSmartMoving) ModLoader.setInGUIHook(this, true, true);

		// PlayerAPI判定
		isPlayerAPI = false;
		/*b166//*/if (!isPlayerAPIDebug) {
/*
	        	PlayerAPI.getRegisteredIds();
            	isPlayerAPI = true;
        		PlayerAPI.register("PlayerFormLittleMaid", PFLM_PlayerBase.class);
*/
//-@-b166
        		try {
					s = getClassName("PlayerAPI");
					Method mes = null;
					mes = Class.forName(s).getMethod("register", new Class[] {String.class, Class.class});
//@-@b166
//-@-125
					//@/if(isSmartMoving) mes.invoke(null, "PlayerFormLittleMaid", PFLM_PlayerBaseSmart.class);
					//@/else {
//@-@125
//-@-b166
						mes.invoke(null, "PlayerFormLittleMaid", PFLM_PlayerBase.class);
					/*125//*///@/}
//	        		PlayerAPI.register("PlayerFormLittleMaid", PFLM_PlayerBase.class);
					isPlayerAPI = true;
					Modchu_Debug.Debug("PlayerAPI register.");
				} catch (Exception e) {
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-PlayerAPI not found! ")).toString());
				}
        	}
		if (!isPlayerAPI) {
			ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-PlayerAPI false.")).toString());
			Modchu_Debug.Debug("PlayerAPI false.");
		}
//@-@b166
		if (!isClearWater) {
			setWatherFog = watherFog;
			setWatherFog2 = watherFog2;
			setwaterStillLightOpacity = waterStillLightOpacity;
			setwaterMovingLightOpacity = waterMovingLightOpacity;
		}
		Block.waterStill.setLightOpacity(setwaterStillLightOpacity);
		Block.waterMoving.setLightOpacity(setwaterMovingLightOpacity);
		PFLM_Texture.getArmorPrefix();
		PFLM_Texture.getModFile();
		PFLM_Texture.getTextures();
		texturesNamber = new int[16][PFLM_Texture.textures.size()];
		for (int color = 0 ; color < 16 ; color++) {
			for (int i2 = 0 ; i2 < PFLM_Texture.textures.size() ; ++i2) {
				texturesNamber[color][i2] = -1;
			}
		}
		String t;
		String t1;
		int i1;
		for (int color = 0 ; color < 16 ; color++) {
			t = "";
			i1 = 0;
			for (int i2 = 0 ; i2 < PFLM_Texture.textures.size() ; ++i2) {
				t1 = PFLM_Texture.getPackege(color, i2);
				if (t1 != t) {
					//Modchu_Debug.mDebug("t1="+t1+" t="+t);
					texturesNamber[color][i1] = i2;
					++i1;
					t = t1;
				}
			}
			maxTexturesNamber[color] = i1;
		}

		if (isDecoBlock) {
			try {
				s = "net.minecraft.decoblock.DecoBlock";
				decoBlock = Class.forName(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				s = "net.minecraft.decoblock.base.BaseBlock";
				decoBlockBase = Class.forName(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (isFavBlock) {
			try {
				s = "net.minecraft.favstar.BlockFav";
				favBlock = Class.forName(s);
			} catch (Exception e) {
				try {
					s = "net.minecraft.favblock.FavBlock";
					favBlock = Class.forName(s);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		newModel();
    }

	public static void shortcutKeysinit() {
		boolean keyFlag = mod_pflm_playerformlittlemaid.playerFormLittleMaidVersion > 129 ? isForge : false;
		String s;
		for(int i = 0; i < maxShortcutKeys;i++) {
			if (shortcutKeysUse[i]) {
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
				PFLMModelsKeyCode = keyBinding.keyCode;
				break;
			}
		}
	}

	public static int optiNameCheck(String s, int j) {
		int i = j;
		String s1 = "HD_MT_AA";
		if (s.indexOf(s1) != -1) {
			ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-"+ s + " Check ok.")).toString());
			Modchu_Debug.Debug(s + " Check ok.");
			i = 3;
			getIconWidthTerrain = 0;
		} else {
			s1 = "HD_U";
			if (s.indexOf(s1) != -1) {
				/*125//*/optiVersionName = "HD_U ";
				ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-"+ s + " Check ok.")).toString());
				Modchu_Debug.Debug(s + " Check ok.");
				i = 3;
			} else {
				s1 = "HD";
				/*125//*/optiVersionName = "HD ";
				if (s.indexOf(s1) != -1) {
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-"+ s + " Check ok.")).toString());
					Modchu_Debug.Debug(s + " Check ok.");
					i = 4;
				} else {
					ModLoader.getLogger().fine((new StringBuilder("playerFormLittleMaid-No OptiFine.Name Chenk error.")).toString());
					Modchu_Debug.Debug("No OptiFine.Name Chenk error.Name="+s);
				}
			}
		}
		return i;
	}

	public String getClassName(String s) {
		if (s == null) return null;
		if (s.indexOf(".") > -1) return s;
		Package pac = getClass().getPackage();
		if (pac != null) s = pac.getName().concat(".").concat(s);
		return s;
	}

	public static Object getPrivateValue(Class var0, Object var1, int var2)
	{
		try
		{
			Field var3 = var0.getDeclaredFields()[var2];
			var3.setAccessible(true);
			return var3.get(var1);
		}
		catch (Exception var4)
		{
			return null;
		}
	}

	public static Object getPrivateValue(Class var0, Object var1, String var2) throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field var3 = var0.getDeclaredField(var2);
			var3.setAccessible(true);
			return var3.get(var1);
		}
		catch (Exception var4)
		{
			return null;
		}
	}

    public static void setPrivateValue(Class var0, Object var1, int var2, Object var3)
    {
        try
        {
            Field var4 = var0.getDeclaredFields()[var2];
            var4.setAccessible(true);
            var4.set(var1, var3);
        }
        catch (Exception var6)
        {
        }
    }

    public static void setPrivateValue(Class var0, Object var1, String var2, Object var3)
    {
        try
        {
            Field var4 = var0.getDeclaredField(var2);
            var4.setAccessible(true);
            var4.set(var1, var3);
        }
        catch (Exception var6)
        {
        }
    }

    private static void startVersionCheckThread()
    {
    	PFLM_ThreadVersionCheck var0 = new PFLM_ThreadVersionCheck();
        var0.start();
    }

	public static boolean checkRelease(String s) {
		if (s != null) {
			if (s.length() > 1) {
				String ck = s.substring(s.length() - 1, s.length());
				String mck = mod_pflm_playerformlittlemaid.getVersion();
				String k = mck;
				while (k.indexOf("-") != -1)
				{
					k = k.substring(k.indexOf("-") + 1);
				}
				k = k.substring(k.indexOf("-") + 1);
				int length = k.length() > 2 ? k.length() - 1 : k.length();
				int m = Integer.valueOf(k.substring(0, length));
				int i;
				if (s.length() > 2) {
					i = Integer.valueOf(s.substring(0, s.length() - 1));
				} else {
					i = Integer.valueOf(s);
					ck = "";
				}
				mck = k.length() > 2 ? k.substring(2, k.length()) : "";
				Modchu_Debug.mDebug("m="+m+" mck="+mck+" i="+i+" ck="+ck);
				if (i > m) {
					return true;
				}
				if (i == m
						&& ck.compareTo(mck) > 0) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public static void setNewRelease(String s) {
		newRelease = true;
		newVersion = s;
	}
}