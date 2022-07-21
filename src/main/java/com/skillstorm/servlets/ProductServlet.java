package com.skillstorm.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.data.DBConnection.InventoryManagementDB;
import com.skillstorm.data.Repositories.abstraction.IUnitOfWork;
import com.skillstorm.data.Repositories.implementations.UnitOfWork;
import com.skillstorm.models.NotFound;
import com.skillstorm.models.Product;
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

@WebServlet(urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {
	InventoryManagementDB inventoryManagementDB;
	private IUnitOfWork unitOfWork;

//	public ProductServlet() {
//		this.unitOfWork = new UnitOfWork(inventoryManagementDB);
//	}

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

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// This method activates on ALL HTTP requests to this servlet
		System.out.println("Servicing request!");
		super.service(req, resp); // Keep this line
	}
	

	private static final long serialVersionUID = 5795274365670879885L;
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();

	/**
	 * @Param tableName		describes the table that will be connected
	 */
	String tableName = "Product";

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
				Object product = unitOfWork.product().get(id, tableName);

				if (product != null) {
					System.out.println(product);
					//resp.getWriter().print(mapper.writeValueAsString(product));
					resp.getWriter().print(product);
				} else {
					resp.setStatus(404);
					resp.getWriter().print(mapper.writeValueAsString(new NotFound("No product with the provided Id found")));
				}
			}
			else if (urlService.extractSubUrlFromURL(req.getPathInfo()).equals("complete")){
				List<Product> products = (List<Product>) unitOfWork.product().getProductWithCategory();
				if (products.size() > 0) {
					System.out.println(products);
					resp.setStatus(200);
					resp.getWriter().print(mapper.writeValueAsString(products));
				}
				else {
					resp.setStatus(404);
					resp.getWriter().print(mapper.writeValueAsString(new NotFound("No products found")));
				}
			}
			else {
				List<Product> products = (List<Product>) unitOfWork.product().getAll(tableName);
				System.out.println(products);
				//resp.getWriter().print(mapper.writeValueAsString(products));
				resp.getWriter().print(products);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Something went wrong at server side")));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Product product = mapper.readValue(reqBody, Product.class);
		product = unitOfWork.product().upsert(product);
		if (product != null) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(product));
			System.out.println(product);
			resp.setStatus(201); // The default is 200
		} else {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to create product")));
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Product product = mapper.readValue(reqBody, Product.class);
		product = unitOfWork.product().upsert(product);
		if (product != null) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(product));
			System.out.println(product);
			resp.setStatus(200); // The default is 200
		} else {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to update product")));
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = urlService.extractIdFromURL(req.getPathInfo());
		boolean isDeleted = unitOfWork.product().delete(id, tableName);
		if (!isDeleted) {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to delete product")));
		} else {
			resp.getWriter().print(mapper.writeValueAsString(new Success("Successfully deleted!"))); // todo update this to return list of available products
			resp.setStatus(200);
		}
	}
}
