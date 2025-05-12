package test;

import java.util.*;
import dogs.*;
import registers.*;
import static utilsProva.UtilsProva.*;

public class Test_DR_1 {
	
	public static void main (String [] args) {
		
		String [] owners = {"Alan", "Bernard", "Charles", "Dorothy"};
		Set<Dog>[] sets = new Set[owners.length];
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
		
		iniciar("Prova 1. MyDogRegister");
		
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
		
		// let's check the owner of each dog
		informar("findOwner...");
		try {
			for (String realOwner : all.keySet()) {
				for (Dog dog : all.get(realOwner)) {
					String owner = wr.findOwner(new DogID(dog.getId().getName(), dog.getId().getFamilyName()));
					if (!realOwner.equals(owner)) {
						notificarError("findOwner. Resultat incorrecte. Esperat: "+realOwner+" obtingut "+owner, SORTIR);
					}
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...findOwner ha proporcionat els resultats esperats\n");
		
		// let's check the dogs of each owner
		informar("registeredDogs...");
		try {
			for (String owner : all.keySet()) {
				TreeSet<Dog> realSet = all.get(owner);
				SortedSet<Dog> returned = wr.registeredDogs(owner);
				if (!realSet.equals(returned)) {
					notificarError("registeredDogs ha proporcionat un resultat incorrecte. ESPERAT:\n  "+realSet+"\nOBTINGUT\n  "+returned, SORTIR);
				}
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("...registeredDogs ha proporcionat els resultats esperats\n");
		
		finalitzar();
	}
	
	
}
