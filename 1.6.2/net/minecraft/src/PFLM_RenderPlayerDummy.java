package net.minecraft.src;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerDummy extends RenderPlayer
{
	protected static MultiModelBaseBiped modelBasicOrig[];
	public static String[] armorFilenamePrefix;
	private static final ItemStack[] armorItemStack = {new ItemStack(Item.helmetDiamond), new ItemStack(Item.plateDiamond), new ItemStack(Item.legsDiamond), new ItemStack(Item.bootsDiamond)};
	private boolean checkGlEnableWrapper = true;
	private boolean checkGlDisableWrapper = true;
	public static PFLM_ModelData modelData;
	public static Class ForgeHooksClient;
	public static RenderManager renderManager;

	public PFLM_RenderPlayerDummy() {
		super();
		modelBasicOrig = new MultiModel[3];
		modelBasicOrig[0] = new MultiModel(0.0F);
		modelBasicOrig[1] = new MultiModel(0.1F);
		modelBasicOrig[2] = new MultiModel(0.5F);
		modelData = new PFLM_ModelData(this);
		armorFilenamePrefix = (String[]) Modchu_Reflect.getFieldObject(RenderBiped.class, "field_82424_k", "bipedArmorFilenamePrefix");
		if (mod_Modchu_ModchuLib.isForge) {
			ForgeHooksClient = PFLM_RenderPlayer.ForgeHooksClient;
		}
		renderManager = super.renderManager;
	}

    protected int setArmorModel(EntityLivingBase entityliving, int i, float f)
    {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	/*b181//*/byte byte0 = -1;
    	//b181 deleteboolean byte0 = false;
    	if (entity != null
    			&& entity.showArmor) ;else return byte0;
    	// �A�[�}�[�̕\���ݒ�
    	modelData.modelFATT.renderParts = i;
    	ItemStack is = armorItemStack[i];
    	if (is != null && is.stackSize > 0) {
    		modelData.modelFATT.showArmorParts(i);
//-@-b181
    		byte0 = (byte) (is.isItemEnchanted() ? 15 : 1);
//@-@b181
    		armorTextureSetting(entity, is, i);
    		boolean flag1 = i == 1 ? true : false;
    		boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelData.modelMain.model);
    		if (isBiped) {
    			((MultiModelBaseBiped) modelData.modelFATT.modelInner).setArmorBipedRightLegShowModel(modelData, flag1);
    			((MultiModelBaseBiped) modelData.modelFATT.modelInner).setArmorBipedLeftLegShowModel(modelData, flag1);
    		}
    	}
    	return byte0;
    }

    private void armorTextureSetting(EntityLiving entityliving, ItemStack is, int i) {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	int i2 = i;
    	String t = entity.textureArmorName;
    	//Modchu_Debug.mDebug("setArmorModel t="+t);
    	boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelData.modelMain.model);
    	if (t != null) ;else t = isBiped ? "Biped" : "default";
    	if (modelData.modelFATT.modelOuter != null
    			&& modelData.modelFATT.modelInner != null) {
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
    			if (mod_Modchu_ModchuLib.isForge) {
    				String t2 = (String) Modchu_Reflect.invokeMethod(ForgeHooksClient, "getArmorTexture", new Class[]{ ItemStack.class, String.class }, null, new Object[]{ is, "/armor/" + a1 + "_" + 2 + ".png" });
    				String t1 = (String) Modchu_Reflect.invokeMethod(ForgeHooksClient, "getArmorTexture", new Class[]{ ItemStack.class, String.class }, null, new Object[]{ is, "/armor/" + a1 + "_" + 1 + ".png" });
    				if (i == 1) {
    					//Modchu_Debug.mDebug("i="+i+" t2="+t2+" t1="+t1);
    					for(int k = 0; k < 4; k++) {
    						modelData.modelFATT.textureInner[i] = new ResourceLocation(t2);
    					}
    				}
    				modelData.modelFATT.textureOuter[i] = new ResourceLocation(t1);
    			} else {
    				modelData.modelFATT.textureInner[i] = new ResourceLocation("/armor/" + a1 + "_" + 2 + ".png");
    				modelData.modelFATT.textureOuter[i] = new ResourceLocation("/armor/" + a1 + "_" + 1 + ".png");
    			}
    		}
    	} else {
    		modelData.modelFATT.textureInner[i] = mod_Modchu_ModchuLib.textureManagerGetArmorTexture(t, 64, is);
    		modelData.modelFATT.textureOuter[i] = mod_Modchu_ModchuLib.textureManagerGetArmorTexture(t, 80, is);
    		//Modchu_Debug.mDebug("modelData.modelFATT.textureOuter["+i+"]="+modelData.modelFATT.textureOuter[i]);
    		//Modchu_Debug.mDebug("modelData.modelFATT.textureInner["+i+"]="+modelData.modelFATT.textureInner[i]);
    	}
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f)
    {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	if (entity != null) ;else return -1;
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
    			//Modchu_Debug.Debug("Armor1 return -1");
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
    	} else {
//-@-b181
			return -1;
//@-@b181
			//b181 deletereturn false;
    	}
	}

    @Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
		float f1 = entity.modelScale;
		if (f1 == 0.0F) {
			f1 = modelData.modelMain.model instanceof MultiModelBaseBiped ? ((MultiModelBaseBiped) modelData.modelMain.model).getModelScale() : 0.9375F;
		}
		GL11.glScalef(f1, f1, f1);
	}

    public void doRenderLiving(EntityLivingBase entityliving, double d, double d1, double d2,
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

    		modelData.modelMain.setRender(this);
    		modelData.owner = (EntityLiving) entityliving;
    		modelData.modelMain.setEntityCaps(modelData);
    		modelData.modelMain.setLivingAnimations(entityliving, f8, f7, f1);
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
    				func_110776_a(new ResourceLocation("%blur%/misc/glint.png"));
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
		modelData.owner = entity;
		modelData.modelMain.setEntityCaps(modelData);
		// 152deleteentity.skinUrl = null;
		// modelData.modelFATT.modelInner.isSneak = modelData.modelFATT.modelOuter.isSneak = mainModel.isSneak = false;
		modelData.modelFATT.modelInner.isRiding = modelData.modelFATT.modelOuter.isRiding = mainModel.isRiding = false;
		modelData.modelFATT.modelInner.onGrounds[0] = modelData.modelFATT.modelOuter.onGrounds[0] = mainModel.onGround =
				renderSwingProgress((EntityLiving) entity, f1);

		modelData.modelFATT.modelOuter.isSneak = modelData.modelFATT.modelInner.isSneak = modelData.modelMain.model.isSneak = false;
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
	}

    @Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		PFLM_EntityPlayerDummy entityDummy = ((PFLM_EntityPlayerDummy) entity);
		if (entityDummy.textureName == null) entityDummy.textureName = "default";
		if (entityDummy.textureArmorName == null) entityDummy.textureArmorName = "default";
		boolean flag = entityDummy.textureModel != null;
		if (modelData.getCapsValue(modelData.caps_ResourceLocation) != null) ;else {
			modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) new ResourceLocation[3]);
		}
		modelData.setCapsValue(modelData.caps_ResourceLocation, 0, mod_Modchu_ModchuLib.textureManagerGetTexture(entityDummy.textureName, entityDummy.maidColor));
		if (!flag) {
			entityDummy.textureArmor0 = null;
			entityDummy.textureArmor1 = null;
		}
		if (flag) {
			modelData.modelMain.model = (MMM_ModelMultiBase)
					(entityDummy.textureModel[0] != null
					&& !entityDummy.textureName.equalsIgnoreCase("default")
					//&& entityDummy.textureModel[0] instanceof MultiModelBaseBiped
					? entityDummy.textureModel[0] : modelBasicOrig[0]);
			modelData.modelFATT.modelInner = (MMM_ModelMultiBase)
					(entityDummy.textureModel[1] != null
					&& !entityDummy.textureArmorName.equalsIgnoreCase("default")
					//&& entityDummy.textureModel[1] instanceof MultiModelBaseBiped
					? entityDummy.textureModel[1] : modelBasicOrig[1]);
			modelData.modelFATT.modelOuter = (MMM_ModelMultiBase)
					(entityDummy.textureModel[2] != null
					&& !entityDummy.textureArmorName.equalsIgnoreCase("default")
					//&& entityDummy.textureModel[2] instanceof MultiModelBaseBiped
					? entityDummy.textureModel[2] : modelBasicOrig[2]);
		} else {
			modelData.modelMain.model = modelBasicOrig[0];
			modelData.modelFATT.modelInner = modelBasicOrig[1];
			modelData.modelFATT.modelOuter = modelBasicOrig[2];
		}
		modelData.modelFATT.modelOuter.isWait = modelData.modelFATT.modelInner.isWait = modelData.modelMain.model.isWait;
		doRenderPlayerFormLittleMaid((EntityLiving) entityDummy, d, d1, d2, f, f1);
	}

	private static void modelInit(Entity entity, String s) {
		Object[] models = mod_Modchu_ModchuLib.modelNewInstance(entity, s, true, true);
		//Modchu_Debug.mDebug("modelInit s="+s+" models[0] != null ? "+(models[0] != null));
		modelData.modelMain.model = models[0] != null ? (MultiModelBaseBiped) models[0] : new MultiModel(0.0F);
		modelData.modelMain.model.setCapsValue(((MultiModelBaseBiped) modelData.modelMain.model).caps_armorType, 0);
//-@-152
		s = mod_Modchu_ModchuLib.textureNameCheck(s);
		if (modelData.getCapsValue(modelData.caps_ResourceLocation) != null) ;else {
			modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) new ResourceLocation[3]);
		}
		modelData.setCapsValue(modelData.caps_ResourceLocation, 0, mod_Modchu_ModchuLib.textureManagerGetTexture(s, modelData.getCapsValueInt(modelData.caps_maidColor)));
//@-@152
	}

	private static void modelArmorInit(Entity entity, String s) {
		boolean isBiped = mod_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(modelData.modelMain.model);
		if (isBiped
				&& (s.equalsIgnoreCase("default")
						| s.equalsIgnoreCase("erasearmor"))) s = "Biped";
		Object[] models = mod_Modchu_ModchuLib.modelNewInstance(entity, s, false, true);
		float[] f1 = mod_Modchu_ModchuLib.getArmorModelsSize(models[0]);
		//Modchu_Debug.mDebug("modelArmorInit s="+s+" models[1] != null ? "+(models[1] != null));
		if (models != null
				&& models[1] != null) ;else {
					models = mod_Modchu_ModchuLib.modelNewInstance(isBiped ? "Biped" : null, false);
					f1 = mod_Modchu_ModchuLib.getArmorModelsSize(models[0]);
				}
		if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
			modelData.modelFATT.modelInner = (MultiModelBaseBiped) models[1];
			modelData.modelFATT.modelOuter = (MultiModelBaseBiped) models[2];
		} else {
			modelData.modelFATT.modelInner = models[1] != null ?
					(MultiModelBaseBiped) models[1] : !isBiped ? new MultiModel(f1[0]) : new MultiModel_Biped(f1[0]);
			modelData.modelFATT.modelOuter = models[2] != null ?
					(MultiModelBaseBiped) models[2] : !isBiped ? new MultiModel(f1[1]) : new MultiModel_Biped(f1[1]);
		}
		modelData.modelFATT.modelInner.setCapsValue(((MultiModelBaseBiped) modelData.modelFATT.modelInner).caps_armorType, 2);
		modelData.modelFATT.modelOuter.setCapsValue(((MultiModelBaseBiped) modelData.modelFATT.modelOuter).caps_armorType, 3);
//-@-152
		if (modelData.getCapsValue(modelData.caps_ResourceLocation) != null) ;else {
			modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) new ResourceLocation[3]);
		}
		modelData.setCapsValue(modelData.caps_ResourceLocation, 1, mod_Modchu_ModchuLib.textureManagerGetTexture(s, modelData.getCapsValueInt(modelData.caps_maidColor)));
		modelData.setCapsValue(modelData.caps_ResourceLocation, 2, mod_Modchu_ModchuLib.textureManagerGetTexture(s, modelData.getCapsValueInt(modelData.caps_maidColor)));
//@-@152
	}

    @Override
    protected void renderEquippedItems(EntityLivingBase entityliving, float f)
    {
    }

    protected void renderSpecials(EntityPlayer entityplayer, float f)
    {
    }

    @Override
    protected void renderModel(EntityLivingBase entityliving, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	//Modchu_Debug.mDebug("PFLM_RenderPlayerDummy renderModel "+func_110775_a(entityliving).func_110623_a());
    	if (func_110775_a(entityliving) != null
    			&& func_110775_a(entityliving).func_110623_a() != null
    			&& func_110775_a(entityliving).func_110624_b() != null) func_110777_b(entityliving);
//-@-b181
    	// 152deleteloadDownloadableImageTexture(entityliving.skinUrl, entityliving.getTexture());
//@-@b181
/*//b181delete
        loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getEntityTexture());
*///b181delete
    	modelData.modelMain.setArmorRendering(true);
    	modelData.modelMain.model.render(modelData, f, f1, f2, f3, f4, f5, true);
    }

    public static RenderManager getRenderManager() {
    	return renderManager;
    }

    @Override
    protected ResourceLocation func_110775_a(Entity entity) {
    	return (ResourceLocation) modelData.getCapsValue(modelData.caps_ResourceLocation, 0);
    }

    protected ResourceLocation func_110775_a(Entity entity, int i) {
    	return (ResourceLocation) modelData.getCapsValue(modelData.caps_ResourceLocation, i);
    }

    protected static void setResourceLocation(Entity entity, ResourceLocation[] resourceLocation) {
    	modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) resourceLocation);
    }

    protected static void setResourceLocation(Entity entity, ResourceLocation resourceLocation) {
    	modelData.setCapsValue(modelData.caps_ResourceLocation, 0, resourceLocation);
    }

    protected static void setResourceLocation(Entity entity, int i, ResourceLocation resourceLocation) {
    	modelData.setCapsValue(modelData.caps_ResourceLocation, i, resourceLocation);
    }
}
