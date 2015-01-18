package modchu.pflm;

public class PFLM_GuiConstant {
	public static final int modeOffline							= 0;
	public static final int modeOnline							= 1;
	public static final int modeRandom							= 2;
	public static final int modePlayer							= 3;
	public static final int modePlayerOffline					= 4;
	public static final int modePlayerOnline					= 5;
	public static int partsSetFlag = 0;

	public static String getChangeModeString(int i) {
		String s = null;
		switch (i) {
		case modeOffline:
			s = "modeOffline";
			break;
		case modeOnline:
			s = "modeOnline";
			break;
		case modeRandom:
			s = "modeRandom";
			break;
		}
		return s;
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