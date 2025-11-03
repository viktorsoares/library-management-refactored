package com.example.library.web;

import com.example.library.model.Borrower;
import com.example.library.repository.BorrowerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {
    private final BorrowerRepository repo;
    public BorrowerController(BorrowerRepository repo){ this.repo = repo; }

    @PostMapping
    public ResponseEntity<Borrower> create(@RequestBody Borrower b){ return ResponseEntity.ok(repo.save(b)); }

    @GetMapping
    public ResponseEntity<List<Borrower>> list(){ return ResponseEntity.ok(repo.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<Borrower> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }

    @PutMapping("/{id}")
    public ResponseEntity<Borrower> update(@PathVariable Long id, @RequestBody Borrower dto){
        return repo.findById(id).map(existing -> {
            existing.setName(dto.getName() != null ? dto.getName() : existing.getName());
            existing.setRole(dto.getRole() != null ? dto.getRole() : existing.getRole());
            return ResponseEntity.ok(repo.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
