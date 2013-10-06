package net.minecraft.src;

import java.awt.image.BufferedImage;

public abstract class PFLM_RenderPlayer extends RenderPlayer
{
	public static PFLM_RenderPlayerMaster pflm_RenderPlayerMaster;
	public abstract PFLM_RenderPlayerMaster getRenderPlayerMaster();
	public abstract void superDoRenderLiving(Entity entity, double d, double d1, double d2, float f, float f1);
	public abstract void superRenderLivingAt(Entity par1EntityLivingBase, double par2, double par4, double par6);
	public abstract void superPreRenderCallback(Entity par1EntityLiving, float par2);
	public abstract void superRotateCorpse(Entity par1EntityLivingBase, float par2, float par3, float par4);

}