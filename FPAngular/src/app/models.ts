export interface User {
    username: string,
    email: string,
    password: string
}

export interface Post {
    title: string,
    description: string,
    id: string,
    likes: number
    // image will be uploaded on spaces
}

export interface Ingredients {
    quantity: string,
    ingredient: string
}

