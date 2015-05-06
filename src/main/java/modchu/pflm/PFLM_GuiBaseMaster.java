package modchu.pflm;

import java.util.HashMap;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_GuiScreenMasterBasis;
import modchu.lib.Modchu_Main;

public class PFLM_GuiBaseMaster extends Modchu_GuiScreenMasterBasis {
	public Object parentScreen;

	public Object popWorld;
	public boolean guiMode;

	public PFLM_GuiBaseMaster(HashMap<String, Object> map) {
		super(map);
		init(map);
	}

	public void init(HashMap<String, Object> map) {
		popWorld = map.get("Object");
		parentScreen = map.containsKey("Object1") ? map.get("Object1") : null;
		guiMode = true;
	}

	protected Object newInstanceButton(int i, int i2, int i3, int i4, int i5, String s) {
		return Modchu_Main.newModchuCharacteristicObject("Modchu_GuiSmallButton", PFLM_GuiSmallButtonMaster.class, base, i, i2, i3, i4, i5, s);
	}

	protected Object newInstanceSlot(Object base, Object popWorld, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
		Modchu_Debug.mDebug("PFLM_GuiBaseMaster newInstanceSlot popWorld="+popWorld);
		return Modchu_Main.newModchuCharacteristicObject("Modchu_GuiSlot", PFLM_GuiOthersPlayerSlotMaster.class, popWorld, base, width, height, topIn, bottomIn, slotHeightIn);
	}

	@Override
	public Object getFontRenderer() {
		return getFontRenderer(base);
	}

	public Object getFontRenderer(Object renderer) {
		Object o = renderer != null ? Modchu_AS.get(Modchu_AS.guiScreenFontRenderer, renderer) : null;
		//Modchu_Debug.lDebug1("getFontRenderer o.getClass()="+(o != null ? o.getClass() : null));
		if (o != null) return o;
		//Modchu_Debug.lDebug1("getFontRenderer minecraftFontRenderer="+(Modchu_AS.get(Modchu_AS.minecraftFontRenderer)).getClass());
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
		//Modchu_Debug.lDebug1("fontRenderer="+fontRenderer);
		if (fontRenderer != null) Modchu_AS.set(Modchu_AS.fontRendererDrawString, fontRenderer, s, i, j, k);
	}

}
