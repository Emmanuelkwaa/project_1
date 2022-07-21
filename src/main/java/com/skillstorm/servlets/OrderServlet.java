package com.skillstorm.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.IUnitOfWork;
import com.skillstorm.data.Repositories.implementations.UnitOfWork;
import com.skillstorm.models.NotFound;
import com.skillstorm.models.Order;
import com.skillstorm.models.Success;
import com.skillstorm.services.URLParserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet(urlPatterns = "/orders/*")
public class OrderServlet extends HttpServlet {
    InventoryManagementDB inventoryManagementDB;
    private IUnitOfWork unitOfWork;

    @Override
    public void init() throws ServletException {
        // This allows us to write code that is run right as the servlet is created
        // You can establish any connections
        System.out.println("Product Servlet Created!");
        this.unitOfWork = new UnitOfWork(inventoryManagementDB);
        super.init();
    }

    @Override
    public void destroy() {
        // If any connections were established in init
        // Terminate those connections here
        System.out.println("ArtistServlet Destroyed!");
        super.destroy();
    }

    // I would prefer filters to this
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // This method activates on ALL HTTP requests to this servlet
        System.out.println("Servicing request!");
        super.service(req, resp); // Keep this line
    }


    private static final long serialVersionUID = 5795274365670879887L;
    ObjectMapper mapper = new ObjectMapper();
    URLParserService urlService = new URLParserService();

    /**
     * @Param tableName		describes the table that will be connected
     */
    String tableName = "Orders";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = 0;

            if (req.getPathInfo() != null) {
                id = urlService.extractIdFromURL(req.getPathInfo());
            }

            String subUrl = null;
            resp.setContentType("application/json");
            if (id > 0) {
                // This means they want a specific artist. Find that artist
                Object order = unitOfWork.order().get(id, tableName);

                if (order != null) {
                    System.out.println(order);
                    resp.getWriter().print(order);
                } else {
                    resp.setStatus(404);
                    resp.getWriter().print(mapper.writeValueAsString(new NotFound("No order with the provided Id found")));
                }
            }
            else if (urlService.extractSubUrlFromURL(req.getPathInfo()).equals("complete")){
                List<Order> orders = (List<Order>) unitOfWork.order().completeOrders();
                if (orders.size() > 0) {
                    System.out.println(orders);
                    resp.setStatus(200);
                    resp.getWriter().print(mapper.writeValueAsString(orders));
                }
                else {
                    resp.setStatus(404);
                    resp.getWriter().print(mapper.writeValueAsString(new NotFound("No orders found")));
                }
            }
            else {
                List<Order> orders = (List<Order>) unitOfWork.order().getAll(tableName);
                System.out.println(orders);
                resp.getWriter().print(orders);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print(mapper.writeValueAsString(new NotFound("Something went wrong at server side")));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream reqBody = req.getInputStream();
        Order order = mapper.readValue(reqBody, Order.class);
        order = unitOfWork.order().update(order);
        if (order != null) {
            resp.setContentType("application/json");
            resp.getWriter().print(mapper.writeValueAsString(order));
            System.out.println(order);
            resp.setStatus(200);
        } else {
            resp.setStatus(400);
            resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to update order")));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = urlService.extractIdFromURL(req.getPathInfo());
        boolean isDeleted = unitOfWork.order().delete(id, tableName);
        if (!isDeleted) {
            resp.setStatus(400);
            resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to delete order")));
        } else {
            resp.getWriter().print(mapper.writeValueAsString(new Success("Successfully deleted!")));
            resp.setStatus(200);
        }
    }
}
