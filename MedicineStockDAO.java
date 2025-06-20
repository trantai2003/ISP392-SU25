package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.MedicineStock;

public class MedicineStockDAO extends GenericDAO<MedicineStock, Integer> {
    public MedicineStockDAO() {
        super(MedicineStock.class);
    }
}