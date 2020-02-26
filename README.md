# Cinemanteca
This app was created as a final project of the Android course taught in my college.

The main idea was to apply all the knowledge we got from the course, and any aditional 
things we thought of, on a cinema app. 

This app acts like a "cinema listing"; that retrieves data from the The Movie DB API, 
and displays it, allowing the user to see more information about it and save it on a fav list.

## Here is a list of the fancy things of the app:
- It was made using the MVVM architectural pattern. 
- Uses local DB with Room; that allowes not only to save fav movies, but does also
allow "offline mode", by saving the other movies that have been seen.
- The list of movies gets updated while typing on the search box.
- Factory pattern was used as one of the design patterns on the app, allowing it to show 
main movies either retrieved from Internet or the app database, depending on whether the
device is or not connected to Internet.
- Unit test was performed, but not thoroughly. The main focus of it, was to be able to create
mock classes by ourselves, instead of using mocking libraries.
- Coroutines are used to enhance response time from the app when doing background job, like
for instance, retrieving information from an API.
- Dependency Injection technique was used throughout the project, to decouple classes from
their dependencies. 

## Bullet of things covered:
- MVVM architectural pattern.
- LiveData component.
- ViewModel component.
- Data binding.
- Room component.
- Coroutines.
- Koin (Dependency Injection).
- Retrofit 2.
- Unit testing.
- Lottie (animations).
- Glide.
