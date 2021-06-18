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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.dto.importexport.ExportTask;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

public class ExportService extends AbstractAdapter {

    public ExportService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    /***
     * @deprecated use {@link #newTask(ExportTask)}
     */
    @Deprecated
    public ExportTaskAdapter newTask() {
        return new ExportTaskAdapter(sessionStorage);
    }

    public ExportRequestAdapter newTask(ExportTask exportTask) {
        return new ExportRequestAdapter(sessionStorage, exportTask);
    }

    public ExportRequestAdapter task(String taskId) {
        if ("".equals(taskId) || "/".equals(taskId)) {
            throw new IllegalArgumentException("'taskId' mustn't be an empty string");
        }
        return new ExportRequestAdapter(sessionStorage, taskId);
    }
}
