public class Dog extends Pet {
    // Instance variables
    private double droolRate;

    // Constructors
    public Dog(String name, double health, int painLevel){
        this(name,health,painLevel,5.0);
    }
    public Dog(String name, double health, int painLevel, double droolRate){
        super(name,health,painLevel);
        if (droolRate <= 0.0){
            droolRate = 0.5;
        }
        this.droolRate = droolRate;
    }

    // Getters
    public double getDroolRate(){
        return  this.droolRate;
    }

    // Inherited Methods
    public int treat(){
        int painMult = 1;
        int healthMult = 1;

        // Modifiers for droolRate
        if (droolRate < 3.5){
            painMult = 2;
        }
        else if (droolRate > 7.5){
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
        String speakString = "bark ".repeat(this.getPainLevel()).trim();
        speakString = this.getPainLevel() > 5 ? speakString.toUpperCase() : speakString;
        super.speak();
        System.out.println(speakString);
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o) && this.droolRate == ((Dog) o).getDroolRate();
    }

}
