package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Employee;

public class EmployeeDAO extends GenericDAO<Employee, Integer> {
    public EmployeeDAO() {
        super(Employee.class);
    }
}