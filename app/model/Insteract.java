
package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Insteract {
	@JsonProperty("_id")
    private String id;
    private Data data;
    private String recordRef;
    private String companyId;
    private String created;
    private String modified;
    private Boolean active;
    @JsonProperty("_id")
    public String getId() {
        return id;
    }
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
    public String getRecordRef() {
        return recordRef;
    }
    public void setRecordRef(String recordRef) {
        this.recordRef = recordRef;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public String getModified() {
        return modified;
    }
    public void setModified(String modified) {
        this.modified = modified;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public long getTime(){
    	try{
    	DateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	Date dateTimes = iso8601.parse(this.getCreated());
    	long timeIs = dateTimes.getTime();
    	return timeIs;
    	}
    	catch(Exception ex){
    		//Logger.info("Exception while converting time");
    		return 0;
    	}
    }
}
