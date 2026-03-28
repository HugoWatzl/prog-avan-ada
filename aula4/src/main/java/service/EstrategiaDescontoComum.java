package service;

public class EstrategiaDescontoComum implements EstrategiaDesconto {
    @Override
    public double calcular(double t) { return t > 300 ? 0.05 : 0; }
}