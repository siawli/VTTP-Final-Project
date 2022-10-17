export interface User {
    username: string,
    email: string,
    password: string
}

export interface RecipeResponse {
    recipes: Recipe[],
    nextURL: string
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
    title: string,
    caption: string,
    recipe_id: string,
    ratings: number,
    likes: number,
    date: string,
    imageUUID: string,
    // file
    isLiked: boolean
}
