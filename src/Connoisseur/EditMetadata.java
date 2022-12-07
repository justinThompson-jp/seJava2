package Connoisseur;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;


/**
 * 
 * Code by: Aristan Galindo
 * This class will be used for extracting mp3 ID tags and filling in the IdTag java class.
 *
 */

public class EditMetadata {
	
	public static final IdTag getIdTag(File file, String fileName) throws IOException, UnsupportedAudioFileException,
	CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
		
		IdTag idTag = new IdTag();
		idTag.setTitle(fileName);
		
		
		// Reading tag info from a file
		AudioFile f = AudioFileIO.read(file);
		Tag tag = f.getTag();
		AudioHeader ah = f.getAudioHeader();
		
		// Checks the AudioHeader class for the track length as this is information
		// that can't be changed so is stored in a different class in the referenced
		// library
		if(ah != null)
			idTag.setDuration(ah.getTrackLength());
		
		// Checks the tag fields of the file for any metadata to fill out the
		// specified tags below
		if(tag != null) {
			
			if(tag.hasField(FieldKey.TITLE));

				// Using getFirst() allows us to get the first key in the specified field
				// in case there are multiple inputs in one field.
				idTag.setTitle(tag.getFirst(FieldKey.TITLE));
				idTag.setAlbum(tag.getFirst(FieldKey.ALBUM));
				idTag.setArtist(tag.getFirst(FieldKey.ARTIST));
				idTag.setGenre(tag.getFirst(FieldKey.GENRE));
				idTag.setYear(tag.getFirst(FieldKey.YEAR));
				
				}
		
		return idTag;
		
	}
	
	/* testing the code
	public static void main(String[] args) throws ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, TagException, CannotWriteException{
		
		
		File testFile = null;
		AudioFile file = AudioFileIO.read(testFile);
		Tag tag = file.getTag();
		tag.setField(FieldKey.GENRE, "Jazz Rock");
		tag.setField(FieldKey.ALBUM, "Alrightest Album");
		tag.setField(FieldKey.ARTIST, "Obscure Band");
		file.commit();

		System.out.println(testFile);
		
		
	}  */


}
