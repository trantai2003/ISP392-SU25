package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.MedicalService;

public class MedicalServiceDAO extends GenericDAO<MedicalService, Integer> {
    public MedicalServiceDAO() {
        super(MedicalService.class);
    }
}