package modchu.pflm;

import java.util.ArrayList;
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
		ArrayList<Object[]> sendList = (ArrayList<Object[]>) Modchu_Reflect.getFieldObject("modchu.pflmf.PFLMF_Main", "sendList");
		if (sendList != null) {
			Object[] o1 = new Object[]{ by, modelData, entity, o };
			sendList.add(o1);
			//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager addSendList add.");
		} else {
			//Modchu_Debug.mDebug("PFLM_PacketPlayerStateManager addSendList sendList null !!");
		}
	}

	public static Object getPlayerStateObject(int entityId, byte by) {
		if (ModchuModel_Main.isPFLMF) ;else return false;
		ConcurrentHashMap<Integer, Object> map = getPlayerState(entityId, by);
		if (map != null) return Modchu_Reflect.invokeMethod("modchu.pflmf.PFLMF_Client", "receivePacket", new Class[]{ ConcurrentHashMap.class, boolean.class }, new Object[]{ map, true });
		return null;
	}

	public static ConcurrentHashMap<Integer, Object> getPlayerState(int entityId, byte by) {
		if (ModchuModel_Main.isPFLMF) ;else return null;
		Object o = Modchu_Reflect.invokeMethod("modchu.pflmf.PFLMF_Main", "getPlayerState", new Class[]{ int.class, byte.class }, null, new Object[]{ entityId, by });
		return o != null ? (ConcurrentHashMap<Integer, Object>) o : null;
	}

}