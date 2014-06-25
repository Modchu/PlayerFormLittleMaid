package modchu.pflm;

import java.util.ArrayList;
import java.util.List;

import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_IGuiModelViewMaster;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.recompileonly.Modchu_GuiBase;
import modchu.lib.characteristic.recompileonly.Modchu_GuiModelView;
import modchu.lib.replace.Modchu_TextureManagerBase;
import modchu.model.ModchuModel_IModelCapsConstant;
import modchu.model.ModchuModel_Main;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class PFLM_GuiModelViewMaster extends PFLM_GuiBaseMaster implements Modchu_IGuiModelViewMaster {

	public Object drawEntity;
	public ArrayList<String> drawStringList;
	public boolean drawEntitySetFlag;
	public boolean textureResetFlag;
	public boolean initDrawStringListFlag;
	public boolean buttonOnline;
	public boolean buttonOffline;
	public boolean buttonRandom;
	public boolean buttonScale;
	public boolean buttonParts;
	public boolean buttonPlayer;
	public boolean buttonReturn;
	public boolean buttonShowArmor;
	public boolean displayButton;
	protected boolean showArmor;
	public int drawStringPosX;
	public int drawStringPosY;
	public int drawStringColor;
	private int comeraRotationX;
	private int comeraRotationY;
	private int comeraRotationZ;
	private int prevMouseX;
	private int prevMouseY;
	private float cameraZoom;
	private long tempLastMouseEventTime;
	private int tempLastMouseClick;
	private float comeraPosX;
	private float comeraPosY;
	private float comeraPosZ;
	private boolean clickMove;

	public PFLM_GuiModelViewMaster(Modchu_GuiBase guiBase, Object par1GuiScreen, Object world, Object... o) {
		super(guiBase, par1GuiScreen, world, (Object[])o);
	}

	@Override
	public void init(Modchu_GuiBase guiBase, Object par1GuiScreen, Object world, Object... o) {
		super.init(guiBase, par1GuiScreen, world, (Object[])o);
		drawEntitySetFlag = true;
		drawStringList = new ArrayList();
		buttonOnline = false;
		buttonOffline = false;
		buttonRandom = false;
		buttonScale = false;
		buttonParts = false;
		buttonPlayer = false;
		buttonReturn = false;
		buttonShowArmor = false;
		displayButton = true;
		drawStringPosX = 0;
		drawStringPosY = 0;
		drawStringColor = 0xffffff;
		prevMouseX = -999;
		prevMouseY = -999;
		cameraZoom = 1.0F;
		initDrawEntity();
	}

	@Override
	public void reInit() {
		super.reInit();
		initDrawEntity();
		setTextureValue();
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_freeVariable, "showArmor", showArmor);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_freeVariable, "showMainModel", true);
		drawEntitySetFlag = true;
	}

	@Override
	public void initGui() {
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		buttonList.clear();
		if (!displayButton) return;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		buttonList.add(newInstanceButton(200, x, y + 100, 75, 20, "Save"));
		buttonList.add(newInstanceButton(13, x, y + 85, 75, 15, "ChangeMode"));
		if (!buttonPlayer) buttonList.add(newInstanceButton(58, x + 75, y + 55, 75, 15, "Handedness"));
		if (buttonReturn) buttonList.add(newInstanceButton(201, x + 75, y + 100, 75, 20, "Return"));
		if (buttonOffline) {
			buttonList.add(newInstanceButton(56, x - 10, y + 10, 85, 15, "ModelListSelect"));
			buttonList.add(newInstanceButton(50, x + 40, y + 25, 15, 15, "<"));
			buttonList.add(newInstanceButton(51, x + 55, y + 25, 15, 15, ">"));
			buttonList.add(newInstanceButton(52, x + 40, y + 40, 15, 15, "-"));
			buttonList.add(newInstanceButton(53, x + 55, y + 40, 15, 15, "+"));
			buttonList.add(newInstanceButton(54, x + 40, y + 55, 15, 15, "<"));
			buttonList.add(newInstanceButton(55, x + 55, y + 55, 15, 15, ">"));
		}
		if (PFLM_ConfigData.useScaleChange
				&& !buttonPlayer
				&& buttonScale) {
			buttonList.add(newInstanceButton(3, width / 2 - 170, height / 2 - 40, 50, 20, "Default"));
			buttonList.add(newInstanceButton(4, width / 2 - 120, height / 2 - 40, 30, 20, "UP"));
			buttonList.add(newInstanceButton(5, width / 2 - 200, height / 2 - 40, 30, 20, "Down"));
			buttonList.add(newInstanceButton(6, x + 75, y + 25, 75, 15, "Close"));
		} else {
			if (!buttonParts && !buttonPlayer) {
				if (PFLM_ConfigData.useScaleChange) buttonList.add(newInstanceButton(7, x + 75, y + 25, 75, 15, "ScaleChange"));
			}
		}
		if (buttonShowArmor) buttonList.add(newInstanceButton(20, x, y + 70, 75, 15, "showArmor"));
		if (((Modchu_GuiModelView) base).getScale() == 0.0F) {
			((Modchu_GuiModelView) base).setScale(PFLM_Main.getModelScale());
		}
		guiMode = true;
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		if (!modelData.getCapsValueBoolean(ModchuModel_IModelCapsConstant.caps_freeVariable, "initDrawEntityFlag")) initDrawEntity();
	}

	@Override
	public boolean drawScreen(int i, int j, float f) {
		base.superDrawDefaultBackground();
		base.superDrawScreen(i, j, f);
		drawGuiContainerBackgroundLayer(f, i, j);
		return false;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		if (drawStringList != null
				&& !drawStringList.isEmpty()
				&& displayButton) ;else return;
		String s;
		for (int i1 = 0; i1 < drawStringList.size(); i1++) {
			s = drawStringList.get(i1);
			drawString(s, drawStringPosX, drawStringPosY + (10 * i1), drawStringColor);
		}
	}

	@Override
	public void drawString(Object renderer, String s, int i, int j, int k) {
		if (!displayButton) return;
		super.drawString(renderer, s, i, j, k);
	}

	public void initDrawEntity() {
		if (drawEntity != null) ;else drawEntity = Modchu_Reflect.newInstance("modchu.lib.characteristic.Modchu_EntityPlayerDummy", new Class[]{ Modchu_Reflect.loadClass("World") }, new Object[]{ popWorld });
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setRender(PFLM_Main.renderPlayerDummyInstance);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_freeVariable, "showMainModel", true);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_freeVariable, "initDrawEntityFlag", true);
	}

	@Override
	public void setTextureArmorPackege() {
		setTextureArmorPackege(2);
	}

	@Override
	public void setTextureArmorPackege(int i) {
		//modelData.setCapsValue(modelData.caps_textureArmorName, modelData.getCapsValue(modelData.caps_textureName));
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		String s = PFLM_Main.getArmorName((String) modelData.getCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName), i);
		modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName, s);
		Object ltb = ModchuModel_Main.checkTextureArmorPackege(s);
		if (ltb != null) ;
		else {
			modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName, "default");
		}
		if (modelData.getCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName) != null) ;
		else modelData.setCapsValue(ModchuModel_IModelCapsConstant.caps_textureArmorName, modelData.getCapsValue(ModchuModel_IModelCapsConstant.caps_textureName));
	}

	@Override
	public void setTextureValue() {
		PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		initDrawStringListFlag = true;
	}

	public void setTextureValueAfter() {
	}

	@Override
	public void setArmorTextureValue() {
	}

	protected void resetFlagCheck() {
		if (drawEntitySetFlag) {
			setTextureValue();
			reLoadModel(drawEntity, true);
			setTextureValueAfter();
			drawEntitySetFlag = false;
		}
		if (textureResetFlag) {
			PFLM_ModelDataMaster.instance.allModelTextureReset(drawEntity);
			textureResetFlag = false;
		}
	}

	@Override
	public void drawMobModel(int i, int j, int x, int y, int x2, int y2, float f, float f1, boolean move) {
		drawMobModel(base, i, j, x, y, x2, y2, f, f1, 30F, -30F, -30F, 0F, move, drawEntity);
	}

	public void drawMobModel(Object guiScreen, int i, int j, int x, int y, int x2, int y2, float f, float f1, boolean move) {
		drawMobModel(guiScreen, i, j, x, y, x2, y2, f, f1, 30F, -30F, -30F, 0F, move, drawEntity);
	}

	@Override
	public void drawMobModel(int i, int j, int x, int y, int x2, int y2, float f, float f1, boolean move, Object entity) {
		drawMobModel(base, i, j, x, y, x2, y2, f, f1, 30F, -30F, -30F, 0F, move, entity);
	}

	public void drawMobModel(Object guiScreen, int i, int j, int x, int y, int x2, int y2, float f, float f1, boolean move, Object entity) {
		drawMobModel(guiScreen, i, j, x, y, x2, y2, f, f1, 30F, -30F, -30F, 0F, move, entity);
	}

	@Override
	public void drawMobModel(int i, int j, int x, int y, int x2, int y2, float f, float f1, float f2, float f3, float f4, float f5, boolean move) {
		drawMobModel(base, i, j, x, y, x2, y2, f, f1, f2, f3, f4, f5, move, drawEntity);
	}

	public void drawMobModel(Object guiScreen, int i, int j, int x, int y, int x2, int y2, float f, float f1, float f2, float f3, float f4, float f5, boolean move) {
		drawMobModel(guiScreen, i, j, x, y, x2, y2, f, f1, f2, f3, f4, f5, move, drawEntity);
	}

	@Override
	public void drawMobModel(int i, int j, int x, int y, int x2, int y2, float f, float f1, float f2, float f3, float f4, float f5, boolean move, Object entity) {
		drawMobModel(base, i, j, x, y, x2, y2, f, f1, f2, f3, f4, f5, move, entity);
	}

	@Override
	public void drawMobModel(Object guiScreen, int i, int j, int x, int y, int x2, int y2, float f, float f1, float f2, float f3, float f4, float f5, boolean move, Object entity) {
		//Modchu_Debug.dDebug("drawMobModel2 x=" + i + " y=" + j, 1);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		float entityWidth = Modchu_AS.getFloat(Modchu_AS.entityWidth, entity);
		float entityHeight = Modchu_AS.getFloat(Modchu_AS.entityHeight, entity);
		float width = Modchu_AS.getFloat(Modchu_AS.guiScreenWidth, guiScreen);
		float height = Modchu_AS.getFloat(Modchu_AS.guiScreenHeight, guiScreen);
		if (entityHeight > 2F) {
			f = f * 2F / entityHeight;
		}
		GL11.glTranslatef(x, y, 50F + f1);
		GL11.glScalef(-f, f, f);
/*
		if (Modchu_Main.getMinecraftVersion() > 169
				| (Modchu_Main.isRelease()
						&& Modchu_Main.isForge)
				| PFLM_Main.oldRender) {
*/
			GL11.glRotatef(180F, 180.0F, 0.0F, 1.0F);
/*
		} else {
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		}
*/
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		if (move) {
			float ff1 = width / 2 + x2 - i;
			float ff2 = height / 2 + y2 - j;
			GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
			//GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(comeraPosX, comeraPosY, comeraPosZ);
			GL11.glRotatef(comeraRotationX, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(comeraRotationY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(comeraRotationZ, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef((float) Math.atan(ff1 / 40F) * f5, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(cameraZoom, cameraZoom, cameraZoom);
			Modchu_AS.set(Modchu_AS.entityRotationYaw, entity, (float) Math.atan(ff1 / 40F) * f2);
			Modchu_AS.set(Modchu_AS.entityRotationPitch, entity, (float) Math.atan(ff2 / 40F) * f3);
			//entity.renderYawOffset = (float)Math.atan(ff1 / 40F) * f4;
			Modchu_AS.set(Modchu_AS.entityLivingBasePrevRotationYawHead, entity, Modchu_AS.getFloat(Modchu_AS.entityLivingBaseRotationYawHead, entity));
			Modchu_AS.set(Modchu_AS.entityLivingBaseRotationYawHead, entity, Modchu_AS.getFloat(Modchu_AS.entityRotationYaw, entity));
			//entity.rotationYawHead = 0F;
			//entity.prevRotationYawHead = 0F;
			Modchu_AS.set(Modchu_AS.entityLivingBaseRenderYawOffset, entity, 0.0F);
			//Modchu_Debug.dDebug("drawMobModel2 ff1=" + ff1 + " f2=" + f2+" entity.rotationYaw="+Modchu_AS.get(Modchu_AS.entityRotationYaw, entity), 2);
			//Modchu_Debug.dDebug("drawMobModel2 ff2=" + ff2 + " f3=" + f3, 3);
		} else {
			Modchu_AS.set(Modchu_AS.entityRotationYaw, entity, 0.0F);
			Modchu_AS.set(Modchu_AS.entityRotationPitch, entity, 0.0F);
			Modchu_AS.set(Modchu_AS.entityLivingBaseRenderYawOffset, entity, 0.0F);
			Modchu_AS.set(Modchu_AS.entityLivingBaseRotationYawHead, entity, 0.0F);
		}
		GL11.glTranslatef(0.0F, Modchu_AS.getFloat(Modchu_AS.entityYOffset, entity), 0.0F);
		//RenderManager.instance.playerViewY = 180F;
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
		Modchu_AS.set(Modchu_AS.renderHelperEnableStandardItemLighting);
		//GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		Modchu_AS.set(Modchu_AS.openGlHelperSetActiveTexture, Modchu_AS.getInt(Modchu_AS.openGlHelperLightmapTexUnit));
		Modchu_AS.set(Modchu_AS.openGlHelperSetActiveTexture, Modchu_AS.getInt(Modchu_AS.openGlHelperDefaultTexUnit));
		boolean b = Modchu_AS.getBoolean(Modchu_AS.renderManagerRenderEntityWithPosYaw, entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		//Modchu_Debug.mDebug("renderManagerRenderEntityWithPosYaw b="+b);
		//Modchu_Debug.mDebug("renderManagerGetEntityRenderObject="+Modchu_AS.get(Modchu_AS.renderManagerGetEntityRenderObject, entity));
		GL11.glPopMatrix();

		//Modchu_AS.set(Modchu_AS.renderHelperDisableStandardItemLighting);
		GL11.glDisable(32826);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		Modchu_AS.set(Modchu_AS.openGlHelperSetActiveTexture, Modchu_AS.getInt(Modchu_AS.openGlHelperLightmapTexUnit));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Modchu_AS.set(Modchu_AS.openGlHelperSetActiveTexture, Modchu_AS.getInt(Modchu_AS.openGlHelperDefaultTexUnit));
		GL11.glDisable(GL11.GL_LIGHTING);

	}

	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickButton, long time) {
		//Modchu_Debug.dDebug("mouseClickMove x=" + mouseX + " y=" + mouseY + " clickButton=" + clickButton + " time=" + time);
		//Modchu_Debug.dDebug("mouseClickMove anyButtonClick=" + PFLM_GuiSmallButtonMaster.anyButtonClick + " allButtonOutOfRangeClick="+PFLM_GuiSmallButtonMaster.allButtonOutOfRangeClick, 1);
		float width = Modchu_AS.getFloat(Modchu_AS.guiScreenWidth, base);
		float height = Modchu_AS.getFloat(Modchu_AS.guiScreenHeight, base);
		if (prevMouseX != -999
				&& prevMouseY != -999
				&& time > 200
				&& ((!PFLM_GuiSmallButtonMaster.anyButtonClick
				&& PFLM_GuiSmallButtonMaster.allButtonOutOfRangeClick)
				| !displayButton)) {
			int x = prevMouseX - mouseX;
			int y = prevMouseY - mouseY;
			switch (clickButton) {
			case 0:
				float f1 = 0.02F;
				if (x != 0) comeraPosX += (Modchu_Main.isForge
						| PFLM_Main.oldRender ? -x : x) * f1;
				if (y != 0) {
					float f2 = y * f1;
					if (Modchu_AS.getBoolean(Modchu_AS.isCtrlKeyDown)) comeraPosZ += f2;
					else comeraPosY += f2;
				}
				float f3 = width / 2;
				if (comeraPosX < -f3) comeraPosX = -f3;
				if (comeraPosY < -f3) comeraPosY = -f3;
				if (comeraPosZ < -f3) comeraPosZ = -f3;
				if (comeraPosX > f3) comeraPosX = f3;
				if (comeraPosY > f3) comeraPosY = f3;
				if (comeraPosZ > f3) comeraPosZ = f3;
				break;
			case 1:
				if (x != 0) comeraRotationY += x;
				if (y != 0) comeraRotationX += y;
				break;
			case 2:
				if (x != 0) comeraRotationZ += x;
				break;
			}
		}
		prevMouseX = mouseX;
		prevMouseY = mouseY;
		tempLastMouseEventTime = -1;
		clickMove = true;
	}

	@Override
	public void mouseMovedOrUp(int mouseX, int mouseY, int clickButton) {
		((Modchu_GuiModelView) base).superMouseMovedOrUp(mouseX, mouseY, clickButton);
		//Modchu_Debug.dDebug("mouseMovedOrUp x="+mouseX+" y="+mouseY+" clickButton="+clickButton);
		prevMouseX = -999;
		prevMouseY = -999;
	}

	@Override
	public boolean handleMouseInput() {
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int k = Mouse.getEventButton();
		int i = Mouse.getEventX() * width / Modchu_AS.getInt(Modchu_AS.minecraftDisplayWidth);
		//Modchu_Debug.dDebug("handleMouseInput k="+k+" cameraZoom="+cameraZoom, 3);
		if (i >= width / 2 - 60) {
			// ホイールの獲得
			int wheel = Mouse.getEventDWheel();
			if (wheel != 0) {
				float f = cameraZoom * (Modchu_AS.getBoolean(Modchu_AS.isCtrlKeyDown) ? 0.25F : 0.05F);
				cameraZoom += wheel > 0 ? f : -f;
				if (cameraZoom < 0.05F) cameraZoom = 0.05F;
				if (cameraZoom > 50.0F) cameraZoom = 50.0F;
			}
		}
		long systemTime = Modchu_AS.getLong(Modchu_AS.minecraftSystemTime);
		if (Mouse.isButtonDown(0)
				| Mouse.isButtonDown(1)
				| Mouse.isButtonDown(2)) ;else {
			if (tempLastMouseEventTime == -1) {
				if (clickMove) {
					clickMove = false;
					tempLastMouseEventTime = -2;
				} else {
					tempLastMouseEventTime = systemTime;
				}
			}
			return true;
		}
		//Modchu_Debug.mDebug("doubleClick systemTime="+systemTime+" tempLastMouseEvent="+tempLastMouseEvent);
		if (tempLastMouseEventTime < 0
				| systemTime - tempLastMouseEventTime > 500L
				| systemTime - tempLastMouseEventTime < 25L) {
			tempLastMouseEventTime = -1;
			return true;
		}
		//Modchu_Debug.mDebug("doubleClick Time="+(systemTime - tempLastMouseEventTime));
		tempLastMouseEventTime = systemTime;
		boolean doubleClick = k == tempLastMouseClick;
		tempLastMouseClick = k;
		if (doubleClick) {
			//Modchu_Debug.mDebug("doubleClick ok.");
			tempLastMouseEventTime = -1;
			switch (tempLastMouseClick) {
			case 0:
				comeraPosX = 0.0F;
				comeraPosY = 0.0F;
				comeraPosZ = 0.0F;
				break;
			case 1:
				comeraRotationX = 0;
				comeraRotationY = 0;
				comeraRotationZ = 0;
				break;
			case 2:
				cameraZoom = 1.0F;
				break;
			}
		}
		return true;
	}

	@Override
	public boolean keyTyped(char c, int i) {
		//Modchu_Debug.dDebug("keyTyped i="+i);
		super.keyTyped(c, i);
		if (i == 59) {
			displayButton = !displayButton;
			initGui();
		}
		return true;
	}

	@Override
	public void selected(String textureName, String textureArmorName, int color, boolean armorMode) {
		if (!armorMode) {
			((Modchu_GuiModelView) base).setTextureName(textureName);
			((Modchu_GuiModelView) base).setColor(color);
		}
		((Modchu_GuiModelView) base).setTextureArmorName(textureArmorName);
	}

	@Override
	public int colorCheck(String textureName, int i, boolean colorReverse) {
		Object texture = ModchuModel_Main.textureManagerGetTexture(textureName, i);
		if (texture == null) {
			int n = 0;
			for (; n < 16 && texture == null; n = n + 1) {
				i = colorReverse ? i - 1 : i + 1;
				i = i & 0xf;
				texture = ModchuModel_Main.textureManagerGetTexture(textureName, i);
			}
		}
		return i;
	}

	public String getTextureName() {
		return null;
	}

	public void setTextureName(String s) {
	}

	public String getTextureArmorName() {
		return null;
	}

	public void setTextureArmorName(String s) {
	}

	public int getColor() {
		return -1;
	}

	public void setColor(int i) {
	}

	public void setChangeMode(int i) {
	}

	public float getScale() {
		return 0.9375F;
	}

	public void setScale(float f) {
	}

	@Override
	public int getHandednessMode() {
		return 0;
	}

	@Override
	public void setHandednessMode(int i) {
	}

	public void reLoadModel(Object o, boolean b) {
		Modchu_Debug.mDebug("------modelDataSetting allModelInit start------ "+o);
		Modchu_AS.set(Modchu_AS.allModelInit, PFLM_Main.renderPlayerDummyInstance, o, b);
		Modchu_Debug.mDebug("------modelDataSetting allModelInit end------ "+o);
	}
}