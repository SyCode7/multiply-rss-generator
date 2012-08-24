/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package febi.rssgen.com.rss;

/**
 *
 * @author itrc169
 */
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
