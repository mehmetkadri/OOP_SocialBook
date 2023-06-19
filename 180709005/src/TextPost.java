public class TextPost extends Post{

    //We used a constructor for our object to be able to create new objects by calling this method with it's attributes as parameters.
    public TextPost(String text, Location location, String[] tagged) {
        super(text, location, tagged);
    }

    //Here we override toString method to be able to print the object's attributes as we please.
    @Override
    public String toString(){
        String tagged = (this.getTagged().length==0)? "" : "\nFriends tagged in this post: " + this.printTagged();
        return this.getText() + "\nDate: " + this.getDate() + "\nLocation: " + this.getLocation() + tagged;
    }
}