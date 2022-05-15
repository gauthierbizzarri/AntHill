### ANTHILL PROJECT 
A java , javafx project 
# Projet
# Ant War


### ADDED FEATURES :

##### Orders visualisation

~~~~~~~~~~~~~~~~
Orders can be selected and visualized by pressing the buttons HOME 
When pressing ot the order HOME will be sent all all ants
 who belongs to the anthill will go home 
~~~~~~~~~~~~~~~~~

##### Audio ambiance

~~~~~~~~~~~~~~~~
When starting the simulation a music will be played 
~~~~~~~~~~~~~~~~~

##### JavaFX feature

~~~~~~~~~~~~~~~~
The simulation is using JavaFx to see the action of the ants 
~~~~~~~~~~~~~~~~~

Sur une map divisé en carré. Des colonies de fourmis combattent pour recuperer le plus de ressource.

Chaque carré de la carte contient un nombre aléatoire de ressource (ex: 20 ou 0 ressources sur une case). Il y a plusieurs fourmillières sur la carte placé aléatoirement sur la map.
Les fourmis bouge de case en case pour aller chercher des ressources (elles peuvent transporter qu'un nombre limité de ressources à chaque trajet), et elle doit ramener sa ressource à la colonie. A chaque fois que des ressources sont déposés dans la colonie son score augmente.

Au bout de x temps, la colonie ayant le plus gros score gagne.

Les fourmis se déplacent de haut en bas, droit gauche (pas de diagonale). Et elle peuvent être plusieurs sur la même case.

Une fourmi doit attendre 50ms maxi entre chaque action, une action est:
- se deplacer d'une case
- recupérer 1 (et une seul) ressource
- déposer 1 (et une seul) ressource dans le fourmilière
- combattre

Les fourmillères se placent aléatoirement.

Il y a 3 types de fourmis:
1. la reine (1)
2. les commandants (5)
3. les ouvrières (50)

Dans chaque fourmillière il y a une reine, cette reine reste dans la fourmillière, elle peut donner des ordres:
- Aller chercher des ressources
- Tout le monde rentre dans la fourmillière
  Mais elle ne connait aucune fourmi. Ce sont les commandant qui "écoutent" via Flow https://docs.oracle.com/javase/9/docs/api/java/util/concurrent/Flow.html (ATTENTION `Observer` est **depreacted** https://docs.oracle.com/javase/8/docs/api/java/util/Observer.html) les ordres. Les commandant non plus ne connaissent pas les ouvrières, mais les fourmis ouvrières écoutent leur commandant, et applique les ordres.

Les commandants peuvent bouger sur la map, mais ils ne peuvent pas ramasser de ressource.

Chaque ouvrière est attribué à un commandant.

Seul la reine connait la durée de la partie, elle doit donc donner l'ordre de rentrer à la fourmilière avant que la partie ne soit fini.

L'affichage de la map se fera en console dans un 1er temps


## Contraintes
- Chaque fourmis est un thread
- Un class singleton Map permet de gerer l'affichage de la carte, de dire quelle fourmis est dans quelle case, gérer les combats
- La reine ne peut connaitre les autres threads elle ne peux que changer son ordre (pas l'envoyer)
- Les commandant ne connaissent pas non plus les ouvrières, les commandants ne font que répeter l'ordre de la reine.


## Variables:
- taille de la map: 500*500
- nombre de fourmillière par map: 3
- temps d'attente entre chaque action: 50ms maxi (au delas le jeu va saccader)
- temps d'une partie: 2min
- ressource min par case: 0
- ressource max par case: 50
- inventaire d'une fourmis: 5
- nombre de commandant au depart: 5
- nombre de ouvrières au départ: 50
- ratio nourriture/point: 1/3
- cout en ressource d'une ouvrière: 5
- cout en ressource d'un commandant: 8
- temps de diminution d'une phéromone sur une case: 2s
- rayon d'ordre de la reine: 100
- rayon d'ordre d'un commandant 50


## Bonus competition
Le projet est individuel. Mais si vous souhaitez, vous pouvez vous mettre par groupe de 2~3, afin d'harmoniser ensemble la class map et ses methodes. Afin de pouvoir rendre vos codes compatibles entre eux. Ainsi, après avoir avancé chacun de votre coté vous pourrez essayer d'assembler vos codes dans un même projet pour voir lequel d'entre vous à réalisé la meilleure IA.

## Ameliorations
### JavaFX
Faire l'affichage de la map et des fourmis en javaFX
Dans un premier temps vous pouvez afficher que des points et des carrés.
Dans un second temps, si vous souhaitez, des images vous sont fournis

### Phéromones 1
Lorsque une ouvrières revient pour déposer ses ressources à la fourmillière, après avoir trouvé un spot intéréssant, elle laisse une trace de phéromone sur chaque case qu'elle traverse sur son trajet de retour. Ainsi les autres ouvrières peuvent suivre la trace et trouver plus rapidement des ressources. Il n'est pas possible pour une ouvrière de lire les pheromones d'une autre fourmillière.


### Phéromones 2
Les phéromones sur chaque case est un compteur, si 3 ouvrières laisse une trace sur une case la valeur de phéromone de la case vaut 3. Cette valeur diminue avec le temps, en raison de 1 phéromones toute les 2 secondes.


### Spawn
Sur la map, il y a plusieurs types de ressources distribué aléatoirement :
1. des Points qui vont servir définir quelle fournillière à gagnée la partie
2. de la Nouriture qui va servir à créer des fourmis
   Le ratio de repartition sur la map est d'environ x nourriture pour y points
   La reine peut décider de créer des ouvrières ou des commandants supplémentaire. Chaque fourmis coute x ressources de type "nouriture".
   De plus la reine peut envoyer un nouveau type d'ordre aux fourmis: se focaliser que sur les Points, ou que sur la nourriture, ou se focaliser sur rien.


### Ordres dans un rayon
La reine peut donner des ordres seulement dans un rayon de X cases. Pour étendre ses ordres, les commandants présents dans le rayon de la reine pourront répéter les ordres eux-même dans un rayon de X cases. Ainsi les commandants peuvent étendre le rayon de communication de la reine
Les commandants peuvent se transmettre des ordre entre eux pour étendre leurs rayons.
Les ouvrières n'ont plus un commandant attribué mais le commandant le plus proche d'elle.


### Combat 1
Si 2 ouvrières adverse se rencontre un combat commence, c'est la class map qui decide arbitrairement de quelle fourmis l'emporte. La fourmis morte disparait, et laisse ses ressources sur la case du combat.
Les commandants ne peuvent pas mourir face à une ouvrière. Si 2 commandants se rencontrent, la class map decide arbitrairement de qui l'emporte.
> Si il y a 3 fourmis de 3 fourmillères différentes (A/B/C) sur la meme case, il y aura 2 combats qui ce suivront, leur ordre est aléatoire (exemple: A VS C => A gagnante, A VS B => B gagnant)

Quand une fourmis meurt, la reine le sais immédiatement (et peut donc décider de faire respawn).

### Combat 2
Les fourmis possèdent des PV, un combat gagné blesse néanmoins l'ouvrière gagnante. Si cette ouvrière retombe sur un autre combat face à une ouvrière n'étant pas bléssé elle meurt obligatoirement, mais elle blesse à son tour l'autre ouvrière avant de mourir.

Un combat entre une ouvrière et un commandant ne peux pas blesser le commandant et l'ouvrière meurt obligatoirement. Si 2 commandants se rencontre les même regles que pour les ouvrières s'appliquent.

Quand une fourmis est blessé elle peut retourner à la fourmillière pour récupérer tout ses PV, cela lui prends une action (50ms).


### Phéromones 3
Les fourmis ne possèdent plus l'emplacement (X, Y) de leur fourmillière. Ils avance aleatoirement sur la map ou suivent une trace de phéromones. Les fourmis qui avancent aléatoirement "se souviennent" de leur précédant trajet, et sur le retour après avoir récupéré des ressources, elle remonte le trajet aller et laisse des phéromones. (la notion de souvenir du trajet est interne à l'ouvrière, il n'y a pas de phéromone sur le trajet aller).


### Map "naturel"
Faire en sorte que les ressources se dispatchent de manière plus naturelle. Les ressources se concentre par zone (de manière non homogènes).
Ajouter des obstacles sur la map.


### BONUS perso
Vous pouvez ajouter des features persos si vous souhaitez, cela ne pourra que augmenter votre note.
Exemple: de nouveaux type d'ordre, des animations FX, des bruitages,

Mais penser à préciser ces features dans le README


@author Gauthier bizzarri 
