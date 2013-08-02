package net.minecraft.src;

public class PFLM_RenderGlobalAlt extends RenderGlobal
{
	public PFLM_RenderGlobalAlt(Minecraft minecraft)
    {
        super(minecraft);
    }
/*//152delete
    public PFLM_RenderGlobalAlt(Minecraft minecraft, RenderEngine renderengine)
    {
        super(minecraft, renderengine);
    }

	public void onEntityCreate(Entity entity)
    {
        entity.updateCloak();
        Minecraft minecraft = Minecraft.getMinecraft();

        if (entity.skinUrl != null)
        {
            minecraft.renderEngine.obtainImageData(entity.skinUrl, new ImageBufferDownload());
        }

        if (entity.cloakUrl != null)
        {
            minecraft.renderEngine.obtainImageData(entity.cloakUrl, new ImageBufferDownload());
        }
    }

    public void drawSelectionBox(EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f)
    {
        super.drawSelectionBox(entityplayer, movingobjectposition, i, itemstack, f);
    }
*///152delete
    public boolean hasCloudFog(double d, double d1, double d2, float f)
    {
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        float f1 = ((EntityPlayer)(entityplayersp)).yOffset - 1.62F;
        return super.hasCloudFog(d, d1 + (double)f1, d2, f);
    }
}
