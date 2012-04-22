/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rentacarapp_ui;

import rentacarapp_model.Empresa;
import rentacarapp_ui.*;
/**
 *
 * @author Nuno Silva
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            Empresa empresa = new Empresa();

            MenuUI uiMenu = new MenuUI(empresa);

            uiMenu.run();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

}
