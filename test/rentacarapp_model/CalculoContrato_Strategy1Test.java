/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentacarapp_model;

import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author HBK
 */
public class CalculoContrato_Strategy1Test {
    ContratoAluguer ca = new ContratoAluguer();
    
    public CalculoContrato_Strategy1Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void run() {
        System.out.println("getValorTotal");
        GrupoAutomovel ga = new GrupoAutomovel("D", 5, "D", "500", true, 45.00F);
        Automovel auto = new Automovel("23-QQ-11", ga, "verde");
        Cliente c = new Cliente("123", "Joana", "Rua das Flores", "220001111", "joana@ola.pt", null);
        ca.setCliente(c);
        ca.setAutomovel(auto);
        ca.setPoliticaDesconto(1);
        
        CalculoContrato_Strategy1 instance = new CalculoContrato_Strategy1();
        
         // < que 5 dias
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 3);
        ca.setDataDevolucaoPrevista(calendar.getTime());
        
        // valor sem desconto
        float valorSemDesconto = ca.getValorTotal();
        
        float valorComDesconto = instance.getValorTotal(ca);
        assertEquals(135.0F, valorComDesconto, 0);
        System.out.format("€%.2f (sem desconto) = €%.2f (com desconto) - %d dias de contrato, não há desconto.%n", valorSemDesconto, valorComDesconto, ca.getDiasDuracaoContrato());
        
        // entre 6 e 15 dias (inclusive)
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 10);
        ca.setDataDevolucaoPrevista(calendar.getTime());
        
        valorSemDesconto = ca.getValorTotal();
        valorComDesconto = instance.getValorTotal(ca);
        assertEquals(427.5F, valorComDesconto, 0);
        System.out.format("€%.2f (sem desconto); €%.2f (com desconto) - %d dias de contrato, 5%% desconto contrato.%n", valorSemDesconto, valorComDesconto, ca.getDiasDuracaoContrato());
        
        // entre 16 e 30 dias (inclusive)
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 25);
        ca.setDataDevolucaoPrevista(calendar.getTime());
        
        ServicoAdicional sa = new ServicoAdicional("Cadeira de bébé", 13.00F, true);
        ca.addServicoAdicional(sa, 2);
        
        valorSemDesconto = ca.getValorTotal();
        valorComDesconto = instance.getValorTotal(ca);
        assertEquals(1088.25F, valorComDesconto, 0);
        System.out.format("€%.2f (sem desconto); €%.2f (com desconto) - %d dias de contrato, 5%% desconto valor automóvel diário, 25%% serviços adicionais.%n", valorSemDesconto, valorComDesconto, ca.getDiasDuracaoContrato());

        
        // > que 30 dias
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 38);
        ca.setDataDevolucaoPrevista(calendar.getTime());
                
        valorSemDesconto = ca.getValorTotal();
        valorComDesconto = instance.getValorTotal(ca);
        assertEquals(1289.0F, valorComDesconto, 0);
        System.out.format("€%.2f (sem desconto); €%.2f (com desconto) - %d dias de contrato, 25%% desconto contrato, 50%% serviços adicionais.%n", valorSemDesconto, valorComDesconto, ca.getDiasDuracaoContrato());
    }
}
