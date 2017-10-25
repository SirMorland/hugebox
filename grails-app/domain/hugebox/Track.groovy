package hugebox

import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.Tag
import org.jaudiotagger.tag.images.Artwork

class Track {
    String title

    String album
    String artist
    String year
    String trackNumber

    int length
    String filePath

    static belongsTo = [user: User]

    static constraints = {
        album nullable: true
        artist nullable: true
        year nullable: true
        trackNumber nullable: true
    }

    /**
     * Get the artwork of the track as a data url.
     * @return The artwork of the track as data url
     */
    String getArtwork()
    {
        File file = new File(filePath)
        AudioFile audioFile = AudioFileIO.read(file)
        Tag tag = audioFile.tag
        Artwork artwork = tag.firstArtwork
        "data:" + artwork?.mimeType + ";base64," + artwork?.binaryData?.encodeBase64()?.toString()
    }

    /**
     * Get the audio data of the track as a data url.
     * @return The audio data of the track as a data url.
     */
    String getAudio()
    {
        File file = new File(filePath)
        "data:" + getMime(file.name) + ";base64," + file.bytes.encodeBase64().toString()
    }

    /**
     * Dirty way of getting mime type of the given file based on it's file ending.
     * TODO Get the real mime type based on real file format
     * @param file Given file
     * @return Mime type of the given file
     */
    String getMime(String file)
    {
        switch (file.substring(file.lastIndexOf(".")))
        {
            case ".mp3":
                return "audio/mpeg"
                break
            case ".wav":
                return "audio/wav"
                break
            case ".ogg":
                return "audio/ogg"
                break
            case ".flac":
                return "audio/flac"
                break
            default:
                return "audio/mpeg"
        }
    }
}