package terceiros;

/**
 *
 * @author HBK
 */
public interface SistemaADCCAdapter {
    public ADCC getAutorizacao(String tokenKey, String numCartao, String dataValidadeCartao, float valorAutorizacaoDebito, String dataLimiteAutorizacao);
}
