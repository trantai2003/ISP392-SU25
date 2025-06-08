/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.Role;

/**
 *
 * @author admin
 */
public class RoleDAO extends GenericDAO<Role, Integer>{
    
    public RoleDAO() {
        super(Role.class);
    }
}
