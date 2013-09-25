package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class PFLM_GuiModelSelectBase extends PFLM_GuiBase {

	protected static boolean drawEntitySetFlag;
	//private Render render;

	public PFLM_GuiModelSelectBase(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		drawEntitySetFlag = true;
	}

	public void setTextureArmorPackege() {
		setTextureArmorPackege(2);
	}

	public void setTextureArmorPackege(int i) {
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
		String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName), i);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
		Object ltb = mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege(s);
		if (ltb != null) ;else {
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, "default");
		}
		if (PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName) != null) ;else PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
	}

	public void setTextureValue() {
		if (getTextureName() == null) setTextureName("default");
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, getTextureName());
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, getColor());
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, getTextureArmorName());
		setTextureArmorPackege();
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, getTextureArmorName());
	}

	public void setArmorTextureValue() {
	}

	public void drawMobModel(int i, int j , int x, int y, int x2, int y2, float f, float f1, EntityLiving entity, boolean move) {
		GL11.glPushMatrix();
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (!move) {
			if (entity.height > 2F) {
				f = f * 2F / entity.height;
			}
		}
		GL11.glTranslatef(x, y, 50F + f1);
		GL11.glScalef(-f, f, f);
		GL11.glRotatef(180F, 0F, 0.0F, 0.0F);
		//RenderHelper.enableStandardItemLighting();
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
		else if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender
				| mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 160) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		if (move) {
			float f5 = (float)(width / 2 + x2) - i;
			float f6 = (float)(height / 2 + y2) - j;
			if (mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef((float)Math.atan(f5 / 40F) * 30F, 0.0F, 1.0F, 0.0F);
			entity.rotationYaw = -(float)Math.atan(f5 / 40F) * 20F;
			entity.rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
		} else {
			entity.rotationYaw = 0F;
			entity.rotationPitch = 0F;
		}
		GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
		//if (render != null) ;else render = RenderManager.instance.getEntityRenderObject(entity);
		//if (render != null) render.doRender(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		//else
		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		//GL13.glMultiTexCoord2f(33985 /*GL_TEXTURE1_ARB*/, 240.0F, 240.0F);
		GL11.glPopMatrix();
		//RenderHelper.disableStandardItemLighting();
		//GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		entity.rotationYaw = 0F;
		entity.rotationPitch = 0F;
	}

	public void drawMobModel2(int i, int j , int x, int y, int x2, int y2, float f, float f1, boolean move) {
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
		GL11.glPushMatrix();
		if (drawEntity.height > 2F) {
			f = f * 2F / drawEntity.height;
		}
		GL11.glTranslatef(x, y, 50F + f1);
		GL11.glScalef(-f, f, f);
		GL11.glRotatef(180F, 180F, 0.0F, 1.0F);
		if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender
				&& mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		if (move) {
			float f5 = (float)(width / 2 + x2) - i;
			float f6 = (float)(height / 2 + y2) - j;
			GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
			//GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef((float)Math.atan(-f5 / 40F) * 30F, 0.0F, 1.0F, 0.0F);
			drawEntity.rotationYaw = -(float)Math.atan(f5 / 40F) * 20F;
			drawEntity.rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
			//entity.renderYawOffset = (float)Math.atan(f5 / 40F) * 20F;
			//entity.prevRotationYawHead = entity.rotationYawHead;
			//entity.rotationYawHead = entity.rotationYaw;
			drawEntity.renderYawOffset = 0F;
			drawEntity.prevRotationYawHead = 0F;
			drawEntity.rotationYawHead = 0F;
		} else {
			drawEntity.rotationYaw = 0F;
			drawEntity.rotationPitch = 0F;
		}
		GL11.glTranslatef(0.0F, drawEntity.yOffset, 0.0F);
		//RenderManager.instance.playerViewY = 180F;
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		RenderManager.instance.renderEntityWithPosYaw(drawEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		//GL13.glMultiTexCoord2f(33985 /*GL_TEXTURE1_ARB*/, 240.0F, 240.0F);
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
		//RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	public void selected(String textureName, String textureArmorName, int color, boolean armorMode) {
		if (!armorMode) {
			setTextureName(textureName);
			setColor(color);
		}
		setTextureArmorName(textureArmorName);
	}

	@Override
	public void memoryRelease() {
		//render = null;
	}

	public abstract String getTextureName();
	public abstract void setTextureName(String s);
	public abstract String getTextureArmorName();
	public abstract void setTextureArmorName(String s);
	public abstract int getColor();
	public abstract void setColor(int i);
	public abstract float getScale();
	public abstract void setScale(float f);
	public abstract void modelChange();

}