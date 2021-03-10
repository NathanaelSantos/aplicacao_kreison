package model;

import java.security.MessageDigest;

public class StringUtil {

    public String gerarHash(String senha) throws Exception {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte hash[] = algorithm.digest(senha.getBytes("UTF-8"));

        StringBuilder texto = new StringBuilder();
        for (byte b : hash) {
            texto.append(String.format("%02x", 0xFF & b));
        }
        return texto.toString();
    }

}
