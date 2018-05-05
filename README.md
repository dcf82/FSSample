# FSSample
This is just a sample application that consumes FourSquare search feeds in JSON format.

# Description
The application is for demostration purposes, where you able to search for places, and also to navigate between all of the results obtained by the web services provided by FourSquare, so you have two ways to see your feeds, from List View or inside a Google Map.

In the technical side, it's very important to mention that this app has the purpose of demostrating the usage of several development tools, such as: RxJava/RxAndroid, ButterKniffe, Retrofit, Picasso, Google Maps & Dagger2 among others. The app has a special focus on code reusability, good code optimization and easy to maintain.

# App Navigation Flow
The app has basically three screens:

## HomeActivity (home)
This screen is where we actually type the search criterion (input text) we want to look for, it's very IMPORTANT to remember that we need to have a good internet connection to be able to get results from internet.

## FourSquareListActivity
This screen is basically where you'll see the results after a successful search, in the bottom left cortner of the screen we will se a button where we are able two switch from two different presentations, the results can be seen inside a List or direcly from a Google Maps view, so from both places you can click over any particular place and see the description of it.

## VenueItemDetailsActivity
This screen is basically where you will see the details of each place, and also you will be able to mark it as favorite by clicking over the favorite icon.
