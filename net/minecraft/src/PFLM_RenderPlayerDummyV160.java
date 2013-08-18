package net.minecraft.src;

public class PFLM_RenderPlayerDummyV160 extends RendererLivingEntity implements PFLM_IRenderPlayerDummy
{
	public static PFLM_RenderPlayerDummyMaster pflm_RenderPlayerDummyMaster;

	public PFLM_RenderPlayerDummyV160() {
		super(null, 0.0F);
		pflm_RenderPlayerDummyMaster = new PFLM_RenderPlayerDummyMaster();
	}

    protected int setArmorModel(EntityLivingBase entityliving, int i, float f)
    {
    	return pflm_RenderPlayerDummyMaster.setArmorModel((Entity) entityliving, i, f);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f)
    {
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) setArmorModel(entityliving, i, f);
    	return pflm_RenderPlayerDummyMaster.shouldRenderPass((Entity) entityliving, i, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
    	pflm_RenderPlayerDummyMaster.preRenderCallback((Entity) entityliving, f);
    }

    public void doRenderLiving(EntityLivingBase entityliving, double d, double d1, double d2,
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
    	modelData.modelMain.setRender(this);
    	modelData.setCapsValue(modelData.caps_Entity, entity);
    	mainModel = modelData.modelMain;
    	setRenderPassModel(modelData.modelFATT);
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) {
    		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) Modchu_Reflect.invokeMethod("RendererLivingEntity", "func_130000_a", new Class[]{ Modchu_Reflect.loadClass("EntityLivingBase"), double.class, double.class, double.class, float.class, float.class }, this, new Object[]{ entity, d, d1, d2, f, f1 });
    		//super.func_130000_a((EntityLivingBase) entity, d, d1, d2, f, f1);
    		else Modchu_Reflect.invokeMethod(RenderLiving.class, "func_77031_a", "doRenderLiving", new Class[]{ Modchu_Reflect.loadClass("EntityLiving"), double.class, double.class, double.class, float.class, float.class }, this, new Object[]{ entity, d, d1, d2, f, f1 });
    		//doRenderLiving((EntityLiving) entity, d, d1, d2, f, f1);
    	}
    	else pflm_RenderPlayerDummyMaster.oldDoRenderLivingPFLM(entity, d, d1, d2, f, f1);
    	modelData.modelMain.setCapsValue(modelData.caps_aimedBow, false);
    }

    @Override
    protected void passSpecialRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
    {
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entityliving, float f)
    {
    }

    protected void renderSpecials(EntityPlayer entityplayer, float f)
    {
    }

    @Override
    protected void renderModel(EntityLivingBase entityliving, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	pflm_RenderPlayerDummyMaster.renderModel((Entity) entityliving, f, f1, f2, f3, f4, f5);
    }

    public static RenderManager getRenderManager() {
    	return pflm_RenderPlayerDummyMaster.renderManager;
    }

    @Override
    protected ResourceLocation func_110775_a(Entity entity) {
    	return (ResourceLocation) pflm_RenderPlayerDummyMaster.getResourceLocation(entity);
    }

    protected ResourceLocation func_110775_a(Entity entity, int i) {
    	return (ResourceLocation) pflm_RenderPlayerDummyMaster.getResourceLocation(entity, i);
    }

    protected static void setResourceLocation(Entity entity, ResourceLocation[] resourceLocation) {
    	pflm_RenderPlayerDummyMaster.setResourceLocation(entity, resourceLocation);
    }

    protected static void setResourceLocation(Entity entity, ResourceLocation resourceLocation) {
    	pflm_RenderPlayerDummyMaster.setResourceLocation(entity, resourceLocation);
    }

    protected static void setResourceLocation(Entity entity, int i, ResourceLocation resourceLocation) {
    	pflm_RenderPlayerDummyMaster.setResourceLocation(entity, i, resourceLocation);
    }
}
