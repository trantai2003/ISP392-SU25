package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Department;

public class DepartmentDAO extends GenericDAO<Department, Integer> {
    public DepartmentDAO() {
        super(Department.class);
    }
}