//MANAR ELBESHBISHY
//501089420
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ActionMap;


// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			
			else if (action.equalsIgnoreCase("DOWNLOAD")){
				//download method takes in two integers as a range of content to download from store
				int fromIndex = 0;
				int toIndex = 0;
					
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					fromIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt()){
					toIndex = scanner.nextInt();
					scanner.nextLine();
				}
					//for loop runs through the number of content desire to download, set a try/catch block to test and catch any exceptions
					for (int i = fromIndex; i <= toIndex; i++){
						try{
							AudioContent content = store.getContent(i);
							
							if (content == null){
							System.out.println("Content Not Found in Store");
							}
							else{
								mylibrary.download(content);
							}
						}
						catch(downloadException e){
							System.out.println(e.getMessage());
							continue;
						}
					}
				}
			//action to download all songs from a single artist
			else if (action.equalsIgnoreCase("DOWNLOADA")){
				String artist = "";
				System.out.print("Artist Name: ");
				if(scanner.hasNextLine()){
					artist = scanner.nextLine();
				}

				try{
					//array list initialized to the returned arraylist from downloadA method (sets indices for content to download)
					ArrayList<Integer> artistArray = store.downloadA(artist);
					
					//for loop runs through number of songs in arraylist and downloads the songs, set a try/catch block to catch any exceptions
					for(int i = 0; i < artistArray.size(); i++){
						try{
							AudioContent content = store.getContent((artistArray.get(i)+1));
							mylibrary.download(content);
						}
						catch (downloadException e){
							System.out.println(e.getMessage());
						}
					}
				}
				catch (downloadException e){
					System.out.println(e.getMessage());
				}
			}
			//action to download all songs from a single genre
			else if(action.equalsIgnoreCase("DOWNLOADG")){
				String genre = "";
				System.out.print("Genre: ");
				if(scanner.hasNextLine()){
					genre = scanner.nextLine();
				}

				try{
					//array list initialized to the returned arraylist from downloadG method (sets indices for content to download)
					ArrayList<Integer> genreArray = store.downloadG(genre);

					//for loop runs through number of content to download, set a try/catch block to catch any exceptions
					for (int i = 0; i < genreArray.size(); i++){
						try{
							AudioContent content = store.getContent((genreArray.get(i)+1));
							mylibrary.download(content);
						}
						catch (downloadException e){
							System.out.println(e.getMessage());
						}
						
					}
				}
				catch (downloadException e){
					System.out.println(e.getMessage());
				}	
			}

			else if (action.equalsIgnoreCase("SEARCH")){
					//prompts for user input of title
					String title = "";
					System.out.print("Title: ");
					if (scanner.hasNext()){
						title = scanner.nextLine();
					}

					//try/catch block to run method and catch exceptions
					try{
					//calls searchTitle method, returns content information
					store.searchTitle(title);
					}
					catch (downloadException e){
						System.out.println(e.getMessage());
					}
				}

				//action to search content by artist/author
				else if (action.equalsIgnoreCase("SEARCHA")){
					//prompts for user input of artist/author
					String artist = "";
					System.out.print("Artist: ");
					if (scanner.hasNext()){
						artist = scanner.nextLine();
					}

					//try/catch block to run method and catch exceptions
					try{
					//calls searchAritst method, returns all content by artist/author
					store.searchArtist(artist);
					}
					catch (downloadException e){
						System.out.println(e.getMessage());
					}
				}
				
				//action to search content by genre
				else if (action.equalsIgnoreCase("SEARCHG")){
					//prompts for user input of genre
					String genre = "";
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
					if (scanner.hasNext()){
						genre = scanner.nextLine();
					}

					//try/catch block to run method and catch exceptions
					try{
					//calls searchGenre method, returns all content of certain genre
					store.searchGenre(genre);
					}
					catch (downloadException e){
						System.out.println(e.getMessage());
					}
				}

			//try/catch block to catch any exceptions from methods
			try{
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
				if (action.equalsIgnoreCase("PLAYSONG")) 
				{
					int index = 0; //initializes variable for user input
					System.out.print("Song Number: "); //prints prompt for user input
					if (scanner.hasNextInt()){
						index = scanner.nextInt(); //sets the variable as user input 
						scanner.nextLine();
					}
					//calls the playsong method	
					mylibrary.playSong(index);
				}

				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
					int index = 0;//initializes variable for user input
					System.out.print("Audio Book Number: ");//prints prompt for user input
					if (scanner.hasNextInt()){
						index = scanner.nextInt();//sets the variable as user input
						scanner.nextLine();
					}

					//calls printAudioBookTOC method
					mylibrary.printAudioBookTOC(index);
				}

				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter 
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{
					//initializes variables for user input
					int bookIndex = 0;
					int chapterIndex = 0;
					System.out.print("Audio Book Number: "); //prompts for user input
					if(scanner.hasNextInt()){
						bookIndex = scanner.nextInt(); //sets the variable as user input
						scanner.nextLine();
					}
					System.out.print("Chapter: "); //prompts for user input
					if(scanner.hasNextInt()){
						chapterIndex = scanner.nextInt(); //sets variable as user input
						scanner.nextLine();
					}

					//calls the playAudioBook method
					mylibrary.playAudioBook(bookIndex, chapterIndex);
				}

				// Specify a playlist title (string) 
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{
					String title = ""; //initializes variable for user input
					System.out.print("Playlist Title: "); //prompts for user input
					if(scanner.hasNext()){
						title = scanner.next(); //sets variable as user input
						scanner.nextLine();
					}

					//calls playPlaylist method
					mylibrary.playPlaylist(title);
				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				// Play all the audio content 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					//initializes variables for user input
					String title = "";
					int index = 0;
					System.out.print("Playlist Title: "); //prompts user for for input
					if(scanner.hasNext()){
						title = scanner.next(); //sets variable for user input
						scanner.nextLine();
					}
					System.out.print("Content Number: "); //prompts for user input
					if(scanner.hasNextInt()){
						index = scanner.nextInt(); //sets variable for user input
						scanner.nextLine();
					}

					//calls playPlaylist method
					mylibrary.playPlaylist(title, index);
				}

				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					int index = 0; //initializes variable for user input
					System.out.print("Library Song #: "); //prompts for user input
					if(scanner.hasNextInt()){
						index = scanner.nextInt(); //sets variable as user input
						scanner.nextLine();
					}

					//calls deleteSong method
					mylibrary.deleteSong(index); 
						
				}

				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					String title = ""; //initializes variable for user input
					System.out.print("Playlist Title: "); //prompts for user input
					if(scanner.hasNext()){
						title = scanner.next(); //sets variable as user input
						scanner.nextLine();
					}
					
					//calls makePlaylist method
					mylibrary.makePlaylist(title);
				
				}

				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
				{
					String title = ""; //initializes variable for user input
					System.out.print("Playlist Title: "); //prompts for user input
					if(scanner.hasNext()){
						title = scanner.next(); //sets variable as user input
						scanner.nextLine();
					}

					//calls printPlaylist method
					mylibrary.printPlaylist(title);
				}

				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					//initializes variable for user input
					String title = ""; 
					int index = 0;
					String type = "";
					System.out.print("Playlist Title: "); //prompts for user input
					if(scanner.hasNext()){
						title = scanner.next(); //sets variable as user input
						scanner.nextLine();
					}
					System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: "); //prompts for user input
					if(scanner.hasNext()){
						type = scanner.next(); //sets variable as user input
						scanner.nextLine();
					}
					System.out.print("Library Content #: "); //prompts for user input
					if(scanner.hasNextInt()){
						index = scanner.nextInt(); //sets variable as user input
					}
					
					//calls addContentToPlaylist method
					mylibrary.addContentToPlaylist(type, index, title);
				}

				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{
					//initializes variables for user input
					String title = "";
					int index = 0;
					System.out.print("Playlist Title: "); //prompts for user input
					if(scanner.hasNext()){
						title = scanner.next(); //sets variable as user input 
						scanner.nextLine();
					}
					System.out.print("Playlist Content #: "); //promps for user input
					if(scanner.hasNextInt()){
						index = scanner.nextInt(); //sets variable as user input
						scanner.nextLine();
					}

					//calls delContentFromPlaylist method
					mylibrary.delContentFromPlaylist(index, title);
				}
				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}
				
				//action to search content by title
				
				System.out.print("\n>");
			}

			//catch blocks to catch exceptions and print error messages, continue allows for the program to run after exception is caught
			catch (playSongException e){
				System.out.println(e.getMessage());
				continue;
			}
			catch(playAudioBookException e){
				System.out.println(e.getMessage());
				continue;
			}
			catch(printAudioBookTOCException e){
				System.out.println(e.getMessage());
				continue;
			}
			catch(makePlaylistException e){
				System.out.println(e.getMessage());
				continue;
			}
			catch(printPlaylistException e){
				System.out.println(e.getMessage());
				continue;
			}
			catch(addContentToPlaylistException e){
				System.out.println(e.getMessage());
				continue;
			}
			catch(delContentFromPlaylistException e){
				System.out.println(e.getMessage());
				continue;
			}
		}
	}
}
