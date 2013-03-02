package net.minecraft.src;

public class PFLM_ModelData
{
	public MMM_ModelDuo modelMain;
	public MMM_ModelDuo modelFATT;
	public String modelArmorName = null;
	public boolean localFlag = false;
	public boolean isActivated = false;
	public boolean isPlayer = false;
	public boolean isWait = false;
	public boolean isWaitFSetFlag = false;
	public boolean shortcutKeysAction = false;
	public boolean shortcutKeysActionInitFlag = true;
	public boolean changeModelFlag = true;
	public boolean resetHandedness = true;
	public float isWaitF = 0.0F;
	public float modelScale = 0.0F;
	public int isWaitTime = 0;
	public int skinMode = 0;
	public int initFlag = 0;
	public int runActionNumber = 0;
	public int handedness = 0;
	public int maidColor = 0;
	public boolean motionResetFlag = false;
	public boolean mushroomConfusionLeft = false;
	public boolean mushroomConfusionRight = false;
	public boolean mushroomConfusionFront = false;
	public boolean mushroomConfusionBack = false;
	public boolean motionSetFlag = false;
	public boolean mushroomBack = false;
	public boolean mushroomForward = false;
	public boolean mushroomKeyBindResetFlag = false;
	public boolean mushroomKeyBindSetFlag = false;
	public int mushroomConfusionType = 0;
	public int mushroomConfusionCount = 0;
	public final int mushroomConfusionTypeMax = 4;
	public boolean mushroomLeft = false;
	public boolean mushroomRight = false;
/*//b173delete
	public boolean keyBindForwardPressed;
	public boolean keyBindBackPressed;
	public boolean keyBindLeftPressed;
	public boolean keyBindRightPressed;
	public KeyBinding keyBindForward;
	public KeyBinding keyBindBack;
	public KeyBinding keyBindLeft;
	public KeyBinding keyBindRight;
	public boolean mushroomConfusionFlag = false;
*///b173delete

	public PFLM_ModelData(RenderLiving renderLiving) {
		modelMain = new MMM_ModelDuo(renderLiving);
		modelMain.isModelAlphablend = mod_PFLM_PlayerFormLittleMaid.AlphaBlend;
		modelMain.textureInner = new String [4];
		modelMain.textureOuter = new String [4];
		modelFATT = new MMM_ModelDuo(renderLiving);
		modelFATT.isModelAlphablend = mod_PFLM_PlayerFormLittleMaid.AlphaBlend;
		modelFATT.textureInner = new String [4];
		modelFATT.textureOuter = new String [4];
	}
}
