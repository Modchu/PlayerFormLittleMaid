package modchu.pflm;

public class PFLM_ConfigData {

	//cfg書き込み項目
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
	public static int othersHandednessMode = 0;
	public static float Physical_Hammer = 1.0F;
	public static float watherFog = 0.1F;
	public static float watherFog2 = 0.05F;
	public static float lavaFog = 2.0F;
	//public static double locationPositionCorrectionY = 0.5D;
	public static String Physical_HurtSound = "damage.hit";
	public static String textureSavedir = "/output/";

	public static float modelScale = 0.0F;
	public static int changeMode = 0;
	public static int maidColor = 0;
	public static int othersMaidColor = 0;
	public static int othersChangeMode = 0;
	public static int setwaterStillLightOpacity = 0;
	public static int setwaterMovingLightOpacity = 0;
	public static int guiSelectWorldSwapCount = 0;
	public static int guiMultiplayerSwapCount = 0;
	public static float setWatherFog = 0F;
	public static float setWatherFog2 = 0F;
	public static String textureName = "default";
	public static String textureArmorName = "default";
	public static String othersTextureName = "default";
	public static String othersTextureArmorName = "default";
	public static float othersModelScale = 0.0F;
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
	public static int[][] texturesNamber;
	public static int[] texturesArmorNamber;
	public static int[] maxTexturesNamber = new int [16];
	public static int maxTexturesArmorNamber;

}