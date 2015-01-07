package com.gildedgames.util.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.playerhook.PlayerHookCore;
import com.gildedgames.util.playerhook.PlayerHookServices;
import com.gildedgames.util.worldhook.WorldHookCore;

@Mod(modid = UtilCore.MOD_ID, name = "Gilded Games Utility", version = UtilCore.VERSION)
public class UtilCore extends PlayerHookServices implements ICore
{

	public static final String MOD_ID = "gilded-games-util";
	
	public static final String VERSION = "1.8-1.0";
	
	private static final boolean DEBUG_MODE = true;
	
	@Instance(UtilCore.MOD_ID)
	public static UtilCore instance;
	
	@SidedProxy(clientSide = "com.gildedgames.util.core.ClientProxy", serverSide = "com.gildedgames.util.core.ServerProxy")
	public static ServerProxy proxy;
	
	public static NetworkWrapper NETWORK = new NetworkWrapper();
	
	public List<ICore> cores = new ArrayList<ICore>();
	
	private SidedObject<UtilServices> serviceLocator = new SidedObject<UtilServices>(new UtilServices(), new UtilServices());
	
	public UtilCore()
	{
		this.cores.add(PlayerHookCore.INSTANCE);
		this.cores.add(WorldHookCore.INSTANCE);
	}
	
	@Override
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
	{
		UtilCore.NETWORK.init();
		
		this.proxy.init();

		for (ICore core : this.cores)
		{
			core.preInit(event);
		}
	}
	
	@Override
	@EventHandler
    public void init(FMLInitializationEvent event)
	{
		for (ICore core : this.cores)
		{
			core.init(event);
		}
	}
	
	@Override
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
	{
		for (ICore core : this.cores)
		{
			core.postInit(event);
		}
	}
	
	@Override
	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{
		for (ICore core : this.cores)
		{
			core.serverAboutToStart(event);
		}
	}
	
	@Override
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		for (ICore core : this.cores)
		{
			core.serverStarting(event);
		}
	}
	
	@Override
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		for (ICore core : this.cores)
		{
			core.serverStarted(event);
		}
	}
	
	@Override
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		for (ICore core : this.cores)
		{
			core.serverStopping(event);
		}
	}
	
	@Override
	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event)
	{
		for (ICore core : this.cores)
		{
			core.serverStopped(event);
		}
	}
	
	public static UtilServices locate()
	{
		return instance.serviceLocator.instance();
	}

	public static String modAddress()
	{
		return UtilCore.MOD_ID + ":";
	}
	
	public static boolean isClient()
	{
		return getSide().isClient();
	}
	
	public static boolean isServer()
	{
		return getSide().isServer();
	}
	
    public static Side getSide()
    {
        Thread thr = Thread.currentThread();
        
        if (thr.getName().equals("Server thread") || thr.getName().startsWith("Netty Server IO"))
        {
            return Side.SERVER;
        }

        return Side.CLIENT;
    }
    
	public static void debugPrint(Object line)
	{
		if (DEBUG_MODE && line != null)
		{
			System.out.println("[ORBIS_CORE DEV]: " + line.toString());
		}
	}

	public static void print(Object line)
	{
		if (line != null)
		{
			System.out.println("[ORBIS_CORE]: " + line.toString());
		}
	}
	
}