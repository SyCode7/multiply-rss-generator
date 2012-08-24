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

/**
 *
 * @author itrc169
 */
public class RSSCategory extends RSSTerm{
    
    public RSSCategory(String category){
        super(category, RSSTerm.CATEGORY);
        
    }
    
    @Override
    public String getFormatted(){
        String ret = "<wp:category>"
                //+ "<wp:term_id>3</wp:term_id>"
                + "<wp:category_nicename>"+getNicename()+"</wp:category_nicename>"
                + "<wp:category_parent></wp:category_parent>"
                + "<wp:cat_name><![CDATA["+getName()+"]]></wp:cat_name>"
                + "</wp:category>";
        return ret;
    }
    
    @Override
    public String toString(){
        return getFormatted();
    }
}
