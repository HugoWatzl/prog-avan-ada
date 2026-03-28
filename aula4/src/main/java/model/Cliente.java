package model;
import service.EstrategiaDesconto;

public class Cliente {
    public int id;
    public String nome;
    public String email; // Adicionado para manter compatibilidade com o original
    private EstrategiaDesconto strategy;

    public Cliente(int id, String nome, EstrategiaDesconto strategy) {
        this.id = id;
        this.nome = nome;
        this.strategy = strategy;
        this.email = nome.replace(" ", "").toLowerCase() + "@email.com";
    }

    public EstrategiaDesconto getStrategy() { return strategy; }
}