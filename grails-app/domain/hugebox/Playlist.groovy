package hugebox

class Playlist {
    String name
    PlaylistTrack firstPlaylistTrack

    static belongsTo = [user: User]

    static constraints = {
        firstPlaylistTrack nullable: true
    }

    /**
     * Add given track to the end of the playlist.
     * If playlist doesn't have tracks make the given track the first track of the playlist.
     * @param track Given track
     */
    void addTrack(Track track)
    {
        if(firstPlaylistTrack)
        {
            PlaylistTrack playlistTrack = new PlaylistTrack(
                    track: track,
                    previousPlaylistTrack: lastPlaylistTrack
            ).save()
            lastPlaylistTrack.nextPlaylistTrack = playlistTrack
        }
        else
        {
            firstPlaylistTrack = new PlaylistTrack(
                    track: track
            ).save()
        }
    }

    /**
     * Remove the given track from the playlist.
     * @param playlistTrack Track to be removed
     */
    void removePlaylistTrack(PlaylistTrack playlistTrack)
    {
        if(firstPlaylistTrack)
        {
            PlaylistTrack temp = firstPlaylistTrack

            if(temp == playlistTrack)
            {
                firstPlaylistTrack = temp.nextPlaylistTrack
                firstPlaylistTrack?.previousPlaylistTrack = null
                temp.delete()
            }
            else
            {
                while (temp.nextPlaylistTrack)
                {
                    temp = temp.nextPlaylistTrack
                    if(temp == playlistTrack)
                    {
                        temp.previousPlaylistTrack.nextPlaylistTrack = temp.nextPlaylistTrack
                        temp.nextPlaylistTrack?.previousPlaylistTrack = temp.previousPlaylistTrack
                        temp.delete()
                        break
                    }
                }
            }
        }
    }

    /**
     * Get the last track of the playlist.
     * If playlist has no tracks return null.
     * @return The last track of the playlist or null
     */
    PlaylistTrack getLastPlaylistTrack()
    {
        if(firstPlaylistTrack)
        {
            PlaylistTrack temp = firstPlaylistTrack
            while (temp.nextPlaylistTrack)
            {
                temp = temp.nextPlaylistTrack
            }
            return temp
        }
        return null
    }

    /**
     * Move track a to before track b.
     * If track a and b are the same do nothing.
     * If track b is not given move track a to the end of the playlist.
     * @param a Track a
     * @param b Track b (optional)
     */
    void movePlaylistTrack(PlaylistTrack a, PlaylistTrack b = null)
    {
        if(a == b)
        {
            return
        }

        if(b)
        {
            a.previousPlaylistTrack?.nextPlaylistTrack = a.nextPlaylistTrack
            a.nextPlaylistTrack?.previousPlaylistTrack = a.previousPlaylistTrack

            b.previousPlaylistTrack?.nextPlaylistTrack = a
            a.previousPlaylistTrack = b.previousPlaylistTrack
            b.previousPlaylistTrack = a
            a.nextPlaylistTrack = b
        }
        else if(a.nextPlaylistTrack)
        {
            PlaylistTrack temp = a
            while (temp.nextPlaylistTrack)
            {
                temp = temp.nextPlaylistTrack
            }

            a.previousPlaylistTrack?.nextPlaylistTrack = a.nextPlaylistTrack
            a.nextPlaylistTrack?.previousPlaylistTrack = a.previousPlaylistTrack

            temp.nextPlaylistTrack = a
            a.previousPlaylistTrack = temp
            a.nextPlaylistTrack = null
        }

        PlaylistTrack temp = a
        while (temp.previousPlaylistTrack)
        {
            temp = temp.previousPlaylistTrack
        }
        firstPlaylistTrack = temp
    }

    /**
     * Get the length of the playlist.
     * @return The length of the playlist
     */
    int getLength()
    {
        int i = 0

        if(firstPlaylistTrack)
        {
            PlaylistTrack temp = firstPlaylistTrack
            i++
            while (temp.nextPlaylistTrack)
            {
                temp = temp.nextPlaylistTrack
                i++
            }
        }

        return i
    }

    @Override
    String toString() {
        String s = name

        if(firstPlaylistTrack)
        {
            s += ": "
            PlaylistTrack temp = firstPlaylistTrack
            s += temp.track.title
            while (temp.nextPlaylistTrack)
            {
                temp = temp.nextPlaylistTrack
                s += ", " + temp.track.title
            }
        }

        return s
    }
}
