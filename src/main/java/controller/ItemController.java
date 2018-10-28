package controller;

import exception.InternalServerError;
import model.Item;
import service.ItemService;

public class ItemController {
    private ItemService itemService = new ItemService();

    public Item save(Item item) throws InternalServerError {
        return itemService.save(item);
    }

    public Item update(Item item) throws InternalServerError{
        return itemService.update(item);
    }

    public Item delete(Long id) throws InternalServerError{
        return itemService.delete(id);
    }

    public Item findById(Long id) throws InternalServerError{
        return itemService.findById(id);
    }

}
