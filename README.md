# Projet Patient Management System

## Description
Application de gestion de patients avec front-end et back-end séparés, intégration d'un gateway et microservices pour les notes et le calcul du risque de diabète.

### Fonctionnalités principales
- Gestion des patients (CRUD)
- Ajout et consultation des notes médicales
- Calcul du risque de diabète via un microservice
- Authentification basique avec Spring Security
- Front-end en Thymeleaf
- Communication entre microservices via RestTemplate et Spring Cloud Gateway

### Technologies utilisées
- Java 17
- Spring Boot (Backend, Frontend, Gateway)
- Spring Cloud Gateway
- Spring Data JPA (MySQL)
- Spring Data MongoDB (Notes)
- Spring Security
- Thymeleaf
- Docker & Docker Compose
- Maven

### Bonnes pratiques / Green Code
- Séparation Front / Back
- DTO pour validation côté API
- Validation des entrées utilisateurs avec `@Valid`
- Utilisation des constantes pour URL et chemins
- Gestion centralisée des exceptions
- Containerisation avec Docker pour environnement reproductible

### Lancer le projet
```bash
docker-compose up --build
