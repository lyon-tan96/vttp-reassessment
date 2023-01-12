export interface PostDetails {
    name: string
    email: string
    phone: string
    title: string
    description: string
    image: Blob
}

export interface PendingPost {
    postingId: string
    postingDate: string
    name: string
    email: string
    phone: string
    title: string
    description: string
    image: string
}