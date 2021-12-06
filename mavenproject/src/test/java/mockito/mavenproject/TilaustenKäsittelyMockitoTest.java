package mockito.mavenproject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TilaustenKäsittelyMockitoTest {
	@Mock
	IHinnoittelija hinnoittelijaMock;
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	public void testaaKäsittelijäWithMockitoHinnoittelija() {
		// Arrange
		float alkuSaldo = 100.0f;
		float listaHinta = 30.0f;
		float alennus = 20.0f;
		float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
		Asiakas asiakas = new Asiakas(alkuSaldo);
		Tuote tuote = new Tuote("TDD in Action", listaHinta);
		// Record
		when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
		.thenReturn(alennus);
		// Act
		TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
		käsittelijä.setHinnoittelija(hinnoittelijaMock);
		käsittelijä.käsittele(new Tilaus(asiakas, tuote));
		// Assert
		assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
		verify(hinnoittelijaMock, times(2)).getAlennusProsentti(asiakas, tuote);
	}
	
	@Test
	public void testaaKäsittelijäWithMockitoHinnoittelijaIsolla() {
		// Arrange
		float alkuSaldo = 1000.0f;
		float listaHinta = 300.0f;
		float alennus = 20.0f;
		float loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus+5) / 100));
		Asiakas asiakas = new Asiakas(alkuSaldo);
		Tuote tuote = new Tuote("TDD in Action", listaHinta);
		// Record
		when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
		.thenReturn(alennus, alennus+5);
		// Act
		TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
		käsittelijä.setHinnoittelija(hinnoittelijaMock);
		käsittelijä.käsittele(new Tilaus(asiakas, tuote));
		// Assert
		assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
		verify(hinnoittelijaMock, times(2)).getAlennusProsentti(asiakas, tuote);
	}
}