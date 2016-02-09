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
import com.jaspersoft.jasperserver.dto.resources.domain.ClientSchema;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.sun.jersey.multipart.FormDataMultiPart;
import java.io.File;
import javax.ws.rs.core.MediaType;

public class DomainSchemaAdapter extends AbstractAdapter {
    private final String domainSchemaUri;

    public DomainSchemaAdapter(SessionStorage sessionStorage, String domainURI) {
        super(sessionStorage);
        this.domainSchemaUri = domainURI;
    }

    public OperationResult<ClientSchema> get() {
        JerseyRequest<ClientSchema> request = buildRequest();
        request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/repository.domainSchema+{mime}"));
        return request.get();
    }


    public OperationResult<ClientSchema> update(ClientSchema schema) {
        JerseyRequest<ClientSchema> request = buildRequest();
        request.setContentType(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/repository.domainSchema+{mime}"));
        return request.put(schema);
    }


    public OperationResult<ClientFile> upload(String path, String name, String description) {
        File file = new File(path);
        return uploadFile(file, ClientFile.FileType.xml, name, description);
    }


    protected JerseyRequest<ClientSchema> buildRequest() {
        JerseyRequest<ClientSchema> jerseyRequest = JerseyRequest.buildRequest(
                sessionStorage,
                ClientSchema.class,
                new String[]{"/resources/", domainSchemaUri},
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
        JerseyRequest<ClientFile> request = JerseyRequest.buildRequest(sessionStorage, ClientFile.class, new String[]{"/resources", domainSchemaUri});
        request.setContentType(MediaType.MULTIPART_FORM_DATA);
        return request;
    }

}
