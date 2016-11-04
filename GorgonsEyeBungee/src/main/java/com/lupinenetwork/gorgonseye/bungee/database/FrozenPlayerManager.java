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
package com.lupinenetwork.gorgonseye.bungee.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 *
 * @author Majora320 &lt;Majora320@gmail.com&gt;
 */
public class FrozenPlayerManager {
    private final String url;
    private final String username;
    private final String password;
    private final String primaryTableName;

    public FrozenPlayerManager(String url, String username, String password, String primaryTableName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.primaryTableName = primaryTableName;
    }

    protected final void initializeDatabase(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Minecraft usernames are max 16 chars long
            stmt.execute("CREATE TABLE IF NOT EXISTS " + primaryTableName + "(id BIGINT NOT NULL AUTO_INCREMENT, player_name CHAR(16), PRIMARY KEY(id))");
        }
    }

    protected final Connection openConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        initializeDatabase(conn);
        return conn;
    }
    
    public boolean isPlayerFrozen(ProxiedPlayer player) throws GorgonsEyeDatabaseException {
        try (Connection conn = openConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT player_name FROM " + primaryTableName + " WHERE (player_name = ?)")) {
            stmt.setString(1, player.getName());
            
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            throw new GorgonsEyeDatabaseException(ex);
        }
    }
}
