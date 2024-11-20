package champollion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChampollionJUnitTest {

	private Enseignant bastide;
	private Enseignant lamine;
	private UE uml;
	private UE bd;
	private UE web;

	@BeforeEach
	public void setUp() {
		bastide = new Enseignant("Rémi Bastide", "Remi.Bastide@irit.fr");
		lamine = new Enseignant("Elyes Lamine", "Elyes.Lamine@univ-jfc.fr");

		uml = new UE("Conception par objets avec UML");
		bd = new UE("Bases de données");
		web = new UE("Technologies web");
	}


	@Test
	public void testEnSousService() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);
		bastide.ajouteEnseignement(bd, 5, 5, 5);

		assertTrue(bastide.enSousService(), "L'enseignant devrait être en sous-service avec moins de 192 heures prévues");

		bastide.ajouteEnseignement(web, 100, 100, 100);
		assertFalse(bastide.enSousService(), "L'enseignant ne devrait pas être en sous-service avec plus de 192 heures prévues");
	}

	@Test
	public void testAjouteEnseignement() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);
		bastide.ajouteEnseignement(bd, 5, 5, 5);

		assertEquals(2, bastide.getServicePrevus().size(), "L'enseignant devrait avoir 2 enseignements");
	}


	@Test
	public void testGetServicePrevuFromUE() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);

		ServicePrevu service = bastide.getServicePrevuFromUE(uml);
		assertNotNull(service, "Le service prévu pour l'UE UML ne doit pas être null");
		assertEquals(uml, service.getUe(), "L'UE du service prévu ne correspond pas à l'UE attendue");
	}

	@Test
	public void testAjouteIntervention() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);

		Intervention intervention = new Intervention(uml, TypeIntervention.CM, 5);
		bastide.ajouteIntervention(intervention);

		assertTrue(bastide.getIntervensions().contains(intervention), "L'intervention doit être ajoutée à la liste des interventions");
	}

	@Test
	public void testAjouteInterventionInvalid() {
		Intervention invalidIntervention = new Intervention(new UE("UE Inexistante"), TypeIntervention.CM, 5);

		assertThrows(IllegalArgumentException.class, () -> {
			bastide.ajouteIntervention(invalidIntervention);
		}, "L'ajout d'une intervention pour une UE non enseignée doit lever une exception");
	}

	@Test
	public void testHeuresPrevuesPourUE() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);

		int heuresUE = bastide.heuresPrevuesPourUE(uml);
		// Calcul : (10h CM * 1.5) + (10h TD * 1) + (10h TP * 0.75) = 15 + 10 + 7.5 = 32.5h
		assertEquals(33, heuresUE, "Le nombre d'heures prévues pour l'UE UML devrait être arrondi à 33 heures.");
	}

	@Test
	public void testEquivalentTD() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);

		// Vérifier que l'équivalent TD pour chaque type d'intervention est correctement calculé
		assertEquals(15, bastide.equivalentTD(TypeIntervention.CM, 10), "1h de CM devrait être équivalent à 1.5h de TD");
		assertEquals(10, bastide.equivalentTD(TypeIntervention.TD, 10), "1h de TD devrait être équivalent à 1h de TD");
		assertEquals(7.5, bastide.equivalentTD(TypeIntervention.TP, 10), "1h de TP devrait être équivalent à 0.75h de TD");
	}

	@Test
	public void testServicePrevuAjout() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);

		ServicePrevu service = bastide.getServicePrevuFromUE(uml);
		assertNotNull(service, "Le service prévu pour l'UE UML ne doit pas être null");
		assertEquals(uml, service.getUe(), "L'UE du service prévu ne correspond pas à l'UE attendue");
		assertEquals(10, service.getVolumeCM(), "Le volume CM pour l'UE UML doit être de 10 heures");
		assertEquals(10, service.getVolumeTD(), "Le volume TD pour l'UE UML doit être de 10 heures");
		assertEquals(10, service.getVolumeTP(), "Le volume TP pour l'UE UML doit être de 10 heures");
	}
	@Test
	public void testAjoutHeuresEnseignementExistante() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);
		bastide.ajouteEnseignement(uml, 5, 5, 5);

		ServicePrevu service = bastide.getServicePrevuFromUE(uml);
		// Le volume total devrait être mis à jour
		assertEquals(15, service.getVolumeCM(), "Le volume CM pour l'UE UML devrait être de 15 heures après ajout");
		assertEquals(15, service.getVolumeTD(), "Le volume TD pour l'UE UML devrait être de 15 heures après ajout");
		assertEquals(15, service.getVolumeTP(), "Le volume TP pour l'UE UML devrait être de 15 heures après ajout");
	}
	@Test
	public void testAjouteEnseignementExistant() {
		bastide.ajouteEnseignement(uml, 10, 10, 10);
		// Ajout de l'enseignement avec des volumes différents
		bastide.ajouteEnseignement(uml, 5, 5, 5);

		ServicePrevu service = bastide.getServicePrevuFromUE(uml);
		// Vérifier que les volumes sont correctement additionnés
		assertEquals(15, service.getVolumeCM(), "Le volume CM pour l'UE UML devrait être de 15 heures après ajout");
		assertEquals(15, service.getVolumeTD(), "Le volume TD pour l'UE UML devrait être de 15 heures après ajout");
		assertEquals(15, service.getVolumeTP(), "Le volume TP pour l'UE UML devrait être de 15 heures après ajout");
	}

	@Test
	public void testListeInterventionsVideAvantAjout() {
		// Avant ajout
		assertTrue(bastide.getIntervensions().isEmpty(), "La liste des interventions devrait être vide au départ.");

		bastide.ajouteEnseignement(uml, 10, 10, 10);
		Intervention intervention = new Intervention(uml, TypeIntervention.CM, 5);
		bastide.ajouteIntervention(intervention);

		// Après ajout
		assertFalse(bastide.getIntervensions().isEmpty(), "La liste des interventions ne devrait pas être vide après ajout.");
		assertTrue(bastide.getIntervensions().contains(intervention), "L'intervention doit être présente dans la liste des interventions.");
	}
	@Test
	public void testEnSousServiceAvec192Heures() {
		bastide.ajouteEnseignement(uml, 64, 64, 64); // 64h CM, 64h TD, 64h TP => 192h

		assertFalse(bastide.enSousService(), "L'enseignant ne devrait pas être en sous-service avec exactement 192 heures prévues.");
	}


}


