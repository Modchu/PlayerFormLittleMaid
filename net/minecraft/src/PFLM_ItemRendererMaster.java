package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_ItemRendererMaster {

	public static boolean flipHorizontal = false;
	public static boolean leftHandedness = false;
	public static boolean isOlddays = false;
	public static Object field_110931_c;

	public ItemStack itemToRender;
	public float equippedProgress;
	public float prevEquippedProgress;
    public MapItemRenderer mapItemRenderer;

    public PFLM_ItemRendererMaster() {
    	try {
    		Object o = Modchu_Reflect.getFieldObject(ItemRenderer.class, "olddays", false);
    		if (o != null) isOlddays = true;
    	} catch(Exception e) {
    	}
    }

    public void renderItemInFirstPerson(float f) {
    	itemToRender = null;
    	equippedProgress = 0.0F;
    	prevEquippedProgress = 0.0F;

    	try {
    		// ローカル変数を確保
    		itemToRender = (ItemStack)Modchu_Reflect.getPrivateValue(ItemRenderer.class, this, 4);
    		equippedProgress = (Float)Modchu_Reflect.getPrivateValue(ItemRenderer.class, this, 5);
    		prevEquippedProgress = (Float)Modchu_Reflect.getPrivateValue(ItemRenderer.class, this, 6);
    		field_110931_c = Modchu_Reflect.getPrivateValue(ItemRenderer.class, this, 1);
    	} catch (Exception e) {
    	}
    	renderItemInFirstPersonHand(f);
    }

    public void renderItemInFirstPersonHand(float par1)
    {
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() < 160) {
    		oldRenderItemInFirstPersonHand(par1);
    		return;
    	}

    	float var2 = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * par1;
    	EntityPlayerSP var3 = (EntityPlayerSP) mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * par1;
        GL11.glPushMatrix();
        GL11.glRotatef(var4, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * par1, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        float var6 = var3.prevRenderArmPitch + (var3.renderArmPitch - var3.prevRenderArmPitch) * par1;
        float var7 = var3.prevRenderArmYaw + (var3.renderArmYaw - var3.prevRenderArmYaw) * par1;
        GL11.glRotatef((var3.rotationPitch - var6) * 0.1F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef((var3.rotationYaw - var7) * 0.1F, 0.0F, 1.0F, 0.0F);
        ItemStack var8 = itemToRender;
        World theWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();
        float var9 = theWorld.getLightBrightness(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ));
        var9 = 1.0F;
        int var10 = theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
        int var11 = var10 % 65536;
        int var12 = var10 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var11 / 1.0F, (float)var12 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var13;
        float var20;
        float var22;

        if (var8 != null)
        {
            var10 = Item.itemsList[var8.itemID].getColorFromItemStack(var8, 0);
            var20 = (float)(var10 >> 16 & 255) / 255.0F;
            var22 = (float)(var10 >> 8 & 255) / 255.0F;
            var13 = (float)(var10 & 255) / 255.0F;
            GL11.glColor4f(var9 * var20, var9 * var22, var9 * var13, 1.0F);
        }
        else
        {
            GL11.glColor4f(var9, var9, var9, 1.0F);
        }

        float var14;
        float var15;
        float var16;
        float var21;
        Render var27;
        RenderPlayer var26;

        Object textureManager = Modchu_Reflect.invokeMethod("Minecraft", "func_110434_K", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
        if (var8 != null && var8.itemID == Item.map.itemID)
        {
            GL11.glPushMatrix();
            var21 = 0.8F;
            var20 = var3.getSwingProgress(par1);
            var22 = MathHelper.sin(var20 * (float)Math.PI);
            var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI);
            GL11.glTranslatef(-var13 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI * 2.0F) * 0.2F, -var22 * 0.2F);
            var20 = 1.0F - var4 / 45.0F + 0.1F;

            if (var20 < 0.0F)
            {
                var20 = 0.0F;
            }

            if (var20 > 1.0F)
            {
                var20 = 1.0F;
            }

            var20 = -MathHelper.cos(var20 * (float)Math.PI) * 0.5F + 0.5F;
            GL11.glTranslatef(0.0F, 0.0F * var21 - (1.0F - var2) * 1.2F - var20 * 0.5F + 0.04F, -0.9F * var21);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(var20 * -85.0F, 0.0F, 0.0F, 1.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            Modchu_Reflect.invokeMethod("TextureManager", "func_110577_a", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, textureManager, new Object[]{ Modchu_Reflect.invokeMethod("AbstractClientPlayer", "func_110306_p", var3) });
            //mc.func_110434_K().func_110577_a(var3.func_110306_p());

            for (var12 = 0; var12 < 2; ++var12)
            {
                int var24 = var12 * 2 - 1;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float)var24);
                GL11.glRotatef((float)(-45 * var24), 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef((float)(-65 * var24), 0.0F, 1.0F, 0.0F);
                var27 = RenderManager.instance.getEntityRenderObject(var3);
                var26 = (RenderPlayer)var27;
                var16 = 1.0F;
                GL11.glScalef(var16, var16, var16);
                var26.renderFirstPersonArm(var3);
                GL11.glPopMatrix();
            }

            var22 = var3.getSwingProgress(par1);
            var13 = MathHelper.sin(var22 * var22 * (float)Math.PI);
            var14 = MathHelper.sin(MathHelper.sqrt_float(var22) * (float)Math.PI);
            GL11.glRotatef(-var13 * 20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-var14 * 20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-var14 * 80.0F, 1.0F, 0.0F, 0.0F);
            var15 = 0.38F;
            GL11.glScalef(var15, var15, var15);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
            var16 = 0.015625F;
            GL11.glScalef(var16, var16, var16);
            Modchu_Reflect.invokeMethod("TextureManager", "func_110577_a", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, textureManager, new Object[]{ field_110931_c });
            //mc.func_110434_K().func_110577_a(field_110931_c);
            Tessellator var30 = Tessellator.instance;
            GL11.glNormal3f(0.0F, 0.0F, -1.0F);
            var30.startDrawingQuads();
            byte var29 = 7;
            var30.addVertexWithUV((double)(0 - var29), (double)(128 + var29), 0.0D, 0.0D, 1.0D);
            var30.addVertexWithUV((double)(128 + var29), (double)(128 + var29), 0.0D, 1.0D, 1.0D);
            var30.addVertexWithUV((double)(128 + var29), (double)(0 - var29), 0.0D, 1.0D, 0.0D);
            var30.addVertexWithUV((double)(0 - var29), (double)(0 - var29), 0.0D, 0.0D, 0.0D);
            var30.draw();
            MapData var19 = Item.map.getMapData(var8, theWorld);

            if (var19 != null)
            {
            	Modchu_Reflect.invokeMethod(MapItemRenderer.class, "renderMap", new Class[]{ EntityPlayer.class, TextureManager.class, MapData.class }, mapItemRenderer, new Object[]{ var3, textureManager, var19 });
            	//mapItemRenderer.renderMap(var3, textureManager, var19);
            }

            GL11.glPopMatrix();
        }
        else if (var8 != null)
        {
            GL11.glPushMatrix();
            var21 = 0.8F;

            if (var3.getItemInUseCount() > 0)
            {
                EnumAction var23 = var8.getItemUseAction();

                if (var23 == EnumAction.eat || var23 == EnumAction.drink)
                {
                    var22 = (float)var3.getItemInUseCount() - par1 + 1.0F;
                    var13 = 1.0F - var22 / (float)var8.getMaxItemUseDuration();
                    var14 = 1.0F - var13;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    var15 = 1.0F - var14;
                    GL11.glTranslatef(0.0F, MathHelper.abs(MathHelper.cos(var22 / 4.0F * (float)Math.PI) * 0.1F) * (float)((double)var13 > 0.2D ? 1 : 0), 0.0F);
                    GL11.glTranslatef(var15 * 0.6F, -var15 * 0.5F, 0.0F);
                    GL11.glRotatef(var15 * 90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(var15 * 10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(var15 * 30.0F, 0.0F, 0.0F, 1.0F);
                }
            }
            else
            {
                var20 = var3.getSwingProgress(par1);
                var22 = MathHelper.sin(var20 * (float)Math.PI);
                var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI);
                GL11.glTranslatef(-var13 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI * 2.0F) * 0.2F, -var22 * 0.2F);
            }

            GL11.glTranslatef(0.7F * var21, -0.65F * var21 - (1.0F - var2) * 0.6F, -0.9F * var21);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            var20 = var3.getSwingProgress(par1);
            var22 = MathHelper.sin(var20 * var20 * (float)Math.PI);
            var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI);
            GL11.glRotatef(-var22 * 20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-var13 * 20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-var13 * 80.0F, 1.0F, 0.0F, 0.0F);
            var14 = 0.4F;
            GL11.glScalef(var14, var14, var14);
            float var17;
            float var18;

            if (var3.getItemInUseCount() > 0)
            {
                EnumAction var25 = var8.getItemUseAction();

                if (var25 == EnumAction.block)
                {
                    GL11.glTranslatef(-0.5F, 0.2F, 0.0F);
                    GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
                }
                else if (var25 == EnumAction.bow)
                {
                    GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
                    var16 = (float)var8.getMaxItemUseDuration() - ((float)var3.getItemInUseCount() - par1 + 1.0F);
                    var17 = var16 / 20.0F;
                    var17 = (var17 * var17 + var17 * 2.0F) / 3.0F;

                    if (var17 > 1.0F)
                    {
                        var17 = 1.0F;
                    }

                    if (var17 > 0.1F)
                    {
                        GL11.glTranslatef(0.0F, MathHelper.sin((var16 - 0.1F) * 1.3F) * 0.01F * (var17 - 0.1F), 0.0F);
                    }

                    GL11.glTranslatef(0.0F, 0.0F, var17 * 0.1F);
                    GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.5F, 0.0F);
                    var18 = 1.0F + var17 * 0.2F;
                    GL11.glScalef(1.0F, 1.0F, var18);
                    GL11.glTranslatef(0.0F, -0.5F, 0.0F);
                    GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
                }
            }

            if (var8.getItem().shouldRotateAroundWhenRendering())
            {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var8.getItem().requiresMultipleRenderPasses())
            {
            	RenderManager.instance.itemRenderer.renderItem(var3, var8, 0);
                int var28 = Item.itemsList[var8.itemID].getColorFromItemStack(var8, 1);
                var16 = (float)(var28 >> 16 & 255) / 255.0F;
                var17 = (float)(var28 >> 8 & 255) / 255.0F;
                var18 = (float)(var28 & 255) / 255.0F;
                GL11.glColor4f(var9 * var16, var9 * var17, var9 * var18, 1.0F);
                RenderManager.instance.itemRenderer.renderItem(var3, var8, 1);
            }
            else
            {
            	RenderManager.instance.itemRenderer.renderItem(var3, var8, 0);
            }

            GL11.glPopMatrix();
        }
//-@-132
        else if (!var3.isInvisible())
        {
//@-@132
            GL11.glPushMatrix();
            if (leftHandedness) {
                var21 = 0.8F;
                var20 = var3.getSwingProgress(par1);
                var22 = MathHelper.sin(var20 * (float)Math.PI);
                var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI);
                float x = -var11 * 0.3F;
                float y = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI * 2.0F) * 0.4F;
                float z = -var12 * 0.4F;
                GL11.glTranslatef(-x, y, z);
                x = 0.8F * var6;
                y = -0.75F * var6 - (1.0F - var2) * 0.6F;
                z = -0.9F * var6;
                GL11.glTranslatef(-x, y, z);
                GL11.glRotatef(-45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                var20 = -var3.getSwingProgress(par1);
                var22 = MathHelper.sin(var20 * var20 * (float)Math.PI);
                var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI);
                GL11.glRotatef(-var11 * 70.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(var12 * 20.0F, 0.0F, 0.0F, 1.0F);
                Modchu_Reflect.invokeMethod("TextureManager", "func_110577_a", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, textureManager, new Object[]{ Modchu_Reflect.invokeMethod("AbstractClientPlayer", "func_110306_p", var3) });
                //mc.func_110434_K().func_110577_a(var3.func_110306_p());
                GL11.glTranslatef(1.0F, 3.6F, 3.5F);
                GL11.glRotatef(-120.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-200.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef(-4.8F, -2.4F, 0.5F);
                //GL11.glTranslatef(4.8F, 0.5F, 0.0F);
            	//if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
                var27 = RenderManager.instance.getEntityRenderObject(var3);
                var16 = 1.0F;
                GL11.glScalef(var16, var16, var16);
            } else {
                var21 = 0.8F;
                var20 = var3.getSwingProgress(par1);
                var22 = MathHelper.sin(var20 * (float)Math.PI);
                var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI);
                GL11.glTranslatef(-var13 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI * 2.0F) * 0.4F, -var22 * 0.4F);
                GL11.glTranslatef(0.8F * var21, -0.75F * var21 - (1.0F - var2) * 0.6F, -0.9F * var21);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                var20 = var3.getSwingProgress(par1);
                var22 = MathHelper.sin(var20 * var20 * (float)Math.PI);
                var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float)Math.PI);
                GL11.glRotatef(var13 * 70.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-var22 * 20.0F, 0.0F, 0.0F, 1.0F);
                Modchu_Reflect.invokeMethod("TextureManager", "func_110577_a", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, textureManager, new Object[]{ Modchu_Reflect.invokeMethod("AbstractClientPlayer", "func_110306_p", var3) });
                //mc.func_110434_K().func_110577_a(var3.func_110306_p());
                GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
                GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef(5.52F, 0.6F, 0.0F);
                var27 = RenderManager.instance.getEntityRenderObject(var3);
                var16 = 1.0F;
                GL11.glScalef(var16, var16, var16);
            }
            if (var27 instanceof RenderPlayer) ((RenderPlayer) var27).renderFirstPersonArm(var3);
            else Modchu_Reflect.invokeMethod(var27.getClass(), "func_82441_a", "renderFirstPersonArm", new Class[]{ Entity.class }, var27, new Object[]{ var3 });
            GL11.glPopMatrix();
//-@-132
        }
//@-@132
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
    }

    public void oldRenderItemInFirstPersonHand(float par1)
    {
        float var2 = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * par1;
        EntityPlayerSP var3 = (EntityPlayerSP) mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
        World theWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();

        float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * par1;
        GL11.glPushMatrix();
        GL11.glRotatef(var4, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * par1, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        float var5;
        float var6;

        if (var3 instanceof EntityPlayerSP)
        {
            var5 = var3.prevRenderArmPitch + (var3.renderArmPitch - var3.prevRenderArmPitch) * par1;
            var6 = var3.prevRenderArmYaw + (var3.renderArmYaw - var3.prevRenderArmYaw) * par1;
            GL11.glRotatef((var3.rotationPitch - var5) * 0.1F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef((var3.rotationYaw - var6) * 0.1F, 0.0F, 1.0F, 0.0F);
        }

        //ItemStack var7 = itemToRender;
        var5 = theWorld.getLightBrightness(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ));
        var5 = 1.0F;
        int var8 = theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
        int var9 = var8 % 65536;
        int var10 = var8 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var9 / 1.0F, (float)var10 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var11;
        float var12;
        float var13;

        GL11.glColor4f(var5, var5, var5, 1.0F);

        float var14;
        float var15;
        Render var17;
        float var16;
        RenderPlayer var18;
//-@-132
        if (!var3.isInvisible())
        {
//@-@132
            GL11.glPushMatrix();
            if (leftHandedness) {
                var6 = 0.8F;
                //var13 = 0.5F;
                var13 = var3.getSwingProgress(par1);
                var12 = MathHelper.sin(var13 * (float)Math.PI);
                var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
                float x = -var11 * 0.3F;
                float y = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI * 2.0F) * 0.4F;
                float z = -var12 * 0.4F;
                GL11.glTranslatef(-x, y, z);
                x = 0.8F * var6;
                y = -0.75F * var6 - (1.0F - var2) * 0.6F;
                z = -0.9F * var6;
                GL11.glTranslatef(-x, y, z);
                GL11.glRotatef(-45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                var13 = -var3.getSwingProgress(par1);
                var12 = MathHelper.sin(var13 * var13 * (float)Math.PI);
                var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
                GL11.glRotatef(-var11 * 70.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(var12 * 20.0F, 0.0F, 0.0F, 1.0F);
                Object renderEngine = Modchu_Reflect.getFieldObject("Minecraft", "renderEngine", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
                EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
                int i = (Integer) Modchu_Reflect.invokeMethod("RenderEngine", "func_78350_a", "getTextureForDownloadableImage", new Class[]{ String.class, String.class }, renderEngine, new Object[]{ Modchu_Reflect.getFieldObject(Entity.class, "field_70120_cr", "skinUrl", thePlayer), Modchu_Reflect.invokeMethod(EntityLiving.class, "func_70073_O", "getTexture", thePlayer) });
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, i);
                GL11.glTranslatef(1.0F, 3.6F, 3.5F);
                GL11.glRotatef(-120.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-200.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef(-4.8F, -2.9F, 0.5F);
                var17 = RenderManager.instance.getEntityRenderObject(var3);
            	var16 = 1.0F;
            	GL11.glScalef(var16, var16, var16);
            } else {
                var6 = 0.8F;
                var13 = var3.getSwingProgress(par1);
                var12 = MathHelper.sin(var13 * (float)Math.PI);
                var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
                GL11.glTranslatef(-var11 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI * 2.0F) * 0.4F, -var12 * 0.4F);
                GL11.glTranslatef(0.8F * var6, -0.75F * var6 - (1.0F - var2) * 0.6F, -0.9F * var6);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                var13 = var3.getSwingProgress(par1);
                var12 = MathHelper.sin(var13 * var13 * (float)Math.PI);
                var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
                GL11.glRotatef(var11 * 70.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-var12 * 20.0F, 0.0F, 0.0F, 1.0F);
                Object renderEngine = Modchu_Reflect.getFieldObject("Minecraft", "renderEngine", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
                EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
                int i = (Integer) Modchu_Reflect.invokeMethod("RenderEngine", "func_78350_a", "getTextureForDownloadableImage", new Class[]{ String.class, String.class }, renderEngine, new Object[]{ Modchu_Reflect.getFieldObject(Entity.class, "field_70120_cr", "skinUrl", thePlayer), Modchu_Reflect.invokeMethod(EntityLiving.class, "func_70073_O", "getTexture", thePlayer) });
                GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
                GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef(5.6F, 0.0F, 0.0F);
                var17 = RenderManager.instance.getEntityRenderObject(var3);
            	var16 = 1.0F;
            	GL11.glScalef(var16, var16, var16);
            }
            if (var17 instanceof RenderPlayer) ((RenderPlayer) var17).renderFirstPersonArm(var3);
            else Modchu_Reflect.invokeMethod(var17.getClass(), "renderFirstPersonArm", var17, new Object[]{ var3 });
            GL11.glPopMatrix();
//-@-132
        }
//@-@132
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
    }

    public boolean getFlipHorizontal() {
    	return flipHorizontal;
    }

    public void setFlipHorizontal(boolean b) {
    	flipHorizontal = b;
    }

    public void drawFirstPersonHand(Render r, int h)
    {
    	//Modchu_Debug.Debug("drawFirstPersonHand h="+h);
    	//olddays用
    	boolean b = false;
    	if (isOlddays) b = (Boolean) Modchu_Reflect.getFieldObject(ItemRenderer.class, "olddays");
    	EntityPlayerSP thePlayer = (EntityPlayerSP) mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	if (!b) {
    		//Modchu_Debug.Debug("drawFirstPersonHand b!");
    		((RenderPlayer)r).renderFirstPersonArm(thePlayer);
    		return;
    	}
    	Modchu_Reflect.invokeMethod(r.getClass(), "func_82441_a", "renderFirstPersonArm", new Class[]{ EntityPlayer.class, int.class }, r, new Object[]{ thePlayer, h });
    	return;
    }
}