package net.minecraft.src;

public class PFLM_PlayerController extends PlayerControllerMP {

    public static EntityClientPlayerMP pflm_entityPlayersp;
    //isModelSize
    /*b166//*/ //public static Object pflm_entityPlayer;
    public static PFLM_PlayerController gotcha;
    //public static PFLM_EntityRenderer erpflm;
    private Minecraft mc;
    private NetClientHandler netClientHandler;

    public PFLM_PlayerController(Minecraft minecraft, NetClientHandler par2NetClientHandler) {
    	super(minecraft, par2NetClientHandler);gotcha = this;
    	mod_PFLM_PlayerFormLittleMaid.guiSelectWorldSwapCount = 0;
    	mc = minecraft;netClientHandler = par2NetClientHandler;
    }

    public EntityClientPlayerMP func_78754_a(World world) {
    	// EntityClientPlayerMP�̒u������ PlayerAPI���g�p
    	if (!mod_PFLM_PlayerFormLittleMaid.isPlayerAPI
    			| mod_PFLM_PlayerFormLittleMaid.isPlayerAPIDebug) {
    		if (pflm_entityPlayersp != null
    				&& !PFLM_Gui.noSaveFlag) mod_PFLM_PlayerFormLittleMaid.loadParamater();
    		Modchu_Debug.Debug("Replace PFLM_EntityPlayerSP.");
//-@-125
    		if (mod_PFLM_PlayerFormLittleMaid.isOlddays) {
    			Class[] types = { Minecraft.class , World.class, Session.class, NetClientHandler.class };
    			Object[] args = {mc, world, mc.func_110432_I(), mc.thePlayer.dimension};
    			pflm_entityPlayersp = (EntityClientPlayerMP) Modchu_Reflect.newInstance(mod_PFLM_PlayerFormLittleMaid.pflm_entityPlayerSP2, types, args);
    			//pflm_entityPlayersp = new PFLM_EntityPlayerSP2(mc, world, mc.session, mc.thePlayer.dimension);
    		} else
//@-@125
    			pflm_entityPlayersp = new PFLM_EntityPlayerSP(mc, world, mc.func_110432_I(), netClientHandler);
    		return pflm_entityPlayersp;
    	} else {
//-@-b166
    		// EntityClientPlayerMP�̒u������ PlayerAPI�p
/*//125delete
    		if (mod_PFLM_PlayerFormLittleMaid.isPlayerAPI) {
    			Modchu_Debug.Debug("Replace PFLM_EntityPlayer.");
    			if (pflm_entityPlayer != null
    					&& !PFLM_Gui.noSaveFlag) mod_PFLM_PlayerFormLittleMaid.loadParamater();
    			pflm_entityPlayer = new PFLM_EntityPlayer(mc, world, mc.session, netClientHandler);
    			return pflm_entityPlayer;
    		} else {
*///125delete
//@-@b166
    			Modchu_Debug.Debug("createPlayer.");
    			return super.func_78754_a(world);
/*//125delete
    		}
*///125delete
    	}
    }
/*
    public static boolean addRenderer()
    {
    	Map var0 = (Map)Modchu_Reflect.getFieldObject(RenderManager.class, "entityRenderMap", "o", RenderManager.instance);
    	if (var0 == null)
    	{
    		return false;
    	}
    	else
    	{
    		PFLM_RenderPlayerSmart var1 = new PFLM_RenderPlayerSmart();
    		var0.put(EntityPlayerSP.class, var1);
    		var0.put(EntityOtherPlayerMP.class, var1);
    		var1.setRenderManager(RenderManager.instance);
    		return true;
    	}
    }
*/
}
