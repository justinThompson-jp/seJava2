package Connoisseur;

import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.TagField;

import java.util.List;
import java.util.Iterator;


public class EditMetadata {
	
	/*
	 * This is the basic structure for the imported library and the methods which will
	 * be used to set, add, and delete tags and their values to audio files.
	 * 
	 * The addField() method is supposed to allow for adding multiple values for one field
	 * type. It should allow for a custom tagging feature that would can set multiple custom
	 * tags to one file and be able to, later on, sort a list of files by the multiple values.
	 */
	
	public interface Tag {
		
		// Create the field based on the generic key and set it to the tag
		void setField(FieldKey genericKey, String... value) throws KeyNotFoundException, FieldDataInvalidException;
		
		/**
		* @param genericKey
		* @param value
		*/
	
		// Add the created field to the tag
		void addField(FieldKey genericKey, String... value) throws KeyNotFoundException, FieldDataInvalidException;
	
		/**
		 * @param genericKey
		 * @param value
		 */
		
		// Delete any field(s) with this key
		void deleteField(FieldKey fieldKey) throws KeyNotFoundException;
		
		/**
		 * @param fieldKey
		 */
		
		// Returns a list of objects with the specified "id" field
		// Can be used to retrieve fields with any identifier
		List<TagField> getFields(String id);
		
		/**
		 * @param id - field ID
		 * @return a list of objects with the specified "id" field
		 */
		
		List<TagField> getFields(FieldKey id) throws KeyNotFoundException;
		
		/**
		 * @param id - field ID
		 * @return a list of objects with the specified "id" field
		 */
		
		// Iterates over all fields within the tag
		Iterator<TagField> getFields();
		
		
		// Retrieves the first String value of this specific key
		String getFirst(String id);
		
		/**
		 * @param id
		 */
		
		// Get String value of the first tag field that exists for this specific key
		String getFirst (FieldKey id) throws KeyNotFoundException;
		
		/**
		 * @param id
		 * @return String value or empty string
		 */
		
		// Retrieve all String values that exist for this generic key
		List<String> getAll(FieldKey id) throws KeyNotFoundException;
		
		/**
		 * @param id
		 */
		
		// Get String value of nth tag field that exists for this specified generic key
		String getValue(FieldKey id, int n);
		
		/**
		 * @param id
		 * @param n
		 */
		
		// Get the first field that exists for the specific key
		// Can be used to retrieve fields with any identifier
		TagField getFirstField(String id);
		
		/**
		 * @param id - audio specific key
		 * @return tag field or null if it doesn't exist
		 */
		
		TagField getFirstField(FieldKey id);
		
		/**
		 * @param id
		 * @return the first field that matches this generic key
		 */
		
		// Returns 'true' if at least one of the contained TagField fields is a common field
		boolean hasCommonFields();
		
		/**
		 * @return true - if a common field is present
		 */
		
		// Determines whether the tag has at least one field with the specified "id"
		boolean hasfield(FieldKey fieldKey);
		
		/**
		 * @param id - the field id to look for
		 * @return true - if the tag contains a TagField with the given TagField id
		 */
		
		// Determines whether the tag has no fields specified
		boolean isEmpty();
		
		/**
		 * @return true - if tag contains no field
		 */
		
		// Sets a field to the structure
		void setField(TagField field) throws FieldDataInvalidException;
		
		/**
		 * @param field - the field to add
		 */
		
		// Add a field to the structure
		void addField(TagField field) throws FieldDataInvalidException;
		
		/**
		 * @param field - the field to add
		 */
		
		// Create a new field based on a generic key
		TagField createField(FieldKey genericKey, String... value) throws KeyNotFoundException, FieldDataInvalidException;
		
		/**
		 * @param genericKey is the generic key
		 * @param value to store
		 */
		
	}
	
	/* <example code for writing/editing tag info to a file>
	 * 
	 * AudioFile file = AudioFileIO.read(testFile);
	 * Tag tag = file.getTag();
	 * tag.setField(FieldKey.ARTIST, "Obscure Band");
	 * file.commit();
	 * 
	 * 
	 * <example code for setting fields>
	 * tag.set(FieldKey.ALBUM, "Alrightest Album of All Time");
	 * 
	 * tag.set(FieldKey.GENRE, "20s Jazz Rock");
	 * 
	 * 
	 */
}