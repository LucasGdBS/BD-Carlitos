package lcg.bdcarlitos.controllers;

import lcg.bdcarlitos.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/clientes-por-bairro")
    public ResponseEntity<List<Map<String, Object>>> getClientesPorBairro() {
        List<Map<String, Object>> result = dashboardService.obterClientesPorBairro();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/produtos-mais-vendidos")
    public ResponseEntity<List<Map<String, Object>>> getProdutosMaisVendidos() {
        List<Map<String, Object>> result = dashboardService.obterProdutosMaisVendidos();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/faturamento-diario")
    public ResponseEntity<List<Map<String, Object>>> getFaturamentoDiario() {
        List<Map<String, Object>> result = dashboardService.obterFaturamentoDiario();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/faturamento-mensal")
    public ResponseEntity<List<Map<String, Object>>> getFaturamentoMensal() {
        List<Map<String, Object>> result = dashboardService.obterFaturamentoMensal();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/faturamento-anual")
    public ResponseEntity<List<Map<String, Object>>> getFaturamentoAnual() {
        List<Map<String, Object>> result = dashboardService.obterFaturamentoAnual();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/forma-pagamento-mais-utilizadas")
    public ResponseEntity<List<Map<String, Object>>> getFormaPagamentoMaisUtilizadas() {
        List<Map<String, Object>> result = dashboardService.obterFormaPagamentoMaisUtilizadas();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/atendentes-com-mais-vendas")
    public ResponseEntity<List<Map<String, Object>>> getAtendentesComMaisVendas() {
        List<Map<String, Object>> result = dashboardService.obterAtendentesComMaisVendas();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/pedidos-por-turno")
    public ResponseEntity<List<Map<String, Object>>> getPedidosPorTurno() {
        List<Map<String, Object>> result = dashboardService.obterPedidosPorTurno();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/funcionarios-salarios-acima-media")
    public ResponseEntity<List<Map<String, Object>>> getFuncionariosComSalariosAcimaDaMedia() {
        List<Map<String, Object>> result = dashboardService.obterFuncionariosComSalariosAcimaDaMedia();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/produtos-com-mais-de-tres-ingredientes")
    public ResponseEntity<List<Map<String, Object>>> getProdutosComMaisDeTresIngredientes() {
        List<Map<String, Object>> result = dashboardService.obterProdutosComMaisDeTresIngredientes();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/ingredientes-proximos-vencimento")
    public ResponseEntity<List<Map<String, Object>>> getIngredientesProximosAoVencimento() {
        List<Map<String, Object>> result = dashboardService.obterIngredientesProximosAoVencimento();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/ingredientes-mais-utilizados")
    public ResponseEntity<List<Map<String, Object>>> getIngredientesMaisUtilizadosNosProdutosMaisVendidos() {
        List<Map<String, Object>> result = dashboardService.obterIngredientesMaisUtilizadosNosProdutosMaisVendidos();
        return ResponseEntity.ok(result);
    }
}
