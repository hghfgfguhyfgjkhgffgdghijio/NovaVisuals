package pl.visuals.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Settings {
    public boolean fps = true, keystrokes = true, armor = true, effects = true, spotify = true, coordinates = false;
    public int color = 0xAA66FF, opacity = 85, scale = 100;
    public int fpsX = 8, fpsY = 8, keysX = 8, keysY = 54, armorX = 8, armorY = 130, effectsX = 8, effectsY = 160, spotifyX = 8, spotifyY = 200, coordsX = 8, coordsY = 32;
    public String spotifyClientId = "";
    public String spotifyAccessToken = "";
    public String spotifyRefreshToken = "";
    public long spotifyTokenExpiry = 0;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("novavisuals.json");
    public static Settings load() {
        try { if (Files.exists(FILE)) { Settings s = GSON.fromJson(Files.readString(FILE), Settings.class); if (s != null) return s; } }
        catch (Exception e) { System.err.println("NovaVisuals: config load: " + e); }
        return new Settings();
    }
    public void save() {
        try { Files.createDirectories(FILE.getParent()); Files.writeString(FILE, GSON.toJson(this)); }
        catch (Exception e) { System.err.println("NovaVisuals: config save: " + e); }
    }
}
