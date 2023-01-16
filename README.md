## VTTP - Final Project: Master Kitchen

Master Kitchen is both web and mobile friendly, and provide users with the following functions:
1) Search for a recipe
2) Save recipes for future references
3) Snap and share a picture of their own cooks after trying out a recipe
4) Explore cooks by people who have tried out a recipe

*Test account*
- Email: fred@gmail.com
- Password: fred

Landing page of the application: Welcome to Master Kitchen
![Landing page](/FPServer/src/main/resources/static/LandingPage.png)

### Sign-up / Login page
* Users can sign up with their username, email and password.
  ![Sign-up page](/FPServer/src/main/resources/static/SignUp.png)
* Upon a successful sign up, an email will be sent to the email that was used upon signing-up and the user will be redirected back to the login page to login.
  ![Successful sign-up email](/FPServer/src/main/resources/static/SuccessSignUpEmail.png)

### 1) Explore
Users can explore the cooks snapped and uploaded by the community. Posts can be filtered according to date (latest first), popularity and by recipe ID. The default landing page shows posts filtered by latest first.
![Explore/Latest page](/FPServer/src/main/resources/static/LatestPage.png)

**Post**
* Each post contains of a title, recipe label, photo snapped, a short description, and the recipe ID. 
* Users are able to like the post by clicking on the heart icon.
* For users own post, they would be able to see a delete icon. Clicking on it would delete the post.
* Clicking the recipe ID link would navigate the user to the details of the recipe.
![Post](/FPServer/src/main/resources/static/Post.png)

### 2) Search for Recipes
Users can search for recipes. Entering a query into the search bar would return a list of recipes. Each page consists of 20 pages. Clicking on a recipe would navigate the user to the details of the recipe.
![Recipe list](/FPServer/src/main/resources/static/SearchRecipes.png)

**Recipe details**
* Each recipe detail states the number of servings, calories, ingredients required, recipe ID and the recipe instructions link.
* Clicking onto the recipe instructions link would open a browser tab that redirects to an external link with the recipe instructions.
* The 'Back' button would bring the user back to the previous page (explore page or recipe list).
* Clicking on 'Snap a cook' button would navigate the user to the 'Snap a cook' page.
* Users can also save this recipe by clicking on the 'Save recipe' button, changing the button to 'Recipe saved'. If the recipe has previously been saved, the button would be shown as 'Recipe saved' when the recipe details first load.
* Posts referencing this recipe ID can be found below the recipe details.
![recipe details](/FPServer/src/main/resources/static/RecipeDetails.png)
![recipe posts](/FPServer/src/main/resources/static/RecipeDetailsPost.png)

### 3) Snap a Cook
Users are able to snap a picture of their cook and post them on the explore page. 

There are 2 entry points to this page:
a) Via recipe details 'Snap a cook' button
b) Via 'Snap a cook' menu button

If entered via a), recipe ID and label fields would be disabled and filled up for you.
![a) entry via recipe details](/FPServer/src/main/resources/static/SearchDetails.png)

If entered via b), recipe label field disabled and would be filled upon entering the recipe label.
![b) entry via menu button](/FPServer/src/main/resources/static/SearchMenuTab.png)

Upon successful post submission, users can either choose to submit another post or be redirected back to the explore page.

### 4) My Profile
Users are able to see their own posts, liked posts as well as saved recipes.
![My Profile/My Posts](/FPServer/src/main/resources/static/ProfileMyPosts.png)
![My Profile/Saved recipes](/FPServer/src/main/resources/static/SavedRecipes.png)

### Logout
Upon logging out, user is returned back to the landing page.

---

### Features
1) Jwt Authentication: 
This app uses Jwt Authentication to ensure that the app functionalities can only be used when the user is logged in. The jwt token has an expiry of 30 mins, so any call to the server side > 30 mins will throw the user back to the landing page and the user has to log in again. 

2) Caching of API call: 
The recipe API has a restricted number of calls/minute, and hence, caching had to be done to minimize calls to the API. I cached the list of recipes by pagination (e.g. cake1, cake2), as well as the recipe details that the user clicks into. The API images hosted on Amazon each has a secret key that expires within a certain time limit, and hence, I set my cache time to live (expiry) of 15 minutes.

### Improvement / Further development
1) Allow a user to edit own post after posting
2) Cache the post images (static) so that they load faster after the first call
3) Fetch more posts only after the user has scrolled down a certain amount -> reduces loading time and data not used

---

### Technologies Used
* Languages: 
  - HTML & CSS
  - Java
  - Typescript
* Springboot for backend
* Angular for frontend
* Databases:
  - MySQL to store data
  - Redis for cahching of data
* RESTful API
