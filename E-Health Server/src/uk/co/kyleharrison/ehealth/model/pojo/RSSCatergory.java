package uk.co.kyleharrison.ehealth.model.pojo;

import java.util.ArrayList;

public class RSSCatergory  {
	private ArrayList <String> yearTitle;
	private int year;
	private String group;
	private String typeOfNotification;
	
	public RSSCatergory(ArrayList<String> yearTitle, int year, String group,
			String typeOfNotification) {
		super();
		this.yearTitle = yearTitle;
		this.year = year;
		this.group = group;
		this.typeOfNotification = typeOfNotification;
	}
	public ArrayList<String> getYearTitle() {
		return yearTitle;
	}
	public void setYearTitle(ArrayList<String> yearTitle) {
		this.yearTitle = yearTitle;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getTypeOfNotification() {
		return typeOfNotification;
	}
	public void setTypeOfNotification(String typeOfNotification) {
		this.typeOfNotification = typeOfNotification;
	}
	
}
