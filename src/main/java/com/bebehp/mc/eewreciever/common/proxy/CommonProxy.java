package com.bebehp.mc.eewreciever.common.proxy;

import java.io.File;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.common.QuakeMain;
import com.bebehp.mc.eewreciever.common.handler.ConfigurationHandler;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeFileManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CommonProxy {

	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();
		EEWRecieverMod.folderDir = new File(event.getModConfigurationDirectory(), Reference.MODID);
		final File configFileDir = new File(EEWRecieverMod.folderDir, Reference.MODID + ".cfg");
		// EEWReciever.cfgの移動に伴うファイル移動
		checkConfigFile(event.getSuggestedConfigurationFile(), configFileDir);
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
	
	public static void createFolders() {
		if (!EEWRecieverMod.folderDir.exists()) {
			if (!EEWRecieverMod.folderDir.mkdirs()) {
				Reference.logger.warn("Could not create EEWReciever directory [{}]!", EEWRecieverMod.folderDir.getAbsolutePath());
			}
		}
	}

	public static void checkConfigFile(final File oldConfigFile, final File configDir) {
		if (oldConfigFile.exists())
			oldConfigFile.renameTo(configDir);
	}

}
