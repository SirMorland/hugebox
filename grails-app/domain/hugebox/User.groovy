package hugebox

class User extends SpringUser {
    String name
    String profilePictureUrl

    static hasMany = [tracks: Track, playlists: Playlist]

    static constraints = {
    }
}
