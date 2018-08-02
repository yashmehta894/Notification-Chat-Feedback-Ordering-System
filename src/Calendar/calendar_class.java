package Calendar;

public class calendar_class {
private String date;
private String eventtype;
private String description;
private String other_text;

public calendar_class(String date,String event,String desc)
{
	this.date=date;
	this.eventtype=event;
	this.description=desc;
}

public String getDate() {
	
	return date;
}

public void setDate(String date) {
	
	this.date = date;
}

public String getEventtype() {
	return eventtype;
}

public void setEventtype(String eventtype) {
	this.eventtype = eventtype;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getOther_text() {
	return other_text;
}

public void setOther_text(String other_text) {
	this.other_text = other_text;
}
	
}
