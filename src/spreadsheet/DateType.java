package spreadsheet;

public final class DateType extends DataType {
    private String formattedData;

    @Override
    public String getFormattedData() {
        return this.formattedData;
    }

    @Override
    public void setFormattedData(String data) {
        this.formattedData = data;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
