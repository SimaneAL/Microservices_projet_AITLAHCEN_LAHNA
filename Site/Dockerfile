
# Choisir une image de base
FROM openjdk:11-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR exécutable dans le conteneur
COPY target/Site-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8000

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
