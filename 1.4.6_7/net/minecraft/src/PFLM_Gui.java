package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PFLM_Gui extends GuiScreen {

	private float xSize_lo;
	private float ySize_lo;
	public static boolean colorReverse = false;
	public static EntityLiving entityliving;
	protected boolean closePlayerToSpawn = false;
	public static float modelScale = 0.0F;
	protected boolean partsSetDefault = false;
	protected boolean modelScaleButton = false;
	public static boolean guiMode = false;
	public static final int partsNumberMax = 79;
	public static boolean partsButton = false;
	public static int partsSetFlag = 1;
	public static boolean showModelFlag = false;
	public static boolean partsSaveFlag = false;
	protected Canvas mcCanvas;
	protected boolean ismcCanvas = false;
	protected int scaleChangeButtonID = 0;
	protected int customizeButtonID = 0;
	public static String parts[] = new String[partsNumberMax];
	public static boolean[] showModel = new boolean[partsNumberMax];
   	boolean result = false;
	private boolean imageWriteComplete = false;
	private String modeltype = null;
	private String modelArmorName = null;
	private BufferedImage bufferedimage;
	private BufferedImage bufferedimage1;
	private boolean imageWriteFail = false;
	private String tagSetFileName = null;
	private boolean bufferedimageMode = false;
	private static double tempYOffset;
	private boolean TempYOffsetInit = false;
	private World popWorld;
	private Entity drawEntity = null;
	private boolean drawEntitySetFlag = true;
	private int scrollY = 0;
	private int handedness;
	public static int setModel = 0;
	public static int setArmor = 0;
	public static int setColor = 0;
	public static boolean noSaveFlag = false;
	public static boolean showArmor = true;
	public static Object textureModel[];
	public static String textureArmor0[];
	public static String textureArmor1[];
	public static String texture;
	public static String textureName;
	public static String textureArmorName;
	public static int changeMode;
	public static final int modeOffline = 0;
	public static final int modeOnline 	= 1;
	public static final int modeRandom 	= 2;
	private static final int maxChangeMode = 3;

	public PFLM_Gui(World world) {
		popWorld = world;
		if (textureName == null) textureName = "default";
		if (textureModel == null
				| textureName.equalsIgnoreCase("default")) {
			Modchu_Debug.mDebug("PFLM_EntityPlayerDummy default");
//-@-110
			if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
				textureModel = new ModelPlayerFormLittleMaidSmart[3];
			} else {
//@-@110
				textureModel = new ModelPlayerFormLittleMaid[3];
			/*110//*/}
		}
		if (textureArmor0 == null
				| textureName.equalsIgnoreCase("default")) textureArmor0 = new String[4];
		if (textureArmor1 == null
				| textureName.equalsIgnoreCase("default")) textureArmor1 = new String[4];
		handedness = mod_PFLM_PlayerFormLittleMaid.getHandednessMode();
	}

	@Override
	public void initGui() {
		controlList.clear();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		controlList.add(new Modchu_GuiSmallButton(200, x, y + 100, 75, 20, "Save"));
		controlList.add(new Modchu_GuiSmallButton(13, x, y + 70, 75, 15, "ChangeMode"));
		controlList.add(new Modchu_GuiSmallButton(58, x + 75, y + 55, 75, 15, "Handedness"));
		controlList.add(new Modchu_GuiSmallButton(57, x + 75, y + 70, 75, 15, "KeyControls"));
		if (changeMode != modeRandom) {
			if(!mod_PFLM_PlayerFormLittleMaid.onlineMode) {
				controlList.add(new Modchu_GuiSmallButton(56, x - 10, y + 10, 85, 15, "ModelListSelect"));
				//controlList.add(new Modchu_GuiSmallButton(57, x - 10, y + 10, 85, 15, "ArmorListSelect"));
				controlList.add(new Modchu_GuiSmallButton(50, x + 40, y + 25, 15, 15, "<"));
				controlList.add(new Modchu_GuiSmallButton(51, x + 55, y + 25, 15, 15, ">"));
				controlList.add(new Modchu_GuiSmallButton(52, x + 40, y + 40, 15, 15, "-"));
				controlList.add(new Modchu_GuiSmallButton(53, x + 55, y + 40, 15, 15, "+"));
				controlList.add(new Modchu_GuiSmallButton(54, x + 40, y + 55, 15, 15, "<"));
				controlList.add(new Modchu_GuiSmallButton(55, x + 55, y + 55, 15, 15, ">"));
			}
			if(mod_PFLM_PlayerFormLittleMaid.guiMultiPngSaveButton) {
				controlList.add(new Modchu_GuiSmallButton(12, x + 75, y + 100, 80, 20, "MultiPngSave"));
				bufferedimageMode = false;
				if (getModelType().startsWith("Biped")
						| mod_PFLM_PlayerFormLittleMaid.onlineMode) {
					controlList.add(new Modchu_GuiSmallButton(14, x + 75, y + 135, 15, 15, "<"));
					controlList.add(new Modchu_GuiSmallButton(15, x + 90, y + 135, 15, 15, ">"));
					controlList.add(new Modchu_GuiSmallButton(17, x + 75, y + 152, 15, 15, "-"));
					controlList.add(new Modchu_GuiSmallButton(16, x + 90, y + 152, 15, 15, "+"));
					controlList.add(new Modchu_GuiSmallButton(18, x + 75, y + 169, 15, 15, "<"));
					controlList.add(new Modchu_GuiSmallButton(19, x + 90, y + 169, 15, 15, ">"));
					controlList.add(new Modchu_GuiSmallButton(20, x + 75, y + 187, 30, 15, ""+showArmor));
					if (bufferedimage == null) {
						String url;
						url = mod_PFLM_PlayerFormLittleMaid.onlineMode ? "http://s3.amazonaws.com/MinecraftSkins/" + mod_PFLM_PlayerFormLittleMaid.getUsername() + ".png" : null;
						boolean er = false;
						try
						{
							if (mod_PFLM_PlayerFormLittleMaid.onlineMode) {
								URL url1 = new URL(url);
								bufferedimage = ImageIO.read(url1);
								//mod_PFLM_PlayerFormLittleMaid.textureName = "Biped_Biped";
								//mod_PFLM_PlayerFormLittleMaid.textureArmorName = "Biped_Biped";
								bufferedimageMode = true;
							} else {
								er = true;
							}
						}
						catch (Exception e1)
						{
							Modchu_Debug.mDebug("PFLM_Gui initGui image read miss.url = "+url);
							e1.printStackTrace();
							er = true;
						}
						if (er) {
							String t = null;
							if (mod_PFLM_PlayerFormLittleMaid.onlineMode) {
								if (mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.isRelease()) {
									mod_PFLM_PlayerFormLittleMaid.textureName = "Biped_Biped";
									mod_PFLM_PlayerFormLittleMaid.textureArmorName = "Biped_Biped";
									t = "/mob/char.png";
									if (url != null) {
										mod_PFLM_PlayerFormLittleMaid.setSkinUrl(null);
										mod_PFLM_PlayerFormLittleMaid.setTexture(t);
									}
									//String t = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, mod_PFLM_PlayerFormLittleMaid.maidColor);
									mod_PFLM_PlayerFormLittleMaid.texture = t;
								} else {
									mod_PFLM_PlayerFormLittleMaid.texture = t = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, mod_PFLM_PlayerFormLittleMaid.maidColor);
									if (t != null) {
									} else {
										mod_PFLM_PlayerFormLittleMaid.textureName = "default";
										mod_PFLM_PlayerFormLittleMaid.textureArmorName = "default";
										mod_PFLM_PlayerFormLittleMaid.maidColor = 0;
										mod_PFLM_PlayerFormLittleMaid.texture = t = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, mod_PFLM_PlayerFormLittleMaid.maidColor);
									}
								}
								Modchu_Debug.mDebug("mod_PFLM_PlayerFormLittleMaid.texture="+mod_PFLM_PlayerFormLittleMaid.texture);
								if (bufferedimage1 == null) {
									Modchu_Debug.mDebug("GUI t="+t);
									if (t != null) {
									} else {
										mod_PFLM_PlayerFormLittleMaid.textureName = "default";
										mod_PFLM_PlayerFormLittleMaid.textureArmorName = "default";
										mod_PFLM_PlayerFormLittleMaid.maidColor = 0;
										mod_PFLM_PlayerFormLittleMaid.texture = t = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, mod_PFLM_PlayerFormLittleMaid.maidColor);
									}
									try
									{
										bufferedimage1 = ImageIO.read((net.minecraft.client.Minecraft.class).getResource(t));
									}
									catch (Exception e2)
									{
										Modchu_Debug.Debug("PFLM_Gui initGui image read miss. File = "+t);
										e2.printStackTrace();
									}
								} else {
									BufferedImage image = bufferedimage1;
									bufferedimage = image;
								}
							}
						}
						if (bufferedimage != null
								&& mod_PFLM_PlayerFormLittleMaid.onlineMode) {
							Object[] s;
//-@-110
							if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
								s = PFLM_RenderPlayerSmart.checkimage(bufferedimage);
							} else {
//@-@110
								s = PFLM_RenderPlayer.checkimage(bufferedimage);
							/*110//*/}
							//boolean localflag = (Boolean) s[0];
							modeltype = (String) s[1];
							modelArmorName = (String) s[2];
							boolean returnflag = (Boolean) s[5];
							if (!returnflag) {
								mod_PFLM_PlayerFormLittleMaid.textureName = mod_PFLM_PlayerFormLittleMaid.textureList.get(mod_PFLM_PlayerFormLittleMaid.modelList.indexOf(modeltype));
								mod_PFLM_PlayerFormLittleMaid.textureArmorName = (String) s[2];
								int s2 = mod_PFLM_PlayerFormLittleMaid.modelList.indexOf(modeltype);
								if(s2 > -1) {
									setModel = s2;
									setColor = (Integer) s[3];
								}
							} else {
								modeltype = "Biped";
							}
							//String TextureName = (String) s[4];
						}
					} else {
						bufferedimageMode = mod_PFLM_PlayerFormLittleMaid.onlineMode ? true : false;
					}
					setcheck();
					if (setModel < mod_PFLM_PlayerFormLittleMaid.modelList.size()) {
						modeltype = mod_PFLM_PlayerFormLittleMaid.modelList.get(setModel);
					} else {
						setModel = setModel < 0 ? 0 : mod_PFLM_PlayerFormLittleMaid.modelList.size();
					}
					if (setArmor < mod_PFLM_PlayerFormLittleMaid.modelList.size()) {
						modelArmorName = mod_PFLM_PlayerFormLittleMaid.modelList.get(setArmor);
					} else {
						setArmor = setArmor < 0 ? 0 : mod_PFLM_PlayerFormLittleMaid.modelList.size();
					}
				}
			}
		}
		if(modelScaleButton) {
			controlList.add(new Modchu_GuiSmallButton(3, width / 2 - 140, height / 2 + 20, 50, 20, "Default"));
			controlList.add(new Modchu_GuiSmallButton(4, width / 2 - 90, height / 2 + 20, 30, 20, "UP"));
			controlList.add(new Modchu_GuiSmallButton(5, width / 2 - 170, height / 2 + 20, 30, 20, "Down"));
			controlList.add(new Modchu_GuiSmallButton(6, x + 75, y + 25, 75, 15, "Close"));
		} else {
			if(!partsButton) {
				controlList.add(new Modchu_GuiSmallButton(7, x + 75, y + 25, 75, 15, "ScaleChange"));
				if(mod_PFLM_PlayerFormLittleMaid.isMulti
						| !mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.isRelease()) {
					controlList.add(new Modchu_GuiSmallButton(30, x + 75, y - 5, 75, 15, "othersPlayer"));
				}
			}
		}
		if (changeMode != modeRandom) {
			if(partsButton) {
				controlList.add(new Modchu_GuiSmallButton(10, x + 75, y + 10, 75, 15, "Close"));
				partsButtonAdd();
			} else {
				if(!modelScaleButton) controlList.add(new Modchu_GuiSmallButton(11, x + 75, y + 10, 75, 15, "Customize"));
			}
		}
		if(mod_PFLM_PlayerFormLittleMaid.textureModel[0] != null
				&& modelScale == 0.0F) {
//-@-110
			if(mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
				modelScale = ((ModelPlayerFormLittleMaidSmart) mod_PFLM_PlayerFormLittleMaid.textureModel[0]).getModelScale();
			} else {
//@-@110
				modelScale = ((ModelPlayerFormLittleMaidBaseBiped) mod_PFLM_PlayerFormLittleMaid.textureModel[0]).getModelScale();
			/*110//*/}
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
		//Modchu_Debug.mDebug("PartsButtonAdd "+controlList.size());
		controlList.add(new Modchu_GuiSmallButton(99, width / 2 - 200, 12 - scrollY, 70, 15, "Default"));
		for (int i = 1; parts[i - 1] != null; i++)
		{
			s = showModel[i - 1] ? "ON" : "OFF";
			j = i % 2 == 0 ? 0 : 1;
			x = width / 2 - 200 + (j * 70);
			y = j == 0 ? 13 + (15 * i / 2) - scrollY : 20 + (15 * i / 2 - 15) - scrollY;
			controlList.add(new Modchu_GuiSmallButton(100 + i - 1, x, y, 70, 15, parts[i - 1]+":"+s));
		}
	}

	public void updateScreen() {
		if(partsSetFlag == 2) {
			partsSetFlag = 3;
			showModelFlag = true;
			if (!partsSetDefault) Modchu_Config.loadShowModelList(mod_PFLM_PlayerFormLittleMaid.showModelList);
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
    		if(mod_PFLM_PlayerFormLittleMaid.textureModel[0] != null) {
    			modelScale = mod_PFLM_PlayerFormLittleMaid.getModelScale();
    		} else {
    			modelScale = 0.9375F;
    		}
    		return;
    	}
    	//isModelSize UP
    	if(guibutton.id == 4)
    	{
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			modelScale += modelScale <= 9.5 ? 0.5F : 0;
    		} else {
    			if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
    				modelScale += modelScale <= 9.99 ? 0.01F : 0;
    			} else {
    				modelScale += modelScale <= 9.9 ? 0.1F : 0;
    			}
    		}
    		return;
    	}
    	//isModelSize Down
    	if(guibutton.id == 5)
    	{
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			modelScale -= modelScale > 0.5 ? 0.5F : 0;
    		}
    		else if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
    			modelScale -= modelScale > 0.01 ? 0.01F : 0;
    		} else {
    			modelScale -= modelScale > 0.1 ? 0.1F : 0;
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
    		imageMultiTagSetSave(mod_PFLM_PlayerFormLittleMaid.maidColor);
    		//for(int i = 0 ; i < 16 ; i++) {
    			//imageMultiTagSetSave(i);
    		//}
    		return;
    	}
    	//SkinModeChange
    	if(guibutton.id == 13)
    	{
    		changeMode++;
    		if (changeMode >= maxChangeMode) changeMode = 0;
    		if (!mod_PFLM_PlayerFormLittleMaid.onlineModeButton
    				&& changeMode == modeOnline) {
    			changeMode++;
        		if (changeMode >= maxChangeMode) changeMode = 0;
    		}
    		switch (changeMode) {
    		case modeOffline:
    			mod_PFLM_PlayerFormLittleMaid.onlineMode = false;
    			break;
    		case modeOnline:
    			mod_PFLM_PlayerFormLittleMaid.onlineMode = true;
    			mc.renderGlobal.releaseEntitySkin(mc.thePlayer);
    			mc.renderGlobal.obtainEntitySkin(mc.thePlayer);
    			break;
    		case modeRandom:
    			mod_PFLM_PlayerFormLittleMaid.onlineMode = false;
    			break;
    		}
    		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
    		if (mod_PFLM_PlayerFormLittleMaid.onlineMode) drawEntitySetFlag = true;
    		bufferedimage = null;
    		partsSetFlag = 1;
    		showModelFlag = true;
    		noSaveFlag = true;
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setModel+
    	if(guibutton.id == 14)
    	{
    		setModel++;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		drawEntitySetFlag = true;
    		setNextTexturePackege(0);
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setModel-
    	if(guibutton.id == 15)
    	{
    		setModel--;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		drawEntitySetFlag = true;
    		setPrevTexturePackege(0);
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setArmor+
    	if(guibutton.id == 16)
    	{
    		setColor++;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		drawEntitySetFlag = true;
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setArmor-
    	if(guibutton.id == 17)
    	{
    		setColor--;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		drawEntitySetFlag = true;
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setColor+
    	if(guibutton.id == 18)
    	{
    		setArmor++;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		drawEntitySetFlag = true;
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton setColor-
    	if(guibutton.id == 19)
    	{
    		setArmor--;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		drawEntitySetFlag = true;
    		initGui();
    		return;
    	}
    	//guiMultiPngSaveButton ShowArmor
    	if(guibutton.id == 20)
    	{
    		showArmor = !showArmor;
    		initGui();
    		return;
    	}
    	//OthersPlayer
    	if(guibutton.id == 30)
    	{
    		mc.displayGuiScreen(new PFLM_GuiOthersPlayer(this, popWorld));
    	}
    	//ModelChange
    	if(guibutton.id == 50) mod_PFLM_PlayerFormLittleMaid.setPrevTexturePackege(0);
    	if(guibutton.id == 51) mod_PFLM_PlayerFormLittleMaid.setNextTexturePackege(0);
    	if(guibutton.id == 50
    			| guibutton.id == 51)
    	{
    		modelChange();
    		return;
    	}
    	//ColorChange
    	if(guibutton.id == 52) {
    		mod_PFLM_PlayerFormLittleMaid.maidColor--;
    		colorReverse = true;
    	}
    	if(guibutton.id == 53) {
    		mod_PFLM_PlayerFormLittleMaid.maidColor++;
    		colorReverse = false;
    	}
    	if(guibutton.id == 52
    			| guibutton.id == 53)
    	{
    		mod_PFLM_PlayerFormLittleMaid.setMaidColor(mod_PFLM_PlayerFormLittleMaid.maidColor);
    		mod_PFLM_PlayerFormLittleMaid.setColorTextureValue();
    		mod_PFLM_PlayerFormLittleMaid.setTextureResetFlag(true);
    		partsSetFlag = 1;
    		showModelFlag = true;
    		partsSaveFlag = false;
    		partsSetDefault = false;
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		noSaveFlag = true;
    		return;
    	}
    	//ArmorChange
    	if(guibutton.id == 54) mod_PFLM_PlayerFormLittleMaid.setPrevTexturePackege(1);
    	if(guibutton.id == 55) mod_PFLM_PlayerFormLittleMaid.setNextTexturePackege(1);
    	if(guibutton.id == 54
    			| guibutton.id == 55)
    	{
    		mod_PFLM_PlayerFormLittleMaid.setTextureValue();
    		if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
    			closePlayerToSpawn = true;
    		}
    		mod_PFLM_PlayerFormLittleMaid.setResetFlag(true);
    		imageWriteComplete = false;
    		imageWriteFail = false;
    		noSaveFlag = true;
    		return;
    	}
    	//ModelListSelect
    	if(guibutton.id == 56) {
    		mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 0));
    		return;
    	}
    	//KeyControls
    	if(guibutton.id == 57) {
    		mc.displayGuiScreen(new PFLM_GuiKeyControls(this, popWorld));
    		return;
    	}
    	//Handedness
    	if(guibutton.id == 58) {
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			mod_PFLM_PlayerFormLittleMaid.handednessMode--;
    		} else {
    			mod_PFLM_PlayerFormLittleMaid.handednessMode++;
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.handednessMode < -1) mod_PFLM_PlayerFormLittleMaid.handednessMode = 1;
    		if (mod_PFLM_PlayerFormLittleMaid.handednessMode > 1) mod_PFLM_PlayerFormLittleMaid.handednessMode = -1;
    		PFLM_RenderPlayer.setHandedness(mc.thePlayer, mod_PFLM_PlayerFormLittleMaid.handednessMode);
    		return;
    	}
    	//Customize Default
    	if(guibutton.id == 99)
    	{
    		partsSetFlag = 1;
    		partsSetDefault = true;
    		partsSaveFlag = true;
    		showModelFlag = true;
    		return;
    	}
    	//PartsButton
    	if(guibutton.id >= 100
    			&& guibutton.id <= 199)
    	{
    		int i = guibutton.id - 100;
    		showModel[i] = showModel[i] ? false : true;
    		showModelFlag = true;
    		partsSaveFlag = true;
    		initGui();
    		//Modchu_Debug.mDebug("showModel["+i+"]="+showModel[i]);
    		return;
    	}
    	//Save
    	if(guibutton.id == 200)
    	{
    		Modchu_Config.clearCfgData();
    		mod_PFLM_PlayerFormLittleMaid.saveParamater();
    		if(partsSaveFlag) Modchu_Config.loadConfigShowModel(mod_PFLM_PlayerFormLittleMaid.showModelList, mod_PFLM_PlayerFormLittleMaid.cfgfile);
    		showModelFlag = true;
    		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
    		noSaveFlag = false;
    		mc.displayGuiScreen(null);
    	}
    }

    public void modelChange() {
    	mod_PFLM_PlayerFormLittleMaid.setTextureValue();
    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
    		closePlayerToSpawn = true;
    	}
    	mod_PFLM_PlayerFormLittleMaid.setResetFlag(true);
    	partsSetFlag = 1;
    	partsSaveFlag = false;
    	partsSetDefault = false;
    	imageWriteComplete = false;
    	imageWriteFail = false;
    	noSaveFlag = true;
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
    			scrollY = scrollY > (partsNumberMax / 2 * 20 + 20) ? partsNumberMax / 2 * 20 + 20 : scrollY;
    			initGui();
    		}
    	}
    }

    @Override
    protected void keyTyped(char c, int i)
    {
    	if(i == 200) {
    		scrollY += 30;
    		scrollY = scrollY > (partsNumberMax / 2 * 20 + 20) ? partsNumberMax / 2 * 20 + 20 : scrollY;
    		initGui();
    	}
    	if(i == 208) {
    		scrollY -= 30;
    		scrollY = scrollY < 0 ? 0 : scrollY;
    		initGui();
    	}
    	super.keyTyped(c, i);
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
    	drawDefaultBackground();
    	super.drawScreen(i, j, f);
    	drawGuiContainerBackgroundLayer(f, i, j);
    	xSize_lo = i;
    	ySize_lo = j;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	int xSize = 80;
    	int ySize = 50;
    	int guiLeft = width / 2 - xSize + 30;
    	int guiTop = height / 2 - (ySize / 2) - 20;
    	if (mod_PFLM_PlayerFormLittleMaid.newRelease
    			&& !partsButton
    			&& !modelScaleButton) {
    		fontRenderer.drawString("PlayerFormLittleMaid", 10, 10, 0xffffff);
    		fontRenderer.drawString((new StringBuilder()).append("newVersion v").append(mod_PFLM_PlayerFormLittleMaid.newVersion).append(" Release!").toString(), 10, 20, 0xffffff);
    	}
    	StringBuilder s = (new StringBuilder()).append("TextureName : ");
    	StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
    	StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
    	int x = partsButton | !mod_PFLM_PlayerFormLittleMaid.onlineMode ? 0 : 150;
    	if (changeMode != modeRandom) {
    		s = s.append(mod_PFLM_PlayerFormLittleMaid.textureName);
    		fontRenderer.drawString(s.toString(), guiLeft - x, guiTop + 90, 0xffffff);
    		if(!mod_PFLM_PlayerFormLittleMaid.onlineMode) {
    			s2 = s2.append(mod_PFLM_PlayerFormLittleMaid.maidColor);
    			fontRenderer.drawString(s2.toString(), guiLeft - x, guiTop + 100, 0xffffff);
    		}
    		if (getModelType().startsWith("Biped")) {
    		} else {
    			s1 = s1.append(mod_PFLM_PlayerFormLittleMaid.textureArmorName);
    			fontRenderer.drawString(s1.toString(), guiLeft - x, guiTop + 110, 0xffffff);
    		}
    	}
    	String s5 = mod_PFLM_PlayerFormLittleMaid.onlineMode ? "OnlineMode" : "OfflineMode";
    	s5 = (new StringBuilder()).append("SkinMode : ").append(s5).toString();
    	fontRenderer.drawString(s5, width - 120, 5, 0xffffff);
    	StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
    	s9 = s9.append(getChangeModeString(changeMode));
    	fontRenderer.drawString(s9.toString(), guiLeft - x, guiTop + 140, 0xffffff);
    	StringBuilder s10 = (new StringBuilder()).append("Handedness : ");
    	s10 = s10.append(getHandednessModeString(mod_PFLM_PlayerFormLittleMaid.handednessMode));
    	if (mod_PFLM_PlayerFormLittleMaid.handednessMode == -1) s10 = s10.append(" Result : ").append(getHandednessModeString(handedness));
    	fontRenderer.drawString(s10.toString(), guiLeft - x, guiTop + 150, 0xffffff);
    	if (!mod_PFLM_PlayerFormLittleMaid.onlineMode
    			&& changeMode != modeRandom) {
    		fontRenderer.drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
    		fontRenderer.drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
    		fontRenderer.drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
    	}
    	if(partsButton) {
    		String s8 = "Parts showModel set";
    		s8 = (new StringBuilder()).append(s8).toString();
    		fontRenderer.drawString(s8, width / 2 - 180, -scrollY, 0xffffff);
    	}
    	if (mod_PFLM_PlayerFormLittleMaid.isModelSize
    			&& !mod_PFLM_PlayerFormLittleMaid.onlineMode) {
    		float f1 = 0.5F;
    		float f2 = 1.35F;
    		float f3 = 1.17F;
    		if (!TempYOffsetInit) {
    			TempYOffsetInit = true;
    			if (mod_PFLM_PlayerFormLittleMaid.gotchaNullCheck())
    				setTempYOffset(mod_PFLM_PlayerFormLittleMaid.getyOffset());
    		}
    		f1 = mod_PFLM_PlayerFormLittleMaid.getWidth();
    		f2 = mod_PFLM_PlayerFormLittleMaid.getHeight();
    		f3 = mod_PFLM_PlayerFormLittleMaid.getyOffset();
    		String s3 = "Size : Width = "+f1+" Height = "+f2;
    		s3 = (new StringBuilder()).append(s3).toString();
    		fontRenderer.drawString(s3, guiLeft, guiTop + 120, 0xffffff);
    		String s4 = "yOffset : "+f3;
    		s4 = (new StringBuilder()).append(s4).toString();
    		fontRenderer.drawString(s4, guiLeft, guiTop + 130, 0xffffff);
    	}
    	if(modelScaleButton) {
    		String s6 = "ModelScale : "+modelScale;
    		s6 = (new StringBuilder()).append(s6).toString();
    		fontRenderer.drawString(s6, guiLeft - 120, guiTop + 90, 0xffffff);
    		String s7 = "ModelScaleChange";
    		s7 = (new StringBuilder()).append(s7).toString();
    		fontRenderer.drawString(s7, guiLeft - 120, guiTop + 55, 0xffffff);
    	}
    	if (changeMode != modeRandom) {
    		if (imageWriteComplete) {
    			s5 = (new StringBuilder()).append(mod_PFLM_PlayerFormLittleMaid.textureSavedir).append(tagSetFileName).append(" Successfully written file.").toString();
    			fontRenderer.drawString(s5, 10, height - 15, 0xffffff);
    		}
    		if (imageWriteFail) {
    			s5 = (new StringBuilder()).append(mod_PFLM_PlayerFormLittleMaid.textureSavedir).append(tagSetFileName).append(" Failed to write file.").toString();
    			fontRenderer.drawString(s5, 10, height - 15, 0xffffff);
    		}
    		if (getModelType().startsWith("Biped")
    				| mod_PFLM_PlayerFormLittleMaid.onlineMode) {
    			setcheck();
    			fontRenderer.drawString("MultiTagSet", width / 2 + 135, height / 2 + 40, 0xffffff);
    			s.delete(0, s.length());
    			s1.delete(0, s1.length());
    			s2.delete(0, s2.length());
    			s = s.append(mod_PFLM_PlayerFormLittleMaid.modelList.get(setModel));
    			s1 = s1.append(modelArmorName);
    			s2 = s2.append(setColor);
    			fontRenderer.drawString(s.toString(), width / 2 + 160, height / 2 + 54, 0xffffff);
    			fontRenderer.drawString(s2.toString(), width / 2 + 160, height / 2 + 72, 0xffffff);
    			fontRenderer.drawString(s1.toString(), width / 2 + 160, height / 2 + 88, 0xffffff);
    			fontRenderer.drawString("ShowArmor : ", width / 2 + 65, height / 2 + 110, 0xffffff);
    		}
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		int l = guiLeft;
    		int i1 = guiTop;
    		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
    		GL11.glPushMatrix();
    		GL11.glTranslatef(l + 51, i1 + 75, 50F);
    		float f1 = 50F;
    		GL11.glScalef(-f1, f1, f1);
    		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
    		float f2 = mc.thePlayer.renderYawOffset;
    		float f3 = mc.thePlayer.rotationYaw;
    		float f4 = mc.thePlayer.rotationPitch;
    		float f5 = (float)(l + 51) - xSize_lo;
    		float f6 = (float)((i1 + 75) - 50) - ySize_lo;
    		GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
    		RenderHelper.enableStandardItemLighting();
    		GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
    		GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
    		mc.thePlayer.renderYawOffset = (float)Math.atan(f5 / 40F) * 20F;
    		mc.thePlayer.rotationYaw = (float)Math.atan(f5 / 40F) * 40F;
    		mc.thePlayer.rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
    		GL11.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
    		RenderManager.instance.playerViewY = 180F;
    		RenderManager.instance.renderEntityWithPosYaw(mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
    		mc.thePlayer.renderYawOffset = f2;
    		mc.thePlayer.rotationYaw = f3;
    		mc.thePlayer.rotationPitch = f4;
    		GL11.glPopMatrix();
    		RenderHelper.disableStandardItemLighting();
    		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
    		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    		if (mod_PFLM_PlayerFormLittleMaid.onlineMode
    				| getModelType().startsWith("Biped")
    				&& mod_PFLM_PlayerFormLittleMaid.guiMultiPngSaveButton) {
    			if (drawEntitySetFlag) {
    				textureName = mod_PFLM_PlayerFormLittleMaid.textureList.get(setModel);
    				texture = PFLM_Texture
    						.getTextureName(textureName, setColor);
    				textureArmorName = mod_PFLM_PlayerFormLittleMaid.textureList.get(setArmor);
    				setTextureValue();
    				drawEntitySetFlag = false;
    				Modchu_Debug.mDebug("textureName="+textureName);
    				Modchu_Debug.mDebug("texture="+texture);
    				Modchu_Debug.mDebug("textureArmorName="+textureArmorName);
    			}
    			if (drawEntity == null) drawEntity = new PFLM_EntityPlayerDummy(popWorld);
    			((EntityLiving) drawEntity).texture = texture;
    			((PFLM_EntityPlayerDummy) drawEntity).textureModel = textureModel;
    			((PFLM_EntityPlayerDummy) drawEntity).textureName = textureName;
    			((PFLM_EntityPlayerDummy) drawEntity).textureArmorName = textureArmorName;
    			((PFLM_EntityPlayerDummy) drawEntity).textureArmor0 = textureArmor0;
    			((PFLM_EntityPlayerDummy) drawEntity).textureArmor1 = textureArmor1;
    			((PFLM_EntityPlayerDummy) drawEntity).showArmor = showArmor;
    			((PFLM_EntityPlayerDummy) drawEntity).others = false;
    			drawEntity.setPosition(mc.thePlayer.posX , mc.thePlayer.posY, mc.thePlayer.posZ);
    			drawMobModel(width / 2 + 90, height / 2 + 105, 30F, drawEntity);
    		}
    	}
    }

    public void drawMobModel(int x, int y, float f, Entity entity)
    {
    	if (entity == null) return;
    	//GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    	GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
    	GL11.glPushMatrix();
    	GL11.glTranslatef(x, y, 50F);
    	GL11.glScalef(-f, f, f);
    	GL11.glRotatef(180F, 0.0F, 0.0F, 0.0F);
    	float f5 = (float)x - xSize_lo;
    	float f6 = (float)(y - 50) - ySize_lo;
    	GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
    	GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
    	GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
    	float yd = (float)Math.atan(f5 / 40F) * 40F;
    	GL11.glRotatef(yd, 0.0F, 1.0F, 0.0F);
    	RenderManager.instance.playerViewY = 180F;
    	RenderHelper.enableStandardItemLighting();
    	//Modchu_Debug.mDebug("drawMobModel "+entity.getTexture());
    	//if (new File(entity.getTexture()).exists())
    		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
    	RenderHelper.disableStandardItemLighting();
    	GL11.glPopMatrix();
    	//GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    	OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public void setTextureValue() {
    	if (textureName == null) {
    		return;
    	}
    	int i = setColor;
    	texture = PFLM_Texture.getTextureName(textureName, i);
    	if (texture == null) {
    		int n = 0;
    		for (; n < 16 && texture == null; n = n + 1) {
    			i++;
    			i = i & 0xf;
    			texture = PFLM_Texture
    					.getTextureName(textureName, i);
    		}
    		setColor = i;
    	}
    	int j = textureName.lastIndexOf('_');
    	String s = textureName.substring(j + 1);
    	if (j > -1) {
    		s = textureName.substring(j + 1);
    	} else {
    		s = ""+textureName;
    	}
    	Object amodelPlayerFormLittleMaid[];
//-@-110
    	if (mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
    		amodelPlayerFormLittleMaid = (ModelPlayerFormLittleMaidSmart[])
    				PFLM_Texture.modelMap.get(s);
    		//Modchu_Debug.mDebug("setTextureValue s="+s);
    		//if ((ModelPlayerFormLittleMaidBaseBipedSmart[])
    			//PFLM_Texture.modelMap.get(s) == null) Modchu_Debug.mDebug("setTextureValue modelMap null");
    	} else {
//@-@110
    		amodelPlayerFormLittleMaid = (ModelPlayerFormLittleMaidBaseBiped[])
    				PFLM_Texture.modelMap.get(s);
    	/*110//*/}
    	if (amodelPlayerFormLittleMaid == null) {
    		//Modchu_Debug.mDebug("setTextureValue() null");
    	} else {
    		if (amodelPlayerFormLittleMaid[0] == null) {
    			//Modchu_Debug.mDebug("setTextureValue() [0] null");
    		} else {
    			textureModel[0] = amodelPlayerFormLittleMaid[0];
    		}
    	}
    }

	public static void setNextTexturePackege(int i) {
		setcheck();
		textureName = mod_PFLM_PlayerFormLittleMaid.textureList.get(setModel);
		textureArmorName = mod_PFLM_PlayerFormLittleMaid.textureList.get(setArmor);
		if (i == 0) {
			setTextureArmorName(textureName);
			setArmorNumber(setModel);
			if (PFLM_Texture.armors
					.get(textureArmorName) == null) {
				if (textureName.indexOf("Biped") == -1) {
					setTextureArmorName("default");
					setArmor = 0;
				} else {
					setTextureArmorName("Biped");
					setArmor = mod_PFLM_PlayerFormLittleMaid.textureList.indexOf(textureArmorName);
				}
			}
		}
		if (i == 1) {
			textureArmorName = PFLM_Texture
					.getNextArmorPackege(textureArmorName);
			setArmor = mod_PFLM_PlayerFormLittleMaid.textureList.indexOf(textureArmorName);
		}
	}

	public static void setPrevTexturePackege(int i) {
		setcheck();
		textureName = mod_PFLM_PlayerFormLittleMaid.textureList.get(setModel);
		textureArmorName = mod_PFLM_PlayerFormLittleMaid.textureList.get(setArmor);
		if (i == 0) {
			setTextureArmorName(textureName);
			setArmorNumber(setModel);
			if (PFLM_Texture.armors
					.get(textureArmorName) == null) {
				if (textureName.indexOf("Biped") == -1) {
					setTextureArmorName("default");
					setArmor = 0;
				} else {
					setTextureArmorName("Biped");
					setArmor = mod_PFLM_PlayerFormLittleMaid.textureList.indexOf(textureArmorName);
				}
			}
		}
		if (i == 1) {
			textureArmorName = PFLM_Texture
					.getPrevArmorPackege(textureArmorName);
			setArmor = mod_PFLM_PlayerFormLittleMaid.textureList.indexOf(textureArmorName);
		}
	}

    public static int getMaidColor()
    {
    	return setColor;
    }

    public static void setTextureName(String s) {
    	textureName = s;
    }

    public static void setTextureArmorName(String s) {
    	textureArmorName = s;
    }

	private static void setArmorNumber(int i) {
		setArmor = i;
	}

    public boolean doesGuiPauseGame()
    {
        return !mod_PFLM_PlayerFormLittleMaid.isMulti;
    }

    public void onGuiClosed()
    {
    	if (closePlayerToSpawn
    			&& mod_PFLM_PlayerFormLittleMaid.isModelSize) {
    		setPositionCorrection();
    	}
    	guiMode = false;
    	TempYOffsetInit = false;
    	drawEntitySetFlag = true;
    }

    public static void setPositionCorrection() {
    	mod_PFLM_PlayerFormLittleMaid.setSize(0.6F, 1.8F);
    	mod_PFLM_PlayerFormLittleMaid.resetHeight();
    	double d = tempYOffset - mod_PFLM_PlayerFormLittleMaid.getyOffset() - 0.5D;
    	mod_PFLM_PlayerFormLittleMaid.setPositionCorrection(0.0D ,-d ,0.0D);
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
    					| !mod_PFLM_PlayerFormLittleMaid.onlineMode) {
    				image = readTextureImage(PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, c));
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
    		String s1 = getModelType();
    		boolean rightBottomSet = false;
    		if (s1.startsWith("Elsa")) rightBottomSet = true;
    		if (s1.startsWith("Biped")
    				| mod_PFLM_PlayerFormLittleMaid.onlineMode) {
    			s1 = modeltype;
    			result = false;
    		} else {
    			result = true;
    		}
    		String s2 = getArmorName();
    		if (s1.startsWith("Biped")
    				| mod_PFLM_PlayerFormLittleMaid.onlineMode) {
    			s2 = modelArmorName;
    		}
    		int t = mod_PFLM_PlayerFormLittleMaid.modelList.indexOf(s1);
    		int t2 = mod_PFLM_PlayerFormLittleMaid.modelList.indexOf(s2);
    		t = t < 0 ? 0 : t;
    		t2 = t2 < 0 ? 0 : t2;
    		int setX = 63;
    		int setY = 0;

    		if (rightBottomSet) setY = 31;

    		int rgb = colorRGB(255, 0, 0);
    		image.setRGB(setX,setY,rgb);
    		rgb = result ? colorRGB(255, 255, 0) : colorRGB(255, 0, 255);

    		setY = 1;
    		if (rightBottomSet) setY = 30;

    		image.setRGB(setX,setY,rgb);

    		int r1 = result ? 255 - c : 255 - setColor;
    		int g1 = 255 - t2;
    		int b1 = 255 - t;
    		rgb = colorRGB(r1,g1,b1);
    		setX = 62;
    		setY = 0;
    		if (rightBottomSet) setY = 31;
    		image.setRGB(setX,setY,rgb);
    		String s = PFLM_Texture.getTextureName(mod_PFLM_PlayerFormLittleMaid.textureName, c);
    		if (s == null
    				| bufferedimageMode) {
    			s = "char.png";
    		} else {
    			File file = new File(s);
    			s = file.getName();
    		}
    		tagSetFileName = s;
    		s = (new StringBuilder()).append(mod_PFLM_PlayerFormLittleMaid.textureSavedir).append(s).toString();
    		createDir(s);
    		try {
    			result = ImageIO.write(image, "png", new File(Minecraft.getMinecraftDir()+s));
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

    private BufferedImage readTextureImage(String s) throws IOException
    {
    	BufferedImage image = ImageIO.read((net.minecraft.client.Minecraft.class).getResource(s));
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
			Modchu_Debug.Debug(e.getMessage());
    	}
        return write;
    }

    public static String getModelType() {
    	String s = mod_PFLM_PlayerFormLittleMaid.textureName;
    	if (s != null) {
    		int i = mod_PFLM_PlayerFormLittleMaid.textureName.lastIndexOf('_');
    		if (i > -1)
    		{
    			s = mod_PFLM_PlayerFormLittleMaid.textureName.substring(i + 1);
    		}
    	}
    	if (s == null) return "";
    	return s;
    }

    public static String getArmorName() {
    	String s = mod_PFLM_PlayerFormLittleMaid.textureArmorName;
    	int i = mod_PFLM_PlayerFormLittleMaid.textureArmorName.lastIndexOf('_');
    	if (i > -1)
    	{
    		s = mod_PFLM_PlayerFormLittleMaid.textureArmorName.substring(i + 1);
    	}
    	return s;
    }

    public static void createDir(String s)
    {
    	String s1 = "";
    	String as[] = s.split("/");
    	for(int i = 0; i < as.length - 1; i++)
    	{
    		s1 = (new StringBuilder()).append(s1).append(as[i]).toString();
    		boolean flag = (new File((new StringBuilder()).append(Minecraft.getMinecraftDir()).append(s1).toString())).mkdir();
    		s1 = (new StringBuilder()).append(s1).append("/").toString();
    	}
    }

    public static void setcheck() {
    	setModel = setModel >= mod_PFLM_PlayerFormLittleMaid.modelList.size() ? 0 : setModel;
    	setModel = setModel < 0 ? mod_PFLM_PlayerFormLittleMaid.modelList.size() - 1 : setModel;
    	setModel = setModel < 0 ? 0 : setModel;
    	setColor = setColor > 15 ? 0 : setColor;
    	setColor = setColor < 0 ? 15 : setColor;
    	setArmor = setArmor >= mod_PFLM_PlayerFormLittleMaid.modelList.size() ? 0 : setArmor;
    	setArmor = setArmor < 0 ? mod_PFLM_PlayerFormLittleMaid.modelList.size() - 1 : setArmor;
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

	public static int setParts(String[] s, int k) {
    	boolean setPartsNumberFlag = false;
    	int j = 0;
		for (int i = 0; i + k < partsNumberMax ; i++)
		{
			if(i < s.length) {
				parts[i + k] = s[i];
				showModel[i + k] = true;
			} else {
				if(!setPartsNumberFlag) {
					j = i + k;
					setPartsNumberFlag = true;
				}
				parts[i + k] = null;
				showModel[i + k] = false;
			}
		}
		return j;
	}
}
