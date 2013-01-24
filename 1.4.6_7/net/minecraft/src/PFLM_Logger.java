package net.minecraft.src;

import java.util.logging.Logger;

public class PFLM_Logger extends Logger
{
	protected PFLM_Logger(String s) {
		this(s, null);
	}

	protected PFLM_Logger(String s, String s1) {
		super(s, s1);
	}

    public void warning(String s)
    {
    	if (s.indexOf("illegal stance") != -1) return;
    	super.warning(s);
    }

    public static synchronized PFLM_Logger getLogger(String s)
    {
        return (PFLM_Logger) PFLM_NetServerHandler.logger;
    }

}