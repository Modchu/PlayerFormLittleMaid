package net.minecraft.src;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerMaster
{
	public String[] armorFilename;
	private boolean checkGlEnableWrapper = true;
	private boolean checkGlDisableWrapper = true;
	private boolean isSizeMultiplier = false;
	private boolean shadersHurtFlashFlag = false;
	private Method sizeMultiplier;
	public Object pflm_RenderPlayerSmart;
//-@-125
	public Object pflm_RenderRenderSmart;
//@-@125
    // b173deleteprivate RenderBlocks renderBlocks;

	public PFLM_RenderPlayerMaster() {
		Modchu_Debug.lDebug("PFLM_RenderPlayerMaster init");
		armorFilename = (String[]) (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ? Modchu_Reflect.getFieldObject(RenderBiped.class, "field_82424_k", "bipedArmorFilenamePrefix") :
			Modchu_Reflect.getFieldObject(RenderPlayer.class, "field_77110_j", "armorFilenamePrefix"));
		Modchu_Debug.lDebug("PFLM_RenderPlayerMaster init 1");
		sizeMultiplier = Modchu_Reflect.getMethod(Entity.class, "getSizeMultiplier", -1);
		isSizeMultiplier = sizeMultiplier != null;
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isSmartMoving) {
			//Modchu_Debug.mDebug("PFLM_RenderPlayerMaster() isSmartMoving");
			pflm_RenderPlayerSmart = Modchu_Reflect.newInstance("PFLM_RenderPlayerSmart", new Class[]{ PFLM_RenderPlayerMaster.class }, new Object[]{ mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer });
//-@-125
			pflm_RenderRenderSmart = Modchu_Reflect.newInstance("PFLM_RenderRenderSmart", new Class[]{ PFLM_RenderPlayerMaster.class }, new Object[]{ mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer });
//@-@125
		}
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
			if (Modchu_Reflect.getMethod("Shaders", "setEntityHurtFlash", new Class[]{ int.class, int.class }) != null
					&& Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash") != null) {
				shadersHurtFlashFlag = true;
			}
		}
		// b173deleterenderBlocks = new RenderBlocks();
		Modchu_Debug.lDebug("PFLM_RenderPlayerMaster init end.");
	}

    public int setArmorModel(Entity entityplayer, int i, float f) {
    	return setArmorModel(null, entityplayer, i, f, 0);
    }

    public int setArmorModel(MultiModelBaseBiped model, Entity entity, int i, float f, int i2) {
    	EntityPlayer entityplayer = (EntityPlayer)entity;
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	/*b181//*/byte byte0 = -1;
    	//b181 deleteboolean byte0 = false;
    	if (modelData != null) ;else return byte0;
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
    		boolean isBiped = mod_PFLM_PlayerFormLittleMaid.pflm_main.BipedClass.isInstance(modelData.modelMain.model);
    		if (isBiped) {
    			if (model != null) {
    				model.setArmorBipedRightLegShowModel(modelData, flag1);
    				model.setArmorBipedLeftLegShowModel(modelData, flag1);
    			} else {
    				if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
    					if (mod_Modchu_ModchuLib.modchu_Main.mmmLibVersion > 599) {
    						if (modelData.modelFATT.modelOuter instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelData.modelFATT.modelOuter).setArmorBipedRightLegShowModel(modelData, flag1);
    						if (modelData.modelFATT.modelOuter instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelData.modelFATT.modelOuter).setArmorBipedLeftLegShowModel(modelData, flag1);
    					} else {
    						if (modelData.modelFATT.modelInner instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelData.modelFATT.modelInner).setArmorBipedRightLegShowModel(modelData, flag1);
    						if (modelData.modelFATT.modelInner instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelData.modelFATT.modelInner).setArmorBipedLeftLegShowModel(modelData, flag1);
    					}
    				}
    			}
    		}
    	}
    	return byte0;
   }

    private void armorTextureSetting(EntityPlayer entityplayer, PFLM_ModelData modelData, ItemStack is, int i) {
    	int i2 = i;
    	String t = (String) modelData.getCapsValue(modelData.caps_textureArmorName);
    	//Modchu_Debug.mDebug("setArmorModel t="+t);
    	boolean isBiped = mod_PFLM_PlayerFormLittleMaid.pflm_main.BipedClass.isInstance(modelData.modelMain.model);
    	if (t != null) ;else t = isBiped ? "Biped" : "default";
    	if (modelData.modelFATT.modelOuter != null
    			&& modelData.modelFATT.modelInner != null) {
    	} else {
    		PFLM_ModelDataMaster.instance.modelArmorInit(entityplayer, modelData, t);
    	}
    	Object[] t3;
    	Object[] t4;
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
    			if (mod_Modchu_ModchuLib.modchu_Main.isForge
    					&& mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 129) {
    				String a1 = itemarmor.renderIndex < armorFilename.length ? armorFilename[itemarmor.renderIndex] : armorFilename[armorFilename.length - 1];
    				Object t1;
    				Object t2;
    				if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() < 150) {
    					t2 = Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ ItemStack.class, String.class }, new Object[]{ is, "/armor/" + a1 + "_" + 2 + ".png" });
    					t1 = Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ ItemStack.class, String.class }, new Object[]{ is, "/armor/" + a1 + "_" + 1 + ".png" });
    				} else if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() < 160) {
    					t2 = Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ Entity.class, ItemStack.class, String.class, int.class, int.class }, new Object[]{ entityplayer, is, "/armor/" + a1 + "_" + 2 + ".png", i, 2 });
    					t1 = Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ Entity.class, ItemStack.class, String.class, int.class, int.class }, new Object[]{ entityplayer, is, "/armor/" + a1 + "_" + 1 + ".png", i, 1 });
    				} else {
    					t1 = Modchu_Reflect.invokeMethod(RenderBiped.class, "getArmorResource", new Class[]{ Entity.class, ItemStack.class, int.class, String.class }, new Object[]{ entityplayer, is, 1, null });
    					t2 = Modchu_Reflect.invokeMethod(RenderBiped.class, "getArmorResource", new Class[]{ Entity.class, ItemStack.class, int.class, String.class }, new Object[]{ entityplayer, is, 2, null });
    				}
    				if (i == 1) {
    					t3 = modelData.modelFATT.textureInner;
    					t3[i] = t2;
    					Modchu_Reflect.setFieldObject("Modchu_ModelBaseDuo", "textureInner", modelData.modelFATT, t3);
    				}
    				t4 = modelData.modelFATT.textureOuter;
    				t4[i] = t1;
    				Modchu_Reflect.setFieldObject("Modchu_ModelBaseDuo", "textureOuter", modelData.modelFATT, t4);
    				//Modchu_Debug.mDebug("i="+i+" t2="+t2+" t1="+t1);
    			} else {
    				t3 = modelData.modelFATT.textureInner;
    				t4 = modelData.modelFATT.textureOuter;
    				if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
    					t3[i] = Modchu_Reflect.invokeMethod(RenderBiped.class, "func_110857_a", new Class[]{ ItemArmor.class, int.class }, new Object[]{ itemarmor, 2 });
    					t4[i] = Modchu_Reflect.invokeMethod(RenderBiped.class, "func_110857_a", new Class[]{ ItemArmor.class, int.class }, new Object[]{ itemarmor, 1 });
    				} else {
    					t3[i] = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetArmorTexture(t, 64, is);
    					t4[i] = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetArmorTexture(t, 80, is);
    					String a1 = itemarmor.renderIndex < armorFilename.length ? armorFilename[itemarmor.renderIndex] : armorFilename[armorFilename.length - 1];
    					if (t3[i] != null) ;else t3[i] = "/armor/" + a1 + "_" + 2 + ".png";
    					if (t4[i] != null) ;else t4[i] = "/armor/" + a1 + "_" + 1 + ".png";
    				}
    				Modchu_Reflect.setFieldObject("Modchu_ModelBaseDuo", "textureInner", modelData.modelFATT, t3);
    				Modchu_Reflect.setFieldObject("Modchu_ModelBaseDuo", "textureOuter", modelData.modelFATT, t4);
    			}
    		}
    	} else {
    		t3 = modelData.modelFATT.textureInner;
    		//o = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetArmorTexture(t, mod_Modchu_ModchuLib.modchu_Main.mmmLibVersion > 599 ? 80 : 64, is);
    		Object o = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetArmorTexture(t, 64, is);
    		if ((t3[i] != null
    				&& !t3[i].equals(o))
    				| t3[i] == null) {
    			t3[i] = o;
    			Modchu_Reflect.setFieldObject(mod_Modchu_ModchuLib.modchu_Main.MMM_ModelBaseDuo, "textureInner", modelData.modelFATT, t3);
    		}
    		t4 = modelData.modelFATT.textureOuter;
    		//o = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetArmorTexture(t, mod_Modchu_ModchuLib.modchu_Main.mmmLibVersion > 599 ? 64 : 80, is);
    		Object o2 = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetArmorTexture(t, 80, is);
    		if ((t4[i] != null
    				&& !t4[i].equals(o2))
    				| t4[i] == null) {
    			t4[i] = o2;
    			Modchu_Reflect.setFieldObject(mod_Modchu_ModchuLib.modchu_Main.MMM_ModelBaseDuo, "textureOuter", modelData.modelFATT, t4);
    		}
    	}
    	//Modchu_Debug.mlDebug("modelData.modelFATT.textureOuter["+i+"]="+modelData.modelFATT.textureOuter[i]);
    	//Modchu_Debug.mlDebug("modelData.modelFATT.textureInner["+i+"]="+modelData.modelFATT.textureInner[i]);
    }

    public int shouldRenderPass(Entity entityliving, int i, float f)
    {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData((EntityPlayer)entityliving);
    	Object t = null;
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
    			mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.setRenderPassModel(modelData.modelFATT);
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
    			mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.setRenderPassModel(modelData.modelFATT);
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

    public float renderPlayerScale(Entity entityliving, float f)
    {
    	if (isSizeMultiplier) {
    		float f2 = 0.9375F * (Float) Modchu_Reflect.invoke(sizeMultiplier, entityliving);
    		GL11.glScalef(f2, f2, f2);
    		return f2;
    	}
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange) {
    		mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.superPreRenderCallback(entityliving, f);
    		return 0.9375F;
    	}
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData((EntityPlayer)entityliving);
    	if (modelData != null) ;else return 0.9375F;
    	float f1 = modelData.getCapsValueBoolean(modelData.caps_isPlayer) ? PFLM_Gui.modelScale : modelData.getCapsValueFloat(modelData.caps_modelScale);
    	if (f1 == 0.0F) f1 = ((MultiModelBaseBiped) modelData.modelMain.model).getModelScale(modelData);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glScalef(f1, f1, f1);
    	//Modchu_Debug.lDebug("renderPlayerScale f1="+f1);
    	return f1;
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

    public void oldDoRenderLivingPFLM(PFLM_ModelData modelData, Entity entityliving, double d, double d1, double d2,
            float f, float f1) {
    	GL11.glEnable(GL11.GL_COLOR_MATERIAL);
    	GL11.glPushMatrix();
    	RenderHelper.enableStandardItemLighting();

    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    			&& !shadersHurtFlashFlag) {
    		//Shaders.glDisableWrapper(k1);
    		shadersGlDisableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glDisable(GL11.GL_CULL_FACE);
    	}
    	int version = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion();
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
    		float prevLimbSwingAmount = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70722_aY", mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ? "prevLimbSwingAmount" : "prevLimbYaw", entityliving);
    		float limbSwingAmount = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70721_aZ", mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ? "limbSwingAmount" : "prevLimbYaw", entityliving);
    		float limbSwing = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70754_ba", "limbSwing", entityliving);
    		if (version < 140) {
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
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
        			&& !shadersHurtFlashFlag) {
    			//Shaders.glEnableWrapper(GL12.GL_RESCALE_NORMAL);
    			shadersGlEnableWrapper(GL12.GL_RESCALE_NORMAL);
    		} else {
    			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    		}
    		GL11.glScalef(-1F, -1F, 1.0F);
    		preRenderCallback((Entity) entityliving, f1);
    		GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
    		float f7 = prevLimbSwingAmount + (limbSwingAmount - prevLimbSwingAmount) * f1;
    		float f8 = limbSwing - limbSwingAmount * (1.0F - f1);
    		if (version > 89
    				&& (Boolean) Modchu_Reflect.invokeMethod(EntityLivingBase, "func_70631_g_", "isChild", entityliving))
    		{
    			f8 *= 3F;
    		}
    		if (f7 > 1.0F)
    		{
    			f7 = 1.0F;
    		}

    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
        			&& !shadersHurtFlashFlag) {
    			//Shaders.glEnableWrapper(GL11.GL_ALPHA_TEST);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.AlphaBlend)
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
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
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    	    			&& !shadersHurtFlashFlag) {
    				//Shaders.glDisableWrapper(k1);
    				shadersGlDisableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glDisable(GL11.GL_BLEND);
    			}
    		}

    		//modelData.modelMain.setEntityCaps(modelData);
    		modelData.modelMain.setRender(mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
    		modelData.setCapsValue(modelData.caps_Entity, entityliving);
    		Modchu_Reflect.invokeMethod(ModelBase.class, "func_78086_a", "setLivingAnimations", new Class[]{ EntityLivingBase, float.class, float.class, float.class }, modelData.modelMain, new Object[]{ entityliving, f8, f7, f1 });
    		renderModel(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    		float f9 = 1.0F;
    		if (version < 80) {
    			//if (currentScreen == null | currentScreen instanceof GuiIngameMenu)
    			f9 = (Float) Modchu_Reflect.invokeMethod(Entity.class, "func_382_a", "getEntityBrightness", new Class[]{ float.class }, entityliving, new Object[]{ f1 });
    		}
    		modelData.modelFATT.showAllParts();
    		for (int i = 0; i < 4; i++)
    		{
    			int j = setArmorModel(entityliving, i, f);

    			if (j <= 0)
    			{
    				continue;
    			}

    			for (int l = 0; l < 5; l += 4)
    			{
    				if (shouldRenderPass(entityliving, i + l, f1) < 0)
    				{
    					continue;
    				}

    				float f10 = 1.0F;
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    			    			&& !shadersHurtFlashFlag) {
    						//Shaders.glEnableWrapper(GL11.GL_BLEND);
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    				}
    				f10 = mod_PFLM_PlayerFormLittleMaid.pflm_main.transparency;
    				GL11.glColor4f(f9, f9, f9, f10);
    				//modelData.modelMain.setEntityCaps(modelData);
    				Modchu_Reflect.invokeMethod(ModelBase.class, "func_78086_a", "setLivingAnimations", new Class[]{ EntityLivingBase, float.class, float.class, float.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderPassModel, new Object[]{ entityliving, f8, f7, f1});
    				//renderPassModel.setLivingAnimations(entityliving, f8, f7, f1);
    				((Modchu_ModelBaseDuo) mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderPassModel).setArmorRendering(true);
    				if ((version > 129
    						&& !mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor)
    						| (mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor
    								&& !entityliving.isInvisible())
    								| version < 130) {
    					mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    				}
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.transparency != 1.0F) GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    				if (version < 90
    						| j != 15) {
    					continue;
    				}

    				float f11 = (float)entityliving.ticksExisted + f1;
    				if (version > 159) Modchu_Reflect.invokeMethod(Render.class, "func_110776_a", "bindTexture", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ Modchu_Reflect.getFieldObject("RendererLivingEntity", "field_110814_a", "RES_ITEM_GLINT", mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer) });
    				else Modchu_Reflect.invokeMethod(Render.class, "func_76985_a", "loadTexture", new Class[]{ String.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ "%blur%/misc/glint.png" });
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
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
    					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
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

    					if ((version > 129
    							&& !mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor)
    							| (version > 129
    									&& mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor
    									&& !entityliving.isInvisible())
    									| version < 130) {
    						mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    					}
    				}

    				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    				GL11.glMatrixMode(GL11.GL_TEXTURE);
    				GL11.glDepthMask(true);
    				GL11.glLoadIdentity();
    				GL11.glMatrixMode(GL11.GL_MODELVIEW);
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    		    			&& !shadersHurtFlashFlag) {
    					//Shaders.glEnableWrapper(GL11.GL_LIGHTING);
    					shadersGlEnableWrapper(GL11.GL_LIGHTING);
    				} else {
    					GL11.glEnable(GL11.GL_LIGHTING);
    				}

    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
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
    					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
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

    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
        			&& !shadersHurtFlashFlag) {
    			//Shaders.glDisableWrapper(GL11.GL_BLEND);
    			shadersGlDisableWrapper(GL11.GL_BLEND);
    			//Shaders.glEnableWrapper(GL11.GL_ALPHA_TEST);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glDisable(GL11.GL_BLEND);
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		if (version < 80) GL11.glColor4f(f9, f9, f9, 1.0F);
    		//modelData.modelMain.setEntityCaps(modelData);
/*
    		if (version > 129
    				&& entityliving.isInvisible()
    				| version < 130) {
*/
    			modelData.modelMain.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6, entityliving);
    			modelData.modelFATT.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6, entityliving);
/*
    		}
*/
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    				&& shadersHurtFlashFlag
    				&& (Boolean) Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash")) {
    			Modchu_Reflect.invokeMethod("Shaders", "setEntityHurtFlash", new Class[]{ int.class, int.class }, new Object[]{ 0, 0 });
    		}
    		renderEquippedItems(entityliving, f1);
    		boolean hurtFlag = mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    				&& shadersHurtFlashFlag
    				&& !(Boolean) Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash")
    				? true : false;
    		if (!hurtFlag) {
    			f9 = entityliving.getBrightness(f1);
    			int k = getColorMultiplier(entityliving, f9, f1);
    			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    					&& !shadersHurtFlashFlag) {
    				//Shaders.glDisableWrapper(GL11.GL_TEXTURE_2D);
    				shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
    			} else {
    				GL11.glDisable(GL11.GL_TEXTURE_2D);
    			}
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    					&& shadersHurtFlashFlag) Modchu_Reflect.invokeMethod("Shaders", "disableLightmap");
    			OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

    			int hurtTime = (Integer) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70737_aN", "hurtTime", entityliving);
    			int deathTime = (Integer) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70725_aQ", "deathTime", entityliving);
    			if ((k >> 24 & 0xff) > 0 || hurtTime > 0 || deathTime > 0)
    			{
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    						&& shadersHurtFlashFlag) Modchu_Reflect.invokeMethod("Shaders", "beginLivingDamage");
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
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
    							mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
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
    							mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    						}
    					}
    				}

    				GL11.glDepthFunc(GL11.GL_LEQUAL);
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
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
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    						&& shadersHurtFlashFlag) Modchu_Reflect.invokeMethod("Shaders", "endLivingDamage");
    			}

    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
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
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    			&& !shadersHurtFlashFlag) {
    		//Shaders.glEnableWrapper(GL11.GL_TEXTURE_2D);
    		shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
    	} else {
    		GL11.glEnable(GL11.GL_TEXTURE_2D);
    	}
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    			&& !shadersHurtFlashFlag) {
    		//Shaders.glEnableWrapper(GL11.GL_CULL_FACE);
    		shadersGlEnableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glEnable(GL11.GL_CULL_FACE);
    	}
    	GL11.glPopMatrix();
    	Modchu_Reflect.invokeMethod("RendererLivingEntity", "func_77033_b", "passSpecialRender", new Class[]{ EntityLivingBase, double.class, double.class, double.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ entityliving, d, d1, d2});
    	//passSpecialRender(entityliving, d, d1, d2);
    }

    public void doRenderPlayerFormLittleMaid(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1) {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	if (modelData != null) ;else return;
    	doRenderSetting(entityplayer, modelData);
    	boolean isPlayer = modelData.getCapsValueBoolean(modelData.caps_isPlayer);
    	if (!isPlayer) PFLM_ModelDataMaster.instance.sitSleepResetCheck(modelData, entityplayer);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isSmartMoving) {
    		Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderPlayer", new Class[]{ EntityPlayer.class, double.class , double.class, double.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderPlayerSmart, new Object[]{ entityplayer, d, d1, d2, f, f1, modelData });
//-@-125
    		Modchu_Reflect.invokeMethod("PFLM_RenderRenderSmart", "renderPlayer", new Class[]{ EntityPlayer.class, double.class , double.class, double.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderRenderSmart, new Object[]{ entityplayer, d, d1, d2, f, f1, modelData });
//@-@125
    	}
    	float prevLimbSwingAmount = (Float)Modchu_Reflect.getFieldObject(EntityLiving.class, "field_70722_aY", mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ? "prevLimbSwingAmount" : "prevLimbYaw", entityplayer);
    	float f8 = entityplayer.limbSwing - prevLimbSwingAmount * (1.0F - f1);
    	//modelData.modelMain.setEntityCaps(modelData);
    	PFLM_ModelDataMaster.instance.waitModeSetting(modelData, f8);
    	if (isPlayer) {
    		modelData.modelMain.setCapsValue(modelData.caps_isWait, mod_PFLM_PlayerFormLittleMaid.pflm_main.isWait);
    	} else {
    		modelData.modelMain.setCapsValue(modelData.caps_isWait, modelData.getCapsValue(modelData.caps_isWait));
    	}
    	modelData.setCapsValue(modelData.caps_isOpenInv, d == 0.0D && d1 == 0.0D && d2 == 0.0D && f == 0.0F && f1 == 1.0F);
    	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    	modelData.modelMain.setCapsValue(modelData.caps_heldItemLeft, 0);
    	modelData.modelMain.setCapsValue(modelData.caps_heldItemRight, 0);
//-@-b173
    	if (itemstack != null && entityplayer.getItemInUseCount() > 0) {
    		EnumAction enumaction = itemstack.getItemUseAction();
    		if (enumaction == EnumAction.block) {
    			if (modelData.modelMain.model.isItemHolder(modelData)) {
    				modelData.modelMain.setCapsValue(modelData.caps_heldItemLeft, 3);
    				modelData.modelMain.setCapsValue(modelData.caps_heldItemRight, 3);
    			}
    		} else if (enumaction == EnumAction.bow) {
    			modelData.modelMain.setCapsValue(modelData.caps_aimedBow, true);
    		}
    	}
//@-@b173
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
    		if (mod_Modchu_ModchuLib.modchu_Main.mmmLibVersion > 499) {
    			int i2 = entityplayer.getBrightnessForRender(f);
    			boolean b = false;
    			b = Modchu_Reflect.setFieldObject(mod_Modchu_ModchuLib.modchu_Main.MMM_ModelBaseNihil, "lighting", modelData.modelMain, i2, 1);
    			b = Modchu_Reflect.setFieldObject(mod_Modchu_ModchuLib.modchu_Main.MMM_ModelBaseNihil, "lighting", modelData.modelFATT, i2, 1);
    		}
    		setRenderCount(modelData.modelFATT, 0);
    	}
    	double d3 = doRenderSettingY(entityplayer, modelData , d1);
    	if (modelData.modelMain.model != null) mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.superDoRenderLiving(entityplayer, d, d3, d2, f, f1);
    }

    public double doRenderSettingY(EntityPlayer entityplayer,
    		PFLM_ModelData modelData, double d1) {
/*
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize
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
    	int version = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion();
    	boolean oldRender = mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender;
    	boolean isAether = mod_PFLM_PlayerFormLittleMaid.pflm_main.isAether;
    	if (oldRender) {
    		//d1 -= isAether ? 0.82D : (double)entityplayer.yOffset;
    		d1 -= isAether ? 0.82D : (double)entityplayer.yOffset;
    	}
    	if (!oldRender
    			&& version < 160) d1 += 0.82D;
    	if (entityplayer.isSneaking() && !(entityplayer instanceof EntityPlayerSP)) d1 -= 0.125D;
    	//if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize) d1 += 0.45D;

    	if (entityplayer.isRiding()) modelData.setCapsValue(modelData.caps_isRiding, true);
    	else modelData.setCapsValue(modelData.caps_isRiding, modelData.getCapsValueBoolean(modelData.caps_isSitting));
    	//Modchu_Debug.mDebug("entityplayer.isRiding()="+modelData.getCapsValueBoolean(modelData.caps_isRiding));
    	if (modelData.getCapsValueBoolean(modelData.caps_isRiding)) {
    		d1 += oldRender ? 0.25D : 0.30D;
    		if (!oldRender
    				&& version < 160) d1 -= 0.16D;
    		if (oldRender
    				&& isAether) d1 -= 0.1D;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize && mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != PFLM_Gui.modeOnline)  d1 -= 0.43D;
    	}
    	if (entityplayer.isSneaking()) {
    		if (modelData.getCapsValueBoolean(modelData.caps_isRiding)) {
    			d1 -= 0.1D;
    		}
    	}
    	if (modelData.getCapsValueBoolean(modelData.caps_isSitting)) {
    		if (!oldRender) d1 -= 0.3D;
    		if (!oldRender
    				&& version < 160) d1 += 0.3D;
    		if (oldRender) d1 += isAether ? 0.1D : -0.2D;
    		d1 += Modchu_ModelCapsHelper.getCapsValueDouble(modelData.modelMain.model, modelData.caps_sittingyOffset);
    	}
    	//Modchu_Debug.mDebug("return d1="+d1);
    	return d1;
    }

	private void doRenderSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
    	//modelData.modelMain.setEntityCaps(modelData);
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	float[] f9 = new float[2];
    	float f10 = mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderSwingProgress(entityplayer, 1.0F);
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
    	if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.mushroomConfusion) mod_PFLM_PlayerFormLittleMaid.pflm_main.mushroomConfusion(entityplayer, modelData);
    	}
    	if (modelData.getCapsValueBoolean(modelData.caps_changeModelFlag)) {
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isOlddays) modelData.modelMain.setCapsValue(modelData.caps_oldwalking, (Boolean) Modchu_Reflect.getFieldObject(ModelBiped.class, "oldwalking", modelData.modelMain.model));
    		modelData.setCapsValue(modelData.caps_partsSetInit, false);
    		modelData.setCapsValue(modelData.caps_partsSetFlag, 1);
    		modelData.modelMain.setCapsValue(modelData.caps_changeModel, modelData);
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.changeModel(entityplayer);
    		modelData.setCapsValue(modelData.caps_changeModelFlag, false);
    	}
    	PFLM_ModelDataMaster.instance.setHandedness(entityplayer, modelData.getCapsValueInt(modelData.caps_dominantArm));
    	modelData.modelMain.setCapsValue(modelData.caps_isSneak, entityplayer.isSneaking());
    	modelData.modelMain.setCapsValue(modelData.caps_isRiding, entityplayer.isRiding());
    	modelData.setCapsValue(modelData.caps_isPlayer, entityplayer.username == thePlayer.username);
    }

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	doRenderPlayerFormLittleMaid((EntityPlayer)entity, d, d1, d2, f, f1);
    }

    public void renderEquippedItems(Entity entityliving, float f)
    {
    	renderSpecials((EntityPlayer)entityliving, f);
    }

    public void renderSpecials(EntityPlayer entityplayer, float f)
    {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	if (modelData != null) ;else return;
    	//modelData.modelMain.setEntityCaps(modelData);
    	modelData.modelMain.setRender(mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
    	if (!mod_Modchu_ModchuLib.modchu_Main.useInvisibilityItem
    			| (mod_Modchu_ModchuLib.modchu_Main.useInvisibilityItem
    			&& !entityplayer.isInvisible())) modelData.modelMain.renderItems(entityplayer, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
    	if (entityplayer.username.equals("deadmau5")) {
    		int version = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion();
    		Class EntityLivingBase = Modchu_Reflect.loadClass("EntityLivingBase");
    		Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");
    		if (version > 159
    				&& (Boolean) Modchu_Reflect.invokeMethod(ThreadDownloadImageData.class, "func_110557_a", "isTextureUploaded", Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110309_l", entityplayer))
    				//&& ((AbstractClientPlayer) entityplayer).func_110309_l().func_110557_a())
    				| (version < 160
    						&& (Boolean) Modchu_Reflect.invokeMethod(Render.class, "func_76984_a", "loadDownloadableImageTexture", new Class[]{ String.class, String.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ (String) Modchu_Reflect.getFieldObject(Entity.class, "field_20047_bv", "skinUrl", entityplayer), (String)null }))) {
    			if (version > 159) Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110306_p", "getLocationSkin", mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer);
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
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderManager != null
    				&& (version > 159
    						&& (Boolean) Modchu_Reflect.invokeMethod("ThreadDownloadImageData", "func_110557_a", Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110310_o", entityplayer))
    				//&& ((AbstractClientPlayer) entityplayer).func_110310_o().func_110557_a()
    				| (version < 160
    						&& (String) Modchu_Reflect.getFieldObject(Entity.class, "field_622_aY", "cloakUrl", entityplayer) != null
    						&& (Boolean) Modchu_Reflect.invokeMethod(Render.class, "func_76984_a", "loadDownloadableImageTexture", new Class[]{ String.class, String.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ (String) Modchu_Reflect.getFieldObject(Entity.class, "field_622_aY", "cloakUrl", entityplayer), (String)null })))
    				&& ((version > 129
    						&& !(Boolean) Modchu_Reflect.invokeMethod(Entity.class, "func_82150_aj", "isInvisible", entityplayer)
    						&& !(Boolean) Modchu_Reflect.invokeMethod(EntityPlayer.class, "func_82241_s", "getHideCape", entityplayer)
    						| version < 130))) {
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
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isAether) {
    		renderSpecialsAether(entityplayer, f);
    	}
/*
    	RenderHelper.disableStandardItemLighting();
    	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    	OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
*/
    }

    public void renderSpecialsAether(EntityPlayer entityplayer, float f)
    {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	if (modelData != null) ;else return;
    	float f1 = 1.62F - (Float) modelData.getCapsValue(modelData.caps_YOffset);
    	Modchu_Reflect.invokeMethod("PFLMF_Aether", "renderSpecials", new Class[]{ Modchu_Reflect.loadClass("AbstractClientPlayer"), float.class, float.class }, new Object[]{ entityplayer, f, f1 });
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer) {
    	renderFirstPersonArm(entityplayer, 2);
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer, int i) {
    	//olddays導入時に2以外のint付きで呼ばれる。
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
/*
    	if (mc != null) ;else mc = mod_Modchu_ModchuLib.modchu_Main.getMinecraft();
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_71462_r", "currentScreen", mc);
    	if(currentScreen instanceof GuiSelectWorld) return;
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return;
*/
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	if (modelData != null) ;else return;
    	if (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
    		modelData.modelMain.setCapsValue(modelData.caps_firstPerson, false);
    		return;
    	}
    	doRenderSetting(entityplayer, modelData);
    	//float var2 = 1.0F;
    	//GL11.glColor3f(var2, var2, var2);
    	modelData.modelMain.setCapsValue(modelData.caps_firstPerson, true);
    	if (i >= 2
    			&& i != 1) {
    		if (modelData.modelMain != null) {
    			if (modelData.modelMain != null) modelData.modelMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, thePlayer);
    		}
    		if (modelData.modelFATT != null) modelData.modelFATT.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, thePlayer);
    	}
    	//Modchu_Debug.Debug("renderFirstPersonArm ResourceLocation="+((ResourceLocation) modelData.getCapsValue(modelData.caps_ResourceLocation)).func_110623_a());
    	if (modelData.modelMain.model != null
    			&& modelData.getCapsValue(modelData.caps_ResourceLocation) != null
    			&& getRenderManager() != null
    			&& getRenderManager().renderEngine != null) {
    		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) Modchu_Reflect.invokeMethod(Render.class, "func_110776_a", "bindTexture", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ modelData.getCapsValue(modelData.caps_ResourceLocation) });
    		else Modchu_Reflect.invokeMethod(Render.class, "func_76985_a", "loadTexture", new Class[]{ String.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ PFLM_ModelDataMaster.instance.getResourceLocation(modelData, entityplayer, 0) });
    		//Modchu_Debug.Debug("modelData.modelMain.model != null ? ="+(modelData.modelMain.model != null));
    		modelData.modelMain.model.renderFirstPersonHand(modelData);
    		//renderFirstPersonArmorRender(modelData, entityplayer, 0.0D, 0.0D, 0.0D, 0.0F, 0.0625F);
    	} else if (modelData.modelMain.model != null
    			&& mod_PFLM_PlayerFormLittleMaid.pflm_main.BipedClass.isInstance(modelData.modelMain.model)) {
    		modelData.modelMain.model.renderFirstPersonHand(modelData);
    	}
    	modelData.modelMain.setCapsValue(modelData.caps_firstPerson, false);
    }

    public boolean isActivatedForPlayer(EntityPlayer entityplayer) {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);
    	if (modelData != null) ;else return false;
/*
    	Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_71462_r", "currentScreen", mc);
    	if (currentScreen instanceof PFLM_GuiOthersPlayer) return false;
*/
    	return modelData.getCapsValueBoolean(modelData.caps_isActivated);
    }

    public double renderLivingLabel(Entity entityplayer, String par2Str, double d, double d1, double d2, int i)
    {
//-@-125
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isSmartMoving) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderName", new Class[]{ EntityPlayer.class, double.class, double.class, double.class }, pflm_RenderPlayerSmart, new Object[]{ entityplayer, d, d1, d2 });
//@-@125
    	if(mod_PFLM_PlayerFormLittleMaid.pflm_main.isRenderName
    			&& mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderManager != null
    			&& mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderManager.renderEngine != null) {
    		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData((EntityPlayer) entityplayer);
    		if (modelData == null) return d1;
    		double d3 = 0.0D;
    		double d4 = 0.0D;
    		double d5 = 0.0D;
    		double height = (double)modelData.modelMain.model.getHeight(modelData);
    		if(entityplayer.isSneaking()) d3 = 0.4D;
    		float f1 = modelData.getCapsValueFloat(modelData.caps_modelScale);
    		if (f1 > 0.0F) {
    			d5 = (double)(0.9375F - f1);
    			d4 = -height * d5;
    			if (f1 > 0.9375F) d4 -= 0.4D * d5;
    		}
    		return (d1 - 1.8D) + height + d3 + d4;
    	}
    	return d1;
    }

    public void renderModel(Entity entityliving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData((EntityPlayer) entityliving);
    	int version = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion();
    	if (!mod_Modchu_ModchuLib.modchu_Main.useInvisibilityBody
    					| (mod_Modchu_ModchuLib.modchu_Main.useInvisibilityBody
    					&& !entityliving.isInvisible())) {
    		int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
    		if (version < 160) {
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderManager != null) {
    				String skinUrl = PFLM_ModelDataMaster.instance.getSkinUrl(modelData, (EntityPlayer) entityliving);
    				String texture = (String) (skinMode == PFLM_ModelDataMaster.instance.skinMode_PlayerOnline
    						| skinMode == PFLM_ModelDataMaster.instance.skinMode_online ?
    						null : modelData.getCapsValue(modelData.caps_ResourceLocation, 0));
    				Modchu_Reflect.invokeMethod(Render.class, "func_76984_a", "loadDownloadableImageTexture", new Class[]{ String.class, String.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ skinUrl, texture });
    				//loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getTexture());
    				//Modchu_Debug.mlDebug("renderModel skinUrl="+skinUrl+" texture="+texture);
    			} else {
    				Modchu_Debug.mDebug("renderModel renderManager == null !!");
    			}
    		} else {
    			Object o = PFLM_ModelDataMaster.instance.getResourceLocation(modelData, entityliving);
    			if (o != null) Modchu_Reflect.invokeMethod(Render.class, "func_110776_a", "bindTexture", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer, new Object[]{ o });
    		}
    		modelData.modelMain.setArmorRendering(true);
    	} else {
    		modelData.modelMain.setArmorRendering(false);
    	}
    	modelData.modelMain.model.render(modelData, par2, par3, par4, par5, par6, par7, modelData.modelMain.isRendering);
    	//Modchu_Debug.mDebug("renderModel modelData.modelMain.isRendering="+modelData.modelMain.isRendering);
    }

    public void renderLivingAt(Entity entityliving, double par2, double par4, double par6)
    {
    	renderPlayerSleep(entityliving, par2, par4, par6);
    	mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.superRenderLivingAt(entityliving, par2, par4, par6);
    }

    public void renderPlayerSleep(Entity var1, double var2, double var4, double var6)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isSmartMoving) renderPlayerAt(var1, var2, var4, var6);
    }

    public void rotateCorpse(EntityPlayer entityliving, float f, float f1, float f2) {
    	rotatePlayer(entityliving, f, f1, f2);
    	mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.superRotateCorpse(entityliving, f, f1, f2);
    }

    public void rotatePlayer(EntityPlayer var1, float var2, float var3, float var4)
    {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(var1);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isSmartMoving
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
    	}
    }

    public float getDeathMaxRotation(Entity par1EntityLiving)
    {
        return 90.0F;
    }

    public float handleRotationFloat(Entity par1EntityLiving, float par2)
    {
        return (float)par1EntityLiving.ticksExisted + par2;
    }

    public void preRenderCallback(Entity par1EntityLiving, float par2)
    {
        renderPlayerScale((Entity) par1EntityLiving, par2);
    }

    public int getColorMultiplier(Entity par1EntityLiving, float par2, float par3) {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(par1EntityLiving);
    	setRenderCount(modelData.modelMain, 16);
    	return 0;
    }

    public int inheritRenderPass(Entity par1EntityLiving, int par2, float par3) {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(par1EntityLiving);
    	setRenderCount(modelData.modelFATT, 16);
    	return shouldRenderPass(par1EntityLiving, par2, par3);
    }

    private void setRenderCount(Object o, int i) {
/*
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
    		if (mod_Modchu_ModchuLib.modchu_Main.mmmLibVersion > 599) {
    			boolean b2 = false;
    			b2 = Modchu_Reflect.setFieldObject(mod_Modchu_ModchuLib.modchu_Main.MMM_ModelBaseNihil, "renderCount", o, i, 1);
    		}
    	}
*/
    	boolean b2 = false;
    	b2 = Modchu_Reflect.setFieldObject(o.getClass(), "renderCount", o, i, 1);
    }

    //smartMoving関連↓
    public RenderManager getRenderManager() {
    	return mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.renderManager;
    }

    public void renderPlayerAt(Entity var1, double var2, double var4, double var6)
    {
    	PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData((EntityPlayer) var1);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isSmartMoving
    			&& modelData != null) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderPlayerAt", new Class[]{ EntityPlayer.class, double.class, double.class, double.class }, pflm_RenderPlayerSmart, new Object[]{ var1, var2, var4, var6 });
    }

    public void renderGuiIngame(Object var0)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isSmartMoving) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderGuiIngame", new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, pflm_RenderPlayerSmart, new Object[]{ var0 });
    }
    //smartMoving関連↑
}

