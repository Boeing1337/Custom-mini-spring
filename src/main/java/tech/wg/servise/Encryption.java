package tech.wg.servise;

import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Log4j2
@Component
public class Encryption {
    public String action(String pass) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] bytes = sha1.digest(pass.getBytes());
            for (byte b : bytes) {
                builder.append(String.format("%01x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
            throw new IllegalStateException("Нет нужного алгоритма", e);
        }
        return builder.toString();
    }
}

