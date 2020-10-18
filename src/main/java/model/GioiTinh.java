package model;

public enum GioiTinh {
	FEMALE("Nu", "Nu"), MALE("Nam", "Nam"),OTHER("Khac","Khac");
	 
	   private String code;
	   private String text;
	 
	   private GioiTinh(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static GioiTinh getByCode(String genderCode) {
	       for (GioiTinh g : GioiTinh.values()) {
	           if (g.code.equals(genderCode)) {
	               return g;
	           }
	       }
	       return null;
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
