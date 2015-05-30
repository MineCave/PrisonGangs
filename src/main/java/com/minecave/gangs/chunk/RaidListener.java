/*
 * Copyright (C) 2011-Current Richmond Steele (Not2EXceL) (nasm) <not2excel@gmail.com>
 * 
 * This file is part of gangs.
 * 
 * gangs can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
package com.minecave.gangs.chunk;

import com.minecave.gangs.Gangs;
import com.minecave.gangs.gang.Gang;
import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class RaidListener implements Listener {

    @Getter
    private Gangs plugin;

    public RaidListener(Gangs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.TNT) {
            return;
        }
        Chunk chunk = event.getBlock().getChunk();
        Player player = event.getPlayer();
        Gang gang = plugin.getGangCoordinator().getGang(chunk);
        if(gang != null) {
            if(!gang.hasPlayer(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();
        Gang gang = plugin.getGangCoordinator().getGang(chunk);
        if(gang != null) {
            if(!gang.hasPlayer(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof ArmorStand) {
            Player player = event.getPlayer();
            Chunk chunk = player.getLocation().getChunk();
            Gang gang = plugin.getGangCoordinator().getGang(chunk);
            if(gang != null) {
                if(!gang.hasPlayer(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}