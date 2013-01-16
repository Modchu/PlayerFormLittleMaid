package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.minecraft.client.Minecraft;
/*//FMLdelete
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
*///FMLdelete
public class PFLM_Texture
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static File minecraft_jar = null;
    private static List playerFormLittleMaid_zips = new ArrayList();
    private static String defDirName = "/mob/littleMaid/";
    private static String defNames[] =
    {
        "mob_littlemaid0.png", "mob_littlemaid1.png", "mob_littlemaid2.png", "mob_littlemaid3.png", "mob_littlemaid4.png", "mob_littlemaid5.png", "mob_littlemaid6.png", "mob_littlemaid7.png", "mob_littlemaid8.png", "mob_littlemaid9.png",
        "mob_littlemaida.png", "mob_littlemaidb.png", "mob_littlemaidc.png", "mob_littlemaidd.png", "mob_littlemaide.png", "mob_littlemaidf.png", "mob_littlemaidw.png", "mob_littlemaid_a00.png", "mob_littlemaid_a01.png"
    };
    public static final int tx_oldwild = 16;
    public static final int tx_oldarmor1 = 17;
    public static final int tx_oldarmor2 = 18;
    public static final int tx_gui = 32;
    public static final int tx_wild = 48;
    public static final int tx_armor1 = 64;
    public static final int tx_armor2 = 80;
    public static Map textures = new TreeMap();
    public static Map modelMap = new TreeMap();
    public static Map armors = new TreeMap();
    public static String armorFilenamePrefix[];

    public PFLM_Texture()
    {
    }

    public static void getArmorPrefix()
    {
        try
        {
            Field field = (RenderPlayer.class).getDeclaredFields()[3];
            field.setAccessible(true);
            String as[] = (String[])field.get(null);
            List list = Arrays.asList(as);
            armorFilenamePrefix = (String[])list.toArray(new String[0]);
        }
        catch (Exception exception) { }
    }

    protected static void getModFile()
    {
        try
        {
            Minecraft _tmp = mc;
            File file = new File(Minecraft.getMinecraftDir(), "/mods/");
            if (file.isDirectory())
            {
                Modchu_Debug.tDebug(String.format("getModFile-get:%d.", new Object[]
                        {
                            Integer.valueOf(file.list().length)
                        }));
                File afile[] = file.listFiles();
                int i = afile.length;
                for (int j = 0; j < i; j++)
                {
                    File file1 = afile[j];
                    if ((file1.getName().indexOf("playerformlittlemaid") != -1 && file1.getName().endsWith(".zip"))
                    		| (file1.getName().indexOf("littleMaidMob") != -1 && file1.getName().endsWith(".zip")))
                    {
                        playerFormLittleMaid_zips.add(file1);
                        Modchu_Debug.tDebug(String.format("getModFile-file:%s", new Object[]
                                {
                                    file1.getName()
                                }));
                    }
                }

                Modchu_Debug.tDebug(String.format("getModFile-files:%d", new Object[]
                        {
                            Integer.valueOf(playerFormLittleMaid_zips.size())
                        }));
            }
            else
            {
                Modchu_Debug.tDebug("getModFile-fail.");
            }
        }
        catch (Exception exception)
        {
            Modchu_Debug.tDebug("getModFile-Exception.");
        }
        try
        {
            minecraft_jar = new File((net.minecraft.src.ModLoader.class).getProtectionDomain().getCodeSource().getLocation().toURI());
            Modchu_Debug.tDebug(String.format("getMincraftFile-file:%s", new Object[]
                    {
                        minecraft_jar.getName()
                    }));
        }
        catch (Exception exception1)
        {
            try
            {
            minecraft_jar = new File(Minecraft.getMinecraftDir(), "/bin/minecraft.jar");
            Modchu_Debug.tDebug(String.format("getMincraftFile-file:%s", new Object[]
                    {
                        minecraft_jar.getName()
                    }));
            }
            catch (Exception exception2)
            {
                Modchu_Debug.tDebug("getMincrafFile-Exception.");
            }
        }
    }

    public static boolean getTextures()
    {
        for (int i = 0; i < defNames.length; i++)
        {
            addTextureName((new StringBuilder()).append(defDirName).append("default/").append(defNames[i]).toString());
        }

        Modchu_Debug.tDebug("getTexture-append-default-done.");
        if (addTexturesJar(minecraft_jar))
        {
            Modchu_Debug.tDebug("getTexture-append-jar-done.");
        }
        else
        {
            Modchu_Debug.tDebug("getTexture-append-jar-fail.");
        }
        for (Iterator iterator = playerFormLittleMaid_zips.iterator(); iterator.hasNext();)
        {
            File file1 = (File)iterator.next();
            if (addTexturesZip(file1))
            {
                Modchu_Debug.tDebug(String.format("getTexture-append-%s-done.", new Object[]
                        {
                            file1.getName()
                        }));
            }
            else
            {
                Modchu_Debug.tDebug(String.format("getTexture-append-%s-fail.", new Object[]
                        {
                            file1.getName()
                        }));
            }
        }

        try
        {
            File file = new File((net.minecraft.src.mod_PFLM_PlayerFormLittleMaid.class).getResource("/mob/littleMaid/").getFile());
            if (addTexturesDebug(file))
            {
                Modchu_Debug.tDebug("getTexture-append-debug-done.");
            }
            else
            {
                Modchu_Debug.tDebug("getTexture-append-debug-fail.");
            }
        }
        catch (Exception exception)
        {
            Modchu_Debug.tDebug("getTexture-append-debug-Exception.");
        }
        Iterator iterator1 = textures.entrySet().iterator();

        do
        {
            if (!iterator1.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
            String s = (String)entry.getKey();
            int j = s.lastIndexOf("_");

            if (j > -1)
            {
                s = s.substring(j + 1);

                if (!s.isEmpty())
                {
                    addModelClass("ModelPlayerFormLittleMaid_".concat(s).concat(".class"));
                    addModelClass("ModelPlayerFormLittleMaidSmart_".concat(s).concat(".class"));
                }
            }
        }
        while (true);

        Modchu_Debug.tDebug("getTexture-append-Models-append-done.");
        return false;
    }

    private static void addModelClass(String s)
    {
    	String s1 = mod_PFLM_PlayerFormLittleMaid.isSmartMoving ? "ModelPlayerFormLittleMaidSmart_" : "ModelPlayerFormLittleMaid_";
    	if (s.startsWith(s1) && s.endsWith(".class") && s.indexOf('_') > -1)
    	{
    		String s2 = s.replace(".class", "");
    		String s3 = s2.substring(s1.length());

    		if (modelMap.containsKey(s3))
    		{
    			return;
    		}

    		try
    		{
    			ClassLoader classloader = (net.minecraft.client.Minecraft.class).getClassLoader();
    			Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
    			Class class1;
    			if (package1 != null)
    			{
    				s2 = (new StringBuilder(String.valueOf(package1.getName()))).append(".").append(s2).toString();
    				class1 = classloader.loadClass(s2);
    			}
    			else
    			{
    				class1 = Class.forName(s2);
    			}
    			Class c1 = mod_PFLM_PlayerFormLittleMaid.isSmartMoving ? net.minecraft.src.ModelPlayerFormLittleMaidSmart.class : net.minecraft.src.ModelPlayerFormLittleMaidBaseBiped.class;
    			if (!c1.isAssignableFrom(class1))
    			{
    				Modchu_Debug.tDebug(String.format("getModelClass-fail.", new Object[0]));
    				return;
    			}
    			Object amodelPlayerFormLittleMaid[];
//-@-110
    			if(mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
    				amodelPlayerFormLittleMaid = new ModelPlayerFormLittleMaidSmart[3];
    			} else {
//@-@110
    				amodelPlayerFormLittleMaid = new ModelPlayerFormLittleMaidBaseBiped[3];
    			/*110//*/}
    			Constructor constructor;
    			c1 = mod_PFLM_PlayerFormLittleMaid.isSmartMoving ? net.minecraft.src.ModelPlayerFormLittleMaidSmart_Biped.class : net.minecraft.src.ModelPlayerFormLittleMaid_Biped.class;
    			try {
//-@-110
    				if(mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
    					constructor = class1.getConstructor(new Class[]{ Float.TYPE });
    					amodelPlayerFormLittleMaid[0] = (ModelPlayerFormLittleMaidSmart)constructor.newInstance(new Object[]{ 0.0F });
    					constructor = class1.getConstructor(new Class[]{ Float.TYPE ,Integer.TYPE ,Integer.TYPE});
    					float f1 = 0.5F;
    					float f2 = 0.1F;
    					if (c1.isAssignableFrom(class1)) {
    						f1 = 1.0F;
    						f2 = 0.5F;
    					}
    					amodelPlayerFormLittleMaid[1] = (ModelPlayerFormLittleMaidSmart)constructor.newInstance(new Object[]{ f1, ModelPlayerFormLittleMaidSmart.NoScaleStart, ModelPlayerFormLittleMaidSmart.NoScaleEnd });
    					amodelPlayerFormLittleMaid[2] = (ModelPlayerFormLittleMaidSmart)constructor.newInstance(new Object[]{ f2, ModelPlayerFormLittleMaidSmart.NoScaleStart, ModelPlayerFormLittleMaidSmart.Scale });
    				} else {
//@-@110
    					constructor = class1.getConstructor(new Class[]{ Float.TYPE });
    					amodelPlayerFormLittleMaid[0] = (ModelPlayerFormLittleMaidBaseBiped)constructor.newInstance(new Object[]{ 0.0F });
    					float f1 = 0.5F;
    					float f2 = 0.1F;
    					if (c1.isAssignableFrom(class1)) {
    						f1 = 1.0F;
    						f2 = 0.5F;
    					}
    					amodelPlayerFormLittleMaid[1] = (ModelPlayerFormLittleMaidBaseBiped)constructor.newInstance(new Object[]{ f1 });
    					amodelPlayerFormLittleMaid[2] = (ModelPlayerFormLittleMaidBaseBiped)constructor.newInstance(new Object[]{ f2 });
    				/*110//*/}
    			} catch( InvocationTargetException e ) {
    				Throwable throwable = e.getCause();
    				throwable.printStackTrace();
    			} catch( IllegalAccessException e ) {
    				e.printStackTrace();
    			}
    			catch( IllegalArgumentException e ) {
    				e.printStackTrace();
    			}
    			modelMap.put(s3, amodelPlayerFormLittleMaid);
    			Modchu_Debug.tDebug(String.format("getModelClass-%s", new Object[]
    					{
    						s2
    					}));
    		}
    		catch (Exception exception)
    		{
    			Modchu_Debug.tDebug(""+exception);
    			Modchu_Debug.tDebug(String.format("getModelClass-Exception.", new Object[0]));
    		}
    		catch (Error error)
    		{
    			Modchu_Debug.tDebug(String.format("getModelClass-Error: ".concat(s), new Object[0]));
    		}
    	}
    }

    private static void addTextureName(String s)
    {
        if (!s.startsWith("/"))
        {
            s = (new StringBuilder()).append("/").append(s).toString();
        }
        if (s.startsWith(defDirName))
        {
            int i = s.lastIndexOf("/");
            if (defDirName.length() < i)
            {
                String s1 = s.substring(defDirName.length(), i);
                s1 = s1.replace('/', '.');
                String s2 = s.substring(i);
                int j = getIndex(s2);
                if (j > -1)
                {
                    String s3 = null;
                    if (j == 17)
                    {
                        j = 64;
                        s3 = "default";
                    }
                    if (j == 18)
                    {
                        j = 80;
                        s3 = "default";
                    }
                    if (j == 16)
                    {
                        j = 60;
                    }
                    if (j >= 64 && j <= 95)
                    {
                        Object obj = (Map)armors.get(s1);
                        if (obj == null)
                        {
                            obj = new HashMap();
                            armors.put(s1, obj);
                            Modchu_Debug.tDebug(String.format("getTextureName-append-armorPack-%s", new Object[]
                                    {
                                        s1
                                    }));
                        }
                        if (s3 == null)
                        {
                            s3 = s2.substring(1, s2.lastIndexOf('_'));
                        }
                        Object obj2 = (Map)((Map) (obj)).get(s3);
                        if (obj2 == null)
                        {
                            obj2 = new HashMap();
                            ((Map) (obj)).put(s3, obj2);
                        }
                        ((Map) (obj2)).put(Integer.valueOf(j), s2);
                    }
                    else
                    {
                        Object obj1 = (Map)textures.get(s1);
                        if (obj1 == null)
                        {
                            obj1 = new HashMap();
                            textures.put(s1, obj1);
                            Modchu_Debug.tDebug(String.format("getTextureName-append-texturePack-%s", new Object[]
                                    {
                                        s1
                                    }));
                        }
                        ((Map) (obj1)).put(Integer.valueOf(j), s2);
                    }
                }
            }
        }
    }

    public static boolean addTexturesZip(File file)
    {
        if (file == null || file.isDirectory())
        {
            return false;
        }
        try
        {
            FileInputStream fileinputstream = new FileInputStream(file);
            ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
            do
            {
                ZipEntry zipentry = zipinputstream.getNextEntry();
                if (zipentry == null)
                {
                    break;
                }
                if (!zipentry.isDirectory())
                {
                    String s1 = mod_PFLM_PlayerFormLittleMaid.isSmartMoving ? "ModelPlayerFormLittleMaidSmart_" : "ModelPlayerFormLittleMaid_";
                    if (zipentry.getName().startsWith(s1) && zipentry.getName().endsWith(".class"))
                    {
                        addModelClass(zipentry.getName());
                    }
                    else
                    {
                        addTextureName(zipentry.getName());
                    }
                }
            }
            while (true);
            zipinputstream.close();
            fileinputstream.close();
            return true;
        }
        catch (Exception exception)
        {
            Modchu_Debug.tDebug("addTextureZip-Exception.");
        }
        return false;
    }

    protected static boolean addTexturesJar(File file)
    {
        if (file.isFile())
        {
            Modchu_Debug.tDebug("addTextureJar-zip.");
            return addTexturesZip(file);
        }
        if (file.isDirectory())
        {
            Modchu_Debug.tDebug("addTextureJar-file.");
            Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
            if (package1 != null)
            {
                String s = package1.getName().replace('.', File.separatorChar);
                file = new File(file, s);
                Modchu_Debug.tDebug(String.format("addTextureJar-file-Packege:%s", new Object[]
                        {
                            s
                        }));
            }
            else
            {
                Modchu_Debug.tDebug("addTextureJar-file-null.");
            }
            return addTexturesZip(file);
        }
        else
        {
            return false;
        }
    }

    public static boolean addTexturesDebug(File file)
    {
        if (file == null)
        {
            return false;
        }
        try
        {
            File afile[] = file.listFiles();
            int i = afile.length;
            for (int j = 0; j < i; j++)
            {
                File file1 = afile[j];
                if (file1.isDirectory())
                {
                    addTexturesDebug(file1);
                    continue;
                }
                String s = file1.getPath().replace('\\', '/');
                String s1 = mod_PFLM_PlayerFormLittleMaid.isSmartMoving ? "ModelPlayerFormLittleMaidSmart_" : "ModelPlayerFormLittleMaid_";
                if (file1.getName().startsWith(s1) && file1.getName().endsWith(".class"))
                {
                    addModelClass(file1.getName());
                    continue;
                }
                int k = s.indexOf(defDirName);
                if (k > -1)
                {
                    addTextureName(s.substring(k).replace('\\', '/'));
                }
            }

            return true;
        }
        catch (Exception exception)
        {
            Modchu_Debug.tDebug("addTextureDebug-Exception.");
        }
        return false;
    }

    private static int getIndex(String s)
    {
        for (int i = 0; i < defNames.length; i++)
        {
            if (s.endsWith(defNames[i]))
            {
                return i;
            }
        }

        Pattern pattern = Pattern.compile("_([0-9a-f]+).png");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find())
        {
            return Integer.decode((new StringBuilder()).append("0x").append(matcher.group(1)).toString()).intValue();
        }
        else
        {
            return -1;
        }
    }

    public static String getNextPackege(String s, int i)
    {
        boolean flag = false;
        Iterator iterator = textures.entrySet().iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            if (flag && ((Map)entry.getValue()).get(Integer.valueOf(i)) != null)
            {
                return (String)entry.getKey();
            }
            if (((String)entry.getKey()).equalsIgnoreCase(s))
            {
                flag = true;
            }
        }
        while (true);
        for (Iterator iterator1 = textures.entrySet().iterator(); iterator1.hasNext();)
        {
            java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator1.next();
            if (((Map)entry1.getValue()).get(Integer.valueOf(i)) != null)
            {
                return (String)entry1.getKey();
            }
        }

        return null;
    }

    public static String getPrevPackege(String s, int i)
    {
        String s1 = null;
        Iterator iterator = textures.entrySet().iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            if (((String)entry.getKey()).equalsIgnoreCase(s) && s1 != null)
            {
                break;
            }
            if (((Map)entry.getValue()).get(Integer.valueOf(i)) != null)
            {
                s1 = (String)entry.getKey();
            }
        }
        while (true);
        return s1;
    }

    public static String getTextureName(String s, int i)
    {
        if (textures.get(s) == null || ((Map)textures.get(s)).get(Integer.valueOf(i)) == null)
        {
            return null;
        }
        else
        {
            return (new StringBuilder()).append(defDirName).append(s.replace('.', '/')).append((String)((Map)textures.get(s)).get(Integer.valueOf(i))).toString();
        }
    }

    public static int getTextureCount()
    {
        return textures.size();
    }

    public static String getNextArmorPackege(String s)
    {
        boolean flag = false;
        Iterator iterator = armors.entrySet().iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            if (flag)
            {
                return (String)entry.getKey();
            }
            if (((String)entry.getKey()).equalsIgnoreCase(s))
            {
                flag = true;
            }
        }
        while (true);
        iterator = armors.entrySet().iterator();
        if (iterator.hasNext())
        {
            java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator.next();
            return (String)entry1.getKey();
        }
        else
        {
            return null;
        }
    }

    public static String getPrevArmorPackege(String s)
    {
        String s1 = null;
        Iterator iterator = armors.entrySet().iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            if (((String)entry.getKey()).equalsIgnoreCase(s) && s1 != null)
            {
                break;
            }
            s1 = (String)entry.getKey();
        }
        while (true);
        return s1;
    }

    public static String getArmorTextureName(String s, int i, ItemStack itemstack)
    {
        Map map = (Map)armors.get(s);
        if (map == null || itemstack == null)
        {
            return null;
        }
        if (!(itemstack.getItem() instanceof ItemArmor))
        {
            return null;
        }
        Map map1 = (Map)map.get(armorFilenamePrefix[((ItemArmor)itemstack.getItem()).renderIndex]);
        if (map1 == null)
        {
            map1 = (Map)map.get("default");
            if (map1 == null)
            {
                return null;
            }
        }
        int j = 0;
        if (itemstack.getMaxDamage() > 0)
        {
            j = (10 * itemstack.getItemDamage()) / itemstack.getMaxDamage();
        }
        String s1 = null;
        int k = i + j;
        do
        {
            if (k < i)
            {
                break;
            }
            s1 = (String)map1.get(Integer.valueOf(k));
            if (s1 != null)
            {
                break;
            }
            k--;
        }
        while (true);
        if (s1 == null)
        {
            return null;
        }
        else
        {
            return (new StringBuilder()).append(defDirName).append(s.replace('.', '/')).append(s1).toString();
        }
    }

    public static int getArmorTextureCount()
    {
        return armors.size();
    }

    public static int getRandomWildColor(String s, Random random)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = ((Map)textures.get(s)).keySet().iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            Integer integer = (Integer)iterator.next();
            if (integer.intValue() >= 48 && integer.intValue() <= 63)
            {
                arraylist.add(Integer.valueOf(integer.intValue() & 0xf));
            }
        }
        while (true);
        if (arraylist.size() > 0)
        {
            return ((Integer)arraylist.get(random.nextInt(arraylist.size()))).intValue();
        }
        else
        {
            return -1;
        }
    }

    //TODO ↓ここ以下、PFLM追加
    public static Object[] multiModelGet(String s)
    {
    	int i = s.lastIndexOf("_");
    	if (i != -1) {
    		s = s.substring(i + 1);
    	}
    	try
    	{
    		String s1 = mod_PFLM_PlayerFormLittleMaid.isSmartMoving ? "ModelPlayerFormLittleMaidSmart" : "ModelPlayerFormLittleMaid";
    		ClassLoader classloader = (net.minecraft.client.Minecraft.class).getClassLoader();
    		Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
    		Class class1;
    		if (package1 != null)
    		{
    			String s2 = (new StringBuilder(String.valueOf(package1.getName()))).append(".").append(s1).append("_").append(s).toString();
    			class1 = classloader.loadClass(s2);
    		}
    		else
    		{
    			class1 = Class.forName((new StringBuilder(s1).append("_").append(s).toString()));
    		}
    		Class c1 = mod_PFLM_PlayerFormLittleMaid.isSmartMoving ? net.minecraft.src.ModelPlayerFormLittleMaidSmart.class : net.minecraft.src.ModelPlayerFormLittleMaidBaseBiped.class;
    		if (!c1.isAssignableFrom(class1))
    		{
    			Modchu_Debug.tDebug("getModelClass-fail. className="+class1.toString());
    			return null;
    		}
    		Object amodelPlayerFormLittleMaid[];
//-@-110
    		if(mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
    			amodelPlayerFormLittleMaid = new ModelPlayerFormLittleMaidSmart[3];
    		} else {
//@-@110
    			amodelPlayerFormLittleMaid = new ModelPlayerFormLittleMaidBaseBiped[3];
    		/*110//*/}
    		Constructor constructor;
			c1 = mod_PFLM_PlayerFormLittleMaid.isSmartMoving ? net.minecraft.src.ModelPlayerFormLittleMaidSmart_Biped.class : net.minecraft.src.ModelPlayerFormLittleMaid_Biped.class;
    		try {
//-@-110
    			if(mod_PFLM_PlayerFormLittleMaid.isSmartMoving) {
    				constructor = class1.getConstructor(new Class[]{ Float.TYPE });
    				amodelPlayerFormLittleMaid[0] = (ModelPlayerFormLittleMaidSmart)constructor.newInstance(new Object[]{ 0.0F });
    				constructor = class1.getConstructor(new Class[]{ Float.TYPE ,Integer.TYPE ,Integer.TYPE});
    				float f1 = 0.5F;
    				float f2 = 0.1F;
					if (c1.isAssignableFrom(class1)) {
    					f1 = 1.0F;
    					f2 = 0.5F;
    				}
    				amodelPlayerFormLittleMaid[1] = (ModelPlayerFormLittleMaidSmart)constructor.newInstance(new Object[]{ f1, ModelPlayerFormLittleMaidSmart.NoScaleStart, ModelPlayerFormLittleMaidSmart.NoScaleEnd });
    				amodelPlayerFormLittleMaid[2] = (ModelPlayerFormLittleMaidSmart)constructor.newInstance(new Object[]{ f2, ModelPlayerFormLittleMaidSmart.NoScaleStart, ModelPlayerFormLittleMaidSmart.Scale });
    			} else {
//@-@110
    				constructor = class1.getConstructor(new Class[]{ Float.TYPE });
    				amodelPlayerFormLittleMaid[0] = (ModelPlayerFormLittleMaidBaseBiped)constructor.newInstance(new Object[]{ 0.0F });
    				float f1 = 0.5F;
    				float f2 = 0.1F;
					if (c1.isAssignableFrom(class1)) {
    					f1 = 1.0F;
    					f2 = 0.5F;
    				}
    				amodelPlayerFormLittleMaid[1] = (ModelPlayerFormLittleMaidBaseBiped)constructor.newInstance(new Object[]{ f1 });
    				amodelPlayerFormLittleMaid[2] = (ModelPlayerFormLittleMaidBaseBiped)constructor.newInstance(new Object[]{ f2 });
    			/*110//*/}
    		} catch( Exception e ) {
    			e.printStackTrace();
    		}
    		//Modchu_Debug.mDebug(String.format("multiModelGet-"+s));
    		return amodelPlayerFormLittleMaid;
    	}
    	catch (Exception exception)
    	{
    		Modchu_Debug.tDebug(""+exception);
    		Modchu_Debug.tDebug("multiModelGet-Exception.");
    	}
    	catch (Error error)
    	{
    		Modchu_Debug.tDebug("multiModelGet-Error: ".concat(s));
    	}
    	return null;
    }

    public static String getPackege(int i, int j)
    {
        if (textures.size() < j
        		| j < 0) return null;
        boolean flag = false;
        Iterator iterator = textures.entrySet().iterator();
        int j1 = 0;
        do
        {
        	++j1;
        	if (!iterator.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            if (flag && ((Map)entry.getValue()).get(Integer.valueOf(i)) != null)
            {
                return (String)entry.getKey();
            }
            if (j1 == j)
            {
                flag = true;
            }
        }
        while (true);
        for (Iterator iterator1 = textures.entrySet().iterator(); iterator1.hasNext();)
        {
            java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator1.next();
            if (((Map)entry1.getValue()).get(Integer.valueOf(i)) != null)
            {
                return (String)entry1.getKey();
            }
        }

        return null;
    }

    public static String getArmorPackege(int i)
    {
        boolean flag = false;
        Iterator iterator = armors.entrySet().iterator();
        int i1 = 0;
        do
        {
        	++i;
            if (!iterator.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            if (flag)
            {
                return (String)entry.getKey();
            }
            if (i1 == i)
            {
                flag = true;
            }
        }
        while (true);
        iterator = armors.entrySet().iterator();
        if (iterator.hasNext())
        {
            java.util.Map.Entry entry1 = (java.util.Map.Entry)iterator.next();
            return (String)entry1.getKey();
        }
        else
        {
            return null;
        }
    }

    public static String getModelSpecificationArmorPackege(String s)
    {
        int i1 = s.lastIndexOf("_");
        if (i1 > -1) {
        	s = s.substring(i1 + 1);
        }
        boolean flag = false;
        Iterator iterator = armors.entrySet().iterator();
        String s1;
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            s1 = (String)entry.getKey();
            i1 = s1.lastIndexOf("_");
            if (i1 > -1) {
            	s1 = s1.substring(i1 + 1);
            	if (s1.equalsIgnoreCase(s))
            	{
                	return (String)entry.getKey();
            	}
            }
        }
        while (true);
        return null;
    }
}
