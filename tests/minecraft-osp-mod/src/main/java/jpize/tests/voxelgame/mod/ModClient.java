package jpize.tests.voxelgame.mod;

import jpize.Jpize;
import jpize.files.Resource;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.glfw.key.Key;
import jpize.tests.minecraftosp.Minecraft;
import jpize.tests.minecraftosp.main.modification.api.ClientModInitializer;
import jpize.tests.minecraftosp.main.text.Component;
import jpize.tests.minecraftosp.main.text.StyleFormatting;
import jpize.tests.minecraftosp.main.text.TextColor;
import jpize.tests.minecraftosp.server.player.ServerPlayer;

import java.util.Collection;

public class ModClient implements ClientModInitializer{
    
    private TextureBatch batch;
    private Texture texture;
    private BitmapFont font;
    
    @Override
    public void onInitializeClient(){
        System.out.println("[Voxel Game Mod]: Mod initialized (Client)");
        
        batch = new TextureBatch();
        font = FontLoader.getDefault();
        texture = new Texture(new Resource("icon.png", ModClient.class));
    }
    
    
    public void render(){
        batch.begin();
        batch.draw(texture, Jpize.getWidth() - 100, Jpize.getHeight() - 30, 100, 30);
        font.drawText(batch, "Mod text", Jpize.getWidth() - 100, Jpize.getHeight() - 30);
        batch.end();
        
        if(Key.F10.isDown()){
            Collection<ServerPlayer> players = Minecraft.getInstance().getIntegratedServer().getPlayerList().getPlayers();
            for(ServerPlayer player: players)
                player.sendMessage(
                    new Component().color(TextColor.DARK_RED).text("<" + player.getName() + "> ").color(1, 0.5, 0.2).style(StyleFormatting.ITALIC).text("Mod text")
                );
        }
    }
    
}
