<div align="center">
  <img src="https://i.imgur.com/qbZ28FD.png" width="500"/>
  
  # 🎮 Projet SAE S2.01, S2.02, S2.06 - Jeu **Latice**
</div>

## 👥 Contexte

> Ce projet consiste à concevoir et développer une version numérique du jeu **Latice**, en Java avec une interface graphique JavaFX. Il est réalisé en équipe de 5.

Les étudiants du projet :

| Avatar | Nom |
|--------|-----|
| <img src="https://github.com/YaelBetton.png" width="32"/> | [BETTON Yaël](https://github.com/YaelBetton) |
| <img src="https://github.com/yazouv.png" width="32"/> | [ENON Loïs](https://github.com/yazouv) |
| <img src="https://github.com/Anton-Hladyshev.png" width="32"/> | [HLADYSHEV Anton](https://github.com/Anton-Hladyshev) |
| <img src="https://github.com/IsaacPar.png" width="32"/> | [PARZYCH Isaac](https://github.com/IsaacPar) |
| <img src="https://github.com/SmasBalloon.png" width="32"/> | [SAUVAGE Mathis](https://github.com/SmasBalloon) |

---

## 📋 Cahier des charges simplifié

L'entreprise **GamIUTer** souhaite disposer rapidement d'une version numérique simplifiée de Latice. Les règles sont allégées pour faciliter le développement d’un MVP (Minimum Viable Product).

### Principales règles simplifiées :
- **2 joueurs maximum**
- **Pas de pierres** : elles sont remplacées par des **points**
  - Demi-pierre → 1 point
  - Pierre soleil → 2 points
- **Pas de limite de points**
- Achat d’action supplémentaire → coûte **2 points**
- Échange = tout le rack ou rien
- **Pas de tuile de vent**
- Fin de partie : après **10 cycles** (1 cycle = 2 tours), ou si rack + pioche vides

### 4 actions possibles via l’interface :
1. Jouer une tuile
2. Acheter une action supplémentaire
3. Échanger tout le rack et passer son tour
4. Passer son tour

---

## 🛠️ Technologies et outils

| Technologie                |  |
|---------------------------|:-----:|
| **Langage** : Java        | <img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original-wordmark.svg" width="32"> |
| **Interface graphique** : JavaFX | <img src="https://upload.wikimedia.org/wikipedia/fr/c/cc/JavaFX_Logo.png" width="32"> |
| **Tests** : JUnit         | <img src="https://i.imgur.com/2Slag9R.png" width="32"> |
| **Gestion de versions** : Git + GitHub | <img src="https://img.icons8.com/color/512/git.png" width="32"> <img src="https://cdn-icons-png.flaticon.com/512/25/25231.png" width="32"> |
| **Modularité** : MVC      | — |
| **Livraisons** : Versions taggées (`V1`, `V2`, ...) | <img src="https://i.imgur.com/pdSuFcQ.png" width="32"> |


## 📁 Structure du projet

```bash
latice/
├── src/
│   ├── model/               # Classes métier (Tuiles, Joueurs, Plateau...)
│   ├── view/                # Interfaces graphiques JavaFX
│   ├── controller/          # Contrôleurs entre vue et modèle
│   │   ├── DragContent.java
│   │   ├── DragDropController.java
│   │   ├── Referee.java
│   ├── application/
│   │   ├── LaticeApplicationConsole.java
│   ├── resources/          # Fichiers de ressources (images, vidéos...)
│   └── test/               # Tests JUnit
└── README.md
