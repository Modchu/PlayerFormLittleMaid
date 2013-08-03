package net.minecraft.src;

import java.text.Format;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.minecraft.client.Minecraft;
/*//FMLdelete
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
*///FMLdelete
public class PFLM_GuiOthersPlayerSlot extends GuiSlot {
	protected Minecraft mc;
	public PFLM_GuiOthersPlayerIndividualCustomizeSelect ownerGui;
	private float xSize_lo;
	private float ySize_lo;
	private int selected;
	private World popWorld;

	public PFLM_GuiOthersPlayerSlot(Minecraft par1Minecraft,
			int par2, int par3, int par4, int par5, int par6) {
		super(par1Minecraft, par2, par3, par4, par5, par6);
	}

	public PFLM_GuiOthersPlayerSlot(
			Minecraft par1Minecraft,
			PFLM_GuiOthersPlayerIndividualCustomizeSelect gui,
			World world) {
		super(par1Minecraft, gui.width, gui.height, 32, gui.height - 52, 36);
		popWorld = world;
		ownerGui = gui;
		selected = 0;
		mc = par1Minecraft;
	}

	@Override
	protected int getSize() {
		return PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size();
	}

	@Override
	protected int getContentHeight()
	{
		return getSize() * 36;
	}

	@Override
	protected void elementClicked(int i, boolean flag) {
		//flag = true 左ダブルクリック
		selected = i;
		String s = PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.get(i);
		if (flag) openGuiCustomize();
	}

	public void openGuiCustomize() {
		if (PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size() < 1) return;
		String s = PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.get(selected);
		PFLM_GuiOthersPlayerIndividualCustomize gui = new PFLM_GuiOthersPlayerIndividualCustomize(ownerGui, mc, popWorld, s);
		mc.displayGuiScreen(gui);
		PFLM_GuiOthersPlayerIndividualCustomize.setChangeMode(PFLM_GuiOthersPlayerIndividualCustomize.modeOthersSettingOffline);
		String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.playerLocalData.get(s);
		if (t != null) {
			gui.setTextureName(t[0]);
			gui.setTextureArmorName(t[1]);
			gui.setMaidColor(Integer.valueOf(t[2]));
			gui.setModelScale(Float.valueOf(t[3]));
			gui.setChangeMode(Integer.valueOf(t[4]));
		}
		gui.initGui();
	}

	@Override
	protected boolean isSelected(int i) {
		if (PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size() > i && i == selected) {
			return true;
		}
		return false;
	}

	@Override
	protected void drawBackground() {
	}

	@Override
	public void drawScreen(int i, int j, float f)
	{
		super.drawScreen(i, j, f);
		xSize_lo = i;
		ySize_lo = j;
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		if (PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size() <= i) return;
		String s = PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.get(i);
		ownerGui.drawString(ownerGui.fontRenderer, s, j + 70, k + 8, 0xffffff);
		boolean setting = mod_PFLM_PlayerFormLittleMaid.playerLocalData.get(s) != null;
		String s0 = setting ? "Setting" : "Non Settings";
		ownerGui.drawString(ownerGui.fontRenderer, s0, j + 70, k + 17, 0xffffff);
		EntityLiving entityliving = (EntityLiving)PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerDummyEntityList.get(s);

		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
		GL11.glPushMatrix();
		float f1 = 15F;
		if (entityliving.height > 2F) {
			f1 = f1 * 2F / entityliving.height;
		}
		GL11.glTranslatef(j + 30, k + 30, 50F + f1);
		GL11.glScalef(-f1, f1, f1);
		GL11.glRotatef(180F, 180F, 0.0F, 1.0F);
		float f5 = (float)(j + 30) - xSize_lo;
		float f6 = (float)((k + 30) - 10) - ySize_lo;
		GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
		entityliving.renderYawOffset = (float)Math.atan(f5 / 40F) * 20F;
		entityliving.rotationYaw = (float)Math.atan(f5 / 40F) * 40F;
		entityliving.rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
		entityliving.prevRotationYawHead = entityliving.rotationYawHead;
		entityliving.rotationYawHead = entityliving.rotationYaw;
		GL11.glTranslatef(0.0F, entityliving.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180F;
		if (setting) {
			String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.playerLocalData.get(s);
			boolean flag = false;
			if(t[4] != null) {
				if (Integer.valueOf(t[4]) == PFLM_GuiOthersPlayerIndividualCustomize.modeOthersSettingOffline) {
					((PFLM_EntityPlayerDummy) entityliving).texture = mod_PFLM_PlayerFormLittleMaid.textureManagerGetTextureName(t[0], Integer.valueOf(t[2]));
					((PFLM_EntityPlayerDummy) entityliving).maidColor = Integer.valueOf(t[2]);
					((PFLM_EntityPlayerDummy) entityliving).textureModel = (Object[]) getTextureModel(t[0], t[1]);
					((PFLM_EntityPlayerDummy) entityliving).textureName = t[0];
					((PFLM_EntityPlayerDummy) entityliving).textureArmorName = t[1];
					((PFLM_EntityPlayerDummy) entityliving).textureArmor0 = mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureArmor0;
					((PFLM_EntityPlayerDummy) entityliving).textureArmor1 = mod_PFLM_PlayerFormLittleMaid.othersIndividualTextureArmor1;
					((PFLM_EntityPlayerDummy) entityliving).modelScale = 0.0F;
					((PFLM_EntityPlayerDummy) entityliving).showArmor = false;
					((PFLM_EntityPlayerDummy) entityliving).others = false;
					RenderManager.instance.renderEntityWithPosYaw(entityliving, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
					flag = true;
				}
			}
			if (!flag) {
				String s1 = PFLM_GuiOthersPlayerIndividualCustomize.getChangeModeString(Integer.valueOf(t[4]));
				ownerGui.fontRenderer.drawString(s1.toString(), j + 10, k + 17, 0xffffff);
			}
		}
		GL13.glMultiTexCoord2f(33985 /*GL_TEXTURE1_ARB*/, 240.0F, 240.0F);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	}

	private Object[] getTextureModel(String s1, String s2) {
		Object amodelPlayerFormLittleMaid = null;
		Object ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox(s1);
		Object[] models = mod_PFLM_PlayerFormLittleMaid.getTextureBoxModels(ltb);
		if (ltb == null) {
			if (s1.indexOf("Biped") == -1) return mod_PFLM_PlayerFormLittleMaid.getDefaultModel();
			ltb = mod_PFLM_PlayerFormLittleMaid.getTextureBox("Biped");
			models = mod_PFLM_PlayerFormLittleMaid.getTextureBoxModels(ltb);
		}
		return models;
	}

	public void deletePlayerLocalData() {
		if (PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size() < 1) return;
		mod_PFLM_PlayerFormLittleMaid.playerLocalData.remove(PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.get(selected));
		mod_PFLM_PlayerFormLittleMaid.clearPlayers();
	}
}
