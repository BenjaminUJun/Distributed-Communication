package util;

import java.util.regex.Pattern;

	public class RegularExpression {
		
	public static boolean isNumeric(String str){ 
		    Pattern pattern = Pattern.compile("[0-9]*"); 
		    return pattern.matcher(str).matches();    
	} 
	
	public static boolean checkEmai(String str){
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		return pattern.matcher(str).matches(); 
	}
	
	public static boolean checkDate(String checkinDate, String checkoutDate){
		Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		boolean a = pattern.matcher(checkinDate).matches();
		boolean b = pattern.matcher(checkoutDate).matches();
		int result = checkinDate.compareTo(checkoutDate);
		if(a&&b&&result<0){
			return true;
		}else{
		 return false;
		}
	}
	
	public static boolean checkPassword(String str){
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{6,16}");
		return pattern.matcher(str).matches();		
	}
	
	public static boolean checkTel(String str){
		Pattern pattern = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
		return pattern.matcher(str).matches();
	}
	public static boolean checkCreditNO(String str){
		Pattern pattern = Pattern.compile("[0-9]{16}");
		return pattern.matcher(str).matches();		
	}
	//public static String 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
