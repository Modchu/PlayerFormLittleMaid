package net.minecraft.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class PFLM_Config extends Modchu_Config {

	public static HashMap<String, HashMap> configPartsMap = new HashMap();
	public static HashMap<String, HashMap> configShowPartsNemeMap = new HashMap();
	public static HashMap<String, HashMap> configModelRendererMap = new HashMap();
	public static HashMap<String, HashMap> configShowPartsHideMap= new HashMap();
	public static HashMap<String, HashMap> configShowPartsRenemeMap = new HashMap();
	public static HashMap<String, HashMap> configDefaultShowPartsMap = new HashMap();
	public static HashMap<String, HashMap> configIndexOfAllSetVisibleMap = new HashMap();
	public static HashMap<String, HashMap> configIndexOfAllSetVisibleBooleanMap = new HashMap();
	public static boolean loadShowModelListInitFlag = false;
	private static String getConfigShowPartsHideMapFlagString;
	private static String getConfigShowPartsRenemeMapFlagString;


	public static void saveParamater(File file, String[] k, String[] k1) {
		// Gui設定項目をcfgファイルに保存
		String textureName = null;
		int maidColor = getMaidColor();
		boolean partsSaveFlag = getPartsSaveFlag();
		if (file.exists() && file.canRead() && file.canWrite()) {
			List lines = new LinkedList();
			try {
				BufferedReader breader = new BufferedReader(new FileReader(file));
				String rl;
				String s;
				String s1;
				boolean[] e = new boolean[k.length];
				boolean ee = false;
				StringBuilder sb = new StringBuilder();
				while ((rl = breader.readLine()) != null) {
					if (partsSaveFlag) {
						for (int i = 0; i < k.length ; i++) {
							s = k[i];
							if(!e[i]) {
								if (rl.startsWith(s)) {
									int i1 = rl.indexOf('=');
									if (i1 > -1) {
										if (s.length() == i1) {
											sb.delete(0, sb.length());
											sb.append(s).append("=")
											.append(k1[i]);
											lines.add(sb.toString());
											e[i] = true;
											//Modchu_Debug.mDebug("saveParamater true rl=" + rl);
											break;
										}
									}
								}
							}
						}
					} else {
						s = "showModel[]";
						if (rl.indexOf(s) > -1) {
							lines.add(rl);
							continue;
						}
					}
				}
				breader.close();
				// 読み込めない項目があったかチェック、読み込めない項目があると作成しなおし
				Boolean e1 = false;
				for (int i = 0; i < k.length; i++) {
					if (e[i] == false) {
						e1 = true;
						continue;
					}
				}
				if (e1
						| !ee) {
					sb.delete(0, sb.length());
					//Modchu_Debug.mDebug("cfg file save. e=" + l.toString());
					for(int i = 0; i < k.length ; i++) {
						if (!e[i]) {
							s = k[i];
							sb.append(s).append("=");
							sb.append(k1[i]);
							lines.add(sb.toString());
							sb.delete(0, sb.length());
						}
					}
					//Modchu_Debug.mDebug("cfg file save. e[4]=" + e[4]);
				}
				//showModel[]専用
				if (partsSaveFlag) {
					boolean b;
					HashMap<String, Boolean> map1 = null;
					Iterator<Entry<String, HashMap>> iterator = configPartsMap.entrySet().iterator();
					Iterator<Entry<String, Boolean>> iterator2;
					Entry<String, HashMap> entry;
					Entry<String, Boolean> entry2;
					s = "showModel[],";
					Modchu_Debug.mDebug("saveParamater showModel[] partsSaveFlag configPartsMap != null ? "+(configPartsMap != null));
					String s2 = null;
					int i1 = 0;
					while(iterator.hasNext()) {
						entry = iterator.next();
						s2 = entry.getKey();
						map1 = entry.getValue();
						if (map1 != null
								&& !map1.isEmpty()) ;else continue;
						sb.delete(0, sb.length());
						sb.append(s).append(s2);
						iterator2 = map1.entrySet().iterator();
						Modchu_Debug.mDebug("saveParamater showModel[] partsSaveFlag s2="+s2);
						while(iterator2.hasNext()) {
							entry2 = iterator2.next();
							sb.append(",");
							s2 = entry2.getKey();
							b = entry2.getValue();
							sb.append("[").append(s2).append("]").append(b);
							Modchu_Debug.mDebug("saveParamater showModel[] partsSaveFlag s2="+s2+" b="+b);
						}
						Modchu_Debug.mDebug("saveParamater showModel[] partsSaveFlag lines.add="+sb.toString());
						lines.add(sb.toString());
					}
					if (failureShowModelList != null
							&& !failureShowModelList.isEmpty()) {
						for(int i = 0; i < failureShowModelList.size(); i++) {
							Modchu_Debug.mDebug("failureShowModelList.get("+i+") = "+failureShowModelList.get(i));
							lines.add(failureShowModelList.get(i));
						}
					}
				}
			} catch (Exception er) {
				Modchu_Debug.Debug("saveParamater file error.");
				er.printStackTrace();
			}
			try {
			// 保存
				if (!lines.isEmpty()
						&& (file.exists() || file.createNewFile())
						&& file.canWrite()) {
					BufferedWriter bwriter = new BufferedWriter(
							new FileWriter(file));
					String t;
					for (int i = 0 ; i < lines.size() ; i++) {
						t = (String) lines.get(i);
						bwriter.write(t);
						bwriter.newLine();
					}
					bwriter.close();
				}
			} catch (Exception er) {
				Modchu_Debug.Debug("saveParamater file save fail.");
				er.printStackTrace();
			}
		}
		loadShowModelListInitFlag = false;
		failureShowModelList.clear();
	}

	public static void loadShowModelList(List<String> list) {
		if (loadShowModelListInitFlag) return;
		loadShowModelListInitFlag = true;
		String textureName = null;
		int maidColor = getMaidColor();
		HashMap<Integer, Boolean>[] maps;
		try {
			//Modchu_Debug.mDebug("loadShowModelList showModelList="+showModelList);
			if(!list.isEmpty()) {
				String s = null;
				String s1 = null;
				boolean b = false;
				int color = 0;
				int type = 0;
				int count = 0;
				int i1 = 0;
				int i2 = 0;
				int i3 = 0;
				HashMap<String, Boolean> booleanMap;
				HashMap<Integer, String> configShowPartsNemeMap;
				HashMap<Integer, String> configShowPartsHideMap;
				Class multiModelCustom = null;
				//Modchu_Debug.mDebug("loadShowModelList");
				for (String rl : list.toArray(new String[0])) {
					//Modchu_Debug.mDebug("loadShowModelList rl="+rl);
					if(rl != null) {
						s = "showModel[]";
						i1 = rl.indexOf(s);
						if (i1 < 0) continue;
						booleanMap = new HashMap();
						color = 0;
						type = 0;
						boolean breakFlag = false;
						//Modchu_Debug.mDebug("loadShowModelList i1="+i1);
						if (i1 == 0) {
							i1 = rl.indexOf(',');
							if (i1 < 0) {
								if (!failureShowModelList.contains(rl)) failureShowModelList.add(rl);
								Modchu_Debug.mDebug("failureShowModelList.add rl="+rl);
								continue;
							}
							rl = rl.substring(i1 + 1);
							count = 0;
							i1 = rl.indexOf(',');
							if (i1 < 0) {
								if (!failureShowModelList.contains(rl)) failureShowModelList.add(rl);
								Modchu_Debug.mDebug("failureShowModelList.add rl="+rl);
								continue;
							}
							textureName = rl.substring(0, i1);
							//Modchu_Debug.mDebug("loadShowModelList textureName="+textureName);
							while(rl != null) {
								rl = rl.substring(i1 + 1);
								while(rl != null) {
									i1 = rl.indexOf(',');
									if (i1 < 0) {
										breakFlag = true;
										s = rl;
									} else {
										s = rl.substring(0, i1);
										rl = rl.substring(i1 + 1);
									}
									//Modchu_Debug.mDebug("loadShowModelList s="+s);
									switch(count) {
									case 0:
										color = Integer.valueOf(s);
										break;
									case 1:
										type = Integer.valueOf(s);
										break;
									default:
										if (s.equalsIgnoreCase("null")) break;
										i2 = s.indexOf('[');
										i3 = s.indexOf(']');
										if (i2 < 0
												| i3 < 0) break;
										s1 = s.substring(i2 + 1, i3);
										i2 = s.indexOf(']');
										s = s.substring(i2 + 1);
										b = Boolean.valueOf(s);
										//Modchu_Debug.mDebug("loadShowModelList s1="+s1+" b="+b);
										booleanMap.put(s1, b);
									}
									count++;
									if (breakFlag) break;
								}
								if (i1 < 0) break;
							}
							setConfigShowPartsMap(textureName, color, type, booleanMap);
						} else {
							//旧式showModel[] load
							Modchu_Debug.mDebug("loadShowModelList 旧式 rl="+rl);
							s = ".showModel[]";
							i1 = rl.indexOf(s);
							if (i1 > -1) {
								addFailureShowModelList(rl);
							}
							continue;
/*
							s1 = rl.substring(0, i1);
							i1 = s1.lastIndexOf('.');
							if (i1 < 0) {
								addFailureShowModelList(rl);
								continue;
							}
							textureName = rl.substring(0, i1);
							if (mod_Modchu_ModchuLib.integerCheck(s1.substring(i1 + 1))) i1 = Integer.parseInt(s1.substring(i1 + 1));
							else {
								addFailureShowModelList(rl);
								continue;
							}
							color = i1;
							if (textureName == null) {
								addFailureShowModelList(rl);
								continue;
							}
							Modchu_Debug.mDebug("loadShowModelList textureName="+textureName);
							Object[] textureModel = modelNewInstance(null, textureName);
							if (textureModel != null
									&& textureModel[0] != null) {
								if (multiModelCustom != null) ;else multiModelCustom = Modchu_Reflect.loadClass(mod_Modchu_ModchuLib.mod_modchu_modchulib.getClassName("MultiModelCustom"));
								if (multiModelCustom != null
										&& multiModelCustom.isInstance(textureModel[0])) {
									Object o = Modchu_Reflect.getFieldObject(multiModelCustom, "customModel", textureModel[0]);
									if (o != null) textureModel[0] = Modchu_Reflect.getFieldObject(o.getClass(), "mainModel", o);
								}
							} else {
								Modchu_Debug.mDebug("loadShowModelList textureModel == null !!");
								addFailureShowModelList(rl);
								continue;
							}
							if (textureModel[0] instanceof MMM_ModelBiped) {
								if (((MMM_ModelBiped) textureModel[0]).modelCaps != null) ;else {
									RenderLiving render = (RenderLiving) RenderManager.instance.getEntityRenderObject(Minecraft.getMinecraft().thePlayer);
									((MMM_ModelBiped) textureModel[0]).modelCaps = (MMM_IModelCaps) Modchu_Reflect.newInstance(mod_Modchu_ModchuLib.mod_modchu_modchulib.getClassName("PFLM_ModelData"), new Class[]{ RenderLiving.class }, new Object[]{ render });
								}
							}
							configShowPartsNemeMap = getConfigShowPartsNemeMap(textureName, 0);
							configShowPartsHideMap = getConfigShowPartsHideMap(textureModel[0], textureName, 0);
							//Modchu_Debug.mDebug("loadShowModelList color="+color);
							i1 = rl.indexOf('=');
							if (i1 < 0) {
								addFailureShowModelList(rl);
								continue;
							}
							rl = rl.substring(i1 + 1);
							b = false;
							for(int i4 = 0; rl != null; i4++) {
								i1 = rl.indexOf(',');
								s = i1 != -1 ? rl.substring(0, i1): rl;
								if (s.equalsIgnoreCase("null")) break;
								b = Boolean.parseBoolean(s);
								if (configShowPartsNemeMap != null
										&& configShowPartsNemeMap.containsKey(i4)) {
									while(configShowPartsNemeMap.containsKey(i4)) {
										s1 = configShowPartsNemeMap.get(i4);
										if (configShowPartsHideMap.containsValue(s1)) {
											i4++;
										} else break;
									}
									booleanMap.put(s1, b);
									Modchu_Debug.mDebug("loadShowModelList booleanMap s1="+s1+" b="+b);
								}
								rl = i1 != -1 ? rl.substring(i1 + 1): null;
							}
							setConfigShowPartsMap(textureName, color, type, booleanMap);
*/
						}
					}
				}
			}
		} catch (Exception ee) {
			Modchu_Debug.Debug("loadShowModelList fail.");
			ee.printStackTrace();
		}
	}

	private static void addFailureShowModelList(String s) {
		if (!failureShowModelList.contains(s)) failureShowModelList.add(s);
		Modchu_Debug.mDebug("failureShowModelList.add s="+s);
	}

	public static void writerModelList(String[] s, File file, List<String> list) {
		//Listファイル書き込み
		try {
			BufferedWriter bwriter = new BufferedWriter(new FileWriter(file));
			list.clear();
			for (int i = 0; i < s.length ; i++)
			{
				//Modchu_Debug.mDebug("s[i]="+s[i]);
				if (s[i] != null) {
					bwriter.write(s[i]);
					if (i != 0) list.add(s[i]);
					bwriter.newLine();
				}
			}
			bwriter.close();
			//Modchu_Debug.Debug("file new file create.");
		} catch (Exception e) {
			Modchu_Debug.Debug("file writer fail.");
			e.printStackTrace();
			Modchu_Debug.Debug(" ");
		}
	}

	public static boolean loadList(File file, List<String> list,String listName) {
		// ModelList読み込み
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader breader = new BufferedReader(new FileReader(
					file));
			String rl;
			int i = 0;
			while ((rl = breader.readLine()) != null) {
				//Modchu_Debug.mDebug("rl="+rl);
				if (i == 0) {
					if (rl.startsWith("autoUpdates")) {
						int k = rl.indexOf("=");
						if (k > -1) {
							if (Boolean.valueOf(rl.substring(k + 1))) {
								breader.close();
								return false;
							}
						} else {
							breader.close();
							return false;
						}
					} else {
						breader.close();
						return false;
					}
				} else {
					list.add(rl);
				}
				i++;
			}
			breader.close();
			//Modchu_Debug.mDebug("modelList "+listName+" load end.");
		} catch (Exception e) {
			Modchu_Debug.Debug("modelList file "+listName+" load fail.");
			e.printStackTrace();
			Modchu_Debug.Debug(" ");
			return false;
		}
		return true;
	}

	public String getClassName(String s) {
		if (s == null) return null;
		return mod_Modchu_ModchuLib.mod_modchu_modchulib.getPackage()+s;
	}

	public static void clearCfgData() {
		cfgData.clear();
	}

	public static void saveOthersPlayerParamater(String playerName, HashMap map, File file, String[] k, String[] k1, boolean flag) {
		// GuiOthersPlayer設定項目をcfgファイルに保存
		if (file.exists() && file.canRead() && file.canWrite()) {
			List lines = new LinkedList();
			try {
				BufferedReader breader = new BufferedReader(new FileReader(
						file));
				String rl;
				String s;
				String s1;
				boolean[] e = new boolean[k.length];
				boolean ee = false;
				StringBuilder sb = new StringBuilder();
				while ((rl = breader.readLine()) != null) {
					for (int i = 0; i < k.length ; i++) {
						s = k[i];
						if(!e[i]) {
							if (rl.startsWith(s)) {
								int i1 = rl.indexOf('=');
								if (i1 > -1) {
									if (s.length() == i1) {
										sb.delete(0, sb.length());
										sb.append(s).append("=")
										.append(k1[i]);
										lines.add(sb.toString());
										e[i] = true;
										//Modchu_Debug.mDebug("saveOthersPlayerParamater true rl=" + rl);
										break;
									}
								}
							}
						}
					}
				}
				breader.close();
				breader = new BufferedReader(new FileReader(file));
				//OthersPlayer[]専用
				while ((rl = breader.readLine()) != null) {
					String m = (String) rl;
					if (flag) {
						//Modchu_Debug.mDebug("cfgOthersPlayer file save.0 rl=" + rl);
						String t[] = (String[]) map.get(playerName);
						s = "OthersPlayer[]";
						if (rl.indexOf(s) != -1) {
							rl = rl.substring(s.length());
							int i1;
							boolean flag1 = false;
							if (rl.startsWith("[")
									| rl.startsWith(".")) {
								rl = rl.substring(1);
								s = playerName;
								if (rl.startsWith(s)) flag1 = true;
							}
							if (flag1) {
								//Modchu_Debug.mDebug("cfgOthersPlayer file save.1 s=" + s +" rl="+rl);
								sb.delete(0, sb.length());
								//Modchu_Debug.mDebug("cfg file save.3 i1=" + i1);
								StringBuilder sb1 = new StringBuilder();
								sb1.append("OthersPlayer[]").append("[").append(s);
								for(int i = 0; i < t.length ;i++) {
									sb1.append("][").append(t[i]);
								}
								sb1.append("]");
								lines.add(sb1.toString());
								Modchu_Debug.mDebug("cfgOthersPlayer file save. " + s);
								ee = true;
								continue;
							}
						}
					}
					s = "OthersPlayer[]";
					if (m.indexOf(s) != -1) {
						//Modchu_Debug.mDebug("cfgOthersPlayer OthersPlayer[]add");
						lines.add(m);
						continue;
					}
				}
				// 読み込めない項目があったかチェック、読み込めない項目があると作成しなおし
				Boolean e1 = false;
				for (int i = 0; i < k.length; i++) {
					if (e[i] == false) {
						e1 = true;
						continue;
					}
				}
				if (e1
						| !ee) {
					sb.delete(0, sb.length());
					//Modchu_Debug.mDebug("cfgOthersPlayer file save. e=" + l.toString());
					for(int i = 0; i < k.length ; i++) {
						if (!e[i]) {
							s = k[i];
							sb.append(s).append("=");
							sb.append(k1[i]);
							lines.add(sb.toString());
							sb.delete(0, sb.length());
						}
					}
					if (!ee
							&& flag) {
						s = playerName;
						String t[] = (String[]) map.get(playerName);
						StringBuilder sb1 = new StringBuilder();
						sb1.append("OthersPlayer[]").append("[").append(s).append("][").append(t[0]).append("][")
						.append(t[1]).append("][").append(t[2]).append("][").append(t[3]).append("][").append(t[4]).append("]");
						lines.add(sb1.toString());
						sb.delete(0, sb.length());
						Modchu_Debug.mDebug("saveOthersPlayerParamater file save. s=" + s);
					}
				}
			} catch (Exception er) {
				Modchu_Debug.Debug("saveOthersPlayerParamater file error.");
				er.printStackTrace();
			}
			try {
			// 保存
				if (!lines.isEmpty()
						&& (file.exists() || file.createNewFile())
						&& file.canWrite()) {
					BufferedWriter bwriter = new BufferedWriter(
							new FileWriter(file));
					String t;
					for (int i = 0 ; i < lines.size() ; i++) {
						t = (String) lines.get(i);
						bwriter.write(t);
						bwriter.newLine();
					}
					bwriter.close();
				}
			} catch (Exception er) {
				Modchu_Debug.Debug("saveOthersPlayerParamater file save fail.");
				er.printStackTrace();
			}
		}
	}

	public static void loadConfigPlayerLocalData(HashMap map, File file) {
		// GuiOthersPlayer設定項目PlayerLocalData読み込み
		int modeOthersSettingOffline = getModeOthersSettingOffline();
		try {
			BufferedReader breader = new BufferedReader(new FileReader(
					file));
			String rl;
			for (int i = 0; (rl = breader.readLine()) != null && i < file.length(); i++) {
				int i1;
				if (rl.startsWith("#")
						| rl.startsWith("/")) continue;
				String s = "OthersPlayer[]";
				if (rl.startsWith(s)) {
					rl = rl.substring(s.length());
					if (rl.startsWith("[")
							| rl.startsWith(".")) {
						String k1 = null;
						String t[] = new String[6];
						if (rl.startsWith("[")) {
							for(int j = 0; rl.indexOf("[") != -1 ; j++) {
								rl = rl.substring(1);
								i1 = rl.indexOf("]");
								if (j == 0) k1 = rl.substring(0, i1);
								else t[j - 1] = rl.substring(0, i1);
								rl = rl.substring(i1 + 1);
							}
						} else {
							i1 = rl.indexOf('.');
							if (i1 > -1) {
								rl = rl.substring(i1 + 1);
								if (rl.indexOf(".") > -1) {
									//Player Name
									k1 = rl.substring(0, rl.indexOf("."));
									rl = rl.substring(rl.indexOf(".") + 1);
									if (rl.indexOf(".") > -1) {
										//TextureName
										t[0] = rl.substring(0, rl.indexOf("."));
										rl = rl.substring(rl.indexOf(".") + 1);
										if (rl.indexOf(".") > -1) {
											//ArmorName
											t[1] = rl.substring(0, rl.indexOf("."));
											rl = rl.substring(rl.indexOf(".") + 1);
											if (rl.indexOf(".") > -1) {
												//MaidColor
												t[2] = rl.substring(0, rl.indexOf("."));
												if (rl.indexOf("[") > -1
														&& rl.indexOf("]") > -1) {
													//ModelScale
													t[3] = rl.substring(rl.indexOf("[") + 1 , rl.indexOf("]"));
													//changeMode
													t[4] = ""+modeOthersSettingOffline;
													rl = rl.substring(0, rl.indexOf("."));
													i1 = rl.indexOf('.');
													if (i1 > -1) {
														t[4] = rl.substring(rl.indexOf(".") + 1, rl.length());
													}
												}
											}
										}
									}
								}
							}
							t[5] = "0";
						}
						if (k1 != null) {
							map.put(k1, t);
							//Modchu_Debug.mDebug("cfgOthersPlayer load "+k1+" t[0]="+t[0]+" t[1]="+t[1]+" t[2]="+t[2]+" t[3]="+t[3]+" t[4]="+t[4]+" t[5]="+t[5]);
						}
					}
				}
			}
			breader.close();
			Modchu_Debug.mDebug("Modchu_Config loadConfigOthersPlayer");
		} catch (Exception e) {
			Modchu_Debug.Debug("Modchu_Config loadConfigShowModel load fail.");
			e.printStackTrace();
		}
		//Modchu_Debug.mDebug("Modchu_Config loadConfigOthersPlayer");
	}

	public static void removeOthersPlayerParamater(File file, String name) {
		// GuiOthersPlayer設定から指定内容削除
		if (file.exists() && file.canRead() && file.canWrite()) {
			List lines = new LinkedList();
			try {
				BufferedReader breader = new BufferedReader(new FileReader(
						file));
				String rl;
				String s;
				String s1;
				StringBuilder sb = new StringBuilder();
				while ((rl = breader.readLine()) != null) {
					String m = (String) rl;
					//Modchu_Debug.mDebug("removeOthersPlayerParamater 0 rl=" + rl);
					s = "OthersPlayer[]";
					if (rl.indexOf(s) != -1) {
						rl = rl.substring(rl.indexOf('.') + 1);
						s = name;
						if (rl.startsWith(s)) {
							sb.delete(0, sb.length());
							Modchu_Debug.mDebug("cfgOthersPlayer file remove. " + s);
							continue;
						}
					}
					lines.add(m);
					continue;
				}
				breader.close();
			} catch (Exception er) {
				Modchu_Debug.Debug("removeOthersPlayerParamater error.");
				er.printStackTrace();
			}
			try {
			// 保存
				if (!lines.isEmpty()
						&& (file.exists() || file.createNewFile())
						&& file.canWrite()) {
					BufferedWriter bwriter = new BufferedWriter(
							new FileWriter(file));
					String t;
					for (int i = 0 ; i < lines.size() ; i++) {
						t = (String) lines.get(i);
						bwriter.write(t);
						bwriter.newLine();
					}
					bwriter.close();
				}
			} catch (Exception er) {
				Modchu_Debug.Debug("removeOthersPlayerParamater file save fail.");
				er.printStackTrace();
			}
		}
	}

	public static HashMap<String, HashMap> getPartsMap() {
		return configPartsMap;
	}

	public static HashMap<String, Boolean> getConfigShowPartsMap(String s, int i, int i2) {
		if (configPartsMap != null) return configPartsMap.get(new StringBuilder().append(s).append(",").append(i).append(",").append(i2).toString());
		//Modchu_Debug.mDebug("getConfigShowPartsMap return null");
		return null;
	}

	public static int getConfigShowPartsMapBoolean(String s, String s1, int i, int i2) {
		if (configPartsMap != null) {
			HashMap<String, Boolean> map = configPartsMap.get(new StringBuilder().append(s).append(",").append(i).append(",").append(i2).toString());
			if (map != null
					&& map.containsKey(s1)) return map.get(s1) ? 1 : 0;
		}
		//Modchu_Debug.mDebug("getConfigShowPartsMapBoolean return false");
		return -1;
	}

	public static void setConfigShowPartsMap(String s, int i, int i2, HashMap<String, Boolean> map) {
		//Modchu_Debug.mDebug("setConfigShowPartsMap s="+s+" i="+i+" i2="+i2+" map="+(map != null));
		String s1 = new StringBuilder().append(s).append(",").append(i).append(",").append(i2).toString();
		if (map != null
				&& !map.isEmpty()) configPartsMap.put(s1, map);
		else if (configPartsMap.containsKey(s1)) configPartsMap.remove(s1);
		//Modchu_Debug.mDebug("setConfigShowPartsMap s="+s+" i="+i+" i2="+i2+" map.size()="+map.size());
	}

	public static HashMap<String, HashMap> getConfigShowPartsNemeMap() {
		return configShowPartsNemeMap;
	}

	public static HashMap<Integer, String> getConfigShowPartsNemeMap(String s, int i) {
		if (s != null) ;else return null;
		//Modchu_Debug.mDebug("getConfigShowPartsNemeMap s="+s+" i="+i);
		String s1 = new StringBuilder().append(s).append(",").append(i).toString();
		HashMap<Integer, String> map = null;
		if (configShowPartsNemeMap != null
				&& configShowPartsNemeMap.containsKey(s1)) {
			//Modchu_Debug.mDebug("getConfigShowPartsNemeMap containsKey ok. configShowPartsNemeMap.get(s1)="+configShowPartsNemeMap.get(s1));
			return configShowPartsNemeMap.get(s1);
		}
		Object[] textureModel = mod_Modchu_ModchuLib.modelNewInstance(null, s, true, false);
		if (textureModel != null) {
			int i1 = 0;
			switch(i) {
			case 0:
				i1 = 0;
				break;
			case 1:
				i1 = 1;
				break;
			case 2:
				i1 = 2;
				break;
			}
			Object model = textureModel[i1];
			Class multiModelCustom = Modchu_Reflect.loadClass(mod_Modchu_ModchuLib.mod_modchu_modchulib.getClassName("MultiModelCustom"));
			if (multiModelCustom != null
					&& multiModelCustom.isInstance(model)) model = Modchu_Reflect.getFieldObject(multiModelCustom, "customModel", model);
			if (model != null) {
				showPartsSetting(model, s, i);
				if (configShowPartsNemeMap != null
						&& configShowPartsNemeMap.containsKey(s1)) {
					//Modchu_Debug.mDebug("getConfigShowPartsNemeMap containsKey ok2. configShowPartsNemeMap.get(s1)="+configShowPartsNemeMap.get(s1));
					return configShowPartsNemeMap.get(s1);
				}
			}
		}
		//Modchu_Debug.mDebug("getConfigShowPartsNemeMap return null");
		return null;
	}

	private static void showPartsSetting(Object model, String s, int i) {
		HashMap<Integer, String> map = new HashMap();
		HashMap<String, Field> modelRendererMap1 = new HashMap();
		Field[] fields = model.getClass().getFields();
		String s1;
		int k = 0;
		for (int i1 = 0; i1 < fields.length; i1++) {
			//Modchu_Debug.mDebug("showPartsSetting fields["+i1+"].getType() = "+fields[i1].getType());
			Object o;
			try {
				o = fields[i1].get(model);
				if (MMM_ModelRenderer.class.isInstance(o)) {
					//Modchu_Debug.mDebug("Modchu_Config showPartsSetting MMM_ModelRenderer.class.isInstance fields["+i1+"].getType() = "+fields[i1].getType());
					try {
						s1 = fields[i1].getName();
						map.put(k, s1);
						modelRendererMap1.put(s1, fields[i1]);
						//Modchu_Debug.mDebug("Modchu_Config showPartsSetting put s1="+s1+" fields["+i1+"].getType() = "+fields[i1].getType());
					} catch (Exception e) {
					}
					k++;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		setConfigShowPartsNemeMap(s, i, map);
		setConfigModelRendererMap(s, i, modelRendererMap1);
	}

	public static void setConfigShowPartsNemeMap(String s, int i, HashMap<Integer, String> map) {
		String s3 = new StringBuilder().append(s).append(",").append(i).toString();
		configShowPartsNemeMap.put(s3, map);
	}

	public static HashMap<String, Field> getConfigModelRendererMap(Object model, String s, int i) {
		String s3 = new StringBuilder().append(s).append(",").append(i).toString();
		if (configModelRendererMap != null
				&& configModelRendererMap.containsKey(s3)) return configModelRendererMap.get(s3);
		showPartsSetting(model, s, i);
		if (configModelRendererMap != null
				&& configModelRendererMap.containsKey(s3)) return configModelRendererMap.get(s3);
		Modchu_Debug.mDebug("getConfigModelRendererMap return null");
		return null;
	}

	public static void setConfigModelRendererMap(String s, int i, HashMap<String, Field> map) {
		String s3 = new StringBuilder().append(s).append(",").append(i).toString();
		configModelRendererMap.put(s3, map);
	}

	public static HashMap<String, String> getConfigShowPartsRenemeMap(Object model, String s, int i) {
		String s3 = new StringBuilder().append(s).append(",").append(i).toString();
		if (configShowPartsRenemeMap != null
				&& configShowPartsRenemeMap.containsKey(s3)) {
			getConfigShowPartsRenemeMapFlagString = null;
			return configShowPartsRenemeMap.get(s3);
		}
		HashMap<String, String> renemeMap = null;
		boolean flag = getConfigShowPartsRenemeMapFlagString != null ? false : true;
		if (flag
				| (getConfigShowPartsRenemeMapFlagString != null
				&& !getConfigShowPartsRenemeMapFlagString.equals(s))) {
			if (model instanceof MultiModelBaseBiped) {
				PFLM_ModelData data = mod_PFLM_PlayerFormLittleMaid.pflm_renderPlayer.getPlayerData(Minecraft.getMinecraft().thePlayer);
				((MultiModelBaseBiped) model).defaultPartsSettingBefore(data);
				renemeMap = (HashMap<String, String>) data.getCapsValue(((MultiModelBaseBiped) model).caps_showPartsRenemeMap);
				if (renemeMap != null) {
					setConfigShowPartsRenemeMap(model, s, i, renemeMap);
					getConfigShowPartsRenemeMapFlagString = null;
					return renemeMap;
				}
			}
			getConfigShowPartsRenemeMapFlagString = s;
		}
		renemeMap = new HashMap();
		setConfigShowPartsRenemeMap(model, s, i, renemeMap);
		return renemeMap;
	}

	public static void addConfigShowPartsRenemeMap(Object model, String s, int i, String[] s1, String[] s2) {
		String s3 = new StringBuilder().append(s).append(",").append(i).toString();
		HashMap<String, String> renemeMap = null;
		if (configShowPartsRenemeMap != null
				&& configShowPartsRenemeMap.containsKey(s3)) renemeMap = configShowPartsRenemeMap.get(s3);
		if (renemeMap != null) ;else renemeMap = new HashMap();
		for(int i1 = 0; i1 < s1.length && i1 < s2.length; i1++) {
			renemeMap.put(s1[i1], s2[i1]);
		}
		setConfigShowPartsRenemeMap(model, s, i, renemeMap);
	}

	public static void setConfigShowPartsRenemeMap(Object model, String s, int i, HashMap<String, String> map) {
		String s3 = new StringBuilder().append(s).append(",").append(i).toString();
		configShowPartsRenemeMap.put(s3, map);
	}

	public static HashMap<Integer, String> getConfigShowPartsHideMap(Object model, String s, int i) {
		//Modchu_Debug.mDebug("getConfigShowPartsHideMap s="+s);
		if (model != null) ;else {
			//Modchu_Debug.mDebug("getConfigShowPartsHideMap model == null !!");
			return null;
		}
		String s3 = new StringBuilder().append(s).append(",").append(i).toString();
		HashMap<Integer, String> hideMap = null;
		if (configShowPartsHideMap != null
				&& configShowPartsHideMap.containsKey(s3)) {
			hideMap = configShowPartsHideMap.get(s3);
			if (!hideMap.isEmpty()) {
				//Modchu_Debug.mDebug("getConfigShowPartsHideMap containsKey ok. s="+s+" hideMap="+configShowPartsHideMap.get(s3));
				getConfigShowPartsHideMapFlagString = null;
				return hideMap;
			}
		}
		boolean flag = getConfigShowPartsHideMapFlagString != null ? false : true;
		if (flag
				| (getConfigShowPartsHideMapFlagString != null
				&& !getConfigShowPartsHideMapFlagString.equals(s3))) {
			Modchu_Debug.mDebug("getConfigShowPartsHideMap flag通過.");
			if (model instanceof MultiModelBaseBiped) {
				Modchu_Debug.mDebug("getConfigShowPartsHideMap MultiModelBaseBiped ok.");
				PFLM_ModelData data = mod_PFLM_PlayerFormLittleMaid.pflm_renderPlayer.getPlayerData(Minecraft.getMinecraft().thePlayer);
				((MultiModelBaseBiped) model).defaultPartsSettingBefore(data);
				List<String> hideList = null;
				if (data != null) hideList = (List<String>) data.getCapsValue(((MultiModelBaseBiped) model).caps_showPartsHideList);
				if (hideList != null) {
					hideMap = new HashMap();
					for(int i1 = 0; i1 < hideList.size(); i1++) {
						hideMap.put(i1, hideList.get(i1));
					}
					setConfigShowPartsHideMap(model, s, i, hideMap);
					getConfigShowPartsHideMapFlagString = null;
					Modchu_Debug.mDebug("getConfigShowPartsHideMap s="+s+" hideMap="+hideMap);
					return hideMap;
				} else {
					Modchu_Debug.mDebug("getConfigShowPartsHideMap hideList == null !!");
				}
			} else {
				Modchu_Debug.mDebug("getConfigShowPartsHideMap MultiModelBaseBiped false!!");
			}
			getConfigShowPartsHideMapFlagString = s3;
		}
		Modchu_Debug.mDebug("getConfigShowPartsHideMap null. s="+s+" flag="+flag+" getConfigShowPartsHideMapFlagString ="+getConfigShowPartsHideMapFlagString+" getConfigShowPartsHideMapFlagString.equals(s) ?"+(getConfigShowPartsHideMapFlagString.equals(s)));
		hideMap = new HashMap();
		setConfigShowPartsHideMap(model, s, i, hideMap);
		return hideMap;
	}

	public static void addConfigShowPartsHideMap(Object model, String s, int i, List<String> hideList) {
		HashMap<Integer, String> map = getConfigShowPartsHideMap(model, s, i);
		int size = map.size();
		for(int i1 = 0; i1 < hideList.size(); i1++) {
			map.put(size + i1, hideList.get(i1));
		}
		setConfigShowPartsHideMap(model, s, i, map);
	}

	public static void addConfigShowPartsHideMap(Object model, String s, int i, String[] s1) {
		HashMap<Integer, String> map = getConfigShowPartsHideMap(model, s, i);
		int size = map.size();
		for(int i1 = 0; i1 < s1.length; i1++) {
			map.put(size + i1, s1[i1]);
		}
		setConfigShowPartsHideMap(model, s, i, map);
	}

	public static void setConfigShowPartsHideMap(Object model, String s, int i, HashMap<Integer, String> hideMap) {
		String s3 = new StringBuilder().append(s).append(",").append(i).toString();
		configShowPartsHideMap.put(s3, hideMap);
	}

	public static HashMap<String, Boolean> getDefaultShowPartsMap(String s, int i) {
		String s1 = new StringBuilder().append(s).append(",").append(i).toString();
		if (configDefaultShowPartsMap != null
				&& configDefaultShowPartsMap.containsKey(s1)) return configDefaultShowPartsMap.get(s1);
		return null;
	}

	public static boolean getDefaultShowPartsMapBoolean(String s, String s1, int i) {
		String s2 = new StringBuilder().append(s).append(",").append(i).toString();
		if (configDefaultShowPartsMap != null
				&& configDefaultShowPartsMap.containsKey(s2)) {
			HashMap<String, Boolean> defaultShowPartsMap = configDefaultShowPartsMap.get(s2);
			if (defaultShowPartsMap != null
					&& defaultShowPartsMap.containsKey(s1)) return defaultShowPartsMap.get(s1);
		}
		return true;
	}

	public static void putDefaultShowPartsMap(String s, String s1, int i, boolean b) {
		String s2 = new StringBuilder().append(s).append(",").append(i).toString();
		HashMap<String, Boolean> defaultShowPartsMap = null;
		if (configDefaultShowPartsMap != null
				&& configDefaultShowPartsMap.containsKey(s2)) {
			defaultShowPartsMap = configDefaultShowPartsMap.get(s2);
		} else defaultShowPartsMap = new HashMap();
		defaultShowPartsMap.put(s1, b);
		configDefaultShowPartsMap.put(s2, defaultShowPartsMap);
		//Modchu_Debug.mDebug("putDefaultShowPartsMap s1="+s1+" b="+b);
	}

	public static HashMap<String, HashMap> getIndexOfAllSetVisibleMap() {
		return configIndexOfAllSetVisibleMap;
	}

	public static HashMap<String, List<String>> getIndexOfAllSetVisibleMap(String s, int i) {
		String s1 = new StringBuilder().append(s).append(",").append(i).toString();
		if (configIndexOfAllSetVisibleMap != null
				&& configIndexOfAllSetVisibleMap.containsKey(s1)) return configIndexOfAllSetVisibleMap.get(s1);
		HashMap<String, List<String>> map = new HashMap();
		setIndexOfAllSetVisibleMap(s, i, map);
		return map;
	}

	public static List<String> getIndexOfAllSetVisibleMap(String s, String s1, int i) {
		String s2 = new StringBuilder().append(s).append(",").append(i).toString();
		if (configIndexOfAllSetVisibleMap != null
				&& configIndexOfAllSetVisibleMap.containsKey(s2)) {
			HashMap<String, List<String>> map = configIndexOfAllSetVisibleMap.get(s2);
			if (map != null
					&& map.containsKey(s1)) return map.get(s1);
		}
		List<String> list = new ArrayList();
		setIndexOfAllSetVisibleMap(s, s1, i, list);
		return list;
	}

	public static void setIndexOfAllSetVisibleMap(String s, int i, HashMap<String, List<String>> map) {
		String s1 = new StringBuilder().append(s).append(",").append(i).toString();
		configIndexOfAllSetVisibleMap.put(s1, map);
	}

	public static void setIndexOfAllSetVisibleMap(String s, String s1, int i, List<String> list) {
		String s2 = new StringBuilder().append(s).append(",").append(i).toString();
		HashMap<String, List<String>> map = null;
		if (configIndexOfAllSetVisibleMap != null
				&& configIndexOfAllSetVisibleMap.containsKey(s2)) {
			map = configIndexOfAllSetVisibleMap.get(s2);
		}
		if (map != null) ;else map = new HashMap();
		if (list != null
				&& !list.isEmpty()) {
			map.put(s1, list);
			configIndexOfAllSetVisibleMap.put(s2, map);
		} else {
			if (configIndexOfAllSetVisibleMap.containsKey(s2)) configIndexOfAllSetVisibleMap.remove(s2);
		}
	}

	public static HashMap<String, HashMap> getIndexOfAllSetVisibleBooleanMap() {
		return configIndexOfAllSetVisibleBooleanMap;
	}

	public static HashMap<String, Boolean> getIndexOfAllSetVisibleBooleanMap(String s, int i) {
		String s1 = new StringBuilder().append(s).append(",").append(i).toString();
		if (configIndexOfAllSetVisibleBooleanMap != null
				&& configIndexOfAllSetVisibleBooleanMap.containsKey(s1)) return configIndexOfAllSetVisibleBooleanMap.get(s1);
		HashMap<String, Boolean> map = new HashMap();
		setIndexOfAllSetVisibleBooleanMap(s, i, map);
		return map;
	}

	public static int getIndexOfAllSetVisibleBooleanMap(String s, String s1, int i) {
		String s2 = new StringBuilder().append(s).append(",").append(i).toString();
		if (configIndexOfAllSetVisibleBooleanMap != null
				&& configIndexOfAllSetVisibleBooleanMap.containsKey(s2)) {
			HashMap<String, Boolean> map = configIndexOfAllSetVisibleBooleanMap.get(s2);
			if (map != null
					&& map.containsKey(s1)) return map.get(s1) == true ? 1 : 0;
		}
		return -1;
	}

	public static void setIndexOfAllSetVisibleBooleanMap(String s, int i, HashMap<String, Boolean> map) {
		String s1 = new StringBuilder().append(s).append(",").append(i).toString();
		configIndexOfAllSetVisibleBooleanMap.put(s1, map);
	}

	public static void setIndexOfAllSetVisibleBooleanMap(String s, String s1, int i, boolean b) {
		String s2 = new StringBuilder().append(s).append(",").append(i).toString();
		HashMap<String, Boolean> map = null;
		if (configIndexOfAllSetVisibleBooleanMap != null
				&& configIndexOfAllSetVisibleBooleanMap.containsKey(s2)) {
			map = configIndexOfAllSetVisibleBooleanMap.get(s2);
		}
		if (map != null) ;else map = new HashMap();
		map.put(s1, b);
		configIndexOfAllSetVisibleBooleanMap.put(s2, map);
	}

	private static String getTextureName() {
		return mod_PFLM_PlayerFormLittleMaid.textureName;
	}

	private static int getMaidColor() {
		return mod_PFLM_PlayerFormLittleMaid.maidColor;
	}

	private static boolean getPartsSaveFlag() {
		return PFLM_Gui.partsSaveFlag;
	}

	private static int getModeOthersSettingOffline() {
		return PFLM_GuiOthersPlayer.modeOthersSettingOffline;
	}
}