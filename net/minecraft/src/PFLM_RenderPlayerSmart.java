package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.move.SmartMoving;
import net.minecraft.move.SmartMovingContext;
import net.minecraft.move.SmartMovingFactory;
import net.minecraft.move.SmartMovingSelf;
import net.minecraft.move.render.IRenderPlayer;
import net.minecraft.move.render.SmartMovingModel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerSmart extends SmartMovingContext
{
    //SmartMovingRenderèëÇ´ä∑Ç¶class

	public PFLM_RenderPlayer irp;
    protected MultiModelSmart[] modelPlayers;
    private static int _iOffset;
    private static int _jOffset;
    private static Minecraft _minecraft;

    public PFLM_RenderPlayerSmart(PFLM_RenderPlayer var1)
    {
        irp = var1;
        modelPlayers = new MultiModelSmart[3];
    }

    public void renderPlayer(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9, PFLM_ModelData modelDataPlayerFormLittleMaid)
    {
    	modelPlayers[0] = (MultiModelSmart) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner;
    	modelPlayers[1] = (MultiModelSmart) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner;
    	modelPlayers[2] = (MultiModelSmart) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter;
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
            boolean var29 = var10.isSneaking;
            boolean var30 = var10.isAngleJumping();
            int var31 = var10.angleJumpType;
            boolean var32 = var10.sp.isPlayerSleeping();
            float var33 = !var23 && !var25 ? 0.0F : (float)var10.getOverGroundHeight(5.0D);
            int var34 = var25 && var33 < 5.0F ? var10.getOverGroundBlockId((double)var33) : -1;
            float var35 = var10.field_703_S_vertical - var10.field_704_R_vertical * (1.0F - var9);
            float var36 = Math.min(1.0F, var10.field_705_Q_vertical + (var10.field_704_R_vertical - var10.field_705_Q_vertical) * var9);
            float var37 = var10.field_703_S_all - var10.field_704_R_all * (1.0F - var9);
            float var38 = Math.min(1.0F, var10.field_705_Q_all + (var10.field_704_R_all - var10.field_705_Q_all) * var9);
            double var39 = 0.0D;
            double var41 = 0.0D;
            double var43 = 0.0D;
            float var45 = 0.0F;
            float var46 = 0.0F;
            float var47 = 0.0F;

            if (!var11 && (var26 || var19 || var20 || var18 || var25 || var27 || var30))
            {
                double var48 = var10.sp.posX - var10.sp.prevPosX;
                double var50 = var10.sp.posY - var10.sp.prevPosY;
                double var52 = var10.sp.posZ - var10.sp.prevPosZ;
                var41 = Math.abs(var50);
                var43 = Math.sqrt(var48 * var48 + var52 * var52);
                var39 = Math.sqrt(var43 * var43 + var41 * var41);
                var45 = var10.sp.rotationYaw / (180F / (float)Math.PI);
                var46 = (float)Math.atan(var50 / var43);

                if (Float.isNaN(var46))
                {
                    var46 = ((float)Math.PI / 2F);
                }

                var47 = (float)(-Math.atan(var48 / var52));

                if (!Float.isNaN(var47) && !var21)
                {
                    if (var52 < 0.0D)
                    {
                        var47 = (float)((double)var47 + Math.PI);
                    }
                }
                else
                {
                    var47 = var45;
                }
            }

            for (int var54 = 0; var54 < modelPlayers.length; ++var54)
            {
                modelPlayers[var54].isInventory = var11;
                modelPlayers[var54].isClimb = var12;
                modelPlayers[var54].isClimbJump = var13;
                modelPlayers[var54].handsClimbType = var14;
                modelPlayers[var54].feetClimbType = var15;
                modelPlayers[var54].isHandsVineClimbing = var16;
                modelPlayers[var54].isFeetVineClimbing = var17;
                modelPlayers[var54].isCeilingClimb = var18;
                modelPlayers[var54].isSwim = var19;
                modelPlayers[var54].isDive = var20;
                modelPlayers[var54].isCrawl = var22;
                modelPlayers[var54].isCrawlClimb = var23;
                modelPlayers[var54].isJump = var24;
                modelPlayers[var54].isHeadJump = var25;
                modelPlayers[var54].isSlide = var27;
                modelPlayers[var54].isFlying = var26;
                modelPlayers[var54].isLevitate = var21;
                modelPlayers[var54].isFalling = var28;
                modelPlayers[var54].isGenericSneaking = var29;
                modelPlayers[var54].isAngleJumping = var30;
                modelPlayers[var54].angleJumpType = var31;
                modelPlayers[var54].smallOverGroundHeight = var33;
                modelPlayers[var54].overGroundBlockId = var34;
                modelPlayers[var54].totalVerticalDistance = var35;
                modelPlayers[var54].currentVerticalSpeed = var36;
                modelPlayers[var54].totalDistance = var37;
                modelPlayers[var54].currentSpeed = var38;
                modelPlayers[var54].distance = var39;
                modelPlayers[var54].verticalDistance = var41;
                modelPlayers[var54].horizontalDistance = var43;
                modelPlayers[var54].currentCameraAngle = var45;
                modelPlayers[var54].currentVerticalAngle = var46;
                modelPlayers[var54].currentHorizontalAngle = var47;
                modelPlayers[var54].prevOuterRenderData = var10.prevOuterRenderData;
                modelPlayers[var54].isSleeping = var32;
            }
        }

        //irp.superRenderPlayer(var1, var2, var4, var6, var8, var9);
    }
/*
    public void drawFirstPersonHand()
    {
        modelBipedMain.firstPerson = true;
        irp.superDrawFirstPersonHand();
        modelBipedMain.firstPerson = false;
    }
*/
    public void rotatePlayer(EntityPlayer var1, float var2, float var3, float var4, PFLM_ModelData modelDataPlayerFormLittleMaid)
    {
    	modelPlayers[0] = (MultiModelSmart) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner;
    	modelPlayers[1] = (MultiModelSmart) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner;
    	modelPlayers[2] = (MultiModelSmart) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter;
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

                if (var5.sp.isPlayerSleeping())
                {
                    var3 = 0.0F;
                    var7 = 0.0F;
                }

                float var8;

                if (var5.isp == null)
                {
                    Minecraft var9 = GetMinecraft();
                    var8 = -var5.sp.rotationYaw;
                    var8 += var9.renderViewEntity.rotationYaw;

                    if (var9.gameSettings.thirdPersonView == 2 && !var9.renderViewEntity.isPlayerSleeping())
                    {
                        var8 += 180.0F;
                    }
                }
                else
                {
                    var8 = var3 - var5.prevOuterRenderData.rotateAngleY * (180F / (float)Math.PI);
                    //Modchu_Debug.mDebug("var5.isp != null var5.prevOuterRenderData.rotateAngleY="+var5.prevOuterRenderData.rotateAngleY);
                }

                for (int var10 = 0; var10 < modelPlayers.length; ++var10)
                {
                    modelPlayers[var10].actualRotation = var3;
                    modelPlayers[var10].forwardRotation = var7;
                    modelPlayers[var10].workingAngle = var8;
                }

                var3 = 0.0F;
            }
        }

        //irp.superRotatePlayer(var1, var2, var3, var4);
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

        //irp.superRenderPlayerAt(var1, var2, var4, var6);
    }

    public static void renderGuiIngame(Minecraft var0)
    {
        SmartMovingSelf var1 = (SmartMovingSelf)SmartMovingFactory.getInstance(var0.thePlayer);

        if (var1 != null && (((Boolean)Options._displayExhaustionBar.value).booleanValue() || ((Boolean)Options._displayJumpChargeBar.value).booleanValue()))
        {
            ScaledResolution var2 = new ScaledResolution(var0.gameSettings, var0.displayWidth, var0.displayHeight);
            int var3 = var2.getScaledWidth();
            int var4 = var2.getScaledHeight();

            if (var0.playerController.shouldDrawHUD())
            {
                float var5 = Config.getMaxExhaustion();
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
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, var0.renderEngine.getTexture("/gui/move/icons.png"));
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

    private static void drawIcon(int var0, int var1)
    {
        _minecraft.ingameGUI.drawTexturedModalRect(_iOffset, _jOffset, var0 * 9, var1 * 9, 9, 9);
    }
}
