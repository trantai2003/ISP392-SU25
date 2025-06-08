/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ehospital.api.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ehospital.DAO.EmployeeDAO;
import ehospital.DAO.UserDAO;
import ehospital.entities.Employee;
import ehospital.entities.User;
import ehospital.utils.ResponseUtils;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "DoctorApi", urlPatterns = {"/admin/doctor-api"})
public class DoctorApi extends HttpServlet {

    private final Gson gson = new Gson();
    private final UserDAO userDAO = new UserDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        config.getServletContext().setAttribute("/admin/doctor-api", this);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DoctorApi</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoctorApi at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                // Get all doctors
                List<User> doctors = userDAO.getAllByRoleID(2);
                for (User doctor : doctors) {
                    doctor.setEmployeeFunc();
                    doctor.getEmployee().setDepartmentFunc();
                }
                JsonObject responseObj = new JsonObject();
                responseObj.addProperty("success", true);
                responseObj.addProperty("message", "Doctors retrieved successfully");
                responseObj.add("data", gson.toJsonTree(doctors));
                ResponseUtils.sendJsonResponse(response, responseObj);
            } else {
                ResponseUtils.sendErrorResponse(response, 404, "Endpoint not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.sendErrorResponse(response, 500, "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer genId = null;
        try {

            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                String requestBody = ResponseUtils.getRequestBody(request);
                User user = gson.fromJson(requestBody, User.class);

                genId = userDAO.insertGetKey(user);

                user.getEmployee().setUserId(genId);
                employeeDAO.insert(user.getEmployee());

                JsonObject responseObj = new JsonObject();
                responseObj.addProperty("success", true);
                responseObj.addProperty("message", "Doctors added successfully");
                responseObj.add("data", null);
                ResponseUtils.sendJsonResponse(response, responseObj);
            } else {
                ResponseUtils.sendErrorResponse(response, 404, "Endpoint not found");
            }
        } catch (JsonSyntaxException | IOException | SQLException e) {
            e.printStackTrace();
            if (e instanceof JsonSyntaxException) {
                ResponseUtils.sendErrorResponse(response, 400, "Bad request: Invalid request body!\n" + e.getMessage());
            }
            if (e instanceof IOException) {
                ResponseUtils.sendErrorResponse(response, 500, "Internal server error: " + e.getMessage());
            }
            if (e instanceof SQLException) {
                if (genId != null) {
                    try {
                        userDAO.deleteById(genId);
                    } catch (SQLException ex) {
                        ResponseUtils.sendErrorResponse(response, 500, "Internal server error: " + e.getMessage());
                        return;
                    }
                }
                ResponseUtils.sendErrorResponse(response, 400, "Bad request: email or phone is existed!\n" + e.getMessage());
            }

        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                String requestBody = ResponseUtils.getRequestBody(request);
                User user = gson.fromJson(requestBody, User.class);

                userDAO.update(user);

                employeeDAO.update(user.getEmployee());

                JsonObject responseObj = new JsonObject();
                responseObj.addProperty("success", true);
                responseObj.addProperty("message", "Doctors update successfully");
                responseObj.add("data", null);
                ResponseUtils.sendJsonResponse(response, responseObj);
            } else {
                ResponseUtils.sendErrorResponse(response, 404, "Endpoint not found");
            }
        } catch (JsonSyntaxException | IOException | SQLException e) {

            e.printStackTrace();
            if (e instanceof JsonSyntaxException) {
                ResponseUtils.sendErrorResponse(response, 400, "Bad request: Invalid request body!\n" + e.getMessage());
            }
            if (e instanceof IOException) {
                ResponseUtils.sendErrorResponse(response, 500, "Internal server error: " + e.getMessage());
            }
            if (e instanceof SQLException) {
                ResponseUtils.sendErrorResponse(response, 400, "Bad request: email or phone is existed!\n" + e.getMessage());
            }
        }
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
