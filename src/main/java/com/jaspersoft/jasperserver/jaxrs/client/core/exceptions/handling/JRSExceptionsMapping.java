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

package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.*;

import java.util.HashMap;
import java.util.Map;

public final class JRSExceptionsMapping {

    public static final Map<String, Class<? extends JSClientWebException>> ERROR_CODE_TO_TYPE_MAP =
            new HashMap<String, Class<? extends JSClientWebException>>() {{
                put(AccessDeniedException.ERROR_CODE_ACCESS_DENIED, AccessDeniedException.class);
                put(FolderAlreadyExistsException.FOLDER_ALREADY_EXISTS, FolderAlreadyExistsException.class);
                put(FolderNotFoundException.ERROR_CODE_FOLDER_NOT_FOUND, FolderNotFoundException.class);
                put(IllegalParameterValueException.ERROR_CODE, IllegalParameterValueException.class);
                put(MandatoryParameterNotFoundException.MANDATORY_PARAMETER_ERROR, MandatoryParameterNotFoundException.class);
                put(ModificationNotAllowedException.ERROR_CODE_MODIFICATION_NOT_ALLOWED, ModificationNotAllowedException.class);
                put(NoResultException.ERROR_CODE_NO_RESULT, NoResultException.class);
                put(NoSuchImportTaskException.ERROR_CODE_NO_SUCH_IMPORT_PROCESS, NoSuchImportTaskException.class);
                put(NoSuchTaskException.ERROR_CODE_NO_SUCH_EXPORT_PROCESS, NoSuchTaskException.class);
                put(NotAFileException.ERROR_NOT_A_FILE, NotAFileException.class);
                put(NotReadyResultException.ERROR_CODE_NOT_READY, NotReadyResultException.class);
                put(PatchException.PATCH_FAILED, PatchException.class);
                put(ResourceAlreadyExistsException.RESOURCE_ALREADY_EXISTS, ResourceAlreadyExistsException.class);
                put(ResourceInUseException.ERROR_CODE, ResourceInUseException.class);
                put(VersionNotMatchException.ERROR_VERSION_NOT_MATCH, VersionNotMatchException.class);
                put(WeakPasswordException.ERROR_CODE_WEAK_PASSWORD, WeakPasswordException.class);
                put(ParamException.ERROR_CODE_PARAMETER_VALUE_ERROR, ParamException.class);
                put(ReportInvalidPageRangeException.INVALID_REPORT_RANGE, ReportInvalidPageRangeException.class);
                put(ReportStartPageGreaterThenEndPageException.REPORT_START_PAGE_GREATER_THEN_END_PAGE, ReportStartPageGreaterThenEndPageException.class);
                put(ReportExportException.ERROR_EXPORTING_REPORT_UNIT, ReportExportException.class);
                put(ReportExportException.ERROR_EXPORT_PARAMETERS_MISSED, ReportExportException.class);
                put(ResourceNotFoundException.ERROR_CODE_RESOURCE_NOT_FOUND, ResourceNotFoundException.class);
                put(UnexpectedErrorException.ERROR_CODE_UNEXPECTED_ERROR, UnexpectedErrorException.class);
                put(ExportFailedException.ERROR_CODE_EXPORT_FAILED, ExportFailedException.class);
            }};

    private JRSExceptionsMapping(){}

}
