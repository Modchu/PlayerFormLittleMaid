package modchu.pflm;import net.minecraft.client.model.ModelBase;import net.minecraft.client.renderer.entity.RenderManager;import net.minecraft.client.renderer.entity.RenderPlayer;import net.minecraft.entity.Entity;public abstract class PFLM_RenderPlayer extends RenderPlayer{	public static PFLM_RenderPlayerMaster pflm_RenderPlayerMaster;	public abstract PFLM_RenderPlayerMaster getRenderPlayerMaster();	public abstract void superDoRenderLiving(Entity entity, double d, double d1, double d2, float f, float f1);	public abstract void superRenderLivingAt(Entity par1EntityLivingBase, double par2, double par4, double par6);	public abstract void superPreRenderCallback(Entity par1EntityLiving, float par2);	public abstract void superRotateCorpse(Entity par1EntityLivingBase, float par2, float par3, float par4);	public abstract ModelBase getRenderPassModel();	public abstract RenderManager getRenderManager();}