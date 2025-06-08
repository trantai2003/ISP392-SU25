/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.HealthInsurance;

/**
 *
 * @author admin
 */
public class HealthInsuranceDAO extends GenericDAO<HealthInsurance, Integer>{
    
    public HealthInsuranceDAO() {
        super(HealthInsurance.class);
    }
    
}
