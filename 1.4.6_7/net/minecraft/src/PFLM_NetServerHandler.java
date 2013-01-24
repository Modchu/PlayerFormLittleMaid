package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;
/*//FMLdelete
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
*///FMLdelete
import net.minecraft.server.MinecraftServer;

public class PFLM_NetServerHandler extends NetServerHandler
{
    private static final Field _player = Modchu_Reflect.getField(NetServerHandler.class, "playerEntity", "e");
    private static final Field _minecraftServer = Modchu_Reflect.getField(NetServerHandler.class, "mcServer", "d");
    private static final Field _playerList = Modchu_Reflect.getField(NetworkListenThread.class, "connections", "d");
	private boolean handleFlyingFlag;
	private Packet10Flying par1Packet10Flying;
    //public static Logger logger = (PFLM_Logger) PFLM_Logger.getLogger("Minecraft");

    public PFLM_NetServerHandler(MinecraftServer var1, INetworkManager var2, EntityPlayerMP var3)
    {
        super(var1, var2, var3);
        logger = new PFLM_Logger("Minecraft");
    }

	@Override
    public void kickPlayerFromServer(String par1Str)
    {
		if (par1Str.equalsIgnoreCase("Illegal stance")) {
			double var7 = par1Packet10Flying.yPosition;
			double var11 = par1Packet10Flying.stance - par1Packet10Flying.yPosition;
			if (!getPlayer().isPlayerSleeping() && (var11 > 2.65D || var11 < -1.5D))
			{
				super.kickPlayerFromServer(par1Str);
			}
			return;
		}
		super.kickPlayerFromServer(par1Str);
    }

	@Override
    public void handleFlying(Packet10Flying var1)
    {
		handleFlyingFlag = true;
		par1Packet10Flying = var1;
/*
		double var11 = par1Packet10Flying.stance - par1Packet10Flying.yPosition;
		double d = (double)(mod_PFLM_PlayerFormLittleMaid.getyOffset() - 1.62F);
		if (var11 > 1.65D) par1Packet10Flying.stance = par1Packet10Flying.stance - d;
		if (var11 < 0.1D) par1Packet10Flying.stance = par1Packet10Flying.stance + d;
*/
		//Modchu_Debug.mDebug("handleFlying");
		super.handleFlying(var1);
		handleFlyingFlag = false;
    }

	@Override
    public void setPlayerLocation(double par1, double par3, double par5, float par7, float par8)
    {
		if (handleFlyingFlag) {
			double var5 = par1Packet10Flying.xPosition;
			double var7 = par1Packet10Flying.yPosition;
			double var9 = getPlayer().posZ;
			double var11 = var5 - getPlayer().posX;
			double var15 = var7 - getPlayer().posY;
			double var17 = var9 - getPlayer().posZ;
			double var19 = Math.min(Math.abs(var11), Math.abs(getPlayer().motionX));
			double var21 = Math.min(Math.abs(var15), Math.abs(getPlayer().motionY));
			double var23 = Math.min(Math.abs(var17), Math.abs(getPlayer().motionZ));
			double var25 = var19 * var19 + var21 * var21 + var23 * var23;
	        MinecraftServer var2 = (MinecraftServer)Modchu_Reflect.getFieldObject(_minecraftServer, this);

			if (var25 > 100.0D && (!var2.isSinglePlayer() || !var2.getServerOwner().equals(getPlayer().username))) {
		    	Modchu_Debug.mDebug("setPlayerLocation var25");
				super.setPlayerLocation(par1, par3, par5, par7, par8);
				return;
			}
	    	Modchu_Debug.mDebug("setPlayerLocation return");
			return;
		}
    	Modchu_Debug.mDebug("setPlayerLocation");
		super.setPlayerLocation(par1, par3, par5, par7, par8);
    }

	@Override
    public void handlePlace(Packet15Place var1)
    {
        super.handlePlace(var1);
/*
        if (var1.getDirection() == 255) {
        	EntityPlayerMP var2 = (EntityPlayerMP)Modchu_Reflect.getField(_player, this);
            ItemStack var3 = var2.inventory.getCurrentItem();
            if (var3 != null) {
                float var4 = var2.getEyeHeight();
                Modchu_Debug.mDebug("handlePlace var4="+var4);
                var2.yOffset += var4;
                super.handlePlace(var1);
                var2.yOffset -= var4;
            }
        } else {
            super.handlePlace(var1);
        }
*/
    }

    public static boolean replace(EntityPlayerMP var0)
    {
    	Modchu_Debug.Debug("Replace PFLM_NetServerHandler");
    	//if (_playerList == null) Modchu_Debug.Debug("_playerList == null");
    	//if (_player == null) Modchu_Debug.Debug("_player == null");
    	NetServerHandler var1 = var0.playerNetServerHandler;
        MinecraftServer var2 = (MinecraftServer)Modchu_Reflect.getFieldObject(_minecraftServer, var1);
        PFLM_NetServerHandler var3 = new PFLM_NetServerHandler(var2, var1.netManager, var0);
        Modchu_Reflect.copyFields(NetServerHandler.class, var1, var3);
        Modchu_Reflect.copyFields(NetHandler.class, var1, var3);
        List var4 = (List)Modchu_Reflect.getFieldObject(_playerList, var2.getNetworkThread());

        for (int var5 = 0; var5 < var4.size(); ++var5) {
            Object var6 = var4.get(var5);
            if (var6 instanceof NetServerHandler && Modchu_Reflect.getFieldObject(_player, var6) == var0) {
                var4.set(var5, var3);
                return true;
            }
        }
        return false;
    }

    public EntityPlayerMP getPlayer()
    {
        return playerEntity;
    }
}
