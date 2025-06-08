/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.Shift;

/**
 *
 * @author admin
 */
public class ShiftDAO extends GenericDAO<Shift, Integer> {

    public ShiftDAO() {
        super(Shift.class);
    }

}
