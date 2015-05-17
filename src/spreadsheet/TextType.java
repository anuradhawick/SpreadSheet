package spreadsheet;

public final class TextType extends DataType {
    private String text = null;

    @Override
    public String getFormattedData() {
        return this.text;
    }

    @Override
    public void setFormattedData(String data) {
        this.text = data;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
