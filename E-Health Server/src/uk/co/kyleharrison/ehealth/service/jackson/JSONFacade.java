package uk.co.kyleharrison.ehealth.service.jackson;

import com.google.gson.Gson;

import uk.co.kyleharrison.ehealth.model.pojo.RSSItem;

public class JSONFacade {

	public static void main(String[] args) {
		 
		RSSItem rssItem = new RSSItem();
		rssItem.setTitle("Hello World");
		rssItem.setCatergory("Testing");
	
		Gson gson = new Gson();      
		String json = gson.toJson(rssItem);
		System.out.println(json);
		
	  }
}
