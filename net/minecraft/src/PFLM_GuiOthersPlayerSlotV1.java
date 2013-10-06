package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PFLM_GuiOthersPlayerSlotV1 extends GuiSlot {
	public PFLM_GuiOthersPlayerSlotMaster pflm_GuiOthersPlayerSlotMaster;

	public PFLM_GuiOthersPlayerSlotV1(Minecraft par1Minecraft,
			int par2, int par3, int par4, int par5, int par6) {
		super(par1Minecraft, par2, par3, par4, par5, par6);
		pflm_GuiOthersPlayerSlotMaster = new PFLM_GuiOthersPlayerSlotMaster(null, par2, par3, par4, par5, par6);
	}

	public PFLM_GuiOthersPlayerSlotV1(
			PFLM_GuiOthersPlayerIndividualCustomizeSelect gui,
			World world) {
		super((Minecraft) mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), gui.width, gui.height, 32, gui.height - 52, 36);
		pflm_GuiOthersPlayerSlotMaster = new PFLM_GuiOthersPlayerSlotMaster(gui, world);
	}

	@Override
	protected int getSize() {
		return pflm_GuiOthersPlayerSlotMaster.getSize();
	}

	@Override
	protected int getContentHeight()
	{
		return pflm_GuiOthersPlayerSlotMaster.getContentHeight();
	}

	@Override
	protected void elementClicked(int i, boolean flag) {
		pflm_GuiOthersPlayerSlotMaster.elementClicked(i, flag);
	}

	public void openGuiCustomize() {
		pflm_GuiOthersPlayerSlotMaster.openGuiCustomize();
	}

	@Override
	protected boolean isSelected(int i) {
		return pflm_GuiOthersPlayerSlotMaster.isSelected(i);
	}

	@Override
	protected void drawBackground() {
		pflm_GuiOthersPlayerSlotMaster.drawBackground();
	}

	@Override
	public void drawScreen(int i, int j, float f)
	{
		super.drawScreen(i, j, f);
		pflm_GuiOthersPlayerSlotMaster.drawScreen(i, j, f);
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		pflm_GuiOthersPlayerSlotMaster.drawSlot(i, j, k, l, tessellator);
	}

	public void deletePlayerLocalData() {
		pflm_GuiOthersPlayerSlotMaster.deletePlayerLocalData();
	}
}
