package pize.tests.minecraft.client.auth;

import pize.util.StringUtils;

import java.util.UUID;

public record PlayerProfile(UUID id, String name){

    public PlayerProfile{
        if(id == null || StringUtils.isBlank(name))
            throw new IllegalArgumentException("Invalid ID or Name");
    }

}
