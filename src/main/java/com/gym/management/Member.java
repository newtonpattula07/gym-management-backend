package com.gym.management;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "members")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    private String name;
    @NotBlank @Email @Column(unique = true)
    private String email;
    @NotBlank @Pattern(regexp = "^[0-9+() -]{7,20}$")
    private String phone;
    @NotBlank
    private String plan;
    @NotNull
    private LocalDate joinDate;
    @NotNull
    private LocalDate expiryDate;
    @NotBlank
    private String status = "ACTIVE";

    public Member() {}
    public Member(String name, String email, String phone, String plan, LocalDate joinDate, LocalDate expiryDate, String status) {
        this.name=name; this.email=email; this.phone=phone; this.plan=plan;
        this.joinDate=joinDate; this.expiryDate=expiryDate; this.status=status;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getPhone(){return phone;} public void setPhone(String phone){this.phone=phone;}
    public String getPlan(){return plan;} public void setPlan(String plan){this.plan=plan;}
    public LocalDate getJoinDate(){return joinDate;} public void setJoinDate(LocalDate joinDate){this.joinDate=joinDate;}
    public LocalDate getExpiryDate(){return expiryDate;} public void setExpiryDate(LocalDate expiryDate){this.expiryDate=expiryDate;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
}
