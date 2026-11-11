# API endpoints (base URL: http://localhost:8080)

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/applicants` | Register applicant |
| GET | `/api/applicants` | List applicants |
| GET | `/api/applicants/{id}` | Get applicant |
| POST | `/api/loans` | Submit loan request |
| GET | `/api/loans` | List loan requests |
| GET | `/api/loans/{id}` | Get loan request |
| GET | `/api/applicants/{id}/loans` | Applicant loan history |
| PATCH | `/api/loans/{id}/status` | Update review status |

Applicant JSON: `{"fullName":"Asha Patil","email":"asha@example.com","phone":"9876543210"}`
Loan JSON: `{"applicantId":1,"loanType":"PERSONAL","amount":75000}`
Status JSON: `{"status":"UNDER_REVIEW","reviewerNote":"Documents received"}`
