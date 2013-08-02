package net.minecraft.src;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
/*//152delete
import net.minecraft.client.Minecraft;
*///152delete
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerV160 extends RenderPlayer implements PFLM_IRenderPlayer
{
	public static PFLM_RenderPlayerMaster pflm_RenderPlayerMaster;

	public PFLM_RenderPlayerV160() {
		pflm_RenderPlayerMaster = new PFLM_RenderPlayerMaster();
	}

    @Override
    protected int setArmorModel(AbstractClientPlayer entityplayer, int i, float f) {
    	return pflm_RenderPlayerMaster != null ? pflm_RenderPlayerMaster.setArmorModel((EntityPlayer) entityplayer, i, f) : -1;
    }

    protected int setArmorModel(MultiModelBaseBiped model, EntityPlayer entityplayer, int i, float f, int i2) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.setArmorModel(model, entityplayer, i, f, i2);
    	return -1;
   }

    @Override
    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f)
    {
    	if (pflm_RenderPlayerMaster != null) ;else return -1;
    	int i1 = setArmorModel((AbstractClientPlayer) entityliving, i, f);
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer) entityliving);
    	setRenderPassModel(modelData.modelFATT);
    	int PFLMVersion = mod_PFLM_PlayerFormLittleMaid.getPFLMVersion();
    	if ((PFLMVersion > 129
    			&& !mod_Modchu_ModchuLib.useInvisibilityArmor)
    			| (PFLMVersion > 129
    					&& mod_Modchu_ModchuLib.useInvisibilityArmor
    					&& !entityliving.isInvisible())
    					| PFLMVersion < 130) {
    		((MMM_ModelBaseDuo) renderPassModel).setArmorRendering(true);
    	} else ((MMM_ModelBaseDuo) renderPassModel).setArmorRendering(false);
    	return i1;
    	//return pflm_RenderPlayerMaster.shouldRenderPass((Entity) entityliving, i, f);
    }

    @Override
    protected void renderPlayerScale(AbstractClientPlayer entityliving, float f)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderPlayerScale((EntityPlayer) entityliving, f);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	if (pflm_RenderPlayerMaster != null) ;else return;
    	pflm_RenderPlayerMaster.doRender(entity, d, d1, d2, f, f1);
    	PFLM_ModelData modelData = getPlayerData((EntityPlayer)entity);
    	if (modelData != null) ;else return;
    	mainModel = modelData.modelMain;
    	setRenderPassModel(modelData.modelFATT);
    	d1 = pflm_RenderPlayerMaster.doRenderSettingY((EntityPlayer) entity, modelData, d1);
    	//Modchu_Debug.mDebug("doRender renderYawOffset="+((EntityPlayer) entity).renderYawOffset);
    	if (!mod_PFLM_PlayerFormLittleMaid.oldRender) super.func_130000_a((EntityLivingBase) entity, d, d1, d2, f, f1);
    	else pflm_RenderPlayerMaster.oldDoRenderLivingPFLM(modelData, entity, d, d1, d2, f, f1);
    	modelData.modelMain.setCapsValue(modelData.caps_aimedBow, false);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entityliving, float f)
    {
    	renderSpecials((AbstractClientPlayer)entityliving, f);
    }

    @Override
    protected void renderSpecials(AbstractClientPlayer entityplayer, float f)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderSpecials(((EntityPlayer) entityplayer), f);
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer entityplayer) {
    	pflm_RenderPlayerMaster.renderManager = renderManager;
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderFirstPersonArm(entityplayer, 2);
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer, int i) {
    	//olddays導入時に2以外のint付きで呼ばれる。
    	pflm_RenderPlayerMaster.renderManager = renderManager;
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderFirstPersonArm(entityplayer, i);
    }

    public static boolean isActivatedForPlayer(AbstractClientPlayer entityplayer) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.isActivatedForPlayer(((EntityPlayer) entityplayer));
    	return false;
    }

    public PFLM_ModelData getPlayerData(EntityPlayer entityplayer) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.getPlayerData(entityplayer);
    	return null;
    }

    public Object[] checkimage(BufferedImage bufferedimage) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.checkimage(bufferedimage);
    	return null;
    }

    public int[] checkImageColor(BufferedImage bufferedimage, int i, int j)
    {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.checkImageColor(bufferedimage, i, j);
    	return null;
    }

    public void renderLivingLabel(EntityLivingBase entityplayer, String par2Str, double d, double d1, double d2, int i)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderLivingLabel(entityplayer, par2Str, d, d1, d2, i);
    }

    protected void renderName(EntityPlayer entityplayer, double d, double d1, double d2)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderLivingLabel(entityplayer, null, d, d1, d2, -1);
    }

    public void clearPlayers() {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.clearPlayers();
    }

    public void removePlayer(EntityPlayer entityPlayer) {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.removePlayer(entityPlayer);
    }

    public void setHandedness(EntityPlayer entityplayer, int i) {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.setHandedness(entityplayer, i);
    }

    @Override
    protected void renderModel(EntityLivingBase par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderModel(par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }

    public float getActionSpeed(PFLM_ModelData modelData) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.getActionSpeed(modelData);
    	return 0.0F;
    }

    @Override
    protected ResourceLocation func_110775_a(Entity entity) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.func_110775_a(entity);
    	return null;
    }

    protected ResourceLocation func_110775_a(Entity entity, int i) {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.func_110775_a(entity, i);
    	return null;
   }

    protected void setResourceLocation(Entity entity, ResourceLocation resourceLocation) {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.setResourceLocation(entity, resourceLocation);
    }

    protected void setResourceLocation(Entity entity, int i, ResourceLocation resourceLocation) {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.setResourceLocation(entity, i, resourceLocation);
    }

    protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
    {
        renderPlayerSleep((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6);
    }

    @Override
    protected void renderPlayerSleep(AbstractClientPlayer var1, double var2, double var4, double var6)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderPlayerSleep(((EntityPlayer) var1), var2, var4, var6);
    }

    @Override
    public void rotatePlayer(AbstractClientPlayer var1, float var2, float var3, float var4)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.rotatePlayer(((EntityPlayer) var1),var2, var3, var4);
    }

    //smartMoving関連↓
    public RenderManager getRenderManager() {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.getRenderManager();
    	return null;
    }

    public void renderPlayerAt(AbstractClientPlayer var1, double var2, double var4, double var6)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderPlayerAt(((EntityPlayer) var1), var2, var4, var6);
    }

    public static void renderGuiIngame(Minecraft var0)
    {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.renderGuiIngame(var0);
    }
    //smartMoving関連↑

    @Override
    public boolean getResetFlag() {
    	if (pflm_RenderPlayerMaster != null) return pflm_RenderPlayerMaster.resetFlag;
    	return false;
    }

    @Override
    public void setResetFlag(boolean b) {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.resetFlag = b;
    }

    @Override
    public PFLM_RenderPlayerMaster getRenderPlayerMaster() {
    	return pflm_RenderPlayerMaster;
    }

	@Override
	public void setTextureResetFlag(boolean b) {
    	if (pflm_RenderPlayerMaster != null) pflm_RenderPlayerMaster.textureResetFlag = b;
	}
}

