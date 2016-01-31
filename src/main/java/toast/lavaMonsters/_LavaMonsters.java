package toast.lavaMonsters;

import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = _LavaMonsters.MODID, name = "Lava Monsters", version = _LavaMonsters.VERSION)
public class _LavaMonsters {
    /* TO DO *\
    >> currentTasks
        * Use game rule to disable mob spawning! (doMobSpawning)
        * Reformat all code!
            > next: EntityLavaMonster.class
    >> tasks
        * None!
    >> goals
        * Other monsters/hazards
            > fireballs shoot out of lava
        * Trophies/Achievements
            ? boss-like lava monster
    \* ** ** */

    // This mod's id.
    public static final String MODID = "LavaMonsters";
    // This mod's version.
    public static final String VERSION = "2.2.1";

    /// If true, this mod starts up in debug mode.
    public static final boolean debug = false;
    @SidedProxy(clientSide = "toast.lavaMonsters.client.ClientProxy", serverSide = "toast.lavaMonsters.CommonProxy")
    public static CommonProxy proxy;
    /// The mod's random number generator.
    public static final Random random = new Random();

    /// Called before initialization. Loads the properties/configurations.
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        _LavaMonsters.debugConsole("Loading in debug mode!");
        Properties.init(new Configuration(event.getSuggestedConfigurationFile()));
    }

    /// Called during initialization. Registers entities, mob spawns, and renderers.
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(EntityLavaMonster.class, "LavaMonster", 0, this, 80, 3, true);
        int id = EntityRegistry.findGlobalUniqueEntityId();
        try {
            Method method = EntityRegistry.class.getDeclaredMethod("validateAndClaimId", int.class);
            method.setAccessible(true);
            id = ((Integer) method.invoke(EntityRegistry.instance(), Integer.valueOf(id))).intValue();
        }
        catch (Exception ex) {
            _LavaMonsters.console("Error claiming spawn egg ID! Spawn egg will probably be overwritten. @" + ex.getClass().getName());
        }
        EntityList.IDtoClassMapping.put(Integer.valueOf(id), EntityLavaMonster.class);
        EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, 0xff0000, 0xfcfc00));

        _LavaMonsters.proxy.registerRenderers();
        new SpawnLavaMonster();
    }

    /// Prints the message to the console with this mod's name tag.
    public static void console(String message) {
        System.out.println("[LavaMonsters] " + message);
    }

    /// Prints the message to the console with this mod's name tag.
    public static void debugConsole(String message) {
        if (_LavaMonsters.debug) {
            System.out.println("[LavaMonsters] (debug) " + message);
        }
    }

    /// Prints the message to the console with this mod's name tag.
    public static void debugException(String message) {
        if (_LavaMonsters.debug)
            throw new RuntimeException("[LavaMonsters] (debug) " + message);
    }
}