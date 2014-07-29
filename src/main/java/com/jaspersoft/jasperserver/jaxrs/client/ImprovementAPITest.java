package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;

import java.util.ArrayList;

/**
 * @author Alexander Krasnyanskiy
 */
public class ImprovementAPITest {
    public static void main(String[] args) {

        RestClientConfiguration config = new RestClientConfiguration("http://23.23.21.26/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(config);

        final ClientUserAttribute stateAttribute = new ClientUserAttribute()
                .setName("State")
                .setValue("NY");

        final ClientUserAttribute ageAttribute = new ClientUserAttribute()
                .setName("Age")
                .setValue("27");

        final ClientUserAttribute accountAttribute = new ClientUserAttribute()
                .setName("Account")
                .setValue("2587652395792469214724");

        final ClientUserAttribute statusAttribute = new ClientUserAttribute()
                .setName("Status")
                .setValue("false");

        final ClientUserAttribute weightAttribute = new ClientUserAttribute()
                .setName("Weight")
                .setValue("190");

        UserAttributesListWrapper list = new UserAttributesListWrapper();
        list.setProfileAttributes(new ArrayList<ClientUserAttribute>() {{
            add(ageAttribute);
            add(accountAttribute);
        }});

        /*
        client.authenticate("superuser", "superuser")
                .usersService()
                .organization("MyCoolOrg")
                .username("Bob")
                .attribute("State")
                .updateOrCreate(stateAttribute);

        client.authenticate("superuser", "superuser")
                .usersService()
                .organization("MyCoolOrg")
                .username("Bob")
                .attributes()
                .updateOrCreate(list);
        */



        // {Step 1}
//        client.authenticate("superuser", "superuser")
//                .usersService()
//                .organization("MyCoolOrg")
//                .user("Bob")
//                .attribute()
//                .updateOrCreate(stateAttribute);
//
//        UserAttributesListWrapper newList = new UserAttributesListWrapper();
//        newList.setProfileAttributes(new ArrayList<ClientUserAttribute>(){
//            {add(ageAttribute);}
//            {add(weightAttribute);}
//        });
//
//        client.authenticate("superuser", "superuser")
//                .usersService()
//                .organization("MyCoolOrg")
//                .user("Bob")
//                .multipleAttributes()
//                .updateOrCreate(newList);

        // {Step 2}
//        stateAttribute.setValue("CA");
        final ClientUserAttribute sexAttribute = new ClientUserAttribute()
                .setName("Sex")
                .setValue("Female");

//        final UserAttributesListWrapper newList2 = new UserAttributesListWrapper();
//        newList2.setProfileAttributes(new ArrayList<ClientUserAttribute>(){
//            {add(sexAttribute);}
//            {add(stateAttribute);}
//        });
//
//        client.authenticate("superuser", "superuser")
//                .usersService()
//                .organization("MyCoolOrg")
//                .user("Bob")
//                .multipleAttributes()
//                .updateOrCreate(newList2);

        // {Step 3}
//        client.authenticate("superuser", "superuser")
//                .usersService()
//                .organization("MyCoolOrg")
//                .user("Bob")
//                .attribute()
//                .delete("State");
//
//        client.authenticate("superuser", "superuser")
//                .usersService()
//                .organization("MyCoolOrg")
//                .user("Bob")
//                .attribute()
//                .updateOrCreate(sexAttribute);

        // {Step 4}
        final ClientUserAttribute roleAttribute = new ClientUserAttribute()
                .setName("Role")
                .setValue("Admin");

        final ClientUserAttribute cardAttribure = new ClientUserAttribute()
                .setName("CreditCard")
                .setValue("4527475209487934");

        UserAttributesListWrapper additionalAttributes = new UserAttributesListWrapper();
        additionalAttributes.setProfileAttributes(new ArrayList<ClientUserAttribute>() {{
            add(roleAttribute);
            add(cardAttribure);
        }});

        client.authenticate("superuser", "superuser")
                .usersService()
                .organization("MyCoolOrg")
                .user("Bob")
                .multipleAttributes()
                .updateOrCreate(additionalAttributes);
    }
}