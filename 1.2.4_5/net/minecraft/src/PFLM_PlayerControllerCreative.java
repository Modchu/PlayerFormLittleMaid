package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.move.Reflect;

public class PFLM_PlayerControllerCreative extends PlayerControllerCreative {

	protected static boolean isCreative = true;
	public static PFLM_EntityPlayerSP pflm_entityPlayersp;
	/*b166//*/public static Object pflm_entityPlayer;
	public static PFLM_PlayerController gotcha;
	//public static PFLM_EntityRenderer erpflm;

	public PFLM_PlayerControllerCreative(Minecraft minecraft) {
		super(minecraft);
		isInTestMode = true;
		mod_PFLM_PlayerFormLittleMaid.guiSelectWorldSwapCount = 0;
	}

    public EntityPlayer createPlayer(World world) {
    	// EntityPlayerSPの置き換え PlayerAPI未使用
    	if (!mod_PFLM_PlayerFormLittleMaid.isPlayerAPI
    			| mod_PFLM_PlayerFormLittleMaid.isPlayerAPIDebug) {
    		if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
    			ModLoader.getLogger().fine("playerFormLittleMaid-addRebderer RenderPlayerFormLittleMaidSmart");
    			//AddRenderer(new RenderPlayerFormLittleMaidSmart());
    			Modchu_Debug.Debug("addRebderer RenderPlayerFormLittleMaidSmart");
    		}
    		Modchu_Debug.Debug("Replace PFLM_EntityPlayerSP.");
    		if (pflm_entityPlayersp != null
    				&& !PFLM_Gui.noSaveFlag) mod_PFLM_PlayerFormLittleMaid.loadParamater();
    		pflm_entityPlayersp = new PFLM_EntityPlayerSP(mc, world, mc.session, world.worldProvider.worldType);
    		return pflm_entityPlayersp;
    	} else {
//-@-b166
    		// EntityPlayerSPの置き換え PlayerAPI用
    		if (mod_PFLM_PlayerFormLittleMaid.isPlayerAPI) {
    			Modchu_Debug.Debug("Replace PFLM_EntityPlayer.");
    			if (pflm_entityPlayer != null
    					&& !PFLM_Gui.noSaveFlag) mod_PFLM_PlayerFormLittleMaid.loadParamater();
    			pflm_entityPlayer = new PFLM_EntityPlayer(mc, world, mc.session, world.worldProvider.worldType);
    			return (EntityPlayer) pflm_entityPlayer;
    		} else {
//@-@b166
    			Modchu_Debug.Debug("createPlayer.");
    			return super.createPlayer(world);
    		/*b166//*/}
    	}
    }

    // テスト用
    public boolean isInCreativeMode() {
    	return isCreative;
    }

    public void setInCreativeMode(boolean flag) {
    	isCreative = flag;
    }
}
