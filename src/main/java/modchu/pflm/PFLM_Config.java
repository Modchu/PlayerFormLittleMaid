package modchu.pflm;import java.io.BufferedReader;import java.io.BufferedWriter;import java.io.File;import java.io.FileReader;import java.io.FileWriter;import java.util.ArrayList;import java.util.LinkedList;import java.util.List;import java.util.concurrent.ConcurrentHashMap;import modchu.lib.Modchu_CastHelper;import modchu.lib.Modchu_Debug;import modchu.model.ModchuModel_Config;public class PFLM_Config extends ModchuModel_Config {	public static void saveParamater(File file, String[] k, String[] k1) {		// Gui設定項目をcfgファイルに保存		String textureName = null;		if (file.exists()				&& file.canRead()				&& file.canWrite()) {			List<String> lines = new LinkedList();			BufferedReader breader = null;			try {				breader = new BufferedReader(new FileReader(file));				String rl;				String s;				String s1;				boolean[] e = new boolean[k.length];				boolean ee = false;				StringBuilder sb = new StringBuilder();				while ((rl = breader.readLine()) != null) {					for (int i = 0; i < k.length ; i++) {						s = k[i];						if(!e[i]) {							if (rl.startsWith(s)) {								int i1 = rl.indexOf('=');								if (i1 > -1) {									if (s.length() == i1) {										sb.delete(0, sb.length());										sb.append(s).append("=")										.append(k1[i]);										lines.add(sb.toString());										e[i] = true;										//Modchu_Debug.mDebug("saveParamater true rl=" + rl);										break;									}								}							}						}					}				}				// 読み込めない項目があったかチェック、読み込めない項目があると作成しなおし				Boolean e1 = false;				for (int i = 0; i < k.length; i++) {					if (e[i] == false) {						e1 = true;						continue;					}				}				if (e1						| !ee) {					sb.delete(0, sb.length());					//Modchu_Debug.mDebug("cfg file save. e=" + l.toString());					for(int i = 0; i < k.length ; i++) {						if (!e[i]) {							s = k[i];							sb.append(s).append("=");							sb.append(k1[i]);							lines.add(sb.toString());							sb.delete(0, sb.length());						}					}					//Modchu_Debug.mDebug("cfg file save. e[4]=" + e[4]);				}			} catch (Exception er) {				Modchu_Debug.lDebug("PFLM_Config saveParamater file="+ file.toString(), 2, er);				er.printStackTrace();			} finally {				try {					if (breader != null) breader.close();				} catch (Exception e) {				}			}			BufferedWriter bwriter = null;			try {			// 保存				if (!lines.isEmpty()						&& (file.exists()								| file.createNewFile())						&& file.canWrite()) {					bwriter = new BufferedWriter(new FileWriter(file));					for (String t : lines) {						bwriter.write(t);						bwriter.newLine();					}					bwriter.close();				}			} catch (Exception er) {				Modchu_Debug.Debug("saveParamater file save fail.");				er.printStackTrace();			} finally {				try {					if (bwriter != null) bwriter.close();				} catch (Exception e) {				}			}		}	}	public static void writerModelList(String[] s, File file, List<String> list) {		//Listファイル書き込み		BufferedWriter bwriter = null;		try {			bwriter = new BufferedWriter(new FileWriter(file));			list.clear();			for (int i = 0; i < s.length ; i++)			{				//Modchu_Debug.mDebug("s[i]="+s[i]);				if (s[i] != null) {					bwriter.write(s[i]);					if (i != 0) list.add(s[i]);					bwriter.newLine();				}			}			//Modchu_Debug.Debug("file new file create.");		} catch (Exception e) {			Modchu_Debug.lDebug("PFLM_Config writerModelList", 2, e);			e.printStackTrace();		} finally {			try {				if (bwriter != null) bwriter.close();			} catch (Exception e) {			}		}	}	public static boolean loadList(File file, List<String> list,String listName) {		// ModelList読み込み		List<String> lines = new ArrayList<String>();		BufferedReader breader = null;		try {			breader = new BufferedReader(new FileReader(file));			String rl;			int i = 0;			while ((rl = breader.readLine()) != null) {				//Modchu_Debug.mDebug("rl="+rl);				if (i == 0) {					if (rl.startsWith("autoUpdates")) {						int k = rl.indexOf("=");						if (k > -1) {							if (Modchu_CastHelper.Boolean(rl.substring(k + 1))) {								breader.close();								return false;							}						} else {							breader.close();							return false;						}					} else {						breader.close();						return false;					}				} else {					list.add(rl);				}				i++;			}			//Modchu_Debug.mDebug("modelList "+listName+" load end.");		} catch (Exception e) {			Modchu_Debug.lDebug("PFLM_Config modelList file "+ listName +" load fail.", 2, e);			e.printStackTrace();			Modchu_Debug.Debug(" ");			return false;		} finally {			try {				if (breader != null) breader.close();			} catch (Exception e) {			}		}		return true;	}	public String getClassName(String s) {		if (s == null) return null;		return s;	}	public static void clearCfgData() {		getCfgData().clear();	}	public static void saveOthersPlayerParamater(String playerName, ConcurrentHashMap map, File file, String[] k, String[] k1, boolean flag) {		// GuiOthersPlayer設定項目をcfgファイルに保存		if (file.exists() && file.canRead() && file.canWrite()) {			List lines = new LinkedList();			BufferedReader breader = null;			try {				breader = new BufferedReader(new FileReader(file));				String rl;				String s;				String s1;				boolean[] e = new boolean[k.length];				boolean ee = false;				StringBuilder sb = new StringBuilder();				while ((rl = breader.readLine()) != null) {					for (int i = 0; i < k.length ; i++) {						s = k[i];						if(!e[i]) {							if (rl.startsWith(s)) {								int i1 = rl.indexOf('=');								if (i1 > -1) {									if (s.length() == i1) {										sb.delete(0, sb.length());										sb.append(s).append("=")										.append(k1[i]);										lines.add(sb.toString());										e[i] = true;										//Modchu_Debug.mDebug("saveOthersPlayerParamater true rl=" + rl);										break;									}								}							}						}					}				}				breader.close();				breader = new BufferedReader(new FileReader(file));				//OthersPlayer[]専用				while ((rl = breader.readLine()) != null) {					String m = (String) rl;					if (flag) {						//Modchu_Debug.mDebug("cfgOthersPlayer file save.0 rl=" + rl);						String t[] = (String[]) map.get(playerName);						s = "OthersPlayer[]";						if (rl.indexOf(s) != -1) {							rl = rl.substring(s.length());							int i1;							boolean flag1 = false;							if (rl.startsWith("[")									| rl.startsWith(".")) {								rl = rl.substring(1);								s = playerName;								if (rl.startsWith(s)) flag1 = true;							}							if (flag1) {								//Modchu_Debug.mDebug("cfgOthersPlayer file save.1 s=" + s +" rl="+rl);								sb.delete(0, sb.length());								//Modchu_Debug.mDebug("cfg file save.3 i1=" + i1);								StringBuilder sb1 = new StringBuilder();								sb1.append("OthersPlayer[]").append("[").append(s);								for(int i = 0; i < t.length ;i++) {									sb1.append("][").append(t[i]);								}								sb1.append("]");								lines.add(sb1.toString());								Modchu_Debug.mDebug("cfgOthersPlayer file save. " + s);								ee = true;								continue;							}						}					}					s = "OthersPlayer[]";					if (m.indexOf(s) != -1) {						//Modchu_Debug.mDebug("cfgOthersPlayer OthersPlayer[]add");						lines.add(m);						continue;					}				}				// 読み込めない項目があったかチェック、読み込めない項目があると作成しなおし				Boolean e1 = false;				for (int i = 0; i < k.length; i++) {					if (e[i] == false) {						e1 = true;						continue;					}				}				if (e1						| !ee) {					sb.delete(0, sb.length());					//Modchu_Debug.mDebug("cfgOthersPlayer file save. e=" + l.toString());					for(int i = 0; i < k.length ; i++) {						if (!e[i]) {							s = k[i];							sb.append(s).append("=");							sb.append(k1[i]);							lines.add(sb.toString());							sb.delete(0, sb.length());						}					}					if (!ee							&& flag) {						s = playerName;						String t[] = (String[]) map.get(playerName);						StringBuilder sb1 = new StringBuilder();						sb1.append("OthersPlayer[]").append("[").append(s).append("][").append(t[0]).append("][")						.append(t[1]).append("][").append(t[2]).append("][").append(t[3]).append("][").append(t[4]).append("]");						lines.add(sb1.toString());						sb.delete(0, sb.length());						Modchu_Debug.lDebug("saveOthersPlayerParamater file save. s=" + s);					}				}			} catch (Exception er) {				Modchu_Debug.lDebug("PFLM_Config saveOthersPlayerParamater file="+ file.toString(), 2, er);				er.printStackTrace();			} finally {				try {					if (breader != null) breader.close();				} catch (Exception e) {				}			}			BufferedWriter bwriter = null;			try {			// 保存				if (!lines.isEmpty()						&& (file.exists() || file.createNewFile())						&& file.canWrite()) {					bwriter = new BufferedWriter(new FileWriter(file));					String t;					for (int i = 0 ; i < lines.size() ; i++) {						t = (String) lines.get(i);						bwriter.write(t);						bwriter.newLine();					}					bwriter.close();				}			} catch (Exception er) {				Modchu_Debug.lDebug("saveOthersPlayerParamater file save fail.");				er.printStackTrace();			} finally {				try {					if (bwriter != null) bwriter.close();				} catch (Exception e) {				}			}		}	}	public static void loadConfigPlayerLocalData(ConcurrentHashMap map, File file) {		// GuiOthersPlayer設定項目PlayerLocalData読み込み		int modeOthersSettingOffline = getModeOthersSettingOffline();		BufferedReader breader = null;		try {			breader = new BufferedReader(new FileReader(file));			String rl;			for (int i = 0; (rl = breader.readLine()) != null && i < file.length(); i++) {				int i1;				if (rl.startsWith("#")						| rl.startsWith("/")) continue;				String s = "OthersPlayer[]";				if (rl.startsWith(s)) {					rl = rl.substring(s.length());					if (rl.startsWith("[")							| rl.startsWith(".")) {						String k1 = null;						String t[] = new String[6];						if (rl.startsWith("[")) {							for(int j = 0; rl.indexOf("[") != -1 ; j++) {								rl = rl.substring(1);								i1 = rl.indexOf("]");								if (j == 0) k1 = rl.substring(0, i1);								else t[j - 1] = rl.substring(0, i1);								rl = rl.substring(i1 + 1);							}						} else {							i1 = rl.indexOf('.');							if (i1 > -1) {								rl = rl.substring(i1 + 1);								if (rl.indexOf(".") > -1) {									//Player Name									k1 = rl.substring(0, rl.indexOf("."));									rl = rl.substring(rl.indexOf(".") + 1);									if (rl.indexOf(".") > -1) {										//TextureName										t[0] = rl.substring(0, rl.indexOf("."));										rl = rl.substring(rl.indexOf(".") + 1);										if (rl.indexOf(".") > -1) {											//ArmorName											t[1] = rl.substring(0, rl.indexOf("."));											rl = rl.substring(rl.indexOf(".") + 1);											if (rl.indexOf(".") > -1) {												//MaidColor												t[2] = rl.substring(0, rl.indexOf("."));												if (rl.indexOf("[") > -1														&& rl.indexOf("]") > -1) {													//ModelScale													t[3] = rl.substring(rl.indexOf("[") + 1 , rl.indexOf("]"));													//changeMode													t[4] = ""+modeOthersSettingOffline;													rl = rl.substring(0, rl.indexOf("."));													i1 = rl.indexOf('.');													if (i1 > -1) {														t[4] = rl.substring(rl.indexOf(".") + 1, rl.length());													}												}											}										}									}								}							}							t[5] = "0";						}						if (k1 != null) {							map.put(k1, t);							//Modchu_Debug.mDebug("cfgOthersPlayer load "+k1+" t[0]="+t[0]+" t[1]="+t[1]+" t[2]="+t[2]+" t[3]="+t[3]+" t[4]="+t[4]+" t[5]="+t[5]);						}					}				}			}			Modchu_Debug.mDebug("PFLM_Config loadConfigOthersPlayer");		} catch (Exception e) {			Modchu_Debug.lDebug("PFLM_Config loadConfigShowModel "+ file.toString() +" load fail.", 2, e);			e.printStackTrace();		} finally {			try {				if (breader != null) breader.close();			} catch (Exception e) {			}		}		//Modchu_Debug.mDebug("PFLM_Config loadConfigOthersPlayer");	}	public static void removeOthersPlayerParamater(File file, String name) {		// GuiOthersPlayer設定から指定内容削除		if (file.exists() && file.canRead() && file.canWrite()) {			List lines = new LinkedList();			BufferedReader breader = null;			try {				breader = new BufferedReader(new FileReader(file));				String rl;				String s;				String s1;				StringBuilder sb = new StringBuilder();				while ((rl = breader.readLine()) != null) {					String m = (String) rl;					//Modchu_Debug.mDebug("removeOthersPlayerParamater 0 rl=" + rl);					s = "OthersPlayer[]";					if (rl.indexOf(s) != -1) {						rl = rl.substring(rl.indexOf('.') + 1);						s = name;						if (rl.startsWith(s)) {							sb.delete(0, sb.length());							Modchu_Debug.mDebug("cfgOthersPlayer file remove. " + s);							continue;						}					}					lines.add(m);					continue;				}				breader.close();			} catch (Exception er) {				Modchu_Debug.lDebug("PFLM_Config removeOthersPlayerParamater", 2, er);				er.printStackTrace();			} finally {				try {					if (breader != null) breader.close();				} catch (Exception e) {				}			}			BufferedWriter bwriter = null;			try {			// 保存				if (!lines.isEmpty()						&& (file.exists() || file.createNewFile())						&& file.canWrite()) {					bwriter = new BufferedWriter(new FileWriter(file));					String t;					for (int i = 0 ; i < lines.size() ; i++) {						t = (String) lines.get(i);						bwriter.write(t);						bwriter.newLine();					}				}			} catch (Exception er) {				Modchu_Debug.lDebug("PFLM_Config removeOthersPlayerParamater file="+ file.toString(), 2, er);				er.printStackTrace();			} finally {				try {					if (bwriter != null) bwriter.close();				} catch (Exception e) {				}			}		}	}	private static String getTextureName() {		return PFLM_ConfigData.textureName;	}	private static int getMaidColor() {		return PFLM_ConfigData.maidColor;	}	private static int getModeOthersSettingOffline() {		return ((PFLM_ModelDataMaster) PFLM_ModelDataMaster.instance).skinMode_offline;	}}