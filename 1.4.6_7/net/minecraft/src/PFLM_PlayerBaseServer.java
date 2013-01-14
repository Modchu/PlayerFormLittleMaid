package net.minecraft.src;

import java.util.List;
import java.util.logging.Logger;

import net.minecraft.server.MinecraftServer;

public class PFLM_PlayerBaseServer extends ServerPlayerBase
{
    public static PFLM_PlayerBaseServer gotcha;
    private boolean _hasReplacedNetServerHandler;
    public static boolean widthResetFlag = true;
    public static boolean heightResetFlag = true;
    public static boolean yOffsetResetFlag = true;
    public PFLM_EntityPlayer player = PFLM_EntityPlayer.gotcha;

    public static void registerPlayerBase()
    {
        ServerPlayerAPI.register("PlayerFormLittleMaid", PFLM_PlayerBaseServer.class);
    }

    public static PFLM_PlayerBaseServer getPlayerBase(Object var0)
    {
        return (PFLM_PlayerBaseServer)((EntityPlayerMP)var0).getServerPlayerBase("PlayerFormLittleMaid");
    }

    public PFLM_PlayerBaseServer(ServerPlayerAPI var1)
    {
        super(var1);
    }

    public float getHeight()
    {
    	if (PFLM_PlayerController.pflm_entityPlayer.gotcha != null) {
    		heightResetFlag = false;
    		return mod_PFLM_PlayerFormLittleMaid.getHeight();
    	}
    	heightResetFlag = true;
    	return 1.8F;
    }

    private float getWidth() {
    	if (PFLM_PlayerController.pflm_entityPlayer.gotcha != null) {
    		widthResetFlag = false;
    		return mod_PFLM_PlayerFormLittleMaid.getWidth();
    	}
    	widthResetFlag = true;
    	return 0.6F;
	}

	private float getyOffset() {
    	if (PFLM_PlayerController.pflm_entityPlayer.gotcha != null) {
    		yOffsetResetFlag = false;
    		return mod_PFLM_PlayerFormLittleMaid.getyOffset();
    	}
    	yOffsetResetFlag = true;
    	return 1.62F;
	}

    public double getMinY()
    {
        return this.player.boundingBox.minY;
    }

    public void setMaxY(double var1)
    {
        this.player.boundingBox.maxY = var1;
    }

    public void afterSetPosition(double var1, double var3, double var5)
    {
    	super.player.posX = var1;
    	super.player.posY = var3;
    	super.player.posZ = var5;
    }

    public void beforeIsPlayerSleeping()
    {
    }

    public void beforeOnUpdate()
    {
        if (!this._hasReplacedNetServerHandler)
        {
            this._hasReplacedNetServerHandler = PFLM_NetServerHandler.replace(super.player);
        }
        if(heightResetFlag) setHeight(getHeight());
        if(widthResetFlag) setWidth(getWidth());
        if(yOffsetResetFlag) setyOffset(getyOffset());

        if (PFLM_PlayerController.pflm_entityPlayer.gotcha != null) {
        	super.player.yOffset = PFLM_PlayerController.pflm_entityPlayer.gotcha.yOffset;
        	super.player.posX = PFLM_PlayerController.pflm_entityPlayer.gotcha.posX;
        	super.player.posY = PFLM_PlayerController.pflm_entityPlayer.gotcha.posY;
        	super.player.posZ = PFLM_PlayerController.pflm_entityPlayer.gotcha.posZ;
        }
        if (gotcha == null) {
           gotcha = this;
        }
        //Modchu_Debug.dDebug("player.posY="+player.posY,2);
    }

	public void afterOnUpdate()
    {
    }

    public void afterOnLivingUpdate()
    {
    }

    public int doGetHealth()
    {
        return this.player.getHealth();
    }

    public AxisAlignedBB getBox()
    {
        return this.player.boundingBox;
    }

    public AxisAlignedBB expandBox(AxisAlignedBB var1, double var2, double var4, double var6)
    {
        return var1.expand(var2, var4, var6);
    }

    public List getEntitiesExcludingPlayer(AxisAlignedBB var1)
    {
        return this.player.worldObj.getEntitiesWithinAABBExcludingEntity(this.player, var1);
    }

    public boolean isDeadEntity(Entity var1)
    {
        return var1.isDead;
    }

    public void onCollideWithPlayer(Entity var1)
    {
        var1.onCollideWithPlayer(this.player);
    }

    public float getEyeHeight()
    {
        return getHeight() - 0.18F;
    }

    public boolean isEntityInsideOpaqueBlock()
    {
    	if (this.player.sleeping) return false;
    	for (int var1 = 0; var1 < 8; ++var1)
        {
            float var2 = ((float)((var1 >> 0) % 2) - 0.5F) * this.player.width * 0.8F;
            float var3 = ((float)((var1 >> 1) % 2) - 0.5F) * 0.1F;
            float var4 = ((float)((var1 >> 2) % 2) - 0.5F) * this.player.width * 0.8F;
            int var5 = MathHelper.floor_double(this.player.posX + (double)var2);
            int var6 = MathHelper.floor_double(this.player.posY + (double)this.player.yOffset + (double)var3);
            int var7 = MathHelper.floor_double(this.player.posZ + (double)var4);

            Modchu_Debug.dDebug("isBlockNormalCube var6="+var6,4);
            if (this.player.worldObj.isBlockNormalCube(var5, var6, var7))
            {
                Modchu_Debug.mDebug("isBlockNormalCube getEyeHeight()="+getEyeHeight()+" var3="+var3);
                return true;
            }
        }

        return false;
    }

    public boolean localIsEntityInsideOpaqueBlock()
    {
        return super.isEntityInsideOpaqueBlock();
    }

    public void addExhaustion(float var1)
    {

    }

    public void localAddExhaustion(float var1)
    {
        super.addExhaustion(var1);
    }

    public void addMovementStat(double var1, double var3, double var5)
    {

    }

    public void localAddMovementStat(double var1, double var3, double var5)
    {
        super.addMovementStat(var1, var3, var5);
    }

    public void setHeight(float var1)
    {
        this.player.height = var1;
    }

    public void setWidth(float var1) {
        this.player.width = var1;
	}

    public void setyOffset(float var1) {
    	this.player.yOffset = var1;
	}

    public void sendPacket(byte[] var1)
    {
        Packet250CustomPayload var2 = new Packet250CustomPayload();
        var2.channel = "PFLM";
        var2.data = var1;
        var2.length = var1.length;
        this.sendPacket(var2);
    }

    public void sendPacket(Packet250CustomPayload var1)
    {
    	super.player.playerNetServerHandler.sendPacketToPlayer(var1);
    }

    public String getUsername()
    {
        return this.player.username;
    }

    public void resetFallDistance()
    {
        this.player.fallDistance = 0.0F;
        this.player.motionY = 0.08D;
    }

    public void sendPacketToTrackedPlayers(Packet250CustomPayload var1)
    {
        if (super.player.dimension >= 0)
        {
            super.player.mcServer.worldServers[super.player.dimension].getEntityTracker().sendPacketToAllPlayersTrackingEntity(super.player, var1);
        }
    }
}
