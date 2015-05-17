package spreadsheet;

public abstract class DataType implements datatypeMethods, cellMethods {
    private String type,
                   enteredVal = null;

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setRawData(String data) {
        this.enteredVal = data;
    }

    @Override
    public String getRawData() {
        return this.enteredVal;
    }

    @Override
    public abstract String getFormattedData();

    @Override
    public abstract void setFormattedData(String data);
}


//~ Formatted by Jindent --- http://www.jindent.com
