package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 * @author Tetiana Iefimenko
 */
public class DataSourceType {

    private String type;
    private String typeValue;
    private String labelMessage;

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
}
