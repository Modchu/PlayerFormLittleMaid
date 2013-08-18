package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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
	public EntityPlayer thePlayer;

	public PFLM_GuiOthersPlayer(GuiScreen par1GuiScreen, World world) {
		popWorld = world;
		parentScreen = par1GuiScreen;
		drawEntitySetFlag = true;
		thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
	}

	@Override
	public void initGui() {
		buttonList.clear();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 400, x + 75, y + 10, 75, 15, "ChangeMode" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 401, x, y + 85 , 100, 15, "IndividualCustomize" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 200, x, y + 100, 75, 20, "Save" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 201, x + 75, y + 100, 75, 20, "Return" }));
		if(changeMode == modeOthersSettingOffline
				| changeMode == modeRandom) {
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 58, x + 75, y + 55, 75, 15, "Handedness" }));
		}
		if(changeMode == modeOthersSettingOffline) {
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 56, x - 10, y + 10, 85, 15, "ModelListSelect" }));
			//buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 57, x - 10, y + 10, 85, 15, "ArmorListSelect" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 50, x + 40, y + 25, 15, 15, "<" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 51, x + 55, y + 25, 15, 15, ">" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 52, x + 40, y + 40, 15, 15, "-" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 53, x + 55, y + 40, 15, 15, "+" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 54, x + 40, y + 55, 15, 15, "<" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 55, x + 55, y + 55, 15, 15, ">" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 20, x, y + 70, 75, 15, "showArmor" }));
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange) {
				if(modelScaleButton) {
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 3, width / 2 - 140, height / 2 + 20, 50, 20, "Default" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 4, width / 2 - 90, height / 2 + 20, 30, 20, "UP" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 5, width / 2 - 170, height / 2 + 20, 30, 20, "Down" }));
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 6, x + 75, y + 25, 75, 15, "Close" }));
				} else {
					buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 7, x + 75, y + 25, 75, 15, "ScaleChange" }));
				}
			}
		}
		setTextureValue();
		if(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[0] != null) {
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale == 0.0F) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[0] instanceof MultiModelBaseBiped ? ((MultiModelBaseBiped) mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[0]).getModelScale() : 0.9375F;
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
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[0] instanceof MultiModelBaseBiped ? ((MultiModelBaseBiped) mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[0]).getModelScale() : 0.9375F;
		}
		//isModelSize UP
		if(guibutton.id == 4)
		{
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale += mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale <= 9.5 ? 0.5F : 0;
			} else {
				if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
					mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale += mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale <= 9.99 ? 0.01F : 0;
				} else {
					mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale += mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale <= 9.9 ? 0.1F : 0;
				}
			}
		}
		//isModelSize Down
		if(guibutton.id == 5)
		{
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale -= mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale > 0.5 ? 0.5F : 0;
			} else {
				if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
					mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale -= mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale > 0.01 ? 0.01F : 0;
				} else {
					mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale -= mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale > 0.1 ? 0.1F : 0;
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
			drawEntitySetFlag = true;
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
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor--;
			colorReverse = true;
		}
		if(guibutton.id == 53) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor++;
			colorReverse = false;
		}
		if(guibutton.id == 52
				| guibutton.id == 53)
		{
			setMaidColor(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor);
			setColorTextureValue();
			noSaveFlag = true;
			drawEntitySetFlag = true;
		}
		//ArmorChange
		if(guibutton.id == 54) setPrevTexturePackege(1);
		if(guibutton.id == 55) setNextTexturePackege(1);
		if(guibutton.id == 54
				| guibutton.id == 55)
		{
			noSaveFlag = true;
			drawEntitySetFlag = true;
			mod_PFLM_PlayerFormLittleMaid.pflm_main.setResetFlag(true);
		}
		//ModelListSelect
		if(guibutton.id == 56) {
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiModelSelect(this, popWorld, 2) });
			//mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, 2));
		}
		//ArmorListSelect
		if(guibutton.id == 57) {

		}
		//Handedness
		if(guibutton.id == 58) {
			if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode--;
			} else {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode++;
			}
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode < -1) mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode = 1;
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode > 1) mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode = -1;
			return;
		}
		//Save
		if(guibutton.id == 200)
		{
			mod_PFLM_PlayerFormLittleMaid.pflm_main.saveOthersPlayerParamater(false);
			PFLM_Config.clearCfgData();
			mod_PFLM_PlayerFormLittleMaid.pflm_main.clearPlayers();
			noSaveFlag = false;
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ null });
			//mc.displayGuiScreen(null);
		}
		//Return
		if(guibutton.id == 201)
		{
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_Gui(popWorld) });
			//mc.displayGuiScreen(new PFLM_Gui(popWorld));
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
			mod_PFLM_PlayerFormLittleMaid.pflm_main.clearPlayers();
			drawEntitySetFlag = true;
			initGui();
		}
		//IndividualCustomize
		if(guibutton.id == 401)
		{
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiOthersPlayerIndividualCustomizeSelect(this, popWorld) });
			//mc.displayGuiScreen(new PFLM_GuiOthersPlayerIndividualCustomizeSelect(this, mc, popWorld));
		}
	}

	public void modelChange() {
		noSaveFlag = true;
		drawEntitySetFlag = true;
		mod_PFLM_PlayerFormLittleMaid.pflm_main.setResetFlag(true);
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
			s10 = s10.append(getHandednessModeString(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode));
			//if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersHandednessMode == -1) s10 = s10.append(" Result : ").append(getHandednessModeString(handedness));
			fontRenderer.drawString(s10.toString(), guiLeft, guiTop + 140, 0xffffff);
		}
		if(changeMode == modeOthersSettingOffline) {
			s = s.append(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName);
			fontRenderer.drawString(s.toString(), guiLeft, guiTop + 90, 0xffffff);
			s2 = s2.append(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor);
			fontRenderer.drawString(s2.toString(), guiLeft, guiTop + 100, 0xffffff);
			s1 = s1.append(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName);
			fontRenderer.drawString(s1.toString(), guiLeft, guiTop + 110, 0xffffff);
			StringBuilder s8 = (new StringBuilder()).append("showArmor : ");
			s8 = s8.append(showArmor);
			fontRenderer.drawString(s8.toString(), guiLeft, guiTop + 120, 0xffffff);
			if(mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange
					&& modelScaleButton) {
				String s6 = "ModelScale : "+mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale;
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
			if (drawEntitySetFlag) {
				setTextureValue();
				drawEntitySetFlag = false;
				// 152delete((EntityLiving) drawEntity).texture = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture;
				//PFLM_RenderPlayerDummy.modelData.setCapsValue(PFLM_RenderPlayerDummy.modelData.caps_ResourceLocation, 0, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture);
				((PFLM_EntityPlayerDummy) drawEntity).textureModel = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel;
				PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor);
				((PFLM_EntityPlayerDummy) drawEntity).textureName = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName;
				((PFLM_EntityPlayerDummy) drawEntity).textureArmorName = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName;
				((PFLM_EntityPlayerDummy) drawEntity).textureArmor0 = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmor0;
				((PFLM_EntityPlayerDummy) drawEntity).textureArmor1 = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmor1;
				((PFLM_EntityPlayerDummy) drawEntity).modelScale = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersModelScale;
				((PFLM_EntityPlayerDummy) drawEntity).showArmor = showArmor;
				((PFLM_EntityPlayerDummy) drawEntity).others = true;
				//Modchu_Debug.mDebug("((PFLM_EntityPlayerDummy) drawEntity).textureModel.getClass()="+((PFLM_EntityPlayerDummy) drawEntity).textureModel[0].getClass());
				//Modchu_Debug.mDebug("textureName="+mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName);
				//Modchu_Debug.mDebug("texture="+mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture);
				//Modchu_Debug.mDebug("textureArmorName="+mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName);
				drawEntity.setPosition(thePlayer.posX , thePlayer.posY, thePlayer.posZ);
			}
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
			if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender
					&& mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			float f5 = (float)(l + 51) - (float)xSize_lo;
			float f6 = (float)((i1 + 75) - 50) - (float)ySize_lo;
			GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
			RenderHelper.enableStandardItemLighting();
			GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 0.0F, 1.0F, 0.0F);
			((EntityLiving) drawEntity).renderYawOffset = (float)Math.atan(f5 / 40F) * 20F;
			((EntityLiving) drawEntity).rotationYaw = (float)Math.atan(f5 / 40F) * 40F;
			((EntityLiving) drawEntity).rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
			GL11.glTranslatef(0.0F, thePlayer.yOffset, 0.0F);
			RenderManager.instance.playerViewY = 180F;
			RenderManager.instance.renderEntityWithPosYaw(drawEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
			GL11.glPopMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		}
	}

	public void setTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName == null) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName = "default";
		}
		int i = getMaidColor();

		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName, i);
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture == null) {
			int n = 0;
			for (; n < 16 && mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture == null; n = n + 1) {
				i++;
				i = i & 0xf;
				setMaidColor(i);
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName, i);
			}
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture == null) {
				setNextTexturePackege(0);
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName, i);
			}
		}
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel != null) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[0] = null;
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[1] = null;
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[2] = null;
		} else {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel = new MMM_ModelMultiBase[3];
		}
		Object[] o = mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(null, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName, false, true);
		if (o != null
				&& o[0] != null) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[0] = o[0];
		}
		setArmorTextureValue();
	}

	public void setColorTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName == null) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName = "default";
		}
		int i = getMaidColor();
		Object t = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture;
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName, i);
		int n = 0;
		for (; n < 16 && mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture == null; n = n + 1) {
			i = colorReverse ? i - 1 : i + 1;
			i = i & 0xf;
			setMaidColor(i);
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName, i);
		}
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture == null) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTexture = t;
		}
		for(int i2 = 0; i2 < mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel.length; i2++) {
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel != null
					&& mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[i2] instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[i2]).changeColor(mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.pflm_RenderPlayerDummyMaster.modelData);
		}
	}

	public void setArmorTextureValue() {
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName == null
				| (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName != null
				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName.isEmpty())) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName);
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName == null
					| (mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName != null
					&& mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName.isEmpty())) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName = "default";
			}
		}
		Object ltb = mod_Modchu_ModchuLib.modchu_Main.getTextureBox(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName);
		Object[] models = mod_Modchu_ModchuLib.modchu_Main.getTextureBoxModels(ltb);
		if (models != null) {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[1] = models[1];
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[2] = models[2];
		} else {
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName = mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName.indexOf("_Biped") == -1 ? "default" : "Biped";
			models = mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(null, mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName, false, false);
			if (models != null) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[1] = models[1];
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureModel[2] = models[2];
			}
		}
	}

	public static void setNextTexturePackege(int i) {
		if (i == 0) {
			String s = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetNextPackege(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName, getMaidColor());
			if (s != null
					&& !s.isEmpty()) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName = s;
				s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName);
				if (s != null
						&& !s.isEmpty()) setTextureArmorName(s);
			}
		}
		if (i == 1) {
			String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName);
			if (s != null
					&& !s.isEmpty()) setTextureArmorName(s);
			Modchu_Debug.mDebug("setNextTexturePackege(int) mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName="+mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName);
		}
	}

	public static void setPrevTexturePackege(int i) {
		if (i == 0) {
			String s = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetPrevPackege(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName, getMaidColor());
			if (s != null
					&& !s.isEmpty()) {
				mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName = s;
				s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName);
				if (s != null
						&& !s.isEmpty()) setTextureArmorName(s);
			}
		}
		if (i == 1) {
			String s = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetPrevArmorPackege(mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName);
			if (s != null
					&& !s.isEmpty()) setTextureArmorName(s);
		}
	}

	public static int getMaidColor()
	{
		return mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor;
	}

	public static void setMaidColor(int i)
	{
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor = i & 0xf;
	}

	public static void setTextureName(String s) {
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName = s;
	}

	public static void setTextureArmorName(String s) {
		mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName = s;
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
