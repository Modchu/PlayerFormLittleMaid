package net.minecraft.src;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.smart.moving.SmartMoving;
import net.smart.moving.SmartMovingFactory;
import net.smart.moving.SmartMovingSelf;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayer extends RenderPlayer
{
	public static HashMap<Entity, PFLM_ModelData> playerData = new HashMap();
	protected static MultiModelBaseBiped modelBasicOrig[];
	public static String[] armorFilenamePrefix;
	private static Minecraft mc = Minecraft.getMinecraft();
	public static boolean resetFlag = false;
	public static boolean textureResetFlag = false;
	public static boolean firstPersonHandResetFlag = true;
	public static boolean initResetFlag = false;
	private static final int skinMode_online							= 0;
	private static final int skinMode_local								= 1;
	private static final int skinMode_char								= 2;
	private static final int skinMode_offline							= 3;
	private static final int skinMode_Player							= 4;
	private static final int skinMode_OthersSettingOffline				= 5;
	private static final int skinMode_PlayerOffline						= 6;
	private static final int skinMode_PlayerOnline						= 7;
	private static final int skinMode_PlayerLocalData					= 8;
	private static final int skinMode_Random							= 9;
	private static final int skinMode_OthersIndividualSettingOffline	= 10;
	private static Random rnd = new Random();
	private static int actionTime;
	private boolean checkGlEnableWrapper = true;
	private boolean checkGlDisableWrapper = true;
	private boolean isSizeMultiplier = false;
	private Method sizeMultiplier;
	public static Object pflm_RenderPlayerSmart;
	public static Class PFLM_RenderPlayerSmart;
    // b173deleteprivate RenderBlocks renderBlocks;

	public PFLM_RenderPlayer() {
		modelBasicOrig = new MultiModel[3];
		modelBasicOrig[0] = new MultiModel(0.0F);
		modelBasicOrig[1] = new MultiModel(0.5F);
		modelBasicOrig[2] = new MultiModel(0.1F);
		armorFilenamePrefix = (String[]) Modchu_Reflect.getFieldObject(RenderPlayer.class, "h", "armorFilenamePrefix");
		sizeMultiplier = Modchu_Reflect.getMethod(Entity.class, "getSizeMultiplier");
		isSizeMultiplier = sizeMultiplier != null;
		if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			Modchu_Debug.mDebug("PFLM_RenderPlayer() isSmartMoving");
			pflm_RenderPlayerSmart = Modchu_Reflect.newInstance(mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.getClassName("PFLM_RenderPlayerSmart"), new Class[]{ PFLM_RenderPlayer.class }, new Object[]{ this });
			PFLM_RenderPlayerSmart = Modchu_Reflect.loadClass(mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.getClassName("PFLM_RenderPlayerSmart"));
		} else {
			Modchu_Debug.mDebug("PFLM_RenderPlayer() isSmartMoving false");
		}
		// b173deleterenderBlocks = new RenderBlocks();
	}

    protected int setArmorModel(EntityPlayer entityplayer, int i, float f)
    {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	/*b181//*/byte byte0 = -1;
    	//b181 deleteboolean byte0 = false;
    	if (modelDataPlayerFormLittleMaid != null) ;else return byte0;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return byte0;
    	// アーマーの表示設定
    	modelDataPlayerFormLittleMaid.modelFATT.renderParts = i;
    	ItemStack is = entityplayer.inventory.armorItemInSlot(i);
    	if (is != null && is.stackSize > 0) {
    		modelDataPlayerFormLittleMaid.modelFATT.showArmorParts(i);
//-@-b181
    		byte0 = (byte) (is.isItemEnchanted() ? 15 : 1);
//@-@b181
    		armorTextureSetting(entityplayer, modelDataPlayerFormLittleMaid, is, i);
    	}
    	return byte0;
    }

    private void armorTextureSetting(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid, ItemStack is, int i) {
    	int i2 = i;
    	String t = modelDataPlayerFormLittleMaid.modelArmorName;
    	//Modchu_Debug.mDebug("setArmorModel t="+t);
    	boolean isBiped = MultiModel_Biped.class.isInstance(modelDataPlayerFormLittleMaid.modelMain.modelArmorInner);
    	if (t != null) ;else t = isBiped ? "Biped" : "default";
    	if (modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter != null
    			&& modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner != null) {
    	} else {
    		modelArmorInit(entityplayer, modelDataPlayerFormLittleMaid, t);
    	}
    	if (isBiped) {
    		ItemArmor itemarmor = null;
    		boolean flag = false;
    		Item item = is.getItem();
    		if(item instanceof ItemArmor)
    		{
    			itemarmor = (ItemArmor)item;
    			flag = itemarmor != null && is.stackSize > 0;
    		}
    		if (flag) {
    			modelDataPlayerFormLittleMaid.modelFATT.textureOuter[i] = "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + 2 + ".png";
    			modelDataPlayerFormLittleMaid.modelFATT.textureInner[i] = "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + 1 + ".png";
    		}
    	} else {
    		modelDataPlayerFormLittleMaid.modelFATT.textureOuter[i] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetArmorTextureName(t, 64, is);
    		modelDataPlayerFormLittleMaid.modelFATT.textureInner[i] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetArmorTextureName(t, 80, is);
    		//Modchu_Debug.mDebug("modelDataPlayerFormLittleMaid.modelFATT.textureOuter["+i+"]="+modelDataPlayerFormLittleMaid.modelFATT.textureOuter[i]);
    		//Modchu_Debug.mDebug("modelDataPlayerFormLittleMaid.modelFATT.textureInner["+i+"]="+modelDataPlayerFormLittleMaid.modelFATT.textureInner[i]);
    		//if (modelDataPlayerFormLittleMaid.modelFATT.textureInner[i] != null) ;else modelDataPlayerFormLittleMaid.modelFATT.textureInner[i] = modelDataPlayerFormLittleMaid.modelFATT.textureOuter[i];
    	}
    }

	/**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData((EntityPlayer)entityliving);
    	if (modelDataPlayerFormLittleMaid != null) {} else return -1;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return -1;
    	String t = null;
    	if (i < 4 && modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter != null)
    	{
//-@-b173
    		if (modelDataPlayerFormLittleMaid.modelFATT.textureOuter == null) return -1;
//@-@b173
    		// b173deleteif (modelDataPlayerFormLittleMaid.modelFATT.textureOuter == null) return false;
    		t = modelDataPlayerFormLittleMaid.modelFATT.textureOuter[i];
    		if (t == null)
    		{
//-@-b181
    			return -1;
//@-@b181
    			//b181 deletereturn false;
    		} else {
    			setRenderPassModel(modelDataPlayerFormLittleMaid.modelFATT);
//-@-b181
    			return 1;
//@-@b181
    			//b181 deletereturn true;
    		}
    	}
    	if (i < 8 && modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner != null)
    	{
//-@-b173
    		if (modelDataPlayerFormLittleMaid.modelFATT.textureInner == null) return -1;
//@-@b173
    		// b173deleteif (modelDataPlayerFormLittleMaid.modelFATT.textureInner == null) return false;
    		t = modelDataPlayerFormLittleMaid.modelFATT.textureInner[i - 4];
    		if (t == null) {
//-@-b181
    			return -1;
//@-@b181
    			//b181 deletereturn false;
    		} else {
    			//loadTexture(t);
    			setRenderPassModel(modelDataPlayerFormLittleMaid.modelFATT);
//-@-b181
    			return 1;
//@-@b181
    			//b181 deletereturn true;
    		}
    	} else {
//-@-b181
			return -1;
//@-@b181
			//b181 deletereturn false;
    	}
	}

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
    	if (isSizeMultiplier) {
    		float f2 = 0.9375F * (Float) Modchu_Reflect.invoke(sizeMultiplier, entityliving);
    		GL11.glScalef(f2, f2, f2);
    		return;
    	}
    	if (!mod_PFLM_PlayerFormLittleMaid.useScaleChange) {
    		super.preRenderCallback(entityliving, f);
    		return;
    	}
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData((EntityPlayer)entityliving);
    	if (modelDataPlayerFormLittleMaid != null) {} else return;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	float f1 = modelDataPlayerFormLittleMaid.isPlayer ? PFLM_Gui.modelScale : modelDataPlayerFormLittleMaid.modelScale;
    	if (f1 == 0.0F) f1 = ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).getModelScale();
    	GL11.glScalef(f1, f1, f1);
    }

    private float interpolateRotation(float par1, float par2, float par3)
    {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F)
        {
            ;
        }

        while (var4 >= 180.0F)
        {
            var4 -= 360.0F;
        }

        return par1 + par3 * var4;
    }

    public void shadersGlDisableWrapper(int i) {
    	Package pac = this.getClass().getPackage();
    	String s;
    	if (pac == null) s = "Shaders";
    	else s = pac.getName().concat(".Shaders");
    	Method mes = null;
    	if (checkGlDisableWrapper) {
    		try {
    			mes = Class.forName(s).getMethod("glDisableWrapper", new Class[] {int.class});
    			try {
    				mes.invoke(null, i);
    			} catch (Exception e) {
    				checkGlDisableWrapper = false;
    			}
    		} catch (Exception e) {
    			checkGlDisableWrapper = false;
    		}
    	}
    	if (!checkGlDisableWrapper) {
    		glDisableWrapper(s, i);
    	}
    }

    public void shadersGlEnableWrapper(int i) {
    	Package pac = this.getClass().getPackage();
    	String s;
    	if (pac == null) s = "Shaders";
    	else s = pac.getName().concat(".Shaders");
    	Method mes = null;
    	if (checkGlEnableWrapper) {
    		try {
    			mes = Class.forName(s).getMethod("glEnableWrapper", new Class[] {int.class});
    			try {
    				mes.invoke(null, i);
    			} catch (Exception e) {
    				checkGlEnableWrapper = false;
    			}
    		} catch (Exception e) {
    			checkGlEnableWrapper = false;
    		}
    	}
    	if (!checkGlEnableWrapper) {
    		glEnableWrapper(s, i);
    	}
    }

	public static void glEnableWrapper(String s, int i) {
		GL11.glEnable(i);
		if (i == GL11.GL_TEXTURE_2D) {
			Method mes = null;
			try {
				mes = Class.forName(s).getMethod("glEnableWrapperTexture2D", (Class[]) null);
				try {
					mes.invoke(null, (Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (i == GL11.GL_FOG) {
			Method mes = null;
			try {
				mes = Class.forName(s).getMethod("glEnableWrapperFog", (Class[]) null);
				try {
					mes.invoke(null, (Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void glDisableWrapper(String s, int i) {
		GL11.glDisable(i);
		if (i == GL11.GL_TEXTURE_2D) {
			Method mes = null;
			try {
				mes = Class.forName(s).getMethod("glDisableWrapperTexture2D", (Class[]) null);
				try {
					mes.invoke(null, (Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (i == GL11.GL_FOG) {
			Method mes = null;
			try {
				mes = Class.forName(s).getMethod("glDisableWrapperFog", (Class[]) null);
				try {
					mes.invoke(null, (Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

    public void doRenderLivingPFLM(PFLM_ModelData modelDataPlayerFormLittleMaid, EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
    	GL11.glPushMatrix();
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    		//Shaders.glDisableWrapper(k1);
    		shadersGlDisableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glDisable(GL11.GL_CULL_FACE);
    	}

    	try
    	{
/*//132delete
    		float f2 = entityliving.prevRenderYawOffset + (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * f1;
    		float f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
    		float f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
*///132delete
//-@-132
    		float f2;
    		float f3;
    		float f4;
    		if (mc.currentScreen != null
    				&& mc.currentScreen instanceof PFLM_Gui
    				| mc.currentScreen instanceof PFLM_GuiOthersPlayer
    				| mc.currentScreen instanceof PFLM_GuiModelSelect) {
    			f2 = entityliving.prevRenderYawOffset + (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * f1;
    			f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
    			f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
    		} else {
    			f2 = this.interpolateRotation(entityliving.prevRenderYawOffset, entityliving.renderYawOffset, f1);
    			f3 = this.interpolateRotation(entityliving.prevRotationYawHead, entityliving.rotationYawHead, f1);
    			f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
    		}
//@-@132
    		renderLivingAt(entityliving, d, d1, d2);
    		float f5 = handleRotationFloat(entityliving, f1);
    		rotateCorpse(entityliving, f5, f2, f1);
    		float f6 = 0.0625F;
    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			//Shaders.glEnableWrapper(GL12.GL_RESCALE_NORMAL);
    			shadersGlEnableWrapper(GL12.GL_RESCALE_NORMAL);
    		} else {
    			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    		}
    		GL11.glScalef(-1F, -1F, 1.0F);
    		preRenderCallback(entityliving, f1);
    		GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
    		float f7 = entityliving.prevLegYaw + (entityliving.legYaw - entityliving.prevLegYaw) * f1;
    		float f8 = entityliving.legSwing - entityliving.legYaw * (1.0F - f1);
//-@-b181
    		if (entityliving.isChild())
    		{
    			f8 *= 3F;
    		}
//@-@b181
    		if (f7 > 1.0F)
    		{
    			f7 = 1.0F;
    		}

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			//Shaders.glEnableWrapper(GL11.GL_ALPHA_TEST);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    				//Shaders.glEnableWrapper(GL11.GL_BLEND);
    				shadersGlEnableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glEnable(GL11.GL_BLEND);
    			}
    			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    		}
    		else
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    				//Shaders.glDisableWrapper(k1);
    				shadersGlDisableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glDisable(GL11.GL_BLEND);
    			}
    		}

    		modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.setLivingAnimations(entityliving, f8, f7, f1);
    		renderModel(entityliving, f8, f7, f5, f3 - f2, f4, f6);
            // b173deletefloat f9 = mc.currentScreen == null | mc.currentScreen instanceof GuiIngameMenu ? entityliving.getEntityBrightness(f1) : 1.0F;
    		for (int i = 0; i < 4; i++)
    		{
    			int j = setArmorModel((EntityPlayer)entityliving, i, f);

    			//b181 deleteif (!j)
    			/*b181//*/if (j <= 0)
    			{
    				continue;
    			}

    			for (int l = 0; l < 5; l += 4)
    			{
    				if (shouldRenderPass(entityliving, i + l, f1) < 0)
    				{
    					continue;
    				}

    				// b166deletefloat f10 = 1.0F;
    				if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    						//Shaders.glEnableWrapper(GL11.GL_BLEND);
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    					/*b173//*/GL11.glColor4f(1.0F, 1.0F, 1.0F, mod_PFLM_PlayerFormLittleMaid.transparency);
    					// b173deletef10 = mod_PFLM_PlayerFormLittleMaid.transparency;
    				}
    				// b173deleteGL11.glColor4f(f9, f9, f9, f10);
    				renderPassModel.setLivingAnimations(entityliving, f8, f7, f1);
//-@-132
    				if (mod_Modchu_ModchuLib.useInvisibilityArmor) {
    					if (!entityliving.getHasActivePotion()) {
//@-@132
    						renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
//-@-132
    					}
    				} else renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
//@-@132
    				if (mod_PFLM_PlayerFormLittleMaid.transparency != 1.0F) GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//-@-b181
    				if (j != 15)
    				{
    					continue;
    				}

    				float f11 = (float)entityliving.ticksExisted + f1;
    				loadTexture("%blur%/misc/glint.png");
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    					//Shaders.glEnableWrapper(GL11.GL_BLEND);
    					shadersGlEnableWrapper(GL11.GL_BLEND);
    				} else {
    					GL11.glEnable(GL11.GL_BLEND);
    				}
    				float f13 = 0.5F;
    				GL11.glColor4f(f13, f13, f13, 1.0F);
    				GL11.glDepthFunc(GL11.GL_EQUAL);
    				GL11.glDepthMask(false);

    				for (int j1 = 0; j1 < 2; j1++)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    						//Shaders.glDisableWrapper(GL11.GL_LIGHTING);
    						shadersGlDisableWrapper(GL11.GL_LIGHTING);
    					} else {
    						GL11.glDisable(GL11.GL_LIGHTING);
    					}
    					float f16 = 0.76F;
    					GL11.glColor4f(0.5F * f16, 0.25F * f16, 0.8F * f16, 1.0F);
    					GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
    					GL11.glMatrixMode(GL11.GL_TEXTURE);
    					GL11.glLoadIdentity();
    					float f17 = f11 * (0.001F + (float)j1 * 0.003F) * 20F;
    					float f18 = 0.3333333F;
    					GL11.glScalef(f18, f18, f18);
    					GL11.glRotatef(30F - (float)j1 * 60F, 0.0F, 0.0F, 1.0F);
    					GL11.glTranslatef(0.0F, f17, 0.0F);
    					GL11.glMatrixMode(GL11.GL_MODELVIEW);
//-@-132
    					if (mod_Modchu_ModchuLib.useInvisibilityArmor) {
    						if (!entityliving.getHasActivePotion()) {
//@-@132
    							renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
//-@-132
    						}
    					} else renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
//@-@132
    				}

    				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    				GL11.glMatrixMode(GL11.GL_TEXTURE);
    				GL11.glDepthMask(true);
    				GL11.glLoadIdentity();
    				GL11.glMatrixMode(GL11.GL_MODELVIEW);
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    					//Shaders.glEnableWrapper(GL11.GL_LIGHTING);
    					shadersGlEnableWrapper(GL11.GL_LIGHTING);
    				} else {
    					GL11.glEnable(GL11.GL_LIGHTING);
    				}

    				if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    						//Shaders.glEnableWrapper(GL11.GL_BLEND);
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    				}
    				else
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    						//Shaders.glDisableWrapper(GL11.GL_BLEND);
    						shadersGlDisableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glDisable(GL11.GL_BLEND);
    					}
    				}

    				GL11.glDepthFunc(GL11.GL_LEQUAL);
//@-@b181
    			}

    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			//Shaders.glDisableWrapper(GL11.GL_BLEND);
    			shadersGlDisableWrapper(GL11.GL_BLEND);
    			//Shaders.glEnableWrapper(GL11.GL_ALPHA_TEST);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glDisable(GL11.GL_BLEND);
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		// b173deleteGL11.glColor4f(f9, f9, f9, 1.0F);
    		/*132//*/if (entityliving.getHasActivePotion()) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6, entityliving);
    		renderEquippedItems(entityliving, f1);
    		/*b173//*/float f9 = entityliving.getBrightness(f1);
    		int k = getColorMultiplier(entityliving, f9, f1);
    		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			//Shaders.glDisableWrapper(GL11.GL_TEXTURE_2D);
    			shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
    		} else {
    			GL11.glDisable(GL11.GL_TEXTURE_2D);
    		}
    		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

    		if ((k >> 24 & 0xff) > 0 || entityliving.hurtTime > 0 || entityliving.deathTime > 0)
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    				//Shaders.glDisableWrapper(GL11.GL_TEXTURE_2D);
    				shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
    				//Shaders.glDisableWrapper(GL11.GL_ALPHA_TEST);
    				shadersGlDisableWrapper(GL11.GL_ALPHA_TEST);
    				//Shaders.glEnableWrapper(GL11.GL_BLEND);
    				shadersGlEnableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glDisable(GL11.GL_TEXTURE_2D);
    				GL11.glDisable(GL11.GL_ALPHA_TEST);
    				GL11.glEnable(GL11.GL_BLEND);
    			}
    			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    			GL11.glDepthFunc(GL11.GL_EQUAL);

    			if (entityliving.hurtTime > 0 || entityliving.deathTime > 0)
    			{
    				GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
    				modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);

    				for (int i1 = 0; i1 < 4; i1++)
    				{
    					if (inheritRenderPass(entityliving, i1, f1) >= 0)
    					{
    						GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
    						renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    					}
    				}
    			}

    			if ((k >> 24 & 0xff) > 0)
    			{
    				float f10 = (float)(k >> 16 & 0xff) / 255F;
    				float f12 = (float)(k >> 8 & 0xff) / 255F;
    				float f14 = (float)(k & 0xff) / 255F;
    				float f15 = (float)(k >> 24 & 0xff) / 255F;
    				GL11.glColor4f(f10, f12, f14, f15);
    				modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);

    				for (int k1 = 0; k1 < 4; k1++)
    				{
    					if (inheritRenderPass(entityliving, k1, f1) >= 0)
    					{
    						GL11.glColor4f(f10, f12, f14, f15);
    						renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    					}
    				}
    			}

    			GL11.glDepthFunc(GL11.GL_LEQUAL);
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    				//Shaders.glDisableWrapper(GL11.GL_BLEND);
    				shadersGlDisableWrapper(GL11.GL_BLEND);
    				//Shaders.glEnableWrapper(GL11.GL_ALPHA_TEST);
    				shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    				//Shaders.glEnableWrapper(GL11.GL_TEXTURE_2D);
    				shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
    			} else {
    				GL11.glDisable(GL11.GL_BLEND);
    				GL11.glEnable(GL11.GL_ALPHA_TEST);
    				GL11.glEnable(GL11.GL_TEXTURE_2D);
    			}
    		}

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			//Shaders.glDisableWrapper(GL12.GL_RESCALE_NORMAL);
    			shadersGlDisableWrapper(GL12.GL_RESCALE_NORMAL);
    		} else {
    			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    		}
    	}
    	catch (Exception exception)
    	{
    		exception.printStackTrace();
    	}

    	OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    		//Shaders.glEnableWrapper(GL11.GL_TEXTURE_2D);
    		shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
    	} else {
    		GL11.glEnable(GL11.GL_TEXTURE_2D);
    	}
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    		//Shaders.glEnableWrapper(GL11.GL_CULL_FACE);
    		shadersGlEnableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glEnable(GL11.GL_CULL_FACE);
    	}
    	GL11.glPopMatrix();
    	passSpecialRender(entityliving, d, d1, d2);
    }

    public void doRenderPlayerFormLittleMaid(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1) {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) ;else return;
    	doRenderSetting(entityplayer, modelDataPlayerFormLittleMaid);
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) Modchu_Reflect.invokeMethod(PFLM_RenderPlayerSmart, "renderPlayer", new Class[]{ EntityPlayer.class, double.class , double.class, double.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderPlayerSmart, new Object[]{ entityplayer, d, d1, d2, f, f1, modelDataPlayerFormLittleMaid });
    	float f8 = entityplayer.legSwing - entityplayer.legYaw * (1.0F - f1);
    	waitModeSetting(modelDataPlayerFormLittleMaid, f8);
    	if (modelDataPlayerFormLittleMaid.isPlayer) {
    		modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.isWait =
    				modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.isWait =
    				modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isWait = mod_PFLM_PlayerFormLittleMaid.isWait;
    	} else {
    		modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.isWait =
    				modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.isWait =
    				modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isWait = modelDataPlayerFormLittleMaid.isWait;
    	}
    	modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.onGround =
    			modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.onGround =
    			modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.onGround = renderSwingProgress((EntityLiving) entityplayer, f1);
    	if (renderPassModel != null) renderPassModel.onGround = mainModel.onGround;
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isInventory =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isInventory =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isInventory =
    			d == 0.0D && d1 == 0.0D && d2 == 0.0D && f == 0.0F && f1 == 1.0F;
    	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    	if (modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isItemHolder()) {
    		modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.heldItemRight =
    				modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.heldItemRight =
    				modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.heldItemRight = itemstack != null ? 1 : 0;
    	}
//-@-b173
    	if (itemstack != null && entityplayer.getItemInUseCount() > 0) {
    		EnumAction enumaction = itemstack.getItemUseAction();
    		if (enumaction == EnumAction.block) {
    			if (modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isItemHolder()) {
    				modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.heldItemRight =
    						modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.heldItemRight =
    						modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.heldItemRight = 3;
    			}
    		} else if (enumaction == EnumAction.bow) {
    			modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.aimedBow =
    					modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.aimedBow =
    					modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.aimedBow = true;
    		}
    	}
//@-@b173
    	double d3;
    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize
//-@-125
    			&& mc.isSingleplayer()
//@-@125
    			// 125delete&& mc.thePlayer.worldObj.isRemote
    			&& !entityplayer.isRiding()) {
    		//d3 = d1 - (double)((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).getyOffset();
    		d3 = d1 - (double)entityplayer.yOffset;
    	}
    	else d3 = d1 - (double)entityplayer.yOffset;
    	if (entityplayer.isSneaking() && !(entityplayer instanceof EntityPlayerSP)) d3 -= 0.125D;
    	//if (mod_PFLM_PlayerFormLittleMaid.isModelSize) d3 += 0.45D;

    	if (entityplayer.isRiding()) {
    		d3 += 0.25D;
    		if (mod_PFLM_PlayerFormLittleMaid.isModelSize && mod_PFLM_PlayerFormLittleMaid.changeMode != PFLM_Gui.modeOnline)  d3 -= 0.43D;
    	}
    	if (entityplayer.isSneaking()) {
    		if (entityplayer.isRiding()) {
    			d3 -= 0.1D;
    		}
    	}
    	byte byte0 = entityplayer.getDataWatcher().getWatchableObjectByte(16);
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isSitting =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isSitting =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isSitting = byte0 == 1;
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isSleeping =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isSleeping =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isSleeping = entityplayer.isPlayerSleeping() | byte0 == 2;
    	if (byte0 == 1) d3 += ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).getSittingyOffset();
    	if (byte0 == 2) d3 += ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).getSleepingyOffset();

    	if (!(mc.currentScreen instanceof PFLM_GuiOthersPlayer)
    			&& modelDataPlayerFormLittleMaid.modelMain.modelArmorInner != null) doRenderLivingPFLM(modelDataPlayerFormLittleMaid, (EntityLiving) entityplayer, d, d3, d2, f, f1);
    	modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.aimedBow =
    			modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.aimedBow =
    			modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.aimedBow = false;
    	modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.isSneak =
    			modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.isSneak =
    			modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isSneak = false;
    	modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.heldItemRight =
    			modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.heldItemRight =
    			modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.heldItemRight = 0;
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isInventory =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isInventory =
    				((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isInventory = false;
    }

    private void doRenderSetting(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid) {
    	if (modelDataPlayerFormLittleMaid.isPlayer) {
    		if (mod_PFLM_PlayerFormLittleMaid.mushroomConfusion) mod_PFLM_PlayerFormLittleMaid.mushroomConfusion(entityplayer, modelDataPlayerFormLittleMaid);
    	}
    	if (modelDataPlayerFormLittleMaid.changeModelFlag) {
    		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).showPartsInit();
    		modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.changeModel(entityplayer);
    		modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.changeModel(entityplayer);
    		modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.changeModel(entityplayer);
    		modelDataPlayerFormLittleMaid.changeModelFlag = false;
    		modelDataPlayerFormLittleMaid.resetHandedness = true;
    	}
    	if (modelDataPlayerFormLittleMaid.resetHandedness) {
    		setHandedness(entityplayer, modelDataPlayerFormLittleMaid.handedness);
    		modelDataPlayerFormLittleMaid.resetHandedness = false;
    	}
    	modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.isSneak =
    			modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.isSneak =
    			modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isSneak = entityplayer.isSneaking();
    	modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter.isRiding =
    			modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner.isRiding =
    			modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isRiding = entityplayer.isRiding();
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isPlayer =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isPlayer =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isPlayer = modelDataPlayerFormLittleMaid.isPlayer;
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).handedness =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).handedness =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).handedness = modelDataPlayerFormLittleMaid.handedness;
    	if (modelDataPlayerFormLittleMaid.shortcutKeysAction) {
    		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).actionSpeed =
    				((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).actionSpeed =
    				((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).actionSpeed = getActionSpeed();
    		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).actionFlag =
    				((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).actionFlag =
    				((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).actionFlag = true;
    		if (modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag) {
    			modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag = false;
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).actionInit(modelDataPlayerFormLittleMaid.runActionNumber);
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).actionInit(modelDataPlayerFormLittleMaid.runActionNumber);
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).actionInit(modelDataPlayerFormLittleMaid.runActionNumber);
    		}
    	} else {
    		if (!modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag) {
    			modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag = true;
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).actionRelease(modelDataPlayerFormLittleMaid.runActionNumber);
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).actionRelease(modelDataPlayerFormLittleMaid.runActionNumber);
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).actionRelease(modelDataPlayerFormLittleMaid.runActionNumber);
    		}
    	}
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).syncModel((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner);
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).syncModel((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner);
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	if(mc.currentScreen instanceof GuiSelectWorld) return;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	doRenderPlayerFormLittleMaid((EntityPlayer)entity, d, d1, d2, f, f1);
    }

    protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
    	renderSpecials((EntityPlayer)entityliving, f);
    }

    protected void renderSpecials(EntityPlayer entityplayer, float f)
    {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) {} else return;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.renderItems(entityplayer, this);

    	if (entityplayer.username.equals("deadmau5") && loadDownloadableImageTexture(entityplayer.skinUrl, null))
    	{
    		for (int i = 0; i < 2; i++)
    		{
    			float f2 = (entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f) - (entityplayer.prevRenderYawOffset + (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * f);
    			float f3 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
    			GL11.glPushMatrix();
    			GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
    			GL11.glRotatef(f3, 1.0F, 0.0F, 0.0F);
    			GL11.glTranslatef(0.375F * (float)(i * 2 - 1), 0.0F, 0.0F);
    			GL11.glTranslatef(0.0F, -0.375F, 0.0F);
    			GL11.glRotatef(-f3, 1.0F, 0.0F, 0.0F);
    			GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
    			float f8 = 1.333333F;
    			GL11.glScalef(f8, f8, f8);
    			modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.renderEars(0.0625F);
    			GL11.glPopMatrix();
    		}
    	}
    	if (loadDownloadableImageTexture(entityplayer.playerCloakUrl, null))
    	{
    		GL11.glPushMatrix();
    		GL11.glTranslatef(0.0F, 0.0F, 0.125F);
    		double d = (entityplayer.field_71091_bM + (entityplayer.field_71094_bP - entityplayer.field_71091_bM) * (double)f) - (entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)f);
    		double d1 = (entityplayer.field_71096_bN + (entityplayer.field_71095_bQ - entityplayer.field_71096_bN) * (double)f) - (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)f);
    		double d2 = (entityplayer.field_71097_bO + (entityplayer.field_71085_bR - entityplayer.field_71097_bO) * (double)f) - (entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)f);
    		float f11 = entityplayer.prevRenderYawOffset + (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * f;
    		double d3 = MathHelper.sin((f11 * 3.141593F) / 180F);
    		double d4 = -MathHelper.cos((f11 * 3.141593F) / 180F);
    		float f13 = (float)d1 * 10F;
    		if (f13 < -6F)
    		{
    			f13 = -6F;
    		}
    		if (f13 > 32F)
    		{
    			f13 = 32F;
    		}
    		float f14 = (float)(d * d3 + d2 * d4) * 100F;
    		float f15 = (float)(d * d4 - d2 * d3) * 100F;
    		if (f14 < 0.0F)
    		{
    			f14 = 0.0F;
    		}
    		float f16 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * f;
    		f13 += MathHelper.sin((entityplayer.prevDistanceWalkedModified + (entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified) * f) * 6F) * 32F * f16;
    		if (entityplayer.isSneaking())
    		{
    			f13 += 25F;
    		}
    		GL11.glRotatef(6F + f14 / 2.0F + f13, 1.0F, 0.0F, 0.0F);
    		GL11.glRotatef(f15 / 2.0F, 0.0F, 0.0F, 1.0F);
    		GL11.glRotatef(-f15 / 2.0F, 0.0F, 1.0F, 0.0F);
    		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
    		modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.renderCloak(0.0625F);
    		GL11.glPopMatrix();
    	}
    }

    public void func_82441_a(EntityPlayer entityplayer) {
    	func_82441_a(entityplayer, 2);
    }

    public void func_82441_a(EntityPlayer entityplayer, int i) {
    	//olddays導入時に2以外のint付きで呼ばれる。
    	if(mc.currentScreen instanceof GuiSelectWorld) return;
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(mc.thePlayer);
    	if (modelDataPlayerFormLittleMaid != null) ;else return;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	if (!modelDataPlayerFormLittleMaid.isPlayer) {
    		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).firstPerson = false;
    		return;
    	}
    	doRenderSetting(entityplayer, modelDataPlayerFormLittleMaid);
    	float var2 = 1.0F;
    	GL11.glColor3f(var2, var2, var2);
    	if (i >= 2
    			&& i != 1) {
    		modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.onGround = 0.0F;
    		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).firstPerson = true;
    		modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mc.thePlayer);
    	}
    	if (firstPersonHandResetFlag
    			&& modelDataPlayerFormLittleMaid.modelMain.modelArmorInner != null
    			&& modelDataPlayerFormLittleMaid.modelMain.textureOuter != null
    			&& renderManager.renderEngine != null) {
    		firstPersonHandResetFlag = false;
    		loadTexture(modelDataPlayerFormLittleMaid.modelMain.textureOuter[0]);
    	}
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).renderFirstPersonHand(0.0625F);
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).firstPerson = false;
    }

    public static boolean isActivatedForPlayer(EntityPlayer entityplayer) {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) {} else return false;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return false;
    	return modelDataPlayerFormLittleMaid.isActivated;
    }

    public static PFLM_ModelData getPlayerData(EntityPlayer entityplayer) {
    	if (entityplayer != null) ;else return null;
    	PFLM_ModelData modelDataPlayerFormLittleMaid = playerData.get(entityplayer);

    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return null;
    	boolean b = false;
    	if (modelDataPlayerFormLittleMaid != null) {
    		//Modchu_Debug.Debug("initFlag="+modelDataPlayerFormLittleMaid.initFlag);
    		if (modelDataPlayerFormLittleMaid.initFlag != 2) b = true;
    	} else b = true;
    	if (b
    			| resetFlag) {
    		if (resetFlag) {
    			resetFlag = false;
    			//Modchu_Debug.mDebug("resetFlag clearPlayers()");
    			mod_PFLM_PlayerFormLittleMaid.clearPlayers();
    			modelDataPlayerFormLittleMaid = null;
    		}
    		modelDataPlayerFormLittleMaid = loadPlayerData(entityplayer, modelDataPlayerFormLittleMaid);
    		playerData.put(entityplayer, modelDataPlayerFormLittleMaid);
    	}
    	if (modelDataPlayerFormLittleMaid != null) {
    	} else {
        	return null;
    	}
    	switch (modelDataPlayerFormLittleMaid.skinMode) {
    	case skinMode_online:
    		entityplayer.skinUrl = "http://skins.minecraft.net/MinecraftSkins/" + entityplayer.username + ".png";
    		entityplayer.texture = null;
    		if (modelDataPlayerFormLittleMaid.modelMain != null
    				&& modelDataPlayerFormLittleMaid.modelMain.textureOuter != null) modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = null;
    		break;
    	case skinMode_local:
    	case skinMode_PlayerOffline:
    	case skinMode_Random:
    	case skinMode_OthersIndividualSettingOffline:
    		entityplayer.skinUrl = null;
    		if (modelDataPlayerFormLittleMaid.modelMain != null
    				&& modelDataPlayerFormLittleMaid.modelMain.textureOuter != null) entityplayer.texture = modelDataPlayerFormLittleMaid.modelMain.textureOuter[0];
    		break;
    	case skinMode_char:
    		entityplayer.skinUrl = null;
    		if (modelDataPlayerFormLittleMaid.modelMain != null
    				&& modelDataPlayerFormLittleMaid.modelMain.textureOuter != null) entityplayer.texture = modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = "/mob/char.png";
    		break;
    	case skinMode_offline:
    		if (mod_PFLM_PlayerFormLittleMaid.textureName != null) {} else  mod_PFLM_PlayerFormLittleMaid.textureName = "default";
    		if (mod_PFLM_PlayerFormLittleMaid.textureArmorName != null) {} else mod_PFLM_PlayerFormLittleMaid.textureArmorName = "default";
    		entityplayer.skinUrl = null;
    		entityplayer.texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, mod_PFLM_PlayerFormLittleMaid.maidColor);
    		if (modelDataPlayerFormLittleMaid.modelMain.modelArmorInner != null
    				&& modelDataPlayerFormLittleMaid.modelMain.textureOuter != null) modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = entityplayer.texture;
    		//Modchu_Debug.mDebug("skinMode_offline mod_PFLM_PlayerFormLittleMaid.textureName="+mod_PFLM_PlayerFormLittleMaid.textureName);
    		break;
    	case skinMode_Player:
    		entityplayer.skinUrl = mc.thePlayer.skinUrl;
    		entityplayer.texture = mc.thePlayer.texture;
    		modelDataPlayerFormLittleMaid = playerData.get(mc.thePlayer);
    		break;
    	case skinMode_OthersSettingOffline:
    		if (mod_PFLM_PlayerFormLittleMaid.othersTextureName != null) {} else mod_PFLM_PlayerFormLittleMaid.othersTextureName = "default";
    		if (mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName != null) {} else mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName = "default";
    		entityplayer.skinUrl = null;
    		entityplayer.texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.othersTextureName, mod_PFLM_PlayerFormLittleMaid.othersMaidColor);
    		break;
    	case skinMode_PlayerOnline:
    		if (modelDataPlayerFormLittleMaid.modelMain != null
    			&& modelDataPlayerFormLittleMaid.modelMain.textureOuter != null) entityplayer.texture = modelDataPlayerFormLittleMaid.modelMain.textureOuter[0];
    		break;
    	case skinMode_PlayerLocalData:
    		String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.playerLocalData.get(entityplayer.username);
    		entityplayer.skinUrl = null;
    		if (modelDataPlayerFormLittleMaid.modelMain != null
        			&& modelDataPlayerFormLittleMaid.modelMain.textureOuter != null) entityplayer.texture = modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(t[0], Integer.valueOf(t[2]));
    		break;
    	}
    	return modelDataPlayerFormLittleMaid;
    }

	private static PFLM_ModelData loadPlayerData(EntityPlayer entityplayer)
	{
		PFLM_ModelData modelDataPlayerFormLittleMaid = new PFLM_ModelData((RenderLiving) RenderManager.instance.getEntityRenderObject(entityplayer));
		return loadPlayerData(entityplayer, modelDataPlayerFormLittleMaid);
	}

	private static PFLM_ModelData loadPlayerData(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid)
	{
		if (entityplayer == null) return null;
		if (modelDataPlayerFormLittleMaid == null) modelDataPlayerFormLittleMaid = new PFLM_ModelData((RenderLiving) RenderManager.instance.getEntityRenderObject(entityplayer));
		modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
//if (!modelDataPlayerFormLittleMaid.isPlayer) Modchu_Debug.mDebug("@@@@@isPlayer false!!");
		BufferedImage bufferedimage = null;
		// 125deleteif (!mod_PFLM_PlayerFormLittleMaid.gotchaNullCheck()) return null;

		if (!modelDataPlayerFormLittleMaid.isPlayer) {
			String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.playerLocalData.get(entityplayer.username);
			if (t != null) {
				switch (Integer.valueOf(t[4])) {
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayer:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_Player;
					modelDataPlayerFormLittleMaid.handedness = playerHandednessSetting();
					modelDataPlayerFormLittleMaid.modelScale = PFLM_Gui.modelScale;
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modeOthersSettingOffline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_OthersIndividualSettingOffline;
					String s2 = t[0];
					Modchu_Debug.mDebug("@@@@loadPlayerData modelName="+s2);
					modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(s2, Integer.valueOf(t[2]));
					modelInit(entityplayer, modelDataPlayerFormLittleMaid, s2);
					s2 = t[1];
					modelDataPlayerFormLittleMaid.modelArmorName = t[1];
					modelArmorInit(entityplayer, modelDataPlayerFormLittleMaid, s2);
					modelDataPlayerFormLittleMaid.handedness = othersPlayerIndividualHandednessSetting(Integer.valueOf(t[5]));
					modelDataPlayerFormLittleMaid.modelScale = Float.valueOf(t[3]);
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayerOffline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_PlayerOffline;
					skinMode_PlayerOfflineSetting(entityplayer, modelDataPlayerFormLittleMaid);
					modelDataPlayerFormLittleMaid.handedness = playerHandednessSetting();
					modelDataPlayerFormLittleMaid.modelScale = PFLM_Gui.modelScale;
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayerOnline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_PlayerOnline;
					modelDataPlayerFormLittleMaid.handedness = playerHandednessSetting();
					modelDataPlayerFormLittleMaid.modelScale = PFLM_Gui.modelScale;
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modeRandom:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_Random;
					skinMode_RandomSetting(entityplayer, modelDataPlayerFormLittleMaid);
					modelDataPlayerFormLittleMaid.handedness = othersPlayerIndividualHandednessSetting(Integer.valueOf(t[5]));
					modelDataPlayerFormLittleMaid.modelScale = Float.valueOf(t[3]);
					break;
				}
				if (modelDataPlayerFormLittleMaid.skinMode != skinMode_PlayerOnline
						&& modelDataPlayerFormLittleMaid.skinMode != skinMode_online) {
					modelDataPlayerFormLittleMaid.initFlag = 2;
					return modelDataPlayerFormLittleMaid;
				}
			} else
			if(PFLM_GuiOthersPlayer.changeMode > 0) {
				switch (PFLM_GuiOthersPlayer.changeMode) {
				case PFLM_GuiOthersPlayer.modePlayer:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_Player;
					modelDataPlayerFormLittleMaid.handedness = playerHandednessSetting();
					modelDataPlayerFormLittleMaid.modelScale = PFLM_Gui.modelScale;
					break;
				case PFLM_GuiOthersPlayer.modeOthersSettingOffline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_OthersSettingOffline;
					String s = mod_PFLM_PlayerFormLittleMaid.othersTextureName;
					modelInit(entityplayer, modelDataPlayerFormLittleMaid, s);
					s = mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName;
					modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName;
					modelArmorInit(entityplayer, modelDataPlayerFormLittleMaid, s);
					modelDataPlayerFormLittleMaid.handedness = othersPlayerHandednessSetting();
					modelDataPlayerFormLittleMaid.modelScale = mod_PFLM_PlayerFormLittleMaid.othersModelScale;
					Modchu_Debug.mDebug("modelDataPlayerFormLittleMaid.handedness="+modelDataPlayerFormLittleMaid.handedness);
					break;
				case PFLM_GuiOthersPlayer.modePlayerOffline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_PlayerOffline;
					skinMode_PlayerOfflineSetting(entityplayer, modelDataPlayerFormLittleMaid);
					modelDataPlayerFormLittleMaid.handedness = playerHandednessSetting();
					modelDataPlayerFormLittleMaid.modelScale = PFLM_Gui.modelScale;
					break;
				case PFLM_GuiOthersPlayer.modePlayerOnline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_PlayerOnline;
					modelDataPlayerFormLittleMaid.handedness = playerHandednessSetting();
					modelDataPlayerFormLittleMaid.modelScale = PFLM_Gui.modelScale;
					break;
				case PFLM_GuiOthersPlayer.modeRandom:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_Random;
					skinMode_RandomSetting(entityplayer, modelDataPlayerFormLittleMaid);
					modelDataPlayerFormLittleMaid.handedness = othersPlayerHandednessSetting();
					modelDataPlayerFormLittleMaid.modelScale = mod_PFLM_PlayerFormLittleMaid.othersModelScale;
					break;
				}
				if (modelDataPlayerFormLittleMaid.skinMode != skinMode_PlayerOnline
						&& modelDataPlayerFormLittleMaid.skinMode != skinMode_online) {
					modelDataPlayerFormLittleMaid.initFlag = 2;
					return modelDataPlayerFormLittleMaid;
				}
			}
		} else {
			modelDataPlayerFormLittleMaid.handedness = playerHandednessSetting();
			if (mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeRandom) {
				modelDataPlayerFormLittleMaid.skinMode = skinMode_Random;
				skinMode_RandomSetting(entityplayer, modelDataPlayerFormLittleMaid);
				modelDataPlayerFormLittleMaid.initFlag = 2;
				return modelDataPlayerFormLittleMaid;
			}
		}
		if (entityplayer.skinUrl == null) {
			if (mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOnline
				| !modelDataPlayerFormLittleMaid.isPlayer) {
				entityplayer.skinUrl = (new StringBuilder()).append("http://skins.minecraft.net/MinecraftSkins/").append(entityplayer.username).append(".png").toString();
			}
		}
		boolean er = false;
		try
		{
			if ((modelDataPlayerFormLittleMaid.isPlayer
					&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOnline)
					| (!modelDataPlayerFormLittleMaid.isPlayer
					&& modelDataPlayerFormLittleMaid.skinMode == skinMode_online)) {
				Modchu_Debug.Debug((new StringBuilder()).append("new model read username = ").append(entityplayer.username).toString());
				if (modelDataPlayerFormLittleMaid.skinMode == skinMode_PlayerOnline) {
					entityplayer.skinUrl = (new StringBuilder()).append("http://skins.minecraft.net/MinecraftSkins/").append(mc.thePlayer.username).append(".png").toString();
				}
				URL url = new URL(entityplayer.skinUrl);
				bufferedimage = ImageIO.read(url);
				String n = modelDataPlayerFormLittleMaid.skinMode == skinMode_PlayerOnline ? mc.thePlayer.username : entityplayer.username;
				if (modelDataPlayerFormLittleMaid.isPlayer
						&& !n.startsWith("Player")
						&& modelDataPlayerFormLittleMaid.initFlag == 0
						&& !initResetFlag) {
					modelDataPlayerFormLittleMaid.initFlag = 1;
					initResetFlag = true;
					resetFlag = true;
					return modelDataPlayerFormLittleMaid;
				}
				modelDataPlayerFormLittleMaid.initFlag = 2;
				Modchu_Debug.Debug("OnlineMode.image ok.");
			} else {
				//Modchu_Debug.Debug("er OnlineMode image.");
				er = true;
			}
		}
		catch (IOException ioexception)
		{
			String url;
			if (modelDataPlayerFormLittleMaid.skinMode != skinMode_PlayerOnline) {
				url = entityplayer.skinUrl;
			} else {
				url = mc.thePlayer.skinUrl;
			}
			Modchu_Debug.Debug((new StringBuilder()).append("Failed to read a player texture from a URL for ").append(url).toString());
			//Modchu_Debug.Debug(ioexception.getMessage());
			er = true;
		}
		modelDataPlayerFormLittleMaid.initFlag = 2;
		if (er) {
			//Modchu_Debug.mDebug("er entityplayer.skinUrl = "+entityplayer.skinUrl);
			if (mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOnline
					&& mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.isRelease()
					| !modelDataPlayerFormLittleMaid.isPlayer) {
				//Modchu_Debug.Debug("er /mob/char.png ");
				modelDataPlayerFormLittleMaid.skinMode = skinMode_char;
				modelDataPlayerFormLittleMaid.modelArmorName = "_Biped";
				((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isPlayer = ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isPlayer =
						((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
				return modelDataPlayerFormLittleMaid;
			} else {
				//Modchu_Debug.Debug("er offline only set.");
				modelDataPlayerFormLittleMaid.skinMode = skinMode_offline;
				mod_PFLM_PlayerFormLittleMaid.setMaidColor(mod_PFLM_PlayerFormLittleMaid.maidColor);
				mod_PFLM_PlayerFormLittleMaid.setTextureValue();
				modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
			}

			try
			{
				bufferedimage = ImageIO.read((net.minecraft.client.Minecraft.class).getResource(entityplayer.getTexture()));
			}
			catch (Exception e)
			{
				//Modchu_Debug.Debug((new StringBuilder()).append("Failed to read local player texture for ").append(entityplayer.getTexture()).toString());
				//Modchu_Debug.Debug(e.getMessage());
				bufferedimage = null;
			}
		} else {
			modelDataPlayerFormLittleMaid.skinMode = skinMode_online;
		}

		return checkSkin(entityplayer, bufferedimage, modelDataPlayerFormLittleMaid);
	}

	private static void modelInit(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid, String s) {
		Object[] models = mod_PFLM_PlayerFormLittleMaid.modelNewInstance(entityplayer, s, false);
		Modchu_Debug.mDebug("modelInit s="+s+" models[0] != null ? "+(models[0] != null));
		modelDataPlayerFormLittleMaid.modelMain.modelArmorInner = (MultiModelBaseBiped) (models[0] != null ? models[0] : new MultiModel(0.0F));
		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).armorType = 0;
	}

	private static void modelArmorInit(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid, String s) {
		boolean isBiped = MultiModel_Biped.class.isInstance(modelDataPlayerFormLittleMaid.modelMain.modelArmorInner);
		if (isBiped
				&& (s.equalsIgnoreCase("default")
						| s.equalsIgnoreCase("erasearmor"))) s = "Biped";
		Object[] models = mod_PFLM_PlayerFormLittleMaid.modelNewInstance(entityplayer, s, false);
		float[] f1 = mod_PFLM_PlayerFormLittleMaid.getArmorModelsSize(models[0]);
		Modchu_Debug.mDebug("modelArmorInit s="+s+" models[1] != null ? "+(models[1] != null));
		if (models != null
				&& models[1] != null) ;else {
					models = mod_PFLM_PlayerFormLittleMaid.modelNewInstance(isBiped ? "Biped" : null);
					f1 = mod_PFLM_PlayerFormLittleMaid.getArmorModelsSize(models[0]);
				}
		if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner = (MultiModelBaseBiped) (models[1] != null ?
					models[1] : !isBiped ? new MultiModelSmart(f1[0]) : new MultiModelSmart_Biped(f1[0]));
			modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter = (MultiModelBaseBiped) (models[2] != null ?
					models[2] : !isBiped ? new MultiModelSmart(f1[1]) : new MultiModelSmart_Biped(f1[1]));
		} else {
			modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner = (MultiModelBaseBiped) (models[1] != null ?
					models[1] : !isBiped ? new MultiModel(f1[0]) : new MultiModel_Biped(f1[0]));
			modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter = (MultiModelBaseBiped) (models[2] != null ?
					models[2] : !isBiped ? new MultiModel(f1[1]) : new MultiModel_Biped(f1[1]));
		}
		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).armorType = 1;
		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).armorType = 2;
	}

	private static int playerHandednessSetting() {
		return mod_PFLM_PlayerFormLittleMaid.handednessMode == -1 ? rnd.nextInt(2) : mod_PFLM_PlayerFormLittleMaid.handednessMode;
	}

	private static int othersPlayerHandednessSetting() {
		return mod_PFLM_PlayerFormLittleMaid.othersHandednessMode == -1 ? rnd.nextInt(2) : mod_PFLM_PlayerFormLittleMaid.othersHandednessMode;
	}

	private static int othersPlayerIndividualHandednessSetting(int i) {
		return i == -1 ? rnd.nextInt(2) : i;
	}

	private static PFLM_ModelData checkSkin(
			EntityPlayer entityplayer, BufferedImage bufferedimage
			,PFLM_ModelData modelDataPlayerFormLittleMaid)
	{
		if (modelDataPlayerFormLittleMaid.isPlayer
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
				| modelDataPlayerFormLittleMaid.skinMode == skinMode_offline) {
			//if (modelDataPlayerFormLittleMaid.modelMain.modelArmorInner != null) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner = null;
			//if (modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter != null) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter = null;
			modelInit(entityplayer, modelDataPlayerFormLittleMaid, mod_PFLM_PlayerFormLittleMaid.textureName);
			modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
			modelArmorInit(entityplayer, modelDataPlayerFormLittleMaid, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
			modelDataPlayerFormLittleMaid.skinMode = skinMode_offline;
			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isPlayer = ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isPlayer =
					((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
			return modelDataPlayerFormLittleMaid;
		}
		if (bufferedimage == null) {
			Modchu_Debug.Debug("bufferedimage == null");
			modelDataPlayerFormLittleMaid.skinMode = skinMode_char;
			modelDataPlayerFormLittleMaid.modelArmorName = "_Biped";
			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isPlayer = ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isPlayer =
					((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
			return modelDataPlayerFormLittleMaid;
		}
		modelDataPlayerFormLittleMaid.isActivated = true;
		Modchu_Debug.mDebug("modelDataPlayerFormLittleMaid.isPlayer="+modelDataPlayerFormLittleMaid.isPlayer);
		Object[] s = checkimage(bufferedimage);
		boolean localflag = (Boolean) s[0];
		modelDataPlayerFormLittleMaid.modelArmorName = (String) s[2];
		int maidcolor = (Integer) s[3];
		String texture = (String) s[1];
		String textureName = (String) s[4];
		boolean returnflag = (Boolean) s[5];
		int handedness = (Integer) s[6];
		float modelScale = (Float) s[7];

		if (returnflag) {
			if (modelDataPlayerFormLittleMaid.isPlayer) {
				mod_PFLM_PlayerFormLittleMaid.textureName = "Biped_Biped";
				modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.textureArmorName = "Biped_Biped";
			}
			modelDataPlayerFormLittleMaid.skinMode = skinMode_online;
			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isPlayer = ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isPlayer =
					((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
			return modelDataPlayerFormLittleMaid;
		}
		if (modelDataPlayerFormLittleMaid.isPlayer) {
			//Modchu_Debug.mDebug("modelDataPlayerFormLittleMaid.isPlayer set textureName="+textureName);
			mod_PFLM_PlayerFormLittleMaid.textureName = textureName;
			mod_PFLM_PlayerFormLittleMaid.maidColor = maidcolor;
			mod_PFLM_PlayerFormLittleMaid.textureArmorName = modelDataPlayerFormLittleMaid.modelArmorName;
		}
		Modchu_Debug.Debug((new StringBuilder()).append("checkSkin textureName = ").append(textureName).toString());
		if(localflag) {
			modelDataPlayerFormLittleMaid.localFlag = true;
			modelDataPlayerFormLittleMaid.skinMode = skinMode_local;
			if (texture != null)
			{
				Modchu_Debug.Debug((new StringBuilder()).append("localflag maidcolor = ").append(maidcolor).toString());
				entityplayer.texture = modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = texture;
				Modchu_Debug.Debug((new StringBuilder()).append("localflag texture = ").append(entityplayer.texture).toString());
				entityplayer.skinUrl = null;
			}
		} else {
			modelDataPlayerFormLittleMaid.localFlag = false;
			modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = null;
		}

		if (textureName != null) modelInit(entityplayer, modelDataPlayerFormLittleMaid, textureName);
		Modchu_Debug.Debug((new StringBuilder()).append("checkSkin Armor = ").append(s[2]).toString());
		if (modelDataPlayerFormLittleMaid.modelArmorName != null) modelArmorInit(entityplayer, modelDataPlayerFormLittleMaid, modelDataPlayerFormLittleMaid.modelArmorName);
		Modchu_Debug.Debug((new StringBuilder()).append("modelDataPlayerFormLittleMaid.textureName = ").append(textureName).toString());

		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isPlayer = ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).isPlayer =
				((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
		modelDataPlayerFormLittleMaid.handedness = handedness;
		if (modelDataPlayerFormLittleMaid.isPlayer) mod_PFLM_PlayerFormLittleMaid.handednessMode = handedness;
		modelDataPlayerFormLittleMaid.modelScale = modelScale;
		return modelDataPlayerFormLittleMaid;
    }

	public static Object[] checkimage(BufferedImage bufferedimage) {
		Object[] object = new Object[8];
		// 0 localflag
		object[0] = false;
		// 1 Texture
		object[1] = "";
		// 2 modelArmorName
		object[2] = "";
		// 3 maidcolor
		object[3] = 0;
		// 4 TextureName
		object[4] = "";
		// 5 return flag
		object[5] = false;
		// 6 handedness
		object[6] = 0;
		// 7 modelScale
		object[7] = 0.9375F;
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int checkX = 0;
		int checkY = 0;
		int[] c1;
		boolean checkPointUnder = false;
		do {
			checkX = 63;
			checkY = 0;
			c1 = checkImageColor(bufferedimage, checkX, checkY);
			r = c1[0];
			g = c1[1];
			b = c1[2];
			a = c1[3];

			checkY = 1;
			if (r != 255 | g != 0 | b != 0 | a != 255) {
				Modchu_Debug.Debug((new StringBuilder()).append("checkimage Out r255 g0 b0 a255.x=63,y=0 r=").append(r).append(" g=").append(g).append(" b=").append(b).append(" a=").append(a).toString());
				checkPointUnder = true;
				checkY = 31;
				c1 = checkImageColor(bufferedimage, checkX, checkY);
				r = c1[0];
				g = c1[1];
				b = c1[2];
				a = c1[3];
				if (r != 255 | g != 0 | b != 0 | a != 255) {
					Modchu_Debug.Debug((new StringBuilder()).append("checkimage Out r255 g0 b0 a255.x=").append(checkX).append(",y=").append(checkY).append(" r=").append(r).append(" g=").append(g).append(" b=").append(b).append(" a=").append(a).toString());
					object[5] = true;
					break;
				}
				checkY = 30;
			}

			c1 = checkImageColor(bufferedimage, checkX, checkY);
			r = c1[0];
			g = c1[1];
			b = c1[2];
			a = c1[3];

			object[0] = false;
			if (r != 255 | g != 255 | b != 0 | a != 255) {
				if (r != 255 | g != 0 | b != 255 | a != 255) {
					Modchu_Debug.Debug((new StringBuilder()).append("checkimage Out r255 g0 b255 a255.x = 63,y = 1 r = ").append(r).append(" g = ").append(g).append(" b = ").append(b).append(" a = ").append(a).toString());
					object[5] = true;
					break;
				} else {
					Modchu_Debug.Debug("checkimage localflag = true");
					object[0] = true;
				}
			}

			checkX = 62;
			checkY = checkPointUnder ? 31 : 0;
			c1 = checkImageColor(bufferedimage, checkX, checkY);
			r = 255 - c1[0];
			g = 255 - c1[1];
			b = 255 - c1[2];
			a = 255 - c1[3];
			break;
		} while (true);
		if (!(Boolean) object[5]) {
			if (g < mod_PFLM_PlayerFormLittleMaid.textureList.size()) {
				object[2] = mod_PFLM_PlayerFormLittleMaid.textureList.get(g);
				Modchu_Debug.mDebug("object[2]="+object[2]);
			}
			object[3] = r;
			if (b < mod_PFLM_PlayerFormLittleMaid.textureList.size()) {
				object[4] = mod_PFLM_PlayerFormLittleMaid.textureList.get(b);
				object[1] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(
						mod_PFLM_PlayerFormLittleMaid.textureList.get(b), r);
			}
		}
		checkX = 62;
		checkY = checkPointUnder ? 30 : 1;
		c1 = checkImageColor(bufferedimage, checkX, checkY);
		object[6] = c1[0] == 255 ? 0 : c1[0] == 0 ? 1 : -1;
		object[7] = (float)c1[1] * (0.9375F / 24F);
		b = c1[2];
		a = c1[3];
		return object;
	}

	public static int[] checkImageColor(BufferedImage bufferedimage, int i, int j)
	{
		Color color = new Color(bufferedimage.getRGB(i, j), true);
		int[] i1 = new int[4];
		i1[0] = color.getRed();
		i1[1] = color.getGreen();
		i1[2] = color.getBlue();
		i1[3] = color.getAlpha();
		return i1;
	}

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(EntityLiving entityliving, double d, double d1, double d2)
    {
    	EntityPlayer entityplayer = (EntityPlayer)entityliving;
    	byte byte0 = entityplayer.getDataWatcher().getWatchableObjectByte(16);
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) {} else return;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	if (entityliving.isEntityAlive() && byte0 == 2)
    	{
    		/*b173//*/byte byte1 = entityplayer.getDataWatcher().getWatchableObjectByte(17);
/*//b173delete
            byte byte1 = 0;
//-@-b166
        	if (mod_PFLM_PlayerFormLittleMaid.isPlayerAPI) {
        		if (entityliving instanceof PFLM_EntityPlayer) {
        			byte1 = (byte)((PFLM_EntityPlayer) entityliving).getSleepMotion();
        		}
        	} else {
//@-@b166
        		if (entityliving instanceof PFLM_EntityPlayerSP) {
        			byte1 = (byte)((PFLM_EntityPlayerSP) entityliving).getSleepMotion();
        		}
//-@-b166
    		}
//@-@b166
// b173delete*/// b173delete
    		float f = 0.0F;
    		float f1 = 0.0F;

    		switch (byte1)
    		{
    		case 0:
    		case 4:
    			f1 = 1.0F * (1.62F - modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.getyOffset());
    			break;

    		case 2:
    			f1 = -1F * (1.62F - modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.getyOffset());
    			break;

    		case 1:
    			f = -1F * (1.62F - modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.getyOffset());
    			break;

    		case 3:
    			f = 1.0F * (1.62F - modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.getyOffset());
    			break;
    		}

    		super.renderLivingAt(entityliving, d + (double)f, d1 + 1.0D, d2 + (double)f1);
    	}
    	else
    	{
    		super.renderLivingAt(entityliving, d, d1, d2);
    	}
    }

    protected void rotateCorpse(EntityLiving entityliving, float f, float f1, float f2)
    {
    	EntityPlayer entityplayer = (EntityPlayer)entityliving;
    	byte byte0 = entityplayer.getDataWatcher().getWatchableObjectByte(16);

    	if (entityplayer.isEntityAlive() && byte0 == 2)
    	{
    		/*b173//*/byte byte1 = entityplayer.getDataWatcher().getWatchableObjectByte(17);
/*//b173delete
            byte byte1 = 0;
//-@-b166
        	if (mod_PFLM_PlayerFormLittleMaid.isPlayerAPI) {
        		if (entityplayer instanceof PFLM_EntityPlayer) {
        			byte1 = (byte)((PFLM_EntityPlayer) entityplayer).getSleepMotion();
        		}
        	} else {
//@-@b166
        		if (entityplayer instanceof PFLM_EntityPlayerSP) {
        			byte1 = (byte)((PFLM_EntityPlayerSP) entityplayer).getSleepMotion();
        		}
//-@-b166
    		}
//@-@b166
// b173delete*/// b173delete
    		int i = 0;

    		switch (byte1)
    		{
    		case 0:
    		case 4:
    			i = 270;
    			break;

    		case 2:
    			i = 90;
    			break;

    		case 1:
    			i = 180;
    			break;

    		case 3:
    			i = 0;
    			break;
    		}

    		GL11.glRotatef(i, 0.0F, 1.0F, 0.0F);
    		GL11.glRotatef(getDeathMaxRotation(entityplayer), 0.0F, 0.0F, 1.0F);
    		GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
    	}
    	else
    	{
    		super.rotateCorpse(entityliving, f, f1, f2);
    	}
    }

    /**
     * Used to render a player's name above their head
     */
    protected void renderName(EntityPlayer entityplayer, double d, double d1, double d2)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) Modchu_Reflect.invokeMethod(PFLM_RenderPlayerSmart, "renderName", new Class[]{ EntityPlayer.class, double.class, double.class, double.class }, pflm_RenderPlayerSmart, new Object[]{ entityplayer, d, d1, d2 });
    	if(mod_PFLM_PlayerFormLittleMaid.isRenderName) {
    		PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    		if (modelDataPlayerFormLittleMaid == null) return;
    		double d3 = 0.0D;
    		double d4 = 0.0D;
    		double d5 = 0.0D;
    		double height = (double)modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.getHeight();
    		if(entityplayer.isSneaking()) d3 = 0.4D;
    		if (modelDataPlayerFormLittleMaid.modelScale > 0.0F) {
    			d5 = (double)(0.9375F - modelDataPlayerFormLittleMaid.modelScale);
    			d4 = -height * d5;
    			if (modelDataPlayerFormLittleMaid.modelScale > 0.9375F) d4 -= 0.4D * d5;
    		}
    		super.renderName(entityplayer, d, (d1 - 1.8D) + height + d3 + d4, d2);
    	}
    }

    public static void clearPlayers()
    {
    	playerData.clear();
    	PFLM_Gui.showModelFlag = true;
    	mod_PFLM_PlayerFormLittleMaid.setShortcutKeysActionInitFlag(false);
    }

    public void waitModeSetting(PFLM_ModelData modelDataPlayerFormLittleMaid, float f) {
    	if (!((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).firstPerson) {
    		if(mod_PFLM_PlayerFormLittleMaid.waitTime == 0
    				&& ((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isPlayer) {
    			if(mod_PFLM_PlayerFormLittleMaid.isWait) {
    				if (((f != modelDataPlayerFormLittleMaid.isWaitF && modelDataPlayerFormLittleMaid.isWaitFSetFlag)
    						| modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.onGround > 0)
    						| (modelDataPlayerFormLittleMaid.isWait && modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isSneak)) {
    					modelDataPlayerFormLittleMaid.isWait = false;
    					mod_PFLM_PlayerFormLittleMaid.isWait = false;
    					modelDataPlayerFormLittleMaid.isWaitTime = 0;
    					modelDataPlayerFormLittleMaid.isWaitF = copyf(f);
    					modelDataPlayerFormLittleMaid.isWaitFSetFlag = true;
    				} else {
    					if ((f != modelDataPlayerFormLittleMaid.isWaitF
    							| modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.onGround > 0)
    							| (modelDataPlayerFormLittleMaid.isWait && modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isSneak)) {
    						//Modchu_Debug.mDebug("f != isWaitF");
    						modelDataPlayerFormLittleMaid.isWait = false;
    						mod_PFLM_PlayerFormLittleMaid.isWait = false;
    						modelDataPlayerFormLittleMaid.isWaitTime = 0;
    						modelDataPlayerFormLittleMaid.isWaitF = copyf(f);
    					}
    					if (!modelDataPlayerFormLittleMaid.isWaitFSetFlag) {
    						modelDataPlayerFormLittleMaid.isWaitF = copyf(f);
    						modelDataPlayerFormLittleMaid.isWaitFSetFlag = true;
    					}
    				}
    			}
    		} else {
    			int i = modelDataPlayerFormLittleMaid.isPlayer ? mod_PFLM_PlayerFormLittleMaid.waitTime : mod_PFLM_PlayerFormLittleMaid.othersPlayerWaitTime;
    			if (!modelDataPlayerFormLittleMaid.isWait
    					&& !modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isSneak
    					&& i > 0) {
    				modelDataPlayerFormLittleMaid.isWaitTime++;
    				if(modelDataPlayerFormLittleMaid.isWaitTime > i) {
    					//Modchu_Debug.Debug("isWaitTime > i");
    					modelDataPlayerFormLittleMaid.isWait = true;
    					if (modelDataPlayerFormLittleMaid.isPlayer) mod_PFLM_PlayerFormLittleMaid.isWait = true;
    					modelDataPlayerFormLittleMaid.isWaitTime = 0;
    					modelDataPlayerFormLittleMaid.isWaitF = copyf(f);
    				}
    			}
    			if ((f != modelDataPlayerFormLittleMaid.isWaitF
    					| modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.onGround > 0)
    					| (modelDataPlayerFormLittleMaid.isWait && modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.isSneak)) {
    				//Modchu_Debug.Debug("f != isWaitF");
    				modelDataPlayerFormLittleMaid.isWait = false;
    				if (modelDataPlayerFormLittleMaid.isPlayer) mod_PFLM_PlayerFormLittleMaid.isWait = false;
    				modelDataPlayerFormLittleMaid.isWaitTime = 0;
    				modelDataPlayerFormLittleMaid.isWaitF = copyf(f);
    			}
    		}
    	} else {
    		if (modelDataPlayerFormLittleMaid.isPlayer) {
    			if (modelDataPlayerFormLittleMaid.isWait) {
    				//Modchu_Debug.Debug("firstPerson isWait false");
    				modelDataPlayerFormLittleMaid.isWait = false;
    				if (modelDataPlayerFormLittleMaid.isPlayer) mod_PFLM_PlayerFormLittleMaid.isWait = false;
    				modelDataPlayerFormLittleMaid.isWaitTime = 0;
    				modelDataPlayerFormLittleMaid.isWaitF = copyf(f);
    				if(mod_PFLM_PlayerFormLittleMaid.waitTime == 0) modelDataPlayerFormLittleMaid.isWaitFSetFlag = true;
    			} else {
    				if(mod_PFLM_PlayerFormLittleMaid.waitTime == 0
    						&& !modelDataPlayerFormLittleMaid.isWaitFSetFlag) {
    					modelDataPlayerFormLittleMaid.isWaitF = copyf(f);
    					modelDataPlayerFormLittleMaid.isWaitFSetFlag = true;
    				}
    			}
    		}
    	}
    }

    public float copyf(float f) {
    	return f;
    }

    private static void skinMode_PlayerOfflineSetting(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid) {
    	if (mod_PFLM_PlayerFormLittleMaid.textureName == null
    			| mod_PFLM_PlayerFormLittleMaid.textureName.isEmpty()) mod_PFLM_PlayerFormLittleMaid.textureName = "Biped_Biped";
    	if (mod_PFLM_PlayerFormLittleMaid.textureArmorName == null
    			| mod_PFLM_PlayerFormLittleMaid.textureArmorName.isEmpty()) mod_PFLM_PlayerFormLittleMaid.textureArmorName = "Biped_Biped";
    	modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, mod_PFLM_PlayerFormLittleMaid.maidColor);
    	String s1 = mod_PFLM_PlayerFormLittleMaid.textureName;
    	modelInit(entityplayer, modelDataPlayerFormLittleMaid, s1);
    	s1 = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
    	modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
    	modelArmorInit(entityplayer, modelDataPlayerFormLittleMaid, s1);
    }

    private static void skinMode_RandomSetting(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid) {
    	String s3 = null;
    	String s4 = null;
    	for(int i1 = 0; s4 == null && i1 < 50; i1++) {
    		int i = rnd.nextInt(16);
    		int j = rnd.nextInt(mod_PFLM_PlayerFormLittleMaid.textureManagerTexturesSize());
    		s3 = mod_PFLM_PlayerFormLittleMaid.getPackege(i, j);
    		mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(s3, i);
    		s4 = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(s3, i);
    		if (i1 == 49) return;
    	}
    	modelDataPlayerFormLittleMaid.modelMain.textureOuter[0] = s4;
    	Modchu_Debug.Debug("Random modelPackege="+s3);
    	//Modchu_Debug.mDebug("Random s4="+s4);
    	String s1 = mod_PFLM_PlayerFormLittleMaid.getArmorName(s3);
    	modelDataPlayerFormLittleMaid.modelArmorName = s1;
    	modelInit(entityplayer, modelDataPlayerFormLittleMaid, s3);
    	modelArmorInit(entityplayer, modelDataPlayerFormLittleMaid, s1);
    	if (modelDataPlayerFormLittleMaid.isPlayer) {
    		if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
    			mod_PFLM_PlayerFormLittleMaid.setSize(0.6F, 1.8F);
    			mod_PFLM_PlayerFormLittleMaid.resetHeight();
    			mod_PFLM_PlayerFormLittleMaid.setPositionCorrection(0.0D ,0.5D ,0.0D);
    		}
    	}
    }

    public static void setHandedness(EntityPlayer entityplayer, int i) {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (i == -1) i = rnd.nextInt(2);
    	((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).handedness =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorInner).handedness =
    			((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelFATT.modelArmorOuter).handedness = modelDataPlayerFormLittleMaid.handedness = i;
    	if (modelDataPlayerFormLittleMaid.isPlayer) {
    		mod_PFLM_PlayerFormLittleMaid.setFlipHorizontal(i == 0 ? false : true);
    		mod_PFLM_PlayerFormLittleMaid.setLeftHandedness(i == 0 ? false : true);
    	}
    }

    protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData((EntityPlayer) par1EntityLiving);
//-@-b132
    	if (par1EntityLiving.getHasActivePotion()
    			&& mod_Modchu_ModchuLib.useInvisibilityBody) {
    		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isRendering = false;
    	} else {
//@-@132
    		loadDownloadableImageTexture(par1EntityLiving.skinUrl, par1EntityLiving.getTexture());
    		((MultiModelBaseBiped) modelDataPlayerFormLittleMaid.modelMain.modelArmorInner).isRendering = true;
//-@-b132
    	}
//@-@132
    	modelDataPlayerFormLittleMaid.modelMain.modelArmorInner.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }

    public float getActionSpeed() {
    	float f = (float)(mc.theWorld.getWorldTime() - actionTime);
    	actionTime = (int) mc.theWorld.getWorldTime();
    	return f;
    }

    public static MultiModelBaseBiped[] getModelBasicOrig() {
    	return modelBasicOrig;
    }
    //smartMoving関連↓
    public RenderManager getRenderManager() {
    	return renderManager;
    }

    @Override
    protected void renderPlayerSleep(EntityPlayer var1, double var2, double var4, double var6)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) renderPlayerAt(var1, var2, var4, var6);
    	else super.renderPlayerSleep(var1, var2, var4, var6);
    }

    @Override
    public void rotatePlayer(EntityPlayer var1, float var2, float var3, float var4)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) Modchu_Reflect.invokeMethod(PFLM_RenderPlayerSmart, "rotatePlayer", new Class[]{ EntityPlayer.class, float.class, float.class, float.class }, pflm_RenderPlayerSmart, new Object[]{ var1, var2, var3, var4 });
    	super.rotatePlayer(var1, var2, var3, var4);
    }

    public void renderPlayerAt(EntityPlayer var1, double var2, double var4, double var6)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) Modchu_Reflect.invokeMethod(PFLM_RenderPlayerSmart, "renderPlayerAt", new Class[]{ EntityPlayer.class, double.class, double.class, double.class }, pflm_RenderPlayerSmart, new Object[]{ var1, var2, var4, var6 });
    }

    public static void renderGuiIngame(Minecraft var0)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) Modchu_Reflect.invokeMethod(PFLM_RenderPlayerSmart, "renderGuiIngame", new Class[]{ Minecraft.class }, pflm_RenderPlayerSmart, new Object[]{ var0 });
    }
    //smartMoving関連↑
}
