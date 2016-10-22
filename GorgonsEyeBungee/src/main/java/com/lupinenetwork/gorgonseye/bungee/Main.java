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
package com.lupinenetwork.gorgonseye.bungee;

import com.lupinenetwork.gorgonseye.bungee.database.FrozenPlayerManager;
import com.lupinenetwork.gorgonseye.bungee.listeners.PlayerTriedToExecuteCommandListener;
import com.lupinenetwork.gorgonseye.bungee.util.C;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Driver;
import java.util.logging.Level;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 *
 * @author Moses Miller &lt;Majora320@gmail.com&gt;
 */
public class Main extends Plugin {
    @Override
    public void onEnable() {
        Path configFile = Paths.get(getDataFolder().getAbsolutePath(), "config.yml");
        InputStream defaultConfig = getResourceAsStream("config.yml");
        Configuration config;
        
        try {
            if (!Files.exists(configFile)) {
                getDataFolder().mkdirs();
                Files.copy(defaultConfig, configFile);
            }
            
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException ex) {
            getLogger().log(Level.WARNING, "Failed to load config file", ex);
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(defaultConfig);
        }
        
        String driverName = config.getString("mysql.driver", Constants.DEFAULT_DRIVER_NAME);
        
        Driver driver = SQLUtil.getDriver(driverName, getLogger());
        
        String url = config.getString("mysql.url", Constants.DEFAULT_URL);
        String username = config.getString("mysql.username");
        String password = config.getString("mysql.password");
        String primaryTableName = config.getString("mysql.table-name", Constants.DEFAULT_PRIMARY_TABLE_NAME);
        
        // Check table name
        if (!primaryTableName.matches("^[A-Za-z_]*$"))
            primaryTableName = Constants.DEFAULT_PRIMARY_TABLE_NAME;
        
        getProxy().getPluginManager().registerListener(this, new PlayerTriedToExecuteCommandListener(new FrozenPlayerManager(
                C.c(config.getString("messages.player-frozen", "&6The player {0} has been frozen!")), 
                C.c(config.getString("messages.player-unfrozen", "&6The player {0} has been unfrozen.")), 
                url, username, password, primaryTableName), C.c(config.getString("messages.command-blocked", "&cYou cannot execute commands while frozen!"))));
    }
}
