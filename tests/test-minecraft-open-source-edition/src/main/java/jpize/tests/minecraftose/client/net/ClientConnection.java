package jpize.tests.minecraftose.client.net;

import jpize.Jpize;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.tests.minecraftose.client.ClientGame;
import jpize.tests.minecraftose.client.chunk.ClientChunk;
import jpize.tests.minecraftose.client.entity.LocalPlayer;
import jpize.tests.minecraftose.client.entity.RemotePlayer;
import jpize.tests.minecraftose.main.chat.MessageSourceServer;
import jpize.tests.minecraftose.main.entity.Entity;
import jpize.tests.minecraftose.main.net.packet.clientbound.*;
import jpize.tests.minecraftose.main.net.packet.serverbound.SBPacketAuth;
import jpize.tests.minecraftose.main.net.packet.serverbound.SBPacketEncryptEnd;
import jpize.tests.minecraftose.main.net.packet.serverbound.SBPacketRenderDistance;
import jpize.tests.minecraftose.main.text.Component;

public class ClientConnection implements PacketHandler{

    private final ClientGame game;
    private TcpConnection connection;

    public ClientConnection(ClientGame game){
        this.game = game;
    }

    public ClientGame getGame(){
        return game;
    }

    public TcpConnection getConnection(){
        return connection;
    }

    public void setConnection(TcpConnection connection){
        this.connection = connection;
    }

    public void sendPacket(IPacket<?> packet){
        game.sendPacket(packet);
    }



    public void time(CBPacketTime packet){
        game.getTime().setTicks(packet.gameTimeTicks);
    }

    public void teleportPlayer(CBPacketTeleportPlayer packet){
        final LocalPlayer localPlayer = game.getPlayer();
        if(localPlayer == null)
            return;

        // Load another level
        if(!packet.levelName.equals(localPlayer.getLevel().getConfiguration().getName())){
            game.createClientLevel(packet.levelName);
            game.getLevel().getChunkManager().startLoadChunks();
            localPlayer.setLevel(game.getLevel());
        }

        localPlayer.getPosition().set(packet.position);
        localPlayer.getRotation().set(packet.rotation);
    }

    public void spawnPlayer(CBPacketSpawnPlayer packet){
        final RemotePlayer remotePlayer = new RemotePlayer(game.getLevel(), packet.playerName);
        remotePlayer.getPosition().set(packet.position);
        remotePlayer.getRotation().set(packet.rotation);
        remotePlayer.setUUID(packet.uuid);

        game.getLevel().addEntity(remotePlayer);
    }

    public void spawnInfo(CBPacketSpawnInfo packet){
        game.createClientLevel(packet.levelName);
        game.spawnPlayer(packet.position);
        game.getLevel().getChunkManager().startLoadChunks();

        sendPacket(new SBPacketRenderDistance(game.getSession().getOptions().getRenderDistance()));
    }

    public void spawnEntity(CBPacketSpawnEntity packet){
        final Entity entity = packet.type.createEntity(game.getLevel());
        entity.getPosition().set(packet.position);
        entity.getRotation().set(packet.rotation);
        entity.setUUID(packet.uuid);

        game.getLevel().addEntity(entity);
    }

    public void removeEntity(CBPacketRemoveEntity packet){
        game.getLevel().removeEntity(packet.uuid);
    }

    public void pong(CBPacketPong packet){
        final String message = "Ping - " + String.format("%.5f", (System.nanoTime() - packet.timeNanos) / Maths.NanosInSecond) + " ms";
        game.getChat().putMessage(new MessageSourceServer(), new Component().text(message));
        System.out.println("[Client]: " + message);
    }

    public void playSound(CBPacketPlaySound packet){
        final Vec3f camPosition = game.getCamera().getPosition();

        Jpize.execSync(() ->
            game.getSession().getSoundPlayer().play(
                packet.sound,
                packet.volume, packet.pitch,
                packet.x - camPosition.x,
                packet.y - camPosition.y,
                packet.z - camPosition.z
            )
        );
    }

    public void playerSneaking(CBPacketPlayerSneaking packet){
        final Entity targetEntity = game.getLevel().getEntity(packet.playerUUID);
        if(targetEntity instanceof RemotePlayer player)
            player.setSneaking(packet.sneaking);
    }

    public void lightUpdate(CBPacketLightUpdate packet){
        final ClientChunk chunk = game.getLevel().getChunkManager().getChunk(packet.position.x, packet.position.z);
        if(chunk == null)
            return;

        final byte[] chunkLight = chunk.getSection(packet.position.y).light;
        System.arraycopy(packet.light, 0, chunkLight, 0, chunkLight.length);

        chunk.rebuild(true);
    }

    public void entityMove(CBPacketEntityMove packet){
        Entity targetEntity = game.getLevel().getEntity(packet.uuid);
        if(targetEntity == null && game.getPlayer().getUUID() == packet.uuid)
            targetEntity = game.getPlayer();

        if(targetEntity != null){
            targetEntity.getPosition().set(packet.position);
            targetEntity.getRotation().set(packet.rotation);
            targetEntity.getVelocity().set(packet.velocity);
        }
    }

    public void encryptStart(CBPacketEncryptStart packet){
        byte[] encryptedClientKey = packet.publicServerKey.encrypt(game.getEncryptKey().getKey().getEncoded());
        sendPacket( new SBPacketEncryptEnd(encryptedClientKey) );

        connection.encode(game.getEncryptKey());// * шифрование *

        sendPacket(new SBPacketAuth(game.getSession().getSessionToken()));
    }

    public void disconnect(CBPacketDisconnect packet){
        game.disconnect();
        System.out.println("[Client]: Connection closed: " + packet.reasonComponent);
    }

    public void chunk(CBPacketChunk packet){
        game.getLevel().getChunkManager().receivedChunk(packet);
    }

    public void chatMessage(CBPacketChatMessage packet){
        game.getChat().putMessage(packet);
    }

    public void blockUpdate(CBPacketBlockUpdate packet){
        game.getLevel().setBlockState(packet.x, packet.y, packet.z, packet.blockData);


        if(packet.blockData == 0)
            for(int i = 0; i < 100; i++)
                game.spawnParticle(game.getSession().BREAK_PARTICLE, new Vec3f(
                    packet.x + Maths.random(1F),
                    packet.y + Maths.random(1F),
                    packet.z + Maths.random(1F)
                ));
    }

    public void abilities(CBPacketAbilities packet){
        game.getPlayer().setFlyEnabled(packet.flyEnabled);
    }

}
