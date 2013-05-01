package net.minecraft.src;

import cpw.mods.fml.common.ReflectionHelper;
import paulscode.sound.SoundSystem;

public class PFLM_GuiCreateWorldAether extends PFLM_GuiCreateWorld
{
    private int musicID;

    public PFLM_GuiCreateWorldAether(GuiScreen var1, int var2)
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
            if (var1.id == 0)
            {
                this.mc.changeWorld1((World)null);
                this.mc.theWorld = null;
                this.mc.thePlayer = null;
                GuiMainMenu.mmactive = false;

                try
                {
                    SoundSystem var2 = (SoundSystem)ModLoader.getPrivateValue(SoundManager.class, (Object)null, 0);
                    var2.stop("sound_" + this.musicID);
                    ModLoader.setPrivateValue(SoundManager.class, this.mc.sndManager, ReflectionHelper.obfuscation ? "i" : "ticksBeforeMusic", Integer.valueOf(6000));
                }
                catch (Exception var3)
                {
                    var3.printStackTrace();
                }

                GuiMainMenu.musicId = -1;
                GuiMainMenu.loadingWorld = true;
            }

            super.actionPerformed(var1);
        }
    }
}
