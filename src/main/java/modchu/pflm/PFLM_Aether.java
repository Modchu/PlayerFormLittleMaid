package modchu.pflm;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_CastHelper;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_IModelBiped;
import modchu.lib.Modchu_IRenderPlayer;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.model.multimodel.base.ModchuModelModelBipedCapsMaster;

public class PFLM_Aether {
	public static Modchu_IModelBiped modchu_ModelBiped;
	public static PFLM_RenderPlayerMaster pflm_RenderPlayerMaster;
	public Object renderPlayerAether;
	private Class RenderPlayerAether;
	private Method renderMiscMethod = null;

	public PFLM_Aether() {
		RenderPlayerAether = Modchu_Reflect.loadClass("net.aetherteam.aether.client.renders.entities.player.RenderPlayerAether", -1);
		if (RenderPlayerAether != null) {
			//renderMiscMethod = Modchu_Reflect.getMethod(RenderPlayerAether, "renderMisc", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), double.class, double.class, double.class, float.class, float.class, boolean.class });
		} else {
			RenderPlayerAether = Modchu_Reflect.loadClass("net.aetherteam.aether.client.RenderPlayerAether");
			renderMiscMethod = Modchu_Reflect.getMethod(RenderPlayerAether, "renderMisc", new Class[]{ Modchu_Reflect.loadClass("EntityPlayer"), double.class, double.class, double.class, float.class, float.class });
		}
		if (renderPlayerAether != null); else {
			renderPlayerAether = RenderPlayerAether != null ? Modchu_Main.getMinecraftVersion() > 162 ? Modchu_Reflect.newInstance(RenderPlayerAether) : Modchu_Reflect.newInstance(RenderPlayerAether, new Class[]{ int.class, Modchu_Reflect.loadClass("net.aetherteam.playercore_api.cores.PlayerCoreRender") }, new Object[]{ 0, null }) : null;
			Modchu_AS.set(Modchu_AS.renderRenderManager, renderPlayerAether);
		}
		if (modchu_ModelBiped != null); else {
			modchu_ModelBiped = (Modchu_IModelBiped) Modchu_Main.newModchuCharacteristicObject("Modchu_ModelBiped", ModchuModelModelBipedCapsMaster.class);
		}
	}

	public Object[] modchu_RenderPlayerDoRender(Object[] o) {
		//Modchu_Debug.mDebug("PFLM_Aether modchu_RenderPlayerDoRender o[0]="+(o != null ? o[0] : null));
		//Modchu_Reflect.setFieldObject(renderPlayerAether.getClass(), "scale", renderPlayerAether, f);
		if (renderMiscMethod != null); else return null;
		if (o != null && o.length > 5 && pflm_RenderPlayerMaster != null); else {
			if (pflm_RenderPlayerMaster == null) {
				Object render = Modchu_Main.getRender(Modchu_AS.get(Modchu_AS.minecraftPlayer));
				if (render != null
						&& render instanceof Modchu_IRenderPlayer) {
					Object master = Modchu_Main.getModchuCharacteristicObjectMaster(render);
					pflm_RenderPlayerMaster = master instanceof PFLM_RenderPlayerMaster ? (PFLM_RenderPlayerMaster) master : null;
				}
				if (pflm_RenderPlayerMaster == null) Modchu_Debug.mDebug("PFLM_Aether modchu_RenderPlayerDoRender pflm_RenderPlayerMaster == null !!");
			}
			return null;
		}
		Object entity = o[0];
		float f1 = Modchu_CastHelper.Float(o[5]);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entity);
		if (modelData != null); else return null;
		Modchu_Reflect.setFieldObject(ModchuModelModelBipedCapsMaster.class, "model", Modchu_Main.getModchuCharacteristicObjectMaster(modchu_ModelBiped), modelData.models[0]);
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
		float hight = modelData.models[0].getHeight(modelData);
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
		//Modchu_Reflect.invoke(renderMiscMethod, renderPlayerAether, new Object[]{ entity, o[1], dd1, o[3], o[4], o[5], false });
		Modchu_Reflect.invoke(renderMiscMethod, renderPlayerAether, new Object[]{ entity, o[1], dd1, o[3], o[4], o[5] });
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
		if (modelData.models[0] != null
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