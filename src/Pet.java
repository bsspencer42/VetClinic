public abstract class Pet {
    // Instance variables
    private String name;
    private double health;
    private int painLevel;

    // Constructor
    public Pet(String name, double health, int painLevel){
        if (health > 1.0){
            health = 1.0;
        }
        else if (health < 0){
            health = 0;
        }
        if (painLevel > 10){
            painLevel = 10;
        }
        else if (painLevel < 1){
            painLevel = 1;
        }
        this.name = name;
        this.health = health;
        this.painLevel = painLevel;
    }

    // Getters
    public String getName(){
        return this.name;
    }
    public double getHealth(){
        return this.health;
    }
    public int getPainLevel(){
        return this.painLevel;
    }

    // Abstract Methods
    abstract int treat();

    // Methods
    public void speak(){
        String speakString = "Hello! My name is " + this.name;
        speakString = this.getPainLevel() > 5 ? speakString.toUpperCase() : speakString;
        System.out.println(speakString);
    }

    protected void heal(){
        this.health = 1.0;
        this.painLevel = 1;
    }

    // Overrides
    @Override
    public boolean equals(Object o){
        if (o instanceof Pet){
            Pet otherPet = (Pet) o;
            return this.name == otherPet.getName();
        }
        return false;
    }
}
