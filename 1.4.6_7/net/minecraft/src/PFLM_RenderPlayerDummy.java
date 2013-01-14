package net.minecraft.src;

import java.io.File;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PFLM_RenderPlayerDummy extends RenderPlayer
{
	protected ModelBiped modelBipedMain;
	protected static ModelPlayerFormLittleMaidBaseBiped modelBasicOrig[];
	protected ModelPlayerFormLittleMaidBaseBiped modelMain;
	protected ModelPlayerFormLittleMaidBaseBiped modelArmorChestplate;
	protected ModelPlayerFormLittleMaidBaseBiped modelArmor;
	public static String[] armorFilenamePrefix;
	private static final ItemStack[] armorItemStack = {new ItemStack(Item.helmetDiamond), new ItemStack(Item.plateDiamond), new ItemStack(Item.legsDiamond), new ItemStack(Item.bootsDiamond)};
	private boolean checkGlEnableWrapper = true;
	private boolean checkGlDisableWrapper = true;

	public PFLM_RenderPlayerDummy() {
		super();
		modelBipedMain = new ModelPlayerFormLittleMaid(0.0F);
		modelArmorChestplate = new ModelPlayerFormLittleMaid(0.5F);
		modelArmor = new ModelPlayerFormLittleMaid(0.1F);
		mainModel = modelBipedMain;
		modelBasicOrig = new ModelPlayerFormLittleMaid[3];
		modelBasicOrig[0] = new ModelPlayerFormLittleMaid(0.0F);
		modelBasicOrig[1] = new ModelPlayerFormLittleMaid(0.5F);
		modelBasicOrig[2] = new ModelPlayerFormLittleMaid(0.1F);
		armorFilenamePrefix = (String[]) Modchu_Reflect.getFieldObject(RenderPlayer.class, "armorFilenamePrefix");
	}

    protected int setArmorModel(EntityLiving entityliving, int i, float f)
    {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
    	if (entity != null) {} else return -1;
    	/*b181//*/byte byte0 = -1;
    	//b181 deleteboolean byte0 = false;
    	if(!entity.showArmor) return byte0;
    	ItemStack itemstack = armorItemStack[3 - i];
    	String t = null;
    	t = entity.textureArmorName;
    	//Modchu_Debug.mDebug("setArmorModel t="+t);
    	if (t != null) {
    		if (entity.textureName.indexOf("Biped") > -1) {
    			t = "Biped";
    		}
    	} else {
    		t = "default";
    	}
    	int i1 = t.lastIndexOf('_');
    	String s = t;
    	if (modelArmorChestplate != null) {
    	} else {
    		Object amodelPlayerFormLittleMaid[] =
    				(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.modelMap.get(s);
    		if (amodelPlayerFormLittleMaid != null)
    		{
    			modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped) amodelPlayerFormLittleMaid[1];
    		} else {
    			modelArmorChestplate = t.indexOf("Biped") > -1 ? new ModelPlayerFormLittleMaid_Biped(1.0F) : modelBasicOrig[1];
    		}
    	}
    	if (modelArmor != null) {
    	} else {
    		Object amodelPlayerFormLittleMaid[];
    		amodelPlayerFormLittleMaid =
    				(ModelPlayerFormLittleMaidBaseBiped[])PFLM_Texture.modelMap.get(s);
    		if (amodelPlayerFormLittleMaid != null)
    		{
    			modelArmor = (ModelPlayerFormLittleMaidBaseBiped) amodelPlayerFormLittleMaid[2];
    		} else {
    			modelArmor = t.indexOf("Biped") > -1 ? new ModelPlayerFormLittleMaid_Biped(0.5F) : modelBasicOrig[2];
    		}
    	}
    	boolean isBiped = ModelPlayerFormLittleMaid_Biped.class.isInstance(modelArmorChestplate);
    	if (isBiped) {
    	} else {
    		entity.textureArmor0[i] = PFLM_Texture.getArmorTextureName(t, 64, itemstack);
    		entity.textureArmor1[i] = PFLM_Texture.getArmorTextureName(t, 80, itemstack);
    	}
    	boolean flag = false;
    	ItemArmor itemarmor = null;
    	if(itemstack != null)
    	{
    		Item item = itemstack.getItem();
    		if(item instanceof ItemArmor)
    		{
    			itemarmor = (ItemArmor)item;
    			flag = itemarmor != null && itemstack.stackSize > 0;
    		}
    	}
    	try
    	{
    		if (isBiped) {
    			if(itemarmor != null) {
    				entity.textureArmor0[i] = "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + 2 + ".png";
    				entity.textureArmor1[i] = "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + 1 + ".png";
    			}
    		}
			ModelPlayerFormLittleMaidBaseBiped var7 = modelArmor;
    		if(flag)
    		{
    			//-@-132
    			if (var7 != null)
    			{
    				var7.onGround = this.mainModel.onGround;
    			}
    			if (var7 != null)
    			{
    				var7.isRiding = this.mainModel.isRiding;
    			}
    			if (var7 != null)
    			{
    				var7.isChild = this.mainModel.isChild;
    			}
    			float var13 = 1.0F;
    			if (itemarmor.getArmorMaterial() == EnumArmorMaterial.CLOTH)
    			{
    				int var9 = itemarmor.getColor(itemstack);
    				float var10 = (float)(var9 >> 16 & 255) / 255.0F;
    				float var11 = (float)(var9 >> 8 & 255) / 255.0F;
    				float var12 = (float)(var9 & 255) / 255.0F;
    				GL11.glColor3f(var13 * var10, var13 * var11, var13 * var12);

    				if (itemstack.isItemEnchanted())
    				{
    					byte0 = 31;
    				} else byte0 = 16;
    			} else GL11.glColor3f(var13, var13, var13);
//@-@132
    			/*132//*/if (byte0 != 31 && byte0 != 16)
    				/*b173//*/byte0 = ((byte)(itemstack.isItemEnchanted() ? 15 : 1));
    			// b173deletebyte0 = true;
    		}
    		ModelPlayerFormLittleMaidBaseBiped var8 = modelArmorChestplate;
    		//var7.settingReflects(0);
    		//var8.settingReflects(0);
    		var7.showArmorParts(3 - i);
    		var8.showArmorParts(3 - i);
    		boolean flag1 = i == 2 ? flag : false;
    		if (isBiped) {
    			var7.setArmorBipedRightLegShowModel(flag1);
    			var7.setArmorBipedLeftLegShowModel(flag1);
    		} else {
    			var8.setArmorBipedRightLegShowModel(flag1);
    			var8.setArmorBipedLeftLegShowModel(flag1);
    		}
    	}
    	catch(Exception exception)
    	{
    		//Modchu_Debug.mDebug("error armorFilenamePrefix="+armorFilenamePrefix);
    	}
    	return byte0;
    }

    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
        String t = null;
        if (i < 4 && modelArmor != null)
        {
            if (entity.textureArmor0 == null) return -1;
            if (entity.textureArmor0[i] == null) return -1;
            t = entity.textureArmor0[i];
            loadTexture(entity.textureArmor0[i]);
            setRenderPassModel(modelArmor);
            return 1;
        }
        if (i < 8 && modelArmorChestplate != null)
        {
            if (entity.textureArmor1 == null) return -1;
            if (entity.textureArmor1[i - 4] == null) return -1;
            t = entity.textureArmor1[i - 4];
            loadTexture(entity.textureArmor1[i - 4]);
            setRenderPassModel(modelArmorChestplate);
            return 1;
        } else {
            return -1;
        }
    }

    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
    	PFLM_EntityPlayerDummy entity = ((PFLM_EntityPlayerDummy) entityliving);
        float f1 = entity.modelScale;
        if (f1 == 0.0F) {
        	f1 = ((ModelPlayerFormLittleMaidBaseBiped) mainModel).getModelScale();
        }
        GL11.glScalef(f1, f1, f1);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1)
    {
        GL11.glPushMatrix();
		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
			shadersGlDisableWrapper(GL11.GL_CULL_FACE);
		} else {
	        GL11.glDisable(GL11.GL_CULL_FACE);
		}

		try
        {
            float f2 = entityliving.prevRenderYawOffset + (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * f1;
            float f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
            float f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
            //renderLivingAt(entityliving, d, d1, d2);
            float f5 = handleRotationFloat(entityliving, f1);
            //rotateCorpse(entityliving, f5, f2, f1);
            float f6 = 0.0625F;
    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			shadersGlEnableWrapper(GL12.GL_RESCALE_NORMAL);
    		} else {
    			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    		}
            GL11.glScalef(-1F, -1F, 1.0F);
            preRenderCallback(entityliving, f1);
            GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
            float f7 = entityliving.prevLegYaw + (entityliving.legYaw - entityliving.prevLegYaw) * f1;
            float f8 = entityliving.legSwing - entityliving.legYaw * (1.0F - f1);
//-@-b181
            if (entityliving.isChild())
            {
                f8 *= 3F;
            }
//@-@b181
            if (f7 > 1.0F)
            {
                f7 = 1.0F;
            }

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
        		GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
            if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
            {
        		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
        			shadersGlEnableWrapper(GL11.GL_BLEND);
        		} else {
            		GL11.glEnable(GL11.GL_BLEND);
        		}
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            }
            else
            {
        		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
        			shadersGlDisableWrapper(GL11.GL_BLEND);
        		} else {
        	        GL11.glDisable(GL11.GL_BLEND);
        		}
            }

            modelMain.setLivingAnimations(entityliving, f8, f7, f1);
            renderModel(entityliving, f8, f7, f5, f3 - f2, f4, f6);

            for (int i = 0; i < 4; i++)
            {
                int j = setArmorModel(entityliving, i, f);

                //b181deleteif (!j)
                /*b181//*/if (j <= 0)
                {
                    continue;
                }

                for (int l = 0; l < 5; l += 4)
                {
                    if (shouldRenderPass(entityliving, i + l, f1) < 0)
                    {
                        continue;
                    }

                    if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend)
                    {
                		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
                			shadersGlEnableWrapper(GL11.GL_BLEND);
                		} else {
                    		GL11.glEnable(GL11.GL_BLEND);
                		}
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glColor4f(1, 1, 1, mod_PFLM_PlayerFormLittleMaid.transparency);
                    }
                    renderPassModel.setLivingAnimations(entityliving, f8, f7, f1);
                    renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
//-@-b181
                    if (j != 15)
                    {
                        continue;
                    }

                    float f11 = (float)entityliving.ticksExisted + f1;
                    loadTexture("%blur%/misc/glint.png");
            		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
            			//Shaders.glEnableWrapper(GL11.GL_BLEND);
            			shadersGlEnableWrapper(GL11.GL_BLEND);
            		} else {
                		GL11.glEnable(GL11.GL_BLEND);
            		}
                    float f13 = 0.5F;
                    GL11.glColor4f(f13, f13, f13, 1.0F);
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDepthMask(false);

                    for (int j1 = 0; j1 < 2; j1++)
                    {
                		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
                			//Shaders.glDisableWrapper(GL11.GL_LIGHTING);
                			shadersGlDisableWrapper(GL11.GL_LIGHTING);
                		} else {
                    		GL11.glDisable(GL11.GL_LIGHTING);
                		}
                        float f16 = 0.76F;
                        GL11.glColor4f(0.5F * f16, 0.25F * f16, 0.8F * f16, 1.0F);
                        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glLoadIdentity();
                        float f17 = f11 * (0.001F + (float)j1 * 0.003F) * 20F;
                        float f18 = 0.3333333F;
                        GL11.glScalef(f18, f18, f18);
                        GL11.glRotatef(30F - (float)j1 * 60F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslatef(0.0F, f17, 0.0F);
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glDepthMask(true);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
            		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
            			shadersGlEnableWrapper(GL11.GL_LIGHTING);
            		} else {
                		GL11.glEnable(GL11.GL_LIGHTING);
            		}

                    if (mod_PFLM_PlayerFormLittleMaid.AlphaBlend
                   		 && entityliving instanceof EntityPlayer)
                    {
                		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
                			shadersGlEnableWrapper(GL11.GL_BLEND);
                		} else {
                    		GL11.glEnable(GL11.GL_BLEND);
                		}
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    }
                    else
                    {
                		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
                			shadersGlDisableWrapper(GL11.GL_BLEND);
                		} else {
                    		GL11.glDisable(GL11.GL_BLEND);
                		}
                    }

                    GL11.glDepthFunc(GL11.GL_LEQUAL);
//@-@b181
                }

                GL11.glEnable(GL11.GL_ALPHA_TEST);
            }

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			shadersGlDisableWrapper(GL11.GL_BLEND);
    			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
    		} else {
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
    		}
            renderEquippedItems(entityliving, f1);
            float f9 = entityliving.getBrightness(f1);
            int k = getColorMultiplier(entityliving, f9, f1);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
    		} else {
        		GL11.glDisable(GL11.GL_TEXTURE_2D);
    		}
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

            if ((k >> 24 & 0xff) > 0 || entityliving.hurtTime > 0 || entityliving.deathTime > 0)
            {
        		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
        			shadersGlDisableWrapper(GL11.GL_TEXTURE_2D);
        			shadersGlDisableWrapper(GL11.GL_ALPHA_TEST);
        			shadersGlEnableWrapper(GL11.GL_BLEND);
        		} else {
            		GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    GL11.glEnable(GL11.GL_BLEND);
        		}
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);

                if (entityliving.hurtTime > 0 || entityliving.deathTime > 0)
                {
                    GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
                    modelMain.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);

                    for (int i1 = 0; i1 < 4; i1++)
                    {
                        if (inheritRenderPass(entityliving, i1, f1) >= 0)
                        {
                            GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
                            renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                        }
                    }
                }

                if ((k >> 24 & 0xff) > 0)
                {
                    float f10 = (float)(k >> 16 & 0xff) / 255F;
                    float f12 = (float)(k >> 8 & 0xff) / 255F;
                    float f14 = (float)(k & 0xff) / 255F;
                    float f15 = (float)(k >> 24 & 0xff) / 255F;
                    GL11.glColor4f(f10, f12, f14, f15);
                    modelMain.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);

                    for (int k1 = 0; k1 < 4; k1++)
                    {
                        if (inheritRenderPass(entityliving, k1, f1) >= 0)
                        {
                            GL11.glColor4f(f10, f12, f14, f15);
                            renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                        }
                    }
                }

                GL11.glDepthFunc(GL11.GL_LEQUAL);
        		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
        			shadersGlDisableWrapper(GL11.GL_BLEND);
        			shadersGlEnableWrapper(GL11.GL_ALPHA_TEST);
        			shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
        		} else {
            		GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
        		}
            }

    		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
    			shadersGlDisableWrapper(GL12.GL_RESCALE_NORMAL);
    		} else {
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    		}
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
			shadersGlEnableWrapper(GL11.GL_TEXTURE_2D);
		} else {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		if (mod_PFLM_PlayerFormLittleMaid.isShaders) {
			shadersGlEnableWrapper(GL11.GL_CULL_FACE);
		} else {
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
        GL11.glPopMatrix();
        //passSpecialRender(entityliving, d, d1, d2);
    }

    public void shadersGlDisableWrapper(int i) {
    	Package pac = this.getClass().getPackage();
    	String s;
    	if (pac == null) s = "Shaders";
    	else s = pac.getName().concat(".Shaders");
    	Method mes = null;
    	if (checkGlDisableWrapper) {
    		try {
    			mes = Class.forName(s).getMethod("glDisableWrapper", new Class[] {int.class});
    			try {
    				mes.invoke(null, i);
    			} catch (Exception e) {
    				checkGlDisableWrapper = false;
    			}
    		} catch (Exception e) {
    			checkGlDisableWrapper = false;
    		}
    	}
    	if (!checkGlDisableWrapper) {
    		glDisableWrapper(s, i);
    	}
    }

    public void shadersGlEnableWrapper(int i) {
    	Package pac = this.getClass().getPackage();
    	String s;
    	if (pac == null) s = "Shaders";
    	else s = pac.getName().concat(".Shaders");
    	Method mes = null;
    	if (checkGlEnableWrapper) {
    		try {
    			mes = Class.forName(s).getMethod("glEnableWrapper", new Class[] {int.class});
    			try {
    				mes.invoke(null, i);
    			} catch (Exception e) {
    				checkGlEnableWrapper = false;
    			}
    		} catch (Exception e) {
    			checkGlEnableWrapper = false;
    		}
    	}
    	if (!checkGlEnableWrapper) {
    		glEnableWrapper(s, i);
    	}
    }

	public static void glEnableWrapper(String s, int i) {
		GL11.glEnable(i);
		if (i == GL11.GL_TEXTURE_2D) {
			Method mes = null;
			try {
				mes = Class.forName(s).getMethod("glEnableWrapperTexture2D", (Class[]) null);
				try {
					mes.invoke(null, (Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (i == GL11.GL_FOG) {
			Method mes = null;
			try {
				mes = Class.forName(s).getMethod("glEnableWrapperFog", (Class[]) null);
				try {
					mes.invoke(null, (Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void glDisableWrapper(String s, int i) {
		GL11.glDisable(i);
		if (i == GL11.GL_TEXTURE_2D) {
			Method mes = null;
			try {
				mes = Class.forName(s).getMethod("glDisableWrapperTexture2D", (Class[]) null);
				try {
					mes.invoke(null, (Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (i == GL11.GL_FOG) {
			Method mes = null;
			try {
				mes = Class.forName(s).getMethod("glDisableWrapperFog", (Class[]) null);
				try {
					mes.invoke(null, (Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doPFLM_RenderPlayer(EntityLiving entity, double d, double d1, double d2,
            float f, float f1)
    {
    	entity.skinUrl = null;
    	//modelArmor.isSneak = modelArmorChestplate.isSneak = mainModel.isSneak = false;
        modelArmor.isRiding = modelArmorChestplate.isRiding = mainModel.isRiding = false;
        modelArmor.onGround = modelArmorChestplate.onGround = mainModel.onGround = renderSwingProgress((EntityLiving) entity, f1);

        modelArmorChestplate.isSneak = modelArmor.isSneak = modelBipedMain.isSneak = false;
        double d3 = d1 - (double)entity.yOffset;
        if (entity.isSneaking())
        {
            d3 -= 0.125D;
        }

        if (entity.isRiding()) {
        	d3 += 0.15D;
        	if (mod_PFLM_PlayerFormLittleMaid.isModelSize) {
        		d3 -= 0.53D;
        	}
     	}
        if (entity.isSneaking()) {
            if (entity.isRiding()) {
              	d3 -= 0.1D;
            }
        }
        doRenderLiving((EntityLiving) entity, d, d3, d2, f, f1);
        //modelArmorChestplate.aimedBow = modelArmor.aimedBow = mainModel.aimedBow = modelBipedMain.aimedBow = false;
        //modelArmorChestplate.isSneak = modelArmor.isSneak = mainModel.isSneak = modelBipedMain.isSneak = false;
        //modelArmorChestplate.heldItemRight = modelArmor.heldItemRight = mainModel.heldItemRight = modelBipedMain.heldItemRight = 0;
	}

    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1)
    {
    	PFLM_EntityPlayerDummy entityDummy = ((PFLM_EntityPlayerDummy) entity);
    	Object[] amodelPlayerFormLittleMaid = entityDummy.textureModel;
    	//if (amodelPlayerFormLittleMaid[0] == null) Modchu_Debug.mDebug("doRender amodelPlayerFormLittleMaid null");;
    	if (entityDummy.textureName == null) entityDummy.textureName = "default";
    	boolean flag = amodelPlayerFormLittleMaid != null;
    	if (!flag) {
    		entityDummy.texture = PFLM_Texture
					.getTextureName(entityDummy.textureName, entityDummy.maidColor);
    		entityDummy.textureArmor0 = null;
    		entityDummy.textureArmor1 = null;
    		//doPFLM_RenderPlayer((EntityLiving) entity, d, d1, d2, f, f1);
        	//return;
    	}
    	if (flag) {
    		mainModel = modelBipedMain = (ModelPlayerFormLittleMaidBaseBiped)
    				(amodelPlayerFormLittleMaid[0] != null
    				&& !entityDummy.textureName.equalsIgnoreCase("default") ?
    						amodelPlayerFormLittleMaid[0] : modelBasicOrig[0]);
    		modelArmorChestplate = (ModelPlayerFormLittleMaidBaseBiped)
    				(amodelPlayerFormLittleMaid[1] != null && !entityDummy.textureArmorName.equalsIgnoreCase("default") ?
    						amodelPlayerFormLittleMaid[1] : modelBasicOrig[1]);
    		modelArmor = (ModelPlayerFormLittleMaidBaseBiped)
    				(amodelPlayerFormLittleMaid[2] != null && !entityDummy.textureArmorName.equalsIgnoreCase("default") ?
    						amodelPlayerFormLittleMaid[2] : modelBasicOrig[2]);
    	} else {
    		mainModel = modelBipedMain = modelBasicOrig[0];
    		modelArmorChestplate = modelBasicOrig[1];
    		modelArmor = modelBasicOrig[2];
    	}
		modelMain = (ModelPlayerFormLittleMaidBaseBiped) mainModel;
    	modelArmorChestplate.isWait = modelArmor.isWait = modelMain.isWait;
    	doPFLM_RenderPlayer((EntityLiving) entityDummy, d, d1, d2, f, f1);
    }

    protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
    }

    protected void renderSpecials(EntityPlayer entityplayer, float f)
    {
    }

    protected void renderModel(EntityLiving entityliving, float f, float f1, float f2, float f3, float f4, float f5)
    {
        loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getTexture());
        mainModel.render(entityliving, f, f1, f2, f3, f4, f5);
    }
/*//b181delete
    protected void renderModel(EntityLiving entityliving, float f, float f1, float f2, float f3, float f4, float f5)
    {
        loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getEntityTexture());
        mainModel.render(entityliving, f, f1, f2, f3, f4, f5);
    }
*///b181delete
}
