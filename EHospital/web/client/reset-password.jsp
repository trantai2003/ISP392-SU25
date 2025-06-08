<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password - E-Hospital</title>
        <style>
            .reset-container {
                max-width: 500px;
                margin: 50px auto;
                padding: 30px;
                background: rgba(255, 255, 255, 0.95);
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            }
            .form-padding {
                padding: 20px;
            }
            .section-title h2 {
                color: #2c3e50;
                font-size: 28px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-bottom: 30px;
                position: relative;
                padding-bottom: 15px;
            }
            .section-title h2:after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 50%;
                transform: translateX(-50%);
                width: 50px;
                height: 3px;
                background: #2c3e50;
            }
            .form-control {
                height: 50px;
                border: 2px solid #e9ecef;
                border-radius: 5px;
                padding: 10px 15px;
                font-size: 16px;
                transition: all 0.3s ease;
                margin-bottom: 20px;
            }
            .form-control:focus {
                border-color: #2c3e50;
                box-shadow: none;
            }
            label {
                color: #2c3e50;
                font-weight: 500;
                margin-bottom: 8px;
                font-size: 14px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }
            #cf-submit {
                background: #2c3e50;
                color: #fff;
                border: none;
                font-size: 16px;
                font-weight: 500;
                text-transform: uppercase;
                letter-spacing: 1px;
                height: 50px;
                border-radius: 5px;
                transition: all 0.3s ease;
            }
            #cf-submit:hover {
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
            .alert-success {
                background: #f0fff4;
                border: 1px solid #9ae6b4;
                color: #2f855a;
            }
            .section-btn {
                background: transparent;
                border: 2px solid #2c3e50;
                color: #2c3e50;
                padding: 10px 20px;
                border-radius: 5px;
                transition: all 0.3s ease;
                text-decoration: none;
                display: inline-block;
                margin-top: 20px;
            }
            .section-btn:hover {
                background: #2c3e50;
                color: #fff;
                text-decoration: none;
            }
            .wow {
                animation-duration: 1s;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        
        <!-- RESET PASSWORD SECTION -->
        <section id="appointment" data-stellar-background-ratio="3">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 col-sm-3">
                        <img src="images/appointment-image.jpg" class="img-responsive" alt="">
                    </div>

                    <div class="col-md-6 col-sm-6 reset-container" style="padding: 20px;">
                        <div>
                            <!-- RESET PASSWORD FORM HERE -->
                            <form id="reset-form" role="form" method="post" action="reset-password">
                                <!-- SECTION TITLE -->
                                <div class="section-title wow fadeInUp" data-wow-delay="0.4s">
                                    <h2 style="margin: 0;">Reset Password</h2>
                                </div>

                                <% if (request.getAttribute("error") != null) { %>
                                    <div class="wow fadeInUp" data-wow-delay="0.6s">
                                        <div class="col-md-12 col-sm-12">
                                            <div class="alert alert-danger">
                                                <i class="fa fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
                                            </div>
                                        </div>
                                    </div>
                                <% } %>

                                <% if (request.getAttribute("success") != null) { %>
                                    <div class="wow fadeInUp" data-wow-delay="0.6s">
                                        <div class="col-md-12 col-sm-12">
                                            <div class="alert alert-success">
                                                <i class="fa fa-check-circle"></i> <%= request.getAttribute("success") %>
                                            </div>
                                        </div>
                                    </div>
                                <% } %>

                                <div class="wow fadeInUp form-padding" data-wow-delay="0.8s">
                                    <input type="hidden" name="token" value="<%= request.getParameter("token") %>">
                                    
                                    <div class="col-md-12 col-sm-12">
                                        <label for="password">New Password</label>
                                        <input type="password" class="form-control" id="password" name="password" 
                                               placeholder="Enter your new password" required>
                                    </div>

                                    <div class="col-md-12 col-sm-12">
                                        <label for="confirmPassword">Confirm Password</label>
                                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
                                               placeholder="Confirm your new password" required>
                                    </div>

                                    <div class="col-md-12 col-sm-12">
                                        <button type="submit" class="form-control" id="cf-submit" name="submit">
                                            Reset Password
                                        </button>
                                    </div>

                                    <div class="col-md-12 col-sm-12">
                                        <div class="text-center">
                                            <a href="login" class="section-btn">
                                                <i class="fa fa-arrow-left"></i> Back to Login
                                            </a>
                                        </div>
                                    </div>
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
        <script>
            // Client-side password validation
            document.getElementById('reset-form').addEventListener('submit', function(e) {
                var password = document.getElementById('password').value;
                var confirmPassword = document.getElementById('confirmPassword').value;
                
                if (password !== confirmPassword) {
                    e.preventDefault();
                    alert('Passwords do not match!');
                }
            });
        </script>
    </body>
</html> 