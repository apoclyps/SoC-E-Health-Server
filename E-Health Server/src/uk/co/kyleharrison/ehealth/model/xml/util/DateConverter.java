package uk.co.kyleharrison.ehealth.model.xml.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

	private String dateString;
	private Date date;
	
	public DateConverter() {
		super();
	}

	public DateConverter(String dateString, Date date) {
		super();
		this.setDateString(dateString);
		this.setDate(date);
	}

	public Date convertStringToDate(String lastBuildDate){
		// Mon, 20 Jan 2014 13:43:29 +0000
		String subLastBuildData = lastBuildDate.substring(0, 25);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
		java.util.Date date = null;
		try {
			date =  simpleDateFormat.parse(subLastBuildData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
