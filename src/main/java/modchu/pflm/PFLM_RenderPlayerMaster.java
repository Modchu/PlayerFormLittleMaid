package modchu.pflm;import java.util.HashMap;import modchu.lib.Modchu_AS;import modchu.lib.Modchu_Debug;import modchu.lib.Modchu_EntityCapsHelper;import modchu.lib.Modchu_IRenderPlayer;import modchu.lib.Modchu_IRenderPlayerMaster;import modchu.lib.Modchu_Main;import modchu.lib.Modchu_Reflect;import modchu.model.ModchuModel_ConfigData;import modchu.model.ModchuModel_Main;import modchu.model.ModchuModel_ModelBaseNihil;import modchu.model.ModchuModel_ModelDataBase;import modchu.model.multimodel.base.MultiModelBaseBiped;import org.lwjgl.opengl.GL11;public class PFLM_RenderPlayerMaster extends PFLM_RenderMasterBase implements Modchu_IRenderPlayerMaster {	public String[] armorFilename;	private boolean checkGlEnableWrapper = true;	private boolean checkGlDisableWrapper = true;	public boolean isSizeMultiplier = false;	private Object dummyMainModel;	public Object pflm_RenderPlayerSmart;//-@-125	public Object pflm_RenderRenderSmart;//@-@125// b173deleteprivate RenderBlocks renderBlocks;	public PFLM_RenderPlayerMaster(HashMap<String, Object> map) {		super(map);		isSizeMultiplier = ModchuModel_Main.isGulliver = ModchuModel_Main.sizeMultiplier != null;		if (PFLM_Main.isSmartMoving) {			//Modchu_Debug.mDebug("PFLM_RenderPlayerMaster() isSmartMoving");			pflm_RenderPlayerSmart = Modchu_Reflect.newInstance("PFLM_RenderPlayerSmart", new Class[]{ PFLM_RenderPlayerMaster.class }, new Object[]{ base });//-@-125			pflm_RenderRenderSmart = Modchu_Reflect.newInstance("PFLM_RenderRenderSmart", new Class[]{ PFLM_RenderPlayerMaster.class }, new Object[]{ base });//@-@125		}		if (PFLM_Main.isShaders) {			if (Modchu_Reflect.getMethod("Shaders", "setEntityHurtFlash", new Class[]{ int.class, int.class }) != null					&& Modchu_Reflect.getFieldObject("Shaders", "useEntityHurtFlash") != null) {				shadersHurtFlashFlag = true;			}		}		// b173deleterenderBlocks = new RenderBlocks();		if (Modchu_Main.getMinecraftVersion() > 179) {			dummyMainModel = Modchu_Reflect.newInstance("ModelPlayer", new Class[]{ float.class, boolean.class }, new Object[]{ 0.0F, false });		} else {			dummyMainModel = Modchu_Reflect.newInstance("ModelBiped", new Class[]{ float.class }, new Object[]{ 0.0F });		}		Modchu_Debug.lDebug("PFLM_RenderPlayerMaster init end.");	}	@Override	public Object getArmorItemStack(Object entity, int i) {		return Modchu_AS.get(Modchu_AS.entityPlayerInventoryPlayerArmorItemInSlot, entity, i);	}	@Override	public int shouldRenderPass(Object entity, int i, float f) {		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);		if (Modchu_Main.getMinecraftVersion() > 179) {			//setRenderPassModel(modelData.modelFATT.modelInner, modelData.modelFATT.modelOuter);		} else {			setRenderPassModel(modelData.armorBase);		}		//Modchu_Debug.mDebug("PFLM_RenderPlayerMaster shouldRenderPass i="+i+" "+(modelData.modelFATT.textureInner[i > 4 ? i - 4 : i]));		if ((i < 4				&& modelData.modelFATT.modelOuter != null				&& modelData.modelFATT.textureOuter != null				&& modelData.modelFATT.textureOuter[i] != null)				| (i < 8						&& modelData.modelFATT.modelInner != null						&& modelData.modelFATT.textureInner != null						&& modelData.modelFATT.textureInner[i > 4 ? i - 4 : i] != null)) {			return 1;		}		return -1;	}	@Override	public void preRenderCallback(Object entity, float par2) {		renderScale(entity, par2);	}	@Override	public void renderScale(Object entity, float f) {		if (!isSizeMultiplier				&& !PFLM_ConfigData.useScaleChange) {			superPreRenderCallback(entity, f);			return;		}		float f1 = getScale(entity, f);		GL11.glScalef(f1, f1, f1);		return;	}	public float getScale(Object entity, float f) {		if (isSizeMultiplier) {			float f2 = !PFLM_Main.oldRender ? 0.9375F : (Float) Modchu_Reflect.invoke(ModchuModel_Main.sizeMultiplier, entity);			return f2;		}		if (!PFLM_ConfigData.useScaleChange) {			return 0.9375F;		}		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);		if (modelData != null) ;		else return 0.9375F;		float f1 = modelData.getCapsValueBoolean(modelData.caps_isPlayer) ? PFLM_ConfigData.modelScale : modelData.getCapsValueFloat(modelData.caps_modelScale);		if (f1 == 0.0F) f1 = ((MultiModelBaseBiped) modelData.modelMain.model).getModelScale(modelData);		if (PFLM_Main.oldRender) GL11.glScalef(f1, f1, f1);		//Modchu_Debug.lDebug("getScale f1="+f1);		return f1;	}	@Override	public void doRender(Object entity, double d, double d1, double d2, float f, float f1) {		ModchuModel_ModelDataBase data = PFLM_ModelDataMaster.instance.getPlayerData(entity);		PFLM_ModelData modelData = data != null				&& data instanceof PFLM_ModelData ? (PFLM_ModelData) data : null;		if (modelData != null); else {			if (data != null) super.doRender(entity, d, d1, d2, f, f1);			return;		}		doRenderSetting(entity, modelData);		boolean isPlayer = ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).isPlayerCheck(modelData, entity);		if (!isPlayer) ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).sitSleepResetCheck(modelData, entity);		if (PFLM_Main.isSmartMoving) {			Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderPlayer", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), double.class, double.class, double.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderPlayerSmart, new Object[]{ entity, d, d1, d2, f, f1, modelData });//-@-125			Modchu_Reflect.invokeMethod("PFLM_RenderRenderSmart", "renderPlayer", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), double.class, double.class, double.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderRenderSmart, new Object[]{ entity, d, d1, d2, f, f1, modelData });//@-@125		}		float prevLimbSwingAmount = Modchu_AS.getFloat(Modchu_AS.entityLivingBasePrevLimbSwingAmount, entity);		float f8 = Modchu_AS.getFloat(Modchu_AS.entityLivingBaseLimbSwing, entity) - prevLimbSwingAmount * (1.0F - f1);		((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).waitModeSetting(modelData, entity, f8);		if (isPlayer) {			modelData.setCapsValue(modelData.caps_isWait, PFLM_Main.isWait);			//Modchu_Debug.mDebug("isPlayer setCapsValue caps_isWait="+(modelData.modelMain.getCapsValue(modelData, modelData.caps_isWait)));		}		modelData.setCapsValue(modelData.caps_isOpenInv, d == 0.0D && d1 == 0.0D && d2 == 0.0D && f == 0.0F && f1 == 1.0F);		Object itemstack = Modchu_AS.get(Modchu_AS.entityPlayerInventoryGetCurrentItem, entity);		modelData.modelMain.setCapsValue(modelData, modelData.caps_heldItemLeft, 0);		modelData.modelMain.setCapsValue(modelData, modelData.caps_heldItemRight, 0);//-@-b173		modelData.modelMain.setCapsValue(modelData, modelData.caps_aimedBow, false);		if (itemstack != null				&& Modchu_AS.getInt(Modchu_AS.entityPlayerGetItemInUseCount, entity) > 0) {			Enum enumaction = Modchu_AS.getEnum(Modchu_AS.itemStackGetItemUseAction, itemstack);			Enum enumactionBlock = Modchu_AS.getEnum(Modchu_AS.enumActionBlock);			Enum enumactionBow = Modchu_AS.getEnum(Modchu_AS.enumActionBow);			if (enumaction == enumactionBlock) {				if (modelData.modelMain.model.isItemHolder(modelData)) {					if (Modchu_EntityCapsHelper.getCapsValueInt(modelData.modelMain.model, modelData, modelData.caps_dominantArm) == 1) modelData.modelMain.setCapsValue(modelData, modelData.caps_heldItemLeft, 3);					if (Modchu_EntityCapsHelper.getCapsValueInt(modelData.modelMain.model, modelData, modelData.caps_dominantArm) == 0) modelData.modelMain.setCapsValue(modelData, modelData.caps_heldItemRight, 3);				}			} else if (enumaction == enumactionBow) {				modelData.modelMain.setCapsValue(modelData, modelData.caps_aimedBow, true);			}		}//@-@b173		if (Modchu_Main.getMinecraftVersion() > 159) {			if (Modchu_Main.mmmLibVersion > 499) {				int i2 = Modchu_AS.getInt(Modchu_AS.entityGetBrightnessForRender, entity, f);				modelData.modelMain.lighting = i2;				modelData.modelFATT.lighting = i2;			}			setRenderCount(modelData.modelFATT, 0);		}		if (isSizeMultiplier) {			modelData.setCapsValue(modelData.caps_freeVariable, "isGliding", (Boolean) Modchu_Reflect.invokeMethod(entity.getClass(), "isGliding", entity));			modelData.setCapsValue(modelData.caps_freeVariable, "isRafting", (Boolean) Modchu_Reflect.invokeMethod(entity.getClass(), "isRafting", entity));			modelData.setCapsValue(modelData.caps_freeVariable, "doesUmbrella", (Boolean) Modchu_Reflect.invokeMethod(entity.getClass(), "doesUmbrella", entity));		}		d1 = doRenderSettingY(entity, modelData, d1);		//Modchu_Debug.dDebug("doRender rotationYaw="+Modchu_AS.get(Modchu_AS.entityRotationYaw, entity));		//Modchu_Debug.dDebug("entityLivingBaseRotationYawHead="+Modchu_AS.get(Modchu_AS.entityLivingBaseRotationYawHead, entity), 1);		GL11.glPushMatrix();		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);		if (modelData.modelMain.model != null) {			if (!PFLM_Main.oldRender) {				Modchu_AS.set(Modchu_AS.renderMainModel, base, modelData.modelBase);				superDoRenderLiving(entity, d, d1, d2, f, f1);			} else oldDoRenderLivingPFLM(modelData, entity, d, d1, d2, f, f1);		}		GL11.glPopAttrib();		GL11.glPopMatrix();		//Modchu_Debug.dDebug("doRender end.", 2);	}	public double doRenderSettingY(Object entity, PFLM_ModelData modelData, double d1) {/*		if (PFLM_ConfigData.isModelSize//-@-125				&& (Boolean) Modchu_Reflect.invokeMethod("Minecraft", "isSingleplayer", mc)//@-@125				// 125delete&& thePlayer.worldObj.isRemote				&& !entityplayer.isRiding()) {			//d1 = d1 - (double)((MultiModelBaseBiped) modelData.modelMain.model).getYOffset();			d1 = d1 - (double)entityplayer.YOffset;		}		else*/		int version = Modchu_Main.getMinecraftVersion();		boolean oldRender = PFLM_Main.oldRender;		boolean isAether = Modchu_Main.isAether;		if (oldRender) {			d1 -= oldRender					&& isAether ? 0.82D : (double) Modchu_AS.getFloat(Modchu_AS.entityYOffset, entity);			//Modchu_Debug.mDebug("doRenderSettingY YOffset="+(Modchu_AS.getFloat(Modchu_AS.entityYOffset, entity)));			//d1 -= (double)entityplayer.YOffset;		} else {			if (isAether) d1 += 0.82D;		}		//if (entityplayer.isSneaking()) d1 -= 0.125D;		//if (PFLM_ConfigData.isModelSize) d1 += 0.45D;		if (Modchu_AS.getBoolean(Modchu_AS.entityIsRiding, entity)) modelData.setCapsValue(modelData.caps_isRiding, true);		else modelData.setCapsValue(modelData.caps_isRiding, modelData.getCapsValueBoolean(modelData.caps_isSitting));		//Modchu_Debug.mDebug("entityplayer.isRiding()="+modelData.getCapsValueBoolean(modelData.caps_isRiding));		if (modelData.getCapsValueBoolean(modelData.caps_isRiding)) {			d1 += oldRender					&& isAether ? -0.1D : isAether ? 0.13D : oldRender ? 0.25D : 0.30D;			if (PFLM_ConfigData.isModelSize					&& PFLM_ConfigData.changeMode != ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).skinMode_online) d1 -= 0.43D;		}		boolean isSneaking = Modchu_AS.getBoolean(Modchu_AS.entityIsSneaking, entity);		if (isSneaking) {			if (modelData.getCapsValueBoolean(modelData.caps_isRiding)) {				d1 -= 0.1D;			}		}		if (modelData.getCapsValueBoolean(modelData.caps_isSitting)) {			d1 += (oldRender && isAether ? 0.1D : oldRender ? -0.1D : isAether ? 0.08D : -0.2D) 					+ Modchu_EntityCapsHelper.getCapsValueDouble(modelData.modelMain.model, modelData, modelData.caps_sittingYOffset);			if (isSizeMultiplier) {				double d = -0.25D;				float size = (Float) Modchu_Reflect.invoke(ModchuModel_Main.sizeMultiplier, entity);				d1 -= d - (d * size);				if (isSneaking) {					double d2 = -0.1D;					d1 -= d2 - (d2 * size);				}			}		}		//Modchu_Debug.dDebug("doRenderSettingY return d1="+d1);		return d1;	}	private void doRenderSetting(Object entity, PFLM_ModelData modelData) {		//modelData.modelMain.setEntityCaps(modelData);		float[] f9 = new float[2];		float f10 = Modchu_AS.getFloat(Modchu_AS.entityLivingBaseGetSwingProgress, entity, 1.0F);		switch (modelData.getCapsValueInt(modelData.caps_dominantArm)) {		case 0:			f9[0] = f10;			f9[1] = 0.0F;			break;		case 1:			f9[0] = 0.0F;			f9[1] = f10;			break;		}		modelData.modelMain.setCapsValue(modelData, modelData.caps_onGround, f9[0], f9[1]);		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {			if (PFLM_ConfigData.mushroomConfusion) PFLM_Main.mushroomConfusion(entity, modelData);		}		if (modelData.getCapsValueBoolean(modelData.caps_changeModelFlag)) {			if (Modchu_Main.isOlddays) modelData.setCapsValue(modelData.caps_oldwalking, (Boolean) Modchu_Reflect.getFieldObject("ModelBiped", "oldwalking", modelData.modelMain.model));			modelData.setCapsValue(modelData.caps_partsSetInit, false);			modelData.setCapsValue(modelData.caps_partsSetFlag, 1);			modelData.modelMain.setCapsValue(modelData, modelData.caps_changeModel, modelData);			PFLM_Main.changeModel(entity);			modelData.setCapsValue(modelData.caps_changeModelFlag, false);		}		((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).setHandedness(entity, modelData.getCapsValueInt(modelData.caps_dominantArm));		//Modchu_Debug.mDebug("PFLM_RenderPlayerMaster doRenderSetting entityIsSneaking="+(Modchu_AS.getBoolean(Modchu_AS.entityIsSneaking, entity)));		modelData.modelMain.setCapsValue(modelData, modelData.caps_isSneak, Modchu_AS.getBoolean(Modchu_AS.entityIsSneaking, entity));		modelData.modelMain.setCapsValue(modelData, modelData.caps_isRiding, Modchu_AS.getBoolean(Modchu_AS.entityIsRiding, entity));		modelData.setCapsValue(modelData.caps_isPlayer, Modchu_AS.getString(Modchu_AS.userName, entity) == Modchu_AS.getString(Modchu_AS.userName, Modchu_AS.get(Modchu_AS.minecraftThePlayer)));	}	@Override	public void renderEquippedItems(Object entity, float f) {		super.renderEquippedItems(entity, f);		renderSpecials(entity, f);	}	@Override	public void renderSpecials(Object entity, float f) {		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);		if (modelData != null); else return;		//modelData.modelMain.setEntityCaps(modelData);		Modchu_AS.set(Modchu_AS.setRender, modelData.modelMain.model, base);		if (!ModchuModel_ConfigData.useInvisibilityItem				| (ModchuModel_ConfigData.useInvisibilityItem				&& !Modchu_AS.getBoolean(Modchu_AS.entityIsInvisible, entity))) Modchu_AS.set(Modchu_AS.renderItems, modelData.modelMain, entity, base);		if (Modchu_AS.getString(Modchu_AS.userName, entity).equals("deadmau5")) {			int version = Modchu_Main.getMinecraftVersion();			Class EntityLivingBase = Modchu_Reflect.loadClass("EntityLivingBase");			Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");			if (version > 159					&& Modchu_AS.getBoolean(Modchu_AS.threadDownloadImageDataIsTextureUploaded, Modchu_AS.get(Modchu_AS.abstractClientPlayerGetTextureSkin, entity))					//&& ((AbstractClientPlayer) entityplayer).func_110309_l().func_110557_a())					| (version < 160							&& (Boolean) Modchu_Reflect.invokeMethod("Render", "func_76984_a", "loadDownloadableImageTexture", new Class[]{ String.class, String.class }, base, new Object[]{ (String) Modchu_Reflect.getFieldObject("Entity", "field_20047_bv", "skinUrl", entity), (String) null }))) {				if (version > 159) Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110306_p", "getLocationSkin", base);				// (((AbstractClientPlayer) entityplayer).func_110306_p());				float prevRenderYawOffset = Modchu_AS.getFloat(Modchu_AS.entityLivingBasePrevRenderYawOffset, entity);				float renderYawOffset = Modchu_AS.getFloat(Modchu_AS.entityLivingBaseRenderYawOffset, entity);				float prevRotationYaw = Modchu_AS.getFloat(Modchu_AS.entityPrevRotationYaw, entity);				float rotationYaw = Modchu_AS.getFloat(Modchu_AS.entityRotationYaw, entity);				float prevRotationPitch = Modchu_AS.getFloat(Modchu_AS.entityPrevRotationPitch, entity);				float rotationPitch = Modchu_AS.getFloat(Modchu_AS.entityRotationPitch, entity);				for (int i = 0; i < 2; i++) {					float f2 = (prevRotationYaw + (rotationYaw - prevRotationYaw) * f) - (prevRenderYawOffset + (renderYawOffset - prevRenderYawOffset) * f);					float f3 = prevRotationPitch + (rotationPitch - prevRotationPitch) * f;					GL11.glPushMatrix();					GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);					GL11.glRotatef(f3, 1.0F, 0.0F, 0.0F);					GL11.glTranslatef(0.375F * (float) (i * 2 - 1), 0.0F, 0.0F);					GL11.glTranslatef(0.0F, -0.375F, 0.0F);					GL11.glRotatef(-f3, 1.0F, 0.0F, 0.0F);					GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);					float f8 = 1.333333F;					GL11.glScalef(f8, f8, f8);					((MultiModelBaseBiped) modelData.modelMain.model).renderEars(0.0625F);					GL11.glPopMatrix();				}			}			boolean renderCloakFlag = false;						if (getRenderManager() != null) {				if (version > 159) {					if (Modchu_AS.getBoolean(Modchu_AS.threadDownloadImageDataIsTextureUploaded, Modchu_AS.get(Modchu_AS.abstractClientPlayerGetTextureCape, entity))) {						renderCloakFlag = true;					}				}				else if (version < 160						&& version > 129) {					String cloakUrl = Modchu_AS.getString(Modchu_AS.entityCloakUrl, entity);					if (cloakUrl != null						&& Modchu_AS.getBoolean(Modchu_AS.renderLoadDownloadableImageTexture, base, cloakUrl, (String) null)) {						renderCloakFlag = true;					}				} else {					renderCloakFlag = true;				}				if (version > 129) {					if (Modchu_AS.getBoolean(Modchu_AS.entityIsInvisible, entity)						| Modchu_AS.getBoolean(Modchu_AS.entityPlayerGetHideCape, entity)) {						renderCloakFlag = false;					}				}			}			if (renderCloakFlag) {				GL11.glPushMatrix();				GL11.glTranslatef(0.0F, 0.0F, 0.125F);				double field_71091_bM = Modchu_AS.getDouble(Modchu_AS.entityPlayerField_71091_bM, entity);				double field_71096_bN = Modchu_AS.getDouble(Modchu_AS.entityPlayerField_71096_bN, entity);				double field_71097_bO = Modchu_AS.getDouble(Modchu_AS.entityPlayerField_71097_bO, entity);				double field_71094_bP = Modchu_AS.getDouble(Modchu_AS.entityPlayerField_71094_bP, entity);				double field_71095_bQ = Modchu_AS.getDouble(Modchu_AS.entityPlayerField_71095_bQ, entity);				double field_71085_bR = Modchu_AS.getDouble(Modchu_AS.entityPlayerField_71085_bR, entity);				double prevPosX = Modchu_AS.getDouble(Modchu_AS.entityPrevPosX, entity);				double prevPosY = Modchu_AS.getDouble(Modchu_AS.entityPrevPosY, entity);				double prevPosZ = Modchu_AS.getDouble(Modchu_AS.entityPrevPosZ, entity);				double posX = Modchu_AS.getDouble(Modchu_AS.entityPosX, entity);				double posY = Modchu_AS.getDouble(Modchu_AS.entityPosY, entity);				double posZ = Modchu_AS.getDouble(Modchu_AS.entityPosZ, entity);				double d = (field_71091_bM + (field_71094_bP - field_71091_bM) * (double) f) - (prevPosX + (posX - prevPosX) * (double) f);				double d1 = (field_71096_bN + (field_71095_bQ - field_71096_bN) * (double) f) - (prevPosY + (posY - prevPosY) * (double) f);				double d2 = (field_71097_bO + (field_71085_bR - field_71097_bO) * (double) f) - (prevPosZ + (posZ - prevPosZ) * (double) f);				float prevRenderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70760_ar", "prevRenderYawOffset", entity);				float renderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70761_aq", "renderYawOffset", entity);				float f11 = prevRenderYawOffset + (renderYawOffset - prevRenderYawOffset) * f;				double d3 = Modchu_AS.getFloat(Modchu_AS.mathHelperSin, (f11 * 3.141593F) / 180F);				double d4 = -Modchu_AS.getFloat(Modchu_AS.mathHelperCos, (f11 * 3.141593F) / 180F);				float f13 = (float) d1 * 10F;				if (f13 < -6F) {					f13 = -6F;				}				if (f13 > 32F) {					f13 = 32F;				}				float f14 = (float) (d * d3 + d2 * d4) * 100F;				float f15 = (float) (d * d4 - d2 * d3) * 100F;				if (f14 < 0.0F) {					f14 = 0.0F;				}				float prevCameraYaw = Modchu_AS.getFloat(Modchu_AS.entityPlayerPrevCameraYaw, entity);				float cameraYaw = Modchu_AS.getFloat(Modchu_AS.entityPlayerCameraYaw, entity);				float f16 = prevCameraYaw + (cameraYaw - prevCameraYaw) * f;				float prevDistanceWalkedModified = Modchu_AS.getFloat(Modchu_AS.entityPrevDistanceWalkedModified, entity);				float distanceWalkedModified = Modchu_AS.getFloat(Modchu_AS.entityDistanceWalkedModified, entity);				f13 += Modchu_AS.getFloat(Modchu_AS.mathHelperSin, (prevDistanceWalkedModified + (distanceWalkedModified - prevDistanceWalkedModified) * f) * 6F) * 32F * f16;				if (Modchu_AS.getBoolean(Modchu_AS.entityIsSneaking, entity)) {					f13 += 25F;				}				GL11.glRotatef(6F + f14 / 2.0F + f13, 1.0F, 0.0F, 0.0F);				GL11.glRotatef(f15 / 2.0F, 0.0F, 0.0F, 1.0F);				GL11.glRotatef(-f15 / 2.0F, 0.0F, 1.0F, 0.0F);				GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);				((MultiModelBaseBiped) modelData.modelMain.model).renderCloak(0.0625F);				GL11.glPopMatrix();			}		}		//Modchu_AS.set(Modchu_AS.renderHelperDisableStandardItemLighting);/*		RenderHelper.disableStandardItemLighting();		GL11.glDisable(GL12.GL_RESCALE_NORMAL);		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);		GL11.glDisable(GL11.GL_TEXTURE_2D);		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);*/	}	public void renderFirstPersonArm(Object entityplayer) {		renderFirstPersonArm(entityplayer, 2);	}	@Override	public void renderFirstPersonArm(Object entityplayer, int i) {		renderFirstPersonArm(entityplayer, i, 0);	}	public void renderFirstPersonArm(Object entityplayer, int i, int i1) {		//olddays導入時に2以外のint付きで呼ばれる。		ModchuModel_ModelDataBase data = PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);		PFLM_ModelData modelData = data != null				&& data instanceof PFLM_ModelData ? (PFLM_ModelData) data : null;		if (modelData != null); else return;		if (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {			modelData.setCapsValue(modelData.caps_firstPerson, false);			return;		}		doRenderSetting(entityplayer, modelData);		modelData.setCapsValue(modelData.caps_firstPerson, true);		if (i >= 2) {			if (modelData.modelMain != null					&& modelData.modelMain.model != null) {				((MultiModelBaseBiped) modelData.modelMain.model).setRotationAnglesfirstPerson(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, modelData);/*				if (modelData.modelFATT != null) {					if (modelData.modelFATT.modelInner != null) ((MultiModelBaseBiped) modelData.modelFATT.modelInner).setRotationAnglesfirstPerson(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, modelData);					if (modelData.modelFATT.modelOuter != null) ((MultiModelBaseBiped) modelData.modelFATT.modelOuter).setRotationAnglesfirstPerson(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, modelData);				}*/			}		}		if (modelData.modelMain.model != null				&& modelData.getCapsValue(modelData.caps_ResourceLocation) != null) {			bindTexture(modelData, modelData.getCapsValue(modelData.caps_ResourceLocation));			//Modchu_Debug.mDebug("PFLM_RenderPlayerMaster renderFirstPersonArm ResourceLocation="+(modelData.getCapsValue(modelData.caps_ResourceLocation)));			//Modchu_Debug.mDebug("PFLM_RenderPlayerMaster renderFirstPersonArm modelData.modelMain.model != null ? ="+(modelData.modelMain.model != null));			modelData.modelMain.model.renderFirstPersonHand(modelData, i1);			//renderFirstPersonArmorRender(modelData, entityplayer, 0.0D, 0.0D, 0.0D, 0.0F, 0.0625F);		} else if (modelData.modelMain.model != null				&& PFLM_Main.bipedCheck(modelData.modelMain.model)) {			modelData.modelMain.model.renderFirstPersonHand(modelData, i1);		}		modelData.setCapsValue(modelData.caps_firstPerson, false);	}	@Override	public void renderFirstPersonLeftArm(Object entityplayer, int i) {		renderFirstPersonArm(entityplayer, i, 1);	}	@Override	public boolean isActivatedForPlayer(Object entityplayer) {		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityplayer);		if (modelData != null); else return false;		return modelData.getCapsValueBoolean(modelData.caps_isActivated);	}	public Object[] modchu_RenderPlayerRenderLivingLabel(Object[] o) {		Object entity = o[0];		String s = (String) o[1];		double d = (Double) o[2];		double d1 = (Double) o[3];		double d2 = (Double) o[4];		int i = (Integer) o[5];		//-@-125		if (PFLM_Main.isSmartMoving) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderName", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), double.class, double.class, double.class }, pflm_RenderPlayerSmart, new Object[]{ entity, d, d1, d2 });		//@-@125		if (PFLM_ConfigData.isRenderName) {			PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);			if (modelData == null) return null;			double d3 = 0.0D;			double d4 = 0.0D;			double d5 = 0.0D;			double height = (double) modelData.modelMain.model.getHeight(modelData);			if (Modchu_AS.getBoolean(Modchu_AS.entityIsSneaking, entity)) d3 = 0.4D;			float f1 = modelData.getCapsValueFloat(modelData.caps_modelScale);			if (f1 > 0.0F) {				d5 = (double) (0.9375F - f1);				d4 = -height * d5;				if (f1 > 0.9375F) d4 -= 0.4D * d5;			}			o[3] = (d1 - 1.8D) + height + d3 + d4;			return Modchu_Main.addObjectArray(o, true, false);		}		return null;	}	@Override	public void renderLivingLabel(Object entity, String par2Str, double d, double d1, double d2, int i) {	}	@Override	public void renderModel(Object entity, float par2, float par3, float par4, float par5, float par6, float par7) {		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);		int version = Modchu_Main.getMinecraftVersion();/*		int tempTextureStateTextureName = 0;		if (version > 179) {			int activeTextureUnit = Modchu_CastHelper.Int(Modchu_Reflect.getFieldObject("net.minecraft.client.renderer.GlStateManager", "field_179162_o", "activeTextureUnit"));			Object[] textureState = Modchu_CastHelper.ObjectArray(Modchu_Reflect.getFieldObject("net.minecraft.client.renderer.GlStateManager", "field_179174_p", "textureState"));			if (textureState != null					&& activeTextureUnit < textureState.length					&& textureState[activeTextureUnit] != null) {				tempTextureStateTextureName = (Integer) Modchu_Reflect.getFieldObject(textureState[activeTextureUnit].getClass(), "field_179059_b", "textureName", textureState[activeTextureUnit]);				Modchu_GlStateManager.bindTexture(tempTextureStateTextureName + 1);			}		}*/		if (!ModchuModel_ConfigData.useInvisibilityBody				| (ModchuModel_ConfigData.useInvisibilityBody						&& !Modchu_AS.getBoolean(Modchu_AS.entityIsInvisible, entity))) {			int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);			Object resourceLocation = skinMode == ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).skinMode_Player ?					PFLM_ModelDataMaster.instance.getPlayerData(Modchu_AS.get(Modchu_AS.minecraftThePlayer)).getCapsValue(modelData.caps_ResourceLocation, 0) :						modelData.getCapsValue(modelData.caps_ResourceLocation, 0);			if (version < 160) {				String skinUrl = ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).getSkinUrl(modelData, entity);				String texture = (String) (skinMode == ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).skinMode_PlayerOnline						| skinMode == ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).skinMode_online ? null : resourceLocation);				boolean b = Modchu_AS.getBoolean(Modchu_AS.renderLoadDownloadableImageTexture, base, skinUrl, texture);				//Modchu_Debug.mlDebug("renderModel skinUrl="+skinUrl+" texture="+texture+" b="+b);				if (!b						&& resourceLocation != null) bindTexture(modelData, resourceLocation);			} else {				//Modchu_Debug.mDebug1("PFLM_RenderPlayerMaster renderModel resourceLocation="+resourceLocation);				if (resourceLocation != null) bindTexture(modelData, resourceLocation);				else {					Modchu_Debug.lDebug1("renderModel resourceLocation == null !!");				}			}			modelData.modelMain.setArmorRendering(true);		} else {			modelData.modelMain.setArmorRendering(false);		}		modelData.modelMain.model.render(modelData, par2, par3, par4, par5, par6, par7, (Boolean) Modchu_Reflect.getFieldObject(ModchuModel_ModelBaseNihil.class, "isRendering", modelData.modelMain));/*		if (Modchu_Main.getMinecraftVersion() > 179) {			Modchu_GlStateManager.bindTexture(tempTextureStateTextureName);		}*/		//Modchu_Debug.mDebug("PFLM_RenderPlayerMaster renderModel modelData.modelMain.isRendering="+modelData.modelMain.isRendering);	}	@Override	public void renderLivingAt(Object entity, double par2, double par4, double par6) {		renderPlayerSleep(entity, par2, par4, par6);		super.renderLivingAt(entity, par2, par4, par6);	}	@Override	public void renderPlayerSleep(Object entity, double var2, double var4, double var6) {		if (PFLM_Main.isSmartMoving) renderPlayerAt(entity, var2, var4, var6);	}	@Override	public void rotateCorpse(Object entity, float f, float f1, float f2) {		rotatePlayer(entity, f, f1, f2);		super.rotateCorpse(entity, f, f1, f2);	}	@Override	public void rotatePlayer(Object entity, float var2, float var3, float var4) {		if (PFLM_Main.oldRender) return;		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);		if (PFLM_Main.isSmartMoving				&& modelData != null) {//-@-125			Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "rotatePlayer", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), float.class, float.class, float.class }, pflm_RenderPlayerSmart, new Object[]{ entity, var2, var3, var4 });//@-@125/*//125delete			Modchu_Reflect.invokeMethod(PFLM_RenderPlayerSmart, "rotatePlayer", new Class[]{ EntityPlayer.class, float.class, float.class, float.class, PFLM_ModelData.class }, pflm_RenderPlayerSmart, new Object[]{ var1, var2, var3, var4, modelData });*///125delete			//Modchu_Reflect.invokeMethod(PFLM_RenderRenderSmart, "rotatePlayer", new Class[]{ EntityPlayer.class, float.class, float.class, float.class }, pflm_RenderRenderSmart, new Object[]{ var1, var2, var3, var4 });		}		if (modelData.getCapsValueBoolean(modelData.caps_isSleeping)) {			switch (modelData.getCapsValueInt(modelData.caps_rotate)) {			case 0:				GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);				break;			case 1:				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);				break;			case 2:				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);				break;			case 3:				GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);				break;			}			GL11.glRotatef(getDeathMaxRotation(entity), 0.0F, 0.0F, 1.0F);			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);		}		if (Modchu_Main.getMinecraftVersion() < 180				&& !modelData.getCapsValueBoolean(modelData.caps_isSleeping)) ((Modchu_IRenderPlayer) base).superRotatePlayer(entity, var2, var3, var4);	}	public float getDeathMaxRotation(Object entity) {		return 90.0F;	}	@Override	public int getColorMultiplier(Object entity, float par2, float par3) {		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);		setRenderCount(modelData.modelMain, 16);		return super.getColorMultiplier(entity, par2, par3);	}	@Override	public int inheritRenderPass(Object entity, int par2, float par3) {		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);		setRenderCount(modelData.modelFATT, 16);		return super.inheritRenderPass(entity, par2, par3);	}	@Override	public void superRenderLivingAt(Object entity, double par2, double par4, double par6) {		((Modchu_IRenderPlayer) base).superRenderLivingAt(entity, par2, par4, par6);	}	@Override	public void superRotateCorpse(Object entity, float f, float f1, float f2) {		((Modchu_IRenderPlayer) base).superRotateCorpse(entity, f, f1, f2);	}	@Override	public Object getMainModel() {		return dummyMainModel;	}//smartMoving関連↓	@Override	public void renderPlayerAt(Object var1, double var2, double var4, double var6) {		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(var1);		if (PFLM_Main.isSmartMoving				&& modelData != null) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderPlayerAt", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), double.class, double.class, double.class }, pflm_RenderPlayerSmart, new Object[]{ var1, var2, var4, var6 });	}	@Override	public void renderGuiIngame(Object var0) {		if (PFLM_Main.isSmartMoving) Modchu_Reflect.invokeMethod("PFLM_RenderPlayerSmart", "renderGuiIngame", new Class[]{ Modchu_Reflect.loadClass("Minecraft") }, pflm_RenderPlayerSmart, new Object[]{ var0 });	}//smartMoving関連↑	@Override	public Object func_180594_a(Object abstractClientPlayer) {		return ((Modchu_IRenderPlayer) base).superFunc_180594_a(abstractClientPlayer);	}	@Override	public void func_82422_c() {		((Modchu_IRenderPlayer) base).superFunc_82422_c();	}	@Override	public void renderOffsetLivingLabel(Object abstractClientPlayer, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_) {		((Modchu_IRenderPlayer) base).superRenderOffsetLivingLabel(abstractClientPlayer, p_96449_2_, p_96449_4_, p_96449_6_, p_96449_8_, p_96449_9_, p_96449_10_);	}	@Override	public void func_177139_c(Object abstractClientPlayer) {		((Modchu_IRenderPlayer) base).superFunc_177139_c(abstractClientPlayer);	}	@Override	public void func_177069_a(Object entity, double p_177069_2_, double p_177069_4_, double p_177069_6_, String p_177069_8_, float p_177069_9_, double p_177069_10_) {		((Modchu_IRenderPlayer) base).superFunc_177069_a(entity, p_177069_2_, p_177069_4_, p_177069_6_, p_177069_8_, p_177069_9_, p_177069_10_);	}}