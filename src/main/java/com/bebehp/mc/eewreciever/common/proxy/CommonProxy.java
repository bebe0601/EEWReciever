package com.bebehp.mc.eewreciever.common.proxy;

import java.io.File;
import java.io.IOException;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.common.QuakeMain;
import com.bebehp.mc.eewreciever.common.handler.ConfigurationHandler;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeFileManager;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy {

	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();

		final File dataDir = new File(event.getModConfigurationDirectory(), Reference.MODID);
		if (dataDir.exists())
			dataDir.mkdirs();
		final File configFileDir = new File(dataDir, Reference.MODID + ".cfg");
		ConfigurationHandler.init(configFileDir);

		Reference.logger.info("Loading the files...");
		EEWRecieverMod.tweetQuakeKey = TweetQuakeFileManager.loadKey();
		EEWRecieverMod.accessToken = TweetQuakeFileManager.loadAccessToken();
	}

	public void init(final FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		MinecraftForge.EVENT_BUS.register(QuakeMain.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ConfigurationHandler.INSTANCE);
	}

	public void serverLoad(final FMLServerStartingEvent event){
	}

	public static File getModDataDir() {
		final File mcDataDir = FMLServerHandler.instance().getSavesDirectory();
		try {
			return new File(mcDataDir.getCanonicalFile(), "config/" + Reference.MODID);
		} catch (final IOException e) {
			Reference.logger.error("Could not canonize path!", e);
			return new File(mcDataDir, "config/" + Reference.MODID);
		}
	}

}
