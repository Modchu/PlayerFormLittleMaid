package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class PFLM_GuiOthersPlayerIndividualCustomize extends
		PFLM_GuiOthersPlayer {
	public static String playerName;
	public static int othersMaidColor;
	public static float othersModelScale;
	public static String othersTextureName;
	public static String othersTexture;
	public static String othersTextureArmorName;
	public static int changeMode;
	public static int othersHandednessMode;

	public PFLM_GuiOthersPlayerIndividualCustomize(GuiScreen par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		parentScreen = par1GuiScreen;
	}

	public PFLM_GuiOthersPlayerIndividualCustomize(GuiScreen par1GuiScreen, Minecraft minecraft, World world, String s) {
		this(par1GuiScreen, world);
		playerName = s;
	}

	@Override
	public void initGui() {
		buttonList.clear();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		buttonList.add(new Modchu_GuiSmallButton(400, x + 75, y + 10, 75, 15, "ChangeMode"));
		buttonList.add(new Modchu_GuiSmallButton(200, x, y + 100, 75, 20, "Save"));
		buttonList.add(new Modchu_GuiSmallButton(201, x + 75, y + 100, 75, 20, "Return"));
		buttonList.add(new Modchu_GuiSmallButton(58, x + 75, y + 55, 75, 15, "Handedness"));
		if(changeMode == modeOthersSettingOffline) {
			buttonList.add(new Modchu_GuiSmallButton(56, x - 10, y + 10, 85, 15, "ModelListSelect"));
			//buttonList.add(new Modchu_GuiSmallButton(57, x - 10, y + 10, 85, 15, "ArmorListSelect"));
			buttonList.add(new Modchu_GuiSmallButton(50, x + 40, y + 25, 15, 15, "<"));
			buttonList.add(new Modchu_GuiSmallButton(51, x + 55, y + 25, 15, 15, ">"));
			buttonList.add(new Modchu_GuiSmallButton(52, x + 40, y + 40, 15, 15, "-"));
			buttonList.add(new Modchu_GuiSmallButton(53, x + 55, y + 40, 15, 15, "+"));
			buttonList.add(new Modchu_GuiSmallButton(54, x + 40, y + 55, 15, 15, "<"));
			buttonList.add(new Modchu_GuiSmallButton(55, x + 55, y + 55, 15, 15, ">"));
			buttonList.add(new Modchu_GuiSmallButton(20, x, y + 70, 75, 15, "showArmor"));
			if (mod_PFLM_PlayerFormLittleMaid.useScaleChange) {
				if(modelScaleButton) {
					buttonList.add(new Modchu_GuiSmallButton(3, width / 2 - 140, height / 2 + 20, 50, 20, "Default"));
					buttonList.add(new Modchu_GuiSmallButton(4, width / 2 - 90, height / 2 + 20, 30, 20, "UP"));
					buttonList.add(new Modchu_GuiSmallButton(5, width / 2 - 170, height / 2 + 20, 30, 20, "Down"));
					buttonList.add(new Modchu_GuiSmallButton(6, x + 75, y + 25, 75, 15, "Close"));
				} else {
					buttonList.add(new Modchu_GuiSmallButton(7, x + 75, y + 25, 75, 15, "ScaleChange"));
				}
			}
		}
		setTextureValue();
		if(mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[0] != null) {
			if (othersModelScale == 0.0F) {
				othersModelScale = ((MultiModelBaseBiped) mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[0]).getModelScale();
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
			othersModelScale = ((MultiModelBaseBiped) mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[0]).getModelScale();
        }
        //isModelSize UP
        if(guibutton.id == 4)
        {
            if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
            	othersModelScale += othersModelScale <= 9.5 ? 0.5F : 0;
            } else {
                if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
                	othersModelScale += othersModelScale <= 9.99 ? 0.01F : 0;
                } else {
                	othersModelScale += othersModelScale <= 9.9 ? 0.1F : 0;
                }
            }
        }
        //isModelSize Down
        if(guibutton.id == 5)
        {
            if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
            	othersModelScale -= othersModelScale > 0.5 ? 0.5F : 0;
            } else {
                if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
                	othersModelScale -= othersModelScale > 0.01 ? 0.01F : 0;
                } else {
                	othersModelScale -= othersModelScale > 0.1 ? 0.1F : 0;
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
    		othersMaidColor--;
    		colorReverse = true;
    	}
    	if(guibutton.id == 53) {
    		othersMaidColor++;
    		colorReverse = false;
    	}
    	if(guibutton.id == 52
    			| guibutton.id == 53)
    	{
    		setMaidColor(othersMaidColor);
    		setColorTextureValue();
    		noSaveFlag = true;
    		mod_PFLM_PlayerFormLittleMaid.setResetFlag(true);
    	}
    	//ArmorChange
    	if(guibutton.id == 54) setPrevTexturePackege(1);
    	if(guibutton.id == 55) setNextTexturePackege(1);
    	if(guibutton.id == 54
    			| guibutton.id == 55)
    	{
    		setTextureValue();
    		noSaveFlag = true;
    	}
    	//ModelListSelect
    	if(guibutton.id == 56) {
    		mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 4, playerName));
    	}
    	//ArmorListSelect
    	if(guibutton.id == 57) {

    	}
    	//Handedness
    	if(guibutton.id == 58) {
    		if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
    			othersHandednessMode--;
    		} else {
    			othersHandednessMode++;
    		}
    		if (othersHandednessMode < -1) othersHandednessMode = 1;
    		if (othersHandednessMode > 1) othersHandednessMode = -1;
    		return;
    	}
        //Save
        if(guibutton.id == 200)
        {
        	setPlayerLocalData();
        	mod_PFLM_PlayerFormLittleMaid.saveOthersPlayerParamater(true);
        	Modchu_Config.clearCfgData();
        	noSaveFlag = false;
        	mc.displayGuiScreen(parentScreen);
        	mod_PFLM_PlayerFormLittleMaid.clearPlayers();
        }
        //Return
        if(guibutton.id == 201)
        {
        	mc.displayGuiScreen(parentScreen);
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
        	//mod_PFLM_PlayerFormLittleMaid.clearPlayers();
        	initGui();
        }
    }

    private void setPlayerLocalData() {
    	String s[] = new String[6];
    	s[0] = othersTextureName;
    	s[1] = othersTextureArmorName;
    	s[2] = ""+othersMaidColor;
    	s[3] = ""+othersModelScale;
    	s[4] = ""+changeMode;
    	s[5] = ""+othersHandednessMode;
    	mod_PFLM_PlayerFormLittleMaid.playerLocalData.put(playerName, s);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
    	int xSize = 80;
    	int ySize = 50;
    	int guiLeft = width / 2 - xSize + 30;
    	int guiTop = height / 2 - (ySize / 2) - 20;
    	StringBuilder s10 = (new StringBuilder()).append("playerName : ");
    	s10 = s10.append(playerName);
    	fontRenderer.drawString(s10.toString(), 10, 10, 0xffffff);
    	fontRenderer.drawString("ModelSetting", 10, 20, 0xffffff);
    	StringBuilder s = (new StringBuilder()).append("TextureName : ");
    	StringBuilder s1 = (new StringBuilder()).append("ArmorName : ");
    	StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
    	StringBuilder s9 = (new StringBuilder()).append("changeMode : ");
    	s9 = s9.append(getChangeModeString(changeMode));
    	fontRenderer.drawString(s9.toString(), guiLeft, guiTop + 130, 0xffffff);
    	StringBuilder s11 = (new StringBuilder()).append("Handedness : ");
    	s11 = s11.append(getHandednessModeString(othersHandednessMode));
    	//if (othersHandednessMode == -1) s11 = s11.append(" Result : ").append(getHandednessModeString(handedness));
    	fontRenderer.drawString(s11.toString(), guiLeft, guiTop + 140, 0xffffff);
    	if(changeMode == modeOthersSettingOffline) {
		s = s.append(othersTextureName);
		fontRenderer.drawString(s.toString(), guiLeft, guiTop + 90, 0xffffff);
		s2 = s2.append(othersMaidColor);
		fontRenderer.drawString(s2.toString(), guiLeft, guiTop + 100, 0xffffff);
		s1 = s1.append(othersTextureArmorName);
		fontRenderer.drawString(s1.toString(), guiLeft, guiTop + 110, 0xffffff);
		StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
		s8 = s8.append(showArmor);
		fontRenderer.drawString(s8.toString(), guiLeft, guiTop + 120, 0xffffff);
		if(mod_PFLM_PlayerFormLittleMaid.useScaleChange
				&& modelScaleButton) {
			String s6 = "ModelScale : "+othersModelScale;
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
		((EntityLiving) drawEntity).texture = othersTexture;
		((PFLM_EntityPlayerDummy) drawEntity).textureModel = mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel;
		((PFLM_EntityPlayerDummy) drawEntity).maidColor = othersMaidColor;
		((PFLM_EntityPlayerDummy) drawEntity).textureName = othersTextureName;
		((PFLM_EntityPlayerDummy) drawEntity).textureArmorName = othersTextureArmorName;
		((PFLM_EntityPlayerDummy) drawEntity).textureArmor0 = mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureArmor0;
		((PFLM_EntityPlayerDummy) drawEntity).textureArmor1 = mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureArmor1;
		((PFLM_EntityPlayerDummy) drawEntity).modelScale = othersModelScale;
		((PFLM_EntityPlayerDummy) drawEntity).showArmor = showArmor;
		((PFLM_EntityPlayerDummy) drawEntity).others = true;
		//Modchu_Debug.mDebug("textureName="+othersTextureName);
		//Modchu_Debug.mDebug("texture="+othersTexture);
		//Modchu_Debug.mDebug("textureArmorName="+othersTextureArmorName);
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
		if (othersTextureName == null) {
			othersTextureName = "default";
		}
		int i = getMaidColor();

		othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(othersTextureName, i);
		if (othersTexture == null) {
			int n = 0;
			for (; n < 16 && othersTexture == null; n = n + 1) {
				i++;
				i = i & 0xf;
				setMaidColor(i);
				othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(othersTextureName, i);
			}
			if (othersTexture == null) {
				setNextTexturePackege(0);
				othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(othersTextureName, i);
			}
		}
		if (mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel != null) {
			mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[0] = null;
			mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[1] = null;
			mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[2] = null;
		} else {
			mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel = new Object[3];
		}
		Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(othersTextureName);
		Object[] models = mod_PFLM_PlayerFormLittleMaid.getTextureBoxModels(ltb);
		if (ltb != null) mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[0] = models[0];
		setArmorTextureValue();
	}

	public void setColorTextureValue() {
		if (othersTextureName == null) {
			othersTextureName = "default";
		}
		int i = getMaidColor();
		String t = othersTexture;
		othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(othersTextureName, i);
		int n = 0;
		for (; n < 16 && othersTexture == null; n = n + 1) {
			if (PFLM_Gui.colorReverse) {
				i--;
			} else {
				i++;
			}
			i = i & 0xf;
			setMaidColor(i);
			othersTexture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(othersTextureName, i);
		}
		if (othersTexture == null) othersTexture = t;
	}

	public void setArmorTextureValue() {
		if (othersTextureArmorName == null) {
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(othersTextureName));
			if (othersTextureArmorName == null) {
				othersTextureArmorName = "default";
			}
		}
		Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(othersTextureArmorName);
    	Object[] models = mod_PFLM_PlayerFormLittleMaid.getTextureBoxModels(ltb);
		if (ltb != null) {
			mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[1] = models[1];
			mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[2] = models[2];
		} else {
			ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox("default");
			if (ltb != null) {
				mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[1] = models[1];
				mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureModel[2] = models[2];
			}
		}
	}

	public static void setNextTexturePackege(int i) {
		if (i == 0) {
			othersTextureName =
					mod_PFLM_PlayerFormLittleMaid.textureManagerGetNextPackege(othersTextureName, getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(othersTextureName));
		}
		if (i == 1) {
			othersTextureArmorName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetNextArmorPackege(othersTextureArmorName);
		}
	}

	public static void setPrevTexturePackege(int i) {
		if (i == 0) {
			othersTextureName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetPrevPackege(othersTextureName, getMaidColor());
			setTextureArmorName(mod_PFLM_PlayerFormLittleMaid.getArmorName(othersTextureName));
		}
		if (i == 1) {
			othersTextureArmorName = mod_PFLM_PlayerFormLittleMaid.textureManagerGetPrevArmorPackege(othersTextureArmorName);
		}
	}

    public static int getMaidColor()
    {
    	return othersMaidColor;
    }

    public static void setMaidColor(int i)
    {
    	othersMaidColor = i & 0xf;
    }

    public static void setTextureName(String s) {
    	othersTextureName = s;
    }

    public static void setTextureArmorName(String s) {
    	othersTextureArmorName = s;
    }

    public static void setModelScale(float f)
    {
    	othersModelScale = f;
    }

	public static void setChangeMode(int i) {
		changeMode = i;
	}
}
