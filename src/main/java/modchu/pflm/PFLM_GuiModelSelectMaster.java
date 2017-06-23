package modchu.pflm;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_CastHelper;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_IGuiModelView;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.model.ModchuModel_EntityPlayerDummyMaster;
import modchu.model.ModchuModel_IEntityCaps;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_ModelDataBase;
import modchu.model.ModchuModel_ModelDataMaster;
import modchu.model.ModchuModel_RenderMasterBase;
import modchu.model.ModchuModel_TextureManagerBase;
import modchu.model.multimodel.base.MultiModelBaseBiped;

import org.lwjgl.input.Mouse;

public class PFLM_GuiModelSelectMaster extends PFLM_GuiModelViewMaster {
	public int modelColor;
	private int modelListx;
	private int modelListy;
	private int pointX;
	private int pointY;
	private int selectSlot;
	private int offsetSlot;
	private int maxOffsetSlot;
	private double[] textureRect;
	private BufferedImage bufferedimage;
	private long lastClicked;
	private int modelNamber;
	private int selectBoxX;
	private int selectBoxY;
	private int selectCursorId;
	private String playerName;
	private int select;
	private int prevButtonPage;
	private int prevButtonColor;
	private boolean changeColorFlag;
	public Object[] drawEntitys;
	private PFLM_ModelData[] modelDatas;
	private boolean isRendering[];
	public boolean displayModels;
	public boolean armorMode;
	private boolean initLoading;

	public PFLM_GuiModelSelectMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void init(HashMap<String, Object> map) {
		super.init(map);
		guiModelSelectMasterInit(map);
	}

	private void guiModelSelectMasterInit(HashMap<String, Object> map) {
		modelColor = 0;
		modelListx = 30;
		modelListy = 60;
		selectSlot = -1;
		offsetSlot = 0;
		modelNamber = 0;
		selectBoxX = 8;
		selectBoxY = 3;
		selectCursorId = -1;
		prevButtonPage = 0;
		prevButtonColor = 0;
		playerName = null;
		displayModels = true;
		displayModels = true;
		armorMode = false;
		PFLM_Main.texturesNamberInit();
		textureRect = new double[8];
		int i1 = getMaxSelectBoxViewCount();
		drawEntitys = new Object[i1];
		modelDatas = new PFLM_ModelData[i1];
		isRendering = new boolean[i1];
		if (map != null
				&& !map.isEmpty()) {
			armorMode = Modchu_CastHelper.Boolean(map.get("Boolean"));
			setColor(Modchu_CastHelper.Int(map.get("Integer")));
			playerName = Modchu_CastHelper.String(map.get("String"));
		}
		for (int i = 0; i < i1; i++) {
			drawEntitys[i] = Modchu_Main.newModchuCharacteristicObject("Modchu_EntityPlayerDummy", ModchuModel_EntityPlayerDummyMaster.class, popWorld);
			modelDatas[i] = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntitys[i]);
			modelDatas[i].setCapsValue(modelDatas[i].caps_freeVariable, "showArmor", armorMode);
			modelDatas[i].setCapsValue(modelDatas[i].caps_freeVariable, "showMainModel", !armorMode);
			modelDatas[i].setCapsValue(modelDatas[i].caps_freeVariable, "showShadowAndFire", false);
			modelDatas[i].setCapsValue(modelDatas[i].caps_freeVariable, "showRenderName", false);
			modelDatas[i].setCapsValue(modelDatas[i].caps_skinMode, ModchuModel_IEntityCaps.skinMode_offline);
			Modchu_AS.set(Modchu_AS.entitySetPosition, drawEntitys[i], Modchu_AS.getDouble(Modchu_AS.entityPosX), Modchu_AS.getDouble(Modchu_AS.entityPosY), Modchu_AS.getDouble(Modchu_AS.entityPosZ));
		}
		if (PFLM_ConfigData.isModelSize) {
			Modchu_AS.set(Modchu_AS.minecraftGameSettingsThirdPersonView, 0);
		}
		try {
			bufferedimage = ImageIO.read((URL) Modchu_Reflect.invokeMethod(Class.class, "getResource", new Class[]{ String.class }, Modchu_Reflect.loadClass("Minecraft"), new Object[]{ Modchu_Main.getMinecraftVersion() > 159 ? "/assets/minecraft/textures/particle/particles.png" : "/particles.png" }));
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
		Modchu_Main.resetTextureRect(textureRect);
		drawEntitySetFlag = true;
		initLoading = true;
		changeColorFlag = true;
		maxOffsetSlotCheack();
	}

	@Override
	public void reInit() {
		super.reInit();
		guiModelSelectMasterInit(null);
	}

	@Override
	public void initGui() {
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		buttonList.clear();
		if (!displayButton) return;
		buttonList.add(newInstanceButton(0, 80, 165, 15, 15, "-"));
		buttonList.add(newInstanceButton(1, 96, 165, 15, 15, "+"));
		int maxCount = getMaxSelectBoxViewCount();
		int maxPage = maxOffsetSlot / maxCount;
		//maxPage = 100;
		int page = offsetSlot / maxCount;
		//Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster initGui page="+page);
		//Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster initGui maxPage="+maxPage);
		if (page > 10) {
			buttonList.add(newInstanceButton(4, 118, 165, 15, 15, "<"));
			buttonList.add(newInstanceButton(5, 316, 165, 15, 15, ">"));
		}
		int x = 138;
		if (maxPage > 0
				&& page <= maxPage) {
			int i3 = prevButtonPage;
			//Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster initGui i3="+i3);
			for (int i1 = 0; i1 < 10; i1++) {
				if (i3 != page) buttonList.add(newInstanceButton(2000 + i3, x, 165, 15, 15, ""+i3));
				if (i3 >= maxPage) break;
				i3++;
				x += 18;
			}
		}
		if (!armorMode) {
			buttonList.add(newInstanceButton(2, 80, 190, 15, 15, "-"));
			buttonList.add(newInstanceButton(3, 96, 190, 15, 15, "+"));
			buttonList.add(newInstanceButton(6, 118, 190, 15, 15, "<"));
			buttonList.add(newInstanceButton(7, 316, 190, 15, 15, ">"));
			x = 138;
			int i2 = getColor() - 6 + prevButtonColor;
			if (i2 < 0) i2 = 0;
			if (i2 > 6) i2 = 6;
			for (int i1 = 0; i1 < 10; i1++) {
				if (i2 >= 16) break;
				buttonList.add(newInstanceButton(200 + i2, x, 190, 15, 15, ""+i2));
				i2++;
				x += 18;
			}
		}
		//PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntitys[i2]);
		//if (!ModchuModel_Main.bipedCheck(modelData.models[0]))
			buttonList.add(newInstanceButton(armorMode ? 102 : 103, 70, 210, 75, 20, armorMode ? "Model" : "Armor"));
		buttonList.add(newInstanceButton(100, 155, 210, 75, 20, "select"));
		buttonList.add(newInstanceButton(101, 240, 210, 75, 20, "return"));
		buttonList.add(newInstanceButton(999, 0, 0, 0, 0, ""));
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
	}

	@Override
	public void actionPerformed(Object guibutton) {
		if (!Modchu_AS.getBoolean(Modchu_AS.guiButtonEnabled, guibutton)) {
			return;
		}
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);
		boolean flag = id == 0
				| id == 1
				| id > 1999;
		int maxCount = getMaxSelectBoxViewCount();
		int maxPage = maxOffsetSlot / maxCount;
		//maxPage = 100;
		int page = offsetSlot / maxCount;
		//pagePrev
		if (flag) {
			if (id == 0) {
				int i = offsetSlot - maxCount;
				if (i > -1) offsetSlot -= maxCount;
				else offsetSlot = maxOffsetSlot;
			}
			//pageNext
			else if (id == 1) {
				int i = maxCount + offsetSlot;
				if (i < ModchuModel_TextureManagerBase.instance.textures.size()) {
					if (getTexturesNamber(i, getColor()) != -1) offsetSlot += maxCount;
					else offsetSlot = 0;
				} else {
					offsetSlot = 0;
				}
				//Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster actionPerformed pageNext offsetSlot="+offsetSlot);
			}
			//page set
			else if (id > 1999) {
				offsetSlot = (id - 2000) * maxCount;
			}
			selectSlot = -1;
			drawEntitySetFlag = true;
			initLoading = true;
			page = offsetSlot / maxCount;
			int i1 = page - 5;
			if (i1 < 0) i1 = 0;
			if (i1 > (maxPage - 10)) i1 = maxPage - 10;
			prevButtonPage = checkPrevButtonPage(i1);
			initGui();
			return;
		}
		flag = id == 4
				| id == 5;
		if (flag) {
			//Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster actionPerformed prevButtonPage 0 prevButtonPage="+prevButtonPage);
			prevButtonPage = checkPrevButtonPage(id == 5 ? prevButtonPage + 1 : prevButtonPage - 1);
			//Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster actionPerformed prevButtonPage 1 prevButtonPage="+prevButtonPage);
			initGui();
			return;
		}
		if (!armorMode) {
			flag = id == 2
					| id == 3
					| (id > 199
							&& id < 216);
			//colorPrev
			if (id == 2) {
				setColor(getColor() - 1);
			}
			//colorNext
			else if (id == 3) {
				setColor(getColor() + 1);
			}
			//color set
			else if (id > 199
					&& id < 216) {
				setColor(id - 200);
			}
			if (flag) {
				maxPageCheack();
				changeColorFlag = true;
				drawEntitySetFlag = true;
				initLoading = true;
				prevButtonColor = 0;
				initGui();
				return;
			}
			flag = id == 6
					| id == 7;
			if (flag) {
				prevButtonColor = id == 7 ? prevButtonColor + 1 : prevButtonColor - 1;
				if (prevButtonColor < -5) prevButtonColor = 5;
				if (prevButtonColor > 5) prevButtonColor = -5;
				initGui();
				return;
			}
		}
		//select
		if (id == 100) {
			if (selectSlot > -1
					&& getTexturesNamber(selectSlot, getColor()) != -1) {
				selected();
			}
		}
		//return
		if (id == 101) {
			if (parentScreen != null
					&& parentScreen instanceof Modchu_IGuiModelView) {
				((Modchu_IGuiModelView) parentScreen).reInit();
			}
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, parentScreen);
		}
		//Armor | Model
		if (id == 102 | id == 103) {
			Modchu_Debug.mDebug("Armor 1 ((Modchu_GuiModelView) parentScreen).getTextureName()="+((Modchu_IGuiModelView) parentScreen).getTextureName());
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", PFLM_GuiModelSelectMaster.class, popWorld, parentScreen, id == 103, getColor()));
			Modchu_Debug.mDebug("Armor 2 ((Modchu_GuiModelView) parentScreen).getTextureName()="+((Modchu_IGuiModelView) parentScreen).getTextureName());
			return;
		}
	}

	private int checkPrevButtonPage(int i) {
		int maxCount = getMaxSelectBoxViewCount();
		int maxPage = maxOffsetSlot / maxCount;
		//maxPage = 100;
		int i1 = maxPage - 10;
		if (i < 0) i = i1;
		if (i < 0) i = 0;

		// 10page以上
		if (i1 > -1) {
			if (i > i1) i = 0;
		} else {
		// 10page以下
			if (i > maxPage) i = 0;
		}
		//Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster checkPrevButtonPage i="+i);
		return i;
	}

	private void maxPageCheack() {
		int maxCount = getMaxSelectBoxViewCount();
		int maxTexturesNamber = getMaxTexturesNamber(getColor());
		if (offsetSlot / (getMaxSelectBoxViewCount()) > (maxTexturesNamber - 1) / maxCount) {
			offsetSlot = (maxTexturesNamber - 1) / maxCount * maxCount;
		}
		maxOffsetSlotCheack();
	}

	private void maxOffsetSlotCheack() {
		int maxCount = getMaxSelectBoxViewCount();
		int size = ModchuModel_TextureManagerBase.instance.textures.size();
		for (int i = 0; i < size; i += maxCount) {
			//Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster maxOffsetSlotCheack i="+i);
			if (getTexturesNamber(i, getColor()) != -1) maxOffsetSlot = i;
		}
		Modchu_Debug.mDebug("PFLM_GuiModelSelectMaster maxOffsetSlotCheack maxOffsetSlot="+maxOffsetSlot);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		//Modchu_Debug.mDebug1("drawGuiContainerBackgroundLayer ((Modchu_GuiModelView) parentScreen).getTextureName()="+((Modchu_GuiModelView) parentScreen).getTextureName());
		if (initLoading) {
			initLoading = false;
			drawString("Now Loading", 120, 155, 0xffffff);
			return;
		}
		if (displayModels) {
			modelNamber = offsetSlot;
			float f1 = 20F;
			for (int i2 = 0; i2 < selectBoxY; i2++) {
				for (int i1 = 0; i1 < selectBoxX; i1++) {
					drawModel(i, j, i1 * 25 + modelListx, i2 * 50 + modelListy, f1, modelNamber);
					++modelNamber;
				}
			}
			drawSelectCursor();
		}
		if (selectSlot > -1) {
			int i2 = maxSelectBoxCheck(selectSlot);
			String textureName = getTextureName(i2);
			if (textureName != null) {
				drawString(armorMode ? "TextureArmorName" : "TextureName", 240, 145, 0xffffff);
				drawString(textureName, 240, 155, 0xffffff);
				int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
				int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
				ModchuModel_RenderMasterBase.drawMobModel(width, height, i, j, 300, 130, 90, 30, 50F, 0.0F, comeraPosX, comeraPosY, comeraPosZ, comeraRotationX, comeraRotationY, comeraRotationZ, cameraZoom, cameraZoom, cameraZoom, true, drawEntitys[i2]);
			}
		}
		drawString("Page : " + offsetSlot / getMaxSelectBoxViewCount() + " / " + (getMaxTexturesNamber(getColor()) - 1) / getMaxSelectBoxViewCount(), 5, 170, 0xffffff);
		if (!armorMode) drawString("MaidColor : " + getColor(), 5, 194, 0xffffff);
		drawString(armorMode ? "ArmorSelect" : "ModelSelect", 180, 5, 0xffffff);
		if (changeColorFlag) changeColorFlag = false;
		if (drawEntitySetFlag) {
			//Modchu_Debug.mDebug("drawGuiContainerBackgroundLayer drawEntitySetFlag = false set");
			drawEntitySetFlag = false;
			initGui();
		}
	}

	private int getMaxTexturesNamber(int i) {
		if (armorMode) return PFLM_ConfigData.texturesArmorNamberList.size();
		List list = PFLM_ConfigData.texturesNamberMap.containsKey(i) ? PFLM_ConfigData.texturesNamberMap.get(i) : null;
		return list != null ? list.size() : -1;
	}

	private void drawSelectCursorInit() {
		if (bufferedimage != null) {
			if (Modchu_Main.getMinecraftVersion() > 159) {
				selectCursorId = Modchu_AS.getInt(Modchu_AS.textureUtilUploadTextureImage, 1, bufferedimage);
			} else {
				Modchu_AS.set(Modchu_AS.renderEngineSetupTexture, bufferedimage, 1);
			}
		}
	}

	private void drawSelectCursor() {
		if (selectCursorId > 0
				&& selectSlot > -1
				&& getTexturesNamber(selectSlot, getColor()) != -1) {
			if (Modchu_Main.getMinecraftVersion() > 159) {
				Modchu_AS.set(Modchu_AS.textureUtilBindTexture, selectCursorId);
			} else {
				Modchu_AS.set(Modchu_AS.renderEngineSetupTexture, bufferedimage, 1);
			}
			Modchu_Main.renderTexture((selectSlot - offsetSlot) % selectBoxX * 25 + modelListx, (selectSlot - offsetSlot) / selectBoxX * 55 + modelListy - 50, 8, 8, textureRect);
		}
	}

	private void drawModel(int i, int j, int x, int y, float f, int modelNamber) {
		int i2 = maxSelectBoxCheck(modelNamber);
		//Modchu_Debug.mDebug("drawModel i2="+i2);
		if (i2 < 0) return;
		if (drawEntitySetFlag) {
			if (i2 == 0) {
				int i3 = getMaxSelectBoxViewCount();
				for (int i4 = 0; i4 < i3; i4++) {
					modelDatas[i4].models[0] = null;
					modelDatas[i4].models[1] = null;
					modelDatas[i4].models[2] = null;
				}
			}
			int i1 = getTexturesNamber(modelNamber, getColor());
			if (armorMode) setColor(0);
			setScale(PFLM_Main.getModelScale(drawEntitys[i2]));
			if (i1 < 0 | i1 >= ModchuModel_TextureManagerBase.instance.textures.size()) ;
			else {
				Object ltb = ModchuModel_TextureManagerBase.instance.getTextureBox(i1, false);
				String textureName = ltb != null ? ModchuModel_TextureManagerBase.instance.getTextureBoxTextureName(ltb) : null;
				if (textureName != null
						&& !textureName.isEmpty()
						| armorMode); else {
					ltb = ModchuModel_TextureManagerBase.instance.getTextureBox(getTextureName(i2), true);
					textureName = ltb != null ? ModchuModel_TextureManagerBase.instance.getTextureBoxPackegeName(ltb) : ModchuModel_TextureManagerBase.instance.getDefaultTextureName();
				}
				setTextureName(i2, textureName);
				setTextureArmorName(i2, textureName);
				if (textureName != null
						&& ltb != null) {
					modelDatas[i2].setCapsValue(modelDatas[i2].caps_freeVariable, "skinChar", false);
					setColor(i2, getColor());
					//Modchu_Debug.mDebug("getTextureName(i2)=" + getTextureName(i2));
					//Modchu_Debug.mDebug("getTextureArmorName(i2)=" + getTextureArmorName(i2));
					reLoadModel(drawEntitys[i2], modelDatas[i2], false, false);
					if (changeColorFlag) modelDatas[i2].setCapsValue(modelDatas[i2].caps_changeColor, drawEntitys[i2]);
					if (armorMode) modelDatas[i2].models[0] = modelDatas[i2].models[1];
					//Modchu_Debug.mDebug("armorMode="+armorMode+" modelDatas["+i2+"].models[0]="+modelDatas[i2].models[0]);
				}
			}
			isRendering[i2] = (!armorMode && modelDatas[i2].models[0] != null) | (armorMode && (modelDatas[i2].models[1] != null | modelDatas[i2].models[2] != null));
		}
		//Modchu_Debug.mDebug("modelNamber="+modelNamber+" getTextureName("+i2+")="+getTextureName(i2));
		if (isRendering[i2]) {
			int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
			int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
			ModchuModel_RenderMasterBase.drawMobModel(width, height, i, j, x, y, 0, -50, f, 0.0F, comeraPosX, comeraPosY, comeraPosZ, comeraRotationX, comeraRotationY, comeraRotationZ, cameraZoom, cameraZoom, cameraZoom, false, drawEntitys[i2]);
		}
	}

	public void reLoadModel(Object o, ModchuModel_ModelDataBase entityCaps, boolean debug, boolean errorDefault) {
		if (debug) Modchu_Debug.mDebug("------modelDataSetting allModelInit start------ "+o);
		if (!armorMode) ModchuModel_ModelDataMaster.instance.modelInit(o, entityCaps, Modchu_CastHelper.String(entityCaps.getCapsValue(entityCaps.caps_textureName)), debug, errorDefault);
		ModchuModel_ModelDataMaster.instance.modelArmorInit(o, entityCaps, Modchu_CastHelper.String(entityCaps.getCapsValue(entityCaps.caps_textureArmorName)), debug, errorDefault);
		if (debug) Modchu_Debug.mDebug("------modelDataSetting allModelInit end------ "+o);
	}

	public void setTextureValue(String texture, String armorTexture, int color) {
	}

	@Override
	public void setTextureArmorPackege() {
	}

	@Override
	public void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		boolean doubleClick = false;
		if (Mouse.isButtonDown(0)
				&& displayModels); else return;
		long l1 = Modchu_AS.getLong(Modchu_AS.minecraftSystemTime);
		if (l1 - lastClicked < 250L) doubleClick = true;
		lastClicked = l1;
		pointX = (x - 21) / 24;
		pointY = (y - 10) / 50;
		//Modchu_Debug.dDebug("x="+x);
		//Modchu_Debug.dDebug("y="+y,1);
		//Modchu_Debug.dDebug("pointX="+pointX,2);
		//Modchu_Debug.dDebug("pointY="+pointY,3);
		if (pointX >= 0 && pointX < selectBoxX && pointY >= 0 && pointY < selectBoxY) {
			int i1 = offsetSlot + pointX + (pointY * selectBoxX);
			if (i1 < ModchuModel_TextureManagerBase.instance.textures.size()) {
				if (getTexturesNamber(i1, getColor()) != -1) selectSlot = i1;
			}
			if (doubleClick && selectSlot > -1 && getTexturesNamber(selectSlot, getColor()) != -1) {
				//Modchu_Debug.mDebug("selectSlot="+selectSlot+" PFLM_Main.texturesNamber[getColor()][selectSlot]="+PFLM_Main.texturesNamber[getColor()][selectSlot]);
				selected();
			}
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		//Modchu_Debug.dDebug("keyTyped i="+i);
		if (i == 61) {
			displayModels = !displayModels;
			initGui();
		}
	}

	private int getTexturesNamber(int i, int i1) {
		if (armorMode) {
			if (i >= PFLM_ConfigData.texturesArmorNamberList.size()) return -1;
			return PFLM_ConfigData.texturesArmorNamberList.get(i);
		} else {
			if (!PFLM_ConfigData.texturesNamberMap.containsKey(i1)) return -1;
			List<Integer> list = PFLM_ConfigData.texturesNamberMap.get(i1);
			return list != null && !list.isEmpty() && i < list.size() ? list.get(i) : -1;
		}
	}

	private void selected() {
		if (parentScreen instanceof Modchu_IGuiModelView); else {
			Modchu_Debug.mDebug("selected !parentScreen instanceof Modchu_IGuiModelView !! parentScreen="+(parentScreen != null ? parentScreen.getClass() : null));
			return;
		}
		Modchu_IGuiModelView gui = (Modchu_IGuiModelView) parentScreen;
		int i2 = maxSelectBoxCheck(selectSlot);
		gui.selected(getTextureName(i2), getTextureArmorName(i2), getColor(), armorMode);
		Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, gui);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(drawEntitys[i2]);
		modelData.setCapsValue(modelData.caps_freeVariable, "showMainModel", true);
		modelData.setCapsValue(modelData.caps_freeVariable, "showShadowAndFire", false);
		modelData.setCapsValue(modelData.caps_freeVariable, "showRenderName", false);
		gui.setTextureValue();
		gui.modelChange();
	}

	public int getMaxSelectBoxViewCount() {
		return selectBoxX * selectBoxY;
	}

	private int maxSelectBoxCheck(int i) {
		int i1 = getMaxSelectBoxViewCount();
		while (i >= i1) {
			i = i - i1;
		}
		return i;
	}

	@Override
	public String getTextureName() {
		return getTextureName(selectSlot);
	}

	public String getTextureName(int i) {
		return modelDatas != null
				&& i > -1
				&& i < modelDatas.length ? Modchu_CastHelper.String(modelDatas[i].getCapsValue(modelDatas[i].caps_textureName)) : null;
	}

	@Override
	public void setTextureName(String s) {
		setTextureName(selectSlot, s);
	}

	public void setTextureName(int i, String s) {
		if (modelDatas != null
				&& i > -1
				&& i < modelDatas.length) modelDatas[i].setCapsValue(modelDatas[i].caps_textureName, s);
	}

	@Override
	public String getTextureArmorName() {
		return null;
	}

	public String getTextureArmorName(int i) {
		return modelDatas != null
				&& i > -1
				&& i < modelDatas.length ? Modchu_CastHelper.String(modelDatas[i].getCapsValue(modelDatas[i].caps_textureArmorName)) : null;
	}

	@Override
	public void setTextureArmorName(String s) {
	}

	public void setTextureArmorName(int i, String s) {
		if (modelDatas != null
				&& i > -1
				&& i < modelDatas.length) modelDatas[i].setCapsValue(modelDatas[i].caps_textureArmorName, s);
	}

	@Override
	public int getColor() {
		return modelColor;
	}

	@Override
	public void setColor(int i) {
		modelColor = i & 0xf;
	}

	public void setColor(int i, int i1) {
		if (modelDatas != null
				&& i > -1
				&& i < modelDatas.length) modelDatas[i].setCapsValue(modelDatas[i].caps_maidColor, i1 & 0xf);
	}

	@Override
	public float getScale() {
		return 1.0F;
	}

	@Override
	public void setScale(float f) {
	}

	@Override
	public void modelChange() {
	}

}