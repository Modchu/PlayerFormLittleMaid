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

	public PFLM_ModelData(RenderLiving renderLiving)
	{
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
