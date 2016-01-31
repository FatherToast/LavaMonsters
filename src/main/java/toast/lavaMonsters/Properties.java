package toast.lavaMonsters;

import java.util.HashMap;
import java.util.Random;

import net.minecraftforge.common.config.Configuration;

/**
 * This helper class automatically creates, stores, and retrieves properties.
 * Supported data types:
 * String, boolean, int, double
 * 
 * Any property can be retrieved as an Object or String.
 * Any non-String property can also be retrieved as any other non-String property.
 * Retrieving a number as a boolean will produce a randomized output depending on the value.
 */
public abstract class Properties {
    /// Mapping of all properties in the mod to their values.
    private static final HashMap<String, Object> map = new HashMap();
    /// Common category names.
    public static final String GENERAL = "_general";
    public static final String SPAWNING = "spawning";

    /// Initializes these properties.
    public static void init(Configuration config) {
        config.load();

        Properties.add(config, Properties.GENERAL, "animated_texture", true, "If true, lava monsters will have animated textures.");
        Properties.add(config, Properties.GENERAL, "monster_armor", 0, "The amount of armor lava monsters have.");
        Properties.add(config, Properties.GENERAL, "monster_health", 16.0, "Lava monsters' maximum health.");
        Properties.add(config, Properties.GENERAL, "monster_attack_cooldown", 80, "Ticks a monster must wait after attacking before it can start winding up again.");
        Properties.add(config, Properties.GENERAL, "monster_attack_shots", 3, "Number of fireballs shot with each attack.");
        Properties.add(config, Properties.GENERAL, "monster_attack_spacing", 6, "Ticks between each fireball shot in an attack.");
        Properties.add(config, Properties.GENERAL, "monster_attack_windup", 60, "Ticks it takes before a monster can start an attack.");

        Properties.add(config, Properties.SPAWNING, "Nether_spawn", true, "If true, lava monsters will spawn in the Nether.");
        Properties.add(config, Properties.SPAWNING, "depth_hazard", false, "If true, lava monsters will not spawn above layer 16.");
        Properties.add(config, Properties.SPAWNING, "flowing_lava", false, "If true, lava monsters do not require a source block to spawn.");
        Properties.add(config, Properties.SPAWNING, "shallow_lava", false, "If true, lava monsters will be able to spawn in lava one block deep.");
        Properties.add(config, Properties.SPAWNING, "spawn_chance", 0.05, "The chance for a lava monster spawn attempt to be successful.");
        Properties.add(config, Properties.SPAWNING, "spawn_frequency", 10, "The number of ticks between each lava monster spawn attempt.");

        config.addCustomCategoryComment(Properties.GENERAL, "General and/or miscellaneous options.");
        config.addCustomCategoryComment(Properties.SPAWNING, "Options dictating the spawning algorithm for lava monsters.");
        config.save();
    }

    /// Gets the mod's random number generator.
    public static Random random() {
        return _LavaMonsters.random;
    }

    /// Passes to the mod.
    public static void debugException(String message) {
        _LavaMonsters.debugException(message);
    }

    /// Loads the property as the specified value.
    public static void add(Configuration config, String category, String field, String defaultValue, String comment) {
        Properties.map.put(category + "@" + field, config.get(category, field, defaultValue, comment).getString());
    }

    public static void add(Configuration config, String category, String field, int defaultValue, String comment) {
        Properties.map.put(category + "@" + field, Integer.valueOf(config.get(category, field, defaultValue, comment).getInt(defaultValue)));
    }

    public static void add(Configuration config, String category, String field, boolean defaultValue, String comment) {
        Properties.map.put(category + "@" + field, Boolean.valueOf(config.get(category, field, defaultValue, comment).getBoolean(defaultValue)));
    }

    public static void add(Configuration config, String category, String field, double defaultValue, String comment) {
        Properties.map.put(category + "@" + field, Double.valueOf(config.get(category, field, defaultValue, comment).getDouble(defaultValue)));
    }

    /// Gets the Object property.
    public static Object getProperty(String category, String field) {
        return Properties.map.get(category + "@" + field);
    }

    /// Gets the value of the property (instead of an Object representing it).
    public static String getString(String category, String field) {
        return Properties.getProperty(category, field).toString();
    }

    public static boolean getBoolean(String category, String field) {
        Object property = Properties.getProperty(category, field);
        if (property instanceof Boolean)
            return ((Boolean) property).booleanValue();
        if (property instanceof Integer)
            return Properties.random().nextInt( ((Number) property).intValue()) == 0;
        if (property instanceof Double)
            return Properties.random().nextDouble() < ((Number) property).doubleValue();
        Properties.debugException("Tried to get boolean for invalid property! @" + property == null ? "(null)" : property.getClass().getName());
        return false;
    }

    public static int getInt(String category, String field) {
        Object property = Properties.getProperty(category, field);
        if (property instanceof Number)
            return ((Number) property).intValue();
        if (property instanceof Boolean)
            return ((Boolean) property).booleanValue() ? 1 : 0;
        Properties.debugException("Tried to get int for invalid property! @" + property == null ? "(null)" : property.getClass().getName());
        return 0;
    }

    public static double getDouble(String category, String field) {
        Object property = Properties.getProperty(category, field);
        if (property instanceof Number)
            return ((Number) property).doubleValue();
        if (property instanceof Boolean)
            return ((Boolean) property).booleanValue() ? 1.0 : 0.0;
        Properties.debugException("Tried to get double for invalid property! @" + property == null ? "(null)" : property.getClass().getName());
        return 0.0;
    }
}