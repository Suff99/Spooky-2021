package me.suff.mc.spookyshit;

import me.suff.mc.spookyshit.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SpookyShit.MODID, name = SpookyShit.NAME, version = SpookyShit.VERSION)
public class SpookyShit {
    public static final String MODID = "spooky_shit";
    public static final String NAME = "Spooky shit";
    public static final String VERSION = "1.0";
    private static final Logger LOG = LogManager.getLogger(NAME);
    @SidedProxy(clientSide = "me.suff.mc.spookyshit.proxy.ClientProxy", serverSide = "me.suff.mc.spookyshiy.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void pre(FMLPreInitializationEvent event) {
        proxy.pre();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event) {
        proxy.init();
    }

}
