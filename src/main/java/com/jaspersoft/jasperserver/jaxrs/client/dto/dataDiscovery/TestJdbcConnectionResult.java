package com.jaspersoft.jasperserver.jaxrs.client.dto.dataDiscovery;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientJdbcDataSource;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class TestJdbcConnectionResult{
    private ClientJdbcDataSource jdbcDataSource;
    private ErrorDescriptor errorDescriptor;

    public TestJdbcConnectionResult() {
    }

    public TestJdbcConnectionResult(TestJdbcConnectionResult other) {
        this.jdbcDataSource = new ClientJdbcDataSource(other.jdbcDataSource);
        this.errorDescriptor = new ErrorDescriptor(other.errorDescriptor);
    }

    public ClientJdbcDataSource getJdbcDataSource() {
        return jdbcDataSource;
    }

    public TestJdbcConnectionResult setJdbcDataSource(ClientJdbcDataSource jdbcDataSource) {
        this.jdbcDataSource = jdbcDataSource;
        return this;
    }

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public TestJdbcConnectionResult setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestJdbcConnectionResult)) return false;

        TestJdbcConnectionResult that = (TestJdbcConnectionResult) o;

        if (getJdbcDataSource() != null ? !getJdbcDataSource().equals(that.getJdbcDataSource()) : that.getJdbcDataSource() != null)
            return false;
        return !(getErrorDescriptor() != null ? !getErrorDescriptor().equals(that.getErrorDescriptor()) : that.getErrorDescriptor() != null);

    }

    @Override
    public int hashCode() {
        int result = getJdbcDataSource() != null ? getJdbcDataSource().hashCode() : 0;
        result = 31 * result + (getErrorDescriptor() != null ? getErrorDescriptor().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestJdbcConnectionResult{" +
                "jdbcDataSource=" + jdbcDataSource +
                ", errorDescriptor=" + errorDescriptor +
                '}';
    }
}
