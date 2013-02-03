package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
/*//FMLdelete
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
*///FMLdelete
public class PFLM_GuiOthersPlayer extends GuiScreen {
	protected int xSize_lo;
	protected int ySize_lo;
	protected float Size_lo;
	protected World popWorld;
	protected boolean modelScaleButton;
	protected boolean noSaveFlag = false;
	protected boolean colorReverse = false;
	protected Entity drawEntity = null;
	protected boolean drawEntitySetFlag;
	public static int changeMode = 0;
	public static final int modefalse 					= 0;
	public static final int modePlayer 					= 1;
	public static final int modeOthersSettingOffline 	= 2;
	public static final int modePlayerOffline 			= 3;
	public static final int modePlayerOnline			= 4;
	public static final int modeRandom 					= 5;
	public static final int changeModeMax =	5;
	public static boolean showArmor = true;
	public GuiScreen parentScreen;

	public PFLM_GuiOthersPlayer(GuiScreen par1GuiScreen, World world) {
		popWorld = world;
		parentScreen = par1GuiScreen;
	}

	@Override
	public void initGui() {
		controlList.clear();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		controlList.add(new Modchu_GuiSmallButton(400, x + 75, y + 10, 75, 15, "ChangeMode"));
		controlList.add(new Modchu_GuiSmallButton(401, x, y + 85 , 100, 15, "IndividualCustomize"));
		controlList.add(new Modchu_GuiSmallButton(200, x, y + 100, 75, 20, "Save"));
		controlList.add(new Modchu_GuiSmallButton(201, x + 75, y + 100, 75, 20, "Return"));
		if(changeMode == modeOthersSettingOffline
				| changeMode == modeRandom) {
			controlList.add(new Modchu_GuiSmallButton(58, x + 75, y + 55, 75, 15, "Handedness"));
		}
		if(changeMode == modeOthersSettingOffline) {
			controlList.add(new Modchu_GuiSmallButton(56, x - 10, y + 10, 85, 15, "ModelListSelect"));
			//controlList.add(new Modchu_GuiSmallButton(57, x - 10, y + 10, 85, 15, "ArmorListSelect"));
			controlList.add(new Modchu_GuiSmallButton(50, x + 40, y + 25, 15, 15, "<"));
			controlList.add(new Modchu_GuiSmallButton(51, x + 55, y + 25, 15, 15, ">"));
			controlList.add(new Modchu_GuiSmallButton(52, x + 40, y + 40, 15, 15, "-"));
			controlList.add(new Modchu_GuiSmallButton(53, x + 55, y + 40, 15, 15, "+"));
			controlList.add(new Modchu_GuiSmallButton(54, x + 40, y + 55, 15, 15, "<"));
			controlList.add(new Modchu_GuiSmallButton(55, x + 55, y + 55, 15, 15, ">"));
			controlList.add(new Modchu_GuiSmallButton(20, x, y + 70, 75, 15, "showArmor"));
			if (mod_PFLM_PlayerFormLittleMaid.useScaleChange) {
				if(modelScaleButton) {
					controlList.add(new Modchu_GuiSmallButton(3, width / 2 - 140, height / 2 + 20, 50, 20, "Default"));
					controlList.add(new Modchu_GuiSmallButton(4, width / 2 - 90, height / 2 + 20, 30, 20, "UP"));
					controlList.add(new Modchu_GuiSmallButton(5, width / 2 - 170, height / 2 + 20, 30, 20, "Down"));
					controlList.add(new Modchu_GuiSmallButton(6, x + 75, y + 25, 75, 15, "Close"));
				} else {
					controlList.add(new Modchu_GuiSmallButton(7, x + 75, y + 25, 75, 15, "ScaleChange"));
				}
			}
		}
		setTextureValue();
		if(mod_PFLM_PlayerFormLittleMaid.othersTextureModel[0] != null) {
			if (mod_PFLM_PlayerFormLittleMaid.othersModelScale == 0.0F) {
				mod_PFLM_PlayerFormLittleMaid.othersModelScale = ((MultiModelBaseBiped) mod_PFLM_PlayerFormLittleMaid.othersTextureModel[0]).getModelScale();
			}
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
        	mod_PFLM_PlayerFormLittleMaid.othersModelScale = ((MultiModelBaseBiped) mod_PFLM_PlayerFormLittleMaid.othersTextureModel[0]).getModelScale();
        }
        //isModelSize UP
        if(guibutton.id == 4)
        {
            if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
            	mod_PFLM_PlayerFormLittleMaid.othersModelScale += mod_PFLM_PlayerFormLittleMaid.othersModelScale <= 9.5 ? 0.5F : 0;
            } else {
                if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
                	mod_PFLM_PlayerFormLittleMaid.othersModelScale += mod_PFLM_PlayerFormLittleMaid.othersModelScale <= 9.99 ? 0.01F : 0;
                } else {
                	mod_PFLM_PlayerFormLittleMaid.othersModelScale += mod_PFLM_PlayerFormLittleMaid.othersModelScale <= 9.9 ? 0.1F : 0;
                }
            }
        }
        //isModelSize Down
        if(guibutton.id == 5)
        {
            if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
            	mod_PFLM_PlayerFormLittleMaid.othersModelScale -= mod_PFLM_PlayerFormLittleMaid.othersModelScale > 0.5 ? 0.5F : 0;
            } else {
                if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
                	mod_PFLM_PlayerFormLittleMaid.othersModelScale -= mod_PFLM_PlayerFormLittleMaid.othersModelScale > 0.01 ? 0.01F : 0;
                } else {
                	mod_PFLM_PlayerFormLittleMaid.othersModelScale -= mod_PFLM_PlayerFormLittleMaid.othersModelScale > 0.1 ? 0.1F : 0;
                }
            }
        }
        //ScaleChange Close
        if(guibutton.id == 6)
        {
        	modelScaleButton = false;
        	initGui();
        }
        //ScaleChange Open
        if(guibutton.id == 7)
        {
        	modelScaleButton = true;
        	initGui();
        }
        //guiMultiPngSaveButton ShowArmor
        if(guibutton.id == 20)
        {
        	showArmor = !showArmor;
        	initGui();
        }
    	//ModelChange
    	if(guibutton.id == 50) setPrevTexturePackege(0);
    	if(guibutton.id == 51) setNextTexturePackege(0);
    	if(guibutton.id == 50
    			| guibutton.id == 51)
    	{
    		modelChange();
    	}
    	//ColorChange
    	if(guibutton.id == 52) {
    		mod_PFLM_PlayerFormLittleMaid.othersMaidColor--;
    		colorReverse = true;
    	}
    	if(guibutton.id == 53) {
    		mod_PFLM_PlayerFormLittleMaid.othersMaidColor++;
    		colorReverse = false;
    	}
    	if(guibutton.id == 52
    			| guibutton.id == 53)
    	{
    		setMaidColor(mod_PFLM_PlayerFormLittleMaid.othersMaidColor);
    		setColorTextureValue();
    		noSaveFlag = true;
    	}
    	//ArmorChange
    	if(guibutton.id == 54) setPrevTexturePackege(1);
    	if(guibutton.id == 55) setNextTexturePackege(1);
    	if(guibutton.id == 54
    			| guibutton.id == 55)
    	{
    		setTextureValue();
    		noSaveFlag = true;
    		mod_PFLM_PlayerFormLittleMaid.setResetFlag(true);
    	}
    	//ModelListSelect
    	if(guibutton.id == 56) {
    		mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 2));
    	}
    	//ArmorListSelect
    	if(guibutton.id == 57) {

    	}
    	//Handedness
    	if(guibutton.id == 58) {
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			mod_PFLM_PlayerFormLittleMaid.othersHandednessMode--;
    		} else {
    			mod_PFLM_PlayerFormLittleMaid.othersHandednessMode++;
    		}
    		if (mod_PFLM_PlayerFormLittleMaid.othersHandednessMode < -1) mod_PFLM_PlayerFormLittleMaid.othersHandednessMode = 1;
    		if (mod_PFLM_PlayerFormLittleMaid.othersHandednessMode > 1) mod_PFLM_PlayerFormLittleMaid.othersHandednessMode = -1;
    		return;
    	}
        //Save
        if(guibutton.id == 200)
        {
        	mod_PFLM_PlayerFormLittleMaid.saveOthersPlayerParamater(false);
        	Modchu_Config.clearCfgData();
        	mod_PFLM_PlayerFormLittleMaid.clearPlayers();
        	noSaveFlag = false;
        	mc.displayGuiScreen(null);
        }
        //Return
        if(guibutton.id == 201)
        {
        	mc.displayGuiScreen(new PFLM_Gui(popWorld));
        }
        //ChangeMode
        if(guibutton.id == 400)
        {
        	if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
        		changeMode--;
        	} else {
        		changeMode++;
        	}
        	if (changeMode > changeModeMax) changeMode = 0;
        	if (changeMode < 0) changeMode = changeModeMax;
        	mod_PFLM_PlayerFormLittleMaid.clearPlayers();
        	initGui();
        }
        //IndividualCustomize
        if(guibutton.id == 401)
        {
        	mc.displayGuiScreen(new PFLM_GuiOthersPlayerIndividualCustomizeSelect(this, mc, popWorld));
        }
    }

    public void modelChange() {
    	setTextureValue();
    	noSaveFlag = true;
		mod_PFLM_PlayerFormLittleMaid.setResetFlag(true);
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
    	drawDefaultBackground();
    	super.drawScreen(i, j, f);
    	drawGuiContainerBackgroundLayer(f, i, j);
    	xSize_lo = i;
    	ySize_lo = j;
    	Size_lo = f;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	int xSize = 80;
    	int ySize = 50;
    	int guiLeft = width / 2 - xSize + 30;
    	int guiTop = height / 2 - (ySize / 2) - 20;
    	fontRenderer.drawString("othersPlayerModelSetting", width / 2 - 50, height / 2 - 100, 0xffffff);
    	StringBuilder s = (new StringBuilder()).append("TextureName : ");
    	StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
    	StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
    	StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
    	s9 = s9.append(getChangeModeString(changeMode));
    	fontRenderer.drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
    	if(changeMode == modeOthersSettingOffline
    			| changeMode == modeRandom) {
    		StringBuilder s10 = (new StringBuilder()).append("Handedness : ");
    		s10 = s10.append(getHandednessModeString(mod_PFLM_PlayerFormLittleMaid.othersHandednessMode));
    		//if (mod_PFLM_PlayerFormLittleMaid.othersHandednessMode == -1) s10 = s10.append(" Result : ").append(getHandednessModeString(handedness));
    		fontRenderer.drawString(s10.toString(), guiLeft, guiTop + 140, 0xffffff);
    	}
    	if(changeMode == modeOthersSettingOffline) {
    		s = s.append(mod_PFLM_PlayerFormLittleMaid.othersTextureName);
    		fontRenderer.drawString(s.toString(), guiLeft, guiTop + 90, 0xffffff);
    		s2 = s2.append(mod_PFLM_PlayerFormLittleMaid.othersMaidColor);
    		fontRenderer.drawString(s2.toString(), guiLeft, guiTop + 100, 0xffffff);
    		s1 = s1.append(mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName);
    		fontRenderer.drawString(s1.toString(), guiLeft, guiTop + 110, 0xffffff);
    		StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
    		s8 = s8.append(showArmor);
    		fontRenderer.drawString(s8.toString(), guiLeft, guiTop + 120, 0xffffff);
    		if(mod_PFLM_PlayerFormLittleMaid.useScaleChange
    				&& modelScaleButton) {
    			String s6 = "ModelScale : "+mod_PFLM_PlayerFormLittleMaid.othersModelScale;
    			s6 = (new StringBuilder()).append(s6).toString();
    			fontRenderer.drawString(s6, guiLeft - 120, guiTop + 90, 0xffffff);
    			String s7 = "ModelScaleChange";
    			s7 = (new StringBuilder()).append(s7).toString();
    			fontRenderer.drawString(s7, guiLeft - 120, guiTop + 55, 0xffffff);
    		}
    		fontRenderer.drawString("Model", width / 2 + 60, height / 2 - 56, 0xffffff);
    		fontRenderer.drawString("Color", width / 2 + 60, height / 2 - 41, 0xffffff);
    		fontRenderer.drawString("Armor", width / 2 + 60, height / 2 - 27, 0xffffff);
    		if (drawEntity == null) drawEntity = new PFLM_EntityPlayerDummy(popWorld);
    		setTextureValue();
    		((EntityLiving) drawEntity).texture = mod_PFLM_PlayerFormLittleMaid.othersTexture;
    		((PFLM_EntityPlayerDummy) drawEntity).textureModel = mod_PFLM_PlayerFormLittleMaid.othersTextureModel;
			((PFLM_EntityPlayerDummy) drawEntity).maidColor = mod_PFLM_PlayerFormLittleMaid.othersMaidColor;
    		((PFLM_EntityPlayerDummy) drawEntity).textureName = mod_PFLM_PlayerFormLittleMaid.othersTextureName;
    		((PFLM_EntityPlayerDummy) drawEntity).textureArmorName = mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName;
    		((PFLM_EntityPlayerDummy) drawEntity).textureArmor0 = mod_PFLM_PlayerFormLittleMaid.othersTextureArmor0;
    		((PFLM_EntityPlayerDummy) drawEntity).textureArmor1 = mod_PFLM_PlayerFormLittleMaid.othersTextureArmor1;
    		((PFLM_EntityPlayerDummy) drawEntity).modelScale = mod_PFLM_PlayerFormLittleMaid.othersModelScale;
    		((PFLM_EntityPlayerDummy) drawEntity).showArmor = showArmor;
    		((PFLM_EntityPlayerDummy) drawEntity).others = true;
    		//Modchu_Debug.mDebug("textureName="+mod_PFLM_PlayerFormLittleMaid.othersTextureName);
    		//Modchu_Debug.mDebug("texture="+mod_PFLM_PlayerFormLittleMaid.othersTexture);
    		//Modchu_Debug.mDebug("textureArmorName="+mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName);
    		drawEntity.setPosition(mc.thePlayer.posX , mc.thePlayer.posY, mc.thePlayer.posZ);
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		int l = guiLeft;
    		int i1 = guiTop;
    		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
    		GL11.glPushMatrix();
    		GL11.glTranslatef(l + 51 , i1 + 155, 50F);
    		float f1 = 50F;
    		GL11.glScalef(-f1, f1, f1);
    		GL11.glRotatef(180F, 180F, 0.0F, 1.0F);
    		float f2 = mc.thePlayer.renderYawOffset;
    		float f3 = mc.thePlayer.rotationYaw;
    		float f4 = mc.thePlayer.rotationPitch;
    		float f5 = (float)(l + 51) - (float)xSize_lo;
    		float f6 = (float)((i1 + 75) - 50) - (float)ySize_lo;
    		GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
    		RenderHelper.enableStandardItemLighting();
    		GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
    		GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 0.0F, 1.0F, 0.0F);
    		((EntityLiving) drawEntity).renderYawOffset = (float)Math.atan(f5 / 40F) * 20F;
    		((EntityLiving) drawEntity).rotationYaw = (float)Math.atan(f5 / 40F) * 40F;
    		((EntityLiving) drawEntity).rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
    		GL11.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
    		RenderManager.instance.playerViewY = 180F;
    		RenderManager.instance.renderEntityWithPosYaw(drawEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
    		mc.thePlayer.renderYawOffset = f2;
    		mc.thePlayer.rotationYaw = f3;
    		mc.thePlayer.rotationPitch = f4;
    		GL11.glPopMatrix();
    		RenderHelper.disableStandardItemLighting();
    		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    	}
    }

	public void setTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.othersTextureName == null) {
			mod_PFLM_PlayerFormLittleMaid.othersTextureName = "default";
		}
		int i = getMaidColor();

		mod_PFLM_PlayerFormLittleMaid.othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.othersTextureName, i);
		if (mod_PFLM_PlayerFormLittleMaid.othersTexture == null) {
			int n = 0;
			for (; n < 16 && mod_PFLM_PlayerFormLittleMaid.othersTexture == null; n = n + 1) {
				i++;
				i = i & 0xf;
				setMaidColor(i);
				mod_PFLM_PlayerFormLittleMaid.othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.othersTextureName, i);
			}
			if (mod_PFLM_PlayerFormLittleMaid.othersTexture == null) {
				setNextTexturePackege(0);
				mod_PFLM_PlayerFormLittleMaid.othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.othersTextureName, i);
			}
		}
		mod_PFLM_PlayerFormLittleMaid.othersTextureModel[0] = null;
		mod_PFLM_PlayerFormLittleMaid.othersTextureModel[1] = null;
		mod_PFLM_PlayerFormLittleMaid.othersTextureModel[2] = null;
		//String s = mod_PFLM_PlayerFormLittleMaid.lastIndexProcessing(mod_PFLM_PlayerFormLittleMaid.othersTextureName, "_");
		Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(mod_PFLM_PlayerFormLittleMaid.othersTextureName);
    	Object[] models = mod_PFLM_PlayerFormLittleMaid.getTextureBoxModels(ltb);
		if (ltb != null) {
			mod_PFLM_PlayerFormLittleMaid.othersTextureModel[0] = models[0];
		}
		setArmorTextureValue();
	}

	public void setColorTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.othersTextureName == null) {
			mod_PFLM_PlayerFormLittleMaid.othersTextureName = "default";
		}
		int i = getMaidColor();
		String t = mod_PFLM_PlayerFormLittleMaid.othersTexture;
		mod_PFLM_PlayerFormLittleMaid.othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.othersTextureName, i);
		int n = 0;
		for (; n < 16 && mod_PFLM_PlayerFormLittleMaid.othersTexture == null; n = n + 1) {
			if (PFLM_Gui.colorReverse) {
				i--;
			} else {
				i++;
			}
			i = i & 0xf;
			setMaidColor(i);
			mod_PFLM_PlayerFormLittleMaid.othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(mod_PFLM_PlayerFormLittleMaid.othersTextureName, i);
		}
		if (mod_PFLM_PlayerFormLittleMaid.othersTexture == null) {
			mod_PFLM_PlayerFormLittleMaid.othersTexture = t;
			return;
		}
	}

	public void setArmorTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName == null) {
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.othersTextureName);
			if (mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName == null) {
				mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName = "default";
			}
		}
		//String s = mod_PFLM_PlayerFormLittleMaid.lastIndexProcessing(mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName, "_");
		Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName);
    	Object[] models = mod_PFLM_PlayerFormLittleMaid.getTextureBoxModels(ltb);
		if (ltb != null) {
			mod_PFLM_PlayerFormLittleMaid.othersTextureModel[1] = models[1];
			mod_PFLM_PlayerFormLittleMaid.othersTextureModel[2] = models[2];
		} else {
			ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox("default");
			if (ltb != null) {
				mod_PFLM_PlayerFormLittleMaid.othersTextureModel[1] = models[1];
				mod_PFLM_PlayerFormLittleMaid.othersTextureModel[2] = models[2];
			}
		}
	}

	public static void setNextTexturePackege(int i) {
		if (i == 0) {
			mod_PFLM_PlayerFormLittleMaid.othersTextureName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetNextPackege(mod_PFLM_PlayerFormLittleMaid.othersTextureName,
							getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.othersTextureName);
			Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(mod_PFLM_PlayerFormLittleMaid.othersTextureName);
			if (ltb == null) {
				if (mod_PFLM_PlayerFormLittleMaid.othersTextureName.indexOf("Biped") == -1) {
					setTextureArmorName("default");
				} else {
					setTextureArmorName("_Biped");
				}
			}
		}
		if (i == 1) {
			mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetNextArmorPackege(mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName);
		}
	}

	public static void setPrevTexturePackege(int i) {
		if (i == 0) {
			mod_PFLM_PlayerFormLittleMaid.othersTextureName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetPrevPackege(mod_PFLM_PlayerFormLittleMaid.othersTextureName,
							getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.othersTextureName);
			Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(mod_PFLM_PlayerFormLittleMaid.othersTextureName);
			if (ltb == null) {
				if (mod_PFLM_PlayerFormLittleMaid.othersTextureName.indexOf("Biped") == -1) {
					setTextureArmorName("erasearmor");
				} else {
					setTextureArmorName("_Biped");
				}
			}
		}
		if (i == 1) {
			mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetPrevArmorPackege(mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName);
		}
	}

    public static int getMaidColor()
    {
    	return mod_PFLM_PlayerFormLittleMaid.othersMaidColor;
    }

    public static void setMaidColor(int i)
    {
    	mod_PFLM_PlayerFormLittleMaid.othersMaidColor = i & 0xf;
    }

    public static void setTextureName(String s) {
    	mod_PFLM_PlayerFormLittleMaid.othersTextureName = s;
    }

    public static void setTextureArmorName(String s) {
    	mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName = s;
    }

    public static String getChangeModeString(int i) {
		String s = null;
		switch (i) {
		case 0:
			s = "false";
			break;
		case 1:
			s = "Player";
			break;
		case 2:
			s = "OthersSettingOffline";
			break;
		case 3:
			s = "PlayerOffline";
			break;
		case 4:
			s = "PlayerOnline";
			break;
		case 5:
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
}
