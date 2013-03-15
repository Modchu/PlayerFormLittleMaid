package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PFLM_EntityPlayerMaster {

	private boolean isRidingCorrectionFlag = false;
	private boolean pushOutOfBlocksFlag = false;
	private float oldWidth = 0.6F;
	private float oldHeight = 1.81F;
	private float locationPositionCorrectionY;
	private Class EntityPlayerSP2;
	private EntityPlayer player;
	private Object base;
	// b173deletepublic static int sleepMotion = 0;

	public PFLM_EntityPlayerMaster(EntityPlayer entity) {
		this(entity, null);
	}

	public PFLM_EntityPlayerMaster(EntityPlayer entity, Object o) {
		player = entity;
		base = o;
		EntityPlayerSP2 = Modchu_Reflect.loadClass("EntityPlayerSP2", false);
	}

	public void init() {
		Modchu_Reflect.setFieldObject(base != null ? base.getClass() : player.getClass(), "initFlag", base != null ? base : player, true);
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm) {
			if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving
					| !mod_PFLM_PlayerFormLittleMaid.isModelSize) {
				setSize(0.6F, 1.8F);
				resetHeight();
			} else {
				if (mod_PFLM_PlayerFormLittleMaid.isModelSize
						&& !mod_PFLM_PlayerFormLittleMaid.isMulti) {
					setSize(mod_PFLM_PlayerFormLittleMaid.getWidth(),
							mod_PFLM_PlayerFormLittleMaid.getHeight());
				} else {
					setSize(0.6F, 1.8F);
				}
				resetHeight();
			}
		}
	}

	public void wakeUpPlayer(boolean flag, boolean flag1, boolean flag2) {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.isMulti
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			if (!flag && flag1 && flag2) {
				preparePlayerToSpawn();
			} else {
				resetHeight();
				setSize(0.6F, 1.8F);
			}
		}
	}

	public void preparePlayerToSpawn() {
		resetHeight();
		setSize(0.6F, 1.8F);
		player.setEntityHealth(player.getMaxHealth());
		player.deathTime = 0;
	}

	public void setPositionCorrection(double par1, double par3, double par5) {
		//Modchu_Debug.mDebug("setPositionCorrection player.height="+player.height);
		//player.yOffset = 1.62F;
		//player.height = 1.8F;
		player.posX = player.posX + par1;
		player.posY = player.posY + par3;
		player.posZ = player.posZ + par5;
		//do {
			if (player.posY < 0.0D) player.posY = 0.0D;
			player.setPosition(player.posX, player.posY + (player.yOffset * 0.1D), player.posZ);
			//if (worldObj.getCollidingBoundingBoxes(this, boundingBox).size() != 0) {
				//player.posY++;
			//}
		//} while (true);
	}

	public void onLivingUpdate() {
		if (mod_PFLM_PlayerFormLittleMaid.isMulti) return;
		if (mod_PFLM_PlayerFormLittleMaid.Physical_BurningPlayer > 0) {
			if (player.worldObj.isDaytime()
					&& player.health > 0) {
				float f = player.getBrightness(1.0F);
				if (f > 0.5F
						&& player.worldObj.canBlockSeeTheSky(
								MathHelper.floor_double(player.posX),
								MathHelper.floor_double(player.posY),
								MathHelper.floor_double(player.posZ))
								&& player.rand.nextFloat() * 30F < (f - 0.4F) * 2.0F) {
					player.setFire(mod_PFLM_PlayerFormLittleMaid.Physical_BurningPlayer);
					//damageEntity(DamageSource.inFire, 1);
					//attackEntityFrom(this, DamageSource.inFire, 1);
				}
			}
		}
		if (mod_PFLM_PlayerFormLittleMaid.Physical_MeltingPlayer > 0 && player.isWet()) {
			player.attackEntityFrom(DamageSource.drown,
					mod_PFLM_PlayerFormLittleMaid.Physical_MeltingPlayer);
		}
		if (Minecraft.getMinecraft().currentScreen == null
				&& locationPositionCorrectionY != 0.0F) {
			setPositionCorrection(0.0D, locationPositionCorrectionY, 0.0D);
			locationPositionCorrectionY = 0.0F;
		}
	}

	public void moveEntityWithHeading(float f, float f1) {
		if (!player.worldObj.isRemote && player.isInWater()) {
			float f2 = mod_PFLM_PlayerFormLittleMaid.getPhysical_Hammer();
			f *= f2;
			f1 *= f2;
			if (player.motionY > 0.0F) {
				player.motionY *= f2;
				if (player.motionY > 1.0F) {
					player.motionY = 1.0F;
				}
			}
		}
	}

	public String getHurtSound() {
		return mod_PFLM_PlayerFormLittleMaid.Physical_HurtSound;
	}
//-@-b181
	public EnumCreatureAttribute getCreatureAttribute() {
		if (mod_PFLM_PlayerFormLittleMaid.Physical_Undead && !player.worldObj.isRemote) {
			return EnumCreatureAttribute.UNDEAD;
		} else {
			return (EnumCreatureAttribute) Modchu_Reflect.invokeMethod(base != null ? base.getClass() : player.getClass(), "supergetCreatureAttribute", base != null ? base : player);
		}
	}
//@-@b181

	protected void setSize(float f, float f1) {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving
				&& !mod_PFLM_PlayerFormLittleMaid.isMulti) {
			if (player.isRiding()) {
				f = mod_PFLM_PlayerFormLittleMaid.getRidingWidth();
				f1 = mod_PFLM_PlayerFormLittleMaid.getRidingHeight();
			} else {
				f = mod_PFLM_PlayerFormLittleMaid.getWidth();
				f1 = mod_PFLM_PlayerFormLittleMaid.getHeight();
			}
			if (oldWidth != f) oldWidth = f;
			if (oldHeight != f1) {
				locationPositionCorrectionY = oldHeight - f1;
				oldHeight = f1;
			}
			Modchu_Debug.mDebug("width="+player.width+" height="+player.height+" yOffset="+player.yOffset);
		}
		Modchu_Reflect.invokeMethod(base != null ? base.getClass() : player.getClass(), "supersetSize", new Class[]{ float.class, float.class }, base != null ? base : player, new Object[]{ f, f1 });
	}

	public double getMountedYOffset() {
		// Modchu_Debug.mDebug("getMountedYOffset");
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.isMulti
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			return (double) player.height * mod_PFLM_PlayerFormLittleMaid.getMountedYOffset();
		}
		return (double) player.height * 0.75D;
	}

	protected void resetHeight() {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.isMulti
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			player.yOffset = mod_PFLM_PlayerFormLittleMaid.getyOffset();
			//PFLM_PlayerBaseServer.yOffsetResetFlag = true;
			return;
		}
		player.yOffset = 1.62F;
	}

	public double getYOffset() {
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& !mod_PFLM_PlayerFormLittleMaid.isMulti
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			if (player.isRiding() && !player.worldObj.isRemote) {
				float f = mod_PFLM_PlayerFormLittleMaid.getRidingyOffset();
				// Modchu_Debug.mDebug("getYOffset isRiding() f="+f);
				return (double) (f);
			}
		}
		return (double) (player.yOffset - 0.5F);
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
			int j1 = (int) player.height;
			double d8 = (double) (player.height - 0.81F);
			boolean hit = false;
			int j2 = (int) (player.boundingBox.minY + d8);
			hit = player.worldObj.isBlockNormalCube(i, j2, k);
			//Modchu_Debug.dDebug("pushOutOfBlocks j1="+j1+" hit="+hit+" boundingBox.minY="+boundingBox.minY);
			//Modchu_Debug.dDebug("j2="+j2+" d1="+d1+" d8="+d8, 1);
			//Modchu_Debug.dDebug("height="+height, 2);
			if (hit) {
				boolean flag = true;
				boolean flag1 = true;
				boolean flag2 = true;
				boolean flag3 = true;
				boolean flag4 = true;
				int n;
				for (n = j1; n > -1 && flag == true; n = n - 1) {
					flag = !player.worldObj.isBlockNormalCube(i - 1, j + n, k);
					// System.out.println("pushOutOfBlocks n="+n);
				}
				// flag = n == -1 ? false : true;
				for (n = j1; n > -1 && flag1 == true; n = n - 1) {
					flag1 = !player.worldObj.isBlockNormalCube(i + 1, j + n, k);
				}
				// flag1 = n == -1 ? false : true;
				for (n = j1; n > -1 && flag2 == true; n = n - 1) {
					flag2 = !player.worldObj.isBlockNormalCube(i, j + n, k - 1);
				}
				// flag2 = n == -1 ? false : true;
				for (n = j1; n > -1 && flag3 == true; n = n - 1) {
					flag3 = !player.worldObj.isBlockNormalCube(i, j + n, k + 1);
				}
				// flag3 = n == -1 ? false : true;
				flag4 = player.worldObj.isBlockNormalCube(i, j + n - 1, k);
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
					player.motionX = -f;
				}
				if (byte0 == 1) {
					player.motionX = f;
				}
				if (byte0 == 4) {
					player.motionZ = -f;
				}
				if (byte0 == 5) {
					player.motionZ = f;
				}
				if (byte0 == 6) {
					player.motionY = -f;
				}
			}
			pushOutOfBlocksFlag = hit;
		} else {
			return (Boolean) Modchu_Reflect.invokeMethod(base != null ? base.getClass() : player.getClass(), "superpushOutOfBlocks", new Class[]{ double.class, double.class, double.class }, base != null ? base : player, new Object[]{ d, d1, d2 });
		}
		return false;
	}

	public boolean isEntityInsideOpaqueBlock() {
		return pushOutOfBlocksFlag;
	}

	public void setPlayerTexture(String s) {
		player.texture = s;
    }

    public void copyPlayer(EntityPlayer entityplayer)
    {
/*//125delete
    	if (entityplayer != null) ;else return;
    	if (player != null) {
    		Modchu_Reflect.invokeMethod(base != null ? base.getClass() : player.getClass(), "supercopyPlayer", new Class[]{ EntityPlayer.class }, base != null ? base : player, new Object[]{ entityplayer });
    		return;
    	}
    	entityplayer.copyPlayer(entityplayer);
*///125delete
//-@-125~b181
    	copyInventory(entityplayer.inventory);
        player.health = entityplayer.getHealth();
        player.foodStats = entityplayer.getFoodStats();
//@-@125~b181
//-@-b173
/*//b181delete
        player.playerLevel = entityplayer.playerLevel;
        player.totalXP = entityplayer.totalXP;
        player.currentXP = entityplayer.currentXP;
        player.score = entityplayer.score;
*///b181delete
//@-@b173
//-@-125
        if (EntityPlayerSP2 != null) Modchu_Reflect.setFieldObject(player.getClass(), "score", entityplayer, Modchu_Reflect.getFieldObject(EntityPlayerSP2, "score", entityplayer));
//@-@125
    }

    public void copyInventory(InventoryPlayer inventoryplayer)
    {
        for(int i = 0; i < inventoryplayer.mainInventory.length; i++)
        {
        	player.inventory.mainInventory[i] = ItemStack.copyItemStack(inventoryplayer.mainInventory[i]);
        }

        for(int j = 0; j < inventoryplayer.armorInventory.length; j++)
        {
        	player.inventory.armorInventory[j] = ItemStack.copyItemStack(inventoryplayer.armorInventory[j]);
        }
    }

	public void publicResetHeight() {
		resetHeight();
	}

	public void publicSetSize(float f, float f1) {
		setSize(f, f1);
	}

/*
    @Override
    public void updateRidden()
    {
        double d = player.posX;
        double d1 = player.posY;
        double d2 = player.posZ;
        player.superupdateRidden();
        player.prevCameraYaw = player.cameraYaw;
        player.cameraYaw = 0.0F;
    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
            player.addMountedMovementStat(player.posX - d, player.posY - d1 - 1.0D, player.posZ - d2);
    	} else {
    		player.addMountedMovementStat(player.posX - d, player.posY - d1, player.posZ - d2);
    	}
   	}

    private void addMountedMovementStat(double d, double d1, double d2)
    {
        if (player.ridingEntity != null)
        {
            int i = Math.round(MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2) * 100F);
            if (i > 0)
            {
                if (player.ridingEntity instanceof EntityMinecart)
                {
                    player.addStat(StatList.distanceByMinecartStat, i);
                    if (player.startMinecartRidingCoordinate == null)
                    {
                        player.startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
                    }
                    else if (player.startMinecartRidingCoordinate.getEuclideanDistanceTo(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)) >= 1000D)
                    {
                        player.addStat(AchievementList.onARail, 1);
                    }
                }
                else if (player.ridingEntity instanceof EntityBoat)
                {
                    player.addStat(StatList.distanceByBoatStat, i);
                }
                else if (player.ridingEntity instanceof EntityPig)
                {
                    player.addStat(StatList.distanceByPigStat, i);
                }
            }
        }
    }

    public boolean attackEntityFrom(Entity var1, DamageSource par1DamageSource, int par2)
    {
    	return var1.attackEntityFrom(par1DamageSource, par2);
    }
*/
/*
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (player.capabilities.disableDamage && !par1DamageSource.canHarmInCreative())
        {
            return false;
        }
        else
        {
            player.entityAge = 0;

            if (player.getHealth() <= 0)
            {
            	onDeath(par1DamageSource);
                return false;
            }
            else
            {
                if (player.isPlayerSleeping() && !player.worldObj.isRemote)
                {
                    player.wakeUpPlayer(true, true, false);
                }

                Entity var3 = par1DamageSource.getEntity();

                if (par1DamageSource.func_76350_n())
                {
                    if (player.worldObj.difficultySetting == 0)
                    {
                        par2 = 0;
                    }

                    if (player.worldObj.difficultySetting == 1)
                    {
                        par2 = par2 / 2 + 1;
                    }

                    if (player.worldObj.difficultySetting == 3)
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
                        player.alertWolves((EntityLiving)var4, false);
                    }

                    player.addStat(StatList.damageTakenStat, par2);
                    return superattackEntityFrom(par1DamageSource, par2);
                }
            }
        }
    }

    public boolean superattackEntityFrom(DamageSource par1DamageSource, int par2)
    {
            player.entityAge = 0;

            if (getHealth() <= 0)
            {
                return false;
            }
            else if (par1DamageSource.fireDamage() && player.isPotionActive(Potion.fireResistance))
            {
                return false;
            }
            else
            {
                player.field_704_R = 1.5F;
                boolean var3 = true;

                if ((float)player.hurtResistantTime > (float)player.maxHurtResistantTime / 2.0F)
                {
                    if (par2 <= player.lastDamage)
                    {
                        return false;
                    }
                    if (!worldObj.isRemote) player.damageEntity(par1DamageSource, par2 - player.lastDamage);
                    setHealth(health);
                    player.lastDamage = par2;
                    var3 = false;
                }
                else
                {
                    player.lastDamage = par2;
                    player.hurtResistantTime = player.maxHurtResistantTime;
                    if (!worldObj.isRemote) player.damageEntity(par1DamageSource, par2);
                    player.prevHealth = getHealth();
                    setHealth(getHealth());
                    player.hurtTime = player.maxHurtTime = 10;
                }

                player.attackedAtYaw = 0.0F;
                Entity var4 = par1DamageSource.getEntity();

                if (var4 != null)
                {
                    if (var4 instanceof EntityLiving)
                    {
                        player.setRevengeTarget((EntityLiving)var4);
                    }

                    if (var4 instanceof EntityPlayer)
                    {
                        player.recentlyHit = 60;
                        player.attackingPlayer = (EntityPlayer)var4;
                    }
                    else if (var4 instanceof EntityWolf)
                    {
                        EntityWolf var5 = (EntityWolf)var4;

                        if (var5.isTamed())
                        {
                            player.recentlyHit = 60;
                            player.attackingPlayer = null;
                        }
                    }
                }

                if (var3)
                {
                    player.worldObj.setEntityState(this, (byte)2);

                    if (par1DamageSource != DamageSource.drown && par1DamageSource != DamageSource.field_76375_l)
                    {
                        player.setBeenAttacked();
                    }

                    if (var4 != null)
                    {
                        double var9 = var4.posX - player.posX;
                        double var7;

                        for (var7 = var4.posZ - player.posZ; var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D)
                        {
                            var9 = (Math.random() - Math.random()) * 0.01D;
                        }

                        player.attackedAtYaw = (float)(Math.atan2(var7, var9) * 180.0D / Math.PI) - player.rotationYaw;
                        player.knockBack(var4, par2, var9, var7);
                    }
                    else
                    {
                        player.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
                    }
                }

                if (player.health <= 0)
                {
                    if (var3)
                    {
                        //player.worldObj.playSoundAtEntity(this, player.getDeathSound(), player.getSoundVolume(), player.getSoundPitch());
                    }

                    onDeath(par1DamageSource);
                }
                else if (var3)
                {
                    //player.worldObj.playSoundAtEntity(this, player.getHurtSound(), player.getSoundVolume(), player.getSoundPitch());
                }

                return true;
            }
    }

    public void onDeath(DamageSource par1DamageSource)
    {
    	health = 0;
    	superonDeath(par1DamageSource);
    	//if (worldObj.isRemote) return;
        player.setSize(0.2F, 0.2F);
        player.setPosition(player.posX, player.posY, player.posZ);
        player.motionY = 0.10000000149011612D;

        if (player.username.equals("Notch"))
        {
            player.dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
        }

        player.inventory.dropAllItems();

        if (par1DamageSource != null)
        {
            player.motionX = (double)(-MathHelper.cos((player.attackedAtYaw + player.rotationYaw) * (float)Math.PI / 180.0F) * 0.1F);
            player.motionZ = (double)(-MathHelper.sin((player.attackedAtYaw + player.rotationYaw) * (float)Math.PI / 180.0F) * 0.1F);
        }
        else
        {
            player.motionX = player.motionZ = 0.0D;
        }

        player.yOffset = 0.1F;
        player.addStat(StatList.deathsStat, 1);
    }

    public void superonDeath(DamageSource par1DamageSource)
    {
    	//if (!worldObj.isRemote) return;
            Entity var2 = par1DamageSource.getEntity();

            if (player.scoreValue >= 0 && var2 != null)
            {
                var2.addToPlayerScore(this, player.scoreValue);
            }

            if (var2 != null)
            {
                var2.onKillEntity(this);
            }

            player.dead = true;

                int var3 = 0;

                if (var2 instanceof EntityPlayer)
                {
                    var3 = EnchantmentHelper.getLootingModifier(((EntityPlayer)var2).inventory);
                }

                int var5 = 0;

                if (!player.isChild())
                {
                    player.dropFewItems(player.recentlyHit > 0, var3);

                    if (player.recentlyHit > 0)
                    {
                        var5 = player.rand.nextInt(200) - var3;

                        if (var5 < 5)
                        {
                            player.dropRareDrop(var5 <= 0 ? 1 : 0);
                        }
                    }
                }

            player.worldObj.setEntityState(this, (byte)3);
    }

    public void setHealth(int par1)
    {
        int var2 = player.getHealth() - par1;
        if (var2 <= 0)
        {
            player.setEntityHealth(par1);

            if (var2 < 0)
            {
                player.hurtResistantTime = player.maxHurtResistantTime / 2;
            }
        }
        else
        {
            player.lastDamage = var2;
            player.setEntityHealth(player.getHealth());
            player.hurtResistantTime = player.maxHurtResistantTime;
            player.damageEntity(DamageSource.generic, var2);
            player.hurtTime = player.maxHurtTime = 10;
        }
    }

/*
    public void sendMotionUpdates()
    {
        if (player.inventoryUpdateTickCounter++ == 20)
        {
            player.inventoryUpdateTickCounter = 0;
        }

        boolean flag = player.isSprinting();

        if (flag != player.wasSneaking)
        {
            if (flag)
            {
                player.sendQueue.addToSendQueue(new Packet19EntityAction(player, 4));
            }
            else
            {
                player.sendQueue.addToSendQueue(new Packet19EntityAction(player, 5));
            }

            player.wasSneaking = flag;
        }

        boolean flag1 = player.isSneaking();

        if (flag1 != player.shouldStopSneaking)
        {
            if (flag1)
            {
                player.sendQueue.addToSendQueue(new Packet19EntityAction(player, 1));
            }
            else
            {
                player.sendQueue.addToSendQueue(new Packet19EntityAction(player, 2));
            }

            player.shouldStopSneaking = flag1;
        }

        double d = player.posX - oldPosX;
        double d1 = player.boundingBox.minY - oldMinY;
        double d2 = player.posY - oldPosY;
        double d3 = player.posZ - oldPosZ;
        double d4 = player.rotationYaw - player.oldRotationYaw;
        double d5 = player.rotationPitch - player.oldRotationPitch;
        boolean flag2 = d1 != 0.0D || d2 != 0.0D || d != 0.0D || d3 != 0.0D;
        boolean flag3 = d4 != 0.0D || d5 != 0.0D;

        if (player.ridingEntity != null)
        {
            if (flag3)
            {
                player.sendQueue.addToSendQueue(new Packet11PlayerPosition(player.motionX, -999D, -999D, player.motionZ, player.onGround));
            }
            else
            {
                player.sendQueue.addToSendQueue(new Packet13PlayerLookMove(player.motionX, -999D, -999D, player.motionZ, player.rotationYaw, player.rotationPitch, player.onGround));
            }

            flag2 = false;
        }
        else if (flag2 && flag3)
        {
            player.sendQueue.addToSendQueue(new Packet13PlayerLookMove(player.posX, player.boundingBox.minY, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, player.onGround));
            player.timeSinceMoved = 0;
        }
        else if (flag2)
        {
            player.sendQueue.addToSendQueue(new Packet11PlayerPosition(player.posX, player.boundingBox.minY, player.posY, player.posZ, player.onGround));
            player.timeSinceMoved = 0;
        }
        else if (flag3)
        {
            player.sendQueue.addToSendQueue(new Packet12PlayerLook(player.rotationYaw, player.rotationPitch, player.onGround));
            player.timeSinceMoved = 0;
        }
        else
        {
            player.sendQueue.addToSendQueue(new Packet10Flying(player.onGround));

            if (player.wasOnGround != player.onGround || player.timeSinceMoved > 200)
            {
                player.timeSinceMoved = 0;
            }
            else
            {
                player.timeSinceMoved++;
            }
        }

        player.wasOnGround = player.onGround;

        if (flag2)
        {
            player.oldPosX = player.posX;
            player.oldMinY = player.boundingBox.minY;
            player.oldPosY = player.posY;
            player.oldPosZ = player.posZ;
        }

        if (flag3)
        {
            player.oldRotationYaw = player.rotationYaw;
            player.oldRotationPitch = player.rotationPitch;
        }
    }
*/
/*//b181delete
    public int getMaxHealth() {
    	return 20;
    }

    public int getEntityHealth()
    {
        return player.health;
    }

    public void setEntityHealth(int i)
    {
        player.health = i;
        if(i > player.getMaxHealth())
        {
            i = player.getMaxHealth();
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
