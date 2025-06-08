/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.ServiceFeedback;

/**
 *
 * @author admin
 */
public class ServiceFeedbackDAO extends GenericDAO<ServiceFeedback, Integer>{
    
    public ServiceFeedbackDAO() {
        super(ServiceFeedback.class);
    }
    
}
