package modchu.pflm;

import java.util.ArrayList;

import modchu.model.ModchuModel_IEntityCaps;

public class PFLM_GuiConstant {
	public static int partsSetFlag = 0;
	public static ArrayList<Integer> changeModelist;
	public static ArrayList<Integer> otherChangeModelist;

	public static void init() {
		if (changeModelist != null); else {
			changeModelist = new ArrayList();
			changeModelist.add(ModchuModel_IEntityCaps.skinMode_offline);
			changeModelist.add(ModchuModel_IEntityCaps.skinMode_online);
			changeModelist.add(ModchuModel_IEntityCaps.skinMode_Random);
		}
		if (otherChangeModelist != null); else {
			otherChangeModelist = new ArrayList();
			otherChangeModelist.add(ModchuModel_IEntityCaps.skinMode_offline);
			otherChangeModelist.add(ModchuModel_IEntityCaps.skinMode_online);
			otherChangeModelist.add(ModchuModel_IEntityCaps.skinMode_Random);
			otherChangeModelist.add(ModchuModel_IEntityCaps.skinMode_Player);
			otherChangeModelist.add(ModchuModel_IEntityCaps.skinMode_PlayerOffline);
			otherChangeModelist.add(ModchuModel_IEntityCaps.skinMode_PlayerOnline);
		}
	}

	public static String getChangeModeString(int i) {
		int i1 = changeModelist.get(i);
		String s = null;
		if (i1 == ModchuModel_IEntityCaps.skinMode_offline) s = "modeOffline";
		if (i1 == ModchuModel_IEntityCaps.skinMode_online) s = "modeOnline";
		if (i1 == ModchuModel_IEntityCaps.skinMode_Random) s = "modeRandom";
		return s;
	}

	public static String getOtherChangeModeString(int i) {
		int i1 = otherChangeModelist.get(i);
		String s = null;
		if (i1 == ModchuModel_IEntityCaps.skinMode_offline) s = "modeOthersSettingOffline";
		if (i1 == ModchuModel_IEntityCaps.skinMode_online) s = "modefalse";
		if (i1 == ModchuModel_IEntityCaps.skinMode_Random) s = "modeRandom";
		if (i1 == ModchuModel_IEntityCaps.skinMode_Player) s = "modePlayer";
		if (i1 == ModchuModel_IEntityCaps.skinMode_PlayerOffline) s = "modePlayerOffline";
		if (i1 == ModchuModel_IEntityCaps.skinMode_PlayerOnline) s = "modePlayerOnline";
		return s;
	}

	public static int getSkinModeToChangeMode(int i) {
		for (int i1 = 0; i1 < changeModelist.size(); i1++) {
			int i2 = changeModelist.get(i1);
			if (i2 == i) return i1;
		}
		return 0;
	}

	public static int getSkinModeToOtherChangeMode(int i) {
		for (int i1 = 0; i1 < otherChangeModelist.size(); i1++) {
			int i2 = otherChangeModelist.get(i1);
			if (i2 == i) return i1;
		}
		return 0;
	}

	public static int getSkinMode(int i) {
		return changeModelist.get(i);
	}

	public static int getOtherSkinMode(int i) {
		return otherChangeModelist.get(i);
	}

	public static String getHandednessModeString(int i) {
		String s = null;
		switch (i) {
		case -1:
			s = "RandomMode";
			break;
		case 0:
			s = "R";
			break;
		case 1:
			s = "L";
			break;
		}
		return s;
	}

	public static String getArmorTypeString(int i) {
		String s = null;
		switch (i) {
		case 0:
			s = "mainInner";
			break;
		case 2:
			s = "armorInner";
			break;
		case 3:
			s = "armorOuter";
			break;
		}
		return s;
	}

}