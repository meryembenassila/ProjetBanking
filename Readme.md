# Description du projet

Ce projet consiste à développer une application de gestion de comptes bancaires basée sur Spring Boot.
Chaque compte appartient à un client et peut subir plusieurs opérations de type **DEBIT** ou **CREDIT**.

Le système gère deux types de comptes :

- Compte courant
- Compte épargne

## Étape 1 : Création du projet Spring Boot
La première étape consiste à créer un projet Spring Boot avec les dépendances suivantes :

- Spring Web
- Spring Data JPA
- H2 Database
- MySQL Driver
- Lombok

Configuration de l'application
Le fichier application.properties permet de configurer la connexion à la base de données et les paramètres JPA/Hibernate :

![img_1.png](img_1.png)


## Étape 2 : Création des entités JPA
Dans cette étape, nous avons modélisé les entités principales du système de gestion des comptes bancaires. Ces entités représentent les tables de la base de données ainsi que les relations entre elles.

###  Customer
Représente un client de la banque.  
Un client peut posséder plusieurs comptes bancaires.

---

###  BankAccount
Classe abstraite représentant un compte bancaire.  
Elle contient les informations générales communes à tous les types de comptes.

Nous avons choisi la stratégie d’héritage **Single Table** (une seule table pour toute la hiérarchie).  
Ainsi, tous les types de comptes (`CurrentAccount`, `SavingAccount`) sont stockés dans une seule table, avec une colonne discriminante permettant de distinguer leur type.

Chaque compte est associé à un seul client.

![img_2.png](img_2.png)
---

###  CurrentAccount
Représente un compte courant.  
Hérite de `BankAccount` et ajoute :
- découvert autorisé (overdraft)

![img_4.png](img_4.png)
---

###  SavingAccount
Représente un compte épargne.  
Hérite de `BankAccount` et ajoute :
- taux d’intérêt (interest rate)

![img_3.png](img_3.png)

---

###  AccountOperation
Représente une opération effectuée sur un compte bancaire.  
Chaque opération est liée à un seul compte.

Elle contient :
- date de l’opération
- montant
- type (débit ou crédit)

---

###  OperationType
Énumération représentant le type d’opération :
- DEBIT
- CREDIT

---

### AccountStatus
Énumération représentant l’état du compte :
- CREATED
- ACTIVATED
- SUSPENDED

---

###  Relations entre les entités

- Un **Customer** possède plusieurs **BankAccount**
- Un **BankAccount** appartient à un seul **Customer**
- Un **BankAccount** peut avoir plusieurs **AccountOperation**
- Une **AccountOperation** est liée à un seul **BankAccount**
- Héritage entre **BankAccount**, **CurrentAccount** et **SavingAccount**

---

###  Diagramme 
![img.png](img.png)

## Étape 3 : Création des interfaces Repository (DAO)
Dans cette étape, nous avons créé les interfaces Repository qui permettent d’interagir avec la base de données en utilisant **Spring Data JPA**.

Grâce à Spring Data, nous n’avons pas besoin d’implémenter les méthodes CRUD manuellement.

---

###  CustomerRepository
Permet de gérer les opérations liées aux clients.

---

###  BankAccountRepository
Permet de gérer les comptes bancaires (CurrentAccount et SavingAccount).

---

###  AccountOperationRepository
Permet de gérer les opérations bancaires (DEBIT / CREDIT).

## Étape 4 : Test de la couche DAO
Dans cette étape, nous avons testé la couche DAO pour vérifier le bon fonctionnement des entités et des repositories.

---

###  Objectifs des tests :
- Créer des clients
- Créer des comptes bancaires
- Ajouter des opérations
- Vérifier les relations entre entités

---

###  Méthode de test utilisée :
Nous avons utilisé un `CommandLineRunner` pour exécuter des tests automatiquement au démarrage de l’application.
 c
---
![img_5.png](img_5.png)

![img_6.png](img_6.png)

### Tests des 3 Stratégies d’héritage JPA 
#### 1. Stratégie Single Table

Dans cette approche, toutes les classes de la hiérarchie (`BankAccount`, `CurrentAccount`, `SavingAccount`) sont stockées dans **une seule table**.

 Une colonne discriminante permet de distinguer le type de compte.

 Voici comment la stratégie **Single Table** est représentée dans la base de données.

![img_8.png](img_8.png)

####  2. Stratégie Table Per Class (Model Per Table)

Dans cette approche, chaque classe concrète possède sa propre table.

- `BankAccount`
- `CurrentAccount`
- `SavingAccount`

Chaque table contient ses propres colonnes.

![img_7.png](img_7.png)

![img_9.png](img_9.png)

![img_10.png](img_10.png)

 Résultat :  
Voici comment la stratégie **Table Per Class** est représentée dans la base de données.

![img_11.png](img_11.png)

![img_12.png](img_12.png)
#### 3. Stratégie Joined

Dans cette approche, les données sont réparties sur plusieurs tables reliées par des clés étrangères.

- Une table pour `BankAccount`
- Une table pour `CurrentAccount`
- Une table pour `SavingAccount`

![img_13.png](img_13.png)

![img_9.png](img_9.png)

![img_10.png](img_10.png)


Voici comment la stratégie **Joined** est représentée dans la base de données.

![img_14.png](img_14.png)

![img_15.png](img_15.png)

![img_16.png](img_16.png)

## Étape 5 : Création de la couche Service

## Étape 6 : Création des DTOs

## Étape 7 : Création des contrôleurs REST

## Étape 8 : Test des web services RESTful

