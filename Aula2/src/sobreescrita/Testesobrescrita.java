package sobreescrita;

public class Testesobrescrita {
    public static void main(String[] args) {
        Animal animal1 = new Animal();
        Animal animal2 = new Cachorro();
        Animal animal3 = new Gato();

        animal1.fazerBarulho();
        animal2.fazerBarulho();
        animal3.fazerBarulho();


    }
}
