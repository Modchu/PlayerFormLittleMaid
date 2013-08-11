package net.minecraft.src;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PFLM_GuiSlot {

    protected int top;
    protected int bottom;
    protected int slotHeight;
    protected int mouseX;
    protected int mouseY;

	private float initialClickY = -2.0F;
	public float amountScrolled;
	private float scrollMultiplier;
	private int width;
	private int height;
	private int selectedElement = -1;
	private int right;
	private int left;
	private int field_77242_t;
	private long lastClicked = 0L;
	private boolean showSelectionBox = true;
	private boolean field_77243_s;

	public PFLM_GuiSlotBase parentScreen;
	public List parentScreenList;
	public int guiNumber = 0;
	public int slotPosY;
	public int slotPosX;
	public int scrollBarY = 0;
	public boolean oneClickLock;
	public int noUseTopBottomSizeY = 0;
	public int selectedDisplayCount;
	private int selected;
	private World popWorld;
	private int showSelectionBoxWidth;
	private int scrollUpButtonID;
	private int scrollDownButtonID;
	private int scrollBarInBoxY;
	private int tempMouseY;
	private boolean mouseClick;

	public PFLM_GuiSlot(Object par1Minecraft, PFLM_GuiSlotBase gui, int par3, int par4, int par5, int par6, int par7) {
		init(par1Minecraft, gui, par3, par4, par5, par6, par7);
	}

	public void init(Object par1Minecraft, PFLM_GuiSlotBase gui, int par3, int par4, int par5, int par6, int par7) {
		width = gui.width;
		height = gui.height;
		top = 0;
		bottom = par3;
		slotHeight = par4;
		left = 0;
		right = par3;
		width = gui.width;
		height = gui.height;
		showSelectionBoxWidth = par3;
		slotPosX = par5;
		slotPosY = par6;
		guiNumber = par7;
		parentScreen = gui;
		selected = 0;
		popWorld = mod_Modchu_ModchuLib.modchu_Main.getTheWorld();
	}

	protected int getSize() {
		return parentScreen.getGuiSlotSize(guiNumber);
	}

	protected int getContentHeight() {
		return parentScreen.getGuiSlotContentHeight(guiNumber);
	}

	protected void elementClicked(int i, boolean flag) {
		parentScreen.guiSlotElementClicked(guiNumber, i, flag);
	}

	protected void outOfRangeClick(int mouse_x, int mouse_y, boolean flag) {
		parentScreen.outOfRangeClick(guiNumber, mouse_x, mouse_y, flag);
	}

	protected boolean isSelected(int i) {
		return parentScreen.guiSlotIsSelected(guiNumber, i);
	}

	protected void drawBackground() {
	}

	public void clickDecision(int mouse_x, int mouse_y, float par3)
	{
		byte mouseMoveY = (byte) (tempMouseY > Mouse.getY() ? 1 : tempMouseY < Mouse.getY() ? -1 : 0);
		tempMouseY = Mouse.getY();
		mouseX = mouse_x;
		mouseY = mouse_y;
		int sizeY = bottom - (slotPosY > -1 ? slotPosY : -slotPosY);
		int scrollBarX = getScrollBarX();
		int showSelectionBoxSizeY = slotHeight - 4;
		int showSelectionBoxLeft = slotPosX;
		int showSelectionBoxRight = slotPosX + showSelectionBoxWidth;
		int slotSize = getSize();
		int scrollBarSizeX = getScrollBarSizeX();
		int scrollBarInBoxSizeY = 0;
		boolean isScrollBar = selectedDisplayCount < getContentHeight() / slotHeight;
		int scrollBarDisplaySizeY = bottom + top;
		//if (guiNumber == 0) Modchu_Debug.mDebug("top="+top+" bottom="+bottom);
		float f;
		if (isScrollBar) {
			f = ((float) selectedDisplayCount / (float) slotSize) * 0.9F;
			//if (guiNumber == 0) Modchu_Debug.mDebug("selectedDisplayCount="+selectedDisplayCount+" slotSize="+slotSize+" f="+f);
			scrollBarInBoxSizeY = (int) (scrollBarDisplaySizeY * f);
			//if (guiNumber == 0) Modchu_Debug.mDebug("scrollBarDisplaySizeY="+scrollBarDisplaySizeY+" scrollBarInBoxSizeY="+scrollBarInBoxSizeY);
		}

		if (Mouse.isButtonDown(0)
				&& !oneClickLock) {
			//if (initialClickY == -1.0F) {
				boolean var7 = true;

				if (mouse_y >= slotPosY && mouse_y <= slotPosY + sizeY + slotHeight) {
					int clickY = mouse_y + ((int)amountScrolled * slotHeight);
					selected = (clickY - slotPosY + (showSelectionBoxSizeY / 2)) / slotHeight;
					//if (guiNumber == 0) Modchu_Debug.mDebug("Mouse.isButtonDown selected="+selected);
					//Modchu_Debug.mDebug("showSelectionBoxSizeY="+showSelectionBoxSizeY);
					//if (guiNumber == 0) Modchu_Debug.mDebug("Mouse.isButtonDown amountScrolled="+amountScrolled);
					//Modchu_Debug.mDebug("clickY="+clickY+" mouse_y="+mouse_y);

					Long l1 = (Long) Modchu_Reflect.invokeMethod("Minecraft", "func_71386_F", "getSystemTime", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
					if (mouse_x >= showSelectionBoxLeft && mouse_x <= showSelectionBoxRight && selected >= 0 && clickY >= 0 && selected < slotSize) {
						boolean var12 = selected == selectedElement && l1 - lastClicked < 250L;
						elementClicked(selected, var12);
						selectedElement = selected;
						lastClicked = l1;
					} else if (mouse_x >= showSelectionBoxLeft && mouse_x <= showSelectionBoxRight && clickY < 0) {
						outOfRangeClick(mouse_x, mouse_y, l1 - lastClicked < 250L);
						var7 = false;
					}
				}// else {
					//initialClickY = -2.0F;
				//}

				//Modchu_Debug.mDebug("mouse_x="+mouse_x+" scrollBarX="+scrollBarX);
				if (isScrollBar
						&& mouse_x >= scrollBarX && mouse_x <= scrollBarX + scrollBarSizeX
						&& (mouse_y >= top && mouse_y <= bottom)) {
					if (mouse_y < scrollBarInBoxY) {
						scrollMultiplier = -scrollBarSizeX;
					} else {
						if (!mouseClick
								| (mouseClick
								&& mouse_y > scrollBarInBoxY + scrollBarInBoxSizeY)) scrollMultiplier = scrollBarSizeX;
					}
					//initialClickY = (float)mouse_y;
					if (mouse_y < scrollBarInBoxY
							| mouse_y > scrollBarInBoxY + scrollBarInBoxSizeY) {
						amountScrolled += mouseClick ? scrollMultiplier > 0 ? 1.0F : -1.0F : scrollMultiplier;
					} else {
						f = mouseMoveY == 1 ? -1.0F : mouseMoveY == -1 ? 1.0F : 0.0F;
						amountScrolled -= f;
						//if (guiNumber == 0) Modchu_Debug.mDebug("scrollBar click amountScrolled="+amountScrolled);
					}
					//Modchu_Debug.mDebug("scrollBar click scrollMultiplier="+scrollMultiplier+" amountScrolled="+amountScrolled);
				}// else {
					//scrollMultiplier = 1.0F;
				//}

				//if (var7) {
				//} else {
					//initialClickY = -2.0F;
				//}
			//}// else if (initialClickY >= 0.0F) {
				//Modchu_Debug.mDebug("amountScrolled="+amountScrolled);
			//}
			mouseClick = true;
		} else {
			if (oneClickLock
					&& !Mouse.isButtonDown(0)) {
				oneClickLock = false;
			}
			if (isScrollBar
					&& ((mouse_x >= scrollBarX && mouse_x <= scrollBarX + scrollBarSizeX)
					| (mouse_x >= showSelectionBoxLeft && mouse_x <= showSelectionBoxRight))
					&& (mouse_y >= slotPosY && mouse_y <= slotPosY + sizeY)) {
				GameSettings gameSettings = (GameSettings) Modchu_Reflect.getFieldObject("Minecraft", "field_71474_y", "gameSettings", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
				while (!gameSettings.touchscreen && Mouse.next()) {
					int var16 = Mouse.getEventDWheel();
					if (var16 != 0) {
						if (var16 > 0) {
							var16 = -1;
						} else if (var16 < 0) {
							var16 = 1;
						}
						int i1 = height / 50 < 2 ? 1 : height / 50;
						if (i1 > selectedDisplayCount) i1 = selectedDisplayCount;
						amountScrolled += (float)(var16 * i1);
						//Modchu_Debug.mDebug("touchscreen && Mouse.next() amountScrolled="+amountScrolled);
					}
				}
			}
			//initialClickY = -1.0F;
			scrollMultiplier = 0.0F;
			mouseClick = false;
		}
	}

	public void drawScreen(int mouse_x, int mouse_y, float par3)
	{
		mouseX = mouse_x;
		mouseY = mouse_y;
		top = getTop();
		bottom = getBottom();
		int slotSize = getSize();
		int scrollBarX = getScrollBarX();
		int scrollBarSizeX = getScrollBarSizeX();
		int showSelectionBoxLeft = slotPosX;
		int showSelectionBoxRight = slotPosX + showSelectionBoxWidth;
		int sizeY = bottom - (slotPosY > -1 ? slotPosY : -slotPosY);
		int selected;
		int showSelectionBoxSizeY = slotHeight - 4;
		int showSelectionBoxPosTop;
		int scrollBarDisplaySizeY = bottom + top;
		//Modchu_Debug.mDebug("scrollBarDisplaySizeY="+scrollBarDisplaySizeY);
		if (scrollBarDisplaySizeY < 1) scrollBarDisplaySizeY = 1;
		//int scrollBarSizeY = getContentHeight();
		//Modchu_Debug.mDebug("scrollBarSizeY="+scrollBarSizeY);

		selectedDisplayCount = getLimitSelectedDisplayCount() > 0 ? getLimitSelectedDisplayCount() : sizeY / slotHeight;
		int scrollBarInBoxSizeY = 0;
		boolean isScrollBar = selectedDisplayCount < getContentHeight() / slotHeight;
		//if (guiNumber == 0) Modchu_Debug.mDebug("scrollBarDisplaySizeY="+scrollBarDisplaySizeY+" slotHeight="+slotHeight);
		//if (guiNumber == 0) Modchu_Debug.mDebug("selectedDisplayCount="+selectedDisplayCount+" getContentHeight() / slotHeight="+(getContentHeight() / slotHeight));
		//if (guiNumber == 0) Modchu_Debug.mDebug("top="+top+" bottom="+bottom);
		if (isScrollBar) {
			float f = ((float) selectedDisplayCount / (float) slotSize) * 0.9F;
			//if (guiNumber == 0) Modchu_Debug.mDebug("selectedDisplayCount="+selectedDisplayCount+" slotSize="+slotSize+" f="+f);
			scrollBarInBoxSizeY = (int) (scrollBarDisplaySizeY * f);
			//if (guiNumber == 0) Modchu_Debug.mDebug("scrollBarDisplaySizeY="+scrollBarDisplaySizeY+" scrollBarInBoxSizeY="+scrollBarInBoxSizeY);
		}

		bindAmountScrolled();
		if (amountScrolled < 0.0F) {
			amountScrolled = 0.0F;
		}
		//GL11.glDisable(GL11.GL_LIGHTING);
		//GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator = Tessellator.instance;
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*
		if (field_77243_s)
		{
			func_77222_a(showSelectionBoxRight, slotPosY, tessellator);
		}
*/
		int startSelected = (int) amountScrolled > 0 ? (int) amountScrolled : 0;
		if (slotSize - startSelected < selectedDisplayCount) {
			//Modchu_Debug.mDebug("スクロール位置調整");
			startSelected = slotSize - selectedDisplayCount;
			if (startSelected < -1) startSelected = 0;
			amountScrolled = (float) startSelected;
		}
		if (startSelected < 0) startSelected = 0;
		//Modchu_Debug.mDebug("スクロール位置="+(slotSize - startSelected));
		//Modchu_Debug.mDebug("guiNumber"+guiNumber+" 表示数="+selectedDisplayCount);
		//Modchu_Debug.mDebug("guiNumber"+guiNumber+" amountScrolled="+amountScrolled);
		//Modchu_Debug.mDebug("startSelected="+startSelected);
		//Modchu_Debug.mDebug("guiNumber"+guiNumber+" startSelected="+startSelected);
		for (selected = startSelected; selected < slotSize
				&& selected <= startSelected + selectedDisplayCount; selected++) {
			//if (guiNumber == 0) Modchu_Debug.mDebug("selected="+selected+" startSelected="+startSelected+" selectedDisplayCount="+selectedDisplayCount);

			showSelectionBoxPosTop = slotPosY + (selected * slotHeight) - ((int) amountScrolled * slotHeight);// - ((int) amountScrolled > 0 ? slotHeight : 0);
			showSelectionBoxSizeY = slotHeight - 4;
			//Modchu_Debug.mDebug("guiNumber"+guiNumber+" showSelectionBoxPosTop="+showSelectionBoxPosTop);
			//Modchu_Debug.mDebug("guiNumber"+guiNumber+" slotPosY="+slotPosY);
			//Modchu_Debug.mDebug("guiNumber"+guiNumber+" slotHeight="+slotHeight);
			//Modchu_Debug.mDebug("guiNumber"+guiNumber+" amountScrolled="+amountScrolled);

			//if (showSelectionBoxPosTop <= slotPosY + sizeY && showSelectionBoxPosTop + showSelectionBoxSizeY >= slotPosY)
			//{
				if (showSelectionBox && isSelected(selected))
				{
					//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator.startDrawingQuads();
					tessellator.setColorOpaque_I(8421504);
					tessellator.addVertexWithUV((double)showSelectionBoxLeft, (double)(showSelectionBoxPosTop + showSelectionBoxSizeY + 2), 0.0D, 0.0D, 1.0D);
					tessellator.addVertexWithUV((double)showSelectionBoxRight, (double)(showSelectionBoxPosTop + showSelectionBoxSizeY + 2), 0.0D, 1.0D, 1.0D);
					tessellator.addVertexWithUV((double)showSelectionBoxRight, (double)(showSelectionBoxPosTop - 2), 0.0D, 1.0D, 0.0D);
					tessellator.addVertexWithUV((double)showSelectionBoxLeft, (double)(showSelectionBoxPosTop - 2), 0.0D, 0.0D, 0.0D);
					tessellator.setColorOpaque_I(0);
					tessellator.addVertexWithUV((double)(showSelectionBoxLeft + 1), (double)(showSelectionBoxPosTop + showSelectionBoxSizeY + 1), 0.0D, 0.0D, 1.0D);
					tessellator.addVertexWithUV((double)(showSelectionBoxRight - 1), (double)(showSelectionBoxPosTop + showSelectionBoxSizeY + 1), 0.0D, 1.0D, 1.0D);
					tessellator.addVertexWithUV((double)(showSelectionBoxRight - 1), (double)(showSelectionBoxPosTop - 1), 0.0D, 1.0D, 0.0D);
					tessellator.addVertexWithUV((double)(showSelectionBoxLeft + 1), (double)(showSelectionBoxPosTop - 1), 0.0D, 0.0D, 0.0D);
					tessellator.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}

				//if (guiNumber == 0) Modchu_Debug.mDebug("drawSlot height="+height+" showSelectionBoxPosTop="+showSelectionBoxPosTop+" showSelectionBoxSizeY="+showSelectionBoxSizeY);
				drawSlot(selected, showSelectionBoxLeft, showSelectionBoxRight, showSelectionBoxPosTop, showSelectionBoxSizeY, tessellator);
			//}
		}
		showSelectionBoxPosTop = slotPosY - ((int) amountScrolled * slotHeight);
		drawSlot(-1, showSelectionBoxLeft, showSelectionBoxRight, showSelectionBoxPosTop, showSelectionBoxSizeY, tessellator);

		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		//GL11.glDisable(GL11.GL_ALPHA_TEST);
		//GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int var19 = func_77209_d();
		//Modchu_Debug.mDebug("var19="+var19);
		if (isScrollBar) {
			if (scrollBarInBoxSizeY < 32) scrollBarInBoxSizeY = 32;
			if (scrollBarInBoxSizeY > scrollBarDisplaySizeY) scrollBarInBoxSizeY = scrollBarDisplaySizeY;
			//Modchu_Debug.mDebug("scrollBarDisplaySizeY="+scrollBarDisplaySizeY+" scrollBarInBoxSizeY="+scrollBarInBoxSizeY+" f="+f);

			//float f1 = amountScrolled / (float)(getSize() - selectedDisplayCount);
			//if (f1 < 0.0F) f1 = 0.0F;

			int var13 = (bottom - top) * (bottom - top) / getContentHeight();
			if (var13 < 32) var13 = 32;
			if (var13 > bottom - top - 32) var13 = bottom - top - 32;
			scrollBarInBoxY = (int)amountScrolled * (bottom - top - var13) / var19 + top;
			//scrollBarInBoxY = (int) (top + ((height - top) * f1));
			//if (guiNumber == 0) Modchu_Debug.mDebug("f1="+f1+" scrollBarInBoxY="+scrollBarInBoxY);
			//if (scrollBarInBoxY < top) scrollBarInBoxY = top;
			//if (scrollBarInBoxY + scrollBarInBoxSizeY > bottom) scrollBarInBoxY = bottom - scrollBarInBoxSizeY;
			//if (guiNumber == 0) Modchu_Debug.mDebug("scrollBarInBoxY="+scrollBarInBoxY);
			//if (guiNumber == 0) Modchu_Debug.mDebug("amountScrolled="+amountScrolled);

			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			double dBottom = (double)bottom + scrollBarY;
			double dTop = (double)top + scrollBarY;
			tessellator.addVertexWithUV((double)scrollBarX, dBottom, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double)scrollBarX + scrollBarSizeX, dBottom, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double)scrollBarX + scrollBarSizeX, dTop, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV((double)scrollBarX, dTop, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(8421504, 255);
			dTop = (double)scrollBarInBoxY + scrollBarY;
			tessellator.addVertexWithUV((double)scrollBarX, dTop + (double)scrollBarInBoxSizeY, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double)scrollBarX + scrollBarSizeX, dTop + (double)scrollBarInBoxSizeY, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double)scrollBarX + scrollBarSizeX, dTop, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV((double)scrollBarX, dTop, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(12632256, 255);
			tessellator.addVertexWithUV((double)scrollBarX, dTop + (double)(scrollBarInBoxSizeY - 1), 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double)(scrollBarSizeX - 1), dTop + (double)(scrollBarInBoxSizeY - 1), 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double)(scrollBarSizeX - 1), dTop, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV((double)scrollBarX, dTop, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glShadeModel(GL11.GL_FLAT);
		//GL11.glEnable(GL11.GL_ALPHA_TEST);
		//GL11.glDisable(GL11.GL_BLEND);
		//GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	}

	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
	}

	protected void drawSlot(int i, int posX, int slotPosY, int k, int l, Tessellator tessellator) {
		parentScreen.guiSlotDrawSlot(guiNumber, i, posX, slotPosY, k, l, tessellator);
	}

	protected int getScrollBarX() {
		return parentScreen.getSlotScrollBarX(guiNumber);
	}

	protected int getScrollBarSizeX() {
		return parentScreen.getSlotScrollBarSizeX(guiNumber);
	}

	protected int getTop() {
		return parentScreen.getTop(guiNumber);
	}

	protected int getBottom() {
		return parentScreen.getBottom(guiNumber);
	}

	protected int getLimitSelectedDisplayCount() {
		return parentScreen.getLimitSelectedDisplayCount(guiNumber);
	}

	private void bindAmountScrolled() {
		int var1 = func_77209_d();
		//if (amountScrolled < 0.0F) amountScrolled = 0.0F;
		if (var1 > 0
				&& amountScrolled > (float)var1) {
			//Modchu_Debug.mDebug("bindAmountScrolled() amountScrolled > (float)var1 var1="+var1);
			amountScrolled = (float)var1;
		}
	}

	public int func_77209_d() {
		//最大スクロール位置
		int i = getSize() - selectedDisplayCount;
		//if (guiNumber == 0) {
			//Modchu_Debug.mDebug("func_77209_d() i="+i);
			//Modchu_Debug.mDebug("func_77209_d() selectedDisplayCount="+selectedDisplayCount+" getSize()="+getSize());
		//}
		if (i < 1) return getSize();
		return i;
	}

	public void registerScrollButtons(int par2, int par3) {
		scrollUpButtonID = par2;
		scrollDownButtonID = par3;
	}

	public void actionPerformed(int i) {
	}

	private void overlayBackground(int par1, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
			Object var4 = Modchu_Reflect.invokeMethod("Minecraft", "func_110434_K", mod_Modchu_ModchuLib.modchu_Main.getMinecraft());
			Modchu_Reflect.invokeMethod("TextureManager", "func_110577_a", new Class[]{ Modchu_Reflect.loadClass("ResourceLocation") }, var4, new Object[]{ Modchu_Reflect.getFieldObject(Gui.class, "field_110325_k") });
			//mc.func_110434_K().func_110577_a(Gui.field_110325_k);
		} else {
			Modchu_Reflect.invokeMethod("RenderEngine", "bindTexture", new Class[]{ String.class }, Modchu_Reflect.getFieldObject("Minecraft", "renderEngine", mod_Modchu_ModchuLib.modchu_Main.getMinecraft()), new Object[]{ "/gui/background.png" });
			//mc.renderEngine.bindTexture("/gui/background.png");
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var6 = 32.0F;
		var5.startDrawingQuads();
		var5.setColorRGBA_I(4210752, par4);
		var5.addVertexWithUV(0.0D, (double)par2, 0.0D, 0.0D, (double)((float)par2 / var6));
		var5.addVertexWithUV((double)width, (double)par2, 0.0D, (double)((float)width / var6), (double)((float)par2 / var6));
		var5.setColorRGBA_I(4210752, par3);
		var5.addVertexWithUV((double)width, (double)par1, 0.0D, (double)((float)width / var6), (double)((float)par1 / var6));
		var5.addVertexWithUV(0.0D, (double)par1, 0.0D, 0.0D, (double)((float)par1 / var6));
		var5.draw();
	}
}
