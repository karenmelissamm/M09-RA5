import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.channels.Pipe.SourceChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];

    private static final String CLAU = "LaClauSecreta01";
   
    public static IvParameterSpec generaIV() {
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    public static IvParameterSpec extreureIv(byte[] bIvIMsgXifrat){
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, iv.length);
        return new IvParameterSpec(iv);

    }
    private static byte[] getBytesXifrats(byte[] bIvIMsgXifrat) {
        int midaXifrat = bIvIMsgXifrat.length - MIDA_IV;
        byte[] bXifrat = new byte[midaXifrat];
        System.arraycopy(bIvIMsgXifrat, MIDA_IV, bXifrat, 0, midaXifrat);
        return bXifrat;
    }
    private static SecretKeySpec generaHash(String clau)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        md.update(clau.getBytes("UTF-8"));

        byte[] bClau = new byte[16]; // 128 bits
        System.arraycopy(md.digest(), 0, bClau, 0, bClau.length);

        return new SecretKeySpec(bClau, ALGORISME_XIFRAT);
    }
    public static byte[] xifraAES(String msg, String clau) throws Exception{
        byte[] bNets = msg.getBytes();

        IvParameterSpec ivPS = generaIV();
        SecretKeySpec secretKeySpec = generaHash(clau);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivPS);

        byte[] bXifrats = cipher.doFinal(bNets);

        byte[] bIVIMsgXifrats = new byte[MIDA_IV + bXifrats.length];
        System.arraycopy(iv, 0, bIVIMsgXifrats, 0, MIDA_IV);
        System.arraycopy(bXifrats, 0, bIVIMsgXifrats, MIDA_IV, bXifrats.length);

        return bIVIMsgXifrats;
    }
    public static String desxifraAES(byte[] bIVIMsgXifrat, String clau) throws Exception{
        IvParameterSpec ivParameterSpec = extreureIv(bIVIMsgXifrat);

        byte[] bXifrat = getBytesXifrats(bIVIMsgXifrat);

        SecretKeySpec secretKeySpec = generaHash(clau);

        Cipher cipherDecrypt = Cipher.getInstance(FORMAT_AES);
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decrypted = cipherDecrypt.doFinal(bXifrat);

        return new String(decrypted);
    }
    public static String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(Character.MAX_RADIX);
    }

    public static byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, Character.MAX_RADIX).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }
    public static void main(String[]args){
        String msgs[] = {"Lorem ipsum dicet", 
        "Hola Andrés cómo está tu cuñado",
        "Àgora ïlla ôtto"};
        for (int i = 0; i < msgs.length; i++) {
            String msg=msgs[i];

            byte[] bXifrats=null;
            String desxifrat="";
            try {
                bXifrats=xifraAES(msg, CLAU);
                desxifrat=desxifraAES(bXifrats, CLAU);
            }catch (Exception e) {
            System.out.println("Error de xifrat: " + e.getLocalizedMessage());
            } 
            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat );   
        }
    }
}
