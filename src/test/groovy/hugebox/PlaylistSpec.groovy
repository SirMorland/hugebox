package hugebox

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PlaylistSpec extends Specification implements DomainUnitTest<Playlist> {

    Track track1
    Track track2
    Track track3

    def setup() {
        track1 = new Track(title: "Track 1")
        track2 = new Track(title: "Track 2")
        track3 = new Track(title: "Track 3")
    }

    def cleanup() {
    }

    void "test empty playlist"()
    {
        expect:
        Playlist playlist = new Playlist(name: "Playlist 1")

        playlist.firstPlaylistTrack == null
        playlist.lastPlaylistTrack == null
        playlist.length == 0
        playlist.toString() == "Playlist 1"

        playlist.removePlaylistTrack(new PlaylistTrack())
        playlist.toString() == "Playlist 1"
    }

    void "test playlist with one track"()
    {
        expect:
        Playlist playlist = new Playlist(name: "Playlist 1")

        playlist.addTrack(track1)
        playlist.firstPlaylistTrack == playlist.lastPlaylistTrack
        playlist.firstPlaylistTrack.track == track1
        playlist.firstPlaylistTrack.previousPlaylistTrack == null
        playlist.firstPlaylistTrack.nextPlaylistTrack == null
        playlist.length == 1
        playlist.toString() == "Playlist 1: Track 1"

        playlist.removePlaylistTrack(playlist.firstPlaylistTrack)
        playlist.length == 0
        playlist.toString() == "Playlist 1"
    }

    void "test playlist with two tracks"() {
        expect:
        Playlist playlist = new Playlist(name: "Playlist 1")

        playlist.addTrack(track1)
        playlist.addTrack(track2)
        playlist.firstPlaylistTrack != playlist.lastPlaylistTrack
        playlist.firstPlaylistTrack.nextPlaylistTrack == playlist.lastPlaylistTrack
        playlist.lastPlaylistTrack.previousPlaylistTrack == playlist.firstPlaylistTrack
        playlist.firstPlaylistTrack.track == track1
        playlist.lastPlaylistTrack.track == track2
        playlist.firstPlaylistTrack.previousPlaylistTrack == null
        playlist.lastPlaylistTrack.nextPlaylistTrack == null
        playlist.length == 2
        playlist.toString() == "Playlist 1: Track 1, Track 2"

        playlist.removePlaylistTrack(playlist.firstPlaylistTrack)
        playlist.firstPlaylistTrack == playlist.lastPlaylistTrack
        playlist.firstPlaylistTrack.track == track2
        playlist.firstPlaylistTrack.previousPlaylistTrack == null
        playlist.firstPlaylistTrack.nextPlaylistTrack == null
        playlist.length == 1
        playlist.toString() == "Playlist 1: Track 2"

        playlist.addTrack(track1)
        playlist.length == 2
        playlist.toString() == "Playlist 1: Track 2, Track 1"

        playlist.removePlaylistTrack(playlist.lastPlaylistTrack)
        playlist.firstPlaylistTrack == playlist.lastPlaylistTrack
        playlist.firstPlaylistTrack.track == track2
        playlist.firstPlaylistTrack.previousPlaylistTrack == null
        playlist.firstPlaylistTrack.nextPlaylistTrack == null
        playlist.length == 1
        playlist.toString() == "Playlist 1: Track 2"
    }

    void "test playlist with more tracks"() {
        expect:
        Playlist playlist = new Playlist(name: "Playlist 1")

        playlist.addTrack(track1)
        playlist.addTrack(track2)
        playlist.addTrack(track3)
        playlist.length == 3
        playlist.toString() == "Playlist 1: Track 1, Track 2, Track 3"

        playlist.removePlaylistTrack(playlist.firstPlaylistTrack.nextPlaylistTrack)
        playlist.firstPlaylistTrack.nextPlaylistTrack == playlist.lastPlaylistTrack
        playlist.lastPlaylistTrack.previousPlaylistTrack == playlist.firstPlaylistTrack
        playlist.firstPlaylistTrack.previousPlaylistTrack == null
        playlist.lastPlaylistTrack.nextPlaylistTrack == null
        playlist.length == 2
        playlist.toString() == "Playlist 1: Track 1, Track 3"

        playlist.addTrack(track2)
        playlist.length == 3
        playlist.toString() == "Playlist 1: Track 1, Track 3, Track 2"

        playlist.removePlaylistTrack(playlist.firstPlaylistTrack)
        playlist.firstPlaylistTrack.nextPlaylistTrack == playlist.lastPlaylistTrack
        playlist.lastPlaylistTrack.previousPlaylistTrack == playlist.firstPlaylistTrack
        playlist.firstPlaylistTrack.previousPlaylistTrack == null
        playlist.lastPlaylistTrack.nextPlaylistTrack == null
        playlist.length == 2
        playlist.toString() == "Playlist 1: Track 3, Track 2"

        playlist.addTrack(track1)
        playlist.length == 3
        playlist.toString() == "Playlist 1: Track 3, Track 2, Track 1"

        playlist.removePlaylistTrack(playlist.lastPlaylistTrack)
        playlist.firstPlaylistTrack.nextPlaylistTrack == playlist.lastPlaylistTrack
        playlist.lastPlaylistTrack.previousPlaylistTrack == playlist.firstPlaylistTrack
        playlist.firstPlaylistTrack.previousPlaylistTrack == null
        playlist.lastPlaylistTrack.nextPlaylistTrack == null
        playlist.length == 2
        playlist.toString() == "Playlist 1: Track 3, Track 2"
    }

    void "test moving tracks"() {
        expect:
        Playlist playlist = new Playlist(name: "Playlist 1")

        playlist.addTrack(track1)
        playlist.addTrack(track2)
        playlist.addTrack(track3)
        playlist.toString() == "Playlist 1: Track 1, Track 2, Track 3"

        PlaylistTrack pt1 = playlist.firstPlaylistTrack
        PlaylistTrack pt2 = pt1.nextPlaylistTrack
        PlaylistTrack pt3 = pt2.nextPlaylistTrack

        playlist.movePlaylistTrack(pt1, pt3)
        playlist.toString() == "Playlist 1: Track 2, Track 1, Track 3"

        playlist.movePlaylistTrack(pt2, pt3)
        playlist.toString() == "Playlist 1: Track 1, Track 2, Track 3"

        playlist.movePlaylistTrack(pt1, pt2)
        playlist.toString() == "Playlist 1: Track 1, Track 2, Track 3"

        playlist.movePlaylistTrack(pt3, pt2)
        playlist.toString() == "Playlist 1: Track 1, Track 3, Track 2"

        playlist.movePlaylistTrack(pt2, pt1)
        playlist.toString() == "Playlist 1: Track 2, Track 1, Track 3"

        playlist.movePlaylistTrack(pt2)
        playlist.toString() == "Playlist 1: Track 1, Track 3, Track 2"

        playlist.movePlaylistTrack(pt3, pt3)
        playlist.toString() == "Playlist 1: Track 1, Track 3, Track 2"
    }
}
