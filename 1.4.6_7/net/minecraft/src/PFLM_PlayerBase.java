package net.minecraft.src;

import net.minecraft.client.Minecraft;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class PFLM_PlayerBase extends PlayerBase {

	private double motionX;
	private double motionZ;
	private boolean mushroomConfusionLeft;
	private boolean mushroomConfusionRight;
	private boolean mushroomConfusionFront;
	private boolean mushroomConfusionBack;
	private boolean motionResetFlag;
	private boolean motionSetFlag;
	private boolean mushroomBack;
	private boolean mushroomForward;
	private boolean mushroomKeyBindResetFlag;
	private boolean mushroomKeyBindSetFlag;
	private int mushroomConfusionType = 0;
	private int mushroomConfusionCount = 0;
	private Random rnd;
	private final int mushroomConfusionTypeMax = 4;
	private boolean mushroomLeft;
	private boolean mushroomRight;
	private boolean _hasReplacedNetServerHandler;
	private static Minecraft mc = Minecraft.getMinecraft();
	public static boolean yOffsetResetFlag = false;
/*//b173delete
	private boolean keyBindForwardPressed;
	private boolean keyBindBackPressed;
	private boolean keyBindLeftPressed;
	private boolean keyBindRightPressed;
	private KeyBinding keyBindForward;
	private KeyBinding keyBindBack;
	private KeyBinding keyBindLeft;
	private KeyBinding keyBindRight;
	private boolean mushroomConfusionFlag = false;
*///b173delete

	public PFLM_PlayerBase(PlayerAPI playerapi) {
		super(playerapi);
		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm) {
			mod_PFLM_PlayerFormLittleMaid.gotcha = this;
			if (mod_PFLM_PlayerFormLittleMaid.textureName == null) {
				if (mod_PFLM_PlayerFormLittleMaid.textureName == null) {
					mod_PFLM_PlayerFormLittleMaid.textureName = "default";
				} else {
					//if (!PFLM_Gui.noSaveFlag) {
						mod_PFLM_PlayerFormLittleMaid.setTextureName(mod_PFLM_PlayerFormLittleMaid.textureName);
					//} else {
						//mod_PFLM_PlayerFormLittleMaid.setTextureName(mod_PFLM_PlayerFormLittleMaid.textureName);
					//}
				}
			}
			if (mod_PFLM_PlayerFormLittleMaid.textureArmorName == null) {
				if (mod_PFLM_PlayerFormLittleMaid.textureArmorName == null) {
					mod_PFLM_PlayerFormLittleMaid.setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.textureName);
				} else {
					//if (!PFLM_Gui.noSaveFlag) {
						mod_PFLM_PlayerFormLittleMaid.setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.textureArmorName);
					//} else {
						//mod_PFLM_PlayerFormLittleMaid.setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.textureArmorName);
					//}
				}
			}
			if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
				player.setSize(0.6F, 1.8F);
				player.resetHeight();
			} else {
				if (mod_PFLM_PlayerFormLittleMaid.isModelSize
						&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline) {
					player.setSize(mod_PFLM_PlayerFormLittleMaid.getWidth(),
							mod_PFLM_PlayerFormLittleMaid.getHeight());
				} else {
					if (mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOnline
							| !mod_PFLM_PlayerFormLittleMaid.isModelSize) {
						player.setSize(0.6F, 1.8F);
					} else {
						player.setSize(0.5F, 1.35F);
					}
					player.resetHeight();
				}
			}
			PFLM_Gui.partsSetFlag = 1;
		}
		mod_PFLM_PlayerFormLittleMaid.setFirstPersonHandResetFlag(true);
		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
		rnd = new Random();
/*//b181delete
		setMaidColor(mod_PFLM_PlayerFormLittleMaid.maidColor);
		setTextureValue();
		player.skinUrl = null;
		setPlayerTexture(mod_PFLM_PlayerFormLittleMaid.textureName);
*///b181delete
/*//b173delete
		keyBindForward = mc.gameSettings.keyBindForward;
		keyBindBack = mc.gameSettings.keyBindBack;
		keyBindLeft = mc.gameSettings.keyBindLeft;
		keyBindRight = mc.gameSettings.keyBindRight;
*///b173delete
	}
/*
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
			int j2 = j - (int) (MathHelper.floor_double(d1) + 0.5D);
			boolean g = player.worldObj.isBlockNormalCube(i, j + j1, k);
			Modchu_Debug.dDebug("pushOutOfBlocks j1="+j1+" g="+g+" j2="+j2);
			if (!g) {
				boolean flag4 = player.worldObj.isBlockNormalCube(i, j + j1 - 1, k);
				double d5 = 9999D;
				if (flag4 && 1.0D - d7 < d5) {
					float f = 0.1F;
					player.motionY = -f;
				}
			}
			if (g) {
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
				// System.out.println("pushOutOfBlocks n="+n+" flag="+flag);
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
				flag4 = !player.worldObj.isBlockNormalCube(i, j + n - 1, k);
				// System.out.println("pushOutOfBlocks flag="+flag+" flag1="+flag1+" flag2="+flag2+" flag3="+flag3);
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
				Modchu_Debug.mDebug("flag4 ="+flag4+" "+(1.0D - d7));
				if (flag4 && 1.0D - d7 < d5) {
					d5 = 1.0D - d7;
					byte0 = 6;
					Modchu_Debug.mDebug("byte0 = 6");
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
				/*125//*///@//return false;
				// 125deletereturn flag;
			//@//}
		//@//} else {
			/*b181//*///@//return super.pushOutOfBlocks(d, d1, d2);
			/*//b181delete
			super.pushOutOfBlocks(d, d1, d2);
            return false;
*///b181delete
	//@//}
		/*b181//*///@//return false;
		// b181deletereturn true;
	//@//}
//-@-b181
	@Override
	public void afterLocalConstructing(Minecraft minecraft, World world,
			Session session, int i) {
		if (mod_PFLM_PlayerFormLittleMaid.textureName != null
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
				&& !PFLM_Gui.noSaveFlag) {
			mod_PFLM_PlayerFormLittleMaid.setTextureValue();
			player.skinUrl = null;
			setPlayerTexture(mod_PFLM_PlayerFormLittleMaid.textureName);
		}
	}
//@-@b181
	@Override
	public void beforeOnLivingUpdate() {
        ItemStack itemstack = player.inventory.getStackInSlot(9);
        //b173deleteboolean mushroomConfusionResetFlag = true;
        if (itemstack != null) {
        	Item item = itemstack.getItem();
        	if (item instanceof ItemBlock) {
        		Block block = Block.blocksList[item.itemID];
        		if (block instanceof BlockMushroom) {
        			ItemStack itemstack2 = player.inventory.getStackInSlot(10);
        			if (itemstack2 != null) {
        				Item item2 = itemstack2.getItem();
        				if (item2 == item.dyePowder) {
        					//b173deletemushroomConfusionResetFlag = false;
        					//b173deleteif (!mushroomConfusionFlag) mushroomConfusionFlag = true;
        					--mushroomConfusionCount;
        					if(mushroomConfusionCount < 0) {
        						mushroomConfusionCount = 500 + (100 * rnd.nextInt(10));
        						mushroomConfusionType = rnd.nextInt(mushroomConfusionTypeMax);
        						//b173deleteif (mushroomConfusionType != 0) mushroomConfusion1();
        					}
        					//b173deleteif (mushroomConfusionType == 0) mushroomConfusion0();
        					/*b173//*/mushroomConfusion(mushroomConfusionType);
        					//Modchu_Debug.dDebug("mushroomConfusionCount="+mushroomConfusionCount,5);
        					//Modchu_Debug.dDebug("mushroomConfusionType="+mushroomConfusionType,6);
        				}
        			}
        		}
        	}
        }
/*//b173delete
        if (mushroomConfusionFlag
        		&& mushroomConfusionResetFlag) {
        	mushroomConfusionFlag = false;
    		mc.gameSettings.keyBindForward = keyBindForward;
    		mc.gameSettings.keyBindBack = keyBindBack;
    		mc.gameSettings.keyBindLeft = keyBindLeft;
    		mc.gameSettings.keyBindRight = keyBindRight;
        }
*///b173delete
		//if (!player.worldObj.isRemote) {
		if (mod_PFLM_PlayerFormLittleMaid.Physical_BurningPlayer > 0) {
			if (player.worldObj.getWorldInfo().getWorldTime() / 12000 % 2 == 0
					&& player.health > 0) {
				float f = player.getBrightness(1.0F);
				if (f > 0.5F
						&& player.worldObj.canBlockSeeTheSky(
								MathHelper.floor_double(player.posX),
								MathHelper.floor_double(player.posY),
								MathHelper.floor_double(player.posZ))
						&& player.rand.nextFloat() * 30F < (f - 0.4F) * 2.0F) {
					//player.setIsImmuneToFireField(false);
					player.setFire(mod_PFLM_PlayerFormLittleMaid.Physical_BurningPlayer);
					//player.attackEntityFrom(DamageSource.inFire, 1);
					//Modchu_Debug.mDebug("par1DamageSource.canHarmInCreative()="+DamageSource.inFire.canHarmInCreative());
				}
			}
		}
			if (mod_PFLM_PlayerFormLittleMaid.Physical_MeltingPlayer > 0
					&& player.isWet()) {
				player.attackEntityFrom(DamageSource.drown,
						mod_PFLM_PlayerFormLittleMaid.Physical_MeltingPlayer);
			}
		//}

		if (!PFLM_Gui.guiMode
				&& PFLM_Gui.partsSetFlag == 2) {
			PFLM_Gui.partsSetFlag = 3;
			Modchu_Config.loadShowModelList(mod_PFLM_PlayerFormLittleMaid.showModelList);
			PFLM_Gui.showModelFlag = true;
		}

		if (mod_PFLM_PlayerFormLittleMaid.isPlayerForm
				&& mod_PFLM_PlayerFormLittleMaid.isModelSize
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
				&& !mod_PFLM_PlayerFormLittleMaid.isSmartMoving
				) {
			if (yOffsetResetFlag
					&& player.ridingEntity == null) {
				player.yOffset = (float) player.getYOffset();
				yOffsetResetFlag = false;
			}
		}
	}

	private void mushroomConfusion(int i) {
		switch (i) {
		case 0:
			mushroomConfusion0();
			break;
		case 1:
		case 2:
		case 3:
		case 4:
			mushroomConfusion1();
			break;
		}
	}

	private void mushroomConfusion0() {
		if (motionResetFlag) {
			if (player.motionX > 0.0D
					| player.motionZ > 0.0D) {
				if (mc.gameSettings.keyBindForward.pressed
						| mc.gameSettings.keyBindBack.pressed
						| mc.gameSettings.keyBindLeft.pressed
						| mc.gameSettings.keyBindRight.pressed) {
					//Modchu_Debug.mDebug("pressed");
					motionResetFlag = false;
					mushroomConfusionLeft = false;
					if(player.motionX > 0.001D) {
						mushroomConfusionLeft = true;
						//Modchu_Debug.mDebug("mushroomConfusionLeft");
					}
					mushroomConfusionRight = false;
					if(player.motionX < -0.001D) {
						mushroomConfusionRight = true;
						//Modchu_Debug.mDebug("mushroomConfusionRight");
					}
					mushroomConfusionFront = false;
					if(player.motionZ > 0.0001D) {
						mushroomConfusionFront = true;
						//Modchu_Debug.mDebug("mushroomConfusionFront");
					}
					mushroomConfusionBack = false;
					if(player.motionZ < -0.001D) {
						mushroomConfusionBack = true;
						//Modchu_Debug.mDebug("mushroomConfusionBack");
					}
					motionSetFlag = true;
				}
			}
		} else {
			float f = player.moveForward * player.moveForward + player.moveStrafing * player.moveStrafing;
			//Modchu_Debug.mDebug("f="+f);
			if (!player.isRiding() && mc.inGameHasFocus && (double)f < 0.10000000000000001D && !player.isJumping) {
				//Modchu_Debug.mDebug("motionResetFlag = true");
				motionResetFlag = true;
				player.motionX = 0.0D;
				player.motionZ = 0.0D;
				motionSetFlag = false;
			} else {
				if (motionSetFlag) {
					if (mushroomConfusionLeft) {
						if (player.motionX > 0.0D) player.motionX = -player.motionX - 0.1D;
						//Modchu_Debug.mDebug("mushroomConfusionLeft "+player.motionX);
					}
					if (mushroomConfusionRight) {
						if (player.motionX < 0.0D) player.motionX = -player.motionX + 0.4D;
						//Modchu_Debug.mDebug("mushroomConfusionRight "+player.motionX);
					}
					if (mushroomConfusionFront) {
						if (player.motionZ > 0.0D) player.motionZ = -player.motionZ - 0.1D;
						//Modchu_Debug.mDebug("mushroomConfusionFront "+player.motionZ);
					}
					if (mushroomConfusionBack) {
						if (player.motionZ < 0.0D) player.motionZ = -player.motionZ + 0.4D;
						//Modchu_Debug.mDebug("mushroomConfusionBack "+player.motionZ);
					}
				}
			}
		}
	}

	private void mushroomConfusion1() {
		boolean forward;
		boolean back;
		boolean left;
		boolean right;
		KeyBinding key1 = null;
		KeyBinding key2 = null;
		KeyBinding key3 = null;
		KeyBinding key4 = null;
		switch (mushroomConfusionType) {
		case 1:
			key1 = mc.gameSettings.keyBindForward;
			key2 = mc.gameSettings.keyBindBack;
			key3 = mc.gameSettings.keyBindLeft;
			key4 = mc.gameSettings.keyBindRight;
			break;
		case 2:
			key2 = mc.gameSettings.keyBindForward;
			key3 = mc.gameSettings.keyBindBack;
			key4 = mc.gameSettings.keyBindLeft;
			key1 = mc.gameSettings.keyBindRight;
			break;
		case 3:
			key2 = mc.gameSettings.keyBindForward;
			key3 = mc.gameSettings.keyBindBack;
			key1 = mc.gameSettings.keyBindLeft;
			key4 = mc.gameSettings.keyBindRight;
			break;
		case 4:
			key4 = mc.gameSettings.keyBindForward;
			key1 = mc.gameSettings.keyBindBack;
			key2 = mc.gameSettings.keyBindLeft;
			key3 = mc.gameSettings.keyBindRight;
			break;
		}
//-@-b173
		if (mushroomBack) {
			back = returnBoolean(Keyboard.isKeyDown(key2.keyCode));
			if (!back) mushroomBack = false;
			key2.pressed = false;
		} else {
			key2.pressed = Keyboard.isKeyDown(key2.keyCode);
			back = returnBoolean(key2.pressed);
		}
		if (mushroomForward) {
			forward = returnBoolean(Keyboard.isKeyDown(key1.keyCode));
			if (!forward) mushroomForward = false;
			key1.pressed = false;
		} else {
			key1.pressed = Keyboard.isKeyDown(key1.keyCode);
			forward = returnBoolean(key1.pressed);
		}
		if (mushroomLeft) {
			left = returnBoolean(Keyboard.isKeyDown(key3.keyCode));
			if (!forward) mushroomLeft = false;
			key3.pressed = false;
		} else {
			key3.pressed = Keyboard.isKeyDown(key3.keyCode);
			left = returnBoolean(key3.pressed);
		}
		if (mushroomRight) {
			right = returnBoolean(Keyboard.isKeyDown(key4.keyCode));
			if (!forward) mushroomRight = false;
			key4.pressed = false;
		} else {
			key4.pressed = Keyboard.isKeyDown(key4.keyCode);
			right = returnBoolean(key4.pressed);
		}
		key1.pressed = returnBoolean(back);
		key2.pressed = returnBoolean(forward);
		key4.pressed = returnBoolean(left);
		key3.pressed = returnBoolean(right);
		//Modchu_Debug.dDebug("keyBindForward="+key1.pressed);
		//Modchu_Debug.dDebug("keyBindBack="+key2.pressed, 1);
		//Modchu_Debug.dDebug("forward="+forward, 2);
		//Modchu_Debug.dDebug("back="+back, 3);
		if (key1.pressed
				&& key2.pressTime > 0) {
			mushroomBack = true;
			mushroomForward = false;
			return;
		}
		mushroomBack = false;
		if (key2.pressed
				&& key1.pressTime > 0) {
			mushroomForward = true;
			mushroomBack = false;
			return;
		}
		mushroomForward = false;
		if (key3.pressed
				&& key4.pressTime > 0) {
			mushroomRight = true;
			mushroomLeft = false;
			return;
		}
		mushroomRight = false;
		if (key4.pressed
				&& key3.pressTime > 0) {
			mushroomLeft = true;
			mushroomRight = false;
			return;
		}
		mushroomLeft = false;
//@-@b173
/*//b173delete
		mc.gameSettings.keyBindBack = key1;
		mc.gameSettings.keyBindForward = key2;
		mc.gameSettings.keyBindRight = key3;
		mc.gameSettings.keyBindLeft = key4;
*///b173delete
	}

	private boolean returnBoolean(boolean b) {
		return b;
	}

	@Override
	public void moveEntityWithHeading(float f, float f1) {
//-@-b181
		if (player.isInWater()) {
			float f2 = mod_PFLM_PlayerFormLittleMaid.getPhysical_Hammer();
			f *= f2;
			f1 *= f2;
			if (player.motionY > 0.0F) {
				player.motionY *= f2;
				if (player.motionY > 1.0F) {
					player.motionY = 1.0F;
				}
			}
			// System.out.println("moveEntityWithHeading f2="+f2+" f="+f+" f1="+f1+" player.motionY="+player.motionY);
		}
		player.localMoveEntityWithHeading(f, f1);
//@-@b181
/*//b181delete
		if (!player.worldObj.multiplayerWorld && player.isInWater()) {
			f *= mod_PFLM_PlayerFormLittleMaid.Physical_Hammer;
			f1 *= mod_PFLM_PlayerFormLittleMaid.Physical_Hammer;
			if (player.motionY > 0.0F) {
				player.motionY *= mod_PFLM_PlayerFormLittleMaid.Physical_Hammer;
			}
		}
		return false;
*///b181delete
	}
//-@-b181
	@Override
	public String getHurtSound() {
		return mod_PFLM_PlayerFormLittleMaid.Physical_HurtSound;
	}
//@-@b181
    public void setPlayerTexture(String s) {
    	player.texture = s;
    }
/*
    public MovingObjectPosition rayTrace(double par1, float par3)
    {
        Vec3 var4 = player.getPosition(par3);
        Vec3 var5 = player.getLook(par3);
        float f1 = 1.62F - 1.17F + 0.6F - (player.isRiding() ? 1.0F : 0.0F);
        Vec3 var6 = var4.addVector(var5.xCoord * par1, var5.yCoord * par1 - f1, var5.zCoord * par1);
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
