package calendar_new;

import java.util.List;

public class Date_Class {
	String Date_of_event;
	List<String> eventtype;
	List<String> description;
	List<String> other_text;
	int length;

	public Date_Class(String date, List<String> eventtype,
			List<String> description, List<String> other_text) {
		this.Date_of_event = date;
		this.eventtype = eventtype;
		this.description = description;
		this.other_text = other_text;
		length=eventtype.size();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDate_of_event() {
		return Date_of_event;
	}

	public void setDate_of_event(String date_of_event) {
		Date_of_event = date_of_event;
	}

	public List<String> getEventtype() {
		return eventtype;
	}

	public void setEventtype(List<String> eventtype) {
		this.eventtype = eventtype;
	}

	public String getDescription(int p) {
		return description.get(p);
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public List<String> getOther_text() {
		return other_text;
	}

	public void setOther_text(List<String> other_text) {
		this.other_text = other_text;
	}
	

}
