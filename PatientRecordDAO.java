package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.PatientRecord;

public class PatientRecordDAO extends GenericDAO<PatientRecord, Integer> {
    public PatientRecordDAO() {
        super(PatientRecord.class);
    }
}