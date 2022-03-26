package in.home.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordConverter {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
        System.out.println(new BCryptPasswordEncoder().matches("password",
                "$2a$10$fCiWaEcUiNKDdb7ZSB5i3O6Yel2hOX1FOGTIpstEoEXQLrhw0sxpG"));

        System.out.println("" + (1 + 1));
    }
}
