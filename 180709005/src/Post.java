import java.util.Date;

public class Post{

    //We've defined the attributes of our object here.
    private String text;
    private Date date;
    private Location location;
    private String[] tagged;

    //We used a constructor for our object to be able to create new objects by calling this method with it's attributes as parameters.
    public Post(String text, Location location, String[] tagged) {
        this.setText(text);
        this.setDate(getCurrentDate());
        this.setLocation(location);
        this.setTagged(tagged);
    }

    public Date getCurrentDate() {                          //I've used date class and it's methods to
        Date current = new Date();                          //hold date information in.
        return current;
    }

    public String printTagged(){
        if(this.getTagged().length==0){                                 //I've checked if we should tag anyone or not in this if conditional.
            return "";
        }
        String taggedPeople = "";
        for(int i = 0 ; i < this.getTagged().length ; i++){             //In this for loop, I've concatenated the users that should be tagged.
            String people = this.getTagged()[i];
            if(i!=this.getTagged().length-1){                           //And finally, this conditional is to check if we have someone else to tag
                taggedPeople = taggedPeople.concat(people + ", ");      //or not. Because there won't be a ", " printed if there's no one to tag after.
            }else if(i==this.getTagged().length-1){
                taggedPeople = taggedPeople.concat(people);
            }

        }
        return taggedPeople;
    }

    //Here are our setter and getter methods written, so that we can access attributes of our object because they're private.
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String[] getTagged() {
        return tagged;
    }

    public void setTagged(String[] tagged) {
        this.tagged = tagged;
    }
}