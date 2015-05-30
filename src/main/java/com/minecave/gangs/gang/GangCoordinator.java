/*
 * Copyright (C) 2011-Current Richmond Steele (Not2EXceL) (nasm) <not2excel@gmail.com>
 * 
 * This file is part of gangs.
 * 
 * gangs can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
package com.minecave.gangs.gang;

import com.minecave.gangs.Gangs;
import com.minecave.gangs.storage.Messages;
import com.minecave.gangs.storage.MsgVar;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GangCoordinator {

    @Getter
    private final Map<String, Gang> gangMap;
    @Getter
    private final Gangs gangs;

    public GangCoordinator(Gangs gangs) {
        gangMap = new HashMap<>();
        this.gangs = gangs;
    }

    //probably should fix the logic here, its kinda backwards lol

    public static boolean isChunkClaimed(Chunk chunk) {
        return Gangs.getInstance().getGangCoordinator().getGangMap().values().stream().anyMatch(g -> g.isChunkClaimed(chunk));
    }

    //NOTE: farmable and spawn chunks should be found via the Gang object
    // which can be referenced from the Hoodlum object

    public void createGang(String name, Player owner) {
        String keyName = name.toLowerCase();
        if (!gangMap.containsKey(keyName)) {
            Gang gang = new Gang(name, owner);
            gangMap.put(keyName, gang);
        }
    }

    public void disbandGang(String name, boolean silent) {
        String keyName = name.toLowerCase();
        if (gangMap.containsKey(keyName)) {
            Gang gang = gangMap.remove(keyName);
            gang.getMembers().forEach(h -> {
                h.setGang(null);
                h.setRole(GangRole.GANGLESS);
            });
            if(!silent){
                Bukkit.getServer().broadcastMessage(Messages.get("disbandFaction",
                        MsgVar.GANG.var(), gang.getName()));
            }
        }
    }

    public void loadGangs() {

    }

    public void unloadGangs() {

    }

    public Gang getGang(Chunk chunk) {
        for(Gang gang : gangMap.values()) {
            if(gang.getClaims().contains(chunk)) {
                return gang;
            }
        }
        return null;
    }

    public boolean gangExists(String name){
        return gangMap.containsKey(name.toLowerCase());
    }

    public Gang getGang(String name){
        return gangMap.get(name.toLowerCase());
    }
}

