# Outfit7 Backend junior-expertise-test project

Congratulations! You have just been hired to work on the backend for our newest RPG/strategy multiplayer game "Heroes of the Colosseum".

The backend is a simple matching web service that provides endpoints for retrieving opponents.

It consists of two main entry points:

- `UserFacade`: a utility endpoint to see all the data present in the database (DB is implemented as a simple json file)
- `MatchingFacade`: the main (matching) functionality

## Your mission:

- Build the project,
- Find & fix all the bugs,
- Implement a new matching service for ranked mode.

Feel free to change the code structure / architecture, or add any functionality, if you see something can be improved.

### Build the project

A developer pushed changes to our repository, but our CI system informed us that the code does not build. Find the error
and fix it. Check the chapter [Building and running the application](#building-and-running-the-application).

### Find & fix all the bugs

During testing, our QA found out that the endpoint `/matching/classic/d7fc5c61-ac15-48ca-9b14-f3d8f55b1946` does not
work as it should: the service returns duplicate opponents (opponents who have the same name).

Add a unit test to catch this case, find and fix the bug.

### Implement a new matching service for ranked mode.

Following the patterns in the project (take a look at usages of `ClassicMatchingService`) implement a new matching
service for ranked mode.

Implement a new endpoint for ranked mode: `/matching/ranked/{userId}`

Ranked mode should return 5 opponents for the user making the request, otherwise return an error so the client will
know to retry the request.
Opponents should be matched by `rank`, check the structure of the data in the example below and add a new field to
the `User` object.
Opponents must be in the same rank range as the player making the request: +/- 100 rank points.
Filter out duplicates by name, don't match the user against himself, and randomize the results
so users get a different set of opponents at each request.

Don't forget to write unit tests :)

```
// The sample data is already populated with values in the 'rank' field
{
    "id": "d7fc5c61-ac15-48ca-9b14-f3d8f55b1946",
    "playerName": "Clodomir Hairyfoot",
    "powerLevel": 1,
    "rank": 7,
    "hero": {
        "id": "0192a218-7a7d-456c-bd49-d06a8a69b762",
        "level": 31,
        "name": "Hal"
    },
    "champions": [
        {
            "id": "2e521a1e-2bd0-4dae-a6dd-96159e599d3e",
            "level": 3,
            "name": "Maxhere"
        }
    ]
}
```

## Submitting your solution:

Please send us either a zip archive or a link to project on github/bitbucket.

## Building and running the application

You can test/build your application using:
```
./gradlew test
./gradlew build
```

You can run your application in dev mode that enables live coding using:
```
./gradlew quarkusDev
```

Check the output logs for an entry like this:
```
(Quarkus Main Thread) Quarkus 1.4.1.Final started in 0.977s. Listening on: http://0.0.0.0:8080
```

Point your browser to the following address, to see the service working:
```
http://0.0.0.0:8080/user
http://0.0.0.0:8080/user/f4afb2fd-b4b9-41fe-ab7c-55059870e78a
```

---
This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, visit its website: [quarkus.io](https://quarkus.io/).