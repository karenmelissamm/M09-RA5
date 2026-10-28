public class Main {
    public static void main(String[]args) throws Exception{
        String salt="qpoweiruañslkdfjz";
        String pw="aaabF!";
        Hashes h= new Hashes();

        String [] aHashes={h.getSHA512AmbSalt(pw,salt), h.getPBKDF2AmbSalt(pw,salt)};
        String pwTrobat=null;
        String[] algorismes{"SHA-512", "PBKDF2"};

        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("=====================\n");            
            System.out.printf("Algorismes:%s\n", algorismes[i]);
            System.out.printf("Hash:%s\n", aHashes[i]);
            System.out.printf("=====================\n");
            System.out.printf("-- Inici de froça bruta---\n");

            long t1=System.currentTimeMillis();
            pwTrobat=h.forçaBruta(algorismes[i],aHashes[i],salt);
            long t2=System.currentTimeMillis();

            System.out.printf("Pass: %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps: %s\n", h.getInterval(t1,t2));
            System.out.printf("------------------\n\n");
        
        
        }
    }
    
}
