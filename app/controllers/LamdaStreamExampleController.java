package controllers;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.io.FileInputStream;
import java.io.InputStream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Insteract;
import model.Item;
import model.Data;
import model.ListI;
import play.*;
import javax.inject.*;
import play.mvc.*;
import java.util.stream.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.core.ObjectCodec;
public class LamdaStreamExampleController extends Controller {
	 public Result getAllData() {
	    	try{
	    	Logger.info("in getAllData");
	    	List<Insteract> insteract = readData();
	    	ObjectMapper mapper = new ObjectMapper();
	    	JsonNode node;
	    	node = mapper.convertValue(insteract, JsonNode.class);
	    	return ok(node);
	    	}catch (Throwable t) {
	    	  Logger.error("Exception with getAllData", t);
	    	  ObjectNode objectNode = returnErrorMessage("Error",0,"Reason Unknown");
	      	  return ok(objectNode);
	    	}
	    }
     /*
      * returns complete list of data based on order flag
      * possible order values --->> ASC,DESC 
      * .stream().sorted(byDateAsc).collect(Collectors.toList())
	 */
	 public Result getAllSortedData(String order) {
    	 ObjectMapper mapper = new ObjectMapper();
	    	try
	    	{
	    	int orderFlag = getOrderFlag(order);
	    	if (orderFlag == 1){
	    	List<Insteract> insteract = readData().stream()
	    	                                      .sorted(byDateAsc)
	    	                                      .collect(Collectors.toList());    	
	    	return ok(mapper.convertValue(insteract, JsonNode.class));
	    	}
	    	else if (orderFlag == -1){
	    		List<Insteract> insteract = readData().stream()
                .sorted(byDateDesc)
                .collect(Collectors.toList());    	
	    	return ok(mapper.convertValue(insteract, JsonNode.class));
	    	}
	    	else {
		   	return ok(returnErrorMessage("Error",0,"No order defined ASC OR DESC"));
		   }
	    	}
	    	catch (Throwable t) {
	    	  Logger.error("Exception with getAllData", t);
	    	  ObjectNode objectNode = returnErrorMessage("Error",0,"Reason Unknown");
	      	  return ok(objectNode);
	    	}
	    }
	 /*
      * returns singleObject 
      * input objectId of object queried
      * .stream().filter(s -> areTheyEqual(s.getId(),objectId)).findFirst().get()
	 */
	 public Result getSingleObjectData(String objectId) {
 	 	ObjectMapper mapper = new ObjectMapper();
	    	try{
	    	if (objectId == null) {
	    		return ok(returnErrorMessage("Error",0,"ObjectId not provided!!!"));
	    	}
	    	Insteract singleObject = readData().stream()
	    	                              .filter(s -> areTheyEqual(s.getId(),objectId))
	    	                              .findFirst()
	    	                              .get();
	    	return ok(mapper.convertValue(singleObject, JsonNode.class));
	    	}
	    	catch (Throwable t) {
	    	  Logger.error("Exception with getSingleObjectData", t);
	      	  return ok(returnErrorMessage("Error",0,"Reason Unknown"));
	    	}
	    }
	 /*
	  * Basic search example 
      * returns list of objects which match search criteria on companyId 
      * input searchString
      * .stream().filter(s -> searchCompanyId(s.getCompanyId(),searchString)).sorted(byDateAsc).collect(Collectors.toList())
	 */
	 public Result searchUsingCompanyId(String searchString) {
  	 	ObjectMapper mapper = new ObjectMapper();
 	    	try{
 	    	if (searchString == null) {
 	    		return ok(returnErrorMessage("Error",0,"SearchString not provided!!!"));
 	    	}
 	    	List<Insteract> searchObjects = readData().stream()
 	    	                              .filter(s -> searchCompanyId(s.getCompanyId(),searchString))
 	    	                              .sorted(byDateAsc)
 	    	                              .collect(Collectors.toList());
 	    	return ok(mapper.convertValue(searchObjects, JsonNode.class));
 	    	}
 	    	catch (Throwable t) {
 	    	  Logger.error("Exception with getSingleObjectData", t);
 	      	  return ok(returnErrorMessage("Error",0,"Reason Unknown"));
 	    	}
 	    }
	 /*
	  * Get list of objects between two timestamps (Date timestamps)
      * returns list of objects which match  criteria 
      * input startTimestamp and stopTimestamp
      * .stream().filter(s -> betweenTimestamps(startTime,stopTime,s.getTime())).sorted(byDateAsc).collect(Collectors.toList())
	 */
	 public Result getDataBetweenTimestamps(Long startTime,Long stopTime) {
   	 	ObjectMapper mapper = new ObjectMapper();
  	    	try{
  	    	if (startTime == null || stopTime == null) {
  	    		return ok(returnErrorMessage("Error",0,"Start,Stop not provided!!!"));
  	    	}
  	    	List<Insteract> timestampObjects = readData().stream()
  	    	                              .filter(s -> betweenTimestamps(startTime,stopTime,s.getTime()))
  	    	                              .sorted(byDateAsc)
  	    	                              .collect(Collectors.toList());
  	    	return ok(mapper.convertValue(timestampObjects, JsonNode.class));
  	    	}
  	    	catch (Throwable t) {
  	    	  Logger.error("Exception with getSingleObjectData", t);
  	      	  return ok(returnErrorMessage("Error",0,"Reason Unknown"));
  	    	}
  	    }
	 /*
	  * Get list of objects based on order , lastTimestamp of element , pageSize
      * returns list of objects which match criteria 
      * input order --> ASC,DESC and lastTimestamp and pageSize  
      * .stream().filter(s -> afterTimestamp(lastTimestamp,s.getTime())).sorted(byDateAsc)..limit(pageSize).collect(Collectors.toList())
	 */
     //need to pass id not timestamp as there can be more than one elements on a timestamp
     public Result getAllSortedDataPaging(String order,Long lastTimestamp,int pageSize) {
    	 ObjectMapper mapper = new ObjectMapper();
	    	try
	    	{
	    	int orderFlag = getOrderFlag(order);
	    	if (orderFlag == 1){
	    	List<Insteract> insteract = readData().stream()
	    	                                      .filter(s -> afterTimestamp(lastTimestamp,s.getTime()))
	    	                                      .sorted(byDateAsc)
	    	                                      .limit(pageSize)
	    	                                      .collect(Collectors.toList());    	
	    	return ok(mapper.convertValue(insteract, JsonNode.class));
	    	}
	    	else if (orderFlag == -1){
	    		List<Insteract> insteract = readData().stream()
	    	    .filter(s -> afterTimestamp(lastTimestamp,s.getTime()))
                .sorted(byDateDesc)
                .limit(pageSize)
                .collect(Collectors.toList());    	
	    	return ok(mapper.convertValue(insteract, JsonNode.class));
	    	}
	    	else {
		   	return ok(returnErrorMessage("Error",0,"No order defined ASC OR DESC"));
		   }
	    	}
	    	catch (Throwable t) {
	    	  Logger.error("Exception with getAllData", t);
	    	  ObjectNode objectNode = returnErrorMessage("Error",0,"Reason Unknown");
	      	  return ok(objectNode);
	    	}
	    }
     
     public static ObjectNode returnErrorMessage(String type,int code,String message){
     	ObjectMapper mapper = new ObjectMapper();
   	  	ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("Type", type);
        objectNode.put("Code",code);
        objectNode.put("Message",message);
        return objectNode;
     }
     /*
	  *return orderFlag 
	  *input order
	 */

     public static int getOrderFlag(String order){
    	 if (order.equals("ASC")){
    		 return 1;
    	 }
    	 else if (order.equals("DESC")){
    		 return -1;
    	 }
    	 else{
    		 return 2;
    	 }
     }
      
     /*
	  *Check two strings equal
	 */

    public static boolean areTheyEqual(String id,String objectId){
    	 return id.equals(objectId);
       }  
    /*
	  *Check if companyId contains searchStirng
	 */
    
    public static boolean searchCompanyId(String companyId,String searchString){
    	return companyId.contains(searchString);
    }
    public static boolean afterTimestamp(Long passedTimestamp,Long currentTimestamp){
    	if (currentTimestamp > passedTimestamp){
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public static boolean betweenTimestamps(Long startTime,Long stopTime,Long currentTimestamp){
    	Logger.info(currentTimestamp + " :: currentTimestamp");
    	Logger.info(startTime + " :: startTime");
    	Logger.info(stopTime + " :: stopTime");
    	if ((currentTimestamp >= startTime) && (currentTimestamp <= stopTime)){
    		return true;
    	}
    	else {
    		return false;
    	}
    }
     Comparator<Insteract> byDateAsc = (e1, e2) -> Long.compare(
             e1.getTime(), e2.getTime());
     Comparator<Insteract> byDateDesc = (e1, e2) -> Long.compare(
             e2.getTime(), e1.getTime());
     public static List<Insteract> readData() {
    	 try{
    	InputStream is = new FileInputStream("C://test1.txt");
     	ObjectMapper mapper = new ObjectMapper();
     	List<Insteract> insteract = mapper.readValue(is, new TypeReference<List<Insteract>>(){});
     	return insteract;
    	 }
    	 catch(Exception t){
    		 Logger.info("For now just log it");
    		 Logger.info(t.getMessage());
    		 return new ArrayList<Insteract>();
    	 }
     	
     }
     
}
