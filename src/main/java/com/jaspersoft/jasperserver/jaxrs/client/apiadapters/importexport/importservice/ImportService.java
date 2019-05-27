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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import java.io.File;

public class ImportService extends AbstractAdapter {

    public ImportService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    @Deprecated
    public ImportTaskRequestAdapter newTask() {
        return new ImportTaskRequestAdapter(sessionStorage);
    }


    public ImportRequestAdapter newImport(File file) {
        return new ImportRequestAdapter(sessionStorage, file, false);
    }

    public ImportRequestAdapter newImport(String pathToFile) {
        return new ImportRequestAdapter(sessionStorage, new File(pathToFile), false);
    }

    public ImportRequestAdapter newMultiPartImport(File file) {
        return new ImportRequestAdapter(sessionStorage, file, true);
    }

    public ImportRequestAdapter task(String taskId) {
        if ("".equals(taskId) || "/".equals(taskId)) {
            throw new IllegalArgumentException("'taskId' mustn't be an empty string");
        }
        return new ImportRequestAdapter(sessionStorage, taskId);
    }
}
