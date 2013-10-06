package net.minecraft.src;

import org.lwjgl.input.Mouse;

public class PFLM_GuiSmallButtonMaster extends GuiSmallButton {

	public boolean buttonClick = false;

	public PFLM_GuiSmallButtonMaster(int i, int x, int j, int k,
			int l, String s) {
		super(i, x, j, k, l, s);
	}

	public boolean mousePressed(Object par1Minecraft, int par2, int par3)
    {
    	if (buttonClick) {
    		buttonClick = false;
    		return enabled && drawButton && par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
    	}
    	return false;
    }

    public void drawButton(Object par1Minecraft, int par2, int par3)
    {
    	if (!buttonClick
    			&& Mouse.isButtonDown(0)) buttonClick = true;
    }
}
