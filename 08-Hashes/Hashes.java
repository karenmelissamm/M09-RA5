import java.security.MessageDigest;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    public int npass=0;
    public String getSHA512AmbSalt(String pw, String salt) throws Exception{
        MessageDigest messageDigest= MessageDigest.getInstance("SHA-512");
        messageDigest.update(salt.getBytes());
        byte[] bytes= messageDigest.digest(pw.getBytes());

        HexFormat hex=HexFormat.of();
        return hex.formatHex(bytes);
    }
    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception{
        PBEKeySpec skPbeKeySpec= new PBEKeySpec(pw.toCharArray(),salt.getBytes(), 65536, 128);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] bytes= secretKeyFactory.generateSecret(skPbeKeySpec).getEncoded();

        return HexFormat.of().formatHex(bytes);
    }
// Calcula el hash de una prueba, usando SHA-512 o PBKDF2 según "algo de texto" si
// es igual nos devuelve la contraseña
    private String calculoDeHash(String alg, String prova, String salt) throws Exception {
        if (alg.equals("SHA-512")) {
            return getSHA512AmbSalt(prova, salt);
        }else {
            return getPBKDF2AmbSalt(prova, salt);
        }
    }
    public String forcaBruta(String alg, String hash, String salt) throws Exception{
        String charset = "abcdefABCDEF1234567890!";
        char[] pw = new char[6];
        npass=0;
        for (int i = 0; i < charset.length(); i++) {
            pw[0] = charset.charAt(i);
            String prova = new String(pw, 0, 1);
            npass++;
            if (calculoDeHash(alg, prova, salt).equals(hash)){
                return prova;
            }
            for (int j = 0; j < charset.length(); j++) {
                pw[1] = charset.charAt(j);
                prova = new String(pw, 0, 2);
                npass++;
                if (calculoDeHash(alg, prova, salt).equals(hash)){
                    return prova;
                }
                for (int k = 0; k < charset.length(); k++) {
                    pw[2] = charset.charAt(k);
                    prova = new String(pw, 0, 3);
                    npass++;
                    if (calculoDeHash(alg, prova, salt).equals(hash)){
                        return prova;
                    }
                    for (int l = 0; l < charset.length(); l++) {
                        pw[3] = charset.charAt(l);
                        prova = new String(pw, 0, 4);
                        npass++;
                        if (calculoDeHash(alg, prova, salt).equals(hash)){
                            return prova;
                        }
                        for (int m = 0; m < charset.length(); m++) {
                            pw[4] = charset.charAt(m);
                            prova = new String(pw, 0, 5);
                            npass++;
                            if (calculoDeHash(alg, prova, salt).equals(hash)){
                                return prova;
                            }
                            for (int n = 0; n < charset.length(); n++) {
                                pw[5] = charset.charAt(n);
                                prova = new String(pw, 0, 6);
                                npass++;
                                if (calculoDeHash(alg, prova, salt).equals(hash)){
                                   return prova; 
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    public String getInterval(long t1, long t2){
        long diff = t2 - t1;
        long millis = diff % 1000;
        diff /= 1000;
        long segons = diff % 60;
        diff /= 60;
        long minuts = diff % 60;
        diff /= 60;
        long hores = diff % 24;
        diff /= 24;
        long dies = diff;
        return dies + " dies / " + hores + " hores /" + minuts+" minuts /"+ segons + " segons /"+ millis +" millis";   
    }
public static void main(String[]args) throws Exception{
        String salt="qpoweiruañslkdfjz";
        String pw="aaabF!";
        Hashes h= new Hashes();

        String [] aHashes={h.getSHA512AmbSalt(pw,salt), h.getPBKDF2AmbSalt(pw,salt)};
        String pwTrobat=null;
        String[] algorismes={"SHA-512", "PBKDF2"};

        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("=====================\n");            
            System.out.printf("Algorismes:%s\n", algorismes[i]);
            System.out.printf("Hash:%s\n", aHashes[i]);
            System.out.printf("=====================\n");
            System.out.printf("-- Inici de froça bruta---\n");

            long t1=System.currentTimeMillis();
            pwTrobat=h.forcaBruta(algorismes[i],aHashes[i],salt);
            long t2=System.currentTimeMillis();

            System.out.printf("Pass: %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps: %s\n", h.getInterval(t1,t2));
            System.out.printf("------------------\n\n"); 
        }
    }
}
