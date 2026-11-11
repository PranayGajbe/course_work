package com.pranay.loan;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="applicants")
class Applicant {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
 @NotBlank String fullName;
 @Email @NotBlank @Column(unique=true) String email;
 @NotBlank @Pattern(regexp="^[0-9]{10}$") @Column(unique=true) String phone;
 protected Applicant() {}
 Applicant(String n,String e,String p){fullName=n;email=e;phone=p;}
}
enum LoanStatus { SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED }
@Entity @Table(name="loan_requests")
class LoanRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
 @ManyToOne(optional=false) Applicant applicant;
 @NotBlank String loanType;
 @NotNull @DecimalMin("1000.00") BigDecimal amount;
 @Enumerated(EnumType.STRING) LoanStatus status=LoanStatus.SUBMITTED;
 LocalDateTime submittedAt=LocalDateTime.now();
 String reviewerNote;
 protected LoanRequest() {}
 LoanRequest(Applicant a,String t,BigDecimal amount){applicant=a;loanType=t;this.amount=amount;}
}
