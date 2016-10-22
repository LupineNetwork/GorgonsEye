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
package com.lupinenetwork.gorgonseye.bungee.listeners;

import com.lupinenetwork.gorgonseye.bungee.database.FrozenPlayerManager;
import com.lupinenetwork.gorgonseye.bungee.database.GorgonsEyeDatabaseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 *
 * @author Majora320 &lt;Majora320@gmail.com&gt;
 */
public class PlayerTriedToExecuteCommandListener implements Listener {
    private final FrozenPlayerManager manager;
    private final String commandBlockedMsg;
    
    public PlayerTriedToExecuteCommandListener(FrozenPlayerManager manager, String commandBlockedMsg) {
        this.manager = manager;
        this.commandBlockedMsg = commandBlockedMsg;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerExecuteCommand(ChatEvent evt) {
        String cmd = evt.getMessage().trim();
        
        try {
            if (cmd.startsWith("/") && evt.getSender() instanceof ProxiedPlayer && manager.isPlayerFrozen((ProxiedPlayer)evt.getSender())) {
                ((ProxiedPlayer)evt.getSender()).sendMessage(new TextComponent(commandBlockedMsg));
                evt.setCancelled(true);
            }
        } catch (GorgonsEyeDatabaseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
