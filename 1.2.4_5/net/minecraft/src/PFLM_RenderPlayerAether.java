package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerAether extends PFLM_RenderPlayer
{
    private ModelBiped modelEnergyShield = new ModelBiped(1.25F);
    private ModelBiped modelCape = new ModelBiped(0.0F);
    private ModelBiped modelMisc = new ModelBiped(0.6F);

    protected boolean setEnergyShieldBrightness(EntityPlayer var1, int var2, float var3)
    {
        if (var1.worldObj.isRemote)
        {
            return false;
        }
        else if (var2 != 0)
        {
            return false;
        }
        else
        {
            InventoryAether var4 = mod_Aether.getPlayer().inv;
            boolean var5 = var4 != null && var4.slots[2] != null && var4.slots[2].itemID == AetherItems.RepShield.shiftedIndex;

            if (!var5)
            {
                return false;
            }
            else
            {
                if ((var1.onGround || var1.ridingEntity != null && var1.ridingEntity.onGround) && var1.moveForward == 0.0F && var1.moveStrafing == 0.0F)
                {
                    this.loadTexture("/aether/mobs/energyGlow.png");
                    GL11.glEnable(GL11.GL_NORMALIZE);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    GL11.glEnable(GL11.GL_NORMALIZE);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    this.loadTexture("/aether/mobs/energyNotGlow.png");
                }

                return true;
            }
        }
    }

    public void renderEnergyShield(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9)
    {
        ItemStack var10 = var1.inventory.getCurrentItem();
        this.modelEnergyShield.heldItemRight = var10 != null ? 1 : 0;
        this.modelEnergyShield.isSneak = var1.isSneaking();
        double var11 = var4 - (double)var1.yOffset;

        if (var1.isSneaking() && !(var1 instanceof EntityPlayerSP))
        {
            var11 -= 0.125D;
        }

        this.doRenderEnergyShield(var1, var2, var11, var6, var8, var9);
        this.modelEnergyShield.isSneak = false;
        this.modelEnergyShield.heldItemRight = 0;
    }

    public void renderMisc(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9)
    {
        ItemStack var10 = var1.inventory.getCurrentItem();
        this.modelMisc.heldItemRight = var10 != null ? 1 : 0;
        this.modelMisc.isSneak = var1.isSneaking();
        double var11 = var4 - (double)var1.yOffset;

        if (var1.isSneaking() && !(var1 instanceof EntityPlayerSP))
        {
            var11 -= 0.125D;
        }

        this.doRenderMisc(var1, var2, var11, var6, var8, var9);
        this.modelMisc.isSneak = false;
        this.modelMisc.heldItemRight = 0;
    }

    public void doRenderMisc(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9)
    {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
        this.modelMisc.onGround = this.renderSwingProgress(var1, var9);
        this.modelMisc.isRiding = var1.isRiding();

        try
        {
            float var10 = var1.prevRenderYawOffset + (var1.renderYawOffset - var1.prevRenderYawOffset) * var9;
            float var11 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9;
            float var12 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9;
            this.renderLivingAt(var1, var2, var4, var6);
            float var13 = this.handleRotationFloat(var1, var9);
            this.rotateCorpse(var1, var13, var10, var9);
            float var14 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(var1, var9);
            GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
            float var15 = var1.field_705_Q + (var1.field_704_R - var1.field_705_Q) * var9;
            float var16 = var1.field_703_S - var1.field_704_R * (1.0F - var9);

            if (var15 > 1.0F)
            {
                var15 = 1.0F;
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            this.modelMisc.setRotationAngles(var16, var15, var13, var11 - var10, var12, var14);
            float var17 = var1.getBrightness(var8);

            if (!var1.worldObj.isRemote)
            {
                InventoryAether var18 = mod_Aether.getPlayer(var1).inv;
                ItemMoreArmor var19;
                float var21;
                int var20;
                float var23;
                float var22;

                if (var18.slots[0] != null)
                {
                    var19 = (ItemMoreArmor)((ItemMoreArmor)var18.slots[0].getItem());
                    this.loadTexture(var19.texture);
                    var20 = var19.getColorFromDamage(0, 1);
                    var21 = (float)(var20 >> 16 & 255) / 255.0F;
                    var22 = (float)(var20 >> 8 & 255) / 255.0F;
                    var23 = (float)(var20 & 255) / 255.0F;

                    if (var19.colouriseRender)
                    {
                        GL11.glColor3f(var21 * var17, var22 * var17, var23 * var17);
                    }
                    else
                    {
                        GL11.glColor3f(var17, var17, var17);
                    }

                    this.modelMisc.bipedBody.render(var14);
                }

                if (var18.slots[6] != null)
                {
                    var19 = (ItemMoreArmor)((ItemMoreArmor)var18.slots[6].getItem());
                    this.loadTexture(var19.texture);
                    var20 = var19.getColorFromDamage(0, 1);
                    var21 = (float)(var20 >> 16 & 255) / 255.0F;
                    var22 = (float)(var20 >> 8 & 255) / 255.0F;
                    var23 = (float)(var20 & 255) / 255.0F;

                    if (var19.colouriseRender)
                    {
                        GL11.glColor3f(var21 * var17, var22 * var17, var23 * var17);
                    }
                    else
                    {
                        GL11.glColor3f(var17, var17, var17);
                    }

                    this.modelMisc.bipedLeftArm.render(var14);
                    this.modelMisc.bipedRightArm.render(var14);
                }
            }

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        catch (Exception var24)
        {
            var24.printStackTrace();
        }

        GL11.glPopMatrix();
    }

    public void drawFirstPersonHand()
    {
        if (this.renderManager.renderEngine != null && !this.invisible(ModLoader.getMinecraftInstance().thePlayer))
        {
            super.drawFirstPersonHand();
        	if (!playerModeltype()) return;

            if (!ModLoader.getMinecraftInstance().theWorld.isRemote)
            {
                EntityPlayerSP var1 = ModLoader.getMinecraftInstance().thePlayer;
                InventoryAether var2 = mod_Aether.getPlayer(var1).inv;

                if (var2.slots[6] != null)
                {
                    float var3 = var1.getBrightness(1.0F);
                    this.modelMisc.onGround = 0.0F;
                    this.modelMisc.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                    this.modelMisc.bipedRightArm.render(0.0625F);
                    ItemMoreArmor var4 = (ItemMoreArmor)((ItemMoreArmor)var2.slots[6].getItem());
                    this.loadTexture(var4.texture);
                    int var5 = var4.getColorFromDamage(0, 1);
                    float var6 = (float)(var5 >> 16 & 255) / 255.0F;
                    float var7 = (float)(var5 >> 8 & 255) / 255.0F;
                    float var8 = (float)(var5 & 255) / 255.0F;

                    if (var4.colouriseRender)
                    {
                        GL11.glColor3f(var6 * var3, var7 * var3, var8 * var3);
                    }
                    else
                    {
                        GL11.glColor3f(var3, var3, var3);
                    }

                    this.modelMisc.bipedRightArm.render(0.0625F);
                }
            }
        }
    }

    protected void renderEquippedItems(EntityLiving var1, float var2)
    {
        this.renderSpecials((EntityPlayer)var1, var2);
        this.renderCape((EntityPlayer)var1, var2);
    }

    public void renderCape(EntityPlayer var1, float var2)
    {
    	if (!playerModeltype()) return;
        InventoryAether var3 = mod_Aether.getPlayer(var1).inv;

        if (var3.slots[1] != null)
        {
            ItemStack var4 = var3.slots[1];

            if (var4.itemID == AetherItems.RepShield.shiftedIndex)
            {
                return;
            }

            this.loadTexture(((ItemMoreArmor)((ItemMoreArmor)var4.getItem())).texture);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double var5 = var1.field_20066_r + (var1.field_20063_u - var1.field_20066_r) * (double)var2 - (var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var2);
            double var7 = var1.field_20065_s + (var1.field_20062_v - var1.field_20065_s) * (double)var2 - (var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var2);
            double var9 = var1.field_20064_t + (var1.field_20061_w - var1.field_20064_t) * (double)var2 - (var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var2);
            float var11 = var1.prevRenderYawOffset + (var1.renderYawOffset - var1.prevRenderYawOffset) * var2;
            double var12 = (double)MathHelper.sin(var11 * (float)Math.PI / 180.0F);
            double var14 = (double)(-MathHelper.cos(var11 * (float)Math.PI / 180.0F));
            float var16 = (float)var7 * 10.0F;

            if (var16 < -6.0F)
            {
                var16 = -6.0F;
            }

            if (var16 > 32.0F)
            {
                var16 = 32.0F;
            }

            float var17 = (float)(var5 * var12 + var9 * var14) * 100.0F;
            float var18 = (float)(var5 * var14 - var9 * var12) * 100.0F;

            if (var17 < 0.0F)
            {
                var17 = 0.0F;
            }

            float var19 = var1.prevCameraYaw + (var1.cameraYaw - var1.prevCameraYaw) * var2;
            var16 += MathHelper.sin((var1.prevDistanceWalkedModified + (var1.distanceWalkedModified - var1.prevDistanceWalkedModified) * var2) * 6.0F) * 32.0F * var19;

            if (var1.isSneaking())
            {
                var16 += 25.0F;
            }

            GL11.glRotatef(6.0F + var17 / 2.0F + var16, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var18 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-var18 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.modelCape.renderCloak(0.0625F);
            GL11.glPopMatrix();
        }
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        if (!this.invisible((EntityPlayer)var1))
        {
            super.doRenderLiving(var1, var2, var4, var6, var8, var9);
        	//if (!playerModeltype()) return;
            //this.renderEnergyShield((EntityPlayer)var1, var2, var4, var6, var8, var9);
            //this.renderMisc((EntityPlayer)var1, var2, var4, var6, var8, var9);
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        if (!this.invisible((EntityPlayer)var1))
        {
            super.doRender(var1, var2, var4, var6, var8, var9);
        	if (!playerModeltype()) return;
            this.renderEnergyShield((EntityPlayer)var1, var2, var4, var6, var8, var9);
            this.renderMisc((EntityPlayer)var1, var2, var4, var6, var8, var9);
        }
    }

    public boolean invisible(EntityPlayer var1)
    {
        if (GuiMainMenu.mmactive)
        {
            return true;
        }
        else if (var1.worldObj.isRemote)
        {
            return false;
        }
        else
        {
            InventoryAether var2 = mod_Aether.getPlayer(var1).inv;
            return !var1.isSwinging && var2.slots[1] != null && var2.slots[1].itemID == AetherItems.InvisibilityCloak.shiftedIndex;
        }
    }

    public void doRenderEnergyShield(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
    	GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
        this.modelEnergyShield.onGround = this.renderSwingProgress(var1, var9);
        this.modelEnergyShield.isRiding = var1.isRiding();

        try
        {
            float var10 = var1.prevRenderYawOffset + (var1.renderYawOffset - var1.prevRenderYawOffset) * var9;
            float var11 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9;
            float var12 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9;
            this.renderLivingAt(var1, var2, var4, var6);
            float var13 = this.handleRotationFloat(var1, var9);
            this.rotateCorpse(var1, var13, var10, var9);
            float var14 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(var1, var9);
            GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
            float var15 = var1.field_705_Q + (var1.field_704_R - var1.field_705_Q) * var9;
            float var16 = var1.field_703_S - var1.field_704_R * (1.0F - var9);

            if (var15 > 1.0F)
            {
                var15 = 1.0F;
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);

            if (this.setEnergyShieldBrightness((EntityPlayer)var1, 0, var9))
            {
                this.modelEnergyShield.render(var1, var16, var15, var13, var11 - var10, var12, var14);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        catch (Exception var17)
        {
            var17.printStackTrace();
        }

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

    public boolean playerModeltype() {
    	return mod_PFLM_PlayerFormLittleMaid.textureName.indexOf("_Biped") != -1 ? true : false;
    }
}
