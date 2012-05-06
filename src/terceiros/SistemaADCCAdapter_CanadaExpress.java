package terceiros;

import helpers.dataHelper;
import java.util.Date;
import pt.ipp.isep.dei.eapli.canadaexpress.CanadaExpress;
import pt.ipp.isep.dei.eapli.canadaexpress.Pedido;

/**
 *
 * @author HBK
 */
public class SistemaADCCAdapter_CanadaExpress implements SistemaADCCAdapter {
    private Pedido pedido;

    public SistemaADCCAdapter_CanadaExpress() {
    }

    public ADCC getAutorizacao(String tokenKey, String numCartao, String dataValidadeCartao, float valorAutorizacaoDebito, String dataLimiteAutorizacao) {

        Date dataValidadeCartaoCredito = dataHelper.converterStringParaData(dataValidadeCartao);
        Date dataLimiteAutorizacaoDebito = dataHelper.converterStringParaData(dataLimiteAutorizacao);

        this.pedido = new Pedido(dataValidadeCartaoCredito, numCartao, valorAutorizacaoDebito, dataLimiteAutorizacaoDebito);

        CanadaExpress canadaExp = new CanadaExpress();
        
        canadaExp.Init(tokenKey);
        String result = canadaExp.ValidaPedido(pedido);
        canadaExp.Finish();

        return new ADCC_CanadaExpress(result);
    }
}
