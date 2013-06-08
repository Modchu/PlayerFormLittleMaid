package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;

public class PFLM_ModelData implements MMM_IModelCaps, Modchu_IModelCaps {

	public MMM_ModelBaseDuo modelMain;
	public MMM_ModelBaseDuo modelFATT;
	public EntityLiving owner;
	private Minecraft mc = Minecraft.getMinecraft();
	private static Map<String, Integer> caps;
	private String textureName = null;
	private String modelArmorName = null;
	private boolean localFlag = false;
	private boolean isActivated = false;
	private boolean isPlayer = false;
	private boolean isWait = false;
	private boolean isWaitFSetFlag = false;
	private boolean isInventory = false;
	private boolean isSitting = false;
	private boolean shortcutKeysAction = false;
	private boolean shortcutKeysActionInitFlag = true;
	private boolean actionFlag = false;
	private boolean actionReverse = false;
	private boolean changeModelFlag = true;
	private boolean resetHandedness = true;
	private boolean motionResetFlag = false;
	private boolean mushroomConfusionLeft = false;
	private boolean mushroomConfusionRight = false;
	private boolean mushroomConfusionFront = false;
	private boolean mushroomConfusionBack = false;
	private boolean motionSetFlag = false;
	private boolean mushroomBack = false;
	private boolean mushroomForward = false;
	private boolean mushroomKeyBindResetFlag = false;
	private boolean mushroomKeyBindSetFlag = false;
	private boolean mushroomLeft = false;
	private boolean mushroomRight = false;
	private boolean sleeping = false;
	private float isWaitF = 0.0F;
	private float modelScale = 0.0F;
    private float actionSpeed = 0.0F;
	private int isWaitTime = 0;
	private int skinMode = 0;
	private int initFlag = 0;
	private int runActionNumber = 0;
	private int actionReleaseNumber = 0;
	private int actionCount = 0;
	private int handedness = 0;
	private int maidColor = 0;
	private int partsSetFlag = 1;
	private int mushroomConfusionType = 0;
	private int mushroomConfusionCount = 0;
	private int rotate = 0;
	private final int mushroomConfusionTypeMax = 4;
	private List<String> showPartsHideList = new ArrayList();
	private HashMap<String, String> showPartsRenemeMap = new HashMap();
/*//b173delete
	private boolean keyBindForwardPressed;
	private boolean keyBindBackPressed;
	private boolean keyBindLeftPressed;
	private boolean keyBindRightPressed;
	private KeyBinding keyBindForward;
	private KeyBinding keyBindBack;
	private KeyBinding keyBindLeft;
	private KeyBinding keyBindRight;
	private boolean mushroomConfusionFlag = false;
*///b173delete

	public PFLM_ModelData(RenderLiving renderLiving) {
		modelMain = new MMM_ModelBaseDuo(renderLiving);
		modelMain.isModelAlphablend = mod_PFLM_PlayerFormLittleMaid.AlphaBlend;
		modelMain.textureInner = new String [4];
		modelMain.textureOuter = new String [4];
		modelFATT = new MMM_ModelBaseDuo(renderLiving);
		modelFATT.isModelAlphablend = mod_PFLM_PlayerFormLittleMaid.AlphaBlend;
		modelFATT.textureInner = new String [4];
		modelFATT.textureOuter = new String [4];
		modelMain.capsLink = modelFATT;
	}

	@Override
	public Map<String, Integer> getModelCaps() {
		return caps;
	}

	@Override
	public boolean setCapsValue(MultiModelBaseBiped model, MMM_IModelCaps entityCaps, int pIndex, Object... pArg) {
		return false;
	}

	@Override
	public boolean setCapsValue(MultiModelBaseBiped model, int pIndex, Object... pArg) {
		switch (pIndex) {
		case caps_maidColor:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMaidColor((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_dominantArm:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setHandedness((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_isOpenInv:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsInventory((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_localFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setLocalFlag((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_isPlayer:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsPlayer((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_partsSetFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setPartsSetFlag((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_initFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setInitFlag((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_skinMode:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setSkinMode((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_isWaitTime:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsWaitTime((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_rotate:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setRotate((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_isWait:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsWait((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_isSitting:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsSitting((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_isSleeping:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsSleeping((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_isActivated:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsActivated((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_isWaitFSetFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsWaitFSetFlag((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_modelScale:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setModelScale((Float) pArg[0]);
				return true;
			}
			return false;
		case caps_isWaitF:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setIsWaitF((Float) pArg[0]);
				return true;
			}
			return false;
		case caps_shortcutKeysAction:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setShortcutKeysAction((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_actionInit:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				if (modelMain.modelInner instanceof  MultiModelAction) ((MultiModelAction) modelMain.modelInner).actionInit(this, (Integer) pArg[0]);
				if (modelMain.modelOuter instanceof  MultiModelAction) ((MultiModelAction) modelMain.modelOuter).actionInit(this, (Integer) pArg[0]);
				if (modelFATT.modelInner instanceof  MultiModelAction) ((MultiModelAction) modelFATT.modelInner).actionInit(this, (Integer) pArg[0]);;
				if (modelFATT.modelOuter instanceof  MultiModelAction) ((MultiModelAction) modelFATT.modelOuter).actionInit(this, (Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_actionRelease:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				if (modelMain.modelInner instanceof  MultiModelAction) ((MultiModelAction) modelMain.modelInner).actionRelease(this, (Integer) pArg[0]);
				if (modelMain.modelOuter instanceof  MultiModelAction) ((MultiModelAction) modelMain.modelOuter).actionRelease(this, (Integer) pArg[0]);
				if (modelFATT.modelInner instanceof  MultiModelAction) ((MultiModelAction) modelFATT.modelInner).actionRelease(this, (Integer) pArg[0]);;
				if (modelFATT.modelOuter instanceof  MultiModelAction) ((MultiModelAction) modelFATT.modelOuter).actionRelease(this, (Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_runActionNumber:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setRunActionNumber((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_actionReleaseNumber:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setActionReleaseNumber((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_actionCount:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setActionCount((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_actionFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setActionFlag((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_actionSpeed:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setActionSpeed((Float) pArg[0]);
				return true;
			}
			return false;
		case caps_actionReverse:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setActionReverse((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_changeModelFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setChangeModelFlag((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_motionResetFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMotionResetFlag((Boolean) pArg[0]);
				return true;
			}
			return false;
/*
		case caps_partsSetInit:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setPartsSetInit((Boolean) pArg[0]);
				return true;
			}
*/
		case caps_shortcutKeysActionInitFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setShortcutKeysActionInitFlag((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomConfusionCount:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomConfusionCount((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomConfusionType:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomConfusionType((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomConfusionLeft:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomConfusionLeft((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomConfusionRight:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomConfusionRight((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomConfusionFront:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomConfusionFront((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomConfusionBack:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomConfusionBack((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_motionSetFlag:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMotionSetFlag((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomBack:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomBack((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomLeft:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomLeft((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_mushroomRight:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setMushroomRight((Boolean) pArg[0]);
				return true;
			}
			return false;
		case caps_changeColor:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				Modchu_Debug.mDebug("caps_changeColor modelFATT.modelInner instanceof  MultiModelBaseBiped ? "+(modelFATT.modelInner instanceof  MultiModelBaseBiped));
				if (modelMain.modelInner instanceof  MultiModelBaseBiped) ((MultiModelBaseBiped) modelMain.modelInner).changeColor(this);
				if (modelMain.modelOuter instanceof  MultiModelBaseBiped) ((MultiModelBaseBiped) modelMain.modelOuter).changeColor(this);
				if (modelFATT.modelInner instanceof  MultiModelBaseBiped) ((MultiModelBaseBiped) modelFATT.modelInner).changeColor(this);
				if (modelFATT.modelOuter instanceof  MultiModelBaseBiped) ((MultiModelBaseBiped) modelFATT.modelOuter).changeColor(this);
				return true;
			}
			return false;
		case caps_indexOfAllVisible:
			if (pArg != null
			&& pArg.length > 1
			&& pArg[0] != null
			&& pArg[1] != null) {
				if (pArg.length > 2
						&& pArg[2] != null) indexOfAllSetVisible((String) pArg[0], (Integer) pArg[1], (Boolean) pArg[2]);
				else indexOfAllSetVisible((String) pArg[0], (Integer) pArg[1]);
				return true;
			}
			return false;
		case caps_showModelSettingReflects:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				showModelSettingReflects((Integer) pArg[0]);
				return true;
			}
			return false;
		case caps_showPartsHideList:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				addShowPartsHideList((String[]) pArg[0]);
				return true;
			}
			return false;
		case caps_showPartsRenemeMap:
			if (pArg != null
			&& pArg.length > 1
			&& pArg[0] != null
			&& pArg[1] != null) {
				addShowPartsRenemeMap((String[]) pArg[0], (String[]) pArg[1]);
				return true;
			}
			return false;
/*
		case caps_showPartsNemeMap:
			if (pArg != null
			&& pArg.length > 1
			&& pArg[0] != null
			&& pArg[1] != null) {
				setShowPartsNemeMap((Integer) pArg[0], (String) pArg[1]);
				return true;
			}
*/
		case caps_textureName:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setTextureName((String) pArg[0]);
				return true;
			}
			return false;
		case caps_textureArmorName:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) {
				setTextureArmorName((String) pArg[0]);
				return true;
			}
			return false;
		case caps_defaultShowPartsMap:
			if (pArg != null
			&& pArg.length > 2
			&& pArg[0] != null
			&& pArg[1] != null
			&& pArg[2] != null) {
				putDefaultShowPartsMap((String) pArg[0], (Integer) pArg[1], (Boolean) pArg[2]);
				return true;
			}
			return false;
		case caps_setLivingAnimationsBefore:
			if (pArg != null
			&& pArg.length > 3
			&& pArg[0] != null
			&& pArg[1] != null
			&& pArg[2] != null
			&& pArg[3] != null) {
				setLivingAnimationsBefore((MMM_ModelMultiBase) pArg[0], (Float) pArg[1], (Float) pArg[2], (Float) pArg[3]);
				return true;
			}
			return false;
		case caps_setLivingAnimationsAfter:
			if (pArg != null
			&& pArg.length > 3
			&& pArg[0] != null
			&& pArg[1] != null
			&& pArg[2] != null
			&& pArg[3] != null) {
				setLivingAnimationsAfter((MMM_ModelMultiBase) pArg[0], (Float) pArg[1], (Float) pArg[2], (Float) pArg[3]);
				return true;
			}
			return false;
		case caps_setRotationAnglesBefore:
			if (pArg != null
			&& pArg.length > 6
			&& pArg[0] != null
			&& pArg[1] != null
			&& pArg[2] != null
			&& pArg[3] != null
			&& pArg[4] != null
			&& pArg[5] != null
			&& pArg[6] != null) {
				setRotationAnglesBefore((MMM_ModelMultiBase) pArg[0], (Float) pArg[1] ,(Float) pArg[2], (Float) pArg[3], (Float) pArg[4], (Float) pArg[5], (Float) pArg[6]);
				return true;
			}
			return false;
		case caps_setRotationAnglesAfter:
			if (pArg != null
			&& pArg.length > 6
			&& pArg[0] != null
			&& pArg[1] != null
			&& pArg[2] != null
			&& pArg[3] != null
			&& pArg[4] != null
			&& pArg[5] != null
			&& pArg[6] != null) {
				setRotationAnglesAfter((MMM_ModelMultiBase) pArg[0], (Float) pArg[1] ,(Float) pArg[2], (Float) pArg[3], (Float) pArg[4], (Float) pArg[5], (Float) pArg[6]);
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		return setCapsValue((MultiModelBaseBiped) null, pIndex, pArg);
	}

	@Override
	public Object getCapsValue(int pIndex, Object ...pArg) {
		return getCapsValue(null, pIndex, pArg);
	}

	@Override
	public Object getCapsValue(MultiModelBaseBiped model, MMM_IModelCaps entityCaps, int pIndex, Object ...pArg) {
		return null;
	}

	@Override
	public Object getCapsValue(MultiModelBaseBiped model, int pIndex, Object ...pArg) {
		switch (pIndex) {
		case caps_dominantArm:
			return getHandedness();
		case caps_isLookSuger:
			return getIsLookSuger();
		case caps_entityIdFactor:
			return getEntityIdFactor();
		case caps_isOpenInv:
			return getIsInventory();
		case caps_Inventory:
			return getInventory();
		case caps_maidColor:
			return getMaidColor();
		case caps_localFlag:
			return getLocalFlag();
		case caps_rotate:
			return getRotate();
		case caps_texture:
			if (pArg != null
			&& pArg.length > 1
			&& pArg[0] != null
			&& pArg[1] != null) return getTexture((String) pArg[0], (Integer) pArg[1]);
			break;
		case caps_armorTexture:
			if (pArg != null
			&& pArg.length > 1
			&& pArg[0] != null
			&& pArg[1] != null) {
				if (pArg.length > 2
						&& pArg[2] != null) return getArmorTexture((String) pArg[0], (Integer) pArg[1], (ItemStack) pArg[2]);
				return getArmorTexture((String) pArg[0], (Integer) pArg[1]);
			}
			break;
		case caps_isPlayer:
			return getIsPlayer();
		case caps_isWait:
			return getIsWait();
		case caps_isSitting:
			return getIsSitting();
		case caps_isSleeping:
			return getIsSleeping();
		case caps_isCamouflage:
			return isCamouflage();
		case caps_isPlanter:
			return isPlanter();
		case caps_height:
			return owner.height;
		case caps_width:
			return owner.width;
		case caps_YOffset:
			return owner.yOffset;
		case caps_HeadMount:
			return getHeadMount();
		case caps_Items:
			return getItems();
		case caps_Actions:
			return getActions();
		case caps_shortcutKeysAction:
			return getShortcutKeysAction();
		case caps_runActionNumber:
			return getRunActionNumber();
		case caps_actionReleaseNumber:
			return getActionReleaseNumber();
		case caps_actionFlag:
			return getActionFlag();
		case caps_actionCount:
			return getActionCount();
		case caps_actionSpeed:
			return getActionSpeed();
		case caps_actionReverse:
			return getActionReverse();
		case caps_changeModelFlag:
			return getChangeModelFlag();
		case caps_partsSetFlag:
			return getPartsSetFlag();
		case caps_shortcutKeysActionInitFlag:
			return getShortcutKeysActionInitFlag();
		case caps_isActivated:
			return getIsActivated();
		case caps_initFlag:
			return getInitFlag();
		case caps_skinMode:
			return getSkinMode();
		case caps_modelScale:
			return getModelScale();
		case caps_isWaitF:
			return getIsWaitF();
		case caps_isWaitFSetFlag:
			return getIsWaitFSetFlag();
		case caps_isWaitTime:
			return getIsWaitTime();
		case caps_mushroomConfusionCount:
			return getMushroomConfusionCount();
		case caps_mushroomConfusionType:
			return getMushroomConfusionType();
		case caps_mushroomConfusionTypeMax:
			return getMushroomConfusionTypeMax();
		case caps_mushroomConfusionLeft:
			return getMushroomConfusionLeft();
		case caps_mushroomConfusionRight:
			return getMushroomConfusionRight();
		case caps_mushroomConfusionFront:
			return getMushroomConfusionFront();
		case caps_mushroomConfusionBack:
			return getMushroomConfusionBack();
		case caps_motionResetFlag:
			return getMotionResetFlag();
		case caps_motionSetFlag:
			return getMotionSetFlag();
		case caps_mushroomLeft:
			return getMushroomLeft();
		case caps_mushroomRight:
			return getMushroomRight();
		case caps_showPartsHideList:
			return getShowPartsHideList();
		case caps_model:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) return getModel((Integer) pArg[0]);
			break;
		case caps_currentArmor:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) return getArmorItemInSlot((Integer) pArg[0]);
			break;
		case caps_textureName:
			return getTextureName();
		case caps_textureArmorName:
			return getTextureArmorName();
		case caps_defaultShowPartsMap:
			if (pArg != null
			&& pArg.length > 1
			&& pArg[0] != null
			&& pArg[1] != null) return getDefaultShowPartsMapBoolean((String) pArg[0], (Integer) pArg[1]);
			break;
		case caps_showPartsRenemeMap:
			return getShowPartsRenemeMap();
		case caps_showPartsMap:
			if (pArg != null
			&& pArg.length > 0
			&& pArg[0] != null) return getShowPartsMap((Integer) pArg[0]);
			break;
		case caps_showPartsMapBoolean:
			if (pArg != null
			&& pArg.length > 1
			&& pArg[0] != null
			&& pArg[1] != null) {
				return getShowPartsMapBoolean((String) pArg[0], (Integer) pArg[1]);
			}
			break;
		case caps_modelRendererName:
			if (pArg != null
			&& pArg.length > 1
			&& pArg[0] != null
			&& pArg[1] != null) return getModelRendererName((MMM_ModelRenderer) pArg[0], (Integer) pArg[1]);
			break;
		case caps_Entity:
				return owner;
		}
		if (model != null) ;else return modelMain.modelInner.getCapsValue(pIndex, pArg);
		return null;
	}

	private String getModelRendererName(MMM_ModelRenderer modelRenderer, int i) {
		MMM_ModelRenderer modelRenderer2;
		Object model = getModel(i);
		HashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(model, textureName, i);
		Modchu_Debug.mDebug("getModelRendererName modelRendererMap != null ?"+(modelRendererMap != null));
		Iterator<Entry<String, Field>> iterator = modelRendererMap.entrySet().iterator();
		Entry<String, Field> entry;
		Field f;
		String s;
		Object o;
		while(iterator.hasNext()) {
			entry = iterator.next();
			s = entry.getKey();
			f = entry.getValue();
			o = null;
			Modchu_Debug.mDebug("getModelRendererName s="+s);
			try {
				o = f.get(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (o != null) {
				modelRenderer2 = (MMM_ModelRenderer) o;
				if (modelRenderer2.equals(modelRenderer)) {
					return s;
				}
			}
		}
		return null;
	}

	/**
	 * setLivingAnimationsLM 呼び出し前に呼ばれる。
	 */
	private void setLivingAnimationsBefore(MMM_ModelMultiBase model, float f, float f1, float f2) {
	}

	/**
	 * setLivingAnimationsLM 呼び出し後に呼ばれる。
	 */
	private void setLivingAnimationsAfter(MMM_ModelMultiBase model, float f, float f1, float f2) {
		if (model != null) ;else return;
		model.setCapsValue(caps_isRiding, !owner.isRiding() ? getIsSitting() : true);
		//Modchu_Debug.mDebug("PFLM_ModelData setLivingAnimationsAfter caps_isRiding="+modelBiped.getCapsValue(caps_isRiding));
		//Modchu_Debug.mDebug("PFLM_ModelData setLivingAnimationsAfter PFLM_Gui.partsSetFlag="+PFLM_Gui.partsSetFlag);
		if (owner != null
				&& model != null
				&& model instanceof MultiModelBaseBiped) ;else return;
		MultiModelBaseBiped multiModelBaseBiped = (MultiModelBaseBiped) model;
		if (PFLM_Gui.partsSetFlag < 2) {
			PFLM_Gui.partsSetFlag = 2;
			partsSetFlag = 1;
		}
		if(partsSetFlag == 1) {
			PFLM_Config.loadShowModelList(mod_PFLM_PlayerFormLittleMaid.showModelList);
			multiModelBaseBiped.defaultPartsSettingBefore(this);
			multiModelBaseBiped.defaultPartsSettingAfter(this);
			partsSetFlag = 2;
			multiModelBaseBiped.showModelSettingReflects(this);
			showModelSettingReflects(Modchu_ModelCapsHelper.getCapsValueInt(multiModelBaseBiped, caps_armorType));
		}
	}

	/**
	 * setRotationAnglesLM 呼び出し前に呼ばれる。
	 */
	private void setRotationAnglesBefore(MMM_ModelMultiBase model, float f, float f1, float f2, float f3, float f4, float f5) {
	}

	/**
	 * setRotationAnglesLM 呼び出し後に呼ばれる。
	 */
	private void setRotationAnglesAfter(MMM_ModelMultiBase model, float f, float f1, float f2, float f3, float f4, float f5) {
		if (owner != null
				&& model != null
				&& model instanceof MultiModelBaseBiped) ;else return;
		MultiModelBaseBiped multiModelBaseBiped = (MultiModelBaseBiped) model;
		if (Modchu_ModelCapsHelper.getCapsValueBoolean(multiModelBaseBiped, caps_firstPerson)) ((MultiModelBaseBiped) model).setRotationAnglesfirstPerson(f, f1, f2, f3, f4, f5, this);
		if (getShortcutKeysAction()) {
			if (model instanceof MultiModelAction) ((MultiModelAction) model).action(f, f1, f2, f3, f4, f5, getRunActionNumber(), this);
			if (getActionFlag()) {
				setActionSpeed(0.0F);
				setActionFlag(false);
			}
		}
	}

	private Object getModel(int i) {
		switch(i) {
		case 0:
			return modelMain.modelInner;
		case 1:
			return modelMain.modelOuter;
		case 2:
			return modelFATT.modelInner;
		case 3:
			return modelFATT.modelOuter;
		}
		return null;
	}

	private String getTextureName() {
		return textureName;
	}

	private void setTextureName(String s) {
		textureName = s;
	}

	private String getTextureArmorName() {
		return modelArmorName;
	}

	private void setTextureArmorName(String s) {
		modelArmorName = s;
	}

	private boolean getChangeModelFlag() {
		return changeModelFlag;
	}

	private void setChangeModelFlag(boolean b) {
		changeModelFlag = b;
	}

	private boolean getMotionResetFlag() {
		return motionResetFlag;
	}

	private void setMotionResetFlag(boolean b) {
		motionResetFlag = b;
	}

	private boolean getIsWaitFSetFlag() {
		return isWaitFSetFlag;
	}

	private void setIsWaitFSetFlag(boolean b) {
		isWaitFSetFlag = b;
	}

	private boolean getLocalFlag() {
		return localFlag;
	}

	private void setLocalFlag(boolean b) {
		localFlag = b;
	}

	private HashMap<String, Boolean> getShowPartsMap(int i) {
		return PFLM_Config.getConfigShowPartsMap(textureName, maidColor, i);
	}

	private int getShowPartsMapBoolean(String s, int i) {
		return PFLM_Config.getConfigShowPartsMapBoolean(textureName, s, maidColor, i);
	}

	private ItemStack getArmorItemInSlot(int i) {
		if (!(owner instanceof EntityPlayer)) return null;
		return ((EntityPlayer) owner).inventory.armorItemInSlot(i);
	}

	private ItemStack getHeadMount() {
		if (!(owner instanceof EntityPlayer)) return null;
		ItemStack itemStack = ((EntityPlayer) owner).inventory.getStackInSlot(9);
		int addSupport = ((MultiModelBaseBiped) modelMain.modelInner).addSupportChecks(itemStack);
		return addSupport != 3
				&& addSupport != 4 ? itemStack : null;
	}

	private EnumAction[] getActions() {
		if (!(owner instanceof EntityPlayer)) return null;
		EnumAction[] lactions = new EnumAction[2];
		ItemStack litemstack = ((EntityPlayer) owner).inventory.getCurrentItem();
		lactions[getHandedness()] = litemstack != null && ((EntityPlayer) owner).getItemInUseCount() > 0 ? litemstack.getItemUseAction() : null;
		return lactions;
	}

	private ItemStack[] getItems() {
		if (!(owner instanceof EntityPlayer)) return null;
		ItemStack[] itemStack = new ItemStack[2];
		itemStack[getHandedness()] = ((EntityPlayer) owner).inventory.getCurrentItem();
		return itemStack;
	}

	private boolean isPlanter() {
		if (!(owner instanceof EntityPlayer)) return false;
		ItemStack itemStack = ((EntityPlayer) owner).inventory.getStackInSlot(9);
		if (itemStack != null) ;else return false;
		Item item = itemStack.getItem();
		if (item != null) ;else return false;
		if (item.itemID < Block.blocksList.length) {
			try {
				Block block = Block.blocksList[item.itemID];
				if (block != null) return block instanceof BlockFlower;
			} catch(Exception e) {
			}
		}
		return false;
	}

	private boolean isCamouflage() {
		if (!(owner instanceof EntityPlayer)) return false;
		ItemStack itemStack = ((EntityPlayer) owner).inventory.getStackInSlot(9);
		if (itemStack != null) ;else return false;
		Item item = itemStack.getItem();
		if (item != null) ;else return false;
		if (item.itemID < Block.blocksList.length) {
			try {
				Block block = Block.blocksList[item.itemID];
				if (block != null) return (item instanceof ItemBlock
						&& block instanceof BlockLeaves)
						|| block instanceof BlockPumpkin;
			} catch(Exception e) {
			}
		}
		return false;
	}

	private boolean getIsWait() {
		return isWait;
	}

	private void setIsWait(boolean b) {
		isWait = b;
	}

	private boolean getIsSitting() {
		return isSitting;
	}

	private void setIsSitting(boolean b) {
		isSitting = b;
	}

	private boolean getIsSleeping() {
		if (!(owner instanceof EntityPlayer)) return false;
		return ((EntityPlayer) owner).isPlayerSleeping()
				| sleeping;
	}

	private void setIsSleeping(boolean b) {
		if (!(owner instanceof EntityPlayer)) return;
		sleeping = b;
		float f1 = ((EntityPlayer) owner).moveForward * ((EntityPlayer) owner).moveForward + ((EntityPlayer) owner).moveStrafing * ((EntityPlayer) owner).moveStrafing;
		if (((EntityPlayer) owner).isRiding() || !mc.inGameHasFocus || f1 > 0.0F || ((EntityPlayer) owner).isJumping) return;
		float f2;
		for (f2 = ((EntityPlayer) owner).rotationYaw; f2 < 0.0F; f2 += 360F) { }
		for (; f2 > 360F; f2 -= 360F) { }
		int i = (int)((f2 + 45F) / 90F);
		setRotate(i);
		//((EntityPlayer) owner).getDataWatcher().updateObject(17, Byte.valueOf((byte)i));
/*//b173delete
		setSleepMotion(i);
*///b173delete
	}

	private int getRotate() {
		return rotate;
	}

	private void setRotate(int i) {
		rotate = i;
	}

	private boolean getIsPlayer() {
		return isPlayer;
	}

	private void setIsPlayer(boolean b) {
		isPlayer = b;
	}

	private boolean getIsInventory() {
		return isInventory;
	}

	private void setIsInventory(boolean b) {
		isInventory = b;
	}

	private int getHandedness() {
		return handedness;
	}

	private void setHandedness(int i) {
		handedness = i;
	}

	private int getIsWaitTime() {
		return isWaitTime;
	}

	private void setIsWaitTime(int i) {
		isWaitTime = i;
	}

	private boolean getShortcutKeysAction() {
		return shortcutKeysAction;
	}

	private void setShortcutKeysAction(boolean b) {
		shortcutKeysAction = b;
	}

	private int getRunActionNumber() {
		return runActionNumber;
	}

	private void setRunActionNumber(int i) {
		runActionNumber = i;
	}

	private int getActionReleaseNumber() {
		return actionReleaseNumber;
	}

	private void setActionReleaseNumber(int i) {
		actionReleaseNumber = i;
	}

	private boolean getActionFlag() {
		return actionFlag;
	}

	private void setActionFlag(boolean b) {
		actionFlag = b;
	}

	private int getActionCount() {
		return actionCount;
	}

	private void setActionCount(int i) {
		actionCount = i;
	}

	private float getActionSpeed() {
		return actionSpeed;
	}

	private void setActionSpeed(float f) {
		actionSpeed = f;
	}

	private boolean getActionReverse() {
		return actionReverse;
	}

	private void setActionReverse(boolean b) {
		actionReverse = b;
	}

	private boolean getMushroomConfusionLeft() {
		return mushroomConfusionLeft;
	}

	private void setMushroomConfusionLeft(boolean b) {
		mushroomConfusionLeft = b;
	}

	private boolean getMushroomConfusionRight() {
		return mushroomConfusionRight;
	}

	private void setMushroomConfusionRight(boolean b) {
		mushroomConfusionRight = b;
	}

	private boolean getMushroomConfusionFront() {
		return mushroomConfusionFront;
	}

	private void setMushroomConfusionFront(boolean b) {
		mushroomConfusionFront = b;
	}

	private boolean getMushroomConfusionBack() {
		return mushroomConfusionBack;
	}

	private void setMushroomConfusionBack(boolean b) {
		mushroomConfusionBack = b;
	}

	private int getMushroomConfusionCount() {
		return mushroomConfusionCount;
	}

	private void setMushroomConfusionCount(int i) {
		mushroomConfusionCount = i;
	}

	private int getMushroomConfusionType() {
		return mushroomConfusionType;
	}

	private void setMushroomConfusionType(int i) {
		mushroomConfusionType = i;
	}

	private int getMushroomConfusionTypeMax() {
		return mushroomConfusionTypeMax;
	}

	private boolean getMotionSetFlag() {
		return motionSetFlag;
	}

	private void setMotionSetFlag(boolean b) {
		motionSetFlag = b;
	}

	private boolean getMushroomBack() {
		return mushroomBack;
	}

	private void setMushroomBack(boolean b) {
		mushroomBack = b;
	}

	private boolean getMushroomForward() {
		return mushroomForward;
	}

	private void setMushroomForward(boolean b) {
		mushroomForward = b;
	}

	private boolean getMushroomLeft() {
		return mushroomLeft;
	}

	private void setMushroomLeft(boolean b) {
		mushroomLeft = b;
	}

	private boolean getMushroomRight() {
		return mushroomRight;
	}

	private void setMushroomRight(boolean b) {
		mushroomRight = b;
	}

	private int getSkinMode() {
		return skinMode;
	}

	private void setSkinMode(int i) {
		skinMode = i;
	}

	private int getPartsSetFlag() {
		return partsSetFlag;
	}

	private void setPartsSetFlag(int i) {
		partsSetFlag = i;
	}

	private int getInitFlag() {
		return initFlag;
	}

	private void setInitFlag(int i) {
		initFlag = i;
	}

	private float getModelScale() {
		return modelScale;
	}

	private void setModelScale(float f) {
		modelScale = f;
	}

	private float getIsWaitF() {
		return isWaitF;
	}

	private void setIsWaitF(float f) {
		isWaitF = f;
	}

	private boolean getIsActivated() {
		return isActivated;
	}

	private void setIsActivated(boolean b) {
		isActivated = b;
	}

	private boolean getShortcutKeysActionInitFlag() {
		return shortcutKeysActionInitFlag;
	}

	private void setShortcutKeysActionInitFlag(boolean b) {
		shortcutKeysActionInitFlag = b;
	}

    private boolean getIsLookSuger() {
    	if (!(owner instanceof EntityPlayer)) return false;
    	ItemStack itemstack = ((EntityPlayer) owner).inventory.getCurrentItem();
    	if (itemstack != null) {
    		Item item = itemstack.getItem();
    		if (item == Item.sugar) return true;
    	}
    	return false;
    }

    private float getOnGround(Object o)
    {
    	if (o != null) {
    		Class c = o.getClass();
    		if (c == Float.class
    				| c == float.class) {
    			return (Float) o;
    		}
    		Object o1 = Modchu_Reflect.getFieldObject(c, "onGround", o);
    		if (o1 != null) return (Float) o1;
    	}
    	return 0.0F;
    }

    private float getEntityIdFactor() {
    	return (float)owner.entityId * 70;
    }

    private Object getInventory() {
    	if (!(owner instanceof EntityPlayer)) return null;
    	return ((EntityPlayer) owner).inventory;
    }

    private int getMaidColor() {
    	if (owner instanceof EntityPlayer) {
    		if (mc.currentScreen != null
    				&& mc.currentScreen instanceof PFLM_Gui) {
    			return mod_PFLM_PlayerFormLittleMaid.maidColor;
    		}
    		return maidColor;
    	} else {
    		if (mc.currentScreen != null) ;else return 0;
    		if (mc.currentScreen instanceof PFLM_Gui) return PFLM_Gui.setColor;
    		if (mc.currentScreen instanceof PFLM_GuiModelSelect) return ((PFLM_GuiModelSelect) mc.currentScreen).modelColor;
    		if (mc.currentScreen instanceof PFLM_GuiOthersPlayerIndividualCustomize) return PFLM_GuiOthersPlayerIndividualCustomize.othersMaidColor;
    		if (mc.currentScreen instanceof PFLM_GuiOthersPlayer) return mod_PFLM_PlayerFormLittleMaid.othersMaidColor;
    	}
    	return 0;
    }

    private void setMaidColor(int i) {
    	maidColor = i;
    }

    private String getTexture(String s, int i) {
    	return mod_Modchu_ModchuLib.textureManagerGetTexture(s, i);
    }


    private String getArmorTexture(String s, int i) {
    	return mod_Modchu_ModchuLib.textureManagerGetArmorTexture(s, i, new ItemStack(Item.helmetDiamond));
    }

    private String getArmorTexture(String s, int i, ItemStack itemStack) {
    	return mod_Modchu_ModchuLib.textureManagerGetArmorTexture(s, i, itemStack);
    }

    /**
     * GUI パーツ表示・非表示反映
     */
    private void showModelSettingReflects(int i) {
    	//Modchu_Debug.mDebug("showModelSettingReflects i="+i);
    	HashMap<String, Boolean> showPartsMap = PFLM_Config.getConfigShowPartsMap(textureName, maidColor, i);
    	HashMap<String, Boolean> defaultShowPartsMap = getDefaultShowPartsMap(i);
    	Object model = getModel(i);
    	HashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(model, textureName, i);
    	//Modchu_Debug.mDebug("showModelSettingReflects textureName="+textureName+" modelRendererMap != null ?"+(modelRendererMap != null));
    	if (modelRendererMap != null) ;else return;
    	if (defaultShowPartsMap != null) settingReflects(modelRendererMap, defaultShowPartsMap, null, null, i);
    	HashMap<String, Boolean> indexOfAllSetVisibleBooleanMap = PFLM_Config.getIndexOfAllSetVisibleBooleanMap(textureName, i);
    	if (showPartsMap != null
    			&& !showPartsMap.isEmpty()) settingReflects(modelRendererMap, showPartsMap, PFLM_Config.getIndexOfAllSetVisibleMap(textureName, i), indexOfAllSetVisibleBooleanMap, i);
    }

    private void settingReflects(HashMap<String, Field> modelRendererMap, HashMap<String, Boolean> map,
    		HashMap<String, List<String>> indexOfAllSetVisibleMap, HashMap<String, Boolean> indexOfAllSetVisibleBooleanMap, int i) {
    	Object model = getModel(i);
    	Field f = null;
    	String s2 = null;
    	MMM_ModelRenderer modelRenderer = null;
    	boolean b;
    	Iterator<Entry<String, Boolean>> iterator = map.entrySet().iterator();
    	Entry<String, Boolean> entry;
    	//Modchu_Debug.mDebug("settingReflects i="+i);
    	while(iterator.hasNext()) {
    		entry = iterator.next();
    		s2 = entry.getKey();
    		b = entry.getValue();
    		if (modelRendererMap.containsKey(s2)) f = modelRendererMap.get(s2);
    		else f = null;
    		//Modchu_Debug.mDebug("settingReflects s2="+s2+" b="+b);
    		if (f != null) {
    			//Modchu_Debug.mDebug("showModelSettingReflects f != null");
    			try {
    				Object o = f.get(model);
    				modelRenderer = o != null ? (MMM_ModelRenderer) o : null;
    				if (modelRenderer != null) {
    					((MultiModelBaseBiped) model).setCapsValue(caps_visible, modelRenderer, b, true);
    					if (mod_Modchu_ModchuLib.ngPlayerModelList != null
    							&& !mod_Modchu_ModchuLib.ngPlayerModelList.contains(textureName)) mod_Modchu_ModchuLib.ngPlayerModelList.add(textureName);
    				}
    			} catch (Exception e) {
    				//e.printStackTrace();
    			}
    		}
    	}
    	if (indexOfAllSetVisibleMap != null
    			&& !indexOfAllSetVisibleMap.isEmpty()) {
    		Iterator<Entry<String, List<String>>> iterator2 = indexOfAllSetVisibleMap.entrySet().iterator();
    		Entry<String, List<String>> entry2;
    		List<String> list;
    		while(iterator2.hasNext()) {
    			entry2 = iterator2.next();
    			s2 = entry2.getKey();
    			list = entry2.getValue();
    			if (modelRendererMap.containsKey(s2)) f = modelRendererMap.get(s2);
    			else f = null;
    			//Modchu_Debug.mDebug("settingReflects indexOfAllSetVisibleMap s2="+s2);
    			if (f != null) {
    				//Modchu_Debug.mDebug("showModelSettingReflects indexOfAllSetVisibleMap f != null");
    				try {
    					Object o = f.get(model);
    					modelRenderer = o != null ? (MMM_ModelRenderer) o : null;
    					if (modelRenderer != null) {
    						b = modelRenderer.showModel;
    						for(int i1 = 0; i1 < list.size(); i1++) {
    							s2 = list.get(i1);
    							if (modelRendererMap.containsKey(s2)) f = modelRendererMap.get(s2);
    							else f = null;
    							//Modchu_Debug.mDebug("settingReflects indexOfAllSetVisibleList s2="+s2);
    							if (f != null) {
    								//Modchu_Debug.mDebug("showModelSettingReflects indexOfAllSetVisibleList f != null");
    								try {
    									o = f.get(model);
    								modelRenderer = o != null ? (MMM_ModelRenderer) o : null;
    								if (modelRenderer != null) {
    									((MultiModelBaseBiped) model).setCapsValue(caps_visible, modelRenderer, b, true);
    								}
    								} catch (Exception e) {
    								}
    							}
    						}
    					}
    				} catch (Exception e) {
    				}
    			}
    		}
    	}
    	if (indexOfAllSetVisibleBooleanMap != null
    			&& !indexOfAllSetVisibleBooleanMap.isEmpty()) {
    		Iterator<Entry<String, Boolean>> iterator2 = indexOfAllSetVisibleBooleanMap.entrySet().iterator();
    		Entry<String, Boolean> entry2;
    		Iterator<Entry<String, Field>> iterator3;
    		Entry<String, Field> entry3;
    		String s3;
    		while(iterator2.hasNext()) {
    			entry2 = iterator2.next();
    			s2 = entry2.getKey();
    			b = entry2.getValue();
    			iterator3 = modelRendererMap.entrySet().iterator();
    			while(iterator3.hasNext()) {
    				entry3 = iterator3.next();
    				s3 = entry3.getKey();
    				if (s3.indexOf(s2) < 0) continue;
    				f = entry3.getValue();
    				//Modchu_Debug.mDebug("settingReflects indexOfAllSetVisibleBooleanMap s3="+s3);
    				if (f != null) {
    					//Modchu_Debug.mDebug("showModelSettingReflects indexOfAllSetVisibleBooleanMap f != null");
    					try {
    						Object o = f.get(model);
    						modelRenderer = o != null ? (MMM_ModelRenderer) o : null;
    						if (modelRenderer != null) {
    							((MultiModelBaseBiped) model).setCapsValue(caps_visible, modelRenderer, b, true);
    						}
    					} catch (Exception e) {
    					}
    				}
    			}
    		}
    	}
    }

	private List<String> getShowPartsHideList() {
    	return showPartsHideList;
    }

    /**
     * GUI パーツ表示・非表示用 ボタン非表示リスト追加
     */
    private void addShowPartsHideList(String[] s) {
    	//Modchu_Debug.mDebug("addShowPartsHideList s.length="+s.length);
    	for(int i = 0; i < s.length; i++) {
    		showPartsHideList.add(s[i]);
    	}
    }

    private HashMap<String, String> getShowPartsRenemeMap() {
    	return showPartsRenemeMap;
    }

    /**
     * GUI パーツ表示・非表示用 ボタン表示名リネーム用追加
     */
    private void addShowPartsRenemeMap(String[] s1, String[] s2) {
    	for(int i = 0; i < s1.length && i < s2.length; i++) {
    		showPartsRenemeMap.put(s1[i], s2[i]);
    	}
    }

    /**
     * indexOfで検索対象のパーツをまとめて指定パーツと同じ状態にセットするListへの追加
     */
    private void indexOfAllSetVisible(String s, int i) {
    	MultiModelBaseBiped model = (MultiModelBaseBiped) getModel(i);
    	List<String> indexOfAllSetVisibleList = PFLM_Config.getIndexOfAllSetVisibleMap(textureName, s, i);
    	HashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(model, textureName, i);
    	if (modelRendererMap != null
    			&& modelRendererMap.containsKey(s)) ;else return;
    	String s0 = null;
    	Field f = modelRendererMap.get(s);
    	if (f != null) ;else return;
    	try {
    		Object o = f.get(model);
    		if (o != null) ;else return;
    		boolean b = ((MMM_ModelRenderer) o).showModel;
    		Iterator<Entry<String, Field>> iterator = modelRendererMap.entrySet().iterator();
    		Entry<String, Field> entry;
    		while(iterator.hasNext()) {
    			entry = iterator.next();
    			s0 = entry.getKey();
    			if (s0 != null
    					&& s0.indexOf(s) > -1) {
    				if (!indexOfAllSetVisibleList.contains(s0)
    						&& !s0.equals(s)) {
    					indexOfAllSetVisibleList.add(s0);
    					//Modchu_Debug.mDebug("indexOfAllSetVisible add s0="+s0);
    					PFLM_Config.setIndexOfAllSetVisibleMap(textureName, s, i, indexOfAllSetVisibleList);
    				}
    			}
    		}
    	} catch (Exception e1) {
    		e1.printStackTrace();
    	}
    }

    /**
     * indexOfで検索対象のパーツをまとめて指定booleanにセットするListへの追加
     */
    private void indexOfAllSetVisible(String s, int i, boolean b) {
    	HashMap<String, Boolean> indexOfAllSetVisibleBooleanMap = PFLM_Config.getIndexOfAllSetVisibleBooleanMap(textureName, i);
    	indexOfAllSetVisibleBooleanMap.put(s, b);
    	PFLM_Config.setIndexOfAllSetVisibleBooleanMap(textureName, i, indexOfAllSetVisibleBooleanMap);
/*
    	HashMap<Integer, String> nemeMap = PFLM_Config.getConfigShowPartsNemeMap(s, i);
    	MultiModelBaseBiped model = (MultiModelBaseBiped) getModel(i);
    	HashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(model, textureName, i);
    	if (nemeMap != null) ;else return;
    	String s0 = null;
    	for(int i1 = 0; i1 < nemeMap.size(); i1++) {
    		s0 = nemeMap.get(i1);
    		if (s0.indexOf(s) > -1) {
    			try {
    				model.setCapsValue(caps_visible, (MMM_ModelRenderer) modelRendererMap.get(s0).get(model), b);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
*/
    }

    private HashMap<String, Boolean> getDefaultShowPartsMap(int i) {
    	return PFLM_Config.getDefaultShowPartsMap(textureName, i);
    }

    private boolean getDefaultShowPartsMapBoolean(String s, int i) {
    	return PFLM_Config.getDefaultShowPartsMapBoolean(textureName, s, i);
    }

    private void putDefaultShowPartsMap(String s, int i, boolean b) {
    	PFLM_Config.putDefaultShowPartsMap(textureName, s, i, b);
    }

	public int getCapsValueInt(int pIndex, Object ...pArg) {
		Object o = getCapsValue(pIndex, pArg);
		if (o instanceof Boolean) {
			boolean b = (Boolean) o;
			return b ? 2 : 1;
		}
		return o != null ? (Integer) o : 0;
	}

	public float getCapsValueFloat(int pIndex, Object ...pArg) {
		Object o = getCapsValue(pIndex, pArg);
		return o != null ? (Float) o : 0.0F;
	}

	public double getCapsValueDouble(int pIndex, Object ...pArg) {
		Object o = getCapsValue(pIndex, pArg);
		return o != null ? (Double) o : 0.0D;
	}

	public boolean getCapsValueBoolean(int pIndex, Object ...pArg) {
		Object o = getCapsValue(pIndex, pArg);
		return o != null ? (Boolean) o : false;
	}
/*//151delete
	public Object getCapsValue(String pCapsName, Object ...pArg) {
		return getCapsValue(caps.get(pCapsName), pArg);
	}

	public boolean setCapsValue(String pCapsName, Object... pArg) {
		return setCapsValue(caps.get(pCapsName), pArg);
	}

	public Object getCapsValue(int pIndex, Object ...pArg) {
		return getCapsValue(null, pIndex, pArg);
	}
*///151delete

	@Override
	public Object getCapsValue(MMM_IModelCaps entityCaps, int pIndex, Object... pArg) {
		return null;
	}

	@Override
	public boolean setCapsValue(MMM_IModelCaps entityCaps, int pIndex, Object... pArg) {
		return false;
	}
}
