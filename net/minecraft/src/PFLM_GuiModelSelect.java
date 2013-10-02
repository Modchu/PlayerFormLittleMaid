package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class PFLM_GuiModelSelect extends PFLM_GuiModelSelectBase {
	public int modelColor;
	private int modelListx = 30;
	private int modelListy = 60;
	private int pointX;
	private int pointY;
	private int selectSlot = -1;
	private int offsetSlot = 0;
	private double textureRect[];
	private BufferedImage bufferedimage;
	private long lastClicked;
	private int modelNamber = 0;
	private int selectBoxX = 8;
	private int selectBoxY = 3;
	private int selectCursorId = -1;
	private String playerName;
	private int select;
	private boolean changeColorFlag = false;
	private Object[][] textureModel;
	private String[] textureName;
	private String[] textureArmorName;
	private boolean isRendering[];
	public boolean armorMode = false;

	public PFLM_GuiModelSelect(PFLM_GuiBase par1GuiScreen, World world, boolean b, int j, String s) {
		this(par1GuiScreen, world, b, j);
		playerName = s;
	}

	public PFLM_GuiModelSelect(PFLM_GuiBase par1GuiScreen, World world, boolean b, int j) {
		super(par1GuiScreen, world);
		mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamberInit();
		//mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesArmorNamberInit();
		textureRect = new double[8];
		int i1 = getMaxSelectBoxViewCount();
		textureModel = new Object[3][i1];
		textureName = new String[i1];
		textureArmorName = new String[i1];
		isRendering = new boolean[i1];
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		setColor(j);
		armorMode = b;
		PFLM_RenderPlayerDummyMaster.showArmor = armorMode;
		PFLM_RenderPlayerDummyMaster.showMainModel = !armorMode;
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isModelSize) {
			GameSettings gameSettings = (GameSettings) Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
			if (gameSettings.thirdPersonView > 0) Modchu_Reflect.setFieldObject(GameSettings.class, "field_74320_O", "thirdPersonView", Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mod_Modchu_ModchuLib.modchu_Main.getMinecraft()), (Object) 0);
		}
		drawEntity.setPosition(thePlayer.posX , thePlayer.posY, thePlayer.posZ);
		try {
			bufferedimage = ImageIO.read((URL) Modchu_Reflect.invokeMethod(Class.class, "getResource", new Class[]{ String.class }, Modchu_Reflect.loadClass("Minecraft"),
					new Object[]{ mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159 ? "/assets/minecraft/textures/particle/particles.png" : "/particles.png" }));
			//bufferedimage = ImageIO.read((Minecraft.class).getResource("/particles.png"));
			int sx = bufferedimage.getWidth() / 128;
			int sy = bufferedimage.getHeight() / 128;
			bufferedimage = bufferedimage.getSubimage(0, 32 * sy, 8 * sx, 8 * sy);
			drawSelectCursorInit();
		} catch (Exception e) {
			e.printStackTrace();
			Modchu_Debug.lDebug("PFLM_GuiModelSelect bufferedimage Exception!!");
			selectCursorId = -1;
		}
		resetTextureRect();
		drawEntitySetFlag = true;
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 0, 80, 185, 15, 15, "<" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 1, 100, 185, 15, 15, ">" }));
		if (!armorMode) {
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 2, 145, 180, 15, 15, "<" }));
			buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 3, 160, 180, 15, 15, ">" }));
		}
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ armorMode ? 102 : 103, 70, 205, 75, 20, armorMode ? "Model" : "Armor" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 100, 155, 205, 75, 20, "select" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 101, 240, 205, 75, 20, "return" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 999, 0, 0, 0, 0, "" }));
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
    		int i1 = getMaxSelectBoxViewCount();
    		int i = offsetSlot - i1;
    		if (i > -1) offsetSlot -= i1;
    		selectSlot = -1;
    		drawEntitySetFlag = true;
    		return;
    	}
    	//pageNext
    	if(guibutton.id == 1)
    	{
    		int i1 = getMaxSelectBoxViewCount();
    		int i = i1 + offsetSlot;
    		if (i < mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTexturesSize()) {
    			if (getTexturesNamber(i, getColor()) != -1) offsetSlot += i1;
    		}
    		selectSlot = -1;
    		drawEntitySetFlag = true;
    		return;
    	}
    	if (!armorMode) {
    		//colorPrev
    		if(guibutton.id == 2)
    		{
    			setColor(getColor() - 1);
    			maxPageCheack();
    			changeColorFlag = true;
    			drawEntitySetFlag = true;
    			return;
    		}
    		//colorNext
    		if(guibutton.id == 3)
    		{
    			setColor(getColor() + 1);
    			maxPageCheack();
    			changeColorFlag = true;
    			drawEntitySetFlag = true;
    			return;
    		}
    	}
    	//select
    	if(guibutton.id == 100)
    	{
    		if (selectSlot > -1
    				&& getTexturesNamber(selectSlot, getColor()) != -1) {
    			selected();
    		}
    	}
    	//return
    	if(guibutton.id == 101)
    	{
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ parentScreen });
    		//mc.displayGuiScreen(parentScreen);
    		((PFLM_GuiModelSelectBase) parentScreen).setTextureValue();
    		PFLM_RenderPlayerDummyMaster.showArmor = true;
    		PFLM_RenderPlayerDummyMaster.showMainModel = true;
			mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.allModelInit(drawEntity, false);
    	}
    	//Armor | Model
    	if(guibutton.id == 102
    		| guibutton.id == 103) {
    		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiModelSelect(parentScreen, popWorld, guibutton.id == 103, getColor()) });
    		//mc.displayGuiScreen(new PFLM_GuiModelSelect(this, popWorld, true, getColor()));
    		return;
    	}
    }

    private void maxPageCheack() {
    	int maxCount = getMaxSelectBoxViewCount();
    	int maxTexturesNamber = getMaxTexturesNamber(getColor());
    	if(offsetSlot / (getMaxSelectBoxViewCount()) > (maxTexturesNamber - 1) / maxCount) {
    		offsetSlot = (maxTexturesNamber - 1) / maxCount * maxCount;
    	}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		//GL11.glPushMatrix();
		modelNamber = offsetSlot;
		float f1 = 20F;
		for (int i2 = 0 ; i2 < selectBoxY ; i2++) {
			for (int i1 = 0 ; i1 < selectBoxX ; i1++) {
				drawModel(i, j, i1 * 25 + modelListx, i2 * 50 + modelListy, f1, modelNamber);
				++modelNamber;
			}
		}
		if (selectSlot > -1) {
			int i2 = maxSelectBoxCheck(selectSlot);
			String textureName = getTextureName(i2);
			if (textureName != null) {
				fontRenderer.drawString(armorMode ? "TextureArmorName" : "TextureName", 240, 170, 0xffffff);
				fontRenderer.drawString(textureName, 240, 180, 0xffffff);
				setTextureModel(i2);
				drawMobModel2(i, j, 300, 150, 90, 30, 50F, 0.0F, true);
			}
		}
		drawSelectCursor();
		fontRenderer.drawString("Page : " + offsetSlot / getMaxSelectBoxViewCount() + " / "+ (getMaxTexturesNamber(getColor()) - 1) / getMaxSelectBoxViewCount(), 55, 170, 0xffffff);
		if (!armorMode) fontRenderer.drawString("MaidColor : "+getColor(), 130, 170, 0xffffff);
		fontRenderer.drawString(armorMode ? "ArmorSelect" : "ModelSelect", 180, 5, 0xffffff);
		if (changeColorFlag) changeColorFlag = false;
		if (drawEntitySetFlag) drawEntitySetFlag = false;
		//GL11.glPopMatrix();
	}

	private int getMaxTexturesNamber(int i) {
		return armorMode ? mod_PFLM_PlayerFormLittleMaid.pflm_main.maxTexturesArmorNamber : mod_PFLM_PlayerFormLittleMaid.pflm_main.maxTexturesNamber[i];
	}

	private void drawSelectCursorInit() {
		if (bufferedimage != null) {
			if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
				selectCursorId = (Integer) Modchu_Reflect.invokeMethod("TextureUtil", "func_110987_a", "uploadTextureImage", new Class[]{ int.class, BufferedImage.class }, new Object[]{ 1, bufferedimage });
				//selectCursorId = TextureUtil.func_110987_a(1, bufferedimage);
			} else {
				Object renderEngine = Modchu_Reflect.getFieldObject("Minecraft", "field_71446_o", "renderEngine", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
				Modchu_Reflect.invokeMethod("RenderEngine", "func_78351_a", "setupTexture", new Class[]{ BufferedImage.class, int.class }, renderEngine, new Object[]{ bufferedimage, 1 });
				//mc.renderEngine.setupTexture(bufferedimage, 1);
			}
		}
	}

	private void drawSelectCursor() {
		if (selectCursorId > 0
				&& selectSlot > -1
				&& getTexturesNamber(selectSlot, getColor()) != -1) {
			if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
				Modchu_Reflect.invokeMethod("TextureUtil", "func_94277_a", "bindTexture", new Class[]{ int.class }, new Object[]{ selectCursorId });
			} else {
				Object renderEngine = Modchu_Reflect.getFieldObject("Minecraft", "field_71446_o", "renderEngine", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
				Modchu_Reflect.invokeMethod("RenderEngine", "func_78351_a", "setupTexture", new Class[]{ BufferedImage.class, int.class }, renderEngine, new Object[]{ bufferedimage, 1 });
				//mc.renderEngine.setupTexture(bufferedimage, 0);
			}
			renderTexture((selectSlot - offsetSlot) % selectBoxX * 25 + modelListx, (selectSlot - offsetSlot) / selectBoxX * 55 + modelListy - 50, 8, 8);
		}
	}

	private void drawModel(int i, int j, int x, int y , float f, int modelNamber) {
		//if (modelNamber >= mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[getColor()].length) return;
		int i2 = maxSelectBoxCheck(modelNamber);
		if (drawEntitySetFlag) {
			int i1 = getTexturesNamber(modelNamber, getColor());
			if (armorMode) setColor(0);
			setScale(mod_PFLM_PlayerFormLittleMaid.pflm_main.getModelScale(drawEntity));
			if (i2 == 0) {
				for(int i3 = 0; i3 < getMaxSelectBoxViewCount(); i3++) {
					textureModel[0][i3] = null;
					textureModel[1][i3] = null;
					textureModel[2][i3] = null;
				}
			}
			if (i1 < 0
					| i1 >= mod_Modchu_ModchuLib.modchu_Main.getTextureManagerTexturesSize()) ;else {
				Object ltb = mod_Modchu_ModchuLib.modchu_Main.getTextureBox(i1);
				setTextureName(i2, null);
				if (ltb != null) setTextureName(i2, mod_Modchu_ModchuLib.modchu_Main.getTextureBoxFileName(ltb));
				if (getTextureName(i2) != null
						&& !getTextureName(i2).isEmpty()
						| armorMode) ;else {
					setTextureName(i2, "default");
					ltb = mod_Modchu_ModchuLib.modchu_Main.getTextureBox(getTextureName(i2));
					if (ltb != null) setTextureName(i2, mod_Modchu_ModchuLib.modchu_Main.getTextureBoxFileName(ltb));
				}
				if (getTextureName(i2) != null
						&& ltb != null) {
					setTextureValue(getTextureName(i2), getTextureName(i2), getColor());
					setTextureArmorName(i2, (String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName));
					Modchu_Debug.mDebug("getTextureName(i2)="+getTextureName(i2));
					Modchu_Debug.mDebug("getTextureArmorName(i2)="+getTextureArmorName(i2));
					if (changeColorFlag) mod_PFLM_PlayerFormLittleMaid.pflm_main.changeColor((PFLM_EntityPlayerDummy)drawEntity);
					mod_PFLM_PlayerFormLittleMaid.pflm_RenderPlayerDummy.allModelInit(drawEntity, false);
					textureModel[0][i2] = !armorMode ? PFLM_RenderPlayerDummyMaster.modelData.modelMain.model : PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelInner;
					textureModel[1][i2] = PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelInner;
					textureModel[2][i2] = PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelOuter;
					Modchu_Debug.mDebug("textureModel[0][i2]="+textureModel[0][i2]);
					//Modchu_Debug.mDebug("textureModel[1][i2]="+textureModel[1][i2]);
					//Modchu_Debug.mDebug("textureModel[2][i2]="+textureModel[2][i2]);
				}
			}
			isRendering[i2] = (!armorMode
					&& textureModel[0][i2] != null)
					| (armorMode
							&& textureModel[1][i2] != null
							| textureModel[2][i2] != null);
			Modchu_Debug.mDebug("textureModel[1]["+i2+"]="+textureModel[1][i2]+" i1="+i1);
		} else {
			setTextureModel(i2);
		}
		//Modchu_Debug.mDebug("modelNamber="+modelNamber+" drawEntity.textureName="+drawEntity.textureName);
		//Modchu_Debug.mDebug("drawModel drawEntity.textureName="+drawEntity.textureName);
		if (isRendering[i2]) drawMobModel2(i, j, x, y, 0, -50, f, 0.0F, false);
	}

	private void setTextureModel(int i) {
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, getTextureName(i));
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, getColor());
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, getTextureArmorName(i));
		PFLM_RenderPlayerDummyMaster.modelData.modelMain.model = (MMM_ModelMultiBase) textureModel[0][i];
		PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelInner = (MMM_ModelMultiBase) textureModel[1][i];
		PFLM_RenderPlayerDummyMaster.modelData.modelFATT.modelOuter = (MMM_ModelMultiBase) textureModel[2][i];
	}

	public void setTextureValue(String texture, String armorTexture, int color) {
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, texture);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, color);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, armorTexture);
		if (!armorMode) {
			setTextureArmorPackege();
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, (String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName));
		}
	}

	public void setTextureArmorPackege() {
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
		String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName), 2);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
	}

	protected void mouseClicked(int x, int y, int i) {
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
				if (getTexturesNamber(i1, getColor()) != -1) selectSlot = i1;
			}
		}
		if (doubleClick
				&& selectSlot > -1
				&& getTexturesNamber(selectSlot, getColor()) != -1) {
			//Modchu_Debug.mDebug("selectSlot="+selectSlot+" mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[getColor()][selectSlot]="+mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[getColor()][selectSlot]);
			selected();
		}
	}

	private int getTexturesNamber(int i, int i1) {
		if (armorMode) {
			if (i >= mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesArmorNamber.length) return -1;
		} else {
			if (i >= mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[i1].length) return -1;
		}
		return armorMode ? mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesArmorNamber[i] : mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[i1][i];
	}

	private void selected() {
		if (parentScreen instanceof PFLM_GuiModelSelectBase) ;else return;
		PFLM_RenderPlayerDummyMaster.showArmor = true;
		PFLM_RenderPlayerDummyMaster.showMainModel = true;
		PFLM_GuiModelSelectBase gui = (PFLM_GuiModelSelectBase) parentScreen;
		int i2 = maxSelectBoxCheck(selectSlot);
		gui.selected(getTextureName(i2), getTextureArmorName(i2), getColor(), armorMode);
		gui.setArmorTextureValue();
		gui.modelChange();
		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ null });
		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ (GuiScreen) gui });
		//mc.displayGuiScreen((GuiScreen) gui);
	}

	private void renderTexture(int x, int y, int width, int height) {
		Tessellator tes = Tessellator.instance;
		tes.startDrawingQuads();
		tes.addVertexWithUV((double)(x + 0), (double)(y + 0), 0.0D, textureRect[0], textureRect[1]);
		tes.addVertexWithUV((double)(x + 0), (double)(y + height), 0.0D, textureRect[2], textureRect[3]);
		tes.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, textureRect[4], textureRect[5]);
		tes.addVertexWithUV((double)(x + width), (double)(y + 0), 0.0D, textureRect[6], textureRect[7]);
		tes.draw();
	}

	private void resetTextureRect() {
		textureRect[0] = 0.0D;
		textureRect[1] = 0.0D;
		textureRect[2] = 0.0D;
		textureRect[3] = 1.0D;
		textureRect[4] = 1.0D;
		textureRect[5] = 1.0D;
		textureRect[6] = 1.0D;
		textureRect[7] = 0.0D;
	}

	private double[] mirrorTexture(double[] src) {
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

	private double[] rotateTexture(double[] src) {
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

	public int getMaxSelectBoxViewCount() {
		return selectBoxX * selectBoxY;
	}

	private int maxSelectBoxCheck(int i) {
		int i1 = getMaxSelectBoxViewCount();
		while(i >= i1) {
			i = i - i1;
		}
		return i;
	}

	@Override
	public String getTextureName() {
		return getTextureName(selectSlot);
	}

	public String getTextureName(int i) {
		return textureName[i];
	}

/*
	public String getTextureName(int i) {
		int i1 = getColor();
		return mod_Modchu_ModchuLib.modchu_Main.getPackege(i1, mod_PFLM_PlayerFormLittleMaid.pflm_main.texturesNamber[i1][i]);
	}
*/
	@Override
	public void setTextureName(String s) {
		setTextureName(selectSlot, s);
	}

	public void setTextureName(int i, String s) {
		textureName[i] = s;
	}

	@Override
	public String getTextureArmorName() {
		return null;
		//return getTextureArmorName(modelNamber);
	}

	public String getTextureArmorName(int i) {
		return textureArmorName[i];
	}

	@Override
	public void setTextureArmorName(String s) {
		//setTextureArmorName(modelNamber, s);
	}

	public void setTextureArmorName(int i, String s) {
		textureArmorName[i] = s;
	}

	@Override
	public int getColor() {
		return modelColor;
	}

	@Override
	public void setColor(int i) {
		modelColor = i & 0xf;
	}

	@Override
	public float getScale() {
		return PFLM_RenderPlayerDummyMaster.modelData.getCapsValueFloat(PFLM_RenderPlayerDummyMaster.modelData.caps_modelScale);
	}

	@Override
	public void setScale(float f) {
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_modelScale, f);
	}

	@Override
	public void memoryRelease() {
		textureRect = null;
		bufferedimage = null;
		playerName = null;
		textureModel = null;
		textureName = null;
		textureArmorName = null;
		isRendering = null;
	}

	@Override
	public void modelChange() {
	}
}
