package rentacarapp_model;

/**
 *
 * @author HBK
 */
public class CalculoContrato_Strategy1 implements CalculoContratoStrategy {

    public CalculoContrato_Strategy1() {
    }

    public float getValorTotal(ContratoAluguer contratoAluguer) {
        int diasDuracaoContrato = contratoAluguer.getDiasDuracaoContrato();
        float valorTotalContrato = contratoAluguer.getValorTotal();

        float descontoTotalAutomovel, descontoSA;
        if (diasDuracaoContrato >= 6 && diasDuracaoContrato <= 15) {
            // 5% desconto sobre valor total do contrato 
            return (float) (valorTotalContrato * 0.95);
        } else if (diasDuracaoContrato >= 16 && diasDuracaoContrato <= 30) {
            // 5% desconto sobre o valor total do aluguer do automóvel, e desconto de 25% nos serviços adicionais
            descontoTotalAutomovel = (float) ((contratoAluguer.getAutomovel().getPrecoDiario() * diasDuracaoContrato) * 0.05);
            descontoSA = (float) (contratoAluguer.getValorTotalServicoAdicional() * 0.25);
            return valorTotalContrato - (descontoTotalAutomovel + descontoSA);
        } else if (diasDuracaoContrato > 30) {
            // 25% desconto sobre o valor total do contrato, e desconto de 50% nos serviços adicionais
            descontoSA = (float) (contratoAluguer.getValorTotalServicoAdicional() * 0.50);
            return (float) (valorTotalContrato * 0.75 - descontoSA);
        }

        return valorTotalContrato;
    }
}
