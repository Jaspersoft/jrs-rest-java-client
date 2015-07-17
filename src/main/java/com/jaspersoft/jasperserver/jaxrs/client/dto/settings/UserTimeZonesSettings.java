package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.LinkedList;

import java.util.List;
import java.util.Map;

/**
 * Created by tetiana.iefimenko on 7/16/2015.
 */

@XmlRootElement



public class UserTimeZonesSettings {

    private List<UserTimeZone> userTimeZones;

    public UserTimeZonesSettings() {
    }

    public UserTimeZonesSettings(UserTimeZonesSettings other) {
        this.userTimeZones = new LinkedList<UserTimeZone>(other.getUserTimeZones());
    }

    public List<UserTimeZone> getUserTimeZones() {
        return userTimeZones;
    }

    public void setUserTimeZones(List<UserTimeZone> userTimeZones) {
        this.userTimeZones = userTimeZones;
    }
}
