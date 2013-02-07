package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.smart.moving.SmartMoving;
import net.smart.moving.SmartMovingFactory;
import net.smart.moving.SmartMovingSelf;
import net.smart.moving.render.IRenderPlayer;
import net.smart.moving.render.SmartMovingModel;
import net.smart.moving.render.SmartRenderContext;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.spacechase0.minecraft.tpc.Config;

public class PFLM_RenderPlayerSmart extends SmartRenderContext
{
    public PFLM_RenderPlayer irp;
    protected MultiModelBaseBipedSmart[] modelPlayers;
    private static int _iOffset;
    private static int _jOffset;
    private static Minecraft _minecraft;

    public PFLM_RenderPlayerSmart(PFLM_RenderPlayer var1)
    {
    	Modchu_Debug.mDebug("public PFLM_RenderPlayerSmart ");
    	irp = var1;
/*
        modelBipedMain = var1.getPlayerModelBipedMain().getMovingModel();
        modelArmorChestplate = var1.getPlayerModelArmorChestplate().getMovingModel();
        modelArmor = var1.getPlayerModelArmor().getMovingModel();
        modelBipedMain.scaleArmType = 0;
        modelBipedMain.scaleLegType = 0;
        modelArmorChestplate.scaleArmType = 1;
        modelArmorChestplate.scaleLegType = 2;
        modelArmor.scaleArmType = 1;
        modelArmor.scaleLegType = 0;
        modelPlayers = new MultiModelBaseBiped[] {modelBipedMain, modelArmorChestplate, modelArmor};
*/
        modelPlayers = new MultiModelBaseBipedSmart[3];
    }

    public void renderPlayer(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9, PFLM_ModelData modelDataPlayerFormLittleMaid)
    {
    	modelPlayers[0] = (MultiModelBaseBipedSmart) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner;
    	modelPlayers[1] = (MultiModelBaseBipedSmart) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner;
    	modelPlayers[2] = (MultiModelBaseBipedSmart) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter;
    	SmartMoving var10 = SmartMovingFactory.getInstance(var1);

    	if (var10 != null)
    	{
    		boolean var11 = var2 == 0.0D && var4 == 0.0D && var6 == 0.0D && var8 == 0.0F && var9 == 1.0F;
    		boolean var12 = var10.isClimbing && !var10.isCrawling && !var10.isCrawlClimbing && !var10.isClimbJumping;
    		boolean var13 = var10.isClimbJumping;
    		int var14 = var10.actualHandsClimbType;
    		int var15 = var10.actualFeetClimbType;
    		boolean var16 = var10.isHandsVineClimbing;
    		boolean var17 = var10.isFeetVineClimbing;
    		boolean var18 = var10.isCeilingClimbing;
    		boolean var19 = var10.isSwimming && !var10.isDipping;
    		boolean var20 = var10.isDiving;
    		boolean var21 = var10.isLevitating;
    		boolean var22 = var10.isCrawling && !var10.isClimbing;
    		boolean var23 = var10.isCrawlClimbing || var10.isClimbing && var10.isCrawling;
    		boolean var24 = var10.isJumping();
    		boolean var25 = var10.isHeadJumping;
    		boolean var26 = var10.doFlyingAnimation();
    		boolean var27 = var10.isSliding;
    		boolean var28 = var10.doFallingAnimation();
    		boolean var29 = var10.isSlow;
    		boolean var30 = var10.isAngleJumping();
    		int var31 = var10.angleJumpType;
    		float var32 = !var23 && !var25 ? 0.0F : (float)var10.getOverGroundHeight(5.0D);
    		int var33 = var25 && var32 < 5.0F ? var10.getOverGroundBlockId((double)var32) : -1;

    		for (int var34 = 0; var34 < modelPlayers.length; ++var34)
    		{
    			modelPlayers[var34].movingModel.isClimb = var12;
    			modelPlayers[var34].movingModel.isClimbJump = var13;
    			modelPlayers[var34].movingModel.handsClimbType = var14;
    			modelPlayers[var34].movingModel.feetClimbType = var15;
    			modelPlayers[var34].movingModel.isHandsVineClimbing = var16;
    			modelPlayers[var34].movingModel.isFeetVineClimbing = var17;
    			modelPlayers[var34].movingModel.isCeilingClimb = var18;
    			modelPlayers[var34].movingModel.isSwim = var19;
    			modelPlayers[var34].movingModel.isDive = var20;
    			modelPlayers[var34].movingModel.isCrawl = var22;
    			modelPlayers[var34].movingModel.isCrawlClimb = var23;
    			modelPlayers[var34].movingModel.isJump = var24;
    			modelPlayers[var34].movingModel.isHeadJump = var25;
    			modelPlayers[var34].movingModel.isSlide = var27;
    			modelPlayers[var34].movingModel.isFlying = var26;
    			modelPlayers[var34].movingModel.isLevitate = var21;
    			modelPlayers[var34].movingModel.isFalling = var28;
    			modelPlayers[var34].movingModel.isGenericSneaking = var29;
    			modelPlayers[var34].movingModel.isAngleJumping = var30;
    			modelPlayers[var34].movingModel.angleJumpType = var31;
    			modelPlayers[var34].movingModel.smallOverGroundHeight = var32;
    			modelPlayers[var34].movingModel.overGroundBlockId = var33;
    		}

    		if (!var11 && var1.isSneaking() && !(var1 instanceof EntityPlayerSP) && var22)
    		{
    			var4 += 0.125D;
    		}

    	}

    	//irp.superRenderRenderPlayer(var1, var2, var4, var6, var8, var9);

    	if (var10 != null && var10.isLevitating)
    	{
    		for (int var35 = 0; var35 < modelPlayers.length; ++var35)
    		{
    			modelPlayers[var35].currentHorizontalAngle = modelPlayers[var35].currentCameraAngle;
    		}
    	}
    }

    public void rotatePlayer(EntityPlayer var1, float var2, float var3, float var4)
    {
    	SmartMoving var5 = SmartMovingFactory.getInstance(var1);

    	if (var5 != null)
    	{
    		boolean var6 = var4 == 1.0F && var5.isp != null && var5.isp.getMcField().currentScreen instanceof GuiInventory;

    		if (!var6)
    		{
    			float var7 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var4;

    			if (var5.isClimbing || var5.isClimbCrawling || var5.isCrawlClimbing || var5.isFlying || var5.isSwimming || var5.isDiving || var5.isCeilingClimbing || var5.isHeadJumping || var5.isSliding || var5.isAngleJumping())
    			{
    				var1.renderYawOffset = var7;
    			}
    		}
    	}

    	//irp.superRenderRotatePlayer(var1, var2, var3, var4);
    }

    public void renderPlayerAt(EntityPlayer var1, double var2, double var4, double var6)
    {
    	if (var1 instanceof EntityOtherPlayerMP)
    	{
    		SmartMoving var8 = SmartMovingFactory.getOtherSmartMoving(var1.entityId);

    		if (var8 != null && var8.heightOffset != 0.0F)
    		{
    			var4 += (double)var8.heightOffset;
    		}
    	}

    	//irp.superRenderRenderPlayerAt(var1, var2, var4, var6);
    }

    public void renderName(EntityPlayer var1, double var2, double var4, double var6)
    {
    	boolean var8 = false;
    	boolean var9 = false;

    	if (Minecraft.isGuiEnabled() && var1 != irp.getRenderManager().livingPlayer)
    	{
    		SmartMoving var10 = SmartMovingFactory.getInstance(var1);

    		if (var10 != null)
    		{
    			var9 = var1.isSneaking();
    			boolean var11 = var9;

    			if (var10.isCrawling && !var10.isClimbing)
    			{
    				var11 = !((Boolean)Config._crawlNameTag.value).booleanValue();
    			}
    			else if (var9)
    			{
    				var11 = !((Boolean)Config._sneakNameTag.value).booleanValue();
    			}

    			var8 = var11 != var9;

    			if (var8)
    			{
    				var1.setSneaking(var11);
    			}

    			if (var10.heightOffset == -1.0F)
    			{
    				var4 -= var11 ? 1.0D : 1.2999999523162842D;
    			}
    		}
    	}

    	//irp.superRenderRenderName(var1, var2, var4, var6);

    	if (var8)
    	{
    		var1.setSneaking(var9);
    	}
    }

    public static void renderGuiIngame(Minecraft var0)
    {
    	if (Client.getNativeUserInterfaceDrawing())
    	{
    		SmartMovingSelf var1 = (SmartMovingSelf)SmartMovingFactory.getInstance(var0.thePlayer);

    		if (var1 != null && Config.enabled && (((Boolean)Options._displayExhaustionBar.value).booleanValue() || ((Boolean)Options._displayJumpChargeBar.value).booleanValue()))
    		{
    			ScaledResolution var2 = new ScaledResolution(var0.gameSettings, var0.displayWidth, var0.displayHeight);
    			int var3 = var2.getScaledWidth();
    			int var4 = var2.getScaledHeight();

    			if (var0.playerController.shouldDrawHUD())
    			{
    				float var5 = Client.getMaximumExhaustion();
    				float var6 = Math.min(var1.exhaustion, var5);
    				boolean var7 = var6 > 0.0F && var6 <= var5;
    				float var8 = ((Float)Config._jumpChargeMaximum.value).floatValue();
    				float var9 = Math.min(var1.jumpCharge, var8);
    				float var10 = ((Float)Config._headJumpChargeMaximum.value).floatValue();
    				float var11 = Math.min(var1.headJumpCharge, var10);
    				boolean var12 = var9 > 0.0F || var11 > 0.0F;
    				float var13 = var9 > var11 ? var8 : var10;
    				float var14 = Math.max(var9, var11);

    				if (var7 || var12)
    				{
    					GL11.glBindTexture(GL11.GL_TEXTURE_2D, var0.renderEngine.getTexture("/net/smart/resources/gui/icons.png"));
    					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    					_minecraft = var0;
    				}

    				if (var7)
    				{
    					float var15 = Math.min(var1.maxExhaustionForAction, var5);
    					float var16 = Math.min(var1.maxExhaustionToStartAction, var5);
    					float var17 = var5 - var6;
    					float var18 = Float.isNaN(var15) ? 0.0F : var5 - var15;
    					float var19 = Float.isNaN(var16) ? 0.0F : var5 - var16;
    					float var20 = Math.max(Math.max(var19, var17), var18);
    					int var21 = (int)Math.floor((double)(var20 / var5 * 21.0F));
    					int var22 = var21 / 2;
    					int var23 = var21 % 2;
    					int var24 = (int)Math.floor((double)(var17 / var5 * 21.0F));
    					int var25 = var24 / 2;
    					int var26 = var24 % 2;
    					int var27 = (int)Math.floor((double)(var18 / var5 * 21.0F));
    					int var28 = var27 / 2;
    					int var29 = var27 % 2;
    					int var30 = (int)Math.floor((double)(var19 / var5 * 21.0F));
    					int var31 = var30 / 2;
    					_jOffset = var4 - 39 - 10 - (var0.thePlayer.isInsideOfMaterial(Material.water) ? 10 : 0);

    					for (int var32 = 0; var32 < Math.min(var22 + var23, 10); ++var32)
    					{
    						_iOffset = var3 / 2 + 90 - (var32 + 1) * 8;

    						if (var32 < var25)
    						{
    							if (var32 < var28)
    							{
    								drawIcon(2, 2);
    							}
    							else if (var32 == var28 && var29 > 0)
    							{
    								drawIcon(3, 2);
    							}
    							else
    							{
    								drawIcon(0, 0);
    							}
    						}
    						else if (var32 == var25 && var26 > 0)
    						{
    							if (var32 < var28)
    							{
    								drawIcon(1, 2);
    							}
    							else if (var32 == var28 && var29 > 0)
    							{
    								if (var32 < var31)
    								{
    									drawIcon(3, 1);
    								}
    								else
    								{
    									drawIcon(4, 2);
    								}
    							}
    							else if (var32 < var31)
    							{
    								drawIcon(1, 1);
    							}
    							else
    							{
    								drawIcon(1, 0);
    							}
    						}
    						else if (var32 < var28)
    						{
    							drawIcon(0, 2);
    						}
    						else if (var32 == var28 && var29 > 0)
    						{
    							if (var32 < var31)
    							{
    								drawIcon(2, 1);
    							}
    							else
    							{
    								drawIcon(5, 2);
    							}
    						}
    						else if (var32 < var31)
    						{
    							drawIcon(0, 1);
    						}
    						else
    						{
    							drawIcon(4, 1);
    						}
    					}
    				}

    				if (var12)
    				{
    					boolean var33 = var14 == var13;
    					int var34 = var33 ? 10 : (int)Math.ceil((double)(var14 - 2.0F) * 10.0D / (double)var13);
    					int var35 = var33 ? 0 : (int)Math.ceil((double)var14 * 10.0D / (double)var13) - var34;
    					_jOffset = var4 - 39 - 10 - (var0.thePlayer.getTotalArmorValue() > 0 ? 10 : 0);

    					for (int var36 = 0; var36 < var34 + var35; ++var36)
    					{
    						_iOffset = var3 / 2 - 91 + var36 * 8;
    						drawIcon(var36 < var34 ? 2 : 3, 0);
    					}
    				}
    			}

    			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    			GL11.glDisable(GL11.GL_BLEND);
    		}
    	}
    }

    private static void drawIcon(int var0, int var1)
    {
    	_minecraft.ingameGUI.drawTexturedModalRect(_iOffset, _jOffset, var0 * 9, var1 * 9, 9, 9);
    }
}
