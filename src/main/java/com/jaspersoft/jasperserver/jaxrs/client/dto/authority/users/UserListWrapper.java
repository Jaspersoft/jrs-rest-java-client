package com.jaspersoft.jasperserver.jaxrs.client.dto.authority.users;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by tetiana.iefimenko on 7/17/2015.
 */
@XmlRootElement(name = "user")
public class UserListWrapper {

    @XmlElementWrapper
    private List<User> user;

    public List<User> getUsers() {
        return user;
    }

    public void setUsers(List<User> user) {
        this.user = user;
    }
}
