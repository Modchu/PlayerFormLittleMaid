package net.minecraft.src;

import net.smart.moving.render.SmartMovingModel;
import net.smart.moving.render.IModelPlayer;
import net.smart.render.SmartRenderModel;

public class PFLM_SmartMovingModel extends SmartMovingModel
{
    public IModelPlayer imp;
    public ModelBiped mp;
    public MultiModelBaseBipedSmart md;

    public PFLM_SmartMovingModel(float var1, net.smart.render.IModelPlayer var2, IModelPlayer var3)
    {
    	super(var1, var2, var3);
        imp = var3;
        md = (MultiModelBaseBipedSmart) var2;
        mp = md;
    }

    private void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        float var7 = 0.6662F;
        isStandard = false;
        float var8 = md.currentCameraAngle;
        float var9 = md.currentHorizontalAngle;
        float var10 = md.currentVerticalAngle;
        float var11 = md.forwardRotation;
        float var12 = md.currentVerticalSpeed;
        float var13 = md.totalVerticalDistance;
        float var14 = md.totalDistance;
        double var15 = md.horizontalDistance;
        float var17 = md.currentSpeed;
        Modchu_ModelRotationRenderer bipedOuter = md.bipedOuter;
        Modchu_ModelRotationRenderer bipedTorso = md.bipedTorso;
        Modchu_ModelRotationRenderer bipedBody = md.bipedBody;
        Modchu_ModelRotationRenderer bipedBreast = md.bipedBreast;
        Modchu_ModelRotationRenderer bipedHead = md.bipedHead;
        Modchu_ModelRotationRenderer bipedRightShoulder = md.bipedRightShoulder;
        Modchu_ModelRotationRenderer bipedRightArm = md.bipedRightArm;
        Modchu_ModelRotationRenderer bipedLeftShoulder = md.bipedLeftShoulder;
        Modchu_ModelRotationRenderer bipedLeftArm = md.bipedLeftArm;
        Modchu_ModelRotationRenderer bipedPelvic = md.bipedPelvic;
        Modchu_ModelRotationRenderer bipedRightLeg = md.bipedRightLeg;
        Modchu_ModelRotationRenderer bipedLeftLeg = md.bipedLeftLeg;
        float var31;
        float var30;
        float var34;
        float var35;
        float var32;
        float var33;
        float var36;

        if (!isClimb && !isCrawlClimb)
        {
            if (isClimbJump)
            {
                bipedRightArm.main.rotateAngleX = 3.5342917F;
                bipedLeftArm.main.rotateAngleX = 3.5342917F;
                bipedRightArm.main.rotateAngleZ = -0.19634955F;
                bipedLeftArm.main.rotateAngleZ = 0.19634955F;
            }
            else if (isCeilingClimb)
            {
                var30 = var1 * 0.7F;
                var31 = Factor(var2, 0.0F, 0.12951545F);
                var32 = Factor(var2, 0.12951545F, 0.0F);
                var33 = var15 < 0.014999999664723873D ? var8 : var9;
                bipedLeftArm.main.rotateAngleX = (MathHelper.cos(var30) * 0.52F + (float)Math.PI) * var31 + (float)Math.PI * var32;
                bipedRightArm.main.rotateAngleX = (MathHelper.cos(var30 + (float)Math.PI) * 0.52F - (float)Math.PI) * var31 - (float)Math.PI * var32;
                bipedLeftLeg.main.rotateAngleX = -MathHelper.cos(var30) * 0.12F * var31;
                bipedRightLeg.main.rotateAngleX = -MathHelper.cos(var30 + (float)Math.PI) * 0.32F * var31;
                var34 = MathHelper.cos(var30) * 0.44F * var31;
                bipedOuter.main.rotateAngleY = var34 + var33;
                bipedRightArm.main.rotateAngleY = bipedLeftArm.main.rotateAngleY = -var34;
                bipedRightLeg.main.rotateAngleY = bipedLeftLeg.main.rotateAngleY = -var34;
                bipedHead.main.rotateAngleY = -var34;
            }
            else if (isSwim)
            {
                var31 = Factor(var2, 0.15679921F, 0.52264464F);
                var32 = Math.min(Factor(var2, 0.0F, 0.15679921F), Factor(var2, 0.52264464F, 0.15679921F));
                var33 = Factor(var2, 0.15679921F, 0.0F);
                var34 = var33 + var32;
                var35 = var15 < (isGenericSneaking ? 0.005D : 0.014999999664723873D) ? var8 : var9;
                bipedHead.rotationOrder = Modchu_ModelRotationRenderer.YXZ;
                bipedHead.main.rotateAngleY = MathHelper.cos(var1 / 2.0F - ((float)Math.PI / 2F)) * var31;
                bipedHead.main.rotateAngleX = -((float)Math.PI / 4F) * var34;
                bipedHead.main.rotationPointZ = -2.0F;
                bipedOuter.main.fadeRotateAngleX = true;
                bipedOuter.main.rotateAngleX = ((float)Math.PI / 2F) - 0.3926991F * var34;
                bipedOuter.main.rotateAngleY = var35;
                bipedBreast.rotateAngleY = bipedBody.rotateAngleY = MathHelper.cos(var1 / 2.0F - ((float)Math.PI / 2F)) * var31;
                bipedRightArm.rotationOrder = Modchu_ModelRotationRenderer.YZX;
                bipedLeftArm.rotationOrder = Modchu_ModelRotationRenderer.YZX;
                bipedRightArm.main.rotateAngleZ = 2.3561945F + MathHelper.cos(var3 * 0.1F) * var34 * 0.8F;
                bipedLeftArm.main.rotateAngleZ = -2.3561945F - MathHelper.cos(var3 * 0.1F) * var34 * 0.8F;
                bipedRightArm.main.rotateAngleX = (var1 * 0.5F % ((float)Math.PI * 2F) - (float)Math.PI) * var31 + 0.3926991F * var34;
                bipedLeftArm.main.rotateAngleX = ((var1 * 0.5F + (float)Math.PI) % ((float)Math.PI * 2F) - (float)Math.PI) * var31 + 0.3926991F * var34;
                bipedRightLeg.main.rotateAngleX = MathHelper.cos(var1) * 0.52264464F * var31;
                bipedLeftLeg.main.rotateAngleX = MathHelper.cos(var1 + (float)Math.PI) * 0.52264464F * var31;
                var36 = 0.3926991F * var34 + MathHelper.cos(var3 * 0.1F) * 0.4F * (var33 - var32);
                bipedRightLeg.main.rotateAngleZ = var36;
                bipedLeftLeg.main.rotateAngleZ = -var36;

                if (scaleLegType != 1)
                {
                    setLegScales(1.0F + (MathHelper.cos(var3 * 0.1F + ((float)Math.PI / 2F)) - 1.0F) * 0.15F * var32, 1.0F + (MathHelper.cos(var3 * 0.1F + ((float)Math.PI / 2F)) - 1.0F) * 0.15F * var32);
                }

                if (scaleArmType != 1)
                {
                    setArmScales(1.0F + (MathHelper.cos(var3 * 0.1F - ((float)Math.PI / 2F)) - 1.0F) * 0.15F * var32, 1.0F + (MathHelper.cos(var3 * 0.1F - ((float)Math.PI / 2F)) - 1.0F) * 0.15F * var32);
                }
            }
            else if (isDive)
            {
                var30 = var14 * 0.7F;
                var31 = Factor(var17, 0.0F, 0.15679921F);
                var32 = Factor(var17, 0.15679921F, 0.0F);
                var33 = (double)var14 < (isGenericSneaking ? 0.005D : 0.014999999664723873D) ? var8 : var9;
                bipedHead.main.rotateAngleX = -((float)Math.PI / 4F);
                bipedHead.main.rotationPointZ = -2.0F;
                bipedOuter.main.fadeRotateAngleX = true;
                bipedOuter.main.rotateAngleX = isLevitate ? 1.1780972F : (isJump ? 0.0F : ((float)Math.PI / 2F) - var10);
                bipedOuter.main.rotateAngleY = var33;
                bipedRightLeg.main.rotateAngleZ = (MathHelper.cos(var30) + 1.0F) * 0.52264464F * var31 + 0.3926991F * var32;
                bipedLeftLeg.main.rotateAngleZ = (MathHelper.cos(var30 + (float)Math.PI) - 1.0F) * 0.52264464F * var31 - 0.3926991F * var32;

                if (scaleLegType != 1)
                {
                    setLegScales(1.0F + (MathHelper.cos(var30 - ((float)Math.PI / 2F)) - 1.0F) * 0.25F * var31, 1.0F + (MathHelper.cos(var30 - ((float)Math.PI / 2F)) - 1.0F) * 0.25F * var31);
                }

                bipedRightArm.main.rotateAngleZ = (MathHelper.cos(var30 + (float)Math.PI) * 0.52264464F * 2.5F + ((float)Math.PI / 2F)) * var31 + 2.3561945F * var32;
                bipedLeftArm.main.rotateAngleZ = (MathHelper.cos(var30) * 0.52264464F * 2.5F - ((float)Math.PI / 2F)) * var31 - 2.3561945F * var32;

                if (scaleArmType != 1)
                {
                    setArmScales(1.0F + (MathHelper.cos(var30 + ((float)Math.PI / 2F)) - 1.0F) * 0.15F * var31, 1.0F + (MathHelper.cos(var30 + ((float)Math.PI / 2F)) - 1.0F) * 0.15F * var31);
                }
            }
            else if (isCrawl)
            {
                var30 = var1 * 1.3F;
                var31 = Factor(var2, 0.0F, 0.12951545F);
                var32 = Factor(var2, 0.12951545F, 0.0F);
                bipedHead.main.rotateAngleZ = -var4 / (180F / (float)Math.PI);
                bipedHead.main.rotateAngleX = -((float)Math.PI / 4F);
                bipedHead.main.rotationPointZ = -2.0F;
                bipedTorso.main.rotatePriority = Modchu_ModelRotationRenderer.YZX;
                bipedTorso.main.rotateAngleX = 1.3744469F;
                bipedTorso.main.rotationPointY = 3.0F;
                bipedTorso.main.rotateAngleZ = MathHelper.cos(var30 + ((float)Math.PI / 2F)) * 0.09817477F * var31;
                bipedBody.rotateAngleY = MathHelper.cos(var30 + (float)Math.PI) * 0.09817477F * var31;
                bipedRightLeg.main.rotateAngleX = (MathHelper.cos(var30 - ((float)Math.PI / 2F)) * 0.09817477F + 0.19634955F) * var31 + 0.19634955F * var32;
                bipedLeftLeg.main.rotateAngleX = (MathHelper.cos(var30 - (float)Math.PI - ((float)Math.PI / 2F)) * 0.09817477F + 0.19634955F) * var31 + 0.19634955F * var32;
                bipedRightLeg.main.rotateAngleZ = (MathHelper.cos(var30 - ((float)Math.PI / 2F)) + 1.0F) * 0.25F * var31 + 0.19634955F * var32;
                bipedLeftLeg.main.rotateAngleZ = (MathHelper.cos(var30 - ((float)Math.PI / 2F)) - 1.0F) * 0.25F * var31 - 0.19634955F * var32;

                if (scaleLegType != 1)
                {
                    setLegScales(1.0F + (MathHelper.cos(var30 + ((float)Math.PI / 2F) - ((float)Math.PI / 2F)) - 1.0F) * 0.25F * var31, 1.0F + (MathHelper.cos(var30 - ((float)Math.PI / 2F) - ((float)Math.PI / 2F)) - 1.0F) * 0.25F * var31);
                }

                bipedRightArm.rotationOrder = Modchu_ModelRotationRenderer.YZX;
                bipedLeftArm.rotationOrder = Modchu_ModelRotationRenderer.YZX;
                bipedRightArm.main.rotateAngleX = 3.926991F;
                bipedLeftArm.main.rotateAngleX = 3.926991F;
                bipedRightArm.main.rotateAngleZ = (MathHelper.cos(var30 + (float)Math.PI) * 0.09817477F + 0.19634955F) * var31 + 0.3926991F * var32;
                bipedLeftArm.main.rotateAngleZ = (MathHelper.cos(var30 + (float)Math.PI) * 0.09817477F - 0.19634955F) * var31 - 0.3926991F * var32;
                bipedRightArm.main.rotateAngleY = -((float)Math.PI / 2F);
                bipedLeftArm.main.rotateAngleY = ((float)Math.PI / 2F);

                if (scaleArmType != 1)
                {
                    setArmScales(1.0F + (MathHelper.cos(var30 + ((float)Math.PI / 2F)) - 1.0F) * 0.15F * var31, 1.0F + (MathHelper.cos(var30 - ((float)Math.PI / 2F)) - 1.0F) * 0.15F * var31);
                }
            }
            else if (isSlide)
            {
                var30 = var1 * 0.7F;
                var31 = Factor(var2, 0.0F, 1.0F) * 0.8F;
                bipedHead.main.rotateAngleZ = -var4 / (180F / (float)Math.PI);
                bipedHead.main.rotateAngleX = -1.1780972F;
                bipedHead.main.rotationPointZ = -2.0F;
                bipedOuter.main.fadeRotateAngleY = false;
                bipedOuter.main.rotateAngleY = var9;
                bipedOuter.main.rotationPointY = 5.0F;
                bipedOuter.main.rotateAngleX = ((float)Math.PI / 2F);
                bipedBody.rotationOrder = Modchu_ModelRotationRenderer.YXZ;
                bipedBody.offsetY = -0.4F;
                bipedBody.rotationPointY = 6.5F;
                bipedBody.rotateAngleX = MathHelper.cos(var30 - ((float)Math.PI / 4F)) * 0.09817477F * var31;
                bipedBody.rotateAngleY = MathHelper.cos(var30 + ((float)Math.PI / 4F)) * 0.09817477F * var31;
                bipedRightLeg.main.rotateAngleX = MathHelper.cos(var30 + (float)Math.PI) * 0.09817477F * var31 + 0.09817477F;
                bipedLeftLeg.main.rotateAngleX = MathHelper.cos(var30 + ((float)Math.PI / 2F)) * 0.09817477F * var31 + 0.09817477F;
                bipedRightLeg.main.rotateAngleZ = 0.19634955F;
                bipedLeftLeg.main.rotateAngleZ = -0.19634955F;
                bipedRightArm.rotationOrder = Modchu_ModelRotationRenderer.YZX;
                bipedLeftArm.rotationOrder = Modchu_ModelRotationRenderer.YZX;
                bipedRightArm.main.rotateAngleX = MathHelper.cos(var30 + ((float)Math.PI / 2F)) * 0.09817477F * var31 + (float)Math.PI - 0.09817477F;
                bipedLeftArm.main.rotateAngleX = MathHelper.cos(var30 - (float)Math.PI) * 0.09817477F * var31 + (float)Math.PI - 0.09817477F;
                bipedRightArm.main.rotateAngleZ = 0.3926991F;
                bipedLeftArm.main.rotateAngleZ = -0.3926991F;
                bipedRightArm.main.rotateAngleY = -((float)Math.PI / 2F);
                bipedLeftArm.main.rotateAngleY = ((float)Math.PI / 2F);
            }
            else if (isFlying)
            {
                var30 = var14 * 0.08F;
                var31 = Factor(var17, 0.0F, 1.0F);
                var32 = Factor(var17, 1.0F, 0.0F);
                var33 = var3 * 0.15F;
                var34 = isJump ? Math.abs(var10) : var10;
                var35 = var15 < 0.05000000074505806D ? var8 : var9;
                bipedOuter.main.fadeRotateAngleX = true;
                bipedOuter.main.rotateAngleX = (((float)Math.PI / 2F) - var34) * var31;
                bipedOuter.main.rotateAngleY = var35;
                bipedHead.main.rotateAngleX = -bipedOuter.main.rotateAngleX / 2.0F;
                bipedRightArm.rotationOrder = Modchu_ModelRotationRenderer.XZY;
                bipedLeftArm.rotationOrder = Modchu_ModelRotationRenderer.XZY;
                bipedRightArm.main.rotateAngleY = MathHelper.cos(var33) * 0.3926991F * var32;
                bipedLeftArm.main.rotateAngleY = MathHelper.cos(var33) * 0.3926991F * var32;
                bipedRightArm.main.rotateAngleZ = (MathHelper.cos(var30 + (float)Math.PI) * 0.09817477F + 2.7488937F) * var31 + ((float)Math.PI / 2F) * var32;
                bipedLeftArm.main.rotateAngleZ = (MathHelper.cos(var30) * 0.09817477F - 2.7488937F) * var31 - ((float)Math.PI / 2F) * var32;
                bipedRightLeg.main.rotateAngleX = MathHelper.cos(var30) * 0.09817477F * var31 + MathHelper.cos(var33 + (float)Math.PI) * 0.09817477F * var32;
                bipedLeftLeg.main.rotateAngleX = MathHelper.cos(var30 + (float)Math.PI) * 0.09817477F * var31 + MathHelper.cos(var33) * 0.09817477F * var32;
                bipedRightLeg.main.rotateAngleZ = 0.09817477F;
                bipedLeftLeg.main.rotateAngleZ = -0.09817477F;
            }
            else if (isHeadJump)
            {
                bipedOuter.main.fadeRotateAngleX = true;
                bipedOuter.main.rotateAngleX = ((float)Math.PI / 2F) - var10;
                bipedOuter.main.rotateAngleY = var9;
                bipedHead.main.rotateAngleX = -bipedOuter.main.rotateAngleX / 2.0F;
                var30 = Math.min(Factor(var10, ((float)Math.PI / 2F), 0.0F), Factor(var10, -((float)Math.PI / 2F), 0.0F));
                bipedRightArm.main.rotateAngleX = var30 * -((float)Math.PI / 4F);
                bipedLeftArm.main.rotateAngleX = var30 * -((float)Math.PI / 4F);
                bipedRightLeg.main.rotateAngleX = var30 * -((float)Math.PI / 4F);
                bipedLeftLeg.main.rotateAngleX = var30 * -((float)Math.PI / 4F);
                var31 = Factor(var10, ((float)Math.PI / 2F), -((float)Math.PI / 2F));

                if (overGroundBlockId > 0 && Block.blocksList[overGroundBlockId].blockMaterial.isSolid())
                {
                    var31 = Math.min(var31, smallOverGroundHeight / 5.0F);
                }

                bipedRightArm.main.rotateAngleZ = 2.7488937F + var31 * ((float)Math.PI / 4F);
                bipedLeftArm.main.rotateAngleZ = -2.7488937F - var31 * ((float)Math.PI / 4F);
                var32 = Factor(var10, -((float)Math.PI / 2F), ((float)Math.PI / 2F));
                bipedRightLeg.main.rotateAngleZ = 0.09817477F * var32;
                bipedLeftLeg.main.rotateAngleZ = -0.09817477F * var32;
            }
            else if (isFalling)
            {
                var30 = var14 * 0.1F;
                bipedRightArm.rotationOrder = Modchu_ModelRotationRenderer.XZY;
                bipedLeftArm.rotationOrder = Modchu_ModelRotationRenderer.XZY;
                bipedRightArm.main.rotateAngleY = MathHelper.cos(var30 + ((float)Math.PI / 2F)) * ((float)Math.PI / 4F);
                bipedLeftArm.main.rotateAngleY = MathHelper.cos(var30 + ((float)Math.PI / 2F)) * ((float)Math.PI / 4F);
                bipedRightArm.main.rotateAngleZ = MathHelper.cos(var30) * ((float)Math.PI / 4F) + ((float)Math.PI / 2F);
                bipedLeftArm.main.rotateAngleZ = MathHelper.cos(var30) * ((float)Math.PI / 4F) - ((float)Math.PI / 2F);
                bipedRightLeg.main.rotateAngleX = MathHelper.cos(var30 + (float)Math.PI + ((float)Math.PI / 2F)) * 0.3926991F + 0.19634955F;
                bipedLeftLeg.main.rotateAngleX = MathHelper.cos(var30 + ((float)Math.PI / 2F)) * 0.3926991F + 0.19634955F;
                bipedRightLeg.main.rotateAngleZ = MathHelper.cos(var30) * 0.3926991F + 0.19634955F;
                bipedLeftLeg.main.rotateAngleZ = MathHelper.cos(var30) * 0.3926991F - 0.19634955F;
            }
            else
            {
                isStandard = true;
            }
        }
        else
        {
            bipedOuter.main.rotateAngleY = var11 / (180F / (float)Math.PI);
            bipedHead.main.rotateAngleY = 0.0F;
            bipedHead.main.rotateAngleX = var5 / (180F / (float)Math.PI);
            bipedLeftLeg.rotationOrder = Modchu_ModelRotationRenderer.YZX;
            bipedRightLeg.rotationOrder = Modchu_ModelRotationRenderer.YZX;
            int var42 = handsClimbType;

            if (isHandsVineClimbing && var42 == 2)
            {
                var42 = 1;
            }

            float var43 = Math.min(0.5F, var12);
            float var44 = Math.min(0.5F, var2);
            float var38;
            float var37;

            switch (var42)
            {
                case 1:
                    var36 = 0.6662F;
                    var37 = 1.0F;
                    var38 = 0.0F;
                    var30 = 0.6662F;
                    var31 = 2.0F;
                    var32 = -2.5F;
                    break;

                case 2:
                    var36 = 0.6662F;
                    var37 = 1.0F;
                    var38 = 0.0F;
                    var30 = 0.6662F;
                    var31 = 2.0F;
                    var32 = -((float)Math.PI / 2F);
                    break;

                default:
                    var36 = 0.6662F;
                    var37 = 1.0F;
                    var38 = 0.0F;
                    var30 = 0.6662F;
                    var31 = 0.0F;
                    var32 = -0.5F;
            }

            float var39;
            float var40;
            float var41;

            switch (feetClimbType)
            {
                case 1:
                    var33 = 0.6662F;
                    var34 = 0.3F / var43;
                    var35 = -0.3F;
                    var39 = 0.6662F;
                    var40 = 0.5F;
                    var41 = 0.0F;
                    break;

                default:
                    var33 = 0.6662F;
                    var34 = 0.0F;
                    var35 = 0.0F;
                    var39 = 0.6662F;
                    var40 = 0.0F;
                    var41 = 0.0F;
            }

            bipedRightArm.main.rotateAngleX = MathHelper.cos(var13 * var30 + (float)Math.PI) * var43 * var31 + var32;
            bipedLeftArm.main.rotateAngleX = MathHelper.cos(var13 * var30) * var43 * var31 + var32;
            bipedRightArm.main.rotateAngleY = MathHelper.cos(var1 * var36 + ((float)Math.PI / 2F)) * var44 * var37 + var38;
            bipedLeftArm.main.rotateAngleY = MathHelper.cos(var1 * var36) * var44 * var37 + var38;

            if (isHandsVineClimbing)
            {
                bipedLeftArm.main.rotateAngleY *= 1.0F + var36;
                bipedRightArm.main.rotateAngleY *= 1.0F + var36;
                bipedLeftArm.main.rotateAngleY += ((float)Math.PI / 4F);
                bipedRightArm.main.rotateAngleY -= ((float)Math.PI / 4F);
                setArmScales(Math.abs(MathHelper.cos(bipedRightArm.main.rotateAngleX)), Math.abs(MathHelper.cos(bipedLeftArm.main.rotateAngleX)));
            }

            if (!isFeetVineClimbing)
            {
                bipedRightLeg.main.rotateAngleX = MathHelper.cos(var13 * var33) * var34 * var43 + var35;
                bipedLeftLeg.main.rotateAngleX = MathHelper.cos(var13 * var33 + (float)Math.PI) * var34 * var43 + var35;
            }

            bipedRightLeg.main.rotateAngleZ = -(MathHelper.cos(var1 * var39) - 1.0F) * var44 * var40 + var41;
            bipedLeftLeg.main.rotateAngleZ = -(MathHelper.cos(var1 * var39 + ((float)Math.PI / 2F)) + 1.0F) * var44 * var40 + var41;
            float var46;
            float var45;

            if (isFeetVineClimbing)
            {
                var45 = (MathHelper.cos(var14 + (float)Math.PI) + 1.0F) * 0.19634955F + 0.3926991F;
                bipedRightLeg.main.rotateAngleX = -var45;
                bipedLeftLeg.main.rotateAngleX = -var45;
                var46 = Math.max(0.0F, MathHelper.cos(var14 - ((float)Math.PI / 2F))) * 0.09817477F;
                bipedLeftLeg.main.rotateAngleZ += -var46;
                bipedRightLeg.main.rotateAngleZ += var46;
                setLegScales(Math.abs(MathHelper.cos(bipedRightLeg.main.rotateAngleX)), Math.abs(MathHelper.cos(bipedLeftLeg.main.rotateAngleX)));
            }

            if (isCrawlClimb)
            {
                var45 = smallOverGroundHeight + 0.25F;
                var46 = 0.7F;
                float var47 = 0.55F;
                float var50;
                float var49;
                float var48;

                if (var45 < var46)
                {
                    var48 = Math.max(0.0F, (float)Math.acos((double)(var45 / var46)));
                    var49 = ((float)Math.PI / 2F) - var48;
                    var50 = 0.19634955F;
                }
                else if (var45 < var46 + var47)
                {
                    var48 = 0.0F;
                    var49 = Math.max(0.0F, (float)Math.acos((double)((var45 - var46) / var47)));
                    var50 = 0.19634955F * (var49 / 1.537F);
                }
                else
                {
                    var48 = 0.0F;
                    var49 = 0.0F;
                    var50 = 0.0F;
                }

                bipedTorso.main.rotateAngleX = var48;
                bipedRightShoulder.main.rotateAngleX = -var48;
                bipedLeftShoulder.main.rotateAngleX = -var48;
                bipedHead.main.rotateAngleX = -var48;
                bipedRightLeg.main.rotateAngleX = var49;
                bipedLeftLeg.main.rotateAngleX = var49;
                bipedRightLeg.main.rotateAngleZ = var50;
                bipedLeftLeg.main.rotateAngleZ = -var50;
            }

            if (var42 == 0 && feetClimbType != 0)
            {
                bipedTorso.main.rotateAngleX = 0.5F;
                bipedHead.main.rotateAngleX -= 0.5F;
                bipedPelvic.main.rotateAngleX -= 0.5F;
                bipedTorso.main.rotationPointZ = -6.0F;
            }
        }
    }

    private boolean isWorking()
    {
        return mp.onGround > 0.0F;
    }

    private void animateAngleJumping()
    {
        float var1 = (float)angleJumpType * ((float)Math.PI / 4F);
        md.bipedPelvic.main.rotateAngleY -= md.bipedOuter.main.rotateAngleY;
        md.bipedPelvic.main.rotateAngleY += md.currentCameraAngle;
        float var2 = 1.0F - Math.abs(var1 - (float)Math.PI) / ((float)Math.PI / 2F);
        float var3 = -Math.min(var1 - (float)Math.PI, 0.0F) / ((float)Math.PI / 2F);
        float var4 = Math.max(var1 - (float)Math.PI, 0.0F) / ((float)Math.PI / 2F);
        md.bipedLeftLeg.main.rotateAngleX = 0.19634955F * (1.0F + var4);
        md.bipedRightLeg.main.rotateAngleX = 0.19634955F * (1.0F + var3);
        md.bipedLeftLeg.main.rotateAngleY = -var1;
        md.bipedRightLeg.main.rotateAngleY = -var1;
        md.bipedLeftLeg.main.rotateAngleZ = 0.19634955F * var2;
        md.bipedRightLeg.main.rotateAngleZ = -0.19634955F * var2;
        md.bipedLeftLeg.rotationOrder = Modchu_ModelRotationRenderer.ZXY;
        md.bipedRightLeg.rotationOrder = Modchu_ModelRotationRenderer.ZXY;
        md.bipedLeftArm.main.rotateAngleZ = -0.3926991F * var4;
        md.bipedRightArm.main.rotateAngleZ = 0.3926991F * var3;
        md.bipedLeftArm.main.rotateAngleX = -((float)Math.PI / 4F) * var2;
        md.bipedRightArm.main.rotateAngleX = -((float)Math.PI / 4F) * var2;
    }

    private void animateNonStandardWorking(float var1)
    {
        md.bipedRightShoulder.main.ignoreSuperRotation = true;
        md.bipedRightShoulder.main.rotateAngleX = var1 / (180F / (float)Math.PI);
        md.bipedRightShoulder.main.rotateAngleY = md.workingAngle / (180F / (float)Math.PI);
        md.bipedRightShoulder.main.rotateAngleZ = (float)Math.PI;
        md.bipedRightShoulder.main.rotatePriority = Modchu_ModelRotationRenderer.ZYX;
        md.bipedRightArm.main.reset();
    }

    public void animateHeadRotation(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        setRotationAngles(var1, var2, var3, var4, var5, var6);

        if (isStandard)
        {
            imp.superAnimateHeadRotation(var1, var2, var3, var4, var5, var6);
        }
    }

    public void animateSleeping(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard)
        {
            imp.superAnimateSleeping(var1, var2, var3, var4, var5, var6);
        }
    }

    public void animateArmSwinging(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard)
        {
            if (isAngleJumping)
            {
                animateAngleJumping();
            }
            else
            {
                imp.superAnimateArmSwinging(var1, var2, var3, var4, var5, var6);
            }
        }
    }

    public void animateRiding(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard)
        {
            imp.superAnimateRiding(var1, var2, var3, var4, var5, var6);
        }
    }

    public void animateLeftArmItemHolding(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard)
        {
            imp.superAnimateLeftArmItemHolding(var1, var2, var3, var4, var5, var6);
        }
    }

    public void animateRightArmItemHolding(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard)
        {
            imp.superAnimateRightArmItemHolding(var1, var2, var3, var4, var5, var6);
        }
    }

    public void animateWorkingBody(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard)
        {
            imp.superAnimateWorkingBody(var1, var2, var3, var4, var5, var6);
        }
        else if (isWorking())
        {
            animateNonStandardWorking(var5);
        }
    }

    public void animateWorkingArms(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard || isWorking())
        {
            imp.superAnimateWorkingArms(var1, var2, var3, var4, var5, var6);
        }
    }

    public void animateSneaking(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard && !isAngleJumping)
        {
            imp.superAnimateSneaking(var1, var2, var3, var4, var5, var6);
        }
    }

    public void animateArms(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard)
        {
            imp.superApplyAnimationOffsets(var1, var2, var3, var4, var5, var6);
        }
    }

    public void animateBowAiming(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        if (isStandard)
        {
            imp.superAnimateBowAiming(var1, var2, var3, var4, var5, var6);
        }
    }

    private void setArmScales(float var1, float var2)
    {
        if (scaleArmType == 0)
        {
            md.bipedRightArm.main.scaleY = var1;
            md.bipedLeftArm.main.scaleY = var2;
        }
        else if (scaleArmType == 2)
        {
            md.bipedRightArm.main.offsetY -= (1.0F - var1) * 0.5F;
            md.bipedLeftArm.main.offsetY -= (1.0F - var2) * 0.5F;
        }
    }

    private void setLegScales(float var1, float var2)
    {
        if (scaleLegType == 0)
        {
            md.bipedRightLeg.main.scaleY = var1;
            md.bipedLeftLeg.main.scaleY = var2;
        }
        else if (scaleLegType == 2)
        {
            md.bipedRightLeg.main.offsetY -= (1.0F - var1) * 0.5F;
            md.bipedLeftLeg.main.offsetY -= (1.0F - var2) * 0.5F;
        }
    }

    private static float Factor(float var0, float var1, float var2)
    {
        return var1 > var2 ? (var0 <= var2 ? 1.0F : (var0 >= var1 ? 0.0F : (var1 - var0) / (var1 - var2))) : (var0 >= var2 ? 1.0F : (var0 <= var1 ? 0.0F : (var0 - var1) / (var2 - var1)));
    }
}
