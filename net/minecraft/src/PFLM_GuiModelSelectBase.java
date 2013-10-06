package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class PFLM_GuiModelSelectBase extends PFLM_GuiBase {

	protected static boolean drawEntitySetFlag;
	protected boolean buttonOnline = false;
	protected boolean buttonRandom = false;
	protected boolean buttonScale = false;
	protected boolean buttonParts = false;
	protected boolean buttonPlayer = false;
	protected boolean buttonReturn = false;
	protected boolean buttonShowArmor = false;
	//private Render render;

	public PFLM_GuiModelSelectBase(PFLM_GuiBase par1GuiScreen, World world) {
		super(par1GuiScreen, world);
		drawEntitySetFlag = true;
	}

	@Override
	public void initGui() {
		buttonList.clear();
		int x = width / 2 + 55;
		int y = height / 2 - 85;
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 200, x, y + 100, 75, 20, "Save" }));
		buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 13, x, y + 85, 75, 15, "ChangeMode" }));
		if (!buttonPlayer) buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 58, x + 75, y + 55, 75, 15, "Handedness" }));
		if (buttonReturn) buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 201, x + 75, y + 100, 75, 20, "Return" }));
		if (!buttonRandom
				&& !buttonPlayer) {
			if(!buttonOnline) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 56, x - 10, y + 10, 85, 15, "ModelListSelect" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 50, x + 40, y + 25, 15, 15, "<" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 51, x + 55, y + 25, 15, 15, ">" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 52, x + 40, y + 40, 15, 15, "-" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 53, x + 55, y + 40, 15, 15, "+" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 54, x + 40, y + 55, 15, 15, "<" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 55, x + 55, y + 55, 15, 15, ">" }));
			}
		}
		if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange
				&& !buttonPlayer
				&& buttonScale) {
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 3, width / 2 - 170, height / 2 - 40, 50, 20, "Default" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 4, width / 2 - 120, height / 2 - 40, 30, 20, "UP" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 5, width / 2 - 200, height / 2 - 40, 30, 20, "Down" }));
				buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 6, x + 75, y + 25, 75, 15, "Close" }));
		} else {
			if(!buttonParts
					&& !buttonPlayer) {
				if (mod_PFLM_PlayerFormLittleMaid.pflm_main.useScaleChange) buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 7, x + 75, y + 25, 75, 15, "ScaleChange" }));
			}
		}
		if (buttonShowArmor) buttonList.add(Modchu_Reflect.newInstance(Modchu_Main.PFLM_GuiSmallButton, new Class[]{ int.class, int.class, int.class, int.class, int.class, String.class }, new Object[]{ 20, x, y + 70, 75, 15, "showArmor" }));
		if(getScale() == 0.0F) {
			setScale(mod_PFLM_PlayerFormLittleMaid.pflm_main.getModelScale());
		}
		guiMode = true;
	}

	public void setTextureArmorPackege() {
		setTextureArmorPackege(2);
	}

	public void setTextureArmorPackege(int i) {
		//PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
		String s = mod_PFLM_PlayerFormLittleMaid.pflm_main.getArmorName((String)PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName), i);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, s);
		Object ltb = mod_Modchu_ModchuLib.modchu_Main.checkTextureArmorPackege(s);
		if (ltb != null) ;else {
			PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, "default");
		}
		if (PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName) != null) ;else PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, PFLM_RenderPlayerDummyMaster.modelData.getCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName));
	}

	public void setTextureValue() {
		String textureName = getTextureName();
		String defaultModelName = (String) Modchu_Reflect.getFieldObject(mod_Modchu_ModchuLib.modchu_Main.MMM_TextureManager, "defaultModelName");
		if (textureName != null
				&& !textureName.isEmpty()) ;else {
					if(textureName != null
							&& textureName.isEmpty()
							| textureName.startsWith("default_" + defaultModelName)) textureName = "default";
				}
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureName, textureName);
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_textureArmorName, getTextureArmorName());
		PFLM_RenderPlayerDummyMaster.modelData.setCapsValue(PFLM_RenderPlayerDummyMaster.modelData.caps_maidColor, getColor());
		setTextureArmorPackege();
		setScale(getScale());
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
		if ((mod_Modchu_ModchuLib.modchu_Main.isRelease()
				&& mod_Modchu_ModchuLib.modchu_Main.isForge
				&& mod_Modchu_ModchuLib.modchu_Main.getMinecraftVersion() > 159)
				| mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glRotatef(180F, 180.0F, 0.0F, 1.0F);
		else GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		if (!mod_PFLM_PlayerFormLittleMaid.pflm_main.oldRender) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
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

    public int colorCheck(String textureName, int i, boolean colorReverse) {
    	Object texture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(textureName, i);
    	if (texture == null) {
    		int n = 0;
    		for (; n < 16 && texture == null; n = n + 1) {
    			i = colorReverse ? i - 1 : i + 1;
    			i = i & 0xf;
    			texture = mod_Modchu_ModchuLib.modchu_Main.textureManagerGetTexture(textureName, i);
    		}
    	}
    	return i;
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