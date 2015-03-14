package modchu.pflm;

import java.util.HashMap;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_IGuiBase;
import modchu.lib.Modchu_IGuiBaseMaster;
import modchu.lib.Modchu_Main;

public class PFLM_GuiBaseMaster implements Modchu_IGuiBaseMaster {
	public Object parentScreen;
	public Modchu_IGuiBase base;

	public Object popWorld;
	public boolean guiMode;

	public PFLM_GuiBaseMaster(HashMap<String, Object> map) {
		init(map);
	}

	public void init(HashMap<String, Object> map) {
		base = map.containsKey("base") ? (Modchu_IGuiBase) map.get("base") : null;
		popWorld = map.get("Object");
		parentScreen = map.containsKey("Object1") ? map.get("Object1") : null;
		guiMode = true;
	}

	@Override
	public void reInit() {
	}

	@Override
	public void initGui() {
	}

	protected Object newInstanceButton(int i, int i2, int i3, int i4, int i5, String s) {
		return Modchu_Main.newModchuCharacteristicObject("Modchu_GuiSmallButton", PFLM_GuiSmallButtonMaster.class, base, i, i2, i3, i4, i5, s);
	}

	protected Object newInstanceSlot(Object base, Object popWorld, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
		return Modchu_Main.newModchuCharacteristicObject("Modchu_GuiSlot", PFLM_GuiOthersPlayerSlotMaster.class, popWorld, base, width, height, topIn, bottomIn, slotHeightIn);
	}

	@Override
	public Object getFontRenderer() {
		return getFontRenderer(base);
	}

	public Object getFontRenderer(Object renderer) {
		Object o = renderer != null ? Modchu_AS.get(Modchu_AS.guiScreenFontRenderer, renderer) : null;
		Modchu_Debug.lDebug1("getFontRenderer o.getClass()="+(o != null ? o.getClass() : null));
		if (o != null) return o;
		Modchu_Debug.lDebug1("getFontRenderer minecraftFontRenderer="+(Modchu_AS.get(Modchu_AS.minecraftFontRenderer)).getClass());
		return Modchu_AS.get(Modchu_AS.minecraftFontRenderer);
	}

	@Override
	public void setFontRenderer(Object fontRenderer) {
		Modchu_AS.set(Modchu_AS.guiScreenFontRenderer, base, fontRenderer);
	}

	@Override
	public void updateScreen() {
		try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void actionPerformed(Object guibutton) {
	}

	@Override
	public boolean drawScreen(int i, int j, float f) {
		return false;
	}

	@Override
	public boolean handleMouseInput() {
		return true;
	}

	@Override
	public boolean mouseClicked(int x, int y, int i) {
		return true;
	}

	@Override
	public void onGuiClosed() {
		guiMode = false;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return !PFLM_Main.isMulti;
	}

	@Override
	public void drawString(String s, int i, int j, int k) {
		drawString(base, s, i, j, k);
	}

	public void drawString(Object renderer, String s, int i, int j, int k) {
		Object fontRenderer = getFontRenderer(renderer);
		Modchu_Debug.lDebug1("fontRenderer="+fontRenderer);
		if (fontRenderer != null) Modchu_AS.set(Modchu_AS.fontRendererDrawString, fontRenderer, s, i, j, k);
	}

	@Override
	public boolean keyTyped(char c, int i) {
		return true;
	}

	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickButton, long time) {
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int clickButton) {
	}

	@Override
	public void mouseMovedOrUp(int mouseX, int mouseY, int clickButton) {
	}

}
