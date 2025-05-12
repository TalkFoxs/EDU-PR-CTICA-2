package registers;

import dogs.Dog;
import dogs.DogID;
import dogs.DogPurpose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class DogRegisterImp implements DogRegister{

    private ArrayList<Dog> llistaDog;
    private ArrayList<String> llistaOwner;
    private HashMap<String,Dog> dogOwner;


    public DogRegisterImp (){
        this.llistaDog = new ArrayList<>();
        this.llistaOwner = new ArrayList<>();
        this.dogOwner = new HashMap<String,Dog>();
    };

    @Override
    public boolean registerOwner(String owner) {
        if(this.llistaOwner.contains(owner)){
            return false;
        }else{
            this.llistaOwner.add(owner);
            return true;
        }
    };

    @Override
    public boolean registerDog(String owner, Dog dog) {
        if(!this.llistaOwner.contains(owner)){
            throw new UnknownOwnerException("El Owner no esta registrado.");
        }
        if(this.dogOwner.containsKey(dog.getId())){
            Dog realOwner = this.dogOwner.get(owner);
            if(realOwner.equals(dog)){
                return false;
            }else {
                throw new DifferentOwnerException();
            }
        }

        dogOwner.put(owner,dog);
        return true;
    }

    @Override
    public String findOwner(DogID id) {
        for(String owner : dogOwner.keySet()){
            Dog dog = dogOwner.get(owner);
            if(dog.getId().equals(id)){
                return owner;
            }
        }
        return null;
    }

    @Override
    public SortedSet<Dog> registeredDogs(String owner) {
        return null;
    }

    @Override
    public SortedSet<String> findPurposeOwners(DogPurpose purpose) {
        return null;
    }


}
