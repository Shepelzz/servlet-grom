package controller;

import exception.BadRequestException;
import exception.InternalServerError;
import model.Item;
import service.ItemService;

public class ItemController {
    private ItemService itemService = new ItemService();

    public Item save(Item item) throws InternalServerError, BadRequestException {
        return itemService.save(item);
    }

    public Item update(Item item) throws InternalServerError, BadRequestException{
        return itemService.update(item);
    }

    public Item delete(Long id) throws InternalServerError, BadRequestException{
        return itemService.delete(id);
    }

    public Item findById(Long id) throws InternalServerError, BadRequestException{
        return itemService.findById(id);
    }

}
