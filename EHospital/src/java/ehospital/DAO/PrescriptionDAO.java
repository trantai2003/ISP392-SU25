package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Prescription;

public class PrescriptionDAO extends GenericDAO<Prescription, Integer> {
    public PrescriptionDAO() {
        super(Prescription.class);
    }
}
