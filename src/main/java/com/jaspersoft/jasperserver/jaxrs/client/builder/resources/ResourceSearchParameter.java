package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

public enum ResourceSearchParameter {

    /**
     * Search for resources having the specified text in the name or description.
     * Note that the search string does not match in the ID of resources.
     *
     * Type/Value: String
     */
    Q("q"),

    /**
     * The path of the base folder for the search.
     *
     * Type/Value: String
     */
    FOLDER_URI("folderUri"),

    /**
     * Indicates whether search should include all sub-folders recursively. When
     * omitted, the default behavior is recursive (true).
     *
     * Type/Value: true|false
     */
    RECURSIVE("recursive"),

    /**
     * Match only resources of the given type. Valid types are listed inV2
     * Resource Descriptor Types, for example: dataType, jdbcDataSource,
     * reportUnit, or file. Multiple type parameters are allowed. Wrong values are
     * ignored.
     *
     * Type/Value: String
     */
    TYPE("type"),

    /**
     * Filters the results by access events: viewed (by current user) or modified (by
     * current user). By default, no access event filter is applied.
     *
     * Type/Value: viewed|modified
     */
    ACCES_TYPE("accessType"),

    /**
     * When set to true, results include nested local resources (in _files) as if they
     * were in the repository. For more information, see Local Resources for more
     * information. By default, hidden items are not shown (false).
     *
     * Type/Value: true|false
     */
    SHOW_HIDDEN_ITEMS("showHiddenItems"),

    /**
     * One of the following strings representing a field in the results to sort by: uri,
     * label, description, type, creationDate, updateDate, accessTime, or popularity
     * (based on access events). By default, results are sorted alphabetically by
     * label.
     *
     * Type/Value: (optional) String
     */
    SORT_BY("sortBy"),

    /**
     * Used for pagination to specify the maximum number of resources to return in
     * each response. This is equivalent to the number of results per page. The
     * default limit is 100.
     *
     * Type/Value: integer
     */
    LIMIT("limit"),

    /**
     * Used for pagination to request an offset in the set of results. This is
     * equivalent to a specific page number. The default offset is 1 (first page).
     *
     * Type/Value: integer
     */
    OFFSET("offset"),

    /**
     * When true, the Total-Count header is set in every paginated response,
     * which impacts performance. When false, the default, the header is set in the
     * first page only.
     *
     * Type/Value: true|false
     */
    FORCE_TOTAL_COUNT("forceTotalCount"),

    /**
     * Specifies a resource to delete. Repeat this paramter to delete multiple
     * resources.
     *
     * Type/Value: String
     */
    RESOURCE_URI("resourceURI")

    ;

    private String name;

    private ResourceSearchParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
