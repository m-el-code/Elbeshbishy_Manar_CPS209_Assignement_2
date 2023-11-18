//MANAR ELBESHBISHY
//501089420
import java.lang.management.PlatformLoggingMXBean;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.xml.sax.ErrorHandler;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		//checks to see if the content is of type: song
		if (content.getType() == Song.TYPENAME){
			//forloop runs through the song array and checks if the song is already in the arraylist; throws exception if it is
			for(int i = 0 ; i < songs.size(); i++){
				if (songs.get(i).equals(content)){
					throw new downloadException("SONG " + content.getTitle() + " already downloaded");
				}
			}
			//casts the content as a Song and adds to the song arraylist
			songs.add((Song) content);
			System.out.println("SONG " + content.getTitle() + " Added to Library");

		}
		//checks to see if content is of type: audiobook
		if (content.getType() == AudioBook.TYPENAME){
			//forloop runs through audiobook arraylist and checks if the audiobook is already in the arraylist; produces error message if it is 
			for(int i = 0 ; i < audiobooks.size(); i++){
				if (content.equals(audiobooks.get(i))){
					throw new downloadException("AUDIOBOOK " + content.getTitle() + " already downloaded");
				}
			}
			//casts the content as an Audiobook and adds to audiobooks arraylist
			audiobooks.add((AudioBook) content);
			System.out.println("AUDIOBOOK " + content.getTitle() + " Added to Library");
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		//forloop runs through all elements in the songs arraylist
		for (int i = 0; i < songs.size(); i++){
			int index = i + 1; //sets index to be printed
			System.out.print("" + index + ". "); //prints adjusted index
			songs.get(i).printInfo();//prints song info
			System.out.println("\n");//prints newline
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		//forloop runs through all elements in the audiobooks arraylist
		for (int i = 0; i < audiobooks.size(); i++){
			int index = i + 1; //sets index to be printed
			System.out.print(""+ index + ". ");//prints adjusted index
			audiobooks.get(i).printInfo();//prints audiobook info
			System.out.println("\n");//prints newline
		}
		
	}
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		//forloop runs through all elements in the playlists arraylist
		for (int i = 0; i < playlists.size(); i++){
			int index = i + 1; //sets index to be printed
			System.out.println("" + index + ". " + playlists.get(i).getTitle()); //prints adjusted index and playlist title
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> listOfArtists = new ArrayList<String>(); //new arraylist created to store artist names
		//forloop runs through songs arraylsit
		for(int i = 0; i < songs.size(); i++){
			//checks to see if the arraylsit already contains the artist, continues
			if (listOfArtists.contains(songs.get(i).getArtist())){
				continue;
			}
			//otherwise, adds the song to the arraylist
			else{
				listOfArtists.add(songs.get(i).getArtist());
			}
		}
		//forloop to run through the arraylist of artists
		for(int i = 0; i < listOfArtists.size(); i++){
			int index = i + 1; //sets index to be printed
			System.out.println(index + ". " + listOfArtists.get(i)); //prints the adjusted index and artist 
		}
		
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public boolean deleteSong(int index)
	{
		//forloop runs through the songs arraylist and removes the song at the given index
		AudioContent removeSong = (AudioContent) songs.get(index-1); //creates an audiocontent object of the song

		songs.remove(index-1); //removes the song from the songs arraylist

		//AudioContent removeSong = (AudioContent) songs.get(index-1);
		//forloop runs through the playlists arraylist
		for (int i = 0; i < playlists.size(); i++){
			//gets the content of the each playlist and removes the song if it is in the playlist
			for (int j = 0; j < playlists.get(i).getContent().size(); j++){ //forloop iterates through content array of playlist
				if(playlists.get(i).getContent().get(j).getType() == (Song.TYPENAME)){ //checks to see if the type of content is of type Song
					if(playlists.get(i).getContent().get(j).equals(removeSong)){ //if the song is the same, it is removed
						playlists.get(i).getContent().remove(removeSong);
						return true;
					}
				}
			}
		}
		return false;
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		//creates a new compartor to be used
		SongYearComparator compare = new SongYearComparator();
		//sorts the songs in the arraylist with the given comparator
		Collections.sort(songs, compare);
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
			//overrides the comoarator compare method
			@Override 
			//creates a new compare method, with two songs as the paramaters, using comparator logic
			public int compare(Song song1, Song song2){
				//if the year of song1 < song2, returns a -1
				if (song1.getYear() < song2.getYear()){
					return -1;
				}
				//if the year of song1 > song2, returns 1
				else if(song1.getYear() > song2.getYear()){
					return 1;
				}
				//otherwise, they are equal, and returns 0
				else{
					return 0;
				}
			}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 //creates a new comparator to be used
	 SongLengthComparator compare = new SongLengthComparator();
	 //sorts the songs in the arraylist using the comparator
	 Collections.sort(songs, compare);
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		//overrides the compare method in the comparator
		@Override
		//creates new compare method, with two songs as the parameters, using comparator logic
		public int compare(Song song1, Song song2){
			//if the length of song 1 < song2, returns -1
			if(song1.getLength() < song2.getLength()){
				return -1;
			}
			//if the length of song1 > song2, return 1
			else if(song1.getLength() > song2.getLength()){
				return 1;
			}
			//otherwise, lengths are equal, return 0
			else{
				return 0;
			}
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		//uses collections.sort and the compare method in class Song to sort the songs alphabetically by title
		Collections.sort(songs);
	}
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			//throws exception if song is not found
			throw new playSongException("Song not found");
		}
		songs.get(index-1).play();
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		//checks to see if the index is valid, throws exception if not valid
		if (index < 1 || index > audiobooks.size()){
			throw new playAudioBookException("AudioBook Not Found");
		}
		//calls the audiobook from arraylsit and the selected chapter is then set
		audiobooks.get(index-1).selectChapter(chapter);
		//calls the audiobook from the arraylist and plays selected content
		audiobooks.get(index-1).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		//checks to see if the index is valid, throws exception if not valid
		if (index < 1 || index > audiobooks.size()){
			throw new printAudioBookTOCException("AudioBook Not Found");
		}
		//calls the audiobook from the arraylsit and calls the printTOC method to print the table of contents of the audiobook
		audiobooks.get(index-1).printTOC();
		
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		//creates a new playlist and sets it as the given title
		Playlist newPlaylist = new Playlist(title);
		//forloop runs through the playlist arraylist to see if the playlist title already exists
		for(int i = 0; i < playlists.size(); i++){
			if(playlists.get(i).getTitle().equals(newPlaylist.getTitle())){
				//throws exception if playlist exists
				throw new makePlaylistException("Playlist " + title + " Already Exists");
			}
		} 
		//otherwise, adds playlist to the playlists arraylist
		playlists.add(newPlaylist);
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		//forloop runs through playlists arraylist
		for(int i = 0; i < playlists.size(); i++){
			//checks to find the given playlist title
			if(playlists.get(i).getTitle().equals(title)){
				//once found, playlist contents are then played by calling the printContents() method
				playlists.get(i).printContents();
			}
			else{

				//throws exception if the playlist does not exist
				throw new printPlaylistException("Playlist Does Not Exist");
			}
		}
	}
	
	// Play all content in a playlist
	public boolean playPlaylist(String playlistTitle)
	{
		//forloop runs through the playlists arraylist
		for(int i = 0; i < playlists.size(); i++){
			//checks to find the playlist
			if (playlists.get(i).getTitle().equals(playlistTitle)){
				//plays all the content in the given playlist
				playlists.get(i).playAll();
				return true;
			}
		}
		return false;
	}
	
	// Play a specific song/audiobook in a playlist
	public boolean playPlaylist(String playlistTitle, int indexInPL)
	{
		//forloop runs through the playlists arraylist
		for(int i = 0; i < playlists.size(); i++){
			//checks to find the given playlist
			if(playlists.get(i).getTitle().equals(playlistTitle)){
				//plays the content of the given playlist index
				playlists.get(i).play(indexInPL);
				return true;
			}
		}
		return false;
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		//check to see if type of content is song
		if(type.equalsIgnoreCase(Song.TYPENAME)){
			//checks if valid index, throws exception if not 
			if (index < 1 || index > songs.size()){
				throw new addContentToPlaylistException("Song Not Found");
			}
			//forloop runs through playlists arraylist, finds playlist and adds the song to the playlist
			for(int i = 0; i < playlists.size(); i++){
				if (playlists.get(i).getTitle().equals(playlistTitle)){
					playlists.get(i).addContent(songs.get(index-1));
				}
				
			}
		}
		//checks to see if type of content is audiobook
		else if(type.equalsIgnoreCase(AudioBook.TYPENAME)){
			//checks if valid index, throws excpetion if not
			if (index < 1 || index > audiobooks.size()){
				throw new addContentToPlaylistException("Audio Book Not Found");
			}
			//forloop runs throuhg playlists arraylist, finds playlist and adds the song to the playlist
			for(int i = 0; i < playlists.size(); i++){
				if (playlists.get(i).getTitle().equals(playlistTitle)){
					playlists.get(i).addContent(audiobooks.get(index-1));
				}
			}
		}
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		//forloop runs through playlists arraylist
		for(int i = 0; i < playlists.size(); i++){
			//checks to find the given playlist
			if (playlists.get(i).getTitle().equals(title)){
				//checks to see if the index is valid
				if(playlists.get(i).contains(index)){
					//deletes content from the playlist
					playlists.get(i).deleteContent(index);
				}
				else{
					//throws excpetion if content is not found in playlist
					throw new delContentFromPlaylistException("Playlist Content Not Found");
				}
			}
		}
		
	}
	
}
class downloadException extends RuntimeException{
	public downloadException(String message){
		super(message);
	}
}
class playSongException extends RuntimeException{
	public playSongException(String message){
		super(message);
	}

}
class playAudioBookException extends RuntimeException{
	public playAudioBookException(String message){
		super(message);
	}
}
class printAudioBookTOCException extends RuntimeException{
	public printAudioBookTOCException(String message){
		super(message);
	}
}
class makePlaylistException extends RuntimeException{
	public makePlaylistException(String message){
		super(message);
	}
}
class printPlaylistException extends RuntimeException{
	public printPlaylistException(String message){
		super(message);
	}
}
class addContentToPlaylistException extends RuntimeException{
	public addContentToPlaylistException(String message){
		super(message);
	}
}
class delContentFromPlaylistException extends RuntimeException{
	public delContentFromPlaylistException(String message){
		super(message);
	}
}
