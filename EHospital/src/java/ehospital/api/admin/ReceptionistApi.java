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
@WebServlet(name = "ReceptionistApi", urlPatterns = {"/admin/receptionist-api"})
public class ReceptionistApi extends HttpServlet {

    private final Gson gson = new Gson();
    private final UserDAO userDAO = new UserDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        config.getServletContext().setAttribute("/admin/receptionist-api", this);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                // Get all receptionists
                List<User> receptionists = userDAO.getAllByRoleID(5);
                for (User receptionist : receptionists) {
                    receptionist.setEmployeeFunc();
                    receptionist.getEmployee().setDepartmentFunc();
                }
                JsonObject responseObj = new JsonObject();
                responseObj.addProperty("success", true);
                responseObj.addProperty("message", "Receptionists retrieved successfully");
                responseObj.add("data", gson.toJsonTree(receptionists));
                ResponseUtils.sendJsonResponse(response, responseObj);
            } else {
                ResponseUtils.sendErrorResponse(response, 404, "Endpoint not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.sendErrorResponse(response, 500, "Internal server error: " + e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer genId = null;
        try {

            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                String requestBody = ResponseUtils.getRequestBody(request);
                User user = gson.fromJson(requestBody, User.class);
                user.setRoleId(5);
                genId = userDAO.insertGetKey(user);

                user.getEmployee().setUserId(genId);
                employeeDAO.insert(user.getEmployee());

                JsonObject responseObj = new JsonObject();
                responseObj.addProperty("success", true);
                responseObj.addProperty("message", "Receptionist added successfully");
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
                user.setRoleId(5);

                userDAO.update(user);

                employeeDAO.update(user.getEmployee());

                JsonObject responseObj = new JsonObject();
                responseObj.addProperty("success", true);
                responseObj.addProperty("message", "Receptionist update successfully");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
