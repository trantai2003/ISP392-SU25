package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Payment;

public class PaymentDAO extends GenericDAO<Payment, Integer> {
    public PaymentDAO() {
        super(Payment.class);
    }
}