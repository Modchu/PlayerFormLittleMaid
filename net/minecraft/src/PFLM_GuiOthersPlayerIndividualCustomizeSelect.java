package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
/*//FMLdelete
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
*///FMLdelete
import org.lwjgl.input.Mouse;

public class PFLM_GuiOthersPlayerIndividualCustomizeSelect extends GuiScreen {
	protected String screenTitle;
	protected GuiSlot selectPanel;
	private GuiButton localScroll;
	private World popWorld;
	private Minecraft mc;
	public static List<String> playerList = new ArrayList<String>();
	public static HashMap playerDummyEntityList = new HashMap();
	private GuiScreen parentScreen;

	public PFLM_GuiOthersPlayerIndividualCustomizeSelect(GuiScreen par1GuiScreen, Minecraft minecraft, World world) {
		mc = minecraft;
		popWorld = world;
		screenTitle = "OthersPlayerModelCustomize";
		playerListClear();
		setPlayerList();
		parentScreen = par1GuiScreen;
	}

	private void playerListClear() {
		playerList.clear();
		playerDummyEntityList.clear();
	}

	private void setPlayerList() {
		if (!mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.isRelease()
				&& !mod_PFLM_PlayerFormLittleMaid.isMulti) {
			String s;
			for (int i = 0 ; i < 2 ; i++) {
				s = "a"+i;
				playerList.add(s);
				playerDummyEntityList.put(s, new PFLM_EntityPlayerDummy(popWorld));
			}
			return;
		}
		Iterator var3 = popWorld.playerEntities.iterator();
		int i = 0;
		while (var3.hasNext())
		{
			try {
				EntityPlayer var1 = (EntityPlayer)popWorld.playerEntities.get(i);
				if (!var1.username.equalsIgnoreCase(mc.thePlayer.username)) {
					playerList.add(var1.username);
					playerDummyEntityList.put(var1.username, new PFLM_EntityPlayerDummy(popWorld));
				}
				i++;
			} catch (Exception e) {
				break;
			}
		}
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiButton(300, width / 2 - 60, height - 44, 120, 20, "Select"));
		buttonList.add(new GuiButton(0, width / 2 + 65, height - 44, 70, 20, "delete"));
		buttonList.add(new GuiButton(201, width / 2 - 135, height - 44, 70, 20, "Return"));

		selectPanel = new PFLM_GuiOthersPlayerSlot(mc, this, popWorld);
		selectPanel.registerScrollButtons(buttonList, 3, 4);

		localScroll = new GuiButton(3, 0, 0, "");
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(!guibutton.enabled) {
			return;
		}
		//delete
		if(guibutton.id == 0) {
			((PFLM_GuiOthersPlayerSlot) selectPanel).deletePlayerLocalData();
		}
		//Return
		if(guibutton.id == 201)
		{
			mc.displayGuiScreen(new PFLM_GuiOthersPlayer(this, popWorld));
		}
		//Select
		if(guibutton.id == 300) {
			((PFLM_GuiOthersPlayerSlot) selectPanel).openGuiCustomize();
			return;
		} else {
			selectPanel.actionPerformed(guibutton);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		selectPanel.drawScreen(i, j, f);
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
		super.drawScreen(i, j, f);
	}

	@Override
	public void handleMouseInput() {
		// ホイールスクロール用
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if(i != 0) {
			localScroll.id = i > 0 ? 3 : 4;
			selectPanel.actionPerformed(localScroll);
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}
}
