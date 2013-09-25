package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class PFLM_GuiOthersPlayerSlotMaster extends PFLM_GuiModelSelectBase {
	public PFLM_GuiOthersPlayerIndividualCustomizeSelect ownerGui;
	private int selected;
	private int mouseX;
	private int mouseY;

	public PFLM_GuiOthersPlayerSlotMaster(Object par1Minecraft,
			int par2, int par3, int par4, int par5, int par6) {
		this((PFLM_GuiBase)null, (World)null);
	}

	public PFLM_GuiOthersPlayerSlotMaster(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		popWorld = world;
		ownerGui = (PFLM_GuiOthersPlayerIndividualCustomizeSelect) par1GuiScreen;
		selected = 0;
		PFLM_RenderPlayerDummyMaster.showArmor = false;
	}

	protected int getSize() {
		return PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size();
	}

	protected int getContentHeight()
	{
		return getSize() * 36;
	}

	protected void elementClicked(int i, boolean flag) {
		//flag = true 左ダブルクリック
		selected = i;
		String s = PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.get(i);
		if (flag) openGuiCustomize();
	}

	public void openGuiCustomize() {
		if (PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size() < 1) return;
		String s = PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.get(selected);
		PFLM_GuiOthersPlayerIndividualCustomize gui = new PFLM_GuiOthersPlayerIndividualCustomize(ownerGui, popWorld, s);
		Modchu_Reflect.invokeMethod("Minecraft", "func_71373_a", "displayGuiScreen", new Class[]{ GuiScreen.class }, mod_Modchu_ModchuLib.modchu_Main.getMinecraft(), new Object[]{ gui });
		//mc.displayGuiScreen(gui);
		gui.setChangeMode(PFLM_GuiOthersPlayerIndividualCustomize.modeOthersSettingOffline);
		String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.pflm_main.playerLocalData.get(s);
		if (t != null) {
			gui.setTextureName(t[0]);
			gui.setTextureArmorName(t[1]);
			gui.setColor(Integer.valueOf(t[2]));
			gui.setScale(Float.valueOf(t[3]));
			gui.setChangeMode(Integer.valueOf(t[4]));
		}
		gui.initGui();
	}

	protected boolean isSelected(int i) {
		if (PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size() > i && i == selected) {
			return true;
		}
		return false;
	}

	protected void drawBackground() {
	}

	public void drawScreen(int i, int j, float f)
	{
		mouseX = i;
		mouseY = j;
	}

	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		if (PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size() <= i) return;
		String s = PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.get(i);
		ownerGui.drawString(ownerGui.fontRenderer, s, j + 70, k + 8, 0xffffff);
		boolean setting = mod_PFLM_PlayerFormLittleMaid.pflm_main.playerLocalData.get(s) != null;
		String s0 = setting ? "Setting" : "Non Settings";
		ownerGui.drawString(ownerGui.fontRenderer, s0, j + 70, k + 17, 0xffffff);
		if (setting) {
			String t[] = (String[]) mod_PFLM_PlayerFormLittleMaid.pflm_main.playerLocalData.get(s);
			boolean flag = false;
			if(t[4] != null) {
				if (Integer.valueOf(t[4]) == PFLM_GuiOthersPlayerIndividualCustomize.modeOthersSettingOffline) {
					setTextureValue(t[0], t[1], Integer.valueOf(t[2]));
					PFLM_RenderPlayerDummyMaster.allModelInit(drawEntity, false);
					float f5 = -((float)(width / 2) - i) + ((float)(j + 30) - i);
					float f6 = -((float)(height / 2) - j) + ((float)((k + 30) - 10) - j);
		    		drawMobModel2(mouseX, mouseY, j + 30, k + 30, (int)f5, (int)f6, 15F, 15F, true);
					flag = true;
				}
			}
			if (!flag) {
				String s1 = PFLM_GuiOthersPlayerIndividualCustomize.getChangeModeString(Integer.valueOf(t[4]));
				ownerGui.fontRenderer.drawString(s1.toString(), j + 10, k + 17, 0xffffff);
			}
		}
/*
		GL11.glEnable(32826);
		GL11.glEnable(2903);
		GL11.glPushMatrix();
		float f1 = 15F;
		if (entityliving.height > 2F) {
			f1 = f1 * 2F / entityliving.height;
		}
		GL11.glTranslatef(j + 30, k + 30, 50F + f1);
		GL11.glScalef(-f1, f1, f1);
		GL11.glRotatef(180F, 180F, 0.0F, 1.0F);
		if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender
				&& mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		float f5 = (float)(j + 30) - i;
		float f6 = (float)((k + 30) - 10) - j;
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
		GL13.glMultiTexCoord2f(33985, 240.0F, 240.0F);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826);
*/
	}

	public void setTextureValue(String texture, String armorTexture, int color) {
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, texture);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, color);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, armorTexture);
		setTextureArmorPackege();
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, armorTexture);
	}

	public void setTextureArmorPackege() {
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
		String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName), 2);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
	}

	public void deletePlayerLocalData() {
		if (PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.size() < 1) return;
		mod_PFLM_PlayerFormLittleMaid.pflm_main.playerLocalData.remove(PFLM_GuiOthersPlayerIndividualCustomizeSelect.playerList.get(selected));
		mod_PFLM_PlayerFormLittleMaid.pflm_main.clearPlayers();
	}

	@Override
	public String getTextureName() {
		return null;
	}

	@Override
	public void setTextureName(String s) {
	}

	@Override
	public String getTextureArmorName() {
		return null;
	}

	@Override
	public void setTextureArmorName(String s) {
	}

	@Override
	public int getColor() {
		return -1;
	}

	@Override
	public void setColor(int i) {
	}

	@Override
	public float getScale() {
		return 0.0F;
	}

	@Override
	public void setScale(float f) {
	}

	@Override
	public void modelChange() {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
	}
}
