package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import java.util.List;

/**
 * Created by tetiana.iefimenko on 7/15/2015.
 */
public class DashboardSettings {

    private List<ItemRegistry> newItemsRegistry;

    public DashboardSettings() {
    }

    public DashboardSettings(DashboardSettings other) {
        this.newItemsRegistry = other.newItemsRegistry;
    }

    public List<ItemRegistry> getNewItemsRegistry() {
        return newItemsRegistry;
    }

    public DashboardSettings setNewItemsRegistry(List<ItemRegistry> newItemsRegistry) {
        this.newItemsRegistry = newItemsRegistry;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DashboardSettings)) return false;

        DashboardSettings that = (DashboardSettings) o;

        return !(getNewItemsRegistry() != null ? !getNewItemsRegistry().equals(that.getNewItemsRegistry()) : that.getNewItemsRegistry() != null);

    }

    @Override
    public int hashCode() {
        return getNewItemsRegistry() != null ? getNewItemsRegistry().hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("DashBoard settings{ New item registry : ");
        for (ItemRegistry item : this.newItemsRegistry) {
            result.append(item.toString()).append(" / ");
        }
        return result.append("}").toString();
    }
}
