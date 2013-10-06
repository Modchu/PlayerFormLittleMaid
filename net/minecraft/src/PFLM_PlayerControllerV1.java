package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PFLM_PlayerControllerV1 extends PlayerControllerMP {

    public static PFLM_PlayerControllerMaster pflm_PlayerControllerMaster;

    public PFLM_PlayerControllerV1(Minecraft minecraft, NetClientHandler par2NetClientHandler) {
    	super(minecraft, par2NetClientHandler);
    	pflm_PlayerControllerMaster = new PFLM_PlayerControllerMaster(minecraft, par2NetClientHandler);
    }

    public EntityClientPlayerMP func_78754_a(World world) {
    	EntityClientPlayerMP entityClientPlayerMP = (EntityClientPlayerMP) pflm_PlayerControllerMaster.createPlayer(world);
    	if (entityClientPlayerMP != null) return entityClientPlayerMP;
    	return super.func_78754_a(world);
    }
}
