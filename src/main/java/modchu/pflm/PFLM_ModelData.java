package modchu.pflm;import modchu.lib.Modchu_Debug;import modchu.lib.Modchu_Main;import modchu.lib.Modchu_EntityCapsHelper;import modchu.lib.Modchu_Reflect;import modchu.lib.characteristic.Modchu_AS;import modchu.lib.characteristic.Modchu_CastHelper;import modchu.lib.characteristic.Modchu_EntityCapsBase;import modchu.lib.characteristic.Modchu_IEntityCapsBase;import modchu.lib.characteristic.Modchu_ModelBaseDuo;import modchu.lib.characteristic.Modchu_ModelBaseDuoBase;import modchu.lib.characteristic.Modchu_ModelBaseNihilBase;import modchu.lib.characteristic.Modchu_ModelBaseSoloBase;import modchu.lib.characteristic.Modchu_ModelRenderer;import modchu.model.ModchuModel_ConfigData;import modchu.model.ModchuModel_Main;import modchu.model.ModchuModel_ModelDataBase;import modchu.model.multimodel.base.MultiModelBaseBiped;public class PFLM_ModelData extends ModchuModel_ModelDataBase {	public Modchu_ModelBaseSoloBase modelMain;	public Modchu_ModelBaseDuo modelFATT;	private boolean localFlag = false;	private boolean isWaitFSetFlag = false;	private boolean isActivated = false;	private boolean isPlayer = false;	private boolean isWait = false;	private boolean isInventory = false;	private boolean changeModelFlag = true;	private boolean resetHandedness = true;	private boolean mushroomConfusionLeft = false;	private boolean mushroomConfusionRight = false;	private boolean mushroomConfusionFront = false;	private boolean mushroomConfusionBack = false;	private boolean mushroomBack = false;	private boolean mushroomForward = false;	private boolean mushroomKeyBindResetFlag = false;	private boolean mushroomKeyBindSetFlag = false;	private boolean mushroomLeft = false;	private boolean mushroomRight = false;	private boolean actionNumberTransmission = false;	private boolean resetActionNumberInitFlag = false;	private boolean resetActionNumberFlag = false;	private boolean resetAllActionNumberFlag = false;	private float modelScale = 0.0F;	private float tempLimbSwing = 0.0F;	private float isWaitF = 0.0F;	private int isWaitTime = 0;	private int handedness = 0;	private int partsSetFlag = 1;	private int mushroomConfusionType = 0;	private int mushroomConfusionCount = 0;	private int rotate = 0;	private final int mushroomConfusionTypeMax = 4;	private Object[] resourceLocations;/*//b173delete	private boolean keyBindForwardPressed;	private boolean keyBindBackPressed;	private boolean keyBindLeftPressed;	private boolean keyBindRightPressed;	private KeyBinding keyBindForward;	private KeyBinding keyBindBack;	private KeyBinding keyBindLeft;	private KeyBinding keyBindRight;	private boolean mushroomConfusionFlag = false;*///b173delete	public PFLM_ModelData() {		super();		modelMain = new Modchu_ModelBaseSoloBase();		modelMain.isModelAlphablend = ModchuModel_ConfigData.AlphaBlend;		Modchu_Reflect.setFieldObject(Modchu_ModelBaseSoloBase.class, "textures", modelMain, Modchu_Reflect.newInstanceArray("ResourceLocation", 3));		modelFATT = new Modchu_ModelBaseDuo(null);		modelFATT.isModelAlphablend = ModchuModel_ConfigData.AlphaBlend;		Modchu_Reflect.setFieldObject(Modchu_ModelBaseDuo.class, "textureInner", modelFATT, Modchu_Reflect.newInstanceArray("ResourceLocation", 4));		Modchu_Reflect.setFieldObject(Modchu_ModelBaseDuo.class, "textureOuter", modelFATT, Modchu_Reflect.newInstanceArray("ResourceLocation", 4));		Modchu_Reflect.setFieldObject(Modchu_ModelBaseDuoBase.class, "textureInner", modelFATT, Modchu_Reflect.newInstanceArray("ResourceLocation", 4));		Modchu_Reflect.setFieldObject(Modchu_ModelBaseDuoBase.class, "textureOuter", modelFATT, Modchu_Reflect.newInstanceArray("ResourceLocation", 4));		Modchu_Reflect.setFieldObject(modelMain.getClass(), "capsLink", modelMain, modelFATT);		if (Modchu_Main.getMinecraftVersion() > 159				&& Modchu_Main.mmmLibVersion > 499) {			int i2 = 15728784;			boolean b = false;			b = Modchu_Reflect.setFieldObject(Modchu_ModelBaseNihilBase.class, "lighting", modelMain, i2, 1);			b = Modchu_Reflect.setFieldObject(Modchu_ModelBaseNihilBase.class, "lighting", modelFATT, i2, 1);		}	}	@Override	public Object getCapsValue(Modchu_IEntityCapsBase model, int pIndex, Object... pArg) {		switch (pIndex) {		case caps_health:			return getHealth();		case caps_isRiding:			return getIsRiding();		case caps_isSneak:			return getIsSneaking();		case caps_ResourceLocation:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) return getResourceLocation((Integer) pArg[0]);			else return getResourceLocation();		case caps_heldItems:		case caps_currentEquippedItem:			return getCurrentEquippedItem();		case caps_dominantArm:			return getHandedness();		case caps_isLookSuger:			return getIsLookSuger();		case caps_entityIdFactor:			return getEntityIdFactor();		case caps_isOpenInv:			return getIsInventory();		case caps_Inventory:			return getInventory();		case caps_localFlag:			return getLocalFlag();		case caps_rotate:			return getRotate();		case caps_texture:			if (pArg != null			&& pArg.length > 1			&& pArg[0] != null			&& pArg[1] != null) return getTexture((String) pArg[0], (Integer) pArg[1]);			break;		case caps_armorTexture:			if (pArg != null			&& pArg.length > 1			&& pArg[0] != null			&& pArg[1] != null) {				if (pArg.length > 2						&& pArg[2] != null) return getArmorTexture((String) pArg[0], (Integer) pArg[1], pArg[2]);				return getArmorTexture((String) pArg[0], (Integer) pArg[1]);			}			break;		case caps_isPlayer:			return getIsPlayer();		case caps_isWait:			return getIsWait();		case caps_isCamouflage:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				return Modchu_AS.getBoolean(Modchu_AS.isCamouflage, pArg[0]);			}			return null;		case caps_isPlanter:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				return Modchu_AS.getBoolean(Modchu_AS.isPlanter, pArg[0]);			}			return null;		case caps_HeadMount:			return getHeadMount();		case caps_Items:			return getItems();		case caps_Actions:			return getActions();		case caps_tempLimbSwing:			return getTempLimbSwing();		case caps_changeModelFlag:			return getChangeModelFlag();		case caps_partsSetFlag:			return getPartsSetFlag();		case caps_isActivated:			return getIsActivated();		case caps_modelScale:			return getModelScale();		case caps_isWaitF:			return getIsWaitF();		case caps_isWaitFSetFlag:			return getIsWaitFSetFlag();		case caps_isWaitTime:			return getIsWaitTime();		case caps_mushroomConfusionCount:			return getMushroomConfusionCount();		case caps_mushroomConfusionType:			return getMushroomConfusionType();		case caps_mushroomConfusionTypeMax:			return getMushroomConfusionTypeMax();		case caps_mushroomConfusionLeft:			return getMushroomConfusionLeft();		case caps_mushroomConfusionRight:			return getMushroomConfusionRight();		case caps_mushroomConfusionFront:			return getMushroomConfusionFront();		case caps_mushroomConfusionBack:			return getMushroomConfusionBack();		case caps_mushroomLeft:			return getMushroomLeft();		case caps_mushroomRight:			return getMushroomRight();		case caps_currentArmor:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) return getArmorItemInSlot((Integer) pArg[0]);			break;		case caps_isRendering:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) return getArmorRendering((Integer) pArg[0]);			return getArmorRendering();		case caps_modelRendererName:			if (pArg != null			&& pArg.length > 1			&& pArg[0] != null			&& pArg[1] != null) return getModelRendererName((Modchu_ModelRenderer) pArg[0], (Integer) pArg[1]);			break;		}		Object o = null;		if (model != null) {			o = model.getCapsValue(this, pIndex, pArg);		}		if (o != null) return o;		return super.getCapsValue(model, pIndex, pArg);	}	@Override	public boolean setCapsValue(Modchu_IEntityCapsBase model, int pIndex, Object... pArg) {		switch (pIndex) {		case caps_Entity:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setEntity(pArg[0]);				return true;			}			return false;		case caps_isRiding:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsRiding((Boolean) pArg[0]);				return true;			}			return false;		case caps_isSneak:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsSneaking((Boolean) pArg[0]);				return true;			}			return false;		case caps_ResourceLocation:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				if (pArg.length > 1) {					setResourceLocation((Integer) pArg[0], pArg[1]);				} else setResourceLocation((Object[]) pArg[0]);				return true;			}			return false;		case caps_dominantArm:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setHandedness((Integer) pArg[0]);				return true;			}			return false;		case caps_isOpenInv:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsInventory((Boolean) pArg[0]);				return true;			}			return false;		case caps_isRendering:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				if (pArg.length > 1) {					setIsRendering((Integer) pArg[0], (Boolean) pArg[1]);				} else setIsRendering((Boolean) pArg[0]);				return true;			}			return false;		case caps_localFlag:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setLocalFlag((Boolean) pArg[0]);				return true;			}			return false;		case caps_isPlayer:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsPlayer((Boolean) pArg[0]);				return true;			}			return false;		case caps_partsSetFlag:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setPartsSetFlag((Integer) pArg[0]);				return true;			}			return false;		case caps_isWaitTime:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsWaitTime((Integer) pArg[0]);				return true;			}			return false;		case caps_rotate:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setRotate((Integer) pArg[0]);				return true;			}			return false;		case caps_isWait:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsWait((Boolean) pArg[0]);				return true;			}			return false;		case caps_isActivated:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsActivated((Boolean) pArg[0]);				return true;			}			return false;		case caps_isWaitFSetFlag:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsWaitFSetFlag((Boolean) pArg[0]);				return true;			}			return false;		case caps_modelScale:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setModelScale((Float) pArg[0]);				return true;			}			return false;		case caps_isWaitF:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setIsWaitF((Float) pArg[0]);				return true;			}			return false;		case caps_tempLimbSwing:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setTempLimbSwing((Float) pArg[0]);				return true;			}			return false;		case caps_changeModelFlag:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setChangeModelFlag((Boolean) pArg[0]);				return true;			}			return false;/*		case caps_partsSetInit:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setPartsSetInit((Boolean) pArg[0]);				return true;			}*/		case caps_mushroomConfusionCount:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomConfusionCount((Integer) pArg[0]);				return true;			}			return false;		case caps_mushroomConfusionType:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomConfusionType((Integer) pArg[0]);				return true;			}			return false;		case caps_mushroomConfusionLeft:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomConfusionLeft((Boolean) pArg[0]);				return true;			}			return false;		case caps_mushroomConfusionRight:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomConfusionRight((Boolean) pArg[0]);				return true;			}			return false;		case caps_mushroomConfusionFront:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomConfusionFront((Boolean) pArg[0]);				return true;			}			return false;		case caps_mushroomConfusionBack:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomConfusionBack((Boolean) pArg[0]);				return true;			}			return false;		case caps_mushroomBack:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomBack((Boolean) pArg[0]);				return true;			}			return false;		case caps_mushroomLeft:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomLeft((Boolean) pArg[0]);				return true;			}			return false;		case caps_mushroomRight:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				setMushroomRight((Boolean) pArg[0]);				return true;			}			return false;		case caps_changeColor:			if (pArg != null			&& pArg.length > 0			&& pArg[0] != null) {				//Modchu_Debug.mDebug("caps_changeColor modelFATT.modelInner instanceof  MultiModelBaseBiped ? "+(modelFATT.modelInner instanceof  MultiModelBaseBiped));				if (modelMain.model instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelMain.model).changeColor(this);				if (modelFATT.modelInner instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelFATT.modelInner).changeColor(this);				if (modelFATT.modelOuter instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) modelFATT.modelOuter).changeColor(this);				return true;			}			return false;		case caps_setLivingAnimationsBefore:			if (pArg != null			&& pArg.length > 3			&& pArg[0] != null			&& pArg[1] != null			&& pArg[2] != null			&& pArg[3] != null) {				setLivingAnimationsBefore(pArg[0], (Float) pArg[1], (Float) pArg[2], (Float) pArg[3]);				return true;			}			return false;		case caps_setLivingAnimationsAfter:			if (pArg != null			&& pArg.length > 3			&& pArg[0] != null			&& pArg[1] != null			&& pArg[2] != null			&& pArg[3] != null) {				setLivingAnimationsAfter(pArg[0], (Float) pArg[1], (Float) pArg[2], (Float) pArg[3]);				return true;			}			return false;		}		return super.setCapsValue(model, pIndex, pArg);	}	@Override	public boolean setCapsValue(int pIndex, Object... pArg) {		return setCapsValue((MultiModelBaseBiped) null, pIndex, pArg);	}	private void setEntity(Object entity) {		if (Modchu_Reflect.loadClass("Entity").isInstance(entity)) Modchu_Reflect.setFieldObject(Modchu_EntityCapsBase.class, "owner", this, entity);	}	private boolean getIsRiding() {		if (getIsSitting()) return true;		return Modchu_AS.getBoolean(Modchu_AS.entityIsRiding, getOwner());	}	private void setIsRiding(boolean b) {		Object owner = getOwner();		if (!isEntityPlayer(owner)) return;		modelMain.setCapsValue(caps_isRiding, b);/*		if (!getOwner().isRiding()) {			getOwner().ridingEntity = b ? getOwner() : null;		} else {			if (!b					&& getOwner().equals(getOwner().ridingEntity)) getOwner().ridingEntity = null;		}*/		//Modchu_Debug.mDebug("setIsRiding getOwner().ridingEntity="+getOwner().ridingEntity);	}	private Object getResourceLocation() {		return getResourceLocation(0);	}	private Object getResourceLocation(int i) {		return resourceLocations != null ? resourceLocations[i] : null;	}	private void setResourceLocation(Object[] resourceLocation) {		resourceLocations = resourceLocation;	}	private void setResourceLocation(int i, Object pArg) {		if (resourceLocations != null) ;		else {			resourceLocations = new Object[3];		}		resourceLocations[i] = pArg;		//Modchu_Debug.mDebug("setResourceLocation resourceLocations["+i+"]="+resourceLocations[i]);	}	/**	 * setLivingAnimationsLM 呼び出し前に呼ばれる。	 */	private void setLivingAnimationsBefore(Object model, float f, float f1, float f2) {	}	/**	 * setLivingAnimationsLM 呼び出し後に呼ばれる。	 */	private void setLivingAnimationsAfter(Object model, float f, float f1, float f2) {		if (model != null) ;		else return;		//setCapsValue(caps_isRiding, getIsSitting());		//model.setCapsValue(caps_isRiding, getIsSitting());		//Modchu_Debug.mDebug("PFLM_ModelData setLivingAnimationsAfter m getIsSitting()="+PFLM_Main.getIsSitting());		//Modchu_Debug.mDebug("PFLM_ModelData setLivingAnimationsAfter getIsSitting()="+getIsSitting());		//Modchu_Debug.mDebug("PFLM_ModelData setLivingAnimationsAfter caps_isRiding="+model.getCapsValue(caps_isRiding));		//Modchu_Debug.mDebug("PFLM_ModelData setLivingAnimationsAfter PFLM_Gui.partsSetFlag="+PFLM_GuiConstant.partsSetFlag);		if (getOwner() != null				&& model != null				&& model instanceof MultiModelBaseBiped) ;		else return;		MultiModelBaseBiped multiModelBaseBiped = (MultiModelBaseBiped) model;		if (PFLM_GuiConstant.partsSetFlag < 2) {			PFLM_GuiConstant.partsSetFlag = 2;			partsSetFlag = 1;		}		if (partsSetFlag == 1) {			PFLM_Config.loadShowModelList(ModchuModel_Main.showModelList);			multiModelBaseBiped.defaultPartsSettingBefore(this);			multiModelBaseBiped.defaultPartsSettingAfter(this);			multiModelBaseBiped.showModelSettingReflects(this);			int armorType = Modchu_EntityCapsHelper.getCapsValueInt(multiModelBaseBiped, this, caps_armorType);			showModelSettingReflects(armorType);			if (armorType == 0) partsSetFlag = 2;		}	}	@Override	protected Object[] getTextureDataModel() {		return new Object[]{ modelMain.model, modelFATT.modelInner, modelFATT.modelOuter };	}	@Override	protected Object getModel(int i) {		switch (i) {		case 0:			return modelMain.model;		case 1:			return modelFATT.modelInner;		case 2:			return modelFATT.modelOuter;		}		return null;	}	@Override	protected String getTextureName() {		return textureName;	}	@Override	protected void setTextureName(String s) {		textureName = s;	}	@Override	protected String getTextureArmorName() {		return modelArmorName;	}	@Override	protected void setTextureArmorName(String s) {		modelArmorName = s;	}	@Override	protected int getMaidColor() {		return maidColor;	}	@Override	protected void setMaidColor(int i) {		maidColor = i;	}	private boolean getChangeModelFlag() {		return changeModelFlag;	}	private void setChangeModelFlag(boolean b) {		changeModelFlag = b;	}	private boolean getIsWaitFSetFlag() {		return isWaitFSetFlag;	}	private void setIsWaitFSetFlag(boolean b) {		isWaitFSetFlag = b;	}	private boolean getLocalFlag() {		return localFlag;	}	private void setLocalFlag(boolean b) {		localFlag = b;	}	private Object getArmorItemInSlot(int i) {		Object owner = getOwner();		if (!isEntityPlayer(owner)) return null;		return Modchu_AS.get(Modchu_AS.entityPlayerInventoryPlayerArmorItemInSlot, getOwner(), i);	}	private Object getHeadMount() {		Object owner = getOwner();		if (!isEntityPlayer(owner)) return null;		return Modchu_AS.get(Modchu_AS.entityPlayerInventoryGetStackInSlot, getOwner(), 9);	}	private Object[] getActions() {		Object owner = getOwner();		if (!isEntityPlayer(owner)) return null;		Object[] lactions = Modchu_Reflect.newInstanceArray("EnumAction", 2);		Object itemstack = Modchu_AS.get(Modchu_AS.entityPlayerInventoryGetCurrentItem, getOwner());		lactions[getHandedness()] = itemstack != null				&& Modchu_AS.getInt(Modchu_AS.entityPlayerGetItemInUseCount, getOwner()) > 0 ? Modchu_AS.get(Modchu_AS.itemStackGetItemUseAction, itemstack) : null;		return lactions;	}	private Object[] getItems() {		//Modchu_Debug.mDebug("getItems()");		Object[] itemStack = Modchu_Reflect.newInstanceArray("ItemStack", 2);		Object owner = getOwner();		if (!isEntityPlayer(owner)) return itemStack;		itemStack[getHandedness()] = Modchu_AS.get(Modchu_AS.entityPlayerInventoryGetCurrentItem, getOwner());		//Modchu_Debug.mDebug("getItems "+itemStack[getHandedness()]);		return itemStack;	}	private Object getCurrentEquippedItem() {		Object owner = getOwner();		if (!isEntityPlayer(owner)) return null;		return Modchu_AS.get(Modchu_AS.entityPlayerInventoryGetCurrentItem, getOwner());	}	private int getHealth() {		Object owner = getOwner();		if (!isEntityPlayer(owner)) return -1;		float f = Modchu_AS.getFloat(Modchu_AS.entityLivingBaseGetHealth, getOwner());		return (int) f;	}	private boolean getIsWait() {		return isWait;	}	private void setIsWait(boolean b) {		isWait = b;	}	@Override	protected boolean getIsSitting() {		if (isEntityPlayer(getOwner())				&& PFLM_Main.isPacetMode()) {/*			if (!getIsPlayer()) {				Modchu_Debug.mdDebug("getIsSitting() !getIsPlayer() isSitting="+(getIsSleeping() ? false : Modchu_CastHelper.Boolean(PFLM_PacketPlayerStateManager.getPlayerStateObject(Modchu_Main.getMinecraftVersion() > 179 ? Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, getOwner()).toString() : Modchu_AS.getInt(Modchu_AS.entityEntityID, getOwner()), PFLM_Main.getPFLMFPacketConstant("packet_IDSitting")))));				Modchu_Debug.mdDebug("getIsSitting() !getIsPlayer() getPlayerStateObject="+(PFLM_PacketPlayerStateManager.getPlayerStateObject(Modchu_Main.getMinecraftVersion() > 179 ? Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, getOwner()).toString() : Modchu_AS.getInt(Modchu_AS.entityEntityID, getOwner()), PFLM_Main.getPFLMFPacketConstant("packet_IDSitting"))), 1);				Modchu_Debug.mdDebug("getIsSitting() !getIsPlayer() getOwner()="+getOwner(), 2);			}*/			return getIsSleeping() ? false : Modchu_CastHelper.Boolean(PFLM_PacketPlayerStateManager.getPlayerStateObject(Modchu_Main.getMinecraftVersion() > 179 ? Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, getOwner()).toString() : Modchu_AS.getInt(Modchu_AS.entityEntityID, getOwner()), PFLM_Main.getPFLMFPacketConstant("packet_IDSitting")));		}		return super.getIsSitting();	}	@Override	protected void setIsSitting(boolean b) {		//Modchu_Debug.mDebug("setIsSitting b="+b);		if (getIsSitting() != b) {			//Modchu_Debug.mDebug("setIsSitting isEntityPlayer(getOwner())="+isEntityPlayer(getOwner()));			//Modchu_Debug.mDebug("setIsSitting PFLM_Main.isPacetMode()="+PFLM_Main.isPacetMode());			//Modchu_Debug.mDebug("setIsSitting ModchuModel_Main.isPFLMF="+ModchuModel_Main.isPFLMF);			if (isEntityPlayer(getOwner()) 					&& PFLM_Main.isPacetMode()					&& ModchuModel_Main.isPFLMF) {				PFLM_PacketPlayerStateManager.addSendList(PFLM_Main.getPFLMFPacketConstant("packet_IDSitting"), this, getOwner(), b);			} else super.setIsSitting(b);		}	}	@Override	protected boolean getIsSleeping() {		if (isEntityPlayer(getOwner())				&& PFLM_Main.isPacetMode()) {			Object[] o = Modchu_CastHelper.ObjectArray(PFLM_PacketPlayerStateManager.getPlayerStateObject(Modchu_Main.getMinecraftVersion() > 179 ? Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, getOwner()).toString() : Modchu_AS.getInt(Modchu_AS.entityEntityID, getOwner()), PFLM_Main.getPFLMFPacketConstant("packet_IDSleeping")));			if (o != null) return Modchu_CastHelper.Boolean(o[0]);		}		return super.getIsSleeping();	}	@Override	protected void setIsSleeping(boolean b) {		if (getIsSleeping() != b) {			Object owner = getOwner();			if (isEntityPlayer(owner)					&& PFLM_Main.isPacetMode()) {				PFLM_PacketPlayerStateManager.addSendList(PFLM_Main.getPFLMFPacketConstant("packet_IDSleeping"), this, getOwner(), b, rotate);			} else {				super.setIsSleeping(b);			}			if (owner != null) ;			else return;			float f2;			for (f2 = Modchu_AS.getFloat(Modchu_AS.entityRotationYaw, owner); f2 < 0.0F; f2 += 360F) {			}			for (; f2 > 360F; f2 -= 360F) {			}			int i = (int) ((f2 + 45F) / 90F);			setRotate(i);		}	}	private int getRotate() {		if (isEntityPlayer(getOwner())				&& PFLM_Main.isPacetMode()) {			Object[] o = Modchu_CastHelper.ObjectArray(PFLM_PacketPlayerStateManager.getPlayerStateObject(Modchu_Main.getMinecraftVersion() > 179 ? Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, getOwner()).toString() : Modchu_AS.getInt(Modchu_AS.entityEntityID, getOwner()), PFLM_Main.getPFLMFPacketConstant("packet_IDSleeping")));			if (o != null) return Modchu_CastHelper.Int(o[1]);		}		return rotate;	}	private void setRotate(int i) {		rotate = i;	}	private boolean getIsSneaking() {		Object owner = getOwner();		if (!isEntityPlayer(owner)) return getCapsValueBoolean(caps_freeVariable, "isSneaking");		return Modchu_AS.getBoolean(Modchu_AS.entityIsSneaking, getOwner());	}	private void setIsSneaking(boolean b) {		Object owner = getOwner();		if (!isEntityPlayer(owner)) {			setCapsValue(caps_freeVariable, "isSneaking", b);			return;		}		Modchu_AS.set(Modchu_AS.entityIsSneaking, getOwner(), b);	}	private boolean getIsPlayer() {		return isPlayer;	}	private void setIsPlayer(boolean b) {		isPlayer = b;	}	private boolean getIsInventory() {		return isInventory;	}	private void setIsInventory(boolean b) {		isInventory = b;	}	private int getHandedness() {		return handedness;	}	private void setHandedness(int i) {		handedness = i;	}	private int getIsWaitTime() {		return isWaitTime;	}	private void setIsWaitTime(int i) {		isWaitTime = i;	}	@Override	protected boolean getActionFlag(boolean allAction) {		boolean b = false;		Object owner = getOwner();		if (isEntityPlayer(owner)				&& PFLM_Main.isPacetMode()				&& !allAction) {			b = Modchu_CastHelper.Boolean(PFLM_PacketPlayerStateManager.getPlayerStateObject(Modchu_Main.getMinecraftVersion() > 179 ? Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, getOwner()).toString() : Modchu_AS.getInt(Modchu_AS.entityEntityID, getOwner()), PFLM_Main.getPFLMFPacketConstant("packet_IDAction")));		} else {			b = super.getActionFlag(allAction);		}		if (!b) {			actionReleaseRun(allAction);			setActionReleaseNumber(0, allAction);		}		//Modchu_Debug.mDebug("getActionFlag() b="+b+" allAction="+allAction);		return b;	}	@Override	protected void setActionFlag(boolean b, boolean allAction) {		//Modchu_Debug.mDebug("setActionFlag() b="+b+" allAction="+allAction);		if (getActionFlag(allAction) != b) {			//Modchu_Debug.mDebug("setActionFlag() b="+b+" allAction="+allAction);			if (isEntityPlayer(getOwner())					&& PFLM_Main.isPacetMode()					&& !allAction) {				PFLM_PacketPlayerStateManager.addSendList(PFLM_Main.getPFLMFPacketConstant("packet_IDAction"), this, getOwner(), b);				//Modchu_Debug.mDebug("setActionFlag() PFLM_PacketPlayerStateManager.addSendList b="+b);			} else {				super.setActionFlag(b, allAction);			}		}	}	@Override	protected int getRunActionNumber(boolean allAction) {		Object owner = getOwner();		if (isEntityPlayer(owner)				&& PFLM_Main.isPacetMode()				&& !allAction) {			int i = Modchu_CastHelper.Int(PFLM_PacketPlayerStateManager.getPlayerStateObject(Modchu_Main.getMinecraftVersion() > 179 ? Modchu_AS.getUUID(Modchu_AS.entityGetUniqueID, getOwner()).toString() : Modchu_AS.getInt(Modchu_AS.entityEntityID, getOwner()), PFLM_Main.getPFLMFPacketConstant("packet_IDRunActionNumber")));			if (i != getActionReleaseNumber(allAction)) {				actionReleaseRun(allAction);				setActionReleaseNumber(i, allAction);			}			return i;		}		return super.getRunActionNumber(allAction);	}	@Override	protected boolean setRunActionNumber(int i, boolean allAction) {		Modchu_Debug.mDebug("setRunActionNumber i="+i+" allAction="+allAction);		if (isEntityPlayer(getOwner())				&& PFLM_Main.isPacetMode()				&& !allAction) {			//Modchu_Debug.mDebug("setRunActionNumber 1");			boolean b = true;			if (getActionNumberTransmission()) {				//Modchu_Debug.mDebug("setRunActionNumber 2");				PFLM_PacketPlayerStateManager.addSendList(PFLM_Main.getPFLMFPacketConstant("packet_IDRunActionNumber"), this, getOwner(), i);				setResetActionNumberInitFlag(false);				setActionNumberTransmission(false);				setActionRequest(new boolean[]{ false, false, false });			} else {				//Modchu_Debug.mDebug("setRunActionNumber 2-1");				if (getRunActionNumber(allAction) < 1) {					//Modchu_Debug.mDebug("setRunActionNumber 2-2");					setActionNumberTransmission(true);					setResetActionNumberFlag(false, allAction);				} else {					//Modchu_Debug.mDebug("setRunActionNumber 2-3");					setResetActionNumberFlag(true, allAction);					if (!getResetActionNumberInitFlag()) {						//Modchu_Debug.mDebug("setRunActionNumber 2-4");						PFLM_PacketPlayerStateManager.addSendList(PFLM_Main.getPFLMFPacketConstant("packet_IDRunActionNumber"), this, getOwner(), 0);					} else {						//Modchu_Debug.mDebug("setRunActionNumber 2-5");						setResetActionNumberInitFlag(true);					}				}				b = false;			}			//Modchu_Debug.mDebug("setRunActionNumber PFLM_PacketPlayerStateManager.addSendList i="+i+" b="+b);			return b;		}		super.setRunActionNumber(i, allAction);		//Modchu_Debug.mDebug("setRunActionNumber super end.");		return true;	}	@Override	protected boolean getResetActionNumberFlag(boolean allAction) {		return allAction ? resetAllActionNumberFlag : resetActionNumberFlag;	}	@Override	protected void setResetActionNumberFlag(boolean b, boolean allAction) {		if (allAction) {			resetAllActionNumberFlag = b;		} else {			resetActionNumberFlag = b;		}	}	protected boolean getActionNumberTransmission() {		return actionNumberTransmission;	}	protected void setActionNumberTransmission(boolean b) {		actionNumberTransmission = b;	}	protected boolean getResetActionNumberInitFlag() {		return resetActionNumberInitFlag;	}	protected void setResetActionNumberInitFlag(boolean b) {		resetActionNumberInitFlag = b;	}	private float getTempLimbSwing() {		return tempLimbSwing;	}	private void setTempLimbSwing(float f) {		tempLimbSwing = f;	}	private boolean getMushroomConfusionLeft() {		return mushroomConfusionLeft;	}	private void setMushroomConfusionLeft(boolean b) {		mushroomConfusionLeft = b;	}	private boolean getMushroomConfusionRight() {		return mushroomConfusionRight;	}	private void setMushroomConfusionRight(boolean b) {		mushroomConfusionRight = b;	}	private boolean getMushroomConfusionFront() {		return mushroomConfusionFront;	}	private void setMushroomConfusionFront(boolean b) {		mushroomConfusionFront = b;	}	private boolean getMushroomConfusionBack() {		return mushroomConfusionBack;	}	private void setMushroomConfusionBack(boolean b) {		mushroomConfusionBack = b;	}	private int getMushroomConfusionCount() {		return mushroomConfusionCount;	}	private void setMushroomConfusionCount(int i) {		mushroomConfusionCount = i;	}	private int getMushroomConfusionType() {		return mushroomConfusionType;	}	private void setMushroomConfusionType(int i) {		mushroomConfusionType = i;	}	private int getMushroomConfusionTypeMax() {		return mushroomConfusionTypeMax;	}	private boolean getMushroomBack() {		return mushroomBack;	}	private void setMushroomBack(boolean b) {		mushroomBack = b;	}	private boolean getMushroomForward() {		return mushroomForward;	}	private void setMushroomForward(boolean b) {		mushroomForward = b;	}	private boolean getMushroomLeft() {		return mushroomLeft;	}	private void setMushroomLeft(boolean b) {		mushroomLeft = b;	}	private boolean getMushroomRight() {		return mushroomRight;	}	private void setMushroomRight(boolean b) {		mushroomRight = b;	}	private int getPartsSetFlag() {		return partsSetFlag;	}	private void setPartsSetFlag(int i) {		partsSetFlag = i;	}	private float getModelScale() {		return modelScale;	}	private void setModelScale(float f) {		modelScale = f;	}	private float getIsWaitF() {		return isWaitF;	}	private void setIsWaitF(float f) {		isWaitF = f;	}	private boolean getIsActivated() {		return isActivated;	}	private void setIsActivated(boolean b) {		isActivated = b;	}	public boolean getArmorRendering() {		return getArmorRendering(0);	}	public boolean getArmorRendering(int i) {		switch (i) {		case 0:			return modelMain != null ? (Boolean) Modchu_Reflect.getFieldObject(Modchu_ModelBaseNihilBase.class, "isRendering", modelMain) : false;		case 1:		case 2:			return modelFATT != null ? (Boolean) Modchu_Reflect.getFieldObject(Modchu_ModelBaseNihilBase.class, "isRendering", modelFATT) : false;		}		return false;	}	public void setIsRendering(boolean b) {		setIsRendering(0, b);	}	public void setIsRendering(int i, boolean b) {		switch (i) {		case 0:			Modchu_Reflect.setFieldObject(Modchu_ModelBaseNihilBase.class, "isRendering", modelMain, b);			return;		case 1:		case 2:			Modchu_Reflect.setFieldObject(Modchu_ModelBaseNihilBase.class, "isRendering", modelFATT, b);			return;		}	}	private boolean getIsLookSuger() {		if (!isEntityPlayer(getOwner())) return false;		Object itemstack = Modchu_AS.get(Modchu_AS.entityPlayerInventoryGetCurrentItem, getOwner());		if (itemstack != null) {			Object item = Modchu_AS.get(Modchu_AS.itemStackGetItem, itemstack);			if (item == Modchu_AS.get(Modchu_AS.getItem, "sugar")) return true;		}		return false;	}	private float getOnGround(Object o) {		if (o != null) {			Class c = o.getClass();			if (c == Float.class					| c == float.class) {				return (Float) o;			}			Object o1 = Modchu_Reflect.getFieldObject(c, "onGround", o);			if (o1 != null) return (Float) o1;		}		return 0.0F;	}	private float getEntityIdFactor() {		return (float) Modchu_AS.getInt(Modchu_AS.entityEntityID, getOwner()) * 70;	}	private Object getInventory() {		if (!isEntityPlayer(getOwner())) return null;		return Modchu_AS.get(Modchu_AS.entityPlayerInventory, getOwner());	}	private Object getTexture(String s, int i) {		return ModchuModel_Main.textureManagerGetTexture(s, i);	}	private Object getArmorTexture(String s, int i) {		return ModchuModel_Main.textureManagerGetArmorTexture(s, i, Modchu_Reflect.newInstance("ItemStack", new Class[]{ Modchu_Reflect.loadClass("Item") }, new Object[]{ Modchu_AS.get(Modchu_AS.getItem, "diamond_helmet") }));	}	private Object getArmorTexture(String s, int i, Object itemStack) {		return ModchuModel_Main.textureManagerGetArmorTexture(s, i, itemStack);	}}