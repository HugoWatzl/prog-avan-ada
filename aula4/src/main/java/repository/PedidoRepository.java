package repository;
import model.Pedido;
import java.util.List;

public interface PedidoRepository {
    void salvar(Pedido p);
    List<Pedido> buscarTodos();
    Pedido buscarPorId(int id);
}