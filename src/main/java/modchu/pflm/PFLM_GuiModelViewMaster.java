package modchu.pflm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_IGuiModelView;
import modchu.lib.Modchu_IGuiModelViewMaster;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.model.ModchuModel_EntityPlayerDummyMaster;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_ModelDataBase;
import modchu.model.ModchuModel_ModelDataMaster;
import modchu.model.ModchuModel_TextureManagerBase;

public class PFLM_GuiModelViewMaster extends PFLM_GuiBaseMaster implements Modchu_IGuiModelViewMaster {

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
	public int drawStringPosX;
	public int drawStringPosY;
	public int drawStringColor;
	protected int comeraRotationX;
	protected int comeraRotationY;
	protected int comeraRotationZ;
	private int prevMouseX;
	private int prevMouseY;
	protected float cameraZoom;
	private long tempLastMouseEventTime;
	private int tempLastMouseClick;
	protected float comeraPosX;
	protected float comeraPosY;
	protected float comeraPosZ;
	private boolean clickMove;

	public PFLM_GuiModelViewMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void init(HashMap<String, Object> map) {
		super.init(map);
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
		Modchu_Debug.mDebug("PFLM_GuiModelViewMaster reInit() 1 getTextureName()="+getTextureName());
		super.reInit();
		Modchu_Debug.mDebug("PFLM_GuiModelViewMaster reInit() 2 getTextureName()="+getTextureName());
		initDrawEntity();
		setTextureValue();
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		modelData.setCapsValue(modelData.caps_freeVariable, "showArmor", PFLM_ConfigData.showArmor);
		modelData.setCapsValue(modelData.caps_freeVariable, "showMainModel", true);
		drawEntitySetFlag = true;
	}

	@Override
	public void initGui() {
		if (base != null); else return;
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		if (buttonList != null); else return;
		buttonList.clear();
		if (!displayButton) return;
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		buttonList.add(newInstanceButton(200, x, y + 100, 75, 20, "Save"));
		buttonList.add(newInstanceButton(13, x, y + 85, 75, 15, "ChangeMode"));
		int version = Modchu_Main.getMinecraftVersion();
		if (version < 190
				&& !buttonPlayer) buttonList.add(newInstanceButton(58, x + 75, y + 55, 75, 15, "Handedness"));
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
		if (((Modchu_IGuiModelView) base).getScale() == 0.0F) {
			((Modchu_IGuiModelView) base).setScale(PFLM_Main.getModelScale(null));
		}
		guiMode = true;
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		if (!modelData.getCapsValueBoolean(modelData.caps_freeVariable, "initDrawEntityFlag")) initDrawEntity();
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		base.superDrawDefaultBackground();
		base.superDrawScreen(i, j, f);
		drawGuiContainerBackgroundLayer(f, i, j);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		if (drawStringList != null
				&& !drawStringList.isEmpty()
				&& displayButton); else return;
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
		try {
			if (drawEntity != null); else drawEntity = Modchu_Main.newModchuCharacteristicObject("Modchu_EntityPlayerDummy", ModchuModel_EntityPlayerDummyMaster.class, popWorld);
			if (drawEntity != null) {
				Modchu_Debug.lDebug("PFLM_GuiModelViewMaster initDrawEntity modelData start ----------------------");
				PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
				Modchu_Debug.lDebug("PFLM_GuiModelViewMaster initDrawEntity modelData end. ----------------------");
				if (modelData != null) {
					modelData.setCapsValue(modelData.caps_freeVariable, "showMainModel", true);
					modelData.setCapsValue(modelData.caps_freeVariable, "showShadowAndFire", false);
					modelData.setCapsValue(modelData.caps_freeVariable, "showRenderName", false);
					modelData.setCapsValue(modelData.caps_freeVariable, "initDrawEntityFlag", true);
				} else {
					Modchu_Debug.lDebug("PFLM_GuiModelViewMasterinitDrawEntity modelData == null error !! ");
				}
			} else {
				Modchu_Debug.lDebug("PFLM_GuiModelViewMasterinitDrawEntity drawEntity == null error !! ");
			}
		} catch(Error e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setTextureArmorPackege() {
		setTextureArmorPackege(2);
	}

	@Override
	public void setTextureArmorPackege(int i) {
		//modelData.setCapsValue(modelData.caps_textureArmorName, modelData.getCapsValue(modelData.caps_textureName));
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		String s = ModchuModel_TextureManagerBase.instance.getArmorName((String) modelData.getCapsValue(modelData.caps_textureArmorName), i, false);
		modelData.setCapsValue(modelData.caps_textureArmorName, s);
		Object ltb = ModchuModel_TextureManagerBase.instance.checkTextureArmorPackege(s);
		if (ltb != null) ;
		else {
			modelData.setCapsValue(modelData.caps_textureArmorName, ModchuModel_TextureManagerBase.instance.getDefaultTextureName());
		}
		if (modelData.getCapsValue(modelData.caps_textureArmorName) != null) ;
		else modelData.setCapsValue(modelData.caps_textureArmorName, modelData.getCapsValue(modelData.caps_textureName));
	}

	@Override
	public void setTextureValue() {
		//PFLM_ModelData drawEntityModelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntity);
		initDrawStringListFlag = true;
	}

	public void setTextureValueAfter() {
	}

	@Override
	public void setArmorTextureValue() {
	}

	protected void resetFlagCheck(boolean debug, boolean errorDefault) {
		if (drawEntitySetFlag) {
			setTextureValue();
			reLoadModel(drawEntity, debug, errorDefault);
			setTextureValueAfter();
			initGui();
			drawEntitySetFlag = false;
		}
		if (textureResetFlag) {
			((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).allModelTextureReset(drawEntity);
			textureResetFlag = false;
		}
	}

	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickButton, long time) {
		//Modchu_Debug.dDebug("mouseClickMove x=" + mouseX + " y=" + mouseY + " clickButton=" + clickButton + " time=" + time);
		//Modchu_Debug.dDebug("mouseClickMove anyButtonClick=" + PFLM_GuiSmallButtonMaster.anyButtonClick + " allButtonOutOfRangeClick="+PFLM_GuiSmallButtonMaster.allButtonOutOfRangeClick, 1);
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
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
				if (x != 0) comeraPosX += (
						//(Modchu_Main.isForge
						//&&
						Modchu_Main.getMinecraftVersion() < 160
						//)
						|
						ModchuModel_Main.oldRender ? -x : x) * f1;
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
	public void mouseReleased(int mouseX, int mouseY, int clickButton) {
		base.superMouseReleased(mouseX, mouseY, clickButton);
		//Modchu_Debug.dDebug("mouseMovedOrUp x="+mouseX+" y="+mouseY+" clickButton="+clickButton);
		prevMouseX = -999;
		prevMouseY = -999;
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
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
				| Mouse.isButtonDown(2)); else {
			if (tempLastMouseEventTime == -1) {
				if (clickMove) {
					clickMove = false;
					tempLastMouseEventTime = -2;
				} else {
					tempLastMouseEventTime = systemTime;
				}
			}
			return;
		}
		//Modchu_Debug.mDebug("doubleClick systemTime="+systemTime+" tempLastMouseEvent="+tempLastMouseEvent);
		if (tempLastMouseEventTime < 0
				| systemTime - tempLastMouseEventTime > 500L
				| systemTime - tempLastMouseEventTime < 25L) {
			tempLastMouseEventTime = -1;
			return;
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
	}

	@Override
	public void keyTyped(char c, int i) {
		//Modchu_Debug.dDebug("keyTyped i="+i);
		super.keyTyped(c, i);
		if (i == 59) {
			displayButton = !displayButton;
			initGui();
		}
		Modchu_Main.checkKeybinding();
	}

	@Override
	public void selected(String textureName, String textureArmorName, int color, boolean armorMode) {
		if (!armorMode) {
			((Modchu_IGuiModelView) base).setTextureName(textureName);
			((Modchu_IGuiModelView) base).setColor(color);
		}
		((Modchu_IGuiModelView) base).setTextureArmorName(textureArmorName);
		Modchu_Debug.mDebug("PFLM_GuiModelViewMaster selected textureName="+textureName+" armorMode="+armorMode);
		Modchu_Debug.mDebug("PFLM_GuiModelViewMaster selected getTextureName()="+getTextureName());
	}

	@Override
	public int colorCheck(String textureName, int i, boolean colorReverse) {
		Object texture = ModchuModel_TextureManagerBase.instance.textureManagerGetTexture(textureName, i);
		if (texture == null) {
			for (int n = 0; n < 16; n++) {
				texture = ModchuModel_TextureManagerBase.instance.textureManagerGetTexture(textureName, i);
				if (texture != null) {
					i = Modchu_Main.normalize(i, 0, 15, 15, 0);
					break;
				}
				i = colorReverse ? i - 1 : i + 1;
				i = Modchu_Main.normalize(i, 0, 15, 15, 0);
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

	public void reLoadModel(Object o, boolean debug, boolean errorDefault) {
		if (debug) Modchu_Debug.mDebug("------modelDataSetting allModelInit start------ "+o);
		Object master = Modchu_Main.getModchuCharacteristicObjectMaster(ModchuModel_Main.renderPlayerDummyInstance);
		Modchu_Reflect.invokeMethod(master.getClass(), "allModelInit", new Class[]{ Object.class, boolean.class, boolean.class }, master, new Object[]{ o, debug, errorDefault });
		if (debug) Modchu_Debug.mDebug("------modelDataSetting allModelInit end------ "+o);
	}

	@Override
	public void modelChange() {
	}
}