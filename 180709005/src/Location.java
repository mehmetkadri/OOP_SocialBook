public class Location{

    //We've defined the attributes of our object here.
    private double latitude;
    private double longitude;

    //We used a constructor for our object to be able to create new objects by calling this method with it's attributes as parameters.
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Here we override toString method to be able to print the object's attributes as we please.
    @Override
    public String toString(){
        return this.longitude + ", " + this.latitude;
    }
}