package net.minecraft.src;

import java.io.File;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerDummy extends RenderPlayer
{
	protected static MultiModelBaseBiped modelBasicOrig[];
	public static MMM_ModelDuo modelMain;
	public static MMM_ModelDuo modelFATT;
	public static String[] armorFilenamePrefix;
	private static final ItemStack[] armorItemStack = {new ItemStack(Item.helmetDiamond), new ItemStack(Item.plateDiamond), new ItemStack(Item.legsDiamond), new ItemStack(Item.bootsDiamond)};
	private boolean checkGlEnableWrapper = true;
	private boolean checkGlDisableWrapper = true;
	public static Class ForgeHooksClient;

	public PFLM_RenderPlayerDummy() {
		super();
		modelMain = new MMM_ModelDuo(this);
		modelMain.isModelAlphablend = mod_PFLM_PlayerFormLittleMaid.AlphaBlend;
		modelMain.textureInner = new String [4];
		modelMain.textureOuter = new String [4];
		modelFATT = new MMM_ModelDuo(this);
		modelFATT.isModelAlphablend = mod_PFLM_PlayerFormLittleMaid.AlphaBlend;
		modelFATT.textureInner = new String [4];
		modelFATT.textureOuter = new String [4];
		modelBasicOrig = new MultiModel[3];
		modelBasicOrig[0] = new MultiModel(0.0F);
		modelBasicOrig[1] = new MultiModel(0.1F);
		modelBasicOrig[2] = new MultiModel(0.5F);
		armorFilenamePrefix = (String[]) Modchu_Reflect.getFieldObject(RenderPlayer.class, "h", "armorFilenamePrefix");
		if (mod_PFLM_PlayerFormLittleMaid.isForge) {
			ForgeHooksClient = PFLM_RenderPlayer.ForgeHooksClient;
		}
	}

    protected int setArmorModel(EntityLiving entityliving, int i, float f)
    {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	/*b181//*/byte byte0 = -1;
    	//b181 deleteboolean byte0 = false;
    	if (entity != null
    			&& entity.showArmor) ;else return byte0;
    	// アーマーの表示設定
    	modelFATT.renderParts = i;
    	ItemStack is = armorItemStack[i];
    	if (is != null && is.stackSize > 0) {
    		modelFATT.showArmorParts(i);
//-@-b181
    		byte0 = (byte) (is.isItemEnchanted() ? 15 : 1);
//@-@b181
    		armorTextureSetting(entity, is, i);
    		boolean flag1 = i == 1 ? true : false;
    		boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelMain.modelArmorInner);
    		if (isBiped) {
    			((MultiModelBaseBiped) modelFATT.modelArmorInner).setArmorBipedRightLegShowModel(flag1);
    			((MultiModelBaseBiped) modelFATT.modelArmorInner).setArmorBipedLeftLegShowModel(flag1);
    		}
    	}
    	return byte0;
    }

    private void armorTextureSetting(EntityLiving entityliving, ItemStack is, int i) {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	int i2 = i;
    	String t = entity.textureArmorName;
    	//Modchu_Debug.mDebug("setArmorModel t="+t);
    	boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelMain.modelArmorInner);
    	if (t != null) ;else t = isBiped ? "Biped" : "default";
    	if (modelFATT.modelArmorOuter != null
    			&& modelFATT.modelArmorInner != null) {
    	} else {
    		modelArmorInit(entity, t);
    	}
    	if (isBiped) {
    		ItemArmor itemarmor = null;
    		boolean flag = false;
    		Item item = is.getItem();
    		if(item instanceof ItemArmor) {
    			itemarmor = (ItemArmor)item;
    			flag = itemarmor != null && is.stackSize > 0;
    		}
    		String a1 = itemarmor.renderIndex < armorFilenamePrefix.length ? armorFilenamePrefix[itemarmor.renderIndex] : armorFilenamePrefix[armorFilenamePrefix.length - 1];
    		if (flag) {
    			if (mod_PFLM_PlayerFormLittleMaid.isForge) {
    				String t2 = (String) Modchu_Reflect.invokeMethod(ForgeHooksClient, "getArmorTexture", new Class[]{ ItemStack.class, String.class }, null, new Object[]{ is, "/armor/" + a1 + "_" + 2 + ".png" });
    				String t1 = (String) Modchu_Reflect.invokeMethod(ForgeHooksClient, "getArmorTexture", new Class[]{ ItemStack.class, String.class }, null, new Object[]{ is, "/armor/" + a1 + "_" + 1 + ".png" });
    				if (i == 1) {
    					//Modchu_Debug.mDebug("i="+i+" t2="+t2+" t1="+t1);
    					for(int k = 0; k < 4; k++) {
    						modelFATT.textureInner[i] = t2;
    					}
    				}
    				modelFATT.textureOuter[i] = t1;
    			} else {
    				modelFATT.textureInner[i] = "/armor/" + a1 + "_" + 2 + ".png";
    				modelFATT.textureOuter[i] = "/armor/" + a1 + "_" + 1 + ".png";
    			}
    		}
    	} else {
    		modelFATT.textureInner[i] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetArmorTextureName(t, 64, is);
    		modelFATT.textureOuter[i] = mod_PFLM_PlayerFormLittleMaid.textureManagerGetArmorTextureName(t, 80, is);
    		//Modchu_Debug.mDebug("modelFATT.textureOuter["+i+"]="+modelFATT.textureOuter[i]);
    		//Modchu_Debug.mDebug("modelFATT.textureInner["+i+"]="+modelFATT.textureInner[i]);
    	}
    }

    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	if (entity != null) ;else return -1;
    	String t = null;
    	if (i < 4 && modelFATT.modelArmorOuter != null)
    	{
//-@-b173
    		if (modelFATT.textureOuter == null) return -1;
//@-@b173
    		// b173deleteif (modelFATT.textureOuter == null) return false;
    		t = modelFATT.textureOuter[i];
    		if (t == null)
    		{
//-@-b181
    			return -1;
//@-@b181
    			//b181 deletereturn false;
    		} else {
    			setRenderPassModel(modelFATT);
//-@-b181
    			return 1;
//@-@b181
    			//b181 deletereturn true;
    		}
    	}
    	if (i < 8 && modelFATT.modelArmorInner != null)
    	{
//-@-b173
    		if (modelFATT.textureInner == null) return -1;
//@-@b173
    		// b173deleteif (modelFATT.textureInner == null) return false;
    		t = modelFATT.textureInner[i - 4];
    		if (t == null) {
    			//Modchu_Debug.Debug("Armor1 return -1");
//-@-b181
    			return -1;
//@-@b181
    			//b181 deletereturn false;
    		} else {
    			setRenderPassModel(modelFATT);
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

	protected void preRenderCallback(EntityLiving entityliving, float f) {
		PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
		float f1 = entity.modelScale;
		if (f1 == 0.0F) {
			f1 = ((MultiModelBaseBiped) modelMain.modelArmorInner).getModelScale();
		}
		GL11.glScalef(f1, f1, f1);
	}

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
    	GL11.glPushMatrix();
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    		shadersGlDisableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glDisable(GL11.GL_CULL_FACE);
    	}

    	try
    	{
    		float f2 = entityliving.prevRenderYawOffset + (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * f1;
    		float f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
    		float f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
    		//renderLivingAt(entityliving, d, d1, d2);
    		float f5 = handleRotationFloat(entityliving, f1);
    		//rotateCorpse(entityliving, f5, f2, f1);
    		float f6 = 0.0625F;
    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			shadersGlEnableWrapper(GL12.GL_RESCALE_NORMAL);
    		} else {
    			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    		}
    		GL11.glScalef(-1F, -1F, 1.0F);
    		preRenderCallback(entityliving, f1);
    		GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
    		float f7 = entityliving.prevLimbYaw + (entityliving.limbYaw - entityliving.prevLimbYaw) * f1;
    		float f8 = entityliving.limbSwing - entityliving.limbYaw * (1.0F - f1);
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
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    				shadersGlEnableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glEnable(GL11.GL_BLEND);
    			}
    			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    		}
    		else
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    				shadersGlDisableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glDisable(GL11.GL_BLEND);
    			}
    		}

    		modelMain.modelArmorInner.setLivingAnimations(entityliving, f8, f7, f1);
    		renderModel(entityliving, f8, f7, f5, f3 - f2, f4, f6);

    		for (int i = 0; i < 4; i++)
    		{
    			int j = setArmorModel(entityliving, i, f);

    			//b181deleteif (!j)
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

    				if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    					GL11.glColor4f(1, 1, 1, mod_PFLM_PlayerFormLittleMaid.transparency);
    				}
    				renderPassModel.setLivingAnimations(entityliving, f8, f7, f1);
    				renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
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
    					renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    				}

    				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    				GL11.glMatrixMode(GL11.GL_TEXTURE);
    				GL11.glDepthMask(true);
    				GL11.glLoadIdentity();
    				GL11.glMatrixMode(GL11.GL_MODELVIEW);
    				if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    					shadersGlEnableWrapper(GL11.GL_LIGHTING);
    				} else {
    					GL11.glEnable(GL11.GL_LIGHTING);
    				}

    				if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend
    						&& entityliving instanceof EntityPlayer)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    				}
    				else
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
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
    			shadersGlDisableWrapper(GL11.GL_BLEND);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glDisable(GL11.GL_BLEND);
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		renderEquippedItems(entityliving, f1);
    		float f9 = entityliving.getBrightness(f1);
    		int k = getColorMultiplier(entityliving, f9, f1);
    		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
    		} else {
    			GL11.glDisable(GL11.GL_TEXTURE_2D);
    		}
    		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

    		if ((k >> 24 & 0xff) > 0 || entityliving.hurtTime > 0 || entityliving.deathTime > 0)
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    				shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
    				shadersGlDisableWrapper(GL11.GL_ALPHA_TEST);
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
    				modelMain.modelArmorInner.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);

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
    				modelMain.modelArmorInner.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);

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
    				shadersGlDisableWrapper(GL11.GL_BLEND);
    				shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    				shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
    			} else {
    				GL11.glDisable(GL11.GL_BLEND);
    				GL11.glEnable(GL11.GL_ALPHA_TEST);
    				GL11.glEnable(GL11.GL_TEXTURE_2D);
    			}
    		}

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
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
    		shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
    	} else {
    		GL11.glEnable(GL11.GL_TEXTURE_2D);
    	}
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    	if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    		shadersGlEnableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glEnable(GL11.GL_CULL_FACE);
    	}
    	GL11.glPopMatrix();
    	//passSpecialRender(entityliving, d, d1, d2);
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

	public void doRenderPlayerFormLittleMaid(EntityLiving entity, double d,
			double d1, double d2, float f, float f1) {
		modelMain.setModelCaps(null);
		entity.skinUrl = null;
		// modelFATT.modelArmorInner.isSneak = modelFATT.modelArmorOuter.isSneak = mainModel.isSneak = false;
		modelFATT.modelArmorInner.isRiding = modelFATT.modelArmorOuter.isRiding = mainModel.isRiding = false;
		modelFATT.modelArmorInner.onGround = modelFATT.modelArmorOuter.onGround = mainModel.onGround =
				renderSwingProgress((EntityLiving) entity, f1);

		modelFATT.modelArmorOuter.isSneak = modelFATT.modelArmorInner.isSneak = modelMain.modelArmorInner.isSneak = false;
		double d3 = d1 - (double) entity.yOffset;
		if (entity.isSneaking()) {
			d3 -= 0.125D;
		}

		if (entity.isRiding()) {
			d3 += 0.15D;
			if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
				d3 -= 0.53D;
			}
		}
		if (entity.isSneaking()) {
			if (entity.isRiding()) {
				d3 -= 0.1D;
			}
		}
		doRenderLiving((EntityLiving) entity, d, d3, d2, f, f1);
		// modelFATT.modelArmorOuter.aimedBow = modelFATT.modelArmorInner.aimedBow = mainModel.aimedBow = modelMain.modelArmorInner.aimedBow = false;
		// modelFATT.modelArmorOuter.isSneak = modelFATT.modelArmorInner.isSneak = mainModel.isSneak = modelMain.modelArmorInner.isSneak = false;
		// modelFATT.modelArmorOuter.heldItemRight = modelFATT.modelArmorInner.heldItemRight = mainModel.heldItemRight = modelMain.modelArmorInner.heldItemRight = 0;
	}

    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
    	PFLM_EntityPlayerDummy entityDummy = ((PFLM_EntityPlayerDummy) entity);
    	if (entityDummy.textureName == null) entityDummy.textureName = "default";
    	boolean flag = entityDummy.textureModel != null;
    	if (!flag) {
    		entityDummy.texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(entityDummy.textureName, entityDummy.maidColor);
    		entityDummy.textureArmor0 = null;
    		entityDummy.textureArmor1 = null;
    	}
    	if (flag) {
    		modelMain.modelArmorInner = (MultiModelBaseBiped)
    				(entityDummy.textureModel[0] != null
    				&& !entityDummy.textureName.equalsIgnoreCase("default")
					&& entityDummy.textureModel[0] instanceof MultiModelBaseBiped ?
    						entityDummy.textureModel[0] : modelBasicOrig[0]);
    		modelFATT.modelArmorOuter = (MultiModelBaseBiped)
    				(entityDummy.textureModel[1] != null
    				&& !entityDummy.textureArmorName.equalsIgnoreCase("default")
					&& entityDummy.textureModel[1] instanceof MultiModelBaseBiped ?
    						entityDummy.textureModel[1] : modelBasicOrig[1]);
    		modelFATT.modelArmorInner = (MultiModelBaseBiped)
    				(entityDummy.textureModel[2] != null
    				&& !entityDummy.textureArmorName.equalsIgnoreCase("default")
					&& entityDummy.textureModel[2] instanceof MultiModelBaseBiped ?
    						entityDummy.textureModel[2] : modelBasicOrig[2]);
    	} else {
    		modelMain.modelArmorInner = modelBasicOrig[0];
    		modelFATT.modelArmorOuter = modelBasicOrig[1];
    		modelFATT.modelArmorInner = modelBasicOrig[2];
    	}
    	modelFATT.modelArmorOuter.isWait = modelFATT.modelArmorInner.isWait = modelMain.modelArmorInner.isWait;
    	doRenderPlayerFormLittleMaid((EntityLiving) entityDummy, d, d1, d2, f, f1);
    }

	private static void modelInit(Entity entity, String s) {
		Object[] models = mod_PFLM_PlayerFormLittleMaid.modelNewInstance(entity, s, false);
		//Modchu_Debug.mDebug("modelInit s="+s+" models[0] != null ? "+(models[0] != null));
		modelMain.modelArmorInner = (MultiModelBaseBiped) (models[0] != null ? models[0] : new MultiModel(0.0F));
		modelMain.modelArmorInner.setCapsValue(((MultiModelBaseBiped) modelMain.modelArmorInner).caps_armorType, 0);
	}

	private static void modelArmorInit(Entity entity, String s) {
		boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelMain.modelArmorInner);
		if (isBiped
				&& (s.equalsIgnoreCase("default")
						| s.equalsIgnoreCase("erasearmor"))) s = "Biped";
		Object[] models = mod_PFLM_PlayerFormLittleMaid.modelNewInstance(entity, s, false);
		float[] f1 = mod_PFLM_PlayerFormLittleMaid.getArmorModelsSize(models[0]);
		//Modchu_Debug.mDebug("modelArmorInit s="+s+" models[1] != null ? "+(models[1] != null));
		if (models != null
				&& models[1] != null) ;else {
					models = mod_PFLM_PlayerFormLittleMaid.modelNewInstance(isBiped ? "Biped" : null);
					f1 = mod_PFLM_PlayerFormLittleMaid.getArmorModelsSize(models[0]);
				}
		if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			modelFATT.modelArmorInner = (MultiModelBaseBiped) models[1];
			modelFATT.modelArmorOuter = (MultiModelBaseBiped) models[2];
		} else {
			modelFATT.modelArmorInner = (MultiModelBaseBiped) (models[1] != null ?
					models[1] : !isBiped ? new MultiModel(f1[0]) : new MultiModel_Biped(f1[0]));
			modelFATT.modelArmorOuter = (MultiModelBaseBiped) (models[2] != null ?
					models[2] : !isBiped ? new MultiModel(f1[1]) : new MultiModel_Biped(f1[1]));
		}
		modelFATT.modelArmorInner.setCapsValue(((MultiModelBaseBiped) modelFATT.modelArmorInner).caps_armorType, 1);
		modelFATT.modelArmorOuter.setCapsValue(((MultiModelBaseBiped) modelFATT.modelArmorOuter).caps_armorType, 2);
	}

    protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
    }

    protected void renderSpecials(EntityPlayer entityplayer, float f)
    {
    }

    protected void renderModel(EntityLiving entityliving, float f, float f1, float f2, float f3, float f4, float f5)
    {
//-@-b181
    	loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getTexture());
//@-@b181
/*//b181delete
        loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getEntityTexture());
*///b181delete
        modelMain.modelArmorInner.render(entityliving, f, f1, f2, f3, f4, f5);
    }
}
