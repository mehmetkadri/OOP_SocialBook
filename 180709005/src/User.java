import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class User{

    //We've defined the attributes of our object here.

    private int userID;
    private String name;
    private String userName;
    private String password;
    private Date dateOfBirth;
    private String school;
    private Date lastLogin;
    private List<User> friends = new ArrayList<User>();
    private List<User> blockedUsers = new ArrayList<User>();
    private List<Post> posts = new ArrayList<Post>();

    //We used a constructor for our object to be able to create new objects by calling this method with it's attributes as parameters.

    public User(String name, String userName, String password, Date dateOfBirth, String school) {
        this.setUserID((MySocialBook.userList.size()+1));
        this.setName(name);
        this.setUserName(userName);
        this.setPassword(password);
        this.setDateOfBirth(dateOfBirth);
        this.setSchool(school);
        MySocialBook.userList.add(this);
    }

    public void signIn(){
        this.lastLogin = getCurrentDate();           //I'm not sure if we were supposed to use this somewhere or not,
    }                                                //but we're taking the last login date and time every time a user has successfully logged in.

    public void showPosts(){
        System.out.println("**************");
        System.out.println(this.getName() + "'s Posts");
        System.out.println("**************");
        for(int i = 0 ; i < posts.size() ; i++){
            System.out.println(posts.get(i));
            System.out.println("--------------");
        }
    }

    public boolean checkFriends(String userName){           //We're iterating through active user's friend list to see
        for(User friend:this.getFriends()){                 //whether the other user is their friend or not.
            if(friend.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public Date getCurrentDate() {                          //I've used date class and it's methods to
        Date current = new Date();                          //hold date information in.
        return current;
    }


    public void removeLastPost(){
        this.getPosts().remove(posts.size()-1);         //This method is written to be able to
    }                                                        //remove the last post the active user uploaded.

    //Here is our method to convert a date saved as String type to a date saved as Date type.

    private static Date stringToDate(String birthsDate)throws Exception {
        Date birth=new SimpleDateFormat("dd/MM/yyyy").parse(birthsDate);
        return birth;
    }

    //Here are our setter and getter methods written, so that we can access attributes of our object because they're private.
    //I've commented out the setter and getter methods that we don't use in our code. I wasn't sure if it was okay for you if I've just deleted them.

    /*public int getUserID() {
        return userID;
    }*/

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        String dateString=new SimpleDateFormat("dd/MM/yyyy").format(this.dateOfBirth);
        return dateString;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    /*public Date getLastLogin() {
        return lastLogin;
    }*/

    /*public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }*/

    public List<User> getFriends() {
        return friends;
    }

    /*public void setFriends(List<User> friends) {
        this.friends = friends;
    }*/

    public void addFriendList(User friend){
        this.friends.add(friend);
    }

    public void removeFriendList(User friend){
        this.friends.remove(friend);
    }

    public List<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void addBlockedList(User blocked){
        this.blockedUsers.add(blocked);
    }

    public void removeBlockedList(User blocked){
        this.blockedUsers.remove(blocked);
    }

    /*public void setBlockedUsers(List<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }*/

    public List<Post> getPosts() {
        return posts;
    }

    /*public void setPosts(List<Post> posts) {
        this.posts = posts;
    }*/

    //Here we override toString method to be able to print the object's attributes as we please.

    @Override
    public String toString(){
        return "Name: " + this.getName() + "\nUsername: " + this.getUserName() + "\nDate of Birth: " + this.getDateOfBirth() + "\nSchool: " + this.getSchool();
    }
}