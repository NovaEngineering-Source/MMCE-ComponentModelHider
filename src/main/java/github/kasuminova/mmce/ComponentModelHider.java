package github.kasuminova.mmce;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("MethodMayBeStatic")
@Mod(modid = ComponentModelHider.MOD_ID, name = ComponentModelHider.MOD_NAME, version = ComponentModelHider.VERSION,
        dependencies = "required-after:forge@[14.23.5.2847,);",
        acceptedMinecraftVersions = "[1.12, 1.13)"
)
public class ComponentModelHider {
    public static final String MOD_ID = "component_model_hider";
    public static final String MOD_NAME = "MMCE Component Model Hider";

    public static final String VERSION = Tags.VERSION;

    public static final String CLIENT_PROXY = "github.kasuminova.mmce.client.ClientProxy";
    public static final String COMMON_PROXY = "github.kasuminova.mmce.CommonProxy";

    @Mod.Instance(MOD_ID)
    public static ComponentModelHider instance = null;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy = null;

    public static Logger log = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = VERSION;
        log = event.getModLog();

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}