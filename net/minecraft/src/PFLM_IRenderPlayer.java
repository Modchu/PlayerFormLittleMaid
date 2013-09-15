package net.minecraft.src;

import java.awt.image.BufferedImage;

public interface PFLM_IRenderPlayer {

	public static Object steveTexture = null;
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1);
	public PFLM_ModelData getPlayerData(EntityPlayer entityplayer);
	public Object[] checkimage(BufferedImage bufferedimage);
	public int[] checkImageColor(BufferedImage bufferedimage, int i, int j);
	public void clearPlayers();
	public void removePlayer(EntityPlayer entityPlayer);
	public void setHandedness(EntityPlayer entityplayer, int i);
	public float getActionSpeed(PFLM_ModelData modelData);
	public void setTextureResetFlag(boolean b);
	public PFLM_RenderPlayerMaster getRenderPlayerMaster();
	public boolean getResetFlag();
	public void setResetFlag(boolean b);
	public void superDoRenderLiving(Entity entity, double d, double d1, double d2, float f, float f1);
	public void sitSleepResetCheck(PFLM_ModelData modelData, EntityPlayer entityplayer);
	public void superRenderLivingAt(Entity par1EntityLivingBase, double par2, double par4, double par6);
	public void superPreRenderCallback(Entity par1EntityLiving, float par2);

}
