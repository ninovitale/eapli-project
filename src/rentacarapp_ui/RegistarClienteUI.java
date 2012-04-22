/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rentacarapp_ui;

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
        boolean bFaz=false;
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

        String strNome = Utils.readLineFromConsole("Introduza o Nome do cliente: ");
        m_controllerRC.setNomeCliente(strNome);
        String strEndereco = Utils.readLineFromConsole("Introduza o Endereço do cliente: ");
        m_controllerRC.setEnderecoCliente(strEndereco);
        String strTelefone = Utils.readLineFromConsole("Introduza o Telefone do cliente: ");
        m_controllerRC.setTelefoneCliente(strTelefone);
        String strEMail = Utils.readLineFromConsole("Introduza o EMail do cliente: ");
        m_controllerRC.setEmailCliente(strEMail);
        String strObs = Utils.readLineFromConsole("Introduza as Observações do cliente: ");
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
