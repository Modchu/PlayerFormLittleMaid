package net.minecraft.src;

public class PFLM_EntityPlayerDummy extends EntityLiving {

	public String armorPrefix[] = new String[] {"", "", "", ""};
	public Object textureModel[];
	public String textureName;
	public String textureArmor0[];
	public String textureArmor1[];
	public String textureArmorName;
	public boolean showArmor;
	public boolean others;
	public float modelScale;
	public int maidColor;

	public PFLM_EntityPlayerDummy(World world) {
		super(world);
		//Modchu_Debug.mDebug("new PFLM_EntityPlayerDummy()");
		skinUrl = null;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

	/*b181//*/@Override
	public int getMaxHealth() {
		return 0;
	}

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

	public void setTexture(String s) {
		texture = s;
	}

}
