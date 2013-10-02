package net.minecraft.src;

public class PFLM_EntityPlayerDummy extends EntityLiving {

	public PFLM_EntityPlayerDummy(World world) {
		super(world);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(0.0D);
		//Modchu_Debug.mDebug("new PFLM_EntityPlayerDummy()");
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}
/*
	public int getMaxHealth() {
		return 0;
	}
*/
	@Override
	public float getShadowSize()
	{
		return 0.0F;
	}

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);
    }
}
