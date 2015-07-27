package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 *  @author Tetiana Iefimenko
 */
public class DecimalFormatSymbolsSettings {
    private String zeroDigit;
    private  String groupingSeparator;
    private  String decimalSeparator;
    private  String perMill;
    private  String percent;
    private  String digit;
    private  String patternSeparator;
    private  String infinity;
    private  String minusSign;
    private  String currencySymbol;
    private  String currency;
    private  String exponentSeparator;
    private  String internationalCurrencySymbol;
    private  String monetaryDecimalSeparator;
    private  String naN;

    public DecimalFormatSymbolsSettings() {
    }

    public DecimalFormatSymbolsSettings(DecimalFormatSymbolsSettings other) {
        this.zeroDigit = other.zeroDigit;
        this.groupingSeparator = other.groupingSeparator;
        this.decimalSeparator = other.decimalSeparator;
        this.perMill = other.perMill;
        this.percent = other.percent;
        this.digit = other.digit;
        this.patternSeparator = other.patternSeparator;
        this.infinity = other.infinity;
        this.minusSign = other.minusSign;
        this.currencySymbol = other.currencySymbol;
        this.currency = other.currency;
        this.exponentSeparator = other.exponentSeparator;
        this.internationalCurrencySymbol = other.internationalCurrencySymbol;
        this.monetaryDecimalSeparator = other.monetaryDecimalSeparator;
        this.naN = other.naN;
    }

    public String getZeroDigit() {
        return zeroDigit;
    }

    public DecimalFormatSymbolsSettings setZeroDigit(String zeroDigit) {
        this.zeroDigit = zeroDigit;
        return this;
    }

    public String getGroupingSeparator() {
        return groupingSeparator;
    }

    public DecimalFormatSymbolsSettings setGroupingSeparator(String groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
        return this;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public DecimalFormatSymbolsSettings setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
        return this;
    }

    public String getPerMill() {
        return perMill;
    }

    public DecimalFormatSymbolsSettings setPerMill(String perMill) {
        this.perMill = perMill;
        return this;
    }

    public String getPercent() {
        return percent;
    }

    public DecimalFormatSymbolsSettings setPercent(String percent) {
        this.percent = percent;
        return this;
    }

    public String getDigit() {
        return digit;
    }

    public DecimalFormatSymbolsSettings setDigit(String digit) {
        this.digit = digit;
        return this;
    }

    public String getPatternSeparator() {
        return patternSeparator;
    }

    public DecimalFormatSymbolsSettings setPatternSeparator(String patternSeparator) {
        this.patternSeparator = patternSeparator;
        return this;
    }

    public String getInfinity() {
        return infinity;
    }

    public DecimalFormatSymbolsSettings setInfinity(String infinity) {
        this.infinity = infinity;
        return this;
    }

    public String getMinusSign() {
        return minusSign;
    }

    public DecimalFormatSymbolsSettings setMinusSign(String minusSign) {
        this.minusSign = minusSign;
        return this;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public DecimalFormatSymbolsSettings setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public DecimalFormatSymbolsSettings setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getExponentSeparator() {
        return exponentSeparator;
    }

    public DecimalFormatSymbolsSettings setExponentSeparator(String exponentSeparator) {
        this.exponentSeparator = exponentSeparator;
        return this;
    }

    public String getInternationalCurrencySymbol() {
        return internationalCurrencySymbol;
    }

    public DecimalFormatSymbolsSettings setInternationalCurrencySymbol(String internationalCurrencySymbol) {
        this.internationalCurrencySymbol = internationalCurrencySymbol;
        return this;
    }

    public String getMonetaryDecimalSeparator() {
        return monetaryDecimalSeparator;
    }

    public DecimalFormatSymbolsSettings setMonetaryDecimalSeparator(String monetaryDecimalSeparator) {
        this.monetaryDecimalSeparator = monetaryDecimalSeparator;
        return this;
    }

    public String getNaN() {
        return naN;
    }

    public DecimalFormatSymbolsSettings setNaN(String naN) {
        this.naN = naN;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecimalFormatSymbolsSettings)) return false;

        DecimalFormatSymbolsSettings that = (DecimalFormatSymbolsSettings) o;

        if (getZeroDigit() != null ? !getZeroDigit().equals(that.getZeroDigit()) : that.getZeroDigit() != null)
            return false;
        if (getGroupingSeparator() != null ? !getGroupingSeparator().equals(that.getGroupingSeparator()) : that.getGroupingSeparator() != null)
            return false;
        if (getDecimalSeparator() != null ? !getDecimalSeparator().equals(that.getDecimalSeparator()) : that.getDecimalSeparator() != null)
            return false;
        if (getPerMill() != null ? !getPerMill().equals(that.getPerMill()) : that.getPerMill() != null) return false;
        if (getPercent() != null ? !getPercent().equals(that.getPercent()) : that.getPercent() != null) return false;
        if (getDigit() != null ? !getDigit().equals(that.getDigit()) : that.getDigit() != null) return false;
        if (getPatternSeparator() != null ? !getPatternSeparator().equals(that.getPatternSeparator()) : that.getPatternSeparator() != null)
            return false;
        if (getInfinity() != null ? !getInfinity().equals(that.getInfinity()) : that.getInfinity() != null)
            return false;
        if (getMinusSign() != null ? !getMinusSign().equals(that.getMinusSign()) : that.getMinusSign() != null)
            return false;
        if (getCurrencySymbol() != null ? !getCurrencySymbol().equals(that.getCurrencySymbol()) : that.getCurrencySymbol() != null)
            return false;
        if (getCurrency() != null ? !getCurrency().equals(that.getCurrency()) : that.getCurrency() != null)
            return false;
        if (getExponentSeparator() != null ? !getExponentSeparator().equals(that.getExponentSeparator()) : that.getExponentSeparator() != null)
            return false;
        if (getInternationalCurrencySymbol() != null ? !getInternationalCurrencySymbol().equals(that.getInternationalCurrencySymbol()) : that.getInternationalCurrencySymbol() != null)
            return false;
        if (getMonetaryDecimalSeparator() != null ? !getMonetaryDecimalSeparator().equals(that.getMonetaryDecimalSeparator()) : that.getMonetaryDecimalSeparator() != null)
            return false;
        return !(getNaN() != null ? !getNaN().equals(that.getNaN()) : that.getNaN() != null);

    }

    @Override
    public int hashCode() {
        int result = getZeroDigit() != null ? getZeroDigit().hashCode() : 0;
        result = 31 * result + (getGroupingSeparator() != null ? getGroupingSeparator().hashCode() : 0);
        result = 31 * result + (getDecimalSeparator() != null ? getDecimalSeparator().hashCode() : 0);
        result = 31 * result + (getPerMill() != null ? getPerMill().hashCode() : 0);
        result = 31 * result + (getPercent() != null ? getPercent().hashCode() : 0);
        result = 31 * result + (getDigit() != null ? getDigit().hashCode() : 0);
        result = 31 * result + (getPatternSeparator() != null ? getPatternSeparator().hashCode() : 0);
        result = 31 * result + (getInfinity() != null ? getInfinity().hashCode() : 0);
        result = 31 * result + (getMinusSign() != null ? getMinusSign().hashCode() : 0);
        result = 31 * result + (getCurrencySymbol() != null ? getCurrencySymbol().hashCode() : 0);
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        result = 31 * result + (getExponentSeparator() != null ? getExponentSeparator().hashCode() : 0);
        result = 31 * result + (getInternationalCurrencySymbol() != null ? getInternationalCurrencySymbol().hashCode() : 0);
        result = 31 * result + (getMonetaryDecimalSeparator() != null ? getMonetaryDecimalSeparator().hashCode() : 0);
        result = 31 * result + (getNaN() != null ? getNaN().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DecimalFormatSymbolsSettings{" +
                "zeroDigit='" + zeroDigit + '\'' +
                ", groupingSeparator='" + groupingSeparator + '\'' +
                ", decimalSeparator='" + decimalSeparator + '\'' +
                ", perMill='" + perMill + '\'' +
                ", percent='" + percent + '\'' +
                ", digit='" + digit + '\'' +
                ", patternSeparator='" + patternSeparator + '\'' +
                ", infinity='" + infinity + '\'' +
                ", minusSign='" + minusSign + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                ", currency='" + currency + '\'' +
                ", exponentSeparator='" + exponentSeparator + '\'' +
                ", internationalCurrencySymbol='" + internationalCurrencySymbol + '\'' +
                ", monetaryDecimalSeparator='" + monetaryDecimalSeparator + '\'' +
                ", naN='" + naN + '\'' +
                '}';
    }
}
