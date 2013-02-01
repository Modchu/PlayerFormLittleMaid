package net.minecraft.src;

public class PFLM_ModelDataSmart
{
	public MultiModelSmart mainModel;
	public MultiModelSmart modelArmorChestplate;
	public MultiModelSmart modelArmor;
	public String modeltype = null;
	public String modelArmorName = null;
	public String texture = null;
	public String textureArmor0[] = new String[4];
	public String textureArmor1[] = new String[4];
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

	public PFLM_ModelDataSmart()
	{
		mainModel = new MultiModelSmart_Biped(0.0F);
		modelArmorChestplate = new MultiModelSmart_Biped(1.0F);
		modelArmor = new MultiModelSmart_Biped(0.5F);
	}
}
