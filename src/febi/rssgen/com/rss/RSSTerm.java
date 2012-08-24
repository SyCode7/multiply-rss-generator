/*
 * This file is part of MultiplyRSSGenerator.
 * 
 * MultiplyRSSGenerator is free software: you can redistribute it and/or modify
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
 * 
 * @author Hidayat Febiansyah
 * @email havban@gmail.com
 * @link http://code.google.com/p/multiply-rss-generator/
 * @year 2012
 */

package febi.rssgen.com.rss;

public abstract class RSSTerm {

    private String name;
    private String nicename;
    private int type;
    public static int CATEGORY = 0;
    public static int TAG = 1;
    public static final int POST = 2;
    public static final int COMMENT = 3;
    public static final int ATTACHMENT = 4;

    public RSSTerm(String name, int type) {
        this.name = name;
        this.type = type;

        this.nicename = name.replaceAll("\\W", "-");
    }

    public String getName() {
        return name;
    }

    public String getNicename() {
        return nicename;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public abstract String getFormatted();
}
