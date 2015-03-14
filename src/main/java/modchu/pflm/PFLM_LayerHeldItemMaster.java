package modchu.pflm;

import java.util.HashMap;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_LayerHeldItemMasterBasis;
import modchu.lib.Modchu_Reflect;

public class PFLM_LayerHeldItemMaster extends Modchu_LayerHeldItemMasterBasis  {

	public PFLM_LayerHeldItemMaster(HashMap<String, Object> map) {
		super(map);
	}

	@Override
	public void doRenderLayer(Object entityLivingBase, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
		Object render = Modchu_AS.get(Modchu_AS.renderManagerGetEntityRenderObject, entityLivingBase);
		Modchu_Reflect.invokeMethod(render.getClass(), "renderEquippedItems", new Class[]{ Modchu_Reflect.loadClass("EntityLivingBase"), float.class }, render, new Object[]{ entityLivingBase, 0.0625F });
	}

}
