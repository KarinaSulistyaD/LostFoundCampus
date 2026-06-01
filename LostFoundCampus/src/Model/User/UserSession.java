package Model.User;

public final class UserSession {

    private static ModelUser currentUser;

    private UserSession() {
    }

    public static void setCurrentUser(ModelUser user) {
        currentUser = user;
    }

    public static ModelUser getCurrentUser() {
        return currentUser;
    }

    public static int getCurrentUserId() {
        return currentUser == null ? 0 : currentUser.getId();
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static boolean isAdmin() {
        return currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole());
    }

    public static void clear() {
        currentUser = null;
    }
}

