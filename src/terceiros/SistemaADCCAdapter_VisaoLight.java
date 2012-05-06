package terceiros;

import pt.ipp.isep.dei.eapli.visaolight.VisaoLight;

/**
 *
 * @author HBK
 */
public class SistemaADCCAdapter_VisaoLight implements SistemaADCCAdapter {
    public SistemaADCCAdapter_VisaoLight() {
    }
    
    public ADCC getAutorizacao(String tokenKey, String numCartao, String dataValidadeCartao, float valorAutorizacaoDebito, String dataLimiteAutorizacao) {        
        String result = VisaoLight.getAutorizacaoDCC(tokenKey, dataValidadeCartao, valorAutorizacaoDebito, dataLimiteAutorizacao);
        
        return new ADCC_VisaoLight(result);
    }
}
