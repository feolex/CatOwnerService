package ru.feolex.ControllerLayer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.feolex.ControllerLayer.Exceptions.ControllerException;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Exceptions.NotFoundException;
import ru.feolex.ServiceLayer.Models.CatModel;
import ru.feolex.ServiceLayer.Services.CatServiceInterface;
import ru.feolex.ServiceLayer.Services.ColorServiceInterface;

import java.util.Collection;

@RestController
@Component
@RequestMapping("/api/cats")
@PreAuthorize("hasAuthority('user:crudOwnCats')")
public class CatController {

    private CatServiceInterface catService;

    private ColorServiceInterface colorService;

    @Autowired
    public CatController(CatServiceInterface catService, ColorServiceInterface colorService) {
        this.catService = catService;
        this.colorService = colorService;
        this.colorService.initColors();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:allActions')")
    public Iterable<CatModel> findAll() throws ControllerException {
        OperationResult result = catService.getAllCatsOrderedById();

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                return ((OperationResult.SuccessWithMeaning<Iterable<CatModel>>) result).getValue();
            }
            case Failure -> {
                OperationResult.Failure failure = (OperationResult.Failure) result;
                throw new ControllerException(failure.toString());
            }
            default -> {
                throw new ControllerException("No result type was provided!");
            }
        }
    }

    @GetMapping("/{id}")
    public CatModel findCatById(@PathVariable("id") Long id) {
        OperationResult result = catService.getCatById(id);

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                return ((OperationResult.SuccessWithMeaning<CatModel>) result).getValue();
            }
            default -> {
                return null;
            }
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatModel create(@RequestBody CatModel catModel) {
        OperationResult result = catService.addCat(catModel);

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                return ((OperationResult.SuccessWithMeaning<CatModel>) result).getValue();
            }
            default -> {
                return null;
            }
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException, ControllerException {
        OperationResult result = catService.getCatById(id);

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                CatModel model = ((OperationResult.SuccessWithMeaning<CatModel>) result).getValue();
                OperationResult resultSecond = catService.addCat(model);

                switch (resultSecond.Type()) {
                    case Success -> {
                        return;
                    }
                    default -> {
                        throw new ControllerException("Didn't been deleted!");
                    }
                }
            }
            default -> {
                throw new NotFoundException();
            }
        }
    }

    @PutMapping("/{id}")
    public CatModel updateCat(@RequestBody CatModel model, @PathVariable Long id) throws
            ControllerException, NotFoundException {
        if (model.getId() != id) {
            throw new ControllerException("Ids not equal!");
        }

        OperationResult result = catService.getCatById(id);
        switch (result.Type()) {
            case SuccessWithMeaning -> {
                OperationResult resultSecond = catService.updateCat(model);

                switch (resultSecond.Type()) {
                    case SuccessWithMeaning -> {
                        CatModel modelNew = ((OperationResult.SuccessWithMeaning<CatModel>) result).getValue();

                        return modelNew;
                    }
                    default -> {
                        throw new ControllerException("Didn't been updated!");
                    }
                }

            }
            default -> {
                throw new NotFoundException();
            }
        }
    }
}
