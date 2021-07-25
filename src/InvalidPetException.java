public class InvalidPetException extends Error {
    public InvalidPetException(){
        super("Your pet is invalid!");
    }
    public InvalidPetException(String s){
        super(s);
    }
}
