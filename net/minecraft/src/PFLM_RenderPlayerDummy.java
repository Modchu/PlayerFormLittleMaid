package net.minecraft.src;

public abstract class PFLM_RenderPlayerDummy extends RenderLiving {

	public static PFLM_RenderPlayerDummyMaster pflm_RenderPlayerDummyMaster = null;

	public PFLM_RenderPlayerDummy(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}

	public abstract void superDoRenderLiving(Entity entity, double d, double d1, double d2, float f, float f1);
	public abstract void allModelInit(Entity entity, boolean debug);

}
