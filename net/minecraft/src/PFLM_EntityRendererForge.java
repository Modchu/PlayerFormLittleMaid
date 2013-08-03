package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.ForgeHooksClient;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class PFLM_EntityRendererForge extends EntityRenderer {

    public static boolean anaglyphEnable = false;

    /** Anaglyph field (0=R, 1=GB) */
    public static int anaglyphField;

    /** A reference to the Minecraft object. */
    private Minecraft mc;
    private float farPlaneDistance = 0.0F;
    public ItemRenderer itemRenderer;

    /** Entity renderer update count */
    private int rendererUpdateCount;

    /** Pointed entity */
    private Entity pointedEntity = null;
    private MouseFilter mouseFilterXAxis = new MouseFilter();
    private MouseFilter mouseFilterYAxis = new MouseFilter();

    /** Mouse filter dummy 1 */
    private MouseFilter mouseFilterDummy1 = new MouseFilter();

    /** Mouse filter dummy 2 */
    private MouseFilter mouseFilterDummy2 = new MouseFilter();

    /** Mouse filter dummy 3 */
    private MouseFilter mouseFilterDummy3 = new MouseFilter();

    /** Mouse filter dummy 4 */
    private MouseFilter mouseFilterDummy4 = new MouseFilter();
    private float thirdPersonDistance = 4.0F;

    /** Third person distance temp */
    private float thirdPersonDistanceTemp = 4.0F;
    private float debugCamYaw = 0.0F;
    private float prevDebugCamYaw = 0.0F;
    private float debugCamPitch = 0.0F;
    private float prevDebugCamPitch = 0.0F;

    /** Smooth cam yaw */
    private float smoothCamYaw;

    /** Smooth cam pitch */
    private float smoothCamPitch;

    /** Smooth cam filter X */
    private float smoothCamFilterX;

    /** Smooth cam filter Y */
    private float smoothCamFilterY;

    /** Smooth cam partial ticks */
    private float smoothCamPartialTicks;
    private float debugCamFOV = 0.0F;
    private float prevDebugCamFOV = 0.0F;
    private float camRoll = 0.0F;
    private float prevCamRoll = 0.0F;

    /**
     * The texture id of the blocklight/skylight texture used for lighting effects
     */
    public int lightmapTexture;

    /**
     * Colors computed in updateLightmap() and loaded into the lightmap emptyTexture
     */
    private int[] lightmapColors;

    /** FOV modifier hand */
    private float fovModifierHand;

    /** FOV modifier hand prev */
    private float fovModifierHandPrev;

    /** FOV multiplier temp */
    private float fovMultiplierTemp;

    /** Cloud fog mode */
    private boolean cloudFog = false;
    private double cameraZoom = 1.0D;
    private double cameraYaw = 0.0D;
    private double cameraPitch = 0.0D;

    /** Previous frame time in milliseconds */
    private long prevFrameTime = System.currentTimeMillis();

    /** End time of last render (ns) */
    private long renderEndNanoTime = 0L;

    /**
     * Is set, updateCameraAndRender() calls updateLightmap(); set by updateTorchFlicker()
     */
    private boolean lightmapUpdateNeeded = false;

    /** Torch flicker X */
    float torchFlickerX = 0.0F;

    /** Torch flicker DX */
    float torchFlickerDX = 0.0F;

    /** Torch flicker Y */
    float torchFlickerY = 0.0F;

    /** Torch flicker DY */
    float torchFlickerDY = 0.0F;
    private Random random = new Random();

    /** Rain sound counter */
    private int rainSoundCounter = 0;

    /** Rain X coords */
    float[] rainXCoords;

    /** Rain Y coords */
    float[] rainYCoords;
    volatile int field_1394_b = 0;
    volatile int field_1393_c = 0;

    /** Fog color buffer */
    FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);

    /** red component of the fog color */
    float fogColorRed;

    /** green component of the fog color */
    float fogColorGreen;

    /** blue component of the fog color */
    float fogColorBlue;

    /** Fog color 2 */
    private float fogColor2;

    /** Fog color 1 */
    private float fogColor1;

    /**
     * Debug view direction (0=OFF, 1=Front, 2=Right, 3=Back, 4=Left, 5=TiltLeft, 6=TiltRight)
     */
    public int debugViewDirection;
	private Class mod_ThirdPersonCamera;
	private Class mod_PFLM_PlayerFormLittleMaid;

    public PFLM_EntityRendererForge(Minecraft par1Minecraft)
    {
        super(par1Minecraft);
        mc = par1Minecraft;
        lightmapTexture = par1Minecraft.renderEngine.allocateAndSetupTexture(new BufferedImage(16, 16, 1));
        lightmapColors = new int[256];
        mod_PFLM_PlayerFormLittleMaid = Modchu_Reflect.loadClass(getClassName("mod_PFLM_PlayerFormLittleMaid"));
        itemRenderer = (ItemRenderer) Modchu_Reflect.newInstance(Modchu_Reflect.loadClass(getClassName("Modchu_ItemRenderer")), new Class[]{ Minecraft.class }, new Object[]{ par1Minecraft });
        if ((Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isThirdPersonCamera")) {
        	try {
        		mod_ThirdPersonCamera = Modchu_Reflect.loadClass(getClassName("mod_ThirdPersonCamera"));
        		Modchu_Reflect.setFieldObject(mod_ThirdPersonCamera, "renderer", this);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }
    }

	public String getClassName(String s) {
		if (s == null) return null;
		if (s.indexOf(".") > -1) return s;
		String s1 = getPackage();
		if (s1 != null) return s1.concat(".").concat(s);
		return s;
	}

	public String getPackage() {
		Package pac = getClass().getPackage();
		if (pac != null) return pac.getName();
		return null;
	}

    /**
     * Updates the entity renderer
     */
    public void updateRenderer()
    {
        updateFovModifierHand();
        updateTorchFlicker();
        fogColor2 = fogColor1;
        thirdPersonDistanceTemp = thirdPersonDistance;
        prevDebugCamYaw = debugCamYaw;
        prevDebugCamPitch = debugCamPitch;
        prevDebugCamFOV = debugCamFOV;
        prevCamRoll = camRoll;
        float var1;
        float var2;

        if (mc.gameSettings.smoothCamera)
        {
            var1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            var2 = var1 * var1 * var1 * 8.0F;
            smoothCamFilterX = mouseFilterXAxis.func_22386_a(smoothCamYaw, 0.05F * var2);
            smoothCamFilterY = mouseFilterYAxis.func_22386_a(smoothCamPitch, 0.05F * var2);
            smoothCamPartialTicks = 0.0F;
            smoothCamYaw = 0.0F;
            smoothCamPitch = 0.0F;
        }

        if (mc.renderViewEntity == null)
        {
            mc.renderViewEntity = mc.thePlayer;
        }

        var1 = mc.theWorld.getLightBrightness(MathHelper.floor_double(mc.renderViewEntity.posX), MathHelper.floor_double(mc.renderViewEntity.posY), MathHelper.floor_double(mc.renderViewEntity.posZ));
        var2 = (float)(3 - mc.gameSettings.renderDistance) / 3.0F;
        float var3 = var1 * (1.0F - var2) + var2;
        fogColor1 += (var3 - fogColor1) * 0.1F;
        ++rendererUpdateCount;
        itemRenderer.updateEquippedItem();
        addRainParticles();
    }

    /**
     * Finds what block or object the mouse is over at the specified partial tick time. Args: partialTickTime
     */
    public void getMouseOver(float par1)
    {
        if (mc.renderViewEntity != null)
        {
            if (mc.theWorld != null)
            {
                double var2 = (double)mc.playerController.getBlockReachDistance();
                mc.objectMouseOver = mc.renderViewEntity.rayTrace(var2, par1);
                double var4 = var2;
                Vec3D var6 = mc.renderViewEntity.getPosition(par1);

                if (mc.playerController.extendedReach())
                {
                    var2 = 6.0D;
                    var4 = 6.0D;
                }
                else
                {
                    if (var2 > 3.0D)
                    {
                        var4 = 3.0D;
                    }

                    var2 = var4;
                }

                if (mc.objectMouseOver != null)
                {
                    var4 = mc.objectMouseOver.hitVec.distanceTo(var6);
                }

                Vec3D var7 = mc.renderViewEntity.getLook(par1);
                Vec3D var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
                pointedEntity = null;
                float var9 = 1.0F;
                List var10 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expand((double)var9, (double)var9, (double)var9));
                double var11 = var4;

                for (int var13 = 0; var13 < var10.size(); ++var13)
                {
                    Entity var14 = (Entity)var10.get(var13);

                    if (var14.canBeCollidedWith())
                    {
                        float var15 = var14.getCollisionBorderSize();
                        AxisAlignedBB var16 = var14.boundingBox.expand((double)var15, (double)var15, (double)var15);
                        MovingObjectPosition var17 = var16.calculateIntercept(var6, var8);

                        if (var16.isVecInside(var6))
                        {
                            if (0.0D < var11 || var11 == 0.0D)
                            {
                                pointedEntity = var14;
                                var11 = 0.0D;
                            }
                        }
                        else if (var17 != null)
                        {
                            double var18 = var6.distanceTo(var17.hitVec);

                            if (var18 < var11 || var11 == 0.0D)
                            {
                                pointedEntity = var14;
                                var11 = var18;
                            }
                        }
                    }
                }

                if (pointedEntity != null && (var11 < var4 || mc.objectMouseOver == null))
                {
                    mc.objectMouseOver = new MovingObjectPosition(pointedEntity);
                }
            }
        }
    }

    /**
     * Update FOV modifier hand
     */
    private void updateFovModifierHand()
    {
        if (mc.renderViewEntity instanceof EntityPlayerSP)
        {
            EntityPlayerSP var1 = (EntityPlayerSP)mc.renderViewEntity;
            fovMultiplierTemp = var1.getFOVMultiplier();
        }
        else
        {
            fovMultiplierTemp = mc.thePlayer.getFOVMultiplier();
        }
        fovModifierHandPrev = fovModifierHand;
        fovModifierHand += (fovMultiplierTemp - fovModifierHand) * 0.5F;
    }

    /**
     * Changes the field of view of the player depending on if they are underwater or not
     */
    private float getFOVModifier(float par1, boolean par2)
    {
        if (debugViewDirection > 0)
        {
            return 90.0F;
        }
        else
        {
            EntityLiving var3 = (EntityLiving)mc.renderViewEntity;
            float var4 = 70.0F;

            if (par2)
            {
                var4 += mc.gameSettings.fovSetting * 40.0F;
                var4 *= fovModifierHandPrev + (fovModifierHand - fovModifierHandPrev) * par1;
            }

            if (var3.getHealth() <= 0)
            {
                float var5 = (float)var3.deathTime + par1;
                var4 /= (1.0F - 500.0F / (var5 + 500.0F)) * 2.0F + 1.0F;
            }

            int var6 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(mc.theWorld, var3, par1);

            if (var6 != 0 && Block.blocksList[var6].blockMaterial == Material.water)
            {
                var4 = var4 * 60.0F / 70.0F;
            }

            return var4 + prevDebugCamFOV + (debugCamFOV - prevDebugCamFOV) * par1;
        }
    }

    private void hurtCameraEffect(float par1)
    {
        EntityLiving var2 = mc.renderViewEntity;
        float var3 = (float)var2.hurtTime - par1;
        float var4;

        if (var2.getHealth() <= 0)
        {
            var4 = (float)var2.deathTime + par1;
            GL11.glRotatef(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
        }

        if (var3 >= 0.0F)
        {
            var3 /= (float)var2.maxHurtTime;
            var3 = MathHelper.sin(var3 * var3 * var3 * var3 * (float)Math.PI);
            var4 = var2.attackedAtYaw;
            GL11.glRotatef(-var4, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(var4, 0.0F, 1.0F, 0.0F);
        }
    }

    /**
     * Setups all the GL settings for view bobbing. Args: partialTickTime
     */
    private void setupViewBobbing(float par1)
    {
        if (mc.renderViewEntity instanceof EntityPlayer)
        {
            EntityPlayer var2 = (EntityPlayer)mc.renderViewEntity;
            float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
            float var4 = -(var2.distanceWalkedModified + var3 * par1);
            float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * par1;
            float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * par1;
            GL11.glTranslatef(MathHelper.sin(var4 * (float)Math.PI) * var5 * 0.5F, -Math.abs(MathHelper.cos(var4 * (float)Math.PI) * var5), 0.0F);
            GL11.glRotatef(MathHelper.sin(var4 * (float)Math.PI) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(Math.abs(MathHelper.cos(var4 * (float)Math.PI - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var6, 1.0F, 0.0F, 0.0F);
        }
    }

    /**
     * sets up player's eye (or camera in third person mode)
     */
    private void orientCamera(float par1)
    {
    	EntityLiving entityliving = mc.renderViewEntity;
    	float yOffset = entityliving.yOffset;
    	float k1 = yOffset;
    	boolean r1 = false;
    	float t = 0.0F;
    	if((Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isPlayerForm")
    			&& !(Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isMulti")){
    		if(!entityliving.isRiding()
    				&& !entityliving.isPlayerSleeping()) k1 = (Float) Modchu_Reflect.invokeMethod(mod_PFLM_PlayerFormLittleMaid, "getyOffset");
    		if(entityliving.isRiding()) t = (Float) Modchu_Reflect.invokeMethod(mod_PFLM_PlayerFormLittleMaid, "ridingViewCorrection");
    		k1 += t;
    	}
    	float f3 = yOffset - k1;
        double d = entityliving.prevPosX + (entityliving.posX - entityliving.prevPosX) * (double)par1;
        double d1 = (entityliving.prevPosY + (entityliving.posY - entityliving.prevPosY) * (double)par1) - (double)f3;
        double d2 = entityliving.prevPosZ + (entityliving.posZ - entityliving.prevPosZ) * (double)par1;
    	if ((Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isThirdPersonCamera")) {
    		try {
    			camRoll = (Float) Modchu_Reflect.invokeMethod(mod_PFLM_PlayerFormLittleMaid, "getPrivateValue", new Class[]{ Class.class, Object.class, int.class}, this, new Object[]{ EntityRenderer.class, this, 26 });
    		} catch (Exception e) {
    			try {
    				camRoll = (Float) Modchu_Reflect.invokeMethod(mod_PFLM_PlayerFormLittleMaid, "getPrivateValue", new Class[]{ Class.class, Object.class, String.class}, this, new Object[]{ EntityRenderer.class, this, "camRoll" });
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
    		}
    	}
        GL11.glRotatef(prevCamRoll + (camRoll - prevCamRoll) * par1, 0.0F, 0.0F, 1.0F);

        if (entityliving.isPlayerSleeping())
        {
        	entityliving.setSize(1.8F, 0.6F);
            f3 = ((float)((double)f3 + 1.0D) - 1.62F);
            GL11.glTranslatef(0.0F, 0.3F, 0.0F);

            if (!mc.gameSettings.debugCamEnable)
            {
                int i = mc.theWorld.getBlockId(MathHelper.floor_double(entityliving.posX), MathHelper.floor_double(entityliving.posY), MathHelper.floor_double(entityliving.posZ));

                if (i == Block.bed.blockID)
                {
                    int j = mc.theWorld.getBlockMetadata(MathHelper.floor_double(entityliving.posX), MathHelper.floor_double(entityliving.posY), MathHelper.floor_double(entityliving.posZ));
                    int k = j & 3;
                    GL11.glRotatef((float)(k * 90), 0.0F, 1.0F, 0.0F);
                }

                GL11.glRotatef(entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * par1 + 180F, 0.0F, -1F, 0.0F);
                GL11.glRotatef(entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * par1, -1F, 0.0F, 0.0F);
            }
        }
        else if (mc.gameSettings.thirdPersonView > 0)
        {
        	if ((Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isThirdPersonCamera")) {
        		try {
        			thirdPersonDistance = (Float) Modchu_Reflect.invokeMethod(mod_PFLM_PlayerFormLittleMaid, "getPrivateValue", new Class[]{ Class.class, Object.class, int.class}, this, new Object[]{ EntityRenderer.class, this, 13 });
        		} catch (Exception e) {
        			try {
        				thirdPersonDistance = (Float) Modchu_Reflect.invokeMethod(mod_PFLM_PlayerFormLittleMaid, "getPrivateValue", new Class[]{ Class.class, Object.class, String.class}, this, new Object[]{ EntityRenderer.class, this, "thirdPersonDistance" });
        			} catch (Exception e1) {
        				e1.printStackTrace();
        			}
        		}
        	}
            double d3 = (double)(thirdPersonDistanceTemp + (thirdPersonDistance - thirdPersonDistanceTemp) * par1);

            if (mc.gameSettings.debugCamEnable)
            {
                float f4 = prevDebugCamYaw + (debugCamYaw - prevDebugCamYaw) * par1;
                float f6 = prevDebugCamPitch + (debugCamPitch - prevDebugCamPitch) * par1;
                GL11.glTranslatef(0.0F, 0.0F, (float)(-d3));
                GL11.glRotatef(f6, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(f4, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                float f5 = entityliving.rotationYaw;
                float f7 = entityliving.rotationPitch;

                if (mc.gameSettings.thirdPersonView == 2)
                {
                    f7 += 180F;
                }

                double d4 = (double)(-MathHelper.sin((f5 / 180F) * (float)Math.PI) * MathHelper.cos((f7 / 180F) * (float)Math.PI)) * d3;
                double d5 = (double)(MathHelper.cos((f5 / 180F) * (float)Math.PI) * MathHelper.cos((f7 / 180F) * (float)Math.PI)) * d3;
                double d6 = (double)(-MathHelper.sin((f7 / 180F) * (float)Math.PI)) * d3;
                float f8 = 0F;
                float f9 = 0F;
                for (int l = 0; l < 8; l++)
                {
                    f8 = (float)((l & 1) * 2 - 1);
                    f9 = (float)((l >> 1 & 1) * 2 - 1);
                    float f10 = (float)((l >> 2 & 1) * 2 - 1);
                    f8 *= 0.1F;
                    f9 *= 0.1F;
                    f10 *= 0.1F;
                    MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(Vec3D.createVectorHelper(d + (double)f8, d1 + (double)f9, d2 + (double)f10), Vec3D.createVectorHelper((d - d4) + (double)f8 + (double)f10, (d1 - d6) + (double)f9, (d2 - d5) + (double)f10));

                    if (movingobjectposition != null)
                    {
                        double d7 = movingobjectposition.hitVec.distanceTo(Vec3D.createVectorHelper(d, d1, d2));

                        if (d7 < d3)
                        {
                            d3 = d7;
                        }
                    }
                }

                if (mc.gameSettings.thirdPersonView == 2)
                {
                    GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                }

                float offsetX = 0.0F;
                float offsetY = 0.0F;
                float offsetZ = 0.0F;
                float f1 = 0.0F;
                if ((Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isThirdPersonCamera")) {
                	Field f11;
                	try {
                		f11 = mod_ThirdPersonCamera.getField("offsetX");
                		offsetX = (Float) f11.get(null);
                		f11 = mod_ThirdPersonCamera.getField("offsetY");
                		offsetY = (Float) f11.get(null);
                		f11 = mod_ThirdPersonCamera.getField("offsetZ");
                		offsetZ = (Float) f11.get(null);
                		f11 = mod_ThirdPersonCamera.getField("yaw");
                		f1 = (Float) f11.get(null);
                		f11 = mod_ThirdPersonCamera.getField("pitch");
                		f8 = (Float) f11.get(null);
                	} catch (Exception e1) {
                		e1.printStackTrace();
                	}
                	if (this.mc.gameSettings.thirdPersonView == 0)
                	{
                		f8 = 0.0F;
                		f9 = 0.0F;
                		f1 = 0.0F;
                	}
                }

                GL11.glRotatef(entityliving.rotationPitch + f8 - f7, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(entityliving.rotationYaw + f1 - f5, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, (float)(-d3));
                if ((Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isThirdPersonCamera")) {
                    GL11.glTranslatef(offsetX, 0.0F, 0.0F);
                    GL11.glTranslatef(0.0F, offsetY, 0.0F);
                    GL11.glTranslatef(0.0F, 0.0F, offsetZ);
                }
                GL11.glRotatef(f5 - entityliving.rotationYaw + f1, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f7 - entityliving.rotationPitch + f8, 1.0F, 0.0F, 0.0F);
            }
        }
        else
        {
            GL11.glTranslatef(0.0F, 0.0F, -0.1F);
        }
        if (!mc.gameSettings.debugCamEnable)
        {
            GL11.glRotatef(entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * par1, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * par1 + 180F, 0.0F, 1.0F, 0.0F);
        }

        GL11.glTranslatef(0.0F, f3, 0.0F);
        d = entityliving.prevPosX + (entityliving.posX - entityliving.prevPosX) * (double)par1;
        d1 = (entityliving.prevPosY + (entityliving.posY - entityliving.prevPosY) * (double)par1) - (double)f3;
        d2 = entityliving.prevPosZ + (entityliving.posZ - entityliving.prevPosZ) * (double)par1;
        cloudFog = mc.renderGlobal.func_27307_a(d, d1, d2, par1);
    }

    /**
     * sets up projection, view effects, camera position/rotation
     */
    private void setupCameraTransform(float par1, int par2)
    {
        farPlaneDistance = (float)(256 >> mc.gameSettings.renderDistance);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float var3 = 0.07F;

        if (mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(-(par2 * 2 - 1)) * var3, 0.0F, 0.0F);
        }

        if (cameraZoom != 1.0D)
        {
            GL11.glTranslatef((float)cameraYaw, (float)(-cameraPitch), 0.0F);
            GL11.glScaled(cameraZoom, cameraZoom, 1.0D);
            GLU.gluPerspective(getFOVModifier(par1, true), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
        }
        else
        {
            GLU.gluPerspective(getFOVModifier(par1, true), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
        }

        float var4;

        if (mc.playerController.func_35643_e())
        {
            var4 = 0.6666667F;
            GL11.glScalef(1.0F, var4, 1.0F);
        }

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        if (mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(par2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
        }

        hurtCameraEffect(par1);

        if (mc.gameSettings.viewBobbing)
        {
            setupViewBobbing(par1);
        }

        var4 = mc.thePlayer.prevTimeInPortal + (mc.thePlayer.timeInPortal - mc.thePlayer.prevTimeInPortal) * par1;

        if (var4 > 0.0F)
        {
            byte var5 = 20;

            if (mc.thePlayer.isPotionActive(Potion.confusion))
            {
                var5 = 7;
            }

            float var6 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
            var6 *= var6;
            GL11.glRotatef(((float)rendererUpdateCount + par1) * (float)var5, 0.0F, 1.0F, 1.0F);
            GL11.glScalef(1.0F / var6, 1.0F, 1.0F);
            GL11.glRotatef(-((float)rendererUpdateCount + par1) * (float)var5, 0.0F, 1.0F, 1.0F);
        }

        orientCamera(par1);

        if (debugViewDirection > 0)
        {
            int var7 = debugViewDirection - 1;

            if (var7 == 1)
            {
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var7 == 2)
            {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var7 == 3)
            {
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var7 == 4)
            {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var7 == 5)
            {
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }
        }
    }

    /**
     * Render player hand
     */
    private void renderHand(float par1, int par2)
    {
        if (debugViewDirection <= 0)
        {
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            float var3 = 0.07F;

            if (mc.gameSettings.anaglyph)
            {
                GL11.glTranslatef((float)(-(par2 * 2 - 1)) * var3, 0.0F, 0.0F);
            }

            if (cameraZoom != 1.0D)
            {
                GL11.glTranslatef((float)cameraYaw, (float)(-cameraPitch), 0.0F);
                GL11.glScaled(cameraZoom, cameraZoom, 1.0D);
                GLU.gluPerspective(getFOVModifier(par1, false), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
            }
            else
            {
                GLU.gluPerspective(getFOVModifier(par1, false), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
            }

            if (mc.playerController.func_35643_e())
            {
                float var4 = 0.6666667F;
                GL11.glScalef(1.0F, var4, 1.0F);
            }

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();

            if (mc.gameSettings.anaglyph)
            {
                GL11.glTranslatef((float)(par2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
            }

            GL11.glPushMatrix();
            hurtCameraEffect(par1);

            if (mc.gameSettings.viewBobbing)
            {
                setupViewBobbing(par1);
            }

            if (mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping() && !mc.gameSettings.hideGUI && !mc.playerController.func_35643_e())
            {
                enableLightmap((double)par1);
                itemRenderer.renderItemInFirstPerson(par1);
                disableLightmap((double)par1);
            }

            GL11.glPopMatrix();

            if (mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping())
            {
                itemRenderer.renderOverlays(par1);
                hurtCameraEffect(par1);
            }

            if (mc.gameSettings.viewBobbing)
            {
                setupViewBobbing(par1);
            }
        }
    }

    /**
     * Disable secondary texture unit used by lightmap
     */
    public void disableLightmap(double par1)
    {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    /**
     * Enable lightmap in secondary texture unit
     */
    public void enableLightmap(double par1)
    {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        float var3 = 0.00390625F;
        GL11.glScalef(var3, var3, var3);
        GL11.glTranslatef(8.0F, 8.0F, 8.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        mc.renderEngine.bindTexture(lightmapTexture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    /**
     * Recompute a random value that is applied to block color in updateLightmap()
     */
    private void updateTorchFlicker()
    {
        torchFlickerDX = (float)((double)torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
        torchFlickerDY = (float)((double)torchFlickerDY + (Math.random() - Math.random()) * Math.random() * Math.random());
        torchFlickerDX = (float)((double)torchFlickerDX * 0.9D);
        torchFlickerDY = (float)((double)torchFlickerDY * 0.9D);
        torchFlickerX += (torchFlickerDX - torchFlickerX) * 1.0F;
        torchFlickerY += (torchFlickerDY - torchFlickerY) * 1.0F;
        lightmapUpdateNeeded = true;
    }

    private void updateLightmap()
    {
        World var1 = mc.theWorld;

        if (var1 != null)
        {
            for (int var2 = 0; var2 < 256; ++var2)
            {
                float var3 = var1.func_35464_b(1.0F) * 0.95F + 0.05F;
                float var4 = var1.worldProvider.lightBrightnessTable[var2 / 16] * var3;
                float var5 = var1.worldProvider.lightBrightnessTable[var2 % 16] * (torchFlickerX * 0.1F + 1.5F);

                if (var1.lightningFlash > 0)
                {
                    var4 = var1.worldProvider.lightBrightnessTable[var2 / 16];
                }

                float var6 = var4 * (var1.func_35464_b(1.0F) * 0.65F + 0.35F);
                float var7 = var4 * (var1.func_35464_b(1.0F) * 0.65F + 0.35F);
                float var10 = var5 * ((var5 * 0.6F + 0.4F) * 0.6F + 0.4F);
                float var11 = var5 * (var5 * var5 * 0.6F + 0.4F);
                float var12 = var6 + var5;
                float var13 = var7 + var10;
                float var14 = var4 + var11;
                var12 = var12 * 0.96F + 0.03F;
                var13 = var13 * 0.96F + 0.03F;
                var14 = var14 * 0.96F + 0.03F;

                if (var1.worldProvider.worldType == 1)
                {
                    var12 = 0.22F + var5 * 0.75F;
                    var13 = 0.28F + var10 * 0.75F;
                    var14 = 0.25F + var11 * 0.75F;
                }

                float var15 = mc.gameSettings.gammaSetting;

                if (var12 > 1.0F)
                {
                    var12 = 1.0F;
                }

                if (var13 > 1.0F)
                {
                    var13 = 1.0F;
                }

                if (var14 > 1.0F)
                {
                    var14 = 1.0F;
                }

                float var16 = 1.0F - var12;
                float var17 = 1.0F - var13;
                float var18 = 1.0F - var14;
                var16 = 1.0F - var16 * var16 * var16 * var16;
                var17 = 1.0F - var17 * var17 * var17 * var17;
                var18 = 1.0F - var18 * var18 * var18 * var18;
                var12 = var12 * (1.0F - var15) + var16 * var15;
                var13 = var13 * (1.0F - var15) + var17 * var15;
                var14 = var14 * (1.0F - var15) + var18 * var15;
                var12 = var12 * 0.96F + 0.03F;
                var13 = var13 * 0.96F + 0.03F;
                var14 = var14 * 0.96F + 0.03F;

                if (var12 > 1.0F)
                {
                    var12 = 1.0F;
                }

                if (var13 > 1.0F)
                {
                    var13 = 1.0F;
                }

                if (var14 > 1.0F)
                {
                    var14 = 1.0F;
                }

                if (var12 < 0.0F)
                {
                    var12 = 0.0F;
                }

                if (var13 < 0.0F)
                {
                    var13 = 0.0F;
                }

                if (var14 < 0.0F)
                {
                    var14 = 0.0F;
                }

                short var19 = 255;
                int var20 = (int)(var12 * 255.0F);
                int var21 = (int)(var13 * 255.0F);
                int var22 = (int)(var14 * 255.0F);
                lightmapColors[var2] = var19 << 24 | var20 << 16 | var21 << 8 | var22;
            }

            mc.renderEngine.createTextureFromBytes(lightmapColors, 16, 16, lightmapTexture);
        }
    }

    /**
     * Will update any inputs that effect the camera angle (mouse) and then render the world and GUI
     */
    public void updateCameraAndRender(float par1)
    {
        if ((Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isDynamicLights")) {
        	Modchu_Reflect.invokeMethod("DynamicLights", "OnTickInGame");
        	//DynamicLights.OnTickInGame();
        }
        Profiler.startSection("lightTex");

        if (lightmapUpdateNeeded)
        {
            updateLightmap();
        }

        Profiler.endSection();

        if (!Display.isActive()
        		&& (Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isMouseOverMinecraftMenu"))
        {
            if (System.currentTimeMillis() - prevFrameTime > 500L)
            {
                mc.displayInGameMenu();
            }
        }
        else
        {
            prevFrameTime = System.currentTimeMillis();
        }

        Profiler.startSection("mouse");

        if (mc.inGameHasFocus)
        {
            mc.mouseHelper.mouseXYChange();
            float var2 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float var3 = var2 * var2 * var2 * 8.0F;
            float var4 = (float)mc.mouseHelper.deltaX * var3;
            float var5 = (float)mc.mouseHelper.deltaY * var3;
            byte var6 = 1;

            if (mc.gameSettings.invertMouse)
            {
                var6 = -1;
            }

            if (mc.gameSettings.smoothCamera)
            {
                smoothCamYaw += var4;
                smoothCamPitch += var5;
                float var7 = par1 - smoothCamPartialTicks;
                smoothCamPartialTicks = par1;
                var4 = smoothCamFilterX * var7;
                var5 = smoothCamFilterY * var7;
                mc.thePlayer.setAngles(var4, var5 * (float)var6);
            }
            else
            {
                mc.thePlayer.setAngles(var4, var5 * (float)var6);
            }
        }

        Profiler.endSection();

        if (!mc.skipRenderWorld)
        {
            anaglyphEnable = mc.gameSettings.anaglyph;
            ScaledResolution var13 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
            int var14 = var13.getScaledWidth();
            int var15 = var13.getScaledHeight();
            int var16 = Mouse.getX() * var14 / mc.displayWidth;
            int var17 = var15 - Mouse.getY() * var15 / mc.displayHeight - 1;
            short var18 = 200;

            if (mc.gameSettings.limitFramerate == 1)
            {
                var18 = 120;
            }

            if (mc.gameSettings.limitFramerate == 2)
            {
                var18 = 40;
            }

            long var8;

            if (mc.theWorld != null)
            {
                Profiler.startSection("level");

                if (mc.gameSettings.limitFramerate == 0)
                {
                    renderWorld(par1, 0L);
                }
                else
                {
                    renderWorld(par1, renderEndNanoTime + (long)(1000000000 / var18));
                }

                Profiler.endStartSection("sleep");

                if (mc.gameSettings.limitFramerate == 2)
                {
                    var8 = (renderEndNanoTime + (long)(1000000000 / var18) - System.nanoTime()) / 1000000L;

                    if (var8 > 0L && var8 < 500L)
                    {
                        try
                        {
                            Thread.sleep(var8);
                        }
                        catch (InterruptedException var12)
                        {
                            var12.printStackTrace();
                        }
                    }
                }

                renderEndNanoTime = System.nanoTime();
                Profiler.endStartSection("gui");

                if (!mc.gameSettings.hideGUI || mc.currentScreen != null)
                {
                    mc.ingameGUI.renderGameOverlay(par1, mc.currentScreen != null, var16, var17);
                }

                Profiler.endSection();
            }
            else
            {
                GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
                GL11.glMatrixMode(GL11.GL_PROJECTION);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glLoadIdentity();
                setupOverlayRendering();
                var8 = (renderEndNanoTime + (long)(1000000000 / var18) - System.nanoTime()) / 1000000L;

                if (var8 < 0L)
                {
                    var8 += 10L;
                }

                if (var8 > 0L && var8 < 500L)
                {
                    try
                    {
                        Thread.sleep(var8);
                    }
                    catch (InterruptedException var11)
                    {
                        var11.printStackTrace();
                    }
                }

                renderEndNanoTime = System.nanoTime();
            }

            if (mc.currentScreen != null)
            {
                GL11.glClear(256);
                mc.currentScreen.drawScreen(var16, var17, par1);

                if (mc.currentScreen != null && mc.currentScreen.guiParticles != null)
                {
                    mc.currentScreen.guiParticles.draw(par1);
                }
            }
        }
        ModLoader.onTick(par1, mc);
    }

    public void renderWorld(float par1, long par2)
    {
        Profiler.startSection("lightTex");

        if (lightmapUpdateNeeded)
        {
            updateLightmap();
        }

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        if (mc.renderViewEntity == null)
        {
            mc.renderViewEntity = mc.thePlayer;
        }

        Profiler.endStartSection("pick");
        getMouseOver(par1);
        EntityLiving var4 = mc.renderViewEntity;
        RenderGlobal var5 = mc.renderGlobal;
        EffectRenderer var6 = mc.effectRenderer;
        double var7 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)par1;
        double var9 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)par1;
        double var11 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)par1;
        Profiler.endStartSection("center");
        IChunkProvider var13 = mc.theWorld.getChunkProvider();
        int var16;

        if (var13 instanceof ChunkProviderLoadOrGenerate)
        {
            ChunkProviderLoadOrGenerate var14 = (ChunkProviderLoadOrGenerate)var13;
            int var15 = MathHelper.floor_float((float)((int)var7)) >> 4;
            var16 = MathHelper.floor_float((float)((int)var11)) >> 4;
            var14.setCurrentChunkOver(var15, var16);
        }

        for (int var19 = 0; var19 < 2; ++var19)
        {
            if (mc.gameSettings.anaglyph)
            {
                anaglyphField = var19;

                if (anaglyphField == 0)
                {
                    GL11.glColorMask(false, true, true, false);
                }
                else
                {
                    GL11.glColorMask(true, false, false, false);
                }
            }

            Profiler.endStartSection("clear");
            GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
            updateFogColor(par1);
            GL11.glClear(16640);
            GL11.glEnable(GL11.GL_CULL_FACE);
            Profiler.endStartSection("camera");
            setupCameraTransform(par1, var19);
            ActiveRenderInfo.updateRenderInfo(mc.thePlayer, mc.gameSettings.thirdPersonView == 2);
            Profiler.endStartSection("frustrum");
            ClippingHelperImpl.getInstance();

            if (mc.gameSettings.renderDistance < 2)
            {
                setupFog(-1, par1);
                Profiler.endStartSection("sky");
                var5.renderSky(par1);
            }

            GL11.glEnable(GL11.GL_FOG);
            setupFog(1, par1);

            if (mc.gameSettings.ambientOcclusion)
            {
                GL11.glShadeModel(GL11.GL_SMOOTH);
            }

            Profiler.endStartSection("culling");
            Frustrum var18 = new Frustrum();
            var18.setPosition(var7, var9, var11);
            mc.renderGlobal.clipRenderersByFrustum(var18, par1);

            if (var19 == 0)
            {
                Profiler.endStartSection("updatechunks");

                while (!mc.renderGlobal.updateRenderers(var4, false) && par2 != 0L)
                {
                    long var21 = par2 - System.nanoTime();

                    if (var21 < 0L || var21 > 1000000000L)
                    {
                        break;
                    }
                }
            }

            setupFog(0, par1);
            GL11.glEnable(GL11.GL_FOG);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/terrain.png"));
            RenderHelper.disableStandardItemLighting();
            Profiler.endStartSection("terrain");
            var5.sortAndRender(var4, 0, (double)par1);
            GL11.glShadeModel(GL11.GL_FLAT);
            EntityPlayer var20;

            if (debugViewDirection == 0)
            {
                RenderHelper.enableStandardItemLighting();
                Profiler.endStartSection("entities");
                var5.renderEntities(var4.getPosition(par1), var18, par1);
                enableLightmap((double)par1);
                Profiler.endStartSection("litParticles");
                var6.func_1187_b(var4, par1);
                RenderHelper.disableStandardItemLighting();
                setupFog(0, par1);
                Profiler.endStartSection("particles");
                var6.renderParticles(var4, par1);
                disableLightmap((double)par1);

                if (mc.objectMouseOver != null && var4.isInsideOfMaterial(Material.water) && var4 instanceof EntityPlayer && !mc.gameSettings.hideGUI)
                {
                    var20 = (EntityPlayer)var4;
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    Profiler.endStartSection("outline");
                    if (!ForgeHooksClient.onBlockHighlight(var5, var20, mc.objectMouseOver, 0, var20.inventory.getCurrentItem(), par1))
                    {
                        var5.drawBlockBreaking(var20, mc.objectMouseOver, 0, var20.inventory.getCurrentItem(), par1);
                        var5.drawSelectionBox(var20, mc.objectMouseOver, 0, var20.inventory.getCurrentItem(), par1);
                    }
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                }
            }

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDepthMask(true);
            setupFog(0, par1);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/terrain.png"));

            if (mc.gameSettings.fancyGraphics)
            {
                Profiler.endStartSection("water");

                if (mc.gameSettings.ambientOcclusion)
                {
                    GL11.glShadeModel(GL11.GL_SMOOTH);
                }

                GL11.glColorMask(false, false, false, false);
                var16 = var5.sortAndRender(var4, 1, (double)par1);

                if (mc.gameSettings.anaglyph)
                {
                    if (anaglyphField == 0)
                    {
                        GL11.glColorMask(false, true, true, true);
                    }
                    else
                    {
                        GL11.glColorMask(true, false, false, true);
                    }
                }
                else
                {
                    GL11.glColorMask(true, true, true, true);
                }

                if (var16 > 0)
                {
                    var5.renderAllRenderLists(1, (double)par1);
                }

                GL11.glShadeModel(GL11.GL_FLAT);
            }
            else
            {
                Profiler.endStartSection("water");
                var5.sortAndRender(var4, 1, (double)par1);
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);

            if (cameraZoom == 1.0D && var4 instanceof EntityPlayer && !mc.gameSettings.hideGUI && mc.objectMouseOver != null && !var4.isInsideOfMaterial(Material.water))
            {
                var20 = (EntityPlayer)var4;
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                Profiler.endStartSection("outline");

                if (!ForgeHooksClient.onBlockHighlight(var5, var20, mc.objectMouseOver, 0, var20.inventory.getCurrentItem(), par1))
                {
                    var5.drawBlockBreaking(var20, mc.objectMouseOver, 0, var20.inventory.getCurrentItem(), par1);
                    var5.drawSelectionBox(var20, mc.objectMouseOver, 0, var20.inventory.getCurrentItem(), par1);
                }
                GL11.glEnable(GL11.GL_ALPHA_TEST);
            }

            Profiler.endStartSection("weather");
            renderRainSnow(par1);
            GL11.glDisable(GL11.GL_FOG);

            if (pointedEntity != null)
            {
                ;
            }

            if (mc.gameSettings.shouldRenderClouds())
            {
                Profiler.endStartSection("clouds");
                GL11.glPushMatrix();
                setupFog(0, par1);
                GL11.glEnable(GL11.GL_FOG);
                var5.renderClouds(par1);
                GL11.glDisable(GL11.GL_FOG);
                setupFog(1, par1);
                GL11.glPopMatrix();
            }

            Profiler.endStartSection("fhooks");
            ForgeHooksClient.onRenderWorldLast(var5, par1);

            Profiler.endStartSection("hand");

            if (cameraZoom == 1.0D)
            {
                GL11.glClear(256);
                renderHand(par1, var19);
            }

            if (!mc.gameSettings.anaglyph)
            {
                Profiler.endSection();
                return;
            }
        }

        GL11.glColorMask(true, true, true, false);
        Profiler.endSection();
    }

    private void addRainParticles()
    {
        float var1 = mc.theWorld.getRainStrength(1.0F);

        if (!mc.gameSettings.fancyGraphics)
        {
            var1 /= 2.0F;
        }

        if (var1 != 0.0F)
        {
            random.setSeed((long)rendererUpdateCount * 312987231L);
            EntityLiving var2 = mc.renderViewEntity;
            World var3 = mc.theWorld;
            int var4 = MathHelper.floor_double(var2.posX);
            int var5 = MathHelper.floor_double(var2.posY);
            int var6 = MathHelper.floor_double(var2.posZ);
            byte var7 = 10;
            double var8 = 0.0D;
            double var10 = 0.0D;
            double var12 = 0.0D;
            int var14 = 0;
            int var15 = (int)(100.0F * var1 * var1);

            if (mc.gameSettings.particleSetting == 1)
            {
                var15 >>= 1;
            }
            else if (mc.gameSettings.particleSetting == 2)
            {
                var15 = 0;
            }

            for (int var16 = 0; var16 < var15; ++var16)
            {
                int var17 = var4 + random.nextInt(var7) - random.nextInt(var7);
                int var18 = var6 + random.nextInt(var7) - random.nextInt(var7);
                int var19 = var3.getPrecipitationHeight(var17, var18);
                int var20 = var3.getBlockId(var17, var19 - 1, var18);
                BiomeGenBase var21 = var3.getBiomeGenForCoords(var17, var18);

                if (var19 <= var5 + var7 && var19 >= var5 - var7 && var21.canSpawnLightningBolt() && var21.getFloatTemperature() > 0.2F)
                {
                    float var22 = random.nextFloat();
                    float var23 = random.nextFloat();

                    if (var20 > 0)
                    {
                        if (Block.blocksList[var20].blockMaterial == Material.lava)
                        {
                            mc.effectRenderer.addEffect(new EntitySmokeFX(var3, (double)((float)var17 + var22), (double)((float)var19 + 0.1F) - Block.blocksList[var20].minY, (double)((float)var18 + var23), 0.0D, 0.0D, 0.0D));
                        }
                        else
                        {
                            ++var14;

                            if (random.nextInt(var14) == 0)
                            {
                                var8 = (double)((float)var17 + var22);
                                var10 = (double)((float)var19 + 0.1F) - Block.blocksList[var20].minY;
                                var12 = (double)((float)var18 + var23);
                            }

                            mc.effectRenderer.addEffect(new EntityRainFX(var3, (double)((float)var17 + var22), (double)((float)var19 + 0.1F) - Block.blocksList[var20].minY, (double)((float)var18 + var23)));
                        }
                    }
                }
            }

            if (var14 > 0 && random.nextInt(3) < rainSoundCounter++)
            {
                rainSoundCounter = 0;

                if (var10 > var2.posY + 1.0D && var3.getPrecipitationHeight(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posZ)) > MathHelper.floor_double(var2.posY))
                {
                    mc.theWorld.playSoundEffect(var8, var10, var12, "ambient.weather.rain", 0.1F, 0.5F);
                }
                else
                {
                    mc.theWorld.playSoundEffect(var8, var10, var12, "ambient.weather.rain", 0.2F, 1.0F);
                }
            }
        }
    }

    /**
     * Render rain and snow
     */
    protected void renderRainSnow(float par1)
    {
        float var2 = mc.theWorld.getRainStrength(par1);

        if (var2 > 0.0F)
        {
            enableLightmap((double)par1);

            if (rainXCoords == null)
            {
                rainXCoords = new float[1024];
                rainYCoords = new float[1024];

                for (int var3 = 0; var3 < 32; ++var3)
                {
                    for (int var4 = 0; var4 < 32; ++var4)
                    {
                        float var5 = (float)(var4 - 16);
                        float var6 = (float)(var3 - 16);
                        float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
                        rainXCoords[var3 << 5 | var4] = -var6 / var7;
                        rainYCoords[var3 << 5 | var4] = var5 / var7;
                    }
                }
            }

            EntityLiving var41 = mc.renderViewEntity;
            World var42 = mc.theWorld;
            int var43 = MathHelper.floor_double(var41.posX);
            int var44 = MathHelper.floor_double(var41.posY);
            int var45 = MathHelper.floor_double(var41.posZ);
            Tessellator var8 = Tessellator.instance;
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/environment/snow.png"));
            double var9 = var41.lastTickPosX + (var41.posX - var41.lastTickPosX) * (double)par1;
            double var11 = var41.lastTickPosY + (var41.posY - var41.lastTickPosY) * (double)par1;
            double var13 = var41.lastTickPosZ + (var41.posZ - var41.lastTickPosZ) * (double)par1;
            int var15 = MathHelper.floor_double(var11);
            byte var16 = 5;

            if (mc.gameSettings.fancyGraphics)
            {
                var16 = 10;
            }

            boolean var17 = false;
            byte var18 = -1;
            float var19 = (float)rendererUpdateCount + par1;

            if (mc.gameSettings.fancyGraphics)
            {
                var16 = 10;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            var17 = false;

            for (int var20 = var45 - var16; var20 <= var45 + var16; ++var20)
            {
                for (int var21 = var43 - var16; var21 <= var43 + var16; ++var21)
                {
                    int var22 = (var20 - var45 + 16) * 32 + var21 - var43 + 16;
                    float var23 = rainXCoords[var22] * 0.5F;
                    float var24 = rainYCoords[var22] * 0.5F;
                    BiomeGenBase var25 = var42.getBiomeGenForCoords(var21, var20);

                    if (var25.canSpawnLightningBolt() || var25.getEnableSnow())
                    {
                        int var26 = var42.getPrecipitationHeight(var21, var20);
                        int var27 = var44 - var16;
                        int var28 = var44 + var16;

                        if (var27 < var26)
                        {
                            var27 = var26;
                        }

                        if (var28 < var26)
                        {
                            var28 = var26;
                        }

                        float var29 = 1.0F;
                        int var30 = var26;

                        if (var26 < var15)
                        {
                            var30 = var15;
                        }

                        if (var27 != var28)
                        {
                            random.setSeed((long)(var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761));
                            float var31 = var25.getFloatTemperature();
                            double var35;
                            float var32;

                            if (var42.getWorldChunkManager().getTemperatureAtHeight(var31, var26) >= 0.15F)
                            {
                                if (var18 != 0)
                                {
                                    if (var18 >= 0)
                                    {
                                        var8.draw();
                                    }

                                    var18 = 0;
                                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/environment/rain.png"));
                                    var8.startDrawingQuads();
                                }

                                var32 = ((float)(rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971 + var20 * var20 * 418711 + var20 * 13761 & 31) + par1) / 32.0F * (3.0F + random.nextFloat());
                                double var33 = (double)((float)var21 + 0.5F) - var41.posX;
                                var35 = (double)((float)var20 + 0.5F) - var41.posZ;
                                float var37 = MathHelper.sqrt_double(var33 * var33 + var35 * var35) / (float)var16;
                                float var38 = 1.0F;
                                var8.setBrightness(var42.getLightBrightnessForSkyBlocks(var21, var30, var20, 0));
                                var8.setColorRGBA_F(var38, var38, var38, ((1.0F - var37 * var37) * 0.5F + 0.5F) * var2);
                                var8.setTranslation(-var9 * 1.0D, -var11 * 1.0D, -var13 * 1.0D);
                                var8.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var27, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var29), (double)((float)var27 * var29 / 4.0F + var32 * var29));
                                var8.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var27, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var29), (double)((float)var27 * var29 / 4.0F + var32 * var29));
                                var8.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var28, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var29), (double)((float)var28 * var29 / 4.0F + var32 * var29));
                                var8.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var28, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var29), (double)((float)var28 * var29 / 4.0F + var32 * var29));
                                var8.setTranslation(0.0D, 0.0D, 0.0D);
                            }
                            else
                            {
                                if (var18 != 1)
                                {
                                    if (var18 >= 0)
                                    {
                                        var8.draw();
                                    }

                                    var18 = 1;
                                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/environment/snow.png"));
                                    var8.startDrawingQuads();
                                }

                                var32 = ((float)(rendererUpdateCount & 511) + par1) / 512.0F;
                                float var46 = random.nextFloat() + var19 * 0.01F * (float)random.nextGaussian();
                                float var34 = random.nextFloat() + var19 * (float)random.nextGaussian() * 0.001F;
                                var35 = (double)((float)var21 + 0.5F) - var41.posX;
                                double var47 = (double)((float)var20 + 0.5F) - var41.posZ;
                                float var39 = MathHelper.sqrt_double(var35 * var35 + var47 * var47) / (float)var16;
                                float var40 = 1.0F;
                                var8.setBrightness((var42.getLightBrightnessForSkyBlocks(var21, var30, var20, 0) * 3 + 15728880) / 4);
                                var8.setColorRGBA_F(var40, var40, var40, ((1.0F - var39 * var39) * 0.3F + 0.5F) * var2);
                                var8.setTranslation(-var9 * 1.0D, -var11 * 1.0D, -var13 * 1.0D);
                                var8.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var27, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var29 + var46), (double)((float)var27 * var29 / 4.0F + var32 * var29 + var34));
                                var8.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var27, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var29 + var46), (double)((float)var27 * var29 / 4.0F + var32 * var29 + var34));
                                var8.addVertexWithUV((double)((float)var21 + var23) + 0.5D, (double)var28, (double)((float)var20 + var24) + 0.5D, (double)(1.0F * var29 + var46), (double)((float)var28 * var29 / 4.0F + var32 * var29 + var34));
                                var8.addVertexWithUV((double)((float)var21 - var23) + 0.5D, (double)var28, (double)((float)var20 - var24) + 0.5D, (double)(0.0F * var29 + var46), (double)((float)var28 * var29 / 4.0F + var32 * var29 + var34));
                                var8.setTranslation(0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                }
            }

            if (var18 >= 0)
            {
                var8.draw();
            }

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            disableLightmap((double)par1);
        }
    }

    /**
     * Setup orthogonal projection for rendering GUI screen overlays
     */
    public void setupOverlayRendering()
    {
        ScaledResolution var1 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        GL11.glClear(256);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, var1.scaledWidthD, var1.scaledHeightD, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
    }

    /**
     * calculates fog and calls glClearColor
     */
    private void updateFogColor(float par1)
    {
        World var2 = mc.theWorld;
        EntityLiving var3 = mc.renderViewEntity;
        float var4 = 1.0F / (float)(4 - mc.gameSettings.renderDistance);
        var4 = 1.0F - (float)Math.pow((double)var4, 0.25D);
        Vec3D var5 = var2.getSkyColor(mc.renderViewEntity, par1);
        float var6 = (float)var5.xCoord;
        float var7 = (float)var5.yCoord;
        float var8 = (float)var5.zCoord;
        Vec3D var9 = var2.getFogColor(par1);
        fogColorRed = (float)var9.xCoord;
        fogColorGreen = (float)var9.yCoord;
        fogColorBlue = (float)var9.zCoord;
        float var11;

        if (mc.gameSettings.renderDistance < 2)
        {
            Vec3D var10 = MathHelper.sin(var2.getCelestialAngleRadians(par1)) > 0.0F ? Vec3D.createVector(-1.0D, 0.0D, 0.0D) : Vec3D.createVector(1.0D, 0.0D, 0.0D);
            var11 = (float)var3.getLook(par1).dotProduct(var10);

            if (var11 < 0.0F)
            {
                var11 = 0.0F;
            }

            if (var11 > 0.0F)
            {
                float[] var12 = var2.worldProvider.calcSunriseSunsetColors(var2.getCelestialAngle(par1), par1);

                if (var12 != null)
                {
                    var11 *= var12[3];
                    fogColorRed = fogColorRed * (1.0F - var11) + var12[0] * var11;
                    fogColorGreen = fogColorGreen * (1.0F - var11) + var12[1] * var11;
                    fogColorBlue = fogColorBlue * (1.0F - var11) + var12[2] * var11;
                }
            }
        }

        fogColorRed += (var6 - fogColorRed) * var4;
        fogColorGreen += (var7 - fogColorGreen) * var4;
        fogColorBlue += (var8 - fogColorBlue) * var4;
        float var19 = var2.getRainStrength(par1);
        float var20;

        if (var19 > 0.0F)
        {
            var11 = 1.0F - var19 * 0.5F;
            var20 = 1.0F - var19 * 0.4F;
            fogColorRed *= var11;
            fogColorGreen *= var11;
            fogColorBlue *= var20;
        }

        var11 = var2.getWeightedThunderStrength(par1);

        if (var11 > 0.0F)
        {
            var20 = 1.0F - var11 * 0.5F;
            fogColorRed *= var20;
            fogColorGreen *= var20;
            fogColorBlue *= var20;
        }

        int var21 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(mc.theWorld, var3, par1);

        if (cloudFog)
        {
            Vec3D var13 = var2.drawClouds(par1);
            fogColorRed = (float)var13.xCoord;
            fogColorGreen = (float)var13.yCoord;
            fogColorBlue = (float)var13.zCoord;
        }
        else if (var21 != 0 && Block.blocksList[var21].blockMaterial == Material.water)
        {
            fogColorRed = 0.02F;
            fogColorGreen = 0.02F;
            fogColorBlue = 0.2F;
        }
        else if (var21 != 0 && Block.blocksList[var21].blockMaterial == Material.lava)
        {
            fogColorRed = 0.6F;
            fogColorGreen = 0.1F;
            fogColorBlue = 0.0F;
        }

        float var22 = fogColor2 + (fogColor1 - fogColor2) * par1;
        fogColorRed *= var22;
        fogColorGreen *= var22;
        fogColorBlue *= var22;
        double var14 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)par1) * var2.worldProvider.getVoidFogYFactor();

        if (var3.isPotionActive(Potion.blindness))
        {
            int var16 = var3.getActivePotionEffect(Potion.blindness).getDuration();

            if (var16 < 20)
            {
                var14 *= (double)(1.0F - (float)var16 / 20.0F);
            }
            else
            {
                var14 = 0.0D;
            }
        }

        if (var14 < 1.0D)
        {
            if (var14 < 0.0D)
            {
                var14 = 0.0D;
            }

            var14 *= var14;
            fogColorRed = (float)((double)fogColorRed * var14);
            fogColorGreen = (float)((double)fogColorGreen * var14);
            fogColorBlue = (float)((double)fogColorBlue * var14);
        }

        if (mc.gameSettings.anaglyph)
        {
            float var23 = (fogColorRed * 30.0F + fogColorGreen * 59.0F + fogColorBlue * 11.0F) / 100.0F;
            float var17 = (fogColorRed * 30.0F + fogColorGreen * 70.0F) / 100.0F;
            float var18 = (fogColorRed * 30.0F + fogColorBlue * 70.0F) / 100.0F;
            fogColorRed = var23;
            fogColorGreen = var17;
            fogColorBlue = var18;
        }

        GL11.glClearColor(fogColorRed, fogColorGreen, fogColorBlue, 0.0F);
    }

    /**
     * Sets up the fog to be rendered. If the arg passed in is -1 the fog starts at 0 and goes to 80% of far plane
     * distance and is used for sky rendering.
     */
    private void setupFog(int par1, float par2)
    {
        EntityLiving var3 = mc.renderViewEntity;
        boolean var4 = false;

        if (var3 instanceof EntityPlayer)
        {
            var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
        }

        if (par1 == 999)
        {
            GL11.glFog(GL11.GL_FOG_COLOR, setFogColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
            GL11.glFogf(GL11.GL_FOG_START, 0.0F);
            GL11.glFogf(GL11.GL_FOG_END, 8.0F);

            if (GLContext.getCapabilities().GL_NV_fog_distance)
            {
                GL11.glFogi(34138, 34139);
            }

            GL11.glFogf(GL11.GL_FOG_START, 0.0F);
        }
        else
        {
            GL11.glFog(GL11.GL_FOG_COLOR, setFogColorBuffer(fogColorRed, fogColorGreen, fogColorBlue, 1.0F));
            GL11.glNormal3f(0.0F, -1.0F, 0.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int var5 = ActiveRenderInfo.getBlockIdAtEntityViewpoint(mc.theWorld, var3, par2);
            float var6;

            if (var3.isPotionActive(Potion.blindness))
            {
                var6 = 5.0F;
                int var7 = var3.getActivePotionEffect(Potion.blindness).getDuration();

                if (var7 < 20)
                {
                    var6 = 5.0F + (farPlaneDistance - 5.0F) * (1.0F - (float)var7 / 20.0F);
                }

                GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

                if (par1 < 0)
                {
                    GL11.glFogf(GL11.GL_FOG_START, 0.0F);
                    GL11.glFogf(GL11.GL_FOG_END, var6 * 0.8F);
                }
                else
                {
                    GL11.glFogf(GL11.GL_FOG_START, var6 * 0.25F);
                    GL11.glFogf(GL11.GL_FOG_END, var6);
                }

                if (GLContext.getCapabilities().GL_NV_fog_distance)
                {
                    GL11.glFogi(34138, 34139);
                }
            }
            else
            {
                float var8;
                float var9;
                float var10;
                float var11;
                float var12;

                if (cloudFog)
                {
                    GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
                    GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
                    var6 = 1.0F;
                    var12 = 1.0F;
                    var8 = 1.0F;

                    if (mc.gameSettings.anaglyph)
                    {
                        var9 = (var6 * 30.0F + var12 * 59.0F + var8 * 11.0F) / 100.0F;
                        var10 = (var6 * 30.0F + var12 * 70.0F) / 100.0F;
                        var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
                    }
                }
                else if (var5 > 0 && Block.blocksList[var5].blockMaterial == Material.water)
                {
                    GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);

                    if (!var3.isPotionActive(Potion.waterBreathing))
                    {
                        GL11.glFogf(GL11.GL_FOG_DENSITY, (Float) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "setWatherFog"));
                    }
                    else
                    {
                        GL11.glFogf(GL11.GL_FOG_DENSITY, (Float) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "setWatherFog2"));
                    }

                    var6 = 0.4F;
                    var12 = 0.4F;
                    var8 = 0.9F;

                    if (mc.gameSettings.anaglyph)
                    {
                        var9 = (var6 * 30.0F + var12 * 59.0F + var8 * 11.0F) / 100.0F;
                        var10 = (var6 * 30.0F + var12 * 70.0F) / 100.0F;
                        var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
                    }
                }
                else if (var5 > 0 && Block.blocksList[var5].blockMaterial == Material.lava)
                {
                    GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
                    GL11.glFogf(GL11.GL_FOG_DENSITY, (Float) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "lavaFog"));
                    var6 = 0.4F;
                    var12 = 0.3F;
                    var8 = 0.3F;

                    if (mc.gameSettings.anaglyph)
                    {
                        var9 = (var6 * 30.0F + var12 * 59.0F + var8 * 11.0F) / 100.0F;
                        var10 = (var6 * 30.0F + var12 * 70.0F) / 100.0F;
                        var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
                    }
                }
                else
                {
                    var6 = farPlaneDistance;

                    if (mc.theWorld.worldProvider.getWorldHasVoidParticles() && !var4
                    		&& (Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isDimming"))
                    {
                        double var13 = (double)((var3.getBrightnessForRender(par2) & 15728640) >> 20) / 16.0D + (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)par2 + 4.0D) / 32.0D;

                        if (var13 < 1.0D
                        		&& (Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isVoidFog"))
                        {
                            if (var13 < 0.0D)
                            {
                                var13 = 0.0D;
                            }

                            var13 *= var13;
                            var9 = 100.0F * (float)var13;

                            if (var9 < 5.0F)
                            {
                                var9 = 5.0F;
                            }

                            if (var6 > var9)
                            {
                                var6 = var9;
                            }
                        }
                    }

                    GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

                    if (par1 < 0)
                    {
                        GL11.glFogf(GL11.GL_FOG_START, 0.0F);
                        GL11.glFogf(GL11.GL_FOG_END, var6 * 0.8F);
                    }
                    else
                    {
                        GL11.glFogf(GL11.GL_FOG_START, var6 * 0.25F);
                        GL11.glFogf(GL11.GL_FOG_END, var6);
                    }

                    if (GLContext.getCapabilities().GL_NV_fog_distance)
                    {
                        GL11.glFogi(34138, 34139);
                    }

                    if (mc.theWorld.worldProvider.func_48218_b((int)var3.posX, (int)var3.posZ))
                    {
                        GL11.glFogf(GL11.GL_FOG_START, var6 * 0.05F);
                        GL11.glFogf(GL11.GL_FOG_END, Math.min(var6, 192.0F) * 0.5F);
                    }
                    if (!(Boolean) Modchu_Reflect.getFieldObject(mod_PFLM_PlayerFormLittleMaid, "isFog")) {
                		GL11.glFogf(2915 /*GL_FOG_START*/, 0.0F);
                		GL11.glFogf(2916 /*GL_FOG_END*/, var6 * 10.0F);
            		}
                }
            }

            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
        }
    }

    /**
     * Update and return fogColorBuffer with the RGBA values passed as arguments
     */
    private FloatBuffer setFogColorBuffer(float par1, float par2, float par3, float par4)
    {
        fogColorBuffer.clear();
        fogColorBuffer.put(par1).put(par2).put(par3).put(par4);
        fogColorBuffer.flip();
        return fogColorBuffer;
    }
}
