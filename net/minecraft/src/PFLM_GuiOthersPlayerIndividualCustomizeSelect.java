package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Mouse;

public class PFLM_GuiOthersPlayerIndividualCustomizeSelect extends PFLM_GuiBase {
	protected String screenTitle;
	protected GuiSlot selectPanel;
	private GuiButton localScroll;
	public static List<String> playerList = new ArrayList<String>();

	public PFLM_GuiOthersPlayerIndividualCustomizeSelect(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		screenTitle = "OthersPlayerModelCustomize";
		playerListClear();
		setPlayerList();
	}

	private void playerListClear() {
		playerList.clear();
	}

	private void setPlayerList() {
		if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.isRelease()
				&& !mod_PFLM_PlayerFormLittleMaid.pflm_main.isMulti) {
			String s;
			for (int i = 0 ; i < 2 ; i++) {
				s = "a"+i;
				playerList.add(s);
			}
			return;
		}
		EntityPlayer thePlayer = mod_Modchu_ModchuLib.modchu_Main.getThePlayer();
		Iterator var3 = popWorld.playerEntities.iterator();
		int i = 0;
		while (var3.hasNext())
		{
			try {
				EntityPlayer var1 = (EntityPlayer)popWorld.playerEntities.get(i);
				//Modchu_Debug.mDebug("setPlayerList var1.username="+var1.username);
				if (!var1.username.equalsIgnoreCase(thePlayer.username)) {
					playerList.add(var1.username);
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

		selectPanel = (GuiSlot) Modchu_Reflect.newInstance("PFLM_GuiOthersPlayerSlot", new Class[]{ PFLM_GuiOthersPlayerIndividualCustomizeSelect.class, World.class }, new Object[]{ this, popWorld });
		Class[] c;
		Object[] o;
		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
			c = new Class[]{ int.class, int.class };
			o = new Object[]{ 3, 4 };
			//selectPanel.registerScrollButtons(3, 4);
		} else {
			c = new Class[]{ List.class, int.class, int.class };
			o = new Object[]{ null, 3, 4 };
			//selectPanel.registerScrollButtons(null, 3, 4);
		}
		Modchu_Reflect.invokeMethod(GuiSlot.class, "func_77220_a", "registerScrollButtons", c, selectPanel, o);
		localScroll = new GuiButton(3, 0, 0, "");
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(!guibutton.enabled) {
			return;
		}
		//delete
		if(guibutton.id == 0) {
			Modchu_Reflect.invokeMethod("PFLM_GuiOthersPlayerSlot", "deletePlayerLocalData", selectPanel);
			//((PFLM_GuiOthersPlayerSlotV160) selectPanel).deletePlayerLocalData();
		}
		//Return
		if(guibutton.id == 201)
		{
			Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ new PFLM_GuiOthersPlayer(this, popWorld) });
			//mc.displayGuiScreen(new PFLM_GuiOthersPlayer(this, popWorld));
		}
		//Select
		if(guibutton.id == 300) {
			Modchu_Reflect.invokeMethod("PFLM_GuiOthersPlayerSlot", "openGuiCustomize", selectPanel);
			//((PFLM_GuiOthersPlayerSlotV160) selectPanel).openGuiCustomize();
			return;
		} else {
			selectPanel.actionPerformed(guibutton);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		selectPanel.drawScreen(i, j, f);
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
		superDrawScreen(i, j, f);
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
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
	}

	@Override
	public void memoryRelease() {
	}
}
