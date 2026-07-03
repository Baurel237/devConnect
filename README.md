# DevConnect — Backend

API REST pour DevConnect, une plateforme où les développeurs partagent des articles techniques (posts) et échangent via des commentaires.

> ✅ **Statut : API complète et fonctionnelle**
> Tous les endpoints listés ci-dessous sont implémentés et disponibles. Toute évolution future du contrat (nouveau champ, endpoint modifié) doit être annoncée à l'équipe frontend avant merge sur `main`.

---

## Stack technique

- **Langage :** Java 26
- **Framework :** Spring Boot 4.1.0
- **Base de données :** MariaDB (via Docker)
- **Validation :** Bean Validation (Jakarta)

## Prérequis

- JDK 26
- Docker + Docker Compose
- Maven (ou wrapper `./mvnw` fourni)

## Lancer le projet en local

```bash
# 1. Démarrer la base de données
docker compose up -d

# 2. Lancer l'application
./mvnw spring-boot:run
```

L'API est accessible sur : `http://localhost:8081/api`

## Endpoints — contrat cible

### Utilisateurs

| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | Liste tous les utilisateurs |
| GET | `/api/users/{id}` | Un utilisateur par son id |
| POST | `/api/users` | Créer un utilisateur |
| PUT | `/api/users/{id}` | Modifier un utilisateur |
| DELETE | `/api/users/{id}` | Supprimer un utilisateur |

**Body POST/PUT :**
```json
{ "name": "Borel" }
```

**Réponse :**
```json
{
  "id": 1,
  "name": "Borel",
  "bio": "Développeur Java passionné"
}
```

### Posts

| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/api/posts` | Tous les posts |
| GET | `/api/posts/{id}` | Un post par son id |
| GET | `/api/posts/user/{id}` | Tous les posts d'un utilisateur |
| POST | `/api/posts/user/{id}` | Créer un post pour un utilisateur |
| PUT | `/api/posts/{id}` | Modifier un post |
| DELETE | `/api/posts/{id}` | Supprimer un post |

**Body POST/PUT :**
```json
{ "title": "Mon premier post", "content": "Contenu du post" }
```

**Réponse :**
```json
{
  "id": 1,
  "title": "Mon premier post",
  "content": "Contenu du post",
  "authorName": "Borel",
  "commentCount": 3
}
```

### Commentaires

| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/api/comments` | Tous les commentaires |
| GET | `/api/comments/{id}` | Un commentaire par son id |
| GET | `/api/comments/post/{id}` | Commentaires d'un post |
| GET | `/api/comments/search?keyword=` | Recherche par mot clé |
| POST | `/api/comments/post/{id}` | Commenter un post |
| PUT | `/api/comments/{id}` | Modifier un commentaire |
| DELETE | `/api/comments/{id}` | Supprimer un commentaire |

**Body POST/PUT :**
```json
{ "text": "Super article !" }
```

**Réponse :**
```json
{
  "id": 1,
  "text": "Super article !",
  "postId": 1,
  "postTitle": "Mon premier post"
}
```

## Gestion des erreurs

Toutes les erreurs retournent un JSON standardisé :

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User non trouvé avec l'id : 99",
  "path": "/api/users/99",
  "timestamp": "2026-07-01T12:00:00"
}
```

| Code | Signification |
|---|---|
| 200 | Succès |
| 201 | Créé avec succès |
| 204 | Supprimé avec succès |
| 400 | Données invalides |
| 404 | Ressource non trouvée |
| 500 | Erreur serveur |

## Règles de validation

| Champ | Ressource | Règle |
|---|---|---|
| `name` | User | Obligatoire, 2 à 50 caractères |
| `title` | Post | Obligatoire, 3 à 100 caractères |
| `content` | Post | Obligatoire, minimum 10 caractères |
| `text` | Comment | Obligatoire, 2 à 500 caractères |

## Notes pour l'équipe

- Sécurité (authentification/autorisation) : hors périmètre de cette phase, prévue plus tard
- Toute évolution du contrat API (nouveau champ, endpoint modifié) doit être communiquée à l'équipe frontend avant merge sur `main`
- Repo frontend associé : `devconnect-frontend`
