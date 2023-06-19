import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class MySocialBook {

    //I've used an array list to hold users' information
    public static List<User> userList = new ArrayList<User>();

    public static void main(String args[]) throws Exception {

        //This is the file that is holding the users' information.
        File users = new File(args[0]);

        Scanner userScanner = new Scanner(users);

        //Inside this while loop, we are creating new users according to the data inside users.txt
        //Since we're adding users to userList while creating new users, we don't have to add them again in any other place.
        while (userScanner.hasNextLine()) {
            String[] userInfo = userScanner.nextLine().split("\t");
            User temp = new User(userInfo[0],userInfo[1],userInfo[2],stringToDate(userInfo[3]),userInfo[4]);
        }
        userScanner.close();

        //This is the file that is holding the commands for us to apply.
        File commandsFile = new File(args[1]);

        Scanner commands = new Scanner(commandsFile);

        //We initialize important variables outside of the loop since they should only
        //change under specific circumstances and should be initialized before that.
        boolean signedIn = false;
        User activeUser = null;
        boolean userNotFound;
        boolean alreadyFriends;
        boolean userExists;
        boolean userUnblocked;
        boolean friendRemoved;
        boolean alreadyBlocked;
        boolean userBlocked;

        while (commands.hasNextLine()) {
            System.out.println("---------------------------");                      //The lines that are separating different command outputs.
            String[] line = commands.nextLine().split("\t");
            String commandName = line[0];

            System.out.print("Command: ");                                          //This prints out the command that is given to us
            for(int i = 0 ; i < line.length ; i++){                                 //in the currently read line.
                System.out.print(line[i] + "\t");
            }
            System.out.println("");

            //I've used switch/case to act according to which command is given in the beginning of the line.
            
            switch(commandName){
                case "ADDUSER":
                    String dateString = line[4];

                    Date date = stringToDate(dateString);

                    User newUser = new User(line[1],line[2],line[3],date,line[5]);
                    System.out.println( line[1] + " has been successfully added.");
                    break;
                case "REMOVEUSER":
                    if(userList.get(Integer.parseInt(line[1])-1)!=null){
                        userList.remove(Integer.parseInt(line[1])-1);
                        System.out.println( "User has been successfully removed.");
                    }else{
                        System.out.println("No such user!");
                    }
                    break;
                case "SIGNIN":
                    if(!signedIn) {
                        for (User a : userList) {
                            if (a.getUserName().equals(line[1]) && a.getPassword().equals(line[2])) {
                                System.out.println("You have successfully signed in.");
                                signedIn = true;
                                activeUser = a;
                                activeUser.signIn();
                            }
                        }
                        if (!signedIn) {
                            System.out.println("Invalid username or password! Please try again.");
                        }
                    }else{
                        System.out.println("Error: Please sign out and try again.");
                    }
                    break;
                case "LISTUSERS":
                    if(signedIn) {
                        for (int i = 0; i < userList.size(); i++) {
                            System.out.println(userList.get(i));
                            System.out.println("---------------------------");
                        }
                    }
                    break;
                case "UPDATEPROFILE":
                    if(signedIn){
                        activeUser.setName(line[1]);
                        Date updateDate = stringToDate(line[2]);
                        activeUser.setDateOfBirth(updateDate);
                        activeUser.setSchool(line[3]);
                        System.out.println("Your user profile has been successfully updated.");
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }

                    break;
                case "CHPASS":
                    if(signedIn){
                        if(line[1].equals(activeUser.getPassword())){
                            activeUser.setPassword(line[2]);
                            System.out.println("Your password has been successfully updated");
                        }else{
                            System.out.println("Password mismatch!");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "ADDFRIEND":
                    if(signedIn) {
                        userNotFound = true;
                        alreadyFriends = false;
                        for (User userExist : userList) {
                            if (userExist.getUserName().equals(line[1])) {
                                if (activeUser.getFriends() != null) {
                                    for (User friend : activeUser.getFriends()) {
                                        if (friend.getUserName().equals(line[1])) {
                                            System.out.println("This user is already in your friend list.");
                                            alreadyFriends = true;
                                        }
                                    }
                                }
                                if (!alreadyFriends) {
                                    for (User friend : userList) {
                                        if (friend.getUserName().equals(line[1])) {
                                            activeUser.addFriendList(friend);
                                            System.out.println(friend.getUserName() + " has been successfully added to your friend list.");
                                        }
                                    }
                                }
                                userNotFound = false;
                            }
                        }
                        if (userNotFound) {
                            System.out.println("No such user!");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "REMOVEFRIEND":
                    if(signedIn) {
                        userNotFound = true;
                        friendRemoved = false;
                        for (User userExist : userList) {
                            if (userExist.getUserName().equals(line[1])) {
                                userNotFound = false;
                                if (activeUser.getFriends() != null) {
                                    for (int i = 0; i < activeUser.getFriends().size(); i++) {
                                        User friend = activeUser.getFriends().get(i);
                                        if (friend.getUserName().equals(line[1])) {
                                            System.out.println(friend.getUserName() + " has been successfully removed from your friend list.");
                                            activeUser.removeFriendList(friend);
                                            friendRemoved = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (!userNotFound && !friendRemoved) {
                            System.out.println("No such friend!");
                        } else if (userNotFound) {
                            System.out.println("No such user!");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "LISTFRIENDS":
                    if(signedIn) {
                        if(!activeUser.getFriends().isEmpty()) {
                            for (User friend : activeUser.getFriends()) {
                                System.out.println(friend);
                                System.out.println("---------------------------");
                            }
                        }else{
                            System.out.println("You have not added any friend yet!");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "ADDPOST-TEXT":
                    if(signedIn) {
                        Location textLocation = new Location(Double.parseDouble(line[2]), Double.parseDouble(line[3]));
                        String[] taggedPeopleText = line[4].split(":");
                        List<String> taggedFriendsText = new ArrayList<String>();
                        List<String> notFriends = new ArrayList<String>();
                        for (int i = 0; i < taggedPeopleText.length; i++) {
                            if (activeUser.checkFriends(taggedPeopleText[i])) {
                                for(int j = 0; j < userList.size() ; j++) {
                                    if (userList.get(j).getUserName().equals(taggedPeopleText[i])) {
                                        taggedFriendsText.add(userList.get(j).getName());
                                    }
                                }
                            } else {
                                if (!notFriends.contains(taggedPeopleText[i])) {
                                    notFriends.add(taggedPeopleText[i]);
                                }
                            }
                        }

                        for (String notFriend : notFriends) {
                            System.out.println(notFriend + " is not your friend, and will not be tagged!");
                        }
                        String[] taggedFriendsTextArray = taggedFriendsText.toArray(new String[taggedFriendsText.size()]);
                        Post textPost = new TextPost(line[1], textLocation, taggedFriendsTextArray);
                        activeUser.getPosts().add(textPost);
                        System.out.println("The post has been successfully added.");
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "ADDPOST-IMAGE":
                    if(signedIn) {
                        Location imageLocation = new Location(Double.parseDouble(line[2]), Double.parseDouble(line[3]));
                        String[] taggedPeopleImage = line[4].split(":");
                        List<String> taggedFriendsImage = new ArrayList<String>();
                        List<String> notFriendsImage = new ArrayList<String>();
                        for (int i = 0; i < taggedPeopleImage.length; i++) {
                            if (activeUser.checkFriends(taggedPeopleImage[i])) {
                                for(int j = 0; j < userList.size() ; j++){
                                    if(userList.get(j).getUserName().equals(taggedPeopleImage[i])){
                                        taggedFriendsImage.add(userList.get(j).getName());
                                    }
                                }
                            } else {
                                if (!notFriendsImage.contains(taggedPeopleImage[i])) {
                                    if (!notFriendsImage.contains(taggedPeopleImage[i])) {
                                        notFriendsImage.add(taggedPeopleImage[i]);
                                    }
                                }
                            }
                        }

                        for (String notFriend : notFriendsImage) {
                            System.out.println(notFriend + " is not your friend, and will not be tagged!");
                        }
                        String[] taggedFriendsImageArray = taggedFriendsImage.toArray(new String[taggedFriendsImage.size()]);
                        Post imagePost = new ImagePost(line[1], imageLocation, taggedFriendsImageArray, line[5], line[6]);
                        activeUser.getPosts().add(imagePost);
                        System.out.println("The post has been successfully added.");
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "ADDPOST-VIDEO":
                    if(signedIn) {
                        Location videoLocation = new Location(Double.parseDouble(line[2]), Double.parseDouble(line[3]));
                        String[] taggedPeopleVideo = line[4].split(":");
                        List<String> taggedFriendsVideo = new ArrayList<String>();
                        List<String> notFriendsVideo = new ArrayList<String>();
                        for (int i = 0; i < taggedPeopleVideo.length; i++) {
                            if (activeUser.checkFriends(taggedPeopleVideo[i])) {
                                for(int j = 0; j < userList.size() ; j++){
                                    if(userList.get(j).getUserName().equals(taggedPeopleVideo[i])){
                                        taggedFriendsVideo.add(userList.get(j).getName());
                                    }
                                }
                            } else {
                                if (!notFriendsVideo.contains(taggedPeopleVideo[i])) {
                                    if (!notFriendsVideo.contains(taggedPeopleVideo[i])) {
                                        notFriendsVideo.add(taggedPeopleVideo[i]);
                                    }
                                }
                            }
                        }

                        for (String notFriend : notFriendsVideo) {
                            System.out.println(notFriend + " is not your friend, and will not be tagged!");
                        }

                        String[] taggedFriendsVideoArray = taggedFriendsVideo.toArray(new String[taggedFriendsVideo.size()]);
                        if (Double.parseDouble(line[6]) < VideoPost.getMaxDuration()) {
                            Post videoPost = new VideoPost(line[1], videoLocation, taggedFriendsVideoArray, line[5], Double.parseDouble(line[6]));
                            activeUser.getPosts().add(videoPost);
                            System.out.println("The post has been successfully added.");
                        } else {
                            System.out.println("Error: Your video exceeds maximum allowed duration of 10 minutes.");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "REMOVELASTPOST":
                    if(signedIn) {
                        if(!activeUser.getPosts().isEmpty()) {
                            activeUser.removeLastPost();
                            System.out.println("Your last post has been successfully removed.");
                        }else{
                            System.out.println("Error: You do not have any post.");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "SHOWPOSTS":
                    for(int i = 0 ; i < userList.size() ; i++){
                        if(line[1].equals(userList.get(i).getUserName())){
                            userList.get(i).showPosts();
                        }
                    }
                    break;
                case "BLOCK":
                    if(signedIn) {
                        alreadyBlocked = false;
                        userBlocked = false;
                        if (activeUser.getBlockedUsers() != null) {
                            for (int i = 0; i < activeUser.getBlockedUsers().size(); i++) {
                                User block = activeUser.getBlockedUsers().get(i);
                                if (block.getUserName().equals(line[1])) {
                                    activeUser.removeBlockedList(block);
                                    System.out.println("This user is already blocked.");
                                    alreadyBlocked = true;
                                }
                            }
                        }
                        if (!alreadyBlocked) {
                            for (User block : userList) {
                                if (block.getUserName().equals(line[1])) {
                                    activeUser.addBlockedList(block);
                                    System.out.println(block.getUserName() + " has been successfully blocked.");
                                    userBlocked = true;
                                }
                            }
                        }
                        if (!alreadyBlocked && !userBlocked) {
                            System.out.println("No such user!");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "SHOWBLOCKEDFRIENDS":
                    if(signedIn) {
                        if(!activeUser.getBlockedUsers().isEmpty()){
                            for (User blocked : activeUser.getBlockedUsers()) {
                                for (User friend : activeUser.getFriends()) {
                                    if (blocked.getUserName().equals(friend.getUserName())) {
                                        System.out.println(blocked);
                                        System.out.println("---------------------------");
                                    }
                                }
                            }
                        }else{
                            System.out.println("You haven't blocked any friend yet!");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "UNBLOCK":
                    if(signedIn) {
                        userExists = false;
                        userUnblocked = false;

                        for (User block : userList) {
                            if (block.getUserName().equals(line[1])) {
                                userExists = true;
                            }
                        }

                        if (activeUser.getBlockedUsers() != null) {
                            for (int i = 0; i < activeUser.getBlockedUsers().size(); i++) {
                                User block = activeUser.getBlockedUsers().get(i);
                                if (block.getUserName().equals(line[1])) {
                                    System.out.println(block.getUserName() + " has been successfully unblocked.");
                                    activeUser.removeBlockedList(block);
                                    userUnblocked = true;
                                }
                            }
                        }

                        if (userExists && !userUnblocked) {
                            System.out.println("No such user in your blocked-user list!");
                        }
                        if (!userExists) {
                            System.out.println("No such user!");
                        }
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                case "SHOWBLOCKEDUSERS":
                    if(signedIn) {
                        if(!activeUser.getBlockedUsers().isEmpty()){
                            for (User blocked : activeUser.getBlockedUsers()) {
                                System.out.println(blocked);
                                System.out.println("---------------------------");
                            }
                        }else{
                            System.out.println("You haven't blocked any user yet!");
                        }
                    }
                    break;
                case "SIGNOUT":
                    if(signedIn) {
                        signedIn = false;
                        activeUser = null;
                        System.out.println("You have successfully signed out.");
                    }else{
                        System.out.println("Error: Please sign in and try again.");
                    }
                    break;
                default:
                    break;
            }
        }
        commands.close();
    }

    //Here is our method to convert a date saved as String type to a date saved as Date type.
    //The difference between this method and same named method in User class is that this method
    //is supposed to hold only day, month and year.
    //Also since the order is different, there were some issues, so I've fixed them by re-ordering our string first.
    private static Date stringToDate(String birthsDate)throws Exception {
        String day = birthsDate.substring(3,5) ;
        String month = birthsDate.substring(0,2) ;
        String year = birthsDate.substring(6,10) ;
        String formattedDate = day.concat("/".concat(month.concat("/".concat(year))));
        Date birth=new SimpleDateFormat("dd/MM/yyyy").parse(formattedDate);
        return birth;
    }
}