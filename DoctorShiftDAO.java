/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.DoctorShift;

/**
 *
 * @author admin
 */
public class DoctorShiftDAO extends GenericDAO<DoctorShift, Integer>{
    
    public DoctorShiftDAO() {
        super(DoctorShift.class);
    }
    
}
