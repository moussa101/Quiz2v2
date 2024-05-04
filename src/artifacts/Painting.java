package artifacts;

public class Painting extends Artifact {

	private String artist;
	private double width;
	private double height;
	private ColorType color;

	public Painting(String name, int yearOfManfucture, String artist,
			double width, double height, ColorType color) {
		super(name, yearOfManfucture);
		this.artist = artist;
		this.width = width;
		this.height = height;
		this.color = color;

	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getArtist() {
		return artist;
	}

	public ColorType getColor() {
		return color;
	}

	public void changeDimensions(double width, double height) {
		
		double oldRatio = this.width / this.height;
		double newRatio = width / height;
		if (oldRatio == newRatio) {
			this.width = width;
			this.height = height;
		}

	}

	

}
