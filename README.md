# play-java-insteract-lamda-streams
Examples with lamda and streams

GET /getAllData                    controllers.LamdaStreamExampleController.getAllData

 *************************************************************************
      * returns complete list of data based on order flag
      * possible order values --->> ASC,DESC 
      * .stream().sorted(byDateAsc).collect(Collectors.toList())
 **************************************************************************      
GET /getAllSortedData/:order       controllers.LamdaStreamExampleController.getAllSortedData(order)

 *************************************************************************
      * returns singleObject 
      * input objectId of object queried
      * .stream().filter(s -> areTheyEqual(s.getId(),objectId)).findFirst().get()
 
 *************************************************************************
GET /getSingleObjectData/:objectId controllers.LamdaStreamExampleController.getSingleObjectData(objectId)

*************************************************************************
	  * Basic search example 
      * returns list of objects which match search criteria on companyId 
      * input searchString
      * .stream().filter(s -> searchCompanyId(s.getCompanyId(),searchString)).sorted(byDateAsc).collect(Collectors.toList())
*************************************************************************

GET /searchUsingCompanyId/:searchString controllers.LamdaStreamExampleController.searchUsingCompanyId(searchString)

 *************************************************************************

	  * Get list of objects between two timestamps (Date timestamps)
      * returns list of objects which match  criteria 
      * input startTimestamp and stopTimestamp
      * .stream().filter(s -> betweenTimestamps(startTime,stopTime,s.getTime())).sorted(byDateAsc).collect(Collectors.toList())

*************************************************************************


GET /getDataBetweenTimestamps/:startTime/:stopTime 				controllers.LamdaStreamExampleController.getDataBetweenTimestamps(startTime: Long,stopTime: Long)

 *************************************************************************
	  * Get list of objects based on order , lastTimestamp of element , pageSize
      * returns list of objects which match criteria 
      * input order --> ASC,DESC and lastTimestamp and pageSize  
      * .stream().filter(s -> afterTimestamp(lastTimestamp,s.getTime())).sorted(byDateAsc)..limit(pageSize).collect(Collectors.toList())
 
*************************************************************************
************************************************
*NOTE : FOR FIRST CALL PASS lastTimestamp as 0.
**************************************************
GET /getAllSortedDataPaging/:order/:lastTimestamp/:pageSize          controllers.LamdaStreamExampleController.getAllSortedDataPaging(order: String,lastTimestamp: Long,pageSize: Integer)
