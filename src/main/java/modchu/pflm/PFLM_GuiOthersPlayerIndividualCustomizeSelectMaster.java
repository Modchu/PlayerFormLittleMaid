package modchu.pflm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_GuiBase;
import modchu.lib.characteristic.Modchu_GuiModelView;

import org.lwjgl.input.Mouse;

public class PFLM_GuiOthersPlayerIndividualCustomizeSelectMaster extends PFLM_GuiBaseMaster {
	public String screenTitle;
	public Object selectPanel;
	private Object localScroll;
	public static List<String> playerList;

	public PFLM_GuiOthersPlayerIndividualCustomizeSelectMaster(Modchu_GuiBase guiBase, Object par1GuiScreen, Object world, Object... o) {
		super(guiBase, par1GuiScreen, world, (Object[])o);
	}

	@Override
	public void init(Modchu_GuiBase guiBase, Object par1GuiScreen, Object world, Object... o) {
		super.init(guiBase, par1GuiScreen, world, o);
		othersPlayerIndividualCustomizeSelectMasterInit();
	}

	@Override
	public void reInit() {
		super.reInit();
		othersPlayerIndividualCustomizeSelectMasterInit();
	}

	private void othersPlayerIndividualCustomizeSelectMasterInit() {
		screenTitle = "OthersPlayerModelCustomize";
		if (playerList != null) {
			playerListClear();
		} else {
			playerList = new ArrayList<String>();
		}
		setPlayerList();
	}

	private void playerListClear() {
		playerList.clear();
	}

	private void setPlayerList() {
		List playerEntities = Modchu_AS.getList(Modchu_AS.worldPlayerEntities, popWorld);
		Iterator iterator = playerEntities.iterator();
		int i = 0;
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
		String thePlayerUsername = Modchu_AS.getString(Modchu_AS.userName, thePlayer);
		while (iterator.hasNext()) {
			try {
				Object entityplayer = playerEntities.get(i);
				//Modchu_Debug.mDebug("setPlayerList var1.username="+var1.username);
				String username = Modchu_AS.getString(Modchu_AS.userName, entityplayer);
				if (!username.equalsIgnoreCase(thePlayerUsername)) {
					playerList.add(username);
				}
				i++;
			} catch (Exception e) {
				break;
			}
		}
		if (!Modchu_Main.isRelease()
				&& playerList.isEmpty()) {
			String s;
			for (int i1 = 0 ; i1 < 2 ; i1++) {
				s = "a"+i1;
				playerList.add(s);
			}
			//return;
		}
	}

	public void initGui() {
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		buttonList.clear();
		buttonList.add(newInstanceButton(300, width / 2 - 60, height - 44, 120, 20, "Select"));
		buttonList.add(newInstanceButton(0, width / 2 + 65, height - 44, 70, 20, "delete"));
		buttonList.add(newInstanceButton(201, width / 2 - 135, height - 44, 70, 20, "Return"));

		selectPanel = newInstanceSlot(base, popWorld);
		Modchu_AS.set(Modchu_AS.guiSlotRegisterScrollButtons, selectPanel, null, 3, 4);
		localScroll = newInstanceButton(3, 0, 0, 0, 0, "");
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
	}

	@Override
	public void actionPerformed(Object guibutton) {
		if (!Modchu_AS.getBoolean(Modchu_AS.guiButtonEnabled, guibutton)) {
			return;
		}
		//delete
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);
		if(id == 0) {
			Modchu_Reflect.invokeMethod(selectPanel.getClass(), "deletePlayerLocalData", selectPanel);
		}
		//Return
		if(id == 201) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, new Modchu_GuiModelView(PFLM_GuiOthersPlayerMaster.class, base, popWorld));
		}
		//Select
		if(id == 300) {
			Modchu_Reflect.invokeMethod(selectPanel.getClass(), "openGuiCustomize", selectPanel);
			return;
		} else {
			Modchu_AS.set(Modchu_AS.guiSlotActionPerformed, selectPanel, guibutton);
		}
	}

	@Override
	public boolean drawScreen(int i, int j, float f) {
		base.superDrawDefaultBackground();
		super.drawScreen(i, j, f);
		Modchu_AS.set(Modchu_AS.guiSlotDrawScreen, selectPanel, i, j, f);
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		Modchu_AS.set(Modchu_AS.guiDrawCenteredString, base, getFontRenderer(), screenTitle, width / 2, 20, 0xffffff);
		base.superDrawScreen(i, j, f);
		return false;
	}

	@Override
	public boolean handleMouseInput() {
		// ホイールスクロール用
		int i = Mouse.getEventDWheel();
		if(i != 0) {
			Modchu_AS.set(Modchu_AS.guiButtonID, localScroll, i > 0 ? 3 : 4);
			Modchu_AS.set(Modchu_AS.guiScreenActionPerformed, selectPanel, localScroll);
		}
		return true;
	}

	public void memoryRelease() {
	}

}