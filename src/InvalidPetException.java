public class InvalidPetException extends Exception {
    public InvalidPetException(){
        super("Your pet is invalid!");
    }
    public InvalidPetException(String s){
        super(s);
    }
}
