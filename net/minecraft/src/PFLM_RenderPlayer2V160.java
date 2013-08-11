package net.minecraft.src;

import java.awt.image.BufferedImage;

public class PFLM_RenderPlayer2V160 extends RenderPlayer2 implements PFLM_IRenderPlayer
{
	private ModelBiped modelBipedMain = new ModelBiped();
	private ModelBase modelBase = new ModelBiped();
	protected RenderBlocks renderBlocks = new RenderBlocks();
	protected float shadowSize;
	protected float shadowOpaque;
	public static PFLM_RenderPlayerV160 pflm_renderplayer;
	public static String[] armorFilenamePrefix;
	public static boolean resetFlag = false;
	public static boolean textureResetFlag = false;
	public static boolean firstPersonHandResetFlag = true;
	public static boolean initResetFlag = false;
	public static Object pflm_RenderPlayerSmart;
	public static Class PFLM_RenderPlayerSmart;

	public PFLM_RenderPlayer2V160() {
		pflm_renderplayer = new PFLM_RenderPlayerV160();
		if (RenderManager.instance != null) {
			pflm_renderplayer.setRenderManager(RenderManager.instance);
			setRenderManager(pflm_renderplayer.renderManager);
		}
		shadowSize = pflm_renderplayer.shadowSize;
		shadowOpaque = pflm_renderplayer.shadowOpaque;
		armorFilenamePrefix = pflm_renderplayer.pflm_RenderPlayerMaster.armorFilename;
	}

    protected int setArmorModel(AbstractClientPlayer entityplayer, int i, float f) {
    	return pflm_renderplayer.setArmorModel(entityplayer, i, f);
    }

    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f) {
    	return pflm_renderplayer.shouldRenderPass(entityliving, i, f);
    }

    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
    	pflm_renderplayer.preRenderCallback(entityliving, f);
    }

    public void shadersGlDisableWrapper(int i) {
    	pflm_renderplayer.pflm_RenderPlayerMaster.shadersGlDisableWrapper(i);
    }

    public void shadersGlEnableWrapper(int i) {
    	pflm_renderplayer.pflm_RenderPlayerMaster.shadersGlEnableWrapper(i);
    }

	public static void glEnableWrapper(String s, int i) {
		if (pflm_renderplayer != null) pflm_renderplayer.pflm_RenderPlayerMaster.glEnableWrapper(s, i);
	}

	public static void glDisableWrapper(String s, int i) {
		if (pflm_renderplayer != null) pflm_renderplayer.pflm_RenderPlayerMaster.glDisableWrapper(s, i);
	}

    public void doRenderLivingPFLM(PFLM_ModelData modelDataPlayerFormLittleMaid, EntityLivingBase entityliving, double d, double d1, double d2,
            float f, float f1) {
    	pflm_renderplayer.pflm_RenderPlayerMaster.doRenderLivingPFLM(modelDataPlayerFormLittleMaid, entityliving, d, d1, d2, f, f1);
    }

    public void doRenderPlayerFormLittleMaid(EntityPlayer entityplayer, double d, double d1, double d2, float f, float f1) {
    	pflm_renderplayer.pflm_RenderPlayerMaster.doRenderPlayerFormLittleMaid((AbstractClientPlayer) entityplayer, d, d1, d2, f, f1);
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
    	if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.instanceCheck(mod_PFLM_PlayerFormLittleMaid.pflm_main.itemRendererClass, pflm_renderplayer.renderManager.itemRenderer)) {
    		Modchu_Debug.Debug("check !renderManager");
    		ItemRenderer itemRenderer2 = mod_PFLM_PlayerFormLittleMaid.pflm_main.newInstanceItemRenderer();
    		if (itemRenderer2 != null) {
    			pflm_renderplayer.renderManager.itemRenderer =
    					renderManager.itemRenderer = itemRenderer2;
    			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.isHD) {
    				mod_PFLM_PlayerFormLittleMaid.pflm_main.initItemRendererHD = true;
    			} else {
    				mod_PFLM_PlayerFormLittleMaid.pflm_main.initItemRenderer = true;
    			}
    		}
    	}
	}

	protected void renderEquippedItems(EntityLivingBase entityliving, float f) {
    	pflm_renderplayer.renderEquippedItems((EntityPlayer)entityliving, f);
    }

    protected void renderSpecials(AbstractClientPlayer entityplayer, float f) {
    	pflm_renderplayer.renderSpecials(entityplayer, f);
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer) {
    	renderFirstPersonArm(entityplayer, 2);
    }

    public void renderFirstPersonArm(EntityPlayer entityplayer, int i) {
    	PFLM_ModelData modelDataPlayerFormLittleMaid = getPlayerData(entityplayer);
    	if (modelDataPlayerFormLittleMaid != null) ;else return;
    	doRenderSetting2(entityplayer, modelDataPlayerFormLittleMaid);
    	pflm_renderplayer.renderFirstPersonArm((AbstractClientPlayer) entityplayer, i);
    }

    public static boolean isActivatedForPlayer(AbstractClientPlayer entityplayer) {
    	if (pflm_renderplayer != null) return pflm_renderplayer.isActivatedForPlayer(entityplayer);
    	return false;
    }

    public PFLM_ModelData getPlayerData(EntityPlayer entityplayer) {
    	if (pflm_renderplayer != null) return pflm_renderplayer.getPlayerData(entityplayer);
    	return null;
    }

	public Object[] checkimage(BufferedImage bufferedimage) {
		if (pflm_renderplayer != null) return pflm_renderplayer.checkimage(bufferedimage);
		return null;
	}

	public int[] checkImageColor(BufferedImage bufferedimage, int i, int j) {
		if (pflm_renderplayer != null) return pflm_renderplayer.checkImageColor(bufferedimage, i, j);
		return null;
	}

    protected void renderLivingAt(EntityLivingBase entityliving, double d, double d1, double d2) {
    	pflm_renderplayer.renderLivingAt(entityliving, d, d1, d2);
    }

    protected void rotateCorpse(EntityLivingBase entityliving, float f, float f1, float f2) {
    	pflm_renderplayer.rotateCorpse(entityliving, f, f1, f2);
    }

//-@-147
    public void renderLivingLabel(EntityLivingBase entityplayer, String par2Str, double d, double d1, double d2, int i) {
    	pflm_renderplayer.renderLivingLabel(entityplayer, par2Str, d, d1, d2, i);
    }
//@-@147
/*//147delete
    protected void renderName(EntityPlayer entityplayer, double d, double d1, double d2) {
    	pflm_renderplayer.renderName(entityplayer, d, d1, d2);
    }
*///147delete

    public void clearPlayers() {
    	if (pflm_renderplayer != null) pflm_renderplayer.clearPlayers();
    }

    public void waitModeSetting(PFLM_ModelData modelDataPlayerFormLittleMaid, float f) {
    	pflm_renderplayer.pflm_RenderPlayerMaster.waitModeSetting(modelDataPlayerFormLittleMaid, f);
    }

    public float copyf(float f) {
    	return pflm_renderplayer.pflm_RenderPlayerMaster.copyf(f);
    }

    public void setHandedness(EntityPlayer entityplayer, int i) {
    	if (pflm_renderplayer != null) pflm_renderplayer.setHandedness(entityplayer, i);
    }

    protected void renderModel(EntityLivingBase par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7) {
    	pflm_renderplayer.renderModel(par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }

    public float getActionSpeed(PFLM_ModelData modelData) {
    	return pflm_renderplayer.getActionSpeed(modelData);
    }

    //smartMovingä÷òAÅ´
    public RenderManager getRenderManager() {
    	return pflm_renderplayer.getRenderManager();
    }

    @Override
    protected void renderPlayerSleep(AbstractClientPlayer var1, double var2, double var4, double var6) {
    	pflm_renderplayer.renderPlayerSleep(var1, var2, var4, var6);
    }

    @Override
    public void rotatePlayer(AbstractClientPlayer var1, float var2, float var3, float var4) {
    	pflm_renderplayer.rotatePlayer(var1, var2, var3, var4);
    }

    public void renderPlayerAt(AbstractClientPlayer var1, double var2, double var4, double var6) {
    	pflm_renderplayer.renderPlayerAt(var1, var2, var4, var6);
    }

    public static void renderGuiIngame(Minecraft var0) {
    	if (pflm_renderplayer != null) pflm_renderplayer.renderGuiIngame(var0);
    }
    //smartMovingä÷òAÅ™

    @Override
    public boolean getResetFlag() {
    	if (pflm_renderplayer != null) return pflm_renderplayer.getResetFlag();
    	return false;
    }

    @Override
    public void setResetFlag(boolean b) {
    	if (pflm_renderplayer != null) pflm_renderplayer.setResetFlag(b);
    }

	@Override
	public PFLM_RenderPlayerMaster getRenderPlayerMaster() {
		return pflm_renderplayer.getRenderPlayerMaster();
	}

	@Override
	public void removePlayer(EntityPlayer entityPlayer) {
		if (pflm_renderplayer != null) pflm_renderplayer.removePlayer(entityPlayer);
	}

	@Override
	public void setTextureResetFlag(boolean b) {
		if (pflm_renderplayer != null) pflm_renderplayer.setTextureResetFlag(b);
	}
}
