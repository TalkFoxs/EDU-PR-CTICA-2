package registers;

import java.util.*;
import dogs.*;

public interface DogRegister {

	/* Registers the given owner. If the owner already exists in the register,
	 * this method does nothing and simple returns false. If the owner did not
	 * exist, it is registered and the method returns true.
	 */
	public boolean registerOwner (String owner);
	
	/* Registers the given dog for the given owner.
	 * Throws UnknownOwnerException if the owner is not already registered
	 * Throws DifferentOwnerException if the dog is already registered to another owner
	 * Returns false if the dog is already registered to owner and true otherwise;
	 */
	public boolean registerDog(String owner, Dog dog);
	
	/* Returns the owner of the given dog. Returns null if no owner is known */
	public String findOwner (DogID id);
	
	/* Returns all the dogs registered to the given owner.
	 * The result is an empty set if the owner is unknown or has
	 * no dogs registered
	 */
	public SortedSet<Dog> registeredDogs(String owner);
	
	/* Returns all the owners who own a dog (or more) with the given purpose */
	public SortedSet<String> findPurposeOwners (DogPurpose purpose);
}
