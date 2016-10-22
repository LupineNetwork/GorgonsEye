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
package com.lupinenetwork.gorgonseye.spigot;

import com.lupinenetwork.gorgonseye.spigot.database.FrozenPlayerManager;
import com.lupinenetwork.gorgonseye.spigot.commands.EyeCommand;
import com.lupinenetwork.gorgonseye.spigot.commands.FreezeCommand;
import com.lupinenetwork.gorgonseye.spigot.listeners.EyeHitPlayerListener;
import com.lupinenetwork.gorgonseye.spigot.listeners.FrozenPlayerDidSomethingListener;
import com.lupinenetwork.gorgonseye.spigot.util.C;
import com.lupinenetwork.gorgonseye.spigot.util.SQLUtil;
import java.sql.Driver;
import java.util.ArrayList;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin class.
 * 
 * @author Majora320 &lt;Majora320@gmail.com&gt;
 */
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Configuration config = getConfig();
        
        String driverName = config.getString("mysql.driver", Constants.DEFAULT_DRIVER_NAME);
        
        Driver driver = SQLUtil.getDriver(driverName, getServer().getLogger());
        
        String url = config.getString("mysql.url", Constants.DEFAULT_URL);
        String username = config.getString("mysql.username");
        String password = config.getString("mysql.password");
        String primaryTableName = config.getString("mysql.table-name", Constants.DEFAULT_PRIMARY_TABLE_NAME);
        
        // Check table name
        if (!primaryTableName.matches("^[A-Za-z_]*$"))
            primaryTableName = Constants.DEFAULT_PRIMARY_TABLE_NAME;
        
        FrozenPlayerManager manager = new FrozenPlayerManager(
                C.c(getConfig().getString("messages.player-frozen", "&6The player {0} has been frozen!")), 
                C.c(getConfig().getString("messages.player-unfrozen", "&6The player {0} has been unfrozen.")),
                url, username, password, primaryTableName);
        
        getCommand("freeze").setExecutor(new FreezeCommand(manager, C.c(getConfig().getString("messages.no-such-player", "&cThe player {0} does not exist!"))));
        getCommand("eye").setExecutor(new EyeCommand(
                C.c(getConfig().getString("messages.must-be-player", "&cYou must be a player to execute this command!")),
                new ArrayList<String>() {{ add(C.c(getConfig().getString("lore.gorgons-eye", "&bBehold the gaze of administration..."))); }}));
        
        getServer().getPluginManager().registerEvents(new EyeHitPlayerListener(manager), this);
        getServer().getPluginManager().registerEvents(new FrozenPlayerDidSomethingListener(manager), this);
    }
}
