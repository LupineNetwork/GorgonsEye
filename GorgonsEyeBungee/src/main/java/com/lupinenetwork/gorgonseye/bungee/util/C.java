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
package com.lupinenetwork.gorgonseye.bungee.util;

import net.md_5.bungee.api.ChatColor;

/**
 * Abbreviation for ChatColor.
 * 
 * @author Majora320 &lt;Majora320@gmail.com&gt;
 */
public final class C {
    /**
     * Translates color codes in the format '&char'
     * 
     * @param s the string to translate
     * @return the translated string
     */
    public static final String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
