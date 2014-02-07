package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class OutputFormatsListWrapper extends StringListWrapper {

    public OutputFormatsListWrapper() {
        super();
    }

    public OutputFormatsListWrapper(List<String> strings) {
        super(strings);
    }

    @Override
    @XmlElement(name = "outputFormat")
    protected List<String> getStrings() {
        return super.getStrings();
    }

    @Override
    protected void setStrings(List<String> strings) {
        super.setStrings(strings);
    }

    public List<String> getOutputFormat() {
        return this.getStrings();
    }

    public void setOutputFormats(List<String> outputFormats) {
        this.setStrings(strings);
    }

    @Override
    public String toString() {
        return "OutputFormatsListWrapper{" +
                "outputFormat=" + strings +
                '}';
    }
}
