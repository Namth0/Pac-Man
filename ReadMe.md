# Projet Pacman

---

## Apperçu

Projet Licence 3 Semestre 5 Université Paris Est Créteil Val-de-Marne.

Editeur : IntelliJ (JetBrains)

Language : Java

Libs : Swing 

Auteurs :
- Thomas HODSON : [Github](https://github.com/Hodson-Thomas)
- Othman Bencheriff : [Github](https://github.com/Namth0)

Published : JAN - 08 - 2024

## A noter que

Le projet implémente les fonctionnalités suivantes: 

- Gestion des paramètres via un fichier de configuration. Nous aurions pu utiliser un fichier de données (i.e. Json, Xml, Yaml, Toml, ...) mais nous opté pour un fichier java pour des raisons de simplicité.
- Le GUI est responsive. Le plateau peu dynamiquement changer de taille (voir fichier `Utilities/Configs.java`). A noter qu'on recommande de rester sur une forme carré et de rester autour de 21 cases. La taille de l'interface, les couleurs, les dimensions des composants peuvent également être changer via se fichier. 
- Le terrain est généré aléatoirement. Nous avons utilisé un algorithme pour générer un labyrinthe ([Voir plus](https://github.com/oppenheimj/maze-generator/blob/master/README.md)) puis plusieurs fonctions l'adaptent aux besoins du jeu. A noter que nous aurions pu utiliser les algoritmes de "Perlin-noise" ou "Wave function collapsed" pour générer le terrain.


## Petite remarque 

Il y a un bug connu que nous n’avons pas résolu. Les fantômes ne se déplacent plus graphiquement à partir d’un temps de jeu aléatoire. En backend, rien à signaler. On pense que le problème provient  d’une mauvaise utilisation de la librairie "Swing".