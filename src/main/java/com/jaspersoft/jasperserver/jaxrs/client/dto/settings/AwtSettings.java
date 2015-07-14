package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import java.util.List;

/**
 * Created by tetiana.iefimenko on 7/14/2015.
 */
public class AwtSettings {

    private boolean productTypeIsEc2;
    private boolean isEc2Instance;
    private  boolean productTypeIsJrsAmi;
    private List<String> awsRegions;
    private boolean productTypeIsMpAmi;
    private boolean suppressEc2CredentialsWarnings;

    public boolean isProductTypeIsEc2() {
        return productTypeIsEc2;
    }

    public void setProductTypeIsEc2(boolean productTypeIsEc2) {
        this.productTypeIsEc2 = productTypeIsEc2;
    }

    public boolean isEc2Instance() {
        return isEc2Instance;
    }

    public void setIsEc2Instance(boolean isEc2Instance) {
        this.isEc2Instance = isEc2Instance;
    }

    public boolean isProductTypeIsJrsAmi() {
        return productTypeIsJrsAmi;
    }

    public void setProductTypeIsJrsAmi(boolean productTypeIsJrsAmi) {
        this.productTypeIsJrsAmi = productTypeIsJrsAmi;
    }

    public List<String> getAwsRegions() {
        return awsRegions;
    }

    public void setAwsRegions(List<String> awsRegions) {
        this.awsRegions = awsRegions;
    }

    public boolean isProductTypeIsMpAmi() {
        return productTypeIsMpAmi;
    }

    public void setProductTypeIsMpAmi(boolean productTypeIsMpAmi) {
        this.productTypeIsMpAmi = productTypeIsMpAmi;
    }

    public boolean isSuppressEc2CredentialsWarnings() {
        return suppressEc2CredentialsWarnings;
    }

    public void setSuppressEc2CredentialsWarnings(boolean suppressEc2CredentialsWarnings) {
        this.suppressEc2CredentialsWarnings = suppressEc2CredentialsWarnings;
    }
}
