package Rules;

public class Rules_Class {
	
	
	String rule_subtitle;
	String  rule_sub_description;
	Rules_Class(String rs,String rsd)
	{
		rule_subtitle=rs;
		rule_sub_description=rsd;
	}
	public String getRule_subtitle() {
		return rule_subtitle;
	}
	public void setRule_subtitle(String rule_subtitle) {
		this.rule_subtitle = rule_subtitle;
	}
	public String getRule_sub_description() {
		return rule_sub_description;
	}
	public void setRule_sub_description(String rule_sub_description) {
		this.rule_sub_description = rule_sub_description;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return rule_subtitle+" "+rule_sub_description;
		
	}


}
