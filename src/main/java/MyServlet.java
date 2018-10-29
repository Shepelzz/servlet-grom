import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ItemController;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/test")
public class MyServlet extends HttpServlet {
    private ItemController itemController = new ItemController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.getWriter().println(itemController.findById(Long.valueOf(req.getParameter("id"))).toString());
        }catch (Exception e){
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.getWriter().println("item was saved with id: "+itemController.save(new ObjectMapper().readValue(req.getInputStream(), Item.class)).getId());
        } catch (Exception e){
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.getWriter().println("Item with id: "+itemController.update(new ObjectMapper().readValue(req.getInputStream(), Item.class)).getId()+" was updated.");
        } catch (Exception e){
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.getWriter().println("Item with id: "+itemController.delete(Long.valueOf(req.getParameter("id"))).getId()+" was deleted.");
        }catch (Exception e){
            resp.getWriter().println(e.getMessage());
        }
    }

    //1. servlet registration - init()
    //2. servlet works with service method
    //3. to close servlet with its resources - destroy

    //HTTP REQUESTS
    //  GET - get some info
    //  POST - save some info
    //  PUT - update
    //  DELETE - delete info
}
