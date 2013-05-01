package net.minecraft.src;

import java.util.Map;

import net.minecraft.client.Minecraft;

public class PFLM_PlayerController extends PlayerControllerSP {

    public static PFLM_EntityPlayerSP pflm_entityPlayersp;
	/*b166//*/public static Object pflm_entityPlayer;
    public static PFLM_PlayerController gotcha;
    //public static PFLM_EntityRenderer erpflm;
    private Minecraft mc;
    private NetClientHandler netClientHandler;

    public PFLM_PlayerController(Minecraft minecraft) {
    	super(minecraft);gotcha = this;
    	mod_PFLM_PlayerFormLittleMaid.guiSelectWorldSwapCount = 0;
    	mc = minecraft;//netClientHandler = par2NetClientHandler;
    }

    public EntityPlayer createPlayer(World world) {
    	// EntityClientPlayerMPの置き換え PlayerAPI未使用
    	if (!mod_PFLM_PlayerFormLittleMaid.isPlayerAPI
    			| mod_PFLM_PlayerFormLittleMaid.isPlayerAPIDebug) {
    		if (pflm_entityPlayersp != null
    				&& !PFLM_Gui.noSaveFlag) mod_PFLM_PlayerFormLittleMaid.loadParamater();
    		Modchu_Debug.Debug("Replace PFLM_EntityPlayerSP.");
/*
    		if (mod_PFLM_PlayerFormLittleMaid.isOlddays) {
    			Class[] types = { Minecraft.class , World.class, Session.class, NetClientHandler.class };
    			Object[] args = {mc, world, mc.session, mc.thePlayer.dimension};
    			pflm_entityPlayersp = (EntityClientPlayerMP) Modchu_Reflect.newInstance(mod_PFLM_PlayerFormLittleMaid.pflm_entityPlayerSP2, types, args);
    			//pflm_entityPlayersp = new PFLM_EntityPlayerSP2(mc, world, mc.session, mc.thePlayer.dimension);
    		} else
*/
    			pflm_entityPlayersp = new PFLM_EntityPlayerSP(mc, world, mc.session, world.worldProvider.worldType);
    		return pflm_entityPlayersp;
    	} else {
//-@-b166
    		// EntityClientPlayerMPの置き換え PlayerAPI用

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

    		}

    	}
    }

    public static boolean addRenderer()
    {
    	Map var0 = (Map)Modchu_Reflect.getFieldObject(RenderManager.class, "entityRenderMap", "o", RenderManager.instance);
    	if (var0 == null)
    	{
    		return false;
    	}
    	else
    	{
    		PFLM_RenderPlayer var1 = new PFLM_RenderPlayer();
    		var0.put(EntityPlayerSP.class, var1);
    		var0.put(EntityOtherPlayerMP.class, var1);
    		var1.setRenderManager(RenderManager.instance);
    		return true;
    	}
    }

}
