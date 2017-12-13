package modchu.pflm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.lwjgl.input.Mouse;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_CastHelper;
import modchu.lib.Modchu_Config;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_FileManager;
import modchu.lib.Modchu_GlStateManager;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_RenderSupport;
import modchu.model.ModchuModel_Config;
import modchu.model.ModchuModel_ConfigData;
import modchu.model.ModchuModel_EntityPlayerDummyMaster;
import modchu.model.ModchuModel_GuiCustomModel;
import modchu.model.ModchuModel_GuiModelSelectMaster;
import modchu.model.ModchuModel_GuiModelViewMaster;
import modchu.model.ModchuModel_IEntityCaps;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_ModelDataBase;
import modchu.model.ModchuModel_ModelDataMaster;
import modchu.model.ModchuModel_ModelRenderer;
import modchu.model.ModchuModel_RenderMasterBase;
import modchu.model.ModchuModel_TextureManagerBase;

public class PFLM_GuiMaster extends ModchuModel_GuiModelViewMaster {

	public Object drawMuitiEntity;
	private BufferedImage bufferedimage;
	private BufferedImage bufferedimage1;
	private String textureName;
	private String textureArmorName;
	private String tagSetFileName;
	private String errorModelString;
	private double tempYOffset;
	private boolean imageWriteComplete;
	private boolean imageWriteFail;
	private boolean bufferedimageMode;
	private boolean tempYOffsetInit;
	private int color;
	private int scrollY;
	private int armorType;
	private int showPartsListSize;
	private float modelScale;
	public boolean closePlayerToSpawn;
	public boolean result;
	public ConcurrentHashMap<String, Boolean> parts;
	public ConcurrentHashMap<String, Boolean> defaultParts;
	public ConcurrentHashMap<Integer, String> guiPartsNemeMap;
	public boolean modelScaleButton;
	public boolean colorReverse;
	public boolean partsButton;
	public boolean partsInitFlag;
	public boolean partsSetDefault;
	public boolean drawMuitiEntitySetFlag;
	public boolean buttonCustomize;
	public boolean buttonMultiPngSave;
	public boolean buttonKeyControls;
	public boolean buttonCustomModel;
	public boolean buttonOtherPlayer;
	public boolean partsSaveFlag;
	public boolean noSaveFlag;
	private static boolean initNewReleaseDrow;
	public int setModel = 0;
	public int setArmor = 0;
	public int setColor = 0;

	public PFLM_GuiMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void init(HashMap<String, Object> map) {
		super.init(map);
		guiMasterInit();
		guiMasterInitAfter();
	}

	protected void guiMasterInitAfter() {
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
		modelData.setCapsValue(modelData.caps_actionRequest, new boolean[]{ true, false });
		setTextureName(Modchu_CastHelper.String(modelData.getCapsValue(modelData.caps_textureName)));
		setTextureArmorName(Modchu_CastHelper.String(modelData.getCapsValue(modelData.caps_textureArmorName)));
		setColor(Modchu_CastHelper.Int(modelData.getCapsValue(modelData.caps_maidColor)));
		setScale(PFLM_ConfigData.modelScale);
	}

	@Override
	public void reInit() {
		super.reInit();
		guiMasterInit();
	}

	private void guiMasterInit() {
		tagSetFileName = null;
		imageWriteComplete = false;
		imageWriteFail = false;
		bufferedimageMode = false;
		tempYOffsetInit = false;
		scrollY = 0;
		armorType = 0;
		showPartsListSize = 0;
		closePlayerToSpawn = false;
		result = false;
		modelScaleButton = false;
		colorReverse = false;
		partsButton = false;
		partsInitFlag = false;
		partsSaveFlag = false;
		partsSetDefault = false;
		noSaveFlag = false;
		buttonCustomize = true;
		buttonMultiPngSave = true;
		buttonKeyControls = true;
		buttonCustomModel = true;
		buttonOtherPlayer = true;
		PFLM_GuiConstant.partsSetFlag = 0;
		setModel = PFLM_ConfigData.setModel;
		setArmor = PFLM_ConfigData.setArmor;
		setColor = PFLM_ConfigData.setColor;
		useScaleChange = PFLM_ConfigData.useScaleChange;
		if (parts != null) {
			parts.clear();
		} else {
			parts = new ConcurrentHashMap();
		}
		int changeMode = getChangeMode();
		if (changeMode > PFLM_GuiConstant.changeModelist.size()) setChangeMode(0);
		if (changeMode < 0) setChangeMode(PFLM_GuiConstant.changeModelist.size() - 1);
		ModchuModel_ModelDataBase modelData = ModchuModel_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", PFLM_ConfigData.showArmor);
		setDrawMuitiTextureValue();
	}

	@Override
	public void initDrawEntity() {
		ModchuModel_ModelDataBase modelData = null;
		try {
			if (drawEntity != null); else drawEntity = Modchu_Main.newModchuCharacteristicObject("Modchu_EntityPlayerDummy", ModchuModel_EntityPlayerDummyMaster.class, popWorld);
			if (drawEntity != null) {
				modelData = PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
				ModchuModel_ModelDataBase thePlayerModelData = PFLM_ModelDataMaster.instance.getPlayerData(Modchu_AS.get(Modchu_AS.minecraftPlayer));
				if (thePlayerModelData != null) {
					modelData.setCapsValue(modelData.caps_skinMode, thePlayerModelData.getCapsValue(modelData.caps_skinMode));
					super.initDrawEntity();
					if (drawMuitiEntity != null); else drawMuitiEntity = Modchu_Main.newModchuCharacteristicObject("Modchu_EntityPlayerDummy", ModchuModel_EntityPlayerDummyMaster.class, popWorld);
					ModchuModel_ModelDataBase drawMuitiModelData = PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
					if (drawMuitiModelData != null) {
						drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "showMainModel", true);
						drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "initDrawEntityFlag", true);
					} else {
						Modchu_Debug.lDebug("PFLM_GuiMaster drawMuitiModelData == null error !! ");
					}
				} else {
					Modchu_Debug.lDebug("PFLM_GuiMaster thePlayerModelData == null error !! ");
				}
			} else {
				Modchu_Debug.lDebug("PFLM_GuiMaster drawEntity == null error !! ");
			}
		} catch(Error e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initGui() {
		Modchu_Debug.mDebug("PFLM_GuiMaster initGui");
		initButtonSetting();
		super.initGui();
		if (!displayButton) return;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);
		showPartsListSize = 0;
		if (buttonKeyControls) buttonList.add(newInstanceButton(57, x + 75, y + 70, 75, 15, "KeyControls"));
		if (buttonCustomModel
				&& !Modchu_Main.isRelease) buttonList.add(newInstanceButton(59, x + 75, y + 40, 75, 15, "CustomModel"));
		int skinMode = getSkinMode();
		if (skinMode != ModchuModel_IEntityCaps.skinMode_Random) {
			if (buttonMultiPngSave
					&& PFLM_ConfigData.guiMultiPngSaveButton
					&& !partsButton
					&& !modelScaleButton) {
				buttonList.add(newInstanceButton(12, x + 75, y + 100, 80, 20, "MultiPngSave"));
				bufferedimageMode = false;
				PFLM_ModelData drawModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
				if (drawModelData.models != null
						&& ModchuModel_Main.bipedCheck(drawModelData.models[0])
						| skinMode == ModchuModel_IEntityCaps.skinMode_online) {
					buttonList.add(newInstanceButton(15, 50, y + 55, 15, 15, "<"));
					buttonList.add(newInstanceButton(14, 65, y + 55, 15, 15, ">"));
					buttonList.add(newInstanceButton(17, 50, y + 72, 15, 15, "-"));
					buttonList.add(newInstanceButton(16, 65, y + 72, 15, 15, "+"));
					buttonList.add(newInstanceButton(19, 50, y + 89, 15, 15, "<"));
					buttonList.add(newInstanceButton(18, 65, y + 89, 15, 15, ">"));
					PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
					PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
					buttonList.add(newInstanceButton(21, 70, y + 107, 30, 15, "" + drawMuitiModelData.getCapsValueBoolean(drawMuitiModelData.caps_freeVariable, "showArmor")));
					if (skinMode == ModchuModel_IEntityCaps.skinMode_online
							&& (bufferedimage == null
							| drawEntitySetFlag)) {
						Modchu_Debug.mDebug("PFLM_GuiMaster initGui() skinMode_online");
						//bufferedimage = PFLM_ModelDataMaster.instance.getOnlineSkin(modelData, thePlayer);
						Object o = modelData.getCapsValue(modelData.caps_freeVariable, "bufferedimage");
						bufferedimage = o != null ? (BufferedImage) o : null;
						drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "skinChar", false);
						drawModelData.setCapsValue(drawModelData.caps_freeVariable, "skinChar", false);
						Modchu_Debug.mDebug("PFLM_GuiMaster bufferedimage drawMuitiModelData set skinChar false");
						modelData.setCapsValue(modelData.caps_skinMode, ModchuModel_IEntityCaps.skinMode_online);
						if (bufferedimage != null) {
							Object[] s = ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).checkimage(bufferedimage);
							boolean returnflag = (Boolean) s[5];
							if (!returnflag) {
								setTextureName((String) s[4]);
								setTextureArmorName((String) s[2]);
								setColor((Integer) s[3]);
								int s2 = PFLM_Main.textureList.indexOf(getTextureName());
								setModel = s2 > -1 ? s2 : 0;
								s2 = PFLM_Main.textureList.indexOf(getTextureArmorName());
								setArmor = s2 > -1 ? s2 : 0;
								Modchu_Debug.mDebug("!returnflag");
							}
							setcheck();
							drawModelData.setCapsValue(drawModelData.caps_localFlag, false);
							((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).setResourceLocation(drawModelData, 0, bufferedimage);
							drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureName, PFLM_Main.textureList.get(setModel));
							drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, PFLM_Main.textureList.get(setArmor));
							Modchu_Debug.mDebug("PFLM_GuiMaster bufferedimage ok.drawMuitiModelData set end.");
						} else {
							drawModelData.setCapsValue(drawModelData.caps_freeVariable, "bufferedimage", null);
							drawModelData.setCapsValue(drawModelData.caps_freeVariable, "skinChar", true);
							Modchu_Debug.mDebug("PFLM_GuiMaster bufferedimage null.drawMuitiModelData set skinChar true");
							Modchu_Debug.mDebug("PFLM_GuiMaster bufferedimage null.drawMuitiModelData set end.");
						}
						//PFLM_Main.removeDataMap();
						textureResetFlag = true;
					} else {
						bufferedimageMode = skinMode == ModchuModel_IEntityCaps.skinMode_online ? true : false;
					}
					setcheck();
				}
			}
		}
		if (PFLM_ConfigData.useScaleChange
				&& modelScaleButton) {
		} else {
			if (!partsButton) {
				if (PFLM_Main.isMulti) {
					if (buttonOtherPlayer) buttonList.add(newInstanceButton(30, x + 75, y - 5, 75, 15, "othersPlayer"));
				}
			}
		}
		if (skinMode != ModchuModel_IEntityCaps.skinMode_Random) {
			if (partsButton) {
				buttonList.add(newInstanceButton(10, x + 75, y + 10, 75, 15, "Close"));
				buttonList = partsButtonAdd(buttonList);
			} else {
				if (buttonCustomize
						&& !modelScaleButton) buttonList.add(newInstanceButton(11, x + 75, y + 10, 75, 15, "Customize"));
			}
		}
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
		Modchu_Debug.mDebug("PFLM_GuiMaster initGui end. buttonList="+buttonList);
	}

	protected void initButtonSetting() {
		int skinMode = getSkinMode();
		buttonOnline = skinMode == ModchuModel_IEntityCaps.skinMode_online;
		buttonOffline = skinMode == ModchuModel_IEntityCaps.skinMode_offline;
		buttonRandom = skinMode == ModchuModel_IEntityCaps.skinMode_Random;
		buttonScale = modelScaleButton;
		buttonParts = partsButton;
		buttonPlayer = false;
		buttonShowArmor = skinMode != ModchuModel_IEntityCaps.skinMode_Random;
		buttonCustomize = true;
		buttonMultiPngSave = true;
		buttonCustomModel = true;
		buttonKeyControls = true;
	}

	public void setTempYOffset(double d) {
		tempYOffset = d;
	}

	public List partsButtonAdd(List buttonList) {
		int j = 0;
		String s;
		int x;
		int y;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		//Modchu_Debug.mDebug("PartsButtonAdd "+buttonList.size());
		buttonList.add(newInstanceButton(99, width / 2 - 200, 12 - scrollY, 70, 15, "Default"));
		if (PFLM_Main.guiShowArmorSupport) {
			buttonList.add(newInstanceButton(97, width / 2 - 130, 12 - scrollY, 15, 15, "-"));
			buttonList.add(newInstanceButton(98, width / 2 - 115, 12 - scrollY, 15, 15, "+"));
		}
		String s2 = null;
		boolean b = true;
		guiPartsNemeMap = new ConcurrentHashMap();
		defaultParts = new ConcurrentHashMap();
		Object model = ModchuModel_Main.getModel(null, armorType);
		ConcurrentHashMap<Integer, String> showPartsNemeMap = ModchuModel_Config.getConfigShowPartsNemeMap(getTextureName(), armorType);
		ConcurrentHashMap<String, String> showPartsReneme = ModchuModel_Config.getConfigShowPartsRenemeMap(model, getTextureName(), armorType);
		ConcurrentHashMap<Integer, String> showPartsHideMap = ModchuModel_Config.getConfigShowPartsHideMap(model, getTextureName(), armorType);
		//if (showPartsNemeMap != null) Modchu_Debug.mDebug("showPartsNemeMap.size()="+showPartsNemeMap.size());
		//else Modchu_Debug.mDebug("showPartsNemeMap.size()=null !!");
		//if (showPartsReneme != null) Modchu_Debug.mDebug("showPartsReneme.size()="+showPartsReneme.size());
		//else Modchu_Debug.mDebug("showPartsReneme.size()=null !!");
		//if (showPartsHideMap != null) Modchu_Debug.mDebug("showPartsHideMap.size()="+showPartsHideMap.size());
		//else Modchu_Debug.mDebug("showPartsHideMap.size()=null !!");
		List<String> list = new ArrayList();
		ModchuModel_ModelRenderer modelRenderer = null;
		Field f = null;
		int i1 = 0;
		for (int i = 0; i < showPartsNemeMap.size(); i++) {
			s2 = showPartsNemeMap.get(i);
			if (s2 != null) ;
			else break;
			if (list.contains(s2)) continue;
			list.add(s2);
			if (!partsSetDefault
					&& parts != null
					&& !parts.isEmpty()
					&& parts.containsKey(s2)) {
				b = parts.get(s2);
			} else {
				if (partsSetDefault) b = ModchuModel_Config.getDefaultShowPartsMapBoolean(getTextureName(), s2, armorType);
				else b = true;
			}
			s = b ? "ON" : "OFF";
			j = i1 % 2 == 0 ? 0 : 1;
			x = width / 2 - 200 + (j * 70);
			y = j == 0 ? 28 + (15 * i1 / 2) - scrollY : 36 + (15 * i1 / 2 - 15) - scrollY;
			if (showPartsHideMap != null && showPartsHideMap.containsValue(s2)) {
				//Modchu_Debug.mDebug("showPartsHideMap s2="+s2);
			} else {
				guiPartsNemeMap.put(i1, s2);
				defaultParts.put(s2, true);
				if (showPartsReneme != null && s2 != null && showPartsReneme.containsKey(s2)) {
					s2 = showPartsReneme.get(s2);
				}
				buttonList.add(newInstanceButton(10000 + i1, x, y, 70, 15, s2 + ":" + s));
				i1++;
			}
		}
		showPartsListSize = i1 / 2;
		if (showPartsListSize < 0) showPartsListSize = 0;
		return buttonList;
	}

	private void partsInit() {
		//Modchu_Debug.mDebug("PFLM_Gui partsInit() getTextureName()="+getTextureName());
		parts = new ConcurrentHashMap();
		String s2 = null;
		boolean b;
		Field f = null;
		List<String> list = new ArrayList();
		ModchuModel_ModelRenderer modelRenderer = null;
		Object model = ModchuModel_Main.getModel(null, armorType);
		ConcurrentHashMap<Integer, String> showPartsNemeMap = ModchuModel_Config.getConfigShowPartsNemeMap(getTextureName(), armorType);
		if (showPartsNemeMap != null
				&& !showPartsNemeMap.isEmpty()); else return;
		ConcurrentHashMap<String, Boolean> showPartsList = ModchuModel_Config.getConfigShowPartsMap(getTextureName(), getColor(), armorType);
		ConcurrentHashMap<String, Field> modelRendererMap = ModchuModel_Config.getConfigModelRendererMap(model, getTextureName(), armorType);
		ConcurrentHashMap<String, Boolean> defaultShowPartsMap = ModchuModel_Config.getDefaultShowPartsMap(getTextureName(), armorType);
		//if (showPartsList != null) Modchu_Debug.mDebug("showPartsList.size()="+showPartsList.size());
		//else Modchu_Debug.mDebug("showPartsList.size()=null !!");
		//Modchu_Debug.mDebug("showPartsNemeMap.size()="+showPartsNemeMap.size());
		for (int i = 1; i < showPartsNemeMap.size(); i++) {
			s2 = showPartsNemeMap.get(i - 1);
			if (s2 != null) ;
			else break;
			if (list.contains(s2)) continue;
			list.add(s2);
			if (showPartsList != null && !showPartsList.isEmpty() && showPartsList.containsKey(s2)) {
				b = showPartsList.get(s2);
			} else {
				b = true;
				if (modelRendererMap != null && !modelRendererMap.isEmpty()) ;
				else break;
				if (modelRendererMap.containsKey(s2)) f = modelRendererMap.get(s2);
				else f = null;
				if (f != null) {
					//Modchu_Debug.mDebug("PFLM_Gui partsButtonAdd() modelRendererMap f != null");
					try {
						Object o = f.get(model);
						modelRenderer = o != null ? (ModchuModel_ModelRenderer) o : null;
						if (modelRenderer != null) {
							//Modchu_Debug.mmlDebug("PFLM_Gui partsInit() modelRendererMap f.getName()="+f.getName()+" modelRenderer.showModel="+modelRenderer.showModel);
							if (defaultShowPartsMap != null && defaultShowPartsMap.containsKey(s2)) b = defaultShowPartsMap.get(s2);
							else b = modelRenderer.showModel;
							//Modchu_Debug.mmlDebug("PFLM_Gui partsInit() modelRendererMap f.getName()="+f.getName()+" b="+b);
						}
					} catch (Exception e) {
					}
				}
			}
			parts.put(s2, b);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		if (!drawMuitiModelData.getCapsValueBoolean(drawMuitiModelData.caps_freeVariable, "initDrawEntityFlag")) initDrawEntity();
		if (PFLM_GuiConstant.partsSetFlag == 2) {
			PFLM_GuiConstant.partsSetFlag = 3;
			//if (!partsSetDefault) PFLM_Config.loadShowModelList(PFLM_Main.showModelList);
			//Modchu_Debug.mDebug("updateScreen()");
			if (!partsInitFlag) {
				partsInitFlag = true;
				partsInit();
				PFLM_GuiConstant.partsSetFlag = 1;
			}
			initGui();
		}
	}

	public void actionPerformed(Object guibutton) {
		Modchu_Debug.mDebug("actionPerformed");
		if (!Modchu_AS.getBoolean(Modchu_AS.guiButtonEnabled, guibutton)) {
			return;
		}
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		//isModelSize Default
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);
		Modchu_Debug.mDebug("id="+id);
		if (id == 3) {
			setScale(ModchuModel_Main.getModelScale(thePlayer));
			setTextureValue();
			drawEntitySetFlag = true;
			return;
		}
		boolean isShiftKeyDown = Modchu_AS.getBoolean(Modchu_AS.isShiftKeyDown);
		boolean isCtrlKeyDown = Modchu_AS.getBoolean(Modchu_AS.isCtrlKeyDown);
		//isModelSize UP
		if (id == 4) {
			if (isShiftKeyDown) {
				setScale(getScale() <= 9.5 ? getScale() + 0.5F : 10.0F);
			} else {
				if (isCtrlKeyDown) {
					setScale(getScale() <= 9.99 ? getScale() + 0.01F : 10.0F);
				} else {
					setScale(getScale() <= 9.9 ? getScale() + 0.1F : 10.0F);
				}
			}
			setTextureValue();
			return;
		}
		//isModelSize Down
		if (id == 5) {
			if (isShiftKeyDown) {
				setScale(getScale() > 0.5 ? getScale() - 0.5F : 0.01F);
			} else if (isCtrlKeyDown) {
				setScale(getScale() > 0.01 ? getScale() - 0.01F : 0.01F);
			} else {
				setScale(getScale() > 0.1 ? getScale() - 0.1F : 0.01F);
			}
			setTextureValue();
			return;
		}
		//ScaleChange Close
		if (id == 6) {
			modelScaleButton = false;
			initGui();
			return;
		}
		//ScaleChange Open
		if (id == 7) {
			modelScaleButton = true;
			initGui();
			return;
		}
		//Customize Close
		if (id == 10) {
			partsButton = false;
			initGui();
			return;
		}
		//CustomizeOpen
		if (id == 11) {
			partsButton = true;
			initGui();
			return;
		}
		//MultiPngSave
		if (id == 12) {
			imageMultiTagSetSave(getColor());
			//for(int i = 0 ; i < 16 ; i++) {
			//imageMultiTagSetSave(i);
			//}
			return;
		}
		//SkinModeChange
		if (id == 13) {
			int changeMode = getChangeMode();
			if (isShiftKeyDown) {
				changeMode--;
			} else {
				changeMode++;
			}
			if (changeMode >= PFLM_GuiConstant.changeModelist.size()) {
				changeMode = 0;
			}
			if (changeMode < 0) changeMode = PFLM_GuiConstant.changeModelist.size() - 1;
			setChangeMode(changeMode);
			if (!PFLM_ConfigData.changeModeButton
					&& changeMode == ModchuModel_IEntityCaps.skinMode_online) {
				changeMode++;
				setChangeMode(changeMode);
				if (changeMode >= PFLM_GuiConstant.changeModelist.size()) {
					changeMode = 0;
					setChangeMode(changeMode);
				}
			}
			if (changeMode == ModchuModel_IEntityCaps.skinMode_online) {
				setModel--;
				id = 14;
			}
			if (changeMode == ModchuModel_IEntityCaps.skinMode_offline
					| changeMode == ModchuModel_IEntityCaps.skinMode_Random) {
				//PFLM_Main.removeDataMap();
				PFLM_Main.changeModel(((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).getPlayerData(thePlayer));
				PFLM_Main.changeModel(((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).getPlayerData(drawEntity));
				PFLM_Main.changeModel(((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).getPlayerData(drawMuitiEntity));
			}
			bufferedimage = null;
			PFLM_GuiConstant.partsSetFlag = 1;
			noSaveFlag = true;
			initGui();
			setTextureValue();
			return;
		}
		//guiMultiPngSaveButton setModel+
		if (id == 14) {
			setModel++;
			boolean b = false;
			while (b == false && setModel < PFLM_Main.textureList.size()) {
				drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureName, PFLM_Main.textureList.get(setModel));
				Object ltb = ModchuModel_TextureManagerBase.instance.checkTexturePackege((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName), drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor));
				if (ltb != null) {
					b = true;
				} else {
					setModel++;
				}
			}
		}
		//guiMultiPngSaveButton setModel-
		if (id == 15) {
			setModel--;
			boolean b = false;
			while (b == false && setModel > -1) {
				drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureName, PFLM_Main.textureList.get(setModel));
				Object ltb = ModchuModel_TextureManagerBase.instance.checkTexturePackege((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName), drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor));
				if (ltb != null) {
					b = true;
				} else {
					setModel--;
				}
			}
		}
		if (id == 14 | id == 15) {
			imageWriteComplete = false;
			imageWriteFail = false;
			setDrawMuitiTextureValue();
			setTextureArmorPackege(1, id == 15);
			initGui();
			return;
		}
		if (id == 16 | id == 17) {
			//guiMultiPngSaveButton setColor+
			int color = 0;
			if (id == 16) {
				color = drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor) + 1;
				colorReverse = false;
			}
			//guiMultiPngSaveButton setColor-
			if (id == 17) {
				color = drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor) - 1;
				colorReverse = true;
			}
			color = Modchu_Main.normalize(color, 0, 15, 15, 0);
			imageWriteComplete = false;
			imageWriteFail = false;
			Modchu_Debug.mDebug("guiMultiPngSaveButton color="+color);
			drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_maidColor, colorCheck((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName), color, colorReverse));
			setColor = drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor);
			Modchu_Debug.mDebug("guiMultiPngSaveButton 2 setColor="+setColor);
			drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_changeColor, drawMuitiModelData);
			setDrawMuitiTextureValue();
			initGui();
			return;
		}
		//guiMultiPngSaveButton setArmor+
		if (id == 18) {
			setArmor++;
			setcheck();
			boolean b = false;
			String textureArmorName = null;
			while (b == false && setArmor < PFLM_Main.textureList.size()) {
				textureArmorName = PFLM_Main.textureList.get(setArmor);
				drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, textureArmorName);
				Object ltb = ModchuModel_TextureManagerBase.instance.checkTextureArmorPackege((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureArmorName));
				if (ltb != null) {
					b = true;
				} else {
					setArmor++;
				}
			}
		}
		//guiMultiPngSaveButton setArmor-
		if (id == 19) {
			setArmor--;
			setcheck();
			boolean b = false;
			while (b == false && setArmor < PFLM_Main.textureList.size()) {
				drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, PFLM_Main.textureList.get(setArmor));
				Object ltb = ModchuModel_TextureManagerBase.instance.checkTextureArmorPackege((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureArmorName));
				if (ltb != null) {
					b = true;
				} else {
					setArmor--;
				}
			}
		}
		if (id == 18 | id == 19) {
			setcheck();
			drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, PFLM_Main.textureList.get(setArmor));
			imageWriteComplete = false;
			imageWriteFail = false;
			setDrawMuitiTextureValue();
			initGui();
			return;
		}
		//ShowArmor
		if(id == 20)
		{
			PFLM_ConfigData.showArmor = !PFLM_ConfigData.showArmor;
			Modchu_Debug.mDebug("actionPerformed showArmor="+PFLM_ConfigData.showArmor);
			drawEntityModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "showArmor", PFLM_ConfigData.showArmor);
			setTextureValue();
			initGui();
			return;
		}
		//guiMultiPngSaveButton ShowArmor
		if(id == 21)
		{
			drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "showArmor", !drawMuitiModelData.getCapsValueBoolean(drawMuitiModelData.caps_freeVariable, "showArmor"));
			drawMuitiEntitySetFlag = true;
			initDrawStringListFlag = true;
			initGui();
			return;
		}
		//OthersPlayer
		if (id == 30) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", PFLM_GuiOthersPlayerMaster.class, popWorld, base));
		}
		//ModelChange
		if (id == 50 | id == 51) {
			String[] s0 = ModchuModel_TextureManagerBase.instance.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), id == 50 ? 1 : 0, false, PFLM_ConfigData.autoArmorSelect, false);
			//Modchu_Debug.mDebug("PFLM_GuiMaster ModelChange PFLM_ConfigData.autoArmorSelect="+PFLM_ConfigData.autoArmorSelect);
			if (s0 != null
					&& s0[0] != null
					&& !s0[0].isEmpty()) {
				setTextureName(s0[0]);
				if (s0[1] != null
						&& !s0[1].isEmpty()) {
					Modchu_Debug.mDebug("PFLM_GuiMaster ModelChange actionPerformed s0[1]="+s0[1]);
					setTextureArmorName(s0[1]);
				} else {
					String s = "PFLM_GuiMaster ModelChange s0[1] == null !!";
					Modchu_Debug.mDebug(s);
					Modchu_Main.setRuntimeException(s);
				}
			} else {

			}
			//modelData.setCapsValue(modelData.caps_textureName, getTextureName());
			//modelData.setCapsValue(modelData.caps_textureArmorName, getTextureArmorName());
			//Modchu_Debug.mDebug("PFLM_GuiMaster ModelChange getTextureName()="+getTextureName());
			//Modchu_Debug.mDebug("PFLM_GuiMaster ModelChange getTextureArmorName()="+getTextureArmorName());
			modelChange();
			return;
		}
		//ColorChange
		if (id == 52 | id == 53) {
			setColor(getColor() + (id == 53 ? 1 : -1));
			Modchu_Debug.mDebug("ColorChange getColor()="+getColor());
			colorReverse = id == 52;
			setColor(colorCheck(getTextureName(), getColor(), colorReverse));
			int color = getColor();
			PFLM_Main.setMaidColor(color);
			modelData.setCapsValue(modelData.caps_maidColor, color);
			((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).allModelTextureReset(thePlayer, modelData);
			PFLM_GuiConstant.partsSetFlag = 1;
			partsInitFlag = false;
			partsSaveFlag = false;
			partsSetDefault = false;
			imageWriteComplete = false;
			imageWriteFail = false;
			noSaveFlag = true;
			textureResetFlag = true;
			ModchuModel_Main.changeColor(thePlayer);
			setTextureValue();
			ModchuModel_Main.changeColor(drawEntity);
			return;
		}
		//ArmorChange
		if (id == 54 | id == 55) {
			String[] s0 = ModchuModel_TextureManagerBase.instance.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), id == 54 ? 1 : 0, true, false, PFLM_ConfigData.autoArmorSelect);
			setTextureArmorName(s0[1]);
			//modelData.setCapsValue(modelData.caps_textureArmorName, getTextureArmorName());
			//Modchu_Debug.mDebug("PFLM_GuiMaster actionPerformed ArmorChange getTextureArmorName()="+getTextureArmorName());
			if (ModchuModel_ConfigData.isModelSize) {
				closePlayerToSpawn = true;
			}
			PFLM_GuiConstant.partsSetFlag = 1;
			partsInitFlag = false;
			imageWriteComplete = false;
			imageWriteFail = false;
			noSaveFlag = true;
			setTextureValue();
			return;
		}
		//ModelListSelect
		if (id == 56) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", ModchuModel_GuiModelSelectMaster.class, popWorld, base, false, getColor()));
			return;
		}
		//KeyControls
		if (id == 57) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", PFLM_GuiKeyControlsMaster.class, popWorld, base));
			return;
		}
		//Handedness
		if (id == 58) {
			if (isShiftKeyDown) {
				PFLM_ConfigData.handednessMode--;
			} else {
				PFLM_ConfigData.handednessMode++;
			}
			if (PFLM_ConfigData.handednessMode < -1) PFLM_ConfigData.handednessMode = 1;
			if (PFLM_ConfigData.handednessMode > 1) PFLM_ConfigData.handednessMode = -1;
			((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).setHandedness(thePlayer, PFLM_ConfigData.handednessMode);
			initDrawStringListFlag = true;
			return;
		}
		//CustomModel
		if (id == 59) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", ModchuModel_GuiCustomModel.class, popWorld, base));
			return;
		}
		//Customize ArmorType
		if (id == 97) {
			armorType--;
			if (armorType == 1) armorType = 0;
		}
		if (id == 98) {
			armorType++;
			if (armorType == 1) armorType = 2;
		}
		if (id == 97 | id == 98) {
			if (armorType < 0) armorType = 3;
			if (armorType > 3) armorType = 0;
			PFLM_GuiConstant.partsSetFlag = 1;
			partsInitFlag = false;
			return;
		}
		//Customize Default
		if (id == 99) {
			parts = new ConcurrentHashMap();
			ModchuModel_Config.setConfigShowPartsMap(getTextureName(), getColor(), armorType, parts);
			partsSetDefault = true;
			modelChange();
			return;
		}
		//PartsButton
		if (id >= 10000 && id <= 19999) {
			partsSetDefault = false;
			int i = id - 10000;
			String s = null;
			if (!guiPartsNemeMap.isEmpty() && guiPartsNemeMap.containsKey(i)) {
				s = guiPartsNemeMap.get(i);
			}
			if (s != null) {
				if (parts != null) {
					boolean b = false;
					if (!parts.isEmpty() && parts.containsKey(s)) b = !parts.get(s);
					parts.put(s, b);
				} else {
					parts = new ConcurrentHashMap();
					parts.put(s, false);
				}
			}
			ModchuModel_Config.setConfigShowPartsMap(getTextureName(), getColor(), armorType, parts);
			partsSaveFlag = true;
			PFLM_GuiConstant.partsSetFlag = 1;
			drawEntitySetFlag = true;
			return;
		}
		//Save
		if (id == 200) {
			PFLM_ConfigData.setModel = setModel;
			PFLM_ConfigData.setColor = setColor;
			PFLM_ConfigData.setArmor = setArmor;
			String s1 = getTextureName();
			String s2 = getTextureArmorName();
			int i1 = getColor();
			modelData.setCapsValue(modelData.caps_textureName, s1);
			modelData.setCapsValue(modelData.caps_textureArmorName, s2);
			modelData.setCapsValue(modelData.caps_maidColor, i1);
			PFLM_ConfigData.textureName = s1;
			PFLM_ConfigData.textureArmorName = s2;
			PFLM_ConfigData.maidColor = i1;
			PFLM_ConfigData.modelScale = modelScale;
			PFLM_Main.saveParamater();
			ModchuModel_Config.saveShowModelParamater(ModchuModel_Main.cfgfile);
			PFLM_Config.clearCfgData();
			if (partsSaveFlag) {
				ModchuModel_Config.setConfigShowPartsMap(getTextureName(), getColor(), armorType, parts);
				partsSaveFlag = false;
			}
			PFLM_Main.loadParamater();
			Modchu_Config.loadConfig(ModchuModel_Main.showModelList, ModchuModel_Main.cfgfile);
			PFLM_Config.loadShowModelList(ModchuModel_Main.showModelList);
			PFLM_Main.clearDataMap();
			noSaveFlag = false;
			//Modchu_Debug.mDebug("PFLM_GuiMaster getTextureName()="+getTextureName());
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, (Object)null);
			return;
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		if (partsButton) {
			int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
			int i = Mouse.getEventX() * width / Modchu_AS.getInt(Modchu_AS.minecraftDisplayWidth);
			//int j = height - Mouse.getEventY() * height / mc.displayHeight - 1;
			if (i < width / 2 - 60) {
				// ホイールの獲得
				int k = Mouse.getEventDWheel();
				if (k != 0) {
					scrollY -= k * 0.25;
					scrollY = scrollY < 0 ? 0 : scrollY;
					scrollY = scrollY > showPartsListSize * 15 ? showPartsListSize * 15 : scrollY;
					initGui();
				}
			}
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == 200) {
			scrollY += 30;
			scrollY = scrollY > showPartsListSize * 15 ? showPartsListSize * 15 : scrollY;
			initGui();
		}
		if (i == 208) {
			scrollY -= 30;
			scrollY = scrollY < 0 ? 0 : scrollY;
			initGui();
		}
		super.keyTyped(c, i);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		//Modchu_Debug.dDebug("drawGuiContainerBackgroundLayer");
		resetFlagCheck(false, false);
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);
		int xSize = 80;
		int ySize = 50;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int guiLeft = width / 2 - xSize + 30;
		int guiTop = height / 2 - (ySize / 2) - 20;
		drawStringPosX = guiLeft;
		drawStringPosY = guiTop + 90;
		//Modchu_Debug.mDebug("drawGuiContainerBackgroundLayer initDrawStringListFlag="+initDrawStringListFlag);
		int skinMode = getSkinMode();
		if (initDrawStringListFlag) {
			initDrawStringListFlag = false;
			drawStringList.clear();
			StringBuilder s = (new StringBuilder()).append("TextureName : ");
			StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
			StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
			int x = 0;
			//if (skinMode != ModchuModel_IEntityCaps.skinMode_Random) {
				PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
				boolean localFlag = modelData.getCapsValueInt(modelData.caps_skinMode) == ModchuModel_IEntityCaps.skinMode_local;
				if (skinMode != ModchuModel_IEntityCaps.skinMode_online
						| localFlag) {
					s = s.append(getTextureName());
					drawStringList.add(s.toString());
					s2 = s2.append(getColor());
					drawStringList.add(s2.toString());
				}
				if ((ModchuModel_Main.bipedCheck(drawEntity)
						| skinMode == ModchuModel_IEntityCaps.skinMode_online)
						&& !localFlag) {
				} else {
					s1 = s1.append(getTextureArmorName());
					drawStringList.add(s1.toString());
				}
			//}
			StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
			s9 = s9.append(PFLM_GuiConstant.getChangeModeString(getChangeMode()));
			drawStringList.add(s9.toString());
			StringBuilder s10 = (new StringBuilder()).append("Handedness : ");
			s10 = s10.append(PFLM_GuiConstant.getHandednessModeString(PFLM_ConfigData.handednessMode));
			//PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
			if (PFLM_ConfigData.handednessMode == -1) s10 = s10.append(" Result : ").append(PFLM_GuiConstant.getHandednessModeString(modelData.getCapsValueInt(modelData.caps_dominantArm)));
			drawStringList.add(s10.toString());
			if (ModchuModel_ConfigData.isModelSize
					&& skinMode != ModchuModel_IEntityCaps.skinMode_online) {
				float f1 = 0.5F;
				float f2 = 1.35F;
				float f3 = 1.17F;
				if (!tempYOffsetInit) {
					tempYOffsetInit = true;
					if (PFLM_Main.gotchaNullCheck()) setTempYOffset(ModchuModel_Main.getYOffset(thePlayer));
				}
				f1 = ModchuModel_Main.getWidth(thePlayer);
				f2 = ModchuModel_Main.getHeight(thePlayer);
				f3 = ModchuModel_Main.getYOffset(thePlayer);
				String s3 = "Size : Width = " + f1 + " Height = " + f2;
				s3 = (new StringBuilder()).append(s3).toString();
				drawStringList.add(s3.toString());
				String s4 = "YOffset : " + f3;
				s4 = (new StringBuilder()).append(s4).toString();
				drawStringList.add(s4.toString());
			}
			if (skinMode != ModchuModel_IEntityCaps.skinMode_Random) {
				//Modchu_Debug.mDebug("changeMode="+changeMode);
				PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
				StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
				s8 = s8.append(PFLM_ConfigData.showArmor);
				drawStringList.add(s8.toString());
			}
			String[] s0 = ModchuModel_TextureManagerBase.instance.getMissingModelClassData(textureName);
			if (s0 != null
					&& s0.length > 0) {
				errorModelString = textureName + " is error model. error message [ " + s0[1]+" ]";
			} else {
				if (ModchuModel_TextureManagerBase.instance.getModelClassName(textureName) == null
						&& textureName.indexOf("_Custom") < 0) {
					errorModelString = textureName + " is error model. error message [ Model Class Not Found. ]";
				} else {
					errorModelString = null;
				}
			}
		}
		int i1 = 20;
		if (PFLM_Main.newRelease
				&& !partsButton
				&& !modelScaleButton
				&& !initNewReleaseDrow) {
			drawString("PlayerFormLittleMaid", 10, 10, 0xffffff);
			drawString((new StringBuilder()).append("newVersion v").append(PFLM_Main.newVersion).append(" Release!").toString(), 10, i1, 0xffffff);
			i1 = i1 + 20;
		}
		if (Modchu_Main.newRelease
				&& !partsButton
				&& !modelScaleButton
				&& !initNewReleaseDrow) {
			drawString("ModchuLib", 10, 30, 0xffffff);
			drawString((new StringBuilder()).append("newVersion v").append(Modchu_Main.newVersion).append(" Release!").toString(), 10, i1, 0xffffff);
			i1 = i1 + 20;
		}
		if (skinMode != ModchuModel_IEntityCaps.skinMode_Random) {
			String s5;
			if (imageWriteComplete) {
				s5 = (new StringBuilder()).append(PFLM_ConfigData.textureSavedir).append(tagSetFileName).append(" Successfully written file.").toString();
				drawString(s5, 10, i1, 0xffffff);
				i1 = i1 + 20;
			}
			if (imageWriteFail) {
				s5 = (new StringBuilder()).append(PFLM_ConfigData.textureSavedir).append(tagSetFileName).append(" Failed to write file.").toString();
				drawString(s5, 10, i1, 0xffffff);
				i1 = i1 + 20;
			}
		}
		if (errorModelString != null) {
			drawString(errorModelString, 10, i1, 0xffffff);
		}
		if (skinMode != ModchuModel_IEntityCaps.skinMode_Random
				&& skinMode != ModchuModel_IEntityCaps.skinMode_online) {
			drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
			drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
			drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
		}
		if (partsButton) {
			String s8 = "Parts showModel set";
			s8 = (new StringBuilder()).append(s8).toString();
			drawString(s8, width / 2 - 180, -scrollY, 0xffffff);
			if (PFLM_Main.guiShowArmorSupport) {
				s8 = (new StringBuilder()).append("Type : ").append(PFLM_GuiConstant.getArmorTypeString(armorType)).toString();
				drawString(s8, width / 2 - 100, 15 - scrollY, 0xffffff);
			}
		}
		if (PFLM_ConfigData.useScaleChange
				&& modelScaleButton) {
			String s6 = "modelScale : " + getScale();
			s6 = (new StringBuilder()).append(s6).toString();
			drawString(s6, guiLeft - 140, guiTop + 30, 0xffffff);
			String s7 = "modelScaleChange";
			s7 = (new StringBuilder()).append(s7).toString();
			drawString(s7, guiLeft - 140, guiTop - 5, 0xffffff);
		}
		if (skinMode != ModchuModel_IEntityCaps.skinMode_Random) {
			//Modchu_Debug.mDebug("changeMode="+changeMode);
			int var4 = guiLeft;
			int var5 = guiTop;
			ModchuModel_RenderMasterBase.drawMobModel(width, height, i, j, var4 + 51, var5 + 75, 0, -25, 50F, 0.0F, comeraPosX, comeraPosY, comeraPosZ, comeraRotationX, comeraRotationY, comeraRotationZ, cameraZoom, cameraZoom, cameraZoom, true, drawEntity);
			if (skinMode == ModchuModel_IEntityCaps.skinMode_online
					| ModchuModel_Main.bipedCheck(drawEntity)
					&& PFLM_ConfigData.guiMultiPngSaveButton
					&& !partsButton
					&& !modelScaleButton) {
				if (Modchu_Main.getMinecraftVersion() > 179) Modchu_GlStateManager.bindTexture(9999);
				PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
				if (drawMuitiEntitySetFlag) {
					Modchu_Debug.mDebug("drawMuitiEntitySetFlag");
					setcheck();
					reLoadModel(drawMuitiEntity, true, false);
					drawMuitiEntitySetFlag = false;
				}
				//GL11.glDisable(GL11.GL_LIGHTING);
				drawString("MultiTagSet", 15, height / 2 - 60, 0xffffff);
				drawString("Model", 15, height / 2 - 25, 0xffffff);
				drawString("Color", 15, height / 2 - 9, 0xffffff);
				drawString("Armor", 15, height / 2 + 8, 0xffffff);
				drawString("ShowArmor", 15, height / 2 + 25, 0xffffff);
				drawString("MultiTextureName : ", 10, guiTop + 90, 0xffffff);
				drawString((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName), 10, guiTop + 100, 0xffffff);
				drawString((new StringBuilder()).append("MultiColor : ").append(drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor)).toString(), 10, guiTop + 110, 0xffffff);
				drawString("MultiArmorName : ", 10, guiTop + 120, 0xffffff);
				drawString((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureArmorName), 10, guiTop + 130, 0xffffff);
				ModchuModel_RenderMasterBase.drawMobModel(width, height, i, j, 120, height / 2 + 20, -90, -50, 30F, 0.0F, comeraPosX, comeraPosY, comeraPosZ, comeraRotationX, comeraRotationY, comeraRotationZ, cameraZoom, cameraZoom, cameraZoom, false, drawMuitiEntity);
				//PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
				//Modchu_Debug.mDebug("drawGuiContainerBackgroundLayer drawEntityModelData skinChar="+drawEntityModelData.getCapsValue(drawEntityModelData.caps_freeVariable, "skinChar"));
			}
		}
		super.drawGuiContainerBackgroundLayer(f, i, j);
		//PFLM_ModelData drawModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		//PFLM_ModelDataMaster.instance.checkModelData(drawModelData);
	}

	@Override
	public void modelChange() {
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);
		if (ModchuModel_ConfigData.isModelSize) {
			closePlayerToSpawn = true;
		}
		//Modchu_AS.set(Modchu_AS.allModelInit, PFLM_Main.renderPlayerDummyInstance, thePlayer, true);
		PFLM_GuiConstant.partsSetFlag = 1;
		partsInitFlag = false;
		partsSaveFlag = false;
		partsSetDefault = false;
		imageWriteComplete = false;
		imageWriteFail = false;
		noSaveFlag = true;
		drawEntitySetFlag = true;
		drawMuitiEntitySetFlag = true;
		//Modchu_Debug.mDebug("modelChange()");
	}

	public void setTextureValue() {
		ModchuModel_ModelDataBase drawEntityModelData = PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		int skinMode = getSkinMode();
		if (skinMode == ModchuModel_IEntityCaps.skinMode_offline) {
			//Modchu_Debug.mDebug("PFLM_GuiMaster setTextureValue getTextureName()="+getTextureName());
			setTextureName(getTextureName());
			setTextureArmorName(getTextureArmorName());
			setColor(getColor());
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_skinMode, ModchuModel_IEntityCaps.skinMode_offline);
		}
		if (skinMode == ModchuModel_IEntityCaps.skinMode_online) {
			//drawEntityModelData.modelMain = thePlayerModelData.modelMain;
			//drawEntityModelData.modelFATT = thePlayerModelData.modelFATT;
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_skinMode, ModchuModel_IEntityCaps.skinMode_online);
			drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.newPlayerData(drawEntity, drawEntityModelData);
/*
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_textureName, thePlayerModelData.getCapsValue(thePlayerModelData.caps_textureName));
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_textureArmorName, thePlayerModelData.getCapsValue(thePlayerModelData.caps_textureArmorName));
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_maidColor, thePlayerModelData.getCapsValueInt(thePlayerModelData.caps_maidColor));
*/
		}
		if (skinMode != ModchuModel_IEntityCaps.skinMode_online) drawEntityModelData.setCapsValue(drawEntityModelData.caps_freeVariable, "skinChar", false);
		super.setTextureValue();
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);
		PFLM_ModelData thePlayerModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_modelScale, getScale());
		boolean isSitting = thePlayerModelData.getCapsValueBoolean(drawEntityModelData.caps_isSitting);
		//Modchu_Debug.mDebug("modelDataSetting isSitting="+isSitting);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_isSitting, isSitting);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_isRiding, isSitting);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_isSneak, thePlayerModelData.getCapsValue(thePlayerModelData.caps_isSneak));
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_isSleeping, thePlayerModelData.getCapsValue(thePlayerModelData.caps_isSleeping));
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_freeVariable, "showArmor", PFLM_ConfigData.showArmor);
		drawEntitySetFlag = true;
		Modchu_Debug.mDebug("PFLM_GuiMaster setTextureValue end. ");
	}

	private void setDrawMuitiTextureValue() {
		setcheck();
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureName, PFLM_Main.textureList.get(setModel));
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, PFLM_Main.textureList.get(setArmor));
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_maidColor, setColor);
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_skinMode, ModchuModel_IEntityCaps.skinMode_offline);
		drawMuitiEntitySetFlag = true;
	}

	@Override
	public void selected(String textureName, String textureArmorName, int color, boolean armorMode) {
		super.selected(textureName, textureArmorName, color, armorMode);
		Modchu_Debug.mDebug("PFLM_GuiMaster selected getTextureName()="+getTextureName()+" textureName="+textureName);
		if (armorMode) {
			setTextureName(getTextureName());
			setColor(getColor());
		}
		Modchu_Debug.mDebug("PFLM_GuiMaster selected");
	}

	public void setTextureArmorPackege(int i, boolean b) {
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		setArmorNumber(setModel);
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName));
		super.setTextureArmorPackege(i);
		String s = (String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureArmorName);
		Modchu_Debug.mDebug("setTextureArmorPackege s=" + s);
		Modchu_Debug.mDebug("setTextureArmorPackege specificationArmorCheckBoolean=" + ModchuModel_TextureManagerBase.instance.specificationArmorCheckBoolean((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName)));
		if (s != null
				&& !s.isEmpty()
				&& s.equalsIgnoreCase("erasearmor")) {
			s = b
					| ModchuModel_TextureManagerBase.instance.specificationArmorCheckBoolean((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName)) ? "x32_QB" : "default";
			drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, s);
		}
		if (ModchuModel_TextureManagerBase.instance.checkTextureArmorPackege(s) != null) ;
		else drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName));
	}

	@Override
	public void setArmorTextureValue() {
		if (getTextureArmorName() == null) setTextureArmorName(getTextureName());
		if (ModchuModel_TextureManagerBase.instance.checkTextureArmorPackege(getTextureArmorName()) == null) {
			setTextureArmorName(ModchuModel_TextureManagerBase.instance.getArmorName(getTextureName(), 1, false));
		}
	}

/*
	private int getTexturesArmorNamber(int i) {
		if (i >= PFLM_Main.maxTexturesArmorNamber
				| i < 0) return -1;
		return PFLM_Main.texturesArmorNamber[i];
	}
*/
	private void setArmorNumber(int i) {
		setArmor = i;
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (closePlayerToSpawn
				&& ModchuModel_ConfigData.isModelSize) {
			setPositionCorrection(null);
		}
		tempYOffsetInit = false;
		drawMuitiEntitySetFlag = true;
		initNewReleaseDrow = false;
	}

	public void setPositionCorrection(Object entity) {
		if (entity != null); else entity = Modchu_AS.get(Modchu_AS.minecraftPlayer);
		PFLM_Main.setSize(entity, 0.6F, 1.8F);
		PFLM_Main.resetHeight(entity);
		double d = tempYOffset - ModchuModel_Main.getYOffset(entity) - 0.5D;
		PFLM_Main.setPositionCorrection(entity, 0.0D, -d, 0.0D);
	}

	public void imageMultiTagSetSave(int c) {
		BufferedImage image = null;
		int skinMode = getSkinMode();
		if (bufferedimageMode) {
			if (bufferedimage != null) {
				image = bufferedimage;
				image = fullColorConversion(image);
				result = true;
			} else {
				image = null;
			}
		} else {
			try {
				if (bufferedimage1 == null
						| skinMode == ModchuModel_IEntityCaps.skinMode_offline) {
					image = readTextureImage(ModchuModel_TextureManagerBase.instance.textureManagerGetTexture(getTextureName(), c));
				} else {
					image = bufferedimage1;
					result = true;
				}
			} catch (IOException e1) {
				Modchu_Debug.mDebug("imageMultiTagSetSave Failed to read image.");
				e1.printStackTrace();
			}
		}
		if (image != null
				&& result) {
			if (ModchuModel_Main.bipedCheck(drawEntity)
					| skinMode == ModchuModel_IEntityCaps.skinMode_online) {
				result = false;
			} else {
				result = true;
			}
			PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(result ? drawEntity : drawMuitiEntity);
			String s1 = (String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName);
			String s0 = Modchu_Main.lastIndexProcessing(s1, "_");
			boolean rightBottomSet = false;
			if (s0.startsWith("Elsa")) rightBottomSet = true;
			Modchu_Debug.mDebug("imageMultiTagSetSave result="+result);
			String s2 = (String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureArmorName);
			int t = result ? PFLM_Main.getTextureListModelIndexOf(s1) : PFLM_Main.textureList.indexOf(s1);
			Modchu_Debug.mDebug("imageMultiTagSetSave t=" + t);
			int t2 = result ? PFLM_Main.getTextureListModelIndexOf(s2) : PFLM_Main.textureList.indexOf(s2);
			t = t < 0 ? 0 : t;
			t2 = t2 < 0 ? 0 : t2;

			//PFLM用か判断用ドット
			int setX = image.getWidth() - 1;
			int setY = 0;
			if (rightBottomSet) setY = 31;
			int rgb = Modchu_RenderSupport.instance.colorRGB(255, 0, 0);
			image.setRGB(setX, setY, rgb);

			//ローカルテクスチャを使用するかどうか判断用ドット
			rgb = result ? Modchu_RenderSupport.instance.colorRGB(255, 255, 0) : Modchu_RenderSupport.instance.colorRGB(255, 0, 255);
			setY = 1;
			if (rightBottomSet) setY = 30;
			image.setRGB(setX, setY, rgb);

			//r = color , g = ArmorName , b = textureName
			int r1 = result ? 255 - c : 255 - drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor);
			int g1 = 255 - t2;
			int b1 = 255 - t;
			rgb = Modchu_RenderSupport.instance.colorRGB(r1, g1, b1);
			setX = image.getWidth() - 2;
			setY = 0;
			if (rightBottomSet) setY = image.getHeight() - 1;
			image.setRGB(setX, setY, rgb);
			Modchu_Debug.mDebug("imageMultiTagSetSave r1=" + r1 + " g1=" + g1 + " b1=" + b1);

			//handedness r = 255 right , r = 0 left , else Random
			Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);
			PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
			int handedness = modelData.getCapsValueInt(modelData.caps_dominantArm);
			r1 = handedness == 0 ? 255 : handedness == 1 ? 0 : 128;
			//g = PFLM_ConfigData.modelScale
			g1 = 255 - ((int) (getScale() / (0.9375F / 24F)));
			if (g1 > 255) g1 = 255;
			if (g1 < 0) g1 = 0;
			b1 = 255;
			rgb = Modchu_RenderSupport.instance.colorRGB(r1, g1, b1);
			setX = image.getWidth() - 2;
			setY = 1;
			if (rightBottomSet) setY = 30;
			image.setRGB(setX, setY, rgb);

			Object resourceLocation = ModchuModel_TextureManagerBase.instance.textureManagerGetTexture(s1, c);
			String s = "output.png";
/*
			if (resourceLocation == null
				| bufferedimageMode) {
					s = "steve.png";
			} else {
				s = resourceLocation.func_110624_b();
			}
			if (s == null
				| s.equals("minecraft")) s = "steve.png";
			if (s.lastIndexOf(".png") == -1) s = s + ".png";
*/
			tagSetFileName = s;
			s = (new StringBuilder()).append(PFLM_ConfigData.textureSavedir).append(s).toString();
			Modchu_FileManager.createDir(s);
			try {
				File file = new File(Modchu_AS.getFile(Modchu_AS.minecraftMcDataDir) + s);
				if (file.exists()) {
					try {
						file.delete();
					} catch (Exception e) {
					}
				}
				result = ImageIO.write(image, "png", file);
				image.flush();
				imageWriteComplete = true;
				imageWriteFail = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Modchu_Debug.mDebug("imageMultiTagSetSave null !!");
			imageWriteComplete = false;
			imageWriteFail = true;
			tagSetFileName = tagSetFileName == null && bufferedimageMode ? "image == null error." : null;
			//tagSetFileName = tagSetFileName == null && !result ? " image size error." : tagSetFileName;
		}
	}

	private BufferedImage readTextureImage(Object o) throws IOException {
		//Modchu_Debug.mDebug("readTextureImage assets/minecraft/" + resourceLocation.func_110624_b());
		//Modchu_Debug.mDebug("readTextureImage assets/minecraft/" + resourceLocation.func_110623_a());
		Modchu_Debug.lDebug("PFLM_GuiMaster readTextureImage o="+o);

		BufferedImage image = null;
		InputStream inputStream = null;
		try {
			inputStream = Modchu_FileManager.getModInputStream(o);
			Modchu_Debug.lDebug("PFLM_GuiMaster readTextureImage getModInputStream inputStream="+inputStream);
			if (inputStream != null) {
				image = ImageIO.read(inputStream);
			} else {
				int version = Modchu_Main.getMinecraftVersion();
				if (version > 159) {
					try {
						inputStream = Modchu_AS.getInputStream(Modchu_AS.resourceManagerInputStream, o);
						Modchu_Debug.lDebug("PFLM_GuiMaster readTextureImage resourceManagerInputStream inputStream="+inputStream);
						if (inputStream != null) image = ImageIO.read(inputStream);
					} finally {
						if (inputStream != null) inputStream.close();
					}
				} else {
					URL url = (URL) Modchu_AS.get(Modchu_AS.getResource, o);
					Modchu_Debug.mDebug("PFLM_GuiMaster readTextureImage url="+url);
					image = ImageIO.read(url);
				}
			}
		} finally {
			if (inputStream != null) inputStream.close();
		}
		return image != null ? fullColorConversion(image) : null;
	}

	private BufferedImage fullColorConversion(BufferedImage image) {
		BufferedImage write = null;
		try {
			//フルカラー変換処理
			result = true;
			int w = image.getWidth();
			int h = image.getHeight();
			write = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int c = image.getRGB(x, y);
					write.setRGB(x, y, c);
				}
			}
		} catch (Exception e) {
			Modchu_Debug.lDebug("" + getClass().getName()+" fullColorConversion", 2, e);
		}
		return write;
	}

	public void setcheck() {
		setModel = setModel >= PFLM_Main.textureList.size() ? 0 : setModel;
		setModel = setModel < 0 ? PFLM_Main.textureList.size() - 1 : setModel;
		setModel = setModel < 0 ? 0 : setModel;
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		if (drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor) > 15) {
			drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_maidColor, 0);
		}
		if (drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor) < 0) {
			drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_maidColor, 15);
		}
		setArmor = setArmor >= PFLM_Main.textureList.size() ? 0 : setArmor;
		setArmor = setArmor < 0 ? PFLM_Main.textureList.size() - 1 : setArmor;
		setArmor = setArmor < 0 ? 0 : setArmor;
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_skinMode, ModchuModel_IEntityCaps.skinMode_offline);
	}

	@Override
	public String getTextureName() {
		//Modchu_Debug.mDebug("PFLM_GuiMaster getTextureName PFLM_ConfigData.textureName="+PFLM_ConfigData.textureName);
		return textureName;
	}

	@Override
	public void setTextureName(String s) {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_textureName, s);
		textureName = s;
		Modchu_Debug.mDebug("PFLM_GuiMaster setTextureName s="+s);
	}

	@Override
	public String getTextureArmorName() {
		return textureArmorName;
	}

	@Override
	public void setTextureArmorName(String s) {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_textureArmorName, s);
		textureArmorName = s;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void setColor(int i) {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_maidColor, i & 0xf);
		color = i;
	}

	@Override
	public float getScale() {
		return modelScale;
	}

	@Override
	public void setScale(float f) {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_modelScale, f);
		modelScale = f;
	}

	protected int getChangeMode() {
		return PFLM_ConfigData.changeMode;
	}

	@Override
	public void setChangeMode(int i) {
		PFLM_ConfigData.changeMode = i;
	}

	public int getSkinMode() {
		return PFLM_GuiConstant.getSkinMode(getChangeMode());
	}
/*
	public static ConcurrentHashMap<String, Boolean> getShowPartsMap() {
		return !partsInitFlag ? PFLM_Config.getConfigShowPartsMap(getTextureName(), getColor(), armorType)
				: partsSetDefault ? PFLM_Gui.defaultParts : PFLM_Gui.parts;
	}
*/
}
