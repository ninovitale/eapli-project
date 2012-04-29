/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ipp.isep.dei.eapli.canadaexpress;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Nuno Silva
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({pt.ipp.isep.dei.eapli.canadaexpress.CanadaExpressTest.class,pt.ipp.isep.dei.eapli.canadaexpress.PedidoTest.class})
public class CanadaExpressTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

}