package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class PFLM_GuiModelSelect extends GuiScreen {
	private static float xSize_lo;
	private static float ySize_lo;
	private static World popWorld;
	private static int modelSelectMode;
	public static int modelColor;
	private static PFLM_EntityPlayerDummy entityPlayerFormLittleMaidDummy;
	private static int modelListx = 30;
	private static int modelListy = 60;
	private static int pointX;
	private static int pointY;
	private static int selectSlot = -1;
	private static int offsetSlot = 0;
	private static double textureRect[] = new double[8];
	private static BufferedImage bufferedimage;
	private static long lastClicked;
	private static int modelNamber = 0;
	private static int selectBoxX = 8;
	private static int selectBoxY = 3;
	private static String playerName;
	private static GuiScreen parentScreen;
	private static int select;
	private static boolean changeColorFlag = false;
	private boolean dummySettingInit;
	private EntityPlayer thePlayer;

	public PFLM_GuiModelSelect(GuiScreen par1GuiScreen, World world, int i, String s) {
		this(par1GuiScreen, world, i);
		playerName = s;
		thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
	}

	public PFLM_GuiModelSelect(GuiScreen par1GuiScreen, World world, int i, int j) {
		this(par1GuiScreen, world, i);
		select = j;
		thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
	}

	public PFLM_GuiModelSelect(GuiScreen par1GuiScreen, World world, int i) {
		thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		popWorld = world;
		parentScreen = par1GuiScreen;
		modelSelectMode = i;
		modelColor = modelSelectMode == 0 ? mod_PFLM_PlayerFormLittleMaid.pflm_main.maidColor :
			modelSelectMode == 2 ? mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor :
				modelSelectMode == 4 ? PFLM_GuiOthersPlayerIndividualCustomize.othersMaidColor :
					mod_PFLM_PlayerFormLittleMaid.pflm_main.maidColor;
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize) {
			GameSettings gameSettings = (GameSettings) Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
			if (gameSettings.thirdPersonView > 0) Modchu_Reflect.setFieldObject(GameSettings.class, "field_74320_O", "thirdPersonView", Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mod_Modchu_ModchuLib.modchu_Main.getMinecraft()), (Object) 0);
		}
		entityPlayerFormLittleMaidDummy = new PFLM_EntityPlayerDummy(popWorld);
		entityPlayerFormLittleMaidDummy.textureArmor0 = new String[] {"","",""};
		entityPlayerFormLittleMaidDummy.textureArmor1 = new String[] {"","",""};
		entityPlayerFormLittleMaidDummy.modelScale = 0.0F;
		entityPlayerFormLittleMaidDummy.showArmor = modelSelectMode % 2 == 0 ? false : true;
		entityPlayerFormLittleMaidDummy.others = false;
		entityPlayerFormLittleMaidDummy.setPosition(thePlayer.posX , thePlayer.posY, thePlayer.posZ);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, modelColor);
		try {
			bufferedimage = ImageIO.read((File) Modchu_Reflect.invokeMethod(Class.class, "getResource", new Class[]{ String.class }, Modchu_Reflect.loadClass("Minecraft"), new Object[]{ "/particles.png" }));
			//bufferedimage = ImageIO.read((Minecraft.class).getResource("/particles.png"));
			int sx = bufferedimage.getWidth() / 128;
			int sy = bufferedimage.getHeight() / 128;
			bufferedimage = bufferedimage.getSubimage(0, 32 * sy, 8 * sx, 8 * sy);
		} catch (Exception e) {
		}
		resetTextureRect();
		dummySettingInit = false;
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 0, 80, 185, 15, 15, "<" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 1, 100, 185, 15, 15, ">" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 2, 145, 180, 15, 15, "<" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 3, 160, 180, 15, 15, ">" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 100, 155, 205, 75, 20, "select" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 101, 240, 205, 75, 20, "return" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 999, 0, 0, 0, 0, "" }));
	}

	@Override
	public void updateScreen() {
		try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
		}
	}

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
    	if(!guibutton.enabled)
    	{
    		return;
    	}
    	//pagePrev
    	if(guibutton.id == 0)
    	{
    		int i = offsetSlot - (selectBoxX * selectBoxY);
    		if (i > -1) offsetSlot -= selectBoxX * selectBoxY;
    		selectSlot = -1;
    		dummySettingInit = false;
    		return;
    	}
    	//pageNext
    	if(guibutton.id == 1)
    	{
    		int i = (selectBoxX * selectBoxY) + offsetSlot;
    		if (i < mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTexturesSize()) {
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][i] != -1) offsetSlot += selectBoxX * selectBoxY;
    		}
    		selectSlot = -1;
    		dummySettingInit = false;
    		return;
    	}
    	//colorPrev
    	if(guibutton.id == 2)
    	{
    		modelColor--;
    		if (modelColor < 0) modelColor = 15;
    		maxPageCheack();
    		changeColorFlag = true;
    		dummySettingInit = false;
    		return;
    	}
    	//colorNext
    	if(guibutton.id == 3)
    	{
    		modelColor++;
    		if (modelColor > 15) modelColor = 0;
    		maxPageCheack();
    		changeColorFlag = true;
    		dummySettingInit = false;
    		return;
    	}
    	//select
    	if(guibutton.id == 100)
    	{
    		if (selectSlot > -1
    				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot] != -1) {
    			selected();
    		}
    	}
    	//return
    	if(guibutton.id == 101)
    	{
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ parentScreen });
    		//mc.displayGuiScreen(parentScreen);
    	}
    }

    private void maxPageCheack() {
    	if(offsetSlot / (selectBoxX * selectBoxY) > (mod_PFLM_PlayerFormLittleMaid.pflm_main.maxTexturesNamber[modelColor] - 1) / (selectBoxX * selectBoxY)) {
    		offsetSlot = (mod_PFLM_PlayerFormLittleMaid.pflm_main.maxTexturesNamber[modelColor] - 1) / (selectBoxX * selectBoxY) * (selectBoxX * selectBoxY);
    	}
	}

	@Override
	public void drawScreen(int i, int j, float f)
	{
		drawDefaultBackground();
		super.drawScreen(i, j, f);
		xSize_lo = i;
		ySize_lo = j;
		modelNamber = offsetSlot;
		float f1 = 20F;
		for (int i2 = 0 ; i2 < selectBoxY ; i2++) {
			for (int i1 = 0 ; i1 < selectBoxX ; i1++) {
				drawModel(i1 * 25 + modelListx, i2 * 50 + modelListy, f1, modelNamber);
				++modelNamber;
			}
		}
		dummySettingInit = true;
		if (bufferedimage != null
				&& selectSlot > -1
				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot] != -1) {
			if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
				Modchu_Reflect.invokeMethod("TextureUtil", "func_110987_a", new Class[]{ int.class, BufferedImage.class }, new Object[]{ 0, bufferedimage });
				//TextureUtil.func_110987_a(0, bufferedimage);
			} else {
				Object renderEngine = Modchu_Reflect.getFieldObject("Minecraft", "field_71446_o", "renderEngine", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
				Modchu_Reflect.invokeMethod("RenderEngine", "func_78351_a", "setupTexture", new Class[]{ BufferedImage.class, int.class }, renderEngine, new Object[]{ bufferedimage, 0 });
				//mc.renderEngine.setupTexture(bufferedimage, 0);
			}
			renderTexture((selectSlot - offsetSlot) % selectBoxX * 25 + modelListx, (selectSlot - offsetSlot) / selectBoxX * 55 + modelListy - 50, 8, 8);
		}
		StringBuilder s5 = (new StringBuilder()).append("Page : ").append(offsetSlot / (selectBoxX * selectBoxY));
		s5.append(" / ").append((mod_PFLM_PlayerFormLittleMaid.pflm_main.maxTexturesNamber[modelColor] - 1) / (selectBoxX * selectBoxY));
		fontRenderer.drawString(s5.toString(), 55, 170, 0xffffff);
		StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
		s2 = s2.append(modelColor);
		fontRenderer.drawString(s2.toString(), 130, 170, 0xffffff);
		if (selectSlot > -1) {
			entityPlayerFormLittleMaidDummy.textureName = mod_Modchu_ModchuLib.modchu_Main.getPackege(modelColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot]);
			if (entityPlayerFormLittleMaidDummy.textureName == null) {
				return;
			}
			//entityPlayerFormLittleMaidDummy.texture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, modelColor);
			PFLM_RenderPlayerDummyMaster.setResourceLocation(entityPlayerFormLittleMaidDummy, 0, mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, modelColor));
			Object ltb = mod_Modchu_ModchuLib.modchu_Main.getTextureBox(entityPlayerFormLittleMaidDummy.textureName);
			entityPlayerFormLittleMaidDummy.textureModel = (Object[]) mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(null, entityPlayerFormLittleMaidDummy.textureName, false, true);
			entityPlayerFormLittleMaidDummy.textureArmorName = modelSelectMode % 2 == 0 ? "" : mod_Modchu_ModchuLib.modchu_Main.getTextureBoxFileName(ltb);
			mod_PFLM_PlayerFormLittleMaid.pflm_main.changeColor(entityPlayerFormLittleMaidDummy);
			StringBuilder s = (new StringBuilder()).append("TextureName : ");
			s = s.append(entityPlayerFormLittleMaidDummy.textureName);
			fontRenderer.drawString(s.toString(), 220, 170, 0xffffff);
			GL11.glPushMatrix();
			GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
			GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			f1 = 50F;
			if (entityPlayerFormLittleMaidDummy.height > 2F) {
				f1 = f1 * 2F / entityPlayerFormLittleMaidDummy.height;
			}
			GL11.glTranslatef(300, 150, 50F);
			GL11.glScalef(-f1, f1, f1);
			GL11.glRotatef(180F, 0F, 0.0F, 0.0F);
			float f5 = (float)(width / 2 + 100) - xSize_lo;
			float f6 = (float)((height / 2 + 40) - 10) - ySize_lo;
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
			else if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender
					| mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 160) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			RenderHelper.enableStandardItemLighting();
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef((float)Math.atan(f5 / 40F) * 30F, 0.0F, 1.0F, 0.0F);
			entityPlayerFormLittleMaidDummy.renderYawOffset = (float)Math.atan(f5 / 40F) * 10F;
			entityPlayerFormLittleMaidDummy.rotationYaw = -(float)Math.atan(f5 / 40F) * 20F;
			entityPlayerFormLittleMaidDummy.rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
			GL11.glTranslatef(0.0F, entityPlayerFormLittleMaidDummy.yOffset, 0.0F);
			//RenderManager.instance.playerViewY = 180F;
			RenderManager.instance.renderEntityWithPosYaw(entityPlayerFormLittleMaidDummy, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
			GL13.glMultiTexCoord2f(33985 /*GL_TEXTURE1_ARB*/, 240.0F, 240.0F);
			GL11.glPopMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
			entityPlayerFormLittleMaidDummy.renderYawOffset = 0F;
			entityPlayerFormLittleMaidDummy.rotationYaw = 0F;
			entityPlayerFormLittleMaidDummy.rotationPitch = 0F;
		}
		if (changeColorFlag) changeColorFlag = false;
	}

	private void drawModel(int i, int j , float f, int modelNamber) {
		float f5 = (float)i - xSize_lo;
		float f6 = (float)(j - 50) - ySize_lo;
		if (modelNamber >= mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor].length) return;
		int i1 = mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][modelNamber];
		if (i1 < 0
				| i1 >= mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTexturesSize()) return;
		Object ltb = mod_Modchu_ModchuLib.modchu_Main.getTextureBox(i1);
		String packgeName = null;
		if (ltb != null) packgeName = mod_Modchu_ModchuLib.modchu_Main.getTextureBoxFileName(ltb);
		if (packgeName != null) ;else {
			packgeName = entityPlayerFormLittleMaidDummy.textureName != null
					&& entityPlayerFormLittleMaidDummy.textureName.indexOf("Biped") > -1 ? "Biped" : "default";
			ltb = mod_Modchu_ModchuLib.modchu_Main.getTextureBox(packgeName);
			if (ltb != null) packgeName = mod_Modchu_ModchuLib.modchu_Main.getTextureBoxFileName(ltb);
		}
		entityPlayerFormLittleMaidDummy.textureName = packgeName;
		if (entityPlayerFormLittleMaidDummy.textureName != null
				&& ltb != null) ;else return;
		//entityPlayerFormLittleMaidDummy.texture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, modelColor);
		PFLM_RenderPlayerDummyMaster.setResourceLocation(entityPlayerFormLittleMaidDummy, 0, mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, modelColor));
		//Modchu_Debug.mDebug("texture="+mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, modelColor));
		entityPlayerFormLittleMaidDummy.textureModel = (Object[]) mod_Modchu_ModchuLib.modchu_Main.modelNewInstance(null, entityPlayerFormLittleMaidDummy.textureName, false, true);
		if (!dummySettingInit) {
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, modelColor);
			for(int i2 = 0; i2 < entityPlayerFormLittleMaidDummy.textureModel.length; i2++) {
				if (entityPlayerFormLittleMaidDummy.textureModel[i2] != null
						&& entityPlayerFormLittleMaidDummy.textureModel[i2] instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) entityPlayerFormLittleMaidDummy.textureModel[i2]).changeColor(mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.pflm_RenderPlayerDummyMaster.modelData);
			}
		}
		entityPlayerFormLittleMaidDummy.textureArmorName = modelSelectMode % 2 == 0 ? "" : packgeName;
		//Modchu_Debug.mDebug("modelNamber="+modelNamber+" entityPlayerFormLittleMaidDummy.textureName="+entityPlayerFormLittleMaidDummy.textureName);
		//Modchu_Debug.mDebug("drawModel entityPlayerFormLittleMaidDummy.textureName="+entityPlayerFormLittleMaidDummy.textureName);
		drawMobModel(i, j, f, entityPlayerFormLittleMaidDummy);
	}

    public void drawMobModel(int x, int y, float f, Entity entity)
    {
    	if (entity == null) return;
    	//GL11.glEnable(GL11.GL_COLOR_MATERIAL);
    	GL11.glPushMatrix();
    	GL11.glTranslatef(x, y, 50F);
    	//entity.setWorld(popWorld);
    	GL11.glScalef(f, f, f);
    	if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender
    			&& mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
    	else GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
    	//RenderHelper.enableStandardItemLighting();
    	Render var10 = RenderManager.instance.getEntityRenderObject(entity);
    	if (var10 != null) var10.doRender(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
    	//RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
    	//RenderHelper.disableStandardItemLighting();
    	GL11.glPopMatrix();
    	OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
    	OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    protected void mouseClicked(int x, int y, int i)
	{
		super.mouseClicked(x, y, i);
		boolean doubleClick = false;
		Long l1 = (Long) Modchu_Reflect.invokeMethod("Minecraft", "func_71386_F", "getSystemTime", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
		if (l1 - lastClicked < 250L) doubleClick = true;
		lastClicked = l1;
		pointX = (x - 21) / 24;
		pointY = (y - 10) / 50;
		//Modchu_Debug.dDebug("x="+x);
		//Modchu_Debug.dDebug("y="+y,1);
		//Modchu_Debug.dDebug("pointX="+pointX,2);
		//Modchu_Debug.dDebug("pointY="+pointY,3);
		if(pointX >= 0 && pointX < selectBoxX && pointY >= 0 && pointY < selectBoxY)
		{
			int i1 = offsetSlot + pointX + (pointY * selectBoxX);
			if (i1 < mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTexturesSize()) {
				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][i1] != -1) selectSlot = i1;
			}
		}
		if (doubleClick
				&& selectSlot > -1
				&& mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot] != -1) {
			//Modchu_Debug.mDebug("selectSlot="+selectSlot+" mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot]="+mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot]);
			selected();
		}
	}

	private void selected() {
		Object gui = null;
		switch (modelSelectMode) {
		case 0:
		case 1:
			mod_PFLM_PlayerFormLittleMaid.pflm_main.textureName = mod_Modchu_ModchuLib.modchu_Main.getPackege(modelColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot]);
			mod_PFLM_PlayerFormLittleMaid.pflm_main.textureArmorName = null;
			mod_PFLM_PlayerFormLittleMaid.pflm_main.setArmorTextureValue();
			mod_PFLM_PlayerFormLittleMaid.pflm_main.maidColor = modelColor;
			gui = parentScreen;
			((PFLM_Gui) gui).modelChange();
			break;
		case 2:
		case 3:
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureName = mod_Modchu_ModchuLib.modchu_Main.getPackege(modelColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot]);
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersTextureArmorName = null;
			gui = parentScreen;
			((PFLM_GuiOthersPlayer) gui).setArmorTextureValue();
			mod_PFLM_PlayerFormLittleMaid.pflm_main.othersMaidColor = modelColor;
			((PFLM_GuiOthersPlayer) gui).modelChange();
			break;
		case 4:
		case 5:
			PFLM_GuiOthersPlayerIndividualCustomize.othersTextureName = mod_Modchu_ModchuLib.modchu_Main.getPackege(modelColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot]);
			PFLM_GuiOthersPlayerIndividualCustomize.othersTextureArmorName = null;
			gui = parentScreen;
			((PFLM_GuiOthersPlayer) gui).setArmorTextureValue();
			PFLM_GuiOthersPlayerIndividualCustomize.othersMaidColor = modelColor;
			((PFLM_GuiOthersPlayer) gui).modelChange();
			break;
		case 6:
		case 7:
			mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysTextureName[select] = mod_Modchu_ModchuLib.modchu_Main.getPackege(modelColor, mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[modelColor][selectSlot]);
			mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysTextureArmorName[select] = null;
			gui = parentScreen;
			((PFLM_GuiKeyControls) gui).setArmorTextureValue();
			mod_PFLM_PlayerFormLittleMaid.pflm_main.shortcutKeysMaidColor[select] = modelColor;
			((PFLM_GuiKeyControls) gui).modelChange();
			break;
		}
		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ (GuiScreen) gui });
		//mc.displayGuiScreen((GuiScreen) gui);
	}

	private void renderTexture(int x, int y, int width, int height)
	{
		Tessellator tes = Tessellator.instance;
		tes.startDrawingQuads();
		tes.addVertexWithUV((double)(x + 0), (double)(y + 0), 0.0D, textureRect[0], textureRect[1]);
		tes.addVertexWithUV((double)(x + 0), (double)(y + height), 0.0D, textureRect[2], textureRect[3]);
		tes.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, textureRect[4], textureRect[5]);
		tes.addVertexWithUV((double)(x + width), (double)(y + 0), 0.0D, textureRect[6], textureRect[7]);
		tes.draw();
	}

	private void resetTextureRect()
	{
		textureRect[0] = 0.0D;
		textureRect[1] = 0.0D;
		textureRect[2] = 0.0D;
		textureRect[3] = 1.0D;
		textureRect[4] = 1.0D;
		textureRect[5] = 1.0D;
		textureRect[6] = 1.0D;
		textureRect[7] = 0.0D;
	}

	private double[] mirrorTexture(double[] src)
	{
		double[] result = new double[8];
		result[0] = src[6];
		result[1] = src[1];
		result[2] = src[4];
		result[3] = src[3];
		result[4] = src[2];
		result[5] = src[5];
		result[6] = src[0];
		result[7] = src[7];

		return result;
	}

	private double[] rotateTexture(double[] src)
	{
		double[] result = new double[8];
		result[0] = src[2];
		result[1] = src[3];
		result[2] = src[4];
		result[3] = src[5];
		result[4] = src[6];
		result[5] = src[7];
		result[6] = src[0];
		result[7] = src[1];

		return result;
	}

    public boolean doesGuiPauseGame()
    {
        return !mod_PFLM_PlayerFormLittleMaid.pflm_main.isMulti;
    }

    public void onGuiClosed()
    {
    }

}
