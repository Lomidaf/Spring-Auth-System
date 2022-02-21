package puttipat.ketpupong.UserSystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import puttipat.ketpupong.UserSystem.entity.Role;
import puttipat.ketpupong.UserSystem.service.RoleService;

import java.net.URI;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long roleId){
        return ResponseEntity.ok().body(roleService.getRoleById(roleId));
    }

    @PostMapping("/")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        log.info("Create "+role.getName()+" into Database");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/").toUriString());
        return ResponseEntity.created(uri).body(roleService.createRole(role));
    }

}
