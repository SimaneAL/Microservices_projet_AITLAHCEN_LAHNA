/*
insert into taux_change (id,devise_src,devise_dest,taux,port)
values(10001,'EUR','USD',1.17,0);
insert into taux_change(id,devise_src,devise_dest,taux,port)
values(10002,'EUR','JPY',130,0);
insert into taux_change(id,devise_src,devise_dest,taux,port)
values(10003,'USD','GBP',0.75,0);
 */

/*
 * Projet NoSQL des Jeux Olympiques 2024
 *
 * Auteurs : AIT LAHCEN Simane et LAHNA Ines
 * Date : Mai 2024
 *
 *
 * Ce projet consiste à modéliser une base de données polyglotte permettant de gérer les informations
 * sur les Jeux Olympiques de 2024, y compris les lieux et les calendriers olympiques des différents sports.
 *
 * Tables créées :
 * - site : Elle stocke les informations sur les sites olympiques et paralympiques.
 * - sport : Elle stocke les informations sur les différents sports.
 * - se_situe_a : Elle stocke les distances et temps de trajet entre les sites.
 * - accueille : Elle stocke les infos sur les calendriers des événements pour chaque sport sur chaque site.
 *
 */

-- Creation de la base de donnees :
-- CREATE DATABASE projetjom2if;

-- Connexion a la base de donnees :
-- psql -U simane -d projetjom2if

-- creation des tables :
-- site


-- Insertion des données dans la table sport
INSERT INTO sport ( nom_sport) VALUES
                                      ('Athlétisme'),
                                      ( 'Natation'),
                                      ( 'Gymnastique'),
                                      ( 'Basketball'),
                                      ( 'Handball'),
                                      ( 'Judo'),
                                      ( 'Rugby'),
                                      ( 'Taekwondo'),
                                      ( 'Boxe');
