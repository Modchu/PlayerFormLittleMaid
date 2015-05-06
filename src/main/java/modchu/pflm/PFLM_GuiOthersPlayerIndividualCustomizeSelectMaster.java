package modchu.pflm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;

import org.lwjgl.input.Mouse;

public class PFLM_GuiOthersPlayerIndividualCustomizeSelectMaster extends PFLM_GuiBaseMaster {
	public String screenTitle;
	public Object selectPanel;
	private Object localScroll;
	public static List<String> playerList;

	public PFLM_GuiOthersPlayerIndividualCustomizeSelectMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void init(HashMap<String, Object> map) {
		super.init(map);
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
		Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftThePlayer);
		String thePlayerUsername = Modchu_AS.getString(Modchu_AS.userName, thePlayer);
		for (Object entityplayer : playerEntities) {
			String username = Modchu_AS.getString(Modchu_AS.userName, entityplayer);
			//Modchu_Debug.mDebug("setPlayerList username="+username);
			if (!username.equalsIgnoreCase(thePlayerUsername)) playerList.add(username);
		}
		if ((!Modchu_Main.isRelease()
				| Modchu_Debug.mDebug)
				&& playerList.isEmpty()) {
			String s;
			for (int i1 = 0 ; i1 < 2 ; i1++) {
				s = "a"+i1;
				playerList.add(s);
			}
			//return;
		}
	}

	@Override
	public void initGui() {
		List buttonList = Modchu_AS.getList(Modchu_AS.guiScreenButtonList, base);
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		int height = Modchu_AS.getInt(Modchu_AS.guiScreenHeight, base);
		buttonList.clear();
		buttonList.add(newInstanceButton(300, width / 2 - 60, height - 44, 120, 20, "Select"));
		buttonList.add(newInstanceButton(0, width / 2 + 65, height - 44, 70, 20, "delete"));
		buttonList.add(newInstanceButton(201, width / 2 - 135, height - 44, 70, 20, "Return"));

		selectPanel = newInstanceSlot(base, popWorld, width, height, 32, height - 64, 36);
		Modchu_AS.set(Modchu_AS.guiSlotRegisterScrollButtons, selectPanel, null, 3, 4);
		localScroll = newInstanceButton(3, 0, 0, 0, 0, "");
		Modchu_AS.set(Modchu_AS.guiScreenButtonList, base, buttonList);
	}

	@Override
	public void actionPerformed(Object guibutton) {
		if (!Modchu_AS.getBoolean(Modchu_AS.guiButtonEnabled, guibutton)) {
			return;
		}
		int id = Modchu_AS.getInt(Modchu_AS.guiButtonID, guibutton);

		//delete
		if (id == 0) {
			Object master = Modchu_Main.getModchuCharacteristicObjectMaster(selectPanel);
			Modchu_Reflect.invokeMethod(master.getClass(), "deletePlayerLocalData", master);
		}
		//Return
		if (id == 201) {
			Modchu_AS.set(Modchu_AS.minecraftDisplayGuiScreen, Modchu_Main.newModchuCharacteristicObject("Modchu_GuiModelView", PFLM_GuiOthersPlayerMaster.class, popWorld, base));
		}
		//Select
		if (id == 300) {
			Object master = Modchu_Main.getModchuCharacteristicObjectMaster(selectPanel);
			Modchu_Reflect.invokeMethod(master.getClass(), "openGuiCustomize", master);
			return;
		}

		Modchu_AS.set(Modchu_AS.guiSlotActionPerformed, selectPanel, guibutton);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		base.superDrawDefaultBackground();
		super.drawScreen(i, j, f);
		Modchu_AS.set(Modchu_AS.guiSlotDrawScreen, selectPanel, i, j, f);
		int width = Modchu_AS.getInt(Modchu_AS.guiScreenWidth, base);
		Modchu_AS.set(Modchu_AS.guiDrawCenteredString, base, getFontRenderer(), screenTitle, width / 2, 20, 0xffffff);
		base.superDrawScreen(i, j, f );
	}

	@Override
	public void handleMouseInput() {
		// ホイールスクロール用
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if(i != 0) {
			Modchu_AS.set(Modchu_AS.guiButtonID, localScroll, i > 0 ? 3 : 4);
			Modchu_AS.set(Modchu_AS.guiSlotActionPerformed, selectPanel, localScroll);
		}
		if (Modchu_Main.getMinecraftVersion() > 179) Modchu_AS.set("GuiSlot", "handleMouseInput", selectPanel, (Class[])null);
	}

	public void memoryRelease() {
	}

}