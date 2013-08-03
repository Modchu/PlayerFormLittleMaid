package net.minecraft.src;

import paulscode.sound.SoundSystem;

public class PFLM_GuiSelectWorldAether extends PFLM_GuiSelectWorld
{
    private int musicID;

    public PFLM_GuiSelectWorldAether(GuiScreen var1, int var2)
    {
        super(var1);
        this.musicID = var2;
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton var1)
    {
        if (var1.enabled)
        {
            if (var1.id == 2)
            {
                this.unloadbackround();
            }

            super.actionPerformed(var1);

            if (var1.id == 3)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.displayGuiScreen(new PFLM_GuiCreateWorldAether(this, GuiMainMenu.musicId));
            }
        }
    }

    protected void unloadbackround()
    {
        this.mc.changeWorld1((World)null);
        this.mc.theWorld = null;
        this.mc.thePlayer = null;
    }

    /**
     * Gets the selected world.
     */
    public void selectWorld(int var1)
    {
        this.unloadbackround();
        GuiMainMenu.mmactive = false;

        try
        {
            SoundSystem var2 = (SoundSystem)ModLoader.getPrivateValue(net.minecraft.src.SoundManager.class, (Object)null, 0);
            if(var2 == null) {
            	super.selectWorld(var1);
            	return;
            }
            var2.stop("sound_" + this.musicID);
            ModLoader.setPrivateValue(net.minecraft.src.SoundManager.class, this.mc.sndManager, "i", Integer.valueOf(6000));
        }
        catch (Exception var5)
        {
            if (var5 instanceof NoSuchFieldException)
            {
                try
                {
                    ModLoader.setPrivateValue(net.minecraft.src.SoundManager.class, this.mc.sndManager, "ticksBeforeMusic", Integer.valueOf(6000));
                }
                catch (Exception var4)
                {
                    var4.printStackTrace();
                }
            }
            else
            {
                var5.printStackTrace();
            }
        }

        GuiMainMenu.musicId = -1;
        GuiMainMenu.loadingWorld = true;
        super.selectWorld(var1);
    }
}
