/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.entities;

import ehospital.anotation.*;
import java.util.Date;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author admin
 */

// =============================================
// TABLE 1: Roles Entity
// =============================================
@Table(name = "Roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Role {
    @Id
    @Column(name = "RoleID")
    Integer roleId;
    
    @Column(name = "RoleName")
    String roleName;
    
    @Column(name = "Description")
    String description;
    
    @Column(name = "CreatedAt")
    Date createdAt;
}
