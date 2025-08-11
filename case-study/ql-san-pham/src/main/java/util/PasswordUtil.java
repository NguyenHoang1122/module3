package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    private PasswordUtil() {
    }

    public static String hash(String plain) {
        if (plain == null) return null;
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    public static boolean verify(String plain, String hashed) {
        if (plain == null || hashed == null || hashed.length() == 0) return false;
        return BCrypt.checkpw(plain, hashed);
    }
}
