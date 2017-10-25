package hugebox

class PlaylistTrack {
    Track track

    PlaylistTrack previousPlaylistTrack
    PlaylistTrack nextPlaylistTrack

    static constraints = {
        previousPlaylistTrack nullable: true
        nextPlaylistTrack nullable: true
    }
}
