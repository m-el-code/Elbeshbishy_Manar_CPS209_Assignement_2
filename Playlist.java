//MANAR ELBESHBISHY
//501089420
import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		//initializes the title and content arraylist for the playlist
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook, podcast)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents()
	{
		//forloop to run through contents arraylist and print out the contents with the associated index, uses the printInfo() method to print the content
		for(int i = 0; i < contents.size(); i++){
			System.out.print(i+1 + ". ");
			contents.get(i).printInfo();
			System.out.println("\n");
		}
	}

	// Play all the AudioContent in the contents list
	public void playAll()
	{
		//forloop to run through the contents arraylist and play the content in the arraylist
		for(int i = 0; i < contents.size(); i++){
			contents.get(i).play();
			System.out.println("\n");
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index)
	{
		//uses if statement and contains method to see if the index is in the correct range
		if (contains(index)){
			//if the index is in the correct range, the index is adjusted to be zero indexed and the play method is called to play the desired content
			contents.get(index-1).play();
		}
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{
		//casts other to be a playlist object
		Playlist otherPlaylist = (Playlist) other;
		//returns true if playlist titles are equal, returns false if not
		return this.title.equals(otherPlaylist.title);
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	// The given index is 1-indexed so convert to 0-indexing before removing
	public void deleteContent(int index)
	{
		if (!contains(index)) return;
		contents.remove(index-1);
	}
	
	
}
