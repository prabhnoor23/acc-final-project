package Project;

public class Utilities {
	
	/**
	  * Checks whether the string is alphanumeric or not
	  * @param s String.
	  */
	public static boolean isAlphaNumeric(String s) {
      return s != null && s.matches("^[a-zA-Z0-9]*$");
	}
	
	/**
	  * Checks whether the string contains only alphabets or not
	  * @param s String.
	  */
	public static boolean isAplhaOnly(String s) {
	      return s != null && s.matches("^[a-zA-Z]*$");
	}

}
