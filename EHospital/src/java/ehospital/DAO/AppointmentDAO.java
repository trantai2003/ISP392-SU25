package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Appointment;

public class AppointmentDAO extends GenericDAO<Appointment, Integer> {
    public AppointmentDAO() {
        super(Appointment.class);
    }
}