package net.minecraft.src;


public interface PFLM_IRenderPlayer
{
    void initialize(MultiModelSmart modelplayerformlittlemaidsmart, float var4);

    void superRenderPlayer(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9);

    void superDrawFirstPersonHand();

    void superRotatePlayer(EntityPlayer var1, float var2, float var3, float var4);

    void superRenderPlayerAt(EntityPlayer var1, double var2, double var4, double var6);

    void superRenderSpecials(EntityPlayer var1, float var2);
}
