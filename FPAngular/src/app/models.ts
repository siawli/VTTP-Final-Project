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
