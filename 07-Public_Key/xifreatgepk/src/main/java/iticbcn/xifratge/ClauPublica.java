package iticbcn.xifratge;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class ClauPublica {
    public static void main(String[] args) throws Exception {
        ClauPublica cp= new ClauPublica();
        String msg="Missatge de prova per xifrar áéíóú àèìòù";
        KeyPair parellClaus=cp.generaParellClausRSA();
        byte[] msgXifrat=cp.xifraRSA(msg, parellClaus.getPublic());
        System.out.println("=========================================");
        System.out.println("Text xifrat: ");
        System.out.println(DatatypeConverter.printHexBinary(msgXifrat));
        
        String msgDesxifrat = cp.desxifratRSA(msgXifrat,parellClaus.getPrivate());
        System.out.println("=========================================");
        System.out.println("Text desxifrat: "+ msgDesxifrat);
        
        String strClauPub=DatatypeConverter.printHexBinary(parellClaus.getPublic().getEncoded());
        String strClauPriv=DatatypeConverter.printHexBinary(parellClaus.getPrivate().getEncoded());

        System.out.println("==========================================");
        System.out.println("Clau pública: " + strClauPub);
        System.out.println("==========================================");
        System.out.println("Clau privada: "+ strClauPriv);
    }
    //metodes a crear
    public KeyPair generaParellClausRSA()throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair pair=keyPairGenerator.generateKeyPair();
        return  pair;
    }
    
    public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception{
        Cipher encrypteCipher=Cipher.getInstance("RSA");
        encrypteCipher.init(Cipher.ENCRYPT_MODE, clauPublica);
        //aqui esta el mensaje secreto
        byte[] msgSecret=msg.getBytes(StandardCharsets.UTF_8);
        //aqui se cifra el mensaje
        byte[] msgEncrypte=encrypteCipher.doFinal(msgSecret);
        // devolvemos el mensaje cifrado 
        return  msgEncrypte;
    }
    public String desxifraRSA(byte[]msgXifrat, PrivateKey ClauPrivada){
        
    }
}
