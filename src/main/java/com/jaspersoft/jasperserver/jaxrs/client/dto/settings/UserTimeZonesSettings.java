package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement
public class UserTimeZonesSettings {

    private Collection<UserTimeZone> userTimeZones;

    public Collection<UserTimeZone> getUserTimeZones() {
        return userTimeZones;
    }

    public void setUserTimeZones(Collection<UserTimeZone> userTimeZones) {
        this.userTimeZones = userTimeZones;
    }

    class UserTimeZone {

        private String code;
        private String description;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserTimeZone that = (UserTimeZone) o;

            if (code != null ? !code.equals(that.code) : that.code != null) return false;
            return !(description != null ? !description.equals(that.description) : that.description != null);

        }

        @Override
        public int hashCode() {
            int result = code != null ? code.hashCode() : 0;
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTimeZonesSettings that = (UserTimeZonesSettings) o;

        return !(userTimeZones != null ? !userTimeZones.equals(that.userTimeZones) : that.userTimeZones != null);

    }

    @Override
    public int hashCode() {
        return userTimeZones != null ? userTimeZones.hashCode() : 0;
    }
}
