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
// TABLE 20: Payments Entity
// =============================================
@Table(name = "Payments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Payment {
    @Id
    @Column(name = "PaymentID")
    Integer paymentId;
    
    @Column(name = "InvoiceID")
    Integer invoiceId;
    
    @Column(name = "Amount")
    Double amount;
    
    @Column(name = "PaymentMethod")
    String paymentMethod;
    
    @Column(name = "PaymentReference")
    String paymentReference;
    
    @Column(name = "PaidAt")
    Date paidAt;
    
    @Column(name = "Notes")
    String notes;
}