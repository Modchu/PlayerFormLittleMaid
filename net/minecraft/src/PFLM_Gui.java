package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_Gui extends PFLM_GuiModelSelectBase {

	private static BufferedImage bufferedimage;
	private static BufferedImage bufferedimage1;
	private static String modelArmorName = null;
	private static String tagSetFileName = null;
	private static double tempYOffset;
	private static boolean imageWriteComplete = false;
	private static boolean imageWriteFail = false;
	private static boolean bufferedimageMode = false;
	private static boolean TempYOffsetInit = false;
	private static int scrollY = 0;
	private static int armorType = 0;
	private static int showPartsListSize = 0;
	protected static boolean closePlayerToSpawn = false;
	protected static boolean modelScaleButton = false;
	protected static boolean result = false;
	public static ConcurrentHashMap<String, Boolean> parts;
	public static ConcurrentHashMap<String, Boolean> defaultParts;
	public static ConcurrentHashMap<Integer, String> guiPartsNemeMap;
	public static boolean colorReverse = false;
	public static boolean partsButton = false;
	public static boolean partsInitFlag = false;
	public static boolean partsSaveFlag = false;
	public static boolean partsSetDefault = false;
	public static float modelScale = 0.0F;
	public static int partsSetFlag = 0;
	public static int setModel = 0;
	public static int setArmor = 0;
	public static boolean noSaveFlag = false;
	public static final int modeOffline = 0;
	public static final int modeOnline = 1;
	public static final int modeRandom = 2;
	private static final int maxChangeMode = 3;
	private EntityPlayer thePlayer;
	private PFLM_ModelData modelData;

	public PFLM_Gui(World world) {
		this(null, world);
	}

	public PFLM_Gui(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		modelData = mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.getPlayerData(thePlayer);
		PFLM_RenderPlayerDummyMaster.showArmor = true;
		parts = new ConcurrentHashMap();
		defaultParts = new ConcurrentHashMap();
		guiPartsNemeMap = new ConcurrentHashMap();
	}

	@Override
	public void initGui() {
		buttonList.clear();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		showPartsListSize = 0;
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 200, x, y + 100, 75, 20, "Save" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 13, x, y + 70, 75, 15, "ChangeMode" }));
		if (!mod_Modchu_ModchuLib.modchu_Main.isRelease) buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 59, x + 75, y + 40, 75, 15, "CustomModel" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 58, x + 75, y + 55, 75, 15, "Handedness" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 57, x + 75, y + 70, 75, 15, "KeyControls" }));
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeRandom) {
			if(mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeOnline) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 56, x - 10, y + 10, 85, 15, "ModelListSelect" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 50, x + 40, y + 25, 15, 15, "<" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 51, x + 55, y + 25, 15, 15, ">" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 52, x + 40, y + 40, 15, 15, "-" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 53, x + 55, y + 40, 15, 15, "+" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 54, x + 40, y + 55, 15, 15, "<" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 55, x + 55, y + 55, 15, 15, ">" }));
			}
			if(mod_PFLM_PlayerFormLittleMaid.pflm_main.guiMultiPngSaveButton
					&& !partsButton
					&& !modelScaleButton) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 12, x + 75, y + 100, 80, 20, "MultiPngSave" }));
				bufferedimageMode = false;
				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.bipedCheck()
						| mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOnline) {
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 15, 50, y + 55, 15, 15, "<" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 14, 65, y + 55, 15, 15, ">" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 17, 50, y + 72, 15, 15, "-" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 16, 65, y + 72, 15, 15, "+" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 19, 50, y + 89, 15, 15, "<" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 18, 65, y + 89, 15, 15, ">" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 20, 70, y + 107, 30, 15, ""+PFLM_RenderPlayerDummyMaster.showArmor }));
					if (bufferedimage == null) {
				    	PFLM_ModelData modelData = PFLM_RenderPlayerMaster.playerData.get(thePlayer);
						bufferedimage = PFLM_RenderPlayerMaster.getOnlineSkin(modelData, thePlayer);
						if (bufferedimage == null) {
							Object t = null;
							if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOnline) {
								if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isRelease()) {
									setTextureName("_Biped");
									setTextureArmorName("_Biped");
									t = mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.steveTexture;
/*
									if (url != null) {
										mod_PFLM_PlayerFormLittleMaid.pflm_main.setSkinUrl(null);
										mod_PFLM_PlayerFormLittleMaid.pflm_main.setTexture(t);
									}
*/
									//String t = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureManagerGetTextureName(getTextureName(), getColor());
									mod_PFLM_PlayerFormLittleMaid.pflm_main.texture = t;
								} else {
									mod_PFLM_PlayerFormLittleMaid.pflm_main.texture = t = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(getTextureName(), getColor());
									if (t != null) {
									} else {
										setTextureName("default");
										setTextureArmorName("default");
										setColor(0);
										mod_PFLM_PlayerFormLittleMaid.pflm_main.texture = t = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(getTextureName(), getColor());
									}
								}
								Modchu_Debug.mDebug("mod_PFLM_PlayerFormLittleMaid.pflm_main.texture="+mod_PFLM_PlayerFormLittleMaid.pflm_main.texture);
/*
								if (bufferedimage1 == null) {
									Modchu_Debug.mDebug("GUI t="+t);
									if (t != null) {
									} else {
										getTextureName() = "default";
										getTextureArmorName() = "default";
										getColor() = 0;
										mod_PFLM_PlayerFormLittleMaid.pflm_main.texture = t = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(getTextureName(), getColor());
									}
									try
									{
										bufferedimage1 = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ?
												ImageIO.read((File) Modchu_Reflect.invokeMethod(Class.class, "getResource", new Class[]{ String.class }, Modchu_Reflect.loadClass("Minecraft"), new Object[]{ (String) Modchu_Reflect.invokeMethod("ResourceLocation", "func_110624_b", t)})) :
												ImageIO.read((URL) Modchu_Reflect.invokeMethod(Class.class, "getResource", new Class[]{ String.class }, Modchu_Reflect.loadClass("Minecraft"), new Object[]{ t }));
									}
									catch (Exception e2)
									{
										Modchu_Debug.lDebug(""+getClass().getName(), "initGui image read miss. File = "+t, 2, e2);
										e2.printStackTrace();
									}
								} else {
									BufferedImage image = bufferedimage1;
									bufferedimage = image;
								}
*/
							}
						}
						if (bufferedimage != null
								&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOnline) {
							Object[] s = mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.checkimage(bufferedimage);
							modelArmorName = (String) s[2];
							boolean returnflag = (Boolean) s[5];
							if (!returnflag) {
								setTextureName((String) s[4]);
								setTextureArmorName((String) s[2]);
								setColor((Integer) s[3]);
								int s2 = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.indexOf(getTextureName());
								setModel = s2 > -1 ? s2 : 0;
								s2 = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.indexOf(getTextureArmorName());
								setArmor = s2 > -1 ? s2 : 0;
							}
							PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(setModel));
							PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(setArmor));
						}
						mod_PFLM_PlayerFormLittleMaid.pflm_main.removePlayer();
					} else {
						bufferedimageMode = mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOnline ? true : false;
					}
					setcheck();
				}
			}
		}
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange
				&& modelScaleButton) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 3, width / 2 - 170, height / 2 - 40, 50, 20, "Default" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 4, width / 2 - 120, height / 2 - 40, 30, 20, "UP" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 5, width / 2 - 200, height / 2 - 40, 30, 20, "Down" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 6, x + 75, y + 25, 75, 15, "Close" }));
		} else {
			if(!partsButton) {
				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange) buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 7, x + 75, y + 25, 75, 15, "ScaleChange" }));
				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isMulti
						| !mod_PFLM_PlayerFormLittleMaid.pflm_main.isRelease()) {
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 30, x + 75, y - 5, 75, 15, "othersPlayer" }));
				}
			}
		}
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeRandom) {
			if(partsButton) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 10, x + 75, y + 10, 75, 15, "Close" }));
				partsButtonAdd();
			} else {
				if(!modelScaleButton) buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 11, x + 75, y + 10, 75, 15, "Customize" }));
			}
		}
		if(getScale() == 0.0F) {
			setScale(mod_PFLM_PlayerFormLittleMaid.pflm_main.getModelScale());
		}
		guiMode = true;
	}

	public void setTempYOffset(double d) {
		tempYOffset = d;
	}

	public void partsButtonAdd() {
		int j = 0;
		String s;
		int x;
		int y;
		//Modchu_Debug.mDebug("PartsButtonAdd "+buttonList.size());
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 99, width / 2 - 200, 12 - scrollY, 70, 15, "Default" }));
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.guiShowArmorSupport) {
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 97, width / 2 - 130, 12 - scrollY, 15, 15, "-" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 98, width / 2 - 115, 12 - scrollY, 15, 15, "+" }));
		}
		String s2 = null;
		boolean b = true;
		guiPartsNemeMap.clear();
		defaultParts.clear();
		Object model = mod_PFLM_PlayerFormLittleMaid.pflm_main.getModel(armorType);
		ConcurrentHashMap<Integer, String> showPartsNemeMap = PFLM_Config.getConfigShowPartsNemeMap(getTextureName(), armorType);
		ConcurrentHashMap<String, String> showPartsReneme = PFLM_Config.getConfigShowPartsRenemeMap(model, getTextureName(), armorType);
		ConcurrentHashMap<Integer, String> showPartsHideMap = PFLM_Config.getConfigShowPartsHideMap(model, getTextureName(), armorType);
		//if (showPartsNemeMap != null) Modchu_Debug.mDebug("showPartsNemeMap.size()="+showPartsNemeMap.size());
		//else Modchu_Debug.mDebug("showPartsNemeMap.size()=null !!");
		//if (showPartsReneme != null) Modchu_Debug.mDebug("showPartsReneme.size()="+showPartsReneme.size());
		//else Modchu_Debug.mDebug("showPartsReneme.size()=null !!");
		//if (showPartsHideMap != null) Modchu_Debug.mDebug("showPartsHideMap.size()="+showPartsHideMap.size());
		//else Modchu_Debug.mDebug("showPartsHideMap.size()=null !!");
		List<String> list = new ArrayList();
		MMM_ModelRenderer modelRenderer = null;
		Field f = null;
		int i1 = 0;
		for (int i = 0; i < showPartsNemeMap.size(); i++) {
			s2 = showPartsNemeMap.get(i);
			if (s2 != null) ;else break;
			if (list.contains(s2)) continue;
			list.add(s2);
			if (!partsSetDefault
					&& parts != null
					&& !parts.isEmpty()
					&& parts.containsKey(s2)) {
				b = parts.get(s2);
			} else {
				if (partsSetDefault) b = PFLM_Config.getDefaultShowPartsMapBoolean(getTextureName(), s2, armorType);
				else b = true;
			}
			s = b ? "ON" : "OFF";
			j = i1 % 2 == 0 ? 0 : 1;
			x = width / 2 - 200 + (j * 70);
			y = j == 0 ? 28 + (15 * i1 / 2) - scrollY : 36 + (15 * i1 / 2 - 15) - scrollY;
			if (showPartsHideMap != null
				&& showPartsHideMap.containsValue(s2)) {
				//Modchu_Debug.mDebug("showPartsHideMap s2="+s2);
			} else {
				guiPartsNemeMap.put(i1, s2);
				defaultParts.put(s2, true);
				if (showPartsReneme != null
					&& s2 != null
					&& showPartsReneme.containsKey(s2)) {
					s2 = showPartsReneme.get(s2);
				}
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 10000 + i1, x, y, 70, 15, s2 + ":" + s }));
				i1++;
			}
		}
		showPartsListSize = i1 / 2;
		if (showPartsListSize < 0) showPartsListSize = 0;
	}

	private void partsInit() {
		//Modchu_Debug.mDebug("PFLM_Gui partsInit() getTextureName()="+getTextureName());
		parts = new ConcurrentHashMap();
		String s2 = null;
		boolean b;
		Field f = null;
		List<String> list = new ArrayList();
		MMM_ModelRenderer modelRenderer = null;
		Object model = mod_PFLM_PlayerFormLittleMaid.pflm_main.getModel(armorType);
		ConcurrentHashMap<Integer, String> showPartsNemeMap = PFLM_Config.getConfigShowPartsNemeMap(getTextureName(), armorType);
		ConcurrentHashMap<String, Boolean> showPartsList = PFLM_Config.getConfigShowPartsMap(getTextureName(), getColor(), armorType);
		ConcurrentHashMap<String, Field> modelRendererMap = PFLM_Config.getConfigModelRendererMap(model, getTextureName(), armorType);
		ConcurrentHashMap<String, Boolean> defaultShowPartsMap = PFLM_Config.getDefaultShowPartsMap(getTextureName(), armorType);
		if (showPartsList != null) Modchu_Debug.mDebug("showPartsList.size()="+showPartsList.size());
		else Modchu_Debug.mDebug("showPartsList.size()=null !!");
		Modchu_Debug.mDebug("showPartsNemeMap.size()="+showPartsNemeMap.size());
		for (int i = 1; i < showPartsNemeMap.size(); i++) {
			s2 = showPartsNemeMap.get(i - 1);
			if (s2 != null) ;else break;
			if (list.contains(s2)) continue;
			list.add(s2);
			if (showPartsList != null
					&& !showPartsList.isEmpty()
					&& showPartsList.containsKey(s2)) {
				b = showPartsList.get(s2);
			} else {
				b = true;
				if (modelRendererMap != null
						&& !modelRendererMap.isEmpty()) ;else break;
				if (modelRendererMap.containsKey(s2)) f = modelRendererMap.get(s2);
				else f = null;
				if (f != null) {
					//Modchu_Debug.mDebug("PFLM_Gui partsButtonAdd() modelRendererMap f != null");
					try {
						Object o = f.get(model);
						modelRenderer = o != null ? (MMM_ModelRenderer) o : null;
						if (modelRenderer != null) {
							//Modchu_Debug.mmlDebug("PFLM_Gui partsInit() modelRendererMap f.getName()="+f.getName()+" modelRenderer.showModel="+modelRenderer.showModel);
							if (defaultShowPartsMap != null
									&& defaultShowPartsMap.containsKey(s2)) b = defaultShowPartsMap.get(s2);
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

	public void updateScreen() {
		super.updateScreen();
		if(partsSetFlag == 2) {
			partsSetFlag = 3;
			//if (!partsSetDefault) PFLM_Config.loadShowModelList(mod_PFLM_PlayerFormLittleMaid.pflm_main.showModelList);
			//Modchu_Debug.mDebug("updateScreen()");
			if (!partsInitFlag) {
				partsInitFlag = true;
				partsInit();
				partsSetFlag = 1;
			}
			initGui();
		}
	}

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
    	if(!guibutton.enabled)
    	{
    		return;
    	}
    	//isModelSize Default
    	if(guibutton.id == 3)
    	{
    		setScale(mod_PFLM_PlayerFormLittleMaid.pflm_main.getModelScale());
    		return;
    	}
    	//isModelSize UP
    	if(guibutton.id == 4)
    	{
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			setScale(getScale() <= 9.5 ? getScale() + 0.5F : 10.0F);
    		} else {
    			if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
    				setScale(getScale() <= 9.99 ? getScale() + 0.01F : 10.0F);
    			} else {
    				setScale(getScale() <= 9.9 ? getScale() + 0.1F : 10.0F);
    			}
    		}
    		return;
    	}
    	//isModelSize Down
    	if(guibutton.id == 5)
    	{
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			setScale(getScale() > 0.5 ? getScale()  - 0.5F : 0.01F);
    		}
    		else if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
    			setScale(getScale() > 0.01 ? getScale()  - 0.01F : 0.01F);
    		} else {
    			setScale(getScale() > 0.1 ? getScale()  - 0.1F : 0.01F);
    		}
    		return;
    	}
    	//ScaleChange Close
    	if(guibutton.id == 6)
    	{
    		modelScaleButton = false;
    		initGui();
    		return;
    	}
    	//ScaleChange Open
    	if(guibutton.id == 7)
    	{
    		modelScaleButton = true;
    		initGui();
    		return;
    	}
    	//Customize Close
    	if(guibutton.id == 10)
    	{
    		partsButton = false;
    		initGui();
    		return;
    	}
    	//CustomizeOpen
    	if(guibutton.id == 11)
    	{
    		partsButton = true;
    		initGui();
    		return;
    	}
    	//MultiPngSave
    	if(guibutton.id == 12)
    	{
    		imageMultiTagSetSave(getColor());
    		//for(int i = 0 ; i < 16 ; i++) {
    			//imageMultiTagSetSave(i);
    		//}
    		return;
    	}
    	//SkinModeChange
    	if(guibutton.id == 13)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode++;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode >= maxChangeMode) mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode = 0;
    		if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.changeModeButton
    				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOnline) {
    			mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode++;
        		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode >= maxChangeMode) mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode = 0;
    		}
    		switch (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode) {
    		case modeOffline:
    			break;
    		case modeOnline:
    			//mod_Modchu_ModchuLib.modchu_Main.getMinecraft().renderGlobal.onEntityDestroy(thePlayer);
    			//mod_Modchu_ModchuLib.modchu_Main.getMinecraft().renderGlobal.onEntityCreate(thePlayer);
    			break;
    		case modeRandom:
    			break;
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOnline) drawEntitySetFlag = true;
    		else mod_PFLM_PlayerFormLittleMaid.pflm_main.removePlayer();
    		bufferedimage = null;
    		partsSetFlag = 1;
    		//showModelFlag = true;
    		noSaveFlag = true;
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setModel+
    	if(guibutton.id == 14)
    	{
    		setModel++;
    		boolean b = false;
    		while(b == false
    				&& setModel < mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size()) {
    			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(setModel));
    			Object ltb = mod_Modchu_ModchuLib.modchu_Main.checkTexturePackege((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName), PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor));
    			if (ltb != null) {
    				b = true;
    			} else {
    				setModel++;
    			}
    		}
    	}
    	//guiMultiPngSaveButton setModel-
    	if(guibutton.id == 15)
    	{
    		setModel--;
    		boolean b = false;
    		while(b == false
    				&& setModel > -1) {
    			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(setModel));
    			Object ltb = mod_Modchu_ModchuLib.modchu_Main.checkTexturePackege((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName), PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor));
    			if (ltb != null) {
    				b = true;
    			} else {
    				setModel--;
    			}
    		}
    	}
    	if(guibutton.id == 14
    			| guibutton.id == 15)
    	{
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		drawEntitySetFlag = true;
    		setTextureArmorPackege(1, guibutton.id == 15);
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setColor+
    	if(guibutton.id == 16)
    	{
    		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor) + 1);
    		colorReverse = false;
    	}
    	//guiMultiPngSaveButton setColor-
    	if(guibutton.id == 17)
    	{
    		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor) - 1);
    		colorReverse = true;
    	}
    	if(guibutton.id == 16
    			| guibutton.id == 17)
    	{
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_changeColor, modelData);
    		drawEntitySetFlag = true;
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setArmor+
    	if(guibutton.id == 18)
    	{
    		setArmor++;
    		setcheck();
    		boolean b = false;
    		while(b == false
    				&& setArmor < mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size()) {
    			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(setArmor));
    			Object ltb = mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName));
    			if (ltb != null) {
    				b = true;
    			} else {
    				setArmor++;
    			}
    		}
    	}
    	//guiMultiPngSaveButton setArmor-
    	if(guibutton.id == 19)
    	{
    		setArmor--;
    		setcheck();
    		boolean b = false;
    		while(b == false
    				&& setArmor < mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size()) {
    			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(setArmor));
    			Object ltb = mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName));
    			if (ltb != null) {
    				b = true;
    			} else {
    				setArmor--;
    			}
    		}
    	}
    	if(guibutton.id == 18
    			| guibutton.id == 19)
    	{
    		setcheck();
    		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.get(setArmor));
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		drawEntitySetFlag = true;
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton ShowArmor
    	if(guibutton.id == 20)
    	{
    		PFLM_RenderPlayerDummyMaster.showArmor = !PFLM_RenderPlayerDummyMaster.showArmor;
    		initGui();
    		return;
    	}
    	//OthersPlayer
    	if(guibutton.id == 30)
    	{
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiOthersPlayer(this, popWorld) });
    	}
    	//ModelChange
    	if(guibutton.id == 50
    			| guibutton.id == 51) {
    		String[] s0 = mod_PFLM_PlayerFormLittleMaid.pflm_main.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), guibutton.id == 50 ? 1 : 0, false);
    		setTextureName(s0[0]);
    		setTextureArmorName(s0[1]);
    		modelChange();
    		return;
    	}
    	//ColorChange
    	if(guibutton.id == 52) {
    		setColor(getColor() - 1);
    		colorReverse = true;
    	}
    	if(guibutton.id == 53) {
    		setColor(getColor() + 1);
    		colorReverse = false;
    	}
    	if(guibutton.id == 52
    			| guibutton.id == 53)
    	{
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.setMaidColor(getColor());
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.setTextureResetFlag(true);
    		partsSetFlag = 1;
    		//showModelFlag = true;
    		partsInitFlag = false;
    		partsSaveFlag = false;
    		partsSetDefault = false;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		noSaveFlag = true;
    		PFLM_RenderPlayerMaster.getPlayerData(thePlayer).setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, getColor());
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.changeColor((EntityPlayer) null);
    		return;
    	}
    	//ArmorChange
    	if(guibutton.id == 54
    			| guibutton.id == 55) {
    		String[] s0 = mod_PFLM_PlayerFormLittleMaid.pflm_main.setTexturePackege(getTextureName(), getTextureArmorName(), getColor(), guibutton.id == 54 ? 1 : 0, true);
    		setTextureArmorName(s0[1]);
    		modelData.setCapsValue(modelData.caps_textureArmorName, getTextureArmorName());
    		Modchu_Debug.mDebug("ArmorChange getTextureArmorName()="+getTextureArmorName());
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize) {
    			closePlayerToSpawn = true;
    		}
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.setResetFlag(true);
    		partsSetFlag = 1;
    		partsInitFlag = false;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		noSaveFlag = true;
    		return;
    	}
    	//ModelListSelect
    	if(guibutton.id == 56) {
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiModelSelect(this, popWorld, false, getColor()) });
    		//mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 0));
    		return;
    	}
    	//KeyControls
    	if(guibutton.id == 57) {
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiKeyControls(this, popWorld) });
    		//mc.displayGuiScreen(new PFLM_GuiKeyControls(this, popWorld));
    		return;
    	}
    	//Handedness
    	if(guibutton.id == 58) {
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode--;
    		} else {
    			mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode++;
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode < -1) mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode = 1;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode > 1) mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode = -1;
    		mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayer.setHandedness(thePlayer, mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode);
    		return;
    	}
    	//CustomModel
    	if(guibutton.id == 59) {
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiCustomModel(this, popWorld) });
    		//mc.displayGuiScreen(new PFLM_GuiCustomModel(this, popWorld));
    		return;
    	}
    	//Customize ArmorType
    	if(guibutton.id == 97) {
    		armorType--;
    		if (armorType == 1) armorType = 0;
    	}
    	if(guibutton.id == 98) {
    		armorType++;
    		if (armorType == 1) armorType = 2;
    	}
    	if(guibutton.id == 97
    			| guibutton.id == 98) {
    		if (armorType < 0) armorType = 3;
    		if (armorType > 3) armorType = 0;
    		partsSetFlag = 1;
    		partsInitFlag = false;
    		return;
    	}
    	//Customize Default
    	if(guibutton.id == 99)
    	{
    		parts = new ConcurrentHashMap();
    		PFLM_Config.setConfigShowPartsMap(getTextureName(), getColor(), armorType, parts);
    		partsSetDefault = true;
    		modelChange();
    		return;
    	}
    	//PartsButton
    	if(guibutton.id >= 10000
    			&& guibutton.id <= 19999)
    	{
    		partsSetDefault = false;
    		int i = guibutton.id - 10000;
    		String s = null;
    		if (!guiPartsNemeMap.isEmpty()
    				&& guiPartsNemeMap.containsKey(i)) {
    			s = guiPartsNemeMap.get(i);
    		}
    		if (s != null) {
    			if (parts != null) {
    				boolean b = false;
    				if (!parts.isEmpty()
    						&& parts.containsKey(s)) b = !parts.get(s);
    				parts.put(s, b);
    			} else {
    				parts = new ConcurrentHashMap();
    				parts.put(s, false);
    			}
    		}
    		PFLM_Config.setConfigShowPartsMap(getTextureName(), getColor(), armorType, parts);
    		partsSaveFlag = true;
    		partsSetFlag = 1;
    		return;
    	}
    	//Save
    	if(guibutton.id == 200)
    	{
    		PFLM_Config.clearCfgData();
    		if (partsSaveFlag) PFLM_Config.setConfigShowPartsMap(getTextureName(), getColor(), armorType, parts);
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.saveParamater();
    		PFLM_Config.loadConfig(mod_PFLM_PlayerFormLittleMaid.pflm_main.showModelList, mod_PFLM_PlayerFormLittleMaid.pflm_main.cfgfile);
    		PFLM_Config.loadShowModelList(mod_PFLM_PlayerFormLittleMaid.pflm_main.showModelList);
    		mod_PFLM_PlayerFormLittleMaid.pflm_main.clearPlayers();
    		noSaveFlag = false;
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ null });
    		//mc.displayGuiScreen(null);
    		return;
    	}
    }

    @Override
    public void handleMouseInput() {
    	super.handleMouseInput();
    	if(partsButton) {
    	// ホイールの獲得
    		int i = Mouse.getEventDWheel();
    		if(i != 0) {
    			scrollY -= i * 0.25;
    			scrollY = scrollY < 0 ? 0 : scrollY;
    			scrollY = scrollY > showPartsListSize * 15 ? showPartsListSize * 15 : scrollY;
    			initGui();
    		}
    	}
    }

    @Override
    protected void keyTyped(char c, int i)
    {
    	if(i == 200) {
    		scrollY += 30;
    		scrollY = scrollY > showPartsListSize * 15 ? showPartsListSize * 15 : scrollY;
    		initGui();
    	}
    	if(i == 208) {
    		scrollY -= 30;
    		scrollY = scrollY < 0 ? 0 : scrollY;
    		initGui();
    	}
    	super.keyTyped(c, i);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
    	int xSize = 80;
    	int ySize = 50;
    	int guiLeft = width / 2 - xSize + 30;
    	int guiTop = height / 2 - (ySize / 2) - 20;
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.newRelease
    			&& !partsButton
    			&& !modelScaleButton) {
    		fontRenderer.drawString("PlayerFormLittleMaid", 10, 10, 0xffffff);
    		fontRenderer.drawString((new StringBuilder()).append("newVersion v").append(mod_PFLM_PlayerFormLittleMaid.pflm_main.newVersion).append(" Release!").toString(), 10, 20, 0xffffff);
    	}
    	if (mod_Modchu_ModchuLib.modchu_Main.newRelease
    			&& !partsButton
    			&& !modelScaleButton) {
    		fontRenderer.drawString("ModchuLib", 10, 30, 0xffffff);
    		fontRenderer.drawString((new StringBuilder()).append("newVersion v").append(mod_Modchu_ModchuLib.modchu_Main.newVersion).append(" Release!").toString(), 10, 40, 0xffffff);
    	}
    	StringBuilder s = (new StringBuilder()).append("TextureName : ");
    	StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
    	StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
    	int x = 0;
    	//int x = partsButton | !getModelType().startsWith("Biped") ? 0 : 150;
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeRandom) {
    		if(mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeOnline) {
    			s = s.append(getTextureName());
    			fontRenderer.drawString(s.toString(), guiLeft - x, guiTop + 90, 0xffffff);
    			s2 = s2.append(getColor());
    			fontRenderer.drawString(s2.toString(), guiLeft - x, guiTop + 100, 0xffffff);
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.bipedCheck()) {
    		} else {
    			s1 = s1.append(getTextureArmorName());
    			fontRenderer.drawString(s1.toString(), guiLeft - x, guiTop + 110, 0xffffff);
    		}
    	}
    	StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
    	s9 = s9.append(getChangeModeString(mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode));
    	fontRenderer.drawString(s9.toString(), guiLeft - x, guiTop + 140, 0xffffff);
    	StringBuilder s10 = (new StringBuilder()).append("Handedness : ");
    	s10 = s10.append(getHandednessModeString(mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode));
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.handednessMode == -1) s10 = s10.append(" Result : ").append(getHandednessModeString(modelData.getCapsValueInt(modelData.caps_dominantArm)));
    	fontRenderer.drawString(s10.toString(), guiLeft - x, guiTop + 150, 0xffffff);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeRandom
    			&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeOnline) {
    		fontRenderer.drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
    		fontRenderer.drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
    		fontRenderer.drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
    	}
    	if(partsButton) {
    		String s8 = "Parts showModel set";
    		s8 = (new StringBuilder()).append(s8).toString();
    		fontRenderer.drawString(s8, width / 2 - 180, -scrollY, 0xffffff);
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.guiShowArmorSupport) {
    			s8 = (new StringBuilder()).append("Type : ").append(getArmorTypeString(armorType)).toString();
    			fontRenderer.drawString(s8, width / 2 - 100, 15 - scrollY, 0xffffff);
    		}
    	}
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize
				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeOnline) {
    		float f1 = 0.5F;
    		float f2 = 1.35F;
    		float f3 = 1.17F;
    		if (!TempYOffsetInit) {
    			TempYOffsetInit = true;
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.gotchaNullCheck())
    				setTempYOffset(mod_PFLM_PlayerFormLittleMaid.pflm_main.getyOffset());
    		}
    		f1 = mod_PFLM_PlayerFormLittleMaid.pflm_main.getWidth();
    		f2 = mod_PFLM_PlayerFormLittleMaid.pflm_main.getHeight();
    		f3 = mod_PFLM_PlayerFormLittleMaid.pflm_main.getyOffset();
    		String s3 = "Size : Width = "+f1+" Height = "+f2;
    		s3 = (new StringBuilder()).append(s3).toString();
    		fontRenderer.drawString(s3, guiLeft, guiTop + 120, 0xffffff);
    		String s4 = "yOffset : "+f3;
    		s4 = (new StringBuilder()).append(s4).toString();
    		fontRenderer.drawString(s4, guiLeft, guiTop + 130, 0xffffff);
    	}
		if(mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange
				&& modelScaleButton) {
    		String s6 = "modelScale : "+getScale();
    		s6 = (new StringBuilder()).append(s6).toString();
    		fontRenderer.drawString(s6, guiLeft - 140, guiTop + 30, 0xffffff);
    		String s7 = "modelScaleChange";
    		s7 = (new StringBuilder()).append(s7).toString();
    		fontRenderer.drawString(s7, guiLeft - 140, guiTop - 5, 0xffffff);
    	}
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode != modeRandom) {
    		String s5;
    		if (imageWriteComplete) {
    			s5 = (new StringBuilder()).append(mod_PFLM_PlayerFormLittleMaid.pflm_main.textureSavedir).append(tagSetFileName).append(" Successfully written file.").toString();
    			fontRenderer.drawString(s5, 10, 10, 0xffffff);
    		}
    		if (imageWriteFail) {
    			s5 = (new StringBuilder()).append(mod_PFLM_PlayerFormLittleMaid.pflm_main.textureSavedir).append(tagSetFileName).append(" Failed to write file.").toString();
    			fontRenderer.drawString(s5, 10, 10, 0xffffff);
    		}
    		//Modchu_Debug.mDebug("mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode="+mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode);
    		int var4 = guiLeft;
    		int var5 = guiTop;
    		func_110423_a(var4 + 51, var5 + 75, 50, (float)(var4 + 51) - i, (float)(var5 + 75 - 50) - j, thePlayer);
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOnline
    				| mod_PFLM_PlayerFormLittleMaid.pflm_main.bipedCheck()
    				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.guiMultiPngSaveButton
    				&& !partsButton
    				&& !modelScaleButton) {
    			if (drawEntitySetFlag) {
    				setcheck();
    				if (drawEntity == null) drawEntity = new PFLM_EntityPlayerDummy(popWorld);
    				PFLM_RenderPlayerDummyMaster.allModelInit(drawEntity, false);
    				drawEntitySetFlag = false;
    			}
    			GL11.glPushMatrix();
    			GL11.glDisable(GL11.GL_LIGHTING);
    			fontRenderer.drawString("MultiTagSet", 15, height / 2 - 60, 0xffffff);
    			fontRenderer.drawString("Model", 15, height / 2 - 25, 0xffffff);
    			fontRenderer.drawString("Color", 15, height / 2 - 9, 0xffffff);
    			fontRenderer.drawString("Armor", 15, height / 2 + 8, 0xffffff);
    			fontRenderer.drawString("ShowArmor", 15, height / 2 + 25, 0xffffff);
    			fontRenderer.drawString("MultiTextureName : ", 10, guiTop + 90, 0xffffff);
    			fontRenderer.drawString((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName), 10, guiTop + 100, 0xffffff);
    			fontRenderer.drawString((new StringBuilder()).append("MultiColor : ").append(PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor)).toString(), 10, guiTop + 110, 0xffffff);
    			fontRenderer.drawString("MultiArmorName : ", 10, guiTop + 120, 0xffffff);
    			fontRenderer.drawString((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName), 10, guiTop + 130, 0xffffff);
    			//drawEntity.setPosition(thePlayer.posX , thePlayer.posY, thePlayer.posZ);
    			drawMobModel2(i, j, 120, height / 2 + 20, -90, -50, 30F, 0.0F, true);
    			GL11.glPopMatrix();
    		}
    	}
    }

    public static void func_110423_a(int par0, int par1, int par2, float par3, float par4, Entity par5EntityLivingBase)
    {
    	GL11.glPushMatrix();
    	GL11.glEnable(GL11.GL_COLOR_MATERIAL);
    	GL11.glTranslatef((float)par0, (float)par1, 50.0F);
    	GL11.glScalef((float)(-par2), (float)par2, (float)par2);
    	GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
    	Class EntityLivingBase = Modchu_Reflect.loadClass("EntityLivingBase");
    	float renderYawOffset = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70761_aq", "renderYawOffset", par5EntityLivingBase);
    	float prevRotationYawHead = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70758_at", "prevRotationYawHead", par5EntityLivingBase);
    	float rotationYawHead = (Float) Modchu_Reflect.getFieldObject(EntityLivingBase, "field_70759_as", "rotationYawHead", par5EntityLivingBase);
    	float var6 = renderYawOffset;
    	float var7 = par5EntityLivingBase.rotationYaw;
    	float var8 = par5EntityLivingBase.rotationPitch;
    	float var9 = prevRotationYawHead;
    	float var10 = rotationYawHead;
    	GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
    	RenderHelper.enableStandardItemLighting();
    	GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
    	GL11.glRotatef(-((float)Math.atan((double)(par4 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);

    	Modchu_Reflect.setFieldObject(EntityLivingBase, "field_70761_aq", "renderYawOffset", par5EntityLivingBase, (float)Math.atan((double)(par3 / 40.0F)) * 20.0F);
    	par5EntityLivingBase.rotationYaw = (float)Math.atan((double)(par3 / 40.0F)) * 40.0F;
    	par5EntityLivingBase.rotationPitch = -((float)Math.atan((double)(par4 / 40.0F))) * 20.0F;
    	Modchu_Reflect.setFieldObject(EntityLivingBase, "field_70759_as", "rotationYawHead", par5EntityLivingBase, par5EntityLivingBase.rotationYaw);
    	Modchu_Reflect.setFieldObject(EntityLivingBase, "field_70758_at", "prevRotationYawHead", par5EntityLivingBase, par5EntityLivingBase.rotationYaw);
    	GL11.glTranslatef(0.0F, par5EntityLivingBase.yOffset, 0.0F);
    	RenderManager.instance.playerViewY = 180.0F;
    	RenderManager.instance.renderEntityWithPosYaw(par5EntityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
    	Modchu_Reflect.setFieldObject(EntityLivingBase, "field_70761_aq", "renderYawOffset", par5EntityLivingBase, var6);
    	par5EntityLivingBase.rotationYaw = var7;
    	par5EntityLivingBase.rotationPitch = var8;
    	Modchu_Reflect.setFieldObject(EntityLivingBase, "field_70758_at", "prevRotationYawHead", par5EntityLivingBase, var9);
    	Modchu_Reflect.setFieldObject(EntityLivingBase, "field_70759_as", "rotationYawHead", par5EntityLivingBase, var10);
    	GL11.glPopMatrix();

    	RenderHelper.disableStandardItemLighting();
    	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    	OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

    }

    public void modelChange() {
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize) {
    		closePlayerToSpawn = true;
    	}
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.setResetFlag(true);
    	partsSetFlag = 1;
    	partsInitFlag = false;
    	partsSaveFlag = false;
    	partsSetDefault = false;
    	imageWriteComplete = false;
    	imageWriteFail = false;
    	noSaveFlag = true;
    	drawEntitySetFlag = true;
    	//Modchu_Debug.mDebug("modelChange()");
    }

    public void setTextureArmorPackege(int i, boolean b) {
    	setArmorNumber(setModel);
    	super.setTextureArmorPackege(i);
    	String s = (String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName);
    	if (s != null
    			&& !s.isEmpty()
    			&& s.equalsIgnoreCase("erasearmor")) {
    		s = b ? "x32_QB" : "default";
    		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
    	}
    	if (mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege(s) != null) ;else PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
    }

    @Override
    public void setArmorTextureValue() {
    	if (getTextureArmorName() == null) setTextureArmorName(getTextureName());
    	if (mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege(getTextureArmorName()) == null) {
    		setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName(getTextureName(), 1));
    	}
    }

    private static void setArmorNumber(int i) {
    	setArmor = i;
    }

    @Override
    public void onGuiClosed() {
    	super.onGuiClosed();
    	if (closePlayerToSpawn
    			&& mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize) {
    		setPositionCorrection();
    	}
    	TempYOffsetInit = false;
    	drawEntitySetFlag = true;
    }

    @Override
    public void memoryRelease() {
    	bufferedimage = null;
    	bufferedimage1 = null;
    	modelArmorName = null;
    	tagSetFileName = null;
    	parts = null;
    	defaultParts = null;
    	guiPartsNemeMap = null;
    }

    public static void setPositionCorrection() {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.setSize(0.6F, 1.8F);
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.resetHeight();
    	double d = tempYOffset - mod_PFLM_PlayerFormLittleMaid.pflm_main.getyOffset() - 0.5D;
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.setPositionCorrection(0.0D ,-d ,0.0D);
    }

    public void imageMultiTagSetSave(int c) {
    	BufferedImage image = null;
    	if(bufferedimageMode) {
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
    					| mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOffline) {
    				image = readTextureImage(mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(getTextureName(), c));
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
    		String s1 = (String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName);
    		String s0 = mod_Modchu_ModchuLib.modchu_Main.lastIndexProcessing(s1, "_");
    		boolean rightBottomSet = false;
    		if (s0.startsWith("Elsa")) rightBottomSet = true;
    		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.bipedCheck()
    				| mod_PFLM_PlayerFormLittleMaid.pflm_main.changeMode == modeOnline) {
    			result = false;
    		} else {
    			result = true;
    		}
    		String s2 = (String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName);
    		int t = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.indexOf(s1);
    		Modchu_Debug.mDebug("imageMultiTagSetSave t="+t);
    		int t2 = mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.indexOf(s2);
    		t = t < 0 ? 0 : t;
    		t2 = t2 < 0 ? 0 : t2;

    		//PFLM用か判断用ドット
    		int setX = 63;
    		int setY = 0;
    		if (rightBottomSet) setY = 31;
    		int rgb = colorRGB(255, 0, 0);
    		image.setRGB(setX,setY,rgb);

    		//ローカルテクスチャを使用するかどうか判断用ドット
    		rgb = result ? colorRGB(255, 255, 0) : colorRGB(255, 0, 255);
    		setY = 1;
    		if (rightBottomSet) setY = 30;
    		image.setRGB(setX,setY,rgb);

    		//r = color , g = ArmorName , b = textureName
    		int r1 = result ? 255 - c : 255 - PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor);
    		int g1 = 255 - t2;
    		int b1 = 255 - t;
    		rgb = colorRGB(r1,g1,b1);
    		setX = 62;
    		setY = 0;
    		if (rightBottomSet) setY = 31;
    		image.setRGB(setX,setY,rgb);
    		Modchu_Debug.mDebug("imageMultiTagSetSave r1="+r1+" g1="+g1+" b1="+b1);

    		//handedness r = 255 right , r = 0 left , else Random
    		int handedness = modelData.getCapsValueInt(modelData.caps_dominantArm);
    		r1 = handedness == 0 ? 255 : handedness == 1 ? 0 : 128;
    		//g = modelScale
    		g1 = 255 - ((int)(getScale() / (0.9375F / 24F)));
    		if (g1 > 255) g1 = 255;
    		if (g1 < 0) g1 = 0;
    		b1 = 255;
    		rgb = colorRGB(r1,g1,b1);
    		setX = 62;
    		setY = 1;
    		if (rightBottomSet) setY = 30;
    		image.setRGB(setX,setY,rgb);

    		Object resourceLocation = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(s1, c);
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
    		s = (new StringBuilder()).append(mod_PFLM_PlayerFormLittleMaid.pflm_main.textureSavedir).append(s).toString();
    		createDir(s);
    		try {
    			result = ImageIO.write(image, "png", new File(mod_Modchu_ModchuLib.modchu_Main.getMinecraftDir()+s));
    			image.flush();
    			imageWriteComplete = true;
    			imageWriteFail = false;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	} else {
    		imageWriteComplete = false;
    		imageWriteFail = true;
    		tagSetFileName = tagSetFileName == null && bufferedimageMode ? "image == null error." : null;
    		tagSetFileName = tagSetFileName == null && !result ? " image size error." : tagSetFileName;
    	}
    }

    private BufferedImage readTextureImage(Object o) throws IOException
    {
    	//Modchu_Debug.mDebug("readTextureImage assets/minecraft/" + resourceLocation.func_110624_b());
    	//Modchu_Debug.mDebug("readTextureImage assets/minecraft/" + resourceLocation.func_110623_a());
    	BufferedImage image = null;
    	InputStream var2 = null;
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
    		try {
    			Object var3 = Modchu_Reflect.invokeMethod("ResourceManager", "func_110536_a", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, Modchu_Reflect.invokeMethod("Minecraft", "func_110442_L", mod_Modchu_ModchuLib.modchu_Main.getMinecraft()), new Object[]{ o });
    			var2 = (InputStream) Modchu_Reflect.invokeMethod("Resource", "func_110527_b", var3);
    			image = ImageIO.read(var2);
    		} finally {
    			if (var2 != null) var2.close();
    		}
    	} else {
    		image = ImageIO.read((File) Modchu_Reflect.invokeMethod(Class.class, "getResource", new Class[]{ String.class }, Modchu_Reflect.loadClass("Minecraft"), new Object[]{ o }));
    	}
    	return fullColorConversion(image);
    }

    private BufferedImage fullColorConversion(BufferedImage image) {
    	BufferedImage write = null;
    	try {
    	//フルカラー変換処理
    	result = (image.getWidth() != 64) | (image.getHeight() != 32) ? false : true;
    	if (!result) return null;
    	int w = 64;
    	int h = 32;
    	write = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	for (int y = 0 ; y < h ; y++) {
    		for (int x = 0 ; x < w ; x++) {
    			int c = image.getRGB(x, y);
    			write.setRGB(x,y,c);
    		}
    	}
    	} catch (Exception e) {
    		Modchu_Debug.lDebug(""+getClass().getName(), "fullColorConversion", 2, e);
    	}
        return write;
    }

    public static void createDir(String s)
    {
    	String s1 = "";
    	String as[] = s.split("/");
    	for(int i = 0; i < as.length - 1; i++)
    	{
    		s1 = (new StringBuilder()).append(s1).append(as[i]).toString();
    		boolean flag = (new File((new StringBuilder()).append(mod_Modchu_ModchuLib.modchu_Main.getMinecraftDir()).append(s1).toString())).mkdir();
    		s1 = (new StringBuilder()).append(s1).append("/").toString();
    	}
    }

    public static void setcheck() {
    	setModel = setModel >= mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size() ? 0 : setModel;
    	setModel = setModel < 0 ? mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size() - 1 : setModel;
    	setModel = setModel < 0 ? 0 : setModel;
    	if (PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor) > 15) {
    		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, 0);
    	}
    	if (PFLM_RenderPlayerDummyMaster.modelData.getCapsValueInt(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor) < 0) {
    		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, 15);
    	}
    	setArmor = setArmor >= mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size() ? 0 : setArmor;
    	setArmor = setArmor < 0 ? mod_PFLM_PlayerFormLittleMaid.pflm_main.textureList.size() - 1 : setArmor;
    	setArmor = setArmor < 0 ? 0 : setArmor;
    }

    public static int colorA(int c) {
    	return c>>>24;
    }

    public static int colorR(int c) {
    	return c>>16&0xff;
    }

    public static int colorG(int c) {
    	return c>>8&0xff;
    }

    public static int colorB(int c) {
    	return c&0xff;
    }

    public static int colorRGB (int r,int g,int b) {
    	return 0xff000000 | r <<16 | g <<8 | b;
    }

    public static int colorARGB (int a,int r,int g,int b) {
    	return a<<24 | r <<16 | g <<8 | b;
    }

    @Override
    public String getTextureName() {
    	return mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName;
    }

    @Override
    public void setTextureName(String s) {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName = s;
    }

    @Override
    public String getTextureArmorName() {
    	return mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName;
    }

    @Override
    public void setTextureArmorName(String s) {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName = s;
    }

    @Override
    public int getColor() {
    	return mod_PFLM_PlayerFormLittleMaid.pflm_main.maidColor;
    }

    @Override
    public void setColor(int i) {
    	mod_PFLM_PlayerFormLittleMaid.pflm_main.maidColor = i & 0xf;
    }

    @Override
    public float getScale() {
    	return modelScale;
    }

    @Override
    public void setScale(float f) {
    	modelScale = f;
    }

    public static String getChangeModeString(int i) {
		String s = null;
		switch (i) {
		case modeOffline:
			s = "Offline";
			break;
		case modeOnline:
			s = "Online";
			break;
		case modeRandom:
			s = "Random";
			break;
		}
		return s;
	}

	public static String getHandednessModeString(int i) {
		String s = null;
		switch (i) {
		case -1:
			s = "RandomMode";
			break;
		case 0:
			s = "R";
			break;
		case 1:
			s = "L";
			break;
		}
		return s;
	}

	public static String getArmorTypeString(int i) {
		String s = null;
		switch (i) {
		case 0:
			s = "mainInner";
			break;
		case 2:
			s = "armorInner";
			break;
		case 3:
			s = "armorOuter";
			break;
		}
		return s;
	}
/*
	public static ConcurrentHashMap<String, Boolean> getShowPartsMap() {
		return !partsInitFlag ? PFLM_Config.getConfigShowPartsMap(getTextureName(), getColor(), armorType)
				: partsSetDefault ? PFLM_Gui.defaultParts : PFLM_Gui.parts;
	}
*/
}
