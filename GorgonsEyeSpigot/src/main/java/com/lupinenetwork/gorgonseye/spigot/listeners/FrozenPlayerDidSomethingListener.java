/*
 * Copyright (C) 2016 Lupine Network <bedev@twpclan.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.lupinenetwork.gorgonseye.spigot.listeners;

import com.lupinenetwork.gorgonseye.spigot.database.FrozenPlayerManager;
import com.lupinenetwork.gorgonseye.spigot.database.GorgonsEyeDatabaseException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author Majora320 &lt;Majora320@gmail.com&gt;
 */
public class FrozenPlayerDidSomethingListener implements Listener {
    private final FrozenPlayerManager manager;
    
    public FrozenPlayerDidSomethingListener(FrozenPlayerManager manager) {
        this.manager = manager;
    }
    
    @EventHandler
    public void onPlayerMoved(PlayerMoveEvent evt) {
        try {
            if (manager.isPlayerFrozen(evt.getPlayer()))
                evt.setCancelled(true);
        } catch (GorgonsEyeDatabaseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent evt) {
        try {
            if (evt.getEntity() instanceof Player && manager.isPlayerFrozen((Player)evt.getEntity()))
                evt.setCancelled(true);
        } catch (GorgonsEyeDatabaseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @EventHandler
    public void onPlayerInventoryEvent(InventoryInteractEvent evt) {
        try {
            if (evt.getWhoClicked() instanceof Player && manager.isPlayerFrozen((Player)evt.getWhoClicked()))
                evt.setCancelled(true);
        } catch (GorgonsEyeDatabaseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
