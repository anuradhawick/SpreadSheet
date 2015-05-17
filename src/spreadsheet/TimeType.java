package spreadsheet;

public final class TimeType extends DataType {
    private String Time;

    @Override
    public String getFormattedData() {
        return this.Time;
    }

    @Override
    public void setFormattedData(String data) {
        this.Time = data;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
