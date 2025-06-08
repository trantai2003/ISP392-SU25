package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Examination;

public class ExaminationDAO extends GenericDAO<Examination, Integer> {
    public ExaminationDAO() {
        super(Examination.class);
    }
}