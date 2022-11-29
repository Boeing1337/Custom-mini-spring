package context;

import dao.UserEntity;

public class GlobalVariable {


    public static UserEntity currentUser;

    public static UserEntity getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserEntity user) {
        currentUser = user;
    }
}
