package dev.dionnek.impactblocks.listener;

import dev.dionnek.impactblocks.ImpactBlocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

public class ImpactBlocksListener implements Listener {
    private ImpactBlocks ImpactBlocks;
    public ImpactBlocksListener(ImpactBlocks ImpactBlocks) {
        this.ImpactBlocks = ImpactBlocks;
    }

    /**
     * This event checks if a player has fallen and, if so, performs a stomp action.
     *
     */
    @EventHandler
    public void onFall(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();

        if (e.getTo().getY() < e.getFrom().getY() && isFallAction(p)) {
            breakBlocks(loc);
        }
    }

    /**
     * Checks if a player can perform a break action based on their fall height.
     *
     * @param player The player being checked.
     * @return true if the player can perform a stomp action, otherwise false.
     */
    private boolean isFallAction(Player player) {
        double stompFallHeight = ImpactBlocks.getConfig().getDouble("blocks_fall_height");
        return player.isOnGround() && player.getFallDistance() >= stompFallHeight;
    }

    /**
     * Performs a break action by destroying random blocks and playing an explosion sound around a given location.
     *
     * @param location The location where the stomp action occurs.
     */
    private void breakBlocks(Location location) {
        int stompBlocksRadius = ImpactBlocks.getConfig().getInt("destroy_blocks_radius");
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int offsetX = random.nextInt(stompBlocksRadius * 2) - stompBlocksRadius;
            int offsetY = random.nextInt(stompBlocksRadius * 2) - stompBlocksRadius;
            int offsetZ = random.nextInt(stompBlocksRadius * 2) - stompBlocksRadius;

            Location blockLocation = location.clone().add(offsetX, offsetY, offsetZ);
            if (blockLocation.getBlock().getType() != Material.AIR) {
                blockLocation.getBlock().setType(Material.AIR);
                location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
            }
        }
    }
}