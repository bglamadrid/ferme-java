package cl.duoc.alumnos.ferme.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public abstract class Hashing {
    
    /**
     * Rutina simple de generacion de hash.<br>
     * Obtiene el algoritmo a usar de la constante 'HASHING_ALGORITHM'
     * Créditos a 'user1452273' (stackoverflow.com)
     * @param data Los datos a encriptar.
     * @return El hash, resultando de la encriptacion.
     */
    public static String hashear(String data) {
        try{
            MessageDigest procesador = MessageDigest.getInstance(Constantes.ALGORITMO_CRIPTOGRAFICO_GLOBAL);
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
     * Encripta una cadena de datos de sesion, usando una sal y 
     * el algoritmo estandar seleccionado.
     * @param sessionData El String a encriptar
     * @return Una cadena de datos con el resultado de la encriptacion (hash). 
     * @throws NoSuchAlgorithmException Si el algoritmo declarado en la constante 
     * 'DEFAULT_HASHING_ALGORITHM' es invalido.
     */
    public static String encriptarStringSesion(String sessionData) throws NoSuchAlgorithmException {
        
        String salt = "Wendy Sulca canta mejor que Shakira"; // :O
        String msg = salt + sessionData;
        
        return hashear(msg);
    }
}
