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

public class RSSTag extends RSSTerm{
    
    public RSSTag(String tag){
        super(tag, RSSTerm.TAG);
    }
    
    @Override
    public String getFormatted(){
        String ret = "<wp:tag>"
                //+ "<wp:term_id>27</wp:term_id>"
                + "<wp:tag_slug>"+getNicename()+"</wp:tag_slug>"
                + "<wp:tag_name><![CDATA["+getName()+"]]>"
                + "</wp:tag_name></wp:tag>";
        return ret;
    }
    
    @Override
    public String toString(){
        return getFormatted();
    }
}
