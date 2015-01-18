package modchu.pflm;

import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_LayerArmorBaseMasterBasis;
import modchu.lib.Modchu_Reflect;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_CastHelper;
import modchu.lib.characteristic.Modchu_ModelBaseNihilBase;
import modchu.lib.characteristic.Modchu_RenderPlayer;
import modchu.model.multimodel.base.MultiModelBaseBiped;

public class PFLM_LayerArmorBaseMaster extends Modchu_LayerArmorBaseMasterBasis  {

	public PFLM_LayerArmorBaseMaster(Object modchu_LayerBipedArmor) {
		super(modchu_LayerBipedArmor);
	}

	@Override
	public void doRenderLayer(Object entityLivingBase, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
		Object render = Modchu_AS.get(Modchu_AS.renderManagerGetEntityRenderObject, entityLivingBase);
		PFLM_ModelData modelData = (PFLM_ModelData) PFLM_ModelDataMaster.instance.getPlayerData(entityLivingBase);
		MultiModelBaseBiped modelInner = modelData.modelFATT.modelInner;
		MultiModelBaseBiped modelOuter = modelData.modelFATT.modelOuter;
		//Modchu_Debug.mDebug("PFLM_LayerArmorBaseMaster doRenderLayer render="+render);
		for (int i = 0; i < 4; i++) {
			int i1 = Modchu_CastHelper.Int(Modchu_Reflect.invokeMethod(render.getClass(), "setArmorModel", new Class[]{ Object.class, Object.class, int.class, float.class, int.class }, render, new Object[]{ null, entityLivingBase, i, f6, 0 }));
			if (i1 < 0
					| Modchu_CastHelper.Int(Modchu_Reflect.invokeMethod(render.getClass(), "shouldRenderPass", new Class[]{ Modchu_Reflect.loadClass("EntityLivingBase"), int.class, float.class }, render, new Object[]{ entityLivingBase, i, f6 })) < 0) continue;

			modelInner.setLivingAnimations(entityLivingBase, f, f1, f2);
			Modchu_AS.set(Modchu_AS.renderBindTexture, render, modelData.modelFATT.textureInner[i]);
			modelInner.setModelAttributes(modelData.modelMain.model);
			modelInner.render(modelData, f, f1, f2, f4, f5, f6, (Boolean) Modchu_Reflect.getFieldObject(Modchu_ModelBaseNihilBase.class, "isRendering", modelData.modelFATT));

			modelOuter.setLivingAnimations(entityLivingBase, f, f1, f2);
			Modchu_AS.set(Modchu_AS.renderBindTexture, render, modelData.modelFATT.textureOuter[i]);
			modelOuter.render(modelData, f, f1, f2, f4, f5, f6, (Boolean) Modchu_Reflect.getFieldObject(Modchu_ModelBaseNihilBase.class, "isRendering", modelData.modelFATT));
		}
	}
/*
	private void func_177182_a(Object entityLivingBase, float f, float f1, float f2, float f3, float f4, float f5, float f6, int i) {
		Object itemStack = func_177176_a(entityLivingBase, i);
		Object item = Modchu_AS.get(Modchu_AS.itemStackGetItem, itemStack);
		if (item != null
				&& Modchu_Reflect.loadClass("ItemArmor").isInstance(item)) {
			Object modelBase = func_177175_a(i);
			modelBase.setModelAttributes(field_177190_a.getMainModel());
			modelBase.setLivingAnimations(entityLivingBase, f, f1, f2);
			modelBase = net.minecraftforge.client.ForgeHooksClient.getArmorModel(entityLivingBase, itemStack, i, modelBase);
			func_177179_a(modelBase, i);
			boolean flag = func_177180_b(i);
			field_177190_a.bindTexture(getArmorResource(entityLivingBase, itemStack, flag ? 2 : 1, null));

			int j = itemArmor.getColor(itemStack);
			if (j != -1) { //Allow this for anything, not only cloth.
				float f7 = (float) (j >> 16 & 255) / 255.0F;
				float f8 = (float) (j >> 8 & 255) / 255.0F;
				float f9 = (float) (j & 255) / 255.0F;
				Modchu_GlStateManager.color(field_177184_f * f7, field_177185_g * f8, field_177192_h * f9, field_177187_e);
				modelBase.render(entityLivingBase, f, f1, f3, f4, f5, f6);
				field_177190_a.bindTexture(getArmorResource(entityLivingBase, itemStack, flag ? 2 : 1, "overlay"));
			}
			// Non-cloth
			Modchu_GlStateManager.color(field_177184_f, field_177185_g, field_177192_h, field_177187_e);
			modelBase.render(entityLivingBase, f, f1, f3, f4, f5, f6);

			// Default, Why is this a switch? there were no breaks.
			if (!field_177193_i
					&& itemStack.isItemEnchanted()) {
				func_177183_a(entityLivingBase, modelBase, f, f1, f2, f3, f4, f5, f6);
			}
		}
	}
*/
}
