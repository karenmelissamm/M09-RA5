public class RotX{
    public static final char[] majuscules =
        "AÀÁÂBCÇDEÈÉÊFGHIÏÍÎJKLMNÑOÓÒÔPQRSTUÚÙÜVWXYZ".toCharArray();
    public static final char[] minuscules =
        "aàáâbcçdeèéêfghiïíîjklmnñoóòôpqrstuúùüvwxyz".toCharArray();
    
    public static int posicioEnArray(char[] llista, char c){
        for(int i=0;i<llista.length;i++){
            if(llista[i]==c){
                return i;
            }
        }
        return -1;
    }
 
    public static char xifraCrRotX(char[] llista, int pos, int despla) {
        int posChar = (pos + despla) % llista.length;
        return llista[posChar];
    }
    public static char desxifraCrRotX(char[] llista, int pos, int despla){
        int posChar = ((pos-despla)<0) ?
        (pos-despla) + llista.length:
        (pos-despla);
        return llista[posChar];
    }
    public static String rotX(String msg, boolean dreta, int despla){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<msg.length();i++){
            char orginalC= msg.charAt(i);
            int pos=posicioEnArray(majuscules, orginalC);
            char caracterRot;
            if(pos != -1){
                caracterRot= dreta ? 
                xifraCrRotX(majuscules,pos,despla):
                desxifraCrRotX(majuscules,pos,despla);
            }else {
                pos = posicioEnArray(minuscules, orginalC);
                if(pos != -1) {
                    caracterRot = dreta ?
                    xifraCrRotX(minuscules, pos, despla) :
                    desxifraCrRotX(minuscules, pos, despla);
                }else {
                    caracterRot=orginalC;
                }
            }
            sb.append(caracterRot);
        }
        return sb.toString();
    }
    public static String xifraRotX(String msg, int despla){
        return rotX(msg, true, despla);
    }
    public static String desxifraRotX(String msgAdesXifra,int  despla){
        return rotX(msgAdesXifra,false, despla);
    }
    public static void forcaBrutaRotX(String msgAdesXifra){
        System.out.println("Missatge Xifrat " + msgAdesXifra);
        System.out.println("---------------");
        for(int desp=0; desp<majuscules.length; desp++) {
            System.out.printf("(%d)->%s%n", desp, desxifraRotX(msgAdesXifra, desp));
        }
    }
    public static void main(String[] args) {
        String msgs[] = {"ABC","XYZ","Hola, Mr. calçot","Perdó, per tu què és?"};
        String msgsXifrats[] = new String[msgs.length];
        int desplacament = 7;

        System.out.println("\nXifrat (desplaçament=" + desplacament + ")\n------");
        for(int i=0; i<msgs.length; i++) {
            msgsXifrats[i] = xifraRotX(msgs[i], desplacament);
            System.out.printf("%-23s => %s%n", msgs[i], msgsXifrats[i]);
        }
        System.out.println("\nDesxifrat\n---------");
        for(int i=0; i<msgsXifrats.length; i++) {
            System.out.printf("%-23s => %s%n", msgsXifrats[i], desxifraRotX(msgsXifrats[i], desplacament));
        }
        System.out.println("\nForça bruta sobre: " + msgsXifrats[0]);
        System.out.println("-----------------------------------");
        forcaBrutaRotX(msgsXifrats[0]);
    }
}

