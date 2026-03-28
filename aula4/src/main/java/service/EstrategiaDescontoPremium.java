package service;

public class EstrategiaDescontoPremium implements EstrategiaDesconto {
    @Override
    public double calcular(double t) { return t > 200 ? 0.10 : 0.03; }
}