package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import java.util.List;

/**
 * Created by tetiana.iefimenko on 7/15/2015.
 */
public class DashboardSettings {
    private List<ItemRegistry> newItemsRegistry;

    public List<ItemRegistry> getNewItemsRegistry() {
        return newItemsRegistry;
    }

    public void setNewItemsRegistry(List<ItemRegistry> newItemsRegistry) {
        this.newItemsRegistry = newItemsRegistry;
    }
}
