export interface User {
    username: string,
    email: string,
    password: string
}

export interface RecipeListResponse {
    recipes: Recipe[],
    nextURL: string
}

export interface RecipeDetailsResponse {
    recipe: Recipe,
    saved: boolean
}

export interface Recipe {
    recipe_id: string,
    storedUUID: string,
    label: string,
    image: string,
    link: string,
    servings: number,
    ingredientLines: string[],
    calories: number
}

export interface Post {
    post_id: number,
    email: string,
    username: string,
    title: string,
    caption: string,
    recipe_id: string,
    recipe_label: string,
    ratings: number,
    likes: number,
    date: string,
    imageUUID: string,
    // file
    liked: boolean
}
