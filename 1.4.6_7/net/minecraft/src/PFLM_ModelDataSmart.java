package net.minecraft.src;

public class PFLM_ModelDataSmart
{
	public boolean isActivated;
	public String modeltype;
	public String modelArmorName;
	public String texture;
	public ModelPlayerFormLittleMaidSmart mainModel;
	public ModelPlayerFormLittleMaidSmart modelArmorChestplate;
	public ModelPlayerFormLittleMaidSmart modelArmor;
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

	public PFLM_ModelDataSmart()
	{
		isActivated = false;
		modeltype = null;
		isPlayer = false;
		localFlag = false;
		shortcutKeysAction = false;
		shortcutKeysActionInitFlag = true;
		changeModelFlag = true;
		initFlag = 0;
		runActionNumber = 0;
		skinMode = 0;
		handedness = 0;
		mainModel = new ModelPlayerFormLittleMaidSmart_Biped(0.0F);
		modelArmorChestplate = new ModelPlayerFormLittleMaidSmart_Biped(1.0F);
		modelArmor = new ModelPlayerFormLittleMaidSmart_Biped(0.5F);
		textureArmor0 = new String[4];
		textureArmor1 = new String[4];
	}
}
