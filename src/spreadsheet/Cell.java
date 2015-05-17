package spreadsheet;

public class Cell implements Comparable<Cell> {
    public double   val   = 0;       // to be used for sorting purposes
    private String  font  = "Tahoma";
    private String  style = null;
    private String  size  = "12";    // stands for having not assigned a font size
    private String  color = "white";
    public DataType D;

    @Override
    public int compareTo(Cell o) {
        return (this.val > o.val)
               ? 1
               : ((this.val < o.val)
                  ? -1
                  : 0);
    }

    /**
     * @return the font
     */
    public String getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(String font) {
        this.font = font;
    }

    /**
     * @return the style
     */
    public String getStyle() {

        // B  bold
        // I italid
        // U underlined
        // st strike through
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {

        // B  bold
        // I italid
        // U underlined
        // st strike through
        this.style = style;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
