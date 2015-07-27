package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 *  @author Tetiana Iefimenko.
 */

public class UserTimeZone {
    private String code;
    private String description;

    public UserTimeZone() {
    }

    public UserTimeZone(UserTimeZone other) {
        this.code = other.code;
        this.description = other.description;
    }

    public String getCode() {
        return code;
    }

    public UserTimeZone setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UserTimeZone setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTimeZone)) return false;

        UserTimeZone that = (UserTimeZone) o;

        if (getCode() != null ? !getCode().equals(that.getCode()) : that.getCode() != null) return false;
        return !(getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getCode() != null ? getCode().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserTimeZone{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
