# MyKogitoDemoPam Project

Demo for IBM Kogito Operator deployments

## How I built the application

set some vars
```
GROUP_ID=marco.demos.kogito
APP_NAME=MyKogitoDemoPam
```

create the quarkus project
```
quarkus create app ${GROUP_ID}:${APP_NAME} --extension='kogito-quarkus,resteasy-reactive,quarkus-smallrye-openapi,resteasy-reactive-jackson,smallrye-health'
cd ${APP_NAME}
```

Then I've created the remote git repo 
```
https://github.com/marcoantonioni/MyKogitoDemoPam
```

and initialized, updated then pushed the initial project structure

```
echo "# ${APP_NAME} - demo for IBM Kogito Operator deployments" >> README.md
git init
git add README.md
git commit -m "first commit for ${APP_NAME}"
git branch -M main
git remote add origin https://github.com/marcoantonioni/${APP_NAME}.git
git push -u origin main
```

## The BPMN process

[image]

I then defined a very simple process made of log scripts, a DMN rule and an human task.

The purpose is to automatically validate a request based on the age of the applicant and an amount threshold or to route to a backoffice clerk the request.

For this demo we expect a REST interactivity, there are no web interaction forms.

# Build and run

Run a build to verify the code and configuration

```
quarkus build
```

Run the app in interactive mode

```
quarkus dev
```

## Test locally

Using another shell run the following commands

<i>Note: if you don't have 'jq' tool installed remove the final piping '| jq .'</i>

```
# set your base URL
URL=http://localhost:8080

# for the interactive REST gui navigate to http://<your-host-name>/q/swagger-ui/

# do some health check
curl -X GET ${URL}/q/health -H 'accept: application/json'
curl -X GET ${URL}/q/health/live -H 'accept: application/json'
curl -X GET ${URL}/q/health/ready -H 'accept: application/json'
curl -X GET ${URL}/q/health/group -H 'accept: application/json'
curl -X GET ${URL}/q/health/group/* -H 'accept: application/json'
curl -X GET ${URL}/q/health/well -H 'accept: application/json'
curl -X GET ${URL}/q/health/started -H 'accept: application/json'

# if you want to download the open api schema doc execute
curl -LO ${URL}/q/openapi

# ping
curl -s -w"\n" -X GET ${URL}/ping

# set vars
URL=http://localhost:8080
SERVICE_NAME=RequestProcess

# get the process model
curl -s -X GET -H 'accept: application/xml' ${URL}/${SERVICE_NAME}

# start a couple of instances
curl -s -X POST -H 'accept: application/json' -H 'Content-Type: application/json' ${URL}/${SERVICE_NAME} -d '{ "amount": 70000, "requestorAge": 58, "requestorName": "Marco" }' | jq .

curl -s -X POST -H 'accept: application/json' -H 'Content-Type: application/json' ${URL}/${SERVICE_NAME} -d '{ "amount": 6000, "requestorAge": 12, "requestorName": "Baby" }' | jq .

# get running instances (the first has been accepted automatically, the second has been routed to human task)
curl -s -X GET -H 'accept: application/json' -H 'Content-Type: application/json' ${URL}/${SERVICE_NAME} | jq .

# query tasks for a specific process ID

PROC_INST_ID="<...your-process-instance-id...>"
curl -s -X GET -H 'accept: application/json' -H 'Content-Type: application/json' ${URL}/${SERVICE_NAME}/${PROC_INST_ID}/tasks | jq .

curl -s -X GET -H 'accept: application/json' -H 'Content-Type: application/json' "${URL}/${SERVICE_NAME}/${PROC_INST_ID}/tasks?withInputData=true&withOutputData=true&withAssignments=true" | jq .

# complete task

TASK_NAME="VerifyRequest"
TASK_INST_ID="<...your-task-instance-id...>"
TASK_PHASE="complete"
curl -s -X POST -H 'accept: application/json' -H 'Content-Type: application/json' "${URL}/${SERVICE_NAME}/${PROC_INST_ID}/${TASK_NAME}/${TASK_INST_ID}?phase=${TASK_PHASE}"  -d '{ "validated": true }'

```

## Deploy on OpenShift cluster using IBM Kogito Operator

[TBD] See "..." at link "..."

# Useful infos




# Useful infos

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/MyKogitoDemoPam-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- SmallRye Health ([guide](https://quarkus.io/guides/microprofile-health)): Monitor service health
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- Kogito ([guide](https://quarkus.io/guides/kogito)): Add business automation capabilities - processes and rules with Kogito (a toolkit that originates from projects Drools and jBPM)

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### SmallRye Health

Monitor your application's health using SmallRye Health

[Related guide section...](https://quarkus.io/guides/smallrye-health)
# MyKogitoDemoPam - demo for IBM Kogito Operator deployments
