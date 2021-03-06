package net.teamfruit.eewreciever2;

import java.util.Map;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.teamfruit.eewreciever2.common.Locations;
import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.proxy.CommonProxy;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY)
public class EEWReciever2 {
	@Instance(Reference.MODID)
	public static EEWReciever2 instance;

	/**
	 * EEWReciever2の各種Eventがpostされます。
	 */
	public static final EventBus EVENT_BUS = new EventBus();
	public static Locations locations;

	@SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
	public static CommonProxy proxy;

	@NetworkCheckHandler
	public boolean checkModList(final Map<String, String> versions, final Side side) {
		return true;
	}

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event) {
		proxy.serverLoad(event);
	}
}
