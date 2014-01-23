package uk.co.jackgraham.ehealth.controller.rest;


import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/feedItems")
public class GetSpecificFeeds {

	private String yearID;
	private int limit1;
	private int limit2;
	
	@Path("/{param}")// Param will be in form year1-1-10
	public void getItems(@PathParam("param") String limits)
	{
		String[] parts = limits.split("-");
		
		yearID = parts[0];
		limit1 = Integer.getInteger(parts[1]);
		limit2 = Integer.getInteger(parts[2]);
		
		int fID =0;
		try{
			if(yearID.substring(4,5)=="A"){
				fID=6;
			}else{
			fID= Integer.parseInt(yearID.substring(4,5));
			}
		}catch(Exception e){
			e.getMessage();
		}
		
		
		switch (fID) {

		case 1:
			// Get year 1 from DB
			
			break;
		case 2:
			// Get year 2 from DB
			
			break;
		case 3:
			// Get year 3 from DB
			
			break;
		case 4:
			// Get year 4 from DB
			
			break;
		case 5:
			// Get year 5 from DB
			
			break;
		case 6:
			// Get all years from DB
			
			break;
		}
	}
	
}
