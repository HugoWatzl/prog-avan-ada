package sobrecarga;


/**
Class calculadora.
 */

public class Calculadora {

    /**
     * @param x int
     * @param y int
     * @return retorna a soma de x + y
     */
    public int somar(final int x, final int y) {

        return x + y;
    }
    /**
     * @param x
     * @param y
     * @param z
     * @return retorna a soma de x + y + z
     */
    public int somar(final int x, final int y, final int z) {
        return x + y + z;
    }
    /**
     * @param x double
     * @param y double
     * @return retorna a soma de x + y sendo doble
     */
    public double somar(final double x, final double y) {
        return x + y;
    }
}
