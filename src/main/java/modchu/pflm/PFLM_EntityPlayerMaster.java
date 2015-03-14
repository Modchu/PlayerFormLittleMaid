package modchu.pflm;import java.util.HashMap;import java.util.Random;import modchu.lib.Modchu_EntityPlayerMasterBasis;import modchu.lib.Modchu_Reflect;public class PFLM_EntityPlayerMaster extends Modchu_EntityPlayerMasterBasis {	private boolean isRidingCorrectionFlag = false;	private boolean pushOutOfBlocksFlag = false;	private float oldWidth = 0.6F;	private float oldHeight = 1.81F;	private float locationPositionCorrectionY;	private Object player;	private Random rand = new Random();	public PFLM_EntityPlayerMaster(HashMap<String, Object> map) {		super(map);		Object o = map != null				&& map.containsKey("Object") ? map.get("Object") : null;		player = o != null				&& Modchu_Reflect.loadClass("EntityPlayer").isInstance(o) ? o : null;	}/*	public void init() {		if (PFLM_ConfigData.isPlayerForm) {			if (PFLM_Main.isSmartMoving | !PFLM_ConfigData.isModelSize) {				setSize(0.6F, 1.8F);				resetHeight();			} else {				if (PFLM_ConfigData.isModelSize						&& !PFLM_Main.isMulti) {					setSize(PFLM_Main.getWidth(), PFLM_Main.getHeight());				} else {					setSize(0.6F, 1.8F);				}				resetHeight();			}		}	}	public boolean wakeUpPlayer(boolean flag, boolean flag1, boolean flag2) {		if (PFLM_ConfigData.isPlayerForm				&& PFLM_ConfigData.isModelSize				&& !PFLM_Main.isMulti &&				!PFLM_Main.isSmartMoving) {			if (!flag					&& flag1					&& flag2) {				preparePlayerToSpawn();			} else {				resetHeight();				setSize(0.6F, 1.8F);			}		}		return true;	}	public boolean preparePlayerToSpawn() {		resetHeight();		setSize(0.6F, 1.8F);		setHealth(base, getMaxHealth(base));		//player.setHealth(getMaxHealth(player));		Modchu_AS.set(Modchu_AS.entityLivingBaseDeathTime, base, 0);		return true;	}	public void setPositionCorrection(double par1, double par3, double par5) {		//Modchu_Debug.mDebug("setPositionCorrection player.height="+player.height);		//player.YOffset = 1.62F;		//player.height = 1.8F;		double posX = Modchu_AS.getDouble(Modchu_AS.entityPosX, base) + par1;		double posY = Modchu_AS.getDouble(Modchu_AS.entityPosY, base) + par3;		double posZ = Modchu_AS.getDouble(Modchu_AS.entityPosZ, base) + par5;		Modchu_AS.set(Modchu_AS.setEntityPosX, base, posX);		Modchu_AS.set(Modchu_AS.setEntityPosY, base, posY);		Modchu_AS.set(Modchu_AS.setEntityPosZ, base, posZ);		float YOffset = Modchu_AS.getFloat(Modchu_AS.entityYOffset, base);		//do {		if (posY < 0.0D) posY = 0.0D;		Modchu_AS.set(Modchu_AS.entitySetPosition, base, posX, posY + (YOffset * 0.1D), posZ);		//if (worldObj.getCollidingBoundingBoxes(this, boundingBox).size() != 0) {			//posY++;		//}		//} while (true);	}	public boolean onLivingUpdate() {		if (PFLM_Main.isMulti) return true;		if (PFLM_ConfigData.Physical_BurningPlayer > 0) {			if (Modchu_AS.getBoolean(Modchu_AS.worldIsDaytime, base)					&& getHealth(base) > 0) {				float f = Modchu_AS.getFloat(Modchu_AS.entityGetBrightness, base, 1.0F);				if (f > 0.5F						&& Modchu_AS.getBoolean(Modchu_AS.worldCanBlockSeeTheSky, base, Modchu_AS.get(Modchu_AS.mathHelperFloor_double, Modchu_AS.getDouble(Modchu_AS.entityPosX, base)),								Modchu_AS.getDouble(Modchu_AS.mathHelperFloor_double, Modchu_AS.getDouble(Modchu_AS.entityPosY, base)),								Modchu_AS.getDouble(Modchu_AS.mathHelperFloor_double, Modchu_AS.getDouble(Modchu_AS.entityPosZ, base)))						&& rand.nextFloat() * 30F < (f - 0.4F) * 2.0F) {					Modchu_AS.set(Modchu_AS.entitySetFire, base, PFLM_ConfigData.Physical_BurningPlayer);					//damageEntity(DamageSource.inFire, 1);					//attackEntityFrom(this, DamageSource.inFire, 1);				}			}		}		if (PFLM_ConfigData.Physical_MeltingPlayer > 0				&& Modchu_AS.getBoolean(Modchu_AS.entityIsWet, base)) {			Modchu_AS.get(Modchu_AS.entityAttackEntityFrom, base, Modchu_AS.get(Modchu_AS.damageSourceDrown), PFLM_ConfigData.Physical_MeltingPlayer);		}		Object currentScreen = Modchu_AS.get(Modchu_AS.minecraftCurrentScreen);		if (currentScreen == null				&& locationPositionCorrectionY != 0.0F) {			setPositionCorrection(0.0D, locationPositionCorrectionY, 0.0D);			locationPositionCorrectionY = 0.0F;		}		return true;	}	private int getMaxHealth(Object entityPlayer) {		if (Modchu_Main.getMinecraftVersion() > 159) {			float f = Modchu_AS.getFloat(Modchu_AS.entityLivingBaseGetMaxHealth, entityPlayer);			return (int) f;		}		return (int) Modchu_AS.getFloat(Modchu_AS.entityLivingBaseGetMaxHealth, entityPlayer);	}	private int getHealth(Object entityPlayer) {		float f = Modchu_AS.getFloat(Modchu_AS.entityLivingBaseGetHealth, entityPlayer);		return (int) f;	}	public float[] moveEntityWithHeading(float f, float f1) {		if (!Modchu_AS.getBoolean(Modchu_AS.worldIsRemote, base)				&& Modchu_AS.getBoolean(Modchu_AS.entityIsInWater, base)) {			float f2 = PFLM_Main.getPhysical_Hammer();			f *= f2;			f1 *= f2;			double motionY = Modchu_AS.getDouble(Modchu_AS.entityMotionY, base);			if (motionY > 0.0F) {				motionY *= f2;				if (motionY > 1.0F) {					motionY = 1.0F;				}			}			Modchu_AS.set(Modchu_AS.entityMotionY, base, motionY);		}		return new float[]{ f, f1 };	}	public String getHurtSound() {		Modchu_Debug.mDebug("PFLM_EntityPlayerMaster getHurtSound PFLM_ConfigData.Physical_HurtSound="+PFLM_ConfigData.Physical_HurtSound);		return PFLM_ConfigData.Physical_HurtSound;	}	public Object getCreatureAttribute() {		if (PFLM_ConfigData.Physical_Undead				&& !Modchu_AS.getBoolean(Modchu_AS.worldIsRemote, base)) {			return Modchu_AS.get(Modchu_AS.enumCreatureAttributeUNDEAD);		} else {			return Modchu_Reflect.invokeMethod(player != null ? player.getClass() : base.getClass(), "supergetCreatureAttribute", player != null ? player : base);		}	}	public float[] setSize(float f, float f1) {		if (PFLM_ConfigData.isPlayerForm				&& PFLM_ConfigData.isModelSize				&& !PFLM_Main.isSmartMoving				&& !PFLM_Main.isMulti) {			if (Modchu_AS.getBoolean(Modchu_AS.entityIsRiding, base)) {				f = PFLM_Main.getRidingWidth();				f1 = PFLM_Main.getRidingHeight();			} else {				f = PFLM_Main.getWidth();				f1 = PFLM_Main.getHeight();			}			if (oldWidth != f) oldWidth = f;			if (oldHeight != f1) {				locationPositionCorrectionY = oldHeight - f1;				oldHeight = f1;			}			Modchu_Debug.mDebug("width=" + Modchu_AS.getDouble(Modchu_AS.entityWidth, base) + " height=" + Modchu_AS.getDouble(Modchu_AS.entityHeight, base) + " YOffset=" + Modchu_AS.getDouble(Modchu_AS.entityYOffset, base));		}		return new float[]{ f, f1 };	}	public double getMountedYOffset() {		// Modchu_Debug.mDebug("getMountedYOffset");		if (PFLM_ConfigData.isPlayerForm				&& PFLM_ConfigData.isModelSize				&& !PFLM_Main.isMulti				&& !PFLM_Main.isSmartMoving) {			return Modchu_AS.getDouble(Modchu_AS.entityHeight, base) * PFLM_Main.getMountedYOffset();		}		return Modchu_AS.getDouble(Modchu_AS.entityHeight, base) * 0.75F;	}	public void resetHeight() {		if (PFLM_ConfigData.isPlayerForm				&& PFLM_ConfigData.isModelSize				&& !PFLM_Main.isMulti				&& !PFLM_Main.isSmartMoving) {			Modchu_AS.set(Modchu_AS.entityYOffset, base, PFLM_Main.getYOffset());			//PFLM_PlayerBaseServer.YOffsetResetFlag = true;			return;		}		Modchu_AS.set(Modchu_AS.entityYOffset, base, Modchu_Main.getMinecraftVersion() > 179 ? 0.0F : 1.62F);	}	public double getYOffset() {		if (PFLM_ConfigData.isPlayerForm				&& PFLM_ConfigData.isModelSize				&& !PFLM_Main.isMulti				&& !PFLM_Main.isSmartMoving) {			if (Modchu_AS.getBoolean(Modchu_AS.entityIsRiding, base)					&& !Modchu_AS.getBoolean(Modchu_AS.worldIsRemote, base)) {				float f = PFLM_Main.getRidingYOffset();				// Modchu_Debug.mDebug("getYOffset isRiding() f="+f);				return (double) (f);			}		}		return (double) (Modchu_AS.getFloat(Modchu_AS.entityYOffset, base) - 0.5F);	}	public boolean pushOutOfBlocks(double d, double d1, double d2) {		if (PFLM_ConfigData.isPlayerForm				&& PFLM_ConfigData.isModelSize) {			int i = (int) Modchu_AS.getDouble(Modchu_AS.mathHelperFloor_double, d);			int j = (int) Modchu_AS.getDouble(Modchu_AS.mathHelperFloor_double, d1);			int k = (int) Modchu_AS.getDouble(Modchu_AS.mathHelperFloor_double, d2);			double d3 = d - (double) i;			double d4 = d2 - (double) k;			double d7 = d1 - (double) j;			double height = Modchu_AS.getDouble(Modchu_AS.entityHeight, base);			int j1 = (int) height;			double d8 = (double) (height - 0.81F);			boolean hit = false;			int j2 = (int) (Modchu_AS.getDouble(Modchu_AS.entityBoundingBoxMinY, base) + d8);			hit = Modchu_AS.getBoolean(Modchu_AS.worldIsBlockNormalCubeDefault, base, i, j2, k, false);			//Modchu_Debug.dDebug("pushOutOfBlocks j1="+j1+" hit="+hit+" boundingBox.minY="+boundingBox.minY);			//Modchu_Debug.dDebug("j2="+j2+" d1="+d1+" d8="+d8, 1);			//Modchu_Debug.dDebug("height="+height, 2);			if (hit) {				boolean flag = true;				boolean flag1 = true;				boolean flag2 = true;				boolean flag3 = true;				boolean flag4 = true;				int n;				for (n = j1; n > -1						&& flag == true; n = n - 1) {					flag = !Modchu_AS.getBoolean(Modchu_AS.worldIsBlockNormalCubeDefault, base, i - 1, j + n, k, false);					// System.out.println("pushOutOfBlocks n="+n);				}				// flag = n == -1 ? false : true;				for (n = j1; n > -1						&& flag1 == true; n = n - 1) {					flag1 = !Modchu_AS.getBoolean(Modchu_AS.worldIsBlockNormalCubeDefault, base, i + 1, j + n, k, false);				}				// flag1 = n == -1 ? false : true;				for (n = j1; n > -1						&& flag2 == true; n = n - 1) {					flag2 = !Modchu_AS.getBoolean(Modchu_AS.worldIsBlockNormalCubeDefault, base, i, j + n, k - 1, false);				}				// flag2 = n == -1 ? false : true;				for (n = j1; n > -1						&& flag3 == true; n = n - 1) {					flag3 = !Modchu_AS.getBoolean(Modchu_AS.worldIsBlockNormalCubeDefault, base, i, j + n, k + 1, false);				}				// flag3 = n == -1 ? false : true;				flag4 = Modchu_AS.getBoolean(Modchu_AS.worldIsBlockNormalCubeDefault, base, i, j + n - 1, k, false);				//Modchu_Debug.dDebug("pushOutOfBlocks flag="+flag+" flag1="+flag1+" flag2="+flag2+" flag3="+flag3,1);				byte byte0 = -1;				double d5 = 9999D;				if (flag						&& d3 < d5) {					d5 = d3;					byte0 = 0;				}				if (flag1						&& 1.0D - d3 < d5) {					d5 = 1.0D - d3;					byte0 = 1;				}				if (flag2						&& d4 < d5) {					d5 = d4;					byte0 = 4;				}				if (flag3						&& 1.0D - d4 < d5) {					double d6 = 1.0D - d4;					byte0 = 5;				}				if (flag4						&& 1.0D - d7 < d5) {					d5 = 1.0D - d7;					byte0 = 6;				}				float f = 0.1F;				if (byte0 == 0) {					Modchu_AS.set(Modchu_AS.entityMotionX, base, -f);				}				if (byte0 == 1) {					Modchu_AS.set(Modchu_AS.entityMotionX, base, f);				}				if (byte0 == 4) {					Modchu_AS.set(Modchu_AS.entityMotionZ, base, -f);				}				if (byte0 == 5) {					Modchu_AS.set(Modchu_AS.entityMotionZ, base, f);				}				if (byte0 == 6) {					Modchu_AS.set(Modchu_AS.entityMotionY, base, -f);				}			}			pushOutOfBlocksFlag = hit;		} else {			return (Boolean) Modchu_Reflect.invokeMethod(player != null ? player.getClass() : base.getClass(), "superpushOutOfBlocks", new Class[]{ double.class, double.class, double.class }, player != null ? player : base, new Object[]{ d, d1, d2 });		}		return false;	}	public boolean isEntityInsideOpaqueBlock() {		return pushOutOfBlocksFlag;	}	public Object copyPlayer(Object entityplayer) {		copyInventory(Modchu_AS.get(Modchu_AS.entityPlayerInventory, entityplayer));		setHealth(base, getHealth(entityplayer));		Modchu_AS.get(Modchu_AS.entityPlayerFoodStats, base, Modchu_AS.get(Modchu_AS.entityPlayerFoodStats, entityplayer));		if (Modchu_Main.getMinecraftVersion() > 79				&& Modchu_Main.getMinecraftVersion() < 130) {			Modchu_AS.set(Modchu_AS.entityPlayerPlayerLevel, Modchu_AS.get(Modchu_AS.entityPlayerPlayerLevel, entityplayer));			Modchu_AS.set(Modchu_AS.entityPlayerTotalXP, Modchu_AS.get(Modchu_AS.entityPlayerTotalXP, entityplayer));			Modchu_AS.set(Modchu_AS.entityPlayerCurrentXP, Modchu_AS.get(Modchu_AS.entityPlayerCurrentXP, entityplayer));			Modchu_AS.set(Modchu_AS.entityPlayerScore, Modchu_AS.get(Modchu_AS.entityPlayerScore, entityplayer));			// TODO			Class EntityPlayerSP2 = Modchu_Reflect.loadClass("modchu.pflm.EntityPlayerSP2", -1);			if (EntityPlayerSP2 != null) Modchu_Reflect.setFieldObject(base.getClass(), "score", entityplayer, Modchu_Reflect.getFieldObject(EntityPlayerSP2, "score", entityplayer));		}		return null;	}	private void setHealth(Object entityplayer, int i) {		Modchu_AS.get(Modchu_AS.entityLivingBaseHealth, entityplayer, i);	}	public Object copyInventory(Object inventoryplayer) {		Object[] inventory = Modchu_AS.getObjectArray(Modchu_AS.entityPlayerInventory, base);		Object[] mainInventory = Modchu_AS.getObjectArray(Modchu_AS.entityPlayerMainInventory, inventoryplayer);		for (int i = 0; i < inventory.length; i++) {			inventory[i] = Modchu_AS.get(Modchu_AS.itemStackCopyItemStack, mainInventory[i]);		}		Modchu_AS.set(Modchu_AS.entityPlayerInventory, base, inventory);		Object[] armorInventory = Modchu_AS.getObjectArray(Modchu_AS.entityPlayerArmorInventory, base);		Object[] inventoryplayerArmorInventory = Modchu_AS.getObjectArray(Modchu_AS.entityPlayerArmorInventory, inventoryplayer);		for (int j = 0; j < armorInventory.length; j++) {			armorInventory[j] = Modchu_AS.get(Modchu_AS.itemStackCopyItemStack, inventoryplayerArmorInventory[j]);		}		Modchu_AS.set(Modchu_AS.entityPlayerArmorInventory, base, armorInventory);		return null;	}	public void publicResetHeight() {		resetHeight();	}	public void publicSetSize(float f, float f1) {		setSize(f, f1);	}	public float getMaxHealth() {		return 20;	}*//*	public void updateRidden() {		double d = player.posX;		double d1 = player.posY;		double d2 = player.posZ;		player.superupdateRidden();		player.prevCameraYaw = player.cameraYaw;		player.cameraYaw = 0.0F;		if (PFLM_ConfigData.isModelSize) {			player.addMountedMovementStat(player.posX - d, player.posY - d1 - 1.0D, player.posZ - d2);		} else {			player.addMountedMovementStat(player.posX - d, player.posY - d1, player.posZ - d2);		}	}	private void addMountedMovementStat(double d, double d1, double d2) {		if (player.ridingEntity != null) {			int i = Math.round(MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2) * 100F);			if (i > 0) {				if (player.ridingEntity instanceof EntityMinecart) {					player.addStat(StatList.distanceByMinecartStat, i);					if (player.startMinecartRidingCoordinate == null) {						player.startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));					} else if (player.startMinecartRidingCoordinate.getEuclideanDistanceTo(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)) >= 1000D) {						player.addStat(AchievementList.onARail, 1);					}				} else if (player.ridingEntity instanceof EntityBoat) {					player.addStat(StatList.distanceByBoatStat, i);				} else if (player.ridingEntity instanceof EntityPig) {					player.addStat(StatList.distanceByPigStat, i);				}			}		}	}	public boolean attackEntityFrom(Entity var1, DamageSource par1DamageSource, int par2) {		return var1.attackEntityFrom(par1DamageSource, par2);	}	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {		if (player.capabilities.disableDamage			&& !par1DamageSource.canHarmInCreative()) {			return false;		} else {			player.entityAge = 0;			if (player.getHealth() <= 0) {				onDeath(par1DamageSource);				return false;			} else {				if (player.isPlayerSleeping()					&& !player.worldObj.isRemote) {					player.wakeUpPlayer(true, true, false);				}				Entity var3 = par1DamageSource.getEntity();				if (par1DamageSource.func_76350_n()) {					if (player.worldObj.difficultySetting == 0) {						par2 = 0;					}					if (player.worldObj.difficultySetting == 1) {						par2 = par2 / 2 + 1;					}					if (player.worldObj.difficultySetting == 3) {						par2 = par2 * 3 / 2;					}				}				if (par2 == 0) {					return false;				} else {					Entity var4 = par1DamageSource.getEntity();					if (var4 instanceof EntityArrow						&& ((EntityArrow) var4).shootingEntity != null) {						var4 = ((EntityArrow) var4).shootingEntity;					}					if (var4 instanceof EntityLiving) {						player.alertWolves((EntityLiving) var4, false);					}					player.addStat(StatList.damageTakenStat, par2);					return superattackEntityFrom(par1DamageSource, par2);				}			}		}	}	public boolean superattackEntityFrom(DamageSource par1DamageSource, int par2) {		player.entityAge = 0;		if (getHealth() <= 0) {			return false;		} else if (par1DamageSource.fireDamage()			&& player.isPotionActive(Potion.fireResistance)) {			return false;		} else {			player.field_704_R = 1.5F;			boolean var3 = true;			if ((float) player.hurtResistantTime > (float) player.maxHurtResistantTime / 2.0F) {				if (par2 <= player.lastDamage) {					return false;				}				if (!worldObj.isRemote) player.damageEntity(par1DamageSource, par2 - player.lastDamage);				setHealth(health);				player.lastDamage = par2;				var3 = false;			} else {				player.lastDamage = par2;				player.hurtResistantTime = player.maxHurtResistantTime;				if (!worldObj.isRemote) player.damageEntity(par1DamageSource, par2);				player.prevHealth = getHealth();				setHealth(getHealth());				player.hurtTime = player.maxHurtTime = 10;			}			player.attackedAtYaw = 0.0F;			Entity var4 = par1DamageSource.getEntity();			if (var4 != null) {				if (var4 instanceof EntityLiving) {					player.setRevengeTarget((EntityLiving) var4);				}				if (var4 instanceof EntityPlayer) {					player.recentlyHit = 60;					player.attackingPlayer = (EntityPlayer) var4;				} else if (var4 instanceof EntityWolf) {					EntityWolf var5 = (EntityWolf) var4;					if (var5.isTamed()) {						player.recentlyHit = 60;						player.attackingPlayer = null;					}				}			}			if (var3) {				player.worldObj.setEntityState(this, (byte) 2);				if (par1DamageSource != DamageSource.drown					&& par1DamageSource != DamageSource.field_76375_l) {					player.setBeenAttacked();				}				if (var4 != null) {					double var9 = var4.posX - player.posX;					double var7;					for (var7 = var4.posZ - player.posZ; var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D) {						var9 = (Math.random() - Math.random()) * 0.01D;					}					player.attackedAtYaw = (float) (Math.atan2(var7, var9) * 180.0D / Math.PI) - player.rotationYaw;					player.knockBack(var4, par2, var9, var7);				} else {					player.attackedAtYaw = (float) ((int) (Math.random() * 2.0D) * 180);				}			}			if (player.health <= 0) {				if (var3) {					//player.worldObj.playSoundAtEntity(this, player.getDeathSound(), player.getSoundVolume(), player.getSoundPitch());				}				onDeath(par1DamageSource);			} else if (var3) {				//player.worldObj.playSoundAtEntity(this, player.getHurtSound(), player.getSoundVolume(), player.getSoundPitch());			}			return true;		}	}	public void onDeath(DamageSource par1DamageSource) {		health = 0;		superonDeath(par1DamageSource);		//if (worldObj.isRemote) return;		player.setSize(0.2F, 0.2F);		player.setPosition(player.posX, player.posY, player.posZ);		player.motionY = 0.10000000149011612D;		if (player.username.equals("Notch")) {			player.dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);		}		player.inventory.dropAllItems();		if (par1DamageSource != null) {			player.motionX = (double) (-Modchu_AccessSupport.instance.mathHelperCos((player.attackedAtYaw + player.rotationYaw) * (float) Math.PI / 180.0F) * 0.1F);			player.motionZ = (double) (-Modchu_AccessSupport.instance.mathHelperSin((player.attackedAtYaw + player.rotationYaw) * (float) Math.PI / 180.0F) * 0.1F);		} else {			player.motionX = player.motionZ = 0.0D;		}		player.YOffset = 0.1F;		player.addStat(StatList.deathsStat, 1);	}	public void superonDeath(DamageSource par1DamageSource) {		//if (!worldObj.isRemote) return;		Entity var2 = par1DamageSource.getEntity();		if (player.scoreValue >= 0			&& var2 != null) {			var2.addToPlayerScore(this, player.scoreValue);		}		if (var2 != null) {			var2.onKillEntity(this);		}		player.dead = true;		int var3 = 0;		if (var2 instanceof EntityPlayer) {			var3 = EnchantmentHelper.getLootingModifier(((EntityPlayer) var2).inventory);		}		int var5 = 0;		if (!player.isChild()) {			player.dropFewItems(player.recentlyHit > 0, var3);			if (player.recentlyHit > 0) {				var5 = player.rand.nextInt(200) - var3;				if (var5 < 5) {					player.dropRareDrop(var5 <= 0 ? 1 : 0);				}			}		}		player.worldObj.setEntityState(this, (byte) 3);	}	public void setHealth(int par1) {		int var2 = player.getHealth() - par1;		if (var2 <= 0) {			player.setEntityHealth(par1);			if (var2 < 0) {				player.hurtResistantTime = player.maxHurtResistantTime / 2;			}		} else {			player.lastDamage = var2;			player.setEntityHealth(player.getHealth());			player.hurtResistantTime = player.maxHurtResistantTime;			player.damageEntity(DamageSource.generic, var2);			player.hurtTime = player.maxHurtTime = 10;		}	}	public void sendMotionUpdates() {		if (player.inventoryUpdateTickCounter++ == 20) {			player.inventoryUpdateTickCounter = 0;		}		boolean flag = player.isSprinting();		if (flag != player.wasSneaking) {			if (flag) {				player.sendQueue.addToSendQueue(new Packet19EntityAction(player, 4));			} else {				player.sendQueue.addToSendQueue(new Packet19EntityAction(player, 5));			}			player.wasSneaking = flag;		}		boolean flag1 = player.isSneaking();		if (flag1 != player.shouldStopSneaking) {			if (flag1) {				player.sendQueue.addToSendQueue(new Packet19EntityAction(player, 1));			} else {				player.sendQueue.addToSendQueue(new Packet19EntityAction(player, 2));			}			player.shouldStopSneaking = flag1;		}		double d = player.posX - oldPosX;		double d1 = player.boundingBox.minY - oldMinY;		double d2 = player.posY - oldPosY;		double d3 = player.posZ - oldPosZ;		double d4 = player.rotationYaw - player.oldRotationYaw;		double d5 = player.rotationPitch - player.oldRotationPitch;		boolean flag2 = d1 != 0.0D || d2 != 0.0D || d != 0.0D || d3 != 0.0D;		boolean flag3 = d4 != 0.0D || d5 != 0.0D;		if (player.ridingEntity != null) {			if (flag3) {				player.sendQueue.addToSendQueue(new Packet11PlayerPosition(player.motionX, -999D, -999D, player.motionZ, player.onGround));			} else {				player.sendQueue.addToSendQueue(new Packet13PlayerLookMove(player.motionX, -999D, -999D, player.motionZ, player.rotationYaw, player.rotationPitch, player.onGround));			}			flag2 = false;		} else if (flag2			&& flag3) {			player.sendQueue.addToSendQueue(new Packet13PlayerLookMove(player.posX, player.boundingBox.minY, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, player.onGround));			player.timeSinceMoved = 0;		} else if (flag2) {			player.sendQueue.addToSendQueue(new Packet11PlayerPosition(player.posX, player.boundingBox.minY, player.posY, player.posZ, player.onGround));			player.timeSinceMoved = 0;		} else if (flag3) {			player.sendQueue.addToSendQueue(new Packet12PlayerLook(player.rotationYaw, player.rotationPitch, player.onGround));			player.timeSinceMoved = 0;		} else {			player.sendQueue.addToSendQueue(new Packet10Flying(player.onGround));			if (player.wasOnGround != player.onGround || player.timeSinceMoved > 200) {				player.timeSinceMoved = 0;			} else {				player.timeSinceMoved++;			}		}		player.wasOnGround = player.onGround;		if (flag2) {			player.oldPosX = player.posX;			player.oldMinY = player.boundingBox.minY;			player.oldPosY = player.posY;			player.oldPosZ = player.posZ;		}		if (flag3) {			player.oldRotationYaw = player.rotationYaw;			player.oldRotationPitch = player.rotationPitch;		}	}*/	// TODO/*	@Override	public boolean updateRidden() {		return true;	}	@Override	public boolean attackEntityFrom(Object var1, Object damageSource, int i) {		return Modchu_CastHelper.Boolean(Modchu_Reflect.invokeMethod(player.getClass(), "superattackEntityFrom", new Class[]{ Modchu_Reflect.loadClass("DamageSource"), int.class }, player, new Object[]{ damageSource, i }));	}	@Override	public boolean attackEntityFrom(Object damageSource, int i) {		return Modchu_CastHelper.Boolean(Modchu_Reflect.invokeMethod(player.getClass(), "superattackEntityFrom", new Class[]{ Modchu_Reflect.loadClass("DamageSource"), int.class }, player, new Object[]{ damageSource, i }));	}	@Override	public boolean onDeath(Object damageSource) {		return true;	}	@Override	public boolean sendMotionUpdates() {		return true;	}	@Override	public int getMaxHealthInt() {		return (int)getMaxHealth();	}	@Override	public float getHealth() {		return (int) Modchu_AS.getFloat(Modchu_AS.entityLivingBaseHealth, base);	}	@Override	public int getHealthInt() {		return (int)getHealth();	}	@Override	public float setHealth(float f) {		return f;	}	@Override	public int setHealthInt(int i) {		return i;	}*/}