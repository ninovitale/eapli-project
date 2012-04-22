/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rentacarapp_model;

/**
 *
 * @author Paulo Maio
 */
public class Cliente {

    private String m_strID; // BI ou Passaporte
    private String m_strNome;
    private String m_strEndereco;
    private String m_strTelefone;
    private String m_strEmail;
    private String m_strObservacoes;


    public Cliente()
    {
    }

    public Cliente(String strID, String strNome,String strEndereco, String strTelefone,String strEmail,String strObs)
    {
        setID(strID);
        setNome(strNome);
        setEndereco(strEndereco);
        setTelefone(strTelefone);
        setEmail(strEmail);
        setObservacoes(strObs);
    }


    public final void setID(String strID)
    {
        m_strID = strID;
    }

    public final void setNome(String strNome)
    {
        m_strNome = strNome;
    }

    public final void setEndereco(String strEndereco)
    {
        m_strEndereco = strEndereco;
    }

    public final void setTelefone(String strTelefone)
    {
        m_strTelefone = strTelefone;
    }

    public final void setEmail(String strEmail)
    {
        this.m_strEmail = strEmail;
    }

    public final void setObservacoes(String strObservacoes)
    {
        m_strObservacoes = strObservacoes;
    }

    
    public String getID()
    {
        return m_strID;
    }

    
    public String getNome()
    {
        return m_strNome;
    }

   
    public String getEndereco()
    {
        return m_strEndereco;
    }

    
    public String getTelefone()
    {
        return m_strTelefone;
    }

    
    public String getEmail()
    {
        return m_strEmail;
    }

    
    public String getObservacoes()
    {
        return m_strObservacoes;
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s - %s", getID(),getNome(),getTelefone());
    }
}
