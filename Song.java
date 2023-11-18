/*
 * A Song is a type of AudioContent. A Song has extra fields such as Artist (person(s) singing the song) and composer 
 */
//MANAR ELBESHBISHY
//501089420
 public class Song extends AudioContent implements Comparable<Song> // implement the Comparable interface
{
	public static final String TYPENAME =	"SONG";
	
	public static enum Genre {POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL}; 
	private String artist; 		// Can be multiple names separated by commas
	private String composer; 	// Can be multiple names separated by commas
	private Genre  genre; 
	private String lyrics;
	
	
	
	public Song(String title, int year, String id, String type, String audioFile, int length, String artist,
			String composer, Song.Genre genre, String lyrics)
	{
		// Make use of the constructor in the super class AudioContent. 
		// Initialize additional Song instance variables. 
		super(title, year, id, type, audioFile, length); //uses super to initialize the variables already set in the superclass, AudioContent
		//initializes added variables not in the superclass
		this.artist = artist;
		this.composer = composer;
		this.genre = genre;
		this.lyrics = lyrics;
	}
	
	public String getType()
	{
		return TYPENAME;
	}
	
	// Print information about the song. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print artist, composer, genre 
	public void printInfo()
	{
		super.printInfo(); //calls the printInfo() function from the superclass to print first set of information
		System.out.print("Artist: " + artist + " Composer: " + composer + " Genre: " + genre + "\n"); //prints the remaining required song information
	}
	
	// Play the song by setting the audioFile to the lyrics string and then calling the play() method of the superclass
	public void play()
	{
		setAudioFile(lyrics); //sets the audiofile in the superclass to lyrics
		super.play(); //calls the play function from the superclass file to play the audiofile
		
	}
	
	public String getComposer()
	{
		return composer;
	}
	public void setComposer(String composer)
	{
		this.composer = composer;
	}
	
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	
	public String getLyrics()
	{
		return lyrics;
	}
	public void setLyrics(String lyrics)
	{
		this.lyrics = lyrics;
	}

	public Genre getGenre()
	{
		return genre;
	}

	public void setGenre(Genre genre)
	{
		this.genre = genre;
	}	
	
	// Two songs are equal if their AudioContent information is equal and both the composer and artists are the same
	// Make use of the superclass equals() method
	public boolean equals(Object other)
	{
		Song otherSong = (Song) other; //casts the other object to be an song object
		//compares the audiocontent information from using the superclass method, as well as the artist and composer to see if they are equal; returns true or false
		return super.equals(otherSong) && this.artist.equals(otherSong.artist) && this.composer.equals(otherSong.composer);
	}
	
	// Implement the Comparable interface 
	// Compare two songs based on their title
	// This method will allow songs to be sorted alphabetically
	public int compareTo(Song other)
	{
		//uses comparTo method from the implemented comparable to sort the song titles alphabetically
		return this.getTitle().compareTo(other.getTitle());
	}
}
