/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.Clinic;

/**
 *
 * @author admin
 */
public class ClinicDAO extends GenericDAO<Clinic, Integer> {

    public ClinicDAO() {
        super(Clinic.class);
    }

}
