package rentacarapp_model;

import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author HBK
 */
public class CalculoContrato_Strategy2Test {

    private Empresa m_empresa;
    Cliente c;
    GrupoAutomovel ga;
    Automovel auto;
    ContratoAluguer caAnterior = new ContratoAluguer();
    ContratoAluguer caActual = new ContratoAluguer();
    CalculoContrato_Strategy2 instance = new CalculoContrato_Strategy2();
    
    public CalculoContrato_Strategy2Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        this.m_empresa = Empresa.instance();
        ga = new GrupoAutomovel("D", 5, "D", "500", true, 45.00F);
        auto = new Automovel("23-QQ-11", ga, "verde");
        c = new Cliente("123", "Joana", "Rua das Flores", "220001111", "joana@ola.pt", null);
        caAnterior.setCliente(c);
        caAnterior.setAutomovel(auto);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void run() {
        System.out.println("getValorTotal");
        // valor sem desconto
        float valorSemDesconto, valorComDesconto;
        
        caActual.setCliente(c);
        caActual.setAutomovel(auto);
        
        // montante <= 500 (não há desconto)
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -200);
        this.caAnterior.m_dataInicioContrato = calendar.getTime();
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -198);
        caAnterior.setDataDevolucaoPrevista(calendar.getTime());
        
        this.m_empresa.addContratoAluguer(caAnterior);
        
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 20);
        caActual.setDataDevolucaoPrevista(calendar.getTime());
        caActual.setPoliticaDesconto(2);
         
        ServicoAdicional sa = new ServicoAdicional("Cadeira de bébé", 13.00F, true);
        caActual.addServicoAdicional(sa, 2);
        
        valorComDesconto = instance.getValorTotal(caActual);
        assertEquals(926.0F, valorComDesconto, 0);
        System.out.format("%nNão houve desconto. Valor a pagar: €%.2f%nValor total de contratos anteriores = €%.2f (< €500)%n", valorComDesconto, instance.valorContratosNoUltimoAno(caAnterior));
        
        // montante > 500 e <= 1000
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -150);
        this.caAnterior.m_dataInicioContrato = calendar.getTime();
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -142);
        caAnterior.setDataDevolucaoPrevista(calendar.getTime());
        
        this.m_empresa.addContratoAluguer(caAnterior);
        
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 14);
        caActual.setDataDevolucaoPrevista(calendar.getTime());
        
        valorSemDesconto = caActual.getValorTotal();
        valorComDesconto = instance.getValorTotal(caActual);
        assertEquals(645.0F, valorComDesconto, 0);

        System.out.format("€%.2f (sem desconto); €%.2f (com desconto) - 10%% desconto valor p/ dia automóvel, 25%% serviços adicionais.%nValor total de contratos anteriores = €%.2f (> €500 e <= €1000)%n", valorSemDesconto, valorComDesconto, instance.valorContratosNoUltimoAno(caAnterior));
        
        // montante > 1000
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -95);
        this.caAnterior.m_dataInicioContrato = calendar.getTime();
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -67);
        caAnterior.setDataDevolucaoPrevista(calendar.getTime());
        
        this.m_empresa.addContratoAluguer(caAnterior);
        
        calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 64);
        caActual.setDataDevolucaoPrevista(calendar.getTime());
        
        valorSemDesconto = caActual.getValorTotal();
        valorComDesconto = instance.getValorTotal(caActual);
        assertEquals(2884.0F, valorComDesconto, 0);

        System.out.format("€%.2f (sem desconto); €%.2f (com desconto) - 20%% desconto valor p/ dia automóvel, 50%% serviços adicionais.%nValor total de contratos anteriores = €%.2f (> €1000)%n", valorSemDesconto, valorComDesconto, instance.valorContratosNoUltimoAno(caAnterior));
    }
}
