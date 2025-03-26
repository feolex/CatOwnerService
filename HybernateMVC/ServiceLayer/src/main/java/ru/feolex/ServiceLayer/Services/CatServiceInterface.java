package ru.feolex.ServiceLayer.Services;


import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Models.CatModel;

public interface CatServiceInterface
{
    OperationResult addCat(CatModel cat);
    OperationResult updateCat(CatModel cat);
    OperationResult deleteCat(CatModel cat);
    OperationResult getCatById(Long id);

    OperationResult getAllCats();
}
