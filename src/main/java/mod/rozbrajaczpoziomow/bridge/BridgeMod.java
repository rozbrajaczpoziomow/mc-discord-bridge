package mod.rozbrajaczpoziomow.bridge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BridgeMod.MODID)
@Mod.EventBusSubscriber(modid = BridgeMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class BridgeMod {
    public static final String MODID = "bridge";
    public static Discord discord = null; // Non-null when server starts
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final String TOKEN = "The gayest gay that ever gayed"; // TODO! CHANGE TO LOAD FROM SOMEWHERE ELSE
    private static final String CHANNEL_ID = "736597446943047692"; // TODO: Load from config

    public BridgeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }

    @SubscribeEvent
    public static void onServerStarted(final ServerStartedEvent event) {
        discord = new Discord(TOKEN, CHANNEL_ID);
        discord.messageSend("Server has started.");
    }

    @SubscribeEvent
    public static void onServerStopped(final ServerStoppedEvent event) {
        discord.messageSend("Server has stopped.");
        discord.goodbye();
    }

    @SubscribeEvent
    public static void onChat(final ServerChatEvent event) {
        discord.messageSend(String.format("%s: %s", event.getUsername(), event.getRawText()));
    }

    // TODO: Achievements, join & leave
}
