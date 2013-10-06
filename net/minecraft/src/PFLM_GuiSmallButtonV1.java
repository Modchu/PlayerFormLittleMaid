package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PFLM_GuiSmallButtonV1 extends GuiSmallButton {

	public PFLM_GuiSmallButtonMaster pflm_GuiSmallButtonMaster;

	public PFLM_GuiSmallButtonV1(int i, int x, int j, int k,
			int l, String s) {
		super(i, x, j, k, l, s);
		pflm_GuiSmallButtonMaster = new PFLM_GuiSmallButtonMaster(i, x, j, k, l, s);
	}

	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
	{
		return pflm_GuiSmallButtonMaster.mousePressed((Object) par1Minecraft, par2, par3);
	}

	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		super.drawButton(par1Minecraft, par2, par3);
		pflm_GuiSmallButtonMaster.drawButton((Object) par1Minecraft, par2, par3);
	}
}
