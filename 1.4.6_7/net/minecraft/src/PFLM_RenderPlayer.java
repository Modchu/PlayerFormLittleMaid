package net.minecraft.src;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.io.IOException;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayer extends RenderPlayer
{
	public static HashMap playerData = new HashMap();
	protected ModelBiped modelBipedMain;
	protected static ModelPlayerFormLittleMaidBaseBiped modelBasicOrig[];
	protected ModelPlayerFormLittleMaidBaseBiped modelMain;
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
	private boolean checkGlEnableWrapper = true;
	private boolean checkGlDisableWrapper = true;
    // b173deleteprivate RenderBlocks renderBlocks;

	public PFLM_RenderPlayer() {
		modelBasicOrig = new ModelPlayerFormLittleMaid[3];
		modelBasicOrig[0] = new ModelPlayerFormLittleMaid(0.0F);
		modelBasicOrig[1] = new ModelPlayerFormLittleMaid(0.5F);
		modelBasicOrig[2] = new ModelPlayerFormLittleMaid(0.1F);
		armorFilenamePrefix = (String[]) Modchu_Reflect.getFieldObject(RenderPlayer.class, "armorFilenamePrefix");
		// b173deleterenderBlocks = new RenderBlocks();
	}

    protected int setArmorModel(EntityPlayer entityplayer, int i, float f)
    {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) {} else return -1;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return -1;
    	/*b181//*/byte byte0 = -1;
    	//b181 deleteboolean byte0 = false;
    	int i2 = 3 - i;
    	ItemStack itemstack = entityplayer.inventory.armorItemInSlot(i2);
    	String t = modelDataPlayerFormLittleMaid.modelArmorName;
    	//Modchu_Debug.mDebug("setArmorModel t="+t);
    	if (t != null) {
    		if (modelDataPlayerFormLittleMaid.modeltype != null) {
    			if (modelDataPlayerFormLittleMaid.modeltype.indexOf("Biped") > -1
    					| modelDataPlayerFormLittleMaid.modelArmorName.indexOf("_Biped") > -1) {
    				t = "Biped";
    			}
    		}
    	} else {
    		t = "default";
    	}
    	int i1 = t.lastIndexOf('_');
    	String s = t;
    	if (modelDataPlayerFormLittleMaid.modelArmorChestplate != null) {
    	} else {
    		Object amodelPlayerFormLittleMaid[];
    		amodelPlayerFormLittleMaid = modelDataPlayerFormLittleMaid.isPlayer ?
    				(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.modelMap.get(s) :
    					(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.multiModelGet(s);
    		if (amodelPlayerFormLittleMaid != null)
    		{
    			modelDataPlayerFormLittleMaid.modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped) amodelPlayerFormLittleMaid[1];
    		} else {
    			modelDataPlayerFormLittleMaid.modelArmorChestplate = modelDataPlayerFormLittleMaid.isPlayer ? modelBasicOrig[1] : t.equalsIgnoreCase("Biped") ? new ModelPlayerFormLittleMaid_Biped(1.0F) : new ModelPlayerFormLittleMaid(0.5F);
    		}
    	}
    	if (modelDataPlayerFormLittleMaid.modelArmor != null) {
    	} else {
    		Object amodelPlayerFormLittleMaid[];
    		amodelPlayerFormLittleMaid = modelDataPlayerFormLittleMaid.isPlayer ?
    				(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.modelMap.get(s) :
    					(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.multiModelGet(s);
    		if (amodelPlayerFormLittleMaid != null)
    		{
    			modelDataPlayerFormLittleMaid.modelArmor = (ModelPlayerFormLittleMaidBaseBiped) amodelPlayerFormLittleMaid[2];
    		} else {
    			modelDataPlayerFormLittleMaid.modelArmor = modelDataPlayerFormLittleMaid.isPlayer ? modelBasicOrig[2] : t.equalsIgnoreCase("Biped") ? new ModelPlayerFormLittleMaid_Biped(0.5F) : new ModelPlayerFormLittleMaid(0.1F);
    		}
    	}
    	boolean isBiped = ModelPlayerFormLittleMaid_Biped.class.isInstance(modelDataPlayerFormLittleMaid.modelArmorChestplate);
    	if (isBiped) {
    	} else {
    		modelDataPlayerFormLittleMaid.textureArmor0[i] = PFLM_Texture.getArmorTextureName(t, 64, itemstack);
    		modelDataPlayerFormLittleMaid.textureArmor1[i] = PFLM_Texture.getArmorTextureName(t, 80, itemstack);
    	}
    	boolean flag = false;
    	ItemArmor itemarmor = null;
    	if(itemstack != null)
    	{
    		Item item = itemstack.getItem();
    		if(item instanceof ItemArmor)
    		{
    			itemarmor = (ItemArmor)item;
    			flag = itemarmor != null && itemstack.stackSize > 0;
    		}
    	}
    	try
    	{
    		if (isBiped) {
    			if(itemarmor != null) {
    				modelDataPlayerFormLittleMaid.textureArmor0[i] = "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + 2 + ".png";
    				modelDataPlayerFormLittleMaid.textureArmor1[i] = "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + 1 + ".png";
    			}
    		}
    		ModelPlayerFormLittleMaidBaseBiped var7 = modelDataPlayerFormLittleMaid.modelArmor;
    		if(flag)
    		{
    			//-@-132
    			if (var7 != null)
    			{
    				var7.onGround = this.mainModel.onGround;
    			}
    			if (var7 != null)
    			{
    				var7.isRiding = this.mainModel.isRiding;
    			}
    			if (var7 != null)
    			{
    				var7.isChild = this.mainModel.isChild;
    			}
    			float var13 = 1.0F;
    			if (itemarmor.getArmorMaterial() == EnumArmorMaterial.CLOTH)
    			{
    				int var9 = itemarmor.getColor(itemstack);
    				float var10 = (float)(var9 >> 16 & 255) / 255.0F;
    				float var11 = (float)(var9 >> 8 & 255) / 255.0F;
    				float var12 = (float)(var9 & 255) / 255.0F;
    				GL11.glColor3f(var13 * var10, var13 * var11, var13 * var12);

    				if (itemstack.isItemEnchanted())
    				{
    					byte0 = 31;
    				} else byte0 = 16;
    			} else GL11.glColor3f(var13, var13, var13);
//@-@132
    			/*132//*/if (byte0 != 31 && byte0 != 16)
    				/*b173//*/byte0 = ((byte)(itemstack.isItemEnchanted() ? 15 : 1));
    			// b173deletebyte0 = true;
    		}
    		ModelPlayerFormLittleMaidBaseBiped var8 = modelDataPlayerFormLittleMaid.modelArmorChestplate;
    		var7.settingReflects(0);
    		var8.settingReflects(0);
    		var7.showArmorParts(3 - i);
    		var8.showArmorParts(3 - i);
    		boolean flag1 = i == 2 ? flag : false;
    		if (isBiped) {
    			var7.setArmorBipedRightLegShowModel(flag1);
    			var7.setArmorBipedLeftLegShowModel(flag1);
    		} else {
    			var8.setArmorBipedRightLegShowModel(flag1);
    			var8.setArmorBipedLeftLegShowModel(flag1);
    		}
    	}
    	catch(Exception exception)
    	{
    		//Modchu_Debug.mDebug("error armorFilenamePrefix="+armorFilenamePrefix);
    	}
    	return byte0;
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
    	if (i < 4 && modelDataPlayerFormLittleMaid.modelArmor != null)
    	{
    		/*b173//*/if (modelDataPlayerFormLittleMaid.textureArmor0 == null) return -1;
    		// b173deleteif (modelDataPlayerFormLittleMaid.textureArmor0 == null) return false;
    		t = modelDataPlayerFormLittleMaid.textureArmor0[i];
    		if (t == null)
    		{
    			//Modchu_Debug.Debug("Armor0 return -1");
    			/*b181//*/return -1;
    			//b181 deletereturn false;
    		} else {
    			loadTexture(t);
    			setRenderPassModel(modelDataPlayerFormLittleMaid.modelArmor);
    			//Modchu_Debug.Debug("Armor0 ok t="+t);
    			/*b181//*/return 1;
    			//b181 deletereturn true;
    		}
    	}
    	if (i < 8 && modelDataPlayerFormLittleMaid.modelArmorChestplate != null)
    	{
    		/*b173//*/if (modelDataPlayerFormLittleMaid.textureArmor1 == null) return -1;
    		// b173deleteif (modelDataPlayerFormLittleMaid.textureArmor1 == null) return false;
    		t = modelDataPlayerFormLittleMaid.textureArmor1[i - 4];
    		if (t == null) {
    			//Modchu_Debug.Debug("Armor1 return -1");
    			/*b181//*/return -1;
    			//b181 deletereturn false;
    		} else {
    			loadTexture(t);
    			setRenderPassModel(modelDataPlayerFormLittleMaid.modelArmorChestplate);
    			//Modchu_Debug.Debug("Armor1 ok t="+t);
    			/*b181//*/return 1;
    			//b181 deletereturn true;
    		}
    	} else {
			/*b181//*/return -1;
			//b181 deletereturn false;
    	}
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData((EntityPlayer)entityliving);
    	if (modelDataPlayerFormLittleMaid != null) {} else return;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	float f1 = modelDataPlayerFormLittleMaid.isPlayer ? PFLM_Gui.modelScale : mod_PFLM_PlayerFormLittleMaid.othersModelScale;
    	if (modelDataPlayerFormLittleMaid.skinMode == skinMode_PlayerLocalData) {
    		String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.playerLocalData.get(((EntityPlayer)entityliving).username);
    		f1 = Float.valueOf(t[3]);
    	}
    	if (f1 == 0.0F) {
    		f1 = modelDataPlayerFormLittleMaid.mainModel.getModelScale();
    	}
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

    public void doRenderLivingPFLM(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1)
    {
    	GL11.glPushMatrix();
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    		//Shaders.glDisableWrapper(k1);
    		shadersGlDisableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glDisable(GL11.GL_CULL_FACE);
    	}

    	this.mainModel.onGround = this.renderSwingProgress(entityliving, f1);
    	if (this.renderPassModel != null)
    	{
    		this.renderPassModel.onGround = this.mainModel.onGround;
    	}
    	this.mainModel.isRiding = entityliving.isRiding();
    	if (this.renderPassModel != null)
    	{
    		this.renderPassModel.isRiding = this.mainModel.isRiding;
    	}
    	this.mainModel.isChild = entityliving.isChild();
    	if (this.renderPassModel != null)
    	{
    		this.renderPassModel.isChild = this.mainModel.isChild;
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

    		modelMain.setLivingAnimations(entityliving, f8, f7, f1);
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
    				if (mod_PFLM_PlayerFormLittleMaid.useInvisibilityArmor) {
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
    					if (mod_PFLM_PlayerFormLittleMaid.useInvisibilityArmor) {
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
    		/*132//*/if (entityliving.getHasActivePotion()) mainModel.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6, entityliving);
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
    				mainModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);

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
    				mainModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);

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

    public void doRenderPlayerFormLittleMaid(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1)
    {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) {} else return;
    	if (modelDataPlayerFormLittleMaid.changeModelFlag) {
    		modelDataPlayerFormLittleMaid.mainModel.changeModel(entityplayer);
    		setHandedness(entityplayer, modelDataPlayerFormLittleMaid.handedness);
    		modelDataPlayerFormLittleMaid.changeModelFlag = false;
    	}
    	byte byte0 = entityplayer.getDataWatcher().getWatchableObjectByte(16);
    	modelDataPlayerFormLittleMaid.modelArmor.isSneak = modelDataPlayerFormLittleMaid.modelArmorChestplate.isSneak = modelDataPlayerFormLittleMaid.mainModel.isSneak = entityplayer.isSneaking();
    	modelDataPlayerFormLittleMaid.modelArmor.isRiding = modelDataPlayerFormLittleMaid.modelArmorChestplate.isRiding = modelDataPlayerFormLittleMaid.mainModel.isRiding = entityplayer.isRiding();
    	modelDataPlayerFormLittleMaid.modelArmor.isSitting = modelDataPlayerFormLittleMaid.modelArmorChestplate.isSitting = modelDataPlayerFormLittleMaid.mainModel.isSitting = byte0 == 1;
    	modelDataPlayerFormLittleMaid.modelArmor.isPlayer = modelDataPlayerFormLittleMaid.modelArmorChestplate.isPlayer = modelDataPlayerFormLittleMaid.mainModel.isPlayer = modelDataPlayerFormLittleMaid.isPlayer;
    	float f8 = entityplayer.legSwing - entityplayer.legYaw * (1.0F - f1);
    	waitModeSetting(modelDataPlayerFormLittleMaid, f8);
    	if (modelDataPlayerFormLittleMaid.isPlayer) {
    		modelDataPlayerFormLittleMaid.modelArmor.isWait = modelDataPlayerFormLittleMaid.modelArmorChestplate.isWait = modelDataPlayerFormLittleMaid.mainModel.isWait = mod_PFLM_PlayerFormLittleMaid.isWait;
    	} else {
    		modelDataPlayerFormLittleMaid.modelArmorChestplate.isWait = modelDataPlayerFormLittleMaid.modelArmor.isWait = modelDataPlayerFormLittleMaid.mainModel.isWait = modelDataPlayerFormLittleMaid.isWait;
    	}
    	modelDataPlayerFormLittleMaid.modelArmor.isSleeping = modelDataPlayerFormLittleMaid.modelArmorChestplate.isSleeping = modelDataPlayerFormLittleMaid.mainModel.isSleeping = entityplayer.isPlayerSleeping() | byte0 == 2;
    	mainModel = modelDataPlayerFormLittleMaid.mainModel;
    	modelMain = (ModelPlayerFormLittleMaidBaseBiped) mainModel;
    	modelDataPlayerFormLittleMaid.modelArmor.onGround = modelDataPlayerFormLittleMaid.modelArmorChestplate.onGround = modelDataPlayerFormLittleMaid.mainModel.onGround = renderSwingProgress((EntityLiving) entityplayer, f1);
    	if (renderPassModel != null)
    	{
    		renderPassModel.onGround = mainModel.onGround;
    	}
    	modelMain.isInventory =
    			modelDataPlayerFormLittleMaid.modelArmor.isInventory =
    			modelDataPlayerFormLittleMaid.modelArmorChestplate.isInventory =
    			d == 0.0D && d1 == 0.0D && d2 == 0.0D && f == 0.0F && f1 == 1.0F;
    	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    	if (modelMain.isItemHolder()) {
    		modelDataPlayerFormLittleMaid.modelArmorChestplate.heldItemRight = modelDataPlayerFormLittleMaid.modelArmor.heldItemRight = modelDataPlayerFormLittleMaid.mainModel.heldItemRight = itemstack != null ? 1 : 0;
    	}
//-@-b173
    	if (itemstack != null && entityplayer.getItemInUseCount() > 0)
    	{
    		EnumAction enumaction = itemstack.getItemUseAction();
    		if (enumaction == EnumAction.block)
    		{
    			if (modelMain.isItemHolder()) {
    				modelDataPlayerFormLittleMaid.modelArmorChestplate.heldItemRight = modelDataPlayerFormLittleMaid.modelArmor.heldItemRight = modelMain.heldItemRight = 3;
    			}
    		}
    		else if (enumaction == EnumAction.bow)
    		{
    			modelDataPlayerFormLittleMaid.modelArmorChestplate.aimedBow = modelDataPlayerFormLittleMaid.modelArmor.aimedBow = modelMain.aimedBow = true;
    		}
    	}
//@-@b173
    	double d3;

    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize
    			&& !mod_PFLM_PlayerFormLittleMaid.onlineMode
    			&& !entityplayer.isRiding()) {
    		//d3 = d1 - (double)modelDataPlayerFormLittleMaid.mainModel.getyOffset();
    		d3 = d1 - (double)entityplayer.yOffset;
    	}
    	else d3 = d1 - (double)entityplayer.yOffset;
    	if (entityplayer.isSneaking() && !(entityplayer instanceof EntityPlayerSP))
    	{
    		d3 -= 0.125D;
    	}

    	//if (mod_PFLM_PlayerFormLittleMaid.isModelSize) d3 += 0.45D;

    	if (entityplayer.isRiding()) {
    		d3 += 0.35D;
    		if (mod_PFLM_PlayerFormLittleMaid.isModelSize && !mod_PFLM_PlayerFormLittleMaid.onlineMode)  d3 -= 0.43D;
    	}
    	if (entityplayer.isSneaking()) {
    		if (entityplayer.isRiding()) {
    			d3 -= 0.1D;
    		}
    	}
    	if (byte0 == 1) {
    		d3 += modelDataPlayerFormLittleMaid.mainModel.getSittingyOffset();
    	}
    	if (byte0 == 2) {
    		d3 += modelDataPlayerFormLittleMaid.mainModel.getSleepingyOffset();
    	}
    	if (modelDataPlayerFormLittleMaid.shortcutKeysAction) {
    		if (modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag) {
    			modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag = false;
    			modelDataPlayerFormLittleMaid.mainModel.actionInit(modelDataPlayerFormLittleMaid.runActionNumber);
    			modelDataPlayerFormLittleMaid.modelArmorChestplate.actionInit(modelDataPlayerFormLittleMaid.runActionNumber);
    			modelDataPlayerFormLittleMaid.modelArmor.actionInit(modelDataPlayerFormLittleMaid.runActionNumber);
    		}
    	} else {
    		if (!modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag) {
    			modelDataPlayerFormLittleMaid.shortcutKeysActionInitFlag = true;
    			modelDataPlayerFormLittleMaid.mainModel.actionRelease(modelDataPlayerFormLittleMaid.runActionNumber);
    			modelDataPlayerFormLittleMaid.modelArmorChestplate.actionRelease(modelDataPlayerFormLittleMaid.runActionNumber);
    			modelDataPlayerFormLittleMaid.modelArmor.actionRelease(modelDataPlayerFormLittleMaid.runActionNumber);
    		}

    	}
    	modelDataPlayerFormLittleMaid.modelArmorChestplate.syncModel(modelDataPlayerFormLittleMaid.mainModel);
    	modelDataPlayerFormLittleMaid.modelArmor.syncModel(modelDataPlayerFormLittleMaid.mainModel);

    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) {}
    	else if (mainModel != null
    			&& modelMain != null) doRenderLivingPFLM((EntityLiving) entityplayer, d, d3, d2, f, f1);
    	modelDataPlayerFormLittleMaid.modelArmorChestplate.aimedBow = modelDataPlayerFormLittleMaid.modelArmor.aimedBow = modelMain.aimedBow = false;
    	modelDataPlayerFormLittleMaid.modelArmorChestplate.isSneak = modelDataPlayerFormLittleMaid.modelArmor.isSneak = modelMain.isSneak = false;
    	modelDataPlayerFormLittleMaid.modelArmorChestplate.heldItemRight = modelDataPlayerFormLittleMaid.modelArmor.heldItemRight = modelMain.heldItemRight = 0;
    	modelMain.isInventory =
    			modelDataPlayerFormLittleMaid.modelArmor.isInventory =
    				modelDataPlayerFormLittleMaid.modelArmorChestplate.isInventory = false;
    }

	public void doRenderLiving(EntityLiving entityLiving, double d, double d1, double d2, float f, float f1)
    {
    	doRenderPlayerFormLittleMaid((EntityPlayer)entityLiving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	if(mc.currentScreen instanceof GuiSelectWorld) return;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	doRenderLiving((EntityPlayer)entity, d, d1, d2, f, f1);
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
    	modelDataPlayerFormLittleMaid.mainModel.renderItems(entityplayer, this);

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
    			modelDataPlayerFormLittleMaid.mainModel.renderEars(0.0625F);
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
    		modelDataPlayerFormLittleMaid.mainModel.renderCloak(0.0625F);
    		GL11.glPopMatrix();
    	}
/*
    	// b173deletefloat f20 = mc.currentScreen == null | mc.currentScreen instanceof GuiIngameMenu ? entityplayer.getEntityBrightness(f) : 1.0F;
    	// フラワーヘッド
    	ItemStack itemstack2 = entityplayer.inventory.getStackInSlot(9);
    	if (itemstack2 != null) {
    		Item item = itemstack2.getItem();
    		if (item instanceof ItemBlock) {
    			Block block = Block.blocksList[item.itemID];
    			boolean flag = false;
    			//DecoBlock, FavBlock用チェック
    			if (mod_PFLM_PlayerFormLittleMaid.isDecoBlock
    					| mod_PFLM_PlayerFormLittleMaid.isFavBlock) flag = decoBlockCheck(entityplayer, modelDataPlayerFormLittleMaid);

    			if (!flag) {
    				if (block instanceof BlockLeaves) {
    					GL11.glPushMatrix();
    					ModelRenderer bipedHead = modelDataPlayerFormLittleMaid.mainModel.getBipedHead();
    					modelDataPlayerFormLittleMaid.mainModel.bipedHeadPostRenderPosition();
    					bipedHead.postRender(0.0625F);

    					if (RenderBlocks.renderItemIn3d(Block.blocksList[itemstack2.itemID].getRenderType()))
    					{
    						float f1 = 0.630F;
    						modelDataPlayerFormLittleMaid.mainModel.equippedItemPositionBlockLeaves();
    						//GL11.glTranslatef(0.0F, -0.25F, 0.0F);
    						GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
    						GL11.glScalef(f1, -f1, f1);
    					}
    					int k = itemstack2.getItem().getColorFromItemStack(itemstack2, 0);
    					float f9 = (float)(k >> 16 & 0xff) / 255F;
    					float f10 = (float)(k >> 8 & 0xff) / 255F;
    					float f12 = (float)(k & 0xff) / 255F;
//-@-b173
    					GL11.glColor4f(f9, f10, f12, 1.0F);
//@-@b173
    					// b173deleteGL11.glColor4f(f9 + f20, f10 + f20, f12 + f20, 1.0F);
//-@-132
    					if (mod_PFLM_PlayerFormLittleMaid.useInvisibilityItem) {
    						if (!entityplayer.getHasActivePotion()) {
//@-@132
    							renderManager.itemRenderer.renderItem(entityplayer, itemstack2, 0);
//-@-132
    						}
    					} else renderManager.itemRenderer.renderItem(entityplayer, itemstack2, 0);
//@-@132
    					GL11.glPopMatrix();
//-@-b173
    					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//@-@b173
    				}
    				if (block instanceof BlockFlower) {
    					GL11.glPushMatrix();
    					loadTexture("/terrain.png");
    					GL11.glEnable(GL11.GL_CULL_FACE);
    					ModelRenderer bipedHead = modelDataPlayerFormLittleMaid.mainModel.getBipedHead();
    					modelDataPlayerFormLittleMaid.mainModel.bipedHeadPostRenderPosition();
    					bipedHead.postRender(0.0625F);
    					GL11.glScalef(1.0F, -1F, 1.0F);
    					modelDataPlayerFormLittleMaid.mainModel.equippedItemPositionFlower();
    					GL11.glRotatef(12F, 0.0F, 1.0F, 0.0F);
//-@-b173
    					int k = itemstack2.getItem().getColorFromItemStack(itemstack2, 0);
    					float f9 = (float)(k >> 16 & 0xff) / 255F;
    					float f10 = (float)(k >> 8 & 0xff) / 255F;
    					float f12 = (float)(k & 0xff) / 255F;
    					GL11.glColor4f(f9, f10, f12, 1.0F);
//@-@b173
    					ItemStack itemstack3 = entityplayer.inventory.getStackInSlot(10);
    					if (itemstack3 != null) {
    						Item item2 = itemstack3.getItem();
    						if (item2 == item.dyePowder) {
    							float f1 = 2.0F;
    							GL11.glScalef(f1, f1, f1);
    							modelDataPlayerFormLittleMaid.mainModel.equippedItemPositionFlowerDyePowder();
    						}
    					}
    					// b166deleteGL11.glColor4f(f20, f20, f20, 1.0F);
//-@-132
    					if (mod_PFLM_PlayerFormLittleMaid.useInvisibilityItem) {
    						if (!entityplayer.getHasActivePotion()) {
//@-@132
    							renderBlocks.renderBlockAsItem(block, itemstack2.getItemDamage(), 1.0F);
//-@-132
    						}
    					} else renderBlocks.renderBlockAsItem(block, itemstack2.getItemDamage(), 1.0F);
//@-@132
    					GL11.glDisable(GL11.GL_CULL_FACE);
    					GL11.glPopMatrix();
//-@-b173
    					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//@-@b173
    				}
    			}
    		}
    	}
    	float var3 = 1.0F;
    	GL11.glColor3f(var3, var3, var3);
    	ItemStack itemstack = entityplayer.inventory.armorItemInSlot(3);
    	if (itemstack != null) {
    		if (itemstack.getItem().itemID < 256) {
    			Item item = itemstack.getItem();
    			int j = item.itemID;
    			Block block = Block.blocksList[j];

    			if (item instanceof ItemBlock)
    			{
    				if (j == Block.pumpkin.blockID || j == Block.pumpkinLantern.blockID || j == Block.leaves.blockID)
    				{
    					GL11.glPushMatrix();
    					ModelRenderer bipedHead = modelDataPlayerFormLittleMaid.mainModel.getBipedHead();
    					modelDataPlayerFormLittleMaid.mainModel.bipedHeadPostRenderPosition();
    					bipedHead.postRender(0.0625F);
    					modelDataPlayerFormLittleMaid.mainModel.equippedBlockAndItemPosition();
    					if (RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
    					{
    						float f1 = 0.625F;
    						modelDataPlayerFormLittleMaid.mainModel.equippedBlockPosition3D();
    						if (mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.playerFormLittleMaidVersion > 129) {
    							GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
    						} else {
    							GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
    						}
    						GL11.glScalef(f1, -f1, f1);
    					}
    					// b173deleteGL11.glColor4f(f20, f20, f20, 1.0F);
//-@-132
    					if (mod_PFLM_PlayerFormLittleMaid.useInvisibilityItem) {
    						if (!entityplayer.getHasActivePotion()) {
//@-@132
    							renderManager.itemRenderer.renderItem(entityplayer, itemstack, 0);
//-@-132
    						}
    					} else renderManager.itemRenderer.renderItem(entityplayer, itemstack, 0);
//@-@132
    					GL11.glPopMatrix();
    				}

    				if (block instanceof BlockFlower)
    				{
    					loadTexture("/terrain.png");
    					GL11.glEnable(GL11.GL_CULL_FACE);
    					GL11.glPushMatrix();
    					ModelRenderer bipedHead = modelDataPlayerFormLittleMaid.mainModel.getBipedHead();
    					modelDataPlayerFormLittleMaid.mainModel.bipedHeadPostRenderPosition();
    					bipedHead.postRender(0.0625F);
    					GL11.glScalef(1.0F, -1F, 1.0F);
    					GL11.glTranslatef(0.0F, 1.0F, 0.0F);
    					GL11.glRotatef(12F, 0.0F, 1.0F, 0.0F);
//-@-132
    					if (mod_PFLM_PlayerFormLittleMaid.useInvisibilityItem) {
    						if (!entityplayer.getHasActivePotion()) {
//@-@132
    							renderBlocks.renderBlockAsItem(block, itemstack.getItemDamage(), 1.0F);
//-@-132
    						}
    					} else renderBlocks.renderBlockAsItem(block, itemstack.getItemDamage(), 1.0F);
//@-@132
    					GL11.glPopMatrix();
    					GL11.glDisable(GL11.GL_CULL_FACE);
    				}
    			}
    		}
//-@-132
    		else if (itemstack.getItem().itemID == Item.skull.itemID)
    		{
    			GL11.glPushMatrix();
    			ModelRenderer bipedHead = modelDataPlayerFormLittleMaid.mainModel.getBipedHead();
    			modelDataPlayerFormLittleMaid.mainModel.bipedHeadPostRenderPosition();
    			bipedHead.postRender(0.0625F);
    			modelDataPlayerFormLittleMaid.mainModel.bipedHeadPostRenderSkullPosition();
    			float var5 = 1.0625F;
    			GL11.glScalef(var5, -var5, -var5);
    			String var6 = "";

    			if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("SkullOwner"))
    			{
    				var6 = itemstack.getTagCompound().getString("SkullOwner");
    			}

    			TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack.getItemDamage(), var6);
    			GL11.glPopMatrix();
    		}
//@-@132
    	}
    	ItemStack itemstack1 = entityplayer.inventory.getCurrentItem();
    	if (itemstack1 != null)
    	{
    		GL11.glPushMatrix();
    		modelDataPlayerFormLittleMaid.mainModel.rightArmPostRender();
    		modelDataPlayerFormLittleMaid.mainModel.equippedBlockAndItemPosition();
    		if (entityplayer.fishEntity != null)
    		{
    			itemstack1 = new ItemStack(Item.stick);
    		}
//-@-b173
    		EnumAction enumaction = null;
    		if (entityplayer.getItemInUseCount() > 0)
    		{
    			enumaction = itemstack1.getItemUseAction();
    		}
//@-@b173
    		if (itemstack1.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack1.itemID].getRenderType()))
    		{
    			float f4 = 0.5F;
    			modelDataPlayerFormLittleMaid.mainModel.equippedBlockPosition();
    			f4 *= 0.75F;
    			GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
    			GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
    			GL11.glScalef(f4, -f4, f4);
    		}
    		else if (itemstack1.itemID == Item.bow.itemID)
    		{
    			float f5 = 0.625F;
    			modelDataPlayerFormLittleMaid.mainModel.equippedItemBow();
    			modelDataPlayerFormLittleMaid.mainModel.equippedItemBowRotatef();
    			GL11.glScalef(f5, -f5, f5);
    			modelDataPlayerFormLittleMaid.mainModel.equippedItemBowRotatef2();
    		}
    		else if (Item.itemsList[itemstack1.itemID].isFull3D())
    		{
    			//Modchu_Debug.mDebug("isFull3D f="+f);
    			float f6 = 0.625F;
    			if (Item.itemsList[itemstack1.itemID].shouldRotateAroundWhenRendering())
    			{
    				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
    				modelDataPlayerFormLittleMaid.mainModel.equippedItemPositionshouldRotateAroundWhenRendering();
    			}
    			modelDataPlayerFormLittleMaid.mainModel.equippedItemPosition3D();
//-@-b173
    			if (entityplayer.getItemInUseCount() > 0 && enumaction == EnumAction.block)
    			{
    				GL11.glTranslatef(0.05F, 0.0F, -0.1F);
    				GL11.glRotatef(-50F, 0.0F, 1.0F, 0.0F);
    				GL11.glRotatef(-10F, 1.0F, 0.0F, 0.0F);
    				GL11.glRotatef(-60F, 0.0F, 0.0F, 1.0F);
    			}
//@-@b173
    			GL11.glScalef(f6, -f6, f6);
    			GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
    			GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
    		}
    		else
    		{
    			//Modchu_Debug.mDebug("hoka f="+f);
    			float f7 = 0.375F;
    			modelDataPlayerFormLittleMaid.mainModel.equippedItemPosition();
    			GL11.glScalef(f7, f7, f7);
    			GL11.glRotatef(60F, 0.0F, 0.0F, 1.0F);
    			GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
    			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
    		}
    		if (itemstack1.getItem().shouldRotateAroundWhenRendering())
    		{
    			//Modchu_Debug.mDebug("shouldRotateAroundWhenRendering");
    			for (int j = 0; j <= 1; j++)
    			{
    				int k = itemstack1.getItem().getColorFromItemStack(itemstack1, j);
    				float f9 = (float)(k >> 16 & 0xff) / 255F;
    				float f10 = (float)(k >> 8 & 0xff) / 255F;
    				float f12 = (float)(k & 0xff) / 255F;
//-@-b173
    				GL11.glColor4f(f9, f10, f12, 1.0F);
//@-@b173
    				// b173deleteGL11.glColor4f(f9 + f20, f10 + f20, f12 + f20, 1.0F);
//-@-132
    				if (mod_PFLM_PlayerFormLittleMaid.useInvisibilityItem) {
    					if (!entityplayer.getHasActivePotion()) {
//@-@132
    						renderManager.itemRenderer.renderItem(entityplayer, itemstack1, j);
//-@-132
    					}
    				} else renderManager.itemRenderer.renderItem(entityplayer, itemstack1, j);
//@-@132
    			}
    		}
    		else
    		{
    			// b173deleteGL11.glColor4f(f20, f20, f20, 1.0F);
//-@-132
    			if (mod_PFLM_PlayerFormLittleMaid.useInvisibilityItem) {
    				if (!entityplayer.getHasActivePotion()) {
//@-@132
    					renderManager.itemRenderer.renderItem(entityplayer, itemstack1, 0);
//-@-132
    				}
    			} else renderManager.itemRenderer.renderItem(entityplayer, itemstack1, 0);
//@-@132
    		}
    		GL11.glPopMatrix();
    	}
*/
    }

    private boolean decoBlockCheck(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid) {
    	//DecoBlock, FavBlock用チェック
    	ItemStack itemstack2 = entityplayer.inventory.getStackInSlot(9);
    	Item item = itemstack2.getItem();
    	Block block = Block.blocksList[item.itemID];
    	boolean flag = false;
    	boolean rotate = false;
    	boolean translatef = false;
    	boolean particle = false;
    	int particleFrequency = 98;
    	String particleString = null;
    	float translatefX = 0.0F;
    	float translatefY = 0.0F;
    	float translatefZ = 0.0F;
    	if (mod_PFLM_PlayerFormLittleMaid.isDecoBlock) {
    		if (mod_PFLM_PlayerFormLittleMaid.decoBlock.isInstance(block)) {
    			flag = rotate = true;
    			particle = true;
    			particleString = "heart";
    		} else
    		if (mod_PFLM_PlayerFormLittleMaid.decoBlockBase.isInstance(block)) {
    			flag = rotate = true;
    		}
    	}
    	if (mod_PFLM_PlayerFormLittleMaid.isFavBlock
    			&& mod_PFLM_PlayerFormLittleMaid.favBlock.isInstance(block)) {
    		flag = rotate = true;
    		translatef = true;
    		translatefX = 0.0F;
    		translatefY = -0.1F;
    		translatefZ = 0.0F;
    		particle = true;
    		particleString = "instantSpell";
    		particleFrequency = 92;
    	}

    	if (flag) {
    		GL11.glPushMatrix();
    		loadTexture("/terrain.png");
    		GL11.glEnable(GL11.GL_CULL_FACE);
    		modelDataPlayerFormLittleMaid.mainModel.bipedHead.postRender(0.0625F);
    		GL11.glScalef(1.0F, -1F, 1.0F);
    		modelDataPlayerFormLittleMaid.mainModel.equippedItemPositionFlower();
    		if (rotate) GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
    		else GL11.glRotatef(12F, 0.0F, 1.0F, 0.0F);
//-@-b173
    		int k = itemstack2.getItem().getColorFromItemStack(itemstack2, 0);
    		float f9 = (float)(k >> 16 & 0xff) / 255F;
    		float f10 = (float)(k >> 8 & 0xff) / 255F;
    		float f12 = (float)(k & 0xff) / 255F;
    		GL11.glColor4f(f9, f10, f12, 1.0F);
//@-@b173
    		ItemStack itemstack3 = entityplayer.inventory.getStackInSlot(10);
    		if (itemstack3 != null) {
    			Item item2 = itemstack3.getItem();
    			if (item2 == item.dyePowder) {
    				float f1 = 2.0F;
    				GL11.glScalef(f1, f1, f1);
    				modelDataPlayerFormLittleMaid.mainModel.equippedItemPositionFlowerDyePowder();
    				if (mod_PFLM_PlayerFormLittleMaid.isFavBlock
    						&& mod_PFLM_PlayerFormLittleMaid.favBlock.isInstance(block)) {
    					particleFrequency = 80;
    				} else particleFrequency = 90;
    			}
    		}
    		// b166deleteGL11.glColor4f(f20, f20, f20, 1.0F);
    		if (translatef) GL11.glTranslatef(translatefX, translatefY, translatefZ);
    		renderBlocks.renderBlockAsItem(block, itemstack2.getItemDamage(), 1.0F);
    		if (particle
    				&& rnd.nextInt(100) > particleFrequency) {
    			double d = rnd.nextGaussian() * 0.02D;
    			double d1 = rnd.nextGaussian() * 0.02D;
    			double d2 = rnd.nextGaussian() * 0.02D;
    			entityplayer.worldObj.spawnParticle(particleString, (entityplayer.posX + (double)(rnd.nextFloat() * entityplayer.width * 2.0F)) - (double)entityplayer.width, entityplayer.posY - 0.5D + (double)(rnd.nextFloat() * entityplayer.height), (entityplayer.posZ + (double)(rnd.nextFloat() * entityplayer.width * 2.0F)) - (double)entityplayer.width, d, d1, d2);
    		}
    		GL11.glDisable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
    		/*b173//*/GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	}
    	return flag;
    }

    public void func_82441_a(EntityPlayer par1EntityPlayer)
    {
    	if(mc.currentScreen instanceof GuiSelectWorld) return;
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(mc.thePlayer);
    	if (modelDataPlayerFormLittleMaid != null) {} else return;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	if (!modelDataPlayerFormLittleMaid.isPlayer) {
    		modelDataPlayerFormLittleMaid.mainModel.firstPerson = false;
    		return;
    	}
    	float var2 = 1.0F;
    	GL11.glColor3f(var2, var2, var2);
    	modelDataPlayerFormLittleMaid.mainModel.onGround = 0.0F;
    	modelDataPlayerFormLittleMaid.mainModel.firstPerson = true;
    	modelDataPlayerFormLittleMaid.mainModel.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mc.thePlayer);
    	if (firstPersonHandResetFlag
    			&& modelDataPlayerFormLittleMaid.texture != null
    			&& renderManager.renderEngine != null) {
    		firstPersonHandResetFlag = false;
    		loadTexture(modelDataPlayerFormLittleMaid.texture);
    	}
    	modelDataPlayerFormLittleMaid.mainModel.renderFirstPersonHand(0.0625F);
    	modelDataPlayerFormLittleMaid.mainModel.firstPerson = false;
    }

    public static boolean isActivatedForPlayer(EntityPlayer entityplayer)
    {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) {} else return false;
    	if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return false;
    	return modelDataPlayerFormLittleMaid.isActivated;
    }

    public static PFLM_ModelData getPlayerData(EntityPlayer entityplayer)
    {
    	if (entityplayer != null) ;else return null;
    	PFLM_ModelData modelDataPlayerFormLittleMaid = (PFLM_ModelData)playerData.get(entityplayer);

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
    			Modchu_Debug.Debug("resetFlag clearPlayers()");
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
    		entityplayer.skinUrl = "http://s3.amazonaws.com/MinecraftSkins/" + entityplayer.username + ".png";
    		entityplayer.texture = modelDataPlayerFormLittleMaid.texture = null;
    		break;
    	case skinMode_local:
    	case skinMode_PlayerOffline:
    	case skinMode_Random:
    	case skinMode_OthersIndividualSettingOffline:
    		entityplayer.skinUrl = null;
    		entityplayer.texture = modelDataPlayerFormLittleMaid.texture;
    		break;
    	case skinMode_char:
    		entityplayer.skinUrl = null;
    		entityplayer.texture = modelDataPlayerFormLittleMaid.texture = "/mob/char.png";
    		break;
    	case skinMode_offline:
    		if (mod_PFLM_PlayerFormLittleMaid.textureName != null) {} else  mod_PFLM_PlayerFormLittleMaid.textureName = "default";
    		if (mod_PFLM_PlayerFormLittleMaid.textureArmorName != null) {} else mod_PFLM_PlayerFormLittleMaid.textureArmorName = "default";
    		entityplayer.skinUrl = null;
    		entityplayer.texture = modelDataPlayerFormLittleMaid.texture = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, mod_PFLM_PlayerFormLittleMaid.maidColor);
    		//Modchu_Debug.mDebug("skinMode_offline mod_PFLM_PlayerFormLittleMaid.textureName="+mod_PFLM_PlayerFormLittleMaid.textureName);
    		break;
    	case skinMode_Player:
    		entityplayer.skinUrl = mc.thePlayer.skinUrl;
    		entityplayer.texture = mc.thePlayer.texture;
    		modelDataPlayerFormLittleMaid = (PFLM_ModelData)playerData.get(mc.thePlayer);
    		break;
    	case skinMode_OthersSettingOffline:
    		if (mod_PFLM_PlayerFormLittleMaid.othersTextureName != null) {} else mod_PFLM_PlayerFormLittleMaid.othersTextureName = "default";
    		if (mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName != null) {} else mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName = "default";
    		entityplayer.skinUrl = null;
    		entityplayer.texture = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.othersTextureName, mod_PFLM_PlayerFormLittleMaid.othersMaidColor);
    		break;
    	case skinMode_PlayerOnline:
    		entityplayer.texture = modelDataPlayerFormLittleMaid.texture;
    		break;
    	case skinMode_PlayerLocalData:
    		String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.playerLocalData.get(entityplayer.username);
    		entityplayer.skinUrl = null;
    		entityplayer.texture = modelDataPlayerFormLittleMaid.texture = PFLM_Texture.getTextureName(t[0], Integer.valueOf(t[2]));
    		break;
    	}
    	return modelDataPlayerFormLittleMaid;
    }

	private static PFLM_ModelData loadPlayerData(EntityPlayer entityplayer)
	{
		PFLM_ModelData modelDataPlayerFormLittleMaid = new PFLM_ModelData();
		return loadPlayerData(entityplayer, modelDataPlayerFormLittleMaid);
	}

	private static PFLM_ModelData loadPlayerData(EntityPlayer entityplayer, PFLM_ModelData modelDataPlayerFormLittleMaid)
	{
		if (entityplayer == null) return new PFLM_ModelData();
		if (modelDataPlayerFormLittleMaid == null) modelDataPlayerFormLittleMaid = new PFLM_ModelData();
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
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modeOthersSettingOffline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_OthersIndividualSettingOffline;
					String s2 = t[0];
					Modchu_Debug.mDebug("@@@@loadPlayerData modelName="+s2);
					modelDataPlayerFormLittleMaid.texture = PFLM_Texture.getTextureName(s2, Integer.valueOf(t[2]));
					int i3 = s2.lastIndexOf('_');
					if (i3 > -1)
					{
						s2 = s2.substring(i3 + 1);
					}
					modelDataPlayerFormLittleMaid.modeltype = s2;
					Modchu_Debug.mDebug("@@@@loadPlayerData modeltype="+s2);
					ModelPlayerFormLittleMaidBaseBiped[] model2 = (ModelPlayerFormLittleMaidBaseBiped[]) PFLM_Texture.multiModelGet(s2);
					if (model2 != null) {
						modelDataPlayerFormLittleMaid.mainModel = model2[0] != null ? model2[0] : new ModelPlayerFormLittleMaid(0.0F);
					} else {
						modelDataPlayerFormLittleMaid.mainModel = new ModelPlayerFormLittleMaid(0.0F);
					}
					s2 = t[1];
					modelDataPlayerFormLittleMaid.modelArmorName = t[1];
					i3 = s2.lastIndexOf('_');
					if (i3 > -1)
					{
						s2 = s2.substring(i3 + 1);
					}
					Modchu_Debug.mDebug("@@@@loadPlayerData modelArmorName="+s2);
					model2 = (ModelPlayerFormLittleMaidBaseBiped[]) PFLM_Texture.multiModelGet(s2);
					if (model2 != null) {
						modelDataPlayerFormLittleMaid.modelArmorChestplate = model2[1] != null ? model2[1] : new ModelPlayerFormLittleMaid(0.5F);
						modelDataPlayerFormLittleMaid.modelArmor = model2[2] != null ? model2[2] : new ModelPlayerFormLittleMaid(0.1F);
					} else {
						modelDataPlayerFormLittleMaid.modelArmorChestplate = new ModelPlayerFormLittleMaid(0.5F);
						modelDataPlayerFormLittleMaid.modelArmor = new ModelPlayerFormLittleMaid(0.1F);
					}
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayerOffline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_PlayerOffline;
					skinMode_PlayerOfflineSetting(modelDataPlayerFormLittleMaid);
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayerOnline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_PlayerOnline;
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modeRandom:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_Random;
					skinMode_RandomSetting(modelDataPlayerFormLittleMaid);
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
					break;
				case PFLM_GuiOthersPlayer.modeOthersSettingOffline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_OthersSettingOffline;
					String s = mod_PFLM_PlayerFormLittleMaid.othersTextureName;
					int i1 = s.lastIndexOf('_');
					if (i1 > -1)
					{
						s = s.substring(i1 + 1);
					}
					ModelPlayerFormLittleMaidBaseBiped[] model = (ModelPlayerFormLittleMaidBaseBiped[]) PFLM_Texture.modelMap.get(s);
					if (model != null) {
						modelDataPlayerFormLittleMaid.mainModel = model[0] != null ? model[0] : new ModelPlayerFormLittleMaid(0.0F);
					} else {
						modelDataPlayerFormLittleMaid.mainModel = new ModelPlayerFormLittleMaid(0.0F);
					}
					s = mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName;
					modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName;
					i1 = s.lastIndexOf('_');
					if (i1 > -1)
					{
						s = mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName.substring(i1 + 1);
					}
					model = (ModelPlayerFormLittleMaidBaseBiped[]) PFLM_Texture.modelMap.get(s);
					if (model != null) {
						modelDataPlayerFormLittleMaid.modelArmorChestplate = model[1] != null ? model[1] : new ModelPlayerFormLittleMaid(0.5F);
						modelDataPlayerFormLittleMaid.modelArmor = model[2] != null ? model[2] : new ModelPlayerFormLittleMaid(0.1F);
					} else {
						modelDataPlayerFormLittleMaid.modelArmorChestplate = new ModelPlayerFormLittleMaid(0.5F);
						modelDataPlayerFormLittleMaid.modelArmor = new ModelPlayerFormLittleMaid(0.1F);
					}
					break;
				case PFLM_GuiOthersPlayer.modePlayerOffline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_PlayerOffline;
					skinMode_PlayerOfflineSetting(modelDataPlayerFormLittleMaid);
					break;
				case PFLM_GuiOthersPlayer.modePlayerOnline:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_PlayerOnline;
					break;
				case PFLM_GuiOthersPlayer.modeRandom:
					modelDataPlayerFormLittleMaid.skinMode = skinMode_Random;
					skinMode_RandomSetting(modelDataPlayerFormLittleMaid);
					break;
				}
				if (modelDataPlayerFormLittleMaid.skinMode != skinMode_PlayerOnline
						&& modelDataPlayerFormLittleMaid.skinMode != skinMode_online) {
					modelDataPlayerFormLittleMaid.initFlag = 2;
					return modelDataPlayerFormLittleMaid;
				}
			}
		} else {
			modelDataPlayerFormLittleMaid.handedness = mod_PFLM_PlayerFormLittleMaid.handednessMode == -1 ? rnd.nextInt(2) : mod_PFLM_PlayerFormLittleMaid.handednessMode;
			if (PFLM_Gui.changeMode == PFLM_Gui.modeRandom) {
				modelDataPlayerFormLittleMaid.skinMode = skinMode_Random;
				skinMode_RandomSetting(modelDataPlayerFormLittleMaid);
				modelDataPlayerFormLittleMaid.initFlag = 2;
				return modelDataPlayerFormLittleMaid;
			}
		}
		if (entityplayer.skinUrl == null) {
			if (mod_PFLM_PlayerFormLittleMaid.onlineMode
				| !modelDataPlayerFormLittleMaid.isPlayer) {
				entityplayer.skinUrl = (new StringBuilder()).append("http://s3.amazonaws.com/MinecraftSkins/").append(entityplayer.username).append(".png").toString();
			}
		}
		boolean er = false;
		try
		{
			if (mod_PFLM_PlayerFormLittleMaid.onlineMode
					| !modelDataPlayerFormLittleMaid.isPlayer
					| modelDataPlayerFormLittleMaid.skinMode == skinMode_online) {
				Modchu_Debug.Debug((new StringBuilder()).append("new model read username = ").append(entityplayer.username).toString());
				if (modelDataPlayerFormLittleMaid.skinMode == skinMode_PlayerOnline) {
					entityplayer.skinUrl = (new StringBuilder()).append("http://s3.amazonaws.com/MinecraftSkins/").append(mc.thePlayer.username).append(".png").toString();
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
			if (mod_PFLM_PlayerFormLittleMaid.onlineMode
					&& mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.isRelease()
					| !modelDataPlayerFormLittleMaid.isPlayer) {
				//Modchu_Debug.Debug("er /mob/char.png ");
				modelDataPlayerFormLittleMaid.skinMode = skinMode_char;
				modelDataPlayerFormLittleMaid.modelArmorName = "_Biped";
				modelDataPlayerFormLittleMaid.modeltype = "Biped";
				modelDataPlayerFormLittleMaid.mainModel.isPlayer = modelDataPlayerFormLittleMaid.modelArmorChestplate.isPlayer =
						modelDataPlayerFormLittleMaid.modelArmor.isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
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

	private static PFLM_ModelData checkSkin(
			EntityPlayer entityplayer, BufferedImage bufferedimage
			,PFLM_ModelData modelDataPlayerFormLittleMaid)
	{
		if (modelDataPlayerFormLittleMaid.isPlayer
				&& !mod_PFLM_PlayerFormLittleMaid.onlineMode
				| modelDataPlayerFormLittleMaid.skinMode == skinMode_offline) {
			modelDataPlayerFormLittleMaid.mainModel = null;
			int j = mod_PFLM_PlayerFormLittleMaid.textureName.lastIndexOf('_');
			//Modchu_Debug.mDebug("offlineMode checkSkin mod_PFLM_PlayerFormLittleMaid.textureName="+mod_PFLM_PlayerFormLittleMaid.textureName);
			if (j > -1)
			{
				String s = mod_PFLM_PlayerFormLittleMaid.textureName.substring(j + 1);
				Object amodelPlayerFormLittleMaid[];
				amodelPlayerFormLittleMaid = modelDataPlayerFormLittleMaid.isPlayer ?
						(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.modelMap.get(s) :
							(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.multiModelGet(s);
				modelDataPlayerFormLittleMaid.modeltype = s;
				if (amodelPlayerFormLittleMaid != null) {
					if (modelDataPlayerFormLittleMaid.isPlayer) modelDataPlayerFormLittleMaid.mainModel = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[0] == null ? modelBasicOrig[0] : amodelPlayerFormLittleMaid[0]);
					else modelDataPlayerFormLittleMaid.mainModel = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[0] == null ? new ModelPlayerFormLittleMaid(0.0F) : amodelPlayerFormLittleMaid[0]);
					Modchu_Debug.mDebug((new StringBuilder()).append("offlineMode mainModel ok. modelDataPlayerFormLittleMaid.modeltype = ").append(modelDataPlayerFormLittleMaid.modeltype).toString());
				} else {
					Modchu_Debug.mDebug((new StringBuilder()).append("offlineMode mainModel null!! modelDataPlayerFormLittleMaid.modeltype = ").append(modelDataPlayerFormLittleMaid.modeltype).toString());
				}
			}
			if (modelDataPlayerFormLittleMaid.mainModel == null) {
				modelDataPlayerFormLittleMaid.modeltype = "default";
				modelDataPlayerFormLittleMaid.mainModel = new ModelPlayerFormLittleMaid(0.0F);
			}
			modelDataPlayerFormLittleMaid.modelArmor = null;
			modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
			j = mod_PFLM_PlayerFormLittleMaid.textureArmorName.lastIndexOf('_');
			//Modchu_Debug.mDebug("offlineMode checkSkin mod_PFLM_PlayerFormLittleMaid.textureName="+mod_PFLM_PlayerFormLittleMaid.textureName);
			String s;
			if (j > -1)
			{
				s = mod_PFLM_PlayerFormLittleMaid.textureArmorName.substring(j + 1);
			} else {
				s = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
			}
			float f1 = 0.5F;
			float f2 = 0.1F;
			if (modelDataPlayerFormLittleMaid.modeltype.equalsIgnoreCase("Biped")) {
				f1 = 1.0F;
				f2 = 0.5F;
				Modchu_Debug.mDebug((new StringBuilder()).append("offlineMode Biped mod_PFLM_PlayerFormLittleMaid.textureArmorName = ").append(mod_PFLM_PlayerFormLittleMaid.textureArmorName).toString());
			}
			Object amodelPlayerFormLittleMaid[];
			amodelPlayerFormLittleMaid = modelDataPlayerFormLittleMaid.isPlayer ?
					(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.modelMap.get(s) :
						(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.multiModelGet(s);
			if (amodelPlayerFormLittleMaid != null) {
				if (modelDataPlayerFormLittleMaid.isPlayer) {
					modelDataPlayerFormLittleMaid.modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[1] != null ? amodelPlayerFormLittleMaid[1] : modelBasicOrig[1]);
					modelDataPlayerFormLittleMaid.modelArmor = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[2] != null ? amodelPlayerFormLittleMaid[2] : modelBasicOrig[2]);
				} else {
					modelDataPlayerFormLittleMaid.modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[1] != null ? amodelPlayerFormLittleMaid[1] : new ModelPlayerFormLittleMaid(f1));
					modelDataPlayerFormLittleMaid.modelArmor = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[2] != null ? amodelPlayerFormLittleMaid[2] : new ModelPlayerFormLittleMaid(f2));
				}
			}
			if (modelDataPlayerFormLittleMaid.modelArmor == null) {
				if (modelDataPlayerFormLittleMaid.modeltype.equalsIgnoreCase("Biped")) {
					modelDataPlayerFormLittleMaid.modelArmorChestplate = new ModelPlayerFormLittleMaid_Biped(f1);
					modelDataPlayerFormLittleMaid.modelArmor = new ModelPlayerFormLittleMaid_Biped(f2);
				} else {
					modelDataPlayerFormLittleMaid.modelArmorChestplate = new ModelPlayerFormLittleMaid(f1);
					modelDataPlayerFormLittleMaid.modelArmor = new ModelPlayerFormLittleMaid(f2);
				}
				Modchu_Debug.mDebug((new StringBuilder()).append("offlineMode modelArmor == null mod_PFLM_PlayerFormLittleMaid.textureArmorName = ").append(mod_PFLM_PlayerFormLittleMaid.textureArmorName).toString());
			}
			modelDataPlayerFormLittleMaid.skinMode = skinMode_offline;
			modelDataPlayerFormLittleMaid.mainModel.isPlayer = modelDataPlayerFormLittleMaid.modelArmorChestplate.isPlayer =
					modelDataPlayerFormLittleMaid.modelArmor.isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
			return modelDataPlayerFormLittleMaid;
		}
		if (bufferedimage == null) {
			Modchu_Debug.Debug("bufferedimage == null");
			modelDataPlayerFormLittleMaid.skinMode = skinMode_char;
			modelDataPlayerFormLittleMaid.modelArmorName = "_Biped";
			modelDataPlayerFormLittleMaid.mainModel.isPlayer = modelDataPlayerFormLittleMaid.modelArmorChestplate.isPlayer =
					modelDataPlayerFormLittleMaid.modelArmor.isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
			return modelDataPlayerFormLittleMaid;
		}
		modelDataPlayerFormLittleMaid.isActivated = true;
		Modchu_Debug.mDebug("modelDataPlayerFormLittleMaid.isPlayer="+modelDataPlayerFormLittleMaid.isPlayer);
		Object[] s = checkimage(bufferedimage);
		boolean localflag = (Boolean) s[0];
		modelDataPlayerFormLittleMaid.modeltype = (String) s[1];
		modelDataPlayerFormLittleMaid.modelArmorName = (String) s[2];
		int maidcolor = (Integer) s[3];
		String textureName = (String) s[4];
		boolean returnflag = (Boolean) s[5];

		if (returnflag) {
			if (modelDataPlayerFormLittleMaid.isPlayer) {
				mod_PFLM_PlayerFormLittleMaid.textureName = "Biped_Biped";
				modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.textureArmorName = "Biped_Biped";
			}
			modelDataPlayerFormLittleMaid.skinMode = skinMode_online;
			modelDataPlayerFormLittleMaid.mainModel.isPlayer = modelDataPlayerFormLittleMaid.modelArmorChestplate.isPlayer =
					modelDataPlayerFormLittleMaid.modelArmor.isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
			return modelDataPlayerFormLittleMaid;
		}
		if (modelDataPlayerFormLittleMaid.isPlayer) {
			//Modchu_Debug.mDebug("modelDataPlayerFormLittleMaid.isPlayer set modelDataPlayerFormLittleMaid.modeltype="+modelDataPlayerFormLittleMaid.modeltype);
			mod_PFLM_PlayerFormLittleMaid.textureName = modelDataPlayerFormLittleMaid.modeltype;
			mod_PFLM_PlayerFormLittleMaid.maidColor = maidcolor;
			mod_PFLM_PlayerFormLittleMaid.textureArmorName = modelDataPlayerFormLittleMaid.modelArmorName;
		}
		Modchu_Debug.Debug((new StringBuilder()).append("checkSkin modeltype = ").append(modelDataPlayerFormLittleMaid.modeltype).toString());
		if(localflag) {
			modelDataPlayerFormLittleMaid.localFlag = true;
			modelDataPlayerFormLittleMaid.skinMode = skinMode_local;
			if (modelDataPlayerFormLittleMaid.modeltype != null)
			{
				Modchu_Debug.Debug((new StringBuilder()).append("localflag maidcolor = ").append(maidcolor).toString());
				entityplayer.texture = modelDataPlayerFormLittleMaid.texture = textureName;
				Modchu_Debug.Debug((new StringBuilder()).append("localflag texture = ").append(entityplayer.texture).toString());
				entityplayer.skinUrl = null;
			}
		} else {
			modelDataPlayerFormLittleMaid.localFlag = false;
			modelDataPlayerFormLittleMaid.texture = null;
		}

		if (modelDataPlayerFormLittleMaid.modeltype != null)
		{
			Object amodelPlayerFormLittleMaid[];
			modelDataPlayerFormLittleMaid.mainModel = null;
			amodelPlayerFormLittleMaid = modelDataPlayerFormLittleMaid.isPlayer ?
					(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.modelMap.get(modelDataPlayerFormLittleMaid.modeltype) :
						(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.multiModelGet(modelDataPlayerFormLittleMaid.modeltype);
			if (amodelPlayerFormLittleMaid != null) {
				if (modelDataPlayerFormLittleMaid.isPlayer) {
					modelDataPlayerFormLittleMaid.mainModel = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[0] == null ? modelBasicOrig[0] : amodelPlayerFormLittleMaid[0]);
					modelDataPlayerFormLittleMaid.modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[1] == null ? modelBasicOrig[1] : amodelPlayerFormLittleMaid[1]);
					modelDataPlayerFormLittleMaid.modelArmor = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[2] == null ? modelBasicOrig[2] : amodelPlayerFormLittleMaid[2]);
				} else {
					modelDataPlayerFormLittleMaid.mainModel = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[0] == null ? new ModelPlayerFormLittleMaid(0.0F) : amodelPlayerFormLittleMaid[0]);
					modelDataPlayerFormLittleMaid.modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[1] == null ? new ModelPlayerFormLittleMaid(0.5F) : amodelPlayerFormLittleMaid[1]);
					modelDataPlayerFormLittleMaid.modelArmor = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[2] == null ? new ModelPlayerFormLittleMaid(0.1F) : amodelPlayerFormLittleMaid[2]);
				}
			}
			if (modelDataPlayerFormLittleMaid.mainModel == null) {
				Modchu_Debug.Debug((new StringBuilder()).append("mainModel == null modelDataPlayerFormLittleMaid.modeltype = ").append(modelDataPlayerFormLittleMaid.modeltype).toString());
				modelDataPlayerFormLittleMaid.modeltype = "default";
				modelDataPlayerFormLittleMaid.mainModel = new ModelPlayerFormLittleMaid(0.0F);
				modelDataPlayerFormLittleMaid.modelArmorChestplate = new ModelPlayerFormLittleMaid(0.5F);
				modelDataPlayerFormLittleMaid.modelArmor = new ModelPlayerFormLittleMaid(0.1F);
			}
		}
		Modchu_Debug.Debug((new StringBuilder()).append("checkSkin Armor = ").append(s[2]).toString());
		if (modelDataPlayerFormLittleMaid.modelArmorName != null)
		{
			Object amodelPlayerFormLittleMaid[];
			modelDataPlayerFormLittleMaid.modelArmor = null;
			String s1 = modelDataPlayerFormLittleMaid.modelArmorName;
			int j = s1.lastIndexOf('_');
			if (j > -1)
			{
				s1 = s1.substring(j + 1);
			}
			amodelPlayerFormLittleMaid = modelDataPlayerFormLittleMaid.isPlayer ?
					(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.modelMap.get(s1) :
						(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.multiModelGet(s1);
			if (amodelPlayerFormLittleMaid != null) {
				if (modelDataPlayerFormLittleMaid.isPlayer) {
					modelDataPlayerFormLittleMaid.modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[1] == null ? modelBasicOrig[1] : amodelPlayerFormLittleMaid[1]);
					modelDataPlayerFormLittleMaid.modelArmor = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[2] == null ? modelBasicOrig[2] : amodelPlayerFormLittleMaid[2]);
				} else {
					modelDataPlayerFormLittleMaid.modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[1] == null ? new ModelPlayerFormLittleMaid(0.5F) : amodelPlayerFormLittleMaid[1]);
					modelDataPlayerFormLittleMaid.modelArmor = (ModelPlayerFormLittleMaidBaseBiped) (amodelPlayerFormLittleMaid[2] == null ? new ModelPlayerFormLittleMaid(0.1F) : amodelPlayerFormLittleMaid[2]);
				}
			}
			if (modelDataPlayerFormLittleMaid.modelArmor == null) {
				modelDataPlayerFormLittleMaid.modelArmorChestplate = s1.equalsIgnoreCase("Biped") ? new ModelPlayerFormLittleMaid_Biped(1.0F) : new ModelPlayerFormLittleMaid(0.5F);
				modelDataPlayerFormLittleMaid.modelArmor = s1.equalsIgnoreCase("Biped") ? new ModelPlayerFormLittleMaid_Biped(0.5F) : new ModelPlayerFormLittleMaid(0.1F);
				Modchu_Debug.Debug("Armor new default");
			}
		}
		Modchu_Debug.Debug((new StringBuilder()).append("modelDataPlayerFormLittleMaid.modeltype = ").append(modelDataPlayerFormLittleMaid.modeltype).toString());

		modelDataPlayerFormLittleMaid.mainModel.isPlayer = modelDataPlayerFormLittleMaid.modelArmorChestplate.isPlayer =
				modelDataPlayerFormLittleMaid.modelArmor.isPlayer = modelDataPlayerFormLittleMaid.isPlayer = entityplayer.username == mc.thePlayer.username;
		return modelDataPlayerFormLittleMaid;
    }

	public static Object[] checkimage(BufferedImage bufferedimage)
	{
		Object[] object = new Object[6];
		//0 localflag
		object[0] = false;
		//1 modeltype
		object[1] = "";
		//2 modelArmorName
		object[2] = "";
		//3 maidcolor
		object[3] = 0;
		//4 TextureName
		object[4] = "";
		//5 return flag
		object[5] = false;
    	int r = 0;
    	int g = 0;
    	int b = 0;
    	int a = 0;
    	do
        {
        	int checkX = 63;
        	int checkY = 0;
        	int[] c1 = checkImageColor(bufferedimage, checkX, checkY);
        	r = c1[0];
        	g = c1[1];
        	b = c1[2];
        	a = c1[3];

        	checkY = 1;
        	if (r != 255 | g != 0 | b != 0 | a != 255)
        	{
        		Modchu_Debug.Debug((new StringBuilder()).append("checkimage Out r255 g0 b0 a255.x=63,y=0 r=").append(r).append(" g=").append(g).append(" b=").append(b).append(" a=").append(a).toString());
        		checkY = 31;
        		c1 = checkImageColor(bufferedimage, checkX, checkY);
        		r = c1[0];
        		g = c1[1];
        		b = c1[2];
        		a = c1[3];
        		if (r != 255 | g != 0 | b != 0 | a != 255)
        		{
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
        	if (r != 255 | g != 255 | b != 0 | a != 255)
        	{
        		if (r != 255 | g != 0 | b != 255 | a != 255)
        		{
        			Modchu_Debug.Debug((new StringBuilder()).append("checkimage Out r255 g0 b255 a255.x = 63,y = 1 r = ").append(r).append(" g = ").append(g).append(" b = ").append(b).append(" a = ").append(a).toString());
        			object[5] = true;
        			break;
        		} else {
        			Modchu_Debug.Debug("checkimage localflag = true");
        			object[0] = true;
        		}
        	}

        	checkX = 62;
        	checkY = checkY == 1 ? 0 : 31;
        	c1 = checkImageColor(bufferedimage, checkX, checkY);
        	r = 255 - c1[0];
        	g = 255 - c1[1];
        	b = 255 - c1[2];
        	a = 255 - c1[3];
        	break;
        }
        while (true);
    	if (!(Boolean) object[5]) {
    		if (b < mod_PFLM_PlayerFormLittleMaid.modelList.size())
			{
    			object[1] = mod_PFLM_PlayerFormLittleMaid.modelList.get(b);
			}
			if (g < mod_PFLM_PlayerFormLittleMaid.textureList.size())
			{
				object[2] = mod_PFLM_PlayerFormLittleMaid.textureList.get(g);
			}
			object[3] = r;
			if (b < mod_PFLM_PlayerFormLittleMaid.textureList.size())
			{
				object[4] = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureList.get(b), r);
			}
		}
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
    			f1 = 1.0F * (1.62F - modelDataPlayerFormLittleMaid.mainModel.getyOffset());
    			break;

    		case 2:
    			f1 = -1F * (1.62F - modelDataPlayerFormLittleMaid.mainModel.getyOffset());
    			break;

    		case 1:
    			f = -1F * (1.62F - modelDataPlayerFormLittleMaid.mainModel.getyOffset());
    			break;

    		case 3:
    			f = 1.0F * (1.62F - modelDataPlayerFormLittleMaid.mainModel.getyOffset());
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
    	if(mod_PFLM_PlayerFormLittleMaid.isRenderName) {
    		PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    		if (modelDataPlayerFormLittleMaid == null) return;
    		double d3 = 0.0D;
    		if(entityplayer.isSneaking()) d3 = 0.4D;
    		super.renderName(entityplayer, d, (d1 - 1.8D) + (double)modelDataPlayerFormLittleMaid.mainModel.getHeight() + d3, d2);
    	}
    }

    public static void clearPlayers()
    {
    	playerData.clear();
    	PFLM_Gui.showModelFlag = true;
    	mod_PFLM_PlayerFormLittleMaid.setShortcutKeysActionInitFlag(false);
    }

    public void waitModeSetting(PFLM_ModelData modelDataPlayerFormLittleMaid, float f) {
    	if (!modelDataPlayerFormLittleMaid.mainModel.firstPerson) {
    		if(mod_PFLM_PlayerFormLittleMaid.waitTime == 0
    				&& modelDataPlayerFormLittleMaid.mainModel.isPlayer) {
    			if(mod_PFLM_PlayerFormLittleMaid.isWait) {
    				if (((f != modelDataPlayerFormLittleMaid.isWaitF && modelDataPlayerFormLittleMaid.isWaitFSetFlag)
    						| modelDataPlayerFormLittleMaid.mainModel.onGround > 0)
    						| (modelDataPlayerFormLittleMaid.isWait && modelDataPlayerFormLittleMaid.mainModel.isSneak)) {
    					modelDataPlayerFormLittleMaid.isWait = false;
    					mod_PFLM_PlayerFormLittleMaid.isWait = false;
    					modelDataPlayerFormLittleMaid.isWaitTime = 0;
    					modelDataPlayerFormLittleMaid.isWaitF = copyf(f);
    					modelDataPlayerFormLittleMaid.isWaitFSetFlag = true;
    				} else {
    					if ((f != modelDataPlayerFormLittleMaid.isWaitF
    							| modelDataPlayerFormLittleMaid.mainModel.onGround > 0)
    							| (modelDataPlayerFormLittleMaid.isWait && modelDataPlayerFormLittleMaid.mainModel.isSneak)) {
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
    					&& !modelDataPlayerFormLittleMaid.mainModel.isSneak
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
    					| modelDataPlayerFormLittleMaid.mainModel.onGround > 0)
    					| (modelDataPlayerFormLittleMaid.isWait && modelDataPlayerFormLittleMaid.mainModel.isSneak)) {
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

    private static void skinMode_PlayerOfflineSetting(
    		PFLM_ModelData modelDataPlayerFormLittleMaid) {
    	if (mod_PFLM_PlayerFormLittleMaid.textureName == null
    			| mod_PFLM_PlayerFormLittleMaid.textureName.isEmpty()) mod_PFLM_PlayerFormLittleMaid.textureName = "Biped_Biped";
    	if (mod_PFLM_PlayerFormLittleMaid.textureArmorName == null
    			| mod_PFLM_PlayerFormLittleMaid.textureArmorName.isEmpty()) mod_PFLM_PlayerFormLittleMaid.textureArmorName = "Biped_Biped";
    	modelDataPlayerFormLittleMaid.texture = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, mod_PFLM_PlayerFormLittleMaid.maidColor);
    	String s1 = mod_PFLM_PlayerFormLittleMaid.textureName;
    	int i2 = s1.lastIndexOf('_');
    	if (i2 > -1)
    	{
    		s1 = s1.substring(i2 + 1);
    	}
    	ModelPlayerFormLittleMaidBaseBiped[] model1 = (ModelPlayerFormLittleMaidBaseBiped[]) PFLM_Texture.modelMap.get(s1);
    	if (model1 != null) {
    		modelDataPlayerFormLittleMaid.mainModel = model1[0] != null ? model1[0] : new ModelPlayerFormLittleMaid(0.0F);
    	} else {
    		modelDataPlayerFormLittleMaid.mainModel = new ModelPlayerFormLittleMaid(0.0F);
    	}
    	s1 = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
    	modelDataPlayerFormLittleMaid.modelArmorName = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
    	i2 = s1.lastIndexOf('_');
    	if (i2 > -1)
    	{
    		s1 = mod_PFLM_PlayerFormLittleMaid.textureArmorName.substring(i2 + 1);
    	}
    	model1 = (ModelPlayerFormLittleMaidBaseBiped[]) PFLM_Texture.modelMap.get(s1);
    	if (model1 != null) {
    		modelDataPlayerFormLittleMaid.modelArmorChestplate = model1[1] != null ? model1[1] : new ModelPlayerFormLittleMaid(0.5F);
    		modelDataPlayerFormLittleMaid.modelArmor = model1[2] != null ? model1[2] : new ModelPlayerFormLittleMaid(0.1F);
    	} else {
    		modelDataPlayerFormLittleMaid.modelArmorChestplate = new ModelPlayerFormLittleMaid(0.5F);
    		modelDataPlayerFormLittleMaid.modelArmor = new ModelPlayerFormLittleMaid(0.1F);
    	}
    }

    private static void skinMode_RandomSetting(
    		PFLM_ModelData modelDataPlayerFormLittleMaid) {
    	int i = rnd.nextInt(16);
    	int j = rnd.nextInt(PFLM_Texture.textures.size());
    	String s3 = PFLM_Texture.getPackege(i, j);
    	Modchu_Debug.Debug("Random modelPackege="+s3);
    	modelDataPlayerFormLittleMaid.texture = PFLM_Texture.getTextureName(s3, i);
    	String s1 = mod_PFLM_PlayerFormLittleMaid.getArmorName(s3);
    	modelDataPlayerFormLittleMaid.modelArmorName = s1;
    	int i4 = s3.lastIndexOf('_');
    	if (i4 > -1)
    	{
    		s3 = s3.substring(i4 + 1);
    	}
    	ModelPlayerFormLittleMaidBaseBiped[] model3 = (ModelPlayerFormLittleMaidBaseBiped[]) PFLM_Texture.modelMap.get(s3);
    	if (model3 != null) {
    		modelDataPlayerFormLittleMaid.mainModel = model3[0] != null ? model3[0] : new ModelPlayerFormLittleMaid(0.0F);
    	} else {
    		modelDataPlayerFormLittleMaid.mainModel = new ModelPlayerFormLittleMaid(0.0F);
    	}
    	int i2 = s1.lastIndexOf('_');
    	if (i2 > -1)
    	{
    		s1 = s1.substring(i2 + 1);
    	}
    	model3 = (ModelPlayerFormLittleMaidBaseBiped[]) PFLM_Texture.modelMap.get(s1);
    	Modchu_Debug.Debug("Random modelMap.get="+s1);
    	if (model3 != null) {
    		modelDataPlayerFormLittleMaid.modelArmorChestplate = model3[1] != null ? model3[1] : new ModelPlayerFormLittleMaid(0.5F);
    		modelDataPlayerFormLittleMaid.modelArmor = model3[2] != null ? model3[2] : new ModelPlayerFormLittleMaid(0.1F);
    		Modchu_Debug.Debug("Random modelArmorName="+modelDataPlayerFormLittleMaid.modelArmorName);
    	} else {
    		modelDataPlayerFormLittleMaid.modelArmorChestplate = new ModelPlayerFormLittleMaid(0.5F);
    		modelDataPlayerFormLittleMaid.modelArmor = new ModelPlayerFormLittleMaid(0.1F);
    		Modchu_Debug.Debug("Random modelArmorName=default");
    	}
    	if (modelDataPlayerFormLittleMaid.isPlayer) {
    		mod_PFLM_PlayerFormLittleMaid.textureModel[0] = modelDataPlayerFormLittleMaid.mainModel;
    		mod_PFLM_PlayerFormLittleMaid.textureModel[1] = modelDataPlayerFormLittleMaid.modelArmorChestplate;
    		mod_PFLM_PlayerFormLittleMaid.textureModel[2] = modelDataPlayerFormLittleMaid.modelArmor;
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
    	modelDataPlayerFormLittleMaid.mainModel.handedness =
    			modelDataPlayerFormLittleMaid.modelArmor.handedness =
    			modelDataPlayerFormLittleMaid.modelArmorChestplate.handedness = modelDataPlayerFormLittleMaid.handedness = i;
    	Modchu_ItemRenderer.flipHorizontal = i == 0 ? false : true;
    	Modchu_ItemRenderer.leftHandedness = i == 0 ? false : true;
    }
//-@-132
    protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	if (!par1EntityLiving.getHasActivePotion()
    			| !mod_PFLM_PlayerFormLittleMaid.useInvisibilityBody) {
    		this.loadDownloadableImageTexture(par1EntityLiving.skinUrl, par1EntityLiving.getTexture());
    		this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
    	}
    }
//@-@132
/*//b181delete
    protected void renderModel(EntityLiving entityliving, float f, float f1, float f2, float f3, float f4, float f5)
    {
        loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getEntityTexture());
        modelMain.render(entityliving, f, f1, f2, f3, f4, f5);
    }
*///b181delete
}
