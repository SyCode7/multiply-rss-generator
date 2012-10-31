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

package febi.rssgen.com.multiply.rss;

import febi.rssgen.com.rss.RSSGenerator;

public class MultiplyRSSGeneratorFactory {
    
    public static RSSGenerator createMultiplyRSSGenerator(String url, String folder){
        if(folder.equals("journal")) { 
            return new MultiplyJournalRSSGenerator(url,
                "Multiply RSS - " + url);
        }
        else if(folder.equals("reviews")) { 
            return new MultiplyReviewsRSSGenerator(url,
                "Multiply RSS - " + url);
        }
        else if(folder.equals("recipes")) { 
            return new MultiplyRecipesRSSGenerator(url,
                "Multiply RSS - " + url);
        }
        else if(folder.equals("notes")) { 
            return new MultiplyNotesRSSGenerator(url,
                "Multiply RSS - " + url);
        }
        else if(folder.equals("photos")) { 
            return new MultiplyPhotosRSSGenerator(url,
                "Multiply RSS - " + url);
        }
        return null;
    }
}
