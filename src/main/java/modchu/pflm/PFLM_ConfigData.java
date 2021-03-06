package modchu.pflm;

import java.util.HashMap;
import java.util.List;

import modchu.model.ModchuModel_TextureManagerBase;

public class PFLM_ConfigData {

	//cfg書き込み項目
	public static boolean Physical_Undead = false;
	public static boolean isPlayerForm = true;
	public static boolean isPlayerAPIDebug = false;
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
	public static int setModel = 0;
	public static int setColor = 0;
	public static int setArmor = 0;
	public static int defaultSystemShortcutKeysGui = 41;
	public static int defaultSystemShortcutKeysModels = 43;
	public static int defaultSystemShortcutKeysWait = 26;
	public static int defaultSystemShortcutKeysSit = 39;
	public static int defaultSystemShortcutKeysLieDown = 27;
	public static float Physical_Hammer = 1.0F;
	public static float watherFog = 0.1F;
	public static float watherFog2 = 0.05F;
	public static float lavaFog = 2.0F;
	public static boolean showArmor = true;
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
	public static String textureName = ModchuModel_TextureManagerBase.instance.getDefaultTextureName();
	public static String textureArmorName = ModchuModel_TextureManagerBase.instance.getDefaultTextureName();
	public static String othersTextureName = ModchuModel_TextureManagerBase.instance.getDefaultTextureName();
	public static String othersTextureArmorName = ModchuModel_TextureManagerBase.instance.getDefaultTextureName();
	public static float othersModelScale = 0.0F;
	public static final int maxShortcutKeys = 100;
	public static final String shortcutKeysName = "key.PFLM";
	public static final String systemShortcutKeysGuiName = "key.PFLM Gui";
	public static final String systemShortcutKeysModelsName = "key.PFLM Models Key";
	public static final String systemShortcutKeysWaitName = "key.PFLM Wait";
	public static final String systemShortcutKeysSitName = "key.PFLM Sit";
	public static final String systemShortcutKeysLieDownName = "key.PFLM LieDown";
	public static float shortcutKeysModelScale[] = new float[maxShortcutKeys];
	public static String shortcutKeysTextureName[] = new String[maxShortcutKeys];
	public static String shortcutKeysTextureArmorName[] = new String[maxShortcutKeys];
	public static int shortcutKeysMaidColor[] = new int[maxShortcutKeys];
	public static int shortcutKeysChangeMode[] = new int[maxShortcutKeys];
	public static boolean shortcutKeysUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysPFLMModelsUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysCtrlUse[] = new boolean[maxShortcutKeys];
	public static boolean shortcutKeysShiftUse[] = new boolean[maxShortcutKeys];
	public static boolean autoArmorSelect = true;
	public static int shortcutKeysNumber = 0;

}
