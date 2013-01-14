package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PFLM_EntityPlayer extends EntityClientPlayerMP {

    public static PFLM_EntityPlayer gotcha;
	private boolean initFlag;
	// b173deletepublic static int sleepMotion = 0;

	public PFLM_EntityPlayer(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler) {
		super(par1Minecraft, par2World, par3Session, par4NetClientHandler);
		gotcha = this;
		initFlag = true;
		//Modchu_Debug.mDebug("PFLM_EntityPlayer");
	}

	public void wakeUpPlayer(boolean flag, boolean flag1, boolean flag2) {
		super.wakeUpPlayer(flag, flag1, flag2);
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.onlineMode
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			if (!flag && flag1 && flag2) {
				preparePlayerToSpawn();
			} else {
				resetHeight();
				setSize(0.6F, 1.8F);
			}
		}
		mod_PFLM_PlayerFormLittleMaid.setFirstPersonHandResetFlag(true);
		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
//-@-125
    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
    		PFLM_PlayerBaseServer.heightResetFlag = true;
    		PFLM_PlayerBaseServer.widthResetFlag = true;
    		PFLM_PlayerBaseServer.yOffsetResetFlag = true;
    	}
//@-@125
	}

	public void preparePlayerToSpawn() {
		super.preparePlayerToSpawn();
		resetHeight();
		setSize(0.6F, 1.8F);
		setEntityHealth(getMaxHealth());
		deathTime = 0;
		mod_PFLM_PlayerFormLittleMaid.setFirstPersonHandResetFlag(true);
		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
//-@-125
    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
    		PFLM_PlayerBaseServer.heightResetFlag = true;
    		PFLM_PlayerBaseServer.widthResetFlag = true;
    		PFLM_PlayerBaseServer.yOffsetResetFlag = true;
    	}
//@-@125
	}
/*
	public void setPosition(double par1, double par3, double par5)
    {
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        float var7 = this.width / 2.0F;
        float var8 = this.height;
        this.boundingBox.setBounds(par1 - (double)var7, par3 - (double)this.yOffset + (double)this.ySize, par5 - (double)var7, par1 + (double)var7, par3 - (double)this.yOffset + (double)this.ySize + (double)var8, par5 + (double)var7);
    }
*/
	public void setPositionCorrection(double par1, double par3, double par5) {
		Modchu_Debug.mDebug("setPositionCorrection height="+height);
		//yOffset = 1.62F;
		//height = 1.8F;
//-@-125
		if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
			super.setSize(0.6F, 1.8F);
			PFLM_PlayerBaseServer.heightResetFlag = true;
			PFLM_PlayerBaseServer.widthResetFlag = true;
		}
//@-@125
		setPosition(posX + par1, posY + par3, posZ + par5);
	}

	@Override
	protected void setSize(float f, float f1) {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving
				&& !mod_PFLM_PlayerFormLittleMaid.onlineMode) {
			if (mod_PFLM_PlayerFormLittleMaid.textureModel[0] != null) {
				// Modchu_Debug.mDebug("setSize Width="+mod_PFLM_PlayerFormLittleMaid.textureModel[0].getWidth()+" Height="+mod_PFLM_PlayerFormLittleMaid.textureModel[0].getHeight());
				if (isRiding()) {
					super.setSize(mod_PFLM_PlayerFormLittleMaid.getRidingWidth(),
							mod_PFLM_PlayerFormLittleMaid.getRidingHeight());
				} else {
					super.setSize(mod_PFLM_PlayerFormLittleMaid.getWidth(),
							mod_PFLM_PlayerFormLittleMaid.getHeight());
				}
			} else {
				// Modchu_Debug.mDebug("setSize Width=0.5 Height=1.35");
				super.setSize(0.5F, 1.35F);
			}
//-@-125
			PFLM_PlayerBaseServer.heightResetFlag = true;
			PFLM_PlayerBaseServer.widthResetFlag = true;
//@-@125
		} else {
			super.setSize(f, f1);
		}

	}

	public double getMountedYOffset() {
		// Modchu_Debug.mDebug("getMountedYOffset");
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.onlineMode
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			return (double) height * mod_PFLM_PlayerFormLittleMaid.getMountedYOffset();
		}
		return (double) height * 0.75D;
	}

	@Override
	protected void resetHeight() {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.onlineMode
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			yOffset = mod_PFLM_PlayerFormLittleMaid.getyOffset();
			// Modchu_Debug.mDebug("resetHeight yOffset="+yOffset);
			/*125//*/PFLM_PlayerBaseServer.yOffsetResetFlag = true;
			return;
		}
		yOffset = 1.62F;
	}

	@Override
	public double getYOffset() {
//-@-125
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.onlineMode
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving
				) {
			/*125//*/PFLM_PlayerBaseServer.yOffsetResetFlag = true;
			if (ridingEntity != null) {
				float f = mod_PFLM_PlayerFormLittleMaid.getRidingyOffset();
				//Modchu_Debug.dDebug("getYOffset isRiding() f="+f,3);
				//Modchu_Debug.dDebug("getYOffset ridingEntity="+ridingEntity,5);
				PFLM_PlayerBase.yOffsetResetFlag = true;
				return (double) (f);
			} else {
				float f = mod_PFLM_PlayerFormLittleMaid.getyOffset();
				//Modchu_Debug.dDebug("getYOffset f="+f,3);
				//Modchu_Debug.dDebug("getYOffset ridingEntity="+ridingEntity,5);
				return (double) (f);
			}
		}
		return (double) (yOffset - 0.5F);
//@-@125
/*//125delete
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.onlineMode
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			if (isRiding() && !worldObj.isRemote) {
				float f = yOffset + 0.2F;
				if (mod_PFLM_PlayerFormLittleMaid.textureModel[0] != null)
					f = ((ModelPlayerFormLittleMaid) mod_PFLM_PlayerFormLittleMaid.textureModel[0])
							.getRidingyOffset();
				// Modchu_Debug.mDebug("getYOffset isRiding() f="+f);
				return (double) (f);
			}
		}
		return (double) (yOffset - 0.5F);
*///125delete
	}

    /**
     * Sets the location and Yaw/Pitch of an entity in the world
     */
    public void setLocationAndAngles(double par1, double par3, double par5, float par7, float par8)
    {
    	super.setLocationAndAngles(par1, par3, par5, par7, par8);
    	if (initFlag) {
    		super.setSize(0.6F, 1.8F);
    		setPositionCorrection(0.0D ,mod_PFLM_PlayerFormLittleMaid.locationPositionCorrectionY ,0.0D);
    	}
    }
//-@-125
    public boolean interactWith(Entity par1Entity)
    {
    	if (!mod_PFLM_PlayerFormLittleMaid.isModelSize) {
    		return super.interactWith(par1Entity);
    	}
    	boolean b = false;
    	if (par1Entity instanceof EntityMinecart) {
    		yOffset = 1.62F;
    		//PFLM_PlayerBaseServer.gotcha.player.yOffset = 1.62F;
    		PFLM_PlayerBaseServer.yOffsetResetFlag = true;
    		if (par1Entity.interact(this)) return true;
    		b = true;
    	}

    	if (!b
    		&& par1Entity.interact(this))
        {
            return true;
        }
        else
        {
            ItemStack var2 = this.getCurrentEquippedItem();

            if (var2 != null && par1Entity instanceof EntityLiving)
            {
                if (this.capabilities.isCreativeMode)
                {
                    var2 = var2.copy();
                }

                if (var2.interactWith((EntityLiving)par1Entity))
                {
                    if (var2.stackSize <= 0 && !this.capabilities.isCreativeMode)
                    {
                        this.destroyCurrentEquippedItem();
                    }

                    return true;
                }
            }

            return false;
        }
    }

	public boolean pushOutOfBlocks(double d, double d1, double d2) {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize) {
			int i = MathHelper.floor_double(d);
			int j = MathHelper.floor_double(d1);
			int k = MathHelper.floor_double(d2);
			double d3 = d - (double) i;
			double d4 = d2 - (double) k;
			double d7 = d1 - (double) j;
			int j1 = (int) (mod_PFLM_PlayerFormLittleMaid.getHeight());
			//int j2 = j - (int) (MathHelper.floor_double(d1) + 0.5D);
			boolean g = worldObj.isBlockNormalCube(i, j + j1, k);
			//Modchu_Debug.dDebug("pushOutOfBlocks j1="+j1+" g="+g+" j="+j+" boundingBox.minY="+boundingBox.minY);
			if (g) {
				boolean flag = true;
				boolean flag1 = true;
				boolean flag2 = true;
				boolean flag3 = true;
				boolean flag4 = true;
				int n;
				for (n = j1; n > -1 && flag == true; n = n - 1) {
					flag = !worldObj.isBlockNormalCube(i - 1, j + n, k);
					// System.out.println("pushOutOfBlocks n="+n);
				}
				// flag = n == -1 ? false : true;
				for (n = j1; n > -1 && flag1 == true; n = n - 1) {
					flag1 = !worldObj.isBlockNormalCube(i + 1, j + n, k);
				}
				// flag1 = n == -1 ? false : true;
				for (n = j1; n > -1 && flag2 == true; n = n - 1) {
					flag2 = !worldObj.isBlockNormalCube(i, j + n, k - 1);
				}
				// flag2 = n == -1 ? false : true;
				for (n = j1; n > -1 && flag3 == true; n = n - 1) {
					flag3 = !worldObj.isBlockNormalCube(i, j + n, k + 1);
				}
				// flag3 = n == -1 ? false : true;
				flag4 = worldObj.isBlockNormalCube(i, j + n - 1, k);
				//Modchu_Debug.dDebug("pushOutOfBlocks flag="+flag+" flag1="+flag1+" flag2="+flag2+" flag3="+flag3,1);
				byte byte0 = -1;
				double d5 = 9999D;
				if (flag && d3 < d5) {
					d5 = d3;
					byte0 = 0;
				}
				if (flag1 && 1.0D - d3 < d5) {
					d5 = 1.0D - d3;
					byte0 = 1;
				}
				if (flag2 && d4 < d5) {
					d5 = d4;
					byte0 = 4;
				}
				if (flag3 && 1.0D - d4 < d5) {
					double d6 = 1.0D - d4;
					byte0 = 5;
				}
				if (flag4 && 1.0D - d7 < d5) {
					d5 = 1.0D - d7;
					byte0 = 6;
				}
				float f = 0.1F;
				if (byte0 == 0) {
					motionX = -f;
				}
				if (byte0 == 1) {
					motionX = f;
				}
				if (byte0 == 4) {
					motionZ = -f;
				}
				if (byte0 == 5) {
					motionZ = f;
				}
				if (byte0 == 6) {
					motionY = -f;
				}
				return flag;
			}
		} else {
			return super.pushOutOfBlocks(d, d1, d2);
		}
		return false;
	}

    public boolean isEntityInsideOpaqueBlock()
    {
		return false;
    }

//@-@125
/*
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (this.capabilities.disableDamage && !par1DamageSource.canHarmInCreative())
        {
        	return false;
        }
        else
        {
            this.entityAge = 0;

            if (this.getHealth() <= 0)
            {
                return false;
            }
            else
            {
                if (this.isPlayerSleeping() && !this.worldObj.isRemote)
                {
                    this.wakeUpPlayer(true, true, false);
                }

                Entity var3 = par1DamageSource.getEntity();

                if (par1DamageSource.func_76350_n())
                {
                    if (this.worldObj.difficultySetting == 0)
                    {
                        par2 = 0;
                    }

                    if (this.worldObj.difficultySetting == 1)
                    {
                        par2 = par2 / 2 + 1;
                    }

                    if (this.worldObj.difficultySetting == 3)
                    {
                        par2 = par2 * 3 / 2;
                    }
                }

                if (par2 == 0)
                {
                    return false;
                }
                else
                {
                    Entity var4 = par1DamageSource.getEntity();

                    if (var4 instanceof EntityArrow && ((EntityArrow)var4).shootingEntity != null)
                    {
                        var4 = ((EntityArrow)var4).shootingEntity;
                    }

                    if (var4 instanceof EntityLiving)
                    {
                        this.alertWolves((EntityLiving)var4, false);
                    }

                	Modchu_Debug.mDebug("@@");
                	localAddStat(StatList.damageTakenStat, par2);
                    return superattackEntityFrom(par1DamageSource, par2);
                }
            }
        }
    }

    public boolean superattackEntityFrom(DamageSource par1DamageSource, int par2)
    {
    	//if (!this.worldObj.isRemote) return false;
    	setBeenAttacked();
    	return false;
    	/*
        this.entityAge = 0;

        if (this.health <= 0)
        {
            return false;
        }
        else if (par1DamageSource.fireDamage() && this.isPotionActive(Potion.fireResistance))
        {
            return false;
        }
        else
        {
            this.legYaw = 1.5F;
            boolean var3 = true;

            if ((float)this.hurtResistantTime > (float)this.maxHurtResistantTime / 2.0F)
            {
                if (par2 <= this.lastDamage)
                {
                    return false;
                }

                this.damageEntity(par1DamageSource, par2 - this.lastDamage);
                this.lastDamage = par2;
                var3 = false;
            }
            else
            {
                this.lastDamage = par2;
                this.prevHealth = this.health;
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.damageEntity(par1DamageSource, par2);
                this.hurtTime = this.maxHurtTime = 10;
            }

            this.attackedAtYaw = 0.0F;
            Entity var4 = par1DamageSource.getEntity();

            if (var4 != null)
            {
                if (var4 instanceof EntityLiving)
                {
                    this.setRevengeTarget((EntityLiving)var4);
                }

                if (var4 instanceof EntityPlayer)
                {
                    this.recentlyHit = 60;
                    this.attackingPlayer = (EntityPlayer)var4;
                }
                else if (var4 instanceof EntityWolf)
                {
                    EntityWolf var5 = (EntityWolf)var4;

                    if (var5.isTamed())
                    {
                        this.recentlyHit = 60;
                        this.attackingPlayer = null;
                    }
                }
            }

            if (var3)
            {
                this.worldObj.setEntityState(this, (byte)2);

                if (par1DamageSource != DamageSource.drown && par1DamageSource != DamageSource.field_76375_l)
                {
                    this.setBeenAttacked();
                }

                if (var4 != null)
                {
                    double var9 = var4.posX - this.posX;
                    double var7;

                    for (var7 = var4.posZ - this.posZ; var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D)
                    {
                        var9 = (Math.random() - Math.random()) * 0.01D;
                    }

                    this.attackedAtYaw = (float)(Math.atan2(var7, var9) * 180.0D / Math.PI) - this.rotationYaw;
                    this.knockBack(var4, par2, var9, var7);
                }
                else
                {
                    this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
                }
            }

            if (this.health <= 0)
            {
                if (var3)
                {
                    //this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
                }

                this.onDeath(par1DamageSource);
            }
            else if (var3)
            {
                //this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
            }

            return true;
        }
    }
*/
/*//b181delete
	@Override
	public String getHurtSound() {
		return mod_PFLM_PlayerFormLittleMaid.Physical_HurtSound;
	}

    public int getMaxHealth()
    {
        return 20;
    }

    public void setEntityHealth(int par1)
    {
        this.health = par1;

        if (par1 > this.getMaxHealth())
        {
            par1 = this.getMaxHealth();
        }
    }

    public void copyPlayer(EntityPlayer entityplayer)
    {
        copyInventory(entityplayer.inventory);
        health = entityplayer.health;
//-@-b173
        foodStats = entityplayer.foodStats;
        playerLevel = entityplayer.playerLevel;
        totalXP = entityplayer.totalXP;
        currentXP = entityplayer.currentXP;
//@-@b173
        score = entityplayer.score;
    }

    public void copyInventory(InventoryPlayer inventoryplayer)
    {
        for(int i = 0; i < inventoryplayer.mainInventory.length; i++)
        {
        	inventory.mainInventory[i] = ItemStack.copyItemStack(inventoryplayer.mainInventory[i]);
        }

        for(int j = 0; j < inventoryplayer.armorInventory.length; j++)
        {
        	inventory.armorInventory[j] = ItemStack.copyItemStack(inventoryplayer.armorInventory[j]);
        }
    }
*///b181delete
/*//b173delete
	public void setSleepMotion(int i) {
		sleepMotion = i;
	}

	public int getSleepMotion() {
		return sleepMotion;
	}
*///b173delete
}
