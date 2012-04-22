/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacarapp_model;

/**
 *
 * @author Nuno Silva
 */
public class CondutorAutorizado
{
    String m_strNome;
    String m_strEndereco;
    String m_strNumeroCartaConducao;
    String m_strValidadeCartaConducao;

    public CondutorAutorizado(String strNome,String strEndereco,String strNumero,String strValidade)
    {
        setNome(strNome);
        setEndereco(strEndereco);
        setNumeroCartaConducao(strNumero);
        setValidadeCartaConducao(strValidade);
    }

    public void setEndereco(String strEndereco)
    {
        m_strEndereco = strEndereco;
    }

    public void setNome(String strNome)
    {
        m_strNome = strNome;
    }

    public void setNumeroCartaConducao(String strNumeroCartaConducao)
    {
        m_strNumeroCartaConducao = strNumeroCartaConducao;
    }

    public void setValidadeCartaConducao(String strValidadeCartaConducao)
    {
        m_strValidadeCartaConducao = strValidadeCartaConducao;
    }

    public String getEndereco()
    {
        return m_strEndereco;
    }

    public String getNome()
    {
        return m_strNome;
    }

    public String getNumeroCartaConducao()
    {
        return m_strNumeroCartaConducao;
    }

    public String getValidadeCartaConducao()
    {
        return m_strValidadeCartaConducao;
    }
}
