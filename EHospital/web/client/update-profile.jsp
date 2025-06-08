<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Profile - E-Hospital</title>
        <style>
            .update-container {
                max-width: 800px;
                margin: 50px auto;
                padding: 30px;
                background: rgba(255, 255, 255, 0.95);
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            }
            .update-header {
                text-align: center;
                margin-bottom: 30px;
            }
            .update-header h2 {
                color: #2c3e50;
                font-size: 28px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-bottom: 10px;
            }
            .form-group {
                margin-bottom: 20px;
            }
            .form-label {
                color: #2c3e50;
                font-weight: 600;
                font-size: 14px;
                text-transform: uppercase;
                margin-bottom: 5px;
            }
            .form-control {
                width: 100%;
                padding: 12px;
                border: 1px solid #e9ecef;
                border-radius: 5px;
                font-size: 16px;
                transition: all 0.3s ease;
            }
            .form-control:focus {
                border-color: #2c3e50;
                box-shadow: 0 0 0 0.2rem rgba(44, 62, 80, 0.25);
                outline: none;
            }
            .form-actions {
                text-align: center;
                margin-top: 30px;
            }
            .btn-update {
                background: #2c3e50;
                color: #fff;
                border: none;
                padding: 12px 30px;
                font-size: 16px;
                font-weight: 500;
                text-transform: uppercase;
                letter-spacing: 1px;
                border-radius: 5px;
                transition: all 0.3s ease;
                cursor: pointer;
            }
            .btn-update:hover {
                background: #34495e;
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }
            .alert {
                border-radius: 5px;
                padding: 15px;
                margin-bottom: 20px;
            }
            .alert-danger {
                background: #fff5f5;
                border: 1px solid #feb2b2;
                color: #c53030;
            }
            .section-title {
                margin: 30px 0 20px;
                padding-bottom: 10px;
                border-bottom: 2px solid #e9ecef;
            }
            .section-title h3 {
                color: #2c3e50;
                font-size: 20px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin: 0;
            }
            .required:after {
                content: " *";
                color: #c53030;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        
        <!-- UPDATE PROFILE SECTION -->
        <section id="appointment" data-stellar-background-ratio="3">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="update-container">
                            <div class="update-header">
                                <h2>Update Profile</h2>
                            </div>

                            <% if (request.getAttribute("error") != null) { %>
                                <div class="alert alert-danger">
                                    <i class="fa fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
                                </div>
                            <% } %>

                            <form action="update-profile" method="POST">
                                <!-- Account Information -->
                                <div class="section-title">
                                    <h3>Account Information</h3>
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label required" for="username">Username</label>
                                    <input type="text" class="form-control" id="username" name="username" 
                                           value="${user.username}" required>
                                </div>

                                <div class="form-group">
                                    <label class="form-label required" for="fullName">Full Name</label>
                                    <input type="text" class="form-control" id="fullName" name="fullName" 
                                           value="${user.fullName}" required>
                                </div>

                                <div class="form-group">
                                    <label class="form-label required" for="email">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" 
                                           value="${user.email}" required>
                                </div>

                                <div class="form-group">
                                    <label class="form-label required" for="phone">Phone Number</label>
                                    <input type="tel" class="form-control" id="phone" name="phone" 
                                           value="${user.phone}" required>
                                </div>

                                <!-- Patient Information -->
                                <div class="section-title">
                                    <h3>Patient Information</h3>
                                </div>

                                <div class="form-group">
                                    <label class="form-label">Patient Code</label>
                                    <input type="text" class="form-control" value="${patient.patientCode}" readonly>
                                </div>

                                <div class="form-group">
                                    <label class="form-label" for="dateOfBirth">Date of Birth</label>
                                    <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" 
                                           value="${patient.dateOfBirth}">
                                </div>

                                <div class="form-group">
                                    <label class="form-label" for="gender">Gender</label>
                                    <select class="form-control" id="gender" name="gender">
                                        <option value="">Select Gender</option>
                                        <option value="Male" ${patient.gender == 'Male' ? 'selected' : ''}>Male</option>
                                        <option value="Female" ${patient.gender == 'Female' ? 'selected' : ''}>Female</option>
                                        <option value="Other" ${patient.gender == 'Other' ? 'selected' : ''}>Other</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label class="form-label" for="address">Address</label>
                                    <textarea class="form-control" id="address" name="address" rows="3">${patient.address}</textarea>
                                </div>

                                <div class="form-group">
                                    <label class="form-label" for="bloodType">Blood Type</label>
                                    <select class="form-control" id="bloodType" name="bloodType">
                                        <option value="">Select Blood Type</option>
                                        <option value="A+" ${patient.bloodType == 'A+' ? 'selected' : ''}>A+</option>
                                        <option value="A-" ${patient.bloodType == 'A-' ? 'selected' : ''}>A-</option>
                                        <option value="B+" ${patient.bloodType == 'B+' ? 'selected' : ''}>B+</option>
                                        <option value="B-" ${patient.bloodType == 'B-' ? 'selected' : ''}>B-</option>
                                        <option value="AB+" ${patient.bloodType == 'AB+' ? 'selected' : ''}>AB+</option>
                                        <option value="AB-" ${patient.bloodType == 'AB-' ? 'selected' : ''}>AB-</option>
                                        <option value="O+" ${patient.bloodType == 'O+' ? 'selected' : ''}>O+</option>
                                        <option value="O-" ${patient.bloodType == 'O-' ? 'selected' : ''}>O-</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label class="form-label" for="chronicDiseases">Chronic Diseases</label>
                                    <textarea class="form-control" id="chronicDiseases" name="chronicDiseases" rows="3">${patient.chronicDiseases}</textarea>
                                </div>

                                <div class="form-group">
                                    <label class="form-label" for="allergies">Allergies</label>
                                    <textarea class="form-control" id="allergies" name="allergies" rows="3">${patient.allergies}</textarea>
                                </div>

                                <div class="form-group">
                                    <label class="form-label" for="emergencyContact">Emergency Contact Name</label>
                                    <input type="text" class="form-control" id="emergencyContact" name="emergencyContact" 
                                           value="${patient.emergencyContact}">
                                </div>

                                <div class="form-group">
                                    <label class="form-label" for="emergencyPhone">Emergency Contact Phone</label>
                                    <input type="tel" class="form-control" id="emergencyPhone" name="emergencyPhone" 
                                           value="${patient.emergencyPhone}">
                                </div>

                                <div class="form-actions">
                                    <button type="submit" class="btn-update">
                                        <i class="fa fa-save"></i> Update Profile
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="footer.jsp"/>
        
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.sticky.js"></script>
        <script src="js/jquery.stellar.min.js"></script>
        <script src="js/wow.min.js"></script>
    </body>
</html> 