package pl.visuals.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {
    private final String[] names={"FPS","Keystrokes","Armor","Efekty","Spotify","XYZ"};
    private int dragging=-1;
    protected HudEditorScreen(){super(Text.literal("Edytor HUD"));}
    private int x(int i){Settings s=VisualsClient.settings;return switch(i){case 0->s.fpsX;case 1->s.keysX;case 2->s.armorX;case 3->s.effectsX;case 4->s.spotifyX;default->s.coordsX;};}
    private int y(int i){Settings s=VisualsClient.settings;return switch(i){case 0->s.fpsY;case 1->s.keysY;case 2->s.armorY;case 3->s.effectsY;case 4->s.spotifyY;default->s.coordsY;};}
    private void move(int i,int xx,int yy){Settings s=VisualsClient.settings;switch(i){case 0->{s.fpsX=xx;s.fpsY=yy;}case 1->{s.keysX=xx;s.keysY=yy;}case 2->{s.armorX=xx;s.armorY=yy;}case 3->{s.effectsX=xx;s.effectsY=yy;}case 4->{s.spotifyX=xx;s.spotifyY=yy;}default->{s.coordsX=xx;s.coordsY=yy;}}}
    @Override public void render(DrawContext ctx,int mx,int my,float delta){
        super.render(ctx,mx,my,delta);
        ctx.drawCenteredTextWithShadow(textRenderer,"Przeciagnij elementy | ESC = zapisz",width/2,12,0xAA66FF);
        for(int i=0;i<names.length;i++){
            int xx=x(i), yy=y(i); int w=textRenderer.getWidth(names[i])+14;
            ctx.fill(xx,yy,xx+w,yy+18,0xBB241A38);
            ctx.drawTextWithShadow(textRenderer,names[i],xx+7,yy+5,0xFFFFFFFF);
        }
    }
    @Override public boolean mouseClicked(net.minecraft.client.gui.Click click, boolean doubled){
        int mx=(int)click.x(),my=(int)click.y();
        for(int i=names.length-1;i>=0;i--){if(mx>=x(i)&&mx<=x(i)+textRenderer.getWidth(names[i])+14&&my>=y(i)&&my<=y(i)+18){dragging=i;return true;}}
        return super.mouseClicked(click,doubled);
    }
    @Override public boolean mouseDragged(net.minecraft.client.gui.Click click,double dx,double dy){
        if(dragging>=0){move(dragging,Math.max(0,(int)click.x()),Math.max(20,(int)click.y()));return true;}
        return super.mouseDragged(click,dx,dy);
    }
    @Override public boolean mouseReleased(net.minecraft.client.gui.Click click){dragging=-1;VisualsClient.settings.save();return super.mouseReleased(click);}
    @Override public void close(){VisualsClient.settings.save();client.setScreen(new VisualsScreen());}
    @Override public boolean shouldPause(){return false;}
}
