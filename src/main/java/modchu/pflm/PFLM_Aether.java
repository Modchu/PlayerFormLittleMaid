package modchu.pflm;

import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_CastHelper;
import modchu.lib.characteristic.Modchu_ModelBiped;
import modchu.lib.characteristic.Modchu_RenderPlayer;

import org.lwjgl.opengl.GL11;

public class PFLM_Aether {
	public static Modchu_ModelBiped modchu_ModelBiped;
	public static PFLM_RenderPlayerMaster pflm_RenderPlayerMaster;
	public Object renderPlayerAether;
	private Class RenderPlayerAether;

	public PFLM_Aether() {
		RenderPlayerAether = Modchu_Reflect.loadClass("net.aetherteam.aether.client.RenderPlayerAether");
		if (renderPlayerAether != null); else {
			renderPlayerAether = RenderPlayerAether != null ? Modchu_Reflect.newInstance(RenderPlayerAether) : null;
			Modchu_AS.set(Modchu_AS.renderRenderManager, renderPlayerAether);
		}
		if (modchu_ModelBiped != null); else {
			modchu_ModelBiped = new Modchu_ModelBiped(null);
		}
	}

	public Object[] modchu_RenderPlayerDoRender(Object[] o) {
		//Modchu_Debug.mDebug("PFLM_Aether modchu_RenderPlayerDoRender o[0]="+(o != null ? o[0] : null));
		//Modchu_Reflect.setFieldObject(renderPlayerAether.getClass(), "scale", renderPlayerAether, f);
		if (o != null && o.length > 5 && pflm_RenderPlayerMaster != null); else {
			if (pflm_RenderPlayerMaster == null) {
				Object render = Modchu_Main.getRender(Modchu_AS.get(Modchu_AS.minecraftThePlayer));
				if (render != null
						&& render instanceof Modchu_RenderPlayer) pflm_RenderPlayerMaster = ((Modchu_RenderPlayer) render).master instanceof PFLM_RenderPlayerMaster ? (PFLM_RenderPlayerMaster) ((Modchu_RenderPlayer) render).master : null;
				if (pflm_RenderPlayerMaster == null) Modchu_Debug.mDebug("PFLM_Aether modchu_RenderPlayerDoRender pflm_RenderPlayerMaster == null !!");
			}
			return null;
		}
		Object entity = o[0];
		float f1 = Modchu_CastHelper.Float(o[5]);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);
		if (modelData != null); else return null;
		modchu_ModelBiped.master = modelData.modelMain.model;
		Modchu_Reflect.setFieldObject(RenderPlayerAether, "modelMisc", renderPlayerAether, modchu_ModelBiped);
		Object modelMisc = Modchu_Reflect.getFieldObject(RenderPlayerAether, "modelMisc", renderPlayerAether);

		GL11.glPushMatrix();
		pflm_RenderPlayerMaster.renderScale(entity, f1);
		//if (modelData.getCapsValueBoolean(modelData.caps_isSleeping)) GL11.glPushMatrix();
		if (modelData.getCapsValueBoolean(modelData.caps_isSleeping)) {
			switch (modelData.getCapsValueInt(modelData.caps_rotate)) {
			case 0:
				GL11.glTranslatef(0.0F, -1.72F, -1.72F);
				GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
				break;
			case 1:
				GL11.glTranslatef(1.72F, -1.72F, 0.0F);
				GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				break;
			case 2:
				GL11.glTranslatef(0.0F, -1.72F, 1.72F);
				GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 0.0F, 1.0F);
				break;
			case 3:
			case 4:
				GL11.glTranslatef(-1.72F, -1.72F, 0.0F);
				GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
				break;
			}
			GL11.glRotatef(pflm_RenderPlayerMaster.getDeathMaxRotation(entity), 0.0F, 0.0F, 1.0F);
		}

		double dd1 = Modchu_CastHelper.Double(o[2]);
		float hight = modelData.modelMain.model.getHeight(modelData);
		dd1 += pflm_RenderPlayerMaster.doRenderSettingY(entity, modelData, dd1) - 0.82D + (hight - 1.8F);
		//Modchu_Debug.dDebug("PFLM_Aether doRenderSettingY dd1="+dd1, 1);
		boolean b = modelData.getCapsValueBoolean(modelData.caps_isSneak);
		boolean b1 = modelData.getCapsValueBoolean(modelData.caps_isRiding)
				| modelData.getCapsValueBoolean(modelData.caps_isSitting);
		boolean b2 = modelData.getCapsValueBoolean(modelData.caps_aimedBow);
		boolean t = false;
		t = Modchu_Reflect.setFieldObject(modelMisc.getClass(), "field_78117_n", "isSneak", modelMisc, b);
		t = Modchu_Reflect.setFieldObject(modelMisc.getClass(), "field_78093_q", "isRiding", modelMisc, b1);
		t = Modchu_Reflect.setFieldObject(modelMisc.getClass(), "field_78118_o", "aimedBow", modelMisc, b2);
		Modchu_Reflect.invokeMethod(RenderPlayerAether, "renderMisc", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), double.class, double.class, double.class, float.class, float.class }, renderPlayerAether, new Object[]{ entity, o[1], dd1, o[3], o[4], o[5] });
		t = Modchu_Reflect.setFieldObject(modelMisc.getClass(), "field_78117_n", "isSneak", modelMisc, false);
		t = Modchu_Reflect.setFieldObject(modelMisc.getClass(), "field_78093_q", "isRiding", modelMisc, false);
		t = Modchu_Reflect.setFieldObject(modelMisc.getClass(), "field_78118_o", "aimedBow", modelMisc, false);
		//if (modelData.getCapsValueBoolean(modelData.caps_isSleeping)) GL11.glPopMatrix();
		GL11.glPopMatrix();
		return null;
	}

	public Object[] modchu_RenderPlayerRenderFirstPersonArm(Object[] o) {
		if (o != null
				&& o.length > 0); else return null;
		//Modchu_Debug.mDebug("PFLM_Aether modchu_RenderPlayerRenderFirstPersonArm o[0]="+(o != null ? o[0] : null));
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(o[0]);
		if (modelData != null); else return null;
		if (modelData.modelMain.model != null
				&& modelData.getCapsValue(modelData.caps_ResourceLocation) != null) {
			pflm_RenderPlayerMaster.bindTexture(modelData, modelData.getCapsValue(modelData.caps_ResourceLocation));
		}
		Class EntityPlayer = Modchu_Reflect.loadClass("EntityPlayer");
		Modchu_Reflect.invokeMethod(RenderPlayerAether, "renderFirstPersonGloves", new Class[]{ EntityPlayer }, renderPlayerAether, new Object[]{ o[0] });
		return null;
	}

	public Object[] modchu_RenderPlayerRenderEquippedItems(Object[] o) {
		if (o != null
				&& o.length > 1); else return null;
		//Modchu_Debug.mDebug("PFLM_Aether modchu_RenderPlayerRenderEquippedItems o[0]="+(o != null ? o[0] : null));
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(o[0]);
		if (modelData != null); else return null;
		float f1 = 1.62F - (Float) modelData.getCapsValue(modelData.caps_YOffset);
		Modchu_Reflect.invokeMethod(RenderPlayerAether, "renderSpecialHeadEars", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), float.class }, renderPlayerAether, new Object[]{ o[0], o[1] });
		return null;
	}

	public Object[] modchu_RenderPlayerRenderPlayerSleep(Object[] o) {
		if (o != null
				&& o.length > 3); else return null;
		//Modchu_Debug.mDebug("PFLM_Aether modchu_RenderPlayerRenderPlayerSleep o[0]="+(o != null ? o[0] : null));
		Modchu_Reflect.invokeMethod(Modchu_Reflect.loadClass("RenderPlayer"), "func_77039_a", "renderLivingAt", new Class[]{ Modchu_Reflect.loadClass("EntityLivingBase"), double.class, double.class, double.class }, renderPlayerAether, new Object[]{ o[0], o[1], o[2], o[3] });
		return null;
	}

}