
# Choisir une image de base
# Use Java 17 JDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR exécutable dans le conteneur
COPY target/*.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8000

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]

