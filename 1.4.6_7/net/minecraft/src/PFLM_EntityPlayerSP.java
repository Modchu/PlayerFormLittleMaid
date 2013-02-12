package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class PFLM_EntityPlayerSP extends EntityClientPlayerMP {

    private ChunkCoordinates startMinecartRidingCoordinate;
	private boolean initFlag;
	private boolean isRidingCorrectionFlag = false;
	private Random rnd;
	private Class EntityPlayerSP2;

    public PFLM_EntityPlayerSP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler) {
		super(par1Minecraft, par2World, par3Session, par4NetClientHandler);
		/*125//*/sendQueue = par4NetClientHandler;
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm) {
			rand = new Random();
			mod_PFLM_PlayerFormLittleMaid.gotcha = this;
			if (mod_PFLM_PlayerFormLittleMaid.textureName == null) {
				if (mod_PFLM_PlayerFormLittleMaid.textureName == null) {
					mod_PFLM_PlayerFormLittleMaid.textureName = "default";
				} else {
					mod_PFLM_PlayerFormLittleMaid.setTextureName(mod_PFLM_PlayerFormLittleMaid.textureName);
				}
			}
			if (mod_PFLM_PlayerFormLittleMaid.textureArmorName == null) {
				if (mod_PFLM_PlayerFormLittleMaid.textureArmorName == null) {
					mod_PFLM_PlayerFormLittleMaid.setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.textureName);
				} else {
					mod_PFLM_PlayerFormLittleMaid.setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.textureArmorName);
				}
			}
			mod_PFLM_PlayerFormLittleMaid.setMaidColor(mod_PFLM_PlayerFormLittleMaid.maidColor);
			mod_PFLM_PlayerFormLittleMaid.setTextureValue();
			if (mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline) {
				skinUrl = null;
				setPlayerTexture(mod_PFLM_PlayerFormLittleMaid.texture);
			}
			// System.out.println("PFLM_EntityPlayerSP mod_PFLM_PlayerFormLittleMaid.texture="+mod_PFLM_PlayerFormLittleMaid.texture);
			if (mod_PFLM_PlayerFormLittleMaid.isModelSize
					&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline) {
				setSize(0.6F, 1.8F);
				resetHeight();
			}
			PFLM_Gui.partsSetFlag = 1;
		}
		initFlag = true;
		mod_PFLM_PlayerFormLittleMaid.setFirstPersonHandResetFlag(true);
		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
		rnd = new Random();
/*//b173delete
		keyBindForward = mc.gameSettings.keyBindForward;
		keyBindBack = mc.gameSettings.keyBindBack;
		keyBindLeft = mc.gameSettings.keyBindLeft;
		keyBindRight = mc.gameSettings.keyBindRight;
*///b173delete
		EntityPlayerSP2 = Modchu_Reflect.loadClass("EntityPlayerSP2", false);
	}

	public void wakeUpPlayer(boolean flag, boolean flag1, boolean flag2) {
		super.wakeUpPlayer(flag, flag1, flag2);
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
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
	}

	protected boolean pushOutOfBlocks(double par1, double par3, double par5) {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			int i = MathHelper.floor_double(par1);
			int j = MathHelper.floor_double(par3);
			int k = MathHelper.floor_double(par5);
			double d = par1 - (double) i;
			double d1 = par3 - (double) j;
			double d2 = par5 - (double) k;
			int j1 = 0;
			j1 = (int) mod_PFLM_PlayerFormLittleMaid.getHeight();
			boolean g = worldObj.isBlockNormalCube(i, j + j1, k);
			if (g) {
				boolean flag = true;
				boolean flag1 = true;
				boolean flag2 = true;
				boolean flag3 = true;
				boolean flag4 = true;
				boolean flag5 = true;
				int n;
				for (n = j1; n > -1 && flag == true; n = n - 1) {
					flag = !worldObj.isBlockNormalCube(i - 1, j + n, k);
				}
				for (n = j1; n > -1 && flag == true; n = n - 1) {
					flag1 = !worldObj.isBlockNormalCube(i + 1, j + n, k);
				}
				for (n = j1; n > -1 && flag == true; n = n - 1) {
					flag2 = !worldObj.isBlockNormalCube(i, j - 1 + n, k);
				}
				for (n = j1; n > -1 && flag == true; n = n - 1) {
					flag3 = !worldObj.isBlockNormalCube(i, j + 1 + n, k);
				}
				for (n = j1; n > -1 && flag == true; n = n - 1) {
					flag4 = !worldObj.isBlockNormalCube(i, j + n, k - 1);
				}
				for (n = j1; n > -1 && flag == true; n = n - 1) {
					flag5 = !worldObj.isBlockNormalCube(i, j + n, k + 1);
				}
				byte byte0 = -1;
				double d3 = 9999D;

				if (flag && d < d3) {
					d3 = d;
					byte0 = 0;
				}

				if (flag1 && 1.0D - d < d3) {
					d3 = 1.0D - d;
					byte0 = 1;
				}

				if (flag2 && d1 < d3) {
					d3 = d1;
					byte0 = 2;
				}

				if (flag3 && 1.0D - d1 < d3) {
					d3 = 1.0D - d1;
					byte0 = 3;
				}

				if (flag4 && d2 < d3) {
					d3 = d2;
					byte0 = 4;
				}

				if (flag5 && 1.0D - d2 < d3) {
					double d4 = 1.0D - d2;
					byte0 = 5;
				}

				float f = rand.nextFloat() * 0.2F + 0.1F;

				if (byte0 == 0) {
					motionX = -f;
				}

				if (byte0 == 1) {
					motionX = f;
				}

				if (byte0 == 2) {
					motionY = -f;
				}

				if (byte0 == 3) {
					motionY = f;
				}

				if (byte0 == 4) {
					motionZ = -f;
				}

				if (byte0 == 5) {
					motionZ = f;
				}

				return true;
			}
		} else {
			super.pushOutOfBlocks(par1, par3, par5);
		}
		return false;
	}
/*
    @Override
    public void updateRidden()
    {
        double d = posX;
        double d1 = posY;
        double d2 = posZ;
        super.updateRidden();
        prevCameraYaw = cameraYaw;
        cameraYaw = 0.0F;
    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
    		//System.out.println("updateRidden");
            addMountedMovementStat(posX - d, posY - d1 - 1.0D, posZ - d2);
    	} else {
    		addMountedMovementStat(posX - d, posY - d1, posZ - d2);
    	}
   	}
*/
/*
    private void addMountedMovementStat(double d, double d1, double d2)
    {
        if (ridingEntity != null)
        {
            int i = Math.round(MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2) * 100F);
            if (i > 0)
            {
                if (ridingEntity instanceof EntityMinecart)
                {
                    addStat(StatList.distanceByMinecartStat, i);
                    if (startMinecartRidingCoordinate == null)
                    {
                        startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
                    }
                    else if (startMinecartRidingCoordinate.getEuclideanDistanceTo(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) >= 1000D)
                    {
                        addStat(AchievementList.onARail, 1);
                    }
                }
                else if (ridingEntity instanceof EntityBoat)
                {
                    addStat(StatList.distanceByBoatStat, i);
                }
                else if (ridingEntity instanceof EntityPig)
                {
                    addStat(StatList.distanceByPigStat, i);
                }
            }
        }
    }
*/
    public void preparePlayerToSpawn()
    {
		super.preparePlayerToSpawn();
		resetHeight();
		setSize(0.6F, 1.8F);
		if (mod_PFLM_PlayerFormLittleMaid.isModelSize) setPositionCorrection(0.0D ,mod_PFLM_PlayerFormLittleMaid.locationPositionCorrectionY ,0.0D);
		setEntityHealth(getMaxHealth());
		deathTime = 0;
		mod_PFLM_PlayerFormLittleMaid.setFirstPersonHandResetFlag(true);
		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
    }

	public void setPositionCorrection(double par1, double par3, double par5) {
		setPosition(posX + par1, posY + par3, posZ + par5);
	}

	public void onLivingUpdate() {
		//if (!worldObj.isRemote) {
			if (mod_PFLM_PlayerFormLittleMaid.Physical_BurningPlayer > 0) {
				if (worldObj.getWorldInfo().getWorldTime() / 12000 % 2 == 0
						&& health > 0) {
					float f = getBrightness(1.0F);
					if (f > 0.5F
							&& worldObj.canBlockSeeTheSky(
									MathHelper.floor_double(posX),
									MathHelper.floor_double(posY),
									MathHelper.floor_double(posZ))
								&& rand.nextFloat() * 30F < (f - 0.4F) * 2.0F) {
						setFire(mod_PFLM_PlayerFormLittleMaid.Physical_BurningPlayer);
						//damageEntity(DamageSource.inFire, 1);
						//attackEntityFrom(this, DamageSource.inFire, 1);
					}
				}
			}
			if (mod_PFLM_PlayerFormLittleMaid.Physical_MeltingPlayer > 0 && isWet()) {
				attackEntityFrom(DamageSource.drown,
						mod_PFLM_PlayerFormLittleMaid.Physical_MeltingPlayer);
			}
		//}
		//Modchu_Debug.mDebug("health="+health);
		if (!PFLM_Gui.guiMode
				&& PFLM_Gui.partsSetFlag == 2) {
			PFLM_Gui.partsSetFlag = 3;
			Modchu_Config.loadShowModelList(mod_PFLM_PlayerFormLittleMaid.showModelList);
			PFLM_Gui.showModelFlag = true;
		}
		if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
			resetHeight();
			if (isRiding()
					&& !isRidingCorrectionFlag) {
				isRidingCorrectionFlag = true;
				setPositionCorrection(0.0D ,mod_PFLM_PlayerFormLittleMaid.locationPositionCorrectionY ,0.0D);
			}
			if (!isRiding()
					&& isRidingCorrectionFlag) {
				isRidingCorrectionFlag = false;
			}
		}
		super.onLivingUpdate();
	}

	public void moveEntityWithHeading(float f, float f1) {
		if (!worldObj.isRemote && isInWater()) {
			float f2 = mod_PFLM_PlayerFormLittleMaid.getPhysical_Hammer();
			f *= f2;
			f1 *= f2;
			if (motionY > 0.0F) {
				motionY *= f2;
				if (motionY > 1.0F) {
					motionY = 1.0F;
				}
			}
			// System.out.println("moveEntityWithHeading f2="+f2+" f="+f+" f1="+f1+" motionY="+motionY);
		}
		super.moveEntityWithHeading(f, f1);
	}

	@Override
	public String getHurtSound() {
		return mod_PFLM_PlayerFormLittleMaid.Physical_HurtSound;
	}
//-@-b181
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		if (mod_PFLM_PlayerFormLittleMaid.Physical_Undead && !worldObj.isRemote) {
			return EnumCreatureAttribute.UNDEAD;
		} else {
			return super.getCreatureAttribute();
		}
	}
//@-@b181

	@Override
	protected void setSize(float f, float f1) {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline) {
			if (isRiding()) {
				super.setSize(mod_PFLM_PlayerFormLittleMaid.getRidingWidth(),
						mod_PFLM_PlayerFormLittleMaid.getRidingHeight());
				return;
			} else {
				super.setSize(mod_PFLM_PlayerFormLittleMaid.getWidth(),
						mod_PFLM_PlayerFormLittleMaid.getHeight());
				return;
			}
		} else {
			super.setSize(f, f1);
		}
	}

	public double getMountedYOffset() {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize) {
			return (double) height * mod_PFLM_PlayerFormLittleMaid.getMountedYOffset();
		}
		return (double) height * 0.75D;
	}

	protected void resetHeight() {
		boolean isRiding = false;
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm) {
			isRiding = mod_PFLM_PlayerFormLittleMaid.getIsRiding();
		}
		if (mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving
				&& !isRiding
				&& !isPlayerSleeping()) {
			if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm) {
				yOffset = mod_PFLM_PlayerFormLittleMaid.getyOffset();
				return;
			}
		}
		yOffset = 1.62F;
	}

	public double getYOffset() {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			if (isRiding() && !worldObj.isRemote) {
				float f = mod_PFLM_PlayerFormLittleMaid.getRidingyOffset();
			}
			setSize(0.5F, 1.35F);
		}
		return (double) (yOffset - 0.5F);
	}

	/**
	 * Sets the location and Yaw/Pitch of an entity in the world
	 */
	public void setLocationAndAngles(double par1, double par3, double par5,
			float par7, float par8) {
		if (initFlag) {
			setSize(0.6F, 1.8F);
			setPositionCorrection(0.0D ,mod_PFLM_PlayerFormLittleMaid.locationPositionCorrectionY ,0.0D);
		}
		super.setLocationAndAngles(par1, par3, par5, par7, par8);
	}

    public void setPlayerTexture(String s) {
    	texture = s;
    }

    public void copyPlayer(EntityPlayer entityplayer)
    {
        copyInventory(entityplayer.inventory);
        health = entityplayer.getHealth();
        foodStats = entityplayer.getFoodStats();
//-@-b173
/*//b181delete
        playerLevel = entityplayer.playerLevel;
        totalXP = entityplayer.totalXP;
        currentXP = entityplayer.currentXP;
        score = entityplayer.score;
*///b181delete
//@-@b173
//-@-125
        if (EntityPlayerSP2 != null) Modchu_Reflect.setFieldObject(this.getClass(), "score", entityplayer, Modchu_Reflect.getFieldObject(EntityPlayerSP2, "score", entityplayer));
//@-@125
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

	public void publicResetHeight() {
		resetHeight();
	}

	public void publicSetSize(float f, float f1) {
		setSize(f, f1);
	}

/*
    public boolean attackEntityFrom(Entity var1, DamageSource par1DamageSource, int par2)
    {
    	return var1.attackEntityFrom(par1DamageSource, par2);
    }
*/
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
            	onDeath(par1DamageSource);
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

                    this.addStat(StatList.damageTakenStat, par2);
                    return superattackEntityFrom(par1DamageSource, par2);
                }
            }
        }
    }

    public boolean superattackEntityFrom(DamageSource par1DamageSource, int par2)
    {
            this.entityAge = 0;

            if (getHealth() <= 0)
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
                    if (!worldObj.isRemote) this.damageEntity(par1DamageSource, par2 - this.lastDamage);
                    setHealth(health);
                    this.lastDamage = par2;
                    var3 = false;
                }
                else
                {
                    this.lastDamage = par2;
                    this.hurtResistantTime = this.maxHurtResistantTime;
                    if (!worldObj.isRemote) this.damageEntity(par1DamageSource, par2);
                    this.prevHealth = getHealth();
                    setHealth(getHealth());
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

                    onDeath(par1DamageSource);
                }
                else if (var3)
                {
                    //this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
                }

                return true;
            }
    }

    public void onDeath(DamageSource par1DamageSource)
    {
    	health = 0;
    	superonDeath(par1DamageSource);
    	//if (worldObj.isRemote) return;
        this.setSize(0.2F, 0.2F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612D;

        if (this.username.equals("Notch"))
        {
            this.dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
        }

        this.inventory.dropAllItems();

        if (par1DamageSource != null)
        {
            this.motionX = (double)(-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * (float)Math.PI / 180.0F) * 0.1F);
            this.motionZ = (double)(-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * (float)Math.PI / 180.0F) * 0.1F);
        }
        else
        {
            this.motionX = this.motionZ = 0.0D;
        }

        this.yOffset = 0.1F;
        this.addStat(StatList.deathsStat, 1);
    }

    public void superonDeath(DamageSource par1DamageSource)
    {
    	//if (!worldObj.isRemote) return;
            Entity var2 = par1DamageSource.getEntity();

            if (this.scoreValue >= 0 && var2 != null)
            {
                var2.addToPlayerScore(this, this.scoreValue);
            }

            if (var2 != null)
            {
                var2.onKillEntity(this);
            }

            this.dead = true;

                int var3 = 0;

                if (var2 instanceof EntityPlayer)
                {
                    var3 = EnchantmentHelper.getLootingModifier(((EntityPlayer)var2).inventory);
                }

                int var5 = 0;

                if (!this.isChild())
                {
                    this.dropFewItems(this.recentlyHit > 0, var3);

                    if (this.recentlyHit > 0)
                    {
                        var5 = this.rand.nextInt(200) - var3;

                        if (var5 < 5)
                        {
                            this.dropRareDrop(var5 <= 0 ? 1 : 0);
                        }
                    }
                }

            this.worldObj.setEntityState(this, (byte)3);
    }

    public void setHealth(int par1)
    {
        int var2 = this.getHealth() - par1;
        if (var2 <= 0)
        {
            this.setEntityHealth(par1);

            if (var2 < 0)
            {
                this.hurtResistantTime = this.maxHurtResistantTime / 2;
            }
        }
        else
        {
            this.lastDamage = var2;
            this.setEntityHealth(this.getHealth());
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(DamageSource.generic, var2);
            this.hurtTime = this.maxHurtTime = 10;
        }
    }

/*
    public void sendMotionUpdates()
    {
        if (inventoryUpdateTickCounter++ == 20)
        {
            inventoryUpdateTickCounter = 0;
        }

        boolean flag = isSprinting();

        if (flag != wasSneaking)
        {
            if (flag)
            {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
            }
            else
            {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
            }

            wasSneaking = flag;
        }

        boolean flag1 = isSneaking();

        if (flag1 != shouldStopSneaking)
        {
            if (flag1)
            {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
            }
            else
            {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
            }

            shouldStopSneaking = flag1;
        }

        double d = posX - oldPosX;
        double d1 = boundingBox.minY - oldMinY;
        double d2 = posY - oldPosY;
        double d3 = posZ - oldPosZ;
        double d4 = rotationYaw - oldRotationYaw;
        double d5 = rotationPitch - oldRotationPitch;
        boolean flag2 = d1 != 0.0D || d2 != 0.0D || d != 0.0D || d3 != 0.0D;
        boolean flag3 = d4 != 0.0D || d5 != 0.0D;

        if (ridingEntity != null)
        {
            if (flag3)
            {
                sendQueue.addToSendQueue(new Packet11PlayerPosition(motionX, -999D, -999D, motionZ, onGround));
            }
            else
            {
                sendQueue.addToSendQueue(new Packet13PlayerLookMove(motionX, -999D, -999D, motionZ, rotationYaw, rotationPitch, onGround));
            }

            flag2 = false;
        }
        else if (flag2 && flag3)
        {
            sendQueue.addToSendQueue(new Packet13PlayerLookMove(posX, boundingBox.minY, posY, posZ, rotationYaw, rotationPitch, onGround));
            timeSinceMoved = 0;
        }
        else if (flag2)
        {
            sendQueue.addToSendQueue(new Packet11PlayerPosition(posX, boundingBox.minY, posY, posZ, onGround));
            timeSinceMoved = 0;
        }
        else if (flag3)
        {
            sendQueue.addToSendQueue(new Packet12PlayerLook(rotationYaw, rotationPitch, onGround));
            timeSinceMoved = 0;
        }
        else
        {
            sendQueue.addToSendQueue(new Packet10Flying(onGround));

            if (wasOnGround != onGround || timeSinceMoved > 200)
            {
                timeSinceMoved = 0;
            }
            else
            {
                timeSinceMoved++;
            }
        }

        wasOnGround = onGround;

        if (flag2)
        {
            oldPosX = posX;
            oldMinY = boundingBox.minY;
            oldPosY = posY;
            oldPosZ = posZ;
        }

        if (flag3)
        {
            oldRotationYaw = rotationYaw;
            oldRotationPitch = rotationPitch;
        }
    }
*/
/*//b181delete
    public int getMaxHealth() {
    	return 20;
    }

    public int getEntityHealth()
    {
        return health;
    }

    public void setEntityHealth(int i)
    {
        health = i;
        if(i > getMaxHealth())
        {
            i = getMaxHealth();
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
