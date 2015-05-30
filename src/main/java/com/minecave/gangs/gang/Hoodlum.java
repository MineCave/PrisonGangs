package com.minecave.gangs.gang;

import com.minecave.gangs.storage.JsonConfigurable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by Carter on 5/25/2015.
 */
public class Hoodlum implements JsonConfigurable {

    @Getter
    private final UUID playerUUID;
    @Getter
    private volatile int power;
    @Getter
    @Setter
    private int maxPower;
    @Getter
    @Setter
    private Gang gang;
    @Getter
    @Setter
    private GangRole role;
    @Getter
    @Setter
    private LocalDateTime lastOnline;
    @Getter
    @Setter
    private LocalDateTime lastOffline;
    @Getter
    private List<String> invites;

    public Hoodlum(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    /**
     * Loads things from the database
     */
    private void loadPlayerFromUuid() {

    }

    public void addPower(int amount) {
        addPower(amount, false);
    }

    public synchronized void addPower(int amount, boolean force) {
        power = (power + amount > maxPower ? (force ? amount + power : maxPower) : amount + power);
    }

    public void removePower(int amount) {
        removePower(amount, false);
    }

    public synchronized void removePower(int amount, boolean force) {
        this.power = (power - amount < -maxPower ? (force ? power - amount : maxPower) : power - amount);
    }

    public synchronized int getPower() {
        return power;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public boolean isInGang() {
        return gang != null;
    }

    public boolean hasRole(GangRole role){
        return this.role.ordinal() >= role.ordinal();
    }

    public int getMinPower() {
        return -maxPower;
    }

    public void sendMessage(String message) {
        Player player = Bukkit.getPlayer(playerUUID);
        if(player.isOnline())
            player.sendMessage(message);
    }

    public void updateLastTimes() {
        setLastOnline(LocalDateTime.now());
        setLastOffline(LocalDateTime.now());
    }

    public boolean isOnline() {
        Player player = getPlayer();
        return player != null && player.isOnline();
    }
}
