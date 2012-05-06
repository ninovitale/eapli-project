/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package terceiros;


/**
 *
 * @author Nuno Silva
 */
public class SistemaAutorizacaoCartaoCredito
{

    static public AutorizacaoDebitoCartaoCredito autorizacaoDebito(String strNumero, String strValidade, float fValor)
    {
        //Logger.getLogger(SistemaAutorizacaoCartaoCredito.class.getName()).log(Level.WARNING, "Autorização de débito em cartão de crédito não implementada. É necessário clarificar processo com o cliente.");
        
        return new AutorizacaoDebitoCartaoCredito(strNumero, strValidade, fValor);
    }
}
