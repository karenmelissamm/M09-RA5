import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetic {
     public static final char[] ALFABET =
            "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    public static char[] PERMUTACIO;
    private static Random rand;
    private static long clauSecreta = 123456;

    public static void initRandom(long clauSecreta){
        rand=new Random(clauSecreta);
    }
    public static void permutaAlfabet(){
        List<Character> permutat = new ArrayList<>();
        for (char c : ALFABET) permutat.add(c);
        
        Collections.shuffle(permutat,rand);

        PERMUTACIO = new char[ALFABET.length];
        for (int i = 0; i < permutat.size(); i++) {
            PERMUTACIO[i] = permutat.get(i);
        }
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
    public static String xifraPoliAlfa(String msg){
        permutaAlfabet();
        return substitueix(ALFABET, permutacio, msg);
    }
    public static String desxifraPoliAlfa(String msgXifrat){
        permutaAlfabet();
        return substitueix(permutacio, ALFABET, msgXifrat);
    }
   
    public static void main(String[] args){

        String msgs[] = {
            "Test 01 Arbitre, coixi, Perímetre",
            "Test 02 Taüll, Día, año",
            "Test 03 Peça, Òrrius, Bòvila"
        };

        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n----------");
        for (int i = 0; i < msgs.length; i++){
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("\nDesxifratge:\n-----------");
        for (int i = 0; i < msgs.length; i++){
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }
        
}
