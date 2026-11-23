package pl.visuals.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class VisualsClient implements ClientModInitializer {
    public static Settings settings;
    public static SpotifyService spotify;
    @Override public void onInitializeClient() {
        settings = Settings.load();
        spotify = new SpotifyService();
        KeyBinding menu = KeyBindingHelper.registerKeyBinding(new KeyBinding("NovaNovaVisuals: otworz menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "NovaVisuals"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menu.wasPressed()) client.setScreen(new VisualsScreen());
            spotify.tick();
        });
        HudRenderCallback.EVENT.register((context, tickCounter) -> Hud.draw(context));
    }
}
