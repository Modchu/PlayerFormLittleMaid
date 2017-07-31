package modchu.pflm;import java.util.HashMap;import modchu.lib.Modchu_AS;import modchu.lib.Modchu_Debug;import modchu.lib.Modchu_Main;import modchu.lib.Modchu_PlayerControllerMasterBasis;import modchu.lib.Modchu_Reflect;public class PFLM_PlayerControllerMaster extends Modchu_PlayerControllerMasterBasis {	public static Object pflm_entityPlayersp;	private Object netClientHandler;	public PFLM_PlayerControllerMaster(HashMap<String, Object> map) {		super(map);		PFLM_ConfigData.guiSelectWorldSwapCount = 0;		netClientHandler = map.get("Object1");	}	@Override	public Object createPlayer(Object world, Object statisticsManager, Object recipeBook) {		// EntityClientPlayerMPの置き換え PlayerAPI未使用		if (!PFLM_Main.isPlayerAPI				| PFLM_ConfigData.isPlayerAPIDebug) {			Modchu_Debug.lDebug("Replace PFLM_EntityPlayerSP.");			Object thePlayer = Modchu_AS.get(Modchu_AS.minecraftPlayer);			if (Modchu_Main.isOlddays) {				pflm_entityPlayersp = Modchu_Main.newModchuCharacteristicObject("Modchu_EntityPlayerSP2", Modchu_Reflect.loadClass("modchu.pflm.PFLM_EntityPlayerSP2"), Modchu_AS.get(Modchu_AS.minecraftGetMinecraft), world, Modchu_Reflect.invokeMethod("Minecraft", "func_110432_I", "getSession", Modchu_AS.get(Modchu_AS.minecraftGetMinecraft)), Modchu_AS.getInt(Modchu_AS.entityDimension, thePlayer));			} else {				pflm_entityPlayersp = Modchu_Main.newModchuCharacteristicObject("Modchu_EntityPlayerSP", PFLM_EntityPlayerMaster.class, Modchu_AS.get(Modchu_AS.minecraftGetMinecraft), world, Modchu_AS.get(Modchu_AS.minecraftSession), netClientHandler);			}			return pflm_entityPlayersp;		} else {			// EntityClientPlayerMPの置き換え PlayerAPI用			if (PFLM_Main.isPlayerAPI) {				Modchu_Debug.Debug("Replace PFLM_EntityPlayer.");				pflm_entityPlayersp = Modchu_Main.newModchuCharacteristicObject("Modchu_EntityPlayer", PFLM_EntityPlayerMaster.class, Modchu_AS.get(Modchu_AS.minecraftGetMinecraft), world, Modchu_AS.get(Modchu_AS.minecraftSession), netClientHandler);				return pflm_entityPlayersp;			} else {				//Modchu_Debug.Debug("createPlayer.");			}		}		return null;	}}