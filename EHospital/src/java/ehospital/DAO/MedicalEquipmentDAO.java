/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.MedicalEquipment;

/**
 *
 * @author admin
 */
public class MedicalEquipmentDAO extends GenericDAO<MedicalEquipment, Integer>{
    
    public MedicalEquipmentDAO() {
        super(MedicalEquipment.class);
    }
    
}
