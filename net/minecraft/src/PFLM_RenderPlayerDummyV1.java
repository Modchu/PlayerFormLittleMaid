package net.minecraft.src;

public class PFLM_RenderPlayerDummyV1 extends PFLM_RenderPlayerDummy
{

	public PFLM_RenderPlayerDummyV1() {
		super(null, 0.0F);
		pflm_RenderPlayerDummyMaster = new PFLM_RenderPlayerDummyMaster();
	}

    protected int setArmorModel(EntityLiving entityliving, int i, float f)
    {
    	return pflm_RenderPlayerDummyMaster.setArmorModel((Entity) entityliving, i, f);
    }

    @Override
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) setArmorModel(entityliving, i, f);
    	return pflm_RenderPlayerDummyMaster.shouldRenderPass((Entity) entityliving, i, f);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f) {
    	pflm_RenderPlayerDummyMaster.preRenderCallback((Entity) entityliving, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
    	pflm_RenderPlayerDummyMaster.oldDoRenderLivingPFLM((Entity) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
    		float f, float f1) {
    	if (pflm_RenderPlayerDummyMaster != null) ;else return;
    	pflm_RenderPlayerDummyMaster.doRender(entity, d, d1, d2, f, f1);
    }

    public void superDoRenderLiving(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	PFLM_ModelData modelData = pflm_RenderPlayerDummyMaster.modelData;
    	mainModel = modelData.modelMain;
    	modelData.modelMain.setEntityCaps(modelData);
    	modelData.setCapsValue(modelData.caps_Entity, entity);
    	mainModel = modelData.modelMain;
    	setRenderPassModel(modelData.modelFATT);
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) {
    		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) Modchu_Reflect.invokeMethod("RendererLivingEntity", "func_130000_a", new Class[]{ Modchu_Reflect.loadClass("EntityLivingBase"), double.class, double.class, double.class, float.class, float.class }, this, new Object[]{ entity, d, d1, d2, f, f1 });
    		//super.func_130000_a((EntityLivingBase) entity, d, d1, d2, f, f1);
    		else Modchu_Reflect.invokeMethod(RenderLiving.class, "func_77031_a", "doRenderLiving", new Class[]{ EntityLiving.class, double.class, double.class, double.class, float.class, float.class }, this, new Object[]{ entity, d, d1, d2, f, f1 });
    		//doRenderLiving((EntityLiving) entity, d, d1, d2, f, f1);
    	}
    	else pflm_RenderPlayerDummyMaster.oldDoRenderLivingPFLM(entity, d, d1, d2, f, f1);
    	modelData.modelMain.setCapsValue(modelData.caps_aimedBow, false);
    }

    @Override
    protected void passSpecialRender(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
    }

    @Override
    protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
    }

    protected void renderSpecials(EntityPlayer entityplayer, float f)
    {
    }

    @Override
    protected void renderModel(EntityLiving entityliving, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	pflm_RenderPlayerDummyMaster.renderModel((Entity) entityliving, f, f1, f2, f3, f4, f5);
    }

    public RenderManager getRenderManager() {
    	return renderManager;
    }

    public void allModelInit(Entity entity, boolean debug) {
    	pflm_RenderPlayerDummyMaster.allModelInit(entity, debug);
    }
}