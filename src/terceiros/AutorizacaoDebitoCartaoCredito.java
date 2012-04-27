/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package terceiros;

/**
 *
 * @author Nuno Silva
 */
public class AutorizacaoDebitoCartaoCredito
{
    private String m_strNumeroCartao;
    private String m_strValidadeCartao;
    private float m_fValorAutorizado;

    public AutorizacaoDebitoCartaoCredito(String strNumero, String strValidade, float fValorAutorizado)
    {
        this.m_strNumeroCartao = strNumero;
        this.m_strValidadeCartao = strValidade;
        this.m_fValorAutorizado = fValorAutorizado;
    }

    public void setValorAutorizado(float fValorAutorizado)
    {
        m_fValorAutorizado = fValorAutorizado;
    }

    public void setNumeroCartao(String strNumeroCartao)
    {
        m_strNumeroCartao = strNumeroCartao;
    }

    public void setValidadeCartao(String strValidadeCartao)
    {
        m_strValidadeCartao = strValidadeCartao;
    }

    public float getValorAutorizado()
    {
        return m_fValorAutorizado;
    }

    public String getNumeroCartao()
    {
        return m_strNumeroCartao;
    }

    public String getValidadeCartao()
    {
        return m_strValidadeCartao;
    }
}
