/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.schema;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.domain.Schema;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jakarta.ws.rs.core.MediaType;

import static java.util.Arrays.asList;

public class DomainSchemaAdapter extends AbstractAdapter {
    public static final String SERVICE_URI = "resources";
    private final List<String> path = new ArrayList<String>();

    public DomainSchemaAdapter(SessionStorage sessionStorage, String domainURI) {
        super(sessionStorage);
        this.path.add(SERVICE_URI);
        this.path.addAll(asList(domainURI.split("/")));
    }

    public OperationResult<Schema> get() {
        JerseyRequest<Schema> request = buildRequest();
        request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(),
                "application/repository.domainSchema+{mime}"));
        return request.get();
    }


    public OperationResult<Schema> update(Schema schema) {
        JerseyRequest<Schema> request = buildRequest();
        request.setContentType(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(),
                "application/repository.domainSchema+{mime}"));
        return request.put(schema);
    }


    public OperationResult<ClientFile> upload(String path, String name, String description) {
        File file = new File(path);
        return uploadFile(file, ClientFile.FileType.xml, name, description);
    }


    protected JerseyRequest<Schema> buildRequest() {
        JerseyRequest<Schema> jerseyRequest = JerseyRequest.buildRequest(
                sessionStorage,
                Schema.class,
                path.toArray(new String[path.size()]),
                new DefaultErrorHandler()
        );
        return jerseyRequest;
    }

    protected OperationResult<ClientFile> uploadFile(File fileContent,
                                                  ClientFile.FileType fileType,
                                                  String label,
                                                  String description) {
        FormDataMultiPart form = prepareUploadForm(fileContent, fileType, label, description);
        JerseyRequest<ClientFile> request = prepareUploadFileRequest();
        return request.post(form);
    }

    protected FormDataMultiPart prepareUploadForm(File fileContent,
                                                ClientFile.FileType fileType,
                                                String label,
                                                String description) {
        FormDataMultiPart form = new FormDataMultiPart();
        form
                .field("data", fileContent, MediaType.WILDCARD_TYPE)
                .field("label", label)
                .field("description", description)
                .field("type", fileType.name());
        return form;
    }


    protected JerseyRequest<ClientFile> prepareUploadFileRequest() {
        JerseyRequest<ClientFile> request = JerseyRequest.buildRequest(sessionStorage,
                ClientFile.class,
                path.toArray(new String[path.size()]));
        request.setContentType(MediaType.MULTIPART_FORM_DATA);
        return request;
    }

}
