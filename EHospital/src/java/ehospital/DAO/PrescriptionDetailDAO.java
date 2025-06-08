package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.PrescriptionDetail;

public class PrescriptionDetailDAO extends GenericDAO<PrescriptionDetail, Integer> {
    public PrescriptionDetailDAO() {
        super(PrescriptionDetail.class);
    }
}
