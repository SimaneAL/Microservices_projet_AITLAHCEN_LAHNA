# PROJET Microservices M2IF : Jeux Olympiques 2024
## Etudiantes : AIT LAHCEN Simane & LAHNA Inès

### Afin de tester nos services sur Postman : 

<img width="300" alt="image" src="https://github.com/SimaneAL/Microservices_projet_AITLAHCEN_LAHNA/assets/64166374/f7b627ea-8314-49da-8e09-6c4a1438ccbe"> 

### Site : 

1. Tous les sites : http://localhost:8000/site/all
2. Mettre à jour un site en fonction de son id : http://localhost:8000/site/id/1
3. Créer un site : http://localhost:8000/site
4. Supprimer un site en fonction de son id : http://localhost:8000/site/id/2
5. Trouver site par nom : http://localhost:8000/site/name/Gymnase
<img width="1269" alt="image" src="https://github.com/SimaneAL/Microservices_projet_AITLAHCEN_LAHNA/assets/64166374/f64a71b1-34bc-4ba8-a22c-ee85f41d5e6a">


### Sport :
1. Tous les sports : http://localhost:8001/sport/all
2. Mettre à jour un sport en fonction de son id : http://localhost:8001/sport/id/1
3. Créer un sport : http://localhost:8001/sport
4. Supprimer un site : http://localhost:8001/sport/id/2


### Planning :
1. Tous les Planning : http://localhost:8003/planning/all
2. Mettre à jour un Planning en fonction de son id : http://localhost:8003/planning/id/1
3. Créer un Planning : http://localhost:8001/sport
4. Supprimer un Planning : http://localhost:8001/sport/id/2
5. Plannig d un spectateur : http://localhost:8003/planning/AL/Simane


### Calendrier :
1. Tous les calendriers : http://localhost:8002/calendrier/all
2. Mettre à jour un calendrier en fonction de son id : http://localhost:8002/calendrier/id/1
3. Créer un calendrier : http://localhost:8003/planning
4. Supprimer un calendrier : http://localhost:8003/planning/id/13
5. Calendrier à une date donnée : http://localhost:8002/calendrier/date/20240622

### Appel de deux microservices :
1. Nom d un sport dans un calendrier donné : http://localhost:8002/calendrier/nameSport/id/4
2. nom d un sport dans un site donnné :  http://localhost:8002/calendrier/getSportsDansUnSiteDonne/Gymnase