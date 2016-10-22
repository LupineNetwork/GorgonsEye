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
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Majora320 &lt;Majora320@gmail.com&gt;
 */
public class EyeHitPlayerListener implements Listener {
    private final FrozenPlayerManager manager;
    
    public EyeHitPlayerListener(FrozenPlayerManager manager) {
        this.manager = manager;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEyeHitPlayer(EntityDamageByEntityEvent evt) {
        if (!(evt.getDamager() instanceof Player || evt.getEntity() instanceof Player))
            return;
        
        Player damager = (Player)evt.getDamager();
        Player damaged = (Player)evt.getEntity();
        
        if (!new NBTItem(damager.getInventory().getItemInMainHand()).hasKey("gorgons-eye"))
            return;
        
        try {
            manager.toggleFrozen(damaged);
        } catch (GorgonsEyeDatabaseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
