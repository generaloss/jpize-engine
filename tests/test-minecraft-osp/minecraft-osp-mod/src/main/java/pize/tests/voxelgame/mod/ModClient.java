package pize.tests.voxelgame.mod;

import pize.Pize;
import pize.files.Resource;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.tests.voxelgame.Minecraft;
import pize.tests.voxelgame.main.modification.api.ClientModInitializer;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.text.StyleFormatting;
import pize.tests.voxelgame.main.text.TextColor;
import pize.tests.voxelgame.server.player.ServerPlayer;

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
        batch.draw(texture, Pize.getWidth() - 100, Pize.getHeight() - 30, 100, 30);
        font.drawText(batch, "Mod text", Pize.getWidth() - 100, Pize.getHeight() - 30);
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
