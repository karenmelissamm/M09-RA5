import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monoalfabetic{
     public static final char[] alfabet =
            "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    public static char[] permutacio;
    public static char[] permutaAlfabet(char[] alfa){
        List<Character> lChars = new ArrayList<>();
        for (char c : alfa) lChars.add(c);
        Collections.shuffle(lChars);
        char[] perm = new char[alfa.length];
        for (int i = 0; i < lChars.size(); i++) {
            perm[i] = lChars.get(i);
        }
        return perm;
    }
    public static int posicio(char[] alfa, char c){
        for (int i = 0; i < alfa.length; i++) {
            if (alfa[i] == c) return i;
        }
        return -1;
    }
    public static String substitueix(char[] orig, char[] desti, String msg){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < msg.length(); i++){
            char c = msg.charAt(i);
            boolean isMin = Character.isLowerCase(c);
            int pos = posicio(orig, Character.toUpperCase(c));
            if (pos != -1){
                char cCodif = isMin ? Character.toLowerCase(desti[pos]) : desti[pos];
                sb.append(cCodif);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public static String xifraMonoAlfa(String msg){
        return substitueix(alfabet, permutacio, msg);
    }
    public static String desxifraMonoAlfa(String msgXifrat){
        return substitueix(permutacio, alfabet, msgXifrat);
    }
    public static void mostraAlfabet(char[] alfa){
        for (int i = 0; i < alfa.length; i++) {
            System.out.printf("%c ", alfa[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        permutacio = permutaAlfabet(alfabet);
        mostraAlfabet(alfabet);
        mostraAlfabet(permutacio);

        String msgs[] = {
            "Test 01 àrbitre, coixí, Perímetre",
            "Test 02 Taüll, DÍA, año",
            "Test 03 Peça, Òrrius, Bòvila"
        };

        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:");
        for (int i = 0; i < msgs.length; i++) {
            msgsXifrats[i] = xifraMonoAlfa(msgs[i]);
            System.out.printf("%-35s -> %s%n", msgs[i], msgsXifrats[i]);
        }
        System.out.println("Desxifratge:");
        for (int i = 0; i < msgs.length; i++) {
            String msg = desxifraMonoAlfa(msgsXifrats[i]);
            System.out.printf("%-35s -> %s%n", msgsXifrats[i], msg);
        }
    }
}