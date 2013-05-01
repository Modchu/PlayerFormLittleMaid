package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.move.Reflect;

public class PFLM_PlayerControllerMP extends PlayerControllerMP {

	/*b166//*/public static PFLM_EntityPlayer entityplayerformlittlemaid;
    public static PFLM_PlayerControllerMP gotcha;
    public static PFLM_EntityRenderer erpflm;
	public static PFLM_EntityPlayerMP entityplayerformlittlemaidmp;
    private NetClientHandler netClientHandler;

	public PFLM_PlayerControllerMP(Minecraft minecraft, NetClientHandler par2NetClientHandler) {
		super(minecraft, par2NetClientHandler);
		gotcha = this;
		mod_PFLM_PlayerFormLittleMaid.guiSelectWorldSwapCount = 0;
		netClientHandler = par2NetClientHandler;
	}

	public EntityPlayer createPlayer(World world) {
    	// EntityPlayerSPの置き換え PlayerAPI未使用
		if (!mod_PFLM_PlayerFormLittleMaid.isPlayerAPI
				| mod_PFLM_PlayerFormLittleMaid.isPlayerAPIDebug) {
			Modchu_Debug.Debug("Replace PFLM_EntityPlayerSP.");
			if (entityplayerformlittlemaidmp != null
					&& !PFLM_Gui.noSaveFlag) mod_PFLM_PlayerFormLittleMaid.loadParamater();
			entityplayerformlittlemaidmp = new PFLM_EntityPlayerMP(mc, world, mc.session, netClientHandler);
			return entityplayerformlittlemaidmp;
		} else {
//-@-b166
			// EntityPlayerSPの置き換え PlayerAPI用
			if (mod_PFLM_PlayerFormLittleMaid.isPlayerAPI) {
				Modchu_Debug.Debug("Replace PFLM_EntityPlayer.");
				if (entityplayerformlittlemaid != null
						&& !PFLM_Gui.noSaveFlag) mod_PFLM_PlayerFormLittleMaid.loadParamater();
				entityplayerformlittlemaid = new PFLM_EntityPlayer(mc, world, mc.session, world.worldProvider.worldType);
				return entityplayerformlittlemaid;
			} else {
//@-@b166
				Modchu_Debug.Debug("createPlayer.");
				return super.createPlayer(world);
			/*b166//*/}
		}
	}

    public boolean isInCreativeMode() {
    	return (Boolean) mod_PFLM_PlayerFormLittleMaid.getPrivateValue(PlayerControllerMP.class, this, 8);
    }

    public void setInCreativeMode(boolean flag) {
    	mod_PFLM_PlayerFormLittleMaid.setPrivateValue(PlayerControllerMP.class, this, 8, flag);
    }
//-@-100
    public static boolean AddRenderer(RenderPlayer renderplayer)
    {
        Object obj = Reflect.GetField(net.minecraft.src.RenderManager.class, RenderManager.instance, "entityRenderMap", "o");
        java.lang.reflect.Method method = Reflect.GetMethod(obj.getClass(), "put", "put", new Class[]
                {
                    java.lang.Object.class, java.lang.Object.class
                });
        Reflect.Invoke(method, obj, new Object[]
                {
                    net.minecraft.src.EntityPlayerSP.class, renderplayer
                });
        Reflect.Invoke(method, obj, new Object[]
                {
                    net.minecraft.src.EntityOtherPlayerMP.class, renderplayer
                });
        renderplayer.setRenderManager(RenderManager.instance);
        return true;
    }
//@-@100
}
