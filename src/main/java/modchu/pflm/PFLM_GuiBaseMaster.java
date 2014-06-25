package modchu.pflm;

import modchu.lib.Modchu_IGuiBaseMaster;
import modchu.lib.Modchu_Reflect;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_GuiPlayerSlot;
import modchu.lib.characteristic.recompileonly.Modchu_GuiBase;
import modchu.lib.characteristic.recompileonly.Modchu_GuiSmallButton;
import modchu.model.ModchuModel_IModelCapsConstant;

public class PFLM_GuiBaseMaster implements Modchu_IGuiBaseMaster {
	public Object parentScreen;
	public Modchu_GuiBase base;

	public Object popWorld;
	public boolean guiMode;

	public PFLM_GuiBaseMaster(Modchu_GuiBase guiBase, Object par1GuiScreen, Object world, Object... o) {
		init(guiBase, par1GuiScreen, world, (Object[])o);
	}

	@Override
	public void init(Modchu_GuiBase guiBase, Object par1GuiScreen, Object world, Object... o) {
		parentScreen = par1GuiScreen;
		base = guiBase;
		popWorld = world;
		guiMode = true;
	}

	@Override
	public void reInit() {
	}

	@Override
	public void initGui() {
	}

	protected Object newInstanceButton(int i, int i2, int i3, int i4, int i5, String s) {
		return new Modchu_GuiSmallButton(PFLM_GuiSmallButtonMaster.class, base, i, i2, i3, i4, i5, s);
	}

	protected Object newInstanceSlot(Modchu_GuiBase base, Object popWorld) {
		return new Modchu_GuiPlayerSlot(PFLM_GuiOthersPlayerSlotMaster.class, base, popWorld);
	}

	@Override
	public Object getFontRenderer() {
		return getFontRenderer(base);
	}

	public Object getFontRenderer(Object renderer) {
		Object o = renderer != null ? Modchu_AS.get(Modchu_AS.guiScreenFontRenderer, renderer) : null;
		if (o != null) return o;
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
	public void mouseMovedOrUp(int mouseX, int mouseY, int clickButton) {
	}

}
