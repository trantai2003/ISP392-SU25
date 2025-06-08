<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Profile - E-Hospital</title>
        <style>
            .profile-container {
                max-width: 800px;
                margin: 50px auto;
                padding: 30px;
                background: rgba(255, 255, 255, 0.95);
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            }
            .profile-header {
                text-align: center;
                margin-bottom: 30px;
            }
            .profile-header h2 {
                color: #2c3e50;
                font-size: 28px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-bottom: 10px;
            }
            .profile-info {
                margin-bottom: 20px;
            }
            .info-group {
                margin-bottom: 15px;
                padding: 15px;
                background: #f8f9fa;
                border-radius: 5px;
            }
            .info-label {
                color: #2c3e50;
                font-weight: 600;
                font-size: 14px;
                text-transform: uppercase;
                margin-bottom: 5px;
            }
            .info-value {
                color: #34495e;
                font-size: 16px;
            }
            .profile-actions {
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
                text-decoration: none;
                display: inline-block;
            }
            .btn-update:hover {
                background: #34495e;
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                text-decoration: none;
                color: #fff;
            }
            .alert {
                border-radius: 5px;
                padding: 15px;
                margin-bottom: 20px;
            }
            .alert-success {
                background: #f0fff4;
                border: 1px solid #9ae6b4;
                color: #2f855a;
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
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        
        <!-- PROFILE SECTION -->
        <section id="appointment" data-stellar-background-ratio="3">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="profile-container">
                            <div class="profile-header">
                                <h2>My Profile</h2>
                            </div>

                            <% if (request.getAttribute("success") != null) { %>
                                <div class="alert alert-success">
                                    <i class="fa fa-check-circle"></i> <%= request.getAttribute("success") %>
                                </div>
                            <% } %>

                            <% if (request.getAttribute("error") != null) { %>
                                <div class="alert alert-danger">
                                    <i class="fa fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
                                </div>
                            <% } %>

                            <!-- Account Information -->
                            <div class="section-title">
                                <h3>Account Information</h3>
                            </div>
                            <div class="profile-info">
                                <div class="info-group">
                                    <div class="info-label">Username</div>
                                    <div class="info-value">${sessionScope.userAuth.username}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Full Name</div>
                                    <div class="info-value">${sessionScope.userAuth.fullName}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Email</div>
                                    <div class="info-value">${sessionScope.userAuth.email}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Phone Number</div>
                                    <div class="info-value">${sessionScope.userAuth.phone}</div>
                                </div>
                            </div>

                            <!-- Patient Information -->
                            <div class="section-title">
                                <h3>Patient Information</h3>
                            </div>
                            <div class="profile-info">
                                <div class="info-group">
                                    <div class="info-label">Patient Code</div>
                                    <div class="info-value">${patient.patientCode}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Date of Birth</div>
                                    <div class="info-value">${patient.dateOfBirth}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Gender</div>
                                    <div class="info-value">${patient.gender}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Address</div>
                                    <div class="info-value">${patient.address}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Blood Type</div>
                                    <div class="info-value">${patient.bloodType}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Chronic Diseases</div>
                                    <div class="info-value">${patient.chronicDiseases}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Allergies</div>
                                    <div class="info-value">${patient.allergies}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Emergency Contact</div>
                                    <div class="info-value">${patient.emergencyContact}</div>
                                </div>

                                <div class="info-group">
                                    <div class="info-label">Emergency Phone</div>
                                    <div class="info-value">${patient.emergencyPhone}</div>
                                </div>
                            </div>

                            <div class="profile-actions">
                                <a href="update-profile" class="btn-update">
                                    <i class="fa fa-edit"></i> Update Profile
                                </a>
                            </div>
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