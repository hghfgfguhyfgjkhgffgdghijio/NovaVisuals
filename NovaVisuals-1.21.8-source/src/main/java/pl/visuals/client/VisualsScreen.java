package pl.visuals.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class VisualsScreen extends Screen {
    private int page = 0;
    private final String[] labels={"FPS", "Keystrokes", "Armor HUD", "Potion HUD", "Spotify HUD", "Koordynaty"};
    protected VisualsScreen(){super(Text.literal("NovaVisuals"));}
    @Override protected void init(){
        super.init();
        int x=width/2-145,y=height/2-100;
        addDrawableChild(ButtonWidget.builder(Text.literal("HUD"),b->{page=0;clearAndInit();}).dimensions(x,y,90,20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Styl"),b->{page=1;clearAndInit();}).dimensions(x+100,y,90,20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("FPS Boost"),b->{page=2;clearAndInit();}).dimensions(x+200,y,90,20).build());
        if(page==0){
            for(int i=0;i<labels.length;i++){
                final int n=i;
                addDrawableChild(ButtonWidget.builder(Text.literal(labels[i]+": "+(get(n)?"ON":"OFF")),b->{set(n,!get(n));clearAndInit();}).dimensions(x+(i%2)*150,y+30+(i/2)*27,140,22).build());
            }
            addDrawableChild(ButtonWidget.builder(Text.literal("Przesun HUD (przeciagnij)"),b->client.setScreen(new HudEditorScreen())).dimensions(x,y+121,290,20).build());
        } else if(page==1){
            int[] colors={0xAA66FF,0xFF5555,0x55AAFF,0x55FFAA,0xFFFFFF};
            String[] names={"Fiolet","Czerwien","Niebieski","Mieta","Bialy"};
            for(int i=0;i<colors.length;i++){final int c=colors[i];addDrawableChild(ButtonWidget.builder(Text.literal(names[i]),b->{VisualsClient.settings.color=c;save();}).dimensions(x+(i%3)*98,y+30+(i/3)*28,94,22).build());}
            addDrawableChild(ButtonWidget.builder(Text.literal("Skala -"),b->{VisualsClient.settings.scale=Math.max(50,VisualsClient.settings.scale-10);save();}).dimensions(x,y+92,90,20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Skala +"),b->{VisualsClient.settings.scale=Math.min(200,VisualsClient.settings.scale+10);save();}).dimensions(x+100,y+92,90,20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Przezroczystosc -"),b->{VisualsClient.settings.opacity=Math.max(25,VisualsClient.settings.opacity-10);save();}).dimensions(x,y+118,140,20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("Przezroczystosc +"),b->{VisualsClient.settings.opacity=Math.min(100,VisualsClient.settings.opacity+10);save();}).dimensions(x+150,y+118,140,20).build());
        } else {
            addDrawableChild(ButtonWidget.builder(Text.literal("LOW"),b->preset(0)).dimensions(x,y+40,90,22).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("BALANCED"),b->preset(1)).dimensions(x+100,y+40,90,22).build());
            addDrawableChild(ButtonWidget.builder(Text.literal("MAX FPS"),b->preset(2)).dimensions(x+200,y+40,90,22).build());
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("Zapisz / Zamknij"),b->{save();close();}).dimensions(x,y+154,290,20).build());
    }
    private void preset(int mode){
        var o=client.options;
        // Ustawienia bezpieczne: nie zmieniamy rozdzielczosci ani plikow gracza.
        o.getViewDistance().setValue(mode==2?4:mode==1?8:12);
        o.getSimulationDistance().setValue(mode==2?4:mode==1?6:10);
        o.getParticles().setValue(mode==2?net.minecraft.client.option.ParticlesMode.MINIMAL:mode==1?net.minecraft.client.option.ParticlesMode.DECREASED:net.minecraft.client.option.ParticlesMode.ALL);
        o.write();
    }
    private boolean get(int i){Settings s=VisualsClient.settings;return switch(i){case 0->s.fps;case 1->s.keystrokes;case 2->s.armor;case 3->s.effects;case 4->s.spotify;default->s.coordinates;};}
    private void set(int i,boolean b){Settings s=VisualsClient.settings;switch(i){case 0->s.fps=b;case 1->s.keystrokes=b;case 2->s.armor=b;case 3->s.effects=b;case 4->s.spotify=b;default->s.coordinates=b;}save();}
    private void save(){VisualsClient.settings.save();}
    @Override public void render(DrawContext ctx,int mx,int my,float delta){
        super.render(ctx,mx,my,delta);
        ctx.drawCenteredTextWithShadow(textRenderer,"NOVAVISUALS 1.21.8",width/2,height/2-125,0xAA66FF);
        if(page==1)ctx.drawCenteredTextWithShadow(textRenderer,"Skala: "+VisualsClient.settings.scale+"% | Tlo: "+VisualsClient.settings.opacity+"%",width/2,height/2+46,0xFFFFFF);
        if(page==2)ctx.drawCenteredTextWithShadow(textRenderer,"LOW = oszczedny | MAX FPS = agresywne ograniczenia",width/2,height/2+10,0xFFFFFF);
    }
    @Override public boolean shouldPause(){return false;}
}
