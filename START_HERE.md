# Pranay Gajbe - Java Spring Boot Portfolio

Three separate Maven projects:
1. Loan Application Processing and Tracking System
2. Product Inventory and Stock Management API
3. Online Course Registration API

## Setup for each project
- Install JDK 21+, Maven 3.9+ and MySQL 8+.
- Open one project folder in IntelliJ IDEA.
- Set MySQL credentials using `DB_USERNAME` and `DB_PASSWORD`, or edit `application.properties`.
- Run `mvn spring-boot:run` inside that project folder.
- Open Swagger UI at `http://localhost:8080/swagger-ui.html`.
- Run projects one at a time because all default to port 8080.

## Publish each project to GitHub
Create a separate empty GitHub repository, then run these commands inside its project folder:
```bash
git init
git add .
git commit -m "Initial commit - Spring Boot REST API"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/REPOSITORY-NAME.git
git push -u origin main
```

Replace the URL with your own repository URL.

## Portfolio checklist
Before listing a project on your resume:
- Run it locally and test every endpoint.
- Add screenshots of Swagger UI.
- Explain the Controller, Repository and JPA entity design.
- Add any extra features only after implementing them.
- Never upload passwords, API keys or private data.
