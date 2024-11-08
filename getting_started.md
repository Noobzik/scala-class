# Lancer le Hand's on

:warning: Le processus de checkout et d'installation de l’environnement  prend du temps, alors, pour le bon déroulement du hand's on, il est primordial de le faire en amont.

## Configurer l'environnement

1. Installer (si nécessaire) Java à l'aide de [sdkman](https://sdkman.io/)
    - pour vérifier si Java est installé, ouvrir le terminal/invite de commande et taper `java --version`
    
2. Installer SBT à l'aide de [sdkman](https://sdkman.io/)
    - pour vérifier que Sbt est installé, ouvrir le terminal/invite de commande et taper `sbt --version`
3. Télécharger le Hand's on : [https://github.com/Noobzik/scala-class.git](https://github.com/oraclewalid/scala-class.git)
    - via git clone
    - via zip (bouton vert à droite)
4. Ouvrir la console/terminal dans le dossier téléchargé et lancer :
    - Linux/Mac: `./handson go`

Cela téléchargera l'ensemble des dépendances nécessaires. A la fin vous aurez un écran comme ceci :

![handson-terminal](docs/assets/handson-terminal.png)

5. Installer un éditeur de texte/IDE
    - [IntelliJ IDEA Community](https://www.jetbrains.com/idea/download/) (puissant mais plus compliqué à configurer)
        - Télécharger et installer
        - Durant le premier lancement intellij va proposer l'installation des plugins. sélectionner le plugin Scala
        - Ouvrir le projet : File > Open
        - Sélectionner le fichier `build.sbt` dans le dossier et cliquer sur `Ouvrir comme un projet`
        - Sélectionner le JDK le plus récent installé et ajouter configurer le SDK Scala (`setup Scala SDK`) comme le suggère IntelliJ
        - IntelliJ va ensuite télécharger les composants nécessaire et configurer le projet (cf barre de chargement en bas à droite)
        - Ouvrir le fichier `src/test/scala/exercices/e01_scala_syntax.scala`, il ne devrait pas y avoir de rouge
        - C'est fini, rendez-vous au Hand's on ;)
    - [Sublime Text](https://www.sublimetext.com/3)
    - Autre (à vos risques et périls ^^)

## Structure du Hand's on

Le Hand's on est simplement une suite de tests modifiée pour rendre les choses plus sympa.

`exercice` et `section` sont simplement des fonctions qui servent respectivement à définir un exercice et un groupe d'exercices.

`shouldBe` est une assertion, les deux parties (gauche et droite) doivent être égales pour qu'elle soit valide

## Lancer le Hand's on

- Ouvrir un terminal dans le dossier du Hand's on et exécuter :
    - Linux/Mac: `./handson go`
- Ouvrir le fichier `src/test/scala/exercices/e01_scala_syntax.scala` dans l'éditeur et faire le premier exercice
- Le terminal exécute à nouveau les tests :D

## Instructions

Voici un exemple du contenu du terminal :

![handson-terminal](docs/assets/handson-terminal.png)

Il affiche l'endroit à modifier pour avancer :

- le nom et ligne du fichier sont en bas
- la flèche `->` indique précisément la ligne en question
- la ligne entre le titre et le code indique ce qu'il faut faire :
    - remplacer `__` par une valeur
    - remplacer `???` par du code

## Aide

Certains exercices ont des aides à la fin, pensez à regarder s'il y en a en cas de difficulté.

## Troubleshooting

Le support pour Windows n'est plus assuré
