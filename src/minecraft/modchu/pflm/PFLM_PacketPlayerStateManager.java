package modchu.pflm;

import java.util.LinkedList;

import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_Reflect;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_ModelDataBase;
import net.minecraft.entity.Entity;

public class PFLM_PacketPlayerStateManager {

	public static void addSendList(int i, ModchuModel_ModelDataBase modelData, Entity entity) {
		addSendList(i, modelData, entity, (Object) null);
	}

	public static void addSendList(int i, ModchuModel_ModelDataBase modelData, Entity entity, Object... o) {
		if (ModchuModel_Main.isPFLMF) ;else return;
		LinkedList<Object[]> sendList = (LinkedList<Object[]>) Modchu_Reflect.getFieldObject("modchu.pflmf.PFLMF", "sendList");
		if (sendList != null) {
			Object[] o1 = new Object[]{ i, modelData, entity, o };
			sendList.add(o1);
			Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager addSendList add.");
		}
	}

	public static boolean getPlayerStateBoolean(int entityId, byte by) {
		Object o = getPlayerState(entityId, by);
		if (o != null) {
			boolean b = false;
			if (o instanceof Object[]) {
				Object[] o1 = (Object[]) o;
				if (o1[0] instanceof Boolean) b = (Boolean) o1[0];
				//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager getPlayerStateBoolean b="+b);
			} else {
				if (o instanceof Boolean) b = (Boolean) o;
				//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager getPlayerStateBoolean else b="+b);
			}
			return b;
		}
		return false;
	}

	public static int getPlayerStateInt(int entityId, byte by) {
		Object o = getPlayerState(entityId, by);
		int i = -1;
		if (o != null) {
			if (o instanceof Object[]) {
				Object[] o1 = (Object[]) o;
				if (o1[0] instanceof Integer) i = Integer.valueOf(""+o1[1]);
				//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager getPlayerStateInt i="+i);
			} else {
				if (o instanceof Integer) i = Integer.valueOf(""+o);
				//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager getPlayerStateInt else i="+i);
			}
		}
		return i;
	}

	public static Object getPlayerState(int entityId, byte by) {
		if (ModchuModel_Main.isPFLMF) ;
		else return null;
		return Modchu_Reflect.invokeMethod("modchu.pflmf.PFLMF", "getPlayerState", new Class[]{ int.class, byte.class }, null, new Object[]{ entityId, by });
	}

}