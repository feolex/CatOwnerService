package com.catownerservice.owner.service;

import com.catownerservice.owner.model.Owner;

import java.util.List;

public interface OwnerService {
    List<Owner> getAllOwners();
    Owner getOwnerById(Long id);
    Owner createOwner(Owner owner);
    Owner updateOwner(Long id, Owner ownerDetails);
    void deleteOwner(Long id);
    Owner getOwnerByEmail(String email);
} 