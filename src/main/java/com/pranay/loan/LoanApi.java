package com.pranay.loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

record ApplicantInput(@NotBlank String fullName,@NotBlank @Email String email,@NotBlank @Pattern(regexp="^[0-9]{10}$") String phone) {}
record LoanInput(@NotNull Long applicantId,@NotBlank String loanType,@NotNull @DecimalMin("1000.00") BigDecimal amount) {}
record StatusInput(@NotNull LoanStatus status,String reviewerNote) {}
interface ApplicantRepo extends JpaRepository<Applicant,Long> { boolean existsByEmail(String email); boolean existsByPhone(String phone); }
interface LoanRepo extends JpaRepository<LoanRequest,Long> { List<LoanRequest> findByApplicantId(Long applicantId); }

@RestController @RequestMapping("/api")
class LoanApi {
 private final ApplicantRepo applicants; private final LoanRepo loans;
 LoanApi(ApplicantRepo a,LoanRepo l){applicants=a;loans=l;}
 @PostMapping("/applicants") @ResponseStatus(HttpStatus.CREATED)
 Applicant createApplicant(@Valid @RequestBody ApplicantInput r){
  if(applicants.existsByEmail(r.email())) throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already registered");
  if(applicants.existsByPhone(r.phone())) throw new ResponseStatusException(HttpStatus.CONFLICT,"Phone already registered");
  return applicants.save(new Applicant(r.fullName(),r.email(),r.phone()));
 }
 @GetMapping("/applicants") List<Applicant> applicants(){return applicants.findAll();}
 @GetMapping("/applicants/{id}") Applicant applicant(@PathVariable Long id){return applicants.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Applicant not found"));}
 @PostMapping("/loans") @ResponseStatus(HttpStatus.CREATED)
 LoanRequest submit(@Valid @RequestBody LoanInput r){
  Applicant a=applicants.findById(r.applicantId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Applicant not found"));
  return loans.save(new LoanRequest(a,r.loanType(),r.amount()));
 }
 @GetMapping("/loans") List<LoanRequest> loans(){return loans.findAll();}
 @GetMapping("/loans/{id}") LoanRequest loan(@PathVariable Long id){return loans.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan not found"));}
 @GetMapping("/applicants/{id}/loans") List<LoanRequest> applicantLoans(@PathVariable Long id){
  if(!applicants.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Applicant not found");
  return loans.findByApplicantId(id);
 }
 @PatchMapping("/loans/{id}/status") LoanRequest status(@PathVariable Long id,@Valid @RequestBody StatusInput r){
  LoanRequest loan=loans.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan not found"));
  if(loan.status==LoanStatus.APPROVED || loan.status==LoanStatus.REJECTED) throw new ResponseStatusException(HttpStatus.CONFLICT,"Final decision cannot be changed");
  loan.status=r.status(); loan.reviewerNote=r.reviewerNote(); return loans.save(loan);
 }
}
