package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class PFLM_RenderPlayerAetherV160 extends PFLM_RenderPlayerV160
{

	public PFLM_RenderPlayerAetherV160() {
		super();
	}

    @Override
    public void func_130009_a(AbstractClientPlayer entity, double d, double d1, double d2, float f, float f1) {
    	//doRender(entity, d, d1, d2, f, f1);
    }

    private void func_130009_aAether(PFLM_ModelData modelData, AbstractClientPlayer entity, double d, double d1, double d2, float f, float f1) {

    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) {
    		float yOffset = (Float) modelData.getCapsValue(modelData.caps_YOffset);
    		d1 += yOffset - 0.82D;
    	}
    	boolean b = modelData.getCapsValueBoolean(modelData.caps_isSneak);
    	boolean b1 = modelData.getCapsValueBoolean(modelData.caps_isRiding)
    			| modelData.getCapsValueBoolean(modelData.caps_isSitting);
    	boolean b2 = modelData.getCapsValueBoolean(modelData.caps_aimedBow);
    	GL11.glPushMatrix();
    	f = pflm_RenderPlayerMaster.renderPlayerScale((Entity)entity, f1);
    	GL11.glPopMatrix();
    	if (modelData.getCapsValueBoolean(modelData.caps_isSleeping)) GL11.glPushMatrix();
    	if (modelData.getCapsValueBoolean(modelData.caps_isSleeping)) {
    		switch(modelData.getCapsValueInt(modelData.caps_rotate)) {
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
    		GL11.glRotatef(getDeathMaxRotation((EntityLivingBase) entity), 0.0F, 0.0F, 1.0F);
    	}
    	Modchu_Reflect.invokeMethod("PFLMF_Aether", "func_130009_a", new Class[]{ AbstractClientPlayer.class, double.class, double.class, double.class, float.class, float.class, boolean.class, boolean.class, boolean.class }, new Object[]{ entity, d, d1, d2, f, f1, b, b1, b2 });
    	if (modelData.getCapsValueBoolean(modelData.caps_isSleeping)) GL11.glPopMatrix();
    }

    public void superFunc_130009_a(AbstractClientPlayer entity, double d, double d1, double d2, float f, float f1) {
    	super.func_130009_a(entity, d, d1, d2, f, f1);
    }

    @Override
    public void superDoRenderLiving(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	//Modchu_Debug.Debug("superDoRenderLiving");
    	PFLM_ModelData modelData = PFLM_ModelDataMaster.instance.getPlayerData((EntityPlayer) entity);
    	if (renderManager != null) ;else setRenderManager(RenderManager.instance);
    	//Modchu_Debug.Debug("superDoRenderLiving modelData.modelMain="+modelData.modelMain.getClass());
    	setMainModel(modelData.modelMain);
    	modelData.modelMain.setEntityCaps(modelData);
    	modelData.modelMain.setRender(this);
    	setRenderPassModel(modelData.modelFATT);
    	GL11.glPushMatrix();
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) {
    		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) {
    			superFunc_130009_a((AbstractClientPlayer) entity, d, d1, d2, f, f1);
    		} else {
    			Modchu_Reflect.invokeMethod(RenderLiving.class, "func_77031_a", "doRenderLiving", new Class[]{ EntityLiving.class, double.class, double.class, double.class, float.class, float.class }, this, new Object[]{ entity, d, d1, d2, f, f1 });
    			//doRenderLiving((EntityLiving) entity, d, d1, d2, f, f1);
    		}
    	} else {
    		pflm_RenderPlayerMaster.oldDoRenderLivingPFLM(modelData, entity, d, d1, d2, f, f1);
    		//Modchu_Debug.Debug("superDoRenderLiving");
    	}
    	func_130009_aAether(modelData, (AbstractClientPlayer) entity, d, d1, d2, f, f1);
    	GL11.glPopMatrix();
    	modelData.modelMain.setCapsValue(modelData.caps_aimedBow, false);
    }

    private void setMainModel(ModelBase modelBase) {
    	mainModel = modelBase;
    	//if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) pflm_RenderPlayerMaster.mainModel = modelBase;
    	Modchu_Reflect.invokeMethod("PFLMF_Aether", "setMainModel", new Class[]{ ModelBase.class }, new Object[]{ modelBase });
    }

    public void setRenderPassModel(ModelBase modelBase) {
    	renderPassModel = modelBase;
    	//if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) pflm_RenderPlayerMaster.setRenderPassModel(modelBase);
    	Modchu_Reflect.invokeMethod("PFLMF_Aether", "setRenderPassModel", new Class[]{ ModelBase.class }, new Object[]{ modelBase });
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer entityplayer) {
    	super.renderFirstPersonArm(entityplayer);
    	Modchu_Reflect.invokeMethod("PFLMF_Aether", "renderFirstPersonArm", new Class[]{ EntityPlayer.class }, new Object[]{ entityplayer });
    }

    public void superRenderLivingAt(Entity par1EntityLivingBase, double par2, double par4, double par6)
    {
    	super.superRenderLivingAt(par1EntityLivingBase, par2, par4, par6);
    	Modchu_Reflect.invokeMethod("PFLMF_Aether", "renderLivingAt", new Class[]{ EntityLivingBase.class, double.class, double.class, double.class }, new Object[]{ par1EntityLivingBase, par2, par4, par6 });
    }

    public ModelBiped getModelBipedMain() {
    	return (ModelBiped)this.mainModel;
    }
}

