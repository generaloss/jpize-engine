package glit.tests.minecraft.server.server;

import glit.tests.minecraft.server.entity.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerList{

    private final Server serverOf;
    private final Map<UUID, ServerPlayerEntity> players;

    public PlayerList(Server serverOf){
        this.serverOf = serverOf;
        players = new HashMap<>();
    }



}
