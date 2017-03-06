package co.com.psl.evaluacionser.persistence;

import io.searchbox.action.AbstractMultiTypeActionBuilder;
import io.searchbox.action.GenericResultAbstractAction;

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
