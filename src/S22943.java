import java.io.*;
import java.util.Random;
import java.util.stream.StreamSupport;

public class S22943 {
    public static void main(String[] args) {

        File file = new File("kontenery.txt");
        Kontener[] konteners;
        Generator generator = new Generator();
        PrintStream fileStream = null;
        try {
            fileStream = new PrintStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //fileStream.println("TYPE | ID | MASS | LENGTH | WIDTH | HEIGHT | CARGO | TEMP/CAPACITY"); //psuje program ERROR
        for (int i = 0; i < 15000; i++) {
            Kontener kontener = generator.generate();
            fileStream.println(kontener.getClass().getName() + " " + kontener);  //generowanie pliku z kontenerami
        }
        fileStream.close();

        GetFromTXT getFromTXT = new GetFromTXT();
        konteners = getFromTXT.getFromFile(String.valueOf(file));

        SortT sortT = new SortT();
        Ship ship = new Ship();
        Kontener[] sort_konteners = sortT.sortM(konteners); //sortowanie tablicy po masie
        Kontener[][][] load_konteners = ship.load(sort_konteners); //ładowanie statku

        try {
            fileStream = new PrintStream(new File("manifest.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

      for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 24; j++) {
                for (int k = 0; k < 48; k++) {
                    if(ship.load(sort_konteners)[i][j][k]!=null) fileStream.println(load_konteners[i][j][k].getId() + "\t" + i + " " + j + " " + k + "\t" + load_konteners[i][j][k].getMass() + "\t" + load_konteners[i][j][k].getCargo() );

                    }

                 }

       }
        fileStream.close();

      System.out.println("Done"); //informuje ze skończył
      // System.out.println(file.getAbsolutePath()); //w celu znalezienia pliku po wygenerowaniu

    }
}

class Generator {

    private int id=0;
    Random random = new Random();

    String[] dry_van_1 = {"Palety", "Kartony", "Skrzynie"};
    String[] dry_van_2 = {"_paczek_od_Chińczyka", "_części_samochodowych", "_ubrań", "_zabawek"};

    String[] reefer_1 = {"Palety", "Kartony", "Skrzynie"};
    String[] reefer_2 = {"_owoców", "_mrożonek", "_mięsa", "_szczepionek"};

    String[] opentop = {"Żwir", "Piasek", "Węgiel"};

    String[] tank = {"Ropa", "Benzyna", "Kwas", "Woda"};

    String[] flat = {"Samochody", "Koparki", "Sprzęt wojskowy"};

    public Kontener generate(){
        int number = random.nextInt(13);
        float mass;
        float m1;
        String cargo;

        switch (number) {
            case 0: {
                cargo = dry_van_1[random.nextInt(3)] + dry_van_2[random.nextInt(4)];
                m1 = random.nextFloat() * 30130;
                mass = m1 < 2370 ? 2370 : m1;
                return new K_20DV(id++, mass, cargo);
            }
            case 1: {
                cargo = dry_van_1[random.nextInt(3)] + dry_van_2[random.nextInt(4)];
                m1 = random.nextFloat() * 28520;
                mass = m1 < 3980 ? 3980: m1;
                return new K_40DV(id++, mass, cargo);
            }
            case 2: {
                cargo = dry_van_1[random.nextInt(3)] + dry_van_2[random.nextInt(4)];
                m1 = random.nextFloat() * 28490;
                mass = m1 < 4010 ? 4010 : m1;
                return new K_40HC(id++, mass, cargo);
            }
            case 3:{
                cargo = dry_van_1[random.nextInt(3)] + dry_van_2[random.nextInt(4)];
                m1 = random.nextFloat() * 29900;
                mass = m1 < 4100 ? 4100 : m1;
                return new K_40PW(id++, mass, cargo);
            }
            case 4: {
                cargo = opentop[random.nextInt(3)];
                m1 = random.nextFloat() * 30250;
                mass = m1 < 2250 ? 2250 : m1;
                return new K_20OT(id++, mass, cargo);
            }
            case 5: {
                cargo = opentop[random.nextInt(3)];
                m1 = random.nextFloat() * 28450;
                mass = m1 < 4050 ? 4050 : m1;
                return new K_40OT(id++, mass, cargo);
            }
            case 6: {
                cargo = dry_van_1[random.nextInt(3)] + dry_van_2[random.nextInt(4)];
                m1 = random.nextFloat() * 27890;
                mass = m1 < 2590 ? 2590 : m1;
                return new K_20HT(id++, mass, cargo);
            }
            case 7: {
                cargo = dry_van_1[random.nextInt(3)] + dry_van_2[random.nextInt(4)];
                m1 = random.nextFloat() * 25780;
                mass = m1 < 4700 ? 4700 : m1;
                return new K_40HT(id++, mass, cargo);
            }
            case 8: {
                cargo = reefer_1[random.nextInt(3)] + reefer_2[random.nextInt(4)];
                m1 = random.nextFloat() * 27575;
                mass = m1 < 2905 ? 2905 : m1;
                int temp = random.nextInt(105)-65;
                return new K_20RF(id++, mass, cargo, temp);
            }
            case 9: {
                cargo = reefer_1[random.nextInt(3)] + reefer_2[random.nextInt(4)];
                m1 = random.nextFloat() * 29400;
                mass = m1 < 4600 ? 4600 : m1;
                int temp = random.nextInt(105)-65;
                return new K_40RF(id++, mass, cargo, temp);
            }
            case 10: {
                cargo = flat[random.nextInt(3)];
                m1 = random.nextFloat() * 37060;
                mass = m1 < 2940 ? 2940 : m1;
                return new K_20FR(id++, mass, cargo);
            }
            case 11: {
                cargo = flat[random.nextInt(3)];
                m1 = random.nextFloat() * 26280;
                mass = m1 < 4200 ? 4200 : m1;
                return new K_40FR(id++, mass, cargo);
            }
            case 12: {
                cargo = tank[random.nextInt(4)];
                m1 = random.nextFloat() * 32580;
                mass = m1 < 3385 ? 3385 : m1;
                float capacity = Math.max(random.nextFloat() * 24000, 24000 * 0.8f);
                return new K_20TK(id++, mass, cargo, capacity);
            }
        }

        return new Kontener();
    }


} // klasa generatora kontenerów
class GetFromTXT {

    public Kontener[] getFromFile(String file) {
        Kontener[] konteners = new Kontener[15000];
        int i;
        int j = 0;
        int spaceN = 0;
        String text = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((i = bufferedReader.read()) != -1) {
                if (spaceN == 0) {
                    spaceN = 0;
                    konteners[j] = new Kontener();
                }
                if (spaceN == 1 && i != ' ') {
                    text += (char) i;
                }
                if (i == ' ' && spaceN == 1) {
                    konteners[j].setId(Integer.parseInt(text));
                }
                if (spaceN == 2 && i != ' ') {
                    text += (char) i;
                }
                if (i == ' ' && spaceN == 2) {
                    konteners[j].setMass(Float.parseFloat(text));
                }
                if (spaceN == 6 && i != '\r') {
                    text += (char) i;
                }
                if ((i == '\r' || i==' ') && spaceN == 6) {
                    konteners[j].setCargo(text);
                }
                if (i == ' ') {
                    spaceN++;
                    text = "";
                }
                if (i == '\n') {
                    j++;
                    spaceN=0;
                    text = "";
                }

            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }


        return konteners;
    }
} // klasa wyciągająca kontenery z pliku
class K_20DV extends Kontener {

    private static int length = 6058;
    private static int width = 2438;
    private static int height = 2591;

    public K_20DV(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}  // klasy kontenerów \/
class K_20FR extends Kontener {

    private static int length = 6058;
    private static int width = 2438;
    private static int height = 2591;

    public K_20FR(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}
class K_20HT extends Kontener {

    private static int length = 6058;
    private static int width = 2438;
    private static int height = 2591;

    public K_20HT(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}
class K_20OT extends Kontener {

    private static int length = 6058;
    private static int width = 2438;
    private static int height = 2591;
    public K_20OT(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}
class K_20RF extends Kontener {

    private static int length = 6058;
    private static int width = 2438;
    private static int height = 2591;
    private int temp;

    public K_20RF(int id, float mass, String cargo, int temp) {
        super(id, mass, length, width, height, cargo);
        this.temp = temp;
    }

    @Override
    public String toString() {
        return super.toString() + " " + temp;
    }
}
class K_20TK extends Kontener {

    private static int length = 6058;
    private static int width = 2438;
    private static int height = 2591;
    private float capacity;

    public K_20TK(int id, float mass, String cargo, float capacity) {
        super(id, mass, length, width, height, cargo);
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return super.toString() + " " + capacity;
    }
}
class K_40DV extends Kontener {

    private static int length = 12192;
    private static int width = 2438;
    private static int height = 2591;

    public K_40DV(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}
class K_40FR extends Kontener {

    private static int length = 12192;
    private static int width = 2438;
    private static int height = 2591;

    public K_40FR(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}
class K_40HC extends Kontener {

    private static int length = 12192;
    private static int width = 2438;
    private static int height = 2591;

    public K_40HC(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}
class K_40HT extends Kontener {

    private static int length = 12192;
    private static int width = 2438;
    private static int height = 2591;

    public K_40HT(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}
class K_40OT extends Kontener {

    private static int length = 12192;
    private static int width = 2438;
    private static int height = 2591;

    public K_40OT(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);
    }
}
class K_40PW extends Kontener {

    private static int length = 12192;
    private static int width = 2438;
    private static int height = 2591;

    public K_40PW(int id, float mass, String cargo) {
        super(id, mass, length, width, height, cargo);;
    }
}
class K_40RF extends Kontener {

    private static int length = 12192;
    private static int width = 2438;
    private static int height = 2591;
    private int temp;

    public K_40RF(int id, float mass, String cargo, int temp) {
        super(id, mass, length, width, height, cargo);
        this.temp = temp;
    }

    @Override
    public String toString() {
        return super.toString() + " " + temp;
    }
}  // klasy kontenerów /\
class Kontener {

    private int id;
    private float mass;
    private int length;
    private int width;
    private int height;
    private String cargo;

    public Kontener(){}

    public Kontener(int id, float mass, int length, int width, int height, String cargo) {
        this.id = id;
        this.mass = mass;
        this.length = length;
        this.width = width;
        this.height = height;
        this.cargo = cargo;
    }

    public float getMass() {
        return mass;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return (id + " " + mass + " " + length + " " + width + " " + height + " " + cargo);
    }
} // klasa kontenera ogólnego
class Ship {
    //Kontenerowiec  24 rzędy po 24 kontenery na 20 kontenerów wysokości = 24x24x20 = 11520 x 40'
    private Kontener[][][] loadspace = new Kontener[20][24][48];

    public Kontener[][][] load(Kontener[] konteners){

        //test

        int licznik1=0;
        int licznik2=0;
        int licznik3=0;
        int licznik4=0;

        float sred1 =0;
        float sred2 =0;
        float sred3 =0;
        float sred4 =0;



        int k1 =0;
        k1:
        for (int k = 0; k < 20; k++) {
            for (int i = 11; i >= 0; i--) {
                for (int j = 24; j < 48; j++) {
                    if(k1 >=15000) break k1;
                    loadspace[k][i][j] = konteners[k1];
                    sred1+= konteners[k1].getMass();
                    licznik1++;
                    if(i%2==1) {
                        if (k1 % 2 == 0) k1 = k1 + 7;
                        else k1 = k1 + 1;
                    }
                    if(i%2==0) {
                        if (k1 % 2 == 0) k1 = k1 + 3;
                        else k1 = k1 + 5;
                    }



                }
                //    k1 = k1 + 3;
            }
        } // Pierwsza cwiartka

        int k2 = 2;
        k2:
        for (int k = 0; k < 20; k++) {
            for (int i = 12; i < 24; i++) {
                for (int j = 24; j < 48; j++) {
                    if(k2 >=15000) break k2;
                    loadspace[k][i][j] = konteners[k2];
                    sred2+= konteners[k2].getMass();;
                    licznik2++;
                    if(i%2==1) {
                        if (k2 % 2 == 0) k2 = k2 + 7;
                        else k2 = k2 + 1;
                    }
                    if(i%2==0) {
                        if (k2 % 2 == 0) k2 = k2 + 3;
                        else k2 = k2 + 5;
                    }
                }
                //   k2 = k2 + 3;
            }
        }// druga cwiartka

        int k3 = 1;
        k3:
        for (int k = 0; k < 20; k++) {
            for (int i = 12; i < 24; i++) {
                for (int j = 23; j >= 0; j--) {
                    if(k3 >=15000) break k3;
                    loadspace[k][i][j] = konteners[k3];
                    sred3+= konteners[k3].getMass();
                    licznik3++;
                    if(i%2==1) {
                        if (k3 % 2 == 0) k3 = k3 + 1;
                        else k3 = k3 + 7;
                    }
                    if(i%2==0) {
                        if (k3 % 2 == 0) k3 = k3 + 3;
                        else k3 = k3 + 5;
                    }
                }
                //   k3 = k3 + 5;
            }
        } // trzecia cwiartka

        int k4 = 3;
        k4:
        for (int k = 0; k < 20; k++) {
            for (int i = 11; i >= 0; i--) {
                for (int j = 23; j >= 0; j--) {
                    if(k4>=15000) break k4;
                    loadspace[k][i][j] = konteners[k4];
                    sred4+= konteners[k4].getMass();
                    licznik4++;
                    if(i%2==1) {
                        if (k4 % 2 == 1) k4 = k4 + 1;
                        else k4 = k4 + 7;
                        ;
                    }
                    if(i%2==0) {
                        if (k4 % 2 == 0) k4 = k4 + 3;
                        else k4 = k4 + 5;
                    }
                }
                //   k4 = k4 + 5;
            }
        }// czwarta cwiartka

        float sr1 = sred1 / licznik1;
        float sr2 = sred2 / licznik2;
        float sr3 = sred3 / licznik3;
        float sr4 = sred4 / licznik4;




        return loadspace;
    }
} // klasa statku oraz załadunku
class SortT {

    public Kontener[] sortM(Kontener[] konteners){
        Kontener[] konteners1 = new Kontener[15000];
        float mass = 0;
        int j = 0;
        int id_max = 0;
        while (j<15000) {
            for (int i = 0; i < 15000; i++) {
                if (konteners[i]!=null && konteners[i].getMass() >= mass) {
                    mass = konteners[i].getMass();
                    id_max = i;
                }
            }
            konteners1[j] = konteners[id_max];
            konteners[id_max] = null;
            mass=0;
            j++;
        }


        return konteners1;
    }

    public Kontener[] sortK(Kontener[] konteners){
        Kontener[] konteners1 = new Kontener[15000];
        float mass = 0;
        int j = 0;
        int id_max = 0;
        while (j<15000) {
            for (int i = 0; i < 15000; i++) {
                if (konteners[i]!=null && konteners[i].getMass() >= mass && konteners[i].getLength() == 6058) {
                    mass = konteners[i].getMass();
                    id_max = i;
                }
            }
            konteners1[j] = konteners[id_max];
            konteners[id_max] = null;
            mass=0;
            j++;
        }
        int j_max = 0;
        while (konteners1[j_max]!=null){
            j_max++;
        }

        j = j_max;
        while (j<15000) {
            for (int i = 0; i < 15000; i++) {
                if (konteners[i]!=null && konteners[i].getMass() >= mass && konteners[i].getLength() == 12192) {
                    mass = konteners[i].getMass();
                    id_max = i;
                }
            }
            konteners1[j] = konteners[id_max];
            konteners[id_max] = null;
            mass=0;
            j++;
        }



        return konteners1;
    }

} // klasa sortująca po masie





