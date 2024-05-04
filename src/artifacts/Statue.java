package artifacts;

public class Statue extends Artifact {
	public static final String AGE = "Pharaonic age";
	private MaterialType material;

	public Statue(String name, int yearOfManfucture, MaterialType material) {
		super(name, yearOfManfucture);
		this.material = material;
	}

	public MaterialType getMaterial() {
		return material;
	}

	public void setMaterial(MaterialType material) {
		this.material = material;
	}

	

}
