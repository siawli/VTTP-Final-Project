## VTTP - Final Project: Master Kitchen

This app is both web and mobile friendly, and provide users with the following fucntions:
1) Search for a recipe
2) Save recipes for future references
3) Snap and share a picture of their own cooks after trying out a recipe
4) Explore cooks by people who have tried out a recipe

Landing page of the application: Welcome to Master Kitchen
![Landing page](/FPServer/src/main/resources/static/LandingPage.png)

### Sign-up / Login page
* Users can sign up with their username, email and password
* Upon a successful sign up: 
  - An email will be sent to the email that was used upon signing-up
    ![Successful sign-up email](/FPServer/src/main/resources/static/SuccessSignUpEmail.png)
  - User will be redirected to the login page
* Users can login to the application via the login page

### 1) Explore
Users can explore the cooks snapped and uploaded by the community. Posts can be filtered according to date (latest first), popularity and by recipe ID. The default landing page shows posts filtered by latest first.
![Explore/Latest page](/FPServer/src/main/resources/static/LatestPage.png)

*Post*
![Post](/FPServer/src/main/resources/static/Post.png)
* Each post contains of a title, recipe label, photo snapped, a short description, and the recipe ID. 
* Users are able to like the post by clicking on the heart icon.
* For users own post, they would be able to see a delete icon. Clicking on it would delete the post.
* Clicking the recipe ID link would navigate the user to the details of the recipe.

### 2) Search for Recipes
Users can search for recipes. Entering a query into the search bar would return a list of recipes. Each page consists of 20 pages. Clicking on a recipe would navigate the user to the details of the recipe.
![Recipe list](/FPServer/src/main/resources/static/SearchRecipes.png)

*Recipe details*
![recipe details](/FPServer/src/main/resources/static/RecipeDetails.png)
* Each recipe detail states the number of servings, calories, ingredients required, recipe ID and the recipe instructions link.
* Clicking onto the recipe instructions link would open a browser tab that redirects to an external link with the recipe instructions.
* The 'Back' button would bring the user back to the previous page (explore page or recipe list).
* Clicking on 'Snap a cook' button would navigate the user to the 'Snap a cook' page.
* Users can also save this recipe by clicking on the 'Save recipe' button, changing the button to 'Recipe saved'. If the recipe has previously been saved, the button would be shown as 'Recipe saved' when the recipe details first load.
* Posts referencing this recipe ID can be found below the recipe details.
![recipe posts](/FPServer/src/main/resources/static/RecipeDetailsPost.png)

### 3) Snap a cook
Users are able to snap a picture of their cook and post them on the explore page. 

There are 2 entry points to this page:
a) Via recipe details 'Snap a cook' button
b) Via 'Snap a cook' menu button

If entered via a), recipe ID and label fields would be disabled and filled up for you. If entered via b), recipe label field disabled and would be filled upon entering the recipe label.

![a) entry via recipe details](/FPServer/src/main/resources/static/SearchDetails.png) ![b) entry via menu button](/FPServer/src/main/resources/static/SearchMenuTab.png)

Upon successful post submission, users can either choose to submit another post or be redirected back to the explore page.

### 4) My Profile
Users are able to see their own posts, liked posts as well as saved recipes.
![My Profile/My Posts](/FPServer/src/main/resources/static/ProfileMyPosts.png)
![My Profile/My Posts](/FPServer/src/main/resources/static/SavedRecipes.png.png)

---

