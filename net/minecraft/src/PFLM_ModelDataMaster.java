package net.minecraft.src;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

public class PFLM_ModelDataMaster
{
	public static PFLM_ModelDataMaster instance = new PFLM_ModelDataMaster();
	public ConcurrentHashMap<Entity, PFLM_ModelData> playerData = new ConcurrentHashMap();
	public boolean resetFlag = false;
	public boolean initResetFlag = false;
	public static final int skinMode_online										= 0;
	public static final int skinMode_local										= 1;
	public static final int skinMode_char										= 2;
	public static final int skinMode_offline										= 3;
	public static final int skinMode_Player										= 4;
	public static final int skinMode_OthersSettingOffline					= 5;
	public static final int skinMode_PlayerOffline							= 6;
	public static final int skinMode_PlayerOnline							= 7;
	public static final int skinMode_PlayerLocalData						= 8;
	public static final int skinMode_Random									= 9;
	public static final int skinMode_OthersIndividualSettingOffline	= 10;
	public Random rnd = new Random();
	public Date timeDate;
	public Object steveTexture;

	public PFLM_ModelDataMaster() {
		steveTexture = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ?
				Modchu_Reflect.newInstance("ResourceLocation", new Class[]{ String.class }, new Object[]{ "textures/entity/steve.png" }) :
					"/mob/char.png";
	}

	public PFLM_ModelData getPlayerData(Entity entity) {
    	if (entity != null) ;else return null;
    	PFLM_ModelData modelData = playerData.get(entity);
    	boolean b = false;
    	if (modelData != null) {
    		//Modchu_Debug.Debug("initFlag="+modelData.initFlag);
    		if (modelData.getCapsValueInt(modelData.caps_initFlag) != 2) b = true;
    	} else b = true;
    	if (b
    			| resetFlag) {
    		if (resetFlag) {
    			resetFlag = false;
    			clearPlayers();
    			modelData = null;
    		}
    		modelData = loadPlayerData(entity, modelData);
    		playerData.put(entity, modelData);
    	}
    	return modelData;
    }

	public PFLM_ModelData loadPlayerData(Entity entity)
	{
		PFLM_ModelData modelData = new PFLM_ModelData(entity instanceof EntityPlayer ? mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer : mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy);
		return loadPlayerData(entity, modelData);
	}

	public PFLM_ModelData loadPlayerData(Entity entity, PFLM_ModelData modelData)
	{
		if (entity != null) ;else return null;
		if (modelData != null) ;else modelData = new PFLM_ModelData(entity instanceof EntityPlayer ? mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer : mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy);
		if (entity instanceof EntityPlayer) ;else {
			modelData.modelMain.setEntityCaps(modelData);
			Modchu_Debug.lDebug("loadPlayerData !EntityPlayer return");
			return modelData;
		}
		EntityPlayer entityplayer = (EntityPlayer)entity;
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		if (thePlayer != null) ;else return null;
		Modchu_Reflect.setFieldObject(MMM_EntityCaps.class, "owner", modelData, entityplayer);
		modelData.modelMain.setEntityCaps(modelData);
		modelData.setCapsValue(modelData.caps_isPlayer, entityplayer.username == thePlayer.username);
//if (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)) Modchu_Debug.mDebug("@@@@@isPlayer false!!");
		// 125deleteif (!mod_PFLM_PlayerFormLittleMaid.pflm_main.gotchaNullCheck()) return null;

		Modchu_Debug.lDebug("loadPlayerData start --------");
		if (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
			String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.pflm_main.playerLocalData.get(entityplayer.username);
			PFLM_ModelData modelData2;
			if (t != null) {
				switch (Integer.valueOf(t[4])) {
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayer:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData Individual modePlayer");
					skinMode_PlayerSetting(entityplayer, modelData);
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modeOthersSettingOffline:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData Individual modeOthersSettingOffline");
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_OthersIndividualSettingOffline);
					String s2 = t[0];
					modelData.setCapsValue(modelData.caps_maidColor, Integer.valueOf(t[2]));
					setResourceLocation(modelData, entityplayer, 0, mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(s2, modelData.getCapsValueInt(modelData.caps_maidColor)));
					setResourceLocation(modelData, entity, 0, mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(s2, modelData.getCapsValueInt(modelData.caps_maidColor)));
					modelInit(entityplayer, modelData, s2);
					s2 = t[1];
					modelData.setCapsValue(modelData.caps_textureName, s2);
					modelData.setCapsValue(modelData.caps_textureArmorName, t[1]);
					modelArmorInit(entityplayer, modelData, s2);
					modelData.setCapsValue(modelData.caps_dominantArm, mod_Modchu_ModchuLib.modchu_Main.integerCheck(t[5]) ? othersPlayerIndividualHandednessSetting(Integer.valueOf(t[5])) : 0);
					modelData.setCapsValue(modelData.caps_modelScale, mod_Modchu_ModchuLib.modchu_Main.integerCheck(t[3]) ? Float.valueOf(t[3]) : 0.0F);
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayerOffline:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData Individual modePlayerOffline");
					skinMode_PlayerOfflineSetting(entityplayer, modelData);
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modePlayerOnline:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData Individual modePlayerOnline");
					skinMode_PlayerOnlineSetting(entityplayer, modelData);
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modeRandom:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData Individual modeRandom");
					skinMode_RandomSetting(entityplayer, modelData);
					modelData.setCapsValue(modelData.caps_dominantArm, mod_Modchu_ModchuLib.modchu_Main.integerCheck(t[5]) ? othersPlayerIndividualHandednessSetting(Integer.valueOf(t[5])) : 0);
					modelData.setCapsValue(modelData.caps_modelScale, mod_Modchu_ModchuLib.modchu_Main.integerCheck(t[3]) ? Float.valueOf(t[3]) : 0.0F);
					break;
				case PFLM_GuiOthersPlayerIndividualCustomize.modefalse:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData Individual modefalse");
					skinMode_FalseSetting(entityplayer, modelData);
					break;
				}
			} else
			if(PFLM_GuiOthersPlayer.changeMode > 0) {
				switch (PFLM_GuiOthersPlayer.changeMode) {
				case PFLM_GuiOthersPlayer.modePlayer:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData OthersSetting modePlayer");
					skinMode_PlayerSetting(entityplayer, modelData);
					break;
				case PFLM_GuiOthersPlayer.modeOthersSettingOffline:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData OthersSetting modeOthersSettingOffline");
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_OthersSettingOffline);
					String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName;
					modelData.setCapsValue(modelData.caps_maidColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor);
					modelInit(entityplayer, modelData, s);
					s = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName;
					modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName);
					modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName);
					modelArmorInit(entityplayer, modelData, s);
					modelData.setCapsValue(modelData.caps_dominantArm, othersPlayerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale);
					//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"modelData handedness="+modelData.getCapsValueInt(modelData.caps_dominantArm));
					break;
				case PFLM_GuiOthersPlayer.modePlayerOffline:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData OthersSetting modePlayerOffline");
					skinMode_PlayerOfflineSetting(entityplayer, modelData);
					break;
				case PFLM_GuiOthersPlayer.modePlayerOnline:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData OthersSetting modePlayerOnline");
					skinMode_PlayerOnlineSetting(entityplayer, modelData);
					break;
				case PFLM_GuiOthersPlayer.modeRandom:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData OthersSetting modeRandom");
					skinMode_RandomSetting(entityplayer, modelData);
					modelData.setCapsValue(modelData.caps_dominantArm, othersPlayerHandednessSetting());
					modelData.setCapsValue(modelData.caps_modelScale, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale);
					break;
				case PFLM_GuiOthersPlayer.modefalse:
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData OthersSetting modefalse");
					skinMode_FalseSetting(entityplayer, modelData);
					break;
				}
			}
			modelData.addSendList(4);
			int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
			if (skinMode != skinMode_PlayerOnline
					&& skinMode != skinMode_online
					&& !(skinMode == skinMode_Player
					&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline)) {
				modelData.setCapsValue(modelData.caps_initFlag, 2);
				Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData !Player skinMode != Online return");
				return checkModelData(modelData);
			}
		} else {
			modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
			modelData.addSendList(0);
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeRandom) {
				skinMode_RandomSetting(entityplayer, modelData);
				modelData.setCapsValue(modelData.caps_initFlag, 2);
				Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData Player Random setting return");
				return checkModelData(modelData);
			}
		}
		BufferedImage bufferedimage = getOnlineSkin(modelData, entityplayer);
		modelData.setCapsValue(modelData.caps_initFlag, 2);
		if (bufferedimage != null) {
			//Modchu_Debug.mlDebug(""+entityplayer.username+" : "+"OnlineMode bufferedimage != null ?"+(bufferedimage != null));
			textureBipedDefaultSetting(modelData);
			String userName = getUserName(modelData, entityplayer);
			if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline) {
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_online);
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"OnlineMode bufferedimage != null isPlayer skinMode_online set.");
				}
				if (!userName.startsWith("Player")
						&& !userName.startsWith("User_")
						&& modelData.getCapsValueInt(modelData.caps_initFlag) == 0
						&& !initResetFlag) {
					modelData.setCapsValue(modelData.caps_initFlag, 1);
					initResetFlag = true;
					resetFlag = true;
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData caps_initFlag return");
					return checkModelData(modelData);
				}
			}
		} else {
			Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData bufferedimage == null");
			if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData GuichangeMode="+PFLM_Gui.getChangeModeString(mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode));

			//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"er entityplayer.skinUrl = "+entityplayer.skinUrl);
			int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
			Modchu_Debug.lDebug(""+entityplayer.username+" : "+"skinMode="+getSkinModeString(skinMode));
			if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline) {
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_char);
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData isPlayer skinMode_char set");
				} else {
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData isPlayer skinMode_char no set");
				}
			} else {
				if (skinMode == skinMode_Player) {
					PFLM_ModelData modelData2 = getPlayerData(thePlayer);
					if (modelData2 != null) {
						if (modelData2.getCapsValueInt(modelData.caps_skinMode) == skinMode_char) {
							modelData.setCapsValue(modelData.caps_skinMode, skinMode_char);
							Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData !isPlayer skinMode_char set");
						}
					}
				}
				else if (skinMode == skinMode_PlayerOnline
						| skinMode == skinMode_online) {
					modelData.setCapsValue(modelData.caps_skinMode, skinMode_char);
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData !isPlayer skinMode_char set");
				} else {
					Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData !isPlayer skinMode_char no set");
				}
			}
			if (modelData.getCapsValueInt(modelData.caps_skinMode) == skinMode_char) {
				textureBipedDefaultSetting(modelData);
				modelInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureName));
				modelArmorInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureArmorName));
				modelData.setCapsValue(modelData.caps_isPlayer, entityplayer.username == thePlayer.username);
				return checkModelData(modelData);
			}
/*
			else {
				//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"er offline only set.");
				modelData.setCapsValue(modelData.caps_skinMode, skinMode_offline);
				modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName);
				modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName);
			}
*/
		}
		Modchu_Debug.lDebug(""+entityplayer.username+" : "+"loadPlayerData skinMode="+getSkinModeString(modelData.getCapsValueInt(modelData.caps_skinMode)));
		return checkSkin(entityplayer, bufferedimage, modelData);
	}

	public BufferedImage getOnlineSkin(PFLM_ModelData modelData, EntityPlayer entityplayer) {
		if (modelData != null) ;else return null;
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		BufferedImage bufferedimage = null;
		int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
		boolean isPlayer = modelData.getCapsValueBoolean(modelData.caps_isPlayer);
		if ((isPlayer
				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline)
				| (!isPlayer
				&& (skinMode == skinMode_online
					| skinMode == skinMode_PlayerOnline
					| (skinMode == skinMode_Player
						&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline)))) {
			Modchu_Debug.lDebug(""+entityplayer.username+" : "+"new model read username = "+entityplayer.username);
			String skinUrl = getSkinUrl(modelData, entityplayer);
			Modchu_Debug.mlDebug(""+entityplayer.username+" : "+"getOnlineSkin OnlineMode skinUrl="+skinUrl);
			try {
				URL url = new URL(skinUrl);
				bufferedimage = ImageIO.read(url);
				Modchu_Debug.mlDebug(""+entityplayer.username+" : "+"getOnlineSkin OnlineMode.image ok.");
			} catch (IOException ioexception) {
				StringBuilder s1 = (new StringBuilder()).append("Failed to read a player texture from a URL for ");
				if (skinUrl != null) {
					s1.append(skinUrl);
				} else {
					String userName = getUserName(modelData, entityplayer);
					s1.append("null entityplayer.userName=").append(userName);
				}
				Modchu_Debug.lDebug(s1.toString());
				Modchu_Debug.mlDebug("\n"+ioexception.getMessage());
			}
		} else {
			Modchu_Debug.lDebug(""+entityplayer.username+" : "+"getOnlineSkin else isPlayer="+isPlayer);
			Modchu_Debug.lDebug(""+entityplayer.username+" : "+"getOnlineSkin else skinMode="+getSkinModeString(skinMode));
		}
		return bufferedimage;
	}

	public String getSkinUrl(PFLM_ModelData modelData, EntityPlayer entityplayer) {
		int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
		if (skinMode == skinMode_online
				| skinMode == skinMode_PlayerOnline
						| (skinMode == skinMode_Player
						&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline)) ;else return null;
		Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		String userName = getUserName(modelData, entityplayer);
		return mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ? (String) Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110300_d", "getSkinUrl", new Class[]{ String.class },
				entityplayer, new Object[]{ userName })
				: (new StringBuilder()).append("http://skins.minecraft.net/MinecraftSkins/").append(userName).append(".png").toString();
	}

	public String getUserName(EntityPlayer entityplayer) {
		PFLM_ModelData modelData = getPlayerData(entityplayer);
		return getUserName(modelData, entityplayer);
	}

	public String getUserName(PFLM_ModelData modelData, EntityPlayer entityplayer) {
		int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		String userName = null;
		if (Modchu_Debug.debugPlayerName != null) {
			userName = modelData.getCapsValueBoolean(modelData.caps_isPlayer) ? Modchu_Debug.debugPlayerName :
				skinMode == skinMode_PlayerOnline
						| (skinMode == skinMode_Player
								&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline) ? Modchu_Debug.debugPlayerName : entityplayer.username;
		} else {
			userName = skinMode == skinMode_PlayerOnline
					| (skinMode == skinMode_Player
							&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline) ? thePlayer.username : entityplayer.username;
		}
		return userName;
	}

	public void modelInit(Entity entity, String s) {
		PFLM_ModelData modelData = getPlayerData(entity);
		modelInit(entity, modelData, s, false);
	}

	public void modelInit(Entity entity, PFLM_ModelData modelData, String s) {
		modelInit(entity, modelData, s, false);
	}

	public void modelInit(Entity entity, PFLM_ModelData modelData, String s, boolean debug) {
		if (modelData != null) ;else return;
		int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
		if (s == null
				| (s !=null
				&& s.isEmpty())
				| (mod_Modchu_ModchuLib.modchu_Main.checkTexturePackege(s, modelData.getCapsValueInt(modelData.caps_maidColor)) == null)
				&& (skinMode != skinMode_char
						&& skinMode != skinMode_online
						&& skinMode != skinMode_PlayerOnline
						&& skinMode != skinMode_Player)
					| !(entity instanceof EntityPlayer)) {
			s = textureNameDefaultSetting(modelData);
		}
		if (s == null
				| (s !=null
				&& s.isEmpty())) {
			throw new RuntimeException("PFLM_RenderPlayerMaster textureName null error !!");
		}
		Object[] models = mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(entity, s, false, true);
		if (debug) {
			Modchu_Debug.mlDebug("modelInit s="+s);
			Modchu_Debug.mlDebug("modelInit skinMode="+getSkinModeString(skinMode));
		}
		if (models != null
				&& models[0] instanceof MultiModelBaseBiped) {
			if (debug) Modchu_Debug.mlDebug("modelInit models[0] != null ? "+(models[0] != null));
		} else {
			if (debug) {
				if (models != null
						&& models[0] instanceof MultiModelBaseBiped) {
					Modchu_Debug.mlDebug("modelInit models = null !!");
				} else {
					Modchu_Debug.mlDebug("modelInit !MultiModelBaseBiped");
				}
			}
			if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)
					&& mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName.indexOf("_") > -1) mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName = "default";
			if (((String) modelData.getCapsValue(modelData.caps_textureName)).indexOf("_") > -1) modelData.setCapsValue(modelData.caps_textureName, "default");
			models = mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(entity, ((String) modelData.getCapsValue(modelData.caps_textureName)), false, true);
			if (models != null) ;else {
				if (debug) Modchu_Debug.mlDebug("modelInit 2 models = null !! textureName="+modelData.getCapsValue(modelData.caps_textureName));
				if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName = "default";
				modelData.setCapsValue(modelData.caps_textureName, "default");
				models = mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(entity, ((String) modelData.getCapsValue(modelData.caps_textureName)), false, true);
				if (models != null) ;else if (debug) Modchu_Debug.mlDebug("modelInit 3 models = null !! textureName="+modelData.getCapsValue(modelData.caps_textureName));
			}
		}
		modelData.modelMain.model = models != null && models[0] != null ? (MMM_ModelMultiBase) models[0] : new MultiModel(0.0F);
		modelData.modelMain.model.setCapsValue(modelData.caps_armorType, 0);
		modelTextureReset(entity, modelData, s);
		if (debug) {
			if (models != null
					&& models[0] != null) Modchu_Debug.mlDebug("modelInit models[0]="+models[0]);
			Modchu_Debug.mlDebug("modelInit s="+s);
			Modchu_Debug.mlDebug("modelInit textureName="+modelData.getCapsValue(modelData.caps_textureName));
			Modchu_Debug.mlDebug("modelInit color="+modelData.getCapsValueInt(modelData.caps_maidColor));
		}
	}

	public void modelArmorInit(Entity entity, String s) {
		PFLM_ModelData modelData = getPlayerData(entity);
		modelArmorInit(entity, modelData, s, false);
	}

	public void modelArmorInit(Entity entity, PFLM_ModelData modelData, String s) {
		modelArmorInit(entity, modelData, s, false);
	}

	public void modelArmorInit(Entity entity, PFLM_ModelData modelData, String s, boolean debug) {
		if (modelData != null) ;else return;
		int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
		boolean isBiped = modelData.modelMain != null
				&& modelData.modelMain.model != null ? mod_PFLM_PlayerFormLittleMaid.pflm_main.BipedClass.isInstance(modelData.modelMain.model) : false;
		if (!isBiped) s = mod_Modchu_ModchuLib.modchu_Main.textureNameCheck(s);
		if (s == null
				| (s !=null
				&& s.isEmpty())
				| (mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege(s) == null)
				&& (skinMode != skinMode_char
						&& skinMode != skinMode_online
						&& skinMode != skinMode_PlayerOnline
						&& skinMode != skinMode_Player)
					| !(entity instanceof EntityPlayer)) {
			//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"modelArmorInit s="+s+" modelData.getCapsValueInt(modelData.caps_skinMode)="+modelData.getCapsValueInt(modelData.caps_skinMode));
			s = textureArmorNameDefaultSetting(modelData);
		}
		if (!isBiped
				&& (s == null
				| (s !=null
				&& s.isEmpty()))) {
			throw new RuntimeException("PFLM_RenderPlayerMaster textureArmorName null error !!");
		}
		if (isBiped
				&& (s == null
						| (s !=null
						&& s.isEmpty())
						| s.equalsIgnoreCase("default")
						| s.equalsIgnoreCase("erasearmor"))) s = "_Biped";
		Object[] models = mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(entity, s, false, true);
		if (models != null) ;else {
			if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName = "default";
			modelData.setCapsValue(modelData.caps_textureArmorName, "default");
			models = mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(entity, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName, true, true);
		}
		float[] f1 = mod_Modchu_ModchuLib.modchu_Main.getArmorModelsSize(models[0]);
		if (debug) Modchu_Debug.mlDebug("modelArmorInit s="+s+" models[1] != null ? "+(models[1] != null));
		if (models != null
				&& models[1] != null) ;else {
					models = mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(isBiped ? "_Biped" : null, false);
					f1 = mod_Modchu_ModchuLib.modchu_Main.getArmorModelsSize(models[0]);
				}
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isSmartMoving) {
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
		//modelTextureArmorReset(modelData, s);
	}

	public String textureNameDefaultSetting(PFLM_ModelData modelData) {
		String s = mod_Modchu_ModchuLib.modchu_Main.textureNameCheck(null);
		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName = s;
		modelData.setCapsValue(modelData.caps_textureName, s);
		return s;
	}

	public String textureArmorNameDefaultSetting(PFLM_ModelData modelData) {
		String s = mod_Modchu_ModchuLib.modchu_Main.textureNameCheck(null);
		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName = s;
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
		return s;
	}

	public void textureBipedDefaultSetting(PFLM_ModelData modelData) {
		String s = "_Biped";
		//if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
			//mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName = s;
			//mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName = s;
		//}
		modelData.setCapsValue(modelData.caps_textureName, s);
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
		Modchu_Debug.lDebug("textureBipedDefaultSetting textureName ="+(String) modelData.getCapsValue(modelData.caps_textureName));
	}

	public void allModelTextureReset(Entity entity) {
		PFLM_ModelData modelData = getPlayerData(entity);
		allModelTextureReset(entity, modelData);
	}

	public void allModelTextureReset(Entity entity, PFLM_ModelData modelData) {
		modelTextureReset(entity, modelData);
		//modelTextureArmorReset(modelData);
	}

	public void modelTextureReset(Entity entity, PFLM_ModelData modelData) {
		modelTextureReset(entity, modelData, (String) modelData.getCapsValue(modelData.caps_textureName));
	}

	public void modelTextureReset(Entity entity, PFLM_ModelData modelData, String s) {
		Object o = null;
		int version = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion();
		int skinMode = modelData.getCapsValueInt(modelData.caps_skinMode);
		Class AbstractClientPlayer = Modchu_Reflect.loadClass("AbstractClientPlayer");
		if (AbstractClientPlayer.isInstance(entity)
				&& (version > 159
						&& modelData.getCapsValueBoolean(modelData.caps_isPlayer)
						&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline)
						&& skinMode != skinMode_local
						| (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)
						&& (skinMode == skinMode_online
								| skinMode == skinMode_PlayerOnline
								| (skinMode == skinMode_Player
								&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline)))) {
			EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
			String userName = getUserName(modelData, ((EntityPlayer)entity));
			o = Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110311_f", "getLocationSkin", new Class[]{ String.class }, skinMode == skinMode_PlayerOnline
					| skinMode == skinMode_Player ? thePlayer : entity, new Object[]{ userName });
			//o = ((AbstractClientPlayer)entity).func_110311_f(userName);
			Object image = Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110304_a", "getDownloadImageSkin", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation"), String.class }, skinMode == skinMode_PlayerOnline
					| skinMode == skinMode_Player ? thePlayer : entity, new Object[]{ o , userName });
			if (!modelData.getCapsValueBoolean(modelData.caps_isPlayer)
					&& (skinMode == skinMode_online
					| skinMode == skinMode_PlayerOnline
					| (skinMode == skinMode_Player
					&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOnline))) {
				//Modchu_Reflect.setFieldObject(AbstractClientPlayer, "field_110316_a", entity, image);
				o = Modchu_Reflect.invokeMethod(AbstractClientPlayer, "func_110306_p", "getLocationSkin", skinMode == skinMode_PlayerOnline
						| skinMode == skinMode_Player ? thePlayer : entity);
				//o = skinMode == skinMode_PlayerOnline
						//| skinMode == skinMode_Player ? ((AbstractClientPlayer)thePlayer).getLocationSkin() : ((AbstractClientPlayer)entity).getLocationSkin();
			}
			//((AbstractClientPlayer)entity).func_110304_a(o, userName);
			Modchu_Debug.lDebug(""+((EntityPlayer)entity).username+" : "+"modelTextureReset o="+o+" skinMode="+getSkinModeString(skinMode));
		} else {
			if (skinMode == skinMode_char) o = steveTexture;
			else {
				//ResourceLocation d = (ResourceLocation) mod_Modchu_ModchuLib.textureManagerGetTexture(s, modelData.getCapsValueInt(modelData.caps_maidColor));
				//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"modelTextureReset d != null ?"+(d != null));
				//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"modelTextureReset d="+d.func_110623_a());
				//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"modelTextureReset d="+d.func_110624_b());
				o = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(s, modelData.getCapsValueInt(modelData.caps_maidColor));
				//Modchu_Debug.lDebug("modelTextureReset else o="+o);
				//Modchu_Debug.lDebug("modelTextureReset else s="+s+" maidColor="+modelData.getCapsValueInt(modelData.caps_maidColor));
				//EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
				//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"modelTextureReset entity == thePlayer ?"+(entity == thePlayer));
			}
		}
		setResourceLocation(modelData, entity, 0, o);
	}

	public int playerHandednessSetting() {
		return mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode == -1 ? rnd.nextInt(2) : mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode;
	}

	public int othersPlayerHandednessSetting() {
		return mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode == -1 ? rnd.nextInt(2) : mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode;
	}

	public int othersPlayerIndividualHandednessSetting(int i) {
		return i == -1 ? rnd.nextInt(2) : i;
	}

	public PFLM_ModelData checkSkin(
			EntityPlayer entityplayer, BufferedImage bufferedimage
			,PFLM_ModelData modelData)
	{
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		modelData.setCapsValue(modelData.caps_isPlayer, entityplayer.username == thePlayer.username);
		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)
				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == PFLM_Gui.modeOffline
				| modelData.getCapsValueInt(modelData.caps_skinMode) == skinMode_offline) {
			//Modchu_Debug.lDebug(""+entityplayer.username+" : "+"checkSkin skinMode_offline");
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName.equals("_Biped")) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName =
						mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName = "default";
			}
			modelData.setCapsValue(modelData.caps_maidColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.maidColor);
			modelData.setCapsValue(modelData.caps_skinMode, skinMode_offline);
			modelData.setCapsValue(modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName);
			modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName);
			modelInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureName));
			modelArmorInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureArmorName));
			return checkModelData(modelData);
		}
		if (bufferedimage == null) {
			Modchu_Debug.lDebug(""+entityplayer.username+" : "+"checkSkin bufferedimage == null");
			//modelData.setCapsValue(modelData.caps_skinMode, skinMode_char);
			textureBipedDefaultSetting(modelData);
			modelInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureName));
			modelArmorInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureArmorName));
			return checkModelData(modelData);
		}
		modelData.setCapsValue(modelData.caps_isActivated, true);
		Object[] s = checkimage(bufferedimage);
		boolean localflag = (Boolean) s[0];
		boolean returnflag = (Boolean) s[5];
		if (!returnflag) modelData.setCapsValue(modelData.caps_textureArmorName, (String) s[2]);
		int maidcolor = (Integer) s[3];
		//Object texture = s[1];
		String textureName = (String) s[4];
		int handedness = (Integer) s[6];
		float modelScale = (Float) s[7];

		if (returnflag) {
			Modchu_Debug.lDebug(""+entityplayer.username+" : "+"returnflag");
			//modelData.setCapsValue(modelData.caps_skinMode, skinMode_online);
			//Modchu_Debug.lDebug(""+entityplayer.username+" : "+"returnflag textureArmorName ="+(String) modelData.getCapsValue(modelData.caps_textureArmorName));
			modelInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureName), true);
			modelArmorInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureArmorName));
			return checkModelData(modelData);
		}
		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
			//Modchu_Debug.mDebug(""+entityplayer.username+" : "+"modelData.isPlayer set textureName="+textureName);
			mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName = textureName;
			mod_PFLM_PlayerFormLittleMaid.pflm_main.maidColor = maidcolor;
			mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName = (String) modelData.getCapsValue(modelData.caps_textureArmorName);
		}
		modelData.setCapsValue(modelData.caps_textureName, textureName);
		modelData.setCapsValue(modelData.caps_maidColor, maidcolor);
		Modchu_Debug.lDebug(""+entityplayer.username+" : "+"checkSkin textureName = "+textureName);
		if(localflag) {
			modelData.setCapsValue(modelData.caps_localFlag, true);
			modelData.setCapsValue(modelData.caps_skinMode, skinMode_local);
			//if (texture != null) {
				Modchu_Debug.lDebug(""+entityplayer.username+" : "+"localflag maidcolor = "+maidcolor);
				//setResourceLocation(modelData, entityplayer, 0, texture);
				//Modchu_Debug.lDebug(""+entityplayer.username+" : "+"localflag texture = "+texture);
			//}
		} else {
			modelData.setCapsValue(modelData.caps_localFlag, false);
			setResourceLocation(modelData, entityplayer, 0, null);
		}

		if (textureName != null) modelInit(entityplayer, modelData, textureName);
		Modchu_Debug.lDebug(""+entityplayer.username+" : "+"checkSkin Armor = "+s[2]);
		if (modelData.getCapsValue(modelData.caps_textureArmorName) != null) modelArmorInit(entityplayer, modelData, (String) modelData.getCapsValue(modelData.caps_textureArmorName));
		Modchu_Debug.lDebug(""+entityplayer.username+" : "+"modelData.textureName = "+textureName);

		modelData.setCapsValue(modelData.caps_dominantArm, handedness);
		modelData.setCapsValue(modelData.modelMain.model, modelData.caps_dominantArm, handedness);
		Modchu_Debug.lDebug(""+entityplayer.username+" : "+"localflag handedness = "+handedness+" Random=-1 Right=0 Left=1");
		modelData.setCapsValue(modelData.caps_modelScale, modelScale);
		Modchu_Debug.lDebug(""+entityplayer.username+" : "+"localflag modelScale = "+modelScale);
		if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode = handedness;
			PFLM_Gui.modelScale = modelScale;
		}
		Modchu_Debug.lDebug(""+entityplayer.username+" : "+"checkSkin ok.return");
		return checkModelData(modelData);
    }

	public PFLM_ModelData checkModelData(PFLM_ModelData modelData) {
		//ぬるぽ及び問題が無いかのチェック
		Modchu_Debug.mlDebug("checkModelData textureName="+modelData.getCapsValue(modelData.caps_textureName));
		Modchu_Debug.mlDebug("checkModelData textureArmorName="+modelData.getCapsValue(modelData.caps_textureArmorName));
		Modchu_Debug.mlDebug("checkModelData maidColor="+modelData.getCapsValueInt(modelData.caps_maidColor));
		Modchu_Debug.mlDebug("checkModelData modelScale="+modelData.getCapsValueFloat(modelData.caps_modelScale));
		Modchu_Debug.mlDebug("checkModelData skinMode="+getSkinModeString(modelData.getCapsValueInt(modelData.caps_skinMode)));
		boolean err = false;
		ArrayList<String> list = new ArrayList();
		list.add("checkModelData Error Reporting.");
		if (modelData.modelMain == null) err = checkModelDataAddList(list, "checkModelData modelData.modelMain null !!");
		else if (modelData.modelMain.model == null) err = checkModelDataAddList(list, "checkModelData modelData.modelMain.model null !!");
		if (modelData.modelFATT == null) err = checkModelDataAddList(list, "checkModelData modelData.modelFATT null !!");
		else {
			if (modelData.modelFATT.modelInner == null) err = checkModelDataAddList(list, "checkModelData modelData.modelFATT.modelInner null !!");
			if (modelData.modelFATT.modelOuter == null) err = checkModelDataAddList(list, "checkModelData modelData.modelFATT.modelOuter null !!");
		}
		if (getResourceLocation(modelData, null, 0) == null) err = checkModelDataAddList(list, "checkModelData ResourceLocation, 0 null !!");

		if (err) {
			list.add("checkModelData error textureName="+modelData.getCapsValue(modelData.caps_textureName));
			list.add("checkModelData error textureArmorName="+modelData.getCapsValue(modelData.caps_textureArmorName));
			list.add("checkModelData error color="+modelData.getCapsValue(modelData.caps_maidColor));
			list.add("checkModelData error skinMode="+getSkinModeString(modelData.getCapsValueInt(modelData.caps_skinMode)));
			for(int i = 0; i < list.size(); i++) {
				Modchu_Debug.lDebug(list.get(i));
			}
		} else {
			Modchu_Debug.lDebug("checkModelData check ok.");
		}
		Modchu_Debug.lDebug("------PFLM_ModelData init end.------");
		return modelData;
	}

	public String getSkinModeString(int i) {
		String s = null;
		switch (i) {
		case 0:
			s = "skinMode_online";
			break;
		case 1:
			s = "skinMode_local";
			break;
		case 2:
			s = "skinMode_char";
			break;
		case 3:
			s = "skinMode_offline";
			break;
		case 4:
			s = "skinMode_Player";
			break;
		case 5:
			s = "skinMode_OthersSettingOffline";
			break;
		case 6:
			s = "skinMode_PlayerOffline";
			break;
		case 7:
			s = "skinMode_PlayerOnline";
			break;
		case 8:
			s = "skinMode_PlayerLocalDat";
			break;
		case 9:
			s = "skinMode_Random";
			break;
		case 10:
			s = "skinMode_OthersIndividualSettingOffline";
			break;
		}
		return s;
	}

	public boolean checkModelDataAddList(ArrayList list, String s) {
		list.add(s);
		return true;
	}

	public Object[] checkimage(BufferedImage bufferedimage) {
		Object[] object = new Object[8];
		// 0 localflag
		object[0] = false;
		// 1 Texture
		object[1] = null;
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
				Modchu_Debug.lDebug((new StringBuilder()).append("checkimage Out r255 g0 b0 a255.x=63,y=0 r=").append(r).append(" g=").append(g).append(" b=").append(b).append(" a=").append(a).toString());
				checkPointUnder = true;
				checkY = 31;
				c1 = checkImageColor(bufferedimage, checkX, checkY);
				r = c1[0];
				g = c1[1];
				b = c1[2];
				a = c1[3];
				if (r != 255 | g != 0 | b != 0 | a != 255) {
					Modchu_Debug.lDebug((new StringBuilder()).append("checkimage Out r255 g0 b0 a255.x=").append(checkX).append(",y=").append(checkY).append(" r=").append(r).append(" g=").append(g).append(" b=").append(b).append(" a=").append(a).toString());
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
					Modchu_Debug.lDebug((new StringBuilder()).append("checkimage Out r255 g0 b255 a255.x = 63,y = 1 r = ").append(r).append(" g = ").append(g).append(" b = ").append(b).append(" a = ").append(a).toString());
					object[5] = true;
					break;
				} else {
					Modchu_Debug.lDebug("checkimage localflag = true");
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
			if (g < mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size()) {
				object[2] = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(g);
				//Modchu_Debug.mDebug("object[2]="+object[2]);
			}
			object[3] = r;
			if (b < mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size()) {
				object[4] = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(b);
				object[1] = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(
						(String) mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(b), r);
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

	public int[] checkImageColor(BufferedImage bufferedimage, int i, int j)
	{
		Color color = new Color(bufferedimage.getRGB(i, j), true);
		int[] i1 = new int[4];
		i1[0] = color.getRed();
		i1[1] = color.getGreen();
		i1[2] = color.getBlue();
		i1[3] = color.getAlpha();
		return i1;
	}

    public void sitSleepResetCheck(PFLM_ModelData modelData, EntityPlayer entityplayer) {
    	if (!Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isSitting)
    			&& !Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isSleeping)) return;
    	float f1 = entityplayer.moveForward * entityplayer.moveForward + entityplayer.moveStrafing * entityplayer.moveStrafing;
    	float f2 = entityplayer.limbSwing - Modchu_ModelCapsHelper.getCapsValueFloat(modelData, modelData.caps_tempLimbSwing);
    	if (entityplayer.isJumping
    			| entityplayer.sleeping
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
    	if (entityplayer.isRiding()
    			&& (modelData.getCapsValueBoolean(modelData.caps_isSitting)
    					| modelData.getCapsValueBoolean(modelData.caps_isSleeping))) {
    		modelData.setCapsValue(modelData.caps_isSitting, false);
    		modelData.setCapsValue(modelData.caps_isSleeping, false);
    	}
    	modelData.setCapsValue(modelData.caps_tempLimbSwing, entityplayer.limbSwing);
    }

    public void clearPlayers() {
    	playerData.clear();
    }

    public void removePlayer(EntityPlayer entityPlayer) {
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
    		if(mod_PFLM_PlayerFormLittleMaid.pflm_main.waitTime == 0
    				&& Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isPlayer)) {
    			if(mod_PFLM_PlayerFormLittleMaid.pflm_main.isWait) {
    				if (((f != modelData.getCapsValueFloat(modelData.caps_isWaitF) && modelData.getCapsValueBoolean(modelData.caps_isWaitFSetFlag))
    						| onGrounds[i1] > 0)
    						| (modelData.getCapsValueBoolean(modelData.caps_isWait) && modelData.modelMain.model.isSneak)) {
    					modelData.setCapsValue(modelData.caps_isWait, false);
    					mod_PFLM_PlayerFormLittleMaid.pflm_main.isWait = false;
    					modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    					modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    					modelData.setCapsValue(modelData.caps_isWaitFSetFlag, true);
    				} else {
    					if ((f != modelData.getCapsValueFloat(modelData.caps_isWaitF)
    							| onGrounds[i1] > 0)
    							| (modelData.getCapsValueBoolean(modelData.caps_isWait) && modelData.modelMain.model.isSneak)) {
    						//Modchu_Debug.mDebug("f != isWaitF");
    						modelData.setCapsValue(modelData.caps_isWait, false);
    						mod_PFLM_PlayerFormLittleMaid.pflm_main.isWait = false;
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
    			int i = Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isPlayer) ? mod_PFLM_PlayerFormLittleMaid.pflm_main.waitTime : mod_PFLM_PlayerFormLittleMaid.pflm_main.othersPlayerWaitTime;
    			if (!modelData.getCapsValueBoolean(modelData.caps_isWait)
    					&& !modelData.modelMain.model.isSneak
    					&& i > 0) {
    				modelData.setCapsValue(modelData.caps_isWaitTime, modelData.getCapsValueInt(modelData.caps_isWaitTime) + 1);
    				if(modelData.getCapsValueInt(modelData.caps_isWaitTime) > i) {
    					//Modchu_Debug.Debug("isWaitTime > i");
    					modelData.setCapsValue(modelData.caps_isWait, true);
    					if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.pflm_main.isWait = true;
    					modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    					modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    				}
    			}
    			if ((f != modelData.getCapsValueFloat(modelData.caps_isWaitF)
    					| onGrounds[i1] > 0)
    					| (modelData.getCapsValueBoolean(modelData.caps_isWait) && modelData.modelMain.model.isSneak)) {
    				//Modchu_Debug.Debug("f != isWaitF");
    				modelData.setCapsValue(modelData.caps_isWait, false);
    				if (Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.pflm_main.isWait = false;
    				modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    				modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    			}
    		}
    	} else {
    		if (Modchu_ModelCapsHelper.getCapsValueBoolean(modelData, modelData.caps_isPlayer)) {
    			if (modelData.getCapsValueBoolean(modelData.caps_isWait)) {
    				//Modchu_Debug.Debug("firstPerson isWait false");
    				modelData.setCapsValue(modelData.caps_isWait, false);
    				if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) mod_PFLM_PlayerFormLittleMaid.pflm_main.isWait = false;
    				modelData.setCapsValue(modelData.caps_isWaitTime, 0);
    				modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    				if(mod_PFLM_PlayerFormLittleMaid.pflm_main.waitTime == 0) modelData.setCapsValue(modelData.caps_isWaitFSetFlag, true);
    			} else {
    				if(mod_PFLM_PlayerFormLittleMaid.pflm_main.waitTime == 0
    						&& !modelData.getCapsValueBoolean(modelData.caps_isWaitFSetFlag)) {
    					modelData.setCapsValue(modelData.caps_isWaitF, copyf(f));
    					modelData.setCapsValue(modelData.caps_isWaitFSetFlag, true);
    				}
    			}
    		}
    	}
    }

    public void skinMode_FalseSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
    	modelData.setCapsValue(modelData.caps_skinMode, skinMode_online);
    }

    public void skinMode_PlayerSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
    	EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
    	PFLM_ModelData modelData2 = getPlayerData(thePlayer);
    	if (modelData2 != null) {
    		modelData.setCapsValue(modelData.caps_skinMode, skinMode_Player);
    		modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
    		modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
    		modelData.setCapsValue(modelData.caps_textureName, modelData2.getCapsValue(modelData.caps_textureName));
    		modelData.setCapsValue(modelData.caps_textureArmorName, modelData2.getCapsValue(modelData.caps_textureArmorName));
    		modelData.setCapsValue(modelData.caps_maidColor, modelData2.getCapsValueInt(modelData.caps_maidColor));
    		if (modelData2.getCapsValueInt(modelData.caps_skinMode) == skinMode_char) {
    			textureBipedDefaultSetting(modelData);
    		}
    		modelInit(entityplayer, modelData, (String)modelData.getCapsValue(modelData.caps_textureName));
    		modelArmorInit(entityplayer, modelData, (String)modelData.getCapsValue(modelData.caps_textureArmorName));
    	}
    }

    public void skinMode_PlayerOnlineSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
    	modelData.setCapsValue(modelData.caps_skinMode, skinMode_PlayerOnline);
    	modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
    	modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
    	textureBipedDefaultSetting(modelData);
    }

    public void skinMode_PlayerOfflineSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
/*
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName == null
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName.isEmpty()) mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName = "_Biped";
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName == null
    			| mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName.isEmpty()) mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName = "_Biped";
*/
    	modelData.setCapsValue(modelData.caps_maidColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.maidColor);
    	//setResourceLocation(modelData, entityplayer, 0, mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName, modelData.getCapsValueInt(modelData.caps_maidColor)));
    	modelData.setCapsValue(modelData.caps_skinMode, skinMode_PlayerOffline);
    	modelData.setCapsValue(modelData.caps_dominantArm, playerHandednessSetting());
    	modelData.setCapsValue(modelData.caps_modelScale, PFLM_Gui.modelScale);
    	String s1 = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName;
    	modelInit(entityplayer, modelData, s1);
    	modelData.setCapsValue(modelData.caps_textureName, s1);
    	s1 = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName;
    	modelData.setCapsValue(modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName);
    	modelArmorInit(entityplayer, modelData, s1);
    }

    public void skinMode_RandomSetting(EntityPlayer entityplayer, PFLM_ModelData modelData) {
    	String s3 = null;
    	Object s4 = null;
    	int i = 0;
    	for(int i1 = 0; s4 == null && i1 < 50; i1++) {
    		i = rnd.nextInt(16);
    		int j = 0;
    		if (mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTexturesSize() > 0) {
    			j = rnd.nextInt(mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTexturesSize());
    			s3 = mod_Modchu_ModchuLib.modchu_Main.getPackege(i, j);
    		} else {
    			s3 = mod_Modchu_ModchuLib.modchu_Main.textureNameCheck(null);
    		}
    		modelData.setCapsValue(modelData.caps_maidColor, i);
    		s4 = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(s3, i);
    		if (i1 == 49) return;
    	}
    	Modchu_Debug.lDebug("Random modelPackege="+s3);
    	modelData.setCapsValue(modelData.caps_skinMode, skinMode_Random);
    	//setResourceLocation(modelData, entityplayer, 0, s4);
    	//Modchu_Debug.mDebug("Random s4="+s4);
    	String s1 = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName(s3);
    	modelData.setCapsValue(modelData.caps_textureName, s3);
    	modelData.setCapsValue(modelData.caps_textureArmorName, s1);
    	modelData.setCapsValue(modelData.caps_maidColor, i);
    	modelInit(entityplayer, modelData, s3);
    	modelArmorInit(entityplayer, modelData, s1);
    }

    public void setHandedness(EntityPlayer entityplayer, int i) {
    	PFLM_ModelData modelData = getPlayerData(entityplayer);
    	if (i == -1) i = rnd.nextInt(2);
    	modelData.setCapsValue(modelData.caps_dominantArm, i);
    	if (modelData.modelMain != null
    			&& modelData.modelMain.model != null) modelData.modelMain.model.setCapsValue(modelData.caps_dominantArm, i);
    	if (modelData.getCapsValueBoolean(modelData.caps_isPlayer)) {
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.setFlipHorizontal(i == 0 ? false : true);
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.setLeftHandedness(i == 0 ? false : true);
    	}
    }

    public float getActionSpeed(PFLM_ModelData modelData) {
/*
    	World theWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();
    	float f = (float)(theWorld.getWorldTime() - modelData.getCapsValueInt(modelData.caps_actionTime));
    	modelData.setCapsValue(modelData.caps_actionTime, (int) theWorld.getWorldTime());
*/
    	timeDate = new Date();
    	float f = (float)((int) timeDate.getTime() - modelData.getCapsValueInt(modelData.caps_actionTime)) / 40;
    	//Modchu_Debug.mDebug("getActionSpeed f="+f);
    	modelData.setCapsValue(modelData.caps_actionTime, (int) timeDate.getTime());
    	//Modchu_Debug.mDebug("getActionSpeed (int) timeDate.getTime()="+(int) timeDate.getTime()+" f="+f);
    	return f;
    }

    public boolean getResetFlag() {
    	return resetFlag;
    }

    public void setResetFlag(boolean b) {
    	resetFlag = b;
    }

    public float copyf(float f) {
    	return f;
    }

    public Object getResourceLocation(Entity entity) {
    	PFLM_ModelData modelData = getPlayerData(entity);
    	return getResourceLocation(modelData, entity, 0);
    }

    public Object getResourceLocation(PFLM_ModelData modelData, Entity entity) {
    	return getResourceLocation(modelData, entity, 0);
    }

    public Object getResourceLocation(Entity entity, int i) {
    	PFLM_ModelData modelData = getPlayerData(entity);
    	return getResourceLocation(modelData, entity, i);
    }

    public Object getResourceLocation(PFLM_ModelData modelData, Entity entity, int i) {
    	if (modelData != null) ;else return null;
    	if (modelData.getCapsValue(modelData.caps_ResourceLocation) != null) ;else {
    		modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) Modchu_Reflect.newInstanceArray("ResourceLocation", 3));
    	}
    	return modelData.getCapsValue(modelData.caps_ResourceLocation, i);
    }

    public void setResourceLocation(Entity entity, Object resourceLocation) {
    	PFLM_ModelData modelData = getPlayerData(entity);
    	setResourceLocation(modelData, entity, 0, resourceLocation);
    }

    public void setResourceLocation(PFLM_ModelData modelData, Entity entity, Object resourceLocation) {
    	setResourceLocation(modelData, entity, 0, resourceLocation);
    }

    public void setResourceLocation(Entity entity, int i, Object resourceLocation) {
    	PFLM_ModelData modelData = getPlayerData(entity);
    	setResourceLocation(modelData, entity, i, resourceLocation);
    }

    public void setResourceLocation(PFLM_ModelData modelData, Entity entity, int i, Object resourceLocation) {
    	if (modelData.getCapsValue(modelData.caps_ResourceLocation) != null) ;else {
    		modelData.setCapsValue(modelData.caps_ResourceLocation, (Object) Modchu_Reflect.newInstanceArray("ResourceLocation", 3));
    	}
    	modelData.setCapsValue(modelData.caps_ResourceLocation, i, resourceLocation);
    }

}