package rentacarapp_model;

import helpers.dataHelper;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author HBK
 */
public class CalculoContrato_Strategy2 implements CalculoContratoStrategy {
    private Empresa m_empresa;
    private List<ContratoAluguer> listaContratos;

    public CalculoContrato_Strategy2() {
        this.m_empresa = Empresa.instance();
        this.listaContratos = this.m_empresa.getRegistoContratoAluguer();
    }
    
    protected float valorContratosNoUltimoAno(ContratoAluguer contratoAluguer) {
        float valorContratosTotal = 0.0F;
        Iterator<ContratoAluguer> it = this.listaContratos.iterator();
        String idCliente = contratoAluguer.getCliente().getID();
        ContratoAluguer contratoAluguerIter;
        
        while (it.hasNext()) {
            contratoAluguerIter = it.next();

            // se o cliente já efectuou um contrato
            if (contratoAluguerIter.getCliente().getID().equals(idCliente)) {
                // e se este contrato foi efectuado no último ano
                if (dataHelper.diferencaDatas(contratoAluguerIter.getDataInicioContrato(), new Date()) < 366) {
                    valorContratosTotal += contratoAluguerIter.getValorTotal();
                }
            }
        }
        return valorContratosTotal;
    }
       
    public float getValorTotal(ContratoAluguer contratoAluguer) {
        float valorContratosTotal = valorContratosNoUltimoAno(contratoAluguer);
        float valorContratoActual = contratoAluguer.getValorTotal();
        
        if (valorContratosNoUltimoAno(contratoAluguer) > 0.0) {
            float descontoAutomovel, descontoSA;
            if (valorContratosTotal > 500.0 && valorContratosTotal <= 1000.0) {
                descontoAutomovel = (float) (contratoAluguer.getAutomovel().getPrecoDiario() * 0.10);
                descontoSA = (float) (contratoAluguer.getValorTotalServicoAdicional() * 0.25);
                return valorContratoActual - (descontoAutomovel + descontoSA);
            } else if (valorContratosTotal > 1000.0) {
                descontoAutomovel = (float) (contratoAluguer.getAutomovel().getPrecoDiario() * 0.20);
                descontoSA = (float) (contratoAluguer.getValorTotalServicoAdicional() * 0.50);
                return valorContratoActual - (descontoAutomovel + descontoSA);
            }
        }
        return valorContratoActual;
    }
    
}
