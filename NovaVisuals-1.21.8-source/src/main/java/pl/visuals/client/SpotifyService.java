package pl.visuals.client;

import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class SpotifyService {
    private final HttpClient http = HttpClient.newHttpClient();
    private volatile String nowPlaying = "Spotify: brak polaczenia";
    private long lastCheck = 0;
    private boolean busy = false;
    public String display(){return nowPlaying;}
    public void tick(){
        if(!VisualsClient.settings.spotify || busy || System.currentTimeMillis()-lastCheck<6000) return;
        lastCheck=System.currentTimeMillis();
        String token=VisualsClient.settings.spotifyAccessToken;
        if(token==null || token.isBlank()){nowPlaying="Spotify: ustaw token w configu";return;}
        busy=true;
        CompletableFuture.runAsync(()->{
            try {
                HttpRequest req=HttpRequest.newBuilder(URI.create("https://api.spotify.com/v1/me/player/currently-playing"))
                    .header("Authorization","Bearer "+token).GET().build();
                HttpResponse<String> res=http.send(req,HttpResponse.BodyHandlers.ofString());
                if(res.statusCode()==204){nowPlaying="Spotify: nic nie gra";return;}
                if(res.statusCode()==401){nowPlaying="Spotify: token wygasl";return;}
                if(res.statusCode()!=200){nowPlaying="Spotify: HTTP "+res.statusCode();return;}
                var obj=JsonParser.parseString(res.body()).getAsJsonObject();
                String song=obj.getAsJsonObject("item").get("name").getAsString();
                String artist=obj.getAsJsonObject("item").getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();
                nowPlaying=(obj.get("is_playing").getAsBoolean()?"▶ ":"⏸ ")+song+" — "+artist;
            } catch(Exception ex){nowPlaying="Spotify: brak odpowiedzi";}
            finally{busy=false;}
        });
    }
}
