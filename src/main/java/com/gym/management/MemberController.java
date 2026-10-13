package com.gym.management;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "null"})
public class MemberController {
    private final MemberRepository repository;
    public MemberController(MemberRepository repository) { this.repository = repository; }

    @GetMapping("/members")
    public List<Member> all() { return repository.findAll(); }

    @GetMapping("/members/{id}")
    public Member one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
    }

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public Member create(@Valid @RequestBody Member member) {
        if (repository.existsByEmailIgnoreCase(member.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A member with this email already exists");
        member.setId(null);
        if (member.getStatus() == null || member.getStatus().isBlank()) member.setStatus("ACTIVE");
        return repository.save(member);
    }

    @PutMapping("/members/{id}")
    public Member update(@PathVariable Long id, @Valid @RequestBody Member incoming) {
        Member current = one(id);
        repository.findAll().stream()
            .filter(m -> !m.getId().equals(id) && m.getEmail().equalsIgnoreCase(incoming.getEmail()))
            .findFirst().ifPresent(m -> { throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already belongs to another member"); });
        current.setName(incoming.getName()); current.setEmail(incoming.getEmail());
        current.setPhone(incoming.getPhone()); current.setPlan(incoming.getPlan());
        current.setJoinDate(incoming.getJoinDate()); current.setExpiryDate(incoming.getExpiryDate());
        current.setStatus(incoming.getStatus());
        return repository.save(current);
    }

    @DeleteMapping("/members/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { repository.delete(one(id)); }

    @GetMapping("/dashboard")
    public Map<String, Long> dashboard() {
        List<Member> members = repository.findAll();
        long active = members.stream().filter(m -> "ACTIVE".equalsIgnoreCase(m.getStatus())).count();
        long expired = members.stream().filter(m -> "EXPIRED".equalsIgnoreCase(m.getStatus())).count();
        return Map.of("totalMembers", (long) members.size(), "activeMembers", active, "expiredMembers", expired);
    }
}
