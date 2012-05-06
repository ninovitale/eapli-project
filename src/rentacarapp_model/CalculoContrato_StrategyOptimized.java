package rentacarapp_model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HBK
 */
public class CalculoContrato_StrategyOptimized implements CalculoContratoStrategy {
    private List<CalculoContratoStrategy> m_strategyList;

    public CalculoContrato_StrategyOptimized() {
        m_strategyList = new ArrayList<CalculoContratoStrategy>();
    }
    
    public void add(CalculoContratoStrategy calcStrategy) {
        this.m_strategyList.add(calcStrategy);
    }
    
    public void remove(CalculoContratoStrategy calcStrategy) {
        this.m_strategyList.remove(calcStrategy);
    } 
    
    public float getValorTotal(ContratoAluguer contratoAluguer) {
        int listSize = this.m_strategyList.size();
        float valorTotalContrato = contratoAluguer.getValorTotal();

        if (listSize > 0) {
            float valoresDesconto[] = new float[listSize];

            int i = 0;
            for (CalculoContratoStrategy calcEstrategia : this.m_strategyList) {
                valoresDesconto[i] = calcEstrategia.getValorTotal(contratoAluguer);
                i++;
            }

            float minimoValorContrato = valorTotalContrato;
            for (float valor : valoresDesconto) {
                if (valor < minimoValorContrato) {
                    minimoValorContrato = valor;
                }
            }

            return minimoValorContrato;

        } else {
            return valorTotalContrato;
        }
    }
}
