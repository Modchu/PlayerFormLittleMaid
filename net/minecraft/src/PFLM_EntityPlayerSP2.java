package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class PFLM_EntityPlayerSP2 extends EntityPlayerSP2 {

	public static PFLM_EntityPlayerSP pflm_entityplayersp;
/*//b173delete
	public static int sleepMotion = 0;
*///b173delete

    public PFLM_EntityPlayerSP2(Minecraft par1Minecraft, World par2World, Session par3Session, int var4) {
		super(par1Minecraft, par2World, par3Session, var4);
		pflm_entityplayersp = new PFLM_EntityPlayerSP(par1Minecraft, par2World, par3Session, null);
	}

	public void wakeUpPlayer(boolean flag, boolean flag1, boolean flag2) {
		pflm_entityplayersp.wakeUpPlayer(flag, flag1, flag2);
	}

	protected boolean pushOutOfBlocks(double par1, double par3, double par5) {
		return pflm_entityplayersp.pushOutOfBlocks(par1, par3, par5);
	}

	public void preparePlayerToSpawn() {
		pflm_entityplayersp.preparePlayerToSpawn();
	}

	public void setPositionCorrection(double par1, double par3, double par5) {
		pflm_entityplayersp.setPositionCorrection(par1, par3, par5);
	}

	public void onLivingUpdate() {
		pflm_entityplayersp.onLivingUpdate();
	}

	public void moveEntityWithHeading(float f, float f1) {
		pflm_entityplayersp.moveEntityWithHeading(f, f1);
	}

	@Override
	public String getHurtSound() {
		return pflm_entityplayersp.getHurtSound();
	}
//-@-b181
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return pflm_entityplayersp.getCreatureAttribute();
	}
//@-@b181

	@Override
	protected void setSize(float f, float f1) {
		pflm_entityplayersp.setSize(f, f1);
	}

	public double getMountedYOffset() {
		return pflm_entityplayersp.getMountedYOffset();
	}

	protected void resetHeight() {
		pflm_entityplayersp.resetHeight();
	}

	public double getYOffset() {
		return pflm_entityplayersp.getYOffset();
	}

	/**
	 * Sets the location and Yaw/Pitch of an entity in the world
	 */
	public void setLocationAndAngles(double par1, double par3, double par5,
			float par7, float par8) {
		pflm_entityplayersp.setLocationAndAngles(par1, par3, par5, par7, par8);
	}

    public void setPlayerTexture(String s) {
    	pflm_entityplayersp.setPlayerTexture(s);
    }

    public void copyPlayer(EntityPlayer entityplayer) {
    	pflm_entityplayersp.copyPlayer(entityplayer);
    }

    public void copyInventory(InventoryPlayer inventoryplayer) {
    	pflm_entityplayersp.copyInventory(inventoryplayer);
    }

	public void publicResetHeight() {
		pflm_entityplayersp.publicResetHeight();
	}

	public void publicSetSize(float f, float f1) {
		pflm_entityplayersp.publicSetSize(f, f1);
	}
}
