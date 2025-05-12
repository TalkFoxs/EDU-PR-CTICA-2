package test;

import java.util.*;

import dogs.*;
import registers.*;
import static utilsProva.UtilsProva.*;

public class Test_DR_2 {
	
	public static void main (String [] args) {
		
		String [] owners = {"Alan", "Bernard", "Charles", "Dorothy"};
		String [] notOwners = {"Alanx", "xBernard", "Charxles", "Dorohy"};
		Set<Dog> [] sets = new Set[owners.length];
		Dog [] dogs = {
				new Dog("BAXTER", 45, DogPurpose.PERSONAL, "Noble", "Trewater" ),
				new Dog("BAXTER", 45, DogPurpose.PERSONAL, "Noble", "Asterling" ),
				new Dog("FINN", 40, DogPurpose.HUNTING, "Winston", "Trewater" ),
				new Dog("MAPLE", 35, DogPurpose.PERSONAL, "Belle", "Asterling" ),
				new Dog("NALA", 44, DogPurpose.PERSONAL, "Belle", "Camrose" ),
				new Dog("DIESEL", 44, DogPurpose.RESCUE, "Diesel", "vom Burgimwald" ),
				new Dog("DIESEL", 44, DogPurpose.PERSONAL, "Diesel", "Vegas du Haut Mansard" ),
				new Dog("FINN", 40, DogPurpose.HUNTING, "Dante", "Kimbertal" ),
				new Dog("FINN", 40, DogPurpose.HUNTING, "Dante", "Altobello" ),
				new Dog("FINN", 40, DogPurpose.HUNTING, "Dante", "von Hohenhalle" ),
				new Dog("MAVERICK", 30, DogPurpose.HUNTING, "Dante", "von der Bleichstrasse" ),
				new Dog("MAVERICK", 30, DogPurpose.HUNTING, "Dante", "del Citone" ),
				new Dog("WILLOW", 35, DogPurpose.MILITARY, "Dante", "Tahi-Reme" ),
				new Dog("JAX", 35, DogPurpose.MILITARY, "Zeus", "Kimbertal" ),
		};
		DogID[] unknownIDs = {
				new DogID("Noble", "Camrose"),
				new DogID("Nobl", "Trewater"),
				new DogID("Zeus", "vom Burgimwald"),
				new DogID("Xeus", "Kimbertal"),
				new DogID("Belle", "Vegas du Haut Mansard"),
				new DogID("Disel", "vom Burgimwald"),
		};
		
		TreeMap<String, TreeSet<Dog>> all = new TreeMap<String, TreeSet<Dog>>();
		
		for (int i=0; i<owners.length; i++) {
			sets[i] = new TreeSet<Dog>();
		}
		
		for (int i=0; i<dogs.length; i++) {
			sets[i%owners.length].add(dogs[i]);
		}
		
		for (int i=0; i<owners.length; i++) {
			all.put(owners[i], (TreeSet<Dog>)sets[i]);
		}
		
		iniciar("Prova 2. MyDogRegister");
		
		DogRegister wr = new DogRegisterImp();
		informar("registre creat\n");
		
		// let's register the owners
		informar("Registrar usuaris...");
		try {
			for (int i = 0; i < owners.length; i++) {
				if (!wr.registerOwner(owners[i])) {
					notificarError(
							"registerOwner. Resultat incorrecte. S'esperava true",
							SORTIR);
				}
			}
		} catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...registre usuaris finalitzat. Aparentment sense problemes\n");
		
		// let's register the same the owners
		informar("Registrar usuaris repetits...");
		try {
			for (int i = 0; i < owners.length; i++) {
				if (wr.registerOwner(owners[i])) {
					notificarError(
							"registerOwner. Resultat incorrecte. S'esperava true",
							SORTIR);
				}
			}
		} catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...registre usuaris repetits finalitzat. Aparentment sense problemes\n");
		
		// let's register dogs to unknown owners...
		informar("Registrar gossos a propietaris desconeguts...");
		try {
			int i = 0;
			for (Dog dog : dogs) {
				try {
					wr.registerDog(notOwners[i], dog);
					notificarError("Gos enregistrat a usuari inexistent. S'esperava excepció UnknownOwnerException.", SORTIR);
				}
				catch(UnknownOwnerException e) {}
				i = (i+1)%notOwners.length;
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("... Registrar gossos a propietaris desconeguts resultat correcte\n");
		
		// let's register the dogs...
		informar("Registrar gossos...");
		try {
			for (String owner : all.keySet()) {
				for (Dog dog : all.get(owner)) {
					if (!wr.registerDog(owner, dog)) {
						notificarError("registerDog. Resultat incorrecte. S'esperava true", SORTIR);
					}
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...registre gossos finalitzat. Aparentment sense problemes\n");
		
		
		// let's register the dogs...
				informar("Registrar gossos per segona vegada. Mateixos propietaris...");
				try {
					for (String owner : all.keySet()) {
						for (Dog dog : all.get(owner)) {
							if (wr.registerDog(owner, dog)) {
								notificarError("registerDog. Resultat incorrecte. S'esperava false", SORTIR);
							}
						}
					}
				}
				catch(Exception e) {
					notificarExcepcio(e, SORTIR);
				}
				informar("...Registrar gossos per segona vegada. Mateixos propietaris finalitzat. Aparentment sense problemes\n");
		
		
		// let's register already registered dogs to known owners...
		informar("Registrar gossos ja registrats. Diferent propietari...");
		try {
			for (int i=0; i<owners.length; i++) {
				String owner = owners[i];
				String other = owners[(i+1)%owners.length];
				Set<Dog> theDogs = all.get(owner);
				for (Dog dog : theDogs) {
					try {
						wr.registerDog(other, dog);
						notificarError("Gos ja registrat a usuari inexistent. S'esperava excepció DifferentOwnerException.", SORTIR);
					}
					catch(DifferentOwnerException e) {}
				}
				
			}
		} catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("... Registrar gossos ja registrats diferent propietari resultat correcte\n");
		
		
		informar("findOwner per gossos no registrats");
		try {
			for (DogID id : unknownIDs) {
				if (wr.findOwner(id)!=null) {
					notificarError("findOwner ha trobat usuari per a gos no registrat", SORTIR);
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...findOwner gossos no registrats ha proporcionat els resultats esperats\n");
		
		/* TODO registered dogs amb usuaris inexistents i amb usuaris sense gossos*/
		
		
		finalitzar();
	}
	
	
}
