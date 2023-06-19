public class ImagePost extends Post{

    //We've defined the attributes of our object here.
    private String fileName;
    private String resolution;

    //We used a constructor for our object to be able to create new objects by calling this method with it's attributes as parameters.
    public ImagePost(String text, Location location, String[] tagged, String fileName, String resolution) {
        super(text, location, tagged);
        this.setFileName(fileName);
        this.setResolution(resolution);
    }

    //Here are our setter and getter methods written, so that we can access attributes of our object because they're private.
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    //Here we override toString method to be able to print the object's attributes as we please.
    @Override
    public String toString(){
        return this.getText() + "\nDate: " + this.getDate() + "\nLocation: " + this.getLocation() +
                "\nFriends tagged in this post: " + this.printTagged() + "\nImage: " + this.getFileName() +
                "\nImage resolution: " + this.getResolution();
    }
}