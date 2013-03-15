package net.minecraft.src;

import net.minecraft.client.Minecraft;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class PFLM_PlayerBase extends PlayerBase {

	public static boolean initFlag = false;
	public static Minecraft mc = ModLoader.getMinecraftInstance();

	public PFLM_PlayerBase(PlayerAPI playerapi) {
		super(playerapi);
		mod_PFLM_PlayerFormLittleMaid.gotcha = this;
		mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster = new PFLM_EntityPlayerMaster(player, this);
	}

	public void init() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.init();
	}
/*
	@Override
	public void afterLocalConstructing(Minecraft minecraft, World world,
			Session session, int i) {
	}
*/
	@Override
	public void beforeOnLivingUpdate() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.onLivingUpdate();
	}

	@Override
	public void moveEntityWithHeading(float f, float f1) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.moveEntityWithHeading(f, f1);
		super.moveEntityWithHeading(f, f1);
	}
//-@-b181
	@Override
	public String getHurtSound() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) return mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.getHurtSound();
		return super.getHurtSound();
	}
//@-@b181
	public void setPlayerTexture(String s) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.setPlayerTexture(s);
	}

	protected void supersetSize(float f, float f1) {
		player.setSize(f, f1);
	}
/*
    public MovingObjectPosition rayTrace(double par1, float par3)
    {
        Vec3D var4 = player.getPosition(par3);
        Vec3D var5 = player.getLook(par3);
        float f1 = 1.62F - 1.17F + 0.6F - (player.isRiding() ? 1.0F : 0.0F);
        Vec3D var6 = var4.addVector(var5.xCoord * par1, var5.yCoord * par1 - f1, var5.zCoord * par1);
        return player.worldObj.rayTraceBlocks(var4, var6);
    }
*/
/*//b173delete
	public boolean handleKeyPress(int i, boolean flag)
    {
		keyBindForwardPressed = i == mc.gameSettings.keyBindForward.keyCode ? true : false ;
		keyBindBackPressed = i == mc.gameSettings.keyBindBack.keyCode ? true : false ;
		keyBindLeftPressed = i == mc.gameSettings.keyBindLeft.keyCode ? true : false ;
		keyBindRightPressed = i == mc.gameSettings.keyBindRight.keyCode ? true : false ;
		return false;
    }
*///b173delete
}
