package retroindex;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Salah on 06/11/2015.
 */
public class LocalIndex {
    public Map<String, WordInformation> getDocumentsForIsToken() {
        return documentsForIsToken;
    }

    public void setDocumentsForIsToken(Map<String, WordInformation> documentsForIsToken) {
        this.documentsForIsToken = documentsForIsToken;
    }

    private Map<String, WordInformation> documentsForIsToken = new HashMap<String, WordInformation>() {};
}
