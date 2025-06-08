package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Invoice;

public class InvoiceDAO extends GenericDAO<Invoice, Integer> {
    public InvoiceDAO() {
        super(Invoice.class);
    }
}