public class VideoPost extends Post{

    //We've defined the attributes of our object here.
    private String fileName;
    private double duration;
    private static final double maxDuration = 10.0;

    //We used a constructor for our object to be able to create new objects by calling this method with it's attributes as parameters.
    public VideoPost(String text, Location location, String[] tagged, String fileName, double duration) {
        super(text, location, tagged);
        this.setFileName(fileName);
        this.setDuration(duration);
    }

    //Here are our setter and getter methods written, so that we can access attributes of our object because they're private.
    public static double getMaxDuration() {
        return maxDuration;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double resolution) {
        this.duration = resolution;
    }

    //Here we override toString method to be able to print the object's attributes as we please.
    @Override
    public String toString(){
        return this.getText() + "\nDate: " + this.getDate() + "\nLocation: " + this.getLocation() +
                "\nFriends tagged in this post: " + this.printTagged() + "\nVideo: " + this.getFileName() +
                "\nVideo duration: " + this.getDuration() + " minutes";
    }
}