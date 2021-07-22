public class Cat extends Pet {
    // Instance variables
    private int miceCaught;

    // Constructors
    public Cat(String name, double health, int painLevel){
        this(name,health,painLevel,0);
    }
    public Cat(String name, double health, int painLevel, int miceCaught){
        super(name,health,painLevel);
        this.miceCaught = Math.max(miceCaught,0);
    }

    // Getters
    public int getMiceCaught(){
        return this.miceCaught;
    }

    // Inherited Methods
    public int treat(){
        int painMult = 1;
        int healthMult = 1;

        // Modifiers for droolRate
        if (miceCaught < 4){
            painMult = 2;
        }
        else if (miceCaught > 7){
            healthMult = 2;
        }

        int timeHeal = (int) ((this.getPainLevel()*painMult) / (this.getHealth()*healthMult));

        // Call parent class heal method
        super.heal();

        return timeHeal;
    }

    // Overrides
    @Override
    public void speak() {
        String speakString = "meow ".repeat(this.getPainLevel()).trim();
        speakString = this.getPainLevel() > 5 ? speakString.toUpperCase() : speakString;
        super.speak();
        System.out.println(speakString);
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o) && this.miceCaught == ((Cat) o).getMiceCaught();
    }
}
