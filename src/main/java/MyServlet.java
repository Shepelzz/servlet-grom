import controller.ItemController;
import exception.BadRequestException;
import exception.InternalServerError;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = "/test")
public class MyServlet extends HttpServlet {
    private ItemController itemController = new ItemController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.getWriter().println(itemController.findById(Long.valueOf(req.getParameter("itemId"))).toString());
        }catch (Exception e){
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//        req.getInputStream();
//        req.getReader();

        Item item = new Item();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

        try {
            item.setName(req.getParameter("name"));
            item.setDateCreated(dateFormat.parse(req.getParameter("dateCreated")));
            item.setLastUpdatedDate(dateFormat.parse(req.getParameter("lastUpdatedDate")));
            item.setDescription(req.getParameter("description"));

            resp.getWriter().println("item was saved with id: "+itemController.save(item).getId());

        } catch (Exception e){
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item item = new Item();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

        try {
            item.setId(Long.valueOf(req.getParameter("id")));
            item.setName(req.getParameter("name"));
            item.setDateCreated(dateFormat.parse(req.getParameter("dateCreated")));
            item.setLastUpdatedDate(dateFormat.parse(req.getParameter("lastUpdatedDate")));
            item.setDescription(req.getParameter("description"));

            resp.getWriter().println("Item with id: "+itemController.update(item).getId()+" was updated.");

        } catch (Exception e){
            resp.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.getWriter().println("Item with id: "+itemController.delete(Long.valueOf(req.getParameter("itemId"))).getId()+" was deleted.");
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
