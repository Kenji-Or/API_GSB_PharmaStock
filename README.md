### 🏥 API GSB PharmaStock
Créé par : Kenji Ogier
Date : 27/03/2025

### 📌 Description
L’API GSB PharmaStock est une API REST conçue pour gérer les stocks de médicaments du laboratoire Galaxy Swiss Bourdin (GSB).
Elle assure :

✔️ La gestion des médicaments (ajout, modification, suppression, consultation)

✔️ La gestion des utilisateurs avec rôles (utilisateur, administrateur)

✔️ Un système d’alertes pour prévenir des stocks bas et des dates de péremption

✔️ Un système d’authentification sécurisé avec JWT

### 🚀 Technologies utilisées
Java 17

Spring Boot 3

Spring Security & JWT

Spring Data JPA (MySQL)

Maven

### 🛠️ Installation et configuration
📥 Prérequis
Avant d’installer l’API, assure-toi d’avoir :
```textplain
✔️ Java 17 installé
✔️ MySQL 9.2 en service
✔️ Maven installé
✔️ Postman (optionnel pour tester les endpoints)
```

### 🔧 Étapes d’installation
1. **Cloner le projet :**
   ```bash
   git clone https://github.com/Kenji-Or/API_GSB_PharmaStock.git
   cd API_GSB_PharmaStock
   ```
2. **Configurer l'API :**
   Modifier application.properties avec votre jwt secret.
   ```properties
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
3. **📥 Importation de la base de données :**
   Créer la base de données :
   ```bash
   mysql -u root -p -e "CREATE DATABASE api_gsb;"
   ```
   Importer le fichier SQL :
   ```bash
   mysql -u root -p api_gsb < database/api_gsb.sql
   ```
3. **Lancer l’API :**
   ```bash
   mvn spring-boot:run
   ```
   L’API sera accessible sur http://localhost:5000 🎉

### 🔑 Authentification
L’API utilise JWT pour sécuriser les endpoints.

🔹 Connexion (/auth/login) → Récupération du token

🔹 Les routes protégées nécessitent un header Authorization: Bearer <token>

### 🔑 Comptes de test disponibles

| Rôle          | Email                 | Mot de passe |
|--------------|----------------------|-------------|
| Utilisateur  | `paul.logan@mail.com` | `12345678`  |
| Administrateur | `admin.gsb@mail.com` | `987654321` |

### 📂 Arborescence du projet
```plaintext
📁 API_GSB_PharmaStock
┣ 📁 src/main/java/com/gsb/api
┃ ┣ 📁 controllers → Gestion des endpoints
┃ ┣ 📁 models → Entités JPA
┃ ┣ 📁 repositories → Interfaces JPA
┃ ┣ 📁 services → Logique métier
┃ ┗ 📄 Application.java → Point d’entrée
┣ 📁 src/main/resources
┃ ┣ 📄 application.properties → Configuration
┣ 📂 database
┣ 📄 README.md                      
┣ 📄 .gitignore                    
┗ 📄 pom.xml → Dépendances Maven
```

### 🖥️Application mobile
👉 Lien vers le dépôt de l'application mobile nécessaire au fonctionnement de l'API : [PharmaStockGSB](https://github.com/Kenji-Or/PharmaStockGSB)
