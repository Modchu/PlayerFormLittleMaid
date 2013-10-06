package net.minecraft.src;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class PFLM_RenderPlayerV1 extends PFLM_RenderPlayer
{

	public PFLM_RenderPlayerV1() {
		Modchu_Debug.lDebug("PFLM_RenderPlayerV1 init");
		if (pflm_RenderPlayerMaster != null) ;else pflm_RenderPlayerMaster = new PFLM_RenderPlayerMaster();
	}

    @Override
    protected int setArmorModel(EntityPlayer entityplayer, int i, float f) {
    	return pflm_RenderPlayerMaster != null ? pflm_RenderPlayerMaster.setArmorModel((EntityPlayer) entityplayer, i, f) : -1;
    }

    protected int setArmorModel(MultiModelBaseBiped model, EntityPlayer entityplayer, int i, float f, int i2) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.setArmorModel(model, entityplayer, i, f, i2);
    	return -1;
   }

    @Override
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
    	if (pflm_RenderPlayerMaster != null) ;else return -1;
    	int i1 = setArmorModel((EntityPlayer) entityliving, i, f);
    	PFLM_ModelData modelData = PFLM_ModelDataMaster.instance.getPlayerData((EntityPlayer) entityliving);
    	setRenderPassModel(modelData.modelFATT);
    	int version = mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion();
    	if ((version > 129
    			&& !mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor)
    			| (version > 129
    					&& mod_Modchu_ModchuLib.modchu_Main.useInvisibilityArmor
    					&& !entityliving.isInvisible())
    					| version < 130) {
    		((MMM_ModelBaseDuo) renderPassModel).setArmorRendering(true);
    	} else ((MMM_ModelBaseDuo) renderPassModel).setArmorRendering(false);
    	return i1;
    	//return pflm_RenderPlayerMaster.shouldRenderPass((Entity) entityliving, i, f);
    }

    @Override
    protected void renderPlayerScale(EntityPlayer entityliving, float f)
    {
    	float f1 = 0.9375F;
    	if (pflm_RenderPlayerMaster != null) f1 = pflm_RenderPlayerMaster.renderPlayerScale((Entity) entityliving, f);
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glScalef(f1, f1, f1);
    }

    @Override
    protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
    {
        renderPlayerScale((EntityPlayer) par1EntityLiving, par2);
    }

    public void superPreRenderCallback(EntityPlayer entityliving, float f)
    {
    	super.preRenderCallback(entityliving, f);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	if (pflm_RenderPlayerMaster != null) ;else return;
    	pflm_RenderPlayerMaster.doRender(entity, d, d1, d2, f, f1);
    }

    @Override
    public void superDoRenderLiving(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	PFLM_ModelData modelData = PFLM_ModelDataMaster.instance.getPlayerData((EntityPlayer) entity);
    	mainModel = modelData.modelMain;
    	setRenderPassModel(modelData.modelFATT);
    	//GL11.glPushMatrix();
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) {
    		if (mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) Modchu_Reflect.invokeMethod("RendererLivingEntity", "func_130000_a", new Class[]{ Modchu_Reflect.loadClass("EntityLivingBase"), double.class, double.class, double.class, float.class, float.class }, this, new Object[]{ entity, d, d1, d2, f, f1 });
    		//super.func_130000_a((EntityLivingBase) entity, d, d1, d2, f, f1);
    		else Modchu_Reflect.invokeMethod(RenderLiving.class, "func_77031_a", "doRenderLiving", new Class[]{ EntityLiving.class, double.class, double.class, double.class, float.class, float.class }, this, new Object[]{ entity, d, d1, d2, f, f1 });
    		//doRenderLiving((EntityLiving) entity, d, d1, d2, f, f1);
    	}
    	else pflm_RenderPlayerMaster.oldDoRenderLivingPFLM(modelData, entity, d, d1, d2, f, f1);
    	//GL11.glPopMatrix();
    	modelData.modelMain.setCapsValue(modelData.caps_aimedBow, false);
    }

    @Override
    protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
    	renderSpecials((EntityPlayer)entityliving, f);
    }

    @Override
    protected void renderSpecials(EntityPlayer entityplayer, float f)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderSpecials(((EntityPlayer) entityplayer), f);
    }

    @Override
    protected void passSpecialRender(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
    	if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isRenderName) super.passSpecialRender(par1EntityLiving, par2, par4, par6);
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer entityplayer) {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderFirstPersonArm(entityplayer, 2);
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer, int i) {
    	//olddays導入時に2以外のint付きで呼ばれる。
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderFirstPersonArm(entityplayer, i);
    }

    public static boolean isActivatedForPlayer(EntityPlayer entityplayer) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.isActivatedForPlayer(((EntityPlayer) entityplayer));
    	return false;
    }

    @Override
    public void renderLivingLabel(EntityLiving entityplayer, String par2Str, double d, double d1, double d2, int i)
    {
    	if (pflm_RenderPlayerMaster != null) d1 = pflm_RenderPlayerMaster.renderLivingLabel((Entity) entityplayer, par2Str, d, d1, d2, i);
    	if(mod_PFLM_PlayerFormLittleMaid.pflm_main.isRenderName) super.renderLivingLabel(entityplayer, par2Str, d, d1, d2, i);
    }

    @Override
    protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderModel((Entity) par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }

    @Override
    protected void renderLivingAt(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderLivingAt((EntityPlayer)par1EntityLiving, par2, par4, par6);
    	//super.renderLivingAt(par1EntityLiving, par2, par4, par6);
    }

    @Override
    protected void renderPlayerSleep(EntityPlayer var1, double var2, double var4, double var6)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderPlayerSleep(((EntityPlayer) var1), var2, var4, var6);
    	super.renderPlayerSleep(var1, var2, var4, var6);
    }

    @Override
    public void superRenderLivingAt(Entity par1EntityLivingBase, double par2, double par4, double par6)
    {
    	super.renderLivingAt((EntityLiving) par1EntityLivingBase, par2, par4, par6);
    }

    @Override
    public void rotatePlayer(EntityPlayer var1, float var2, float var3, float var4)
    {
    	if (pflm_RenderPlayerMaster != null
    			&& !mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) pflm_RenderPlayerMaster.rotatePlayer(((EntityPlayer) var1),var2, var3, var4);
    	PFLM_ModelData modelData = PFLM_ModelDataMaster.instance.getPlayerData(var1);
    	if (!modelData.getCapsValueBoolean(modelData.caps_isSleeping)) super.rotatePlayer((EntityPlayer) var1, var2, var3, var4);
    }

    @Override
    public void superRotateCorpse(Entity par1EntityLivingBase, float par2, float par3, float par4)
    {
    	super.rotateCorpse((EntityLiving) par1EntityLivingBase, par2, par3, par4);
    }

    //smartMoving関連↓
    public RenderManager getRenderManager() {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.getRenderManager();
    	return null;
    }

    public void renderPlayerAt(EntityPlayer var1, double var2, double var4, double var6)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderPlayerAt(((EntityPlayer) var1), var2, var4, var6);
    }

    public static void renderGuiIngame(Minecraft var0)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderGuiIngame(var0);
    }
    //smartMoving関連↑

    @Override
    public PFLM_RenderPlayerMaster getRenderPlayerMaster() {
    	return pflm_RenderPlayerMaster;
    }

    @Override
    public void superPreRenderCallback(Entity par1EntityLiving, float par2) {
    	super.preRenderCallback((EntityLiving) par1EntityLiving, par2);
    }

    protected void func_98191_a(EntityPlayer par1EntityPlayer)
    {
    	loadDownloadableImageTexture(par1EntityPlayer.skinUrl, par1EntityPlayer.getTexture());
    }

    protected void func_96449_a(EntityLiving par1EntityLiving, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        func_96450_a((EntityPlayer)par1EntityLiving, par2, par4, par6, par8Str, par9, par10);
    }

    protected void func_82408_c(EntityLiving par1EntityLiving, int par2, float par3)
    {
        func_82439_b((EntityPlayer)par1EntityLiving, par2, par3);
    }

    protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        rotatePlayer((EntityPlayer)par1EntityLiving, par2, par3, par4);
    }

    protected void func_98190_a(EntityLiving par1EntityLiving)
    {
        func_98191_a((EntityPlayer)par1EntityLiving);
    }
}

