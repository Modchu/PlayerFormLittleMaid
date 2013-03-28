package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PFLM_EntityPlayerSP extends EntityClientPlayerMP {

	public boolean initFlag;

    public PFLM_EntityPlayerSP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler) {
		super(par1Minecraft, par2World, par3Session, par4NetClientHandler);
		/*125//*/sendQueue = par4NetClientHandler;
		mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster = new PFLM_EntityPlayerMaster(this);
	}

	public void init() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.init();
	}

	@Override
	public void wakeUpPlayer(boolean flag, boolean flag1, boolean flag2) {
		super.wakeUpPlayer(flag, flag1, flag2);
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.wakeUpPlayer(flag, flag1, flag2);
	}

	@Override
	public void preparePlayerToSpawn() {
		super.preparePlayerToSpawn();
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.preparePlayerToSpawn();
	}

	public void setPositionCorrection(double par1, double par3, double par5) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.setPositionCorrection(par1, par3, par5);
	}

	public void onLivingUpdate() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.onLivingUpdate();
		super.onLivingUpdate();
	}

	public void moveEntityWithHeading(float f, float f1) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.moveEntityWithHeading(f, f1);
		super.moveEntityWithHeading(f, f1);
	}

	@Override
	public String getHurtSound() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) return mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.getHurtSound();
		return super.getHurtSound();
	}
//-@-b181
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) return mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.getCreatureAttribute();
		return super.getCreatureAttribute();
	}

	public EnumCreatureAttribute supergetCreatureAttribute() {
		return super.getCreatureAttribute();
	}
//@-@b181

	@Override
	protected void setSize(float f, float f1) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.setSize(f, f1);
	}

	protected void supersetSize(float f, float f1) {
		super.setSize(f, f1);
	}

	@Override
	public double getMountedYOffset() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) return mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.getMountedYOffset();
		return super.getMountedYOffset();
	}

	@Override
	protected void resetHeight() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.resetHeight();
	}

	@Override
	public double getYOffset() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) return mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.getYOffset();
		return super.getYOffset();
	}

	@Override
	public boolean pushOutOfBlocks(double d, double d1, double d2) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) return mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.pushOutOfBlocks(d, d1, d2);
		return super.pushOutOfBlocks(d, d1, d2);
	}

	public boolean superpushOutOfBlocks(double d, double d1, double d2) {
		return super.pushOutOfBlocks(d, d1, d2);
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) return mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.isEntityInsideOpaqueBlock();
		return super.isEntityInsideOpaqueBlock();
	}

	public void setPlayerTexture(String s) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.setPlayerTexture(s);
	}

	public void copyPlayer(EntityPlayer entityplayer) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.copyPlayer(entityplayer);
	}
/*//125delete
	public void supercopyPlayer(EntityPlayer entityplayer) {
		super.copyPlayer(entityplayer);
	}
*///125delete
	public void copyInventory(InventoryPlayer inventoryplayer) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.copyInventory(inventoryplayer);
	}

	public void publicResetHeight() {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.resetHeight();
	}

	public void publicSetSize(float f, float f1) {
		if (mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster != null) mod_PFLM_PlayerFormLittleMaid.entityPlayerMaster.setSize(f, f1);
	}
/*
    @Override
    public void updateRidden() {
		entityPlayerMaster.updateRidden();
   	}

    private void addMountedMovementStat(double d, double d1, double d2) {
		entityPlayerMaster.addMountedMovementStat(d, d1, d2);
    }

    public boolean attackEntityFrom(Entity var1, DamageSource par1DamageSource, int par2) {
    	return entityPlayerMaster.attackEntityFrom(var1, par1DamageSource, par2);
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
    	return entityPlayerMaster.attackEntityFrom(par1DamageSource, par2);
    }

    public boolean superattackEntityFrom(DamageSource par1DamageSource, int par2) {
    	return entityPlayerMaster.superattackEntityFrom(par1DamageSource, par2);
    }

    public void onDeath(DamageSource par1DamageSource) {
    	entityPlayerMaster.onDeath(par1DamageSource);
    }

    public void superonDeath(DamageSource par1DamageSource) {
    	entityPlayerMaster.superonDeath(par1DamageSource);
    }

    public void setHealth(int par1) {
    	entityPlayerMaster.setHealth(par1);
    }

    public void sendMotionUpdates() {
    	entityPlayerMaster.sendMotionUpdates();
    }
*/
/*//b181delete
    public int getMaxHealth() {
    	return entityPlayerMaster.getMaxHealth();
    }

    public int getEntityHealth() {
        return entityPlayerMaster.getEntityHealth();
    }

    public void setEntityHealth(int i) {
    	entityPlayerMaster.setEntityHealth(i);
    }
*///b181delete
/*//b173delete
	public void setSleepMotion(int i) {
		entityPlayerMaster.setSleepMotion(i);
	}

	public int getSleepMotion() {
		return entityPlayerMaster.getSleepMotion();
	}
*///b173delete
}
