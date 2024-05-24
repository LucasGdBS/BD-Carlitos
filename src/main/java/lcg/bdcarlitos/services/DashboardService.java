package lcg.bdcarlitos.services;

import lcg.bdcarlitos.repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public List<Map<String, Object>> obterClientesPorBairro() {
        return dashboardRepository.getClientesPorBairro();
    }

    public List<Map<String, Object>> obterProdutosMaisVendidos() {
        return dashboardRepository.getProdutosMaisVendidos();
    }

    public List<Map<String, Object>> obterFaturamentoDiario() {
        return dashboardRepository.getFaturamentoDiario();
    }

    public List<Map<String, Object>> obterFaturamentoMensal() {
        return dashboardRepository.getFaturamentoMensal();
    }

    public List<Map<String, Object>> obterFaturamentoAnual() {
        return dashboardRepository.getFaturamentoAnual();
    }

    public List<Map<String, Object>> obterFormaPagamentoMaisUtilizadas() {
        return dashboardRepository.getFormaPagamentoMaisUtilizadas();
    }

    public List<Map<String, Object>> obterAtendentesComMaisVendas() {
        return dashboardRepository.getAtendentesComMaisVendas();
    }

    public List<Map<String, Object>> obterPedidosPorTurno() {
        return dashboardRepository.getPedidosPorTurno();
    }

    public List<Map<String, Object>> obterFuncionariosComSalariosAcimaDaMedia() {
        return dashboardRepository.getFuncionariosComSalariosAcimaDaMedia();
    }

    public List<Map<String, Object>> obterProdutosComMaisDeTresIngredientes() {
        return dashboardRepository.getProdutosComMaisDeTresIngredientes();
    }

    public List<Map<String, Object>> obterIngredientesProximosAoVencimento() {
        return dashboardRepository.getIngredientesProximosAoVencimento();
    }

    public List<Map<String, Object>> obterIngredientesMaisUtilizadosNosProdutosMaisVendidos() {
        return dashboardRepository.getIngredientesMaisUtilizadosNosProdutosMaisVendidos();
    }
}
