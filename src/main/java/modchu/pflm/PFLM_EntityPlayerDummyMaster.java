package modchu.pflm;

import modchu.lib.Modchu_IEntityLivingMaster;

public class PFLM_EntityPlayerDummyMaster implements Modchu_IEntityLivingMaster {
	public boolean initFlag;
	public Object popWorld;

	public PFLM_EntityPlayerDummyMaster(Object world) {
		popWorld = world;
	}

	@Override
	public void init() {
		if (initFlag) return;
		initFlag = true;
		setMaxHealth(0.0D);
	}

	@Override
	public void preparePlayerToSpawn() {
	}

	@Override
	public boolean onLivingUpdate() {
		return true;
	}

	@Override
	public float[] moveEntityWithHeading(float f, float f1) {
		return new float[]{ f, f1 };
	}

	@Override
	public String getHurtSound() {
		return null;
	}

	@Override
	public Object getCreatureAttribute() {
		return null;
	}

	@Override
	public float[] setSize(float f, float f1) {
		return new float[]{ f, f1 };
	}

	@Override
	public double getMountedYOffset() {
		return 0;
	}

	@Override
	public double getYOffset() {
		return 0;
	}

	@Override
	public boolean pushOutOfBlocks(double d, double d1, double d2) {
		return false;
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return false;
	}

	@Override
	public Object copyPlayer(Object entityplayer) {
		return entityplayer;
	}

	@Override
	public Object copyInventory(Object inventoryplayer) {
		return inventoryplayer;
	}

	@Override
	public void resetHeight() {
	}

	@Override
	public void updateRidden() {
	}

	@Override
	public boolean attackEntityFrom(Object var1, Object par1DamageSource, int par2) {
		return false;
	}

	@Override
	public boolean attackEntityFrom(Object par1DamageSource, int par2) {
		return false;
	}

	@Override
	public void onDeath(Object par1DamageSource) {
	}

	@Override
	public int setHealth(int i) {
		return i;
	}

	@Override
	public float getHealth() {
		return 0.0F;
	}

	@Override
	public float setHealth(float f) {
		return f;
	}

	@Override
	public double setMaxHealth(double d) {
		return d;
	}

	@Override
	public float getShadowSize() {
		return 0.0F;
	}

}
