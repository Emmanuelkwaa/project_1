package com.skillstorm.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.IUnitOfWork;
import com.skillstorm.data.Repositories.implementations.UnitOfWork;
import com.skillstorm.models.Category;
import com.skillstorm.models.NotFound;
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

@WebServlet(urlPatterns = "/category/*")
public class CategoryServlet extends HttpServlet {
    InventoryManagementDB inventoryManagementDB;
    private IUnitOfWork unitOfWork;

    @Override
    public void init() throws ServletException {
        System.out.println("Product Servlet Created!");
        this.unitOfWork = new UnitOfWork(inventoryManagementDB);
        super.init();
    }

    @Override
    public void destroy() {
        System.out.println("ArtistServlet Destroyed!");
        super.destroy();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servicing request!");
        super.service(req, resp); // Keep this line
    }

    private static final long serialVersionUID = 5795274365670879888L;
    ObjectMapper mapper = new ObjectMapper();
    URLParserService urlService = new URLParserService();

    /**
     * @Param tableName		describes the table that will be connected
     */
    String tableName = "Category";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Category> categories = (List<Category>) unitOfWork.category().getAll(tableName);
            System.out.println(categories);
            //resp.getWriter().print(mapper.writeValueAsString(products));
            resp.getWriter().print(categories);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream reqBody = req.getInputStream();
        Category category = mapper.readValue(reqBody, Category.class);
        category = unitOfWork.category().upsert(category);
        if (category != null) {
            resp.setContentType("application/json");
            resp.getWriter().print(mapper.writeValueAsString(category));
            System.out.println(category);
            resp.setStatus(201); // The default is 200
        } else {
            resp.setStatus(400);
            resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to create category")));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream reqBody = req.getInputStream();
        Category category = mapper.readValue(reqBody, Category.class);
        category = unitOfWork.category().upsert(category);
        if (category != null) {
            resp.setContentType("application/json");
            resp.getWriter().print(mapper.writeValueAsString(category));
            System.out.println(category);
            resp.setStatus(200);
        } else {
            resp.setStatus(400);
            resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to update category")));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = urlService.extractIdFromURL(req.getPathInfo());
        boolean isDeleted = unitOfWork.category().delete(id, tableName);
        if (!isDeleted) {
            resp.setStatus(400);
            resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to delete category")));
        } else {
            resp.getWriter().print(mapper.writeValueAsString(new Success("Successfully deleted!"))); // todo update this to return list of available products
            resp.setStatus(200);
        }
    }
}
