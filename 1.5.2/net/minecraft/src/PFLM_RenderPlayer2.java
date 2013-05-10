package net.minecraft.src;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;

public class PFLM_RenderPlayer2 extends RenderPlayer2
{
	private ModelBiped modelBipedMain = new ModelBiped();
	private ModelBase modelBase = new ModelBiped();
	protected RenderBlocks renderBlocks = new RenderBlocks();
	protected float shadowSize;
	protected float shadowOpaque;
	public static PFLM_RenderPlayer pflm_renderplayer;
	public static String[] armorFilenamePrefix;
	public static boolean resetFlag = false;
	public static boolean textureResetFlag = false;
	public static boolean firstPersonHandResetFlag = true;
	public static boolean initResetFlag = false;
	public static Object pflm_RenderPlayerSmart;
	public static Class PFLM_RenderPlayerSmart;

	public PFLM_RenderPlayer2() {
		pflm_renderplayer = new PFLM_RenderPlayer();
		if (RenderManager.instance != null) {
			pflm_renderplayer.setRenderManager(RenderManager.instance);
			setRenderManager(pflm_renderplayer.renderManager);
		}
		shadowSize = pflm_renderplayer.shadowSize;
		shadowOpaque = pflm_renderplayer.shadowOpaque;
		armorFilenamePrefix = pflm_renderplayer.armorFilename;
	}

    protected int setArmorModel(EntityPlayer entityplayer, int i, float f) {
    	return pflm_renderplayer.setArmorModel(entityplayer, i, f);
    }

    protected int shouldRenderPass(EntityLiving entityliving, int i, float f) {
    	return pflm_renderplayer.shouldRenderPass(entityliving, i, f);
    }

    protected void preRenderCallback(EntityLiving entityliving, float f) {
    	pflm_renderplayer.preRenderCallback(entityliving, f);
    }

    public void shadersGlDisableWrapper(int i) {
    	pflm_renderplayer.shadersGlDisableWrapper(i);
    }

    public void shadersGlEnableWrapper(int i) {
    	pflm_renderplayer.shadersGlEnableWrapper(i);
    }

	public static void glEnableWrapper(String s, int i) {
		if (pflm_renderplayer != null) pflm_renderplayer.glEnableWrapper(s, i);
	}

	public static void glDisableWrapper(String s, int i) {
		if (pflm_renderplayer != null) pflm_renderplayer.glDisableWrapper(s, i);
	}

    public void doRenderLivingPFLM(PFLM_ModelData modelDataPlayerFormLittleMaid, EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
    	pflm_renderplayer.doRenderLivingPFLM(modelDataPlayerFormLittleMaid, entityliving, d, d1, d2, f, f1);
    }

    public void doRenderPlayerFormLittleMaid(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1) {
    	pflm_renderplayer.doRenderPlayerFormLittleMaid(entityplayer, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData((EntityPlayer) entity);
    	if (modelDataPlayerFormLittleMaid != null) ;else return;
    	doRenderSetting2(((EntityPlayer) entity), modelDataPlayerFormLittleMaid);
    	pflm_renderplayer.doRender(entity, d, d1, d2, f, f1);
    }

    private void doRenderSetting2(EntityPlayer entityPlayer, PFLM_ModelData modelDataPlayerFormLittleMaid) {
    	if (pflm_renderplayer.renderManager != null) ;
    	else if (RenderManager.instance != null) {
    		pflm_renderplayer.setRenderManager(RenderManager.instance);
    		setRenderManager(pflm_renderplayer.renderManager);
    	}
    	if (!mod_PFLM_PlayerFormLittleMaid.instanceCheck(mod_PFLM_PlayerFormLittleMaid.itemRendererClass, pflm_renderplayer.renderManager.itemRenderer)) {
    		Modchu_Debug.Debug("check !renderManager");
    		ItemRenderer itemRenderer2 = mod_PFLM_PlayerFormLittleMaid.newInstanceItemRenderer();
    		if (itemRenderer2 != null) {
    			pflm_renderplayer.renderManager.itemRenderer =
    					renderManager.itemRenderer = itemRenderer2;
    			if (mod_PFLM_PlayerFormLittleMaid.isHD) {
    				mod_PFLM_PlayerFormLittleMaid.initItemRendererHD = true;
    			} else {
    				mod_PFLM_PlayerFormLittleMaid.initItemRenderer = true;
    			}
    		}
    	}
	}

	protected void renderEquippedItems(EntityLiving entityliving, float f) {
    	pflm_renderplayer.renderEquippedItems((EntityPlayer)entityliving, f);
    }

    protected void renderSpecials(EntityPlayer entityplayer, float f) {
    	pflm_renderplayer.renderSpecials(entityplayer, f);
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer) {
    	renderFirstPersonArm(entityplayer, 2);
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer, int i) {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) ;else return;
    	doRenderSetting2(((EntityPlayer) entityplayer), modelDataPlayerFormLittleMaid);
    	pflm_renderplayer.renderFirstPersonArm(entityplayer, i);
    }

    public static boolean isActivatedForPlayer(EntityPlayer entityplayer) {
    	if (pflm_renderplayer != null) return pflm_renderplayer.isActivatedForPlayer(entityplayer);
    	return false;
    }

    public static PFLM_ModelData getPlayerData(EntityPlayer entityplayer) {
    	if (pflm_renderplayer != null) return pflm_renderplayer.getPlayerData(entityplayer);
    	return null;
    }

	public static Object[] checkimage(BufferedImage bufferedimage) {
		if (pflm_renderplayer != null) return pflm_renderplayer.checkimage(bufferedimage);
		return null;
	}

	public static int[] checkImageColor(BufferedImage bufferedimage, int i, int j) {
		if (pflm_renderplayer != null) return pflm_renderplayer.checkImageColor(bufferedimage, i, j);
		return null;
	}

    protected void renderLivingAt(EntityLiving entityliving, double d, double d1, double d2) {
    	pflm_renderplayer.renderLivingAt(entityliving, d, d1, d2);
    }

    protected void rotateCorpse(EntityLiving entityliving, float f, float f1, float f2) {
    	pflm_renderplayer.rotateCorpse(entityliving, f, f1, f2);
    }

//-@-147
    public void renderLivingLabel(EntityLiving entityplayer, String par2Str, double d, double d1, double d2, int i) {
    	pflm_renderplayer.renderLivingLabel(entityplayer, par2Str, d, d1, d2, i);
    }
//@-@147
/*//147delete
    protected void renderName(EntityPlayer entityplayer, double d, double d1, double d2) {
    	pflm_renderplayer.renderName(entityplayer, d, d1, d2);
    }
*///147delete

    public static void clearPlayers() {
    	if (pflm_renderplayer != null) pflm_renderplayer.clearPlayers();
    }

    public void waitModeSetting(PFLM_ModelData modelDataPlayerFormLittleMaid, float f) {
    	pflm_renderplayer.waitModeSetting(modelDataPlayerFormLittleMaid, f);
    }

    public float copyf(float f) {
    	return pflm_renderplayer.copyf(f);
    }

    public static void setHandedness(EntityPlayer entityplayer, int i) {
    	if (pflm_renderplayer != null) pflm_renderplayer.setHandedness(entityplayer, i);
    }

    protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7) {
    	pflm_renderplayer.renderModel(par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }

    public float getActionSpeed() {
    	return pflm_renderplayer.getActionSpeed();
    }

    //smartMovingä÷òAÅ´
    public RenderManager getRenderManager() {
    	return pflm_renderplayer.getRenderManager();
    }

    @Override
    protected void renderPlayerSleep(EntityPlayer var1, double var2, double var4, double var6) {
    	pflm_renderplayer.renderPlayerSleep(var1, var2, var4, var6);
    }

    @Override
    public void rotatePlayer(EntityPlayer var1, float var2, float var3, float var4) {
    	pflm_renderplayer.rotatePlayer(var1, var2, var3, var4);
    }

    public void renderPlayerAt(EntityPlayer var1, double var2, double var4, double var6) {
    	pflm_renderplayer.renderPlayerAt(var1, var2, var4, var6);
    }

    public static void renderGuiIngame(Minecraft var0) {
    	if (pflm_renderplayer != null) pflm_renderplayer.renderGuiIngame(var0);
    }
    //smartMovingä÷òAÅ™
}
