package retroindex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salah on 06/11/2015.
 */
public class SearchResult {
    private List<Document> Documents = new ArrayList<Document>();
    private String query;
    private Double time;

    public List<Document> getDocuments() {
        return Documents;
    }

    public void setDocuments(List<Document> documents) {
        Documents = documents;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
