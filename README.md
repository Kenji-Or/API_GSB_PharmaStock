# 🏥 API GSB PharmaStock

## 📌 Présentation
Cette API REST, développée avec **Spring Boot**, permet la gestion des stocks de médicaments pour le laboratoire pharmaceutique **GSB**. Elle assure la gestion des utilisateurs, des médicaments et des alertes.

## ⚙️ Technologies utilisées
- **Java 17**
- **Spring Boot 3**
- **Spring Security & JWT**
- **Spring Data JPA (MySQL)**
- **Maven**

## 🚀 Installation et exécution
### 📥 Prérequis
- **Java 17** installé
- **MySQL 9.2** en service
- **Postman** (optionnel pour tester les endpoints)

### 🔧 Étapes d’installation
1. **Cloner le projet :**
   ```bash
   git clone https://github.com/Kenji-Or/API_GSB_PharmaStock.git
   cd API_GSB_PharmaStock
   ```
2. **Configurer l'API :**
   Modifier application.properties avec votre jwt secret.
   ```bash
   spring.application.name=API_GSBPharmaStock
   spring.datasource.url=jdbc:mysql://localhost:3306/api_gsb
   spring.datasource.username=root
   spring.datasource.password=MonMotDePasse
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   server.port=5000
   logging.level.root=error
   logging.level.com.cours=info
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.show-sql=true
   jwt.secret=<your-secret-jwt>
   ```
3. **Lancer l’API :**
   ```bash
   mvn spring-boot:run
   ```

### 🔑 Authentification
L’API utilise JWT pour sécuriser les endpoints.

🔹 Connexion (/auth/login) → Récupération du token

🔹 Les routes protégées nécessitent un header Authorization: Bearer <token>
