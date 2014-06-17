package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.domain.DomainMetaData;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.QueryField;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.QueryResult;

import java.util.Arrays;

public class AppLauncher {
    public static void main(String[] args) {

        final String PATH = "http://localhost:8085/jasperserver-pro/";
        RestClientConfiguration configuration = new RestClientConfiguration(PATH);
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        Session session = client.authenticate("superuser", "superuser");

        OperationResult<DomainMetaData> result = session.domainService()
                .domainMetadataAdapter("/Foodmart_Sales")
                .getDomainMetaData();

        DomainMetaData domainMetaData = result.getEntity();
        System.out.println(domainMetaData);

        Query testQuery;

        configuration = new RestClientConfiguration(PATH);
        client = new JasperserverRestClient(configuration);
        session = client.authenticate("superuser", "superuser");
        testQuery = new Query();

        QueryField fieldOne = new QueryField();
        fieldOne.setId("accounts.name");
        QueryField fieldTwo = new QueryField();
        fieldTwo.setId("accounts.billing_address_postalcode");
        QueryField fieldThree = new QueryField();
        fieldThree.setId("opportunities.amount");

        testQuery.setQueryFields(Arrays.asList(fieldOne, fieldTwo, fieldThree));

        String resourceURI = "/organizations/organization_1/Domains/Simple_Domain";

        QueryResult newQueryResult = session.queryExecutorService()
                .query(testQuery, resourceURI)
                .queryResult("application/xml", "application/json")
                .getEntity();

        System.out.println(newQueryResult);
    }
}
