package sobrecarga;

public class Testesobrecarga {
    public static void main(String[] args) {

        Calculadora calculadora = new Calculadora();

        System.out.println(calculadora.somar(2,5));
        System.out.println(calculadora.somar(2.2,5.4));
        System.out.println(calculadora.somar(2,5,7));
    }
}
