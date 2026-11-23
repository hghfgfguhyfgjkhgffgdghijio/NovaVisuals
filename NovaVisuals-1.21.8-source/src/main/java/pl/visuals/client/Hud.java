package pl.visuals.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class Hud {
    static void line(DrawContext ctx, String value, int x, int y) {
        Settings s = VisualsClient.settings;
        int width = MinecraftClient.getInstance().textRenderer.getWidth(value);
        int a = Math.max(25, Math.min(100, s.opacity)) * 255 / 100;
        ctx.fill(x-3,y-3,x+width+5,y+12,(a << 24) | 0x10101A);
        ctx.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal(value), x,y,0xFF000000 | s.color);
    }
    public static void draw(DrawContext ctx) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden || mc.currentScreen instanceof VisualsScreen) return;
        Settings s = VisualsClient.settings;
        ctx.getMatrices().pushMatrix();
        ctx.getMatrices().scale(Math.max(0.5f,Math.min(2f,s.scale / 100f)), Math.max(0.5f,Math.min(2f,s.scale / 100f)));
        if (s.fps) line(ctx, "FPS: " + mc.getCurrentFps(), s.fpsX, s.fpsY);
        if (s.coordinates) line(ctx, "XYZ: " + mc.player.getBlockX() + " " + mc.player.getBlockY() + " " + mc.player.getBlockZ(), s.coordsX,s.coordsY);
        if (s.keystrokes) {
            GameOptions o = mc.options;
            line(ctx, (o.forwardKey.isPressed()?"[W]":" W "),s.keysX+18,s.keysY);
            line(ctx,(o.leftKey.isPressed()?"[A]":" A ")+" "+(o.backKey.isPressed()?"[S]":" S ")+" "+(o.rightKey.isPressed()?"[D]":" D "),s.keysX,s.keysY+16);
            line(ctx,(o.jumpKey.isPressed()?"[SPACE]":" SPACE "),s.keysX,s.keysY+32);
        }
        if (s.armor) {
            int y=s.armorY;
            for (ItemStack stack:mc.player.getInventory().armor) {
                if (!stack.isEmpty()) { line(ctx,stack.getName().getString() + (stack.isDamageable()?" " + (stack.getMaxDamage()-stack.getDamage()) : ""),s.armorX,y); y+=16; }
            }
        }
        if (s.effects) {
            int y=s.effectsY;
            for (StatusEffectInstance effect:mc.player.getStatusEffects()) {
                line(ctx,effect.getEffectType().value().getName().getString() + " " + (effect.getAmplifier()+1),s.effectsX,y); y+=16;
            }
        }
        if (s.spotify) line(ctx,"♫ " + VisualsClient.spotify.display(),s.spotifyX,s.spotifyY);
        ctx.getMatrices().popMatrix();
    }
}
