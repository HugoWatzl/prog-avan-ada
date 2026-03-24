package cleancode;

public class Cliente {
    public int id;
    public String nome;
    public String email;
    public int tipo; // 1 comum, 2 premium, 3 vip

    public String getTipoCliente() {
        switch (tipo) {
            case 1:
                return "comum";
            case 2:
                return "premium";
            case 3:
                return "vip";
            default:
                return "outro";
        }
    }
}