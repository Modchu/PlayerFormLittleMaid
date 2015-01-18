package modchu.pflm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import modchu.lib.Modchu_Config;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_FileManager;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.lib.Modchu_RenderEngine;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_GuiBase;
import modchu.lib.characteristic.Modchu_GuiModelView;
import modchu.lib.characteristic.Modchu_IEntityCapsBase;
import modchu.lib.characteristic.Modchu_ModelRenderer;
import modchu.model.ModchuModel_Config;
import modchu.model.ModchuModel_Main;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PFLM_GuiMaster extends PFLM_GuiModelViewMaster {

	public Object drawMuitiEntity;
	private BufferedImage bufferedimage;
	private BufferedImage bufferedimage1;
	private String modelArmorName;
	private String tagSetFileName;
	private double tempYOffset;
	private boolean imageWriteComplete;
	private boolean imageWriteFail;
	private boolean bufferedimageMode;
	private boolean TempYOffsetInit;
	private int scrollY;
	private int armorType;
	private int showPartsListSize;
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
	public boolean partsSaveFlag;
	public boolean noSaveFlag;
	public int setModel = 0;
	public int setArmor = 0;
	public int setColor = 0;
	private final int maxChangeMode = 3;

	public PFLM_GuiMaster(Object guiBase, Object guiScreen, Object world, Object... o) {
		super(guiBase, guiScreen, world, o);
	}

	@Override
	public void init(Object guiBase, Object guiScreen, Object world, Object... o) {
		super.init(guiBase, guiScreen, world, o);
		guiMasterInit();
	}

	@Override
	public void reInit() {
		super.reInit();
		guiMasterInit();
	}

	private void guiMasterInit() {
		modelArmorName = null;
		tagSetFileName = null;
		imageWriteComplete = false;
		imageWriteFail = false;
		bufferedimageMode = false;
		TempYOffsetInit = false;
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
		showArmor = true;
		PFLM_GuiConstant.partsSetFlag = 0;
		setModel = PFLM_ConfigData.setModel;
		setArmor = PFLM_ConfigData.setArmor;
		setColor = PFLM_ConfigData.setColor;
		showArmor = PFLM_ConfigData.showArmor;
		if (parts != null) {
			parts.clear();
		} else {
			parts = new ConcurrentHashMap();
		}
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		setTextureName(getTextureName());
		setTextureArmorName(getTextureArmorName());
		setColor(getColor());
		setDrawMuitiTextureValue();
	}

	@Override
	public void initDrawEntity() {
		super.initDrawEntity();
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		if (drawMuitiEntity != null) ;else drawMuitiEntity = Modchu_Reflect.newInstance("modchu.lib.characteristic.Modchu_EntityPlayerDummy", new Class[]{ Modchu_Reflect.loadClass("World") }, new Object[]{ popWorld });
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "showMainModel", true);
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "initDrawEntityFlag", true);
	}

	@Override
	public void initGui() {
		Modchu_Debug.mDebug("initGui");
		initButtonSetting();
		super.initGui();
		if (!displayButton) return;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
		showPartsListSize = 0;
		if (buttonKeyControls) buttonList.add(newInstanceButton(57, x + 75, y + 70, 75, 15, "KeyControls"));
		if (buttonCustomModel
				&& !Modchu_Main.isRelease) buttonList.add(newInstanceButton(59, x + 75, y + 40, 75, 15, "CustomModel"));
		if (getChangeMode() != PFLM_GuiConstant.modeRandom) {
			if (buttonMultiPngSave
					&& PFLM_ConfigData.guiMultiPngSaveButton
					&& !partsButton
					&& !modelScaleButton) {
				buttonList.add(newInstanceButton(12, x + 75, y + 100, 80, 20, "MultiPngSave"));
				bufferedimageMode = false;
				PFLM_ModelData drawModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
				if (drawModelData.modelMain != null
						&& PFLM_Main.bipedCheck(drawModelData.modelMain.model)
						| getChangeMode() == PFLM_GuiConstant.modeOnline) {
					buttonList.add(newInstanceButton(15, 50, y + 55, 15, 15, "<"));
					buttonList.add(newInstanceButton(14, 65, y + 55, 15, 15, ">"));
					buttonList.add(newInstanceButton(17, 50, y + 72, 15, 15, "-"));
					buttonList.add(newInstanceButton(16, 65, y + 72, 15, 15, "+"));
					buttonList.add(newInstanceButton(19, 50, y + 89, 15, 15, "<"));
					buttonList.add(newInstanceButton(18, 65, y + 89, 15, 15, ">"));
					PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
					PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
					buttonList.add(newInstanceButton(21, 70, y + 107, 30, 15, "" + drawMuitiModelData.getCapsValueBoolean(drawMuitiModelData.caps_freeVariable, "showArmor")));
					if (getChangeMode() == PFLM_GuiConstant.modeOnline
							&& (bufferedimage == null
							| drawEntitySetFlag)) {
						//bufferedimage = PFLM_ModelDataMaster.instance.getOnlineSkin(modelData, thePlayer);
						Object o = modelData.getCapsValue(modelData.caps_freeVariable, "bufferedimage");
						bufferedimage = o != null ? (BufferedImage) o : null;
						drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "skinChar", false);
						drawModelData.setCapsValue(drawModelData.caps_freeVariable, "skinChar", false);
						Modchu_Debug.mDebug("PFLM_GuiMaster bufferedimage drawMuitiModelData set skinChar false");
						modelData.setCapsValue(modelData.caps_skinMode, PFLM_ModelDataMaster.skinMode_online);
						if (bufferedimage != null) {
							Object[] s = ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).checkimage(bufferedimage);
							modelArmorName = (String) s[2];
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
						bufferedimageMode = getChangeMode() == PFLM_GuiConstant.modeOnline ? true : false;
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
					buttonList.add(newInstanceButton(30, x + 75, y - 5, 75, 15, "othersPlayer"));
				}
			}
		}
		if (getChangeMode() != PFLM_GuiConstant.modeRandom) {
			if (partsButton) {
				buttonList.add(newInstanceButton(10, x + 75, y + 10, 75, 15, "Close"));
				buttonList = partsButtonAdd(buttonList);
			} else {
				if (buttonCustomize
						&& !modelScaleButton) buttonList.add(newInstanceButton(11, x + 75, y + 10, 75, 15, "Customize"));
			}
		}
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
	}

	protected void initButtonSetting() {
		buttonOnline = getChangeMode() == PFLM_GuiConstant.modeOnline;
		buttonOffline = getChangeMode() == PFLM_GuiConstant.modeOffline;
		buttonRandom = getChangeMode() == PFLM_GuiConstant.modeRandom;
		buttonScale = modelScaleButton;
		buttonParts = partsButton;
		buttonPlayer = false;
		buttonShowArmor = getChangeMode() != PFLM_GuiConstant.modeRandom;
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
		Object model = PFLM_Main.getModel(armorType);
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
		Modchu_ModelRenderer modelRenderer = null;
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
		Modchu_ModelRenderer modelRenderer = null;
		Object model = PFLM_Main.getModel(armorType);
		ConcurrentHashMap<Integer, String> showPartsNemeMap = ModchuModel_Config.getConfigShowPartsNemeMap(getTextureName(), armorType);
		if (showPartsNemeMap != null
				&& !showPartsNemeMap.isEmpty()) ;else return;
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
						modelRenderer = o != null ? (Modchu_ModelRenderer) o : null;
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
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		//isModelSize Default
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);
		Modchu_Debug.mDebug("id="+id);
		if (id == 3) {
			setScale(PFLM_Main.getModelScale());
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
			setChangeMode(getChangeMode() + 1);
			if (getChangeMode() >= maxChangeMode) setChangeMode(0);
			if (!PFLM_ConfigData.changeModeButton
					&& getChangeMode() == PFLM_GuiConstant.modeOnline) {
				setChangeMode(getChangeMode() + 1);
				if (getChangeMode() >= maxChangeMode) setChangeMode(0);
			}
			switch (getChangeMode()) {
			case PFLM_GuiConstant.modeOnline:
				setModel--;
				id = 14;
			case PFLM_GuiConstant.modeOffline:
			case PFLM_GuiConstant.modeRandom:
				PFLM_Main.removeDataMap();
				break;
			}
			bufferedimage = null;
			PFLM_GuiConstant.partsSetFlag = 1;
			noSaveFlag = true;
			//if (getChangeMode() != modeOnline) {
				initGui();
			//}
			setTextureValue();
			return;
		}
		//guiMultiPngSaveButton setModel+
		if (id == 14) {
			setModel++;
			boolean b = false;
			while (b == false && setModel < PFLM_Main.textureList.size()) {
				drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureName, PFLM_Main.textureList.get(setModel));
				Object ltb = ModchuModel_Main.checkTexturePackege((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName), drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor));
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
				Object ltb = ModchuModel_Main.checkTexturePackege((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName), drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor));
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
			while (b == false && setArmor < PFLM_Main.textureList.size()) {
				drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, PFLM_Main.textureList.get(setArmor));
				Object ltb = ModchuModel_Main.checkTextureArmorPackege((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureArmorName));
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
				Object ltb = ModchuModel_Main.checkTextureArmorPackege((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureArmorName));
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
			showArmor = !showArmor;
			Modchu_Debug.mDebug("actionPerformed showArmor="+showArmor);
			drawEntityModelData.setCapsValue(drawMuitiModelData.caps_freeVariable, "showArmor", showArmor);
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
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiModelView(PFLM_GuiOthersPlayerMaster.class, base, popWorld));
		}
		//ModelChange
		if (id == 50 | id == 51) {
			String[] s0 = PFLM_Main.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), id == 50 ? 1 : 0, false);
			if (s0 != null && s0[0] != null && !s0[0].isEmpty()) {
				setTextureName(s0[0]);
				if (s0[1] != null && !s0[1].isEmpty()) setTextureArmorName(s0[1]);
			}
			//modelData.setCapsValue(drawMuitiModelData.caps_textureName, getTextureName());
			//modelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, getTextureArmorName());
			Modchu_Debug.mDebug("ModelChange getTextureName()="+getTextureName());
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
			modelData.setCapsValue(drawMuitiModelData.caps_maidColor, color);
			((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).allModelTextureReset(thePlayer, modelData);
			PFLM_GuiConstant.partsSetFlag = 1;
			partsInitFlag = false;
			partsSaveFlag = false;
			partsSetDefault = false;
			imageWriteComplete = false;
			imageWriteFail = false;
			noSaveFlag = true;
			textureResetFlag = true;
			PFLM_Main.changeColor(thePlayer);
			setTextureValue();
			PFLM_Main.changeColor(drawEntity);
			return;
		}
		//ArmorChange
		if (id == 54 | id == 55) {
			//armorNamber += id == 55 ? 1 : -1;
			//if (armorNamber >= PFLM_Main.maxTexturesArmorNamber) armorNamber = 0;
			//if (armorNamber < 0) armorNamber = PFLM_Main.maxTexturesArmorNamber - 1;
			String[] s0 = PFLM_Main.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), id == 54 ? 1 : 0, true);
			setTextureArmorName(s0[1]);
			//Modchu_Debug.mDebug("armorNamber="+armorNamber);

			//Object ltb = Modchu_Main.getTextureBox(getTexturesArmorNamber(armorNamber));
			//if (ltb != null) setTextureArmorName(Modchu_Main.getTextureBoxFileName(ltb));

			//modelData.setCapsValue(data.caps_maidColor, getColor());
			modelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, getTextureArmorName());
			//Modchu_Debug.mDebug("ArmorChange getTextureArmorName()="+getTextureArmorName());
			if (PFLM_ConfigData.isModelSize) {
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
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiModelView(PFLM_GuiModelSelectMaster.class, base, popWorld, false, getColor()));
			//mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 0));
			return;
		}
		//KeyControls
		if (id == 57) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiModelView(PFLM_GuiKeyControlsMaster.class, base, popWorld));
			//mc.displayGuiScreen(new PFLM_GuiKeyControls(this, popWorld));
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
			//Modchu_Main.displayGuiScreen(new PFLM_GuiCustomModel(this, popWorld));
			//mc.displayGuiScreen(new PFLM_GuiCustomModel(this, popWorld));
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
			PFLM_ConfigData.showArmor = showArmor;
			PFLM_ConfigData.setModel = setModel;
			PFLM_ConfigData.setColor = setColor;
			PFLM_ConfigData.setArmor = setArmor;
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
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, (Object)null);
			return;
		}
	}

	@Override
	public boolean handleMouseInput() {
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
		return true;
	}

	@Override
	public boolean keyTyped(char c, int i) {
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
		return true;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		//Modchu_Debug.dDebug("drawGuiContainerBackgroundLayer tes 5");
		//textureResetFlag = true;
		resetFlagCheck(true);
/*
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Modchu_AS.set(Modchu_AS.renderHelperEnableStandardItemLighting);
*/
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//GL11.glDisable(GL11.GL_ALPHA_TEST);
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		//OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		//GL11.glDepthMask(true);
		//GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		//Modchu_AS.set(Modchu_AS.openGlHelperSetActiveTexture, Modchu_AS.get(Modchu_AS.openGlHelperLightmapTexUnit));
		//GL11.glDisable(GL11.GL_TEXTURE_2D);
		//Modchu_AS.set(Modchu_AS.openGlHelperSetActiveTexture, Modchu_AS.get(Modchu_AS.openGlHelperDefaultTexUnit));

		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
		int xSize = 80;
		int ySize = 50;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int guiLeft = width / 2 - xSize + 30;
		int guiTop = height / 2 - (ySize / 2) - 20;
		drawStringPosX = guiLeft;
		drawStringPosY = guiTop + 90;
		//Modchu_Debug.mDebug("drawGuiContainerBackgroundLayer initDrawStringListFlag="+initDrawStringListFlag);
		if (initDrawStringListFlag) {
			initDrawStringListFlag = false;
			drawStringList.clear();
			StringBuilder s = (new StringBuilder()).append("TextureName : ");
			StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
			StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
			int x = 0;
			//int x = partsButton | !getModelType().startsWith("Biped") ? 0 : 150;
			if (getChangeMode() != PFLM_GuiConstant.modeRandom) {
				PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
				boolean localFlag = modelData.getCapsValueInt(modelData.caps_skinMode) == PFLM_ModelDataMaster.skinMode_local;
				if (getChangeMode() != PFLM_GuiConstant.modeOnline | localFlag) {
					s = s.append(getTextureName());
					drawStringList.add(s.toString());
					s2 = s2.append(getColor());
					drawStringList.add(s2.toString());
				}
				if ((PFLM_Main.bipedCheck(drawEntity) | getChangeMode() == PFLM_GuiConstant.modeOnline) && !localFlag) {
				} else {
					s1 = s1.append(getTextureArmorName());
					drawStringList.add(s1.toString());
				}
			}
			StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
			s9 = s9.append(PFLM_GuiConstant.getChangeModeString(getChangeMode()));
			drawStringList.add(s9.toString());
			StringBuilder s10 = (new StringBuilder()).append("Handedness : ");
			s10 = s10.append(PFLM_GuiConstant.getHandednessModeString(PFLM_ConfigData.handednessMode));
			PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
			if (PFLM_ConfigData.handednessMode == -1) s10 = s10.append(" Result : ").append(PFLM_GuiConstant.getHandednessModeString(modelData.getCapsValueInt(Modchu_IEntityCapsBase.caps_dominantArm)));
			drawStringList.add(s10.toString());
			if (PFLM_ConfigData.isModelSize && getChangeMode() != PFLM_GuiConstant.modeOnline) {
				float f1 = 0.5F;
				float f2 = 1.35F;
				float f3 = 1.17F;
				if (!TempYOffsetInit) {
					TempYOffsetInit = true;
					if (PFLM_Main.gotchaNullCheck()) setTempYOffset(PFLM_Main.getYOffset());
				}
				f1 = PFLM_Main.getWidth();
				f2 = PFLM_Main.getHeight();
				f3 = PFLM_Main.getYOffset();
				String s3 = "Size : Width = " + f1 + " Height = " + f2;
				s3 = (new StringBuilder()).append(s3).toString();
				drawStringList.add(s3.toString());
				String s4 = "YOffset : " + f3;
				s4 = (new StringBuilder()).append(s4).toString();
				drawStringList.add(s4.toString());
			}
			if (getChangeMode() != PFLM_GuiConstant.modeRandom) {
				//Modchu_Debug.mDebug("getChangeMode()="+getChangeMode());
				PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
				StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
				s8 = s8.append(showArmor);
				drawStringList.add(s8.toString());
			}
		}
		if (PFLM_Main.newRelease
				&& !partsButton
				&& !modelScaleButton) {
			drawString("PlayerFormLittleMaid", 10, 10, 0xffffff);
			drawString((new StringBuilder()).append("newVersion v").append(PFLM_Main.newVersion).append(" Release!").toString(), 10, 20, 0xffffff);
		}
		if (Modchu_Main.newRelease
				&& !partsButton
				&& !modelScaleButton) {
			drawString("ModchuLib", 10, 30, 0xffffff);
			drawString((new StringBuilder()).append("newVersion v").append(Modchu_Main.newVersion).append(" Release!").toString(), 10, 40, 0xffffff);
		}
		if (getChangeMode() != PFLM_GuiConstant.modeRandom) {
			String s5;
			if (imageWriteComplete) {
				s5 = (new StringBuilder()).append(PFLM_ConfigData.textureSavedir).append(tagSetFileName).append(" Successfully written file.").toString();
				drawString(s5, 10, 10, 0xffffff);
			}
			if (imageWriteFail) {
				s5 = (new StringBuilder()).append(PFLM_ConfigData.textureSavedir).append(tagSetFileName).append(" Failed to write file.").toString();
				drawString(s5, 10, 10, 0xffffff);
			}
		}
		if (getChangeMode() != PFLM_GuiConstant.modeRandom
				&& getChangeMode() != PFLM_GuiConstant.modeOnline) {
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
		if (getChangeMode() != PFLM_GuiConstant.modeRandom) {
			//Modchu_Debug.mDebug("getChangeMode()="+getChangeMode());
			int var4 = guiLeft;
			int var5 = guiTop;
			drawMobModel(i, j, var4 + 51, var5 + 75, 0, -25, 50F, 0.0F, true);
			if (getChangeMode() == PFLM_GuiConstant.modeOnline
					| PFLM_Main.bipedCheck(drawEntity)
					&& PFLM_ConfigData.guiMultiPngSaveButton
					&& !partsButton
					&& !modelScaleButton) {
				PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
				if (drawMuitiEntitySetFlag) {
					Modchu_Debug.mDebug("drawMuitiEntitySetFlag");
					setcheck();
					reLoadModel(drawMuitiEntity, true);
					drawMuitiEntitySetFlag = false;
				}
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
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
				drawMobModel(i, j, 120, height / 2 + 20, -90, -50, 30F, 0.0F, false, drawMuitiEntity);
				GL11.glPopMatrix();
				PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
				//Modchu_Debug.mDebug("drawGuiContainerBackgroundLayer drawEntityModelData skinChar="+drawEntityModelData.getCapsValue(drawEntityModelData.caps_freeVariable, "skinChar"));
			}
		}
		super.drawGuiContainerBackgroundLayer(f, i, j);
		//PFLM_ModelData drawModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		//PFLM_ModelDataMaster.instance.checkModelData(drawModelData);
	}

	@Override
	public void modelChange() {
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
		if (PFLM_ConfigData.isModelSize) {
			closePlayerToSpawn = true;
		}
		Modchu_AS.set(Modchu_AS.allModelInit, PFLM_Main.renderPlayerDummyInstance, thePlayer, true);
		setTextureValue();
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
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		switch(getChangeMode()) {
		case PFLM_GuiConstant.modeOffline:
			setTextureName(getTextureName());
			setTextureArmorName(getTextureArmorName());
			setColor(getColor());
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_skinMode, PFLM_ModelDataMaster.skinMode_offline);
			break;
		case PFLM_GuiConstant.modeOnline:
			//drawEntityModelData.modelMain = thePlayerModelData.modelMain;
			//drawEntityModelData.modelFATT = thePlayerModelData.modelFATT;
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_skinMode, PFLM_ModelDataMaster.skinMode_online);
			drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.newPlayerData(drawEntity, drawEntityModelData);
/*
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_textureName, thePlayerModelData.getCapsValue(thePlayerModelData.caps_textureName));
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_textureArmorName, thePlayerModelData.getCapsValue(thePlayerModelData.caps_textureArmorName));
			drawEntityModelData.setCapsValue(drawEntityModelData.caps_maidColor, thePlayerModelData.getCapsValueInt(thePlayerModelData.caps_maidColor));
*/
			break;
		}
		if (getChangeMode() != PFLM_GuiConstant.modeOnline) drawEntityModelData.setCapsValue(drawEntityModelData.caps_freeVariable, "skinChar", false);
		super.setTextureValue();
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
		PFLM_ModelData thePlayerModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_modelScale, getScale());
		boolean isSitting = thePlayerModelData.getCapsValueBoolean(drawEntityModelData.caps_isSitting);
		//Modchu_Debug.mDebug("modelDataSetting isSitting="+isSitting);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_isSitting, isSitting);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_isRiding, isSitting);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_isSneak, thePlayerModelData.getCapsValue(thePlayerModelData.caps_isSneak));
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_isSleeping, thePlayerModelData.getCapsValue(thePlayerModelData.caps_isSleeping));
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_freeVariable, "showArmor", showArmor);
		drawEntitySetFlag = true;
		Modchu_Debug.mDebug("setTextureValue end. ");
	}

	private void setDrawMuitiTextureValue() {
		setcheck();
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureName, PFLM_Main.textureList.get(setModel));
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, PFLM_Main.textureList.get(setArmor));
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_maidColor, setColor);
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_skinMode, PFLM_ModelDataMaster.skinMode_offline);
		drawMuitiEntitySetFlag = true;
	}

	@Override
	public void selected(String textureName, String textureArmorName, int color, boolean armorMode) {
		super.selected(textureName, textureArmorName, color, armorMode);
		//Modchu_Debug.mDebug("PFLM_GuiMaster selected getTextureName()="+getTextureName()+" textureName="+textureName);
		if (armorMode) {
			setTextureName(getTextureName());
			setColor(getColor());
		}
		//Modchu_Debug.mDebug("PFLM_GuiMaster selected");
	}

	public void setTextureArmorPackege(int i, boolean b) {
		PFLM_ModelData drawMuitiModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawMuitiEntity);
		setArmorNumber(setModel);
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName));
		super.setTextureArmorPackege(i);
		String s = (String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureArmorName);
		Modchu_Debug.mDebug("setTextureArmorPackege s=" + s);
		Modchu_Debug.mDebug("setTextureArmorPackege specificationArmorCheckBoolean=" + PFLM_Main.specificationArmorCheckBoolean((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName)));
		if (s != null && !s.isEmpty() && s.equalsIgnoreCase("erasearmor")) {
			s = b | PFLM_Main.specificationArmorCheckBoolean((String) drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName)) ? "x32_QB" : "default";
			drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, s);
		}
		if (ModchuModel_Main.checkTextureArmorPackege(s) != null) ;
		else drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_textureArmorName, drawMuitiModelData.getCapsValue(drawMuitiModelData.caps_textureName));
	}

	@Override
	public void setArmorTextureValue() {
		if (getTextureArmorName() == null) setTextureArmorName(getTextureName());
		if (ModchuModel_Main.checkTextureArmorPackege(getTextureArmorName()) == null) {
			setTextureArmorName(PFLM_Main.getArmorName(getTextureName(), 1));
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
				&& PFLM_ConfigData.isModelSize) {
			setPositionCorrection();
		}
		TempYOffsetInit = false;
		drawMuitiEntitySetFlag = true;
	}

	public void setPositionCorrection() {
		PFLM_Main.setSize(0.6F, 1.8F);
		PFLM_Main.resetHeight();
		double d = tempYOffset - PFLM_Main.getYOffset() - 0.5D;
		PFLM_Main.setPositionCorrection(0.0D, -d, 0.0D);
	}

	public void imageMultiTagSetSave(int c) {
		BufferedImage image = null;
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
						| getChangeMode() == PFLM_GuiConstant.modeOffline) {
					image = readTextureImage(ModchuModel_Main.textureManagerGetTexture(getTextureName(), c));
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
			if (PFLM_Main.bipedCheck(drawEntity)
					| getChangeMode() == PFLM_GuiConstant.modeOnline) {
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
			int rgb = Modchu_RenderEngine.instance.colorRGB(255, 0, 0);
			image.setRGB(setX, setY, rgb);

			//ローカルテクスチャを使用するかどうか判断用ドット
			rgb = result ? Modchu_RenderEngine.instance.colorRGB(255, 255, 0) : Modchu_RenderEngine.instance.colorRGB(255, 0, 255);
			setY = 1;
			if (rightBottomSet) setY = 30;
			image.setRGB(setX, setY, rgb);

			//r = color , g = ArmorName , b = textureName
			int r1 = result ? 255 - c : 255 - drawMuitiModelData.getCapsValueInt(drawMuitiModelData.caps_maidColor);
			int g1 = 255 - t2;
			int b1 = 255 - t;
			rgb = Modchu_RenderEngine.instance.colorRGB(r1, g1, b1);
			setX = image.getWidth() - 2;
			setY = 0;
			if (rightBottomSet) setY = image.getHeight() - 1;
			image.setRGB(setX, setY, rgb);
			Modchu_Debug.mDebug("imageMultiTagSetSave r1=" + r1 + " g1=" + g1 + " b1=" + b1);

			//handedness r = 255 right , r = 0 left , else Random
			Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
			PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(thePlayer);
			int handedness = modelData.getCapsValueInt(Modchu_IEntityCapsBase.caps_dominantArm);
			r1 = handedness == 0 ? 255 : handedness == 1 ? 0 : 128;
			//g = PFLM_ConfigData.modelScale
			g1 = 255 - ((int) (getScale() / (0.9375F / 24F)));
			if (g1 > 255) g1 = 255;
			if (g1 < 0) g1 = 0;
			b1 = 255;
			rgb = Modchu_RenderEngine.instance.colorRGB(r1, g1, b1);
			setX = image.getWidth() - 2;
			setY = 1;
			if (rightBottomSet) setY = 30;
			image.setRGB(setX, setY, rgb);

			Object resourceLocation = ModchuModel_Main.textureManagerGetTexture(s1, c);
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
				result = ImageIO.write(image, "png", new File(Modchu_AS.getFile(Modchu_AS.minecraftMcDataDir) + s));
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
		BufferedImage image = null;
		InputStream inputStream = null;
		if (Modchu_Main.getMinecraftVersion() > 159) {
			try {
				inputStream = Modchu_AS.getInputStream(Modchu_AS.resourceManagerInputStream, o);
				image = ImageIO.read(inputStream);
			} finally {
				if (inputStream != null) inputStream.close();
			}
		} else {
			image = ImageIO.read(Modchu_AS.getFile(Modchu_AS.getResource, o));
		}
		return fullColorConversion(image);
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
		drawMuitiModelData.setCapsValue(drawMuitiModelData.caps_skinMode, PFLM_ModelDataMaster.skinMode_offline);
	}

	@Override
	public String getTextureName() {
		return PFLM_ConfigData.textureName;
	}

	@Override
	public void setTextureName(String s) {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_textureName, s);
		PFLM_ConfigData.textureName = s;
		Modchu_Debug.mDebug("PFLM_GuiMaster setTextureName s="+s);
	}

	@Override
	public String getTextureArmorName() {
		return PFLM_ConfigData.textureArmorName;
	}

	@Override
	public void setTextureArmorName(String s) {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_textureArmorName, s);
		PFLM_ConfigData.textureArmorName = s;
	}

	@Override
	public int getColor() {
		return PFLM_ConfigData.maidColor;
	}

	@Override
	public void setColor(int i) {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_maidColor, i & 0xf);
		PFLM_ConfigData.maidColor = i;
	}

	@Override
	public float getScale() {
		return PFLM_ConfigData.modelScale;
	}

	@Override
	public void setScale(float f) {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		drawEntityModelData.setCapsValue(drawEntityModelData.caps_modelScale, f);
		PFLM_ConfigData.modelScale = f;
	}

	protected int getChangeMode() {
		return PFLM_ConfigData.changeMode;
	}

	@Override
	public void setChangeMode(int i) {
		PFLM_ConfigData.changeMode = i;
	}
/*
	public static ConcurrentHashMap<String, Boolean> getShowPartsMap() {
		return !partsInitFlag ? PFLM_Config.getConfigShowPartsMap(getTextureName(), getColor(), armorType)
				: partsSetDefault ? PFLM_Gui.defaultParts : PFLM_Gui.parts;
	}
*/
}
