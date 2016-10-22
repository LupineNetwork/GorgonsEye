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

import de.tr7zw.itemnbtapi.NBTItem;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Majora320 &lt;Majora320@gmail.com&gt;
 */
public class EyeCommand implements CommandExecutor {
    private final String mustBePlayerMsg;
    private final List<String> gorgonsEyeLore;
    
    public EyeCommand(String mustBePlayerMsg, List<String> gorgonsEyeLore) {
        this.mustBePlayerMsg = mustBePlayerMsg;
        this.gorgonsEyeLore = gorgonsEyeLore;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(mustBePlayerMsg);
        }
        
        Player player = (Player)sender;
        
        ItemStack eye = new ItemStack(Material.STICK);
        
        ItemMeta meta = eye.getItemMeta();
        meta.setLore(gorgonsEyeLore);
        
        eye.setItemMeta(meta);
        
        NBTItem nbt = new NBTItem(eye);
        nbt.setBoolean("gorgons-eye", true);
        
        player.getInventory().addItem(eye);
        
        return true;
    }
}
