package net.minecraft.src;

public class PFLM_ModelData
{
	public boolean isActivated;
	public String modeltype;
	public String modelArmorName;
	public String texture;
	public ModelPlayerFormLittleMaidBaseBiped mainModel;
	public ModelPlayerFormLittleMaidBaseBiped modelArmorChestplate;
	public ModelPlayerFormLittleMaidBaseBiped modelArmor;
	public boolean isPlayer;
	public boolean localFlag;
	public boolean isWait;
	public int isWaitTime = 0;
	public float isWaitF = 0.0F;
	public boolean isWaitFSetFlag;
	public int skinMode;
	public static String textureArmor0[];
	public static String textureArmor1[];
	public int initFlag;
	public int runActionNumber;
	public boolean shortcutKeysAction;
	public boolean shortcutKeysActionInitFlag;
	public boolean changeModelFlag;
	public int handedness;

	public PFLM_ModelData()
	{
		isActivated = false;
		modeltype = null;
		isPlayer = false;
		localFlag = false;
		isWait = false;
		shortcutKeysAction = false;
		shortcutKeysActionInitFlag = true;
		changeModelFlag = true;
		initFlag = 0;
		runActionNumber = 0;
		skinMode = 0;
		handedness = 0;
		mainModel = new ModelPlayerFormLittleMaid_Biped(0.0F);
		modelArmorChestplate = new ModelPlayerFormLittleMaid_Biped(1.0F);
		modelArmor = new ModelPlayerFormLittleMaid_Biped(0.5F);
		textureArmor0 = new String[4];
		textureArmor1 = new String[4];
	}
}
