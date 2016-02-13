package com.bebehp.mc.eewreciever;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bebehp.mc.eewreciever.ping.QuakeMain;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid=EEWRecieverMod.owner, name=EEWRecieverMod.owner, version="1.0")
public class EEWRecieverMod
{
	public static final String owner = "EEWReciever";
	public static final Logger logger = LogManager.getLogger("EEWReciever");

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		logger.info("EEW is setting up.");
		FMLCommonHandler.instance().bus().register(new QuakeMain());
		MinecraftForge.EVENT_BUS.register(this);
	}

/*
	@SubscribeEvent
	public void onServerChat(ServerChatEvent event)
	{
		if (event.message.contains("fuck"))
		{
			logger.info("ファッキュン♡©");
			sendServerChat("ファッキュン♡©");
		}
		if (event.message.contains("fuck"))
	{
			fireworksAllPlayer();
			EEWRecieverMod.sendServerChat("fuck you too");
		}

		if (event.message.contains("bubu"))
		{
			EntityPlayerMP player = event.player;
			player.worldObj.createExplosion(
					player,
					player.getPlayerCoordinates().posX,
					player.getPlayerCoordinates().posY,
					player.getPlayerCoordinates().posZ,
					2F,
					false
			);

			player.worldObj.spawnEntityInWorld(new EntityTNTPrimed(
					player.worldObj,
					player.getPlayerCoordinates().posX,
					player.getPlayerCoordinates().posY,
					player.getPlayerCoordinates().posZ,
					player
			));

		}
	}
*/

	@NetworkCheckHandler
	public boolean netCheckHandler(Map<String, String> mods, Side side)
	{
		return true;
	}

	public static void sendServerChat(String msg)
	{
		ServerConfigurationManager sender = FMLCommonHandler.instance().getMinecraftServerInstance()
			.getConfigurationManager();

		String[] linemsg = msg.split("\n");
		for (String line : linemsg)
		{
			sender.sendChatMsg(new ChatComponentText(line));
		}
	}
/*
	public static void fireworksAllPlayer()
	{
		World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();

		List<?> players = world.playerEntities;
		for (Object playerobj : players)
		{
			EntityPlayerMP player = (EntityPlayerMP) playerobj;
//			player.moveEntity(5, 0, 0);
//			player.setPosition(0, 5, 0);
//			player.setPositionAndUpdate(0, 5, 0);
			fireworksPlayer(player);
		}
	}

	public static void fireworksPlayer(EntityPlayerMP player)
	{
		player.worldObj.spawnEntityInWorld(new EntityFireworkRocket(
				player.worldObj,
				player.getPlayerCoordinates().posX,
				player.getPlayerCoordinates().posY,
				player.getPlayerCoordinates().posZ,
				makeFireworks()
		));
	}

	public static ItemStack makeFireworks()
	{
		final NBTTagCompound explosion = new NBTTagCompound() {{
			setByte("Type", (byte)2);
			setByte("Trail", (byte)1);
			setIntArray("Colors", ItemDye.field_150922_c);
		}};
		final NBTTagList nbttaglist = new NBTTagList() {{
			appendTag(explosion);
			appendTag(explosion);
			appendTag(explosion);
		}};
        final NBTTagCompound nbt = new NBTTagCompound() {{
        	setTag("Fireworks", new NBTTagCompound() {{
            	setTag("Explosions", nbttaglist);
                setByte("Flight", (byte)1);
			}});
        }};
		ItemStack itemstack = new ItemStack(Items.firework_charge);
        itemstack.setTagCompound(nbt);
        return itemstack;
	}
*/}