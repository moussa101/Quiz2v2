package artifacts;


public  class Artifact {

	public static final  String ORIGIN = "Egypt";
	private String name;
	private int yearOfManfucture;
	
	
	 public Artifact(String name, int yearOfManfucture){
		 this.name = name;
		 this.yearOfManfucture = yearOfManfucture;
	 }


	public String getName() {
		return name;
	}
	
	

	public int getYearOfManfucture() {
		return yearOfManfucture;
	}
	 
	public int  calculateAge(){
		 return 2024-yearOfManfucture;
	 }
	
}
