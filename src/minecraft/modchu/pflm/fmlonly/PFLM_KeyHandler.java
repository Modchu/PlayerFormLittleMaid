package modchu.pflm.fmlonly;

import java.util.EnumSet;

import modchu.lib.Modchu_Reflect;
import modchu.pflm.PFLM_Main;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class PFLM_KeyHandler extends KeyHandler {

	public PFLM_KeyHandler(KeyBinding[] arg0, boolean[] repeatings) {
		super(arg0, repeatings);
	}

	@Override
	public String getLabel() {
		return (String) Modchu_Reflect.invokeMethod(PFLM_Main.mod_pflm_playerformlittlemaid.getClass(), "getLabel", PFLM_Main.mod_pflm_playerformlittlemaid);
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		//Modchu_Debug.mDebug("PFLM_KeyHandler keyDown");
		Modchu_Reflect.invokeMethod(PFLM_Main.mod_pflm_playerformlittlemaid.getClass(), "keyboardEvent", new Class[]{ KeyBinding.class }, PFLM_Main.mod_pflm_playerformlittlemaid, new Object[]{ kb });
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {

	}

	@Override
	public EnumSet<TickType> ticks() {
		return (EnumSet<TickType>) Modchu_Reflect.invokeMethod(PFLM_Main.mod_pflm_playerformlittlemaid.getClass(), "ticks", PFLM_Main.mod_pflm_playerformlittlemaid);
	}

}
