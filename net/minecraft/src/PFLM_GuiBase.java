package net.minecraft.src;

public abstract class PFLM_GuiBase extends GuiScreen {

	protected World popWorld;
	protected EntityLiving drawEntity;
	public boolean guiMode;
	public PFLM_GuiBase parentScreen;

	public PFLM_GuiBase(PFLM_GuiBase par1GuiScreen, World world) {
		popWorld = world;
		parentScreen = par1GuiScreen;
		guiMode = true;
	}

	public void updateScreen() {
		try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		super.drawScreen(i, j, f);
		drawGuiContainerBackgroundLayer(f, i, j);
	}

	public void superDrawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
	}

	@Override
	public void onGuiClosed() {
		memoryRelease();
		guiMode = false;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return !mod_PFLM_PlayerFormLittleMaid.pflm_main.isMulti;
	}

	public abstract void memoryRelease();
	protected abstract void drawGuiContainerBackgroundLayer(float f, int i, int j);

}