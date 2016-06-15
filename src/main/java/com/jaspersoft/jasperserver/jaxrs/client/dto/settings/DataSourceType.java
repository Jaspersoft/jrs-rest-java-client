package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 * @author Tetiana Iefimenko
 */
public class DataSourceType {

    private String type;
    private String typeValue;
    private String labelMessage;

    public DataSourceType() {
    }

    public DataSourceType(DataSourceType other) {
        this.type = other.type;
        this.typeValue = other.typeValue;
        this.labelMessage = other.labelMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getLabelMessage() {
        return labelMessage;
    }

    public void setLabelMessage(String labelMessage) {
        this.labelMessage = labelMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourceType)) return false;

        DataSourceType that = (DataSourceType) o;

        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
        if (getTypeValue() != null ? !getTypeValue().equals(that.getTypeValue()) : that.getTypeValue() != null)
            return false;
        return !(getLabelMessage() != null ? !getLabelMessage().equals(that.getLabelMessage()) : that.getLabelMessage() != null);

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getTypeValue() != null ? getTypeValue().hashCode() : 0);
        result = 31 * result + (getLabelMessage() != null ? getLabelMessage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataSourceType{" +
                "type='" + type + '\'' +
                ", typeValue='" + typeValue + '\'' +
                ", labelMessage='" + labelMessage + '\'' +
                '}';
    }
}
