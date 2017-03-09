package co.com.psl.evaluacionser.persistence;

import io.searchbox.action.AbstractMultiTypeActionBuilder;
import io.searchbox.action.GenericResultAbstractAction;

/**
 * this is a class overwritten from JEST, the original didn't have support for ES 5, the changes where at the params:
 * buildURI and getRestMethodName, delete by query was a method not supported in ES 2, that is the official
 * version from JEST hence the need to overwrite it
 */
public class DeleteByQuery extends GenericResultAbstractAction {

    protected DeleteByQuery(DeleteByQuery.Builder builder) {
        super(builder);

        this.payload = builder.query;
        setURI(buildURI());
    }

    @Override
    protected String buildURI() {
        return super.buildURI() + "/_delete_by_query";
    }

    @Override
    public String getPathToResult() {
        return "ok";
    }

    @Override
    public String getRestMethodName() {
        return "POST";
    }

    public static class Builder extends AbstractMultiTypeActionBuilder<DeleteByQuery, Builder> {

        private String query;

        public Builder(String query) {
            this.query = query;
        }

        @Override
        public DeleteByQuery build() {
            return new DeleteByQuery(this);
        }
    }

}
