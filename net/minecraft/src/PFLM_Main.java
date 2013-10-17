package net.minecraft.src;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class PFLM_Main
{
	public static final String version = "25";
	public static final String modName = "PlayerFormLittleMaid";
	public static final String versionString = ""+ Modchu_Version.version + "-" + version;

	//cfg書き込み項目
	public static boolean AlphaBlend = true;
	public static boolean Physical_Undead = false;
	public static boolean isPlayerForm = true;
	public static boolean isPlayerAPIDebug = false;
	public static boolean isModelSize = false;
	public static boolean isClearWater = false;
	public static boolean isVoidFog = true;
	public static boolean isFog = true;
	public static boolean isDimming = true;
	public static boolean isSwapGuiCreate = true;
	public static boolean guiMultiPngSaveButton = true;
	public static boolean changeModeButton = true;
	public static boolean isRenderName = true;
	public static boolean isMouseOverMinecraftMenu = true;
	public static boolean multiAutochangeMode = true;
	public static boolean versionCheck = true;
	public static boolean useScaleChange = true;
	public static boolean mushroomConfusion = true;
	public static boolean entityReplace = false;
	public static boolean useAddChatMessage = true;
	public static int Physical_BurningPlayer = 0;
	public static int Physical_MeltingPlayer = 0;
	public static int waterStillLightOpacity = 3;
	public static int waterMovingLightOpacity = 3;
	public static int waitTime = 600;
	public static int othersPlayerWaitTime = 600;
	public static int handednessMode = 0;
	public static float Physical_Hammer = 1.0F;
	public static float watherFog = 0.1F;
	public static float watherFog2 = 0.05F;
	public static float lavaFog = 2.0F;
	public static float transparency = 1.0F;
	//public static double locationPositionCorrectionY = 0.5D;
	public static String Physical_HurtSound = "damage.hit";
	public static String textureSavedir = "/output/";

	public static boolean isThirdPersonCamera = false;
	public static boolean isnoBiomesX = false;
	public static boolean isSmartMoving = false;
	public static boolean isShaders = false;
	public static boolean isShader = false;
	public static boolean isDynamicLights = false;
	public static boolean isAether = false;
	public static boolean is2D = false;
	public static boolean isCCTV = false;
	public static boolean isWait = false;
	public static boolean isMulti = false;
	public static boolean isOlddays = false;
	public static boolean isSSP = false;
	public static boolean isLMM = false;
	public static boolean isHD = false;
	public static boolean isItemRendererHD = false;
	public static boolean isPlayerAPI = false;
	public static boolean guiEnable = true;
	public static boolean entityReplaceFlag = false;
	public static boolean initItemRenderer = false;
	public static boolean initItemRendererHD = false;
	private static boolean isItemRendererDebug = false;
	private static boolean setMultiAutochangeMode = true;
	private static boolean loadInitFlag = false;
	private static boolean isRelease = true;
	public static int changeMode = 0;
	public static int maidColor = 0;
	public static int setwaterStillLightOpacity = 0;
	public static int setwaterMovingLightOpacity = 0;
	public static int guiSelectWorldSwapCount = 0;
	public static int guiMultiplayerSwapCount = 0;
	private static int erpflmCheck = 0;
	public static float setWatherFog = 0F;
	public static float setWatherFog2 = 0F;
	public static String textureArmor0[] = new String[4];
	public static String textureArmor1[] = new String[4];
	public static Object texture = null;
	public static String textureName = "default";
	public static String textureArmorName = "default";
	public static String othersTextureName = "default";
	public static String othersTextureArmorName = "default";
	public static int othersMaidColor = 0;
	public static float othersModelScale = 0.0F;
	public static int othersHandednessMode = 0;
	public static final int maxShortcutKeys = 100;
	public static float shortcutKeysModelScale[] = new float[maxShortcutKeys];
	public static String shortcutKeysTextureName[] = new String[maxShortcutKeys];
	public static String shortcutKeysTextureArmorName[] = new String[maxShortcutKeys];
	public static int shortcutKeysMaidColor[] = new int[maxShortcutKeys];
	public static int shortcutKeysChangeMode[] = new int[maxShortcutKeys];
	public static boolean shortcutKeysUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysPFLMModelsUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysCtrlUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysShiftUse[] = new boolean[maxShortcutKeys];
	public static int shortcutKeysNumber = 0;
	private static File cfgdir;
	private static File mainCfgfile;
	public static File cfgfile;
	public static File othersCfgfile;
	private static File modelListfile;
	private static File textureListfile;
	private static File shortcutKeysCfgfile;
	public static List<String> showModelList = new ArrayList<String>();
	public static List<String> textureList = new ArrayList<String>();
	public static ConcurrentHashMap playerLocalData = new ConcurrentHashMap();
	public static Object mc;
	public static boolean newRelease = false;
	public static String newVersion = "";
	public static int[][] texturesNamber;
	public static int[] texturesArmorNamber;
	public static int[] maxTexturesNamber = new int [16];
	public static int maxTexturesArmorNamber;
	public static Class itemRendererClass;
	public static int PFLMModelsKeyCode;
	private static Random rnd = new Random();
	public static PFLM_EntityPlayerMaster entityPlayerMaster;
	public static String packageName = null;
	private static String runtimeExceptionString = null;
	private static boolean isReleasekey = false;
	private static boolean keySitLock = false;
	public static boolean oldRender = false;
	private static boolean itemRendererReplaceFlag = false;
	private static boolean runtimeExceptionFlag = false;
	private static boolean texturesNamberInitFlag = false;

	//不具合有り機能封印
	public static boolean guiShowArmorSupport = false;

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
	public static Class BipedClass;

	public PFLM_Main()
	{
	}

	public boolean isRelease() {
		return isRelease;
	}

	public String getName() {
		return modName;
	}

	public String getVersion()
	{
		return versionString;
	}

	static{
		String s = System.getenv("isRelease");
		if ((s != null
				&& s.equals("on"))) {
			isRelease = false;
		}
		s = System.getenv("debugPlayerName");
		if (s != null) {
			Modchu_Debug.debugPlayerName = s;
		}
	}

	public void load()
	{
		//MinecraftForge判定
		boolean isForge = false;
		try {
			String s = ""+Class.forName("net.minecraft.src.FMLRenderAccessLibrary");
			if (s != null) isForge = true;
		} catch (Exception e) {
		}
		if (!isForge) {
			Package pack = getClass().getPackage();
			if (pack != null) packageName = pack.getName();
			else packageName = null;
		} else {
			packageName = !isRelease ? "net.minecraft.src" : null;
		}
		if (mc != null) ;else mc = mod_Modchu_ModchuLib.modchu_Main.getMinecraft();
		//System.out.println("mod_PFLM_PlayerFormLittleMaid-load() packageName="+packageName);
		cfgdir = new File(mod_Modchu_ModchuLib.modchu_Main.getMinecraftDir(), "/config/");
		mainCfgfile = new File(cfgdir, ("PlayerFormLittleMaid.cfg"));
		cfgfile = new File(cfgdir, ("PlayerFormLittleMaidGuiSave.cfg"));
		othersCfgfile = new File(cfgdir, ("PlayerFormLittleMaidGuiOthersPlayer.cfg"));
		modelListfile = new File(cfgdir, ("PlayerFormLittleMaidModelList.cfg"));
		textureListfile = new File(cfgdir, ("PlayerFormLittleMaidtextureList.cfg"));
		shortcutKeysCfgfile = new File(cfgdir, ("PlayerFormLittleMaidShortcutKeys.cfg"));
		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftDir().getAbsolutePath().indexOf("jars") != -1) {
			isRelease = false;
		}
		loadcfg();
		Modchu_Debug.init(packageName);
		Class MMM_TextureManager = Modchu_Reflect.loadClass(getClassName("MMM_TextureManager"), -1);
		Class MMM_FileManager = Modchu_Reflect.loadClass(getClassName("MMM_FileManager"), -1);
		Class MMM_TextureBox = Modchu_Reflect.loadClass(getClassName("MMM_TextureBox"), -1);
		if (MMM_TextureManager != null) ;else {
			MMM_TextureManager = Modchu_Reflect.loadClass(getClassName("Modchu_TextureManager"));
			MMM_FileManager = Modchu_Reflect.loadClass(getClassName("Modchu_FileManager"));
			MMM_TextureBox = Modchu_Reflect.loadClass(getClassName("Modchu_TextureBox"));
		}
		Modchu_Reflect.invokeMethod(MMM_FileManager, "getModFile", new Class[]{String.class, String.class}, null, new Object[]{"playerformlittlemaid", "MultiModel"});
		Modchu_Reflect.invokeMethod(MMM_FileManager, "getModFile", new Class[]{String.class, String.class}, null, new Object[]{"playerformlittlemaid", "playerformlittlemaid"});
		Object o = Modchu_Reflect.getFieldObject(MMM_TextureManager, "instance");
		if (o != null) {
			Modchu_Reflect.invokeMethod(MMM_TextureManager, "addSearch", new Class[]{String.class, String.class, String.class}, o, new Object[]{"playerformlittlemaid", "/assets/minecraft/textures/entity/littleMaid/", mod_Modchu_ModchuLib.modchu_Main.modelClassName+"_"});
		} else {
			ModLoader.getLogger().warning("mod_PFLM_PlayerFormLittleMaid-you must check MMMLib revision.");
			throw new RuntimeException("mod_PFLM_PlayerFormLittleMaid-The revision of MMMLib is old.");
		}
		if (mod_Modchu_ModchuLib.modchu_Main.isForge) {
			o = Modchu_Reflect.invokeMethod("cpw.mods.fml.common.Loader", "instance");
			if (o != null) {
				List list = (List) Modchu_Reflect.invokeMethod("cpw.mods.fml.common.Loader", "getActiveModList", o);
				if (list != null) {
					int size = list.size();
					String name = null;
					for (int i = 0; i < size; i++)
					{
						o = list.get(i);
						name = (String) Modchu_Reflect.invokeMethod("cpw.mods.fml.common.ModContainer", "getName", o);
						if (name.startsWith("Aether")) {
							isAether = true;
							Modchu_Debug.lDebug("Aether Check ok.");
							aetherAddRenderer = true;
							oldRender = true;
							Class PlayerCoreRender = Modchu_Reflect.loadClass("net.aetherteam.playercore_api.cores.PlayerCoreRender");
							Modchu_Debug.lDebug("Aether Check PlayerCoreRender="+PlayerCoreRender);
							Modchu_Debug.lDebug("Aether Check Modchu_Reflect.loadClass(PFLM_RenderPlayerAether)="+Modchu_Reflect.loadClass("PFLM_RenderPlayerAether"));
							Render render = RenderManager.instance.getEntityClassRenderObject(EntityPlayer.class);
							Class PFLMF_Aether = Modchu_Reflect.loadClass("PFLMF_Aether");
							if (PFLMF_Aether != null) ;else throw new RuntimeException("PlayerFormLittleMaidFML PFLMF_Aether not found !!");
							boolean b1 = Modchu_Reflect.setFieldObject("PFLMF_Aether", "render_PlayerCoreRender", render);
							if (!b1) throw new RuntimeException("PlayerFormLittleMaidFML PFLMF_Aether set error !!");
							Modchu_Debug.lDebug("Aether Check render.getClass()="+render.getClass());
							//mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer = (PFLM_RenderPlayer) Modchu_Reflect.invokeMethod(PlayerCoreRender, "getPlayerCoreObject", new Class[]{ Class.class }, render, new Object[]{ Modchu_Reflect.loadClass("PFLM_RenderPlayerAether") });
							mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer = (PFLM_RenderPlayer) Modchu_Reflect.newInstance("PFLM_RenderPlayerAether");
							if (mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer != null) ;else {
								throw new RuntimeException("Aether Check pflm_RenderPlayer == null !!");
							}
							//render = (Render) Modchu_Reflect.invokeMethod(PlayerCoreRender, "getPlayerCoreObject", new Class[]{ Class.class }, render, new Object[]{ Modchu_Reflect.loadClass("net.aetherteam.aether.client.RenderPlayerAether") });
							render = (Render) Modchu_Reflect.newInstance("modchu.pflm.PFLMF_RenderPlayerAether");
							if (render != null) ;else {
								throw new RuntimeException("Aether Check PFLMF_RenderPlayerAether render == null !!");
							}
							render.setRenderManager(RenderManager.instance);
							Modchu_Reflect.setFieldObject("PFLMF_Aether", "renderPlayerAether", render);
							Render render2 = (Render) Modchu_Reflect.getFieldObject(PlayerCoreRender, "renderPlayer", render);
							Modchu_Reflect.setFieldObject(PlayerCoreRender, "nextPlayerCore", render, render2);
							Modchu_Debug.lDebug("Aether Check render="+render.getClass()+" render2="+render2.getClass());
							//Modchu_Reflect.setFieldObject(PlayerCoreRender, "shouldCallSuper", render, true);
							//cpw.mods.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(Modchu_Reflect.loadClass("net.aetherteam.playercore_api.cores.PlayerCoreClient"), (Render) mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
							//Modchu_Reflect.invokeMethod("cpw.mods.fml.client.registry.RenderingRegistry", "registerEntityRenderingHandler", new Class[]{ Class.class, Render.class }, new Object[]{ EntityPlayer.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer });
							break;
						}
					}
				} else {
					Modchu_Debug.lDebug("Aether Check list == null !!");
				}
			} else {
				Modchu_Debug.lDebug("Aether Check o == null !!");
			}
		}
	}

	public String getClassName(String s) {
		if (s == null) return null;
		if (s.indexOf(".") > -1) return s;
		if (packageName != null) return packageName.concat(".").concat(s);
		return s;
	}

	public void loadInit() {
		loadInitFlag = true;
		mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy = (PFLM_RenderPlayerDummy) Modchu_Reflect.newInstance("PFLM_RenderPlayerDummy");
	}

	public void addRenderer(Map map)
	{
		if (!isPlayerForm) return;
		((Render) mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy).setRenderManager(RenderManager.instance);
//-@-125
		Modchu_Debug.lDebug("addRenderer");
		if (!isAether) {
			mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer = (PFLM_RenderPlayer) Modchu_Reflect.newInstance("PFLM_RenderPlayer");
			((Render) mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer).setRenderManager(RenderManager.instance);

			//if (isSmartMoving) {
				//PFLM_RenderPlayerSmart var1 = new PFLM_RenderPlayerSmart();
				//map.put(EntityClientPlayerMP.class, var1);
				//map.put(EntityOtherPlayerMP.class, var1);
			//}
			//else

			if (mod_Modchu_ModchuLib.modchu_Main.isForge) {
				//isModelSize
				//if (isPlayerAPI
				//&& !isPlayerAPIDebug) map.put(PFLM_EntityPlayer.class, var1);
				//else
				map.put(Modchu_Reflect.loadClass("PFLM_EntityPlayerSP"), mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
				map.put(EntityClientPlayerMP.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
				map.put(EntityOtherPlayerMP.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
			}
			else if (isOlddays) {
				try {
					mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer = (PFLM_RenderPlayer) Modchu_Reflect.newInstance("PFLM_RenderPlayer2", null, null);
					map.put(EntityClientPlayerMP.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
					map.put(EntityOtherPlayerMP.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
					map.remove(EntityPlayer.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				//map.put(EntityClientPlayerMP.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
				//map.put(EntityOtherPlayerMP.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
				map.put(EntityPlayer.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
			}
		} else {
			Modchu_Debug.lDebug("addRenderer isAether mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer="+mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
			((Render) mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer).setRenderManager(RenderManager.instance);
			map.put(EntityPlayer.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
		}
		map.put(PFLM_EntityPlayerDummy.class, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy);
//@-@125
/*//125delete
		if (!isAether) {
			Modchu_Debug.lDebug("addRenderer");
			map.put(EntityPlayerSP.class, new pflm_renderPlayer());
			map.put(EntityOtherPlayerMP.class, new pflm_renderPlayer());
		}
		if (guiMultiPngSaveButton) {

		//if (isSmartMoving) {
		//map.put(PFLM_EntityPlayerDummy.class, mod_PFLM_PlayerFormLittleMaid..pflm_RenderPlayerDummy);
		//} else {

			map.put(PFLM_EntityPlayerDummy.class, mod_PFLM_PlayerFormLittleMaid..pflm_RenderPlayerDummy);
		//}
		}
*///125delete
	}

	public void keyboardEvent(KeyBinding keybinding) {
		//Modchu_Debug.Debug("keyboardEvent");
		Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_71462_r", "currentScreen", mc);
		if (currentScreen instanceof GuiChat) return;
//-@-125
		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129) {
			if (mod_Modchu_ModchuLib.modchu_Main.isForge) {
				if (keybinding.keyCode == keyCode
						&& keybindingTime > 0) {
					//Modchu_Debug.Debug("keyboardEvent return keybindingTime="+keybindingTime);
					return;
				}
				setKeyCode(keybinding.keyCode);
				keybindingTime = 10;
			}
		}
//@-@125
		// GUIを開く
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		World theWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();
		if (thePlayer == null) {
			//ここに来る=取得ミス=GUIが出せない。致命的
			//Modchu_Debug.Debug("keyboardEvent return thePlayer == null");
			return;
		}
		if (keybinding.keyDescription.equals("key.PlayerFormLittleMaid")) {
			if (theWorld != null && currentScreen == null
					&& !Keyboard.isKeyDown(PFLMModelsKeyCode)
					&& !mod_Modchu_ModchuLib.modchu_Main.isCtrlKeyDown()
					&& !mod_Modchu_ModchuLib.modchu_Main.isShiftKeyDown()) {
				ModLoader.openGUI(thePlayer, new PFLM_Gui(theWorld));
			}
			return;
		}
		if (keybinding.keyDescription.equals("key.Sit")) {
			if (!Keyboard.isKeyDown(PFLMModelsKeyCode)
					&& !mod_Modchu_ModchuLib.modchu_Main.isCtrlKeyDown()
					&& !mod_Modchu_ModchuLib.modchu_Main.isShiftKeyDown()
					&& !keySitLock) {
				float f = thePlayer.moveForward * thePlayer.moveForward + thePlayer.moveStrafing * thePlayer.moveStrafing;
				//Modchu_Debug.mDebug("-----key.Sit s");
				//Modchu_Debug.mDebug("key.Sit entityplayersp.isRiding()="+entityplayersp.isRiding());
				if ((Boolean) Modchu_Reflect.getFieldObject("Minecraft", "field_71415_G", "inGameHasFocus", mc) && (double)f < 0.20000000000000001D && !thePlayer.isJumping) {
					boolean b = getIsSitting();
					//Modchu_Debug.mDebug("key.Sit setIsSitting getIsSitting()="+getIsSitting());
					setIsSitting(!b);
					//Modchu_Debug.mDebug("key.Sit setIsSitting !b="+!b);
					setIsSleeping(false);
					keySitLock = true;
				}
				//Modchu_Debug.mDebug("key.Sit getIsSitting()="+getIsSitting());
				//Modchu_Debug.mDebug("key.Sit entityplayersp.isRiding()="+entityplayersp.isRiding());
				//Modchu_Debug.mDebug("-----key.Sit e");
			}
			return;
		} else if (keybinding.keyDescription.equals("key.LieDown")) {
			if (!Keyboard.isKeyDown(PFLMModelsKeyCode)
					&& !mod_Modchu_ModchuLib.modchu_Main.isCtrlKeyDown()
					&& !mod_Modchu_ModchuLib.modchu_Main.isShiftKeyDown()) {
				boolean b = getIsSleeping();
				setIsSleeping(!b);
				setIsSitting(false);
			}
			return;
		} else if (keybinding.keyDescription.equals("key.PFLM Models Key")) {
			if (mod_Modchu_ModchuLib.modchu_Main.isCtrlKeyDown()) {
				clearDataMap();
				GameSettings gameSettings = (GameSettings) Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mc);
				if (changeMode == PFLM_Gui.modeRandom
						&& gameSettings.thirdPersonView == 0) gameSettings.thirdPersonView = 1;
			}
			return;
		} else if (keybinding.keyDescription.startsWith("key.ModelChange")
				&& isPlayerForm) {
			int i = Integer.valueOf(keybinding.keyDescription.substring("key.ModelChange".length(), keybinding.keyDescription.length()));
			boolean flag = true;
			if (shortcutKeysPFLMModelsUse[i]
					&& !Keyboard.isKeyDown(PFLMModelsKeyCode)) flag = false;
			if (shortcutKeysCtrlUse[i]
					&& !mod_Modchu_ModchuLib.modchu_Main.isCtrlKeyDown()) flag = false;
			if (shortcutKeysShiftUse[i]
					&& !mod_Modchu_ModchuLib.modchu_Main.isShiftKeyDown()) flag = false;
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
					changeMode = PFLM_Gui.modeOffline;
					clear = true;
					break;
				case PFLM_GuiKeyControls.modePlayerOnline:
					changeMode = PFLM_Gui.modeOnline;
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeRandom:
					changeMode = PFLM_Gui.modeRandom;
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
				case PFLM_GuiKeyControls.modeCustomModelCfgReLoad:
					customModelCfgReLoad();
					clear = true;
					break;
				case PFLM_GuiKeyControls.modeAllMultiModelActionModeChangePlus:
					Modchu_ModelDataBase.setAllMultiModelActionModePlus();
					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useAddChatMessage) mod_Modchu_ModchuLib.modchu_Main.printChatMessage("PFLM AllMultiModelActionMode = "+Modchu_ModelDataBase.getAllMultiModelActionModeName(Modchu_ModelDataBase.getAllMultiModelActionMode()));
					break;
				case PFLM_GuiKeyControls.modeAllMultiModelActionModeChangeMinus:
					Modchu_ModelDataBase.setAllMultiModelActionModeMinus();
					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useAddChatMessage) mod_Modchu_ModchuLib.modchu_Main.printChatMessage("PFLM AllMultiModelActionMode = "+Modchu_ModelDataBase.getAllMultiModelActionModeName(Modchu_ModelDataBase.getAllMultiModelActionMode()));
					break;
				case PFLM_GuiKeyControls.modeAllMultiModelActionPlus:
					Modchu_ModelDataBase.setAllMultiModelActionPlus();
					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useAddChatMessage) mod_Modchu_ModchuLib.modchu_Main.printChatMessage("PFLM AllMultiModelAction = "+Modchu_ModelDataBase.getAllMultiModelRunActionNumber());
					break;
				case PFLM_GuiKeyControls.modeAllMultiModelActionMinus:
					Modchu_ModelDataBase.setAllMultiModelActionMinus();
					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useAddChatMessage) mod_Modchu_ModchuLib.modchu_Main.printChatMessage("PFLM AllMultiModelAction = "+Modchu_ModelDataBase.getAllMultiModelRunActionNumber());
					break;
				case PFLM_GuiKeyControls.modeAllMultiModelActionRun:
					Modchu_ModelDataBase.setAllMultiModelActionFlag(!Modchu_ModelDataBase.getAllMultiModelActionFlag());
					break;
				}
				if (shortcutKeysChangeMode[i] >= PFLM_GuiKeyControls.modeAction
						&& shortcutKeysChangeMode[i] <= PFLM_GuiKeyControls.modeActionLast) {
					int i1 = shortcutKeysChangeMode[i] - PFLM_GuiKeyControls.modeAction + 1;
					if (getRunActionNumber() == i1) {
						setShortcutKeysAction(false);
					} else {
						if (!getShortcutKeysAction()
								| (getShortcutKeysAction()
										&& getRunActionNumber() != i1)) {
							setRunActionNumber(i1);
							setActionReleaseNumber(getRunActionNumber());
							setShortcutKeysAction(true);
						}
					}
				}
				if (PFLM_Gui.partsSaveFlag) {
					PFLM_Gui.partsSaveFlag = false;
					saveParamater();
					PFLM_Config.clearCfgData();
				}
				if (clear) {
					clearDataMap();
					GameSettings gameSettings = (GameSettings) Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mc);
					if (changeMode == PFLM_Gui.modeRandom
							&& gameSettings.thirdPersonView == 0) gameSettings.thirdPersonView = 1;
				}
				shortcutKeysNumber = i;
			}
			return;
		}
		else if (waitTime == 0) {
			if (keybinding.keyDescription.equals("key.PFLM wait"))
			{
				//Modchu_Debug.mDebug("PFLMWait");
				if (!thePlayer.isJumping)
				{
					isWait = !isWait;
				}
			}
		}
	}

	private void setKeyCode(int i) {
		keyCode = i;
	}

	public boolean onTickInGUI(float f, Object minecraft, GuiScreen par1GuiScreen) {
//-@-125
		//if(isSmartMoving && mc != null) PFLM_SmartMovingOther.TranslateIfNecessary((GameSettings)null);
//@-@125
/*//125delete
//-@-110
		if (!smartMovingAddRenderer
				&& isPlayerForm
				&& isSmartMoving
				&& !mod_Modchu_ModchuLib.modchu_Main.isForge) {
			PFLM_PlayerController.addRenderer();
			smartMovingAddRenderer = true;
			Modchu_Debug.lDebug("SmartMovingAddRenderer");
		}
//@-@110
//-@-123
		// GUI置き換え
		if (par1GuiScreen instanceof GuiSelectWorld
				&& isAether
				&& guiSelectWorldSwapCount < 10) {
			// ワールドセレクトを置き換え
			if (!(par1GuiScreen instanceof PFLM_GuiSelectWorldAether)) {
				guiSelectWorldSwapCount++;
				try {
					int ID = (Integer) Modchu_Reflect.getFieldObject(GuiMainMenu.class, "musicId", par1GuiScreen);
					minecraft.displayGuiScreen(new PFLM_GuiSelectWorldAether((GuiSelectWorld)par1GuiScreen, ID));
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
			if (!(par1GuiScreen instanceof PFLM_GuiSelectWorld)) {
				guiSelectWorldSwapCount++;
				minecraft.displayGuiScreen(new PFLM_GuiSelectWorld((GuiSelectWorld)par1GuiScreen));
				Modchu_Debug.Debug("Swap GuiSelectWorld.");
//-@-b166
				return true;
//@-@b166
				// b166deletereturn;
			}
		}
*///125delete
//-@-b166
		return false;
		//return true;
//@-@b166
		// b166deletereturn;
	}

	public boolean onTickInGame(float f, Object minecraft)
	{
		if (runtimeExceptionFlag) throw new RuntimeException(runtimeExceptionString);
		//else if (!runtimeExceptionFlag) return false;
		sitSleepResetCheck();

		if (!Keyboard.getEventKeyState()
				&& !Mouse.getEventButtonState()) {
			if (!isReleasekey) isReleasekey = true;
			if (keySitLock) {
				//Modchu_Debug.mDebug("keySitLock解除");
				keySitLock = false;
			}
		} else if (isReleasekey) isReleasekey = false;
		if (mc != null) ;else {
			mc = mod_Modchu_ModchuLib.modchu_Main.getMinecraft();
			if (mc != null) ;else Modchu_Debug.lDebug("onTickInGame mc == null !!");
		}

//-@-125
/*
		Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_71462_r", "currentScreen", mc);
		if (currentScreen != null
				&& !mod_Modchu_ModchuLib.modchu_Main.isForge) onTickInGUI(0.0F, minecraft, (GuiScreen) currentScreen);
*/
//@-@125
		if (!itemRendererReplaceFlag
				&& itemRendererClass != null) {
			ItemRenderer itemRenderer = null;
			EntityRenderer entityRenderer = (EntityRenderer) Modchu_Reflect.getFieldObject("Minecraft", "field_71460_t", "entityRenderer", mc);
			if (entityRenderer != null) ;else Modchu_Debug.Debug("onTickInGame entityRenderer == null !!");
			for(int i = 0; i < 2; i++) {
				itemRenderer = i == 0 ? entityRenderer.itemRenderer: RenderManager.instance.itemRenderer;
				if (!instanceCheck(itemRendererClass, itemRenderer)) {
					ItemRenderer itemRenderer2 = newInstanceItemRenderer();
					if (itemRenderer2 != null) {
						Modchu_Debug.mDebug("onTickInGame itemRenderer2 != null");
						if (i == 0) {
							entityRenderer.itemRenderer = itemRenderer2;
						} else if (i == 1) {
							RenderManager.instance.itemRenderer = itemRenderer2;
						}
						if (isHD) {
							initItemRendererHD = true;
						} else {
							initItemRenderer = true;
						}
						itemRendererReplaceFlag = true;
					} else {
						Modchu_Debug.mDebug("onTickInGame itemRenderer2 == null !!");
					}
				}
			}
		}
/*//125delete
//-@-110
		if (isPlayerForm
				&& isSmartMoving
				&& !mod_Modchu_ModchuLib.modchu_Main.isForge) {
			if (!(RenderManager.instance.getEntityClassRenderObject(EntityPlayerSP.class) instanceof pflm_renderPlayer)) {
				PFLM_PlayerController.addRenderer();
				Modchu_Debug.Debug("onTickInGame SmartMovingAddRenderer");
			}
		}
//@-@110
*///125delete
//-@-125
		if (isSSP) {
			Object playerController = Modchu_Reflect.getFieldObject("Minecraft", "field_71442_b", "playerController", mc);
			if (!Modchu_Reflect.loadClass("PFLM_PlayerController2").isInstance(playerController)
				&& netclienthandler != null) {
				Class[] types = { Modchu_Reflect.loadClass("Minecraft") , NetClientHandler.class };
				Object[] args = {mc, netclienthandler};
				Modchu_Reflect.setFieldObject("Minecraft", "field_71442_b", "playerController", mc, Modchu_Reflect.newInstance("PFLM_PlayerController2", types, args));
				//playerController = new PFLM_PlayerController2(mc, netclienthandler);
			}
			Object b = Modchu_Reflect.getFieldObject(Modchu_Reflect.getField("PFLM_PlayerController2", "entityplayerformlittlemaidsp"), null);
			EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
			World theWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();
			if (theWorld.getWorldInfo().getGameType() == EnumGameType.SURVIVAL
					&& !Modchu_Reflect.loadClass("PFLM_EntityPlayerSP2").isInstance(thePlayer)
					&& b != null) {
				mod_Modchu_ModchuLib.modchu_Main.setThePlayer((EntityClientPlayerMP) b);
				//thePlayer = PFLM_PlayerController2.entityplayerformlittlemaidsp;
			}
			if (theWorld.getWorldInfo().getGameType() == EnumGameType.CREATIVE
					&& !Modchu_Reflect.loadClass("PFLM_PlayerControllerCreative2").isInstance(playerController)
					&& netclienthandler != null) {
				Class[] types = { Modchu_Reflect.loadClass("Minecraft") , NetClientHandler.class };
				Object[] args = {mc, netclienthandler};
				Modchu_Reflect.setFieldObject("Minecraft", "field_71442_b", "playerController", mc, Modchu_Reflect.newInstance("PFLM_PlayerControllerCreative2", types, args));
				//playerController = new PFLM_PlayerControllerCreative2(mc, netclienthandler);
			}
		}
		if (isOlddays) {
			if (!Modchu_Reflect.loadClass("PFLM_RenderPlayer2").isInstance(RenderManager.instance.getEntityClassRenderObject(EntityClientPlayerMP.class))) {
				Object ret = Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "addRenderer");
				//PFLM_PlayerController2.addRenderer();
				//Modchu_Debug.Debug("onTickInGame PFLM_PlayerController2.addRenderer()");
			}
		}

		if (mod_Modchu_ModchuLib.modchu_Main.isForge
				&& keybindingTime > 0) --keybindingTime;
		if (Modchu_Reflect.invokeMethod("Minecraft", "func_71391_r", "getNetHandler", mc) != null) {
			if (!(Boolean) Modchu_Reflect.invokeMethod("Minecraft", "func_71356_B", "isSingleplayer", mc)) {
				if (multiAutochangeMode) {
					if (changeMode == PFLM_Gui.modeOffline
						&& setMultiAutochangeMode) {
						setMultiAutochangeMode = false;
						changeMode = PFLM_Gui.modeOnline;
						clearDataMap();
					} else {
						setMultiAutochangeMode = false;
					}
				}
				if (!isMulti) isMulti = true;
			} else {
				if (isMulti) isMulti = false;
			}
		}
		World theWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();
		if (theWorld == null
				&& !setMultiAutochangeMode) {
			//Modchu_Debug.mDebug("onTickInGame minecraft.theWorld == null");
			setMultiAutochangeMode = true;
		}

		if (!addRendererFlag
				&& mod_Modchu_ModchuLib.modchu_Main.isForge) {
			addRendererFlag = true;
			Map map = (Map) Modchu_Reflect.getFieldObject(RenderManager.class, "field_78729_o", "entityRenderMap", Modchu_Reflect.getFieldObject(RenderManager.class, "field_78727_a", "instance"));
			//Map map = RenderManager.instance.entityRenderMap;
			mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.addRenderer(map);
		}
		//if(isSmartMoving
				//&& mc != null) PFLM_SmartMovingOther.onTickInGame();
//@-@125
/*//125delete
			if(minecraft.thePlayer.worldObj.isRemote) {
				if (multiAutochangeMode) {
					if (changeMode != PFLM_Gui.modeOnline
						&& setMultiAutochangeMode) {
						Modchu_Debug.mDebug("onTickInGame setMultiAutochangeMode");
						setMultiAutochangeMode = false;
						changeMode = PFLM_Gui.modeOnline;
						clearDataMap();
					} else {
						setMultiAutochangeMode = false;
					}
				}
				if (!isMulti) isMulti = true;
				if (minecraft.theWorld == null
						&& !setMultiAutochangeMode) {
					Modchu_Debug.mDebug("onTickInGame !setMultiAutochangeMode minecraft.theWorld == null");
					setMultiAutochangeMode = true;
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
/*
		if (isPlayerForm
					&& isSmartMoving) {
				if (!(RenderManager.instance.getEntityClassRenderObject(EntityClientPlayerMP.class) instanceof PFLM_RenderPlayerSmart)
						| !(RenderManager.instance.getEntityClassRenderObject(EntityOtherPlayerMP.class) instanceof PFLM_RenderPlayerSmart)) {
					PFLM_PlayerController.addRenderer();
					Modchu_Debug.Debug("onTickInGame SmartMovingAddRenderer");
				}
			}
*/
//-@-123
/*//125delete
			if (!aetherAddRenderer && isAether) {
				if (!(RenderManager.instance.getEntityClassRenderObject(thePlayer.getClass()) instanceof PFLM_RenderPlayerAether)) {
					aetherAddRenderer = true;
					RenderPlayer renderplayer = new PFLM_RenderPlayerAether();
					Object obj = null;
					try {
						obj = getPrivateValue(RenderManager.class, RenderManager.instance, "entityRenderMap");
					} catch (Exception e) {
					}
					if (obj == null) {
						try {
							obj = getPrivateValue(RenderManager.class, RenderManager.instance, "o");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (obj != null) {
						((Map) obj).put(thePlayer.getClass(), renderplayer);
						((Map) obj).put(EntityOtherPlayerMP.class, renderplayer);
						renderplayer.setRenderManager(RenderManager.instance);
						Modchu_Debug.lDebug("aetherAddRenderer");
						//Modchu_Debug.lDebug("getEntityClassRenderObject "+(RenderManager.instance.getEntityClassRenderObject(thePlayer.getClass())));
					} else {
						Modchu_Debug.lDebug("aetherAddRenderer obj null !!");
					}
				}
			}
*///125delete
//@-@123
//-@-b166
		return true;
	}

	public static boolean instanceCheck(Class c, Object o) {
		boolean b = true;
		if (o != null) {
			if(!c.isInstance(o)) {
				b = false;
			}
		} else {
			b = false;
		}
		return b;
	}

	public static ItemRenderer newInstanceItemRenderer() {
		return (ItemRenderer) Modchu_Reflect.newInstance(itemRendererClass, new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mod_Modchu_ModchuLib.modchu_Main.getMinecraft() });
	}

	private double getPosX(EntityPlayer entityplayer) {
		return entityplayer.posX;
	}

	private double getPosY(EntityPlayer entityplayer) {
		return entityplayer.posY;
	}

	private double getPosZ(EntityPlayer entityplayer) {
		return entityplayer.posZ;
	}

	public void serverConnect(NetClientHandler netClientHandler) {
		clientConnect(netClientHandler);
		return;
	}

	public void clientConnect(NetClientHandler netClientHandler) {
		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() < 130
				| !entityReplaceFlag) return;
		//Modchu_Debug.mDebug("clientConnect");
		netclienthandler = netClientHandler;
		if (mc != null) ;else mc = mod_Modchu_ModchuLib.modchu_Main.getMinecraft();
		double x = 0.0D;
		double y = 0.0D;
		double z = 0.0D;
		boolean setPositionFlag = false;
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		if (thePlayer != null) {
			setPositionFlag = true;
			x = getPosX(thePlayer);
			y = getPosY(thePlayer);
			z = getPosZ(thePlayer);
		}
		EntityPlayer var9 = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		//Modchu_Debug.Debug("get x="+x+" y="+y+" z="+z);
		Object playerController = Modchu_Reflect.getFieldObject("Minecraft", "field_71442_b", "playerController", mc);
		World theWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();
		try {
			EnumGameType enumGameType = (EnumGameType) Modchu_Reflect.getPrivateValue(PlayerControllerMP.class, playerController, 11);
			//int var2 = 0;
			//if (thePlayer != null) var2 = thePlayer.entityId;

			Class[] types = { Modchu_Reflect.loadClass("Minecraft") , NetClientHandler.class };
			Object[] args = {mc, netclienthandler};
			if(isSSP) {
				enumGameType = theWorld.getWorldInfo().getGameType();
				if (enumGameType != null) {
					//Modchu_Debug.Debug("enumGameType="+enumGameType);
					if (enumGameType == EnumGameType.CREATIVE) {
						Modchu_Reflect.setFieldObject("Minecraft", "field_71442_b", "playerController", mc, Modchu_Reflect.newInstance("PFLM_PlayerControllerCreative2", types, args));
						//playerController = (PlayerControllerMP) Modchu_Reflect.newInstance(pflm_playerControllerCreative2, types, args);
						//playerController = new PFLM_PlayerControllerCreative2(mc, netClientHandler);
						Modchu_Debug.lDebug("Replace PFLM_PlayerControllerCreative2.");
					} else {
						Modchu_Reflect.setFieldObject("Minecraft", "field_71442_b", "playerController", mc, Modchu_Reflect.newInstance("PFLM_PlayerController2", types, args));
						//playerController = (PlayerControllerMP) Modchu_Reflect.newInstance(pflm_playerController2, types, args);
						//playerController = new PFLM_PlayerController2(mc, netClientHandler);
						Modchu_Debug.lDebug("Replace PFLM_PlayerController2.");
					}
				} else {
					Modchu_Reflect.setFieldObject("Minecraft", "field_71442_b", "playerController", mc, Modchu_Reflect.newInstance("PFLM_PlayerController2", types, args));
					//playerController = (PlayerControllerMP) Modchu_Reflect.newInstance(pflm_playerController2, types, args);
					//playerController = new PFLM_PlayerController2(mc, netClientHandler);
					Modchu_Debug.lDebug("Replace PFLM_PlayerController2.");
				}
			} else {
				Modchu_Reflect.setFieldObject("Minecraft", "field_71442_b", "playerController", mc, Modchu_Reflect.newInstance("PFLM_PlayerController", types, args));
				//playerController = new PFLM_PlayerController(mc, netClientHandler);
				Modchu_Debug.lDebug("Replace PFLM_PlayerController.");
			}

			if (enumGameType != null) {
				Modchu_Debug.mDebug("enumGameType="+enumGameType);
				Modchu_Reflect.invokeMethod("PlayerControllerMP", "setGameType", new Class[]{ EnumGameType.class }, playerController, new Object[]{ enumGameType });
			}
			if (thePlayer != null) Modchu_Reflect.invokeMethod("Minecraft", "func_71354_a", "setDimensionAndSpawnPlayer", new Class[]{ int.class }, mc, new Object[]{ thePlayer.dimension });
			else Modchu_Reflect.invokeMethod("Minecraft", "func_71354_a", "setDimensionAndSpawnPlayer", new Class[]{ int.class }, mc, new Object[]{ 0 });
			if(isSSP
					&& enumGameType != null
					&& enumGameType == EnumGameType.CREATIVE) {
				Modchu_Reflect.invokeMethod("PlayerControllerCreative", "a", new Class[]{EntityPlayer.class}, playerController, new Object[]{ thePlayer });
				//((PFLM_PlayerControllerCreative2) playerController).setPlayerCapabilities(thePlayer);
				Modchu_Reflect.invokeMethod("PFLM_PlayerControllerCreative2", "setInCreativeMode", new Class[]{boolean.class}, playerController, new Object[]{ true });
				//((PFLM_PlayerControllerCreative2) playerController).setInCreativeMode(true);
				Modchu_Reflect.invokeMethod("PFLM_EntityPlayerSP2", "copyPlayer", new Class[]{EntityPlayer.class}, mod_Modchu_ModchuLib.modchu_Main.getThePlayer(), new Object[]{ var9 });
				//((PFLM_EntityPlayerSP2) thePlayer).copyPlayer(var9);
				//Modchu_Debug.Debug("isSSP CREATIVE set");
			}

/*//@/
			if(!isSmartMoving
					&& isModelSize
					&& !isPlayerAPIDebug) {
				PFLM_PlayerBaseServer.registerPlayerBase();
				Modchu_Debug.Debug("PlayerAPI Server register.");
			}
*///@/
/*
			int var3 = thePlayer.dimension;
		        theWorld.setSpawnLocation();
		        theWorld.removeAllEntities();
		        if (thePlayer != null)
		        {
		        	theWorld.setEntityDead(thePlayer);
		        }
		        renderViewEntity = null;
		        thePlayer = playerController.func_78754_a(theWorld);
		        thePlayer.dimension = var3;
		        renderViewEntity = thePlayer;
		        thePlayer.preparePlayerToSpawn();
		        theWorld.spawnEntityInWorld(thePlayer);
		        playerController.flipPlayer(thePlayer);
		        thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
		        if (var2 > 0) thePlayer.entityId = var2;
		        playerController.setPlayerCapabilities(thePlayer);
		        thePlayer.sendQueue.handleClientCommand(new Packet205ClientCommand(1));
*/

			if (setPositionFlag) {
				//double d = (double)(1.8F - getHeight());
				//if (d < 0) d = -d + 1.0D;
				//d = d + 0.5D;
				//Modchu_Debug.mDebug("setPositionFlag getHeight()="+getHeight());
				if (isModelSize) {
					setPosition(x, y, z);
					setPositionCorrection(0.0D, 0.5D, 0.0D);
				}
				//thePlayer.setPositionAndRotation2(x, y, z, thePlayer.rotationYaw, thePlayer.rotationPitch, 3);
				//Modchu_Debug.mDebug("setPositionAndRotation2 x="+x+" y="+y+" z="+z);
			}
			clearDataMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void playerControllerReplace() {
		if (mc != null) ;else mc = mod_Modchu_ModchuLib.modchu_Main.getMinecraft();
		World theWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();
		if (theWorld == null
				| Modchu_Reflect.getFieldObject("World", "field_4209_q", "worldProvider", theWorld) == null) return;
		double x = 0.0D;
		double y = 0.0D;
		double z = 0.0D;
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		boolean setPositionFlag = false;
		if (thePlayer != null) {
			setPositionFlag = true;
			x = getPosX(thePlayer);
			y = getPosY(thePlayer);
			z = getPosZ(thePlayer);
		}
		EntityPlayer var9 = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		//Modchu_Debug.Debug("playerControllerReplace get x="+x+" y+"+y+" z="+z);
		int var10 = 0;

		if (thePlayer != null) {
			var10 = thePlayer.entityId;
			Modchu_Reflect.invokeMethod(World.class, "func_607_d", "setEntityDead", new Class[]{ Entity.class }, theWorld, new Object[]{ thePlayer });
			//theWorld.setEntityDead(thePlayer);
		}
		//Modchu_Debug.Debug("x="+minecraft.thePlayer.posX+" y="+minecraft.thePlayer.posY+" z="+minecraft.thePlayer.posZ);
		Object playerController = Modchu_Reflect.getFieldObject("Minecraft", "field_71442_b", "playerController", mc);
 		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 79
 				&& !(Boolean) Modchu_Reflect.getFieldObject("PlayerController", "field_1064_b", "isInTestMode", playerController)
 				| mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() < 80) {
			Modchu_Debug.lDebug("Replace PFLM_PlayerController.");
			Modchu_Reflect.setFieldObject("Minecraft", "field_71442_b", "playerController", mc, Modchu_Reflect.newInstance("PFLM_PlayerController", new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc }));
			//playerController = new PFLM_PlayerController(mc);
 		} else {
			Modchu_Debug.lDebug("Replace PFLM_PlayerControllerCreative.");
			Modchu_Reflect.setFieldObject("Minecraft", "field_71442_b", "playerController", mc, Modchu_Reflect.newInstance("PFLM_PlayerControllerCreative", new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc }));
			//playerController = new PFLM_PlayerControllerCreative(mc);
		}
		if (thePlayer != null) thePlayer.setDead();
		mod_Modchu_ModchuLib.modchu_Main.setThePlayer((EntityPlayer) Modchu_Reflect.invokeMethod("PlayerControllerMP", "createPlayer", new Class[]{ World.class }, playerController, new Object[]{ theWorld }));
		Modchu_Reflect.setFieldObject("Minecraft", "field_71451_h", "renderViewEntity", mc, new Object[]{ null });
		if (thePlayer != null
				&& var9 != null) {
			if (isPlayerAPI && !isPlayerAPIDebug) {
				Modchu_Reflect.invokeMethod("PFLM_EntityPlayer", "copyPlayer", new Class[]{EntityPlayer.class}, mod_Modchu_ModchuLib.modchu_Main.getThePlayer(), new Object[]{ var9 });
			} else {
				Modchu_Reflect.invokeMethod("PFLM_EntityPlayerSP", "copyPlayer", new Class[]{EntityPlayer.class}, mod_Modchu_ModchuLib.modchu_Main.getThePlayer(), new Object[]{ var9 });
			}
			//thePlayer.copyPlayer(var9);
		}
		//minecraft.thePlayer.dimension = par2;
		Modchu_Reflect.setFieldObject("Minecraft", "field_71451_h", "renderViewEntity", mc, thePlayer);
		//minecraft.thePlayer.preparePlayerToSpawn();
		//minecraft.thePlayer.setLocationAndAngles((double)((float)minecraft.thePlayer.posX + 0.5F), (double)((float)minecraft.thePlayer.posY + 0.1F), (double)((float)minecraft.thePlayer.posZ + 0.5F), 0.0F, 0.0F);

		Modchu_Reflect.invokeMethod("PlayerControllerMP", "func_78745_b", "flipPlayer", new Class[]{ EntityPlayer.class }, playerController, new Object[]{ thePlayer });
		Modchu_Reflect.invokeMethod(World.class, "spawnPlayerWithLoadedChunks", new Class[]{ EntityPlayer.class }, theWorld, new Object[]{ thePlayer });
		//playerController.flipPlayer(thePlayer);
		//theWorld.spawnPlayerWithLoadedChunks(thePlayer);
		Modchu_Reflect.setFieldObject(EntityPlayerSP.class, "movementInput", thePlayer, new MovementInputFromOptions((GameSettings) Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mc)));
		//thePlayer.movementInput = new MovementInputFromOptions(gameSettings);

		Modchu_Reflect.setFieldObject(Entity.class, "entityId", thePlayer, var10);
		//thePlayer.entityId = var10;
		Modchu_Reflect.invokeMethod(EntityPlayerSP.class, "func_6420_o", thePlayer);
		//thePlayer.func_6420_o();
		if(mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 79
 				&& (Boolean) Modchu_Reflect.invokeMethod("PlayerController", "isInCreativeMode", playerController)) Modchu_Reflect.invokeMethod("PlayerController", "func_6473_b", new Class[]{ EntityPlayer.class }, playerController, new Object[]{ thePlayer });
		if (isPlayerAPI
				&& !isPlayerAPIDebug) {
			if (!(Boolean) Modchu_Reflect.getFieldObject("PFLM_PlayerBase", "initFlag", thePlayer)) Modchu_Reflect.invokeMethod("PFLM_PlayerBase", "init", thePlayer);
		} else {
			if (!(Boolean) Modchu_Reflect.getFieldObject("PFLM_EntityPlayerSP", "initFlag", thePlayer)) Modchu_Reflect.invokeMethod("PFLM_EntityPlayerSP", "init", thePlayer);
		}
		setPosition(x, y, z);
	}

	public static void mushroomConfusion(EntityPlayer entityplayer, PFLM_ModelData modelData) {
		ItemStack itemstack = entityplayer.inventory.getStackInSlot(9);
		//if(mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 79) boolean mushroomConfusionResetFlag = true;
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof ItemBlock) {
				Block block = Block.blocksList[item.itemID];
				if (block instanceof BlockMushroom) {
					ItemStack itemstack2 = entityplayer.inventory.getStackInSlot(10);
					if (itemstack2 != null) {
						Item item2 = itemstack2.getItem();
						if (item2 == item.dyePowder) {
							//if(mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 79) mushroomConfusionResetFlag = false;
							//b173deleteif (!modelData.mushroomConfusionFlag) modelData.mushroomConfusionFlag = true;
							modelData.setCapsValue(modelData.caps_mushroomConfusionCount, modelData.getCapsValueInt(modelData.caps_mushroomConfusionCount) - 1);
							if(modelData.getCapsValueInt(modelData.caps_mushroomConfusionCount) < 0) {
								modelData.setCapsValue(modelData.caps_mushroomConfusionCount, 500 + (100 * rnd.nextInt(10)));
								modelData.setCapsValue(modelData.caps_mushroomConfusionType, rnd.nextInt(modelData.getCapsValueInt(modelData.caps_mushroomConfusionTypeMax)));
								//b173deleteif (modelData.mushroomConfusionType != 0) mushroomConfusion1(entityplayer, modelData);
							}
							//b173deleteif (modelData.mushroomConfusionType == 0) mushroomConfusion0(entityplayer, modelData);
							/*b173//*/mushroomConfusion(entityplayer, modelData, modelData.getCapsValueInt(modelData.caps_mushroomConfusionType));
							//Modchu_Debug.dDebug("modelData.mushroomConfusionCount="+modelData.getCapsValueInt(modelData.caps_mushroomConfusionCount));
							//Modchu_Debug.dDebug("modelData.mushroomConfusionType="+modelData.mushroomConfusionType);
						}
					}
				}
			}
		}
/*//b173delete
		if (mushroomConfusionFlag
				&& mushroomConfusionResetFlag) {
			mushroomConfusionFlag = false;
			gameSettings.keyBindForward = keyBindForward;
			gameSettings.keyBindBack = keyBindBack;
			gameSettings.keyBindLeft = keyBindLeft;
			gameSettings.keyBindRight = keyBindRight;
		}
*///b173delete
	}

	private static void mushroomConfusion(EntityPlayer entityplayer, PFLM_ModelData modelData, int i) {
		switch (i) {
		case 0:
			mushroomConfusion0(entityplayer, modelData);
			break;
		case 1:
		case 2:
		case 3:
		case 4:
			mushroomConfusion1(entityplayer, modelData);
			break;
		}
	}

	private static void mushroomConfusion0(EntityPlayer entityplayer, PFLM_ModelData modelData) {
		if (modelData.getCapsValueBoolean(modelData.caps_motionResetFlag)) {
			GameSettings gameSettings = (GameSettings) Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mc);
			if (entityplayer.motionX > 0.0D
					| entityplayer.motionZ > 0.0D) {
				if (gameSettings.keyBindForward.pressed
						| gameSettings.keyBindBack.pressed
						| gameSettings.keyBindLeft.pressed
						| gameSettings.keyBindRight.pressed) {
					//Modchu_Debug.mDebug("pressed");
					modelData.setCapsValue(modelData.caps_motionResetFlag, false);
					modelData.setCapsValue(modelData.caps_mushroomConfusionLeft, false);
					if(entityplayer.motionX > 0.001D) {
						modelData.setCapsValue(modelData.caps_mushroomConfusionLeft, true);
						//Modchu_Debug.mDebug("mushroomConfusionLeft");
					}
					modelData.setCapsValue(modelData.caps_mushroomConfusionRight, false);
					if(entityplayer.motionX < -0.001D) {
						modelData.setCapsValue(modelData.caps_mushroomConfusionRight, true);
						//Modchu_Debug.mDebug("mushroomConfusionRight");
					}
					modelData.setCapsValue(modelData.caps_mushroomConfusionFront, false);
					if(entityplayer.motionZ > 0.0001D) {
						modelData.setCapsValue(modelData.caps_mushroomConfusionFront, true);
						//Modchu_Debug.mDebug("mushroomConfusionFront");
					}
					modelData.setCapsValue(modelData.caps_mushroomConfusionBack, false);
					if(entityplayer.motionZ < -0.001D) {
						modelData.setCapsValue(modelData.caps_mushroomConfusionBack, true);
						//Modchu_Debug.mDebug("mushroomConfusionBack");
					}
					modelData.setCapsValue(modelData.caps_motionSetFlag, true);
				}
			}
		} else {
			float f = entityplayer.moveForward * entityplayer.moveForward + entityplayer.moveStrafing * entityplayer.moveStrafing;
			//Modchu_Debug.mDebug("f="+f);
			if (!entityplayer.isRiding() && (Boolean) Modchu_Reflect.getFieldObject("Minecraft", "field_71415_G", "inGameHasFocus", mc) && (double)f < 0.10000000000000001D && !entityplayer.isJumping) {
				//Modchu_Debug.mDebug("motionResetFlag = true");
				modelData.setCapsValue(modelData.caps_motionResetFlag, true);
				entityplayer.motionX = 0.0D;
				entityplayer.motionZ = 0.0D;
				modelData.setCapsValue(modelData.caps_motionSetFlag, false);
			} else {
				if (modelData.getCapsValueBoolean(modelData.caps_motionSetFlag)) {
					if (modelData.getCapsValueBoolean(modelData.caps_mushroomConfusionLeft)) {
						if (entityplayer.motionX > 0.0D) entityplayer.motionX = -entityplayer.motionX - 0.1D;
						//Modchu_Debug.mDebug("mushroomConfusionLeft "+motionX);
					}
					if (modelData.getCapsValueBoolean(modelData.caps_mushroomConfusionRight)) {
						if (entityplayer.motionX < 0.0D) entityplayer.motionX = -entityplayer.motionX + 0.4D;
						//Modchu_Debug.mDebug("mushroomConfusionRight "+motionX);
					}
					if (modelData.getCapsValueBoolean(modelData.caps_mushroomConfusionFront)) {
						if (entityplayer.motionZ > 0.0D) entityplayer.motionZ = -entityplayer.motionZ - 0.1D;
						//Modchu_Debug.mDebug("mushroomConfusionFront "+motionZ);
					}
					if (modelData.getCapsValueBoolean(modelData.caps_mushroomConfusionBack)) {
						if (entityplayer.motionZ < 0.0D) entityplayer.motionZ = -entityplayer.motionZ + 0.4D;
						//Modchu_Debug.mDebug("mushroomConfusionBack "+motionZ);
					}
				}
			}
		}
	}

	private static void mushroomConfusion1(EntityPlayer entityplayer, PFLM_ModelData modelData) {
		boolean forward;
		boolean back;
		boolean left;
		boolean right;
		KeyBinding key1 = null;
		KeyBinding key2 = null;
		KeyBinding key3 = null;
		KeyBinding key4 = null;
		GameSettings gameSettings = (GameSettings) Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mc);
		switch (modelData.getCapsValueInt(modelData.caps_mushroomConfusionType)) {
		case 1:
			key1 = gameSettings.keyBindForward;
			key2 = gameSettings.keyBindBack;
			key3 = gameSettings.keyBindLeft;
			key4 = gameSettings.keyBindRight;
			break;
		case 2:
			key2 = gameSettings.keyBindForward;
			key3 = gameSettings.keyBindBack;
			key4 = gameSettings.keyBindLeft;
			key1 = gameSettings.keyBindRight;
			break;
		case 3:
			key2 = gameSettings.keyBindForward;
			key3 = gameSettings.keyBindBack;
			key1 = gameSettings.keyBindLeft;
			key4 = gameSettings.keyBindRight;
			break;
		case 4:
			key4 = gameSettings.keyBindForward;
			key1 = gameSettings.keyBindBack;
			key2 = gameSettings.keyBindLeft;
			key3 = gameSettings.keyBindRight;
			break;
		}
//-@-b173
		if (modelData.getCapsValueBoolean(modelData.caps_mushroomBack)) {
			back = Keyboard.isKeyDown(key2.keyCode);
			if (!back) modelData.setCapsValue(modelData.caps_mushroomBack, false);
			key2.pressed = false;
		} else {
			key2.pressed = Keyboard.isKeyDown(key2.keyCode);
			back = key2.pressed;
		}
		if (modelData.getCapsValueBoolean(modelData.caps_mushroomForward)) {
			forward = Keyboard.isKeyDown(key1.keyCode);
			if (!forward) modelData.setCapsValue(modelData.caps_mushroomForward, false);
			key1.pressed = false;
		} else {
			key1.pressed = Keyboard.isKeyDown(key1.keyCode);
			forward = key1.pressed;
		}
		if (modelData.getCapsValueBoolean(modelData.caps_mushroomLeft)) {
			left = Keyboard.isKeyDown(key3.keyCode);
			if (!forward) modelData.setCapsValue(modelData.caps_mushroomLeft, false);
			key3.pressed = false;
		} else {
			key3.pressed = Keyboard.isKeyDown(key3.keyCode);
			left = key3.pressed;
		}
		if (modelData.getCapsValueBoolean(modelData.caps_mushroomRight)) {
			right = Keyboard.isKeyDown(key4.keyCode);
			if (!forward) modelData.setCapsValue(modelData.caps_mushroomRight, false);
			key4.pressed = false;
		} else {
			key4.pressed = Keyboard.isKeyDown(key4.keyCode);
			right = key4.pressed;
		}
		key1.pressed = back;
		key2.pressed = forward;
		key4.pressed = left;
		key3.pressed = right;
		//Modchu_Debug.dDebug("keyBindForward="+key1.pressed);
		//Modchu_Debug.dDebug("keyBindBack="+key2.pressed, 1);
		//Modchu_Debug.dDebug("forward="+forward, 2);
		//Modchu_Debug.dDebug("back="+back, 3);
		if (key1.pressed
				&& key2.pressTime > 0) {
			modelData.setCapsValue(modelData.caps_mushroomBack, true);
			modelData.setCapsValue(modelData.caps_mushroomForward, false);
			return;
		}
		modelData.setCapsValue(modelData.caps_mushroomBack, false);
		if (key2.pressed
				&& key1.pressTime > 0) {
			modelData.setCapsValue(modelData.caps_mushroomForward, true);
			modelData.setCapsValue(modelData.caps_mushroomBack, false);
			return;
		}
		modelData.setCapsValue(modelData.caps_mushroomForward, false);
		if (key3.pressed
				&& key4.pressTime > 0) {
			modelData.setCapsValue(modelData.caps_mushroomRight, true);
			modelData.setCapsValue(modelData.caps_mushroomLeft, false);
			return;
		}
		modelData.setCapsValue(modelData.caps_mushroomRight, false);
		if (key4.pressed
				&& key3.pressTime > 0) {
			modelData.setCapsValue(modelData.caps_mushroomLeft, true);
			modelData.setCapsValue(modelData.caps_mushroomRight, false);
			return;
		}
		modelData.setCapsValue(modelData.caps_mushroomLeft, false);
//@-@b173
/*//b173delete
		gameSettings.keyBindBack = key1;
		gameSettings.keyBindForward = key2;
		gameSettings.keyBindRight = key3;
		gameSettings.keyBindLeft = key4;
*///b173delete
	}

    public static void changeModel(EntityPlayer entityplayer) {
    	if (entityplayer != null) ;else entityplayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	if (entityReplaceFlag
    			&& data != null
    			&& data.getCapsValueBoolean(data.caps_isPlayer)) {
    		if (isPlayerAPI
    				&& !isPlayerAPIDebug
    				&& entityplayer != null) Modchu_Reflect.invokeMethod("PFLM_PlayerBase", "init", entityplayer);
    		else if (!isPlayerAPI
    				&& entityplayer != null) Modchu_Reflect.invokeMethod("PFLM_EntityPlayerSP", "init", entityplayer);
    	}
    }

    public static void changeColor(EntityPlayer entityplayer) {
    	if (entityplayer != null) ;else entityplayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	if (data != null
    			&& data.getCapsValueBoolean(data.caps_isPlayer)) {
    		data.setCapsValue(data.caps_changeColor, entityplayer);
    		//Modchu_Debug.lDebug("changeColor");
    	} else {
    		//Modchu_Debug.lDebug("changeColor out.entityplayer != null ?"+(entityplayer != null));
    		//Modchu_Debug.lDebug("changeColor out.data != null ?"+(data != null));
    		//if (data != null) Modchu_Debug.lDebug("changeColor out.data.getCapsValueBoolean(data.caps_isPlayer) ?"+(data.getCapsValueBoolean(data.caps_isPlayer)));
    	}
    }

    public static void changeColor(PFLM_EntityPlayerDummy dummy) {
    	if (PFLM_RenderPlayerDummyMaster.modelData != null
    			&& PFLM_RenderPlayerDummyMaster.modelData.modelMain != null
    			&& PFLM_RenderPlayerDummyMaster.modelData.modelMain.model instanceof MultiModelBaseBiped) ;else return;
    	((MultiModelBaseBiped) PFLM_RenderPlayerDummyMaster.modelData.modelMain.model).changeColor(PFLM_RenderPlayerDummyMaster.modelData);
    	if (PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelInner instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelInner).changeColor(PFLM_RenderPlayerDummyMaster.modelData);
    	if (PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelOuter instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelOuter).changeColor(PFLM_RenderPlayerDummyMaster.modelData);
    }

    private void sitSleepResetCheck() {
    	EntityPlayer entityplayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (entityplayer != null) {
    		PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    		if (data != null) PFLM_ModelDataMaster.instance.sitSleepResetCheck(data, entityplayer);
    	}
    }

    public void setPosition(double x, double y, double z) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (thePlayer != null
    			&& thePlayer.worldObj != null
    			&& !isMulti) {
    	} else return;
    	thePlayer.setPosition(x, y, z);
    }

    public static void setPositionCorrection(double x, double y, double z) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (thePlayer != null
    			&& thePlayer.worldObj != null
    			&& !isMulti) {
    	} else return;
//-@-b166
    	if (isPlayerAPI
    			&& !isPlayerAPIDebug) {
    		Modchu_Reflect.invokeMethod("PFLM_EntityPlayer", "setPositionCorrection", new Class[]{ double.class, double.class, double.class }, thePlayer, new Object[]{ x, y, z });
    		//PFLM_EntityPlayer.thePlayer.setPositionCorrection(x, y, z);
    	} else {
/*
    		if (isSSP) {
    			Object ret = Modchu_Reflect.invokeMethod(pflm_entityPlayerSP2, "setPositionCorrection", new Class[]{double.class, double.class, double.class}, thePlayer, x, y, z);
    			//((PFLM_EntityPlayerSP2) thePlayer).setPositionCorrection(x, y, z);
    		} else
*/
//@-@b166
    		Modchu_Reflect.invokeMethod("PFLM_EntityPlayerSP", "setPositionCorrection", new Class[]{ double.class, double.class, double.class }, thePlayer, new Object[]{ x, y, z });
    	/*b166//*/}
    }

    public static boolean gotchaNullCheck() {
//-@-125
    	return true;
//@-@125
/*//125delete
    	if (!entityReplaceFlag
    			| isMulti) return true;
    	if (thePlayer != null
    			&& thePlayer.worldObj != null
    			&& !(currentScreen instanceof GuiMainMenu)) {
        } else return false;
//-@-b166
    	if (isPlayerAPI
				&& !isPlayerAPIDebug) {
    		if (!PFLM_EntityPlayer.isInstance(thePlayer)) {
    			Modchu_Debug.mDebug("playerControllerReplace2");
    			playerControllerReplace();
    		}
    	} else {
//@-@b166
    		if (!(thePlayer instanceof PFLM_EntityPlayerSP)) {
    			Modchu_Debug.mDebug("playerControllerReplace3");
    			playerControllerReplace();
    		}
//-@-b166
    	}
//@-@b166
    	return true;
*///125delete
    }

    public static String getArmorName(String s) {
    	return getArmorName(s, 0);
    }

    public static String getArmorName(String s, int i) {
    	if (s == null) return "";
    	String s1 = s;
    	Object ltb = mod_Modchu_ModchuLib.modchu_Main.getTextureBox(s);
    	if (ltb != null
    			&& mod_Modchu_ModchuLib.modchu_Main.getTextureBoxHasArmor(ltb)) {
    		//Modchu_Debug.mDebug("getArmorName getTextureBoxHasArmor true s1="+s1);
    	} else {
    		//Modchu_Debug.mDebug("getArmorName getTextureBoxHasArmor false s1="+s1);
    		s1 = mod_Modchu_ModchuLib.modchu_Main.getModelSpecificationArmorPackege(s);
    		if (s1 != null) return s1;
    		boolean flag = specificationArmorCheckBoolean(s);
    		s1 = specificationArmorCheck(s);
    		if (!flag) {
    			if (i == 1) {
    				s1 = s1 != null ? s1.indexOf("_Biped") > -1 ? "_Biped" : "erasearmor" : "erasearmor";
    			} else {
    				s1 = s1 != null ? s1.indexOf("_Biped") > -1 ? "_Biped" : "default" : "default";
    			}
    		} else {
    			s1 = i == 1 ? "erasearmor" : "default";
    		}
    	}
    	//Modchu_Debug.mDebug("getArmorName s1="+s1+" i="+i);
    	return s1;
    }

    public static String specificationArmorCheck(String s) {
    	s = mod_Modchu_ModchuLib.modchu_Main.lastIndexProcessing(s, "_");
    	String[] cheackModelName = specificationArmorCheckModelName();
    	boolean flag = false;
    	for (int i2 = 0 ; i2 < cheackModelName.length ; i2++) {
    		if (s.startsWith(cheackModelName[i2])) {
    			s = "erasearmor";
    		}
    	}
    	return s;
    }

    public static boolean specificationArmorCheckBoolean(String s) {
    	s = mod_Modchu_ModchuLib.modchu_Main.lastIndexProcessing(s, "_");
    	String[] cheackModelName = specificationArmorCheckModelName();
    	boolean flag = false;
    	for (int i2 = 0 ; i2 < cheackModelName.length ; i2++) {
    		if (s.startsWith(cheackModelName[i2])) {
    			flag = true;
    		}
    	}
    	return flag;
    }

    public static String[] specificationArmorCheckModelName() {
    	return new String[] {
    			"Elsa"
    	};
    }

    public static void setSkinUrl(String s) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (thePlayer != null
    			&& thePlayer.worldObj != null
    			&& !isMulti) {
//-@-b166
    		if (isPlayerAPI
    				&& !isPlayerAPIDebug) {
    			Modchu_Reflect.setFieldObject("PFLM_PlayerBase", "skinUrl", Modchu_Reflect.getFieldObject("PFLM_PlayerBase", "player", thePlayer), s);
    		} else {
/*
    			if (isSSP) {
    				Modchu_Reflect.setFieldObject(Modchu_Reflect.getField(pflm_entityPlayerSP2, "skinUrl"), null, s);
    				//((PFLM_EntityPlayerSP2) thePlayer).skinUrl = s;
    			} else
*/
//@-@b166
    			Modchu_Reflect.setFieldObject("PFLM_EntityPlayerSP", "skinUrl", thePlayer, s);
    		/*b166//*/}
    	}
    }

    public static void setTexture(Object t) {
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (thePlayer != null
    			&& thePlayer.worldObj != null
    			&& !isMulti) {
//-@-b166
    		if (isPlayerAPI
    				&& !isPlayerAPIDebug) {
    			Modchu_Reflect.invokeMethod("PFLM_PlayerBase", "setPlayerTexture", new Class[]{String.class}, thePlayer, t);
    		} else {
/*
    			if (isSSP) {
        			Object ret = Modchu_Reflect.invokeMethod(pflm_entityPlayerSP2, "setPlayerTexture", new Class[]{String.class}, thePlayer, s);
    				//((PFLM_EntityPlayerSP2) thePlayer).setPlayerTexture(s);
    			} else
*/
//@-@b166
    			Modchu_Reflect.invokeMethod("PFLM_EntityPlayerSP", "setPlayerTexture", new Class[]{String.class}, thePlayer, t);
    		/*b166//*/}
    	}
    }

    public static void resetHeight() {
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (thePlayer != null
    			&& thePlayer.worldObj != null
    			&& !isMulti) {
    		Modchu_Reflect.invokeMethod(EntityPlayer.class, "func_71061_d_", "resetHeight", thePlayer);
    	}
    }

    public static void clearDataMap() {
/*
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	RenderGlobal renderGlobal = (RenderGlobal) Modchu_Reflect.getFieldObject("Minecraft", "field_6323_f", "renderGlobal", mc);
    	if (renderGlobal != null
    			&& thePlayer != null) {
    		renderGlobal.onEntityDestroy(thePlayer);
    		renderGlobal.onEntityCreate(thePlayer);
    	}
*/
/*
    		if (isOlddays) {
    			Object ret = Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "clearPlayers");
    			//PFLM_RenderPlayer2.clearDataMap();
    		} else
*/
    	PFLM_ModelDataMaster.instance.clearDataMap();
    }

    public static void removeDataMap() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	removeDataMap(thePlayer);
    }

    public static void removeDataMap(EntityPlayer entityPlayer) {
    	PFLM_ModelDataMaster.instance.removeDataMap(entityPlayer);
    }

    public static void setSize(float f1, float f2) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (thePlayer != null
    			&& thePlayer.worldObj != null
    			&& !isMulti) {
    		Modchu_Reflect.invokeMethod(Entity.class, "func_70105_a", "setSize", new Class[]{ float.class, float.class }, thePlayer, new Object[]{ f1, f2 });
    	}
    }

    public static float getModelScale() {
    	return getModelScale(null);
    }

    public static float getModelScale(Entity entity) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (entity != null) ;else entity = thePlayer;
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).getModelScale(data);
    	return 0.9375F;
    }

    public static float getWidth() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float f = 0.6F;
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129
    			&& isOlddays) {
    		Object[] obj = (Object[]) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getModelBasicOrig");
    		return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getWidth", obj[0]));
    		//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getWidth();
    	}
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return f;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).getWidth(data);
    	return f;
    }

    public static float getHeight() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float f = 1.8F;
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129
    			&& isOlddays) {
    		Object[] obj = (Object[]) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getModelBasicOrig");
    		return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getHeight", obj[0]));
    		//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getHeight();
    	}
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return f;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).getHeight(data);
    	return f;
    }

    public static float getyOffset() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float f = 1.62F;
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129
    			&& isOlddays) {
    		Object[] obj = (Object[]) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getModelBasicOrig");
    		return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getyOffset", obj[0]));
    		//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getyOffset();
    	}
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return f;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).getyOffset(data);
    	return f;
    }

    public static float getRidingWidth() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float f = 0.6F;
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129
    			&& isOlddays) {
    		Object[] obj = (Object[]) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getModelBasicOrig");
    		return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getRidingWidth", obj[0]));
    		//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getRidingWidth();
    	}
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return f;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).getRidingWidth(data);
    	return f;
    }

    public static float getRidingHeight() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float f = 1.8F;
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129
    			&& isOlddays) {
    		Object[] obj = (Object[]) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getModelBasicOrig");
    		return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getRidingHeight", obj[0]));
    		//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getRidingHeight();
    	}
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return f;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).getRidingHeight(data);
    	return f;
    }

    public static float getRidingyOffset() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float f = 1.62F;
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129
    			&& isOlddays) {
    		Object[] obj = (Object[]) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getModelBasicOrig");
    		return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getRidingyOffset", obj[0]));
    		//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getRidingyOffset();
    	}
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return f;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).getRidingyOffset(data);
    	return f;
    }

    public static double getMountedYOffset() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	double d = 0.75D;
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129
    			&& isOlddays) {
    		Object[] obj = (Object[]) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getModelBasicOrig");
    		return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_RenderPlayer2", "getMountedYOffset", obj[0]));
    		//return PFLM_RenderPlayer2.getModelBasicOrig()[0].getMountedYOffset();
    	}
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return d;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).getMountedYOffset(data);
    	return d;
    }

    public static boolean getIsRiding() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return false;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).isRiding;
    	return false;
    }

    public static float getPhysical_Hammer() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return Physical_Hammer;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).Physical_Hammer((Modchu_IModelCaps) data);
    	return Physical_Hammer;
    }

    public static float ridingViewCorrection() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return 0.0F;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) textureModel).ridingViewCorrection(data);
    	return 0.0F;
    }

    public static boolean bipedCheck() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (thePlayer != null) ;else return false;
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return false;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return BipedClass.isInstance(textureModel);
    	return false;
    }

    public static float getOnGround() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return 0.0F;
    	Object textureModel = data.modelMain.model;
    	if (textureModel != null
    			&& textureModel instanceof MultiModelBaseBiped) return (Float) ((MultiModelBaseBiped) textureModel).getCapsValue(((MultiModelBaseBiped) textureModel).caps_onGround, (MMM_IModelCaps) data);
    	return 0.0F;
    }

    public static float getEyeHeight() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float f = 0.12F;
    	if (thePlayer != null
    			&& thePlayer.worldObj != null
    			&& !isMulti) {
//-@-125
    		if (isSSP) {
    			if (thePlayer != null) return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_EntityPlayerSP2", "getEyeHeight", thePlayer));
    			//return ((PFLM_EntityPlayerSP2) thePlayer).getEyeHeight();
    		} else
//@-@125
    			if (thePlayer != null) {
//-@-b166
    				if (isPlayerAPI
    						&& !isPlayerAPIDebug) {
    					Object o = Modchu_Reflect.getFieldObject("PFLM_EntityPlayer", "thePlayer");
    					if (o != null) return (Float) Modchu_Reflect.invokeMethod(EntityPlayer.class, "getEyeHeight", o);
    				} else {
//@-@b166
    					if (thePlayer != null) return Float.valueOf((String) Modchu_Reflect.invokeMethod("PFLM_EntityPlayerSP2", "getEyeHeight", thePlayer));
//-@-b166
    				}
//@-@b166
    			}
    	}
    	if (mc != null
    			&& thePlayer != null) return thePlayer.getEyeHeight();
    	return 0.12F;
    }

    public static String getUsername() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (mc != null
    			&& thePlayer != null) return thePlayer.username;
    	return null;
    }

    public static boolean getChangeModelFlag() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) return modelData.getCapsValueBoolean(modelData.caps_changeModelFlag);
    	return false;
    }

    public static void setChangeModelFlag(boolean b) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	modelData.setCapsValue(modelData.caps_changeModelFlag, b);
    }

    public static int getHandednessMode(Entity entity) {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);
    	if (modelData != null) return modelData.getCapsValueInt(modelData.caps_dominantArm);
    	return 0;
    }

    public static void setHandednessMode(Entity entity, int i) {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);
    	modelData.setCapsValue(modelData.caps_dominantArm, i);
    }

    public static boolean getShortcutKeysAction() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) return modelData.getCapsValueBoolean(modelData.caps_shortcutKeysAction);
    	return false;
    }

    public static void setShortcutKeysAction(boolean b) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) modelData.setCapsValue(modelData.caps_shortcutKeysAction, b);
    	return;
    }

    public static int getRunActionNumber() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) return modelData.getCapsValueInt(modelData.caps_runActionNumber);
    	return -1;
    }

    public static void setRunActionNumber(int i) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) modelData.setCapsValue(modelData.caps_runActionNumber, i);
    	return;
    }

    public static int getActionReleaseNumber() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) return modelData.getCapsValueInt(modelData.caps_actionReleaseNumber);
    	return -1;
    }

    public static void setActionReleaseNumber(int i) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) modelData.setCapsValue(modelData.caps_actionReleaseNumber, i);
    	return;
    }

    public static Object getModel(int i) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) return modelData.getCapsValue((MultiModelBaseBiped) null, modelData.caps_model, i);
    	return null;
    }

    public static Object getModel(EntityPlayer entityPlayer, int i) {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityPlayer);
    	if (modelData != null) return modelData.getCapsValue((MultiModelBaseBiped) null, modelData.caps_model, i);
    	return null;
    }

    public static void setShortcutKeysActionInitFlag(boolean b) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) modelData.setCapsValue(modelData.caps_shortcutKeysActionInitFlag, b);
    	return;
    }

    public static boolean getIsSitting() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) return modelData.getCapsValueBoolean(modelData.caps_isSitting);
    	Modchu_Debug.mDebug("getIsSitting() modelData == null!!");
    	return false;
    }

    public static void setIsSitting(boolean b) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) modelData.setCapsValue(modelData.caps_isSitting, b);
    	return;
    }

    public static boolean getIsSleeping() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) return modelData.getCapsValueBoolean(modelData.caps_isSleeping);
    	return false;
    }

    public static void setIsSleeping(boolean b) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (modelData != null) modelData.setCapsValue(modelData.caps_isSleeping, b);
    	return;
    }

    public static String[] setTexturePackege(String textureName, String textureArmorName, int color, int prevNextNormal, boolean armorOnly) {
    	if (!armorOnly) {
    		String s = textureName;
    		switch(prevNextNormal) {
    		case 0:
    			s = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetPrevPackege(textureName, color);
    			break;
    		case 1:
    			s = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetNextPackege(textureName, color);
    			break;
    		case 2:
    			break;
    		}
    		if (s != null
    				&& !s.isEmpty()) ;else return null;
    		textureName = s;
    		textureArmorName = textureName;
    		String s1 = getArmorName(textureArmorName, prevNextNormal);
    		if (s1 != null
    				&& !s1.isEmpty()) textureArmorName = s1;
    	} else {
    		textureArmorName = prevNextNormal == 0 ? mod_Modchu_ModchuLib.modchu_Main.textureManagerGetPrevArmorPackege(textureArmorName) : mod_Modchu_ModchuLib.modchu_Main.textureManagerGetNextArmorPackege(textureArmorName);
    	}
    	return new String[]{ textureName, textureArmorName };
    }

    public static int getMaidColor() {
    	return maidColor;
    }

    public static void setMaidColor(int i) {
    	maidColor = i & 0xf;
    }

    public static void setMaidColor(EntityPlayer entityplayer, int i) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (entityplayer != null) ;else entityplayer = thePlayer;
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	if (data != null) {
    		data.setCapsValue(data.caps_maidColor, i);
    	}
    }

    public static void setTextureName(String s) {
    	textureName = s;
    }

    public static void setTextureArmorName(String s) {
    	textureArmorName = s;
    }

    public static boolean getFlipHorizontal() {
    	return PFLM_ItemRendererMaster.flipHorizontal;
    }

    public static void setFlipHorizontal(boolean b) {
    	PFLM_ItemRendererMaster.flipHorizontal = b;
    	return;
    }

    public static void setLeftHandedness(boolean b) {
    	PFLM_ItemRendererMaster.leftHandedness = b;
    	return;
    }

    public static boolean getLeftHandedness() {
    	return PFLM_ItemRendererMaster.leftHandedness;
    }

    public void customModelCfgReLoad() {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData data = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
    	if (data != null) ;else return;
    	Object[] textureModel = {
    			data.modelMain.model,
    			data.modelFATT.modelInner,
    			data.modelFATT.modelOuter
    	};
    	float[] f1;
    	for(int i = 0; i < textureModel.length; i++) {
    		if (textureModel != null
    				&& textureModel[i] instanceof MultiModelCustom) {
    			f1 = mod_Modchu_ModchuLib.modchu_Main.getArmorModelsSize(textureModel[i]);
    			if (f1 != null) ((MultiModelCustom) textureModel[i]).customModel.init(null, null, f1[0], f1[1]);
    		}
    	}
    }

    public static void loadcfg() {
		// cfg読み込み
		if (cfgdir.exists()) {
			if (!mainCfgfile.exists()) {
				// cfgファイルが無い = 新規作成
				String s[] = {
						"AlphaBlend=true",
						"Physical_HurtSound=damage.hit", "isPlayerForm=true",
						"isPlayerAPIDebug=false",
						"isClearWater=false",
						"watherFog=0.1F", "watherFog2=0.05F", "waterStillLightOpacity=3",
						"waterMovingLightOpacity=3",
						"transparency=1.0F",
						"textureSavedir=/output/", "guiMultiPngSaveButton=true",
						"changeModeButton=true", "isRenderName=true",
						"waitTime=600",
						"multiAutochangeMode=true", "skirtFloats=false", "skirtFloatsVolume=1.0F",
						"othersPlayerWaitTime=600", "versionCheck=true",
						"useScaleChange=true", "mushroomConfusion=true", "entityReplace=false",
						"useAddChatMessage=true"
/*//125delete
						, "Physical_BurningPlayer=0", "Physical_MeltingPlayer=0", "Physical_Hammer=1.0F",
						"Physical_Undead=false", "isVoidFog=true", "isFog=true",
						"isDimming=true", "lavaFog=2.0F", "isMouseOverMinecraftMenu=true",
						"isSwapGuiSelectWorld=false", "isModelSize=false"
*///125delete
				};
				PFLM_Config.writerConfig(mainCfgfile, s);
			} else {
				// cfgファイルがある
				AlphaBlend = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "AlphaBlend", AlphaBlend)).toString());
				Physical_HurtSound = (PFLM_Config.loadConfig(mainCfgfile, "Physical_HurtSound", Physical_HurtSound)).toString();
				isPlayerForm = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isPlayerForm", isPlayerForm)).toString());
				isPlayerAPIDebug = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isPlayerAPIDebug", isPlayerAPIDebug)).toString());
				isClearWater = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isClearWater", isClearWater)).toString());
				watherFog = Float.valueOf((PFLM_Config.loadConfig(mainCfgfile, "watherFog", watherFog)).toString());
				watherFog2 = Float.valueOf((PFLM_Config.loadConfig(mainCfgfile, "watherFog2", watherFog2)).toString());
				waterStillLightOpacity = Integer.valueOf((PFLM_Config.loadConfig(mainCfgfile, "waterStillLightOpacity", waterStillLightOpacity)).toString());
				waterMovingLightOpacity = Integer.valueOf((PFLM_Config.loadConfig(mainCfgfile, "waterMovingLightOpacity", waterMovingLightOpacity)).toString());
				transparency = Float.valueOf((PFLM_Config.loadConfig(mainCfgfile, "transparency", transparency)).toString());
				textureSavedir = (PFLM_Config.loadConfig(mainCfgfile, "textureSavedir", textureSavedir)).toString();
				guiMultiPngSaveButton = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "guiMultiPngSaveButton", guiMultiPngSaveButton)).toString());
				changeModeButton = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "changeModeButton", changeModeButton)).toString());
				isRenderName = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isRenderName", isRenderName)).toString());
				waitTime = Integer.valueOf((PFLM_Config.loadConfig(mainCfgfile, "waitTime", waitTime)).toString());
				multiAutochangeMode = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "multiAutochangeMode", multiAutochangeMode)).toString());
				othersPlayerWaitTime = Integer.valueOf((PFLM_Config.loadConfig(mainCfgfile, "othersPlayerWaitTime", othersPlayerWaitTime)).toString());
				versionCheck = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "versionCheck", versionCheck)).toString());
				useScaleChange = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "useScaleChange", useScaleChange)).toString());
				mushroomConfusion = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "mushroomConfusion", mushroomConfusion)).toString());
				entityReplace = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "entityReplace", entityReplace)).toString());
				useAddChatMessage = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "useAddChatMessage", useAddChatMessage)).toString());
/*//125delete
				Physical_BurningPlayer = Integer.valueOf((PFLM_Config.loadConfig(mainCfgfile, "Physical_BurningPlayer", Physical_BurningPlayer)).toString());
				Physical_MeltingPlayer = Integer.valueOf((PFLM_Config.loadConfig(mainCfgfile, "Physical_MeltingPlayer", Physical_MeltingPlayer)).toString());
				Physical_Hammer = Float.valueOf((PFLM_Config.loadConfig(mainCfgfile, "Physical_Hammer", Physical_Hammer)).toString());
				Physical_Undead = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "Physical_Undead", Physical_Undead)).toString());
				isVoidFog = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isVoidFog", isVoidFog)).toString());
				isFog = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isFog", isFog)).toString());
				isDimming = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isDimming", isDimming)).toString());
				lavaFog = Float.valueOf((PFLM_Config.loadConfig(mainCfgfile, "lavaFog", lavaFog)).toString());
				isMouseOverMinecraftMenu = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isMouseOverMinecraftMenu", isMouseOverMinecraftMenu)).toString());
				isSwapGuiSelectWorld = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isSwapGuiSelectWorld", isSwapGuiSelectWorld)).toString());
				isModelSize = Boolean.valueOf((PFLM_Config.loadConfig(mainCfgfile, "isModelSize", isModelSize)).toString());
*///125delete
				cfgMaxMinCheck();
				String k[] = {
						"AlphaBlend",
						"Physical_HurtSound", "isPlayerForm",
						"isPlayerAPIDebug",
						"isClearWater",
						"watherFog", "watherFog2", "waterStillLightOpacity",
						"waterMovingLightOpacity",
						"transparency",
						"textureSavedir", "guiMultiPngSaveButton",
						"changeModeButton", "isRenderName",
						"waitTime",
						"multiAutochangeMode",
						"othersPlayerWaitTime", "versionCheck",
						"useScaleChange", "mushroomConfusion", "entityReplace",
						"useAddChatMessage"
/*//125delete
						, "Physical_BurningPlayer", "Physical_MeltingPlayer", "Physical_Hammer",
						"Physical_Undead", "isVoidFog", "isFog",
						"isDimming", "lavaFog", "isMouseOverMinecraftMenu",
						"isSwapGuiSelectWorld", "isModelSize"
*///125delete
				};
				String k1[] = {
						""+AlphaBlend,
						""+Physical_HurtSound, ""+isPlayerForm,
						""+isPlayerAPIDebug,
						""+isClearWater,
						""+watherFog, ""+watherFog2, ""+waterStillLightOpacity,
						""+waterMovingLightOpacity,
						""+transparency,
						""+textureSavedir, ""+guiMultiPngSaveButton,
						""+changeModeButton, ""+isRenderName,
						""+waitTime,
						""+multiAutochangeMode,
						""+othersPlayerWaitTime, ""+versionCheck,
						""+useScaleChange, ""+mushroomConfusion, ""+entityReplace,
						""+useAddChatMessage
/*//125delete
						, ""+Physical_BurningPlayer, ""+Physical_MeltingPlayer, ""+Physical_Hammer,
						""+Physical_Undead, ""+isVoidFog, ""+isFog,
						""+isDimming, ""+lavaFog, ""+isMouseOverMinecraftMenu,
						""+isSwapGuiSelectWorld, ""+isModelSize
*///125delete
				};
				PFLM_Config.writerSupplementConfig(mainCfgfile, k, k1);
				checkEntityReplaceNecessity();
			}
		}
	}

	private static void checkEntityReplaceNecessity() {
		if (entityReplace
				&& (Physical_BurningPlayer != 0
				| Physical_MeltingPlayer != 0
				| Physical_Hammer != 1.0F
				| Physical_Undead
				| !Physical_HurtSound.equalsIgnoreCase("damage.hit")
/*//125delete
				| isSwapGuiSelectWorld
*///125delete
				| isModelSize)) entityReplaceFlag = true;
	}

	public static void cfgMaxMinCheck() {
		if (watherFog < 0.0F) watherFog = 0.0F;
		if (watherFog > 1.0F) watherFog = 1.0F;
		if (watherFog2 < 0.0F) watherFog2 = 0.0F;
		if (watherFog2 > 1.0F) watherFog2 = 1.0F;
		if (waterStillLightOpacity < 0) waterStillLightOpacity = 0;
		if (waterStillLightOpacity > 255) waterStillLightOpacity = 255;
		if (waterMovingLightOpacity < 0) waterMovingLightOpacity = 0;
		if (waterMovingLightOpacity > 255) waterMovingLightOpacity = 255;
		if (lavaFog < 0.0F) lavaFog = 0.0F;
		if (lavaFog > 2.0F) lavaFog = 2.0F;
		if (transparency < 0.0F) transparency = 0.0F;
		if (transparency > 1.0F) transparency = 1.0F;
	}

	public static void writerParamater() {
		//GUI設定ファイル書き込み
		String s[] = {
				"textureName="+textureName, "textureArmorName="+textureArmorName, "maidColor="+maidColor,
				"ModelScale="+PFLM_Gui.modelScale, "changeMode="+changeMode, "setModel="+PFLM_Gui.setModel,
				"setColor="+PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor), "setArmor="+PFLM_Gui.setArmor, "changeMode="+changeMode
		};
		PFLM_Config.writerConfig(cfgfile, s);
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
				textureArmorName = (PFLM_Config.loadConfig(cfgfile, "textureArmorName", textureArmorName)).toString();
				textureName = (PFLM_Config.loadConfig(cfgfile, "textureName", textureName)).toString();
				maidColor = Integer.valueOf((PFLM_Config.loadConfig(cfgfile, "maidColor", maidColor)).toString());
				PFLM_Gui.modelScale = Float.valueOf((PFLM_Config.loadConfig(cfgfile, "ModelScale", PFLM_Gui.modelScale)).toString());
				changeMode = Integer.valueOf((PFLM_Config.loadConfig(cfgfile, "changeMode", changeMode)).toString());
				PFLM_Gui.setModel = Integer.valueOf((PFLM_Config.loadConfig(cfgfile, "setModel", PFLM_Gui.setModel)).toString());
				PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, Integer.valueOf((PFLM_Config.loadConfig(cfgfile, "maidColor", maidColor)).toString()));
				PFLM_Gui.setArmor = Integer.valueOf((PFLM_Config.loadConfig(cfgfile, "setArmor", PFLM_Gui.setArmor)).toString());
				handednessMode = Integer.valueOf((PFLM_Config.loadConfig(cfgfile, "handednessMode", handednessMode)).toString());
				changeMode = Integer.valueOf((PFLM_Config.loadConfig(cfgfile, "changeMode", changeMode)).toString());
				PFLM_Config.loadConfig(showModelList, cfgfile);
				if (handednessMode < -1) handednessMode = -1;
				if (handednessMode > 1) handednessMode = 1;
			}
		}
	}

	public static void saveParamater() {
		// Gui設定項目をcfgファイルに保存
		String k[] = {
				"textureName", "textureArmorName", "maidColor", "ModelScale", "setModel",
				"setColor", "setArmor", "handednessMode", "changeMode"
		};
		String k1[] = {
				""+textureName, ""+textureArmorName, ""+maidColor, ""+PFLM_Gui.modelScale, ""+PFLM_Gui.setModel,
				""+PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor), ""+PFLM_Gui.setArmor, ""+handednessMode, ""+changeMode
		};
		PFLM_Config.saveParamater(cfgfile, k, k1);
	}

	public static void writerTextureList() {
		//textureListファイル 新規作成
		String s[] = {
				"autoUpdates=true",
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
				"LittleVocal_VUD1.Yukari1_VUD1.0_SR2", "VOICEROID.Yukari_Yukari", "VOICEROID.Yukari_Yukari", "VOICEROID.YukariS0_Yukari", "VOICEROID.YukariS1_Yukari",
				"e11color_Elsa3", "e11under_Elsa3", "Catcher_Pawapro", "Batter_Pawapro", "b4under_Beverly4",
				"kimono_pl_Shion", "Sword_NM", "Ar_NM", "x16_QB", "Hituji",
				"Udonge_usagi", "neta_chu", "ColorVariation_chu", "NetaPetit_Petit", "CV_DressYukari",
				"e12color_Elsa3", "b14color_Beverly5", "e14color_Elsa4", "e14under_Elsa4", "b15color_Beverly6",
				"b15under_Beverly6", "default_Custom1", "e15color_Elsa5", "e15under_Elsa5", "b16color_Chloe2",
				"b16under_Chloe2", "b16color_Beverly7", "b16under_Beverly7", "jamBAND.Kana_SA", "jamBAND.Kanon_SA",
				"jamBAND.Maki_SA", "jamBAND.Rizumu_SA", "HiFM_Squirrel", "HiFM_Sheep", "HiFM_Rabbit",
				"HiFM_Fox", "HiFM_Dog", "HiFM_Cat", "HiFM_Bear"
		};
		PFLM_Config.writerModelList(s, textureListfile, textureList);
	}

	public static void loadTextureList() {
		// ModelList読み込み
		if (cfgdir.exists()) {
			if (!textureListfile.exists()) {
				// textureListファイルが無い = 新規作成
				writerTextureList();
			} else {
				// textureListファイルがある
				boolean b = PFLM_Config.loadList(textureListfile, textureList, "PlayerFormLittleMaidtextureList.cfg");
				if (!b) writerTextureList();
			}
		}
	}

	public static void writerOthersPlayerParamater() {
		//GUIOthersPlayer設定ファイル書き込み
		String s[] = {
				"othersTextureName="+othersTextureName, "othersTextureArmorName="+othersTextureArmorName, "othersMaidColor="+othersMaidColor,
				"othersModelScale="+othersModelScale
		};
		PFLM_Config.writerConfig(othersCfgfile, s);
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
				othersTextureName = (PFLM_Config.loadConfig(othersCfgfile, "othersTextureName", othersTextureName)).toString();
				othersTextureArmorName = (PFLM_Config.loadConfig(othersCfgfile, "othersTextureArmorName", othersTextureArmorName)).toString();
				othersMaidColor = Integer.valueOf((PFLM_Config.loadConfig(othersCfgfile, "othersMaidColor", othersMaidColor)).toString());
				othersModelScale = Float.valueOf((PFLM_Config.loadConfig(othersCfgfile, "othersModelScale", othersModelScale)).toString());
				PFLM_GuiOthersPlayer.changeMode = Integer.valueOf((PFLM_Config.loadConfig(othersCfgfile, "changeMode", PFLM_GuiOthersPlayer.changeMode)).toString());
				othersHandednessMode = Integer.valueOf((PFLM_Config.loadConfig(othersCfgfile, "othersHandednessMode", othersHandednessMode)).toString());
				PFLM_Config.loadConfigPlayerLocalData(playerLocalData, othersCfgfile);
				if (PFLM_GuiOthersPlayer.changeMode > PFLM_GuiOthersPlayer.changeModeMax) PFLM_GuiOthersPlayer.changeMode = 0;
				if (PFLM_GuiOthersPlayer.changeMode < 0) PFLM_GuiOthersPlayer.changeMode = PFLM_GuiOthersPlayer.changeModeMax;
			}
		}
	}

	public static void saveOthersPlayerParamater(boolean flag) {
		// GuiOthersPlayer設定項目をcfgファイルに保存
		String k[] = {
				"othersTextureName", "othersTextureArmorName", "othersMaidColor", "othersModelScale", "changeMode",
				"othersHandednessMode"
		};
		String k1[] = {
				""+othersTextureName, ""+othersTextureArmorName, ""+othersMaidColor, ""+othersModelScale, ""+PFLM_GuiOthersPlayer.changeMode,
				""+othersHandednessMode
		};
		PFLM_Config.saveOthersPlayerParamater(PFLM_GuiOthersPlayerIndividualCustomize.playerName, playerLocalData, othersCfgfile, k, k1, flag);
	}

	public static void removeOthersPlayerParamater(String s) {
		// GuiOthersPlayer設定から指定内容削除
		PFLM_Config.removeOthersPlayerParamater(othersCfgfile, s);
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
		PFLM_Config.saveParamater(shortcutKeysCfgfile, k, k1);
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
		PFLM_Config.writerConfig(shortcutKeysCfgfile, s);
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
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysTextureName[").append(i).append("]").toString(), k).toString();
					shortcutKeysTextureName[i] = s != null ? s: "default";
					k = shortcutKeysTextureArmorName[i] != null ? shortcutKeysTextureArmorName[i] : "";
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysTextureArmorName[").append(i).append("]").toString(), k).toString();
					shortcutKeysTextureArmorName[i] = s != null ? s: "default";
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysMaidColor[").append(i).append("]").toString(), shortcutKeysMaidColor[i]).toString();
					shortcutKeysMaidColor[i] = s != null ? Integer.valueOf(s) : 0;
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysChangeMode[").append(i).append("]").toString(), shortcutKeysChangeMode[i]).toString();
					shortcutKeysChangeMode[i] = s != null ? Integer.valueOf(s) : 0;
					if (shortcutKeysChangeMode[i] > PFLM_GuiKeyControls.changeModeMax) shortcutKeysChangeMode[i] = 0;
					if (shortcutKeysChangeMode[i] < 0) shortcutKeysChangeMode[i] = PFLM_GuiKeyControls.changeModeMax;
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysModelScale[").append(i).append("]").toString(), shortcutKeysModelScale[i]).toString();
					shortcutKeysModelScale[i] = s != null ? Float.valueOf(s) : 0.9375F;
					if (shortcutKeysModelScale[i] > 10.0F) shortcutKeysModelScale[i] = 10.0F;
					if (shortcutKeysModelScale[i] < 0.0F) shortcutKeysModelScale[i] = 0.0F;
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysUse[").append(i).append("]").toString(), shortcutKeysUse[i]).toString();
					shortcutKeysUse[i] = s != null ? Boolean.valueOf(s) : false;
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysPFLMModelsUse[").append(i).append("]").toString(), shortcutKeysPFLMModelsUse[i]).toString();
					shortcutKeysPFLMModelsUse[i] = s != null ? Boolean.valueOf(s) : false;
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysCtrlUse[").append(i).append("]").toString(), shortcutKeysCtrlUse[i]).toString();
					shortcutKeysCtrlUse[i] = s != null ? Boolean.valueOf(s) : false;
					s = PFLM_Config.loadConfig(shortcutKeysCfgfile, new StringBuilder().append("shortcutKeysShiftUse[").append(i).append("]").toString(), shortcutKeysShiftUse[i]).toString();
					shortcutKeysShiftUse[i] = s != null ? Boolean.valueOf(s) : false;
				}
			}
		}
	}

	public void modsLoaded()
	{
	}

	public void modsLoadedInit() {
		if (!loadInitFlag) {
			runtimeExceptionFlag = true;
			runtimeExceptionString = "mod_PFLM_PlayerFormLittleMaid-modsLoaded() loadInit error !";
			throw new RuntimeException("mod_PFLM_PlayerFormLittleMaid-modsLoaded() loadInit error !");
		}
		if (Modchu_Reflect.loadClass(mod_Modchu_ModchuLib.modchu_Main.getClassName("MultiModel")) != null) ;else {
			runtimeExceptionFlag = true;
			runtimeExceptionString = "MultiModel_DefaultModelSet not found !!";
		}
		int i1 = Modchu_Main.getVersionStringConversionInt(Modchu_Main.version);
		if (i1 < 507) {
			Modchu_Debug.lDebug("ModchuLib Version is old !! VersionInt="+i1);
			runtimeExceptionFlag = true;
			runtimeExceptionString = "ModchuLib Version is old !!";
		} else {
			Modchu_Debug.lDebug("ModchuLib VersionInt="+i1);
		}
		loadParamater();
		loadTextureList();
		loadOthersPlayerParamater();
		loadShortcutKeysParamater();
		if (versionCheck) startVersionCheckThread();
		//対応MOD導入チェック
		List list = ModLoader.getLoadedMods();
		int size = list.size();
		BaseMod mod = null;
		String name = null;
		for (int i = 0; i < size; i++)
		{
			mod = (BaseMod)list.get(i);
			name = mod.getClass().getSimpleName();
			if (name.equals("mod_ThirdPersonCamera")) {
				isThirdPersonCamera = true;
				Modchu_Debug.lDebug("mod_ThirdPersonCamera Check ok.");
			}
			else if (name.equals("mod_noBiomesX")) {
				isnoBiomesX = true;
				Modchu_Debug.lDebug("mod_noBiomesX Check ok.");
			}
			else if (name.equals("mod_SmartMoving")) {
				if (!isRelease()) {
					isSmartMoving = true;
					Modchu_Debug.lDebug("mod_SmartMoving Check ok.");
				}
			}
			else if (name.equals("mod_Aether")) {
				isAether = true;
				Modchu_Debug.lDebug("mod_Aether Check ok.");
			}
			else if (name.equals("mod_2DCraft")) {
				is2D = true;
				Modchu_Debug.lDebug("mod_2DCraft Check ok.");
			}
			else if (name.equals("mod_CCTV")) {
				isCCTV = true;
				Modchu_Debug.lDebug("mod_CCTV Check ok.");
			}
			else if (name.equals("mod_LMM_littleMaidMob")) {
				isLMM = true;
				Modchu_Debug.lDebug("mod_LMM_littleMaidMob Check ok.");
			}
		}
		//mod_SmartMovingMp Shaders MinecraftForgeなど対応クラス存在チェック
		String className1[] = {
				"mod_SmartMovingMp", "Shaders", "DynamicLights", "Shader", "EntityPlayerSP2",
				"RenderPlayer2", "ItemRendererHD"
		};
		String test2 = null;
		String s = null;
		for(int n = 0 ; n < className1.length ; n++){
			try {
				test2 = mod_Modchu_ModchuLib.modchu_Main.getClassName(className1[n]);
				//Modchu_Debug.mDebug("test2 = "+test2);
				test2 = ""+Class.forName(test2);
				Modchu_Debug.lDebug(test2 + " Check ok.");
				if(n == 0) isSmartMoving = true;
				if(n == 1) isShaders = true;
				if(n == 2) isDynamicLights = true;
				if(n == 3) {
					isShader = true;
					erpflmCheck = 7;
				}
				if(n == 4) {
					try {
						s = mod_Modchu_ModchuLib.modchu_Main.getClassName("EntityPlayerSP2");
						if (s != null) {
							Object o = Modchu_Reflect.getFieldObject(s, "armor", -1);
							if (o != null) isSSP = true;
						}
					} catch(Exception e) {
					}
				}
				if(n == 5) {
					try {
						Object o = Modchu_Reflect.getFieldObject(ItemRenderer.class, "olddays", -1);
						if (o != null) {
							isOlddays = true;
							Modchu_Debug.lDebug("ItemRenderer olddays Check ok.");
						} else {
							Modchu_Debug.lDebug("ItemRenderer olddays Check false.");
						}
					} catch(Exception e) {
					}
				}
				if(n == 6) isItemRendererHD = true;
			} catch (ClassNotFoundException e) {
			}
		}
    	if (!isThirdPersonCamera) {
			Modchu_Debug.lDebug("isThirdPersonCamera false.");
		}
		if (!isnoBiomesX) {
			Modchu_Debug.lDebug("isnoBiomesX false.");
		}
		if (!isSmartMoving) {
			Modchu_Debug.lDebug("isSmartMoving false.");
		}
		if (!isShaders) {
			Modchu_Debug.lDebug("isShaders false.");
		}
		if (!isDynamicLights) {
			Modchu_Debug.lDebug("isDynamicLights false.");
		}
		if (!isShader) {
			Modchu_Debug.lDebug("isShader false.");
		}
		if (!isAether) {
			Modchu_Debug.lDebug("isAether false.");
		}
		if (!is2D) {
			Modchu_Debug.lDebug("is2D false.");
		}
		if (!isCCTV) {
			Modchu_Debug.lDebug("isCCTV false.");
		}
		if (!isOlddays) {
			Modchu_Debug.lDebug("No Olddays.");
		}
		if (!isLMM) {
			Modchu_Debug.lDebug("No littleMaidMob.");
		}
		if (!isItemRendererHD) {
			Modchu_Debug.lDebug("No ItemRendererHD.");
		}

		if (isItemRendererHD) {
			Object b = Modchu_Reflect.getFieldObject(mod_Modchu_ModchuLib.modchu_Main.getClassName("ItemRendererHD"), "debugPFLM");
			if (b != null
					&& (Boolean) b) {
				isItemRendererDebug = true;
			}
		}
		isHD = isItemRendererHD
				&& !isItemRendererDebug;
		itemRendererClass = isHD ? Modchu_Reflect.loadClass("PFLM_ItemRendererHD") : Modchu_Reflect.loadClass("PFLM_ItemRenderer");
		if (isSmartMoving) {
			isModelSize = false;
			mod_Modchu_ModchuLib.modchu_Main.skirtFloats = false;
			mod_Modchu_ModchuLib.modchu_Main.modelClassName = "MultiModelSmart";
			BipedClass = Modchu_Reflect.loadClass(mod_Modchu_ModchuLib.modchu_Main.getClassName("MultiModelSmart_Biped"));
			Modchu_Reflect.invokeMethod(mod_Modchu_ModchuLib.modchu_Main.MMM_TextureManager, "addSearch", new Class[]{String.class, String.class, String.class}, null, new Object[]{mod_Modchu_ModchuLib.modchu_Main.modelClassName, "/mob/littleMaid/", mod_Modchu_ModchuLib.modchu_Main.modelClassName+"_"});
			Modchu_Reflect.invokeMethod(mod_Modchu_ModchuLib.modchu_Main.MMM_TextureManager, "addSearch", new Class[]{String.class, String.class, String.class}, null, new Object[]{"playerformlittlemaid", "/mob/littleMaid/", mod_Modchu_ModchuLib.modchu_Main.modelClassName+"_"});
		} else {
			BipedClass = MultiModel_Biped.class;
		}

		if (isPlayerForm) {
			/*//147delete
			int ID = ModLoader.getUniqueEntityId();
*///147delete
//-@-147
			Map map = (Map) Modchu_Reflect.getFieldObject(EntityList.class, "field_75623_d", "IDtoClassMapping");
			int ID = -1;
			if (map != null) {
				for(int i = 64; i < 3000; i++) {
					//Modchu_Debug.mDebug("i"+i+" !map.containsKey(i) = "+(!map.containsKey(i)));
					if (!map.containsKey(i)) {
						ID = i;
						//Modchu_Debug.mDebug("!map.containsKey ID="+ID);
						break;
					}
				}
			} else {
				Modchu_Debug.lDebug("IDtoClassMapping map == null !!");
			}
//@-@147
			if (guiMultiPngSaveButton
					&& ID > -1) ModLoader.registerEntityID(PFLM_EntityPlayerDummy.class, "PFLM_EntityPlayerDummy", ID);
		}
		if (isModelSize) {
/*//110delete
//-@-100
				// OptiFine判定L
				try {
					s = (String) getPrivateValue(RenderGlobal.class, this, "version");
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
			String className2 = mod_Modchu_ModchuLib.modchu_Main.getClassName("Config");
			test2 = null;
			int n1 = 0;
			try {
				test2 = ""+Class.forName(className2);
				Modchu_Debug.lDebug(test2 + " Check ok.");
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
					Modchu_Debug.lDebug("No OptiFine.");
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
						Modchu_Debug.lDebug("No OptiFine.");
					}
				} catch (Exception e1) {
					Modchu_Debug.lDebug("No OptiFine.");
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
								Modchu_Debug.lDebug("OptiFine "+ s2 + " Check ok.");
								// 125deleteerpflmCheck = erpflmCheck == 3 ? 6 : 5 ;
								/*125//*/erpflmCheck = 6;
							}
//@-@123
//-@-110
//-@-125
							s1 = "B3";
							if (s2.indexOf(s1) != -1) {
								Modchu_Debug.lDebug("OptiFine "+ s2 + " Check ok.");
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
						Modchu_Debug.lDebug("No OptiFine.");
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
					Modchu_Debug.lDebug("No Optimine.");
*///b166delete
//-@-b166
				Modchu_Debug.lDebug("No OptiFine Config.");
//-@-125
				try {
					s = "VersionThread";
					s = ""+Class.forName(s);
					erpflmCheck = 2;
				} catch (Exception e1) {
					Modchu_Debug.lDebug("No OptiFineL.");
				}
//@-@125
//@-@b166
				// b166delete}
			}
		}
/*//125delete
		if (isModelSize) {
			try {
				if (isCCTV) {
					String s3;
					entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererCCTV"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
					entityRenderer.itemRenderer = newInstanceItemRenderer();
					s3 = "PFLM_EntityRendererCCTV";
					Modchu_Debug.lDebug(s3 + " to set.");
					Class CCTV = Modchu_Reflect.loadClass(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("mod_CCTV"));
					Modchu_Reflect.setFieldObject(CCTV, "c", entityRenderer);
					Modchu_Reflect.setFieldObject(CCTV, "rendererReplaced", true);
				} else
				if (is2D) {
					String s3;
					entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRenderer2D"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
					s3 = "PFLM_EntityRenderer2D";
					Modchu_Debug.lDebug(s3 + " to set.");
				} else
				if (erpflmCheck == 0) {
					entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRenderer"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
					Modchu_Debug.lDebug("PFLM_EntityRenderer to set.");
				} else
				if (erpflmCheck == 1) {
					entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererForge"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
					Modchu_Debug.lDebug("PFLM_EntityRendererForge to set.");
				} else
				if (erpflmCheck == 2) {
					entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiL"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
					Modchu_Debug.lDebug("PFLM_EntityRendererOptiL to set.");
				} else
				if (erpflmCheck == 3) {
					String s3;
					if(isShaders) {
//-@-125
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShaders"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererShaders";
//@-@125

*///125delete
/*//125delete
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShadersHDM"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererShadersHDM";
*///125delete
/*//125delete
					} else {
//-@-125
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiHD"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererOptiHD";
//@-@125
*///125delete
/*//125delete
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiHDM"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererOptiHDM";
*///125delete
/*//125delete
					}
					Modchu_Debug.lDebug(s3 + " to set.");
				} else
				if (erpflmCheck == 4) {
					String s3;
					if(isShaders) {
						//-@-125
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShadersHDU"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererShadersHDU";
//@-@125
*///125delete
/*//125delete
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShaders"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererShaders";
*///125delete
/*//125delete
					} else {
//-@-125
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiHDU"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererOptiHDU";
//@-@125
*///125delete
/*//125delete
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiHD"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererOptiHD";
*///125delete
/*//125delete
					}
					Modchu_Debug.lDebug(s3 + " to set.");
				} else
					if (erpflmCheck == 5) {
						String s3;
						if(isShaders) {
//-@-125
							entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShadersHDU3"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
							s3 = "PFLM_EntityRendererShadersHDU3";
//@-@125
*///125delete
/*//125delete
							entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShadersC"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
							s3 = "PFLM_EntityRendererShadersC";
*///125delete
/*//125delete
						} else {
//-@-125
							entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiHDU3"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
							s3 = "PFLM_EntityRendererOptiHDU3";
//@-@125
*///125delete
/*//125delete
							entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiHDC"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
							s3 = "PFLM_EntityRendererOptiHDC";
*///125delete
/*//125delete
						}
						Modchu_Debug.lDebug(s3 + " to set.");
					} else
						if (erpflmCheck == 6) {
						String s3;
						if(isShaders) {
//-@-125
							entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShadersHDU3"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
							s3 = "PFLM_EntityRendererShadersHDU3";
//@-@125
*///125delete
/*//125delete
							entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShadersHDMTC"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
							s3 = "PFLM_EntityRendererShadersHDMTC";
*///125delete
/*//125delete
						} else {
//-@-125
							entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiHDU2"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
							s3 = "PFLM_EntityRendererOptiHDU2";
//@-@125
*///125delete
/*//125delete
							entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererOptiHDMTC"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
							s3 = "PFLM_EntityRendererOptiHDMTC";
*///125delete
/*//125delete
						}
						Modchu_Debug.lDebug(s3 + " to set.");
					} else
					if (erpflmCheck == 7) {
					String s3;
					if(isShader) {
//-@-110
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRendererShader"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRendererShader";
//@-@110
*///125delete
/*//110delete
						entityRenderer = (EntityRenderer) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PFLM_EntityRenderer"), new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, new Object[]{ mc });
						s3 = "PFLM_EntityRenderer";
*///110delete
/*//125delete
						Modchu_Debug.lDebug(s3 + " to set.");
					}
				}
			} catch(Exception exception) {
				Modchu_Debug.lDebug("PFLM_EntityRenderer Fail to set!");
				exception.printStackTrace();
			}
		} else {
			if (itemRendererClass != null) {
				if (!instanceCheck(itemRendererClass, entityRenderer.itemRenderer)) {
					ItemRenderer itemRenderer2 = newInstanceItemRenderer();
					if (itemRenderer2 != null) {
						Modchu_Debug.mDebug("modsLoaded itemRenderer2 != null");
						entityRenderer.itemRenderer = itemRenderer2;
						if (isHD) {
							initItemRendererHD = true;
						} else {
							initItemRenderer = true;
						}
						if (isCCTV) {
							Class CCTV = Modchu_Reflect.loadClass(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("mod_CCTV"));
							Class EntityExtensibleRenderer = Modchu_Reflect.loadClass(mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("EntityExtensibleRenderer"));
							Modchu_Reflect.setFieldObject(EntityExtensibleRenderer, "c", "itemRenderer", Modchu_Reflect.getFieldObject(CCTV, "c"), itemRenderer2);
						}
					} else {
						Modchu_Debug.mDebug("modsLoaded itemRenderer2 == null !!");
					}
				}
			}
		}
/*
		if(isSmartMoving) {
			isModelSize = false;
			try {
				s = mod_Modchu_ModchuLib.modchu_Main.mod_modchu_modchulib.getClassName("PlayerAPI");
				Method mes = null;
				mes = Class.forName(s).getMethod("unregister", new Class[] {String.class});
				if(isSmartMoving) mes.invoke(null, "Smart Moving");
				//PlayerAPI.unregister("Smart Moving");
			} catch (Exception e) {
			}
			PFLM_PlayerBaseSmartServer.registerPlayerBase();
			PFLM_SmartOtherPlayerData.initialize(gameSettings, false, ModLoader.getLogger());
		}
*/

		// PlayerAPI判定
		isPlayerAPI = false;
		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 69
				&& !isPlayerAPIDebug
				&& entityReplaceFlag) {
			//PlayerAPI.register("PlayerFormLittleMaid", PFLM_PlayerBase.class);
			s = mod_Modchu_ModchuLib.modchu_Main.getClassName("PlayerAPI");
			if (Modchu_Reflect.loadClass(s, -1) != null) {
				Class base;
				if (isSmartMoving) {
					base = Modchu_Reflect.loadClass("PFLM_PlayerBaseSmart");
				} else {
					base = Modchu_Reflect.loadClass("PFLM_PlayerBase");
				}
				Modchu_Reflect.invokeMethod(s, "register", new Class[] {String.class, Class.class}, null, new Object[]{ "PlayerFormLittleMaid", base });
				isPlayerAPI = true;
				Modchu_Debug.Debug("PlayerAPI register.");
			}
		}
		if (!isPlayerAPI) {
			Modchu_Debug.lDebug("PlayerAPI false.");
		}

		if (!isClearWater) {
			setWatherFog = watherFog;
			setWatherFog2 = watherFog2;
			setwaterStillLightOpacity = waterStillLightOpacity;
			setwaterMovingLightOpacity = waterMovingLightOpacity;
		} else {
			Block.waterStill.setLightOpacity(setwaterStillLightOpacity);
			Block.waterMoving.setLightOpacity(setwaterMovingLightOpacity);
		}
		//MMM_TextureManager.getArmorPrefix();
		//MMM_TextureManager.getModFile();
		//MMM_TextureManager.getTextures();

		if (entityReplaceFlag) Modchu_Debug.Debug("PFLM-EntityPlayerReplace setting on.");
		else Modchu_Debug.Debug("PFLM-EntityPlayerReplace setting off.");
	}

	public static void texturesNamberInit() {
		//モデルリスト用テクスチャーパックナンバー作成
		if (texturesNamberInitFlag) return;
		texturesNamberInitFlag = true;
		List textures = mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTextures();
		texturesNamber = new int[16][textures.size()];
		texturesArmorNamber = new int[textures.size()];
		for (int color = 0 ; color < 16 ; color++) {
			for (int i2 = 0 ; i2 < textures.size() ; ++i2) {
				texturesNamber[color][i2] = -1;
			}
		}
		String fileName = null;
		String t = null;
		String t1 = null;
		int[] i1 = new int[16];
		int i3 = 0;
		Object ltb;
		for (int i2 = 0 ; i2 < textures.size() ; ++i2) {
			texturesArmorNamber[i3] = -1;
			ltb = textures.get(i2);
			fileName = mod_Modchu_ModchuLib.modchu_Main.getTextureBoxFileName(ltb);
			t1 = fileName != null ? fileName : t1;
			if (ltb != null
					&& t1 != null
					&& !t1.isEmpty()) {
				for (int color = 0 ; color < 16 ; color++) {
					if (mod_Modchu_ModchuLib.modchu_Main.getTextureBoxHasColor(ltb, color)) {
						//Modchu_Debug.mDebug("color="+color+":i1="+i1[color]+" t1="+t1+" t="+t);
						texturesNamber[color][i1[color]] = i2;
						++i1[color];
						t = t1;
					}
					maxTexturesNamber[color] = i1[color];
				}
				if (mod_Modchu_ModchuLib.modchu_Main.getTextureBoxHasArmor(ltb)) {
					texturesArmorNamber[i3] = i2;
					++i3;
				}
			}
		}
		maxTexturesArmorNamber = i3;
	}
/*
	public static void texturesArmorNamberInit() {
		//モデルリスト用テクスチャーアーマーパックナンバー作成
		if (texturesArmorNamberInitFlag) return;
		texturesArmorNamberInitFlag = true;
		List textures = mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTextures();
		texturesArmorNamber = new int[textures.size()];
		int i3 = 0;
		Object ltb;
		for (int i2 = 0 ; i2 < textures.size() ; ++i2) {
			texturesArmorNamber[i3] = -1;
			ltb = textures.get(i2);
			if (ltb != null) {
				if (mod_Modchu_ModchuLib.modchu_Main.getTextureBoxHasArmor(ltb)) {
					texturesArmorNamber[i3] = i2;
					++i3;
				}
			}
		}
		maxTexturesArmorNamber = i3;
	}
 */
	public static int optiNameCheck(String s, int j) {
		int i = j;
		String s1 = "HD_MT_AA";
		if (s.indexOf(s1) != -1) {
			Modchu_Debug.lDebug(s + " Check ok.");
			i = 3;
			getIconWidthTerrain = 0;
		} else {
			s1 = "HD_U";
			if (s.indexOf(s1) != -1) {
				/*125//*/optiVersionName = "HD_U ";
				Modchu_Debug.lDebug(s + " Check ok.");
				i = 3;
			} else {
				s1 = "HD";
				/*125//*/optiVersionName = "HD ";
				if (s.indexOf(s1) != -1) {
					Modchu_Debug.lDebug(s + " Check ok.");
					i = 4;
				} else {
					Modchu_Debug.lDebug("No OptiFine.Name Chenk error.Name="+s);
				}
			}
		}
		return i;
	}

    private static void startVersionCheckThread()
    {
    	PFLM_ThreadVersionCheck var0 = new PFLM_ThreadVersionCheck();
        var0.start();
    }

	public static boolean checkRelease(String s) {
		if (s != null) {
			if (s.length() > 1) {
				String ck = s.substring(s.length() - 1);
				String mck = mod_PFLM_PlayerFormLittleMaid.pflm_main.getVersion();
				String k = version;
				mck = k.substring(k.length() - 1);
				if (mod_Modchu_ModchuLib.modchu_Main.integerCheck(mck)) mck = "";
				boolean check = mod_Modchu_ModchuLib.modchu_Main.integerCheck(k);
				while(!check
						&& k.length() > 1){
					//Modchu_Debug.mDebug("checkRelease k="+k);
					check = mod_Modchu_ModchuLib.modchu_Main.integerCheck(k.substring(0, k.length() - 1));
					k = k.substring(0, k.length() - 1);
				}
				int m = Integer.valueOf(k);
				//Modchu_Debug.mDebug("checkRelease m="+m+" mck="+mck);
				if (mod_Modchu_ModchuLib.modchu_Main.integerCheck(ck)) ck = "";
				check = mod_Modchu_ModchuLib.modchu_Main.integerCheck(s);
				while(!check
						&& s.length() > 1){
					//Modchu_Debug.mDebug("checkRelease s="+s);
					check = mod_Modchu_ModchuLib.modchu_Main.integerCheck(s.substring(0, s.length() - 1));
					s = s.substring(0, s.length() - 1);
				}
				int i = Integer.valueOf(s);
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