package net.minecraft.src;

import net.minecraft.client.Minecraft;
/*//FMLdelete
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
*///FMLdelete
public class PFLM_RenderGlobalAlt extends RenderGlobal
{
    public PFLM_RenderGlobalAlt(Minecraft minecraft, RenderEngine renderengine)
    {
        super(minecraft, renderengine);
    }

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    public void onEntityCreate(Entity entity)
    {
        entity.updateCloak();
        Minecraft minecraft = Minecraft.getMinecraft();

        if (entity.skinUrl != null)
        {
/*
        	boolean b;
        	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
        		b = PFLM_RenderPlayerSmart.isActivatedForPlayer((EntityPlayer)entity);
        	} else {
        		b = PFLM_RenderPlayer.isActivatedForPlayer((EntityPlayer)entity);
        	}
        	if ((entity instanceof EntityPlayer) && b)
            {
                minecraft.renderEngine.obtainImageData(entity.skinUrl, new ImageBufferDownloadAltPlayerFormLittleMaid());
            }
            else
            {
*/
                minecraft.renderEngine.obtainImageData(entity.skinUrl, new ImageBufferDownload());
//            }
        }

        if (entity.cloakUrl != null)
        {
            minecraft.renderEngine.obtainImageData(entity.cloakUrl, new ImageBufferDownload());
        }
    }

    /**
     * Draws the selection box for the player. Args: entityPlayer, rayTraceHit, i, itemStack, partialTickTime
     */
    public void drawSelectionBox(EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f)
    {
        super.drawSelectionBox(entityplayer, movingobjectposition, i, itemstack, f);
    }

    public boolean hasCloudFog(double d, double d1, double d2, float f)
    {
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        float f1 = ((EntityPlayer)(entityplayersp)).yOffset - 1.62F;
        return super.hasCloudFog(d, d1 + (double)f1, d2, f);
    }
}
