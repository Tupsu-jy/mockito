package mockito.mavenproject;

public class FakeHinnoittelija implements IHinnoittelija {
	private float alennus;
	public FakeHinnoittelija(float alennus) {
		this.alennus = alennus;
	}
	public float getAlennusProsentti(Asiakas asiakas, Tuote tuote) {
		return alennus;
	}
}