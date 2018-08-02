package Rules;

import java.util.ArrayList;

public class Rules_perm {
String title;
ArrayList<String> rules_in_title;
public Rules_perm(String title, ArrayList<String> rules_in_title) {
	super();
	this.title = title;
	this.rules_in_title = rules_in_title;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public ArrayList<String> getRules_in_title() {
	return rules_in_title;
}
public void setRules_in_title(ArrayList<String> rules_in_title) {
	this.rules_in_title = rules_in_title;
}
@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title+" "+rules_in_title.toArray().toString()+" ";
	}


}
