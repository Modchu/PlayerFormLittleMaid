package modchu.pflm;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_Reflect;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_ModelDataBase;

public class PFLM_PacketPlayerStateManager {

	public static void addSendList(byte by, ModchuModel_ModelDataBase modelData, Object entity) {
		addSendList(by, modelData, entity, (Object) null);
	}

	public static void addSendList(byte by, ModchuModel_ModelDataBase modelData, Object entity, Object... o) {
		if (ModchuModel_Main.isPFLMF) ;else {
			//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager addSendList !ModchuModel_Main.isPFLMF return");
			return;
		}
		LinkedList<Object[]> sendList = (LinkedList<Object[]>) Modchu_Reflect.getFieldObject("modchu.pflmf.PFLMF_Main", "sendList");
		if (sendList != null) {
			Object[] o1 = new Object[]{ by, modelData, entity, o };
			sendList.add(o1);
			//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager addSendList add.");
		} else {
			//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager addSendList sendList null !!");
		}
	}

	public static Object getPlayerStateObject(Object entityId, byte by) {
		if (ModchuModel_Main.isPFLMF) ;else return false;
		LinkedList list = getPlayerState(entityId, by);
		if (list != null) return Modchu_Reflect.invokeMethod("modchu.pflmf.PFLMF_Client", "receivePacket", new Class[]{ LinkedList.class, boolean.class }, new Object[]{ list, true });
		return null;
	}

	public static LinkedList getPlayerState(Object entityId, byte by) {
		if (ModchuModel_Main.isPFLMF) ;else return null;
		Object o = Modchu_Reflect.invokeMethod("modchu.pflmf.PFLMF_Main", "getPlayerState", new Class[]{ Object.class, byte.class }, null, new Object[]{ entityId, by });
		return o != null ? (LinkedList) o : null;
	}

}