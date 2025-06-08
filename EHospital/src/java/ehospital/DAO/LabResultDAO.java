/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.LabResult;

/**
 *
 * @author admin
 */
public class LabResultDAO extends GenericDAO<LabResult, Integer>{
    
    public LabResultDAO() {
        super(LabResult.class);
    }
    
}
