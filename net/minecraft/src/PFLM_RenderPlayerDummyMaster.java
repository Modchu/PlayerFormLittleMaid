package net.minecraft.src;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerDummyMaster
{
	protected static MultiModelBaseBiped modelBasicOrig[];
	public static String[] armorFilename;
	private static final ItemStack[] armorItemStack = {new ItemStack(Item.helmetDiamond), new ItemStack(Item.plateDiamond), new ItemStack(Item.legsDiamond), new ItemStack(Item.bootsDiamond)};
	private boolean checkGlEnableWrapper = true;
	private boolean checkGlDisableWrapper = true;
	public static PFLM_ModelData modelData;
	public static boolean showMainModel = true;
	public static boolean showArmor = true;

	public PFLM_RenderPlayerDummyMaster() {
		super();
		modelBasicOrig = new MultiModel[3];
		modelBasicOrig[0] = new MultiModel(0.0F);
		modelBasicOrig[1] = new MultiModel(0.1F);
		modelBasicOrig[2] = new MultiModel(0.5F);
		modelData = new PFLM_ModelData(mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy);
		armorFilename = (String[]) (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ? Modchu_Reflect.getFieldObject(RenderBiped.class, "field_82424_k", "bipedArmorFilenamePrefix") :
			Modchu_Reflect.getFieldObject(RenderPlayer.class, "field_77110_j", "armorFilenamePrefix"));
	}

    protected int setArmorModel(Entity entityliving, int i, float f)
    {
    	//Modchu_Debug.mDebug("setArmorModel "+modelData.getCapsValue(modelData.caps_ResourceLocation, 0));
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	/*b181//*/byte byte0 = -1;
    	//b181 deleteboolean byte0 = false;
    	if (entity != null) ;else return byte0;
    	// アーマーの表示設定
    	modelData.modelFATT.renderParts = i;
    	ItemStack is = armorItemStack[i];
    	if (is != null && is.stackSize > 0) {
    		modelData.modelFATT.showArmorParts(i);
//-@-b181
    		byte0 = (byte) (is.isItemEnchanted() ? 15 : 1);
//@-@b181
    		armorTextureSetting(entity, is, i);
    		boolean flag1 = i == 1 ? true : false;
    		boolean isBiped = mod_PFLM_PlayerFormLittleMaid.pflm_main.BipedClass.isInstance(modelData.modelMain.model);
    		if (isBiped) {
    			((MultiModelBaseBiped) modelData.modelFATT.modelInner).setArmorBipedRightLegShowModel(modelData, flag1);
    			((MultiModelBaseBiped) modelData.modelFATT.modelInner).setArmorBipedLeftLegShowModel(modelData, flag1);
    		}
    	}
    	return byte0;
    }

    private void armorTextureSetting(Entity entityliving, ItemStack is, int i) {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	int i2 = i;
    	String t = (String) modelData.getCapsValue(modelData.caps_textureArmorName);
    	//Modchu_Debug.mDebug("setArmorModel t="+t);
    	boolean isBiped = mod_PFLM_PlayerFormLittleMaid.pflm_main.BipedClass.isInstance(modelData.modelMain.model);
    	if (t != null) ;else t = isBiped ? "Biped" : "default";
    	if (modelData.modelFATT.modelOuter != null
    			&& modelData.modelFATT.modelInner != null) {
    	} else {
    		PFLM_ModelDataMaster.instance.modelArmorInit(entity, modelData, t);
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
    					t2 = Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ Entity.class, ItemStack.class, String.class, int.class, int.class, String.class }, new Object[]{ entity, is, "/armor/" + a1 + "_" + 2 + ".png", i, 2, null });
    					t1 = Modchu_Reflect.invokeMethod("net.minecraftforge.client.ForgeHooksClient", "getArmorTexture", new Class[]{ Entity.class, ItemStack.class, String.class, int.class, int.class, String.class }, new Object[]{ entity, is, "/armor/" + a1 + "_" + 1 + ".png", i, 1, null });
    				} else {
    					t1 = Modchu_Reflect.invokeMethod(RenderBiped.class, "getArmorResource", new Class[]{ Entity.class, ItemStack.class, int.class, String.class }, new Object[]{ entity, is, 1, null });
    					t2 = Modchu_Reflect.invokeMethod(RenderBiped.class, "getArmorResource", new Class[]{ Entity.class, ItemStack.class, int.class, String.class }, new Object[]{ entity, is, 2, null });
    				}
    				if (i == 1) {
    					t3 = modelData.modelFATT.textureInner;
    					t3[i] = t2;
    					Modchu_Reflect.setFieldObject("MMM_ModelBaseDuo", "textureInner", modelData.modelFATT, t3);
    				}
    				t4 = modelData.modelFATT.textureOuter;
    				t4[i] = t1;
    				Modchu_Reflect.setFieldObject("MMM_ModelBaseDuo", "textureOuter", modelData.modelFATT, t4);
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
    				}
    				Modchu_Reflect.setFieldObject("MMM_ModelBaseDuo", "textureInner", modelData.modelFATT, t3);
    				Modchu_Reflect.setFieldObject("MMM_ModelBaseDuo", "textureOuter", modelData.modelFATT, t4);
    			}
    		}
    	} else {
    		t3 = modelData.modelFATT.textureInner;
    		t3[i] = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetArmorTexture(t, 64, is);
    		Modchu_Reflect.setFieldObject("MMM_ModelBaseDuo", "textureInner", modelData.modelFATT, t3);
    		t4 = modelData.modelFATT.textureOuter;
    		t4[i] = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetArmorTexture(t, 80, is);
    		Modchu_Reflect.setFieldObject("MMM_ModelBaseDuo", "textureOuter", modelData.modelFATT, t4);
    		//Modchu_Debug.mlDebug("t3["+i+"]="+t3[i]);
    	}
    	//Modchu_Debug.mlDebug("modelData.modelFATT.textureOuter["+i+"]="+modelData.modelFATT.textureOuter[i]);
    	//Modchu_Debug.mlDebug("modelData.modelFATT.textureInner["+i+"]="+modelData.modelFATT.textureInner[i]);
    }

    protected int shouldRenderPass(Entity entityliving, int i, float f)
    {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	modelData.modelFATT.isRendering = true;
    	mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.setRenderPassModel(modelData.modelFATT);
    	//Modchu_Debug.mDebug("PFLM_RenderPlayerDummyMaster shouldRenderPass entity.showArmor="+entity.showArmor);
    	return showArmor ? 1 : -1;
    }

	protected void preRenderCallback(Entity entityliving, float f) {
		PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
		float f1 = modelData.getCapsValueFloat(modelData.caps_modelScale);
		//Modchu_Debug.mDebug("preRenderCallback f1="+f1);
		if (f1 == 0.0F) {
			f1 = modelData.modelMain.model instanceof MultiModelBaseBiped ? ((MultiModelBaseBiped) modelData.modelMain.model).getModelScale(PFLM_RenderPlayerDummyMaster.modelData) : 0.9375F;
		}
		GL11.glScalef(f1, f1, f1);
	}

    public void oldDoRenderLivingPFLM(Entity entityliving, double d, double d1, double d2,
            float f, float f1) {
    	GL11.glPushMatrix();

    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    		shadersGlDisableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glDisable(GL11.GL_CULL_FACE);
    	}
    	int PFLMVersion = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion();
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
    		float prevLimbSwingAmount = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70722_aY", "prevLimbSwingAmount", entityliving);
    		float limbSwingAmount = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70721_aZ", "limbSwingAmount", entityliving);
    		float limbSwing = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70754_ba", "limbSwing", entityliving);
    		f2 = prevRenderYawOffset + (renderYawOffset - prevRenderYawOffset) * f1;
    		f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
    		f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
    		//Object currentScreen = Modchu_Reflect.getFieldObject("Minecraft", "field_71462_r", "currentScreen", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
    		//renderLivingAt(entityliving, d, d1, d2);
    		float f5 = handleRotationFloat((Entity) entityliving, f1);
    		//rotateCorpse((EntityPlayer) entityliving, f5, f2, f1);
    		float f6 = 0.0625F;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
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
    		if (PFLMVersion > 89
    				&& (Boolean) Modchu_Reflect.invokeMethod(EntityLivingBase, "func_70631_g_", "isChild", entityliving))
    		{
    			f8 *= 3F;
    		}
    		if (f7 > 1.0F)
    		{
    			f7 = 1.0F;
    		}

    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    			//Shaders.glEnableWrapper(GL11.GL_ALPHA_TEST);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
    			GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.AlphaBlend)
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    				//Shaders.glEnableWrapper(GL11.GL_BLEND);
    				shadersGlEnableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glEnable(GL11.GL_BLEND);
    			}
    			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    		}
    		else
    		{
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    				//Shaders.glDisableWrapper(k1);
    				shadersGlDisableWrapper(GL11.GL_BLEND);
    			} else {
    				GL11.glDisable(GL11.GL_BLEND);
    			}
    		}

    		modelData.modelMain.setEntityCaps(modelData);
    		modelData.setRender(mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy);
    		modelData.setCapsValue(modelData.caps_Entity, entityliving);
    		Modchu_Reflect.invokeMethod(modelData.modelMain.getClass(), "func_78086_a", "setLivingAnimations", new Class[]{ EntityLivingBase, float.class, float.class, float.class }, modelData.modelMain, new Object[]{ entityliving, f8, f7, f1 });
    		renderModel(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    		float f9 = 1.0F;
    		if (PFLMVersion < 80) {
    			//if (currentScreen == null | currentScreen instanceof GuiIngameMenu)
    			f9 = (Float) Modchu_Reflect.invokeMethod(Entity.class, "func_382_a", "getEntityBrightness", new Class[]{ float.class }, entityliving, new Object[]{ f1 });
    		}
    		modelData.modelFATT.showAllParts();
    		for (int i = 0; i < 4; i++)
    		{
    			int j = setArmorModel((Entity) entityliving, i, f);

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
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    						//Shaders.glEnableWrapper(GL11.GL_BLEND);
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    				}
    				f10 = mod_PFLM_PlayerFormLittleMaid.pflm_main.transparency;
    				GL11.glColor4f(f9, f9, f9, f10);
    				modelData.modelMain.setEntityCaps(modelData);
    				Modchu_Reflect.invokeMethod(mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderPassModel.getClass(), "func_78086_a", "setLivingAnimations", new Class[]{ EntityLivingBase, float.class, float.class, float.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderPassModel, new Object[]{ entityliving, f8, f7, f1});
    				//renderPassModel.setLivingAnimations(entityliving, f8, f7, f1);
    				((MMM_ModelBaseDuo) mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderPassModel).setArmorRendering(true);
    				if ((PFLMVersion > 129
    						&& !mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor)
    						| (mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor
    								&& !entityliving.isInvisible())
    								| PFLMVersion < 130) {
    					mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    				}
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.transparency != 1.0F) GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    				if (PFLMVersion < 90
    						| j != 15) {
    					continue;
    				}

    				float f11 = (float)entityliving.ticksExisted + f1;
    				if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) Modchu_Reflect.invokeMethod(Render.class, "func_110776_a", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy, Modchu_Reflect.newInstance("ResourceLocation", new Class[]{ String.class }, new Object[]{ "%blur%/misc/glint.png" }));
    				else Modchu_Reflect.invokeMethod(Render.class, "func_76985_a", "loadTexture", new Class[]{ String.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy, new Object[]{ "%blur%/misc/glint.png" });
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
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
    					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
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
    							&& !mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor)
    							| (PFLMVersion > 129
    									&& mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor
    									&& !entityliving.isInvisible())
    									| PFLMVersion < 130) {
    						mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    					}
    				}

    				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    				GL11.glMatrixMode(GL11.GL_TEXTURE);
    				GL11.glDepthMask(true);
    				GL11.glLoadIdentity();
    				GL11.glMatrixMode(GL11.GL_MODELVIEW);
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    					//Shaders.glEnableWrapper(GL11.GL_LIGHTING);
    					shadersGlEnableWrapper(GL11.GL_LIGHTING);
    				} else {
    					GL11.glEnable(GL11.GL_LIGHTING);
    				}

    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.AlphaBlend)
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    						//Shaders.glEnableWrapper(GL11.GL_BLEND);
    						shadersGlEnableWrapper(GL11.GL_BLEND);
    					} else {
    						GL11.glEnable(GL11.GL_BLEND);
    					}
    					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    				}
    				else
    				{
    					if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
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

    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
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
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    				&& (Boolean) Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash")) {
    			Modchu_Reflect.invokeMethod("Shaders", "setEntityHurtFlash", new Class[]{ int.class, int.class }, new Object[]{ 0, 0 });
    		}
    		//renderEquippedItems(entityliving, f1);
    		boolean hurtFlag = mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders
    				&& !(Boolean) Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash")
    				? true : false;
    		if (!hurtFlag) {
    			f9 = entityliving.getBrightness(f1);
    			int k = getColorMultiplier(entityliving, f9, f1);
    			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    				//Shaders.glDisableWrapper(GL11.GL_TEXTURE_2D);
    				shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
    			} else {
    				GL11.glDisable(GL11.GL_TEXTURE_2D);
    			}
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) Modchu_Reflect.invokeMethod("Shaders", "disableLightmap");
    			OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

    			int hurtTime = (Integer) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70737_aN", "hurtTime", entityliving);
    			int deathTime = (Integer) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70725_aQ", "deathTime", entityliving);
    			if ((k >> 24 & 0xff) > 0 || hurtTime > 0 || deathTime > 0)
    			{
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) Modchu_Reflect.invokeMethod("Shaders", "beginLivingDamage");
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
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
    							mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
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
    							mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
    						}
    					}
    				}

    				GL11.glDepthFunc(GL11.GL_LEQUAL);
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
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
    				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) Modchu_Reflect.invokeMethod("Shaders", "endLivingDamage");
    			}

    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
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
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    		//Shaders.glEnableWrapper(GL11.GL_TEXTURE_2D);
    		shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
    	} else {
    		GL11.glEnable(GL11.GL_TEXTURE_2D);
    	}
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isShaders) {
    		//Shaders.glEnableWrapper(GL11.GL_CULL_FACE);
    		shadersGlEnableWrapper(GL11.GL_CULL_FACE);
    	} else {
    		GL11.glEnable(GL11.GL_CULL_FACE);
    	}
    	GL11.glPopMatrix();
    	//Modchu_Reflect.invokeMethod("RendererLivingEntity", "func_77033_b", "passSpecialRender", new Class[]{ EntityLivingBase, double.class, double.class, double.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy, new Object[]{ entityliving, d, d1, d2});
    	//passSpecialRender(entityliving, d, d1, d2);
    }

    protected float handleRotationFloat(Entity par1EntityLiving, float par2)
    {
        return (float)par1EntityLiving.ticksExisted + par2;
    }

    protected int getColorMultiplier(Entity par1EntityLiving, float par2, float par3)
    {
        return 0;
    }

    protected int inheritRenderPass(Entity par1EntityLiving, int par2, float par3)
    {
        return shouldRenderPass(par1EntityLiving, par2, par3);
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

	public void glEnableWrapper(String s, int i) {
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

	public void glDisableWrapper(String s, int i) {
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
		modelData.modelFATT.modelInner.isRiding = modelData.modelFATT.modelOuter.isRiding = false;
		modelData.modelFATT.modelInner.onGrounds[0] = modelData.modelFATT.modelOuter.onGrounds[0] =
				mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderSwingProgress((EntityLiving) entity, f1);

		modelData.modelFATT.modelOuter.isSneak = modelData.modelFATT.modelInner.isSneak = modelData.modelMain.model.isSneak = false;
		double d3 = d1 - (double) entity.yOffset;
		if (entity.isSneaking()) {
			d3 -= 0.125D;
		}

		if (entity.isRiding()) {
			d3 += 0.15D;
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize) {
				d3 -= 0.53D;
			}
		}
		if (entity.isSneaking()) {
			if (entity.isRiding()) {
				d3 -= 0.1D;
			}
		}
	}

	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		PFLM_EntityPlayerDummy entityDummy = ((PFLM_EntityPlayerDummy) entity);
		if (modelData.getCapsValue(modelData.caps_textureName) == null) modelData.setCapsValue(modelData.caps_textureName, "default");
		if (modelData.getCapsValue(modelData.caps_textureArmorName) == null) modelData.setCapsValue(modelData.caps_textureArmorName, "default");
		if (modelData.getCapsValue(modelData.caps_ResourceLocation) != null) ;else {
			modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) Modchu_Reflect.newInstanceArray("ResourceLocation", 3));
		}
		Object resourceLocation = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture((String)modelData.getCapsValue(modelData.caps_textureName), modelData.getCapsValueInt(modelData.caps_maidColor));
		modelData.setCapsValue(modelData.caps_ResourceLocation, 0, resourceLocation);
		if (modelData.modelMain.model != null) ;else modelData.modelMain.model = modelBasicOrig[0];
		if (modelData.modelFATT.modelInner != null) ;else modelData.modelFATT.modelInner = modelBasicOrig[1];
		if (modelData.modelFATT.modelOuter != null) ;else modelData.modelFATT.modelOuter = modelBasicOrig[2];
		modelData.modelFATT.modelOuter.isWait = modelData.modelFATT.modelInner.isWait = modelData.modelMain.model.isWait;
/*
		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159
				&& mod_Modchu_ModchuLib.modchu_Main.mmmLibVersion > 499) {
			int i2 = entity.getBrightnessForRender(f);
			//Modchu_Debug.mDebug("i2="+i2);
			boolean b = false;
			b = Modchu_Reflect.setFieldObject(mod_Modchu_ModchuLib.modchu_Main.MMM_ModelBaseNihil, "lighting", modelData.modelMain, i2, 1);
			b = Modchu_Reflect.setFieldObject(mod_Modchu_ModchuLib.modchu_Main.MMM_ModelBaseNihil, "lighting", modelData.modelFATT, i2, 1);
		}
*/
		doRenderPlayerFormLittleMaid((EntityLiving) entityDummy, d, d1, d2, f, f1);
		mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.superDoRenderLiving(entityDummy, d, d1, d2, f, f1);
	}
/*
    public void setLightmapTextureCoords(int pValue) {
		int ls = pValue & 0xffff;
		int lt = pValue >>> 16;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
				(float) ls / 1.0F, (float) lt / 1.0F);
	}
*/
	public void allModelInit(Entity entity, boolean debug) {
		PFLM_ModelDataMaster.instance.modelInit(entity, modelData, (String)modelData.getCapsValue(modelData.caps_textureName), debug);
		PFLM_ModelDataMaster.instance.modelArmorInit(entity, modelData, (String)modelData.getCapsValue(modelData.caps_textureArmorName), debug);
	}

	protected void renderModel(Entity entityliving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	if (showMainModel) ;else return;
    	int version = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion();
    	if ((version > 129
    			&& (!mod_Modchu_ModchuLib.modchu_Main.useInvisibilityBody
    					| mod_Modchu_ModchuLib.modchu_Main.useInvisibilityBody
    					&& !entityliving.isInvisible()))
    					| version < 130) {
    		if (version < 159) {
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.renderManager != null) {
    				String skinUrl = null;
    				String texture = (String) (modelData.getCapsValue(modelData.caps_ResourceLocation));
    				Modchu_Reflect.invokeMethod(Render.class, "func_76984_a", "loadDownloadableImageTexture", new Class[]{ String.class, String.class }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy, new Object[]{ skinUrl, texture });
    				//loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getTexture());
    				//Modchu_Debug.mDebug("renderModel skinUrl="+skinUrl+" texture="+texture);
    			} else {
    				Modchu_Debug.mDebug("renderModel renderManager == null !!");
    			}
    		} else {
    			Object o = PFLM_ModelDataMaster.instance.getResourceLocation(modelData, entityliving);
    			if (o != null) Modchu_Reflect.invokeMethod(Render.class, "func_110776_a", "bindTexture", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy, new Object[]{ o });
    			else Modchu_Debug.lDebug("PFLM_RenderPlayerDummyMaster renderModel getResourceLocation null !!");
    		}
    		modelData.modelMain.setArmorRendering(true);
    	} else {
    		modelData.modelMain.setArmorRendering(false);
    	}
    	GL11.glPushMatrix();

    	//int i2 = entityliving.getBrightnessForRender(1.0F);
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159
    			&& mod_Modchu_ModchuLib.modchu_Main.mmmLibVersion > 499) {
    		//setLightmapTextureCoords(0x00f000f0);//61680
    		//GL11.glMatrixMode(GL11.GL_TEXTURE);
    		//GL11.glLoadIdentity();
    		//GL11.glMatrixMode(GL11.GL_MODELVIEW);
    		GL11.glDisable(GL11.GL_LIGHTING);
    		//GL11.glEnable(GL11.GL_BLEND);
    		//GL11.glDepthFunc(GL11.GL_LEQUAL);
    		//GL11.glEnable(GL11.GL_ALPHA_TEST);
    		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		//GL11.glDepthMask(true);
    		//OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    		//GL11.glDisable(GL11.GL_TEXTURE_2D);
    		//OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    		//setLightmapTextureCoords(i2);
    	}

    	modelData.modelMain.model.render(modelData, par2, par3, par4, par5, par6, par7, modelData.modelMain.isRendering);
    	//modelData.modelMain.render(entityliving, par2, par3, par4, par5, par6, par7);
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159
    			&& mod_Modchu_ModchuLib.modchu_Main.mmmLibVersion > 499) {
    		//setLightmapTextureCoords(i2);
    		//GL11.glDisable(GL11.GL_BLEND);
    		//GL11.glDisable(GL11.GL_ALPHA_TEST);
    		//GL11.glDepthMask(true);
    		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		//GL11.glDisable(GL11.GL_LIGHTING);
    		//GL11.glEnable(GL11.GL_LIGHTING);
    		//OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    		//GL11.glDisable(GL11.GL_TEXTURE_2D);
    		//OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    		//GL11.glDisable(GL11.GL_CULL_FACE);
    	}

    	GL11.glPopMatrix();
   	//Modchu_Debug.mDebug("renderModel modelData.modelMain.isRendering="+modelData.modelMain.isRendering);
    }
}
