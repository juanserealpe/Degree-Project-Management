package App.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageQueryRepository extends Message{
    /** The data extracted from the query. **/
    protected List<Map<String, Object>> data;

    /**
     * Constructs a successful MessageQueryRepository with data.
     *
     * @param pMessage Description of the query result
     * @param pData List of rows, each represented as a map of column-value pairs
     **/
    public MessageQueryRepository(String pMessage, List<Map<String, Object>> pData) {
        super(true, pMessage);
        if(pData.isEmpty()) data = new ArrayList<>();
        else this.data = pData;
    }

    /**
     * Constructs a successful MessageQueryRepository with data.
     *
     * @param pMessage Description of the result
     */
    public MessageQueryRepository(String pMessage) {
        super(true, pMessage);
        this.data = new ArrayList<>();
    }

    /** Returns the extracted data as a list of maps. **/
    public List<Map<String, Object>> getData() {
        return data;
    }

    /** Returns the extracted data as a list of maps. **/
    public int GetNumberRowsExtracted(){
        if(data == null) return 0;
        return data.size();
    }

}
