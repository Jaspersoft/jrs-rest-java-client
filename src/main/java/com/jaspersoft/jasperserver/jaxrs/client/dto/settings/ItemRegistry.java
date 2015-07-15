package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 * Created by tetiana.iefimenko on 7/15/2015.
 */
public class ItemRegistry {

    private String id;
    private String label;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
