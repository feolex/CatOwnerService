package ru.feolex.ControllerLayer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.feolex.ControllerLayer.Exceptions.ControllerException;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Exceptions.NotFoundException;
import ru.feolex.ServiceLayer.Models.OwnerModel;
import ru.feolex.ServiceLayer.Services.OwnerServiceInterface;

import java.util.Collection;

@RestController
@Component
@RequestMapping("/api/owners")
@PreAuthorize("hasAuthority('user:crudOwnCats')")
public class OwnerContoller {

    @Autowired
    private OwnerServiceInterface ownerService;

    @GetMapping
    @PreAuthorize("hasAuthority('user:allActions')")
    public Iterable<OwnerModel> findAll() {
        OperationResult result = ownerService.getAllOwnersOrderedById();

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                return ((OperationResult.SuccessWithMeaning<Collection<OwnerModel>>) result).getValue();
            }
            default -> {
                return null;
            }
        }
    }

    @GetMapping("/{id}")
    public OwnerModel findOwnerById(@PathVariable("id") Long id) {
        OperationResult result = ownerService.getOwnerById(id);

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                return ((OperationResult.SuccessWithMeaning<OwnerModel>) result).getValue();
            }
            default -> {
                return null;
            }
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerModel create(@RequestBody OwnerModel ownerModel) {
        OperationResult result = ownerService.addOwner(ownerModel);

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                return ((OperationResult.SuccessWithMeaning<OwnerModel>) result).getValue();
            }
            default -> {
                return null;
            }
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException, ControllerException {
        OperationResult result = ownerService.getOwnerById(id);

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                OwnerModel model = ((OperationResult.SuccessWithMeaning<OwnerModel>) result).getValue();
                OperationResult resultSecond = ownerService.deleteOwner(model);

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
    public OwnerModel updateOwner(@RequestBody OwnerModel model, @PathVariable Long id) throws ControllerException, NotFoundException {
        if (model.getId() != id) {
            throw new ControllerException("Ids not equal!");
        }

        OperationResult result = ownerService.getOwnerById(id);
        switch (result.Type()) {
            case SuccessWithMeaning -> {
                OperationResult resultSecond = ownerService.updateOwner(model);

                switch (resultSecond.Type()) {
                    case SuccessWithMeaning -> {
                        OwnerModel modelNew = ((OperationResult.SuccessWithMeaning<OwnerModel>) result).getValue();

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
