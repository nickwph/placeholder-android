# Android App for Placeholder

[![CircleCI](https://circleci.com/gh/nickwph/placeholder-android.svg?style=shield)](https://circleci.com/gh/nickwph/placeholder-android)

## Demo video

[![IMAGE ALT TEXT](http://img.youtube.com/vi/1oVEgwCw6nE/0.jpg)](http://www.youtube.com/watch?v=1oVEgwCw6nE "Placeholder demo")

## Features

### Application flow

This app requires you to select a user first, and you will be able to see the user info, posts, 
albums and todos of that person. You will be able to go deeper into album photos and post comments
by clicking those entries.

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
- JUnit5 and AssertJ for unit tests
- Room for local database
- Databinding for better UI layouts

### CI integration

This project is integrated with CircleCI and will build and test project whenever there are 
updates to the code base.

## To-do list

- [ ] Create posts / comments / albums / todos
- [ ] Upload photos
- [ ] More unit tests with Robolectric
