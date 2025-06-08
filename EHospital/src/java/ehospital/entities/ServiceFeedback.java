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
// TABLE 24: ServiceFeedback Entity
// =============================================
@Table(name = "ServiceFeedback")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ServiceFeedback {
    @Id
    @Column(name = "FeedbackID")
    Integer feedbackId;
    
    @Column(name = "PatientID")
    Integer patientId;
    
    @Column(name = "ServiceID")
    Integer serviceId;
    
    @Column(name = "FeedbackDate")
    Date feedbackDate;
    
    @Column(name = "Rating")
    Integer rating;
    
    @Column(name = "Comments")
    String comments;
}
