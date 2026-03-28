package repository;
import model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class Db implements PedidoRepository {
    private List<Pedido> banco = new ArrayList<>();

    @Override
    public void salvar(Pedido p) {
        banco.add(p);
        System.out.println("salvou no banco");
    }

    @Override
    public List<Pedido> buscarTodos() { return banco; }

    @Override
    public Pedido buscarPorId(int id) {
        return banco.stream().filter(p -> p.id == id).findFirst().orElse(null);
    }
}