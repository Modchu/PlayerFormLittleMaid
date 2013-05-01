package net.minecraft.src;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.io.File;
import org.lwjgl.input.Keyboard;

public class PFLM_GuiSelectWorld extends GuiSelectWorld {

	private List saveList;
	private PFLM_GuiSelectWorld worldSlotContainer;

	public PFLM_GuiSelectWorld(GuiScreen par1GuiScreen) {
		super(((GuiSelectWorld) par1GuiScreen).parentScreen);
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		int t1 = getselectedWorld();
		try {
			if (!par1GuiButton.enabled) {
				return;
			}
			if (par1GuiButton.id == 2) {
				String s = getSaveName(t1);
				if (s != null) {
					mod_PFLM_PlayerFormLittleMaid.setPrivateValue(GuiSelectWorld.class, this, 10, true);
					StringTranslate stringtranslate = StringTranslate .getInstance();
					String s1 = stringtranslate .translateKey("selectWorld.deleteQuestion");
					String s2 = (new StringBuilder()).append("'").append(s).append("' ").append(stringtranslate.translateKey("selectWorld.deleteWarning")).toString();
					String s3 = stringtranslate.translateKey("selectWorld.deleteButton");
					String s4 = stringtranslate.translateKey("gui.cancel");
					GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, t1);
					mc.displayGuiScreen(guiyesno);
				}
			} else if (par1GuiButton.id == 1) {
				selectWorld(t1);
			} else if (par1GuiButton.id == 3) {
				if (mod_PFLM_PlayerFormLittleMaid.isnoBiomesX) {
					mod_PFLM_PlayerFormLittleMaid.isSwapGuiSelectWorld = false;
					mc.displayGuiScreen(new GuiSelectWorld(this));
					return;
				}
				if (mod_PFLM_PlayerFormLittleMaid.isPlayerAPI
						| !mod_PFLM_PlayerFormLittleMaid.isSwapGuiCreate) {
					mc.displayGuiScreen(new GuiCreateWorld(this));
				} else {
					Modchu_Debug.Debug("Swap GuiCreateWorld.");
					mc.displayGuiScreen(new PFLM_GuiCreateWorld(this));
				}
			} else if (par1GuiButton.id == 6) {
				mc.displayGuiScreen(new GuiRenameWorld(this,getSaveFileName(t1)));
			} else if (par1GuiButton.id == 0) {
				mc.displayGuiScreen(parentScreen);
			} else {
				((GuiWorldSlot) mod_PFLM_PlayerFormLittleMaid.getPrivateValue(GuiSelectWorld.class,this, 6)).actionPerformed(par1GuiButton);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mod_PFLM_PlayerFormLittleMaid.guiSelectWorldSwapCount = 0;
	}

	public void selectWorld(int par1) {
		// PlayerContller‚Ì’u‚«Š·‚¦
		mc.displayGuiScreen(null);
		try {
			if (((Boolean) ModLoader.getPrivateValue(GuiSelectWorld.class,
					this, 3)).booleanValue()) {
				return;
			}
			mod_PFLM_PlayerFormLittleMaid.setPrivateValue(GuiSelectWorld.class, this, 3,
					Boolean.TRUE);
		} catch (Exception e) {
			Modchu_Debug.Debug("(Boolean)getPrivateValue error.");
		}
		int j = 0;
		try {
			List list1 = (List) mod_PFLM_PlayerFormLittleMaid.getPrivateValue(GuiSelectWorld.class,
					this, 5);
			j = ((SaveFormatComparator) list1.get(par1)).getGameType();
		} catch (Exception exception) {
			Modchu_Debug.Debug("(List)getPrivateValue error.");
		}
		if (j == 0) {
			Modchu_Debug.Debug("Replace PFLM_PlayerController.");
			mc.playerController = new PFLM_PlayerController(mc);
		} else {
			Modchu_Debug.Debug("Replace PFLM_PlayerControllerCreative.");
			mc.playerController = new PFLM_PlayerControllerCreative(mc);
		}
		String s = getSaveFileName(par1);
		if (s == null) {
			s = (new StringBuilder()).append("World").append(par1).toString();
		}
		mc.startWorld(s, getSaveName(par1), null);
		mc.displayGuiScreen(null);
	}

    /**
     * loads the saves
     */
    private void loadSaves()
    {
        saveList = mc.getSaveLoader().getSaveList();
        Collections.sort(saveList);
        setselectedWorld(-1);
    }

	public int getselectedWorld() {
		int i = -1;
		try {
			i = (Integer) ModLoader.getPrivateValue(GuiSelectWorld.class, this,
					4);
		} catch (Exception e) {
			Modchu_Debug
					.Debug("getselectedWorld getPrivateValue error.[selectedWorld]");
		}
		return i;
	}

	public void setselectedWorld(int i) {
		try {
			ModLoader.setPrivateValue(GuiSelectWorld.class, this, 4, i);
		} catch (Exception e) {
			Modchu_Debug
					.Debug("setselectedWorld setPrivateValue error.[selectedWorld]");
		}
	}
}
