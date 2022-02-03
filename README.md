# k-events-mgnt

Simple events' management project for K.

You can create and retrieve events by most recent start date using the front-end application.

Each date is stored in ISO format, then display by using user's zone time.

[Available demo here](docs/demo.mov)

**Give it a try by running the start.sh file!**

```bash
# Please start docker before
sh start.sh
```

Then http://localhost:3000

## What's inside?

#### 2 projects
- k-events-api
- k-events-app

#### Kubernetes configuration
- [k-events-api](kubernetes/deploy_api.yml)
- [mongodb](kubernetes/deploy_db.yml)
- [kevents-app](kubernetes/deploy_ui.yml)

#### Github Actions (K8s deployment not present)
- [k-events-api](.github/workflows/api.yml)
- [kevents-app](.github/workflows/ui.yml)

### k-events-api

Back-end application using MongoDb as primary database to store events.

#### Technologies
- Java 11+
- Maven 3.x.x
- Spring
- MongoDb
- Docker

#### Dockerfile
[Dockerfile](Dockerfile_API) _size ~ 247MB_

### k-events-app

Front-end application consuming k-events-api to create and retrieve events.

#### Technologies
- React 17+
- Typescript
- Docker
- Antd for design library https://ant.design
- Formik for forms https://formik.org
- Redux Toolkit https://redux-toolkit.js.org
- Axios https://axios-http.com
- Date-fns for date management https://date-fns.org 
- Lodash https://lodash.com

#### Dockerfile
[Dockerfile](Dockerfile_APP) _size ~ 186MB_

## How to improve this project?

### UI
- Use graphQL
- Use SASS for cleaner css code (variables...)

### API
- Use graphQL
- Use Quarkus for smaller footprint and better performance
- Native compilation

-----------------------------

#### Original assignment (French)

## Application de gestion d’événements

L’objectif est d’avoir une application basique de gestion des événements.

Un événement est caractérisé par son nom (32 caractères maximum), sa description et ses dates de début et de fin. Attention, les événements peuvent avoir lieu n’importe où dans le monde, n’oublie pas de prendre en compte la timezone.

Le back-end est une API REST qui retourne du JSON. Il doit gérer les opérations suivantes :

Créer un événement
Lister les événements

Le front-end doit permettre de :

Créer un événement
Lister les événements (optionnel)
 
Optionnel: une fois cette application terminée prépare son déploiement en :

Écrivant les Dockerfile qui construisent les applications front-end et back-end
Écrivant les GitHub Action pour tester, builder et déployer dans Kubernetes
Écrivant les manifests Kubernetes pour déployer les 2 applications avec un Ingress, on part du principe que le cluster possède un Nginx Ingress Controller
