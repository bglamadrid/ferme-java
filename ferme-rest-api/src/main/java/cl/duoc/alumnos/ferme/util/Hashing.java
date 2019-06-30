package cl.duoc.alumnos.ferme.util;

import cl.duoc.alumnos.ferme.FermeConfig;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public final class Hashing {
    
    private final static String HASHING_ALGORITHM = FermeConfig.ALGORITMO_CRIPTOGRAFICO_GLOBAL;
    
    /**
     * Rutina simple de generación de hash.<br>
     * Obtiene el algoritmo a usar de la constante 'HASHING_ALGORITHM'
     * Créditos a 'user1452273' (stackoverflow.com)
     * @param data Los datos a encriptar.
     * @return El hash, resultando de la encriptación.
     */
    public static String hashear(String data) {
        try{
            MessageDigest procesador = MessageDigest.getInstance(HASHING_ALGORITHM);
            byte[] hashBytes;
            hashBytes = data.getBytes(StandardCharsets.UTF_8);
            hashBytes = procesador.digest(hashBytes);
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hashBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
    
    /**
     * Encripta una cadena de datos de sesión, usando una sal y 
     * el algoritmo estándar seleccionado.
     * @param sessionData El String a encriptar
     * @return Una cadena de datos con el resultado de la encriptación (hash). 
     * @throws NoSuchAlgorithmException Si el algoritmo declarado en la constante 
     * 'DEFAULT_HASHING_ALGORITHM' es inválido.
     */
    public static String encriptarStringSesion(String sessionData) throws NoSuchAlgorithmException {
        String salt = "Wendy Sulca canta mejor que Shakira"; // :O
        String msg = salt + sessionData;
        
        String hash = hashear(msg);
        
        return hash;
    }
}
