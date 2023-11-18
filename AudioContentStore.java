//MANAR ELBESHBISHY
//501089420
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore extends RuntimeException
{
		private ArrayList<AudioContent> contents;
		private Map<String, Integer> contentList;
		private Map<String, ArrayList<Integer>> artistList;
		private Map<String, ArrayList<Integer>> genreList;

		//method to read the content in "store.txt" file, and return the content as an arraylist
		private ArrayList<AudioContent> readAudioContent(String fileName) throws FileNotFoundException{
			//initializes the contents arraylist
			contents = new ArrayList<AudioContent>();
			//creates new file object with "store.txt", and reads file through scanner
			File storeContent = new File(fileName);
			Scanner input = new Scanner(storeContent);
			//while loop runs as long as there is still audioContent information in file
			while (input.hasNextLine()){
				String line = input.nextLine();
				//checks to see if the audioContent is of type song,, sets array for individual song content, and string for the audiofile
				if (line.equals("SONG")){
					String[] songContent = new String[7];
					String lyrics = "";
					//forloop iterates through the lines and sets the information into the array
					for (int i = 0; i < 7; i++){
						line = input.nextLine();
						songContent[i] = line;
					}
					//sets number of lyrics to int
					line = input.nextLine();
					int numOfLyrics = Integer.parseInt(line);
					//forloop runs through the lyrics and adds it to string
					for(int i = 0; i < numOfLyrics; i++){
						line = input.nextLine();
						lyrics +=  line + "\n";
					}
					//creates song object from information read from the file and adds to contents arraylist
					System.out.println("Loading SONG");
					contents.add(new Song(songContent[1], Integer.parseInt(songContent[2]), songContent[0], Song.TYPENAME, lyrics, Integer.parseInt(songContent[3]), songContent[4], songContent[5], Song.Genre.valueOf(songContent[6]), lyrics));
				}

				//same logic from "song" content type is used if the content is of type audiobook
				if(line.equals("AUDIOBOOK")){
					String[] audioBookContent = new String[6];
					ArrayList<String> titles = new ArrayList<String>();
					ArrayList<String> chapters = new ArrayList<String>();
					String chapter = "";
					for (int i = 0; i < 6; i++){
						line = input.nextLine();
						audioBookContent[i] = line;
					}
					line = input.nextLine();
					int numOfTitles = Integer.parseInt(line);
					for(int i = 0; i < numOfTitles; i++){
						line = input.nextLine();
						titles.add(line);
					}
					for (int i = 0; i < numOfTitles; i++){
						line = input.nextLine();
						int numOfLines = Integer.parseInt(line);
						for (int j = 0; j < numOfLines; j++){
							line = input.nextLine();
							chapter += line + "\r\n";
						}
						chapters.add(chapter);
					}
					System.out.println("Loading AUDIOBOOK");
					AudioBook book = new AudioBook(audioBookContent[1], Integer.parseInt(audioBookContent[2]), audioBookContent[0], AudioBook.TYPENAME, "", Integer.parseInt(audioBookContent[3]), audioBookContent[4], audioBookContent[5], titles, chapters);
					contents.add(book);
				}
			}
			//closes the scanner and returns the arraylist
			input.close();
			return contents;
		}

		//constructor to run the readAudioContent method and create maps
		public AudioContentStore()
		{
			//set a try/catch block to test and catch exceptions
			try {
				//runs "store.txt" through the method
				readAudioContent("store.txt");
				//initializes arraylists
				contentList = new HashMap<>();
				artistList = new HashMap<>();
				genreList = new HashMap<>();

				//creates map for all content in the store
				for (int i = 0; i < contents.size(); i++){
					contentList.put(contents.get(i).getTitle(), i);	
				}

				//creates map for artists and all their songs
				for (int i = 0; i < contents.size(); i++){

					//checks to see if object is of type song or audiobook, creates each respective object and checks to see if it is already in the map
					//if it is, adds that specifc object to key
					//if it is not, creates a new key/value pairing in map
					if (contents.get(i).getType() ==  Song.TYPENAME){
						Song newSong = (Song) contents.get(i);
						if(artistList.containsKey(newSong.getArtist())){
							artistList.get(newSong.getArtist()).add(i);
						}
						else{
							artistList.put(newSong.getArtist(), new ArrayList<Integer>());
							artistList.get(newSong.getArtist()).add(i);
						}
					}

					else if(contents.get(i).getType() == AudioBook.TYPENAME){
						AudioBook newBook = (AudioBook) contents.get(i);
						if(artistList.containsKey(newBook.getAuthor())){
							artistList.get(newBook.getAuthor()).add(i);
						}
						else{
							artistList.put(newBook.getAuthor(), new ArrayList<Integer>());
							artistList.get(newBook.getAuthor()).add(i);
						}

					}
				}

				//creates map for the genre and all songs of that genre
				for (int i = 0; i < contents.size(); i++){

					//checks to see if the object is of type SONG, creates song object if it is 
					//checks to see if the song is of that genre, and if it is already created as a key, adds the value of indice in contents to arraylist if it is
					//if it is not, creates a new key/value pairing in map
					if (contents.get(i).getType() == Song.TYPENAME){
						Song songGenre = (Song) contents.get(i);
						if (genreList.containsKey(songGenre.getGenre().toString())){
							genreList.get(songGenre.getGenre().toString()).add(i);
						}
						else{
							genreList.put(songGenre.getGenre().toString(), new ArrayList<Integer>());
							genreList.get(songGenre.getGenre().toString()).add(i);
						}
					}
				}
				
			}
			//catch block to catch exception
			catch(FileNotFoundException e){
				System.out.println(e.getMessage());
				System.exit(1);
			}
			
		}

		//method to search the map for the desired title
		public void searchTitle(String title){
			//checks to see if the title is in the map, if not, throws exception
			//if it is, prints information for content
			if(!contentList.containsKey(title)){
				throw new downloadException("No matches for " + title);
			}
			else{
				System.out.print("" + (contentList.get(title)+1) + ". ");
				contents.get(contentList.get(title)).printInfo();
				System.out.print("\n");
			}
			
		} 

		//method to search artist map for desired artist/song pairings
		public void searchArtist(String artist){
			//checks to see if artist is in the map, if not, throws exception
			//if it is, prints information for each song by that artist
			if(!artistList.containsKey(artist)){
				throw new downloadException("No matches for " + artist);
			}
			else{
				for(int i = 0; i < artistList.get(artist).size(); i++){
					System.out.print("" + (artistList.get(artist).get(i) + 1) +". ");
					contents.get(artistList.get(artist).get(i)).printInfo();
					System.out.print("\n");
			}
			}
			
		}

		//method to search genre map for desired genre/song pairings
		public void searchGenre(String genre){

			//checks to see if the genre is in the map, if not, throws exception
			//if it is, prints information for each song of that genre type
			if(!genreList.containsKey(genre)){
				throw new downloadException("No matches for " + genre);
			}
			else{
				for (int i = 0; i < genreList.get(genre).size(); i++){
					System.out.print("" + (genreList.get(genre).get(i) +1) + ". ");
					contents.get(genreList.get(genre).get(i)).printInfo();
					System.out.print("\n");
			}
			}
			
		}

		//method to get desired songs to download, returns arraylist of indices in the store
		public ArrayList<Integer> downloadA(String artist){

			//checks to see if the artist is in the map, if not, throws exception
			//if it is, returns the arraylist associated with the artist
			if(!artistList.containsKey(artist)){
				throw new downloadException("No matches for " + artist);
			}
			return artistList.get(artist);
		}

		//method to get desired songs to download, returns arraylist of indices in the store
		public ArrayList<Integer> downloadG(String genre){

			//checks to see if the genre is in the map, if not, throws exception
			//if it is, returns arraylist associated with genre
			if(!genreList.containsKey(genre)){
				throw new downloadException("No matches for " + genre);
			}
			return genreList.get(genre);
		}

		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.print("\n");
			}
		}
}
