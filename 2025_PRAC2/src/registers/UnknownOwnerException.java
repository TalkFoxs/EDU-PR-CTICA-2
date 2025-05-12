package registers;

public class UnknownOwnerException extends IllegalStateException {


	public UnknownOwnerException(String owner) {
		super(owner);
		
	}


}
