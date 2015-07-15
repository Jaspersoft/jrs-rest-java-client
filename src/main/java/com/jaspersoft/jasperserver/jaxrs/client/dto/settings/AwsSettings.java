package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import java.util.List;

/**
 * Created by tetiana.iefimenko on 7/14/2015.
 */
public class AwsSettings {


    private Boolean productTypeIsEc2;
    private Boolean isEc2Instance;
    private  Boolean productTypeIsJrsAmi;
    private List<String> awsRegions;
    private Boolean productTypeIsMpAmi;
    private Boolean suppressEc2CredentialsWarnings;

    public Boolean isProductTypeIsEc2() {
        return productTypeIsEc2;
    }

    public void setProductTypeIsEc2(Boolean productTypeIsEc2) {
        this.productTypeIsEc2 = productTypeIsEc2;
    }

    public Boolean isEc2Instance() {
        return isEc2Instance;
    }

    public void setIsEc2Instance(Boolean isEc2Instance) {
        this.isEc2Instance = isEc2Instance;
    }

    public Boolean isProductTypeIsJrsAmi() {
        return productTypeIsJrsAmi;
    }

    public void setProductTypeIsJrsAmi(Boolean productTypeIsJrsAmi) {
        this.productTypeIsJrsAmi = productTypeIsJrsAmi;
    }

    public List<String> getAwsRegions() {
        return awsRegions;
    }

    public void setAwsRegions(List<String> awsRegions) {
        this.awsRegions = awsRegions;
    }

    public Boolean isProductTypeIsMpAmi() {
        return productTypeIsMpAmi;
    }

    public void setProductTypeIsMpAmi(Boolean productTypeIsMpAmi) {
        this.productTypeIsMpAmi = productTypeIsMpAmi;
    }

    public Boolean isSuppressEc2CredentialsWarnings() {
        return suppressEc2CredentialsWarnings;
    }

    public void setSuppressEc2CredentialsWarnings(Boolean suppressEc2CredentialsWarnings) {
        this.suppressEc2CredentialsWarnings = suppressEc2CredentialsWarnings;
    }
}
