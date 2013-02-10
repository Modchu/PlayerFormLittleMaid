package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PFLM_ThreadVersionCheck extends Thread
{
    public void run()
    {
        HttpURLConnection var1 = null;
        try
        {
        	String s = mod_PFLM_PlayerFormLittleMaid.mod_pflm_playerformlittlemaid.minecraftVersion;
        	if (s != null) {
            	URL var2 = new URL((new StringBuilder()).append("https://dl.dropbox.com/u/105864172/PlayerFormLittleMaid").append(s).append(".txt").toString());
                var1 = (HttpURLConnection)var2.openConnection();
                var1.setDoInput(true);
                var1.setDoOutput(false);
                var1.connect();
                try {
                    InputStream var3 = var1.getInputStream();
                    String var4 = readInputStream(var3);
                    var3.close();
                    String[] var5 = tokenize(var4, "\n\r");
                    if (var5.length < 1) return;
                    String var6 = var5[0];
                    Modchu_Debug.Debug("PlayerFormLittleMaid Version found: " + var6);
                    if (mod_PFLM_PlayerFormLittleMaid.checkRelease(var6)) {
                    	mod_PFLM_PlayerFormLittleMaid.setNewRelease(var6);
                        return;
                    }
                } finally {
                    if (var1 != null) var1.disconnect();
                }
        	}
        } catch (Exception e) {
        }
    }

    public static String readInputStream(InputStream var0, String var1) throws IOException
    {
        InputStreamReader var2 = new InputStreamReader(var0, var1);
        BufferedReader var3 = new BufferedReader(var2);
        StringBuffer var4 = new StringBuffer();

        while (true)
        {
            String var5 = var3.readLine();

            if (var5 == null)
            {
                return var4.toString();
            }

            var4.append(var5);
            var4.append("\n");
        }
    }

    public static String readInputStream(InputStream var0) throws IOException
    {
        return readInputStream(var0, "ASCII");
    }

    public static String[] tokenize(String var0, String var1)
    {
        StringTokenizer var2 = new StringTokenizer(var0, var1);
        ArrayList var3 = new ArrayList();

        while (var2.hasMoreTokens())
        {
            String var4 = var2.nextToken();
            var3.add(var4);
        }

        String[] var5 = (String[])((String[])var3.toArray(new String[var3.size()]));
        return var5;
    }
}
