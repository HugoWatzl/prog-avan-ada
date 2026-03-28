package service;

public class EstrategiaDescontoVip implements EstrategiaDesconto {
    @Override
    public double calcular(double t) { return 0.15; }
}