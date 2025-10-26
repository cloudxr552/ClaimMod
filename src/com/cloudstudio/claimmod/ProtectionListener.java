package com.cloudstudio.claimmod;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.List;

public class ProtectionListener implements Listener {
    private final List<Claim> claims;

    public ProtectionListener(List<Claim> claims) {
        this.claims = claims;
    }

    private boolean inClaim(Block block) {
        for (Claim c : claims) {
            if (c.isInside(block)) return true;
        }
        return false;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!p.isOp() && inClaim(e.getBlock())) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "This area is claimed!");
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!p.isOp() && inClaim(e.getBlock())) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "This area is claimed!");
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (!p.isOp()) {
            for (Claim c : claims) {
                if (c.isInside(e.getItem().getLocation().getBlock())) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "You cannot pick up items in this area!");
                    break;
                }
            }
        }
    }
}
