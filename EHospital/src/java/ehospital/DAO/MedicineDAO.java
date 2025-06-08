package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Medicine;

public class MedicineDAO extends GenericDAO<Medicine, Integer> {
    public MedicineDAO() {
        super(Medicine.class);
    }
}