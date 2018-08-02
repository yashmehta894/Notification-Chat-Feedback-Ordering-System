package food;

public class Hotel_Object {
	public static String hotel_name;
	public static String hotel_image;
	public static String hotel_num1;
	public static String hotel_num2;
	public static String hotel_id;
	Hotel_Object(String id,String h,String n1,String n2)
	{
		hotel_id=id;
		hotel_name=h;
		//hotel_image=i;
		hotel_num1=n1;
		hotel_num2=n2;
	}
	public  String getHotel_name() {
		return hotel_name;
	}
	public  void setHotel_name(String hotel_name) {
		Hotel_Object.hotel_name = hotel_name;
	}
	public  String getHotel_image() {
		return hotel_image;
	}
	public  void setHotel_image(String hotel_image) {
		Hotel_Object.hotel_image = hotel_image;
	}
	public  String getHotel_num1() {
		return hotel_num1;
	}
	public  void setHotel_num1(String hotel_num1) {
		Hotel_Object.hotel_num1 = hotel_num1;
	}
	public  String getHotel_num2() {
		return hotel_num2;
	}
	public  void setHotel_num2(String hotel_num2) {
		Hotel_Object.hotel_num2 = hotel_num2;
	}
	public  String getHotel_id() {
		return hotel_id;
	}
	public  void setHotel_id(String hotel_id) {
		Hotel_Object.hotel_id = hotel_id;
	}
    public String toString()
    {
    	return hotel_id+" "+hotel_name+" "+hotel_num1+" "+hotel_num2;
    	
    	
    }

}
