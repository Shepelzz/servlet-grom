package service;

import dao.ItemDAO;
import exception.BadRequestException;
import exception.InternalServerError;
import model.Item;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public Item save(Item item) throws InternalServerError {
        if(item.getName().equals(""))
            throw new BadRequestException("Item name can not be empty");
        return itemDAO.save(item);
    }

    public Item update(Item item) throws InternalServerError{
        return itemDAO.update(item);
    }

    public Item delete(Long id) throws InternalServerError{
        return itemDAO.delete(id);
    }

    public Item findById(Long id) throws InternalServerError{
        return itemDAO.findById(id);
    }
}
