package service;

import dao.ItemDAO;
import exception.BadRequestException;
import exception.InternalServerError;
import model.Item;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public Item save(Item item) throws InternalServerError {
        validateItem(item);
        return itemDAO.save(item);
    }

    public Item update(Item item) throws InternalServerError{
        validateItem(item);
        return itemDAO.update(item);
    }

    public Item delete(Long id) throws InternalServerError{
        Item item = itemDAO.findById(id);
        if(item == null)
            throw new BadRequestException("There is no item with id: "+id);

        return itemDAO.delete(item);
    }

    public Item findById(Long id) throws InternalServerError{
        return itemDAO.findById(id);
    }

    private void validateItem(Item item) throws InternalServerError {
        if(item.getName().equals(""))
            throw new BadRequestException("Item name can not be empty");
        if(itemDAO.findByName(item.getName()) != null)
            throw new BadRequestException("Item with name: "+item.getName()+" already exists");
    }


}
