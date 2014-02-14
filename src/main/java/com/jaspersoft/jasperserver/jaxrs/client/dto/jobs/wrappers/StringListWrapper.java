package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers;

import java.util.ArrayList;
import java.util.List;

public class StringListWrapper {

    protected List<String> strings;

    protected StringListWrapper(){}

    protected StringListWrapper(List<String> strings){
        this.strings = new ArrayList<String>(strings.size());
        for (String r : strings){
            this.strings.add(r);
        }
    }

    protected List<String> getStrings() {
        return strings;
    }

    protected void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringListWrapper that = (StringListWrapper) o;

        if (strings != null ? !strings.equals(that.strings) : that.strings != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return strings != null ? strings.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StringListWrapper{" +
                "strings=" + strings +
                '}';
    }
}
