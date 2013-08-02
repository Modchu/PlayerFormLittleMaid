package net.minecraft.src;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
/*//152delete
import net.minecraft.client.Minecraft;
*///152delete
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerMaster extends RenderPlayer
{
	public static HashMap<Entity, PFLM_ModelData> playerData = new HashMap();
	public static String[] armorFilename;
	private static final ItemStack[] dummyArmorItemStack = {new ItemStack(Item.helmetDiamond), new ItemStack(Item.plateDiamond), new ItemStack(Item.legsDiamond), new ItemStack(Item.bootsDiamond)};
	private static Object mc = mod_PFLM_PlayerFormLittleMaid.getMinecraft();
	public static boolean resetFlag = false;
	public static boolean textureResetFlag = false;
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
	private boolean isSizeMultiplier = false;
	private boolean shadersHurtFlashFlag = false;
	private Method sizeMultiplier;
	public static Object pflm_RenderPlayerSmart;
//-@-125
	public static Object pflm_RenderRenderSmart;
//@-@125
    // b173deleteprivate RenderBlocks renderBlocks;

	public PFLM_RenderPlayerMaster() {
		armorFilename = 	(String[]) Modchu_Reflect.getFieldObject(RenderBiped.class, "field_82424_k", "bipedArmorFilenamePrefix");
		sizeMultiplier = Modchu_Reflect.getMethod(Entity.class, "getSizeMultiplier", -1);
		isSizeMultiplier = sizeMultiplier != null;
		if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			//Modchu_Debug.mDebug("PFLM_RenderPlayerMaster() isSmartMoving");
			pflm_RenderPlayerSmart = Modchu_Reflect.newInstance("PFLM_RenderPlayerSmart", new Class[]{ PFLM_RenderPlayerMaster.class }, new Object[]{ this });
//-@-125
			pflm_RenderRenderSmart = Modchu_Reflect.newInstance("PFLM_RenderRenderSmart", new Class[]{ PFLM_RenderPlayerMaster.class }, new Object[]{ this });
//@-@125
		}
		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
			if (Modchu_Reflect.getMethod("Shaders", "setEntityHurtFlash", new Class[]{ int.class, int.class }) != null
					&& Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash") != null) {
				shadersHurtFlashFlag = true;
			}
		}
		// b173deleterenderBlocks = new RenderBlocks();
	}

    protected int setArmorModel(EntityPlayer entityplayer, int i, float f) {
    	return setArmorModel(null, entityplayer, i, f, 0);
    }

    protected int setArmorModel(MultiModelBaseBiped model, EntityPlayer entityplayer, int i, float f, int i2) {
    	PFLM_ModelData modelData = getPlayerData(entityplayer);
    	/*b181//*/byte byte0 = -1;
    	//b181 deleteboolean byte0 = false;
    	if (modelData != null) ;else return byte0;
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return byte0;
    	// アーマーの表示設定
    	if (model != null) {
    	} else {
    		modelData.modelFATT.renderParts = i;
    	}
    	ItemStack is = entityplayer.inventory.armorItemInSlot(i);
    	if (is != null && is.stackSize > 0) {
    		if (model != null) {
    			model.showArmorParts(modelData, i, i2);
    		} else {
    			if (modelData.modelFATT.modelInner instanceof MultiModelBaseBiped) {
    				((MultiModelBaseBiped) modelData.modelFATT.modelInner).showArmorParts(modelData, i, 0);
    				((MultiModelBaseBiped) modelData.modelFATT.modelOuter).showArmorParts(modelData, i, 1);
    			} else {
    				modelData.modelFATT.showArmorParts(i);
    			}
    		}
//-@-b181
    		byte0 = (byte) (is.isItemEnchanted() ? 15 : 1);
//@-@b181
    		armorTextureSetting(entityplayer, modelData, is, i);
    		boolean flag1 = i == 1 ? true : false;
    		boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelData.modelMain.model);
    		if (isBiped) {
    			if (model != null) {
    				model.setArmorBipedRightLegShowModel(modelData, flag1);
    				model.setArmorBipedLeftLegShowModel(modelData, flag1);
    			} else {
    				if (modelData.modelFATT.modelInner instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelData.modelFATT.modelInner).setArmorBipedRightLegShowModel(modelData, flag1);
    				if (modelData.modelFATT.modelInner instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelData.modelFATT.modelInner).setArmorBipedLeftLegShowModel(modelData, flag1);
    			}
    		}
    	}
    	return byte0;
   }

    private void armorTextureSetting(EntityPlayer entityplayer, PFLM_ModelData modelData, ItemStack is, int i) {
    	int i2 = i;
    	String t = (String) modelData.getCapsValue(modelData.caps_textureArmorName);
    	//Modchu_Debug.mDebug("setArmorModel t="+t);
    	boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelData.modelMain.model);
    	if (t != null) ;else t = isBiped ? "Biped" : "default";
    	if (modelData.modelFATT.modelOuter != null
    			&& modelData.modelFATT.modelInner != null) {
    	} else {
    		modelArmorInit(entityplayer, modelData, t);
    	}
    	if (isBiped) {
    		ItemArmor itemarmor = null;
    		boolean flag = false;
    		Item item = is.getItem();
    		if(item instanceof ItemArmor) {
    			itemarmor = (ItemArmor)item;
    			flag = itemarmor != null && is.stackSize > 0;
    		} else {
				modelData.modelFATT.textureInner[i] = null;
				modelData.modelFATT.textureOuter[i] = null;
    			return;
    		}
    		if (flag) {
//-@-125
    			if (mod_Modchu_ModchuLib.isForge) {
    	    		String a1 = itemarmor.renderIndex < armorFilename.length ? armorFilename[itemarmor.renderIndex] : armorFilename[armorFilename.length - 1];
/*//147delete
    				String t2 = (String) Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ ItemStack.class, String.class }, null, new Object[]{ is, "/armor/" + a1 + "_" + 2 + ".png" });
    				String t1 = (String) Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ ItemStack.class, String.class }, null, new Object[]{ is, "/armor/" + a1 + "_" + 1 + ".png" });
*///147delete
//-@-147
/*//152delete
    				String t2 = (String) Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ Entity.class, ItemStack.class, String.class, int.class, int.class, String.class }, null, new Object[]{ entityplayer, is, "/armor/" + a1 + "_" + 2 + ".png", i, 2, null });
    				String t1 = (String) Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ Entity.class, ItemStack.class, String.class, int.class, int.class, String.class }, null, new Object[]{ entityplayer, is, "/armor/" + a1 + "_" + 1 + ".png", i, 1, null });
*///152delete
//@-@147
    				Object t1 = Modchu_Reflect.invokeMethod(RenderBiped.class, "getArmorResource", new Class[]{ Entity.class, ItemStack.class, int.class, String.class }, new Object[]{ entityplayer, is, 1, null });
    				Object t2 = Modchu_Reflect.invokeMethod(RenderBiped.class, "getArmorResource", new Class[]{ Entity.class, ItemStack.class, int.class, String.class }, new Object[]{ entityplayer, is, 2, null });
    				if (i == 1) {
    					//Modchu_Debug.mDebug("i="+i+" t2="+t2+" t1="+t1);
    					Object[] t3 = modelData.modelFATT.textureInner;
    					t3[i] = t2;
    					Modchu_Reflect.setFieldObject("MMM_ModelBaseDuo", "textureInner", modelData.modelFATT, t3);
    				}
    				Object[] t4 = modelData.modelFATT.textureOuter;
    				t4[i] = t1;
    				Modchu_Reflect.setFieldObject("MMM_ModelBaseDuo", "textureOuter", modelData.modelFATT, t4);
    			} else {
//@-@125
    				modelData.modelFATT.textureInner[i] = RenderBiped.func_110857_a(itemarmor, 2);
    				modelData.modelFATT.textureOuter[i] = RenderBiped.func_110857_a(itemarmor, 1);
//-@-125
    			}
//@-@125
    		}
    	} else {
    		modelData.modelFATT.textureInner[i] = mod_Modchu_ModchuLib.textureManagerGetArmorTexture(t, 64, is);
    		modelData.modelFATT.textureOuter[i] = mod_Modchu_ModchuLib.textureManagerGetArmorTexture(t, 80, is);
    	}
    	//Modchu_Debug.mlDebug("modelData.modelFATT.textureOuter["+i+"]="+modelData.modelFATT.textureOuter[i]);
    	//Modchu_Debug.mlDebug("modelData.modelFATT.textureInner["+i+"]="+modelData.modelFATT.textureInner[i]);
    }

    protected int shouldRenderPass(Entity entityliving, int i, float f)
    {
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer)entityliving);
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if (modelData != null
    			&& !(currentScreen instanceof PFLM_GuiOthersPlayer)) ;else return -1;
    	ResourceLocation t = null;
    	if (i < 4 && modelData.modelFATT.modelOuter != null)
    	{
//-@-b173
    		if (modelData.modelFATT.textureOuter == null) return -1;
//@-@b173
    		// b173deleteif (modelData.modelFATT.textureOuter == null) return false;
    		t = modelData.modelFATT.textureOuter[i];
    		if (t == null)
    		{
//-@-b181
    			return -1;
//@-@b181
    			//b181 deletereturn false;
    		} else {
    			setRenderPassModel(modelData.modelFATT);
//-@-b181
    			return 1;
//@-@b181
    			//b181 deletereturn true;
    		}
    	}
    	if (i < 8 && modelData.modelFATT.modelInner != null)
    	{
//-@-b173
    		if (modelData.modelFATT.textureInner == null) return -1;
//@-@b173
    		// b173deleteif (modelData.modelFATT.textureInner == null) return false;
    		t = modelData.modelFATT.textureInner[i - 4];
    		if (t == null) {
//-@-b181
    			return -1;
//@-@b181
    			//b181 deletereturn false;
    		} else {
    			//loadTexture(t);
    			setRenderPassModel(modelData.modelFATT);
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

    protected void renderPlayerScale(EntityPlayer entityliving, float f)
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
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer)entityliving);
    	if (modelData != null) ;else return;
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	float f1 = modelData.getCapsValueBoolean(modelData.caps_isPlayer) ? PFLM_Gui.modelScale : modelData.getCapsValueFloat(modelData.caps_modelScale);
    	if (f1 == 0.0F) f1 = ((MultiModelBaseBiped) modelData.modelMain.model).getModelScale();
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
    	Package pac = getClass().getPackage();
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
    	Package pac = getClass().getPackage();
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

    public void doRenderLivingPFLM(PFLM_ModelData modelData, Entity entityliving, double d, double d1, double d2,
            float f, float f1) {
    	modelData.modelMain.setEntityCaps(modelData);
    	modelData.modelMain.setRender(this);
    	modelData.setCapsValue(modelData.caps_Entity, entityliving);
    	//oldDoRenderLivingPFLM(modelData, entityliving, d, d1, d2, f, f1);
    }

    public void oldDoRenderLivingPFLM(PFLM_ModelData modelData, Entity entityliving, double d, double d1, double d2,
            float f, float f1) {
    	GL11.glPushMatrix();

    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders
    			&& !shadersHurtFlashFlag) {
    		//Shaders.glDisableWrapper(k1);
    		shadersGlDisableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glDisable(GL11.GL_CULL_FACE);
    	}
    	int PFLMVersion = mod_PFLM_PlayerFormLittleMaid.getPFLMVersion();
    	Class EntityLivingBase = Modchu_Reflect.loadClass("EntityLivingBase");
    	Class Minecraft = Modchu_Reflect.loadClass("Minecraft");
    	try
    	{
    		float f2;
    		float f3;
    		float f4;
    		float prevRenderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70760_ar", "prevRenderYawOffset", entityliving);
    		float renderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70761_aq", "renderYawOffset", entityliving);
    		float prevRotationYawHead = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70758_at", "prevRotationYawHead", entityliving);
    		float rotationYawHead = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70759_as", "rotationYawHead", entityliving);
    		float prevLimbYaw = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70722_aY", "prevLimbYaw", entityliving);
    		float limbYaw = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70721_aZ", "limbYaw", entityliving);
    		float limbSwing = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70754_ba", "limbSwing", entityliving);
    		Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    		if (PFLMVersion < 140
    				| (currentScreen != null
    				&& (currentScreen instanceof PFLM_Gui
    				| currentScreen instanceof PFLM_GuiOthersPlayer
    				| currentScreen instanceof PFLM_GuiModelSelect))) {
    			f2 = prevRenderYawOffset + (renderYawOffset - prevRenderYawOffset) * f1;
    			f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
    			f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
    		} else {
    			f2 = interpolateRotation(prevRenderYawOffset, renderYawOffset, f1);
    			f3 = interpolateRotation(prevRotationYawHead, rotationYawHead, f1);
    			f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
    		}

    		renderLivingAt(entityliving, d, d1, d2);
    		float f5 = handleRotationFloat((Entity) entityliving, f1);
    		rotateCorpse((EntityPlayer) entityliving, f5, f2, f1);
    		float f6 = 0.0625F;
    		if (mod_PFLM_PlayerFormLittleMaid.isShaders
        			&& !shadersHurtFlashFlag) {
    			//Shaders.glEnableWrapper(GL12.GL_RESCALE_NORMAL);
    			shadersGlEnableWrapper(GL12.GL_RESCALE_NORMAL);
    		} else {
    			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    		}
    		GL11.glScalef(-1F, -1F, 1.0F);
    		preRenderCallback((Entity) entityliving, f1);
    		GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
    		float f7 = prevLimbYaw + (limbYaw - prevLimbYaw) * f1;
    		float f8 = limbSwing - limbYaw * (1.0F - f1);
    		if (PFLMVersion > 89
    				&& (Boolean) Modchu_Reflect.invokeMethod(EntityLivingBase, "func_70631_g_", "isChild", entityliving))
    		{
    			f8 *= 3F;
    		}
    		if (f7 > 1.0F)
    		{
    			f7 = 1.0F;
    		}

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders
        			&& !shadersHurtFlashFlag) {
    			//Shaders.glEnableWrapper(GL11.GL_ALPHA_TEST);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders
    	    			&& !shadersHurtFlashFlag) {
    				//Shaders.glEnableWrapper(GL11.GL_BLEND);
    				shadersGlEnableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glEnable(GL11.GL_BLEND);
    			}
    			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    		}
    		else
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders
    	    			&& !shadersHurtFlashFlag) {
    				//Shaders.glDisableWrapper(k1);
    				shadersGlDisableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glDisable(GL11.GL_BLEND);
    			}
    		}

    		modelData.modelMain.setEntityCaps(modelData);
    		modelData.modelMain.setRender(this);
    		modelData.setCapsValue(modelData.caps_Entity, entityliving);
    		Modchu_Reflect.invokeMethod(modelData.modelMain.getClass(), "func_78086_a", "setLivingAnimations", new Class[]{ EntityLivingBase, float.class, float.class, float.class }, modelData.modelMain, new Object[]{ entityliving, f8, f7, f1 });
    		Modchu_Reflect.invokeMethod(this.getClass(), "renderModel", new Class[]{ Entity.class, float.class, float.class, float.class, float.class, float.class, float.class }, this, new Object[]{ entityliving, f8, f7, f5, f3 - f2, f4, f6 });
    		//renderModel(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    		float f9 = 1.0F;
    		if (PFLMVersion < 80) {
    			if (currentScreen == null | currentScreen instanceof GuiIngameMenu) f9 = (Float) Modchu_Reflect.invokeMethod(Entity.class, "func_382_a", "getEntityBrightness", new Class[]{ float.class }, entityliving, new Object[]{ f1 });
    		}
    		modelData.modelFATT.showAllParts();
    		for (int i = 0; i < 4; i++)
    		{
    			int j = setArmorModel((EntityPlayer) entityliving, i, f);

    			if (j <= 0)
    			{
    				continue;
    			}

    			for (int l = 0; l < 5; l += 4)
    			{
    				if (shouldRenderPass((Entity) entityliving, i + l, f1) < 0)
    				{
    					continue;
    				}

    				float f10 = 1.0F;
    				if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders
    			    			&& !shadersHurtFlashFlag) {
    						//Shaders.glEnableWrapper(GL11.GL_BLEND);
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    				}
    				f10 = mod_PFLM_PlayerFormLittleMaid.transparency;
    				GL11.glColor4f(f9, f9, f9, f10);
    				modelData.modelMain.setEntityCaps(modelData);
    				Modchu_Reflect.invokeMethod(renderPassModel.getClass(), "setLivingAnimations", new Class[]{ EntityLivingBase, float.class, float.class, float.class }, renderPassModel, new Object[]{ entityliving, f8, f7, f1});
    				//renderPassModel.setLivingAnimations(entityliving, f8, f7, f1);
    				((MMM_ModelBaseDuo) renderPassModel).setArmorRendering(true);
    				if ((PFLMVersion > 129
    						&& !mod_Modchu_ModchuLib.useInvisibilityArmor)
    						| (mod_Modchu_ModchuLib.useInvisibilityArmor
    								&& !entityliving.isInvisible())
    								| PFLMVersion < 130) {
    					renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    				}
    				if (mod_PFLM_PlayerFormLittleMaid.transparency != 1.0F) GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    				if (PFLMVersion < 90
    						| j != 15) {
    					continue;
    				}

    				float f11 = (float)entityliving.ticksExisted + f1;
    				func_110776_a(new ResourceLocation("%blur%/misc/glint.png"));
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders
    		    			&& !shadersHurtFlashFlag) {
    					//Shaders.glEnableWrapper(GL11.GL_BLEND);
    					shadersGlEnableWrapper(GL11.GL_BLEND);
    				} else {
    					GL11.glEnable(GL11.GL_BLEND);
    				}
    				float f13 = 0.5F;
    				GL11.glColor4f(f13, f13, f13, 1.0F);
    				GL11.glDepthFunc(GL11.GL_EQUAL);
    				GL11.glDepthMask(false);

    				for (int j1 = 0; j1 < 2; j1++) {
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders
    			    			&& !shadersHurtFlashFlag) {
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

    					if ((PFLMVersion > 129
    							&& !mod_Modchu_ModchuLib.useInvisibilityArmor)
    							| (PFLMVersion > 129
    									&& mod_Modchu_ModchuLib.useInvisibilityArmor
    									&& !entityliving.isInvisible())
    									| PFLMVersion < 130) {
    						renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    					}
    				}

    				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    				GL11.glMatrixMode(GL11.GL_TEXTURE);
    				GL11.glDepthMask(true);
    				GL11.glLoadIdentity();
    				GL11.glMatrixMode(GL11.GL_MODELVIEW);
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders
    		    			&& !shadersHurtFlashFlag) {
    					//Shaders.glEnableWrapper(GL11.GL_LIGHTING);
    					shadersGlEnableWrapper(GL11.GL_LIGHTING);
    				} else {
    					GL11.glEnable(GL11.GL_LIGHTING);
    				}

    				if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders
    			    			&& !shadersHurtFlashFlag) {
    						//Shaders.glEnableWrapper(GL11.GL_BLEND);
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    				}
    				else
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders
    			    			&& !shadersHurtFlashFlag) {
    						//Shaders.glDisableWrapper(GL11.GL_BLEND);
    						shadersGlDisableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glDisable(GL11.GL_BLEND);
    					}
    				}

    				GL11.glDepthFunc(GL11.GL_LEQUAL);
    			}

    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders
        			&& !shadersHurtFlashFlag) {
    			//Shaders.glDisableWrapper(GL11.GL_BLEND);
    			shadersGlDisableWrapper(GL11.GL_BLEND);
    			//Shaders.glEnableWrapper(GL11.GL_ALPHA_TEST);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glDisable(GL11.GL_BLEND);
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		if (PFLMVersion < 80) GL11.glColor4f(f9, f9, f9, 1.0F);
    		modelData.modelMain.setEntityCaps(modelData);
/*
    		if (PFLMVersion > 129
    				&& entityliving.isInvisible()
    				| PFLMVersion < 130) {
*/
    			modelData.modelMain.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6, entityliving);
    			modelData.modelFATT.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6, entityliving);
/*
    		}
*/
    		if (mod_PFLM_PlayerFormLittleMaid.isShaders
    				&& shadersHurtFlashFlag
    				&& (Boolean) Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash")) {
    			Modchu_Reflect.invokeMethod("Shaders", "setEntityHurtFlash", new Class[]{ int.class, int.class }, new Object[]{ 0, 0 });
    		}
    		renderEquippedItems(entityliving, f1);
    		boolean hurtFlag = mod_PFLM_PlayerFormLittleMaid.isShaders
    				&& shadersHurtFlashFlag
    				&& !(Boolean) Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash")
    				? true : false;
    		if (!hurtFlag) {
    			f9 = entityliving.getBrightness(f1);
    			int k = getColorMultiplier(entityliving, f9, f1);
    			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders
    					&& !shadersHurtFlashFlag) {
    				//Shaders.glDisableWrapper(GL11.GL_TEXTURE_2D);
    				shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
    			} else {
    				GL11.glDisable(GL11.GL_TEXTURE_2D);
    			}
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders
    					&& shadersHurtFlashFlag) Modchu_Reflect.invokeMethod("Shaders", "disableLightmap");
    			OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

    			int hurtTime = (Integer) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70737_aN", "hurtTime", entityliving);
    			int deathTime = (Integer) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70725_aQ", "deathTime", entityliving);
    			if ((k >> 24 & 0xff) > 0 || hurtTime > 0 || deathTime > 0)
    			{
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders
    						&& shadersHurtFlashFlag) Modchu_Reflect.invokeMethod("Shaders", "beginLivingDamage");
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders
    						&& !shadersHurtFlashFlag) {
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

    				if (hurtTime > 0 || deathTime > 0)
    				{
    					GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
    					modelData.modelMain.model.render(modelData, f8, f7, f5, f3 - f2, f4, f6, true);

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
    					modelData.modelMain.model.render(modelData, f8, f7, f5, f3 - f2, f4, f6, true);

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
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders
    						&& !shadersHurtFlashFlag) {
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
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders
    						&& shadersHurtFlashFlag) Modchu_Reflect.invokeMethod("Shaders", "endLivingDamage");
    			}

    			if (mod_PFLM_PlayerFormLittleMaid.isShaders
    					&& !shadersHurtFlashFlag) {
    				//Shaders.glDisableWrapper(GL12.GL_RESCALE_NORMAL);
    				shadersGlDisableWrapper(GL12.GL_RESCALE_NORMAL);
    			} else {
    				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    			}
    		}
    	}
    	catch (Exception exception)
    	{
    		exception.printStackTrace();
    	}

    	OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders
    			&& !shadersHurtFlashFlag) {
    		//Shaders.glEnableWrapper(GL11.GL_TEXTURE_2D);
    		shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
    	} else {
    		GL11.glEnable(GL11.GL_TEXTURE_2D);
    	}
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders
    			&& !shadersHurtFlashFlag) {
    		//Shaders.glEnableWrapper(GL11.GL_CULL_FACE);
    		shadersGlEnableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glEnable(GL11.GL_CULL_FACE);
    	}
    	GL11.glPopMatrix();
    	Modchu_Reflect.invokeMethod("RendererLivingEntity", "func_77033_b", "passSpecialRender", new Class[]{ EntityLivingBase, double.class, double.class, double.class }, this, new Object[]{ entityliving, d, d1, d2});
    	//passSpecialRender(entityliving, d, d1, d2);
    }

    public void doRenderPlayerFormLittleMaid(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1) {
    	PFLM_ModelData modelData = getPlayerData(entityplayer);
    	if (modelData != null) ;else return;
    	if (textureResetFlag) {
    		textureResetFlag = false;
    		modelTextureReset(entityplayer, modelData);
    		modelTextureArmorReset(modelData);
    	}
    	doRenderSetting(entityplayer, modelData);
    	sitSleepResetCheck(modelData, entityplayer);
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
    		Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderPlayer", new Class[]{ EntityPlayer.class, double.class , double.class, double.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderPlayerSmart, new Object[]{ entityplayer, d, d1, d2, f, f1, modelData });
//-@-125
    		Modchu_Reflect.invokeMethod("PFLM_RenderRenderSmart", "renderPlayer", new Class[]{ EntityPlayer.class, double.class , double.class, double.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderRenderSmart, new Object[]{ entityplayer, d, d1, d2, f, f1, modelData });
//@-@125
    	}

    	float f8 = entityplayer.limbSwing - entityplayer.limbYaw * (1.0F - f1);
    	modelData.modelMain.setEntityCaps(modelData);
    	waitModeSetting(modelData, f8);
    	if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
    		modelData.modelMain.setCapsValue(modelData.caps_isWait, mod_PFLM_PlayerFormLittleMaid.isWait);
    	} else {
    		modelData.modelMain.setCapsValue(modelData.caps_isWait, modelData.getCapsValue(modelData.caps_isWait));
    	}
    	float[] f9 = new float[2];
    	float f10 = renderSwingProgress(entityplayer, f1);
    	switch(Modchu_ModelCapsHelper.getCapsValueInt(modelData, modelData.caps_dominantArm)) {
    		case 0:
    			f9[0] = f10;
    			f9[1] = 0.0F;
    		break;
    		case 1:
    			f9[0] = 0.0F;
    			f9[1] = f10;
    		break;
    	}
    	modelData.modelMain.setCapsValue(modelData.caps_onGround, f9[0], f9[1]);
    	modelData.setCapsValue(modelData.caps_isOpenInv, d == 0.0D && d1 == 0.0D && d2 == 0.0D && f == 0.0F && f1 == 1.0F);
    	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    	modelData.modelMain.setCapsValue(modelData.caps_heldItemLeft, 0);
    	modelData.modelMain.setCapsValue(modelData.caps_heldItemRight, 0);
//-@-b173
    	if (itemstack != null && entityplayer.getItemInUseCount() > 0) {
    		EnumAction enumaction = itemstack.getItemUseAction();
    		if (enumaction == EnumAction.block) {
    			if (modelData.modelMain.model.isItemHolder()) {
    				modelData.modelMain.setCapsValue(modelData.caps_heldItemLeft, 3);
    				modelData.modelMain.setCapsValue(modelData.caps_heldItemRight, 3);
    			}
    		} else if (enumaction == EnumAction.bow) {
    			modelData.modelMain.setCapsValue(modelData.caps_aimedBow, true);
    		}
    	}
//@-@b173
    	double d3 = doRenderSettingY(entityplayer, modelData , d1);
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if (!(currentScreen instanceof PFLM_GuiOthersPlayer)
    			&& modelData.modelMain.model != null) doRenderLivingPFLM(modelData, (Entity) entityplayer, d, d3, d2, f, f1);
/*
    	modelData.modelMain.setCapsValue(modelData.caps_aimedBow, false);
    	modelData.modelMain.setCapsValue(modelData.caps_isSneak, false);
    	modelData.modelMain.setCapsValue(modelData.caps_heldItemLeft, (Integer)0);
    	modelData.modelMain.setCapsValue(modelData.caps_heldItemRight, (Integer)0);
    	modelData.setCapsValue(modelData.caps_isOpenInv, false);
*/
    }

    public double doRenderSettingY(EntityPlayer entityplayer,
    		PFLM_ModelData modelData, double d1) {
/*
    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize
//-@-125
    			&& (Boolean) Modchu_Reflect.invokeMethod("Minecraft", "isSingleplayer", mc)
//@-@125
    			// 125delete&& thePlayer.worldObj.isRemote
    			&& !entityplayer.isRiding()) {
    		//d1 = d1 - (double)((MultiModelBaseBiped) modelData.modelMain.model).getyOffset();
    		d1 = d1 - (double)entityplayer.yOffset;
    	}
    	else
*/
    	if (mod_PFLM_PlayerFormLittleMaid.oldRender) d1 = d1 - (double)entityplayer.yOffset;
    	if (entityplayer.isSneaking() && !(entityplayer instanceof EntityPlayerSP)) d1 -= 0.125D;
    	//if (mod_PFLM_PlayerFormLittleMaid.isModelSize) d1 += 0.45D;

    	if (entityplayer.isRiding()) modelData.setCapsValue(modelData.caps_isRiding, true);
    	else modelData.setCapsValue(modelData.caps_isRiding, modelData.getCapsValueBoolean(modelData.caps_isSitting));
    	//Modchu_Debug.mDebug("entityplayer.isRiding()="+modelData.getCapsValueBoolean(modelData.caps_isRiding));
    	if (modelData.getCapsValueBoolean(modelData.caps_isRiding)) {
    		d1 += mod_PFLM_PlayerFormLittleMaid.oldRender ? 0.25D : 0.30D;
    		if (mod_PFLM_PlayerFormLittleMaid.isModelSize && mod_PFLM_PlayerFormLittleMaid.changeMode != PFLM_Gui.modeOnline)  d1 -= 0.43D;
    	}
    	if (entityplayer.isSneaking()) {
    		if (modelData.getCapsValueBoolean(modelData.caps_isRiding)) {
    			d1 -= 0.1D;
    		}
    	}
    	if (modelData.getCapsValueBoolean(modelData.caps_isSitting)) {
    		if (!mod_PFLM_PlayerFormLittleMaid.oldRender) d1 += -0.3D;
    		d1 += Modchu_ModelCapsHelper.getCapsValueDouble(modelData.modelMain.model, modelData.caps_sittingyOffset);
    	}
    	return d1;
    }

	private void doRenderSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
    	modelData.modelMain.setEntityCaps(modelData);
    	EntityPlayer thePlayer = mod_PFLM_PlayerFormLittleMaid.getThePlayer();
    	if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
    		if (mod_PFLM_PlayerFormLittleMaid.mushroomConfusion) mod_PFLM_PlayerFormLittleMaid.mushroomConfusion(entityplayer, modelData);
    	}
    	if (modelData.getCapsValueBoolean(modelData.caps_changeModelFlag)) {
    		if (mod_PFLM_PlayerFormLittleMaid.isOlddays) modelData.modelMain.setCapsValue(modelData.caps_oldwalking, (Boolean) Modchu_Reflect.getFieldObject(ModelBiped.class, "oldwalking", modelData.modelMain.model));
    		modelData.setCapsValue(modelData.caps_partsSetInit, false);
    		modelData.setCapsValue(modelData.caps_partsSetFlag, 1);
    		modelData.modelMain.setCapsValue(modelData.caps_changeModel, modelData);
    		mod_PFLM_PlayerFormLittleMaid.changeModel(entityplayer);
    		modelData.setCapsValue(modelData.caps_changeModelFlag, false);
    	}
    	setHandedness(entityplayer, modelData.getCapsValueInt(modelData.caps_dominantArm));
    	modelData.modelMain.setCapsValue(modelData.caps_isSneak, entityplayer.isSneaking());
    	modelData.modelMain.setCapsValue(modelData.caps_isRiding, entityplayer.isRiding());
    	modelData.setCapsValue(modelData.caps_isPlayer, entityplayer.username == thePlayer.username);
    	boolean shortcutKeysAction = modelData.getCapsValueBoolean(modelData.caps_shortcutKeysAction);
    	boolean shortcutKeysActionInitFlag = modelData.getCapsValueBoolean(modelData.caps_shortcutKeysActionInitFlag);
    	int actionReleaseNumber = modelData.getCapsValueInt(modelData.caps_actionReleaseNumber);
    	int runActionNumber = modelData.getCapsValueInt(modelData.caps_runActionNumber);
    	if (shortcutKeysAction) {
    		//Modchu_Debug.mDebug("doRenderSetting actionReleaseNumber="+actionReleaseNumber);
    		if (actionReleaseNumber != runActionNumber
    				&& actionReleaseNumber != 0) {
    			if (actionReleaseNumber < 0) {
    				modelData.setCapsValue(modelData.caps_actionRelease, -actionReleaseNumber);
    			} else {
    				modelData.setCapsValue(modelData.caps_actionRelease, actionReleaseNumber);
    			}
    			modelData.setCapsValue(modelData.caps_actionReleaseNumber, 0);
    			modelData.setCapsValue(modelData.caps_actionInit, runActionNumber);
    			modelData.setCapsValue(modelData.caps_shortcutKeysActionInitFlag, false);
    			shortcutKeysActionInitFlag = false;
    		}
    		float actionSpeed = getActionSpeed(modelData);
    		modelData.setCapsValue(modelData.caps_actionSpeed, actionSpeed);
    		//Modchu_Debug.mDebug("doRenderSetting actionSpeed="+actionSpeed);
    		modelData.setCapsValue(modelData.caps_actionFlag, true);
    		if (shortcutKeysActionInitFlag) {
    			modelData.setCapsValue(modelData.caps_shortcutKeysActionInitFlag, false);
    			modelData.setCapsValue(modelData.caps_actionInit, runActionNumber);
    			modelData.setCapsValue(modelData.caps_actionReleaseNumber, runActionNumber);
    		}
    	} else {
    		if (actionReleaseNumber > 0) {
    			modelData.setCapsValue(modelData.caps_actionRelease, actionReleaseNumber);
    			modelData.setCapsValue(modelData.caps_actionReleaseNumber, 0);
    		}
    		if (!shortcutKeysActionInitFlag) {
    			modelData.setCapsValue(modelData.caps_shortcutKeysActionInitFlag, true);
    			modelData.setCapsValue(modelData.caps_actionRelease, runActionNumber);
    			modelData.setCapsValue(modelData.caps_actionReleaseNumber, 0);
    			modelData.setCapsValue(modelData.caps_runActionNumber, 0);
    		}
    	}
    	modelData.modelFATT.setCapsValue(modelData.caps_syncModel, modelData, modelData.modelMain.model);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if(currentScreen instanceof GuiSelectWorld) return;
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	doRenderPlayerFormLittleMaid((EntityPlayer)entity, d, d1, d2, f, f1);
    }

    private void sitSleepResetCheck(PFLM_ModelData modelData, EntityPlayer entityplayer) {
    	float f1 = entityplayer.moveForward * entityplayer.moveForward + entityplayer.moveStrafing * entityplayer.moveStrafing;
    	float f2 = entityplayer.limbSwing - Modchu_ModelCapsHelper.getCapsValueFloat(modelData, modelData.caps_tempLimbSwing);
    	if (entityplayer.isJumping
    			| entityplayer.sleeping
/*//152delete
    			| entityplayer.isRiding()
*///152delete
    			| ((Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isSitting)
    					&& ((double) f1 > 0.20000000000000001D
    							| f2 != 0.0F)))
    			| ((Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isSleeping)
    					&& (f1 > 0.0F
    							| f2 != 0.0F)))) {
    		//Modchu_Debug.mDebug("Sitting 自動OFF");
    		modelData.modelMain.setCapsValue(modelData.caps_isRiding, false);
    		modelData.setCapsValue(modelData.caps_isSitting, false);
    		modelData.setCapsValue(modelData.caps_isSleeping, false);
    	}
    	modelData.setCapsValue(modelData.caps_tempLimbSwing, entityplayer.limbSwing);
    }

    protected void renderEquippedItems(Entity entityliving, float f)
    {
    	renderSpecials((EntityPlayer)entityliving, f);
    }

    protected void renderSpecials(EntityPlayer entityplayer, float f)
    {
    	PFLM_ModelData modelData = getPlayerData(entityplayer);
    	if (modelData != null) ;else return;
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	modelData.modelMain.setEntityCaps(modelData);
    	modelData.modelMain.setRender(this);
    	modelData.modelMain.renderItems(entityplayer, this);
    	if (entityplayer.username.equals("deadmau5")) {
    		int PFLMVersion = mod_PFLM_PlayerFormLittleMaid.getPFLMVersion();
    		Class EntityLivingBase = Modchu_Reflect.loadClass("EntityLivingBase");
    		Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");
    		if (PFLMVersion > 159
    				&& (Boolean) Modchu_Reflect.invokeMethod(ThreadDownloadImageData.class, "func_110557_a", Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110309_l", entityplayer))
    				//&& ((AbstractClientPlayer) entityplayer).func_110309_l().func_110557_a())
    				| (PFLMVersion < 160
    						&& (Boolean) Modchu_Reflect.invokeMethod(Render.class, "func_76984_a", "loadDownloadableImageTexture", new Class[]{ String.class, String.class }, this, new Object[]{ (String) Modchu_Reflect.getFieldObject(Entity.class, "field_20047_bv", "skinUrl", entityplayer), (String)null }))) {
    			if (PFLMVersion > 159) Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110776_a", this);
    			// (((AbstractClientPlayer) entityplayer).func_110306_p());
    			float prevRenderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70760_ar", "prevRenderYawOffset", entityplayer);
    			float renderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70761_aq", "renderYawOffset", entityplayer);
    			for (int i = 0; i < 2; i++)
    			{
    				float f2 = (entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f) - (prevRenderYawOffset + (renderYawOffset - prevRenderYawOffset) * f);
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
    				((MultiModelBaseBiped) modelData.modelMain.model).renderEars(0.0625F);
    				GL11.glPopMatrix();
    			}
    		}
    		if (renderManager != null
    				&& (PFLMVersion > 159
    						&& (Boolean) Modchu_Reflect.invokeMethod("ThreadDownloadImageData", "func_110557_a", Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110310_o", entityplayer))
    				//&& ((AbstractClientPlayer) entityplayer).func_110310_o().func_110557_a()
    				| (PFLMVersion < 160
    						&& (String) Modchu_Reflect.getFieldObject(Entity.class, "field_622_aY", "cloakUrl", entityplayer) != null
    						&& (Boolean) Modchu_Reflect.invokeMethod(Render.class, "func_76984_a", "loadDownloadableImageTexture", new Class[]{ String.class, String.class }, this, new Object[]{ (String) Modchu_Reflect.getFieldObject(Entity.class, "field_622_aY", "cloakUrl", entityplayer), (String)null })))
    				&& ((PFLMVersion > 129
    						&& !(Boolean) Modchu_Reflect.invokeMethod(Entity.class, "func_82150_aj", "isInvisible", entityplayer)
    						&& !(Boolean) Modchu_Reflect.invokeMethod(EntityPlayer.class, "func_82241_s", "getHideCape", entityplayer)
    						| PFLMVersion < 130))) {
    			GL11.glPushMatrix();
    			GL11.glTranslatef(0.0F, 0.0F, 0.125F);
    			double d = (entityplayer.field_71091_bM + (entityplayer.field_71094_bP - entityplayer.field_71091_bM) * (double)f) - (entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)f);
    			double d1 = (entityplayer.field_71096_bN + (entityplayer.field_71095_bQ - entityplayer.field_71096_bN) * (double)f) - (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)f);
    			double d2 = (entityplayer.field_71097_bO + (entityplayer.field_71085_bR - entityplayer.field_71097_bO) * (double)f) - (entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)f);
    			float prevRenderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70760_ar", "prevRenderYawOffset", entityplayer);
    			float renderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70761_aq", "renderYawOffset", entityplayer);
    			float f11 = prevRenderYawOffset + (renderYawOffset - prevRenderYawOffset) * f;
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
    			((MultiModelBaseBiped) modelData.modelMain.model).renderCloak(0.0625F);
    			GL11.glPopMatrix();
    		}
    	}
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer entityplayer) {
    	renderFirstPersonArm(entityplayer, 2);
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer, int i) {
    	//olddays導入時に2以外のint付きで呼ばれる。
    	EntityPlayer thePlayer = mod_PFLM_PlayerFormLittleMaid.getThePlayer();
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if(currentScreen instanceof GuiSelectWorld) return;
    	PFLM_ModelData modelData = getPlayerData(entityplayer);
    	if (modelData != null) ;else return;
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return;
    	if (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
    		modelData.modelMain.setCapsValue(modelData.caps_firstPerson, false);
    		return;
    	}
    	doRenderSetting(entityplayer, modelData);
    	float var2 = 1.0F;
    	GL11.glColor3f(var2, var2, var2);
    	modelData.modelMain.setCapsValue(modelData.caps_firstPerson, true);
    	if (i >= 2
    			&& i != 1) {
    		if (modelData.modelMain != null) {
    			modelData.modelMain.setCapsValue(modelData.caps_onGround, 0.0F, 0.0F);
    			if (modelData.modelMain != null) modelData.modelMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, thePlayer);
    		}
    		if (modelData.modelFATT != null) modelData.modelFATT.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, thePlayer);
    	}
    	//Modchu_Debug.Debug("renderFirstPersonArm ResourceLocation="+((ResourceLocation) modelData.getCapsValue(modelData.caps_ResourceLocation)).func_110623_a());
    	if (modelData.modelMain.model != null
    			&& modelData.getCapsValue(modelData.caps_ResourceLocation) != null
    			&& getRenderManager() != null
    			&& getRenderManager().renderEngine != null) {
    		func_110776_a((ResourceLocation) modelData.getCapsValue(modelData.caps_ResourceLocation));
/*//125delete
    		((MultiModelBaseBiped) modelData.modelMain.model).entity = entityplayer;
*///125delete
    		//Modchu_Debug.Debug("modelData.modelMain.model != null ? ="+(modelData.modelMain.model != null));
    		modelData.modelMain.model.renderFirstPersonHand(modelData);
    		//renderFirstPersonArmorRender(modelData, entityplayer, 0.0D, 0.0D, 0.0D, 0.0F, 0.0625F);
    	} else if (modelData.modelMain.model != null
    			&& mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelData.modelMain.model)) {
    		modelData.modelMain.model.renderFirstPersonHand(modelData);
    	}
    	modelData.modelMain.setCapsValue(modelData.caps_firstPerson, false);
    }

    public static boolean isActivatedForPlayer(EntityPlayer entityplayer) {
    	PFLM_ModelData modelData = getPlayerData(entityplayer);
    	if (modelData != null) ;else return false;
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return false;
    	return modelData.getCapsValueBoolean(modelData.caps_isActivated);
    }

    public static PFLM_ModelData getPlayerData(EntityPlayer entityplayer) {
    	if (entityplayer != null) ;else return null;
    	PFLM_ModelData modelData = playerData.get(entityplayer);

    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_6313_p", "currentScreen", mc);
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return null;
    	boolean b = false;
    	if (modelData != null) {
    		//Modchu_Debug.Debug("initFlag="+modelData.initFlag);
    		if (modelData.getCapsValueInt(modelData.caps_initFlag) != 2) b = true;
    	} else b = true;
    	if (b
    			| resetFlag) {
    		if (resetFlag) {
    			resetFlag = false;
    			mod_PFLM_PlayerFormLittleMaid.clearPlayers();
    			modelData = null;
    		}
    		modelData = loadPlayerData(entityplayer, modelData);
    		playerData.put(entityplayer, modelData);
    	}
    	return modelData;
    }

	private static PFLM_ModelData loadPlayerData(EntityPlayer entityplayer)
	{
		PFLM_ModelData modelData = new PFLM_ModelData((RenderLiving) RenderManager.instance.getEntityRenderObject(entityplayer));
		return loadPlayerData(entityplayer, modelData);
	}

	private static PFLM_ModelData loadPlayerData(EntityPlayer entityplayer, PFLM_ModelData modelData)
	{
		if (entityplayer != null) ;else return null;
		if (modelData != null) ;else modelData = new PFLM_ModelData((RendererLivingEntity) RenderManager.instance.getEntityRenderObject(entityplayer));
		EntityPlayer thePlayer = mod_PFLM_PlayerFormLittleMaid.getThePlayer();
		if (thePlayer != null) ;else return null;
		modelData.owner = (EntityLivingBase) entityplayer;
		modelData.setCapsValue(modelData.caps_isPlayer, entityplayer.username == thePlayer.username);
//if (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)) Modchu_Debug.mDebug("@@@@@isPlayer false!!");
		BufferedImage bufferedimage = null;
		// 125deleteif (!mod_PFLM_PlayerFormLittleMaid.gotchaNullCheck()) return null;

		if (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
			String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.playerLocalData.get(entityplayer.username);
			PFLM_ModelData modelData2;
			if (t != null) {
				switch (Integer.valueOf(t[4])) {
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayer:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_Player);
					modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
					modelData2 = getPlayerData(thePlayer);
					modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.textureName);
					modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
					if (modelData2 != null) modelData.setCapsValue(modelData.caps_maidColor, modelData2.getCapsValueInt(modelData.caps_maidColor));
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modeOthersSettingOffline:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_OthersIndividualSettingOffline);
					String s2 = t[0];
					modelData.setCapsValue(modelData.caps_maidColor, Integer.valueOf(t[2]));
					modelData.setCapsValue(modelData.caps_ResourceLocation, 0, mod_Modchu_ModchuLib.textureManagerGetTexture(s2, modelData.getCapsValueInt(modelData.caps_maidColor)));
					modelInit(entityplayer, modelData, s2);
					s2 = t[1];
					modelData.setCapsValue(modelData.caps_textureName, s2);
					modelData.setCapsValue(modelData.caps_textureArmorName, t[1]);
					modelArmorInit(entityplayer, modelData, s2);
					modelData.setCapsValue(modelData.caps_dominantArm, mod_Modchu_ModchuLib.integerCheck(t[5]) ? othersPlayerIndividualHandednessSetting(Integer.valueOf(t[5])) : 0);
					modelData.setCapsValue(modelData.caps_modelScale, mod_Modchu_ModchuLib.integerCheck(t[3]) ? Float.valueOf(t[3]) : 0.0F);
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayerOffline:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_PlayerOffline);
					skinMode_PlayerOfflineSetting(entityplayer, modelData);
					modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
					modelData2 = getPlayerData(thePlayer);
					modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.textureName);
					modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
					if (modelData2 != null) modelData.setCapsValue(modelData.caps_maidColor, modelData2.getCapsValueInt(modelData.caps_maidColor));
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayerOnline:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_PlayerOnline);
					modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
					modelData2 = getPlayerData(thePlayer);
					modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.textureName);
					modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
					if (modelData2 != null) modelData.setCapsValue(modelData.caps_maidColor, modelData2.getCapsValueInt(modelData.caps_maidColor));
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modeRandom:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_Random);
					skinMode_RandomSetting(entityplayer, modelData);
					modelData.setCapsValue(modelData.caps_dominantArm, mod_Modchu_ModchuLib.integerCheck(t[5]) ? othersPlayerIndividualHandednessSetting(Integer.valueOf(t[5])) : 0);
					modelData.setCapsValue(modelData.caps_modelScale, mod_Modchu_ModchuLib.integerCheck(t[3]) ? Float.valueOf(t[3]) : 0.0F);
					break;
				}
				if (modelData.getCapsValueInt(modelData.caps_skinMode) != skinMode_PlayerOnline
						&& modelData.getCapsValueInt(modelData.caps_skinMode) != skinMode_online) {
					modelData.setCapsValue(modelData.caps_initFlag, 2);
					modelData.addSendList(4);
					return modelData;
				}
			} else
			if(PFLM_GuiOthersPlayer.changeMode > 0) {
				switch (PFLM_GuiOthersPlayer.changeMode) {
				case PFLM_GuiOthersPlayer.modePlayer:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_Player);
					modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
					modelData2 = getPlayerData(thePlayer);
					modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.textureName);
					modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
					if (modelData2 != null) modelData.setCapsValue(modelData.caps_maidColor, modelData2.getCapsValueInt(modelData.caps_maidColor));
					break;
				case PFLM_GuiOthersPlayer.modeOthersSettingOffline:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_OthersSettingOffline);
					String s = mod_PFLM_PlayerFormLittleMaid.othersTextureName;
					modelData.setCapsValue(modelData.caps_maidColor, mod_PFLM_PlayerFormLittleMaid.othersMaidColor);
					modelInit(entityplayer, modelData, s);
					s = mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName;
					modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.othersTextureName);
					modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName);
					modelArmorInit(entityplayer, modelData, s);
					modelData.setCapsValue(modelData.caps_dominantArm, othersPlayerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, mod_PFLM_PlayerFormLittleMaid.othersModelScale);
					Modchu_Debug.mDebug("modelData handedness="+modelData.getCapsValueInt(modelData.caps_dominantArm));
					break;
				case PFLM_GuiOthersPlayer.modePlayerOffline:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_PlayerOffline);
					skinMode_PlayerOfflineSetting(entityplayer, modelData);
					modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
					modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.textureName);
					modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
					break;
				case PFLM_GuiOthersPlayer.modePlayerOnline:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_PlayerOnline);
					modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
					modelData2 = getPlayerData(thePlayer);
					modelData.setCapsValue(modelData.caps_textureName, "_Biped");
					modelData.setCapsValue(modelData.caps_textureArmorName, "_Biped");
					if (modelData2 != null) modelData.setCapsValue(modelData.caps_maidColor, modelData2.getCapsValueInt(modelData.caps_maidColor));
					break;
				case PFLM_GuiOthersPlayer.modeRandom:
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_Random);
					skinMode_RandomSetting(entityplayer, modelData);
					modelData.setCapsValue(modelData.caps_dominantArm, othersPlayerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, mod_PFLM_PlayerFormLittleMaid.othersModelScale);
					break;
				}
				if (modelData.getCapsValueInt(modelData.caps_skinMode) != skinMode_PlayerOnline
						&& modelData.getCapsValueInt(modelData.caps_skinMode) != skinMode_online) {
					modelData.setCapsValue(modelData.caps_initFlag, 2);
					modelData.addSendList(4);
					return modelData;
				}
			}
			modelData.addSendList(4);
		} else {
			modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
			modelData.addSendList(0);
			if (mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeRandom) {
				modelData.setCapsValue(modelData.caps_skinMode, skinMode_Random);
				skinMode_RandomSetting(entityplayer, modelData);
				modelData.setCapsValue(modelData.caps_initFlag, 2);
				return modelData;
			}
		}
		boolean er = false;
		Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");
		try {
			if ((modelData.getCapsValueBoolean(modelData.caps_isPlayer)
					&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOnline)
					| (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)
					&& modelData.getCapsValueInt(modelData.caps_skinMode) == skinMode_online)) {
				Modchu_Debug.Debug((new StringBuilder()).append("new model read username = ").append(entityplayer.username).toString());
				String skinUrl = modelData.getCapsValueInt(modelData.caps_skinMode) == skinMode_PlayerOnline ? thePlayer.username : entityplayer.username;
				skinUrl = mod_PFLM_PlayerFormLittleMaid.getPFLMVersion() > 159 ? (String) Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110300_d", new Class[]{ String.class }, entityplayer, new Object[]{ skinUrl })
						: (new StringBuilder()).append("http://skins.minecraft.net/MinecraftSkins/").append(skinUrl).append(".png").toString();
						//((AbstractClientPlayer) entityplayer).func_110300_d(thePlayer.username);
				//Modchu_Debug.mlDebug("OnlineMode skinUrl="+skinUrl);
				URL url = new URL(skinUrl);
				bufferedimage = ImageIO.read(url);
				//Modchu_Debug.mlDebug("OnlineMode bufferedimage != null ?"+(bufferedimage != null));
				String n = modelData.getCapsValueInt(modelData.caps_skinMode) == skinMode_PlayerOnline ? thePlayer.username : entityplayer.username;
				if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)
						&& !n.startsWith("Player")
						&& modelData.getCapsValueInt(modelData.caps_initFlag) == 0
						&& !initResetFlag) {
					modelData.setCapsValue(modelData.caps_initFlag, 1);
					initResetFlag = true;
					resetFlag = true;
					return modelData;
				}
				modelData.setCapsValue(modelData.caps_initFlag, 2);
				//Modchu_Debug.mlDebug("OnlineMode.image ok.");
			} else {
				//Modchu_Debug.Debug("er OnlineMode image.");
				er = true;
			}
		} catch (IOException ioexception) {
			String url = modelData.getCapsValueInt(modelData.caps_skinMode) == skinMode_PlayerOnline ? thePlayer.username : entityplayer.username;
			url = mod_PFLM_PlayerFormLittleMaid.getPFLMVersion() > 159 ? (String) Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110300_d", new Class[]{ String.class }, entityplayer, new Object[]{ url })
					: (new StringBuilder()).append("http://skins.minecraft.net/MinecraftSkins/").append(url).append(".png").toString();
			StringBuilder s1 = (new StringBuilder()).append("Failed to read a player texture from a URL for ");
			if (url != null) {
				s1.append(url);
			} else {
				s1.append("null entityplayer.userName=").append(entityplayer.username);
			}
			Modchu_Debug.Debug(s1.toString());
			//Modchu_Debug.Debug(ioexception.getMessage());
			er = true;
		}
		modelData.setCapsValue(modelData.caps_initFlag, 2);
		if (er) {
			//Modchu_Debug.mDebug("er entityplayer.skinUrl = "+entityplayer.skinUrl);
			if (mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOnline
					&& mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.isRelease()
					| !modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
				//Modchu_Debug.Debug("er /mob/char.png ");
				modelData.setCapsValue(modelData.caps_skinMode, skinMode_char);
				modelData.setCapsValue(modelData.caps_textureName, "_Biped");
				modelData.setCapsValue(modelData.caps_textureArmorName, "_Biped");
				modelInit(entityplayer, modelData, "_Biped");
				modelArmorInit(entityplayer, modelData, "_Biped");
				modelData.setCapsValue(modelData.caps_isPlayer, entityplayer.username == thePlayer.username);
				return modelData;
			} else {
				//Modchu_Debug.mDebug("er offline only set.");
				modelData.setCapsValue(modelData.caps_skinMode, skinMode_offline);
				modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.textureName);
				modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
			}

			bufferedimage = null;
		} else {
			modelData.setCapsValue(modelData.caps_skinMode, skinMode_online);
		}

		return checkSkin(entityplayer, bufferedimage, modelData);
	}

	private static void modelInit(EntityPlayer entityplayer, PFLM_ModelData modelData, String s) {
		Object[] models = mod_Modchu_ModchuLib.modelNewInstance(entityplayer, s, false, true);
		Modchu_Debug.mDebug("modelInit s="+s);
		if (models != null
				&& models[0] instanceof MultiModelBaseBiped) Modchu_Debug.mDebug("modelInit models[0] != null ? "+(models[0] != null));
		else {
			if (models != null
					&& models[0] instanceof MultiModelBaseBiped) Modchu_Debug.mDebug("modelInit models = null !!");
			else Modchu_Debug.mDebug("modelInit !MultiModelBaseBiped");
			if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)
					&& mod_PFLM_PlayerFormLittleMaid.textureName.indexOf("_") > -1) mod_PFLM_PlayerFormLittleMaid.textureName = "default";
			if (((String) modelData.getCapsValue(modelData.caps_textureName)).indexOf("_") > -1) modelData.setCapsValue(modelData.caps_textureName, "default");
			models = mod_Modchu_ModchuLib.modelNewInstance(entityplayer, ((String) modelData.getCapsValue(modelData.caps_textureName)), false, true);
		}
		modelData.modelMain.model = models != null && models[0] != null ? (MMM_ModelMultiBase) models[0] : new MultiModel(0.0F);
		modelData.modelMain.model.setCapsValue(modelData.caps_armorType, 0);
//-@-152
		s = mod_Modchu_ModchuLib.textureNameCheck(s);
		modelTextureReset(entityplayer, modelData, s);
//@-@152
		Modchu_Debug.mDebug("modelInit color="+modelData.getCapsValueInt(modelData.caps_maidColor));
	}

	private static void modelArmorInit(EntityPlayer entityplayer, PFLM_ModelData modelData, String s) {
		boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelData.modelMain.model);
		if (isBiped
				&& (s.equalsIgnoreCase("default")
						| s.equalsIgnoreCase("erasearmor"))) s = "Biped";
		Object[] models = mod_Modchu_ModchuLib.modelNewInstance(entityplayer, s, false, true);
		if (models != null) ;else {
			if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.textureArmorName = "default";
			modelData.setCapsValue(modelData.caps_textureArmorName, "default");
			models = mod_Modchu_ModchuLib.modelNewInstance(entityplayer, mod_PFLM_PlayerFormLittleMaid.textureArmorName, true, true);
		}
		float[] f1 = mod_Modchu_ModchuLib.getArmorModelsSize(models[0]);
		Modchu_Debug.mDebug("modelArmorInit s="+s+" models[1] != null ? "+(models[1] != null));
		if (models != null
				&& models[1] != null) ;else {
					models = mod_Modchu_ModchuLib.modelNewInstance(isBiped ? "Biped" : null, false);
					f1 = mod_Modchu_ModchuLib.getArmorModelsSize(models[0]);
				}
		if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			modelData.modelFATT.modelInner = (MMM_ModelMultiBase) models[1];
			modelData.modelFATT.modelOuter = (MMM_ModelMultiBase) models[2];
		} else {
			modelData.modelFATT.modelInner = models != null && models[1] != null ?
					(MMM_ModelMultiBase) models[1] : !isBiped ? new MultiModel(f1[0]) : new MultiModel_Biped(f1[0]);
			modelData.modelFATT.modelOuter = models != null && models[2] != null ?
					(MMM_ModelMultiBase) models[2] : !isBiped ? new MultiModel(f1[1]) : new MultiModel_Biped(f1[1]);
		}
		modelData.modelFATT.modelInner.setCapsValue(modelData.caps_armorType, 1);
		modelData.modelFATT.modelOuter.setCapsValue(modelData.caps_armorType, 2);
//-@-152
		modelTextureArmorReset(modelData, s);
//@-@152
	}

	private static void modelTextureReset(EntityPlayer entityplayer, PFLM_ModelData modelData) {
		modelTextureReset(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureName));
	}

	private static void modelTextureReset(EntityPlayer entityplayer, PFLM_ModelData modelData, String s) {
		if (modelData.getCapsValue(modelData.caps_ResourceLocation) != null) ;else {
			modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) new ResourceLocation[3]);
		}
		Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");
		if ((modelData.getCapsValueBoolean(modelData.caps_isPlayer)
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOnline)
				| !modelData.getCapsValueBoolean(modelData.caps_isPlayer)
				&& modelData.getCapsValueInt(modelData.caps_skinMode) == skinMode_online) {
			Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110304_a", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation"), String.class }, entityplayer, new Object[]{ Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110306_p", entityplayer) , entityplayer.username });
			//((AbstractClientPlayer) entityplayer).func_110304_a(((AbstractClientPlayer) entityplayer).func_110306_p(), entityplayer.username);
			modelData.setCapsValue(modelData.caps_ResourceLocation, 0, Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110306_p", entityplayer));
			//	modelData.setCapsValue(modelData.caps_ResourceLocation, 0, ((AbstractClientPlayer) entityplayer).func_110306_p());
		} else {
			modelData.setCapsValue(modelData.caps_ResourceLocation, 0, mod_Modchu_ModchuLib.textureManagerGetTexture(s, modelData.getCapsValueInt(modelData.caps_maidColor)));
		}
	}

	private static void modelTextureArmorReset(PFLM_ModelData modelData) {
		modelTextureArmorReset(modelData, (String) modelData.getCapsValue(modelData.caps_textureArmorName));
	}

	private static void modelTextureArmorReset(PFLM_ModelData modelData, String s) {
		if (modelData.getCapsValue(modelData.caps_ResourceLocation) != null) ;else {
			modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) new ResourceLocation[3]);
		}
		modelData.setCapsValue(modelData.caps_ResourceLocation, 1, mod_Modchu_ModchuLib.textureManagerGetTexture(s, modelData.getCapsValueInt(modelData.caps_maidColor)));
		modelData.setCapsValue(modelData.caps_ResourceLocation, 2, mod_Modchu_ModchuLib.textureManagerGetTexture(s, modelData.getCapsValueInt(modelData.caps_maidColor)));
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
			,PFLM_ModelData modelData)
	{
		EntityPlayer thePlayer = mod_PFLM_PlayerFormLittleMaid.getThePlayer();
		modelData.setCapsValue(modelData.caps_isPlayer, entityplayer.username == thePlayer.username);
		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)
				&& mod_PFLM_PlayerFormLittleMaid.changeMode == PFLM_Gui.modeOffline
				| modelData.getCapsValueInt(modelData.caps_skinMode) == skinMode_offline) {
			if (mod_PFLM_PlayerFormLittleMaid.textureName.equals("_Biped")) {
				mod_PFLM_PlayerFormLittleMaid.textureName =
						mod_PFLM_PlayerFormLittleMaid.textureArmorName = "default";
			}
			modelData.setCapsValue(modelData.caps_maidColor, mod_PFLM_PlayerFormLittleMaid.maidColor);
			modelInit(entityplayer, modelData, mod_PFLM_PlayerFormLittleMaid.textureName);
			modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.textureName);
			modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
			modelArmorInit(entityplayer, modelData, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
			modelData.setCapsValue(modelData.caps_skinMode, skinMode_offline);
			return modelData;
		}
		if (bufferedimage == null) {
			Modchu_Debug.Debug("bufferedimage == null");
			modelData.setCapsValue(modelData.caps_skinMode, skinMode_char);
			modelData.setCapsValue(modelData.caps_textureName, "_Biped");
			modelData.setCapsValue(modelData.caps_textureArmorName, "_Biped");
			modelInit(entityplayer, modelData, "_Biped");
			modelArmorInit(entityplayer, modelData, "_Biped");
			return modelData;
		}
		modelData.setCapsValue(modelData.caps_isActivated, true);
		Object[] s = checkimage(bufferedimage);
		boolean localflag = (Boolean) s[0];
		modelData.setCapsValue(modelData.caps_textureArmorName, (String) s[2]);
		int maidcolor = (Integer) s[3];
		String texture = (String) s[1];
		String textureName = (String) s[4];
		boolean returnflag = (Boolean) s[5];
		int handedness = (Integer) s[6];
		float modelScale = (Float) s[7];

		if (returnflag) {
			if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
				mod_PFLM_PlayerFormLittleMaid.textureName = "_Biped";
				modelData.setCapsValue(modelData.caps_textureName, "_Biped");
				modelData.setCapsValue(modelData.caps_textureArmorName, "_Biped");
			}
			modelData.setCapsValue(modelData.caps_skinMode, skinMode_online);
			modelInit(entityplayer, modelData, "_Biped");
			modelArmorInit(entityplayer, modelData, "_Biped");
			return modelData;
		}
		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
			//Modchu_Debug.mDebug("modelData.isPlayer set textureName="+textureName);
			mod_PFLM_PlayerFormLittleMaid.textureName = textureName;
			mod_PFLM_PlayerFormLittleMaid.maidColor = maidcolor;
			mod_PFLM_PlayerFormLittleMaid.textureArmorName = (String) modelData.getCapsValue(modelData.caps_textureArmorName);
		}
		modelData.setCapsValue(modelData.caps_textureName, textureName);
		modelData.setCapsValue(modelData.caps_maidColor, maidcolor);
		Modchu_Debug.Debug((new StringBuilder()).append("checkSkin textureName = ").append(textureName).toString());
		if(localflag) {
			modelData.setCapsValue(modelData.caps_localFlag, true);
			modelData.setCapsValue(modelData.caps_skinMode, skinMode_local);
			if (texture != null) {
				Modchu_Debug.Debug((new StringBuilder()).append("localflag maidcolor = ").append(maidcolor).toString());
				modelData.setCapsValue(modelData.caps_ResourceLocation, 0, mod_Modchu_ModchuLib.textureManagerGetTexture(texture, modelData.getCapsValueInt(modelData.caps_maidColor)));
				Modchu_Debug.Debug((new StringBuilder()).append("localflag texture = ").append(texture).toString());
			}
		} else {
			modelData.setCapsValue(modelData.caps_localFlag, false);
			modelData.setCapsValue(modelData.caps_ResourceLocation, 0, null);
		}

		if (textureName != null) modelInit(entityplayer, modelData, textureName);
		Modchu_Debug.Debug((new StringBuilder()).append("checkSkin Armor = ").append(s[2]).toString());
		if (modelData.getCapsValue(modelData.caps_textureArmorName) != null) modelArmorInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureArmorName));
		Modchu_Debug.Debug((new StringBuilder()).append("modelData.textureName = ").append(textureName).toString());

		modelData.setCapsValue(modelData.caps_dominantArm, handedness);
		modelData.setCapsValue(modelData.modelMain.model, modelData.caps_dominantArm, handedness);
		Modchu_Debug.Debug((new StringBuilder()).append("localflag handedness = ").append(handedness).append(" Random=-1 Right=0 Left=1").toString());
		modelData.setCapsValue(modelData.caps_modelScale, modelScale);
		Modchu_Debug.Debug((new StringBuilder()).append("localflag modelScale = ").append(modelScale).toString());
		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
			mod_PFLM_PlayerFormLittleMaid.handednessMode = handedness;
			PFLM_Gui.modelScale = modelScale;
		}
		return modelData;
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
				object[1] = mod_Modchu_ModchuLib.textureManagerGetTexture(
						mod_PFLM_PlayerFormLittleMaid.textureList.get(b), r);
			}
		}
		checkX = 62;
		checkY = checkPointUnder ? 30 : 1;
		c1 = checkImageColor(bufferedimage, checkX, checkY);
		object[6] = c1[0] == 255 ? 0 : c1[0] == 0 ? 1 : -1;
		object[7] = (float)(255 - c1[1]) * (0.9375F / 24F);
		//Modchu_Debug.mDebug((new StringBuilder()).append("checkimage modelScale color c1[1] = ").append(c1[1]).toString());
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
     * Used to render a player's name above their head
     */
    @Override
    public void renderLivingLabel(EntityLivingBase entityplayer, String par2Str, double d, double d1, double d2, int i)
    {
//-@-125
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderName", new Class[]{ EntityPlayer.class, double.class, double.class, double.class }, pflm_RenderPlayerSmart, new Object[]{ entityplayer, d, d1, d2 });
//@-@125
    	if(mod_PFLM_PlayerFormLittleMaid.isRenderName
    			&& renderManager != null
    			&& renderManager.renderEngine != null) {
    		PFLM_ModelData modelData = getPlayerData((EntityPlayer) entityplayer);
    		if (modelData == null) return;
    		double d3 = 0.0D;
    		double d4 = 0.0D;
    		double d5 = 0.0D;
    		double height = (double)modelData.modelMain.model.getHeight();
    		if(entityplayer.isSneaking()) d3 = 0.4D;
    		float f1 = modelData.getCapsValueFloat(modelData.caps_modelScale);
    		if (f1 > 0.0F) {
    			d5 = (double)(0.9375F - f1);
    			d4 = -height * d5;
    			if (f1 > 0.9375F) d4 -= 0.4D * d5;
    		}
    		super.renderLivingLabel(entityplayer, par2Str, d, (d1 - 1.8D) + height + d3 + d4, d2, i);
    	}
    }

    public static void clearPlayers() {
    	playerData.clear();
    }

    public static void removePlayer(EntityPlayer entityPlayer) {
    	playerData.remove(entityPlayer);
    }

    public void waitModeSetting(PFLM_ModelData modelData, float f) {
    	Object o = modelData != null
    			&& modelData.modelMain != null
    			&& modelData.modelMain.model != null ? modelData.modelMain.model.getCapsValue(modelData.caps_onGround) : null;
    	if (o != null) ;else return;
    	float[] onGrounds = (float[]) o;
    	int i1 = Modchu_ModelCapsHelper.getCapsValueInt(modelData, modelData.caps_dominantArm);
    	if (!Modchu_ModelCapsHelper.getCapsValueBoolean(modelData.modelMain.model, modelData.caps_firstPerson)) {
    		if(mod_PFLM_PlayerFormLittleMaid.waitTime == 0
    				&& Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isPlayer)) {
    			if(mod_PFLM_PlayerFormLittleMaid.isWait) {
    				if (((f != modelData.getCapsValueFloat(modelData.caps_isWaitF) && modelData.getCapsValueBoolean(modelData.caps_isWaitFSetFlag))
    						| onGrounds[i1] > 0)
    						| (modelData.getCapsValueBoolean(modelData.caps_isWait) && modelData.modelMain.model.isSneak)) {
    					modelData.setCapsValue(modelData.caps_isWait, false);
    					mod_PFLM_PlayerFormLittleMaid.isWait = false;
    					modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    					modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    					modelData.setCapsValue(modelData.caps_isWaitFSetFlag, true);
    				} else {
    					if ((f != modelData.getCapsValueFloat(modelData.caps_isWaitF)
    							| onGrounds[i1] > 0)
    							| (modelData.getCapsValueBoolean(modelData.caps_isWait) && modelData.modelMain.model.isSneak)) {
    						//Modchu_Debug.mDebug("f != isWaitF");
    						modelData.setCapsValue(modelData.caps_isWait, false);
    						mod_PFLM_PlayerFormLittleMaid.isWait = false;
    						modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    						modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    					}
    					if (!modelData.getCapsValueBoolean(modelData.caps_isWaitFSetFlag)) {
    						modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    						modelData.setCapsValue(modelData.caps_isWaitFSetFlag, true);
    					}
    				}
    			}
    		} else {
    			int i = Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isPlayer) ? mod_PFLM_PlayerFormLittleMaid.waitTime : mod_PFLM_PlayerFormLittleMaid.othersPlayerWaitTime;
    			if (!modelData.getCapsValueBoolean(modelData.caps_isWait)
    					&& !modelData.modelMain.model.isSneak
    					&& i > 0) {
    				modelData.setCapsValue(modelData.caps_isWaitTime, modelData.getCapsValueInt(modelData.caps_isWaitTime) + 1);
    				if(modelData.getCapsValueInt(modelData.caps_isWaitTime) > i) {
    					//Modchu_Debug.Debug("isWaitTime > i");
    					modelData.setCapsValue(modelData.caps_isWait, true);
    					if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.isWait = true;
    					modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    					modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    				}
    			}
    			if ((f != modelData.getCapsValueFloat(modelData.caps_isWaitF)
    					| onGrounds[i1] > 0)
    					| (modelData.getCapsValueBoolean(modelData.caps_isWait) && modelData.modelMain.model.isSneak)) {
    				//Modchu_Debug.Debug("f != isWaitF");
    				modelData.setCapsValue(modelData.caps_isWait, false);
    				if (Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.isWait = false;
    				modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    				modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    			}
    		}
    	} else {
    		if (Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isPlayer)) {
    			if (modelData.getCapsValueBoolean(modelData.caps_isWait)) {
    				//Modchu_Debug.Debug("firstPerson isWait false");
    				modelData.setCapsValue(modelData.caps_isWait, false);
    				if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.isWait = false;
    				modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    				modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    				if(mod_PFLM_PlayerFormLittleMaid.waitTime == 0) modelData.setCapsValue(modelData.caps_isWaitFSetFlag, true);
    			} else {
    				if(mod_PFLM_PlayerFormLittleMaid.waitTime == 0
    						&& !modelData.getCapsValueBoolean(modelData.caps_isWaitFSetFlag)) {
    					modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    					modelData.setCapsValue(modelData.caps_isWaitFSetFlag, true);
    				}
    			}
    		}
    	}
    }

    public float copyf(float f) {
    	return f;
    }

    private static void skinMode_PlayerOfflineSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
    	if (mod_PFLM_PlayerFormLittleMaid.textureName == null
    			| mod_PFLM_PlayerFormLittleMaid.textureName.isEmpty()) mod_PFLM_PlayerFormLittleMaid.textureName = "_Biped";
    	if (mod_PFLM_PlayerFormLittleMaid.textureArmorName == null
    			| mod_PFLM_PlayerFormLittleMaid.textureArmorName.isEmpty()) mod_PFLM_PlayerFormLittleMaid.textureArmorName = "_Biped";
    	modelData.setCapsValue(modelData.caps_maidColor, mod_PFLM_PlayerFormLittleMaid.maidColor);
    	modelData.setCapsValue(modelData.caps_ResourceLocation, 0, mod_Modchu_ModchuLib.textureManagerGetTexture(mod_PFLM_PlayerFormLittleMaid.textureName, modelData.getCapsValueInt(modelData.caps_maidColor)));
    	String s1 = mod_PFLM_PlayerFormLittleMaid.textureName;
    	modelInit(entityplayer, modelData, s1);
    	modelData.setCapsValue(modelData.caps_textureName, s1);
    	s1 = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
    	modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.textureArmorName);
    	modelArmorInit(entityplayer, modelData, s1);
    }

    private static void skinMode_RandomSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
    	String s3 = null;
    	ResourceLocation s4 = null;
    	for(int i1 = 0; s4 == null && i1 < 50; i1++) {
    		int i = rnd.nextInt(16);
    		int j = 0;
    		if (mod_Modchu_ModchuLib.getTextureManagerTexturesSize() > 0) {
    			j = rnd.nextInt(mod_Modchu_ModchuLib.getTextureManagerTexturesSize());
    			s3 = mod_Modchu_ModchuLib.getPackege(i, j);
    		} else {
    			s3 = mod_Modchu_ModchuLib.textureNameCheck(null);
    		}
    		modelData.setCapsValue(modelData.caps_maidColor, i);
    		s4 = mod_Modchu_ModchuLib.textureManagerGetTexture(s3, i);
    		if (i1 == 49) return;
    	}
    	modelData.setCapsValue(modelData.caps_ResourceLocation, 0, s4);
    	Modchu_Debug.Debug("Random modelPackege="+s3);
    	//Modchu_Debug.mDebug("Random s4="+s4);
    	String s1 = mod_PFLM_PlayerFormLittleMaid.getArmorName(s3);
    	modelData.setCapsValue(modelData.caps_textureName, s3);
    	modelData.setCapsValue(modelData.caps_textureArmorName, s1);
    	modelInit(entityplayer, modelData, s3);
    	modelArmorInit(entityplayer, modelData, s1);
    }

    public static void setHandedness(EntityPlayer entityplayer, int i) {
    	PFLM_ModelData modelData = getPlayerData(entityplayer);
    	if (i == -1) i = rnd.nextInt(2);
    	modelData.setCapsValue(modelData.caps_dominantArm, i);
    	modelData.modelMain.model.setCapsValue(modelData.caps_dominantArm, i);
    	if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
    		mod_PFLM_PlayerFormLittleMaid.setFlipHorizontal(i == 0 ? false : true);
    		mod_PFLM_PlayerFormLittleMaid.setLeftHandedness(i == 0 ? false : true);
    	}
    }

    protected void renderModel(EntityLivingBase entityliving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer) entityliving);
    	//ResourceLocation resourceLocation = func_110775_a(par1EntityLiving);
    	//Modchu_Debug.mDebug("renderModel resourceLocation.func_110623_a()="+resourceLocation.func_110623_a());
    	func_110777_b(entityliving);
    	int PFLMVersion = mod_PFLM_PlayerFormLittleMaid.getPFLMVersion();
    	if ((PFLMVersion > 129
    			&& !mod_Modchu_ModchuLib.useInvisibilityBody)
    			| (PFLMVersion > 129
    					&& mod_Modchu_ModchuLib.useInvisibilityBody
    					&& !entityliving.isInvisible())
    					| PFLMVersion < 130) {
    		modelData.modelMain.setArmorRendering(true);
    	} else {
    		modelData.modelMain.setArmorRendering(false);
    	}
    	modelData.modelMain.model.render(modelData, par2, par3, par4, par5, par6, par7, modelData.modelMain.isRendering);
    	//Modchu_Debug.mDebug("renderModel modelData.modelMain.isRendering="+modelData.modelMain.isRendering);
    }

    public float getActionSpeed(PFLM_ModelData modelData) {
    	World theWorld = mod_PFLM_PlayerFormLittleMaid.getTheWorld();
    	float f = (float)(theWorld.getWorldTime() - modelData.getCapsValueInt(modelData.caps_actionTime));
    	modelData.setCapsValue(modelData.caps_actionTime, (int) theWorld.getWorldTime());
    	return f;
    }

    @Override
    protected ResourceLocation func_110775_a(Entity entity) {
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer) entity);
    	return (ResourceLocation) modelData.getCapsValue(modelData.caps_ResourceLocation, 0);
    }

    protected ResourceLocation func_110775_a(Entity entity, int i) {
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer) entity);
    	return (ResourceLocation) modelData.getCapsValue(modelData.caps_ResourceLocation, i);
    }

    protected void setResourceLocation(Entity entity, ResourceLocation resourceLocation) {
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer) entity);
    	modelData.setCapsValue(modelData.caps_ResourceLocation, 0, resourceLocation);
    }

    protected void setResourceLocation(Entity entity, int i, ResourceLocation resourceLocation) {
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer) entity);
    	modelData.setCapsValue(modelData.caps_ResourceLocation, i, resourceLocation);
    }

    protected void renderLivingAt(Entity entityliving, double par2, double par4, double par6)
    {
    	renderPlayerSleep(entityliving, par2, par4, par6);
    }

    protected void renderPlayerSleep(Entity var1, double var2, double var4, double var6)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) renderPlayerAt(var1, var2, var4, var6);
    	Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");
    	Modchu_Reflect.invokeMethod(RenderPlayer.class, "func_77105_b", "renderPlayerSleep", new Class[]{ AbstractClientPlayer, double.class, double.class, double.class }, this, new Object[]{ var1, var2, var4, var6 });
    	//super.renderPlayerSleep((AbstractClientPlayer) var1, var2, var4, var6);
    }

    protected void rotateCorpse(EntityPlayer entityliving, float f, float f1, float f2) {
    	rotatePlayer(entityliving, f, f1, f2);
    }

    public void rotatePlayer(EntityPlayer var1, float var2, float var3, float var4)
    {
    	PFLM_ModelData modelData = getPlayerData(var1);
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving
    			&& modelData != null) {
//-@-125
    		Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "rotatePlayer", new Class[]{ EntityPlayer.class, float.class, float.class, float.class }, pflm_RenderPlayerSmart, new Object[]{ var1, var2, var3, var4 });
//@-@125
/*//125delete
    		Modchu_Reflect.invokeMethod(PFLM_RenderPlayerSmart, "rotatePlayer", new Class[]{ EntityPlayer.class, float.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderPlayerSmart, new Object[]{ var1, var2, var3, var4, modelData });
*///125delete
    		//Modchu_Reflect.invokeMethod(PFLM_RenderRenderSmart, "rotatePlayer", new Class[]{ EntityPlayer.class, float.class, float.class, float.class }, pflm_RenderRenderSmart, new Object[]{ var1, var2, var3, var4 });
    	}
    	if (modelData.getCapsValueBoolean(modelData.caps_isSleeping)) {
    		switch(modelData.getCapsValueInt(modelData.caps_rotate)) {
    		case 0:
        		GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
    			break;
    		case 1:
        		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
    			break;
    		case 2:
        		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
    			break;
    		case 3:
        		GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
    			break;
    		}
    		GL11.glRotatef(getDeathMaxRotation((Entity) var1), 0.0F, 0.0F, 1.0F);
    		GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
    	} else {
    		Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");
    		Modchu_Reflect.invokeMethod(RenderPlayer.class, "func_77102_a", "rotatePlayer", new Class[]{ AbstractClientPlayer, float.class, float.class, float.class }, this, new Object[]{ var1, var2, var3, var4 });
    		//super.rotatePlayer((AbstractClientPlayer) var1, var2, var3, var4);
    	}
    }

    protected float getDeathMaxRotation(Entity par1EntityLiving)
    {
        return 90.0F;
    }

    protected float handleRotationFloat(Entity par1EntityLiving, float par2)
    {
        return (float)par1EntityLiving.ticksExisted + par2;
    }

    protected void preRenderCallback(Entity par1EntityLiving, float par2)
    {
        renderPlayerScale((EntityPlayer) par1EntityLiving, par2);
    }

    protected int getColorMultiplier(Entity par1EntityLiving, float par2, float par3)
    {
        return 0;
    }

    protected int inheritRenderPass(Entity par1EntityLiving, int par2, float par3)
    {
        return shouldRenderPass(par1EntityLiving, par2, par3);
    }

    //smartMoving関連↓
    public RenderManager getRenderManager() {
    	return renderManager;
    }

    public void renderPlayerAt(Entity var1, double var2, double var4, double var6)
    {
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer) var1);
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving
    			&& modelData != null) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderPlayerAt", new Class[]{ EntityPlayer.class, double.class, double.class, double.class }, pflm_RenderPlayerSmart, new Object[]{ var1, var2, var4, var6 });
    }

    public static void renderGuiIngame(Minecraft var0)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderGuiIngame", new Class[]{ Minecraft.class }, pflm_RenderPlayerSmart, new Object[]{ var0 });
    }
    //smartMoving関連↑
}

