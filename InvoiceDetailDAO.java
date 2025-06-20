package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.InvoiceDetail;

public class InvoiceDetailDAO extends GenericDAO<InvoiceDetail, Integer> {
    public InvoiceDetailDAO() {
        super(InvoiceDetail.class);
    }
}