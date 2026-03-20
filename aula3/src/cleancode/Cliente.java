package cleancode;

public class Cliente {
    public int id;
    public String nome;
    public String email;
    public int tipo; // 1 comum, 2 premium, 3 vip

    public String getTipoDesc() {
        if (tipo == 1) {
            return "comum";
        } else if (tipo == 2) {
            return "premium";
        } else if (tipo == 3) {
            return "vip";
        } else {
            return "outro";
        }
    }
}