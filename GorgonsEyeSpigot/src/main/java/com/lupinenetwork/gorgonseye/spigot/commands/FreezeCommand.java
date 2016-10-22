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
package com.lupinenetwork.gorgonseye.spigot.commands;

import com.lupinenetwork.gorgonseye.spigot.database.FrozenPlayerManager;
import com.lupinenetwork.gorgonseye.spigot.database.GorgonsEyeDatabaseException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**true
 *
 * @author Majora320 &lt;Majora320@gmail.com&gt;
 */
public class FreezeCommand implements CommandExecutor {
    private final String playerDoesNotExistMsg;
    private final FrozenPlayerManager manager;
    
    public FreezeCommand(FrozenPlayerManager manager, String playerDoesNotExistMsg) {
        this.playerDoesNotExistMsg = playerDoesNotExistMsg;
        this.manager = manager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) throws RuntimeException {
        if (args.length < 1)
            return false;
        
        String playerName = args[0];
        Player player = Bukkit.getServer().getPlayer(playerName);
        
        if (player == null)
            sender.sendMessage(playerDoesNotExistMsg);
        else {
            try {
                manager.toggleFrozen(player);
            } catch (GorgonsEyeDatabaseException ex) {
                throw new RuntimeException(ex);
            }
        }
        
        return true;
    }
}
