package net.minecraft.src;

import java.awt.image.BufferedImage;

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

	public PFLM_GuiModelSelect(GuiScreen par1GuiScreen, World world, int i, String s) {
		this(par1GuiScreen, world, i);
		playerName = s;
	}

	public PFLM_GuiModelSelect(GuiScreen par1GuiScreen, World world, int i, int j) {
		this(par1GuiScreen, world, i);
		select = j;
	}

	public PFLM_GuiModelSelect(GuiScreen par1GuiScreen, World world, int i) {
		popWorld = world;
		parentScreen = par1GuiScreen;
		modelSelectMode = i;
		modelColor = modelSelectMode == 0 ? mod_PFLM_PlayerFormLittleMaid.maidColor :
			modelSelectMode == 2 ? mod_PFLM_PlayerFormLittleMaid.othersMaidColor :
				modelSelectMode == 4 ? PFLM_GuiOthersPlayerIndividualCustomize.othersMaidColor :
					mod_PFLM_PlayerFormLittleMaid.maidColor;
		if (mc == null) mc = Minecraft.getMinecraft();
		if (mc.gameSettings.thirdPersonView > 0) mc.gameSettings.thirdPersonView = 0;
		entityPlayerFormLittleMaidDummy = new PFLM_EntityPlayerDummy(popWorld);
		entityPlayerFormLittleMaidDummy.textureArmor0 = new String[] {"","",""};
		entityPlayerFormLittleMaidDummy.textureArmor1 = new String[] {"","",""};
		entityPlayerFormLittleMaidDummy.modelScale = 0.0F;
		entityPlayerFormLittleMaidDummy.showArmor = modelSelectMode % 2 == 0 ? false : true;
		entityPlayerFormLittleMaidDummy.others = false;
		entityPlayerFormLittleMaidDummy.setPosition(mc.thePlayer.posX , mc.thePlayer.posY, mc.thePlayer.posZ);
		try {
			bufferedimage = ImageIO.read((Minecraft.class).getResource("/particles.png"));
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
		buttonList.add(new PFLM_GuiSmallButton(0, 80, 185, 15, 15, "<"));
		buttonList.add(new PFLM_GuiSmallButton(1, 100, 185, 15, 15, ">"));
		buttonList.add(new PFLM_GuiSmallButton(2, 145, 180, 15, 15, "<"));
		buttonList.add(new PFLM_GuiSmallButton(3, 160, 180, 15, 15, ">"));
		buttonList.add(new PFLM_GuiSmallButton(100, 155, 205, 75, 20, "select"));
		buttonList.add(new PFLM_GuiSmallButton(101, 240, 205, 75, 20, "return"));
		buttonList.add(new PFLM_GuiSmallButton(999, 0, 0, 0, 0, ""));
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
    		if (i < mod_Modchu_ModchuLib.getTextureManagerTexturesSize()) {
    			if (mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][i] != -1) offsetSlot += selectBoxX * selectBoxY;
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
    				&& mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot] != -1) {
    			selected();
    		}
    	}
    	//return
    	if(guibutton.id == 101)
    	{
    		mc.displayGuiScreen(parentScreen);
    	}
    }

    private void maxPageCheack() {
    	if(offsetSlot / (selectBoxX * selectBoxY) > (mod_PFLM_PlayerFormLittleMaid.maxTexturesNamber[modelColor] - 1) / (selectBoxX * selectBoxY)) {
    		offsetSlot = (mod_PFLM_PlayerFormLittleMaid.maxTexturesNamber[modelColor] - 1) / (selectBoxX * selectBoxY) * (selectBoxX * selectBoxY);
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
				&& mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot] != -1) {
			TextureUtil.func_110987_a(0, bufferedimage);
			renderTexture((selectSlot - offsetSlot) % selectBoxX * 25 + modelListx, (selectSlot - offsetSlot) / selectBoxX * 55 + modelListy - 50, 8, 8);
		}
		StringBuilder s5 = (new StringBuilder()).append("Page : ").append(offsetSlot / (selectBoxX * selectBoxY));
		s5.append(" / ").append((mod_PFLM_PlayerFormLittleMaid.maxTexturesNamber[entityPlayerFormLittleMaidDummy.maidColor] - 1) / (selectBoxX * selectBoxY));
		fontRenderer.drawString(s5.toString(), 55, 170, 0xffffff);
		StringBuilder s2 = (new StringBuilder()).append("MaidColor : ");
		s2 = s2.append(entityPlayerFormLittleMaidDummy.maidColor);
		fontRenderer.drawString(s2.toString(), 130, 170, 0xffffff);
		if (selectSlot > -1) {
			entityPlayerFormLittleMaidDummy.maidColor = modelColor;
			entityPlayerFormLittleMaidDummy.textureName = mod_Modchu_ModchuLib.getPackege(entityPlayerFormLittleMaidDummy.maidColor, mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot]);
			if (entityPlayerFormLittleMaidDummy.textureName == null) {
				return;
			}
			// 152deleteentityPlayerFormLittleMaidDummy.texture = mod_Modchu_ModchuLib.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, entityPlayerFormLittleMaidDummy.maidColor);
			PFLM_RenderPlayerDummy.modelData.setCapsValue(PFLM_RenderPlayerDummy.modelData.caps_ResourceLocation, 0, mod_Modchu_ModchuLib.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, entityPlayerFormLittleMaidDummy.maidColor));
			Object ltb = mod_Modchu_ModchuLib.getTextureBox(entityPlayerFormLittleMaidDummy.textureName);
			entityPlayerFormLittleMaidDummy.textureModel = (Object[]) mod_Modchu_ModchuLib.modelNewInstance(null, entityPlayerFormLittleMaidDummy.textureName, false, true);
			entityPlayerFormLittleMaidDummy.textureArmorName = modelSelectMode % 2 == 0 ? "" : mod_Modchu_ModchuLib.getTextureBoxFileName(ltb);
			mod_PFLM_PlayerFormLittleMaid.changeColor(entityPlayerFormLittleMaidDummy);
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
			GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
			RenderHelper.enableStandardItemLighting();
			GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
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
		entityPlayerFormLittleMaidDummy.maidColor = modelColor;
		if (modelNamber >= mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor].length) return;
		int i1 = mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][modelNamber];
		if (i1 < 0
				| i1 >= mod_Modchu_ModchuLib.getTextureManagerTexturesSize()) return;
		Object ltb = mod_Modchu_ModchuLib.getTextureBox(i1);
		String packgeName = null;
		if (ltb != null) packgeName = mod_Modchu_ModchuLib.getTextureBoxFileName(ltb);
		if (packgeName != null) ;else {
			packgeName = entityPlayerFormLittleMaidDummy.textureName != null
					&& entityPlayerFormLittleMaidDummy.textureName.indexOf("Biped") > -1 ? "Biped" : "default";
			ltb = mod_Modchu_ModchuLib.getTextureBox(packgeName);
			if (ltb != null) packgeName = mod_Modchu_ModchuLib.getTextureBoxFileName(ltb);
		}
		entityPlayerFormLittleMaidDummy.textureName = packgeName;
		if (entityPlayerFormLittleMaidDummy.textureName != null
				&& ltb != null) ;else return;
		// 152deleteentityPlayerFormLittleMaidDummy.texture = mod_Modchu_ModchuLib.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, entityPlayerFormLittleMaidDummy.maidColor);
		PFLM_RenderPlayerDummy.modelData.setCapsValue(PFLM_RenderPlayerDummy.modelData.caps_ResourceLocation, 0, mod_Modchu_ModchuLib.textureManagerGetTexture(entityPlayerFormLittleMaidDummy.textureName, entityPlayerFormLittleMaidDummy.maidColor));
		entityPlayerFormLittleMaidDummy.textureModel = (Object[]) mod_Modchu_ModchuLib.modelNewInstance(null, entityPlayerFormLittleMaidDummy.textureName, false, true);
		if (!dummySettingInit) {
			for(int i2 = 0; i2 < entityPlayerFormLittleMaidDummy.textureModel.length; i2++) {
				if (entityPlayerFormLittleMaidDummy.textureModel[i2] != null
						&& entityPlayerFormLittleMaidDummy.textureModel[i2] instanceof MultiModelBaseBiped) ((MultiModelBaseBiped) entityPlayerFormLittleMaidDummy.textureModel[i2]).changeColor(PFLM_RenderPlayerDummy.modelData);
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
    	GL11.glRotatef(180F, 0.0F, 0.0F, 0.0F);
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
		if (Minecraft.getSystemTime() - lastClicked < 250L) doubleClick = true;
		lastClicked = Minecraft.getSystemTime();
		pointX = (x - 21) / 24;
		pointY = (y - 10) / 50;
		//Modchu_Debug.dDebug("x="+x);
		//Modchu_Debug.dDebug("y="+y,1);
		//Modchu_Debug.dDebug("pointX="+pointX,2);
		//Modchu_Debug.dDebug("pointY="+pointY,3);
		if(pointX >= 0 && pointX < selectBoxX && pointY >= 0 && pointY < selectBoxY)
		{
			int i1 = offsetSlot + pointX + (pointY * selectBoxX);
			if (i1 < mod_Modchu_ModchuLib.getTextureManagerTexturesSize()) {
				if (mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][i1] != -1) selectSlot = i1;
			}
		}
		if (doubleClick
				&& selectSlot > -1
				&& mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot] != -1) {
			//Modchu_Debug.mDebug("selectSlot="+selectSlot+" mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot]="+mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot]);
			selected();
		}
	}

	private void selected() {
		Object gui = null;
		switch (modelSelectMode) {
		case 0:
		case 1:
			mod_PFLM_PlayerFormLittleMaid.textureName = mod_Modchu_ModchuLib.getPackege(entityPlayerFormLittleMaidDummy.maidColor, mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot]);
			mod_PFLM_PlayerFormLittleMaid.textureArmorName = null;
			mod_PFLM_PlayerFormLittleMaid.setArmorTextureValue();
			mod_PFLM_PlayerFormLittleMaid.maidColor = entityPlayerFormLittleMaidDummy.maidColor;
			gui = parentScreen;
			((PFLM_Gui) gui).modelChange();
			break;
		case 2:
		case 3:
			mod_PFLM_PlayerFormLittleMaid.othersTextureName = mod_Modchu_ModchuLib.getPackege(entityPlayerFormLittleMaidDummy.maidColor, mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot]);
			mod_PFLM_PlayerFormLittleMaid.othersTextureArmorName = null;
			gui = parentScreen;
			((PFLM_GuiOthersPlayer) gui).setArmorTextureValue();
			mod_PFLM_PlayerFormLittleMaid.othersMaidColor = entityPlayerFormLittleMaidDummy.maidColor;
			((PFLM_GuiOthersPlayer) gui).modelChange();
			break;
		case 4:
		case 5:
			PFLM_GuiOthersPlayerIndividualCustomize.othersTextureName = mod_Modchu_ModchuLib.getPackege(entityPlayerFormLittleMaidDummy.maidColor, mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot]);
			PFLM_GuiOthersPlayerIndividualCustomize.othersTextureArmorName = null;
			gui = parentScreen;
			((PFLM_GuiOthersPlayer) gui).setArmorTextureValue();
			PFLM_GuiOthersPlayerIndividualCustomize.othersMaidColor = entityPlayerFormLittleMaidDummy.maidColor;
			((PFLM_GuiOthersPlayer) gui).modelChange();
			break;
		case 6:
		case 7:
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureName[select] = mod_Modchu_ModchuLib.getPackege(entityPlayerFormLittleMaidDummy.maidColor, mod_PFLM_PlayerFormLittleMaid.texturesNamber[entityPlayerFormLittleMaidDummy.maidColor][selectSlot]);
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysTextureArmorName[select] = null;
			gui = parentScreen;
			((PFLM_GuiKeyControls) gui).setArmorTextureValue();
			mod_PFLM_PlayerFormLittleMaid.shortcutKeysMaidColor[select] = entityPlayerFormLittleMaidDummy.maidColor;
			((PFLM_GuiKeyControls) gui).modelChange();
			break;
		}
		mc.displayGuiScreen((GuiScreen) gui);
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
        return !mod_PFLM_PlayerFormLittleMaid.isMulti;
    }

    public void onGuiClosed()
    {
    }

}
