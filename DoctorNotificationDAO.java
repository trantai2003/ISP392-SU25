/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.DoctorNotification;

/**
 *
 * @author admin
 */
public class DoctorNotificationDAO extends GenericDAO<DoctorNotification, Integer>{
    
    public DoctorNotificationDAO() {
        super(DoctorNotification.class);
    }
    
}
