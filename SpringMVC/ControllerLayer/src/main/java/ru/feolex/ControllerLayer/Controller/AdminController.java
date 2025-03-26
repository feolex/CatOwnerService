package ru.feolex.ControllerLayer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.SecurityLayer.Security.ApiUser;
import ru.feolex.SecurityLayer.Security.ApiUserService;
import ru.feolex.ServiceLayer.Models.OwnerModel;
import ru.feolex.ServiceLayer.Services.OwnerService;

@RestController
@Component
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('user:allActions')")
public class AdminController {

    private final OwnerService ownerService;

    private final ApiUserService userService;

    @Autowired
    public AdminController(OwnerService ownerService, ApiUserService userService) {
        this.ownerService = ownerService;
        this.userService = userService;
    }

    @PostMapping("/createApiUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiUser create(@RequestBody ApiUser apiUser) {
        System.out.println(apiUser);
        ownerService.addOwner(new OwnerModel(apiUser.getOwnerId()));
        OperationResult result = userService.registerUser(apiUser);

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                return ((OperationResult.SuccessWithMeaning<ApiUser>) result).getValue();
            }
            default -> {
                return null;
            }
        }
    }
}
