package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_ItemRendererV160 extends Modchu_ItemRenderer {

	public PFLM_ItemRendererMaster pflm_ItemRendererMaster;

    public PFLM_ItemRendererV160(Minecraft minecraft) {
    	super(minecraft);
    	pflm_ItemRendererMaster = new PFLM_ItemRendererMaster();
    	pflm_ItemRendererMaster.mapItemRenderer = mapItemRenderer;
    }

    public void renderItemInFirstPerson(float f) {
    	ItemStack var7 = mc.thePlayer.inventory.getCurrentItem();
    	if (var7 != null) {
    		if (var7.itemID == Item.map.itemID) {
    			super.renderItemInFirstPerson(f);
    			return;
    		}
    		if (pflm_ItemRendererMaster.flipHorizontal) {
    			//GL11.glPushMatrix();
    			GL11.glScalef(-1.0F, 1.0F, 1.0F);
    			//GL11.glPopMatrix();
    		}
    	} else {
    		pflm_ItemRendererMaster.renderItemInFirstPersonHand(f);
    		return;
    	}
    	//Modchu_Debug.mDebug("renderItemInFirstPerson");
    	super.renderItemInFirstPerson(f);
    }

    public void drawFirstPersonHand(Render r, int h)
    {
    	pflm_ItemRendererMaster.drawFirstPersonHand(r, h);
    }
}