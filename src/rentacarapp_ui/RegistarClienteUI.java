/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rentacarapp_ui;

import helpers.formatHelper;
import rentacarapp_controller.RegistarClienteController;
import rentacarapp_model.Cliente;
import rentacarapp_model.Empresa;
import utils.Utils;

/**
 *
 * @author Paulo Maio
 */
public class RegistarClienteUI {
    private Empresa m_empresa;
    private RegistarClienteController m_controllerRC;

    public RegistarClienteUI( Empresa empresa )
    {
        m_empresa = empresa;
        m_controllerRC = new RegistarClienteController(m_empresa);
    }

    public Cliente run()
    {
        iniciaRegistoCliente();
        String strID = "";
        boolean bFaz;
        do{
            bFaz=false;
            strID = Utils.readLineFromConsole("Introduza o BI/Passaporte do cliente: ");

            if (m_controllerRC.existeClienteID(strID))
            {
                System.out.println("Cliente já existe.");
                if (Utils.readBooleanFromConsole("Deseja reintroduzir o BI/Passaporte?(s/n)"))
                     bFaz=true;
                else
                    return null;// termina o caso de uso
            }
            else
                m_controllerRC.setClienteID(strID);
        }while(bFaz);

        String strNome;
        do {
            strNome = Utils.readLineFromConsole("Introduza o Nome do cliente: ");
            if (strNome.isEmpty()) System.out.println("O nome é um campo obrigatório!");
        } while(strNome.isEmpty());
       
        m_controllerRC.setNomeCliente(strNome);
        
        String strEndereco;
        do {
            strEndereco = Utils.readLineFromConsole("Introduza o Endereço do cliente: ");
            if (strEndereco.isEmpty()) System.out.println("O endereço é um campo obrigatório!");
        } while(strEndereco.isEmpty());
        
        m_controllerRC.setEnderecoCliente(strEndereco);
        
        String strTelefone;
        do {
            strTelefone = Utils.readLineFromConsole("Introduza o Telefone do cliente: ");
            if (strTelefone.isEmpty()) System.out.println("O endereço é um campo obrigatório!");
            else if(!formatHelper.isNumber(strTelefone) || strTelefone.length() != 9) System.out.println("O número de telefone não está no formato correcto.");
        } while(strTelefone.isEmpty());
        
        m_controllerRC.setTelefoneCliente(strTelefone);
        
        String strEmail;
        boolean validEmail;
        do {
            validEmail = false;
            strEmail = Utils.readLineFromConsole("Introduza o Email do cliente (opcional): ");
            if (strEmail.isEmpty()) validEmail = true;
            else {
                String validaEmailRegex = "\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
                if (!strEmail.matches(validaEmailRegex)) {
                    System.out.println("Por favor introduza o email num formato válido!");
                } else validEmail = true;
            }
        } while(!validEmail);
        
        m_controllerRC.setEmailCliente(strEmail);
        
        String strObs = Utils.readLineFromConsole("Introduza as Observações do cliente (opcional): ");
        m_controllerRC.setObservacoesCliente(strObs);

        terminaRegistoCliente();

        return m_controllerRC.getClientebyID(strID);
    }

    private void iniciaRegistoCliente()
    {
        m_controllerRC.iniciaRegistoNovoCliente();
    }

    private void terminaRegistoCliente()
    {
        if (!m_controllerRC.terminaRegistoNovoCliente())
            System.out.println("Erro: Não foi possivel registar o novo cliente.");
    }
}
