Based on Java, using Jsoup to parse, Gson to handle JSON data format, and one-jar to build into a single Jar file.

<div>
Version: 0.2.3<br />
Date: 2012/11/28<br />
License GPLv3<br />
</div>
<div>
Features:<br>
<ul>
<li>Parse and generate RSS/WXR files for Multiply journal, notes, reviews, recipes and photo albums</li>
<li>image attachment support</li>
<li>tags support</li>
<li>category support</li>
<li>date ranged export</li>
</ul>
</div>
<div>
<a href='http://febiansyah.name'><a href='http://febiansyah.name'>http://febiansyah.name</a></a><br />
<a href='http://febiansyah.name/apps/rssgen/multiply.php'><a href='http://febiansyah.name/apps/rssgen/multiply.php'>http://febiansyah.name/apps/rssgen/multiply.php</a></a>
</div>
<br />
What's new in this version (0.2.1):
<ol>
<blockquote><li>Use of <a href='http://jsoup.org/'>Jsoup</a> library to perform HTML parsing and fetch html element for further processing. Previously, we used RegEx that stalled so frequently in users' computers. Hope Jsoup will perform flawlessly now.</li>
<li>Use of <a href='https://sites.google.com/site/gson/'>Gson</a> library to parse photo data reference in photo albums page. The data format in javascript fits JSON format, so we can just throw it to the Gson parser.</li>
<li>Photo albums export by obtaining the maximum resolution image of <strong>600x600 pixels</strong>, not the hi-res (couldn't be obtained if you're not premium members). We then append them to the blog's content. Still not supporting comments parsing for single photo page, maybe will be supported in the next version.</li>
<li>Use of <a href='http://one-jar.sourceforge.net/'>One-Jar</a> library to pack required libraries into a single jar file, ease the use of the program.</li>
<li>Date range support, we can select certain date range for posts to be exported.</li>
<li>Bug fixing such as tags and categories registration, error handling and reporting.</li>
<li>Tested with thousands of posts and several multiply users, it seems more stable #finger_crossed</li>
<li>Archive file changed to MultiplyRSSGenerator-latest.zip for consistency in future update release.</li>
<li>Robustness by retrying to download page 3 times before giving up</li>
</ol>