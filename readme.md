# Android App for Placeholder

## Features

### Integration with placeholder APIs

This app uses the APIs from placeholder in https://jsonplaceholder.typicode.com/

### Local database for offline uses

All views are displaying data from the location database, and will reflect the changes in 
real time if there is any updates. When data is downloaded from the server, they will be
inserted into the database, and which will also trigger changes in the UI.

## Architecture and code quality

### Library used

- Exposy for complex recycler views
- Retrofit, RxJava and Jackson for network abstractions
- Dagger for dependency injection

### Unit tests with JUnit 5 and JA

### CI integration

## To-do list

[ ] Create posts / comments / albums / todos
[ ] Upload photos
[ ] More unit tests with Robolectric
