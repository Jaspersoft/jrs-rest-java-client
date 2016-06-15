package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tetiana Iefimenko
 *
 */
public class AwsSettings {


    private Boolean productTypeIsEc2;
    private Boolean isEc2Instance;
    private Boolean productTypeIsJrsAmi;
    private List<String> awsRegions;
    private Boolean productTypeIsMpAmi;
    private Boolean suppressEc2CredentialsWarnings;

    public AwsSettings() {
    }

    public AwsSettings(AwsSettings other) {
        this.productTypeIsEc2 = other.productTypeIsEc2;
        this.isEc2Instance = other.isEc2Instance;
        this.productTypeIsJrsAmi = other.productTypeIsJrsAmi;
        if (other.awsRegions != null) {
            this.awsRegions = new LinkedList<String>();
            for (String awsRegion : other.awsRegions) {
                this.awsRegions.add(awsRegion);
            }
        }
        this.productTypeIsMpAmi = other.productTypeIsMpAmi;
        this.suppressEc2CredentialsWarnings = other.suppressEc2CredentialsWarnings;
    }

    public Boolean isProductTypeIsEc2() {
        return productTypeIsEc2;
    }

    public AwsSettings setProductTypeIsEc2(Boolean productTypeIsEc2) {
        this.productTypeIsEc2 = productTypeIsEc2;
        return this;
    }

    public Boolean isEc2Instance() {
        return isEc2Instance;
    }

    public AwsSettings setIsEc2Instance(Boolean isEc2Instance) {
        this.isEc2Instance = isEc2Instance;
        return this;
    }

    public Boolean isProductTypeIsJrsAmi() {
        return productTypeIsJrsAmi;
    }

    public AwsSettings setProductTypeIsJrsAmi(Boolean productTypeIsJrsAmi) {
        this.productTypeIsJrsAmi = productTypeIsJrsAmi;
        return this;
    }

    public List<String> getAwsRegions() {
        return awsRegions;
    }

    public AwsSettings setAwsRegions(List<String> awsRegions) {
        this.awsRegions = awsRegions;
        return this;
    }

    public Boolean isProductTypeIsMpAmi() {
        return productTypeIsMpAmi;
    }

    public AwsSettings setProductTypeIsMpAmi(Boolean productTypeIsMpAmi) {
        this.productTypeIsMpAmi = productTypeIsMpAmi;
        return this;
    }

    public Boolean isSuppressEc2CredentialsWarnings() {
        return suppressEc2CredentialsWarnings;
    }

    public AwsSettings setSuppressEc2CredentialsWarnings(Boolean suppressEc2CredentialsWarnings) {
        this.suppressEc2CredentialsWarnings = suppressEc2CredentialsWarnings;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AwsSettings)) return false;

        AwsSettings that = (AwsSettings) o;

        if (productTypeIsEc2 != null ? !productTypeIsEc2.equals(that.productTypeIsEc2) : that.productTypeIsEc2 != null)
            return false;
        if (isEc2Instance != null ? !isEc2Instance.equals(that.isEc2Instance) : that.isEc2Instance != null)
            return false;
        if (productTypeIsJrsAmi != null ? !productTypeIsJrsAmi.equals(that.productTypeIsJrsAmi) : that.productTypeIsJrsAmi != null)
            return false;
        if (getAwsRegions() != null ? !getAwsRegions().equals(that.getAwsRegions()) : that.getAwsRegions() != null)
            return false;
        if (productTypeIsMpAmi != null ? !productTypeIsMpAmi.equals(that.productTypeIsMpAmi) : that.productTypeIsMpAmi != null)
            return false;
        return !(suppressEc2CredentialsWarnings != null ? !suppressEc2CredentialsWarnings.equals(that.suppressEc2CredentialsWarnings) : that.suppressEc2CredentialsWarnings != null);

    }

    @Override
    public int hashCode() {
        int result = productTypeIsEc2 != null ? productTypeIsEc2.hashCode() : 0;
        result = 31 * result + (isEc2Instance != null ? isEc2Instance.hashCode() : 0);
        result = 31 * result + (productTypeIsJrsAmi != null ? productTypeIsJrsAmi.hashCode() : 0);
        result = 31 * result + (getAwsRegions() != null ? getAwsRegions().hashCode() : 0);
        result = 31 * result + (productTypeIsMpAmi != null ? productTypeIsMpAmi.hashCode() : 0);
        result = 31 * result + (suppressEc2CredentialsWarnings != null ? suppressEc2CredentialsWarnings.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AwsSettings{" +
                "productTypeIsEc2=" + productTypeIsEc2 +
                ", isEc2Instance=" + isEc2Instance +
                ", productTypeIsJrsAmi=" + productTypeIsJrsAmi +
                ", awsRegions=" + awsRegions +
                ", productTypeIsMpAmi=" + productTypeIsMpAmi +
                ", suppressEc2CredentialsWarnings=" + suppressEc2CredentialsWarnings +
                '}';
    }
}
