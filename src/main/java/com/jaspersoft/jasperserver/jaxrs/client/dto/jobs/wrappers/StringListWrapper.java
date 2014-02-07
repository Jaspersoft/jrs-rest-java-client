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

}
