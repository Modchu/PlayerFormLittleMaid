package modchu.pflm;import modchu.lib.Modchu_Debug;import modchu.lib.Modchu_Main;import modchu.lib.Modchu_Reflect;import net.minecraft.client.entity.EntityClientPlayerMP;import net.minecraft.client.multiplayer.NetClientHandler;import net.minecraft.entity.player.EntityPlayer;import net.minecraft.util.Session;import net.minecraft.world.World;public class PFLM_PlayerControllerMaster {    public static EntityClientPlayerMP pflm_entityPlayersp;    private NetClientHandler netClientHandler;    public PFLM_PlayerControllerMaster(Object minecraft, NetClientHandler par2NetClientHandler) {    	PFLM_Main.guiSelectWorldSwapCount = 0;    	netClientHandler = par2NetClientHandler;    }    public EntityPlayer createPlayer(World world) {    	// EntityClientPlayerMPの置き換え PlayerAPI未使用    	if (!PFLM_Main.isPlayerAPI    			| PFLM_Main.isPlayerAPIDebug) {    		if (pflm_entityPlayersp != null    				&& !PFLM_Gui.noSaveFlag) PFLM_Main.loadParamater();    		Modchu_Debug.lDebug("Replace PFLM_EntityPlayerSP.");//-@-125    		Class[] types = { Modchu_Reflect.loadClass("Minecraft") , World.class, Session.class, NetClientHandler.class };    		EntityPlayer thePlayer = Modchu_Main.getThePlayer();    		if (PFLM_Main.isOlddays) {    			Object[] args = {Modchu_Main.getMinecraft(), world, Modchu_Reflect.invokeMethod("Minecraft", "func_110432_I", "getSession", Modchu_Main.getMinecraft()), thePlayer.dimension};    			pflm_entityPlayersp = (EntityClientPlayerMP) Modchu_Reflect.newInstance("PFLM_EntityPlayerSP2", types, args);    			//pflm_entityPlayersp = new PFLM_EntityPlayerSP2(mc, world, mc.session, mc.thePlayer.dimension);    		} else {//@-@125    			Object[] args = {Modchu_Main.getMinecraft(), world, Modchu_Reflect.invokeMethod("Minecraft", "func_110432_I", "getSession", Modchu_Main.getMinecraft()), netClientHandler};    			pflm_entityPlayersp = (EntityClientPlayerMP) Modchu_Reflect.newInstance("PFLM_EntityPlayerSP", types, args);    			//pflm_entityPlayersp = new PFLM_EntityPlayerSP(mc, world, mc.func_110432_I(), netClientHandler);    		}    		return pflm_entityPlayersp;    	} else {//-@-b166    		// EntityClientPlayerMPの置き換え PlayerAPI用/*//125delete    		if (PFLM_Main.isPlayerAPI) {    			Modchu_Debug.Debug("Replace PFLM_EntityPlayer.");    			if (pflm_entityPlayer != null    					&& !PFLM_Gui.noSaveFlag) PFLM_Main.loadParamater();    			pflm_entityPlayer = new PFLM_EntityPlayer(mc, world, mc.session, netClientHandler);    			return pflm_entityPlayer;    		} else {*///125delete//@-@b166    			//Modchu_Debug.Debug("createPlayer.");/*//125delete    		}*///125delete    	}    	return null;    }/*    public static boolean addRenderer()    {    	Map var0 = (Map)Modchu_Reflect.getFieldObject(RenderManager.class, "entityRenderMap", "o", RenderManager.instance);    	if (var0 == null)    	{    		return false;    	}    	else    	{    		PFLM_RenderPlayerSmart var1 = new PFLM_RenderPlayerSmart();    		var0.put(EntityPlayerSP.class, var1);    		var0.put(EntityOtherPlayerMP.class, var1);    		var1.setRenderManager(RenderManager.instance);    		return true;    	}    }*/}